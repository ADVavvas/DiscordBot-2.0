package bot.discord.DiscordBot.utilities;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class RedditEmbed {
	
	private static final String REDDIT_URL = "https://www.reddit.com/";
	
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
				url = new URL(REDDIT_URL + URL);
				
				URLConnection connection = url.openConnection();
				
				BufferedReader jsonIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    StringBuilder jsonFile = new StringBuilder();
		    
		    String jsonLine;
		    while ((jsonLine = jsonIn.readLine()) != null) {
		        jsonFile.append(jsonLine);
		        jsonFile.append("\n");
		    }
		    jsonIn.close();
	
		    String title = new JSONObject(jsonFile.toString()).getJSONArray("title").get(0).toString(); //testing
		    //TODO fix json search, surround with try catch, make MessageEmbed
		    System.out.println(title);
		    return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
		//EmbedBuilder eb = new EmbedBuilder();
		//eb.setAuthor("reddit", REDDIT_URL);
		//eb.setColor(Color.DARK_GRAY);
		
	}
}
