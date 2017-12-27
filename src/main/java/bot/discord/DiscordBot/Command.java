package bot.discord.DiscordBot;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public abstract class Command extends ListenerAdapter {
	
  public abstract void exeCommand(MessageReceivedEvent e);
  
  @Override
  public void onMessageReceived(MessageReceivedEvent e) {
  }
}
