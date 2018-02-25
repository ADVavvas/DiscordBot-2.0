package bot.discord.DiscordBot.commands.music;

import java.util.Arrays;
import java.util.List;

import bot.discord.DiscordBot.commands.Command;
import bot.discord.DiscordBot.main.Setup;
import bot.discord.DiscordBot.music.MusicManager;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;

public class JoinCommand extends Command {

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    Guild guild = e.getGuild();
    MusicManager manager = MusicController.getInstance().getMusicManager(guild);
    connect(e, manager);
  }

  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("join", "summon", "getyourassbackhere");
  }

  private void connect(MessageReceivedEvent e, MusicManager manager) {
    Guild guild = e.getGuild();
    VoiceChannel chan = null;
    
    chan = guild.getMember(e.getAuthor()).getVoiceState().getChannel();

    if (chan == null) {
      sendMessage(e, "Connect to a voice channel first!");
    }
    else {
        guild.getAudioManager().setSendingHandler(manager.getSendHandler());

        try {
          guild.getAudioManager().openAudioConnection(chan);
        } catch (PermissionException ex) {
          if (ex.getPermission() == Permission.VOICE_CONNECT) {
            sendMessage(e, "Doge does not have permission to connect to: " + chan.getName());
          }
        }
    }
  }

  @Override
  protected void commandHelp(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    mb.append("Type `" + Setup.BOT_PREFIX + "join/summon` for the bot to join the channel!\n");
    sendMessage(e, mb.build());
  }
}
