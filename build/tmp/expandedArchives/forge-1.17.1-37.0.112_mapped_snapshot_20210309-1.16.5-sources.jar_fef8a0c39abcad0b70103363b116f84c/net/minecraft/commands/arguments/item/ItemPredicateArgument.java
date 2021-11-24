package net.minecraft.commands.arguments.item;

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
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemPredicateArgument implements ArgumentType<ItemPredicateArgument.Result> {
   private static final Collection<String> f_121033_ = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
   private static final DynamicCommandExceptionType f_121034_ = new DynamicCommandExceptionType((p_121047_) -> {
      return new TranslatableComponent("arguments.item.tag.unknown", p_121047_);
   });

   public static ItemPredicateArgument m_121037_() {
      return new ItemPredicateArgument();
   }

   public ItemPredicateArgument.Result parse(StringReader p_121039_) throws CommandSyntaxException {
      ItemParser itemparser = (new ItemParser(p_121039_, true)).m_121032_();
      if (itemparser.m_121014_() != null) {
         ItemPredicateArgument.ItemPredicate itempredicateargument$itempredicate = new ItemPredicateArgument.ItemPredicate(itemparser.m_121014_(), itemparser.m_121018_());
         return (p_121045_) -> {
            return itempredicateargument$itempredicate;
         };
      } else {
         ResourceLocation resourcelocation = itemparser.m_121022_();
         return (p_175098_) -> {
            Tag<Item> tag = p_175098_.getSource().m_81377_().m_129895_().m_144458_(Registry.f_122904_, resourcelocation, (p_175094_) -> {
               return f_121034_.create(p_175094_.toString());
            });
            return new ItemPredicateArgument.TagPredicate(tag, itemparser.m_121018_());
         };
      }
   }

   public static Predicate<ItemStack> m_121040_(CommandContext<CommandSourceStack> p_121041_, String p_121042_) throws CommandSyntaxException {
      return p_121041_.getArgument(p_121042_, ItemPredicateArgument.Result.class).m_121067_(p_121041_);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_121054_, SuggestionsBuilder p_121055_) {
      StringReader stringreader = new StringReader(p_121055_.getInput());
      stringreader.setCursor(p_121055_.getStart());
      ItemParser itemparser = new ItemParser(stringreader, true);

      try {
         itemparser.m_121032_();
      } catch (CommandSyntaxException commandsyntaxexception) {
      }

      return itemparser.m_121009_(p_121055_, ItemTags.m_13193_());
   }

   public Collection<String> getExamples() {
      return f_121033_;
   }

   static class ItemPredicate implements Predicate<ItemStack> {
      private final Item f_121058_;
      @Nullable
      private final CompoundTag f_121059_;

      public ItemPredicate(Item p_121061_, @Nullable CompoundTag p_121062_) {
         this.f_121058_ = p_121061_;
         this.f_121059_ = p_121062_;
      }

      public boolean test(ItemStack p_121064_) {
         return p_121064_.m_150930_(this.f_121058_) && NbtUtils.m_129235_(this.f_121059_, p_121064_.m_41783_(), true);
      }
   }

   public interface Result {
      Predicate<ItemStack> m_121067_(CommandContext<CommandSourceStack> p_121068_) throws CommandSyntaxException;
   }

   static class TagPredicate implements Predicate<ItemStack> {
      private final Tag<Item> f_121069_;
      @Nullable
      private final CompoundTag f_121070_;

      public TagPredicate(Tag<Item> p_121072_, @Nullable CompoundTag p_121073_) {
         this.f_121069_ = p_121072_;
         this.f_121070_ = p_121073_;
      }

      public boolean test(ItemStack p_121075_) {
         return p_121075_.m_150922_(this.f_121069_) && NbtUtils.m_129235_(this.f_121070_, p_121075_.m_41783_(), true);
      }
   }
}