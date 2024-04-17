// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command;

import org.bukkit.plugin.Plugin;
import br.com.stenox.training.Main;
import java.util.function.Consumer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.Bukkit;
import org.bukkit.World;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class ClearDropsCommand extends Command
{
    public ClearDropsCommand() {
        super("cleardrops");
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
            final Gamer gamer = Gamer.getGamer(sender.getName());
            if (gamer.getGroup().ordinal() > Group.MOD.ordinal()) {
                return false;
            }
        }
        if (args.length == 0) {
            Bukkit.getWorlds().get(0).getEntities().stream().filter(entity -> entity instanceof Item).forEach(Entity::remove);
            Bukkit.broadcastMessage("§cOs itens do ch\u00e3o foram removidos.");
            return true;
        }
        if (!this.isInteger(args[0])) {
            sender.sendMessage("§cO tempo deve ser um n\u00famero.");
            return false;
        }
        final int time = Integer.parseInt(args[0]);
        if (time < 0) {
            return false;
        }
        Bukkit.broadcastMessage("§cOs itens do ch\u00e3o ser\u00e3o limpos em " + time + " segundos.");
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Main.getInstance(), () -> {
            Bukkit.getWorlds().get(0).getEntities().stream().filter(entity -> entity instanceof Item).forEach(entity -> entity.remove());
            Bukkit.broadcastMessage("§cOs itens do ch\u00e3o foram removidos.");
            return;
        }, time * 20L);
        return false;
    }
    
    public boolean isInteger(final String string) {
        try {
            Integer.parseInt(string);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
