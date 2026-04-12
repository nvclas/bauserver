package de.theniclas.bauplugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryCreator {

    public static final HashMap<Player, Integer> currentPage = new HashMap<>();

    private InventoryCreator() {}

    // -------------------------------------------------------------------------
    // Glass-pane helpers
    // -------------------------------------------------------------------------

    private static ItemStack grayPane() {
        return namedItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    }

    private static ItemStack lightBluePane() {
        return namedItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ");
    }

    private static ItemStack limePane() {
        return namedItem(Material.LIME_STAINED_GLASS_PANE, " ");
    }

    private static ItemStack redPane() {
        return namedItem(Material.RED_STAINED_GLASS_PANE, " ");
    }

    private static ItemStack whitePane() {
        return namedItem(Material.WHITE_STAINED_GLASS_PANE, " ");
    }

    private static ItemStack namedItem(Material mat, String name) {
        ItemStack is = new ItemStack(mat);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return is;
    }

    // -------------------------------------------------------------------------
    // World inventory
    // -------------------------------------------------------------------------

    public static void openWorldInventory(Player p) {
        List<ItemStack> items = buildWorldItems(p);

        Inventory inv = Bukkit.createInventory(null, 6 * 9, Vars.INV_WORLDS);

        // Top row decoration
        inv.setItem(0, grayPane()); inv.setItem(1, grayPane()); inv.setItem(2, grayPane());
        inv.setItem(3, whitePane());
        ItemStack createBtn = namedItem(Material.NETHER_STAR, ChatColor.RED + "Welt erstellen");
        inv.setItem(4, createBtn);
        inv.setItem(5, whitePane());
        inv.setItem(6, grayPane()); inv.setItem(7, grayPane()); inv.setItem(8, grayPane());

        // Side borders (light-blue)
        for (int slot : new int[]{9, 18, 27, 36, 17, 26, 35, 44}) {
            inv.setItem(slot, lightBluePane());
        }

        // Bottom row
        ItemStack prevBtn = namedItem(Material.GUNPOWDER, ChatColor.AQUA + "Vorherige Seite");
        inv.setItem(45, prevBtn);
        for (int i = 46; i <= 52; i++) inv.setItem(i, grayPane());
        ItemStack nextBtn = namedItem(Material.GUNPOWDER, ChatColor.AQUA + "Nächste Seite");
        inv.setItem(53, nextBtn);

        int page = currentPage.getOrDefault(p, 1);
        int startIndex = (page - 1) * 28;
        int endIndex   = Math.min(startIndex + 28, items.size());

        for (int i = startIndex; i < endIndex; i++) {
            inv.addItem(items.get(i));
        }

        if (items.size() > page * 28) {
            inv.setItem(53, namedItem(Material.GLOWSTONE_DUST, ChatColor.AQUA + "Nächste Seite"));
        }
        if (page > 1) {
            inv.setItem(45, namedItem(Material.GLOWSTONE_DUST, ChatColor.AQUA + "Vorherige Seite"));
        }

        p.openInventory(inv);
    }

    private static List<ItemStack> buildWorldItems(Player p) {
        List<ItemStack> items = new ArrayList<>();
        ConfigurationSection worldsSection = Configs.worldsConfig.getConfigurationSection("Worlds");
        if (worldsSection == null) return items;

        boolean globalVisibility = Configs.worldsConfig.getBoolean("Visibility");

        for (String world : worldsSection.getKeys(false)) {
            // Respect locked worlds
            boolean locked = Configs.worldsConfig.getBoolean("Worlds." + world + ".Properties.Locked");
            if (locked && !p.hasPermission("bs.admin") && !p.hasPermission("bs.seelocked")) continue;

            // Respect global visibility setting
            if (!globalVisibility && !p.hasPermission("bs.seeworlds")) {
                String owner = Configs.worldsConfig.getString("Worlds." + world + ".Owner");
                boolean isPublic = "0".equals(owner);
                if (!Vars.isOwner(p, world) && !Vars.isTrusted(p, world) && !isPublic) continue;
            }

            items.add(buildWorldItem(world, p, locked));
        }
        return items;
    }

    private static ItemStack buildWorldItem(String world, Player p, boolean locked) {
        String symbolName = Configs.worldsConfig.getString("Worlds." + world + ".Symbol");
        Material mat = symbolName != null ? Material.getMaterial(symbolName) : null;
        ItemStack worldItem = new ItemStack(mat != null ? mat : Material.GRASS_BLOCK);

        ItemMeta im = worldItem.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + world);

        List<String> lore = new ArrayList<>();
        String ownerUuid = Configs.worldsConfig.getString("Worlds." + world + ".Owner");
        if (!"0".equals(ownerUuid) && ownerUuid != null) {
            lore.add(ChatColor.GOLD + "Ersteller" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW
                    + Bukkit.getOfflinePlayer(UUID.fromString(ownerUuid)).getName());
        } else {
            lore.add(ChatColor.GOLD + "Ersteller" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + "Niemand");
        }

        String type = Configs.worldsConfig.getString("Worlds." + world + ".Type");
        if (type != null && !"0".equals(type)) {
            lore.add(ChatColor.GOLD + "Typ" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + type);
        } else {
            lore.add(ChatColor.GOLD + "Typ" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + "Unbekannt");
        }

        if (locked) lore.add(ChatColor.DARK_RED + "Welt gesperrt");

        if (Vars.isOwner(p, world) || p.hasPermission("bs.admin")) {
            lore.add(ChatColor.RED + "Rechtsklick zum Löschen");
        }

        im.setLore(lore);
        worldItem.setItemMeta(im);
        return worldItem;
    }
}
