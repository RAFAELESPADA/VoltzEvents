// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command;

import br.com.stenox.training.Main;
import org.bukkit.inventory.ItemStack;
import br.com.stenox.training.warp.Warp;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class SpawnCommand extends Command
{
    public SpawnCommand() {
        super("spawn");
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
            final Gamer gamer = Gamer.getGamer(sender.getName());
            if (((Player)sender).hasMetadata("combatPlayer")) {
                sender.sendMessage("§cVoc\u00ea est\u00e1 em combate.");
                return false;
            }
            if (gamer.getWarp() == null) {
                sender.sendMessage("§cVoc\u00ea j\u00e1 est\u00e1 no spawn.");
                return false;
            }
            gamer.getWarp().removePlayer(gamer.getPlayer());
            gamer.setWarp(null);
            gamer.getPlayer().getInventory().clear();
            gamer.getPlayer().getInventory().setArmorContents((ItemStack[])null);
            gamer.getPlayer().setHealth(20.0);
            gamer.getPlayer().teleport(Main.SPAWN);
        }
        return false;
    }
}
