package bot.discord.DiscordBot.main;

import java.util.List;

import bot.discord.DiscordBot.commands.Command;

public class Setup {

	public static final char BOT_PREFIX = ';';
	private String botToken;
	private String redditToken;
	private List<String> trusted;
	private List<Command> commands;
	
	
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
	
	public List<Command> getCommands() {
	  return this.commands;
	}
	
	public void setCommand(List<Command> commands) {
	  this.commands = commands;
	}
	public void addCommand(Command command) {
	  commands.add(command);
	}
}
