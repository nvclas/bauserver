package de.theniclas.bauplugin.events;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.InventoryCreator;
import de.theniclas.bauplugin.utils.Vars;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();			
			if(e.getClickedInventory() != null && e.getCurrentItem().getType() != null && e.getCurrentItem() != null) {
				if(!e.getCurrentItem().hasItemMeta()) {
					if(e.getClickedInventory().getName().equals("§6§lTools")) {
						e.setCancelled(true);
						p.getInventory().addItem(e.getCurrentItem());		
					}
				} else {
					if(e.getClickedInventory().getName().equals("§5§lSpezialblöcke")) {
						e.setCancelled(true);
						p.getInventory().addItem(e.getCurrentItem());	
					} else if(e.getClickedInventory().getName().equals("§6§lTools")) {
						e.setCancelled(true);
						p.getInventory().addItem(e.getCurrentItem());
						
					} else if(e.getClickedInventory().getName().equals("§3Welten")) {
						e.setCancelled(true);
						if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cWelt erstellen")) {
							Inventory inv = Bukkit.createInventory(null, 3*9,"§3Welt erstellen");
							inv.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
							inv.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
							inv.setItem(2, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
							inv.setItem(9, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
							inv.setItem(11, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
							inv.setItem(18, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
							inv.setItem(19, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
							inv.setItem(20, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
							ItemStack is1 = new ItemStack(Material.STAINED_GLASS, 1, (short) 0);
							ItemMeta im1 = is1.getItemMeta();
							im1.setDisplayName("§7Void");
							is1.setItemMeta(im1);
							inv.setItem(10, is1);
							
							
							inv.setItem(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
							inv.setItem(4, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
							inv.setItem(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
							inv.setItem(12, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
							inv.setItem(14, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
							inv.setItem(21, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
							inv.setItem(22, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
							inv.setItem(23, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
							ItemStack is2 = new ItemStack(Material.GRASS);
							ItemMeta im2 = is2.getItemMeta();
							im2.setDisplayName("§bFlat");
							is2.setItemMeta(im2);
							inv.setItem(13, is2);
							
										
							inv.setItem(6, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
							inv.setItem(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
							inv.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
							inv.setItem(15, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
							inv.setItem(17, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
							inv.setItem(24, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
							inv.setItem(25, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
							inv.setItem(26, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
							ItemStack is3 = new ItemStack(Material.SAPLING);
							ItemMeta im3 = is3.getItemMeta();
							im3.setDisplayName("§aNormal");
							is3.setItemMeta(im3);
							inv.setItem(16, is3);
							
							for(int i = 0; i<27; i++) {
								ItemStack item = inv.getItem(i);
								if(item != null && item.getType() == Material.STAINED_GLASS_PANE) {
									ItemMeta meta = item.getItemMeta();
									meta.setDisplayName(" ");
									item.setItemMeta(meta);
								}
							}
							
							p.openInventory(inv);
		
						} else if(Configs.worldsConfig.getConfigurationSection("Worlds").contains(e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "")) && e.getAction() == InventoryAction.PICKUP_ALL) {
								if(Configs.worldsConfig.get("Worlds." + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "") + ".Spawns") == null || Configs.worldsConfig.getConfigurationSection("Worlds." + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "") + ".Spawns").getKeys(false).isEmpty()) {
									if(Bukkit.getWorld("worlds/" + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "")) != null) {
										p.teleport(Bukkit.getWorld("worlds/" + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "")).getSpawnLocation());
									} else {
										p.closeInventory();
										p.sendMessage(Vars.pr + "§6Welt wird geladen...");
										Bukkit.getScheduler().runTask(Main.getPlugin(), new Runnable() {
											public void run() {
												new WorldCreator("worlds/" + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "")).createWorld();
												p.teleport(Bukkit.getWorld("worlds/" + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "")).getSpawnLocation());
											}
										});
									}
								} else {
									Inventory spawnpoints = Bukkit.createInventory(null, 9, "§6" + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", ""));
									for(String spawns : Configs.worldsConfig.getConfigurationSection("Worlds." + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "") + ".Spawns").getKeys(false)) {
										ItemStack is = new ItemStack(Material.EYE_OF_ENDER);
										ItemMeta im = is.getItemMeta();
										im.setDisplayName("§5" + spawns);
										List<String> data = new ArrayList<>();
										String[] arg = Configs.worldsConfig.getString("Worlds." + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "") + ".Spawns." + spawns + ".Location").split(",");
										data.add("§6Location§8: §e" + String.valueOf(Math.round(Double.parseDouble(arg[1]))) + ", " + String.valueOf(Math.round(Double.parseDouble(arg[2]))) + ", " + String.valueOf(Math.round(Double.parseDouble(arg[3]))));
										if(Vars.isOwner(p, e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "")) || p.hasPermission("bs.admin")) {
											data.add("§cRechtsklick zum Löschen");
										}
										im.setLore(data);
										is.setItemMeta(im);
										spawnpoints.addItem(is);
									}
									p.openInventory(spawnpoints);		
								}
						} else if(Configs.worldsConfig.getConfigurationSection("Worlds").getKeys(false).contains(e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "")) && (e.getAction() == InventoryAction.PICKUP_HALF && Vars.isOwner(p, e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "")) || p.hasPermission("bs.admin"))) {
							Inventory delete = Bukkit.createInventory(null, 9*3, "§c" + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "") + " löschen");
							for(int i = 0; i < 27; i++) {
								ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
								ItemMeta im = is.getItemMeta();
								im.setDisplayName(" ");
								is.setItemMeta(im);
								delete.setItem(i, is);
							}
							ItemStack is1 = new ItemStack(Material.BARRIER);
							ItemMeta im1 = is1.getItemMeta();
							im1.setDisplayName("§4Welt löschen");
							List<String> warning = new ArrayList<>();
							warning.add("§cAchtung, dieser Vorgang kann nicht rückgängig gemacht werden");
							im1.setLore(warning);
							is1.setItemMeta(im1);
							delete.setItem(13, is1);
							
							delete.setItem(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(4, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(12, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(14, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(21, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(22, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(23, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							
							for(int i = 0; i < 27; i++) {
								if(!delete.getItem(i).hasItemMeta()) {
									ItemMeta im = delete.getItem(i).getItemMeta();
									im.setDisplayName(" ");
									delete.getItem(i).setItemMeta(im);
								}
							}
							
							p.openInventory(delete);
							
						} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bNächste Seite") && e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
							int page = InventoryCreator.currentPage.get(p);
							InventoryCreator.currentPage.put(p, page + 1);
							InventoryCreator.openWorldInventory(p);
						} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bVorherige Seite") && e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
							int page = InventoryCreator.currentPage.get(p);
							InventoryCreator.currentPage.put(p, page - 1);
							InventoryCreator.openWorldInventory(p);
						}
							
					} else if(e.getClickedInventory().getName().equals("§3Welt erstellen")) {
						e.setCancelled(true);
						if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§7Void")) {
							p.closeInventory();
							p.sendMessage(Vars.pr + "§aBitte nimm ein Item deiner Wahl in die Hand und gib einen Namen für deine Welt in den Chat ein");
							Vars.voidWorldName.add(p);
						} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bFlat")) {
							p.closeInventory();
							p.sendMessage(Vars.pr + "§aBitte nimm ein Item deiner Wahl in die Hand und gib einen Namen für deine Welt in den Chat ein");
							Vars.flatWorldName.add(p);
						} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aNormal")) {
							p.closeInventory();
							p.sendMessage(Vars.pr + "§aBitte nimm ein Item deiner Wahl in die Hand und gib einen Namen für deine Welt in den Chat ein");
							Vars.normalWorldName.add(p);
						}
					} else if(Configs.worldsConfig.getConfigurationSection("Worlds").getKeys(false).contains(e.getInventory().getName().replaceAll("§6", ""))) {
						e.setCancelled(true);
						if(e.getAction() == InventoryAction.PICKUP_HALF && (Vars.isOwner(p, e.getInventory().getName().replaceAll("§6", "")) || p.hasPermission("bs.admin"))) {
							Inventory delete = Bukkit.createInventory(null, 9*3, e.getInventory().getName() + " " + e.getCurrentItem().getItemMeta().getDisplayName());
							for(int i = 0; i < 27; i++) {
								ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
								ItemMeta im = is.getItemMeta();
								im.setDisplayName(" ");
								is.setItemMeta(im);
								delete.setItem(i, is);
							}
							ItemStack is1 = new ItemStack(Material.BARRIER);
							ItemMeta im1 = is1.getItemMeta();
							im1.setDisplayName("§4Spawnpunkt löschen");
							List<String> warning = new ArrayList<>();
							warning.add("§cAchtung, dieser Vorgang kann nicht rückgängig gemacht werden");
							im1.setLore(warning);
							is1.setItemMeta(im1);
							delete.setItem(13, is1);
							
							delete.setItem(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(4, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(12, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(14, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(21, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(22, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							delete.setItem(23, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
							
							for(int i = 0; i < 27; i++) {
								if(!delete.getItem(i).hasItemMeta()) {
									ItemMeta im = delete.getItem(i).getItemMeta();
									im.setDisplayName(" ");
									delete.getItem(i).setItemMeta(im);
								}
							}
							
							p.openInventory(delete);
							
						} else {
							String[] arg = Configs.worldsConfig.getString("Worlds." + e.getInventory().getName().replaceAll("§6", "") + ".Spawns." + e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§5", "") + ".Location").split(",");
							double[] parsed = new double[3];
							for (int a = 0; a < 3; a++) {
							    parsed[a] = Double.parseDouble(arg[a+1]);
							}
							if(Bukkit.getWorld("worlds/" + arg[0]) != null) {
								Location location = new Location(Bukkit.getWorld("worlds/" + arg[0]), parsed[0], parsed[1], parsed[2]);
								p.teleport(location);
							} else {
								p.closeInventory();
								p.sendMessage(Vars.pr + "§6Welt wird geladen...");
								Bukkit.getScheduler().runTask(Main.getPlugin(), new Runnable() {
									public void run() {
										new WorldCreator("worlds/" + arg[0]).createWorld();
										Location location = new Location(Bukkit.getWorld("worlds/" + arg[0]), parsed[0], parsed[1], parsed[2]);
										p.teleport(location);
									}
								});
							}
						}
					} else if(e.getInventory().getName().split(" ").length > 1 && Configs.worldsConfig.getConfigurationSection("Worlds").getKeys(false).contains(e.getInventory().getName().replaceAll("§c", "").substring(0, e.getInventory().getName().replaceAll("§c", "").indexOf(' ')))) {
						e.setCancelled(true);
						String worldName = e.getInventory().getName().replaceAll("§c", "").substring(0, e.getInventory().getName().replaceAll("§c", "").indexOf(' '));
						if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§4Welt löschen")) {
							if(Configs.worldsConfig.getString("Spawn.World") != null) {
								if(!Configs.worldsConfig.getString("Spawn.World").equals("world/" + worldName)) {
									p.closeInventory();
									for(Player all : Bukkit.getOnlinePlayers()) {
										if(all.getWorld().getName().equals("worlds/" + worldName)) {
											World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
											Double x = Configs.worldsConfig.getDouble("Spawn.X");
											Double y = Configs.worldsConfig.getDouble("Spawn.Y");
											Double z = Configs.worldsConfig.getDouble("Spawn.Z");
											Location location = new Location(w, x, y, z);
											all.teleport(location);
											all.sendMessage(Vars.pr + "§6Die Welt in der du dich befandest wurde gelöscht");
										}
									}
									p.sendMessage(Vars.pr + "§6Welt wird gelöscht...");
									Configs.worldsConfig.set("Worlds." + worldName, null);
									Configs.saveConfiguration();
									
									if(Bukkit.getWorld("worlds/" + worldName) != null) {
										Bukkit.unloadWorld("worlds/" + worldName, false);
									}
									try {
										FileUtils.deleteDirectory(new File("worlds/" + worldName));
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									p.sendMessage(Vars.pr + "§aWelt erfolgreich gelöscht");
								} else {
									p.sendMessage(Vars.pr + "§cDiese Welt kann nicht gelöscht werden, da dort der globale Spawnpunkt gesetzt ist");
								}
							} else {
								p.sendMessage(Vars.pr + "§cEs können keine Welten gelöscht werden, da kein globaler Spawnpunkt erstellt wurde");
								
							}
						}
					} else if(e.getInventory().getName().split(" ").length > 1 && Configs.worldsConfig.getConfigurationSection("Worlds").getKeys(false).contains(e.getInventory().getName().replaceAll("§6", "").substring(0, e.getInventory().getName().replaceAll("§6", "").indexOf(' ')))) {
						String worldName = e.getInventory().getName().replaceAll("§6", "").substring(0, e.getInventory().getName().replaceAll("§6", "").indexOf(' '));
						if(Configs.worldsConfig.getConfigurationSection("Worlds." + worldName + ".Spawns").getKeys(false).contains(e.getInventory().getName().replaceAll("§6" + worldName + " ", "").replaceAll("§5", ""))) {
							String spawnName = e.getInventory().getName().replaceAll("§6" + worldName + " ", "").replaceAll("§5", "");
							e.setCancelled(true);
							if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§4Spawnpunkt löschen")) {
								p.closeInventory();
								Configs.worldsConfig.set("Worlds." + worldName + ".Spawns." + spawnName, null);
								p.sendMessage(Vars.pr + "§aSpawnpunkt erfolgreich gelöscht");
							}
						}
					}
				}
			}
		}
	}
}