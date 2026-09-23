package de.theniclas.bauplugin;

import de.theniclas.bauplugin.commands.CMDaddspawn;
import de.theniclas.bauplugin.commands.CMDblocks;
import de.theniclas.bauplugin.commands.CMDfly;
import de.theniclas.bauplugin.commands.CMDglobalspawn;
import de.theniclas.bauplugin.commands.CMDgm;
import de.theniclas.bauplugin.commands.CMDping;
import de.theniclas.bauplugin.commands.CMDprepare;
import de.theniclas.bauplugin.commands.CMDsetowner;
import de.theniclas.bauplugin.commands.CMDspawn;
import de.theniclas.bauplugin.commands.CMDspeed;
import de.theniclas.bauplugin.commands.CMDtools;
import de.theniclas.bauplugin.commands.CMDtpa;
import de.theniclas.bauplugin.commands.CMDtpaccept;
import de.theniclas.bauplugin.commands.CMDtrust;
import de.theniclas.bauplugin.commands.CMDtrusted;
import de.theniclas.bauplugin.commands.CMDuntrust;
import de.theniclas.bauplugin.commands.CMDvisibility;
import de.theniclas.bauplugin.commands.CMDwkick;
import de.theniclas.bauplugin.commands.CMDworldlock;
import de.theniclas.bauplugin.commands.CMDworlds;
import de.theniclas.bauplugin.events.AsyncPlayerChat;
import de.theniclas.bauplugin.events.BlockBreak;
import de.theniclas.bauplugin.events.BlockPlace;
import de.theniclas.bauplugin.events.EntityDamageByEntity;
import de.theniclas.bauplugin.events.FoodLevelChange;
import de.theniclas.bauplugin.events.HangingBreakByEntity;
import de.theniclas.bauplugin.events.InventoryClick;
import de.theniclas.bauplugin.events.PlayerChangedWorld;
import de.theniclas.bauplugin.events.PlayerCommandPreprocess;
import de.theniclas.bauplugin.events.PlayerDeath;
import de.theniclas.bauplugin.events.PlayerDropItem;
import de.theniclas.bauplugin.events.PlayerInteract;
import de.theniclas.bauplugin.events.PlayerInteractAtEntity;
import de.theniclas.bauplugin.events.PlayerJoin;
import de.theniclas.bauplugin.events.PlayerPickupItem;
import de.theniclas.bauplugin.events.PlayerQuit;
import de.theniclas.bauplugin.events.PlayerRespawn;
import de.theniclas.bauplugin.events.WeatherChange;
import de.theniclas.bauplugin.utils.BauserverConfig;
import de.theniclas.bauplugin.utils.InventoryCreator;
import de.theniclas.bauplugin.utils.Vars;
import de.theniclas.bauplugin.utils.WorldMaker;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

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
