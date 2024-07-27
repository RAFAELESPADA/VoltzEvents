package me.rafaelauler.events;


import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import net.wavemc.pvp.cooldown1.WaveCooldown2;



public class KitHandler implements Listener {

	public void execute(Player player) {
	}

protected boolean hasCooldown(Player player) {
    return WaveCooldown2.hasCooldown(player, "Kit");
}

protected boolean hasCooldown(Player player, String cooldown) {
    return WaveCooldown2.hasCooldown(player, cooldown);
}

protected boolean inCooldown(Player player, String cooldown) {
    return WaveCooldown2.inCooldown(player, cooldown);
}

protected boolean inCooldown(Player player) {
    return WaveCooldown2.inCooldown(player, "Kit");
}

protected void sendMessageCooldown(Player player) {
	WaveCooldown2.sendMessage(player, "Kit");
}

protected void sendMessageCooldown(Player player, String cooldown) {
	WaveCooldown2.sendMessage(player, cooldown);
}

protected void addCooldown(Player player, String cooldownName, long time) {
    if (WaveCooldown2.hasCooldown(player, cooldownName)) {
        WaveCooldown2.removeCooldown(player, cooldownName);
    }
    WaveCooldown2.addCooldown(player, new net.wavemc.pvp.cooldown1.WaveCooldownAPI(cooldownName, time));
}

protected void addCooldown(Player player, long time) {
    if (WaveCooldown2.hasCooldown(player,"Kit")) {
        WaveCooldown2.removeCooldown(player,"Kit");
    }
    WaveCooldown2.addCooldown(player, new  net.wavemc.pvp.cooldown1.WaveCooldownAPI("Kit", time));
}

protected void addItemCooldown(Player player, ItemStack item, String cooldownName, long time) {
    if (WaveCooldown2.hasCooldown(player, cooldownName)) {
        WaveCooldown2.removeCooldown(player, cooldownName);
    }
    WaveCooldown2.addCooldown(player, new  net.wavemc.pvp.cooldown1.ItemCooldown(item, cooldownName, time));
}
}
