package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;

public class TimeArgument implements ArgumentType<Integer> {
   private static final Collection<String> f_113031_ = Arrays.asList("0d", "0s", "0t", "0");
   private static final SimpleCommandExceptionType f_113032_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.time.invalid_unit"));
   private static final DynamicCommandExceptionType f_113033_ = new DynamicCommandExceptionType((p_113041_) -> {
      return new TranslatableComponent("argument.time.invalid_tick_count", p_113041_);
   });
   private static final Object2IntMap<String> f_113034_ = new Object2IntOpenHashMap<>();

   public static TimeArgument m_113037_() {
      return new TimeArgument();
   }

   public Integer parse(StringReader p_113039_) throws CommandSyntaxException {
      float f = p_113039_.readFloat();
      String s = p_113039_.readUnquotedString();
      int i = f_113034_.getOrDefault(s, 0);
      if (i == 0) {
         throw f_113032_.create();
      } else {
         int j = Math.round(f * (float)i);
         if (j < 0) {
            throw f_113033_.create(j);
         } else {
            return j;
         }
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_113044_, SuggestionsBuilder p_113045_) {
      StringReader stringreader = new StringReader(p_113045_.getRemaining());

      try {
         stringreader.readFloat();
      } catch (CommandSyntaxException commandsyntaxexception) {
         return p_113045_.buildFuture();
      }

      return SharedSuggestionProvider.m_82970_(f_113034_.keySet(), p_113045_.createOffset(p_113045_.getStart() + stringreader.getCursor()));
   }

   public Collection<String> getExamples() {
      return f_113031_;
   }

   static {
      f_113034_.put("d", 24000);
      f_113034_.put("s", 20);
      f_113034_.put("t", 1);
      f_113034_.put("", 1);
   }
}