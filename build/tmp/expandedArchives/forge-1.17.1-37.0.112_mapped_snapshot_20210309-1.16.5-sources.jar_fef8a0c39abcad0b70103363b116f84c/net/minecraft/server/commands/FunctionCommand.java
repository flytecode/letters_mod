package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.ServerFunctionManager;

public class FunctionCommand {
   public static final SuggestionProvider<CommandSourceStack> f_137712_ = (p_137719_, p_137720_) -> {
      ServerFunctionManager serverfunctionmanager = p_137719_.getSource().m_81377_().m_129890_();
      SharedSuggestionProvider.m_82929_(serverfunctionmanager.m_136131_(), p_137720_, "#");
      return SharedSuggestionProvider.m_82926_(serverfunctionmanager.m_136130_(), p_137720_);
   };

   public static void m_137714_(CommandDispatcher<CommandSourceStack> p_137715_) {
      p_137715_.register(Commands.m_82127_("function").requires((p_137722_) -> {
         return p_137722_.m_6761_(2);
      }).then(Commands.m_82129_("name", FunctionArgument.m_120907_()).suggests(f_137712_).executes((p_137717_) -> {
         return m_137723_(p_137717_.getSource(), FunctionArgument.m_120910_(p_137717_, "name"));
      })));
   }

   private static int m_137723_(CommandSourceStack p_137724_, Collection<CommandFunction> p_137725_) {
      int i = 0;

      for(CommandFunction commandfunction : p_137725_) {
         i += p_137724_.m_81377_().m_129890_().m_136112_(commandfunction, p_137724_.m_81324_().m_81358_(2));
      }

      if (p_137725_.size() == 1) {
         p_137724_.m_81354_(new TranslatableComponent("commands.function.success.single", i, p_137725_.iterator().next().m_77981_()), true);
      } else {
         p_137724_.m_81354_(new TranslatableComponent("commands.function.success.multiple", i, p_137725_.size()), true);
      }

      return i;
   }
}