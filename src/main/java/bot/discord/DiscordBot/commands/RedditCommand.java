package bot.discord.DiscordBot.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bot.discord.DiscordBot.main.Setup;
import bot.discord.DiscordBot.utilities.RedditEmbed;
import bot.discord.DiscordBot.utilities.RedditRequest;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RedditCommand extends Command {

	//TODO cleanup
	@Override
	public void exeCommand(MessageReceivedEvent e) {
		String[] args = getArguments(e.getMessage());
		MessageBuilder mb = new MessageBuilder();
		
		
		List<MessageEmbed> messageEmbedList = new ArrayList<MessageEmbed>();
		
		if(args.length <= 1) {
			mb.append("This is the reddit command! Hooray!\n");
			mb.append("To use it type one of the following\n");
			mb.append("1 - \"" + Setup.BOT_PREFIX + "reddit trending\" - Displays the 5 most trending subreddits");
			sendMessage(e, mb.build());
			return;
		}
		switch(args[1]) {
			case "trending":
				Collections.addAll(messageEmbedList, getTrending());
				break;
			default:
				mb.append("What you typed doesn't match a command :frowning2:");
				sendMessage(e, mb.build());
				return;
		}
		
		for(MessageEmbed me : messageEmbedList) {
			sendMessage(e, me);
		}
		
	}
	
	private MessageEmbed[] getTrending() {
		RedditRequest rr = new RedditRequest();
		RedditEmbed re = new RedditEmbed();
		
		String[] trending = null;
		
		
		try {
			trending = rr.getTrending();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return re.embedURL(trending);
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("reddit");
	}

}
