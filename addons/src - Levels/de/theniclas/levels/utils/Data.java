package de.theniclas.levels.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.theniclas.levels.main.Main;

public class Data {

	private static File file = new File("plugins/Levels/data.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public static void loadConfiguration() {
		if(!file.exists()) {
			Main.getPlugin().getDataFolder().mkdir();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Config erstellt");
		}
	}
	
	public static FileConfiguration getConfig() {
		return config;
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
