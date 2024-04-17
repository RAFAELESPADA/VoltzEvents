// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.gamer.group;

public enum Group
{
    OWNER("Dono", "§4"), 
    ADMIN("Admin", "§4"), 
    MOD("Mod", "§5"), 
    MEMBER("Membro", "§7");
    
    private final String name;
    private final String color;
    
    public static Group fromString(final String name) {
        for (final Group group : values()) {
            if (name.equalsIgnoreCase(group.name()) || name.equalsIgnoreCase(group.getName())) {
                return group;
            }
        }
        return null;
    }
    
    private Group(final String name, final String color) {
        this.name = name;
        this.color = color;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getColor() {
        return this.color;
    }
}
