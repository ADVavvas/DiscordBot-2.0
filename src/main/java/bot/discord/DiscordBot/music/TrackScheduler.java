package bot.discord.DiscordBot.music;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class TrackScheduler extends AudioEventAdapter {

  private AudioPlayer player;
  private BlockingQueue<AudioTrack> queue;
  
  public TrackScheduler(AudioPlayer player) {
    this.player = player;
    this.queue = new LinkedBlockingQueue<AudioTrack>();
  }
  
  public void queue(AudioTrack track) {
    player.setPaused(false);
    if (!player.startTrack(track, true)) {
      queue.offer(track);
    }
  }
  
  public void nextTrack() {
    player.startTrack(queue.poll(), false);
  }
  
  public void stop() {
    clearQueue();
    player.stopTrack();
    player.setPaused(true);
  }
  
  public void clearQueue() {
    queue.clear();
  }
  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    if (endReason.mayStartNext){
      nextTrack();
    }
  }
  
  
  public Queue<AudioTrack> getQueue(){
    return this.queue;
  }
}
