package de.theniclas.bauplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerChangedWorld implements Listener {
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		PermissionUser pp = PermissionsEx.getUser(p);
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(all.getWorld() == e.getFrom()) {
				all.sendMessage("§e" + p.getName() + " §7hat die Welt verlassen");
			} else if(all.getWorld() == p.getWorld()) {
				all.sendMessage("§e" + p.getName() + " §7hat die Welt betreten");
			}
		}
		
		if(!p.hasPermission("bs.admin")) {
			if(Vars.isOwner(p, p.getWorld().getName()) || Vars.isTrusted(p, p.getWorld().getName())) {
				pp.addPermission("worldedit.history.undo");
				pp.addPermission("worldedit.history.redo");
				pp.addPermission("worldedit.navigation.*");
				pp.addPermission("worldedit.wand.*");
				pp.addPermission("worldedit.selection.*");
				pp.addPermission("worldedit.analysis.*");
				pp.addPermission("worldedit.region.*");
				pp.addPermission("worldedit.generation.*");
				pp.addPermission("worldedit.clipboard.*");
				pp.addPermission("worldedit.tool.*");
				pp.addPermission("worldedit.brush.*");
				pp.addPermission("worldedit.biome.*");
				pp.addPermission("gobrush.use");
				pp.addPermission("gopaint.use");
				pp.addPermission("voxelsniper.sniper");
				pp.addPermission("voxelsniper.brush");
				pp.addPermission("astools.use");
				PermissionsEx.getPlugin().reloadConfig();
			} else {
				pp.removePermission("worldedit.history.undo");
				pp.removePermission("worldedit.history.redo");
				pp.removePermission("worldedit.navigation.*");
				pp.removePermission("worldedit.wand.*");
				pp.removePermission("worldedit.selection.*");
				pp.removePermission("worldedit.analysis.*");
				pp.removePermission("worldedit.region.*");
				pp.removePermission("worldedit.generation.*");
				pp.removePermission("worldedit.clipboard.*");
				pp.removePermission("worldedit.tool.*");
				pp.removePermission("worldedit.brush.*");
				pp.removePermission("worldedit.biome.*");
				pp.removePermission("gobrush.use");
				pp.removePermission("gopaint.use");
				pp.removePermission("voxelsniper.sniper");
				pp.removePermission("voxelsniper.brush");
				pp.removePermission("astools.use");
				PermissionsEx.getPlugin().reloadConfig();
			}
		}
		
		if(!e.getFrom().getName().equals(Configs.worldsConfig.getString("Spawn.World")) && e.getFrom().getPlayers().size() == 0) {
			Bukkit.unloadWorld(e.getFrom(), true);
		}
	}
}