package de.theniclas.bauplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class PlayerCommandPreprocess implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage().toLowerCase();

        // Custom /help
        if (msg.startsWith("/help")) {
            e.setCancelled(true);
            sendHelp(p);
            return;
        }

        // /weather off|on intercepted so world owners can toggle it without needing OP
        if (msg.startsWith("/weather off")) {
            e.setCancelled(true);
            if (Vars.isTrusted(p, p.getWorld().getName())
                    || Vars.isOwner(p, p.getWorld().getName())
                    || p.hasPermission("bs.admin")) {
                p.getWorld().setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                Configs.worldsConfig.set("Worlds." + Vars.stripWorldsPrefix(p.getWorld().getName()) + ".Properties.Weather", false);
                Configs.saveConfiguration();
                p.sendMessage(Vars.PREFIX + "§aWetteränderungen wurden für diese Welt §edeaktiviert");
            } else {
                p.sendMessage(Vars.PREFIX + "§cDu hast hier keine Rechte :(");
            }
            return;
        }

        if (msg.startsWith("/weather on")) {
            e.setCancelled(true);
            if (Vars.isTrusted(p, p.getWorld().getName())
                    || Vars.isOwner(p, p.getWorld().getName())
                    || p.hasPermission("bs.admin")) {
                p.getWorld().setGameRule(GameRule.DO_WEATHER_CYCLE, true);
                Configs.worldsConfig.set("Worlds." + Vars.stripWorldsPrefix(p.getWorld().getName()) + ".Properties.Weather", true);
                Configs.saveConfiguration();
                p.sendMessage(Vars.PREFIX + "§aWetteränderungen wurden für diese Welt §eaktiviert");
            } else {
                p.sendMessage(Vars.PREFIX + "§cDu hast hier keine Rechte :(");
            }
            return;
        }

        // Protect WorldEdit undo/redo (/abu) in worlds where the player has no rights
        if (msg.startsWith("/abu")) {
            if (!Vars.isOwner(p, p.getWorld().getName())
                    && !Vars.isTrusted(p, p.getWorld().getName())
                    && !p.hasPermission("bs.admin")) {
                e.setCancelled(true);
                p.sendMessage(Vars.PREFIX + "§cDu hast hier keine Rechte :(");
            }
            return;
        }

        // Simple /tp that respects the bs.tp permission
        String[] args = msg.split(" ");
        if (args[0].equals("/tp") && args.length == 2 && p.hasPermission("bs.tp")) {
            e.setCancelled(true);
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                p.teleport(target);
                p.sendMessage(Vars.PREFIX + "§aDu wurdest zu §e" + target.getName() + " §ateleportiert");
            } else {
                p.sendMessage(Vars.PREFIX + "§cDieser Spieler ist nicht online");
            }
        }
    }

    private void sendHelp(Player p) {
        p.sendMessage("§e-------§6Verfügbare Befehle§e-------");
        p.sendMessage("§6/fly §7- §eAktiviere / deaktiviere das Fliegen");
        p.sendMessage("§6/tpa §7- §eSende eine Teleportanfrage an einen Spieler");

        if (p.hasPermission("bs.gm"))    p.sendMessage("§6/gm §7- §eÄndere deinen Spielmodus");
        if (p.hasPermission("bs.tp"))    p.sendMessage("§6/tp §7- §eTeleportiere dich zu Spielern");
        if (p.hasPermission("bs.speed")) p.sendMessage("§6/speed §7- §eÄndere deine Flug- und Laufgeschwindigkeit");
        if (p.hasPermission("bs.blocks"))p.sendMessage("§6/blocks §7- §eÖffne eine Übersicht von Spezialblöcken");
        if (p.hasPermission("bs.tools")) p.sendMessage("§6/tools §7- §eÖffne eine Übersicht von Bautools");

        if (p.hasPermission("bs.worlds")) {
            p.sendMessage("§6/worlds §7- §eÖffne das Weltenmenü");
            p.sendMessage("§6/addspawn §7- §eErstelle einen neuen Spawnpunkt");
            p.sendMessage("§6/trust §7- §eGib einem Spieler Baurechte in deiner Welt");
            p.sendMessage("§6/untrust §7- §eEntziehe einem Spieler Baurechte in deiner Welt");
            p.sendMessage("§6/wkick §7- §eKicke einen Spieler aus deiner Welt");
            p.sendMessage("§6/prepare §7- §eBereite eine Welt vor");
        }

        if (p.hasPermission("bs.admin")) {
            p.sendMessage("§6/visibility §7- §eMache nur eigene Welten sichtbar");
            p.sendMessage("§6/worldlock §7- §eBlende eine Welt aus dem Welteninventar aus");
            p.sendMessage("§6/setowner §7- §eÄndere den Besitzer einer Welt");
        }

        p.sendMessage("§e-------------------------------");
    }
}
