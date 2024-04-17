// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.Listener;

public class MotdListener implements Listener
{
    @EventHandler
    public void onServerListPing(final ServerListPingEvent event) {
        event.setMotd("§6§lKONOHA - §eTraining Server\n§bdiscord.gg/konohaa");
    }
}
