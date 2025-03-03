package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDwkick implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("wkick")) {
            if (sender instanceof Player p) {
                if (p.hasPermission("bs.worlds")) {
                    if (Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
                        if (args.length >= 1) {
                            Player target = Bukkit.getPlayer(args[0]);
                            if (target != null) {
                                if (!target.hasPermission("bs.admin")) {
                                    World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
                                    double x = Configs.worldsConfig.getDouble("Spawn.X");
                                    double y = Configs.worldsConfig.getDouble("Spawn.Y");
                                    double z = Configs.worldsConfig.getDouble("Spawn.Z");
                                    Location spawn = new Location(w, x, y, z);
                                    target.teleport(spawn);
                                    target.sendMessage(Vars.pr + "§cDu wurdest aus der Welt gekickt");
                                    p.sendMessage(Vars.pr + "§e" + target.getName() + " §awurde aus deiner Welt gekickt");
                                } else {
                                    p.sendMessage(Vars.pr + "§cDiesen Spieler kannst du nicht kicken");
                                }
                            } else {
                                p.sendMessage(Vars.pr + "§cDieser Spieler ist nicht online");
                            }
                        } else {
                            p.sendMessage(Vars.pr + "§cWen willst du kicken?");
                        }
                    } else {
                        p.sendMessage(Vars.pr + "§cDas hier ist gar nicht deine Welt");
                    }
                } else {
                    p.sendMessage(Vars.noperm);
                }
            }
        }
        return false;
    }

}