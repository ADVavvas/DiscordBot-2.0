package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;

import bot.discord.DiscordBot.main.Setup;
import bot.discord.DiscordBot.utilities.Reddit;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RedditCommand extends Command {

	//TODO cleanup
	@Override
	public void exeCommand(MessageReceivedEvent e) {
		String[] args = getArguments(e.getMessage());
		MessageBuilder mb = new MessageBuilder();
		
		if(args.length <= 1) {
		  commandHelp(e);
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
	protected void commandHelp(MessageReceivedEvent e) {
	  MessageBuilder mb = new MessageBuilder();
    mb.append("This is the reddit command! Hooray!\n")
      .append("Type one of the following:\n")
      .append("1 - `" + Setup.BOT_PREFIX + "reddit joke` - Displays a random joke from /r/Jokes!\n")
      .append("2 - `" + Setup.BOT_PREFIX + "reddit powerlifting` - Displays a random post from /r/powerlifting (retarded command)!\n");
    sendMessage(e, mb.build());
	}
	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("reddit");
	}

}
