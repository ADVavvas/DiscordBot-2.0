package bot.discord.DiscordBot.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;

import bot.discord.DiscordBot.main.SetupManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class YoutubeAPI {
  public static AudioPlaylist search(String query, int maxResults, YoutubeAudioSourceManager sourceManager) {
    JSONObject data;
    String key = SetupManager.getInstance().getSetup().getRedditToken();
  
    OkHttpClient client = new OkHttpClient();
    
    Request request = new Request.Builder()
        .url("")
        .get()
        .addHeader("key", key)
        .addHeader("type", "video")
        .addHeader("maxResults", Integer.toString(maxResults))
        .addHeader("q", query)
        .build();
    String jsonString = null;
    try {
      Response response = client.newCall(request).execute();
      
      jsonString = response.body().string();
    } catch (IOException e) {
        e.printStackTrace();
    }
  
    data = new JSONObject(jsonString);
    //The search contains all values we need, except for the duration :feelsbadman:
    //so we need to do another query for each video.
    List<String> ids = new ArrayList<>(maxResults);
    try {
        JSONArray items = data.getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            ids.add(item.getJSONObject("id").getString("videoId"));
        }
    } catch (JSONException e) {
        String message = String.format("Youtube search with API key ending on %s for query %s returned unexpected JSON:\n%s",
                key.substring(20), query, data.toString());
        e.printStackTrace();
    }
  
    List<AudioTrack> tracks = new ArrayList<>();
    for (String id : ids) {
        try {
            //YoutubeVideo vid = getVideoFromID(id, true);
            //tracks.add(sourceManager.buildTrackObject(id, vid.name, vid.channelTitle, vid.isStream, vid.getDurationInMillis()));
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
    }
    return new BasicAudioPlaylist("Search results for: " + query, tracks, null, true);
  }
}