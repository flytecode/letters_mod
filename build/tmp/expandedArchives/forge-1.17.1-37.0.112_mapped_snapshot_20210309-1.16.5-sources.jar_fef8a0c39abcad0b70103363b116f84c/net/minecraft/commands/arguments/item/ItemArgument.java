package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.tags.ItemTags;

public class ItemArgument implements ArgumentType<ItemInput> {
   private static final Collection<String> f_120957_ = Arrays.asList("stick", "minecraft:stick", "stick{foo=bar}");

   public static ItemArgument m_120960_() {
      return new ItemArgument();
   }

   public ItemInput parse(StringReader p_120962_) throws CommandSyntaxException {
      ItemParser itemparser = (new ItemParser(p_120962_, false)).m_121032_();
      return new ItemInput(itemparser.m_121014_(), itemparser.m_121018_());
   }

   public static <S> ItemInput m_120963_(CommandContext<S> p_120964_, String p_120965_) {
      return p_120964_.getArgument(p_120965_, ItemInput.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_120968_, SuggestionsBuilder p_120969_) {
      StringReader stringreader = new StringReader(p_120969_.getInput());
      stringreader.setCursor(p_120969_.getStart());
      ItemParser itemparser = new ItemParser(stringreader, false);

      try {
         itemparser.m_121032_();
      } catch (CommandSyntaxException commandsyntaxexception) {
      }

      return itemparser.m_121009_(p_120969_, ItemTags.m_13193_());
   }

   public Collection<String> getExamples() {
      return f_120957_;
   }
}