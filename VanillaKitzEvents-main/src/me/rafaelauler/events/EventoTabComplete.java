package me.rafaelauler.events;




import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventoTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("event")) {
            if (args[0].equalsIgnoreCase("explicar") || args[0].equalsIgnoreCase("tpto")) {
                if (args[1].length() == 0) {
                    for (EventType evento : EventType.values()) {
                        list.add(evento.getName().toLowerCase());
                    }
                    return list;
                } else {
                    for (EventType evento : EventType.values()) {
                        if (evento.getName().substring(0, args[1].length()).equalsIgnoreCase(args[1])) {
                            return Collections.singletonList(evento.getName().toLowerCase());
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("effect")) {
                list.add("clear");
                if (args[1].length() == 0) {
                    for (PotionEffectType effect : PotionEffectType.values()) {
                        if (effect == null) continue;
                        list.add(effect.getName().toLowerCase());
                    }
                } else {
                    for (PotionEffectType effect : PotionEffectType.values()) {
                        if (effect == null) continue;
                        if (effect.getName().substring(0, args[1].length()).equalsIgnoreCase(args[1])) {
                            list.add(effect.getName().toLowerCase());
                        }
                    }
                }
                return list;
            }
        }
        return null;
    }
}