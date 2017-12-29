package bot.discord.DiscordBot.main;


import javax.security.auth.login.LoginException;

import bot.discord.DiscordBot.commands.*;
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
      JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT).setToken(TOKEN);
    
      jdaBuilder.addEventListener(new InfoCommand());
      jdaBuilder.addEventListener(new SearchCommand());
    
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
