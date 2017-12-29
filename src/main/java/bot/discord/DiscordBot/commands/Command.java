package bot.discord.DiscordBot.commands;

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
    if(isCommand(e.getMessage())) {
      exeCommand(e);
    }
  }
  
  public boolean isCommand(Message m) {
    return getCommandAliases().contains(m.getContentDisplay());
  }
  
  public boolean containsCommand(String commandAlias) {
    	if(getCommandAliases().contains(commandAlias)) {
    		return true;
    	}
    	return false;
  }
  
  public String[] getArguments(Message m) {
    return m.toString().split(" ");
  }
  
  public Message sendMessage(MessageReceivedEvent e, Message message) {
    return e.getTextChannel().sendMessage(message).complete();
  }
}
