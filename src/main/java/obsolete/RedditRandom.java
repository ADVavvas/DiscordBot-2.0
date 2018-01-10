package obsolete;

import java.net.HttpURLConnection;
import java.net.URL;

import net.dv8tion.jda.core.entities.MessageEmbed;

public class RedditRandom {

  public static final String BASE_URL = "https://oauth.reddit.com/r/";
  public static final String RANDOM = "/random";
  
  public MessageEmbed getRandom(String subreddit) {
    try {
      URL url = new URL(BASE_URL + subreddit);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      
      conn.setRequestProperty("Authorization", "Bearer " + " ");
      conn.setRequestMethod("GET");
    } catch(Exception e) {
      
    }
    
    return null;
  }
}
