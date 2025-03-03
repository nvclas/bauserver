package de.nvclas.tokyobuild.levels.achievements;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import de.nvclas.tokyobuild.levels.utils.Methods;

public class EnterNetherPortal implements Listener {
	@EventHandler 
	public void onEnteringNetherPortal(PlayerPortalEvent e) {
		
		Player p = e.getPlayer();
		if(e.getCause() == TeleportCause.NETHER_PORTAL) {
			if(Methods.isUnlocked(p)) {
				Methods.addAchievement(p, "Kann man in den Nether?!", 20);				
			}
		}
	}
}
