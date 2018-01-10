package bot.discord.DiscordBot.main;

public class SetupManager {
  
  private static SetupManager singleton;
  private Setup setup;
  
  public static SetupManager getInstance() {
    if(singleton == null) {
      singleton = new SetupManager();
    }
    return singleton;
  }
  
  public SetupManager() {
    setup = new Setup();
    setup.setBotToken("");
    setup.setRedditToken("");
  }

   public Setup getSetup() {
     return setup;
   }
}
