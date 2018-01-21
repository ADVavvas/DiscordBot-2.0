package bot.discord.DiscordBot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class MusicManager {

  private AudioPlayer player;
  private TrackScheduler scheduler;
  
  public MusicManager(AudioPlayerManager manager) {
    player = manager.createPlayer();
    scheduler = new TrackScheduler(player);
    player.addListener(scheduler);
  }
  
  public AudioPlayer getAudioPlayer() {
    return this.player;
  }
  
  public TrackScheduler getTrackScheduler() {
    return this.scheduler;
  }
  
  public AudioPlayerSendHandler getSendHandler() {
    return new AudioPlayerSendHandler(player);
  }
}
