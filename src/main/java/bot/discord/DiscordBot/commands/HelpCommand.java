package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;

import bot.discord.DiscordBot.main.Setup;
import bot.discord.DiscordBot.main.SetupManager;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand extends Command {

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    String[] arguments = getArguments(e.getMessage());
    
    if(arguments.length != 2) {
      commandHelp(e);
      return;
    }
    
    switch(arguments[1]) {
      case "clear":
      case "clearchat":
        new ClearCommand().commandHelp(e);
        break;
      case "reddit":
        new RedditCommand().commandHelp(e);
        break;
      case "roll":
        new RollCommand().commandHelp(e);
        break;
      case "role":
      case "roles":
        new RoleCommand().commandHelp(e);
        break;
      case "search":
      case "google":
        new SearchCommand().commandHelp(e);
        break;
      case "trust":
        new TrustCommand().commandHelp(e);
        break;
      case "info":
      case "about":
        new InfoCommand().commandHelp(e);
        break;
      default:
        commandHelp(e);
    }
  }

  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("help");
  }

  @Override
  protected void commandHelp(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    mb.append("Type `" + Setup.BOT_PREFIX + "help` and the alias of a command to get more info about the command!\n");
    for(Command command : SetupManager.getInstance().getSetup().getCommands()) {
      boolean first = true;
      
      for(String alias : command.getCommandAliases()) {
        
        if(first) {
          first = false;
        } else {
          mb.append(" - ");
        }
        mb.append(Setup.BOT_PREFIX);
        mb.append("`")
          .append(alias)
          .append("`");
      }
      
      mb.append("\n");
    }
    sendMessage(e, mb.build());
  }
}
