package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Vars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class CMDtpa implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;

        if (args.length < 1) {
            p.sendMessage(Vars.PREFIX + "§cWem willst du eine Anfrage schicken?");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage(Vars.PREFIX + "§e" + args[0] + " §cist nicht online");
            return false;
        }

        if (target.equals(p)) {
            p.sendMessage(Vars.PREFIX + "§cDu bist doch schon bei dir");
            return false;
        }

        String targetId = target.getUniqueId().toString();
        String senderId  = p.getUniqueId().toString();

        if (senderId.equals(Vars.tpa.get(targetId))) {
            p.sendMessage(Vars.PREFIX + "§cDu hast §e" + target.getName() + " §cbereits eine Anfrage gesendet");
            return false;
        }

        Vars.tpa.put(targetId, senderId);
        p.sendMessage(Vars.PREFIX + "§6Du hast §e" + target.getName() + " §6eine Anfrage gesendet");
        target.sendMessage(Vars.PREFIX + "§e" + p.getName() + " §6möchte sich zu dir teleportieren");
        target.sendMessage(Vars.PREFIX + "§6Die Anfrage ist §e30 Sekunden §6lang gültig");

        // Clickable accept-message using the Adventure API (Paper / modern Spigot).
        // The prefix contains legacy section-sign codes and is deserialized accordingly.
        Component acceptMsg = LegacyComponentSerializer.legacySection()
                .deserialize(Vars.PREFIX + "§7Klicke hier zum Annehmen§8: ")
                .append(Component.text("[ANNEHMEN]")
                        .color(NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/tpaccept " + p.getName()))
                        .hoverEvent(HoverEvent.showText(
                                Component.text("Klicken zum Annehmen").color(NamedTextColor.GREEN))));
        target.sendMessage(acceptMsg);

        // Expire the request after 30 seconds
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            if (senderId.equals(Vars.tpa.get(targetId))) {
                Vars.tpa.remove(targetId);
                if (target.isOnline()) target.sendMessage(Vars.PREFIX + "§cDie Anfrage von §e" + p.getName() + " §cist abgelaufen");
                if (p.isOnline())      p.sendMessage(Vars.PREFIX + "§cDeine Anfrage an §e" + target.getName() + " §cist abgelaufen");
            }
        }, 20L * 30);

        return false;
    }
}
