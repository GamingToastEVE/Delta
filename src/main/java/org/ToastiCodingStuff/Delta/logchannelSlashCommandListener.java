package org.ToastiCodingStuff.Delta;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class logchannelSlashCommandListener extends ListenerAdapter {

    private final databaseHandler handler;

    public logchannelSlashCommandListener(databaseHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onSlashCommandInteraction (SlashCommandInteractionEvent event) {
        if (event.getName().equals("set-log-channel")) {
            Channel channel = event.getOption("logchannel").getAsChannel();
            String channelID = handler.setLogChannel(event.getGuild().getId(), channel.getId());
            if (!channelID.equals("Error")) {
                event.reply("Set log channel to: " + channel.getAsMention()).queue();
            }else {
                event.reply("There was an Error. Please try again or contact the developer!");
            }
        }
        if (event.getName().equals("get-log-channel")) {
            if (handler.hasLogChannel(event.getGuild().getId())) {
                Channel channel = event.getGuild().getTextChannelById(handler.getLogChannelID(event.getGuild().getId()));
                event.reply("Log Channel set to: " + channel.getAsMention()).queue();
                return;
            }
            event.reply("Couldnt find a Log Channel.").queue();
        }
    }
}
