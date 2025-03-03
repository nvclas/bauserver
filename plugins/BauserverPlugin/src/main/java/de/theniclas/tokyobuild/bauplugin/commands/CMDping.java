package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDping implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ping") && sender instanceof Player p) {
                int ping = p.getPing();
                if (ping <= 40) {
                    p.sendMessage(Vars.pr + "§eDein Ping beträgt §a" + ping + "ms");
                } else if (ping >= 80) {
                    p.sendMessage(Vars.pr + "§eDein Ping beträgt §c" + ping + "ms");
                } else {
                    p.sendMessage(Vars.pr + "§eDein Ping beträgt §6" + ping + "ms");
                }
            }

        return false;
    }

}
