package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDprepare implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("prepare") && sender instanceof Player p) {
            if (p.hasPermission("bs.worlds")) {
                if (Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
                    p.getWorld().setTime(6000);
                    p.getWorld().setGameRule(org.bukkit.GameRule.DO_MOB_SPAWNING, false);
                    p.sendMessage(Vars.pr + "§aMobspawning §edeaktiviert");

                    p.getWorld().setGameRule(org.bukkit.GameRule.MOB_GRIEFING, false);
                    p.sendMessage(Vars.pr + "§aMobgriefing §edeaktiviert");

                    p.getWorld().setGameRule(org.bukkit.GameRule.DO_FIRE_TICK, false);
                    p.sendMessage(Vars.pr + "§aFeuerausbreitung §edeaktiviert");

                    p.getWorld().setGameRule(org.bukkit.GameRule.RANDOM_TICK_SPEED, 0);
                    p.sendMessage(Vars.pr + "§aZufällige Blockupdates §edeaktiviert");

                    p.getWorld().setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                    p.sendMessage(Vars.pr + "§aWetter §edeaktiviert");

                    p.getWorld().setGameRule(org.bukkit.GameRule.DO_DAYLIGHT_CYCLE, false);
                    p.sendMessage(Vars.pr + "§aTag-/Nachtzyklus §edeaktiviert");

                    if (Bukkit.getPluginManager().getPlugin("AntiBlockUpdate") != null) {
                        p.chat("/abu");
                    }
                } else {
                    p.sendMessage(Vars.pr + "§cDu bist nicht der Ersteller dieser Welt");
                }
            } else {
                p.sendMessage(Vars.noperm);
            }
        }

        return false;
    }
}
