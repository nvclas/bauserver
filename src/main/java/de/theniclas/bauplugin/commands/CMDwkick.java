package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.Bauserver;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.BauserverConfig;
import de.theniclas.bauplugin.utils.Vars;

@RequiredArgsConstructor
public class CMDwkick implements CommandExecutor {

    private final Bauserver plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PR + "§cDas hier ist gar nicht deine Welt"); return false;
        }
        if (args.length < 1) { p.sendMessage(Vars.PR + "§cWen willst du kicken?"); return false; }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { p.sendMessage(Vars.PR + "§cDieser Spieler ist nicht online"); return false; }
        if (target.hasPermission("bs.admin")) { p.sendMessage(Vars.PR + "§cDiesen Spieler kannst du nicht kicken"); return false; }
        if (plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection("Spawn") == null) {
            p.sendMessage(Vars.PR + "§cKein globaler Spawn gesetzt"); return false;
        }
        World w = Bukkit.getWorld(plugin.getBauserverConfig().getWorldsConfig().getString("Spawn.World"));
        if (w == null) { p.sendMessage(Vars.PR + "§cSpawnwelt nicht gefunden"); return false; }
        double x = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.X");
        double y = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.Y");
        double z = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.Z");
        target.teleport(new Location(w, x, y, z));
        target.sendMessage(Vars.PR + "§cDu wurdest aus der Welt gekickt");
        p.sendMessage(Vars.PR + "§e" + target.getName() + " §awurde aus deiner Welt gekickt");
        return false;
    }
}
