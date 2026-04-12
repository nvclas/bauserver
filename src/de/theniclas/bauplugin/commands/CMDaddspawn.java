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
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NO_PERM); return false; }

        if (args.length < 1) {
            p.sendMessage(Vars.PREFIX + "§cWie soll der Spawnpunkt heißen?");
            return false;
        }

        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PREFIX + "§cDu musst dich in deiner eigenen Welt befinden");
            return false;
        }

        String worldKey = Vars.stripWorldsPrefix(p.getWorld().getName());
        String spawnPath = "Worlds." + worldKey + ".Spawns";
        var spawnsSection = Configs.worldsConfig.getConfigurationSection(spawnPath);

        if (spawnsSection != null && spawnsSection.getKeys(false).size() >= 9) {
            p.sendMessage(Vars.PREFIX + "§cEs existieren bereits zu viele Spawnpunkte für diese Welt, lösche sie mithilfe von §e/worlds");
            return false;
        }

        if (spawnsSection != null && spawnsSection.getKeys(false).contains(args[0])) {
            p.sendMessage(Vars.PREFIX + "§cDiesen Spawnpunktnamen gibt es bereits für diese Welt");
            return false;
        }

        String loc = worldKey + ", " + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ();
        Configs.worldsConfig.set(spawnPath + "." + args[0] + ".Location", loc);
        Configs.saveConfiguration();
        p.sendMessage(Vars.PREFIX + "§aSpawnpunkt §e" + args[0] + " §aerstellt");
        return false;
    }
}
