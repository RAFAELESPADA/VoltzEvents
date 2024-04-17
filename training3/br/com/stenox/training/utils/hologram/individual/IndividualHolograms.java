// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram.individual;

import br.com.stenox.training.Main;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.List;

public class IndividualHolograms
{
    private final List<HologramLine> hologramLines;
    private final String[] text;
    private final Location location;
    private final double DISTANCE = 0.3;
    private final Player target;
    private boolean hided;
    int count;
    private ClickHandler clickHandler;
    
    public IndividualHolograms(final Player p, final String[] text, final Location location) {
        this.hologramLines = new ArrayList<HologramLine>();
        this.hided = true;
        this.target = p;
        this.text = text;
        this.location = location;
        this.create();
        Main.getInstance().getHologramManager().getHologramsList().add(this);
    }
    
    public void show() {
        this.hologramLines.forEach(HologramLine::show);
        this.setHided(false);
    }
    
    public void hide() {
        this.hologramLines.forEach(HologramLine::hide);
        this.setHided(true);
    }
    
    private void create() {
        final Location locationClone = this.location.clone();
        for (final String text : this.text) {
            final HologramLine hl = new HologramLine(this.getTarget(), locationClone, text);
            hl.build();
            this.hologramLines.add(hl);
            final Location location = locationClone;
            final double n = 0.0;
            this.getClass();
            location.subtract(n, 0.3, 0.0);
        }
    }
    
    public void updateText(final int index, final String text) {
        this.hologramLines.get(index).update(text);
    }
    
    public HologramLine getLine(final int i) {
        return this.hologramLines.get(i);
    }
    
    public String getText(final int i) {
        return this.hologramLines.get(i).getText();
    }
    
    private double square(final double val) {
        return val * val;
    }
    
    public List<HologramLine> getHologramLines() {
        return this.hologramLines;
    }
    
    public String[] getText() {
        return this.text;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public double getDISTANCE() {
        this.getClass();
        return 0.3;
    }
    
    public Player getTarget() {
        return this.target;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public boolean isHided() {
        return this.hided;
    }
    
    public void setHided(final boolean hided) {
        this.hided = hided;
    }
    
    public ClickHandler getClickHandler() {
        return this.clickHandler;
    }
    
    public void setClickHandler(final ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }
}
