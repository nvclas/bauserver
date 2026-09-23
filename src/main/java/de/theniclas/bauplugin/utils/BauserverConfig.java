package de.theniclas.bauplugin.utils;

import de.theniclas.bauplugin.Bauserver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
@RequiredArgsConstructor
public class BauserverConfig {

    public static final File WORLDS_FILE = new File("plugins/Bauserver", "worlds.yml");
    private final Bauserver plugin;
    private FileConfiguration worldsConfig = YamlConfiguration.loadConfiguration(WORLDS_FILE);

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
