package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;

public class DifficultyCommand {
   private static final DynamicCommandExceptionType f_136933_ = new DynamicCommandExceptionType((p_136948_) -> {
      return new TranslatableComponent("commands.difficulty.failure", p_136948_);
   });

   public static void m_136938_(CommandDispatcher<CommandSourceStack> p_136939_) {
      LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.m_82127_("difficulty");

      for(Difficulty difficulty : Difficulty.values()) {
         literalargumentbuilder.then(Commands.m_82127_(difficulty.m_19036_()).executes((p_136937_) -> {
            return m_136944_(p_136937_.getSource(), difficulty);
         }));
      }

      p_136939_.register(literalargumentbuilder.requires((p_136943_) -> {
         return p_136943_.m_6761_(2);
      }).executes((p_136941_) -> {
         Difficulty difficulty1 = p_136941_.getSource().m_81372_().m_46791_();
         p_136941_.getSource().m_81354_(new TranslatableComponent("commands.difficulty.query", difficulty1.m_19033_()), false);
         return difficulty1.m_19028_();
      }));
   }

   public static int m_136944_(CommandSourceStack p_136945_, Difficulty p_136946_) throws CommandSyntaxException {
      MinecraftServer minecraftserver = p_136945_.m_81377_();
      if (minecraftserver.m_129910_().m_5472_() == p_136946_) {
         throw f_136933_.create(p_136946_.m_19036_());
      } else {
         minecraftserver.m_129827_(p_136946_, true);
         p_136945_.m_81354_(new TranslatableComponent("commands.difficulty.success", p_136946_.m_19033_()), true);
         return 0;
      }
   }
}