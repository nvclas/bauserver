package de.theniclas.tokyobuild.bauplugin;

import de.theniclas.tokyobuild.bauplugin.commands.CMDaddspawn;
import de.theniclas.tokyobuild.bauplugin.commands.CMDblocks;
import de.theniclas.tokyobuild.bauplugin.commands.CMDfly;
import de.theniclas.tokyobuild.bauplugin.commands.CMDglobalspawn;
import de.theniclas.tokyobuild.bauplugin.commands.CMDgm;
import de.theniclas.tokyobuild.bauplugin.commands.CMDping;
import de.theniclas.tokyobuild.bauplugin.commands.CMDprepare;
import de.theniclas.tokyobuild.bauplugin.commands.CMDsetowner;
import de.theniclas.tokyobuild.bauplugin.commands.CMDspawn;
import de.theniclas.tokyobuild.bauplugin.commands.CMDspeed;
import de.theniclas.tokyobuild.bauplugin.commands.CMDtools;
import de.theniclas.tokyobuild.bauplugin.commands.CMDtpa;
import de.theniclas.tokyobuild.bauplugin.commands.CMDtpaccept;
import de.theniclas.tokyobuild.bauplugin.commands.CMDtrust;
import de.theniclas.tokyobuild.bauplugin.commands.CMDtrusted;
import de.theniclas.tokyobuild.bauplugin.commands.CMDuntrust;
import de.theniclas.tokyobuild.bauplugin.commands.CMDvisibility;
import de.theniclas.tokyobuild.bauplugin.commands.CMDwkick;
import de.theniclas.tokyobuild.bauplugin.commands.CMDworldlock;
import de.theniclas.tokyobuild.bauplugin.commands.CMDworlds;
import de.theniclas.tokyobuild.bauplugin.events.AsyncPlayerChat;
import de.theniclas.tokyobuild.bauplugin.events.BlockBreak;
import de.theniclas.tokyobuild.bauplugin.events.BlockPlace;
import de.theniclas.tokyobuild.bauplugin.events.EntityDamageByEntity;
import de.theniclas.tokyobuild.bauplugin.events.FoodLevelChange;
import de.theniclas.tokyobuild.bauplugin.events.HangingBreakByEntity;
import de.theniclas.tokyobuild.bauplugin.events.InventoryClick;
import de.theniclas.tokyobuild.bauplugin.events.PlayerChangedWorld;
import de.theniclas.tokyobuild.bauplugin.events.PlayerCommandPreprocess;
import de.theniclas.tokyobuild.bauplugin.events.PlayerDeath;
import de.theniclas.tokyobuild.bauplugin.events.PlayerDropItem;
import de.theniclas.tokyobuild.bauplugin.events.PlayerInteract;
import de.theniclas.tokyobuild.bauplugin.events.PlayerInteractAtEntity;
import de.theniclas.tokyobuild.bauplugin.events.PlayerJoin;
import de.theniclas.tokyobuild.bauplugin.events.PlayerPickupItem;
import de.theniclas.tokyobuild.bauplugin.events.PlayerQuit;
import de.theniclas.tokyobuild.bauplugin.events.PlayerRespawn;
import de.theniclas.tokyobuild.bauplugin.events.WeatherChange;
import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BauserverPlugin extends JavaPlugin {

    @Getter
    private static BauserverPlugin plugin;
    @Getter
    private static LuckPerms luckPerms;

    @Override
    public void onEnable() {

        plugin = this;
        luckPerms = LuckPermsProvider.get();

        this.getCommand("gm").setExecutor(new CMDgm());
        this.getCommand("speed").setExecutor(new CMDspeed());
        this.getCommand("blocks").setExecutor(new CMDblocks());
        this.getCommand("tools").setExecutor(new CMDtools());
        this.getCommand("fly").setExecutor(new CMDfly());
        this.getCommand("worlds").setExecutor(new CMDworlds());
        this.getCommand("trust").setExecutor(new CMDtrust());
        this.getCommand("untrust").setExecutor(new CMDuntrust());
        this.getCommand("tpa").setExecutor(new CMDtpa());
        this.getCommand("tpaccept").setExecutor(new CMDtpaccept());
        this.getCommand("ping").setExecutor(new CMDping());
        this.getCommand("addspawn").setExecutor(new CMDaddspawn());
        this.getCommand("globalspawn").setExecutor(new CMDglobalspawn());
        this.getCommand("spawn").setExecutor(new CMDspawn());
        this.getCommand("visibility").setExecutor(new CMDvisibility());
        this.getCommand("prepare").setExecutor(new CMDprepare());
        this.getCommand("worldlock").setExecutor(new CMDworldlock());
        this.getCommand("wkick").setExecutor(new CMDwkick());
        this.getCommand("trusted").setExecutor(new CMDtrusted());
        this.getCommand("setowner").setExecutor(new CMDsetowner());

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
        getServer().getPluginManager().registerEvents(new PlayerChangedWorld(this, luckPerms), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChange(), this);

        Configs.loadConfiguration();
        Vars.loadGlobalSpawnWorld();

        System.out.println("Bauserver-Plugin gestartet");
    }

}
