package bot.discord.DiscordBot.commands.music;

import java.util.HashMap;
import java.util.Map;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import bot.discord.DiscordBot.music.MusicManager;
import net.dv8tion.jda.core.entities.Guild;

public class MusicController {
  
  private static MusicController singleton;
  
  private AudioPlayerManager playerManager;
  private Map<String, MusicManager> musicManagers;
  private final static int BASE_VOLUME = 50;
  
  private MusicController() {
  //java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies").setLevel(Level.OFF);
    
    this.playerManager = new DefaultAudioPlayerManager();
    playerManager.registerSourceManager(new YoutubeAudioSourceManager());
    playerManager.registerSourceManager(new SoundCloudAudioSourceManager());
    playerManager.registerSourceManager(new BandcampAudioSourceManager());
    playerManager.registerSourceManager(new VimeoAudioSourceManager());
    playerManager.registerSourceManager(new TwitchStreamAudioSourceManager());
    playerManager.registerSourceManager(new HttpAudioSourceManager());
    playerManager.registerSourceManager(new LocalAudioSourceManager());
    
    musicManagers = new HashMap<String, MusicManager>();
  }
  
  public static MusicController getInstance() {
    if(singleton == null) {
      singleton = new MusicController();
    }
    return singleton;
  }
  
  public AudioPlayerManager getAudioPlayerManager() {
    return this.playerManager;
  }
  public MusicManager getMusicManager(Guild guild) {
    String ID = guild.getId();
    MusicManager manager = musicManagers.get(ID);
    if (manager == null) {
      synchronized (musicManagers) {
        manager = musicManagers.get(ID);
        if (manager == null) {
          manager = new MusicManager(playerManager);
          manager.getAudioPlayer().setVolume(BASE_VOLUME);
          musicManagers.put(ID, manager);
        }
      }
    }
    return manager;
  }
}
