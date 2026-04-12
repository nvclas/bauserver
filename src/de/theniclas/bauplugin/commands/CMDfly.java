package de.theniclas.bauplugin.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDfly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;

        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
            p.sendMessage(Vars.PREFIX + "§cDu kannst in deinem Spielmodus schon längst fliegen");
            return false;
        }

        p.setAllowFlight(!p.getAllowFlight());
        p.sendMessage(Vars.PREFIX + "§aFlugmodus §e" + (p.getAllowFlight() ? "aktiviert" : "deaktiviert"));
        return false;
    }
}
