package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDwkick implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PR + "\u00a7cDas hier ist gar nicht deine Welt"); return false;
        }
        if (args.length < 1) { p.sendMessage(Vars.PR + "\u00a7cWen willst du kicken?"); return false; }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { p.sendMessage(Vars.PR + "\u00a7cDieser Spieler ist nicht online"); return false; }
        if (target.hasPermission("bs.admin")) { p.sendMessage(Vars.PR + "\u00a7cDiesen Spieler kannst du nicht kicken"); return false; }
        if (Configs.worldsConfig.getConfigurationSection("Spawn") == null) {
            p.sendMessage(Vars.PR + "\u00a7cKein globaler Spawn gesetzt"); return false;
        }
        World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
        if (w == null) { p.sendMessage(Vars.PR + "\u00a7cSpawnwelt nicht gefunden"); return false; }
        double x = Configs.worldsConfig.getDouble("Spawn.X");
        double y = Configs.worldsConfig.getDouble("Spawn.Y");
        double z = Configs.worldsConfig.getDouble("Spawn.Z");
        target.teleport(new Location(w, x, y, z));
        target.sendMessage(Vars.PR + "\u00a7cDu wurdest aus der Welt gekickt");
        p.sendMessage(Vars.PR + "\u00a7e" + target.getName() + " \u00a7awurde aus deiner Welt gekickt");
        return false;
    }
}
