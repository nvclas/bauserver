package de.theniclas.bauplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class WeatherChange implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        String worldKey = Vars.stripWorldsPrefix(e.getWorld().getName());
        boolean weatherAllowed = Configs.worldsConfig.getBoolean("Worlds." + worldKey + ".Properties.Weather");
        if (!weatherAllowed) {
            e.setCancelled(true);
        }
    }
}
