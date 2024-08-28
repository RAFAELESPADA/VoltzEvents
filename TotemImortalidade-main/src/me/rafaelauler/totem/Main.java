package me.rafaelauler.totem;





import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.rafaelauler.totem.TotemEvents2;









public class Main extends JavaPlugin implements Listener {

    public static Main plugin;
    @Override
    public void onEnable() {
    	  plugin = this;
registerEvents();
PluginManager pm = Bukkit.getPluginManager();
Bukkit.getConsoleSender().sendMessage("[TOTEM3] EVENTOS INICIANDO");

pm.registerEvents((Listener)new me.rafaelauler.totem.TotemEvents2(), this);
Bukkit.getConsoleSender().sendMessage("[TOTEM3] EVENTOS INICIADO");
    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[TOTEM] PLUGIN DESLIGADO COM SUCESSO");
    }

    public void registerEvents(){

Bukkit.getConsoleSender().sendMessage("[TOTEM] EVENTOS INICIANDO v2");
    }

public static void darEfeito(Player p, PotionEffectType tipo, int duracao, int level)
/*     */   {
/* 349 */     p.addPotionEffect(new PotionEffect(tipo, 20 * duracao, level));
/*     */   }
    


        }

