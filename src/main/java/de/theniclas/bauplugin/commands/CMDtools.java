package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.theniclas.bauplugin.utils.InvHolder;
import de.theniclas.bauplugin.utils.Vars;

public class CMDtools implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.tools")) { p.sendMessage(Vars.NOPERM); return false; }
        Inventory inv = Bukkit.createInventory(new InvHolder("tools"), 9,
                net.kyori.adventure.text.Component.text("§6§lTools"));
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
        return false;
    }
}
