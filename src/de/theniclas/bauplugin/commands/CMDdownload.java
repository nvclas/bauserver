package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

/** Placeholder for a future world-download feature. */
public class CMDdownload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NO_PERM); return false; }

        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PREFIX + "§cDu kannst nur deine eigenen Welten herunterladen");
        } else {
            p.sendMessage(Vars.PREFIX + "§cDiese Funktion ist noch nicht implementiert");
        }
        return false;
    }
}
