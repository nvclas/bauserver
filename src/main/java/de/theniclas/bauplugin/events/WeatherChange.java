package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.Bauserver;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import de.theniclas.bauplugin.utils.BauserverConfig;

@RequiredArgsConstructor
public class WeatherChange implements Listener {

    private final Bauserver plugin;

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (!plugin.getBauserverConfig().getWorldsConfig().getBoolean(
                "Worlds." + e.getWorld().getName().replace("worlds/", "") + ".Properties.Weather")) {
            e.setCancelled(true);
        }
    }
}
