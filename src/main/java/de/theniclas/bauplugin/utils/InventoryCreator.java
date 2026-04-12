package de.theniclas.bauplugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryCreator {

    public static HashMap<Player, Integer> currentPage = new HashMap<>();
    private static final ArrayList<ItemStack> items = new ArrayList<>();

    private static ItemStack pane(Material mat) {
        ItemStack is = new ItemStack(mat);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(" ");
        is.setItemMeta(im);
        return is;
    }

    public static void openWorldInventory(Player p) {
        items.clear();
        Inventory inv = Bukkit.createInventory(new InvHolder("worlds"), 6 * 9);

        for (int i : new int[]{0, 1, 2, 6, 7, 8}) inv.setItem(i, pane(Material.GRAY_STAINED_GLASS_PANE));
        inv.setItem(3, pane(Material.WHITE_STAINED_GLASS_PANE));
        ItemStack create = new ItemStack(Material.NETHER_STAR);
        ItemMeta cm = create.getItemMeta();
        cm.setDisplayName("§cWelt erstellen");
        create.setItemMeta(cm);
        inv.setItem(4, create);
        inv.setItem(5, pane(Material.WHITE_STAINED_GLASS_PANE));

        for (int i : new int[]{9, 18, 27, 36, 17, 26, 35, 44})
            inv.setItem(i, pane(Material.LIGHT_BLUE_STAINED_GLASS_PANE));

        ItemStack prev = new ItemStack(Material.GUNPOWDER);
        ItemMeta pm = prev.getItemMeta();
        pm.setDisplayName("§bVorherige Seite");
        prev.setItemMeta(pm);
        inv.setItem(45, prev);
        for (int i : new int[]{46, 47, 48, 49, 50, 51, 52}) inv.setItem(i, pane(Material.GRAY_STAINED_GLASS_PANE));
        ItemStack next = new ItemStack(Material.GUNPOWDER);
        ItemMeta nm = next.getItemMeta();
        nm.setDisplayName("§bNächste Seite");
        next.setItemMeta(nm);
        inv.setItem(53, next);

        ConfigurationSection worldNames = Configs.worldsConfig.getConfigurationSection("Worlds");
        if (worldNames != null) {
            for (String world : worldNames.getKeys(false)) {
                boolean locked = Configs.worldsConfig.getBoolean("Worlds." + world + ".Properties.Locked");
                boolean lockedSet = Configs.worldsConfig.get("Worlds." + world + ".Properties.Locked") != null;
                if (locked && lockedSet && !p.hasPermission("bs.admin") && !p.hasPermission("bs.seelocked")) continue;
                if (!Configs.worldsConfig.getBoolean("Visibility") && !p.hasPermission("bs.seeworlds")) {
                    String owner = Configs.worldsConfig.getString("Worlds." + world + ".Owner");
                    if (!Vars.isOwner(p, world) && !Vars.isTrusted(p, world) && !"0".equals(owner)) continue;
                }
                items.add(buildWorldItem(p, world));
            }
        }

        int page = currentPage.get(p);
        int start = (page - 1) * 28;
        int end = Math.min(start + 28, items.size());
        for (int i = start; i < end; i++) inv.addItem(items.get(i));

        if (items.size() > 28 * page) inv.getItem(53).setType(Material.GLOWSTONE_DUST);
        if (page > 1) inv.getItem(45).setType(Material.GLOWSTONE_DUST);

        p.openInventory(inv);
    }

    private static ItemStack buildWorldItem(Player p, String world) {
        String symbolStr = Configs.worldsConfig.getString("Worlds." + world + ".Symbol");
        Material mat = Material.GRASS_BLOCK;
        if (symbolStr != null) {
            String matName = symbolStr.contains(":") ? symbolStr.split(":")[0] : symbolStr;
            Material m = Material.getMaterial(matName);
            if (m != null) mat = m;
        }
        ItemStack item = new ItemStack(mat);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§a" + world);
        List<String> lore = new ArrayList<>();
        String owner = Configs.worldsConfig.getString("Worlds." + world + ".Owner");
        lore.add("§6Ersteller§8: §e" + ("0".equals(owner) || owner == null ? "Niemand"
                : Bukkit.getOfflinePlayer(UUID.fromString(owner)).getName()));
        String type = Configs.worldsConfig.getString("Worlds." + world + ".Type");
        lore.add("§6Typ§8: §e" + (type == null || "0".equals(type) ? "Unbekannt" : type));
        if (Configs.worldsConfig.getBoolean("Worlds." + world + ".Properties.Locked")
                && Configs.worldsConfig.get("Worlds." + world + ".Properties.Locked") != null)
            lore.add("§4Welt gesperrt");
        if (Vars.isOwner(p, world) || p.hasPermission("bs.admin"))
            lore.add("§cRechtsklick zum Löschen");
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }
}
