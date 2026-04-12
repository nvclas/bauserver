package de.theniclas.bauplugin.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.theniclas.bauplugin.main.Main;

public class Configs {

    private static final File WORLDS_FILE = new File("plugins/Bauserver", "worlds.yml");

    public static FileConfiguration worldsConfig = YamlConfiguration.loadConfiguration(WORLDS_FILE);

    /** Creates the worlds.yml if it does not yet exist, then loads it. */
    public static void loadConfiguration() {
        if (!WORLDS_FILE.exists()) {
            Main.getPlugin().getDataFolder().mkdirs();
            try {
                WORLDS_FILE.createNewFile();
            } catch (IOException e) {
                Main.getPlugin().getLogger().severe("Could not create worlds.yml: " + e.getMessage());
            }
            worldsConfig.set("Visibility", false);
            saveConfiguration();
            Main.getPlugin().getLogger().info("worlds.yml created.");
        }
    }

    /** Persists the current config to disk and reloads it. */
    public static void saveConfiguration() {
        try {
            worldsConfig.save(WORLDS_FILE);
        } catch (IOException e) {
            Main.getPlugin().getLogger().severe("Could not save worlds.yml: " + e.getMessage());
        }
        worldsConfig = YamlConfiguration.loadConfiguration(WORLDS_FILE);
    }
}
