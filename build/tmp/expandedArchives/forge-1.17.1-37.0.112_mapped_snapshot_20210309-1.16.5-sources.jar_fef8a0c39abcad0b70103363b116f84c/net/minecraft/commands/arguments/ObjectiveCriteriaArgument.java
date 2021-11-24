package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ObjectiveCriteriaArgument implements ArgumentType<ObjectiveCriteria> {
   private static final Collection<String> f_102552_ = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
   public static final DynamicCommandExceptionType f_102551_ = new DynamicCommandExceptionType((p_102569_) -> {
      return new TranslatableComponent("argument.criteria.invalid", p_102569_);
   });

   private ObjectiveCriteriaArgument() {
   }

   public static ObjectiveCriteriaArgument m_102555_() {
      return new ObjectiveCriteriaArgument();
   }

   public static ObjectiveCriteria m_102565_(CommandContext<CommandSourceStack> p_102566_, String p_102567_) {
      return p_102566_.getArgument(p_102567_, ObjectiveCriteria.class);
   }

   public ObjectiveCriteria parse(StringReader p_102560_) throws CommandSyntaxException {
      int i = p_102560_.getCursor();

      while(p_102560_.canRead() && p_102560_.peek() != ' ') {
         p_102560_.skip();
      }

      String s = p_102560_.getString().substring(i, p_102560_.getCursor());
      return ObjectiveCriteria.m_83614_(s).orElseThrow(() -> {
         p_102560_.setCursor(i);
         return f_102551_.create(s);
      });
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_102572_, SuggestionsBuilder p_102573_) {
      List<String> list = Lists.newArrayList(ObjectiveCriteria.m_166115_());

      for(StatType<?> stattype : Registry.f_122867_) {
         for(Object object : stattype.m_12893_()) {
            String s = this.m_102556_(stattype, object);
            list.add(s);
         }
      }

      return SharedSuggestionProvider.m_82970_(list, p_102573_);
   }

   public <T> String m_102556_(StatType<T> p_102557_, Object p_102558_) {
      return Stat.m_12862_(p_102557_, (T)p_102558_);
   }

   public Collection<String> getExamples() {
      return f_102552_;
   }
}