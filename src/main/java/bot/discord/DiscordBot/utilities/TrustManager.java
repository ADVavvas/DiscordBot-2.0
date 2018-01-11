package bot.discord.DiscordBot.utilities;

import java.util.ArrayList;
import java.util.List;

public class TrustManager {

  private static TrustManager instance;
  private List<String> trusted;
  public static TrustManager getInstance() {
    if(instance == null) {
      instance = new TrustManager();
    }
    return instance;
  }
  
  public TrustManager() {
    trusted = new ArrayList<String>();
  }
  
  public void setTrusted(String id) {
    trusted.add(id);
  }
  
  public List<String> getTrusted(){
    return trusted;
  }
}
