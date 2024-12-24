package me.rafaelauler.events;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardBuilder {

	
private static String text = "";
private static String text2 = "";
private static String text3 = "";
private static String text4 = "";

private static String textlava = "";
private static String text5 = "";
private static String text6 = "";
private static String textlobby = "";
private static String text7 = "";
private static String text9 = "";
private static String textC = "";

private static WaveAnimation waveAnimation44;


private static String textL = "";
private static String textx1 = "";
private static WaveAnimation waveAnimation;

private static WaveAnimation waveAnimationL;
private static WaveAnimation waveAnimation2;
private static WaveAnimation waveAnimation3;
private static WaveAnimation waveAnimation4;
private static WaveAnimation waveAnimation5;
private static WaveAnimation waveAnimation6;
private static WaveAnimation waveAnimationC;

private static WaveAnimation waveAnimationx1;
private static WaveAnimation waveAnimationlava;
public static void init() {

    waveAnimationL = new WaveAnimation("EVENTOS" , "§b§l" , "§f§l" , "§e§l");
    waveAnimation44 = new WaveAnimation("SUMO" , "§b§l" , "§f§l" , "§e§l");
    waveAnimation = new WaveAnimation("ARENAPVP" , "§b§l" , "§f§l" , "§e§l");
    waveAnimation2 = new WaveAnimation("ARENA NINJA" , "§b§l" , "§3§l" , "§f§l");
    waveAnimationx1 = new WaveAnimation("1V1" , "§b§l" , "§f§l" , "§e§l");
    waveAnimation4 = new WaveAnimation("ARENA DAMAGE" , "§b§l" , "§f§l" , "§e§l");
    waveAnimation3 = new WaveAnimation("MESTRE MANDOU" , "§b§l" , "§f§l" , "§e§l");
    waveAnimationC = new WaveAnimation("EVENTO CUSTOMIZADO" , "§b§l" , "§f§l" , "§e§l");
    
    waveAnimation5 = new WaveAnimation("COPA" , "§b§l" , "§f§l" , "§e§l");
    waveAnimation6 = new WaveAnimation("ARENA ANCHOR" , "§b§l" , "§f§l" , "§e§l");
    waveAnimationlava = new WaveAnimation("LAVA" , "§b§l" , "§f§l" , "§e§l");

    textx1 = "EVENTOS";
    textL = "EVENTOS";
    text = "SUMO";
    textlobby = "ARENAPVP";
    text3 = "MESTRE MANDOU";
    textC = "EVENTO CUSTOMIZADO";
    text4 = "ARENA DAMAGE";
    text2 = "ARENA NINJA";
    text6 = "ARENA ANCHOR";
    text5 = "COPA";
    Bukkit.getScheduler().runTaskTimer(Main.instance, new Runnable() {
          public void run() {
            ScoreboardBuilder.textL = ScoreboardBuilder.waveAnimationL.next();
            ScoreboardBuilder.textlobby = ScoreboardBuilder.waveAnimation.next();
            ScoreboardBuilder.textC = ScoreboardBuilder.waveAnimationC.next();
            
            ScoreboardBuilder.text2 = ScoreboardBuilder.waveAnimation2.next();
            ScoreboardBuilder.text3 = ScoreboardBuilder.waveAnimation3.next();
            ScoreboardBuilder.text4 = ScoreboardBuilder.waveAnimation4.next();
            ScoreboardBuilder.text5 = ScoreboardBuilder.waveAnimation5.next();
            ScoreboardBuilder.text6 = ScoreboardBuilder.waveAnimation6.next();
            ScoreboardBuilder.text = ScoreboardBuilder.waveAnimation44.next();
            ScoreboardBuilder.textx1 = ScoreboardBuilder.waveAnimationx1.next();

            ScoreboardBuilder.textlava = ScoreboardBuilder.waveAnimationlava.next();
            ScoreboardBuilder.textL = ScoreboardBuilder.waveAnimationL.next();
            for (Player onlines : Bukkit.getOnlinePlayers()) {
              if (onlines == null)
                continue; 
              if (!onlines.isOnline())
                continue; 
              if (onlines.isDead())
                continue; 
              Scoreboard score = onlines.getScoreboard();
              if (score == null)
                continue; 
              Objective objective = score.getObjective(DisplaySlot.SIDEBAR);
              if (objective == null)
                continue; 
              if (WaveWarp.SPAWN.hasPlayer(onlines.getName())) {
              objective.setDisplayName(ScoreboardBuilder.textL);
              }
              else if (WaveWarp.ARENAPVP.hasPlayer(onlines.getName())) {
                  objective.setDisplayName(ScoreboardBuilder.textlobby);
                  }
              else if (WaveWarp.CUSTOM.hasPlayer(onlines.getName())) {
                  objective.setDisplayName(ScoreboardBuilder.textC);
                  }
              else if (WaveWarp.MESTREMANDOU.hasPlayer(onlines.getName())) {
                  objective.setDisplayName(ScoreboardBuilder.text3);
                  }
              else if (WaveWarp.SUMO.hasPlayer(onlines.getName())) {
                  objective.setDisplayName(ScoreboardBuilder.text);
                  }
              else if (WaveWarp.CUSTOM.hasPlayer(onlines.getName())) {
                  objective.setDisplayName(ScoreboardBuilder.textC);
                  }
              else if (WaveWarp.ARENANINJA.hasPlayer(onlines.getName())) {
                  objective.setDisplayName(ScoreboardBuilder.text2);
                  }
              else if (WaveWarp.LAVACHALLENGE.hasPlayer(onlines.getName())) {
                  objective.setDisplayName(ScoreboardBuilder.textlava);
                  }
              
              
             
            	  
              }
            } 
          
        },  20L, 1L);
  }
	public void build(Player player) {
		 {
			 
			 if (WaveWarp.SPAWN.hasPlayer(player.getName())) {
				 player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective objective = scoreboard.registerNewObjective("pvp", "dummy");
					
					objective.setDisplayName(textL);
					objective.setDisplaySlot(DisplaySlot.SIDEBAR);
					
					String l12 = " §3Você está";
					String l11 = " §3Na sala de  eventos";
					String l10 = " §b";
					String l9 = " §bO evento ja vai começar";
					
					
					String l5 = "§1";
					String lb = " §aQualquer dúvida";
					String lb2 = " §aPergunte ao";
					String l4 = " §aPromotor";
					String l3 = " §f";
					String lx = " §f";
					String l2 = "§0";
					String l1 = "§ekombopvp.com";
					
		
					objective.getScore(l12).setScore(11);
					objective.getScore(l11).setScore(10);
					objective.getScore(l10).setScore(9);
					objective.getScore(l9).setScore(8);
					
					objective.getScore(l5).setScore(7);
					objective.getScore(lb).setScore(6);
					objective.getScore(lb2).setScore(5);
					objective.getScore(l4).setScore(4);
					objective.getScore(l3).setScore(3);
					objective.getScore(lx).setScore(2);
					objective.getScore(l2).setScore(1);
					objective.getScore(l1).setScore(0);
					
					player.setScoreboard(scoreboard);
				
					
			 } 
			 if (WaveWarp.ARENAPVP.hasPlayer(player.getName())) {
				 player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective objective = scoreboard.registerNewObjective("pvpb", "dummyb");
					
					objective.setDisplayName(text);
					objective.setDisplaySlot(DisplaySlot.SIDEBAR);
					
					String l12 = "§3Jogando ArenaPvP";
					String l11 = " §aNo §6§lKombo";
					String l10 = " §aMate todos";
					String l9 = " §aE seja o ultimo";
					
					
					String l5 = "§aSobrevivente";
					String l2 = "§0";
					String l1 = "§ekombopvp.com";
					
				//	if (KitManager.getPlayer(player.getName()).hasKit()) {
		
					objective.getScore(l12).setScore(6);
					objective.getScore(l11).setScore(5);
					objective.getScore(l10).setScore(4);
					objective.getScore(l9).setScore(3);
					
					objective.getScore(l5).setScore(2);
					objective.getScore(l2).setScore(1);
					objective.getScore(l1).setScore(0);
					
					player.setScoreboard(scoreboard);
				
					
			 } 
			 if (WaveWarp.CUSTOM.hasPlayer(player.getName())) {
				 player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective objective = scoreboard.registerNewObjective("pvpba", "dummyba");
					
					objective.setDisplayName(text);
					objective.setDisplaySlot(DisplaySlot.SIDEBAR);
					
					String l12 = "§3Jogando Evento §b§lCustom";
					String l11 = " §aNo §6§lKombo";
					String l10 = " §aMate todos";
					String l9 = " §aE seja o ultimo";
					
					
					String l5 = "§aSobrevivente";
					String l2 = "§0";
					String l1 = "§ekombopvp.com";
					
				//	if (KitManager.getPlayer(player.getName()).hasKit()) {
		
					objective.getScore(l12).setScore(6);
					objective.getScore(l11).setScore(5);
					objective.getScore(l10).setScore(4);
					objective.getScore(l9).setScore(3);
					
					objective.getScore(l5).setScore(2);
					objective.getScore(l2).setScore(1);
					objective.getScore(l1).setScore(0);
					
					player.setScoreboard(scoreboard);
				
					
			 } 
			 if (WaveWarp.MESTREMANDOU.hasPlayer(player.getName())) {
				 player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective objective = scoreboard.registerNewObjective("pvpD", "dummyD");
					
					objective.setDisplayName(text3);
					objective.setDisplaySlot(DisplaySlot.SIDEBAR);
					
					String l12 = "§3Jogando §a§lMestreMandou";
					String l11 = " §aNo §6§lKombo";
					String l10 = " §aSiga todas as ordens";
					String l9 = " §aDo promotor";
					
					
					String l5 = "§aOu seja kikado do evento";
					String l2 = "§0";
					String l1 = "§ekombopvp.com";
					
				//	if (KitManager.getPlayer(player.getName()).hasKit()) {
		
					objective.getScore(l12).setScore(6);
					objective.getScore(l11).setScore(5);
					objective.getScore(l10).setScore(4);
					objective.getScore(l9).setScore(3);
					
					objective.getScore(l5).setScore(2);
					objective.getScore(l2).setScore(1);
					objective.getScore(l1).setScore(0);
					
					player.setScoreboard(scoreboard);
				
					
			 }
			 if (WaveWarp.SUMO.hasPlayer(player.getName())) {
				 player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective objective = scoreboard.registerNewObjective("pvpb", "dummyb");
					
					objective.setDisplayName(text4);
					objective.setDisplaySlot(DisplaySlot.SIDEBAR);
					
					String l12 = "§3Jogando Sumo";
					String l11 = " §aNo §6§lKombo";
					String l10 = " §aDerrote seus oponentes";
					String l9 = " §aE vença";
					
					
					String l2 = "§0";
					String l1 = "§ekombopvp.com";
					
				//	if (KitManager.getPlayer(player.getName()).hasKit()) {
		
					objective.getScore(l12).setScore(5);
					objective.getScore(l11).setScore(4);
					objective.getScore(l10).setScore(3);
					objective.getScore(l9).setScore(2);
					
					objective.getScore(l2).setScore(1);
					objective.getScore(l1).setScore(0);
					
					player.setScoreboard(scoreboard);
				
					
			 } 
			 else if (WaveWarp.LAVACHALLENGE.hasPlayer(player.getName())) {
					player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				 Scoreboard scoreboard2 = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective objective2 = scoreboard2.registerNewObjective("pvp3", "dummy3");
					
					objective2.setDisplayName(textlava);
					objective2.setDisplaySlot(DisplaySlot.SIDEBAR);
					String l9 = "§c";
					String l8 = "§fJogando §cEvento";
					String l7 = "§aLavaChallenge";
					String l6 = "§fTanke a lava";
					String l5 = "§fAté ser o ultimo";
					String l4 = "§fSobrevivente";
					String l3 = "§2Divirta-se";
					String l2 = "§0";
					String l1 = "§fkombopvp.com"; 
					objective2.getScore(l8).setScore(8);
					objective2.getScore(l9).setScore(8);
					objective2.getScore(l8).setScore(7);
					objective2.getScore(l7).setScore(6);
					objective2.getScore(l6).setScore(5);
					objective2.getScore(l5).setScore(4);
					objective2.getScore(l4).setScore(3);
					objective2.getScore(l3).setScore(2);
					objective2.getScore(l2).setScore(1);
					objective2.getScore(l1).setScore(0);
					player.setScoreboard(scoreboard2);
				
				
			 }
			 else if (WaveWarp.ARENANINJA.hasPlayer(player.getName())) {
					player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				 Scoreboard scoreboard2 = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective objective2 = scoreboard2.registerNewObjective("pvpl", "dummy3");
					
					objective2.setDisplayName(text2);
					
					objective2.setDisplaySlot(DisplaySlot.SIDEBAR);
					String l9 = "§c";
					String l8 = "§fJogando §cEvento";
					String l7 = "§aArenaNinja";
					String l6 = "§fMate todos ao seu redor";
					String l5 = "§fE seja o ultimo";
					String l4 = "§fAo sobreviver";
					String l3 = "§2Divirta-se";
					String l2 = "§0";
					String l1 = "§fkombopvp.com"; 
					objective2.getScore(l8).setScore(8);
					objective2.getScore(l9).setScore(8);
					objective2.getScore(l8).setScore(7);
					objective2.getScore(l7).setScore(6);
					objective2.getScore(l6).setScore(5);
					objective2.getScore(l5).setScore(4);
					objective2.getScore(l4).setScore(3);
					objective2.getScore(l3).setScore(2);
					objective2.getScore(l2).setScore(1);
					objective2.getScore(l1).setScore(0);
					player.setScoreboard(scoreboard2);
				
				
			 }

			 else if (WaveWarp.ONEVSONE.hasPlayer(player.getName())) {
					player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				 Scoreboard scoreboard2 = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective objective2 = scoreboard2.registerNewObjective("pvpg", "dummyg");
objective2.setDisplayName(textx1);
					
					objective2.setDisplaySlot(DisplaySlot.SIDEBAR);
					String l9 = "§c";
					String l8 = "§fJogando §cEvento";
					String l7 = "§a1V1";
					String l6 = "§fMate seu oponente";
					String l5 = "§fEm rodadas";
					String l4 = "§fE seja o ultimo";
					String l3 = "§2Ao sobreviver";
					String l2 = "§0";
					String l1 = "§fkombopvp.com"; 
					objective2.getScore(l8).setScore(8);
					objective2.getScore(l9).setScore(8);
					objective2.getScore(l8).setScore(7);
					objective2.getScore(l7).setScore(6);
					objective2.getScore(l6).setScore(5);
					objective2.getScore(l5).setScore(4);
					objective2.getScore(l4).setScore(3);
					objective2.getScore(l3).setScore(2);
					objective2.getScore(l2).setScore(1);
					objective2.getScore(l1).setScore(0);
					player.setScoreboard(scoreboard2);
			 }
			 
			 			 		 
		
		
		 }
	
	
		}
	}
