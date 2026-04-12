package de.theniclas.bauplugin.events;

import org.bukkit.Bukkit;
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

        if (msg.startsWith("/help")) {
            e.setCancelled(true);
            p.sendMessage("\u00a7e-------\u00a76Verf\u00fcgbare Befehle\u00a7e-------");
            p.sendMessage("\u00a76/fly \u00a77- \u00a7eDe- und aktiviere das Fliegen");
            p.sendMessage("\u00a76/tpa \u00a77- \u00a7eSende eine Teleportanfrage");
            if (p.hasPermission("bs.gm")) p.sendMessage("\u00a76/gm \u00a77- \u00a7e\u00c4ndere deinen Spielmodus");
            if (p.hasPermission("bs.tp")) p.sendMessage("\u00a76/tp \u00a77- \u00a7eTeleportiere dich zu Spielern");
            if (p.hasPermission("bs.speed")) p.sendMessage("\u00a76/speed \u00a77- \u00a7e\u00c4ndere deine Geschwindigkeit");
            if (p.hasPermission("bs.blocks")) p.sendMessage("\u00a76/blocks \u00a77- \u00a7e\u00d6ffne Spezialbl\u00f6cke");
            if (p.hasPermission("bs.tools")) p.sendMessage("\u00a76/tools \u00a77- \u00a7e\u00d6ffne Bautools");
            if (p.hasPermission("bs.worlds")) {
                p.sendMessage("\u00a76/worlds \u00a77- \u00a7e\u00d6ffne das Weltenmenu");
                p.sendMessage("\u00a76/addspawn \u00a77- \u00a7eErstelle einen Spawnpunkt");
                p.sendMessage("\u00a76/trust \u00a77- \u00a7eGib einem Spieler Baurechte");
                p.sendMessage("\u00a76/untrust \u00a77- \u00a7eEntziehe Baurechte");
                p.sendMessage("\u00a76/wkick \u00a77- \u00a7eKicke einen Spieler aus deiner Welt");
                p.sendMessage("\u00a76/prepare \u00a77- \u00a7eBereite eine Welt vor");
            }
            if (p.hasPermission("bs.admin")) {
                p.sendMessage("\u00a76/visibility \u00a77- \u00a7eWeltsichtbarkeit umschalten");
                p.sendMessage("\u00a76/worldlock \u00a77- \u00a7eWelt sperren/entsperren");
                p.sendMessage("\u00a76/setowner \u00a77- \u00a7eWeltbesitzer \u00e4ndern");
            }
            p.sendMessage("\u00a7e-------------------------------");
            return;
        }

        if (msg.startsWith("/weather off")) {
            e.setCancelled(true);
            if (Vars.isTrusted(p, p.getWorld().getName()) || Vars.isOwner(p, p.getWorld().getName())
                    || p.hasPermission("bs.admin")) {
                p.getWorld().setStorm(false);
                p.getWorld().setThundering(false);
                Configs.worldsConfig.set("Worlds." + p.getWorld().getName().replace("worlds/", "") + ".Properties.Weather", false);
                Configs.saveConfiguration();
                p.sendMessage(Vars.PR + "\u00a7aWetter\u00e4nderungen wurden f\u00fcr diese Welt \u00a7edeaktiviert");
            } else {
                p.sendMessage(Vars.PR + "\u00a7cDu hast hier keine Rechte");
            }
            return;
        }

        if (msg.startsWith("/weather on")) {
            e.setCancelled(true);
            if (Vars.isTrusted(p, p.getWorld().getName()) || Vars.isOwner(p, p.getWorld().getName())
                    || p.hasPermission("bs.admin")) {
                Configs.worldsConfig.set("Worlds." + p.getWorld().getName().replace("worlds/", "") + ".Properties.Weather", true);
                Configs.saveConfiguration();
                p.sendMessage(Vars.PR + "\u00a7aWetter\u00e4nderungen wurden f\u00fcr diese Welt \u00a7eaktiviert");
            } else {
                p.sendMessage(Vars.PR + "\u00a7cDu hast hier keine Rechte");
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
                    p.sendMessage(Vars.PR + "\u00a7aDu wurdest zu \u00a7e" + target.getName() + " \u00a7ateleportiert");
                } else {
                    p.sendMessage(Vars.PR + "\u00a7cDieser Spieler ist nicht online");
                }
            }
        }
    }
}
