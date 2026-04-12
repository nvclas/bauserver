package de.theniclas.bauplugin;

import org.bukkit.plugin.java.JavaPlugin;

import de.theniclas.bauplugin.commands.*;
import de.theniclas.bauplugin.events.*;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

import java.util.Objects;

public class Bauserver extends JavaPlugin {

    private static Bauserver plugin;

    @Override
    public void onEnable() {
        // Commands
        Objects.requireNonNull(getCommand("gm")).setExecutor(new CMDgm());
        Objects.requireNonNull(getCommand("speed")).setExecutor(new CMDspeed());
        Objects.requireNonNull(getCommand("blocks")).setExecutor(new CMDblocks());
        Objects.requireNonNull(getCommand("tools")).setExecutor(new CMDtools());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new CMDfly());
        Objects.requireNonNull(getCommand("worlds")).setExecutor(new CMDworlds());
        Objects.requireNonNull(getCommand("trust")).setExecutor(new CMDtrust());
        Objects.requireNonNull(getCommand("untrust")).setExecutor(new CMDuntrust());
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new CMDtpa());
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(new CMDtpaccept());
        Objects.requireNonNull(getCommand("ping")).setExecutor(new CMDping());
        Objects.requireNonNull(getCommand("addspawn")).setExecutor(new CMDaddspawn());
        Objects.requireNonNull(getCommand("globalspawn")).setExecutor(new CMDglobalspawn());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new CMDspawn());
        Objects.requireNonNull(getCommand("visibility")).setExecutor(new CMDvisibility());
        Objects.requireNonNull(getCommand("prepare")).setExecutor(new CMDprepare());
        Objects.requireNonNull(getCommand("worldlock")).setExecutor(new CMDworldlock());
        Objects.requireNonNull(getCommand("wkick")).setExecutor(new CMDwkick());
        Objects.requireNonNull(getCommand("trusted")).setExecutor(new CMDtrusted());
        Objects.requireNonNull(getCommand("setowner")).setExecutor(new CMDsetowner());

        // Events
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
        getServer().getPluginManager().registerEvents(new PlayerPickupItem(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntity(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new HangingBreakByEntity(), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new WeatherChange(), this);
        getServer().getPluginManager().registerEvents(new PlayerChangedWorld(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChange(), this);

        // Configs
        Configs.loadConfiguration();
        Vars.loadGlobalSpawnWorld();

        getLogger().info("Bauserver-Plugin gestartet");
    }

    public static Bauserver getPlugin() {
        return plugin;
    }
}
