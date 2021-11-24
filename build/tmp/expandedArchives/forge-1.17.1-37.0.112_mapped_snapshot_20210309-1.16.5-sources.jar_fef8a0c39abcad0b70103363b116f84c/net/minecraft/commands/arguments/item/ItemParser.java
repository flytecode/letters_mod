package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.item.Item;

public class ItemParser {
   public static final SimpleCommandExceptionType f_120991_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.item.tag.disallowed"));
   public static final DynamicCommandExceptionType f_120992_ = new DynamicCommandExceptionType((p_121013_) -> {
      return new TranslatableComponent("argument.item.id.invalid", p_121013_);
   });
   private static final char f_175091_ = '{';
   private static final char f_175092_ = '#';
   private static final BiFunction<SuggestionsBuilder, TagCollection<Item>, CompletableFuture<Suggestions>> f_120993_ = (p_121028_, p_121029_) -> {
      return p_121028_.buildFuture();
   };
   private final StringReader f_120994_;
   private final boolean f_120995_;
   private Item f_120997_;
   @Nullable
   private CompoundTag f_120998_;
   private ResourceLocation f_120999_ = new ResourceLocation("");
   private int f_121000_;
   private BiFunction<SuggestionsBuilder, TagCollection<Item>, CompletableFuture<Suggestions>> f_121001_ = f_120993_;

   public ItemParser(StringReader p_121004_, boolean p_121005_) {
      this.f_120994_ = p_121004_;
      this.f_120995_ = p_121005_;
   }

   public Item m_121014_() {
      return this.f_120997_;
   }

   @Nullable
   public CompoundTag m_121018_() {
      return this.f_120998_;
   }

   public ResourceLocation m_121022_() {
      return this.f_120999_;
   }

   public void m_121026_() throws CommandSyntaxException {
      int i = this.f_120994_.getCursor();
      ResourceLocation resourcelocation = ResourceLocation.m_135818_(this.f_120994_);
      this.f_120997_ = Registry.f_122827_.m_6612_(resourcelocation).orElseThrow(() -> {
         this.f_120994_.setCursor(i);
         return f_120992_.createWithContext(this.f_120994_, resourcelocation.toString());
      });
   }

   public void m_121030_() throws CommandSyntaxException {
      if (!this.f_120995_) {
         throw f_120991_.create();
      } else {
         this.f_121001_ = this::m_121019_;
         this.f_120994_.expect('#');
         this.f_121000_ = this.f_120994_.getCursor();
         this.f_120999_ = ResourceLocation.m_135818_(this.f_120994_);
      }
   }

   public void m_121031_() throws CommandSyntaxException {
      this.f_120998_ = (new TagParser(this.f_120994_)).m_129373_();
   }

   public ItemParser m_121032_() throws CommandSyntaxException {
      this.f_121001_ = this::m_121023_;
      if (this.f_120994_.canRead() && this.f_120994_.peek() == '#') {
         this.m_121030_();
      } else {
         this.m_121026_();
         this.f_121001_ = this::m_121015_;
      }

      if (this.f_120994_.canRead() && this.f_120994_.peek() == '{') {
         this.f_121001_ = f_120993_;
         this.m_121031_();
      }

      return this;
   }

   private CompletableFuture<Suggestions> m_121015_(SuggestionsBuilder p_121016_, TagCollection<Item> p_121017_) {
      if (p_121016_.getRemaining().isEmpty()) {
         p_121016_.suggest(String.valueOf('{'));
      }

      return p_121016_.buildFuture();
   }

   private CompletableFuture<Suggestions> m_121019_(SuggestionsBuilder p_121020_, TagCollection<Item> p_121021_) {
      return SharedSuggestionProvider.m_82926_(p_121021_.m_13406_(), p_121020_.createOffset(this.f_121000_));
   }

   private CompletableFuture<Suggestions> m_121023_(SuggestionsBuilder p_121024_, TagCollection<Item> p_121025_) {
      if (this.f_120995_) {
         SharedSuggestionProvider.m_82929_(p_121025_.m_13406_(), p_121024_, String.valueOf('#'));
      }

      return SharedSuggestionProvider.m_82926_(Registry.f_122827_.m_6566_(), p_121024_);
   }

   public CompletableFuture<Suggestions> m_121009_(SuggestionsBuilder p_121010_, TagCollection<Item> p_121011_) {
      return this.f_121001_.apply(p_121010_.createOffset(this.f_120994_.getCursor()), p_121011_);
   }
}