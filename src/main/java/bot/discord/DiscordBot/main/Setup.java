package bot.discord.DiscordBot.main;

public class Setup {

	public static final char BOT_PREFIX = ';';
	private String botToken;
	private String redditToken;
	
	
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
}
