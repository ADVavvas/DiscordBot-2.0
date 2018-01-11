package bot.discord.DiscordBot.utilities;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class RoleManager extends ListenerAdapter {
  
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
}
