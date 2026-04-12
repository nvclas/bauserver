package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.Bauserver;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.BauserverConfig;
import de.theniclas.bauplugin.utils.Vars;

@RequiredArgsConstructor
public class CMDaddspawn implements CommandExecutor {

    private final Bauserver plugin;
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!(Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin"))) {
            p.sendMessage(Vars.prefixed("<red>Du musst dich in deiner eigenen Welt befinden")); return false;
        }
        if (args.length < 1) { p.sendMessage(Vars.prefixed("<red>Wie soll der Spawnpunkt heißen?")); return false; }

        String worldKey = p.getLocation().getWorld().getName().replace("worlds/", "");
        String spawnsPath = "Worlds." + worldKey + ".Spawns";
        if (plugin.getBauserverConfig().getWorldsConfig().get(spawnsPath) != null &&
                plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection(spawnsPath).getKeys(false).size() >= 9) {
            p.sendMessage(Vars.prefixed("<red>Es existieren bereits zu viele Spawnpunkte für diese Welt"));
            return false;
        }
        if (plugin.getBauserverConfig().getWorldsConfig().get(spawnsPath) != null &&
                plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection(spawnsPath).getKeys(false).contains(args[0])) {
            p.sendMessage(Vars.prefixed("<red>Diesen Spawnpunktnamen gibt es bereits für diese Welt"));
            return false;
        }
        String loc = worldKey + ", " + p.getLocation().getX() + ", " + p.getLocation().getY()
                + ", " + p.getLocation().getZ();
        plugin.getBauserverConfig().getWorldsConfig().set(spawnsPath + "." + args[0] + ".Location", loc);
        plugin.getBauserverConfig().saveConfiguration();
        p.sendMessage(Vars.prefixed("<green>Spawnpunkt <yellow>" + args[0] + " <green>erstellt"));
        return false;
    }
}
