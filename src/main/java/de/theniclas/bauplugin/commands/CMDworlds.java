package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.InventoryCreator;
import de.theniclas.bauplugin.utils.Vars;

public class CMDworlds implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (Vars.voidWorldName.contains(p) || Vars.flatWorldName.contains(p) || Vars.normalWorldName.contains(p)) {
            p.sendMessage(Vars.PR + "\u00a7cDu bist bereits dabei eine Welt zu erstellen");
            p.sendMessage(Vars.PR + "\u00a7cGib \"stop\" zum Abbruch in den Chat ein");
            return false;
        }
        InventoryCreator.currentPage.put(p, 1);
        InventoryCreator.openWorldInventory(p);
        return false;
    }
}
