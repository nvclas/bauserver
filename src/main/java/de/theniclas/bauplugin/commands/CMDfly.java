package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDfly implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fly") && sender instanceof Player p) {
                if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
                    if (!p.getAllowFlight()) {
                        p.setAllowFlight(true);
                        p.sendMessage(Vars.pr + "§aFlugmodus §eaktiviert");
                    } else {
                        p.setAllowFlight(false);
                        p.sendMessage(Vars.pr + "§aFlugmodus §edeaktiviert");
                    }
                } else {
                    p.sendMessage(Vars.pr + "§cDu kannst in deinem Spielmodus schon längst fliegen");
                }
            }

        return false;
    }

}
