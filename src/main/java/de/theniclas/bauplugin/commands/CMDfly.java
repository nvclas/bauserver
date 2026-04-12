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
            p.sendMessage(Vars.prefixed("<red>Du kannst in deinem Spielmodus schon längst fliegen"));
            return false;
        }
        if (!p.getAllowFlight()) {
            p.setAllowFlight(true);
            p.sendMessage(Vars.prefixed("<green>Flugmodus <yellow>aktiviert"));
        } else {
            p.setAllowFlight(false);
            p.sendMessage(Vars.prefixed("<green>Flugmodus <yellow>deaktiviert"));
        }
        return false;
    }
}
