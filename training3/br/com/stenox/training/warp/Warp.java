// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.warp;

import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import java.util.List;
import br.com.stenox.training.kit.Kit;

public class Warp
{
    private String name;
    private Kit defaultKit;
    private boolean pvp;
    private final List<String> players;
    private Location spawn;
    
    public Warp() {
        this.players = Lists.newCopyOnWriteArrayList();
    }
    
    public boolean hasDefaultKit() {
        return this.defaultKit != null;
    }
    
    public void addPlayer(final Player player) {
        this.players.add(player.getName());
    }
    
    public void removePlayer(final Player player) {
        final Gamer gamer = Gamer.getGamer(player.getName());
        if (gamer != null && gamer.getWarpName().equals(this.getName()) && this.hasDefaultKit()) {
            gamer.setKit(null);
        }
        this.players.remove(player.getName());
    }
    
    public String getName() {
        return this.name;
    }
    
    public Kit getDefaultKit() {
        return this.defaultKit;
    }
    
    public boolean isPvp() {
        return this.pvp;
    }
    
    public List<String> getPlayers() {
        return this.players;
    }
    
    public Location getSpawn() {
        return this.spawn;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setDefaultKit(final Kit defaultKit) {
        this.defaultKit = defaultKit;
    }
    
    public void setPvp(final boolean pvp) {
        this.pvp = pvp;
    }
    
    public void setSpawn(final Location spawn) {
        this.spawn = spawn;
    }
}
