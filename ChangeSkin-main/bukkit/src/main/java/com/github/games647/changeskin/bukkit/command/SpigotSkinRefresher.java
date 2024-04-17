package com.github.games647.changeskin.bukkit.command;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpigotSkinRefresher {

    private void sendPackets(Packet... packets) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Packet packet : packets) {
                EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();

                nmsPlayer.playerConnection.sendPacket(packet);

            }
        }

    }

    private void sendPacketsNotFor(String notFor, Packet... packets) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.getName().equals(notFor)) {
                for (Packet packet : packets) {
                    EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();
                    nmsPlayer.playerConnection.sendPacket(packet);
                }
            }
        }
    }
    public static void changeSkin(Player p) {
        GameProfile profile = ((CraftPlayer) p).getHandle().getProfile();

        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;

        connection.sendPacket(new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) p).getHandle())); // remove player is e
        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", getSkin());
        connection.sendPacket(new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) p).getHandle())); // add player is a
    }
    private static Property getSkin() {
        return new Property("textures", "PpjPEa4c7n57ci/ReofqzzusiDJ/u/p2XpP1MlgwiUjaOmnNrf2xN4+bfg3rmeoXntyxtt57QWByVwKwulLtLvnFeJSZjuYCo+AxcX7y5E1xexwy0kcQ8ChSgZ/E5HP86AuLPY9tlaNVqBaF4Moejp3NZEpoi9Vus9o80HGrX91pvnf5s5X3Y7iFW/vGfl/fHfwQ17zHutPTBsvlP7T/jgoJQT0wKKovgA0vu6iQK1ZTCjL/aNp2eQpdE/Bh8q5TLc3kn3KNmvpoawjSsHxkdj2zo7wWIke208+7wcrzBc8K/rXZFiTgXvt3ktWWrOZHDDqRUJ+tfD1zLfD1bESrQ2+tW98w13Wgqwsox33HCvUGkgixsbvVcbliwqB6L+7AtD08rK4fK+BwFsVu+ILI8q5TGgQEQ45uwhJvO4qifXr3Hq+GeTDojoSWyGk6UsM6yo+lPcHt0XY2OWMzUZhyfW0R+slNtS1GZNYEesx8PPP7NwtX7/Nvoe+GeG6yJR+zR4H6Jkd/Ug+tZQBH4qahB3F7qbNz0Jp2QHMBWz2AwuK57CWceFavimE46APdjaC3OWAgJOVNwwHWoJg6T1+IKAVDu91XhPPNLKCWF5HlyX1WutX9tMOSkgc/lsK36Jzjk4FFKxUyE9PTqwvEsOqJUmaYyXAZl+/gBUh/aNK8UU8=");

    }
}


