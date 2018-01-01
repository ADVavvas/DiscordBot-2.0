
/**
 * Inspired buy Yui bot by DV8FromTheWorld
 */

package bot.discord.DiscordBot.commands;

import bot.discord.DiscordBot.main.Setup;
import java.util.List;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
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
  	if(e.getMessage().getContentDisplay().charAt(0) != Setup.BOT_PREFIX) {
  		return;
  	}
  	if(isCommand(e.getMessage())) {
  		exeCommand(e);
  	}
  }

  public boolean isCommand(Message m) {
    return getCommandAliases().contains(getArguments(m)[0]);
  }
  
  public boolean containsCommand(String commandAlias) {
    	if(getCommandAliases().contains(commandAlias)) {
    		return true;
    	}
    	return false;
  }
  
  public String[] getArguments(Message m) {
  	String args = m.getContentDisplay().substring(1);
    return args.split(" ");
  }
  
  public Message sendMessage(MessageReceivedEvent e, Message message) {
    return e.getTextChannel().sendMessage(message).complete();
  }
  
  public Message sendMessage(MessageReceivedEvent e, MessageEmbed messageEmbed) {
  	return e.getTextChannel().sendMessage(messageEmbed).complete();
  }
  
//  public void deleteMessage(MessageEmbedEvent e, String[] id) {
//  	for(String i : id) {
//  		deleteMessage(e,i);
//  	}
//  }
//  public void deleteMessage(MessageEmbedEvent e, String id) {
//  	e.getTextChannel().deleteMessageById(id).complete();
//  }
  
}
