package de.theniclas.bauplugin.events;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPlayedBefore()) {
            e.joinMessage(LegacyComponentSerializer.legacySection().deserialize(
                    "\u00a79" + p.getName() + " \u00a77ist neu beigetreten"));
            p.sendMessage(Vars.PR + "\u00a7aJoine auf unseren Discord, um dich freizuschalten!");
        } else {
            e.joinMessage(LegacyComponentSerializer.legacySection().deserialize(
                    "\u00a79" + p.getName() + " \u00a77hat den Server betreten"));
        }

        p.setFoodLevel(20);
        p.setHealth(20);

        if (Configs.worldsConfig.getConfigurationSection("Spawn") == null) return;
        World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
        if (w == null) return;
        double x = Configs.worldsConfig.getDouble("Spawn.X");
        double y = Configs.worldsConfig.getDouble("Spawn.Y");
        double z = Configs.worldsConfig.getDouble("Spawn.Z");
        p.teleport(new Location(w, x, y, z));
    }
}
