package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.GameRules;

public class GameRuleCommand {
   public static void m_137744_(CommandDispatcher<CommandSourceStack> p_137745_) {
      final LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.m_82127_("gamerule").requires((p_137750_) -> {
         return p_137750_.m_6761_(2);
      });
      GameRules.m_46164_(new GameRules.GameRuleTypeVisitor() {
         public <T extends GameRules.Value<T>> void m_6889_(GameRules.Key<T> p_137764_, GameRules.Type<T> p_137765_) {
            literalargumentbuilder.then(Commands.m_82127_(p_137764_.m_46328_()).executes((p_137771_) -> {
               return GameRuleCommand.m_137757_(p_137771_.getSource(), p_137764_);
            }).then(p_137765_.m_46358_("value").executes((p_137768_) -> {
               return GameRuleCommand.m_137754_(p_137768_, p_137764_);
            })));
         }
      });
      p_137745_.register(literalargumentbuilder);
   }

   static <T extends GameRules.Value<T>> int m_137754_(CommandContext<CommandSourceStack> p_137755_, GameRules.Key<T> p_137756_) {
      CommandSourceStack commandsourcestack = p_137755_.getSource();
      T t = commandsourcestack.m_81377_().m_129900_().m_46170_(p_137756_);
      t.m_46370_(p_137755_, "value");
      commandsourcestack.m_81354_(new TranslatableComponent("commands.gamerule.set", p_137756_.m_46328_(), t.toString()), true);
      return t.m_6855_();
   }

   static <T extends GameRules.Value<T>> int m_137757_(CommandSourceStack p_137758_, GameRules.Key<T> p_137759_) {
      T t = p_137758_.m_81377_().m_129900_().m_46170_(p_137759_);
      p_137758_.m_81354_(new TranslatableComponent("commands.gamerule.query", p_137759_.m_46328_(), t.toString()), false);
      return t.m_6855_();
   }
}