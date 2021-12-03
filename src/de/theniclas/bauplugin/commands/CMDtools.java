package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.theniclas.bauplugin.utils.Vars;

public class CMDtools implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("tools")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.tools")) {
					Inventory inv = Bukkit.createInventory(null, 9, "§6§lTools");
					inv.addItem(new ItemStack(Material.WOOD_AXE));
					inv.addItem(new ItemStack(Material.STICK));
					inv.addItem(new ItemStack(Material.BARRIER));
					inv.addItem(new ItemStack(Material.WOOD_PICKAXE));
					inv.addItem(new ItemStack(Material.WOOD_SPADE));
					inv.addItem(new ItemStack(Material.FLINT));
					inv.addItem(new ItemStack(Material.SULPHUR));
					inv.addItem(new ItemStack(Material.FEATHER));
					inv.addItem(new ItemStack(Material.COMPASS));
					p.openInventory(inv);
						
				} else {
					p.sendMessage(Vars.noperm);
				}
			}
		}
		return false;
	}

	
}

