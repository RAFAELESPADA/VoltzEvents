// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command;

import br.com.stenox.training.gamer.tag.Tag;
import br.com.stenox.training.Main;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class AddTagCommand extends Command
{
    public AddTagCommand() {
        super("addtag");
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
            final Gamer gamer = Gamer.getGamer(sender.getName());
            if (gamer.getGroup().ordinal() > Group.ADMIN.ordinal()) {
                return false;
            }
        }
        if (args.length != 2) {
            sender.sendMessage("§cUsage: /addtag [player] [tag]");
            return false;
        }
        final Gamer target = Main.getInstance().getGamerController().searchOrFind(args[0]);
        if (target == null) {
            sender.sendMessage("§cJogador n\u00e3o encontrado.");
            return false;
        }
        final Tag tag = Tag.fromString(args[1]);
        if (tag == null) {
            sender.sendMessage("§cTag n\u00e3o encontrada.");
            return false;
        }
        if (sender instanceof Player) {
            final Gamer gamer2 = Gamer.getGamer(sender.getName());
            if (tag.equals(Tag.OWNER) && gamer2.getGroup().equals(Group.ADMIN)) {
                sender.sendMessage("§cA tag n\u00e3o pode ser maior que a sua.");
                return false;
            }
        }
        target.addTag(tag);
        if (target.getPlayer() == null) {
            Main.getInstance().getGamerController().update(target);
        }
        else {
            target.updateTag();
        }
        sender.sendMessage("§aTag adicionada com sucesso.");
        return false;
    }
}
