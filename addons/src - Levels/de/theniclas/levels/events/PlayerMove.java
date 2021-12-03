package de.theniclas.levels.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.theniclas.bauplugin.utils.Vars;
import de.theniclas.levels.utils.Scheduler;

public class PlayerMove implements Listener {
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(Scheduler.afk.containsKey(p)) {
			Scheduler.afk.remove(p);
		}
		if(Scheduler.afkPlayers.contains(p)) {
			Scheduler.afkPlayers.remove(p);
			p.sendMessage(Vars.pr + "§eDu bist nun nicht mehr AFK");
		}
		
	}
}
