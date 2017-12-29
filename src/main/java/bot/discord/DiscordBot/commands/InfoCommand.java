package bot.discord.DiscordBot.commands;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class InfoCommand extends Command {

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    mb.append("Welcome to DiscordBot 2.0 - What can I help you with");
    sendMessage(e, mb.build());
  }

  @Override
  public List<String> getCommandAliases() {
    List<String> aliases = new ArrayList<String>();
    aliases.add("info");
    aliases.add("about");
    return aliases;
  }

}
