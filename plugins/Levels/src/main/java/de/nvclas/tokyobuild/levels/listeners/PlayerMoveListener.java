package de.nvclas.tokyobuild.levels.listeners;

import de.nvclas.tokyobuild.levels.utils.Scheduler;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        Scheduler.afkTimer.remove(p);
        if (Scheduler.afkPlayers.remove(p)) {
            p.sendMessage(Vars.pr + "�eDu bist nun nicht mehr AFK");
        }

    }
}
