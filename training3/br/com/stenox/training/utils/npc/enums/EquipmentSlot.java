// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc.enums;

public enum EquipmentSlot
{
    HAND(Integer.valueOf(0)), 
    BOOTS(Integer.valueOf(1)), 
    LEGGINGS(Integer.valueOf(2)), 
    CHESTPLATE(Integer.valueOf(3)), 
    HELMET(Integer.valueOf(4));
    
    private Integer value;
    
    private EquipmentSlot(final Integer value) {
        this.value = value;
    }
    
    public Integer getValue() {
        return this.value;
    }
}
