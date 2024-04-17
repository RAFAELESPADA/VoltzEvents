// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.gamer;

import org.bukkit.potion.PotionEffect;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import br.com.stenox.training.Main;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.LinkedHashSet;
import java.util.Comparator;
import java.util.HashSet;
import org.bukkit.Bukkit;
import java.util.UUID;
import br.com.stenox.training.utils.scoreboard.ScoreboardWrapper;
import br.com.stenox.training.kit.Kit;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.gamer.tag.Tag;
import java.util.Set;
import br.com.stenox.training.warp.Warp;
import org.bukkit.entity.Player;

public class Gamer
{
    private final String name;
    private Player player;
    private Warp warp;
    private Set<Tag> tags;
    private Tag currentTag;
    private Group group;
    private Kit kit;
    private ScoreboardWrapper wrapper;
    private boolean tell;
    private boolean isMuted;
    private boolean isBanned;
    private String muteReason;
    private String banReason;
    private long muteTime;
    private long banTime;
    private UUID lastTell;
    
    public Gamer(final String name) {
        this.tell = true;
        this.name = name;
        this.player = Bukkit.getPlayer(name);
        this.tags = new HashSet<Tag>();
        this.group = Group.MEMBER;
    }
    
    public String getWarpName() {
        return (this.warp == null) ? "Nenhuma" : this.warp.getName();
    }
    
    public String getKitName() {
        return (this.kit == null) ? "Nenhum" : this.kit.getName();
    }
    
    public void joinWarp(final Warp warp) {
        this.setWarp(warp);
        if (warp.hasDefaultKit()) {
            this.setKit(warp.getDefaultKit());
        }
    }
    
    public void addTag(final Tag tag) {
        this.tags.add(tag);
    }
    
    public void removeTag(final Tag tag) {
        this.tags.add(tag);
    }
    
    public boolean hasTag(final Tag tag) {
        return this.tags.contains(tag);
    }
    
    public Set<Tag> getTags() {
        return this.tags = this.tags.stream().sorted(Comparator.comparingInt(o -> o.ordinal()).reversed()).collect((Collector<? super Object, ?, Set<Tag>>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new));
    }
    
    public Tag getBiggestTag() {
        for (final Tag tag : Tag.values()) {
            if (this.hasTag(tag)) {
                return tag;
            }
        }
        return Tag.MEMBER;
    }
    
    public void loadTags() {
        this.tags.addAll(Tag.getByGroup(this.group));
        if (this.tags.size() == 0) {
            this.tags.add(Tag.MEMBER);
        }
    }
    
    public void updateTag() {
        this.setCurrentTag(this.getBiggestTag());
    }
    
    public void setCurrentTag(final Tag currentTag) {
        this.currentTag = currentTag;
        Main.getInstance().getTagProvider().setTag(this.getPlayer(), currentTag);
    }
    
    public String getTagsString() {
        final StringBuilder sb = new StringBuilder();
        if (this.tags.isEmpty()) {
            this.tags.add(Tag.MEMBER);
        }
        for (final Tag tag : this.tags) {
            sb.append(tag.getName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    
    public void setGroup(final Group group) {
        this.group = group;
        this.loadTags();
    }
    
    public void resetPlayer() {
        this.getPlayer();
        this.player.setLevel(0);
        this.player.setMaxHealth(20.0);
        this.player.setHealth(20.0);
        this.player.setFoodLevel(20);
        this.player.setFireTicks(0);
        this.player.setItemOnCursor((ItemStack)null);
        this.player.setFallDistance(-1.0f);
        this.player.getInventory().clear();
        this.player.getInventory().setHeldItemSlot(0);
        this.player.getInventory().setArmorContents((ItemStack[])null);
        this.player.getOpenInventory().getTopInventory().clear();
        this.player.getActivePotionEffects().forEach(effect -> this.player.removePotionEffect(effect.getType()));
    }
    
    public void ban(final String reason, final long time) {
        this.setBanned(true);
        this.setBanReason(reason);
        this.setBanTime(time);
    }
    
    public void unban() {
        this.setBanned(false);
        this.setBanReason(null);
        this.setBanTime(0L);
        Main.getInstance().getGamerRepository().update(this);
    }
    
    public void mute(final String reason, final long time) {
        this.setMuted(true);
        this.setMuteReason(reason);
        this.setMuteTime(time);
    }
    
    public void unmute() {
        this.setMuted(false);
        this.setMuteReason(null);
        this.setMuteTime(0L);
        Main.getInstance().getGamerRepository().update(this);
    }
    
    public Player getPlayer() {
        if (this.player == null) {
            this.player = Bukkit.getPlayer(this.name);
        }
        return this.player;
    }
    
    public static Gamer getGamer(final String name) {
        return Main.getInstance().getGamerController().search(name);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Warp getWarp() {
        return this.warp;
    }
    
    public Tag getCurrentTag() {
        return this.currentTag;
    }
    
    public Group getGroup() {
        return this.group;
    }
    
    public Kit getKit() {
        return this.kit;
    }
    
    public ScoreboardWrapper getWrapper() {
        return this.wrapper;
    }
    
    public boolean isTell() {
        return this.tell;
    }
    
    public boolean isMuted() {
        return this.isMuted;
    }
    
    public boolean isBanned() {
        return this.isBanned;
    }
    
    public String getMuteReason() {
        return this.muteReason;
    }
    
    public String getBanReason() {
        return this.banReason;
    }
    
    public long getMuteTime() {
        return this.muteTime;
    }
    
    public long getBanTime() {
        return this.banTime;
    }
    
    public UUID getLastTell() {
        return this.lastTell;
    }
    
    public void setWarp(final Warp warp) {
        this.warp = warp;
    }
    
    public void setKit(final Kit kit) {
        this.kit = kit;
    }
    
    public void setWrapper(final ScoreboardWrapper wrapper) {
        this.wrapper = wrapper;
    }
    
    public void setTell(final boolean tell) {
        this.tell = tell;
    }
    
    public void setMuted(final boolean isMuted) {
        this.isMuted = isMuted;
    }
    
    public void setBanned(final boolean isBanned) {
        this.isBanned = isBanned;
    }
    
    public void setMuteReason(final String muteReason) {
        this.muteReason = muteReason;
    }
    
    public void setBanReason(final String banReason) {
        this.banReason = banReason;
    }
    
    public void setMuteTime(final long muteTime) {
        this.muteTime = muteTime;
    }
    
    public void setBanTime(final long banTime) {
        this.banTime = banTime;
    }
    
    public void setLastTell(final UUID lastTell) {
        this.lastTell = lastTell;
    }
}
