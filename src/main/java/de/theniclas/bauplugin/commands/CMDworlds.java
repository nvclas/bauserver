package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.Bauserver;
import de.theniclas.bauplugin.utils.Vars;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CMDworlds implements CommandExecutor {

    private final Bauserver plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p))
            return false;
        if (!p.hasPermission("bs.worlds")) {
            p.sendMessage(Vars.NOPERM);
            return false;
        }
        if (Vars.voidWorldName.contains(p) || Vars.flatWorldName.contains(p) || Vars.normalWorldName.contains(p)) {
            p.sendMessage(Vars.prefixed("<red>Du bist bereits dabei eine Welt zu erstellen"));
            p.sendMessage(Vars.prefixed("<red>Gib \"stop\" zum Abbruch in den Chat ein"));
            return false;
        }
        plugin.getInventoryCreator().setCurrentPage(p, 1);
        plugin.getInventoryCreator().openWorldInventory(p);
        return false;
    }
}
