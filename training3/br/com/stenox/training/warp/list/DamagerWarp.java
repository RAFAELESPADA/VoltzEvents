// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.warp.list;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import java.util.Random;
import br.com.stenox.training.Main;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import java.util.List;
import java.util.Map;
import org.bukkit.event.Listener;
import br.com.stenox.training.warp.Warp;

public class DamagerWarp extends Warp implements Listener
{
    private Map<String, Integer> damage;
    private List<String> randomDamage;
    private int[] damages;
    
    public DamagerWarp() {
        this.damages = new int[] { 4, 6, 8, 9 };
        this.setName("Damager");
        this.setPvp(false);
        this.setSpawn(new Location(Bukkit.getWorlds().get(0), Main.getInstance().getConfig().getInt("DamagerX"), Main.getInstance().getConfig().getInt("DamagerY"), Main.getInstance().getConfig().getInt("DamagerZ")));
        this.damage = new HashMap<String, Integer>();
        this.randomDamage = new ArrayList<String>();
       
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Main.getInstance(), () -> {
            this.getPlayers().iterator();
            Iterator<String> iterator = getPlayers().listIterator();
            while (iterator.hasNext()) {
            	
                String name = iterator.next();
                Player player = Bukkit.getPlayer(name);
                if (player == null) {
                    continue;
                }
                if (!(player.getLocation().getZ() > -137 && player.getLocation().getZ() < -109)) {
                	return;
                }           
                else if (this.damage.containsKey(player.getName())) {
                    player.damage((double)this.damage.get(player.getName()));
                }
                else if (this.randomDamage.contains(player.getName())) {
                    player.damage((double)this.damages[new Random().nextInt(4)]);
                }
                else {
                    continue;
                }
            }
            }, 0L, 1L);
    }
    
    public void joinPlayer(final Gamer gamer, final int damage, final boolean cocoaInv) {
        gamer.joinWarp(this);
        final Player player = gamer.getPlayer();
        player.teleport(this.getSpawn());
        this.addPlayer(player);
        final int[] bowl = { 9, 13, 17, 18, 22, 26, 27, 31, 35 };
        player.getInventory().clear();
        for (final int i : bowl) {
            player.getInventory().setItem(i, new ItemStack(Material.BOWL, 64));
        }
        for (int j = 0; j <= 8; ++j) {
            player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.MUSHROOM_SOUP) });
        }
        if (cocoaInv) {
            for (int j = 0; j < 18; ++j) {
                player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.getMaterial(351), 64, (short)3) });
            }
        }
        else {
            final int[] array2;
            final int[] redMushroom = array2 = new int[] { 10, 12, 14, 19, 23, 25, 28, 30, 32 };
            for (final int k : array2) {
                player.getInventory().setItem(k, new ItemStack(Material.RED_MUSHROOM, 64));
            }
            for (int l = 0; l <= 9; ++l) {
                player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.BROWN_MUSHROOM, 64) });
            }
        }
        player.updateInventory();
        if (damage == -1) {
            this.randomDamage.add(gamer.getName());
        }
        else {
            this.damage.put(gamer.getName(), damage);
        }
    }
    
    public void setDamage(final Map<String, Integer> damage) {
        this.damage = damage;
    }
    
    public void setRandomDamage(final List<String> randomDamage) {
        this.randomDamage = randomDamage;
    }
    
    public void setDamages(final int[] damages) {
        this.damages = damages;
    }
}
