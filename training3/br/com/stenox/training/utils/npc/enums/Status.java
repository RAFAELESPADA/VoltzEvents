// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc.enums;

public enum Status
{
    HURT((byte)2), 
    DEAD((byte)3);
    
    private Byte value;
    
    private Status(final Byte value) {
        this.value = value;
    }
    
    public Byte getValue() {
        return this.value;
    }
}
