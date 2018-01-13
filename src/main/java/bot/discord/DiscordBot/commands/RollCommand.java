package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import bot.discord.DiscordBot.main.Setup;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RollCommand extends Command{

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();

    if(getArguments(e.getMessage()).length > 1) {
      commandHelp(e);
      return;
    }
    mb.append(e.getAuthor().getAsMention())
      .append(" rolled `")
      .append(roll(e))
      .append("` !");
    sendMessage(e, mb.build());
  }

  private int roll(MessageReceivedEvent e) {
    Random rand = new Random();
    rand.setSeed(Long.parseLong(e.getMessageId()));
    return (rand.nextInt(100) + 1);
  }
  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("roll");
  }
  
  @Override
  protected void commandHelp(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    mb.append("Welcome to the roll command!\n")
      .append("Type `" + Setup.BOT_PREFIX + "roll` to roll from 0-100 (inclusive)\n");
    sendMessage(e, mb.build());
  }

}
