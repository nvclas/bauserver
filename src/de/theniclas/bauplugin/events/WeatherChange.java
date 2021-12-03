package de.theniclas.bauplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import de.theniclas.bauplugin.utils.Configs;

public class WeatherChange implements Listener {
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		
		if(Configs.worldsConfig.getBoolean("Worlds." + e.getWorld().getName().replaceAll("worlds/", "") + ".Properties.Weather") != true) {
			e.setCancelled(true);
		}
	}
}
