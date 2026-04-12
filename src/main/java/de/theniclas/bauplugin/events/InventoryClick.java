package de.theniclas.bauplugin.events;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.InvHolder;
import de.theniclas.bauplugin.utils.InventoryCreator;
import de.theniclas.bauplugin.utils.Vars;

public class InventoryClick implements Listener {

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

            if (displayName.equals("\u00a7cWelt erstellen")) {
                openCreateWorldInventory(p);
                return;
            }
            if (displayName.equals("\u00a7bN\u00e4chste Seite") && e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
                InventoryCreator.currentPage.put(p, InventoryCreator.currentPage.get(p) + 1);
                InventoryCreator.openWorldInventory(p);
                return;
            }
            if (displayName.equals("\u00a7bVorherige Seite") && e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
                InventoryCreator.currentPage.put(p, InventoryCreator.currentPage.get(p) - 1);
                InventoryCreator.openWorldInventory(p);
                return;
            }

            String worldName = displayName.replace("\u00a7a", "");
            if (Configs.worldsConfig.getConfigurationSection("Worlds") == null
                    || !Configs.worldsConfig.getConfigurationSection("Worlds").getKeys(false).contains(worldName)) return;

            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                if (Configs.worldsConfig.get("Worlds." + worldName + ".Spawns") == null
                        || Configs.worldsConfig.getConfigurationSection("Worlds." + worldName + ".Spawns").getKeys(false).isEmpty()) {
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
            if (name.equals("\u00a77Void")) {
                p.closeInventory(); p.sendMessage(Vars.PR + "\u00a7aHalte ein Item in die Hand und gib einen Weltnamen ein");
                Vars.voidWorldName.add(p);
            } else if (name.equals("\u00a7bFlat")) {
                p.closeInventory(); p.sendMessage(Vars.PR + "\u00a7aHalte ein Item in die Hand und gib einen Weltnamen ein");
                Vars.flatWorldName.add(p);
            } else if (name.equals("\u00a7aNormal")) {
                p.closeInventory(); p.sendMessage(Vars.PR + "\u00a7aHalte ein Item in die Hand und gib einen Weltnamen ein");
                Vars.normalWorldName.add(p);
            }
            return;
        }

        if (id.startsWith("spawns:")) {
            e.setCancelled(true);
            String worldName = id.substring("spawns:".length());
            if (!e.getCurrentItem().hasItemMeta()) return;
            String spawnName = e.getCurrentItem().getItemMeta().getDisplayName().replace("\u00a75", "");
            if (e.getAction() == InventoryAction.PICKUP_HALF
                    && (Vars.isOwner(p, worldName) || p.hasPermission("bs.admin"))) {
                openDeleteSpawnInventory(p, worldName, spawnName);
            } else {
                String locStr = Configs.worldsConfig.getString("Worlds." + worldName + ".Spawns." + spawnName + ".Location");
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
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("\u00a74Welt l\u00f6schen")) {
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
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("\u00a74Spawnpunkt l\u00f6schen")) {
                p.closeInventory();
                Configs.worldsConfig.set("Worlds." + worldName + ".Spawns." + spawnName, null);
                Configs.saveConfiguration();
                p.sendMessage(Vars.PR + "\u00a7aSpawnpunkt erfolgreich gel\u00f6scht");
            }
        }
    }

    private void teleportToWorld(Player p, String worldName, double[] coords) {
        World w = Bukkit.getWorld("worlds/" + worldName);
        if (w != null) {
            p.teleport(coords != null ? new Location(w, coords[0], coords[1], coords[2]) : w.getSpawnLocation());
        } else {
            p.closeInventory();
            p.sendMessage(Vars.PR + "\u00a76Welt wird geladen...");
            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                World loaded = new WorldCreator("worlds/" + worldName).createWorld();
                if (loaded == null) { p.sendMessage(Vars.PR + "\u00a7cFehler beim Laden der Welt"); return; }
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
        ItemStack voidItem = named(new ItemStack(Material.WHITE_STAINED_GLASS), "\u00a77Void");
        inv.setItem(10, voidItem);
        for (int i : new int[]{3, 4, 5, 12, 14, 21, 22, 23}) inv.setItem(i, cyan);
        ItemStack flatItem = named(new ItemStack(Material.GRASS_BLOCK), "\u00a7bFlat");
        inv.setItem(13, flatItem);
        for (int i : new int[]{6, 7, 8, 15, 17, 24, 25, 26}) inv.setItem(i, lime);
        ItemStack normalItem = named(new ItemStack(Material.OAK_SAPLING), "\u00a7aNormal");
        inv.setItem(16, normalItem);
        p.openInventory(inv);
    }

    private void openSpawnpointsInventory(Player p, String worldName) {
        Inventory inv = Bukkit.createInventory(new InvHolder("spawns:" + worldName), 9);
        for (String spawn : Configs.worldsConfig.getConfigurationSection("Worlds." + worldName + ".Spawns").getKeys(false)) {
            ItemStack is = new ItemStack(Material.ENDER_EYE);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("\u00a75" + spawn);
            List<String> lore = new ArrayList<>();
            String locStr = Configs.worldsConfig.getString("Worlds." + worldName + ".Spawns." + spawn + ".Location");
            String[] arg = locStr.split(",");
            lore.add("\u00a76Location\u00a78: \u00a7e" + Math.round(Double.parseDouble(arg[1].trim()))
                    + ", " + Math.round(Double.parseDouble(arg[2].trim()))
                    + ", " + Math.round(Double.parseDouble(arg[3].trim())));
            if (Vars.isOwner(p, worldName) || p.hasPermission("bs.admin"))
                lore.add("\u00a7cRechtsklick zum L\u00f6schen");
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
        bm.setDisplayName("\u00a74Welt l\u00f6schen");
        bm.setLore(List.of("\u00a7cAchtung, dieser Vorgang kann nicht r\u00fcckg\u00e4ngig gemacht werden"));
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
        bm.setDisplayName("\u00a74Spawnpunkt l\u00f6schen");
        bm.setLore(List.of("\u00a7cAchtung, dieser Vorgang kann nicht r\u00fcckg\u00e4ngig gemacht werden"));
        barrier.setItemMeta(bm);
        inv.setItem(13, barrier);
        p.openInventory(inv);
    }

    private void deleteWorld(Player p, String worldName) {
        if (Configs.worldsConfig.getString("Spawn.World") == null) {
            p.sendMessage(Vars.PR + "\u00a7cKein globaler Spawn gesetzt, Welten k\u00f6nnen nicht gel\u00f6scht werden"); return;
        }
        if (Configs.worldsConfig.getString("Spawn.World").equals("worlds/" + worldName)) {
            p.sendMessage(Vars.PR + "\u00a7cDiese Welt kann nicht gel\u00f6scht werden (globaler Spawn)"); return;
        }
        p.closeInventory();
        World spawnWorld = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
        double sx = Configs.worldsConfig.getDouble("Spawn.X");
        double sy = Configs.worldsConfig.getDouble("Spawn.Y");
        double sz = Configs.worldsConfig.getDouble("Spawn.Z");
        Location spawnLoc = spawnWorld != null ? new Location(spawnWorld, sx, sy, sz) : null;
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.getWorld().getName().equals("worlds/" + worldName)) {
                if (spawnLoc != null) all.teleport(spawnLoc);
                all.sendMessage(Vars.PR + "\u00a76Die Welt wurde gel\u00f6scht");
            }
        }
        p.sendMessage(Vars.PR + "\u00a76Welt wird gel\u00f6scht...");
        Configs.worldsConfig.set("Worlds." + worldName, null);
        Configs.saveConfiguration();
        if (Bukkit.getWorld("worlds/" + worldName) != null) Bukkit.unloadWorld("worlds/" + worldName, false);
        File worldDir = new File("worlds/" + worldName);
        if (worldDir.exists()) {
            try (Stream<Path> paths = Files.walk(worldDir.toPath())) {
                paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                    try { Files.delete(path); } catch (IOException ex) { ex.printStackTrace(); }
                });
            } catch (IOException ex) { ex.printStackTrace(); }
        }
        p.sendMessage(Vars.PR + "\u00a7aWelt erfolgreich gel\u00f6scht");
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
