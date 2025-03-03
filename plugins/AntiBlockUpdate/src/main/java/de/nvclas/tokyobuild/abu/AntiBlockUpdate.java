package de.nvclas.tokyobuild.abu;

import de.nvclas.tokyobuild.abu.commands.CMDabu;
import de.nvclas.tokyobuild.abu.config.Config;
import de.nvclas.tokyobuild.abu.events.BlockFromToListener;
import de.nvclas.tokyobuild.abu.events.BlockPhysicsListener;
import de.nvclas.tokyobuild.abu.events.BlockPlaceListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Level;

@Getter
public class AntiBlockUpdate extends JavaPlugin {

    public static final String PREFIX = "§8[§9§lTokyo-Build§8] ";

    private Config worldConfig;
    
    @Override
    public void onEnable() {

        worldConfig = new Config("antiblockupdate.yml", this);

        Objects.requireNonNull(this.getCommand("abu")).setExecutor(new CMDabu(this));

        Bukkit.getPluginManager().registerEvents(new BlockPhysicsListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockFromToListener(this), this);

        getLogger().log(Level.INFO, () -> "AntiBlockUpdate geladen");

    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        return worldConfig.getConfigFile();
    }
}
