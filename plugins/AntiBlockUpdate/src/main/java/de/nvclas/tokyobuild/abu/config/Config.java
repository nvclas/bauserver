package de.nvclas.tokyobuild.abu.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {

	private final JavaPlugin plugin;
	@Getter
    private final File file;
	@Getter
    private FileConfiguration configFile;

	public Config(String fileName, JavaPlugin plugin) {
		this.file = new File(plugin.getDataFolder(), fileName);
		this.plugin = plugin;
		initializeConfig();
	}

	protected void initializeConfig() {
		try {
			createParentDirectory();
			if (file.createNewFile()) {
				plugin.getLogger().log(Level.CONFIG, () -> "Datei " + file.getName() + " wurde erstellt");
			}
		} catch (IOException e) {
			plugin.getLogger()
					.log(Level.SEVERE, () -> "Fehler beim Erstellen der Datei: " + file.getName());
		}
		configFile = YamlConfiguration.loadConfiguration(file);
	}

	private void createParentDirectory() {
		File dataFolder = plugin.getDataFolder();
		if (!dataFolder.exists() && !dataFolder.mkdir()) {
			plugin.getLogger().log(Level.CONFIG, () -> "Failed to create plugin data folder.");
		}
	}
	
	public void saveConfiguration() {
		try {
			configFile.save(file);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, () -> "Fehler beim Speichern der Datei: " + file.getName());
		}
		// Reload the configuration after saving
		configFile = YamlConfiguration.loadConfiguration(file);
	}
}