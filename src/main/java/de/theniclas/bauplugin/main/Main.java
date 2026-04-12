package de.theniclas.bauplugin.main;

import org.bukkit.plugin.java.JavaPlugin;

import de.theniclas.bauplugin.commands.*;
import de.theniclas.bauplugin.events.*;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getCommand("gm").setExecutor(new CMDgm());
        getCommand("speed").setExecutor(new CMDspeed());
        getCommand("blocks").setExecutor(new CMDblocks());
        getCommand("tools").setExecutor(new CMDtools());
        getCommand("fly").setExecutor(new CMDfly());
        getCommand("worlds").setExecutor(new CMDworlds());
        getCommand("trust").setExecutor(new CMDtrust());
        getCommand("untrust").setExecutor(new CMDuntrust());
        getCommand("tpa").setExecutor(new CMDtpa());
        getCommand("tpaccept").setExecutor(new CMDtpaccept());
        getCommand("ping").setExecutor(new CMDping());
        getCommand("addspawn").setExecutor(new CMDaddspawn());
        getCommand("globalspawn").setExecutor(new CMDglobalspawn());
        getCommand("spawn").setExecutor(new CMDspawn());
        getCommand("visibility").setExecutor(new CMDvisibility());
        getCommand("prepare").setExecutor(new CMDprepare());
        getCommand("worldlock").setExecutor(new CMDworldlock());
        getCommand("wkick").setExecutor(new CMDwkick());
        getCommand("trusted").setExecutor(new CMDtrusted());
        getCommand("setowner").setExecutor(new CMDsetowner());

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

        Configs.loadConfiguration();
        Vars.loadGlobalSpawnWorld();

        getLogger().info("Bauserver-Plugin gestartet");
    }

    public static Main getPlugin() {
        return plugin;
    }
}
