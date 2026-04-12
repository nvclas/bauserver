package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.Bauserver;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.theniclas.bauplugin.utils.BauserverConfig;
import de.theniclas.bauplugin.utils.Vars;

@RequiredArgsConstructor
public class PlayerCommandPreprocess implements Listener {

    private final Bauserver plugin;

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage().toLowerCase();

        if (msg.startsWith("/help")) {
            e.setCancelled(true);
            p.sendMessage("§e-------§6Verfügbare Befehle§e-------");
            p.sendMessage("§6/fly §7- §eDe- und aktiviere das Fliegen");
            p.sendMessage("§6/tpa §7- §eSende eine Teleportanfrage");
            if (p.hasPermission("bs.gm")) p.sendMessage("§6/gm §7- §eÄndere deinen Spielmodus");
            if (p.hasPermission("bs.tp")) p.sendMessage("§6/tp §7- §eTeleportiere dich zu Spielern");
            if (p.hasPermission("bs.speed")) p.sendMessage("§6/speed §7- §eÄndere deine Geschwindigkeit");
            if (p.hasPermission("bs.blocks")) p.sendMessage("§6/blocks §7- §eÖffne Spezialblöcke");
            if (p.hasPermission("bs.tools")) p.sendMessage("§6/tools §7- §eÖffne Bautools");
            if (p.hasPermission("bs.worlds")) {
                p.sendMessage("§6/worlds §7- §eÖffne das Weltenmenu");
                p.sendMessage("§6/addspawn §7- §eErstelle einen Spawnpunkt");
                p.sendMessage("§6/trust §7- §eGib einem Spieler Baurechte");
                p.sendMessage("§6/untrust §7- §eEntziehe Baurechte");
                p.sendMessage("§6/wkick §7- §eKicke einen Spieler aus deiner Welt");
                p.sendMessage("§6/prepare §7- §eBereite eine Welt vor");
            }
            if (p.hasPermission("bs.admin")) {
                p.sendMessage("§6/visibility §7- §eWeltsichtbarkeit umschalten");
                p.sendMessage("§6/worldlock §7- §eWelt sperren/entsperren");
                p.sendMessage("§6/setowner §7- §eWeltbesitzer ändern");
            }
            p.sendMessage("§e-------------------------------");
            return;
        }

        if (msg.startsWith("/weather off")) {
            e.setCancelled(true);
            if (Vars.isTrusted(p, p.getWorld().getName()) || Vars.isOwner(p, p.getWorld().getName())
                    || p.hasPermission("bs.admin")) {
                p.getWorld().setStorm(false);
                p.getWorld().setThundering(false);
                plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + p.getWorld().getName().replace("worlds/", "") + ".Properties.Weather", false);
                plugin.getBauserverConfig().saveConfiguration();
                p.sendMessage(Vars.PR + "§aWetteränderungen wurden für diese Welt §edeaktiviert");
            } else {
                p.sendMessage(Vars.PR + "§cDu hast hier keine Rechte");
            }
            return;
        }

        if (msg.startsWith("/weather on")) {
            e.setCancelled(true);
            if (Vars.isTrusted(p, p.getWorld().getName()) || Vars.isOwner(p, p.getWorld().getName())
                    || p.hasPermission("bs.admin")) {
                plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + p.getWorld().getName().replace("worlds/", "") + ".Properties.Weather", true);
                plugin.getBauserverConfig().saveConfiguration();
                p.sendMessage(Vars.PR + "§aWetteränderungen wurden für diese Welt §eaktiviert");
            } else {
                p.sendMessage(Vars.PR + "§cDu hast hier keine Rechte");
            }
            return;
        }

        if (msg.startsWith("/tp ") || msg.equals("/tp")) {
            String[] args = msg.split(" ");
            if (args.length == 2 && p.hasPermission("bs.tp")) {
                e.setCancelled(true);
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    p.teleport(target);
                    p.sendMessage(Vars.PR + "§aDu wurdest zu §e" + target.getName() + " §ateleportiert");
                } else {
                    p.sendMessage(Vars.PR + "§cDieser Spieler ist nicht online");
                }
            }
        }
    }
}
