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
    setup.setBotToken("MzY2NjQzMzU5MTI2NzgxOTUz.DOvEIw.NquAjO2ABXpmANkhT4uJQJSjvlY");
    setup.setRedditToken("joELa-s-mj-vRjfZVwFbeWMiuNk");
  }

   public Setup getSetup() {
     return setup;
   }
}
