package de.theniclas.abu.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.theniclas.abu.main.Main;

public class Vars {

	public static File file = new File("plugins/AntiBlockUpdate", "worlds.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public static String pr = "§8[§9§lTokyo-Build§8] ";
	
	public static void loadConfiguration() {
		
		if(!file.exists()) {
			Main.getPlugin().getDataFolder().mkdir();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		saveConfiguration();
	}
	
	public static void saveConfiguration() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	
}
