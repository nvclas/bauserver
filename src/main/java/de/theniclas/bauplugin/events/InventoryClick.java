package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.main.BauserverPlugin;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.InventoryCreator;
import de.theniclas.bauplugin.utils.Vars;
import org.apache.commons.io.FileUtils;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        if (e.getWhoClicked() instanceof Player p) {
            if (e.getClickedInventory() != null) {
                e.getCurrentItem().getType();
                if (e.getCurrentItem() != null) {
                    if (!e.getCurrentItem().hasItemMeta()) {
                        if (e.getView().getTitle().equals("§6§lTools")) {
                            e.setCancelled(true);
                            p.getInventory().addItem(e.getCurrentItem());
                        }
                    } else {
                        if (e.getView().getTitle().equals("§5§lSpezialblöcke")) {
                            e.setCancelled(true);
                            p.getInventory().addItem(e.getCurrentItem());
                        } else if (e.getView().getTitle().equals("§6§lTools")) {
                            e.setCancelled(true);
                            p.getInventory().addItem(e.getCurrentItem());

                        } else if (e.getView().getTitle().equals("§3Welten")) {
                            e.setCancelled(true);
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cWelt erstellen")) {
                                Inventory inv = Bukkit.createInventory(null, 3 * 9, "§3Welt erstellen");
                                inv.setItem(0, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                inv.setItem(1, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                inv.setItem(2, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                inv.setItem(9, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                inv.setItem(11, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                inv.setItem(18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                inv.setItem(19, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                inv.setItem(20, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                ItemStack is1 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
                                ItemMeta im1 = is1.getItemMeta();
                                im1.setDisplayName("§7Void");
                                is1.setItemMeta(im1);
                                inv.setItem(10, is1);


                                inv.setItem(3, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                                inv.setItem(4, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                                inv.setItem(5, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                                inv.setItem(12, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                                inv.setItem(14, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                                inv.setItem(21, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                                inv.setItem(22, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                                inv.setItem(23, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                                ItemStack is2 = new ItemStack(Material.GRASS_BLOCK);
                                ItemMeta im2 = is2.getItemMeta();
                                im2.setDisplayName("§bFlat");
                                is2.setItemMeta(im2);
                                inv.setItem(13, is2);


                                inv.setItem(6, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                inv.setItem(7, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                inv.setItem(8, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                inv.setItem(15, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                inv.setItem(17, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                inv.setItem(24, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                inv.setItem(25, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                inv.setItem(26, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                ItemStack is3 = new ItemStack(Material.OAK_SAPLING);
                                ItemMeta im3 = is3.getItemMeta();
                                im3.setDisplayName("§aNormal");
                                is3.setItemMeta(im3);
                                inv.setItem(16, is3);

                                for (int i = 0; i < 27; i++) {
                                    ItemStack item = inv.getItem(i);
                                    if (item != null && item.getType().name().endsWith("_STAINED_GLASS_PANE") && !item.getItemMeta().getDisplayName().equals("§7Void")) {
                                        ItemMeta meta = item.getItemMeta();
                                        meta.setDisplayName(" ");
                                        item.setItemMeta(meta);
                                    }
                                }

                                p.openInventory(inv);

                            } else if (Configs.worldsConfig.getConfigurationSection("Worlds")
                                    .contains(e.getCurrentItem()
                                            .getItemMeta()
                                            .getDisplayName()
                                            .replace("§a", "")) && e.getAction() == InventoryAction.PICKUP_ALL) {
                                if (Configs.worldsConfig.get("Worlds." + e.getCurrentItem()
                                        .getItemMeta()
                                        .getDisplayName()
                                        .replace("§a",
                                                "") + ".Spawns") == null || Configs.worldsConfig.getConfigurationSection(
                                        "Worlds." + e.getCurrentItem()
                                                .getItemMeta()
                                                .getDisplayName()
                                                .replace("§a", "") + ".Spawns").getKeys(false).isEmpty()) {
                                    if (Bukkit.getWorld("worlds/" + e.getCurrentItem()
                                            .getItemMeta()
                                            .getDisplayName()
                                            .replace("§a", "")) != null) {
                                        p.teleport(Bukkit.getWorld("worlds/" + e.getCurrentItem()
                                                .getItemMeta()
                                                .getDisplayName()
                                                .replace("§a", "")).getSpawnLocation());
                                    } else {
                                        p.closeInventory();
                                        p.sendMessage(Vars.pr + "§6Welt wird geladen...");
                                        Bukkit.getScheduler().runTask(BauserverPlugin.getPlugin(), () -> {
                                            new WorldCreator("worlds/" + e.getCurrentItem()
                                                    .getItemMeta()
                                                    .getDisplayName()
                                                    .replace("§a", "")).createWorld();
                                            p.teleport(Bukkit.getWorld("worlds/" + e.getCurrentItem()
                                                    .getItemMeta()
                                                    .getDisplayName()
                                                    .replace("§a", "")).getSpawnLocation());
                                        });
                                    }
                                } else {
                                    Inventory spawnpoints = Bukkit.createInventory(null,
                                            9,
                                            "§6" + e.getCurrentItem()
                                                    .getItemMeta()
                                                    .getDisplayName()
                                                    .replace("§a", ""));
                                    for (String spawns : Configs.worldsConfig.getConfigurationSection("Worlds." + e.getCurrentItem()
                                            .getItemMeta()
                                            .getDisplayName()
                                            .replace("§a", "") + ".Spawns").getKeys(false)) {
                                        ItemStack is = new ItemStack(Material.ENDER_EYE);
                                        ItemMeta im = is.getItemMeta();
                                        im.setDisplayName("§5" + spawns);
                                        List<String> data = new ArrayList<>();
                                        String[] arg = Configs.worldsConfig.getString("Worlds." + e.getCurrentItem()
                                                .getItemMeta()
                                                .getDisplayName()
                                                .replace("§a", "") + ".Spawns." + spawns + ".Location").split(",");
                                        data.add("§6Location§8: §e" + Math.round(Double.parseDouble(arg[1])) + ", " + Math.round(
                                                Double.parseDouble(arg[2])) + ", " + Math.round(Double.parseDouble(arg[3])));
                                        if (Vars.isOwner(p,
                                                e.getCurrentItem()
                                                        .getItemMeta()
                                                        .getDisplayName()
                                                        .replace("§a", "")) || p.hasPermission("bs.admin")) {
                                            data.add("§cRechtsklick zum L§schen");
                                        }
                                        im.setLore(data);
                                        is.setItemMeta(im);
                                        spawnpoints.addItem(is);
                                    }
                                    p.openInventory(spawnpoints);
                                }
                            } else if (Configs.worldsConfig.getConfigurationSection("Worlds")
                                    .getKeys(false)
                                    .contains(e.getCurrentItem()
                                            .getItemMeta()
                                            .getDisplayName()
                                            .replace("§a",
                                                    "")) && (e.getAction() == InventoryAction.PICKUP_HALF && Vars.isOwner(
                                    p,
                                    e.getCurrentItem()
                                            .getItemMeta()
                                            .getDisplayName()
                                            .replace("§a", "")) || p.hasPermission("bs.admin"))) {
                                Inventory delete = Bukkit.createInventory(null,
                                        9 * 3,
                                        "§c" + e.getCurrentItem()
                                                .getItemMeta()
                                                .getDisplayName()
                                                .replace("§a", "") + " l§schen");
                                for (int i = 0; i < 27; i++) {
                                    ItemStack is = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                                    ItemMeta im = is.getItemMeta();
                                    im.setDisplayName(" ");
                                    is.setItemMeta(im);
                                    delete.setItem(i, is);
                                }
                                ItemStack is1 = new ItemStack(Material.BARRIER);
                                ItemMeta im1 = is1.getItemMeta();
                                im1.setDisplayName("§4Welt l§schen");
                                List<String> warning = new ArrayList<>();
                                warning.add("§cAchtung, dieser Vorgang kann nicht r§ckg§ngig gemacht werden");
                                im1.setLore(warning);
                                is1.setItemMeta(im1);
                                delete.setItem(13, is1);

                                delete.setItem(3, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(4, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(5, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(12, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(14, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(21, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(22, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(23, new ItemStack(Material.RED_STAINED_GLASS_PANE));

                                for (int i = 0; i < 27; i++) {
                                    if (!delete.getItem(i).hasItemMeta()) {
                                        ItemMeta im = delete.getItem(i).getItemMeta();
                                        im.setDisplayName(" ");
                                        delete.getItem(i).setItemMeta(im);
                                    }
                                }

                                p.openInventory(delete);

                            } else if (e.getCurrentItem()
                                    .getItemMeta()
                                    .getDisplayName()
                                    .equals("§bN§chste Seite") && e.getCurrentItem()
                                    .getType() == Material.GLOWSTONE_DUST) {
                                InventoryCreator.currentPage.compute(p, (k, page) -> page + 1);
                                InventoryCreator.openWorldInventory(p);
                            } else if (e.getCurrentItem()
                                    .getItemMeta()
                                    .getDisplayName()
                                    .equals("§bVorherige Seite") && e.getCurrentItem()
                                    .getType() == Material.GLOWSTONE_DUST) {
                                InventoryCreator.currentPage.compute(p, (k, page) -> page - 1);
                                InventoryCreator.openWorldInventory(p);
                            }

                        } else if (e.getView().getTitle().equals("§3Welt erstellen")) {
                            e.setCancelled(true);
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§7Void")) {
                                p.closeInventory();
                                p.sendMessage(Vars.pr + "§aBitte nimm ein Item deiner Wahl in die Hand und gib einen Namen für deine Welt in den Chat ein");
                                Vars.voidWorldName.add(p);
                            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§bFlat")) {
                                p.closeInventory();
                                p.sendMessage(Vars.pr + "§aBitte nimm ein Item deiner Wahl in die Hand und gib einen Namen für deine Welt in den Chat ein");
                                Vars.flatWorldName.add(p);
                            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aNormal")) {
                                p.closeInventory();
                                p.sendMessage(Vars.pr + "§aBitte nimm ein Item deiner Wahl in die Hand und gib einen Namen für deine Welt in den Chat ein");
                                Vars.normalWorldName.add(p);
                            }
                        } else if (Configs.worldsConfig.getConfigurationSection("Worlds")
                                .getKeys(false)
                                .contains(e.getView().getTitle().replace("§6", ""))) {
                            e.setCancelled(true);
                            if (e.getAction() == InventoryAction.PICKUP_HALF && (Vars.isOwner(p,
                                    e.getView().getTitle().replace("§6", "")) || p.hasPermission("bs.admin"))) {
                                Inventory delete = Bukkit.createInventory(null,
                                        9 * 3,
                                        e.getView().getTitle() + " " + e.getCurrentItem()
                                                .getItemMeta()
                                                .getDisplayName());
                                for (int i = 0; i < 27; i++) {
                                    ItemStack is = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                                    ItemMeta im = is.getItemMeta();
                                    im.setDisplayName(" ");
                                    is.setItemMeta(im);
                                    delete.setItem(i, is);
                                }
                                ItemStack is1 = new ItemStack(Material.BARRIER);
                                ItemMeta im1 = is1.getItemMeta();
                                im1.setDisplayName("§4Spawnpunkt l§schen");
                                List<String> warning = new ArrayList<>();
                                warning.add("§cAchtung, dieser Vorgang kann nicht r§ckg§ngig gemacht werden");
                                im1.setLore(warning);
                                is1.setItemMeta(im1);
                                delete.setItem(13, is1);

                                delete.setItem(3, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(4, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(5, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(12, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(14, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(21, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(22, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                                delete.setItem(23, new ItemStack(Material.RED_STAINED_GLASS_PANE));

                                for (int i = 0; i < 27; i++) {
                                    if (!delete.getItem(i).hasItemMeta()) {
                                        ItemMeta im = delete.getItem(i).getItemMeta();
                                        im.setDisplayName(" ");
                                        delete.getItem(i).setItemMeta(im);
                                    }
                                }

                                p.openInventory(delete);

                            } else {
                                String[] arg = Configs.worldsConfig.getString("Worlds." + e.getView()
                                        .getTitle()
                                        .replace("§6", "") + ".Spawns." + e.getCurrentItem()
                                        .getItemMeta()
                                        .getDisplayName()
                                        .replace("§5", "") + ".Location").split(",");
                                double[] parsed = new double[3];
                                for (int a = 0; a < 3; a++) {
                                    parsed[a] = Double.parseDouble(arg[a + 1]);
                                }
                                if (Bukkit.getWorld("worlds/" + arg[0]) != null) {
                                    Location location = new Location(Bukkit.getWorld("worlds/" + arg[0]),
                                            parsed[0],
                                            parsed[1],
                                            parsed[2]);
                                    p.teleport(location);
                                } else {
                                    p.closeInventory();
                                    p.sendMessage(Vars.pr + "§6Welt wird geladen...");
                                    Bukkit.getScheduler().runTask(BauserverPlugin.getPlugin(), () -> {
                                        new WorldCreator("worlds/" + arg[0]).createWorld();
                                        Location location = new Location(Bukkit.getWorld("worlds/" + arg[0]),
                                                parsed[0],
                                                parsed[1],
                                                parsed[2]);
                                        p.teleport(location);
                                    });
                                }
                            }
                        } else if (e.getView().getTitle()
                                .split(" ").length > 1 && Configs.worldsConfig.getConfigurationSection("Worlds")
                                .getKeys(false)
                                .contains(e.getView()
                                        .getTitle()
                                        .replace("§c", "")
                                        .substring(0, e.getView().getTitle().replace("§c", "").indexOf(' ')))) {
                            e.setCancelled(true);
                            String worldName = e.getView()
                                    .getTitle()
                                    .replace("§c", "")
                                    .substring(0, e.getView().getTitle().replace("§c", "").indexOf(' '));
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4Welt l§schen")) {
                                if (Configs.worldsConfig.getString("Spawn.World") != null) {
                                    if (!Configs.worldsConfig.getString("Spawn.World").equals("world/" + worldName)) {
                                        p.closeInventory();
                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            if (all.getWorld().getName().equals("worlds/" + worldName)) {
                                                World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
                                                double x = Configs.worldsConfig.getDouble("Spawn.X");
                                                double y = Configs.worldsConfig.getDouble("Spawn.Y");
                                                double z = Configs.worldsConfig.getDouble("Spawn.Z");
                                                Location location = new Location(w, x, y, z);
                                                all.teleport(location);
                                                all.sendMessage(Vars.pr + "§6Die Welt in der du dich befandest wurde gel§scht");
                                            }
                                        }
                                        p.sendMessage(Vars.pr + "§6Welt wird gel§scht...");
                                        Configs.worldsConfig.set("Worlds." + worldName, null);
                                        Configs.saveConfiguration();

                                        if (Bukkit.getWorld("worlds/" + worldName) != null) {
                                            Bukkit.unloadWorld("worlds/" + worldName, false);
                                        }
                                        try {
                                            FileUtils.deleteDirectory(new File("worlds/" + worldName));
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                        p.sendMessage(Vars.pr + "§aWelt erfolgreich gel§scht");
                                    } else {
                                        p.sendMessage(Vars.pr + "§cDiese Welt kann nicht gel§scht werden, da dort der globale Spawnpunkt gesetzt ist");
                                    }
                                } else {
                                    p.sendMessage(Vars.pr + "§cEs k§nnen keine Welten gel§scht werden, da kein globaler Spawnpunkt erstellt wurde");

                                }
                            }
                        } else if (e.getView()
                                .getTitle()
                                .split(" ").length > 1 && Configs.worldsConfig.getConfigurationSection("Worlds")
                                .getKeys(false)
                                .contains(e.getView()
                                        .getTitle()
                                        .replace("§6", "")
                                        .substring(0, e.getView().getTitle().replace("§6", "").indexOf(' ')))) {
                            String worldName = e.getView()
                                    .getTitle()
                                    .replace("§6", "")
                                    .substring(0, e.getView().getTitle().replace("§6", "").indexOf(' '));
                            if (Configs.worldsConfig.getConfigurationSection("Worlds." + worldName + ".Spawns")
                                    .getKeys(false)
                                    .contains(e.getView()
                                            .getTitle()
                                            .replace("§6" + worldName + " ", "")
                                            .replace("§5", ""))) {
                                String spawnName = e.getView()
                                        .getTitle()
                                        .replace("§6" + worldName + " ", "")
                                        .replace("§5", "");
                                e.setCancelled(true);
                                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4Spawnpunkt l§schen")) {
                                    p.closeInventory();
                                    Configs.worldsConfig.set("Worlds." + worldName + ".Spawns." + spawnName, null);
                                    p.sendMessage(Vars.pr + "§aSpawnpunkt erfolgreich gel§scht");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}