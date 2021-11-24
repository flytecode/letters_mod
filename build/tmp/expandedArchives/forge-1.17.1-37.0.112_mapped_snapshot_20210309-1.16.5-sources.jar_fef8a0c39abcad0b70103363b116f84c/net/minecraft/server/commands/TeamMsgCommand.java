package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.PlayerTeam;

public class TeamMsgCommand {
   private static final Style f_138996_ = Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, new TranslatableComponent("chat.type.team.hover"))).m_131142_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/teammsg "));
   private static final SimpleCommandExceptionType f_138997_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.teammsg.failed.noteam"));

   public static void m_138999_(CommandDispatcher<CommandSourceStack> p_139000_) {
      LiteralCommandNode<CommandSourceStack> literalcommandnode = p_139000_.register(Commands.m_82127_("teammsg").then(Commands.m_82129_("message", MessageArgument.m_96832_()).executes((p_139002_) -> {
         return m_139003_(p_139002_.getSource(), MessageArgument.m_96835_(p_139002_, "message"));
      })));
      p_139000_.register(Commands.m_82127_("tm").redirect(literalcommandnode));
   }

   private static int m_139003_(CommandSourceStack p_139004_, Component p_139005_) throws CommandSyntaxException {
      Entity entity = p_139004_.m_81374_();
      PlayerTeam playerteam = (PlayerTeam)entity.m_5647_();
      if (playerteam == null) {
         throw f_138997_.create();
      } else {
         Component component = playerteam.m_83367_().m_130948_(f_138996_);
         List<ServerPlayer> list = p_139004_.m_81377_().m_6846_().m_11314_();

         for(ServerPlayer serverplayer : list) {
            if (serverplayer == entity) {
               serverplayer.m_6352_(new TranslatableComponent("chat.type.team.sent", component, p_139004_.m_81357_(), p_139005_), entity.m_142081_());
            } else if (serverplayer.m_5647_() == playerteam) {
               serverplayer.m_6352_(new TranslatableComponent("chat.type.team.text", component, p_139004_.m_81357_(), p_139005_), entity.m_142081_());
            }
         }

         return list.size();
      }
   }
}