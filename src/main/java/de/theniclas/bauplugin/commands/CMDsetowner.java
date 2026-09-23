package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.Bauserver;
import de.theniclas.bauplugin.utils.Vars;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CMDsetowner implements CommandExecutor {

    private final Bauserver plugin;

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p))
            return false;
        if (!p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.NOPERM);
            return false;
        }
        if (args.length < 1) {
            p.sendMessage(Vars.prefixed("<red>Wer soll der neue Besitzer werden?"));
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            p.sendMessage(Vars.prefixed("<red>Dieser Spieler hat noch nie hier gespielt"));
            return false;
        }
        String worldKey = p.getWorld().getName().replace("worlds/", "");
        String currentOwner = plugin.getBauserverConfig().getWorldsConfig().getString("Worlds." + worldKey + ".Owner");
        if (target.getUniqueId().toString().equals(currentOwner)) {
            p.sendMessage(Vars.prefixed("<red>Dieser Spieler ist bereits der Besitzer dieser Welt"));
            return false;
        }
        plugin.getBauserverConfig()
                .getWorldsConfig()
                .set("Worlds." + worldKey + ".Owner", target.getUniqueId().toString());
        plugin.getBauserverConfig().saveConfiguration();
        p.sendMessage(Vars.prefixed("<green>Der neue Besitzer dieser Welt ist nun <yellow>" + args[0]));
        return false;
    }
}
