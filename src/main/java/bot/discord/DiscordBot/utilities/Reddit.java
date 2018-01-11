package bot.discord.DiscordBot.utilities;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Reddit {
  
  public static final String BASE_URL = "https://oauth.reddit.com/";
  public static final String SUB_URL = "r/";
  public static final String RANDOM = "/random/.json";
  private static String AUTH_TOKEN = null;
  
  public static void setup(String token) {
    AUTH_TOKEN = token;
  }
  
  
  
  public static MessageEmbed getRandom(String subreddit) {
    try {
      OkHttpClient client = new OkHttpClient();

      Request request = new Request.Builder()
        .url(BASE_URL + SUB_URL + subreddit + RANDOM)
        .get()
        .addHeader("authorization", "Bearer" + AUTH_TOKEN)
        .addHeader("user-agent", "macos:DogeBot:0.1 (by /u/HamSlayer-)")
        .addHeader("cache-control", "no-cache")
        .build();

      System.out.print("what" + AUTH_TOKEN);
      Response response = client.newCall(request).execute();
     
      String jsonString = response.body().string();
      
      JSONArray responseJSONArray = new JSONArray(jsonString);
      
      JSONObject responseJSON = responseJSONArray
          .getJSONObject(0)
          .getJSONObject("data")
          .getJSONArray("children")
          .getJSONObject(0)
          .getJSONObject("data");
      
      String title = responseJSON.getString("title");
      String description = responseJSON.getString("selftext");
      String author = responseJSON.getString("author");
      EmbedBuilder eb = new EmbedBuilder();
      String postUrl = responseJSON.getString("url");
      
      eb.setColor(new Color(233, 30, 99));
      eb.setAuthor(author, postUrl);
      eb.setTitle(title);
      eb.setDescription(description);
      
      return eb.build();
      /*
      URL url = new URL(BASE_URL + SUB_URL + subreddit + RANDOM);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      
      System.out.println(url.toString());
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);
      conn.setRequestProperty("User-agent", "macos:DogeBot:0.1 (by /u/HamSlayer-)");
      
      
      BufferedReader jsonIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder jsonFile = new StringBuilder();
      
      String jsonLine;
      while ((jsonLine = jsonIn.readLine()) != null) {
          jsonFile.append(jsonLine);
          jsonFile.append("\n");
      }
      System.out.println("got:" + conn.getURL().toString());
      jsonIn.close();
      
      
      JSONObject responseJSON = new JSONObject(jsonFile.toString());
      
      String title = responseJSON.getString("title");
      String description = responseJSON.getString("selftext");
      EmbedBuilder eb = new EmbedBuilder();
      
      eb.setColor(Color.DARK_GRAY);
      eb.setAuthor("Reddit");
      eb.setTitle(title);
      eb.setDescription(description);
      
      return eb.build();
      */
      
      
    } catch(Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
