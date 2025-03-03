package de.theniclas.tokyobuild.bauplugin.events;

import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocess implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (e.getMessage().toLowerCase().startsWith("/help")) {
            e.setCancelled(true);

            p.sendMessage("§e-------§6Verfügbare Befehle§e-------");
            p.sendMessage("§6/fly §7- §eDe- und aktiviere das Fliegen");
            p.sendMessage("§6/tpa §7- §eSende eine Teleportanfrage an einen Spieler");

            if (p.hasPermission("bs.gm")) {
                p.sendMessage("§6/gm §7- §eÄndere deinen Spielmodus");
            }

            if (p.hasPermission("bs.tp")) {
                p.sendMessage("§6/tp §7- §eTeleportiere dich zu Spielern");
            }

            if (p.hasPermission("bs.speed")) {
                p.sendMessage("§6/speed §7- §eÄndere deine Flug- und Laufgeschwindigkeit");
            }

            if (p.hasPermission("bs.blocks")) {
                p.sendMessage("§6/blocks §7- §eÖffne eine Übersicht von Spezialblöcken");
            }

            if (p.hasPermission("bs.tools")) {
                p.sendMessage("§6/tools §7- §eÖffne eine Übersicht von Bautools");
            }

            if (p.hasPermission("bs.worlds")) {
                p.sendMessage("§6/worlds §7- §eÖffne das Weltenmenu");
                p.sendMessage("§6/addspawn §7- §eErstelle einen neuen Spawnpunkt");
                p.sendMessage("§6/trust §7- §eGib einem Spieler Baurechte in deiner Welt");
                p.sendMessage("§6/untrust §7- §eEntziehe einem Spieler Baurechte in deiner Welt");
                p.sendMessage("§6/wkick §7- §eKicke einen Spieler aus deiner Welt");
                p.sendMessage("§6/prepare §7- §eBereite eine Welt vor");
                if (Bukkit.getPluginManager().getPlugin("Levels") != null) {
                    p.sendMessage("§6/level §7- §eLasse dir dein Level und deine XP anzeigen");
                }
            }

            if (p.hasPermission("bs.admin")) {
                p.sendMessage("§6/visibility §7- §eMache nur eigene Welten sichtbar");
                p.sendMessage("§6/worldlock §7- §eBlende eine Welt aus dem Welteninventar aus");
                p.sendMessage("§6/setowner §7- §eÄndere den Besitzer einer Welt");
                if (Bukkit.getPluginManager().getPlugin("Levels") != null) {
                    p.sendMessage("§6/unlock §7- §eSchalte einen Spieler frei");
                    p.sendMessage("§6/lock §7- §eSperre einen Spieler");
                }
            }

            p.sendMessage("§e-------------------------------");

        }

        if (e.getMessage().toLowerCase().startsWith("/weather off")) {
            e.setCancelled(true);
            if (Vars.isTrusted(p, p.getWorld().getName()) || Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission(
                    "bs.admin")) {
                Configs.worldsConfig.set("Worlds." + p.getWorld()
                        .getName()
                        .replace("worlds/", "") + ".Properties.Weather", false);
                Configs.saveConfiguration();
                p.sendMessage(Vars.pr + "§aWetteränderungen wurden für diese Welt §edeaktiviert");
            } else {
                p.sendMessage(Vars.pr + "§cDu hast hier keine Rechte :(");
            }
        } else if (e.getMessage().toLowerCase().startsWith("/weather on")) {
            e.setCancelled(true);
            if (Vars.isTrusted(p, p.getWorld().getName()) || Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission(
                    "bs.admin")) {
                Configs.worldsConfig.set("Worlds." + p.getWorld()
                        .getName()
                        .replace("worlds/", "") + ".Properties.Weather", true);
                Configs.saveConfiguration();
                p.sendMessage(Vars.pr + "§aWetteränderungen wurden für diese Welt §eaktiviert");
            } else {
                p.sendMessage(Vars.pr + "§cDu hast hier keine Rechte :(");
            }
        }

        if (e.getMessage().toLowerCase().startsWith("/abu ") || e.getMessage().equalsIgnoreCase("/abu")) {
            if (!Vars.isOwner(p, p.getWorld().getName()) && !Vars.isTrusted(p,
                    p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
                e.setCancelled(true);
                p.sendMessage(Vars.pr + "§cDu hast hier keine Rechte :(");
            }
        }

        if (e.getMessage().toLowerCase().startsWith("/tp ") || e.getMessage().equalsIgnoreCase("/tp")) {
            String[] args = e.getMessage().toLowerCase().split(" ");
            if (args.length == 2 && p.hasPermission("bs.tp")) {
                e.setCancelled(true);
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    p.teleport(target);
                    p.sendMessage(Vars.pr + "§aDu wurdest zu §e" + target.getName() + " §ateleportiert");
                } else {
                    p.sendMessage(Vars.pr + "§cDieser Spieler ist nicht online");
                }
            }
        }

    }
}
