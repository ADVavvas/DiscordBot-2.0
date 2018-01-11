package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import bot.discord.DiscordBot.main.SetupManager;
import bot.discord.DiscordBot.utilities.TrustManager;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RoleCommand extends Command{
  
  @Override
  public void exeCommand(MessageReceivedEvent e) {
    
    MessageBuilder mb = new MessageBuilder();
    
    String[] arguments = getArguments(e.getMessage());
    
    if(arguments.length == 1) {
      commandHelp(e);
    } else if (arguments.length == 2) {
      String arg = arguments[1];
      if(arg.matches("^(\\d?[1-9]|[1-9]0)$")) {
        int role = Integer.parseInt(arg);
        if(role > e.getGuild().getRoles().size()){
          mb.append("Role index out of bounds");
        } else if(addRole(e, role-1)) {
          mb.append("Role added successfully!");
        } else {
          mb.append("I don't have the permission to do so");
        }
        sendMessage(e, mb.build());
      } else {
        switch(arg) {
          case "list":
            listRoles(e);
            break;
          case "remove":
            if(removeRoles(e)) {
              mb.append("Roles removed successfully!");
            } else {
              mb.append("I don't have the permission to do so");
            }
            sendMessage(e, mb.build());
            break;
          default:
            mb.append("What you typed doesn't match a command :frowning2:");
            sendMessage(e, mb.build());
            break;
        }
      }
    }
    return;
  }

  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("role","roles");
  }
  
  @Override
  public void onGuildMemberJoin(GuildMemberJoinEvent e) {
    try {
      Member member = e.getMember();
      Guild guild = e.getGuild();
      
      Role role;
      if(guild.getRoles().size()==1) {
        role = guild.getPublicRole();
      } else
      {
        role = guild.getRoles().get(guild.getRoles().size()-2);
      }
      guild.getController().addSingleRoleToMember(member, role).complete();
      
      MessageBuilder mb = new MessageBuilder();
      mb.append("Hey ")
        .append(member.getAsMention())
        .append(" welcome to ")
        .append(guild.getName())
        .append("! Enjoy your stay!")
        .append("You have been assigned the role of `")
        .append(role.getName())
        .append("`!");
      guild.getDefaultChannel().sendMessage(mb.build()).complete();
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  private void commandHelp(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    mb.append("Welcome to the role command! Use one of the follwing \n")
    .append(";role list - Get all roles in the guild\n")
    .append(";role [i] - Request a role\n")
    .append(";role remove - Remove all roles except default\n");
    sendMessage(e, mb.build());
  }
  private void listRoles(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    int i = 1;
    for(Role role : e.getGuild().getRoles()) {
      mb.append("```")
        .append(i)
        .append(" - ")
        .append(role.getName().replace("@", ""))
        .append("```\n");
      i++;
    }
    sendMessage(e, mb.build());
  }
  
  private boolean removeRoles(MessageReceivedEvent e) {
    Member bot = e.getGuild().getSelfMember();
    Member user = e.getMember();
    
    if(!bot.hasPermission(Permission.MANAGE_ROLES)) {
      return false;
    } else if(bot.getRoles().get(0).compareTo(user.getRoles().get(0)) <= 0) {
      return false;
    } else {
      e.getGuild().getController().removeRolesFromMember(user, user.getRoles()).complete();
      return true;
    }
  }
  
  private boolean addRole(MessageReceivedEvent e, int i) {
    Member bot = e.getGuild().getSelfMember();
    Member user = e.getMember();
    
    if(!bot.hasPermission(Permission.MANAGE_ROLES)) {
      return false;
    }
    if(bot.getRoles().get(0).compareTo(e.getGuild().getRoles().get(i)) < 0) {
      return false;
    }
    if(SetupManager.getInstance().getSetup().getTrusted().contains(user.getUser().getId())) {
      e.getGuild().getController().addSingleRoleToMember(user, e.getGuild().getRoles().get(i)).complete();
      return true;
    }
    if(user.getRoles().size() == 0 || user.getRoles().get(0).compareTo(e.getGuild().getRoles().get(i)) <= 0) {
      return false;
    }
    else {
      e.getGuild().getController().addSingleRoleToMember(user, e.getGuild().getRoles().get(i)).complete();
      return true;
    }
  }
}
