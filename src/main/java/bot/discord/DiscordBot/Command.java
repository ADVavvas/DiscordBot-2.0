package bot.discord.DiscordBot;

import java.util.List;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public abstract class Command extends ListenerAdapter {
	
  public abstract void exeCommand(MessageReceivedEvent e);
  public abstract List<String> getCommandAliases();
  @Override
  public void onMessageReceived(MessageReceivedEvent e) {
  	if(e.getAuthor().isBot()) {
  		return;
  	}
  }
  
  public void isCommand(Message m) {
  }
  
  public boolean containsCommand(String commandAlias) {
  	if(getCommandAliases().contains(commandAlias)) {
  		return true;
  	}
  	return false;
  }
}
