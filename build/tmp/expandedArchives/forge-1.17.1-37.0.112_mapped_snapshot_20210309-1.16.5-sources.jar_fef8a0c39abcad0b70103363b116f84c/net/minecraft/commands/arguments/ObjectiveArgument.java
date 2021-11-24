package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public class ObjectiveArgument implements ArgumentType<String> {
   private static final Collection<String> f_101952_ = Arrays.asList("foo", "*", "012");
   private static final DynamicCommandExceptionType f_101953_ = new DynamicCommandExceptionType((p_101971_) -> {
      return new TranslatableComponent("arguments.objective.notFound", p_101971_);
   });
   private static final DynamicCommandExceptionType f_101954_ = new DynamicCommandExceptionType((p_101969_) -> {
      return new TranslatableComponent("arguments.objective.readonly", p_101969_);
   });
   public static final DynamicCommandExceptionType f_101951_ = new DynamicCommandExceptionType((p_101964_) -> {
      return new TranslatableComponent("commands.scoreboard.objectives.add.longName", p_101964_);
   });

   public static ObjectiveArgument m_101957_() {
      return new ObjectiveArgument();
   }

   public static Objective m_101960_(CommandContext<CommandSourceStack> p_101961_, String p_101962_) throws CommandSyntaxException {
      String s = p_101961_.getArgument(p_101962_, String.class);
      Scoreboard scoreboard = p_101961_.getSource().m_81377_().m_129896_();
      Objective objective = scoreboard.m_83477_(s);
      if (objective == null) {
         throw f_101953_.create(s);
      } else {
         return objective;
      }
   }

   public static Objective m_101965_(CommandContext<CommandSourceStack> p_101966_, String p_101967_) throws CommandSyntaxException {
      Objective objective = m_101960_(p_101966_, p_101967_);
      if (objective.m_83321_().m_83621_()) {
         throw f_101954_.create(objective.m_83320_());
      } else {
         return objective;
      }
   }

   public String parse(StringReader p_101959_) throws CommandSyntaxException {
      String s = p_101959_.readUnquotedString();
      if (s.length() > 16) {
         throw f_101951_.create(16);
      } else {
         return s;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_101974_, SuggestionsBuilder p_101975_) {
      if (p_101974_.getSource() instanceof CommandSourceStack) {
         return SharedSuggestionProvider.m_82970_(((CommandSourceStack)p_101974_.getSource()).m_81377_().m_129896_().m_83474_(), p_101975_);
      } else if (p_101974_.getSource() instanceof SharedSuggestionProvider) {
         SharedSuggestionProvider sharedsuggestionprovider = (SharedSuggestionProvider)p_101974_.getSource();
         return sharedsuggestionprovider.m_5497_((CommandContext<SharedSuggestionProvider>)p_101974_, p_101975_);
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return f_101952_;
   }
}