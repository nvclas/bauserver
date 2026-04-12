package de.theniclas.bauplugin.events;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.InventoryCreator;
import de.theniclas.bauplugin.utils.Vars;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;

        String title = e.getView().getTitle();

        if (title.equals(Vars.INV_BLOCKS) || title.equals(Vars.INV_TOOLS)) {
            e.setCancelled(true);
            p.getInventory().addItem(e.getCurrentItem().clone());
            return;
        }

        if (title.equals(Vars.INV_WORLDS)) {
            handleWorldsInventory(e, p);
            return;
        }

        if (title.equals(Vars.INV_CREATE_WORLD)) {
            handleCreateWorldInventory(e, p);
            return;
        }

        // Spawn-point list for a world:  "§6<worldName>"
        if (title.startsWith(ChatColor.GOLD.toString())) {
            String worldName = ChatColor.stripColor(title);
            if (isKnownWorld(worldName)) {
                handleSpawnListInventory(e, p, worldName);
                return;
            }
        }

        // Delete-world confirmation:  "§c<worldName> löschen"
        if (title.startsWith(ChatColor.RED.toString())) {
            String stripped = ChatColor.stripColor(title);
            if (stripped.endsWith(" löschen")) {
                String worldName = stripped.substring(0, stripped.length() - " löschen".length());
                if (isKnownWorld(worldName)) {
                    handleDeleteWorldInventory(e, p, worldName);
                    return;
                }
            }
        }

        // Delete-spawn confirmation:  "§6<worldName> §5<spawnName>"
        if (title.startsWith(ChatColor.GOLD.toString()) && title.contains(" ")) {
            String stripped = ChatColor.stripColor(title);
            int spaceIdx = stripped.indexOf(' ');
            if (spaceIdx > 0) {
                String worldName = stripped.substring(0, spaceIdx);
                String spawnName = stripped.substring(spaceIdx + 1);
                if (isKnownWorld(worldName)) {
                    handleDeleteSpawnInventory(e, p, worldName, spawnName);
                }
            }
        }
    }

    // -------------------------------------------------------------------------
    // Worlds inventory  (§3Welten)
    // -------------------------------------------------------------------------

    private void handleWorldsInventory(InventoryClickEvent e, Player p) {
        e.setCancelled(true);
        if (!e.getCurrentItem().hasItemMeta()) return;

        String displayName = e.getCurrentItem().getItemMeta().getDisplayName();

        if (displayName.equals(ChatColor.RED + "Welt erstellen")) {
            openCreateWorldMenu(p);
            return;
        }

        if (displayName.equals(ChatColor.AQUA + "Nächste Seite") && e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
            int page = InventoryCreator.currentPage.getOrDefault(p, 1);
            InventoryCreator.currentPage.put(p, page + 1);
            InventoryCreator.openWorldInventory(p);
            return;
        }

        if (displayName.equals(ChatColor.AQUA + "Vorherige Seite") && e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
            int page = InventoryCreator.currentPage.getOrDefault(p, 1);
            InventoryCreator.currentPage.put(p, page - 1);
            InventoryCreator.openWorldInventory(p);
            return;
        }

        String worldName = ChatColor.stripColor(displayName);
        if (!isKnownWorld(worldName)) return;

        if (e.getAction() == InventoryAction.PICKUP_ALL) {
            // Left-click: teleport to world (or open spawn list)
            handleWorldTeleport(p, worldName);
        } else if (e.getAction() == InventoryAction.PICKUP_HALF
                && (Vars.isOwner(p, worldName) || p.hasPermission("bs.admin"))) {
            // Right-click (owner / admin): open delete confirmation
            openDeleteWorldMenu(p, worldName);
        }
    }

    private void handleWorldTeleport(Player p, String worldName) {
        var spawnsSection = Configs.worldsConfig.getConfigurationSection("Worlds." + worldName + ".Spawns");

        if (spawnsSection == null || spawnsSection.getKeys(false).isEmpty()) {
            // No custom spawn points – teleport directly to world spawn
            World world = Bukkit.getWorld("worlds/" + worldName);
            if (world != null) {
                p.teleport(world.getSpawnLocation());
            } else {
                p.closeInventory();
                p.sendMessage(Vars.PREFIX + "§6Welt wird geladen...");
                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                    World loaded = new WorldCreator("worlds/" + worldName).createWorld();
                    if (loaded != null) p.teleport(loaded.getSpawnLocation());
                });
            }
        } else {
            // Multiple spawn points – open a spawn-selection inventory
            openSpawnListMenu(p, worldName);
        }
    }

    private void openSpawnListMenu(Player p, String worldName) {
        Inventory spawnInv = Bukkit.createInventory(null, 9, ChatColor.GOLD + worldName);
        var spawnsSection = Configs.worldsConfig.getConfigurationSection("Worlds." + worldName + ".Spawns");
        for (String spawn : spawnsSection.getKeys(false)) {
            ItemStack is = new ItemStack(Material.ENDER_EYE);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(ChatColor.DARK_PURPLE + spawn);
            List<String> lore = new ArrayList<>();
            String locStr = Configs.worldsConfig.getString("Worlds." + worldName + ".Spawns." + spawn + ".Location");
            if (locStr != null) {
                String[] parts = locStr.split(", ");
                if (parts.length >= 4) {
                    lore.add(ChatColor.GOLD + "Location" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW
                            + Math.round(Double.parseDouble(parts[1])) + ", "
                            + Math.round(Double.parseDouble(parts[2])) + ", "
                            + Math.round(Double.parseDouble(parts[3])));
                }
            }
            if (Vars.isOwner(p, worldName) || p.hasPermission("bs.admin")) {
                lore.add(ChatColor.RED + "Rechtsklick zum Löschen");
            }
            im.setLore(lore);
            is.setItemMeta(im);
            spawnInv.addItem(is);
        }
        p.openInventory(spawnInv);
    }

    private void openDeleteWorldMenu(Player p, String worldName) {
        Inventory del = Bukkit.createInventory(null, 9 * 3,
                ChatColor.RED + worldName + " löschen");
        for (int i = 0; i < 27; i++) del.setItem(i, grayPane());

        // Surround slot 13 with red panes
        for (int s : new int[]{3, 4, 5, 12, 14, 21, 22, 23}) {
            del.setItem(s, redPane());
        }

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta bm = barrier.getItemMeta();
        bm.setDisplayName(ChatColor.DARK_RED + "Welt löschen");
        bm.setLore(List.of(ChatColor.RED + "Achtung, dieser Vorgang kann nicht rückgängig gemacht werden"));
        barrier.setItemMeta(bm);
        del.setItem(13, barrier);

        p.openInventory(del);
    }

    private void openCreateWorldMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, Vars.INV_CREATE_WORLD);

        // Void column (slots 0,9,18 border + slot 10 icon)
        for (int s : new int[]{0, 1, 2, 9, 11, 18, 19, 20}) inv.setItem(s, grayPane());
        ItemStack void_ = new ItemStack(Material.WHITE_STAINED_GLASS);
        ItemMeta vm = void_.getItemMeta(); vm.setDisplayName(ChatColor.GRAY + "Void"); void_.setItemMeta(vm);
        inv.setItem(10, void_);

        // Flat column
        for (int s : new int[]{3, 4, 5, 12, 14, 21, 22, 23}) inv.setItem(s, lightBluePane());
        ItemStack flat = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta fm = flat.getItemMeta(); fm.setDisplayName(ChatColor.AQUA + "Flat"); flat.setItemMeta(fm);
        inv.setItem(13, flat);

        // Normal column
        for (int s : new int[]{6, 7, 8, 15, 17, 24, 25, 26}) inv.setItem(s, limePane());
        ItemStack normal = new ItemStack(Material.OAK_SAPLING);
        ItemMeta nm = normal.getItemMeta(); nm.setDisplayName(ChatColor.GREEN + "Normal"); normal.setItemMeta(nm);
        inv.setItem(16, normal);

        p.openInventory(inv);
    }

    // -------------------------------------------------------------------------
    // "Welt erstellen" inventory
    // -------------------------------------------------------------------------

    private void handleCreateWorldInventory(InventoryClickEvent e, Player p) {
        e.setCancelled(true);
        if (!e.getCurrentItem().hasItemMeta()) return;

        String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
        switch (name) {
            case "Void"   -> { p.closeInventory(); p.sendMessage(Vars.PREFIX + "§aBitte nimm ein Item deiner Wahl in die Hand und gib einen Namen für deine Welt in den Chat ein"); Vars.voidWorldName.add(p); }
            case "Flat"   -> { p.closeInventory(); p.sendMessage(Vars.PREFIX + "§aBitte nimm ein Item deiner Wahl in die Hand und gib einen Namen für deine Welt in den Chat ein"); Vars.flatWorldName.add(p); }
            case "Normal" -> { p.closeInventory(); p.sendMessage(Vars.PREFIX + "§aBitte nimm ein Item deiner Wahl in die Hand und gib einen Namen für deine Welt in den Chat ein"); Vars.normalWorldName.add(p); }
        }
    }

    // -------------------------------------------------------------------------
    // Spawn-list inventory  (§6<worldName>)
    // -------------------------------------------------------------------------

    private void handleSpawnListInventory(InventoryClickEvent e, Player p, String worldName) {
        e.setCancelled(true);
        if (!e.getCurrentItem().hasItemMeta()) return;

        String spawnName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

        if (e.getAction() == InventoryAction.PICKUP_HALF
                && (Vars.isOwner(p, worldName) || p.hasPermission("bs.admin"))) {
            // Right-click: open delete-spawn confirmation
            Inventory del = Bukkit.createInventory(null, 9 * 3,
                    ChatColor.GOLD + worldName + " " + ChatColor.DARK_PURPLE + spawnName);
            for (int i = 0; i < 27; i++) del.setItem(i, grayPane());
            for (int s : new int[]{3, 4, 5, 12, 14, 21, 22, 23}) del.setItem(s, redPane());

            ItemStack barrier = new ItemStack(Material.BARRIER);
            ItemMeta bm = barrier.getItemMeta();
            bm.setDisplayName(ChatColor.DARK_RED + "Spawnpunkt löschen");
            bm.setLore(List.of(ChatColor.RED + "Achtung, dieser Vorgang kann nicht rückgängig gemacht werden"));
            barrier.setItemMeta(bm);
            del.setItem(13, barrier);
            p.openInventory(del);
        } else {
            // Left-click: teleport to spawn point
            String locStr = Configs.worldsConfig.getString("Worlds." + worldName + ".Spawns." + spawnName + ".Location");
            if (locStr == null) return;
            String[] parts = locStr.split(", ");
            if (parts.length < 4) return;
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);

            World world = Bukkit.getWorld("worlds/" + parts[0]);
            if (world != null) {
                p.teleport(new Location(world, x, y, z));
            } else {
                p.closeInventory();
                p.sendMessage(Vars.PREFIX + "§6Welt wird geladen...");
                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                    World loaded = new WorldCreator("worlds/" + parts[0]).createWorld();
                    if (loaded != null) p.teleport(new Location(loaded, x, y, z));
                });
            }
        }
    }

    // -------------------------------------------------------------------------
    // Delete-world confirmation inventory
    // -------------------------------------------------------------------------

    private void handleDeleteWorldInventory(InventoryClickEvent e, Player p, String worldName) {
        e.setCancelled(true);
        if (!e.getCurrentItem().hasItemMeta()) return;
        if (!ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Welt löschen")) return;

        String spawnWorld = Configs.worldsConfig.getString("Spawn.World");
        if (spawnWorld != null && spawnWorld.equals("worlds/" + worldName)) {
            p.sendMessage(Vars.PREFIX + "§cDiese Welt kann nicht gelöscht werden, da dort der globale Spawnpunkt gesetzt ist");
            return;
        }
        if (spawnWorld == null) {
            p.sendMessage(Vars.PREFIX + "§cEs können keine Welten gelöscht werden, da kein globaler Spawnpunkt erstellt wurde");
            return;
        }

        p.closeInventory();

        // Kick all players currently in the world back to spawn
        World spawnW = Bukkit.getWorld(spawnWorld);
        if (spawnW != null) {
            double sx = Configs.worldsConfig.getDouble("Spawn.X");
            double sy = Configs.worldsConfig.getDouble("Spawn.Y");
            double sz = Configs.worldsConfig.getDouble("Spawn.Z");
            Location spawnLoc = new Location(spawnW, sx, sy, sz);
            World target = Bukkit.getWorld("worlds/" + worldName);
            if (target != null) {
                for (Player other : target.getPlayers()) {
                    other.teleport(spawnLoc);
                    other.sendMessage(Vars.PREFIX + "§6Die Welt in der du dich befandest wurde gelöscht");
                }
            }
        }

        p.sendMessage(Vars.PREFIX + "§6Welt wird gelöscht...");
        Configs.worldsConfig.set("Worlds." + worldName, null);
        Configs.saveConfiguration();

        if (Bukkit.getWorld("worlds/" + worldName) != null) {
            Bukkit.unloadWorld("worlds/" + worldName, false);
        }

        try {
            deleteDirectory(new File("worlds/" + worldName));
            p.sendMessage(Vars.PREFIX + "§aWelt erfolgreich gelöscht");
        } catch (IOException ex) {
            Main.getPlugin().getLogger().severe("Failed to delete world directory: " + ex.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Delete-spawn confirmation inventory
    // -------------------------------------------------------------------------

    private void handleDeleteSpawnInventory(InventoryClickEvent e, Player p, String worldName, String spawnName) {
        e.setCancelled(true);
        if (!e.getCurrentItem().hasItemMeta()) return;
        if (!ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Spawnpunkt löschen")) return;

        p.closeInventory();
        Configs.worldsConfig.set("Worlds." + worldName + ".Spawns." + spawnName, null);
        Configs.saveConfiguration();
        p.sendMessage(Vars.PREFIX + "§aSpawnpunkt erfolgreich gelöscht");
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private boolean isKnownWorld(String name) {
        var section = Configs.worldsConfig.getConfigurationSection("Worlds");
        return section != null && section.getKeys(false).contains(name);
    }

    private static ItemStack grayPane() {
        ItemStack is = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta im = is.getItemMeta(); im.setDisplayName(" "); is.setItemMeta(im);
        return is;
    }

    private static ItemStack redPane() {
        ItemStack is = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta im = is.getItemMeta(); im.setDisplayName(" "); is.setItemMeta(im);
        return is;
    }

    private static ItemStack lightBluePane() {
        ItemStack is = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta im = is.getItemMeta(); im.setDisplayName(" "); is.setItemMeta(im);
        return is;
    }

    private static ItemStack limePane() {
        ItemStack is = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta im = is.getItemMeta(); im.setDisplayName(" "); is.setItemMeta(im);
        return is;
    }

    /** Recursively deletes a directory using Java NIO. */
    private static void deleteDirectory(File dir) throws IOException {
        Path path = dir.toPath();
        if (!Files.exists(path)) return;
        try (var stream = Files.walk(path)) {
            stream.sorted(Comparator.reverseOrder())
                  .map(Path::toFile)
                  .forEach(File::delete);
        }
    }
}
