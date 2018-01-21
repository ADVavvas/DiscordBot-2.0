package bot.discord.DiscordBot.commands.music;

import java.util.Arrays;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import bot.discord.DiscordBot.commands.Command;
import bot.discord.DiscordBot.music.MusicManager;
import bot.discord.DiscordBot.music.TrackScheduler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class StopCommand extends Command {

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    Guild guild = e.getGuild();
    MusicManager manager = MusicController.getInstance().getMusicManager(guild);
    AudioPlayer player = manager.getAudioPlayer();
    TrackScheduler scheduler = manager.getTrackScheduler();
    String[] command = getArguments(e.getMessage());
    AudioManager audioManager = guild.getAudioManager();

    if(!audioManager.isConnected() && audioManager.isAttemptingToConnect()) {
      sendMessage(e, "Not playing anything bro!");
      return;
    }
    if(audioManager.isAttemptingToConnect()) {
      sendMessage(e, "Trying to connect first...");
    }
    scheduler.stop();
    sendMessage(e, "Player has stopped.");
  }

  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("stop");
  }

}
