package bot.discord.DiscordBot.main;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import bot.discord.DiscordBot.commands.Command;

public class Setup {

	public static final char BOT_PREFIX = ';';
  @Expose private String botToken;
  @Expose private String redditToken;
	@Expose private List<String> trusted;
	@Expose (serialize = false, deserialize = false)
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
