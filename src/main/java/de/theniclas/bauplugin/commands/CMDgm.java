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
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (args.length == 0) { p.sendMessage(Vars.PR + "§cUps, fehlt da etwa eine Zahl?"); return false; }

        if (args.length == 1) {
            GameMode gm = parseGameMode(args[0]);
            if (gm == null) { p.sendMessage(Vars.PR + "§cAber diesen Spielmodus gibt's gar nicht"); return false; }
            p.setGameMode(gm);
            p.sendMessage(Vars.PR + "§aDu bist nun im Spielmodus §e" + gm.name());
        } else {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) { p.sendMessage(Vars.PR + "§cDieser Spieler ist nicht online"); return false; }
            if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
                p.sendMessage(Vars.PR + "§cDu musst der Besitzer dieser Welt sein"); return false;
            }
            if (target.getWorld() != p.getWorld() && !p.hasPermission("bs.admin")) {
                p.sendMessage(Vars.PR + "§cDas Ziel muss sich in deiner Welt befinden"); return false;
            }
            GameMode gm = parseGameMode(args[0]);
            if (gm == null) { p.sendMessage(Vars.PR + "§cAber diesen Spielmodus gibt's gar nicht"); return false; }
            target.setGameMode(gm);
            target.sendMessage(Vars.PR + "§aDu bist nun im Spielmodus §e" + gm.name());
            p.sendMessage(Vars.PR + "§e" + target.getName() + " §aist nun im Spielmodus §e" + gm.name());
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
