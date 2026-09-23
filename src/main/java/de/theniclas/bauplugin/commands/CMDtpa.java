package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.Bauserver;
import de.theniclas.bauplugin.utils.Vars;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CMDtpa implements CommandExecutor {

    private final Bauserver plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!(sender instanceof Player p))
            return false;
        if (args.length < 1) {
            p.sendMessage(Vars.prefixed("<red>Wem willst du eine Anfrage schicken?"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage(Vars.prefixed("<yellow>" + args[0] + " <red>ist nicht online"));
            return true;
        }
        if (target.getName().equals(p.getName())) {
            p.sendMessage(Vars.prefixed("<red>Du bist doch schon bei dir"));
            return true;
        }
        if (Vars.tpa.containsKey(target.getUniqueId().toString())
                && Vars.tpa.get(target.getUniqueId().toString()).equals(p.getUniqueId().toString())) {
            p.sendMessage(
                    Vars.prefixed("<red>Du hast <yellow>" + target.getName() + " <red>bereits eine Anfrage gesendet"));
            return true;
        }
        Vars.tpa.put(target.getUniqueId().toString(), p.getUniqueId().toString());
        p.sendMessage(Vars.prefixed("<gold>Du hast <yellow>" + target.getName() + " <gold>eine Anfrage gesendet"));
        target.sendMessage(Vars.prefixed("<yellow>" + p.getName() + " <gold>möchte sich zu dir teleportieren"));
        target.sendMessage(Vars.prefixed("<gold>Die Anfrage ist <yellow>30 Sekunden <gold>lang gültig"));
        target.sendMessage(Vars.prefix().append(Component.text("Klicke hier: ", NamedTextColor.GRAY))
                .append(Component.text("[ANNEHMEN]")
                        .color(NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/tpaccept " + p.getName()))));
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (Vars.tpa.containsKey(target.getUniqueId().toString())
                    && Vars.tpa.get(target.getUniqueId().toString()).equals(p.getUniqueId().toString())) {
                Vars.tpa.remove(target.getUniqueId().toString());
                target.sendMessage(
                        Vars.prefixed("<red>Die Anfrage von <yellow>" + p.getName() + " <red>ist abgelaufen"));
                if (p.isOnline())
                    p.sendMessage(Vars.prefixed(
                            "<red>Deine Anfrage an <yellow>" + target.getName() + " <red>ist abgelaufen"));
            }
        }, (long) 20 * 30);
        return true;
    }
}
