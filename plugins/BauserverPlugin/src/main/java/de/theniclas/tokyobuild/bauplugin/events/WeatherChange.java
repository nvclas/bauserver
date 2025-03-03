package de.theniclas.tokyobuild.bauplugin.events;

import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange implements Listener {
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {

        if (!Configs.worldsConfig.getBoolean("Worlds." + e.getWorld()
                .getName()
                .replace("worlds/", "") + ".Properties.Weather")) {
            e.setCancelled(true);
        }
    }
}
