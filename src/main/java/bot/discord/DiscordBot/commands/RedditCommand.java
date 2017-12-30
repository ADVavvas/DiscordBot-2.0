package bot.discord.DiscordBot.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import bot.discord.DiscordBot.main.Setup;
import bot.discord.DiscordBot.utilities.RedditEmbed;
import bot.discord.DiscordBot.utilities.RedditRequest;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RedditCommand extends Command {

	
	@Override
	public void exeCommand(MessageReceivedEvent e) {
		String[] args = getArguments(e.getMessage());
		MessageBuilder mb = new MessageBuilder();
		RedditRequest rr = new RedditRequest();
		RedditEmbed re = new RedditEmbed();
		
		if(args.length <= 1) {
			mb.append("This is the reddit command! Hooray!\n");
			mb.append("To use it type one of the following\n");
			mb.append("1 - \"" + Setup.BOT_PREFIX + "reddit trending\" - Displays the 5 most trending subreddits");
			sendMessage(e, mb.build());
			return;
		}
		
		String commandURL = null;
		switch(args[1]) {
			case "trending":
				re.embedURL("r/Frugal/about.json"); //testing
				break;
		}
		
		if(commandURL == null) {
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
