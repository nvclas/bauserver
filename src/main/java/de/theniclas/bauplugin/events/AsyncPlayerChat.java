package de.theniclas.bauplugin.events;

import io.papermc.paper.event.player.AsyncChatEvent;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;
import de.theniclas.bauplugin.utils.WorldMaker;

public class AsyncPlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player p = e.getPlayer();
        if (!Vars.voidWorldName.contains(p) && !Vars.flatWorldName.contains(p) && !Vars.normalWorldName.contains(p)) return;

        e.setCancelled(true);
        String message = PlainTextComponentSerializer.plainText().serialize(e.message());
        ItemStack icon = p.getInventory().getItemInMainHand();

        if (message.matches("[^a-zA-Z0-9]") || message.contains(" ") || message.contains("%")
                || message.contains("/") || message.length() > 16) {
            p.sendMessage(Vars.PR + "\u00a7cDer Weltenname darf maximal 16 Zeichen besitzen und keine Leerzeichen oder unerlaubte Symbole enthalten");
        } else if (message.equalsIgnoreCase("abbrechen") || message.equalsIgnoreCase("abbruch")
                || message.equalsIgnoreCase("stop") || message.equalsIgnoreCase("stopp")) {
            Vars.voidWorldName.remove(p);
            Vars.flatWorldName.remove(p);
            Vars.normalWorldName.remove(p);
            p.sendMessage(Vars.PR + "\u00a7aWeltenerstellung abgebrochen");
        } else if (Configs.worldsConfig.getConfigurationSection("Worlds") != null
                && Configs.worldsConfig.getConfigurationSection("Worlds").getKeys(false).contains(message)) {
            p.sendMessage(Vars.PR + "\u00a7cEs existiert bereits eine Welt mit diesem Namen");
        } else if (icon.getType() == Material.AIR) {
            p.sendMessage(Vars.PR + "\u00a7cBitte halte ein Item f\u00fcr das Welticon in der Hand");
        } else {
            if (Vars.voidWorldName.contains(p)) {
                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> WorldMaker.createVoidWorld(message, p, icon));
            } else if (Vars.flatWorldName.contains(p)) {
                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> WorldMaker.createFlatWorld(message, p, icon));
            } else if (Vars.normalWorldName.contains(p)) {
                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> WorldMaker.createNormalWorld(message, p, icon));
            }
        }
    }
}
