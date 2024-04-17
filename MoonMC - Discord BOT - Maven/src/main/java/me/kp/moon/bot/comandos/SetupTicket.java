package me.kp.moon.bot.comandos;

import me.kp.moon.bot.enums.GlobalVariables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SetupTicket extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getMember() == null) return;
        if (event.getAuthor().isBot()) return;
        String[] message = event.getMessage().getContentRaw().split(" ");
        String command = message[0];
        if (command.equalsIgnoreCase(".setup-ticket")) {
            if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                event.getMessage().reply("Você não tem permissão para isso.").queue();
                return;
            }
            if (event.getMessage().getMentionedChannels().isEmpty()) {
                event.getMessage().reply("Por favor, mencione um canal.").queue();
                return;
            }
            MessageChannel channel = event.getMessage().getMentionedChannels().get(0);
            channel.sendMessage(new EmbedBuilder()
                    .setAuthor("🚀 MoonMC - Sistema de Tickets", null, event.getGuild().getIconUrl())
                    .setDescription("Reaja essa mensagem para abrir um ticket.")
                    .setFooter(GlobalVariables.footer)
                    .setColor(GlobalVariables.mainColor)
                    .build()).queue(msg -> msg.addReaction("📩").queue(),
                    err -> event.getMessage().reply("Não foi possível enviar uma mensagem ao canal mencionado.").queue());
        }
    }
}
