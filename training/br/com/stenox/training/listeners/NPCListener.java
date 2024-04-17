// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.listeners;

import br.com.stenox.training.menus.DamagerMenu;
import br.com.stenox.training.menus.NinjaMenu;
import br.com.stenox.training.utils.menu.PlayerMenuUtility;
import br.com.stenox.training.menus.AnchorMenu;
import br.com.stenox.training.utils.npc.NPCInteractEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import br.com.stenox.training.utils.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import br.com.stenox.training.utils.npc.enums.EquipmentSlot;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import br.com.stenox.training.Main;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;

public class NPCListener implements Listener
{

    
    public NPCListener() {
    	
    }
    
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player receiver = event.getPlayer();

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.getInstance(), () -> {
            Location location = new Location(Bukkit.getWorlds().get(0), 47.5, 67.0, -2.5, 90.0f, 0.0f);
            NPC npc = Main.getInstance().getNpcManager().createNPC("[Anchor]", "ewogICJ0aW1lc3RhbXAiIDogMTY1NDI4NjY3OTMyOSwKICAicHJvZmlsZUlkIiA6ICIxMjA4ZTVhZmU1OTI0ODNmOTQwOTQ3MjY3MzgzZmYyMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJMaWFuX2JyYWJvIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQyNzRjYzllOWIzZDkxNDNlNTRmNTlmZWIxZGYxNWZjNTQ5YzBmN2MyN2FkYzQ3NzZjNDA1YjNkNmRmZGQzYzEiCiAgICB9CiAgfQp9", "J3M6VRr6qIhSQodtVLWt33HvD+2EXunpxe5Q484XS0U3p0kLsskBmdAzwb4K/XT5omSPLbuevad4JJDR0Tp9kA8wakz4XYJwlTxda4pJGc6CfxdEteYsGMuDq0ARXZ4RXjjgqIJIWgMS3TRyIyX0GtVYE//Ndu5Ek41HneRusCT5oMKC/pbvie9QU6CGZms5h1wcHtbj9pNhcP9hjWYiUJQIRRxBs7VtvkmTUHE/XOJwDkxHXSlKPIKCz+qS8XVtD+cdBv3luA07tY4DKfYZ80qIjCFM+MB4x1Jfsua2sl24g1orZcLl2xE50jUm77+DEYbsYGd+E6DdNkyFpMQ97vwqoA4nRA0n2u4uR4J8sooM90tjPFXkAUP32AAVOG0uBnGKptHxqOtedHXZV2cQTSHVFkJKelYXNk5Ob6mjmmF+3UNo1Uq9XlhoRMyD44QKorRQk9dLBpVbDTUb1IJPRx1B9eOcaog/54SvYi4dSJZqnJrlqU5EK0NtVKXRaAoFVv2Gek52Tld58I0VZFu8Fjxw3gk/DJQF61rpB0YL9djbaKJI3nLL6TPE2i4XGwcUA7MbIkYP6/k+DRHCe5CR4jAC8G4YItLe9O90iwiAfHoyQv9+TeSIKBqlJ0K/9EoxZ+Moxs9Dru2cCDRAZs//CRjwGOiI/BwMuMLCzBzKfaY=", location, receiver);
            npc.getLocation().getChunk().load();
            npc.setEquipment(EquipmentSlot.HAND, new ItemStack(Material.ANVIL));
            Location location2 = new Location(Bukkit.getWorlds().get(0), 47.5, 67.0, 0.5, 90.0f, 0.0f);
            NPC npc2 = Main.getInstance().getNpcManager().createNPC("[Ninja]", "ewogICJ0aW1lc3RhbXAiIDogMTY1NDUzMTkwNjgxOSwKICAicHJvZmlsZUlkIiA6ICIyODMwN2JlMmRlMzM0N2NhYTY0NjBjNzU4MDg5ZWFjYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJtYXR1ZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85OTJkYjg4YmQxNjg1MGFlZTM2ODQ1MDE1ZTkwMjA5OTU1NjYzODdiMmI5NWZlNzU4N2IwOGM3MzA1YzgwODFhIgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzQwYzBlMDNkZDI0YTExYjE1YThiMzNjMmE3ZTllMzJhYmIyMDUxYjI0ODFkMGJhN2RlZmQ2MzVjYTdhOTMzIgogICAgfQogIH0KfQ==", "HiPC31BczrTOVvC72JwrFXlfCOZxH3Kxs0xjofWtWhdT+CgLVnLCqa3H1jL2I1E91AhPJcHLZm/9buFL2HFHDCQSAZUDJknkm2AI6YAor7HDjUI8ldSAau7TyVsPl9/Y3E8U0i7tZa+DwigxndBCKQNsNo2gCWiB7Zyk6hCk7LWQFgKEivd16vhKY31PWIkj3u+K5ehxHuxtda3m8kMRtP/d8zURAWhoAA4x99GhtGJSKKRYDfo5d4M3SF3CaquwD0KNSmiFb0tJHlA8U6xIJ3UGSbwuacdTkXigoIoLuXHX8NeQZeQJ1J0WGSgpfWWIBTDf2cNgcGvXshc9uVRohHkzkXpggpZ3BMlEi29OlMY+CjXbCEPJC/gF5OBoF13FHagS+l43hyr04QU2FR1DosLpBkZAB5XNskfy30PsYL686oRwSB390xjVIOIjWEsN1SVS4eXhCs7Wr9983kgC9o1KbDkl+JV4TM87zTwI0WOMWKMNOrQOZryMWKFoMfAyNWSl002vZp9aavpVRzbt+hpcWfLL0wucDaBmkyV/JtDhrYHOi8JxhWuKipwkyzgekEPWIT7CPu4Ew+IhE9qyuQsIjFCuohM5RPvXj3+lmvGh7lBt51qeBKKXFVTaOC5CSqYX/4eT8VqbPZfn+eul7uG8KRpN57l8z6L1Sq7IBto=", location2, receiver);
            npc2.getLocation().getChunk().load();
            npc2.setEquipment(EquipmentSlot.HAND, new ItemStack(Material.NETHER_STAR));
            Location location3 = new Location(Bukkit.getWorlds().get(0), 47.5, 67.0, 3.5, 90.0f, 0.0f);
           NPC npc3 = Main.getInstance().getNpcManager().createNPC("[Damager]", "ewogICJ0aW1lc3RhbXAiIDogMTY1NDI4NzMzMTc3MywKICAicHJvZmlsZUlkIiA6ICJhYjRiMzU5MGViNGI0MGY4YThlMDNhMmU4YWY5ZjBkNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJyZWduaWVsIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ExMDk5MTE4NmEwNDc1ODlhZWY5NzIzNTQwOTNhNzIxMjQxMWQyODAwN2EzMTkyMmU4OWNhOTg3MzEyYTg5NWQiCiAgICB9CiAgfQp9", "nu6/Fdcu+8Qt6pQOIMw0I+6H2qxN6+95oZ0hfqiwMU2LcSoWwNTg0Pcr+EUSal6s1lD/cVan/WQ/g+fCgkVKjpD9tRiSANq9TuYW8NQsw/6i0VaumapTCSOfNfMhq3YzQ+1wH7O/36NBsDM7E2rY00xbRo2TM6+h0HXAqjTtqIAbbe6XlE+TNQqwvouUcULHSkyu7cHe3A3BSL29wtMOEbHWXgkqU+tyEI4GKU1UclTTnWLVOlA01gf2bhKN+rmaFsF1Uu6naM8WV8AUTgXV+4+IoXh9rAwSAazKWsgY5k2c9kQ0cNseb013ss57yJYBIfJOfpjPOrZV9wB67KWDBNCmUhdY1Fk7H7UglloEc0MYZ5Vmsmx+JpAJSp20moa5lhXeWFLUHidworCUJXECY54dpxQlhKSlteWjS76ydwWgOMRK+pAC3KG/HrUVyQfoGK577S73GC73spSqEpBHucV8DMmqYCrYRQYNtk8RvTv6yxdO85Ciwic2bTGwNJHgY3vuKeTxc4iIcWP3XhsRJsXzivOolS/kiLu3wPshJGFynF/Rnmg5YdjlmboYUNPHXolr5Gv1S+i+t9JSsvw2WcjOc5Wo/K5nRcgK8DZkT7bKUNd2QQq0OC2uWOLuu+c5iPzzU8ghbxcXTwCocAlq15ijwviXjRJxm3fi3MbRhUg=", location3, receiver);
            npc3.getLocation().getChunk().load();
        }, 10L);
    }
    
    
    @EventHandler
    public void onNPCInteract(final NPCInteractEvent event) {
        final String name = event.getNPC().getName();
        switch (name) {
            case "[Anchor]": {
                new AnchorMenu().open(new PlayerMenuUtility(event.getPlayer()));
                break;
            }
            case "[Ninja]": {
                new NinjaMenu().open(new PlayerMenuUtility(event.getPlayer()));
                break;
            }
            case "[Damager]": {
                new DamagerMenu().open(new PlayerMenuUtility(event.getPlayer()));
                break;
            }
        }
    }
}
