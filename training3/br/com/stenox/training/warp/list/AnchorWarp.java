// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.warp.list;

import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import br.com.stenox.training.utils.ItemCreator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import br.com.stenox.training.Main;
import br.com.stenox.training.warp.Warp;

public class AnchorWarp extends Warp
{
    private boolean cocoaInv;
    
    public AnchorWarp() {
        this.setName("Anchor");
        this.setDefaultKit(Main.getInstance().getKitManager().searchKit("Anchor"));
        this.setPvp(true);
        this.setSpawn(new Location((World)Bukkit.getWorlds().get(0), Main.getInstance().getConfig().getInt("AnchorX"), Main.getInstance().getConfig().getInt("AnchorY"), Main.getInstance().getConfig().getInt("AnchorZ")));
    }
    
    public void joinPlayer(final Gamer gamer, final boolean cocoaInv) {
        gamer.joinWarp(this);
        final Player player = gamer.getPlayer();
        player.teleport(this.getSpawn());
        this.addPlayer(player);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        int[] chestplaties;
        if (cocoaInv) {
            chestplaties = new int[] { 8, 9, 18, 27, 29, 31, 33 };
        }
        else {
            chestplaties = new int[] { 8, 9, 18, 27, 29 };
        }
        int[] leggings;
        if (cocoaInv) {
            leggings = new int[] { 7, 10, 19, 28, 30, 32, 34 };
        }
        else {
            leggings = new int[] { 7, 10, 19, 28, 30 };
        }
        final int[] helmets = { 5, 12, 21 };
        int[] boots;
        if (cocoaInv) {
            boots = new int[] { 6, 11, 20 };
        }
        else {
            boots = new int[] { 6, 11, 20, 31 };
        }
        final int[] soups = { 1, 2, 3, 4 };
        for (final int i : chestplaties) {
            player.getInventory().setItem(i, new ItemStack(Material.IRON_CHESTPLATE));
        }
        for (final int i : leggings) {
            player.getInventory().setItem(i, new ItemStack(Material.IRON_LEGGINGS));
        }
        for (final int i : boots) {
            player.getInventory().setItem(i, new ItemStack(Material.IRON_BOOTS));
        }
        for (final int i : helmets) {
            player.getInventory().setItem(i, new ItemStack(Material.IRON_HELMET));
        }
        for (final int i : soups) {
            player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
        }
        player.getInventory().setItem(13, new ItemStack(Material.BOWL, 64));
        player.getInventory().setItem(22, new ItemStack(Material.BOWL, 64));
        player.getInventory().setItem(0, new ItemCreator().setMaterial(Material.DIAMOND_SWORD).setEnchant(Enchantment.DAMAGE_ALL, 3).getStack());
        if (cocoaInv) {
            for (int j = 0; j <= 9; ++j) {
                player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.getMaterial(351), 64, (short)3) });
            }
        }
        else {
            final int[] redMushroom = { 14, 16, 23, 25, 32, 34 };
            final int[] brownMushroom = { 15, 17, 24, 26, 33, 35 };
            for (final int k : redMushroom) {
                player.getInventory().setItem(k, new ItemStack(Material.RED_MUSHROOM, 64));
            }
            for (final int k : brownMushroom) {
                player.getInventory().setItem(k, new ItemStack(Material.BROWN_MUSHROOM, 64));
            }
        }
    }
}
