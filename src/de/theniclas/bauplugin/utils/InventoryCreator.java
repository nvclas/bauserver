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
	private static ArrayList<ItemStack> items = new ArrayList<>();
	
	public static void openWorldInventory(Player p) {
		items.clear();
		Inventory inv = Bukkit.createInventory(null, 6*9, "§3Welten");
		inv.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(2, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		
		inv.setItem(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0));
		ItemStack is1 = new ItemStack(Material.NETHER_STAR);
		ItemMeta im1 = is1.getItemMeta();
		im1.setDisplayName("§cWelt erstellen");
		is1.setItemMeta(im1);
		inv.setItem(4, is1);
		inv.setItem(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0));
		
		inv.setItem(6, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		
		inv.setItem(9, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
		inv.setItem(18, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
		inv.setItem(27, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
		inv.setItem(36, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
		inv.setItem(17, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
		inv.setItem(26, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
		inv.setItem(35, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
		inv.setItem(44, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3));
		
		
		ItemStack is2 = new ItemStack(Material.SULPHUR);
		ItemMeta im2 = is2.getItemMeta();
		im2.setDisplayName("§bVorherige Seite");
		is2.setItemMeta(im2);
		inv.setItem(45, is2);					
		inv.setItem(46, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(47, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(48, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(49, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(50, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(51, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		inv.setItem(52, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		
		ItemStack is3 = new ItemStack(Material.SULPHUR);
		ItemMeta im3 = is3.getItemMeta();
		im3.setDisplayName("§bNächste Seite");
		is3.setItemMeta(im3);
		inv.setItem(53, is3);
		
		for(int i = 0; i < 54; i++) {
			ItemStack item = inv.getItem(i);
			if(item != null && item.getType() == Material.STAINED_GLASS_PANE) {
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(" ");
				item.setItemMeta(meta);
			}
		}
		
		ConfigurationSection worldNames = Configs.worldsConfig.getConfigurationSection("Worlds");
		
		if(Configs.worldsConfig.getConfigurationSection("Worlds") != null && !worldNames.getKeys(false).isEmpty()) {
			for(String worlds : worldNames.getKeys(false)) {
				if(Configs.worldsConfig.getBoolean("Worlds." + worlds + ".Properties.Locked") == false || Configs.worldsConfig.get("Worlds." + worlds + ".Properties.Locked") == null || p.hasPermission("bs.admin") || p.hasPermission("bs.seelocked")) {
					if(Configs.worldsConfig.getBoolean("Visibility") == false && !p.hasPermission("bs.seeworlds")) {
						if(Vars.isOwner(p, worlds) || Vars.isTrusted(p, worlds) || Configs.worldsConfig.getString("Worlds." + worlds + ".Owner").equals("0")) {
							ItemStack worldItem = new ItemStack(Material.AIR);
							if(Configs.worldsConfig.getString("Worlds." + worlds + ".Symbol") != null) {
								String[] args = Configs.worldsConfig.getString("Worlds." + worlds + ".Symbol").split(":");
								String material = args[0];
								worldItem = new ItemStack(Material.getMaterial(material), 1, Byte.valueOf(args[1]));
								
							} else {
								worldItem.setType(Material.GRASS);
							}
							ItemMeta im = worldItem.getItemMeta();
							im.setDisplayName("§a" + worlds);
							List<String> data = new ArrayList<>();
							if(!Configs.worldsConfig.getString("Worlds." + worlds + ".Owner").equals("0")) {
								data.add("§6Ersteller§8: §e" + Bukkit.getOfflinePlayer(UUID.fromString(Configs.worldsConfig.getString("Worlds." + worlds + ".Owner"))).getName());
							} else {
								data.add("§6Ersteller§8: §eNiemand");
							}
							if(!Configs.worldsConfig.getString("Worlds." + worlds + ".Type").equals("0")) {
								data.add("§6Typ§8: §e" + Configs.worldsConfig.getString("Worlds." + worlds + ".Type"));
							} else {
								data.add("§6Typ§8: §eUnbekannt");
							}
							if(Configs.worldsConfig.getBoolean("Worlds." + worlds + ".Properties.Locked") == true && Configs.worldsConfig.get("Worlds." + worlds + ".Properties.Locked") != null) {
								data.add("§4Welt gesperrt");
							}
							if(Vars.isOwner(p, worlds) || p.hasPermission("bs.admin")) {
								data.add("§cRechtsklick zum Löschen");
							}
							im.setLore(data);
							worldItem.setItemMeta(im);
							items.add(worldItem);
						}
					} else {
						ItemStack worldItem = new ItemStack(Material.AIR);
						if(Configs.worldsConfig.getString("Worlds." + worlds + ".Symbol") != null) {
							String[] args = Configs.worldsConfig.getString("Worlds." + worlds + ".Symbol").split(":");
							String material = args[0];
							worldItem = new ItemStack(Material.getMaterial(material), 1, Byte.valueOf(args[1]));
							
						} else {
							worldItem.setType(Material.GRASS);
						}
						ItemMeta im = worldItem.getItemMeta();
						im.setDisplayName("§a" + worlds);
						List<String> data = new ArrayList<>();
						if(!Configs.worldsConfig.getString("Worlds." + worlds + ".Owner").equals("0")) {
							data.add("§6Ersteller§8: §e" + Bukkit.getOfflinePlayer(UUID.fromString(Configs.worldsConfig.getString("Worlds." + worlds + ".Owner"))).getName());
						} else {
							data.add("§6Ersteller§8: §eNiemand");
						}
						if(!Configs.worldsConfig.getString("Worlds." + worlds + ".Type").equals("0")) {
							data.add("§6Typ§8: §e" + Configs.worldsConfig.getString("Worlds." + worlds + ".Type"));
						} else {
							data.add("§6Typ§8: §eUnbekannt");
						}
						if(Configs.worldsConfig.getBoolean("Worlds." + worlds + ".Properties.Locked") == true && Configs.worldsConfig.get("Worlds." + worlds + ".Properties.Locked") != null) {
							data.add("§4Welt gesperrt");
						}
						if(Vars.isOwner(p, worlds) || p.hasPermission("bs.admin")) {
							data.add("§cRechtsklick zum Löschen");
						}
						im.setLore(data);
						worldItem.setItemMeta(im);
						items.add(worldItem);
					}
				}
			}
		}
		int page = currentPage.get(p);
		int index = page * 28 - 28;
		int endIndex = index <= items.size() ? items.size() : (index + 28);
		for (; index < endIndex; index++) {
			inv.addItem(items.get(index));
		}
		if(items.size() > 28 * page) {
			inv.getItem(53).setType(Material.GLOWSTONE_DUST);
		}
		if(page > 1) {
			inv.getItem(45).setType(Material.GLOWSTONE_DUST);
		}
		p.openInventory(inv);
	}
	
}
