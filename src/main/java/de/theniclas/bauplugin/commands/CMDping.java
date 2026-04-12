package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDping implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        int ping = p.getPing();
        String color = ping <= 40 ? "\u00a7a" : ping >= 80 ? "\u00a7c" : "\u00a76";
        p.sendMessage(Vars.PR + "\u00a7eDein Ping betr\u00e4gt " + color + ping + "ms");
        return false;
    }
}
