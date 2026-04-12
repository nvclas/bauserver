package de.theniclas.bauplugin.utils;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.theniclas.bauplugin.Bauserver;

@Getter
@RequiredArgsConstructor
public class BauserverConfig {

    private final Bauserver plugin;
    private FileConfiguration worldsConfig = YamlConfiguration.loadConfiguration(WORLDS_FILE);

    public static final File WORLDS_FILE = new File("plugins/Bauserver", "worlds.yml");

    public void loadConfiguration() {
        if (!WORLDS_FILE.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                WORLDS_FILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            worldsConfig.set("Visibility", false);
            saveConfiguration();
            plugin.getLogger().info("Weltenconfig erstellt");
        }
    }

    public void saveConfiguration() {
        try {
            worldsConfig.save(WORLDS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        worldsConfig = YamlConfiguration.loadConfiguration(WORLDS_FILE);
    }
}
