// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.kit;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.Bukkit;
import br.com.stenox.training.event.custom.TimeSecondEvent;
import br.com.stenox.training.gamer.Gamer;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.event.Listener;

public class Kit implements Listener
{
    private String name;
    private double cooldownSeconds;
    private double combatCooldownSeconds;
    private final ConcurrentHashMap<UUID, Long> cooldown;
    private final ConcurrentHashMap<UUID, Long> combatCooldown;
    private final DecimalFormat FORMATTER;
    
    public Kit() {
        this.cooldown = new ConcurrentHashMap<UUID, Long>();
        this.combatCooldown = new ConcurrentHashMap<UUID, Long>();
        this.FORMATTER = new DecimalFormat("#.#");
    }
    
    public boolean hasKit(final String playerName) {
        final Gamer gamer = Gamer.getGamer(playerName);
        return gamer.getKitName().equalsIgnoreCase(this.getName());
    }
    
    @EventHandler
    public void onTimeSecond(final TimeSecondEvent event) {
        if (event.getType() == TimeSecondEvent.TimeType.MILLISECONDS) {
            for (final UUID uuid : this.cooldown.keySet()) {
                final double seconds = this.cooldown.get(uuid) / 1000.0 + this.cooldownSeconds - System.currentTimeMillis() / 1000.0;
                if (seconds <= 0.1) {
                    this.cooldown.remove(uuid);
                    final Player player = Bukkit.getPlayer(uuid);
                    if (player == null) {
                        continue;
                    }
                    player.sendMessage("§aVoc\u00ea j\u00e1 pode usar seu kit novamente.");
                }
            }
            for (final UUID uuid : this.combatCooldown.keySet()) {
                final double seconds = this.combatCooldown.get(uuid) / 1000.0 + this.combatCooldownSeconds - System.currentTimeMillis() / 1000.0;
                if (seconds <= 0.1) {
                    this.combatCooldown.remove(uuid);
                }
            }
        }
    }
    
    public long getCooldown(final Player player) {
        return this.cooldown.containsKey(player.getUniqueId()) ? 0L : this.cooldown.get(player.getUniqueId());
    }
    
    public long getCombatCooldown(final Player player) {
        return this.combatCooldown.containsKey(player.getUniqueId()) ? 0L : this.combatCooldown.get(player.getUniqueId());
    }
    
    public void setCooldownSeconds(final double cooldownSeconds) {
        this.cooldownSeconds = cooldownSeconds;
    }
    
    public void setCombatCooldownSeconds(final double combatCooldownSeconds) {
        this.combatCooldownSeconds = combatCooldownSeconds;
    }
    
    public void addCooldown(final Player player) {
        this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
    }
    
    public void addCombatCooldown(final Player player, final double seconds) {
        this.combatCooldown.put(player.getUniqueId(), System.currentTimeMillis());
        this.combatCooldownSeconds = seconds;
    }
    
    public void addCooldown(final Player player, final double seconds) {
        this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
        this.cooldownSeconds = seconds;
    }
    
    public void sendCooldown(final Player player) {
        final double seconds = this.cooldown.get(player.getUniqueId()) / 1000.0 + this.cooldownSeconds - System.currentTimeMillis() / 1000.0;
        player.sendMessage("§cVoc\u00ea est\u00e1 em cooldown, aguarde " + this.FORMATTER.format(seconds) + " segundos.");
    }
    
    public void sendCombatCooldown(final Player player) {
        final double seconds = this.combatCooldown.get(player.getUniqueId()) / 1000.0 + this.combatCooldownSeconds - System.currentTimeMillis() / 1000.0;
        player.sendMessage("§cVoc\u00ea est\u00e1 em combate, aguarde " + this.FORMATTER.format(seconds) + "s.");
    }
    
    public boolean inCooldown(final Player player) {
        return this.cooldown.containsKey(player.getUniqueId());
    }
    
    public boolean inCombatCooldown(final Player player) {
        return this.combatCooldown.containsKey(player.getUniqueId());
    }
    
    public void removeCooldown(final Player player) {
        this.cooldown.remove(player.getUniqueId());
    }
    
    public void removeCombatCooldown(final Player player) {
        this.combatCooldown.remove(player.getUniqueId());
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getCooldownSeconds() {
        return this.cooldownSeconds;
    }
    
    public double getCombatCooldownSeconds() {
        return this.combatCooldownSeconds;
    }
    
    public ConcurrentHashMap<UUID, Long> getCooldown() {
        return this.cooldown;
    }
    
    public ConcurrentHashMap<UUID, Long> getCombatCooldown() {
        return this.combatCooldown;
    }
    
    public DecimalFormat getFORMATTER() {
        return this.FORMATTER;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
