package net.minecraft.commands.arguments.blocks;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.tags.BlockTags;

public class BlockStateArgument implements ArgumentType<BlockInput> {
   private static final Collection<String> f_116117_ = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}");

   public static BlockStateArgument m_116120_() {
      return new BlockStateArgument();
   }

   public BlockInput parse(StringReader p_116122_) throws CommandSyntaxException {
      BlockStateParser blockstateparser = (new BlockStateParser(p_116122_, false)).m_116806_(true);
      return new BlockInput(blockstateparser.m_116808_(), blockstateparser.m_116764_().keySet(), blockstateparser.m_116815_());
   }

   public static BlockInput m_116123_(CommandContext<CommandSourceStack> p_116124_, String p_116125_) {
      return p_116124_.getArgument(p_116125_, BlockInput.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_116128_, SuggestionsBuilder p_116129_) {
      StringReader stringreader = new StringReader(p_116129_.getInput());
      stringreader.setCursor(p_116129_.getStart());
      BlockStateParser blockstateparser = new BlockStateParser(stringreader, false);

      try {
         blockstateparser.m_116806_(true);
      } catch (CommandSyntaxException commandsyntaxexception) {
      }

      return blockstateparser.m_116779_(p_116129_, BlockTags.m_13115_());
   }

   public Collection<String> getExamples() {
      return f_116117_;
   }
}