package bot.discord.DiscordBot.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bot.discord.DiscordBot.main.Setup;
import bot.discord.DiscordBot.utilities.Reddit;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import obsolete.RedditEmbed;
import obsolete.RedditRequest;

public class RedditCommand extends Command {

	//TODO cleanup
	@Override
	public void exeCommand(MessageReceivedEvent e) {
		String[] args = getArguments(e.getMessage());
		MessageBuilder mb = new MessageBuilder();
		
		if(args.length <= 1) {
			mb.append("This is the reddit command! Hooray!\n");
			mb.append("To use it type one of the following\n");
			mb.append("1 - \"" + Setup.BOT_PREFIX + "reddit joke\" - Displays a random joke from /r/Jokes!");
			sendMessage(e, mb.build());
			return;
		}
		switch(args[1]) {
			case "joke":
			case "jokes":
				sendMessage(e, Reddit.getRandom("jokes"));
				break;
			case "powerlifting":
			  sendMessage(e, Reddit.getRandom("powerlifting"));
			  break;
			default:
				mb.append("What you typed doesn't match a command :frowning2:");
				sendMessage(e, mb.build());
				return;
		}
		
	}
	
	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("reddit");
	}

}
