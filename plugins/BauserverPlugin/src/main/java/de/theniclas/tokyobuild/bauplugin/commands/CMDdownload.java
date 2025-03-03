package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDdownload implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("download") && sender instanceof Player p) {
            if (p.hasPermission("bs.worlds")) {
                if (Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
                    //upload file to web server
                } else p.sendMessage(Vars.pr + "§cDu kannst nur deine eigenen Welten herunterladen");
            } else p.sendMessage(Vars.noperm);
        }

        return false;
    }

}
