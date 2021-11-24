package net.minecraft.server.commands;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.BanListEntry;
import net.minecraft.server.players.PlayerList;

public class BanListCommands {
   public static void m_136543_(CommandDispatcher<CommandSourceStack> p_136544_) {
      p_136544_.register(Commands.m_82127_("banlist").requires((p_136548_) -> {
         return p_136548_.m_6761_(3);
      }).executes((p_136555_) -> {
         PlayerList playerlist = p_136555_.getSource().m_81377_().m_6846_();
         return m_136549_(p_136555_.getSource(), Lists.newArrayList(Iterables.concat(playerlist.m_11295_().m_11395_(), playerlist.m_11299_().m_11395_())));
      }).then(Commands.m_82127_("ips").executes((p_136553_) -> {
         return m_136549_(p_136553_.getSource(), p_136553_.getSource().m_81377_().m_6846_().m_11299_().m_11395_());
      })).then(Commands.m_82127_("players").executes((p_136546_) -> {
         return m_136549_(p_136546_.getSource(), p_136546_.getSource().m_81377_().m_6846_().m_11295_().m_11395_());
      })));
   }

   private static int m_136549_(CommandSourceStack p_136550_, Collection<? extends BanListEntry<?>> p_136551_) {
      if (p_136551_.isEmpty()) {
         p_136550_.m_81354_(new TranslatableComponent("commands.banlist.none"), false);
      } else {
         p_136550_.m_81354_(new TranslatableComponent("commands.banlist.list", p_136551_.size()), false);

         for(BanListEntry<?> banlistentry : p_136551_) {
            p_136550_.m_81354_(new TranslatableComponent("commands.banlist.entry", banlistentry.m_8003_(), banlistentry.m_10960_(), banlistentry.m_10962_()), false);
         }
      }

      return p_136551_.size();
   }
}