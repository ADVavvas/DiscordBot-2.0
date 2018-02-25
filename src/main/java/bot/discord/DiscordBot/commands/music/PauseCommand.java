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

public class PauseCommand extends Command {

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    Guild guild = e.getGuild();
    MusicManager manager = MusicController.getInstance().getMusicManager(guild);
    AudioPlayer player = manager.getAudioPlayer();
    TrackScheduler scheduler = manager.getTrackScheduler();
    
    AudioManager audioManager = guild.getAudioManager();
    
    if((!audioManager.isConnected() && audioManager.isAttemptingToConnect())) {
      sendMessage(e, "Not connected!");
      return;
    }
    
    if(player.isPaused()) {
      sendMessage(e, "Player is already paused!");
      return;
    }
    if(scheduler.getQueue().isEmpty() && player.getPlayingTrack() == null) {
      sendMessage(e, "Queue is empty. Try adding something first!");
      return;
    }
    player.setPaused(true);
    sendMessage(e, "Pausing...");
  }

  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("pause");
  }

}
