// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.gamer;

import java.util.HashSet;
import br.com.stenox.training.Main;
import java.util.Set;

public class GamerController
{
    private final Set<Gamer> gamers;
    private final Main plugin;
    
    public GamerController(final Main plugin) {
        this.gamers = new HashSet<Gamer>();
        this.plugin = plugin;
    }
    
    public void create(final Gamer model) {
        this.gamers.add(model);
    }
    
    public Gamer search(final String name) {
        return this.gamers.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }
    
    public Gamer searchOrFind(final String name) {
        Gamer gamer = this.gamers.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
        if (gamer == null) {
            gamer = this.plugin.getGamerRepository().fetch(name);
        }
        return gamer;
    }
    
    public void update(final Gamer gamer) {
        if (gamer != null) {
            this.plugin.getGamerRepository().update(gamer);
        }
    }
    
    public void remove(final String name) {
        final Gamer gamer = this.search(name);
        if (gamer != null) {
            this.update(gamer);
            this.gamers.remove(gamer);
        }
    }
    
    public boolean contains(final String name) {
        final Gamer gamer = this.search(name);
        return gamer != null || this.plugin.getGamerRepository().contains(name);
    }
    
    public Set<Gamer> getGamers() {
        return this.gamers;
    }
}
