package net.minecraft.commands.arguments.blocks;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockPredicateArgument implements ArgumentType<BlockPredicateArgument.Result> {
   private static final Collection<String> f_115566_ = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "#stone", "#stone[foo=bar]{baz=nbt}");
   private static final DynamicCommandExceptionType f_115567_ = new DynamicCommandExceptionType((p_115580_) -> {
      return new TranslatableComponent("arguments.block.tag.unknown", p_115580_);
   });

   public static BlockPredicateArgument m_115570_() {
      return new BlockPredicateArgument();
   }

   public BlockPredicateArgument.Result parse(StringReader p_115572_) throws CommandSyntaxException {
      BlockStateParser blockstateparser = (new BlockStateParser(p_115572_, true)).m_116806_(true);
      if (blockstateparser.m_116808_() != null) {
         BlockPredicateArgument.BlockPredicate blockpredicateargument$blockpredicate = new BlockPredicateArgument.BlockPredicate(blockstateparser.m_116808_(), blockstateparser.m_116764_().keySet(), blockstateparser.m_116815_());
         return (p_115578_) -> {
            return blockpredicateargument$blockpredicate;
         };
      } else {
         ResourceLocation resourcelocation = blockstateparser.m_116822_();
         return (p_173736_) -> {
            Tag<Block> tag = p_173736_.m_144458_(Registry.f_122901_, resourcelocation, (p_173732_) -> {
               return f_115567_.create(p_173732_.toString());
            });
            return new BlockPredicateArgument.TagPredicate(tag, blockstateparser.m_116846_(), blockstateparser.m_116815_());
         };
      }
   }

   public static Predicate<BlockInWorld> m_115573_(CommandContext<CommandSourceStack> p_115574_, String p_115575_) throws CommandSyntaxException {
      return p_115574_.getArgument(p_115575_, BlockPredicateArgument.Result.class).m_115602_(p_115574_.getSource().m_81377_().m_129895_());
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_115587_, SuggestionsBuilder p_115588_) {
      StringReader stringreader = new StringReader(p_115588_.getInput());
      stringreader.setCursor(p_115588_.getStart());
      BlockStateParser blockstateparser = new BlockStateParser(stringreader, true);

      try {
         blockstateparser.m_116806_(true);
      } catch (CommandSyntaxException commandsyntaxexception) {
      }

      return blockstateparser.m_116779_(p_115588_, BlockTags.m_13115_());
   }

   public Collection<String> getExamples() {
      return f_115566_;
   }

   static class BlockPredicate implements Predicate<BlockInWorld> {
      private final BlockState f_115591_;
      private final Set<Property<?>> f_115592_;
      @Nullable
      private final CompoundTag f_115593_;

      public BlockPredicate(BlockState p_115595_, Set<Property<?>> p_115596_, @Nullable CompoundTag p_115597_) {
         this.f_115591_ = p_115595_;
         this.f_115592_ = p_115596_;
         this.f_115593_ = p_115597_;
      }

      public boolean test(BlockInWorld p_115599_) {
         BlockState blockstate = p_115599_.m_61168_();
         if (!blockstate.m_60713_(this.f_115591_.m_60734_())) {
            return false;
         } else {
            for(Property<?> property : this.f_115592_) {
               if (blockstate.m_61143_(property) != this.f_115591_.m_61143_(property)) {
                  return false;
               }
            }

            if (this.f_115593_ == null) {
               return true;
            } else {
               BlockEntity blockentity = p_115599_.m_61174_();
               return blockentity != null && NbtUtils.m_129235_(this.f_115593_, blockentity.m_6945_(new CompoundTag()), true);
            }
         }
      }
   }

   public interface Result {
      Predicate<BlockInWorld> m_115602_(TagContainer p_115603_) throws CommandSyntaxException;
   }

   static class TagPredicate implements Predicate<BlockInWorld> {
      private final Tag<Block> f_115604_;
      @Nullable
      private final CompoundTag f_115605_;
      private final Map<String, String> f_115606_;

      TagPredicate(Tag<Block> p_115608_, Map<String, String> p_115609_, @Nullable CompoundTag p_115610_) {
         this.f_115604_ = p_115608_;
         this.f_115606_ = p_115609_;
         this.f_115605_ = p_115610_;
      }

      public boolean test(BlockInWorld p_115617_) {
         BlockState blockstate = p_115617_.m_61168_();
         if (!blockstate.m_60620_(this.f_115604_)) {
            return false;
         } else {
            for(Entry<String, String> entry : this.f_115606_.entrySet()) {
               Property<?> property = blockstate.m_60734_().m_49965_().m_61081_(entry.getKey());
               if (property == null) {
                  return false;
               }

               Comparable<?> comparable = (Comparable)property.m_6215_(entry.getValue()).orElse(null);
               if (comparable == null) {
                  return false;
               }

               if (blockstate.m_61143_(property) != comparable) {
                  return false;
               }
            }

            if (this.f_115605_ == null) {
               return true;
            } else {
               BlockEntity blockentity = p_115617_.m_61174_();
               return blockentity != null && NbtUtils.m_129235_(this.f_115605_, blockentity.m_6945_(new CompoundTag()), true);
            }
         }
      }
   }
}