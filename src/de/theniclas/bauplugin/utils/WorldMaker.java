package de.theniclas.bauplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class WorldMaker {
	
	public static void createNormalWorld(String name, Player p, ItemStack icon) {	
		if(Configs.worldsConfig.getBoolean("Visibility") == true || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
			Vars.normalWorldName.remove(p);
			p.sendMessage(Vars.pr + "§eErstelle normale Welt...");
			WorldCreator wc = new WorldCreator("worlds/" + name);
			wc.type(WorldType.NORMAL);
			wc.createWorld();
			Bukkit.getWorlds().add(Bukkit.getWorld("worlds/" + name));
			Bukkit.getWorld("worlds/" + name).save();
			Configs.worldsConfig.set("Worlds." + name + ".Owner", p.getUniqueId().toString());
			Configs.worldsConfig.set("Worlds." + name + ".Symbol", icon.getType().toString() + ":" + icon.getData().getData());
			Configs.worldsConfig.set("Worlds." + name + ".Type", "Normal");
			Configs.saveConfiguration();
			System.out.println("Preparing spawn area for " + name + ", done");
			Bukkit.getWorld("worlds/" + name).setSpawnLocation(0, Bukkit.getWorld("worlds/" + name).getHighestBlockYAt(0, 0), 0);
			p.teleport(Bukkit.getWorld("worlds/" + name).getSpawnLocation());
			p.sendMessage(Vars.pr + "§aDeine normale Welt wurde mit dem Namen §e" + name + " §aerstellt");
			p.sendMessage(Vars.pr + "§aNutze §e/prepare§a, um Mobs, Wetter, Blockphysiken und andere Einflüsse auszustellen");
		} else {
			p.sendMessage(Vars.pr + "§cDu kannst keine weiteren Welten erstellen");
			Vars.voidWorldName.remove(p);
			Vars.flatWorldName.remove(p);
			Vars.normalWorldName.remove(p);
		}
	}
	
	public static void createFlatWorld(String name, Player p, ItemStack icon) {
		if(Configs.worldsConfig.getBoolean("Visibility") == true || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
			Vars.flatWorldName.remove(p);
			p.sendMessage(Vars.pr + "§eErstelle Flat-Welt...");
			WorldCreator wc = new WorldCreator("worlds/" + name);
			wc.type(WorldType.FLAT);
			wc.createWorld();
			Bukkit.getWorlds().add(Bukkit.getWorld("worlds/" + name));
			Bukkit.getWorld("worlds/" + name).save();
			Configs.worldsConfig.set("Worlds." + name + ".Owner", p.getUniqueId().toString());
			Configs.worldsConfig.set("Worlds." + name + ".Symbol", icon.getType().toString() + ":" + icon.getData().getData());
			Configs.worldsConfig.set("Worlds." + name + ".Type", "Flat");
			Configs.saveConfiguration();
			System.out.println("Preparing spawn area for " + name + ", done");
			Bukkit.getWorld("worlds/" + name).setSpawnLocation(0, Bukkit.getWorld("worlds/" + name).getHighestBlockYAt(0, 0), 0);
			p.teleport(Bukkit.getWorld("worlds/" + name).getSpawnLocation());
			p.sendMessage(Vars.pr + "§aDeine Flat-Welt wurde mit dem Namen §e" + name + " §aerstellt");
			p.sendMessage(Vars.pr + "§aNutze §e/prepare§a, um Mobs, Wetter, Blockphysiken und andere Einflüsse auszustellen");
		} else {
			p.sendMessage(Vars.pr + "§cDu kannst keine weiteren Welten erstellen");
			Vars.voidWorldName.remove(p);
			Vars.flatWorldName.remove(p);
			Vars.normalWorldName.remove(p);
		}
	}

	public static void createVoidWorld(String name, Player p, ItemStack icon) {
		if(Configs.worldsConfig.getBoolean("Visibility") == true || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
			Vars.voidWorldName.remove(p);
			p.sendMessage(Vars.pr + "§eErstelle Void-Welt...");
			WorldCreator wc = new WorldCreator("worlds/" + name);
			wc.type(WorldType.FLAT);
			wc.generatorSettings("2;0;1;");
			wc.createWorld();
			Bukkit.getWorlds().add(Bukkit.getWorld("worlds/" + name));
			Bukkit.getWorld("worlds/" + name).save();
			Configs.worldsConfig.set("Worlds." + name + ".Owner", p.getUniqueId().toString());
			Configs.worldsConfig.set("Worlds." + name + ".Symbol", icon.getType().toString() + ":" + icon.getData().getData());
			Configs.worldsConfig.set("Worlds." + name + ".Type", "Void");
			Configs.saveConfiguration();
			System.out.println("Preparing spawn area for " + name + ", done");
			Bukkit.getWorld("worlds/" + name).setSpawnLocation(0, 100, 0);
			Bukkit.getWorld("worlds/" + name).getBlockAt(new Location(Bukkit.getWorld("worlds/" + name), 0, 99, 0)).setType(Material.BEDROCK);;
			p.teleport(Bukkit.getWorld("worlds/" + name).getSpawnLocation());
			p.sendMessage(Vars.pr + "§aDeine Void-Welt wurde mit dem Namen §e" + name + " §aerstellt");
			p.sendMessage(Vars.pr + "§aNutze §e/prepare§a, um Mobs, Wetter, Blockphysiken und andere Einflüsse auszustellen");
		} else {
			p.sendMessage(Vars.pr + "§cDu kannst keine weiteren Welten erstellen");
			Vars.voidWorldName.remove(p);
			Vars.flatWorldName.remove(p);
			Vars.normalWorldName.remove(p);
		}
	}	
}
