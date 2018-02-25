package bot.discord.DiscordBot.main;


import javax.security.auth.login.LoginException;

import bot.discord.DiscordBot.commands.*;
import bot.discord.DiscordBot.commands.music.*;
import bot.discord.DiscordBot.utilities.Reddit;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class DiscordBot {

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
      JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT).setToken(setup.getBotToken());
      Reddit.setup(setup.getRedditToken());
      
      
      jdaBuilder.addEventListener(new InfoCommand());
      setup.addCommand(new InfoCommand());
      jdaBuilder.addEventListener(new SearchCommand());
      setup.addCommand(new SearchCommand());
      jdaBuilder.addEventListener(new RedditCommand());
      setup.addCommand(new RedditCommand());
      jdaBuilder.addEventListener(new RoleCommand());
      setup.addCommand(new RoleCommand());
      jdaBuilder.addEventListener(new TrustCommand());
      setup.addCommand(new TrustCommand());
      jdaBuilder.addEventListener(new ClearCommand());
      setup.addCommand(new ClearCommand());
      jdaBuilder.addEventListener(new RollCommand());
      setup.addCommand(new RollCommand());
      jdaBuilder.addEventListener(new HelpCommand());
      setup.addCommand(new HelpCommand());
      
      jdaBuilder.addEventListener(new PlayCommand());
      setup.addCommand(new PlayCommand());
      jdaBuilder.addEventListener(new StopCommand());
      setup.addCommand(new StopCommand());
      jdaBuilder.addEventListener(new SkipCommand());
      setup.addCommand(new SkipCommand());
      jdaBuilder.addEventListener(new PauseCommand());
      setup.addCommand(new PauseCommand());
      jdaBuilder.addEventListener(new JoinCommand());
      setup.addCommand(new JoinCommand());
      
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
