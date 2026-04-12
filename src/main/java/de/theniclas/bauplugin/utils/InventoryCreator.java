package de.theniclas.bauplugin.utils;

import de.theniclas.bauplugin.Bauserver;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class InventoryCreator {

    private final Bauserver plugin;

    private final HashMap<Player, Integer> currentPage = new HashMap<>();
    private final ArrayList<ItemStack> items = new ArrayList<>();

    private static ItemStack pane(Material mat) {
        ItemStack is = new ItemStack(mat);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(" ");
        is.setItemMeta(im);
        return is;
    }

    public int getCurrentPage(Player p) {
        return currentPage.getOrDefault(p, 1);
    }

    public void setCurrentPage(Player p, int page) {
        currentPage.put(p, page);
    }

    public void openWorldInventory(Player p) {
        items.clear();
        Inventory inv = Bukkit.createInventory(new InvHolder("worlds"), 6 * 9);

        for (int i : new int[]{0, 1, 2, 6, 7, 8}) {
            inv.setItem(i, pane(Material.GRAY_STAINED_GLASS_PANE));
        }
        inv.setItem(3, pane(Material.WHITE_STAINED_GLASS_PANE));
        ItemStack create = new ItemStack(Material.NETHER_STAR);
        ItemMeta cm = create.getItemMeta();
        cm.setDisplayName("Welt erstellen");
        create.setItemMeta(cm);
        inv.setItem(4, create);
        inv.setItem(5, pane(Material.WHITE_STAINED_GLASS_PANE));

        for (int i : new int[]{9, 18, 27, 36, 17, 26, 35, 44}) {
            inv.setItem(i, pane(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
        }

        ItemStack prev = new ItemStack(Material.GUNPOWDER);
        ItemMeta pm = prev.getItemMeta();
        pm.setDisplayName("Vorherige Seite");
        prev.setItemMeta(pm);
        inv.setItem(45, prev);
        for (int i : new int[]{46, 47, 48, 49, 50, 51, 52}) {
            inv.setItem(i, pane(Material.GRAY_STAINED_GLASS_PANE));
        }
        ItemStack next = new ItemStack(Material.GUNPOWDER);
        ItemMeta nm = next.getItemMeta();
        nm.setDisplayName("Nächste Seite");
        next.setItemMeta(nm);
        inv.setItem(53, next);

        ConfigurationSection worldNames = plugin.getBauserverConfig()
                .getWorldsConfig()
                .getConfigurationSection("Worlds");
        if (worldNames != null) {
            for (String world : worldNames.getKeys(false)) {
                boolean locked = plugin.getBauserverConfig()
                        .getWorldsConfig()
                        .getBoolean("Worlds." + world + ".Properties.Locked");
                boolean lockedSet =
                        plugin.getBauserverConfig().getWorldsConfig().get("Worlds." + world + ".Properties.Locked")
                                != null;
                if (locked && lockedSet && !p.hasPermission("bs.admin") && !p.hasPermission("bs.seelocked"))
                    continue;
                if (!plugin.getBauserverConfig().getWorldsConfig().getBoolean("Visibility") && !p.hasPermission(
                        "bs.seeworlds")) {
                    String owner = plugin.getBauserverConfig()
                            .getWorldsConfig()
                            .getString("Worlds." + world + ".Owner");
                    if (!Vars.isOwner(p, world) && !Vars.isTrusted(p, world) && !"0".equals(owner))
                        continue;
                }
                items.add(buildWorldItem(p, world));
            }
        }

        int page = getCurrentPage(p);
        int start = (page - 1) * 28;
        int end = Math.min(start + 28, items.size());
        for (int i = start; i < end; i++) {
            inv.addItem(items.get(i));
        }

        if (items.size() > 28 * page)
            inv.getItem(53).setType(Material.GLOWSTONE_DUST);
        if (page > 1)
            inv.getItem(45).setType(Material.GLOWSTONE_DUST);

        p.openInventory(inv);
    }

    private ItemStack buildWorldItem(Player p, String world) {
        String symbolStr = plugin.getBauserverConfig().getWorldsConfig().getString("Worlds." + world + ".Symbol");
        Material mat = Material.GRASS_BLOCK;
        if (symbolStr != null) {
            String matName = symbolStr.contains(":") ? symbolStr.split(":")[0] : symbolStr;
            Material m = Material.getMaterial(matName);
            if (m != null)
                mat = m;
        }
        ItemStack item = new ItemStack(mat);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(world);
        List<String> lore = new ArrayList<>();
        String owner = plugin.getBauserverConfig().getWorldsConfig().getString("Worlds." + world + ".Owner");
        lore.add("Ersteller: " + ("0".equals(owner) || owner == null ? "Niemand"
                : Bukkit.getOfflinePlayer(UUID.fromString(owner)).getName()));
        String type = plugin.getBauserverConfig().getWorldsConfig().getString("Worlds." + world + ".Type");
        lore.add("Typ: " + (type == null || "0".equals(type) ? "Unbekannt" : type));
        if (plugin.getBauserverConfig().getWorldsConfig().getBoolean("Worlds." + world + ".Properties.Locked")
                && plugin.getBauserverConfig().getWorldsConfig().get("Worlds." + world + ".Properties.Locked") != null)
            lore.add("Welt gesperrt");
        if (Vars.isOwner(p, world) || p.hasPermission("bs.admin"))
            lore.add("Rechtsklick zum Löschen");
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }
}
