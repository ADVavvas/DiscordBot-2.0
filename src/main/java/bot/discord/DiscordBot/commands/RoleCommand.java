package bot.discord.DiscordBot.commands;

import java.util.Arrays;
import java.util.List;

import bot.discord.DiscordBot.main.Setup;
import bot.discord.DiscordBot.main.SetupManager;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RoleCommand extends Command{
  
  @Override
  public void exeCommand(MessageReceivedEvent e) {
    
    String[] arguments = getArguments(e.getMessage());
    
    if(arguments.length <= 2) {
      if(arguments[1].equals("list")) {
        listRoles(e);
        return;
      }
      commandHelp(e);
      return;
    }
    switch(arguments[1]) {
      case "add":
        if(addRole(e, arguments)) sendMessage(e, new MessageBuilder("Role added successfully!").build());
        else sendMessage(e, new MessageBuilder("Role was not added!").build());
        break;
      case "remove":
        if(removeRole(e, arguments)) sendMessage(e, new MessageBuilder("Role removed successfully!").build());
        else sendMessage(e, new MessageBuilder("Role was not removed!").build());
        break;
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
  @Override
  protected void commandHelp(MessageReceivedEvent e) {
    MessageBuilder mb = new MessageBuilder();
    mb.append("Welcome to the role command!\nType one of the following\n")
    .append("`" + Setup.BOT_PREFIX + "role list` - Get all roles in the guild\n")
    .append("`" + Setup.BOT_PREFIX + "role add <role-number> <memberID/mention>(optional)` - Add a role (admin)\n")
    .append("`" + Setup.BOT_PREFIX + "role remove <role-number> <memberID/mention>(optional)` - Remove a role (admin)\n");
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
  
  private boolean removeRole(MessageReceivedEvent e, String[] args) {
    Member bot = e.getGuild().getSelfMember();
    Member user = e.getMember();
    Member reqMem = null;
    
    Role botRole = bot.getRoles().get(0);
    Role reqRole = null;
    boolean override = SetupManager.getInstance().getSetup().getTrusted().contains(user.getUser().getId());
    MessageBuilder mb = new MessageBuilder();
    
    //Bot has permission?
    if(!bot.hasPermission(Permission.MANAGE_ROLES)){
      sendMessage(e, new MessageBuilder("I don't have the `Manage Role` permission!").build());
      return false;
    }
    //User has permission?
    if(!user.hasPermission(Permission.MANAGE_ROLES) && !override) {
      sendMessage(e, new MessageBuilder("You don't have the `Manage Role` permission!").build());
      return false;
    }
    //Is 3rd argument an integer (1-99)
    if(!args[2].matches("^(\\d?[1-9]|[1-9]0)$")) {
      return false;
    }
    //Does the role exist in the guild
    if(Integer.parseInt(args[2]) > e.getGuild().getRoles().size()){
      sendMessage(e, new MessageBuilder("Role index out of bounds!").build());
      return false;
    }
    reqRole = e.getGuild().getRoles().get(Integer.parseInt(args[2]) - 1);
    
    //Does the bot have a sufficient role
    if(botRole.compareTo(reqRole) <= 0) {
      sendMessage(e, new MessageBuilder("My role is not high enough!").build());
      return false;
    }
    //Does the user have a sufficient role
    if((user.getRoles().isEmpty() || user.getRoles().get(0).compareTo(reqRole) <= 0) && !override) {
      sendMessage(e, new MessageBuilder("Your role is not high enough").build());
      return false;
    }
    
    //Own request or for other?
    if(args.length >= 4) {
       
       if(args[3].matches("[1-9]*")) {
         reqMem = e.getGuild().getMemberById(args[3]);
       } else {
         reqMem = e.getMessage().getMentionedMembers().get(0);
       }
       
       //Is 4th argument a user id?
       if(reqMem == null) {
         sendMessage(e, new MessageBuilder("Target member ID is invalid or does not exist!").build());
         return false;
       }
       //Does the requester have higher role receiver
       if(user.getRoles().get(0).compareTo(reqRole) <= 0 && !override) {
         sendMessage(e, new MessageBuilder("Your role is lower than the target's!").build());
         return false;
       }
       //Does the receiver have the role
       if(!reqMem.getRoles().contains(reqRole)) {
         sendMessage(e, new MessageBuilder("Target does not have that role!").build());
         return false;
       }
       e.getGuild().getController().removeSingleRoleFromMember(reqMem, reqRole).complete();
       return true; 
    }
    if(args.length == 3) {
      if(!user.getRoles().contains(reqRole)) {
        sendMessage(e, new MessageBuilder("Target does not have that role!").build());
        return false;
      }
      e.getGuild().getController().removeSingleRoleFromMember(user, reqRole).complete();
      return true;
    }
    
    return false;
  }
  
  private boolean addRole(MessageReceivedEvent e, String[] args) {
    Member bot = e.getGuild().getSelfMember();
    Member user = e.getMember();
    Member reqMem = null;
    
    Role botRole = bot.getRoles().get(0);
    Role reqRole = null;
    boolean override = SetupManager.getInstance().getSetup().getTrusted().contains(user.getUser().getId());
    
    //Bot has permission?
    if(!bot.hasPermission(Permission.MANAGE_ROLES)){
      sendMessage(e, new MessageBuilder("I don't have the `Manage Role` permission!").build());
      return false;
    }
    //User has permission?
    if(!user.hasPermission(Permission.MANAGE_ROLES) && !override) {
      sendMessage(e, new MessageBuilder("You don't have the `Manage Role` permission!").build());
      return false;
    }
    //Is 3rd argument an integer (1-99)
    if(!args[2].matches("^(\\d?[1-9]|[1-9]0)$")) {
      return false;
    }
    //Does the role exist in the guild
    if(Integer.parseInt(args[2]) > e.getGuild().getRoles().size()){
      sendMessage(e, new MessageBuilder("Role index out of bounds!").build());
      return false;
    }
    reqRole = e.getGuild().getRoles().get(Integer.parseInt(args[2]) - 1);
    
    //Does the bot have a sufficient role
    if(botRole.compareTo(reqRole) <= 0) {
      sendMessage(e, new MessageBuilder("My role is not high enough!").build());
      return false;
    }
    
    //Does the user have a sufficient role
    if((user.getRoles().isEmpty() || user.getRoles().get(0).compareTo(reqRole) <= 0 )&& !override) {
      sendMessage(e, new MessageBuilder("Your role is not high enough").build());
      return false;
    }
    //Own request or for other?
    if(args.length >= 4) {
      if(args[3].matches("[1-9]*")) {
        reqMem = e.getGuild().getMemberById(args[3]);
      } else {
        reqMem = e.getMessage().getMentionedMembers().get(0);
      }
       //Is 4th argument a user id?
       if(reqMem == null) {
         sendMessage(e, new MessageBuilder("Target member ID is invalid or does not exist!").build());
         return false;
       }
       //Does the requester have higher role receiver
       if(user.getRoles().get(0).compareTo(reqRole) <= 0 && !override) {
         sendMessage(e, new MessageBuilder("Your role is lower than the target's!").build());
         return false;
       }
       //Does the receiver have the role
       if(reqMem.getRoles().contains(reqRole)) {
         sendMessage(e, new MessageBuilder("Target already has that role!").build());
         return false;
       }
       e.getGuild().getController().addSingleRoleToMember(reqMem, reqRole).complete();
       return true; 
    }
    if(args.length == 3) {
      if(user.getRoles().contains(reqRole)) {
        sendMessage(e, new MessageBuilder("Target already has that role!").build());
        return false;
      }
      e.getGuild().getController().addSingleRoleToMember(user, reqRole).complete();
      return true;
    }
    
    return false;
  }
}