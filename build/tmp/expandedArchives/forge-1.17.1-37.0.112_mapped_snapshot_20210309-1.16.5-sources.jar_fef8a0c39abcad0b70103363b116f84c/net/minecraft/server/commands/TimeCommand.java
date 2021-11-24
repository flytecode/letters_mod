package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;

public class TimeCommand {
   public static void m_139071_(CommandDispatcher<CommandSourceStack> p_139072_) {
      p_139072_.register(Commands.m_82127_("time").requires((p_139076_) -> {
         return p_139076_.m_6761_(2);
      }).then(Commands.m_82127_("set").then(Commands.m_82127_("day").executes((p_139101_) -> {
         return m_139077_(p_139101_.getSource(), 1000);
      })).then(Commands.m_82127_("noon").executes((p_139099_) -> {
         return m_139077_(p_139099_.getSource(), 6000);
      })).then(Commands.m_82127_("night").executes((p_139097_) -> {
         return m_139077_(p_139097_.getSource(), 13000);
      })).then(Commands.m_82127_("midnight").executes((p_139095_) -> {
         return m_139077_(p_139095_.getSource(), 18000);
      })).then(Commands.m_82129_("time", TimeArgument.m_113037_()).executes((p_139093_) -> {
         return m_139077_(p_139093_.getSource(), IntegerArgumentType.getInteger(p_139093_, "time"));
      }))).then(Commands.m_82127_("add").then(Commands.m_82129_("time", TimeArgument.m_113037_()).executes((p_139091_) -> {
         return m_139082_(p_139091_.getSource(), IntegerArgumentType.getInteger(p_139091_, "time"));
      }))).then(Commands.m_82127_("query").then(Commands.m_82127_("daytime").executes((p_139086_) -> {
         return m_139087_(p_139086_.getSource(), m_139069_(p_139086_.getSource().m_81372_()));
      })).then(Commands.m_82127_("gametime").executes((p_139081_) -> {
         return m_139087_(p_139081_.getSource(), (int)(p_139081_.getSource().m_81372_().m_46467_() % 2147483647L));
      })).then(Commands.m_82127_("day").executes((p_139074_) -> {
         return m_139087_(p_139074_.getSource(), (int)(p_139074_.getSource().m_81372_().m_46468_() / 24000L % 2147483647L));
      }))));
   }

   private static int m_139069_(ServerLevel p_139070_) {
      return (int)(p_139070_.m_46468_() % 24000L);
   }

   private static int m_139087_(CommandSourceStack p_139088_, int p_139089_) {
      p_139088_.m_81354_(new TranslatableComponent("commands.time.query", p_139089_), false);
      return p_139089_;
   }

   public static int m_139077_(CommandSourceStack p_139078_, int p_139079_) {
      for(ServerLevel serverlevel : p_139078_.m_81377_().m_129785_()) {
         serverlevel.m_8615_((long)p_139079_);
      }

      p_139078_.m_81354_(new TranslatableComponent("commands.time.set", p_139079_), true);
      return m_139069_(p_139078_.m_81372_());
   }

   public static int m_139082_(CommandSourceStack p_139083_, int p_139084_) {
      for(ServerLevel serverlevel : p_139083_.m_81377_().m_129785_()) {
         serverlevel.m_8615_(serverlevel.m_46468_() + (long)p_139084_);
      }

      int i = m_139069_(p_139083_.m_81372_());
      p_139083_.m_81354_(new TranslatableComponent("commands.time.set", i), true);
      return i;
   }
}