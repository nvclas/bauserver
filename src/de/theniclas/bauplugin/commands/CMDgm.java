package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDgm implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NO_PERM); return false; }

        if (args.length == 0) {
            p.sendMessage(Vars.PREFIX + "§cUps, fehlt da etwa eine Zahl?");
            return false;
        }

        GameMode mode = parseMode(args[0]);
        if (mode == null) {
            p.sendMessage(Vars.PREFIX + "§cAber diesen Spielmodus gibt's gar nicht");
            return false;
        }

        if (args.length >= 2) {
            // Change another player's game mode
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) { p.sendMessage(Vars.PREFIX + "§cDieser Spieler ist nicht online"); return false; }

            boolean sameWorld = target.getWorld() == p.getWorld();
            if (!p.hasPermission("bs.admin")) {
                if (!Vars.isOwner(p, p.getWorld().getName())) {
                    p.sendMessage(Vars.PREFIX + "§cDu musst der Besitzer dieser Welt sein");
                    return false;
                }
                if (!sameWorld) {
                    p.sendMessage(Vars.PREFIX + "§cDas Ziel muss sich in deiner Welt befinden");
                    return false;
                }
            }
            target.setGameMode(mode);
            target.sendMessage(Vars.PREFIX + "§aDu bist nun im Spielmodus §e" + modeName(mode));
            p.sendMessage(Vars.PREFIX + "§e" + target.getName() + " §aist nun im Spielmodus §e" + modeName(mode));
        } else {
            p.setGameMode(mode);
            p.sendMessage(Vars.PREFIX + "§aDu bist nun im Spielmodus §e" + modeName(mode));
        }
        return false;
    }

    private GameMode parseMode(String arg) {
        return switch (arg) {
            case "0" -> GameMode.SURVIVAL;
            case "1" -> GameMode.CREATIVE;
            case "2" -> GameMode.ADVENTURE;
            case "3" -> GameMode.SPECTATOR;
            default  -> null;
        };
    }

    private String modeName(GameMode mode) {
        return switch (mode) {
            case SURVIVAL  -> "Survival";
            case CREATIVE  -> "Creative";
            case ADVENTURE -> "Adventure";
            case SPECTATOR -> "Spectator";
        };
    }
}
