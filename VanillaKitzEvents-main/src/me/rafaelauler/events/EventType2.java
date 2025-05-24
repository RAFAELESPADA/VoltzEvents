package me.rafaelauler.events;






import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;

import lombok.Getter;

@Getter
public enum EventType2 {


<<<<<<< HEAD
    ARENAPVP("ArenaPvP", Arrays.asList("Olá a todos! Bem vindo ao &c&lArena PvP&l&f!", "No evento, vocês receberam um kit com items", "Com o tempo com jogadores diminuindo vocês serão teleportados a um novo local.", "Todos tem 20 segundos para se preparar.", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fNão tente escapar da arena pvp.", "&fNão faça mais times que o promotor deixar." , "Começando evento! Boa sorte"), WaveWarp.ARENAPVP.getLocation()),
    LAVA("LavaChallenge", Arrays.asList("Olá a todos! Bem vindo ao evento &4&lLava Challenge", "&fAo inicio do evento todos receberam um kit com sopas.", "Todos precisarão tankar a lava que vocês serão colocados!", "Suas sopas NÃO serão reabastecidas durante o evento, então use elas com cautela.", "Vocês terão 10 segundos para se prepararem!", "Começando evento! Boa sorte!"), WaveWarp.LAVACHALLENGE.getLocation()),
    MESTREMANDOU("MestreMandou", Arrays.asList("Olá a todos! Bem vindo ao evento &4&lMestre Mandou", "&fAo inicio do evento todos serão dados ordens aleatórias.", "Como craftar certos itens e fazer certas ações!", "Se não cumprir a ação dentro do prazo determinado será kikado.", "Vocês terão 10 segundos para se prepararem!", "Começando evento! Boa sorte!"), WaveWarp.MESTREMANDOU.getLocation()),
    SUMO("Sumo", Arrays.asList("Bem vindo ao evento Sumô!", "No início do evento vocês serão puxados de 2 em 2 para uma luta individual", "Quem ganhar voltará ao evento!", "E quem perder voltará ao spawn!", "Começando evento. Divirta-se"), WaveWarp.SUMO.getLocation()),
    ARENANINJA("ArenaNinja", Arrays.asList("Olá a todos! Bem vindo ao &c&lArena NINJA&l&f!", "No evento, vocês receberam um kit com items", "Com o tempo com jogadores diminuindo vocês serão teleportados a um novo local.", "Todos tem 20 segundos para se preparar.", "Todos tem a habilidade do kit NINJA durante o evento", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fNão tente escapar da arena pvp.", "&fNão faça mais times que o promotor deixar." , "Começando evento! Boa sorte"), WaveWarp.ARENANINJA.getLocation()),
    ONEVSONE("OnevsOne", Arrays.asList("Bem vindo ao evento 1vs1!", "No início do evento vocês serão puxados de 2 em 2 para uma luta individual", "Quem ganhar voltará ao evento!", "E quem perder voltará ao spawn!", "Começando evento. Divirta-se"), WaveWarp.ONEVSONE.getLocation()),
    CUSTOM("Custom", Arrays.asList("Olá a todos! Bem vindo ao &c&lCUSTOM&l&f!", "No evento, vocês receberam um kit com items escolhidos pelo staff", "O Staff que decidirá como será o evento.", "Todos tem 20 segundos para se preparar.", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fNão tente escapar da arena.", "&fNão faça mais times que o promotor deixar." , "Começando evento! Boa sorte"), WaveWarp.CUSTOM.getLocation()),
=======
    ARENAPVP("ArenaPvP", Arrays.asList("OlÃ¡ a todos! Bem vindo ao &c&lArena PvP&l&f!", "No evento, vocÃªs receberam um kit com items", "Com o tempo com jogadores diminuindo vocÃªs serÃ£o teleportados a um novo local.", "Todos tem 20 segundos para se preparar.", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fNÃ£o tente escapar da arena pvp.", "&fNÃ£o faÃ§a mais times que o promotor deixar." , "ComeÃ§ando evento! Boa sorte"), WaveWarp.ARENAPVP.getLocation()),
    LAVA("LavaChallenge", Arrays.asList("OlÃ¡ a todos! Bem vindo ao evento &4&lLava Challenge", "&fAo inicio do evento todos receberam um kit com sopas.", "Todos precisarÃ£o tankar a lava que vocÃªs serÃ£o colocados!", "Suas sopas NÃƒO serÃ£o reabastecidas durante o evento, entÃ£o use elas com cautela.", "VocÃªs terÃ£o 10 segundos para se prepararem!", "ComeÃ§ando evento! Boa sorte!"), WaveWarp.LAVACHALLENGE.getLocation()),
    MESTREMANDOU("MestreMandou", Arrays.asList("OlÃ¡ a todos! Bem vindo ao evento &4&lMestre Mandou", "&fAo inicio do evento todos serÃ£o dados ordens aleatÃ³rias.", "Como craftar certos itens e fazer certas aÃ§Ãµes!", "Se nÃ£o cumprir a aÃ§Ã£o dentro do prazo determinado serÃ¡ kikado.", "VocÃªs terÃ£o 10 segundos para se prepararem!", "ComeÃ§ando evento! Boa sorte!"), WaveWarp.MESTREMANDOU.getLocation()),
    SUMO("Sumo", Arrays.asList("Bem vindo ao evento SumÃ´!", "No inÃ­cio do evento vocÃªs serÃ£o puxados de 2 em 2 para uma luta individual", "Quem ganhar voltarÃ¡ ao evento!", "E quem perder voltarÃ¡ ao spawn!", "ComeÃ§ando evento. Divirta-se"), WaveWarp.SUMO.getLocation()),
    ARENANINJA("ArenaNinja", Arrays.asList("OlÃ¡ a todos! Bem vindo ao &c&lArena NINJA&l&f!", "No evento, vocÃªs receberam um kit com items", "Com o tempo com jogadores diminuindo vocÃªs serÃ£o teleportados a um novo local.", "Todos tem 20 segundos para se preparar.", "Todos tem a habilidade do kit NINJA durante o evento", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fNÃ£o tente escapar da arena pvp.", "&fNÃ£o faÃ§a mais times que o promotor deixar." , "ComeÃ§ando evento! Boa sorte"), WaveWarp.ARENANINJA.getLocation()),
    ONEVSONE("OnevsOne", Arrays.asList("Bem vindo ao evento 1vs1!", "No inÃ­cio do evento vocÃªs serÃ£o puxados de 2 em 2 para uma luta individual", "Quem ganhar voltarÃ¡ ao evento!", "E quem perder voltarÃ¡ ao spawn!", "ComeÃ§ando evento. Divirta-se"), WaveWarp.ONEVSONE.getLocation()),
    CUSTOM("Custom", Arrays.asList("OlÃ¡ a todos! Bem vindo ao &c&lCUSTOM&l&f!", "No evento, vocÃªs receberam um kit com items escolhidos pelo staff", "O Staff que decidirÃ¡ como serÃ¡ o evento.", "Todos tem 20 segundos para se preparar.", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fNÃ£o tente escapar da arena.", "&fNÃ£o faÃ§a mais times que o promotor deixar." , "ComeÃ§ando evento! Boa sorte"), WaveWarp.CUSTOM.getLocation()),
>>>>>>> 5b4d6e78b041025f8b3790b564ac214e917a0cef

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
               p.sendMessage("§6§lKOMBO" + " §f" + ChatColor.translateAlternateColorCodes('&', message));
               p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        });
            	
    }, actualsec * 20);
            actualsec += 5;
        }
}
}


// mdr 801.5 100 519.5
// lava 641.5 118 518.5
// pvp 732.5 80 521.5
// 1v1 868.5 95 457.5
