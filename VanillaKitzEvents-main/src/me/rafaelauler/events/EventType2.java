package me.rafaelauler.events;






import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lombok.Getter;

@Getter
public enum EventType2 {


    ARENAPVP("ArenaPvP", Arrays.asList("Ol� a todos! Bem vindo ao &c&lArena PvP&l&f!", "No evento, voc�s receberam um kit com items", "Com o tempo com jogadores diminuindo voc�s ser�o teleportados a um novo local.", "Todos tem 20 segundos para se preparar.", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fN�o tente escapar da arena pvp.", "&fN�o fa�a mais times que o promotor deixar." , "Come�ando evento! Boa sorte"), WaveWarp.ARENAPVP.getLocation()),
    LAVA("LavaChallenge", Arrays.asList("Ol� a todos! Bem vindo ao evento &4&lLava Challenge", "&fAo inicio do evento todos receberam um kit com sopas.", "Todos precisar�o tankar a lava que voc�s ser�o colocados!", "Suas sopas N�O ser�o reabastecidas durante o evento, ent�o use elas com cautela.", "Voc�s ter�o 10 segundos para se prepararem!", "Come�ando evento! Boa sorte!"), WaveWarp.LAVACHALLENGE.getLocation()),
    MESTREMANDOU("MestreMandou", Arrays.asList("Ol� a todos! Bem vindo ao evento &4&lMestre Mandou", "&fAo inicio do evento todos ser�o dados ordens aleat�rias.", "Como craftar certos itens e fazer certas a��es!", "Se n�o cumprir a a��o dentro do prazo determinado ser� kikado.", "Voc�s ter�o 10 segundos para se prepararem!", "Come�ando evento! Boa sorte!"), WaveWarp.MESTREMANDOU.getLocation()),
    SUMO("Sumo", Arrays.asList("Bem vindo ao evento Sum�!", "No in�cio do evento voc�s ser�o puxados de 2 em 2 para uma luta individual", "Quem ganhar voltar� ao evento!", "E quem perder voltar� ao spawn!", "Come�ando evento. Divirta-se"), WaveWarp.SUMO.getLocation()),
    ARENANINJA("ArenaNinja", Arrays.asList("Ol� a todos! Bem vindo ao &c&lArena NINJA&l&f!", "No evento, voc�s receberam um kit com items", "Com o tempo com jogadores diminuindo voc�s ser�o teleportados a um novo local.", "Todos tem 20 segundos para se preparar.", "Todos tem a habilidade do kit NINJA durante o evento", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fN�o tente escapar da arena pvp.", "&fN�o fa�a mais times que o promotor deixar." , "Come�ando evento! Boa sorte"), WaveWarp.ARENANINJA.getLocation()),
    ONEVSONE("OnevsOne", Arrays.asList("Bem vindo ao evento 1vs1!", "No in�cio do evento voc�s ser�o puxados de 2 em 2 para uma luta individual", "Quem ganhar voltar� ao evento!", "E quem perder voltar� ao spawn!", "Come�ando evento. Divirta-se"), WaveWarp.ONEVSONE.getLocation()),
    CUSTOM("Custom", Arrays.asList("Ol� a todos! Bem vindo ao &c&lCUSTOM&l&f!", "No evento, voc�s receberam um kit com items escolhidos pelo staff", "O Staff que decidir� como ser� o evento.", "Todos tem 20 segundos para se preparar.", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fN�o tente escapar da arena.", "&fN�o fa�a mais times que o promotor deixar." , "Come�ando evento! Boa sorte"), WaveWarp.CUSTOM.getLocation()),

    ;
    private final String name;
    private final List<String> explicacao;
    private final Location location;
    EventType2(String name, List<String> explicacao, Location location) {
        this.name = name;
        this.explicacao = explicacao;
        this.location = location;
    }

    public static EventType2 getEventoByName(String name) {
        for (EventType2 evento : EventType2.values()) {
        	 if (evento.getName().equalsIgnoreCase(name))
                 return evento;
         }
        return null;
    }
    public String getName() {
		return name;
	}
    public List<String> getExplicacao() {
		return explicacao;
	}
    public Location getLocation() {
		return location;
	}

    public static void explicarEvento(EventType2 evento) {
        List<String> explic = evento.getExplicacao();
        int actualsec = 1;
        for (String message : explic) {
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            	EventoUtils.getEventoPlayers().forEach(p -> {
               p.sendMessage("�6�lVOLTZ" + " �f" + ChatColor.translateAlternateColorCodes('&', message));
               p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        });
            	
    }, actualsec * 20);
            actualsec += 5;
        }
}

public static void tirarEfeitos(final Player p) {
    p.removePotionEffect(PotionEffectType.ABSORPTION);
    p.removePotionEffect(PotionEffectType.BLINDNESS);
    p.removePotionEffect(PotionEffectType.NAUSEA);
    p.removePotionEffect(PotionEffectType.RESISTANCE);
    p.removePotionEffect(PotionEffectType.HASTE);
    p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
    p.removePotionEffect(PotionEffectType.INSTANT_DAMAGE);
    p.removePotionEffect(PotionEffectType.INSTANT_HEALTH);
    p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
    p.removePotionEffect(PotionEffectType.HUNGER);
    p.removePotionEffect(PotionEffectType.INSTANT_DAMAGE);
    p.removePotionEffect(PotionEffectType.INVISIBILITY);
    p.removePotionEffect(PotionEffectType.JUMP_BOOST);
    p.removePotionEffect(PotionEffectType.NIGHT_VISION);
    p.removePotionEffect(PotionEffectType.POISON);
    p.removePotionEffect(PotionEffectType.REGENERATION);
    p.removePotionEffect(PotionEffectType.SATURATION);
    p.removePotionEffect(PotionEffectType.SLOWNESS);
    p.removePotionEffect(PotionEffectType.STRENGTH);
    p.removePotionEffect(PotionEffectType.SPEED);
    p.removePotionEffect(PotionEffectType.WATER_BREATHING);
    p.removePotionEffect(PotionEffectType.WEAKNESS);
    p.removePotionEffect(PotionEffectType.WITHER);
}


public static void darEfeito(final Player p, final PotionEffectType tipo, final int duracao, final int level) {
      p.addPotionEffect(new PotionEffect(tipo, 20 * duracao, level));
  }
	public static String cor(String s) {
		return s.replace("&", "�");
	}
}

// mdr 801.5 100 519.5
// lava 641.5 118 518.5
// pvp 732.5 80 521.5
// 1v1 868.5 95 457.5
