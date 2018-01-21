package bot.discord.DiscordBot.commands.music;

import java.util.Arrays;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import bot.discord.DiscordBot.commands.Command;
import bot.discord.DiscordBot.main.Setup;
import bot.discord.DiscordBot.music.MusicManager;
import bot.discord.DiscordBot.music.TrackScheduler;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;

public class PlayCommand extends Command{

  @Override
  public void exeCommand(MessageReceivedEvent e) {
    
    Guild guild = e.getGuild();
    MusicManager manager = MusicController.getInstance().getMusicManager(guild);
    AudioPlayer player = manager.getAudioPlayer();
    TrackScheduler scheduler = manager.getTrackScheduler();
    String[] command = getArguments(e.getMessage());
    
    if (!e.isFromType(ChannelType.TEXT))
      return;
    if(command.length == 1) {
      if (player.isPaused()) {
          player.setPaused(false);
          sendMessage(e, "Resuming playback.");
      }
      else if (player.getPlayingTrack() != null) {
          sendMessage(e, "Already playing.");
      }
      else if (scheduler.getQueue().isEmpty()) {
          sendMessage(e, "Queue is empty. Try adding something first!");
          this.commandHelp(e);
      }
    }
    
    if(command.length >= 2) {
      connect(e, manager);
      play(manager, e.getChannel(), getArguments(e.getMessage())[1], false);   
    } 
  }

  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("play");
  }
  
  @Override
  public void commandHelp (MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    mb.append("Type `" + Setup.BOT_PREFIX + "play <ulr-or-title>` to play a song!\n");
    sendMessage(e, mb.build());
  }

  private void play(MusicManager manager, final MessageChannel channel, String url, final boolean addPlaylist)
  {
      final String trackUrl;

      //Strip <>'s that prevent discord from embedding link resources
      if (url.startsWith("<") && url.endsWith(">"))
          trackUrl = url.substring(1, url.length() - 1);
      else
          trackUrl = url;

      MusicController.getInstance().getAudioPlayerManager().loadItemOrdered(manager, trackUrl, new AudioLoadResultHandler()
      {
          @Override
          public void trackLoaded(AudioTrack track) {
              String msg = "Adding to queue: " + track.getInfo().title;
              if (manager.getAudioPlayer().getPlayingTrack() == null)
                  msg += "\nNow playing...";

              manager.getTrackScheduler().queue(track);
              channel.sendMessage(msg).queue();
          }

          @Override
          public void playlistLoaded(AudioPlaylist playlist) {
              AudioTrack firstTrack = playlist.getSelectedTrack();
              List<AudioTrack> tracks = playlist.getTracks();

              if (firstTrack == null) {
                firstTrack = playlist.getTracks().get(0);
              }

              if (addPlaylist) {
                channel.sendMessage("Adding **" + playlist.getTracks().size() +"** tracks to queue from playlist: " + playlist.getName()).queue();
                tracks.forEach(manager.getTrackScheduler()::queue);
              }
              else {
                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
                manager.getTrackScheduler().queue(firstTrack);
              }
          }

          @Override
          public void noMatches() {
            channel.sendMessage("Nothing found by " + trackUrl).queue();
          }

          @Override
          public void loadFailed(FriendlyException exception) {
            channel.sendMessage("Could not play: " + exception.getMessage()).queue();
          }
      });
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
}
