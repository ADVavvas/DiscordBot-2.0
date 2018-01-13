package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class InfoCommand extends Command {

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    commandHelp(e);
  }

  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("info", "about");
  }
  
  @Override
  protected void commandHelp(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    mb.append("Welcome to DiscordBot 2.0 - What can I help you with\n")
      .append("Bot is currently being developed by Alex V.\n")
      .append("Check it out on GitHub!");
    sendMessage(e, mb.build());
  }
}
