// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;

public class DamageListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamageWoodSword(final EntityDamageByEntityEvent event) {
        if (event.isCancelled() || !(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }
        final Player p = (Player)event.getDamager();
        final ItemStack item = ((Player)event.getDamager()).getItemInHand();
        if (item != null) {
            if (item.getType().equals((Object)Material.MUSHROOM_SOUP)) {
                event.setDamage(1.0);
            }
            else if (item.getType().equals((Object)Material.WOOD_SWORD)) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().equals((Object)Material.STONE_SWORD)) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 4.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().equals((Object)Material.IRON_SWORD)) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 4.0) : (event.getDamage() - 1.5));
            }
            else if (item.getType().equals((Object)Material.DIAMOND_SWORD)) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 6.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().equals((Object)Material.GOLD_SWORD)) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 1.0));
            }
            else if (item.getType().name().contains("WOOD_AXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().name().contains("STONE_AXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().name().contains("IRON_AXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().name().contains("DIAMOND_AXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().name().contains("GOLD_AXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().name().contains("WOOD_PICKAXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().name().contains("STONE_PICKAXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().name().contains("IRON_PICKAXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().name().contains("DIAMOND_PICKAXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().name().contains("GOLD_PICKAXE")) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().equals((Object)Material.IRON_SPADE)) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 4.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().equals((Object)Material.DIAMOND_SPADE)) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 4.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().equals((Object)Material.DIAMOND_HOE)) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
            else if (item.getType().equals((Object)Material.IRON_HOE)) {
                event.setDamage(isCritical((LivingEntity)p) ? (event.getDamage() - 3.0) : (event.getDamage() - 2.0));
            }
        }
        if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
            for (final PotionEffect effect : p.getActivePotionEffects()) {
                if (effect.getType().equals((Object)PotionEffectType.INCREASE_DAMAGE)) {
                    final int level = effect.getAmplifier() + 1;
                    final double newDamage = event.getDamage(EntityDamageEvent.DamageModifier.BASE) / (level * 1.5 + 1.0) * level;
                    final double damagePercent = newDamage / event.getDamage(EntityDamageEvent.DamageModifier.BASE);
                    for (final EntityDamageEvent.DamageModifier dm : new EntityDamageEvent.DamageModifier[] { EntityDamageEvent.DamageModifier.ARMOR, EntityDamageEvent.DamageModifier.MAGIC, EntityDamageEvent.DamageModifier.RESISTANCE, EntityDamageEvent.DamageModifier.BLOCKING }) {
                        if (event.isApplicable(dm)) {
                            event.setDamage(dm, event.getDamage(dm) * damagePercent);
                        }
                    }
                    event.setDamage(EntityDamageEvent.DamageModifier.BASE, newDamage);
                }
            }
        }
    }
    
    public static boolean isCritical(final LivingEntity e) {
        return !e.isOnGround() && e.getFallDistance() > 0.0;
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onInstantDamge(final EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
            e.setDamage(e.getDamage() / 2.0);
        }
        else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            e.setDamage(3.0);
        }
        else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            e.setDamage(7.0);
        }
        else if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            e.setDamage(3.0);
        }
        else if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            e.setDamage(2.0);
        }
    }
}
