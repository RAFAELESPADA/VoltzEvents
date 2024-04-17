// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram;

import java.util.Collection;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;

public class Hologram
{
    private boolean spawned;
    private final Location location;
    private TouchHandler touchHandler;
    private final Map<Integer, HologramLine> lines;
    
    public Hologram(final Location location, final String... lines) {
        this.lines = new HashMap<Integer, HologramLine>();
        this.location = location;
        int index = 0;
        for (final String line : lines) {
            this.lines.put(++index, new HologramLine(this, location.clone().add(0.0, 0.33 * index, 0.0), line));
        }
    }
    
    public void spawn() {
        if (this.spawned) {
            return;
        }
        this.spawned = true;
        this.lines.values().forEach(HologramLine::spawn);
    }
    
    public void despawn() {
        if (!this.spawned) {
            return;
        }
        this.spawned = false;
        this.lines.values().forEach(HologramLine::despawn);
    }
    
    public void addLine(final String text) {
        int line;
        for (line = 1; this.lines.containsKey(line); ++line) {}
        final HologramLine hl = new HologramLine(this, this.location.clone().add(0.0, 0.33 * line, 0.0), text);
        this.lines.put(line, hl);
        if (this.spawned) {
            hl.spawn();
        }
    }
    
    public void addLineTOP(final String text) {
        int line;
        for (line = 1; this.lines.containsKey(line); --line) {}
        final HologramLine hl = new HologramLine(this, this.location.clone().add(0.0, 0.33 * line, 0.0), text);
        this.lines.put(line, hl);
        if (this.spawned) {
            hl.spawn();
        }
    }
    
    public void setTouchHandler(final TouchHandler touchHandler) {
        this.touchHandler = touchHandler;
    }
    
    public boolean hasTouchHandler() {
        return this.touchHandler != null;
    }
    
    public TouchHandler getTouchHandler() {
        return this.touchHandler;
    }
    
    public void updateLine(final int line, final String text) {
        if (this.lines.containsKey(line)) {
            this.lines.get(line).setText(text);
        }
    }
    
    public boolean isSpawned() {
        return this.spawned;
    }
    
    public Location getInitLocation() {
        return this.location;
    }
    
    public List<HologramLine> getLines() {
        return (List<HologramLine>)ImmutableList.copyOf((Collection)this.lines.values());
    }
}
