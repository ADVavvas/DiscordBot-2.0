package bot.discord.DiscordBot.utilities;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class RedditEmbed {
	
	private static final String REDDIT_URL = "https://www.reddit.com/";
	private static final String ABOUT_URL = "/about.json";
	
	private static final String TITLE = "Reddit";
	private static final String AUTHOR = "Reddit";
	private static final String DESCRIPTION = "Reddit: the front page of the internet";
	private static final String THUMBNAIL = "https://upload.wikimedia.org/wikipedia/en/thumb/8/82/Reddit_logo_and_wordmark.svg/150px-Reddit_logo_and_wordmark.svg.png";
	
	public MessageEmbed[] embedURL(String[] URL) {
		MessageEmbed[] me = new MessageEmbed[URL.length];
		for(int i=0;i<URL.length;i++) {
			me[i] = embedURL(URL[i]);
		}
		return me;
	}
	public MessageEmbed embedURL(String URL) {
			try {
				URL url;
				url = new URL(REDDIT_URL + URL + ABOUT_URL);
				
				URLConnection connection = url.openConnection();
				
				BufferedReader jsonIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    StringBuilder jsonFile = new StringBuilder();
		    
		    String jsonLine;
		    while ((jsonLine = jsonIn.readLine()) != null) {
		        jsonFile.append(jsonLine);
		        jsonFile.append("\n");
		    }
		    jsonIn.close();
		    
		    JSONObject jo = new JSONObject(jsonFile.toString()).getJSONObject("data");
		    String title = jo.getString("title");
		    String description = jo.get("public_description").toString();
		    String thumbnail = jo.get("header_img").toString();
		   
		    if(title == null || title.compareTo("")==0) title = TITLE;
		    if(description == null || description.compareTo("")==0) description = DESCRIPTION;
		    if(thumbnail == null || thumbnail.compareTo("null") == 0) thumbnail = THUMBNAIL;
		    

		    EmbedBuilder eb = new EmbedBuilder();
		    eb.setColor(Color.DARK_GRAY);
		    eb.setAuthor(AUTHOR);
		    eb.setDescription(description);
		    eb.setTitle(title + " • /" + URL, REDDIT_URL + URL);
		    eb.setThumbnail(thumbnail);

		    return eb.build();
		    
		    //TODO regex on json Strings
		    //		 check for null
		    // 		 improve defensive code
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			return null;
	}
}
