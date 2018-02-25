package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;
import bot.discord.DiscordBot.main.SetupManager;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TrustCommand extends Command {

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    if(!e.getMember().equals(e.getGuild().getOwner())) {
      mb.append("Only the server owner can use this command! :anguished:");
      sendMessage(e, mb.build());
      return;
    }
    if(getArguments(e.getMessage()).length < 2) {
      mb.append("You need to add an argument buddy! *Doesn't even know how to use his bot* :thinking:");
      sendMessage(e, mb.build());
      return;
    }
    String[] args = getArguments(e.getMessage());
    Member trusted = e.getGuild().getMember(e.getAuthor());
    if(args[1].matches("[1-9]*")) {
      trusted = e.getGuild().getMemberById(args[1]);
    } else {
      trusted = e.getMessage().getMentionedMembers().get(0);
    }
    
    String arg = getArguments(e.getMessage())[1];
    mb.append("User with ID: ")
      .append(trusted.getUser().getId())
      .append(" successfully added to trusted users!");
    
    
    SetupManager.getInstance().getSetup().addTrusted(trusted.getUser().getId());
    SetupManager.getInstance().saveSettings();
    sendMessage(e, mb.build());
  }

  @Override
  protected void commandHelp(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    mb.append("This command is only for server admins");
    sendMessage(e, mb.build());
  }
  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("trust");
  }

}
