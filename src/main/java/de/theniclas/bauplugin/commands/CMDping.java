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
        String color = ping <= 40 ? "<green>" : ping >= 80 ? "<red>" : "<gold>";
        p.sendMessage(Vars.prefixed("<yellow>Dein Ping beträgt " + color + ping + "ms"));
        return false;
    }
}
