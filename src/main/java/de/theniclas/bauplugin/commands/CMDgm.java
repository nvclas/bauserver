package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDgm implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p))
            return false;
        if (!p.hasPermission("bs.worlds")) {
            p.sendMessage(Vars.NOPERM);
            return false;
        }
        if (args.length == 0) {
            p.sendMessage(Vars.prefixed("<red>Ups, fehlt da etwa eine Zahl?"));
            return false;
        }

        if (args.length == 1) {
            GameMode gm = parseGameMode(args[0]);
            if (gm == null) {
                p.sendMessage(Vars.prefixed("<red>Aber diesen Spielmodus gibt's gar nicht"));
                return false;
            }
            p.setGameMode(gm);
            p.sendMessage(Vars.prefixed("<green>Du bist nun im Spielmodus <yellow>" + gm.name()));
        } else {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                p.sendMessage(Vars.prefixed("<red>Dieser Spieler ist nicht online"));
                return false;
            }
            if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
                p.sendMessage(Vars.prefixed("<red>Du musst der Besitzer dieser Welt sein"));
                return false;
            }
            if (target.getWorld() != p.getWorld() && !p.hasPermission("bs.admin")) {
                p.sendMessage(Vars.prefixed("<red>Das Ziel muss sich in deiner Welt befinden"));
                return false;
            }
            GameMode gm = parseGameMode(args[0]);
            if (gm == null) {
                p.sendMessage(Vars.prefixed("<red>Aber diesen Spielmodus gibt's gar nicht"));
                return false;
            }
            target.setGameMode(gm);
            target.sendMessage(Vars.prefixed("<green>Du bist nun im Spielmodus <yellow>" + gm.name()));
            p.sendMessage(Vars.prefixed(
                    "<yellow>" + target.getName() + " <green>ist nun im Spielmodus <yellow>" + gm.name()));
        }
        return false;
    }

    private GameMode parseGameMode(String s) {
        return switch (s) {
            case "0" -> GameMode.SURVIVAL;
            case "1" -> GameMode.CREATIVE;
            case "2" -> GameMode.ADVENTURE;
            case "3" -> GameMode.SPECTATOR;
            default -> null;
        };
    }
}
