package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDaddspawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!(Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin"))) {
            p.sendMessage(Vars.PR + "\u00a7cDu musst dich in deiner eigenen Welt befinden"); return false;
        }
        if (args.length < 1) { p.sendMessage(Vars.PR + "\u00a7cWie soll der Spawnpunkt hei\u00dfen?"); return false; }

        String worldKey = p.getLocation().getWorld().getName().replace("worlds/", "");
        String spawnsPath = "Worlds." + worldKey + ".Spawns";
        if (Configs.worldsConfig.get(spawnsPath) != null &&
                Configs.worldsConfig.getConfigurationSection(spawnsPath).getKeys(false).size() >= 9) {
            p.sendMessage(Vars.PR + "\u00a7cEs existieren bereits zu viele Spawnpunkte f\u00fcr diese Welt");
            return false;
        }
        if (Configs.worldsConfig.get(spawnsPath) != null &&
                Configs.worldsConfig.getConfigurationSection(spawnsPath).getKeys(false).contains(args[0])) {
            p.sendMessage(Vars.PR + "\u00a7cDiesen Spawnpunktnamen gibt es bereits f\u00fcr diese Welt");
            return false;
        }
        String loc = worldKey + ", " + p.getLocation().getX() + ", " + p.getLocation().getY()
                + ", " + p.getLocation().getZ();
        Configs.worldsConfig.set(spawnsPath + "." + args[0] + ".Location", loc);
        Configs.saveConfiguration();
        p.sendMessage(Vars.PR + "\u00a7aSpawnpunkt \u00a7e" + args[0] + " \u00a7aerstellt");
        return false;
    }
}
