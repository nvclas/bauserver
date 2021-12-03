package de.theniclas.bauplugin.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Vars {
	
	public static String pr = "§8[§9§lTokyo-Build§8] ";
	public static String noperm = pr + "§cDafür hast du keine Rechte";
	
	public static ArrayList<Player> voidWorldName = new ArrayList<>();
	public static ArrayList<Player> flatWorldName = new ArrayList<>();
	public static ArrayList<Player> normalWorldName = new ArrayList<>();
	
	public static HashMap<Player, Integer> placedBlocks = new HashMap<>();
	public static HashMap<String, String> tpa = new HashMap<>();
	
	public static ItemStack getSkull(String url, String displayName) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		if(url.isEmpty())return item;
			SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
			itemMeta.setDisplayName(displayName);
			GameProfile profile = new GameProfile(UUID.randomUUID(), null);
			byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
			profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
			Field profileField = null;
			try
			{
				profileField = itemMeta.getClass().getDeclaredField("profile");
				profileField.setAccessible(true);
				profileField.set(itemMeta, profile);
			}
			catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
			item.setItemMeta(itemMeta);
			return item;
	    }
	
	public static boolean isOwner(Player p, String worldName) {
		if(Configs.worldsConfig.getString("Worlds." + worldName.replaceAll("worlds/", "") + ".Owner").equals(p.getUniqueId().toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isTrusted(OfflinePlayer p, String worldName) {
		if(Configs.worldsConfig.getStringList("Worlds." + worldName.replaceAll("worlds/", "") + ".Trusted") != null && Configs.worldsConfig.getStringList("Worlds." + worldName.replaceAll("worlds/", "") + ".Trusted").contains(p.getUniqueId().toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void loadGlobalSpawnWorld() {
		if(Configs.worldsConfig.getString("Spawn.World") != null) {
			Bukkit.createWorld(new WorldCreator(Configs.worldsConfig.getString("Spawn.World")));
		}
	}
	
	public static int getWorldAmount(Player p) {
		int amount = 0;
		for(String worlds : Configs.worldsConfig.getConfigurationSection("Worlds").getKeys(false)) {
			if(Configs.worldsConfig.getString("Worlds." + worlds + ".Owner").equals(p.getUniqueId().toString())) {
				amount++;
			}
		}
		return amount;
	}
	
	public static int getMaxWorldAmount(Player p) {
		if(!p.hasPermission("bs.worlds.infinite")) {
			int i = 0;
			for(; i <= 30;) {
				if(!p.hasPermission("bs.worlds." + i)) {
					i++;
				} else {
					return i;
				}
			}
			return 0;
		} else {
			return Integer.MAX_VALUE;
		}
	}
	
}
