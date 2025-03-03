package de.nvclas.tokyobuild.levels.utils;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.nvclas.tokyobuild.levels.main.Main;

public class Data {

	private static File file = new File("plugins/Levels/data.yml");
	@Getter
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

    public static void saveConfiguration() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
 	
}
