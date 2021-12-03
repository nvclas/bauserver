package de.theniclas.bauplugin.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.theniclas.bauplugin.main.Main;

public class Configs {
	
	public static File worldsFile = new File("plugins/Bauserver", "worlds.yml");
	public static FileConfiguration worldsConfig = YamlConfiguration.loadConfiguration(worldsFile);
	
	public static void loadConfiguration() {
		File worldsFile = new File("plugins/Bauserver", "worlds.yml");
		
		if(!worldsFile.exists()) {
			Main.getPlugin().getDataFolder().mkdir();
			try {
				worldsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Configs.worldsConfig.set("Visibility", false);
			saveConfiguration();
			System.out.println("Weltenconfig erstellt");
		}
	}
	
	public static void saveConfiguration() {
		try {
			worldsConfig.save(worldsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		worldsConfig = YamlConfiguration.loadConfiguration(worldsFile);
	}
}