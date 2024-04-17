package com.github.games647.changeskin.bukkit;


import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class JsonMessage {
    public static void send(Player target, String rawmsg) {
        TextComponent component = new TextComponent("");
        String msg = rawmsg.replace("&", "§");
        if (!msg.contains("/-/")) {
            if (msg.contains(": ttp>")) {
                String[] rawtext = msg.split(" : ");
                TextComponent action = new TextComponent(rawtext[0]);
                if (msg.contains(": exe>")) {
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rawtext[2].replace("exe>", "")));
                    component.addExtra((BaseComponent)action);
                } else if (msg.contains(": sgt>")) {
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rawtext[2].replace("sgt>", "")));
                    component.addExtra((BaseComponent)action);
                } else if (msg.contains(": url>")) {
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, rawtext[2].replace("url>", "")));
                    component.addExtra((BaseComponent)action);
                } else {
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    component.addExtra((BaseComponent)action);
                }
            } else if (msg.contains(": exe>")) {
                String[] rawtext = msg.split(" : ");
                TextComponent action = new TextComponent(rawtext[0]);
                action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rawtext[1].replace("exe>", "")));
                component.addExtra((BaseComponent)action);
            } else if (msg.contains(": sgt>")) {
                String[] rawtext = msg.split(" : ");
                TextComponent action = new TextComponent(rawtext[0]);
                action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rawtext[1].replace("sgt>", "")));
                component.addExtra((BaseComponent)action);
            } else if (msg.contains(": url>")) {
                String[] rawtext = msg.split(" : ");
                TextComponent action = new TextComponent(rawtext[0]);
                action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, rawtext[1].replace("url>", "")));
                component.addExtra((BaseComponent)action);
            } else {
                for (BaseComponent components : TextComponent.fromLegacyText(msg))
                    component.addExtra(components);
            }
        } else {
            for (String message : msg.split("/-/")) {
                if (message.contains(": ttp>")) {
                    String[] rawtext = message.split(" : ");
                    TextComponent action = new TextComponent(rawtext[0]);
                    if (message.contains(": exe>")) {
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                        action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rawtext[2].replace("exe>", "")));
                        component.addExtra((BaseComponent)action);
                    } else if (message.contains(": sgt>")) {
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                        action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rawtext[2].replace("sgt>", "")));
                        component.addExtra((BaseComponent)action);
                    } else if (message.contains(": url>")) {
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                        action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, rawtext[2].replace("url>", "")));
                        component.addExtra((BaseComponent)action);
                    } else {
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                        component.addExtra((BaseComponent)action);
                    }
                } else if (message.contains(": exe>")) {
                    String[] rawtext = message.split(" : ");
                    TextComponent action = new TextComponent(rawtext[0]);
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rawtext[1].replace("exe>", "")));
                    component.addExtra((BaseComponent)action);
                } else if (message.contains(": sgt>")) {
                    String[] rawtext = message.split(" : ");
                    TextComponent action = new TextComponent(rawtext[0]);
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rawtext[1].replace("sgt>", "")));
                    component.addExtra((BaseComponent)action);
                } else if (message.contains(": url>")) {
                    String[] rawtext = message.split(" : ");
                    TextComponent action = new TextComponent(rawtext[0]);
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, rawtext[1].replace("url>", "")));
                    component.addExtra((BaseComponent)action);
                } else {
                    for (BaseComponent components : TextComponent.fromLegacyText(message))
                        component.addExtra(components);
                }
            }
        }
        target.spigot().sendMessage((BaseComponent)component);
    }

    public static TextComponent deserialize(Player target, String rawmsg) {
        TextComponent component = new TextComponent("");
        String msg = rawmsg.replace("&", "§");
        if (!msg.contains("/-/")) {
            if (msg.contains(": ttp>")) {
                String[] rawtext = msg.split(" : ");
                TextComponent action = new TextComponent(rawtext[0]);
                if (msg.contains(": exe>")) {
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rawtext[2].replace("exe>", "")));
                    component.addExtra((BaseComponent)action);
                } else if (msg.contains(": sgt>")) {
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rawtext[2].replace("sgt>", "")));
                    component.addExtra((BaseComponent)action);
                } else if (msg.contains(": url>")) {
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, rawtext[2].replace("url>", "")));
                    component.addExtra((BaseComponent)action);
                } else {
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    component.addExtra((BaseComponent)action);
                }
            } else if (msg.contains(": exe>")) {
                String[] rawtext = msg.split(" : ");
                TextComponent action = new TextComponent(rawtext[0]);
                action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rawtext[1].replace("exe>", "")));
                component.addExtra((BaseComponent)action);
            } else if (msg.contains(": sgt>")) {
                String[] rawtext = msg.split(" : ");
                TextComponent action = new TextComponent(rawtext[0]);
                action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rawtext[1].replace("sgt>", "")));
                component.addExtra((BaseComponent)action);
            } else if (msg.contains(": url>")) {
                String[] rawtext = msg.split(" : ");
                TextComponent action = new TextComponent(rawtext[0]);
                action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, rawtext[1].replace("url>", "")));
                component.addExtra((BaseComponent)action);
            } else {
                for (BaseComponent components : TextComponent.fromLegacyText(msg))
                    component.addExtra(components);
            }
        } else {
            for (String message : msg.split("/-/")) {
                if (message.contains(": ttp>")) {
                    String[] rawtext = message.split(" : ");
                    TextComponent action = new TextComponent(rawtext[0]);
                    if (message.contains(": exe>")) {
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                        action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rawtext[2].replace("exe>", "")));
                        component.addExtra((BaseComponent)action);
                    } else if (message.contains(": sgt>")) {
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                        action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rawtext[2].replace("sgt>", "")));
                        component.addExtra((BaseComponent)action);
                    } else if (message.contains(": url>")) {
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                        action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, rawtext[2].replace("url>", "")));
                        component.addExtra((BaseComponent)action);
                    } else {
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                        component.addExtra((BaseComponent)action);
                    }
                } else if (message.contains(": exe>")) {
                    String[] rawtext = message.split(" : ");
                    TextComponent action = new TextComponent(rawtext[0]);
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rawtext[1].replace("exe>", "")));
                    component.addExtra((BaseComponent)action);
                } else if (message.contains(": sgt>")) {
                    String[] rawtext = message.split(" : ");
                    TextComponent action = new TextComponent(rawtext[0]);
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rawtext[1].replace("sgt>", "")));
                    component.addExtra((BaseComponent)action);
                } else if (message.contains(": url>")) {
                    String[] rawtext = message.split(" : ");
                    TextComponent action = new TextComponent(rawtext[0]);
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, rawtext[1].replace("url>", "")));
                    component.addExtra((BaseComponent)action);
                } else {
                    for (BaseComponent components : TextComponent.fromLegacyText(message))
                        component.addExtra(components);
                }
            }
        }
        return component;
    }
}

