package de.theniclas.tokyobuild.bauplugin.utils;

import de.theniclas.tokyobuild.bauplugin.BauserverPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configs {

    public static final File worldsFile = new File("plugins/Bauserver", "worlds.yml");
    public static FileConfiguration worldsConfig = YamlConfiguration.loadConfiguration(worldsFile);

    public static void loadConfiguration() {
        File worldsFile = new File("plugins/Bauserver", "worlds.yml");

        if (!worldsFile.exists()) {
            BauserverPlugin.getPlugin().getDataFolder().mkdir();
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