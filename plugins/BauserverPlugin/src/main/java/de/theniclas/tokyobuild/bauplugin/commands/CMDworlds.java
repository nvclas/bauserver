package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.InventoryCreator;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDworlds implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("worlds")) {
            if (sender instanceof Player p) {
                if (p.hasPermission("bs.worlds")) {
                    if (!Vars.voidWorldName.contains(p) && !Vars.flatWorldName.contains(p) && !Vars.normalWorldName.contains(
                            p)) {
                        InventoryCreator.currentPage.put(p, 1);
                        InventoryCreator.openWorldInventory(p);
                    } else {
                        p.sendMessage(Vars.pr + "§cDu bist bereits dabei eine Welt zu erstellen");
                        p.sendMessage(Vars.pr + "§cGib \"stop\" oder ähnliche Begriffe zum Abbruch in den Chat ein");
                    }
                } else {
                    p.sendMessage(Vars.noperm);
                }
            }
        }
        return false;
    }
}
