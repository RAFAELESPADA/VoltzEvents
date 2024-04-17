// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram.individual;

import java.util.ArrayList;
import org.bukkit.event.Listener;
import java.util.UUID;
import org.bukkit.entity.Player;
import br.com.stenox.training.Main;
import java.util.List;
import br.com.stenox.training.custom.CustomManager;

public class HologramManager extends CustomManager
{
    private static final List<IndividualHolograms> hologramsList;
    
    public HologramManager(final Main plugin) {
        super(plugin);
    }
    
    public void removePlayerHologram(final Player p) {
        HologramManager.hologramsList.removeIf(c -> c.getTarget().getUniqueId().equals(p.getUniqueId()));
    }
    
    public void removePlayerHologram(final UUID uuid) {
        HologramManager.hologramsList.removeIf(c -> c.getTarget().getUniqueId().equals(uuid));
    }
    
    public List<IndividualHolograms> getHologramsList() {
        return HologramManager.hologramsList;
    }
    
    @Override
    public void onEnable() {
        this.registerListener((Listener)new HologramListeners());
    }
    
    @Override
    public void onDisable() {
        HologramManager.hologramsList.clear();
    }
    
    static {
        hologramsList = new ArrayList<IndividualHolograms>();
    }
}
