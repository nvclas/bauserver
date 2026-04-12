package de.theniclas.bauplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
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
            e.setJoinMessage("§9" + p.getName() + " §7ist neu beigetreten");
            p.sendMessage(Vars.PREFIX + "§aJoine auf unseren Discord, um dich freizuschalten!");
            p.sendMessage(Vars.PREFIX + "§aKlicke hier: §ehttps://discord.gg/FjHUFJe6ny");
        } else {
            e.setJoinMessage("§9" + p.getName() + " §7hat den Server betreten");
        }

        p.setFoodLevel(20);
        double maxHealth = p.getAttribute(Attribute.MAX_HEALTH).getValue();
        p.setHealth(maxHealth);

        if (Configs.worldsConfig.getConfigurationSection("Spawn") == null) return;

        World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
        if (w == null) return;
        double x = Configs.worldsConfig.getDouble("Spawn.X");
        double y = Configs.worldsConfig.getDouble("Spawn.Y");
        double z = Configs.worldsConfig.getDouble("Spawn.Z");
        p.teleport(new Location(w, x, y, z));
    }
}
