package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import java.util.List;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;

public class ListPlayersCommand {
   public static void m_137820_(CommandDispatcher<CommandSourceStack> p_137821_) {
      p_137821_.register(Commands.m_82127_("list").executes((p_137830_) -> {
         return m_137824_(p_137830_.getSource());
      }).then(Commands.m_82127_("uuids").executes((p_137823_) -> {
         return m_137831_(p_137823_.getSource());
      })));
   }

   private static int m_137824_(CommandSourceStack p_137825_) {
      return m_137826_(p_137825_, Player::m_5446_);
   }

   private static int m_137831_(CommandSourceStack p_137832_) {
      return m_137826_(p_137832_, (p_137819_) -> {
         return new TranslatableComponent("commands.list.nameAndId", p_137819_.m_7755_(), p_137819_.m_36316_().getId());
      });
   }

   private static int m_137826_(CommandSourceStack p_137827_, Function<ServerPlayer, Component> p_137828_) {
      PlayerList playerlist = p_137827_.m_81377_().m_6846_();
      List<ServerPlayer> list = playerlist.m_11314_();
      Component component = ComponentUtils.m_178440_(list, p_137828_);
      p_137827_.m_81354_(new TranslatableComponent("commands.list.players", list.size(), playerlist.m_11310_(), component), false);
      return list.size();
   }
}