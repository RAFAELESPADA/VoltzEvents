// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc.enums;

public enum Animation
{
    SWING_ARM(Integer.valueOf(0)), 
    TAKE_DAMAGE(Integer.valueOf(1)), 
    LEAVE_BED(Integer.valueOf(2)), 
    EAT_FOOD(Integer.valueOf(3)), 
    CRITICAL_EFFECT(Integer.valueOf(4)), 
    MAGIC_CRITICAL_EFFECT(Integer.valueOf(5));
    
    private Integer value;
    
    private Animation(final Integer value) {
        this.value = value;
    }
    
    public Integer getValue() {
        return this.value;
    }
}
