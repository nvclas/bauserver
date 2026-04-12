package de.theniclas.bauplugin.commands;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDprepare implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NO_PERM); return false; }

        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PREFIX + "§cDu bist nicht der Ersteller dieser Welt");
            return false;
        }

        World world = p.getWorld();
        world.setTime(6000);

        world.setGameRule(GameRule.DO_MOB_SPAWNING,     false);
        p.sendMessage(Vars.PREFIX + "§aMobspawning §edeaktiviert");

        world.setGameRule(GameRule.MOB_GRIEFING,         false);
        p.sendMessage(Vars.PREFIX + "§aMobgriefing §edeaktiviert");

        world.setGameRule(GameRule.DO_FIRE_TICK,         false);
        p.sendMessage(Vars.PREFIX + "§aFeuerausbreitung §edeaktiviert");

        world.setGameRule(GameRule.RANDOM_TICK_SPEED,    0);
        p.sendMessage(Vars.PREFIX + "§aZufällige Blockupdates §edeaktiviert");

        world.setGameRule(GameRule.DO_WEATHER_CYCLE,     false);
        world.setStorm(false);
        Configs.worldsConfig.set("Worlds." + Vars.stripWorldsPrefix(world.getName()) + ".Properties.Weather", false);
        Configs.saveConfiguration();
        p.sendMessage(Vars.PREFIX + "§aWetteränderungen §edeaktiviert");

        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,    false);
        p.sendMessage(Vars.PREFIX + "§aTag-/Nachtzyklus §edeaktiviert");

        return false;
    }
}
