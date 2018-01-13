package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;

import bot.discord.DiscordBot.main.Setup;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SearchCommand extends Command {

	@Override
	public void exeCommand(MessageReceivedEvent e) {
		
		String[] args = getArguments(e.getMessage());

		MessageBuilder mb = new MessageBuilder();
		
		if(args.length == 1) {
			commandHelp(e);
			return;
		}
		
		mb.append("http://www.google.com/webhp?#q=");
		int i = 2;
		mb.append(args[i-1]);
		while(i<args.length) {
			mb.append("+");
			mb.append(args[i]);
			i++;
		}
		mb.append("&btnI=I");
		sendMessage(e, mb.build());
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("google", "search");
	}
	
	@Override
	protected void commandHelp(MessageReceivedEvent e) {
	  MessageBuilder mb = new MessageBuilder();
	  mb.append("Welcome to the search command!\n")
	    .append("Type `" + Setup.BOT_PREFIX + "search` or `" + Setup.BOT_PREFIX + "google` followed by some keyword to search google!\n");
    sendMessage(e, mb.build());
	}

}
