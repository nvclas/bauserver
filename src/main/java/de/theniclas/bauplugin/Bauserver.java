package de.theniclas.bauplugin;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import de.theniclas.bauplugin.commands.*;
import de.theniclas.bauplugin.events.*;
import de.theniclas.bauplugin.utils.BauserverConfig;
import de.theniclas.bauplugin.utils.InventoryCreator;
import de.theniclas.bauplugin.utils.Vars;
import de.theniclas.bauplugin.utils.WorldMaker;

import java.util.Objects;

@Getter
public class Bauserver extends JavaPlugin {

    private BauserverConfig bauserverConfig;
    private Vars vars;
    private InventoryCreator inventoryCreator;
    private WorldMaker worldMaker;

    @Override
    public void onEnable() {

        // Configs & dependencies
        bauserverConfig = new BauserverConfig(this);
        bauserverConfig.loadConfiguration();
        vars = Vars.inject(this);
        inventoryCreator = new InventoryCreator(this);
        worldMaker = new WorldMaker(this);

        // Commands
        Objects.requireNonNull(getCommand("gm")).setExecutor(new CMDgm());
        Objects.requireNonNull(getCommand("speed")).setExecutor(new CMDspeed());
        Objects.requireNonNull(getCommand("blocks")).setExecutor(new CMDblocks());
        Objects.requireNonNull(getCommand("tools")).setExecutor(new CMDtools());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new CMDfly());
        Objects.requireNonNull(getCommand("worlds")).setExecutor(new CMDworlds(this));
        Objects.requireNonNull(getCommand("trust")).setExecutor(new CMDtrust(this));
        Objects.requireNonNull(getCommand("untrust")).setExecutor(new CMDuntrust(this));
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new CMDtpa(this));
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(new CMDtpaccept());
        Objects.requireNonNull(getCommand("ping")).setExecutor(new CMDping());
        Objects.requireNonNull(getCommand("addspawn")).setExecutor(new CMDaddspawn(this));
        Objects.requireNonNull(getCommand("globalspawn")).setExecutor(new CMDglobalspawn(this));
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new CMDspawn(this));
        Objects.requireNonNull(getCommand("visibility")).setExecutor(new CMDvisibility(this));
        Objects.requireNonNull(getCommand("prepare")).setExecutor(new CMDprepare());
        Objects.requireNonNull(getCommand("worldlock")).setExecutor(new CMDworldlock(this));
        Objects.requireNonNull(getCommand("wkick")).setExecutor(new CMDwkick(this));
        Objects.requireNonNull(getCommand("trusted")).setExecutor(new CMDtrusted(this));
        Objects.requireNonNull(getCommand("setowner")).setExecutor(new CMDsetowner(this));

        // Events
        getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChat(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
        getServer().getPluginManager().registerEvents(new PlayerPickupItem(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntity(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new HangingBreakByEntity(), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocess(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new WeatherChange(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChangedWorld(this), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChange(), this);

        Vars.loadGlobalSpawnWorld();

        getLogger().info("Bauserver-Plugin gestartet");
    }
}
