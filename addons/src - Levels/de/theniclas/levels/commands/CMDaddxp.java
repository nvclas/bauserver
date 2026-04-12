package de.theniclas.levels.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.levels.utils.Methods;

public class CMDaddxp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("bs.admin")) {
            if (args.length >= 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    Methods.addXp(target, Integer.valueOf(args[0]));
                }
            } else if (args.length >= 1 && sender instanceof Player) {
                Methods.addXp((Player) sender, Integer.valueOf(args[0]));
            }
        }
        return false;
    }

}
