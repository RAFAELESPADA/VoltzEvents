// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils;

import net.md_5.bungee.api.ChatColor;
import java.util.ArrayList;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.Color;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemCreator
{
    private ItemStack itemStack;
    
    public ItemCreator(final Material material) {
        this.itemStack = new ItemStack(material);
    }
    
    public ItemCreator(final ItemStack stack) {
        this.itemStack = stack;
    }
    
    public ItemCreator() {
    }
    
    public ItemCreator setMaterial(final Material type) {
        this.itemStack = new ItemStack(type);
        return this;
    }
    
    public ItemCreator setFast(final Material type, final String name, final int data) {
        this.setMaterial(type);
        this.setName(name);
        this.setDurability(data);
        return this;
    }
    
    public ItemCreator setFast(final Material type, final String name) {
        this.setMaterial(type);
        this.setName(name);
        return this;
    }
    
    public ItemCreator setType(final Material type) {
        this.setMaterial(type);
        return this;
    }
    
    public ItemCreator setAmount(final int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }
    
    public ItemCreator setDurability(final int durability) {
        this.itemStack.setDurability((short)durability);
        return this;
    }
    
    public ItemCreator addItemFlag(final ItemFlag... flag) {
        final ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(flag);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    
    public ItemCreator setName(final String name) {
        final ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(name);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    
    public ItemCreator setDescription(final List<String> desc) {
        final ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore((List)desc);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    
    public ItemCreator setDescription(final String... desc) {
        this.setDescription(Arrays.asList(desc));
        return this;
    }
    
    public ItemCreator setDescription(final String text) {
        final List<String> lore = getFormattedLore(text);
        this.setDescription((String[])lore.toArray(new String[0]));
        return this;
    }
    
    public ItemCreator glow() {
        return this;
    }
    
    public ItemCreator setEnchant(final Enchantment[] enchant, final int[] level) {
        for (int i = 0; i < enchant.length; ++i) {
            this.itemStack.addUnsafeEnchantment(enchant[i], level[i]);
        }
        return this;
    }
    
    public ItemCreator setEnchant(final Enchantment enchant, final int level) {
        this.itemStack.addUnsafeEnchantment(enchant, level);
        return this;
    }
    
    public ItemCreator setUnbreakable() {
        final ItemMeta meta = this.itemStack.getItemMeta();
        meta.spigot().setUnbreakable(true);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    
    public ItemCreator setBreakable() {
        final ItemMeta meta = this.itemStack.getItemMeta();
        meta.spigot().setUnbreakable(false);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    
    public ItemCreator setBreakable(final boolean breakable) {
        final ItemMeta meta = this.itemStack.getItemMeta();
        meta.spigot().setUnbreakable(!breakable);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    
    public ItemCreator build(final Player player, final int... slot) {
        this.build((Inventory)player.getInventory(), slot);
        player.updateInventory();
        return this;
    }
    
    public ItemCreator noAttributes() {
        return this;
    }
    
    public ItemCreator build(final Player player) {
        player.getInventory().addItem(new ItemStack[] { this.itemStack });
        player.updateInventory();
        return this;
    }
    
    public ItemCreator build(final Inventory inventory, final int... slot) {
        for (final int slots : slot) {
            inventory.setItem(slots, this.itemStack);
        }
        return this;
    }
    
    public ItemCreator build(final Inventory inventory) {
        inventory.addItem(new ItemStack[] { this.itemStack });
        return this;
    }
    
    public ItemStack getStack() {
        return this.itemStack;
    }
    
    public ItemMeta setName(final ItemStack stack, final String name) {
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        return meta;
    }
    
    public ItemCreator setSkull(final String owner) {
        final SkullMeta meta = (SkullMeta)this.itemStack.getItemMeta();
        meta.setOwner(owner);
        this.itemStack.setItemMeta((ItemMeta)meta);
        return this;
    }
    
    public ItemStack setColor(final Material material, final Color color) {
        final ItemStack stack = new ItemStack(material);
        final LeatherArmorMeta armorMeta = (LeatherArmorMeta)stack.getItemMeta();
        armorMeta.setColor(color);
        stack.setItemMeta((ItemMeta)armorMeta);
        return stack;
    }
    
    public ItemCreator setColor(final Color color) {
        final LeatherArmorMeta armorMeta = (LeatherArmorMeta)this.getStack().getItemMeta();
        armorMeta.setColor(color);
        this.getStack().setItemMeta((ItemMeta)armorMeta);
        return this;
    }
    
    public ItemStack setColor(final Material material, final Color color, final String name) {
        final ItemStack stack = new ItemStack(material);
        final LeatherArmorMeta armorMeta = (LeatherArmorMeta)stack.getItemMeta();
        armorMeta.setColor(color);
        armorMeta.setDisplayName(name);
        stack.setItemMeta((ItemMeta)armorMeta);
        return stack;
    }
    
    public ItemCreator setColor(final Color color, final String name) {
        final LeatherArmorMeta armorMeta = (LeatherArmorMeta)this.itemStack.getItemMeta();
        armorMeta.setColor(color);
        armorMeta.setDisplayName(name);
        this.itemStack.setItemMeta((ItemMeta)armorMeta);
        return this;
    }
    
    public ItemCreator chanceItemStack(final ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }
    
    public boolean checkItem(final ItemStack item, final String display) {
        return item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(display);
    }
    
    public boolean checkContains(final ItemStack item, final String display) {
        return item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains(display);
    }
    
    public static List<String> getFormattedLore(String text) {
        final List<String> lore = new ArrayList<String>();
        final String[] split = text.split(" ");
        text = "";
        for (int i = 0; i < split.length; ++i) {
            if (ChatColor.stripColor(text).length() > 25 || ChatColor.stripColor(text).endsWith(".") || ChatColor.stripColor(text).endsWith("!")) {
                lore.add(text);
                if (text.endsWith(".") || text.endsWith("!")) {
                    lore.add("");
                }
                text = "";
            }
            String toAdd = split[i];
            if (toAdd.contains("\n")) {
                toAdd = toAdd.substring(0, toAdd.indexOf("\n"));
                split[i] = split[i].substring(toAdd.length() + 1);
                lore.add(text + ((text.length() == 0) ? "" : " ") + toAdd);
                text = "";
                --i;
            }
            else {
                text = text + ((text.length() == 0) ? "" : " ") + toAdd;
            }
        }
        lore.add(text);
        return lore;
    }
}
