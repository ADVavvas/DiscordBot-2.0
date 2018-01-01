package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SearchCommand extends Command {

	@Override
	public void exeCommand(MessageReceivedEvent e) {
		
		System.out.println("searched");
		
		String[] args = getArguments(e.getMessage());

		MessageBuilder mb = new MessageBuilder();
		
		if(args.length <= 1) {
			mb.append("Add some search parameters");
			sendMessage(e, mb.build());
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

}
