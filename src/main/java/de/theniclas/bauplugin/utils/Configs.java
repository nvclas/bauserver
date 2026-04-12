package de.theniclas.bauplugin.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.theniclas.bauplugin.Bauserver;

public class Configs {

    public static File worldsFile = new File("plugins/Bauserver", "worlds.yml");
    public static FileConfiguration worldsConfig = YamlConfiguration.loadConfiguration(worldsFile);

    public static void loadConfiguration() {
        worldsFile = new File("plugins/Bauserver", "worlds.yml");

        if (!worldsFile.exists()) {
            Bauserver.getPlugin().getDataFolder().mkdirs();
            try {
                worldsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            worldsConfig.set("Visibility", false);
            saveConfiguration();
            Bauserver.getPlugin().getLogger().info("Weltenconfig erstellt");
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
