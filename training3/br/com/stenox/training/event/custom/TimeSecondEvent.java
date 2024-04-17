// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.event.custom;

import br.com.stenox.training.event.CustomEvent;

public class TimeSecondEvent extends CustomEvent
{
    private TimeType type;
    
    public TimeSecondEvent(final TimeType type) {
        this.type = type;
    }
    
    public TimeType getType() {
        return this.type;
    }
    
    public enum TimeType
    {
        MILLISECONDS, 
        SECONDS;
    }
}
