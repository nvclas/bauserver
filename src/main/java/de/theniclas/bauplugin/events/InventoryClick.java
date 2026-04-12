package de.theniclas.bauplugin.events;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.theniclas.bauplugin.Bauserver;
import de.theniclas.bauplugin.utils.InvHolder;
import de.theniclas.bauplugin.utils.Vars;

@RequiredArgsConstructor
public class InventoryClick implements Listener {

    private final Bauserver plugin;

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;

        InventoryHolder holder = e.getInventory().getHolder();
        if (!(holder instanceof InvHolder ih)) return;
        String id = ih.getId();

        if (id.equals("tools") || id.equals("blocks")) {
            e.setCancelled(true);
            p.getInventory().addItem(e.getCurrentItem().clone());
            return;
        }

        if (id.equals("worlds")) {
            e.setCancelled(true);
            if (!e.getCurrentItem().hasItemMeta()) return;
            String displayName = e.getCurrentItem().getItemMeta().getDisplayName();

            if (displayName.equals("§cWelt erstellen")) {
                openCreateWorldInventory(p);
                return;
            }
            if (displayName.equals("§bNächste Seite") && e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
                plugin.getInventoryCreator().setCurrentPage(p, plugin.getInventoryCreator().getCurrentPage(p) + 1);
                plugin.getInventoryCreator().openWorldInventory(p);
                return;
            }
            if (displayName.equals("§bVorherige Seite") && e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
                plugin.getInventoryCreator().setCurrentPage(p, plugin.getInventoryCreator().getCurrentPage(p) - 1);
                plugin.getInventoryCreator().openWorldInventory(p);
                return;
            }

            String worldName = displayName.replace("§a", "");
            if (plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection("Worlds") == null
                    || !plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection("Worlds").getKeys(false).contains(worldName)) return;

            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                if (plugin.getBauserverConfig().getWorldsConfig().get("Worlds." + worldName + ".Spawns") == null
                        || plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection("Worlds." + worldName + ".Spawns").getKeys(false).isEmpty()) {
                    teleportToWorld(p, worldName, null);
                } else {
                    openSpawnpointsInventory(p, worldName);
                }
            } else if (e.getAction() == InventoryAction.PICKUP_HALF
                    && (Vars.isOwner(p, worldName) || p.hasPermission("bs.admin"))) {
                openDeleteWorldInventory(p, worldName);
            }
            return;
        }

        if (id.equals("create_world")) {
            e.setCancelled(true);
            if (!e.getCurrentItem().hasItemMeta()) return;
            String name = e.getCurrentItem().getItemMeta().getDisplayName();
            if (name.equals("§7Void")) {
                p.closeInventory(); p.sendMessage(Vars.PR + "§aHalte ein Item in die Hand und gib einen Weltnamen ein");
                Vars.voidWorldName.add(p);
            } else if (name.equals("§bFlat")) {
                p.closeInventory(); p.sendMessage(Vars.PR + "§aHalte ein Item in die Hand und gib einen Weltnamen ein");
                Vars.flatWorldName.add(p);
            } else if (name.equals("§aNormal")) {
                p.closeInventory(); p.sendMessage(Vars.PR + "§aHalte ein Item in die Hand und gib einen Weltnamen ein");
                Vars.normalWorldName.add(p);
            }
            return;
        }

        if (id.startsWith("spawns:")) {
            e.setCancelled(true);
            String worldName = id.substring("spawns:".length());
            if (!e.getCurrentItem().hasItemMeta()) return;
            String spawnName = e.getCurrentItem().getItemMeta().getDisplayName().replace("§5", "");
            if (e.getAction() == InventoryAction.PICKUP_HALF
                    && (Vars.isOwner(p, worldName) || p.hasPermission("bs.admin"))) {
                openDeleteSpawnInventory(p, worldName, spawnName);
            } else {
                String locStr = plugin.getBauserverConfig().getWorldsConfig().getString("Worlds." + worldName + ".Spawns." + spawnName + ".Location");
                if (locStr == null) return;
                String[] arg = locStr.split(",");
                double[] coords = new double[]{
                        Double.parseDouble(arg[1].trim()),
                        Double.parseDouble(arg[2].trim()),
                        Double.parseDouble(arg[3].trim())
                };
                teleportToWorld(p, arg[0].trim(), coords);
            }
            return;
        }

        if (id.startsWith("delete_world:")) {
            e.setCancelled(true);
            String worldName = id.substring("delete_world:".length());
            if (!e.getCurrentItem().hasItemMeta()) return;
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4Welt löschen")) {
                deleteWorld(p, worldName);
            }
            return;
        }

        if (id.startsWith("delete_spawn:")) {
            e.setCancelled(true);
            String rest = id.substring("delete_spawn:".length());
            int sep = rest.indexOf(':');
            if (sep < 0) return;
            String worldName = rest.substring(0, sep);
            String spawnName = rest.substring(sep + 1);
            if (!e.getCurrentItem().hasItemMeta()) return;
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4Spawnpunkt löschen")) {
                p.closeInventory();
                plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + worldName + ".Spawns." + spawnName, null);
                plugin.getBauserverConfig().saveConfiguration();
                p.sendMessage(Vars.PR + "§aSpawnpunkt erfolgreich gelöscht");
            }
        }
    }

    private void teleportToWorld(Player p, String worldName, double[] coords) {
        World w = Bukkit.getWorld("worlds/" + worldName);
        if (w != null) {
            p.teleport(coords != null ? new Location(w, coords[0], coords[1], coords[2]) : w.getSpawnLocation());
        } else {
            p.closeInventory();
            p.sendMessage(Vars.PR + "§6Welt wird geladen...");
            Bukkit.getScheduler().runTask(plugin, () -> {
                World loaded = new WorldCreator("worlds/" + worldName).createWorld();
                if (loaded == null) { p.sendMessage(Vars.PR + "§cFehler beim Laden der Welt"); return; }
                p.teleport(coords != null ? new Location(loaded, coords[0], coords[1], coords[2]) : loaded.getSpawnLocation());
            });
        }
    }

    private void openCreateWorldInventory(Player p) {
        Inventory inv = Bukkit.createInventory(new InvHolder("create_world"), 27);
        ItemStack gray = pane(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack cyan = pane(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemStack lime = pane(Material.LIME_STAINED_GLASS_PANE);
        for (int i : new int[]{0, 1, 2, 9, 11, 18, 19, 20}) inv.setItem(i, gray);
        ItemStack voidItem = named(new ItemStack(Material.WHITE_STAINED_GLASS), "§7Void");
        inv.setItem(10, voidItem);
        for (int i : new int[]{3, 4, 5, 12, 14, 21, 22, 23}) inv.setItem(i, cyan);
        ItemStack flatItem = named(new ItemStack(Material.GRASS_BLOCK), "§bFlat");
        inv.setItem(13, flatItem);
        for (int i : new int[]{6, 7, 8, 15, 17, 24, 25, 26}) inv.setItem(i, lime);
        ItemStack normalItem = named(new ItemStack(Material.OAK_SAPLING), "§aNormal");
        inv.setItem(16, normalItem);
        p.openInventory(inv);
    }

    private void openSpawnpointsInventory(Player p, String worldName) {
        Inventory inv = Bukkit.createInventory(new InvHolder("spawns:" + worldName), 9);
        for (String spawn : plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection("Worlds." + worldName + ".Spawns").getKeys(false)) {
            ItemStack is = new ItemStack(Material.ENDER_EYE);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§5" + spawn);
            List<String> lore = new ArrayList<>();
            String locStr = plugin.getBauserverConfig().getWorldsConfig().getString("Worlds." + worldName + ".Spawns." + spawn + ".Location");
            String[] arg = locStr.split(",");
            lore.add("§6Location§8: §e" + Math.round(Double.parseDouble(arg[1].trim()))
                    + ", " + Math.round(Double.parseDouble(arg[2].trim()))
                    + ", " + Math.round(Double.parseDouble(arg[3].trim())));
            if (Vars.isOwner(p, worldName) || p.hasPermission("bs.admin"))
                lore.add("§cRechtsklick zum Löschen");
            im.setLore(lore);
            is.setItemMeta(im);
            inv.addItem(is);
        }
        p.openInventory(inv);
    }

    private void openDeleteWorldInventory(Player p, String worldName) {
        Inventory inv = Bukkit.createInventory(new InvHolder("delete_world:" + worldName), 27);
        ItemStack gray = pane(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack red = pane(Material.RED_STAINED_GLASS_PANE);
        for (int i = 0; i < 27; i++) inv.setItem(i, gray);
        for (int i : new int[]{3, 4, 5, 12, 14, 21, 22, 23}) inv.setItem(i, red);
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta bm = barrier.getItemMeta();
        bm.setDisplayName("§4Welt löschen");
        bm.setLore(List.of("§cAchtung, dieser Vorgang kann nicht rückgängig gemacht werden"));
        barrier.setItemMeta(bm);
        inv.setItem(13, barrier);
        p.openInventory(inv);
    }

    private void openDeleteSpawnInventory(Player p, String worldName, String spawnName) {
        Inventory inv = Bukkit.createInventory(new InvHolder("delete_spawn:" + worldName + ":" + spawnName), 27);
        ItemStack gray = pane(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack red = pane(Material.RED_STAINED_GLASS_PANE);
        for (int i = 0; i < 27; i++) inv.setItem(i, gray);
        for (int i : new int[]{3, 4, 5, 12, 14, 21, 22, 23}) inv.setItem(i, red);
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta bm = barrier.getItemMeta();
        bm.setDisplayName("§4Spawnpunkt löschen");
        bm.setLore(List.of("§cAchtung, dieser Vorgang kann nicht rückgängig gemacht werden"));
        barrier.setItemMeta(bm);
        inv.setItem(13, barrier);
        p.openInventory(inv);
    }

    private void deleteWorld(Player p, String worldName) {
        if (plugin.getBauserverConfig().getWorldsConfig().getString("Spawn.World") == null) {
            p.sendMessage(Vars.PR + "§cKein globaler Spawn gesetzt, Welten können nicht gelöscht werden"); return;
        }
        if (plugin.getBauserverConfig().getWorldsConfig().getString("Spawn.World").equals("worlds/" + worldName)) {
            p.sendMessage(Vars.PR + "§cDiese Welt kann nicht gelöscht werden (globaler Spawn)"); return;
        }
        p.closeInventory();
        World spawnWorld = Bukkit.getWorld(plugin.getBauserverConfig().getWorldsConfig().getString("Spawn.World"));
        double sx = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.X");
        double sy = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.Y");
        double sz = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.Z");
        Location spawnLoc = spawnWorld != null ? new Location(spawnWorld, sx, sy, sz) : null;
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.getWorld().getName().equals("worlds/" + worldName)) {
                if (spawnLoc != null) all.teleport(spawnLoc);
                all.sendMessage(Vars.PR + "§6Die Welt wurde gelöscht");
            }
        }
        p.sendMessage(Vars.PR + "§6Welt wird gelöscht...");
        plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + worldName, null);
        plugin.getBauserverConfig().saveConfiguration();
        if (Bukkit.getWorld("worlds/" + worldName) != null) Bukkit.unloadWorld("worlds/" + worldName, false);
        File worldDir = new File("worlds/" + worldName);
        if (worldDir.exists()) {
            try (Stream<Path> paths = Files.walk(worldDir.toPath())) {
                paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                    try { Files.delete(path); } catch (IOException ex) { ex.printStackTrace(); }
                });
            } catch (IOException ex) { ex.printStackTrace(); }
        }
        p.sendMessage(Vars.PR + "§aWelt erfolgreich gelöscht");
    }

    private ItemStack pane(Material mat) {
        ItemStack is = new ItemStack(mat);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(" ");
        is.setItemMeta(im);
        return is;
    }

    private ItemStack named(ItemStack item, String name) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        item.setItemMeta(im);
        return item;
    }
}
