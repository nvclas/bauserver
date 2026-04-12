package de.theniclas.bauplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;
import de.theniclas.bauplugin.utils.WorldMaker;

@SuppressWarnings("deprecation") // AsyncPlayerChatEvent is soft-deprecated on Paper; kept for Spigot compatibility
public class AsyncPlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        boolean awaitingName = Vars.voidWorldName.contains(p)
                || Vars.flatWorldName.contains(p)
                || Vars.normalWorldName.contains(p);
        if (!awaitingName) return;

        e.setCancelled(true);
        String message = e.getMessage();
        ItemStack icon = p.getInventory().getItemInHand();

        if (message.equalsIgnoreCase("abbrechen") || message.equalsIgnoreCase("abbruch")
                || message.equalsIgnoreCase("stop") || message.equalsIgnoreCase("stopp")) {
            Vars.voidWorldName.remove(p);
            Vars.flatWorldName.remove(p);
            Vars.normalWorldName.remove(p);
            p.sendMessage(Vars.PREFIX + "§aWeltenerstellung abgebrochen");
            return;
        }

        if (!message.matches("[a-zA-Z0-9]+") || message.length() > 16) {
            p.sendMessage(Vars.PREFIX + "§cDer Weltenname darf maximal 16 Zeichen besitzen und keine Leerzeichen oder unerlaubte Symbole enthalten");
            return;
        }

        var worldsSection = Configs.worldsConfig.getConfigurationSection("Worlds");
        if (worldsSection != null && worldsSection.getKeys(false).contains(message)) {
            p.sendMessage(Vars.PREFIX + "§cEs existiert bereits eine Welt mit diesem Namen");
            return;
        }

        if (icon == null || icon.getType() == Material.AIR) {
            p.sendMessage(Vars.PREFIX + "§cBitte halte ein Item für das Welticon in der Hand");
            return;
        }

        // World creation must run on the main thread
        if (Vars.voidWorldName.contains(p)) {
            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> WorldMaker.createVoidWorld(message, p, icon));
        } else if (Vars.flatWorldName.contains(p)) {
            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> WorldMaker.createFlatWorld(message, p, icon));
        } else if (Vars.normalWorldName.contains(p)) {
            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> WorldMaker.createNormalWorld(message, p, icon));
        }
    }
}
