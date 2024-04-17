// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.gamer.tag;

import java.util.ArrayList;
import java.util.List;
import br.com.stenox.training.gamer.group.Group;

public enum Tag
{
    OWNER("Dono", "§4Dono", "§4§lADMIN §4", "§4", "a"), 
    ADMIN("Admin", "§4Admin", "§4§lADMIN §4", "§4", "b"), 
    MOD("Mod", "§5Mod", "§5§lMOD §5", "§5", "c"), 
    KONOHA("Konoha", "§eKonoha", "§e§lKONOHA §e", "§e", "d"), 
    FAMOUS("Famous", "§bFamous", "§b§lFAMOUS §b", "§b", "e"), 
    MEMBER("Membro", "§7Membro", "§7", "§7", "f");
    
    private final String name;
    private final String displayName;
    private final String prefix;
    private final String color;
    private final String position;
    
    private Tag(final String name, final String displayName, final String prefix, final String color, final String position) {
        this.name = name;
        this.displayName = displayName;
        this.prefix = prefix;
        this.color = color;
        this.position = position;
    }
    
    public static Tag fromString(final String name) {
        for (final Tag tag : values()) {
            if (name.equalsIgnoreCase(tag.name()) || name.equalsIgnoreCase(tag.getName())) {
                return tag;
            }
        }
        return null;
    }
    
    public static List<Tag> getByGroup(final Group group) {
        final List<Tag> tags = new ArrayList<Tag>();
        final Tag tag = fromString(group.getName());
        if (tag != null) {
            for (final Tag value : values()) {
                if (value.ordinal() >= tag.ordinal()) {
                    tags.add(value);
                }
            }
        }
        return tags;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public String getColor() {
        return this.color;
    }
    
    public String getPosition() {
        return this.position;
    }
}
