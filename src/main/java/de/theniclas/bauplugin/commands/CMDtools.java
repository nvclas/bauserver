package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CMDtools implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tools")) {
            if (sender instanceof Player p) {
                if (p.hasPermission("bs.tools")) {
                    Inventory inv = Bukkit.createInventory(null, 9, "§6§lTools");
                    inv.addItem(new ItemStack(Material.WOODEN_AXE));
                    inv.addItem(new ItemStack(Material.STICK));
                    inv.addItem(new ItemStack(Material.BARRIER));
                    inv.addItem(new ItemStack(Material.WOODEN_PICKAXE));
                    inv.addItem(new ItemStack(Material.WOODEN_SHOVEL));
                    inv.addItem(new ItemStack(Material.FLINT));
                    inv.addItem(new ItemStack(Material.GUNPOWDER));
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

