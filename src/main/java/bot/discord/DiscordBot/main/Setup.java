package bot.discord.DiscordBot.main;

import java.util.List;

public class Setup {

	public static final char BOT_PREFIX = ';';
	private String botToken;
	private String redditToken;
	private List<String> trusted;
	
	
	public Setup() {
		super();
	}
	
	public String getBotToken() {
	  return botToken;
	}
	public void setBotToken(String botToken) {
	  this.botToken = botToken;
	}
	
	public String getRedditToken() {
	  return redditToken;
	}
	public void setRedditToken(String redditToken) {
	  this.redditToken = redditToken;
	}
	
	public List<String> getTrusted() {
	  return this.trusted;
	}
	public void setTrusted(List<String> trusted) {
	  this.trusted = trusted;
	}
	public void addTrusted(String id) {
	  this.trusted.add(id);
	}
}
