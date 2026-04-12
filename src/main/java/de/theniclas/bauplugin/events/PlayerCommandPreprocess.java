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
            p.sendMessage(Vars.mini("<yellow>-------<gold>Verfügbare Befehle<yellow>-------"));
            p.sendMessage(Vars.mini("<gold>/fly <gray>- <yellow>De- und aktiviere das Fliegen"));
            p.sendMessage(Vars.mini("<gold>/tpa <gray>- <yellow>Sende eine Teleportanfrage"));
            if (p.hasPermission("bs.gm")) p.sendMessage(Vars.mini("<gold>/gm <gray>- <yellow>Ändere deinen Spielmodus"));
            if (p.hasPermission("bs.tp")) p.sendMessage(Vars.mini("<gold>/tp <gray>- <yellow>Teleportiere dich zu Spielern"));
            if (p.hasPermission("bs.speed")) p.sendMessage(Vars.mini("<gold>/speed <gray>- <yellow>Ändere deine Geschwindigkeit"));
            if (p.hasPermission("bs.blocks")) p.sendMessage(Vars.mini("<gold>/blocks <gray>- <yellow>Öffne Spezialblöcke"));
            if (p.hasPermission("bs.tools")) p.sendMessage(Vars.mini("<gold>/tools <gray>- <yellow>Öffne Bautools"));
            if (p.hasPermission("bs.worlds")) {
                p.sendMessage(Vars.mini("<gold>/worlds <gray>- <yellow>Öffne das Weltenmenu"));
                p.sendMessage(Vars.mini("<gold>/addspawn <gray>- <yellow>Erstelle einen Spawnpunkt"));
                p.sendMessage(Vars.mini("<gold>/trust <gray>- <yellow>Gib einem Spieler Baurechte"));
                p.sendMessage(Vars.mini("<gold>/untrust <gray>- <yellow>Entziehe Baurechte"));
                p.sendMessage(Vars.mini("<gold>/wkick <gray>- <yellow>Kicke einen Spieler aus deiner Welt"));
                p.sendMessage(Vars.mini("<gold>/prepare <gray>- <yellow>Bereite eine Welt vor"));
            }
            if (p.hasPermission("bs.admin")) {
                p.sendMessage(Vars.mini("<gold>/visibility <gray>- <yellow>Weltsichtbarkeit umschalten"));
                p.sendMessage(Vars.mini("<gold>/worldlock <gray>- <yellow>Welt sperren/entsperren"));
                p.sendMessage(Vars.mini("<gold>/setowner <gray>- <yellow>Weltbesitzer ändern"));
            }
            p.sendMessage(Vars.mini("<yellow>-------------------------------"));
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
                p.sendMessage(Vars.prefixed("<green>Wetteränderungen wurden für diese Welt <yellow>deaktiviert"));
            } else {
                p.sendMessage(Vars.prefixed("<red>Du hast hier keine Rechte"));
            }
            return;
        }

        if (msg.startsWith("/weather on")) {
            e.setCancelled(true);
            if (Vars.isTrusted(p, p.getWorld().getName()) || Vars.isOwner(p, p.getWorld().getName())
                    || p.hasPermission("bs.admin")) {
                plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + p.getWorld().getName().replace("worlds/", "") + ".Properties.Weather", true);
                plugin.getBauserverConfig().saveConfiguration();
                p.sendMessage(Vars.prefixed("<green>Wetteränderungen wurden für diese Welt <yellow>aktiviert"));
            } else {
                p.sendMessage(Vars.prefixed("<red>Du hast hier keine Rechte"));
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
                    p.sendMessage(Vars.prefixed("<green>Du wurdest zu <yellow>" + target.getName() + " <green>teleportiert"));
                } else {
                    p.sendMessage(Vars.prefixed("<red>Dieser Spieler ist nicht online"));
                }
            }
        }
    }
}
