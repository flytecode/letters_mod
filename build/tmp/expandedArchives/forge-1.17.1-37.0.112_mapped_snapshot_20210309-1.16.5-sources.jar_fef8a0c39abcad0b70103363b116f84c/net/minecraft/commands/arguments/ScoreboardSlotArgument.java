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
import net.minecraft.world.scores.Scoreboard;

public class ScoreboardSlotArgument implements ArgumentType<Integer> {
   private static final Collection<String> f_109193_ = Arrays.asList("sidebar", "foo.bar");
   public static final DynamicCommandExceptionType f_109192_ = new DynamicCommandExceptionType((p_109203_) -> {
      return new TranslatableComponent("argument.scoreboardDisplaySlot.invalid", p_109203_);
   });

   private ScoreboardSlotArgument() {
   }

   public static ScoreboardSlotArgument m_109196_() {
      return new ScoreboardSlotArgument();
   }

   public static int m_109199_(CommandContext<CommandSourceStack> p_109200_, String p_109201_) {
      return p_109200_.getArgument(p_109201_, Integer.class);
   }

   public Integer parse(StringReader p_109198_) throws CommandSyntaxException {
      String s = p_109198_.readUnquotedString();
      int i = Scoreboard.m_83504_(s);
      if (i == -1) {
         throw f_109192_.create(s);
      } else {
         return i;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_109206_, SuggestionsBuilder p_109207_) {
      return SharedSuggestionProvider.m_82967_(Scoreboard.m_83494_(), p_109207_);
   }

   public Collection<String> getExamples() {
      return f_109193_;
   }
}