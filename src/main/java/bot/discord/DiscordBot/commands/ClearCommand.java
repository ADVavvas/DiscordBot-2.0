package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ClearCommand extends Command {

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    Member member = e.getMember();
    MessageBuilder mb = new MessageBuilder();
    
    if(!member.hasPermission(Permission.MESSAGE_MANAGE)) {
      mb.append("You don't have the \"Message Manage\" permission :cold_sweat:");
      sendMessage(e, mb.build());
      return;
    }
    if(e.getTextChannel().getIterableHistory().complete().size() >= 2) {
      e.getTextChannel().deleteMessages(e.getTextChannel().getIterableHistory().limit(100).complete()).complete();
    } else {
      e.getTextChannel().deleteMessageById(e.getTextChannel().getLatestMessageId()).complete();
    }
    
  }

  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("clearchat" , "clear");
  }

}
