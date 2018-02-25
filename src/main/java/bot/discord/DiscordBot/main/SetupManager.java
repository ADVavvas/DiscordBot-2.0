package bot.discord.DiscordBot.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bot.discord.DiscordBot.commands.Command;

public class SetupManager {
  
  private static SetupManager singleton;
  private Setup setup;
  
  private final Path configFile = new File(".").toPath().resolve("Config.json");
  private final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
  
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
    setup.setTrusted(new ArrayList<String>());
    loadSettings();
    //gson sets field to null for some reason, set ArrayList after to avoid NullPointerException
    setup.setCommand(new ArrayList<Command>());
  }

   public Setup getSetup() {
     return setup;
   }
   public void loadSettings() {
     try {
       System.out.print(configFile.toUri());
       BufferedReader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8);
       this.setup = gson.fromJson(reader, Setup.class);
       reader.close();
       System.out.println("SettingsManager: Settings loaded");
     } catch (IOException e) {
       System.out.println("SettingsManager: Error Loading Settings");
       e.printStackTrace();
     }
   }
   
   public void saveSettings() {
     System.out.println("SettingsManager: Settings saved");
     String jsonOut = gson.toJson(this.setup);
     try {
         BufferedWriter writer = Files.newBufferedWriter(configFile, StandardCharsets.UTF_8);
         writer.append(jsonOut);
         writer.close();
     } catch (IOException e) {
         e.printStackTrace();
     }
   }
}
