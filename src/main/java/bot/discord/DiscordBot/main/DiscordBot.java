package bot.discord.DiscordBot.main;


import javax.security.auth.login.LoginException;

import bot.discord.DiscordBot.commands.*;
import bot.discord.DiscordBot.utilities.Reddit;
import bot.discord.DiscordBot.utilities.RoleManager;
import bot.discord.DiscordBot.utilities.TrustManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class DiscordBot {

  private final static String TOKEN = "";
  private static JDA jda;
  public static void main(String[] arguments) {
    init();
  }
  
  public static JDA getJDA() {
    return jda;
  }
  
  private static void init() {
    try {
      Setup setup = SetupManager.getInstance().getSetup();
      TrustManager.getInstance();
      JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT).setToken(setup.getBotToken());
      Reddit.setup(setup.getRedditToken());
      
      
      jdaBuilder.addEventListener(new InfoCommand());
      jdaBuilder.addEventListener(new SearchCommand());
      jdaBuilder.addEventListener(new RedditCommand());
      jdaBuilder.addEventListener(new RoleCommand());
      jdaBuilder.addEventListener(new TrustCommand());
      jdaBuilder.addEventListener(new ClearCommand());
      
    
      jda = jdaBuilder.buildBlocking();
      System.out.println("all done");
    } catch (LoginException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (RateLimitedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
