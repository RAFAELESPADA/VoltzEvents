// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.protocol;

import java.util.stream.Stream;
import lombok.NonNull;

public enum ProtocolVersion
{
    MINECRAFT_1_11_1(Integer.valueOf(316)), 
    MINECRAFT_1_11(Integer.valueOf(315)), 
    MINECRAFT_1_10(Integer.valueOf(210)), 
    MINECRAFT_1_9_4(Integer.valueOf(110)), 
    MINECRAFT_1_9_2(Integer.valueOf(109)), 
    MINECRAFT_1_9_1(Integer.valueOf(108)), 
    MINECRAFT_1_9(Integer.valueOf(107)), 
    MINECRAFT_1_8(Integer.valueOf(47)), 
    MINECRAFT_1_7_10(Integer.valueOf(5)), 
    MINECRAFT_1_7_5(Integer.valueOf(4)), 
    UNKNOWN(Integer.valueOf(-1));
    
    @NonNull
    private Integer id;
    
    public static ProtocolVersion getById(final int id) {
        return Stream.of(values()).filter(e -> e.getId() == id).findFirst().orElse(ProtocolVersion.UNKNOWN);
    }
    
    private ProtocolVersion(final Integer id) {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        }
        this.id = id;
    }
    
    @NonNull
    public Integer getId() {
        return this.id;
    }
}
