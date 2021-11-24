package net.minecraft.commands.arguments.blocks;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockStateParser {
   public static final SimpleCommandExceptionType f_116741_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.block.tag.disallowed"));
   public static final DynamicCommandExceptionType f_116742_ = new DynamicCommandExceptionType((p_116790_) -> {
      return new TranslatableComponent("argument.block.id.invalid", p_116790_);
   });
   public static final Dynamic2CommandExceptionType f_116743_ = new Dynamic2CommandExceptionType((p_116820_, p_116821_) -> {
      return new TranslatableComponent("argument.block.property.unknown", p_116820_, p_116821_);
   });
   public static final Dynamic2CommandExceptionType f_116744_ = new Dynamic2CommandExceptionType((p_116813_, p_116814_) -> {
      return new TranslatableComponent("argument.block.property.duplicate", p_116814_, p_116813_);
   });
   public static final Dynamic3CommandExceptionType f_116745_ = new Dynamic3CommandExceptionType((p_116795_, p_116796_, p_116797_) -> {
      return new TranslatableComponent("argument.block.property.invalid", p_116795_, p_116797_, p_116796_);
   });
   public static final Dynamic2CommandExceptionType f_116746_ = new Dynamic2CommandExceptionType((p_116792_, p_116793_) -> {
      return new TranslatableComponent("argument.block.property.novalue", p_116792_, p_116793_);
   });
   public static final SimpleCommandExceptionType f_116747_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.block.property.unclosed"));
   private static final char f_174101_ = '[';
   private static final char f_174102_ = '{';
   private static final char f_174103_ = ']';
   private static final char f_174104_ = '=';
   private static final char f_174105_ = ',';
   private static final char f_174106_ = '#';
   private static final BiFunction<SuggestionsBuilder, TagCollection<Block>, CompletableFuture<Suggestions>> f_116748_ = (p_116857_, p_116858_) -> {
      return p_116857_.buildFuture();
   };
   private final StringReader f_116749_;
   private final boolean f_116750_;
   private final Map<Property<?>, Comparable<?>> f_116751_ = Maps.newHashMap();
   private final Map<String, String> f_116752_ = Maps.newHashMap();
   private ResourceLocation f_116753_ = new ResourceLocation("");
   private StateDefinition<Block, BlockState> f_116754_;
   private BlockState f_116755_;
   @Nullable
   private CompoundTag f_116756_;
   private ResourceLocation f_116757_ = new ResourceLocation("");
   private int f_116758_;
   private BiFunction<SuggestionsBuilder, TagCollection<Block>, CompletableFuture<Suggestions>> f_116759_ = f_116748_;

   public BlockStateParser(StringReader p_116762_, boolean p_116763_) {
      this.f_116749_ = p_116762_;
      this.f_116750_ = p_116763_;
   }

   public Map<Property<?>, Comparable<?>> m_116764_() {
      return this.f_116751_;
   }

   @Nullable
   public BlockState m_116808_() {
      return this.f_116755_;
   }

   @Nullable
   public CompoundTag m_116815_() {
      return this.f_116756_;
   }

   @Nullable
   public ResourceLocation m_116822_() {
      return this.f_116757_;
   }

   public BlockStateParser m_116806_(boolean p_116807_) throws CommandSyntaxException {
      this.f_116759_ = this::m_116853_;
      if (this.f_116749_.canRead() && this.f_116749_.peek() == '#') {
         this.m_116830_();
         this.f_116759_ = this::m_116843_;
         if (this.f_116749_.canRead() && this.f_116749_.peek() == '[') {
            this.m_116838_();
            this.f_116759_ = this::m_116831_;
         }
      } else {
         this.m_116826_();
         this.f_116759_ = this::m_116847_;
         if (this.f_116749_.canRead() && this.f_116749_.peek() == '[') {
            this.m_116834_();
            this.f_116759_ = this::m_116831_;
         }
      }

      if (p_116807_ && this.f_116749_.canRead() && this.f_116749_.peek() == '{') {
         this.f_116759_ = f_116748_;
         this.m_116842_();
      }

      return this;
   }

   private CompletableFuture<Suggestions> m_116809_(SuggestionsBuilder p_116810_, TagCollection<Block> p_116811_) {
      if (p_116810_.getRemaining().isEmpty()) {
         p_116810_.suggest(String.valueOf(']'));
      }

      return this.m_116823_(p_116810_, p_116811_);
   }

   private CompletableFuture<Suggestions> m_116816_(SuggestionsBuilder p_116817_, TagCollection<Block> p_116818_) {
      if (p_116817_.getRemaining().isEmpty()) {
         p_116817_.suggest(String.valueOf(']'));
      }

      return this.m_116827_(p_116817_, p_116818_);
   }

   private CompletableFuture<Suggestions> m_116823_(SuggestionsBuilder p_116824_, TagCollection<Block> p_116825_) {
      String s = p_116824_.getRemaining().toLowerCase(Locale.ROOT);

      for(Property<?> property : this.f_116755_.m_61147_()) {
         if (!this.f_116751_.containsKey(property) && property.m_61708_().startsWith(s)) {
            p_116824_.suggest(property.m_61708_() + "=");
         }
      }

      return p_116824_.buildFuture();
   }

   private CompletableFuture<Suggestions> m_116827_(SuggestionsBuilder p_116828_, TagCollection<Block> p_116829_) {
      String s = p_116828_.getRemaining().toLowerCase(Locale.ROOT);
      if (this.f_116757_ != null && !this.f_116757_.m_135815_().isEmpty()) {
         Tag<Block> tag = p_116829_.m_13404_(this.f_116757_);
         if (tag != null) {
            for(Block block : tag.m_6497_()) {
               for(Property<?> property : block.m_49965_().m_61092_()) {
                  if (!this.f_116752_.containsKey(property.m_61708_()) && property.m_61708_().startsWith(s)) {
                     p_116828_.suggest(property.m_61708_() + "=");
                  }
               }
            }
         }
      }

      return p_116828_.buildFuture();
   }

   private CompletableFuture<Suggestions> m_116831_(SuggestionsBuilder p_116832_, TagCollection<Block> p_116833_) {
      if (p_116832_.getRemaining().isEmpty() && this.m_116767_(p_116833_)) {
         p_116832_.suggest(String.valueOf('{'));
      }

      return p_116832_.buildFuture();
   }

   private boolean m_116767_(TagCollection<Block> p_116768_) {
      if (this.f_116755_ != null) {
         return this.f_116755_.m_155947_();
      } else {
         if (this.f_116757_ != null) {
            Tag<Block> tag = p_116768_.m_13404_(this.f_116757_);
            if (tag != null) {
               for(Block block : tag.m_6497_()) {
                  if (block.m_49966_().m_155947_()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private CompletableFuture<Suggestions> m_116835_(SuggestionsBuilder p_116836_, TagCollection<Block> p_116837_) {
      if (p_116836_.getRemaining().isEmpty()) {
         p_116836_.suggest(String.valueOf('='));
      }

      return p_116836_.buildFuture();
   }

   private CompletableFuture<Suggestions> m_116839_(SuggestionsBuilder p_116840_, TagCollection<Block> p_116841_) {
      if (p_116840_.getRemaining().isEmpty()) {
         p_116840_.suggest(String.valueOf(']'));
      }

      if (p_116840_.getRemaining().isEmpty() && this.f_116751_.size() < this.f_116755_.m_61147_().size()) {
         p_116840_.suggest(String.valueOf(','));
      }

      return p_116840_.buildFuture();
   }

   private static <T extends Comparable<T>> SuggestionsBuilder m_116786_(SuggestionsBuilder p_116787_, Property<T> p_116788_) {
      for(T t : p_116788_.m_6908_()) {
         if (t instanceof Integer) {
            p_116787_.suggest((Integer)t);
         } else {
            p_116787_.suggest(p_116788_.m_6940_(t));
         }
      }

      return p_116787_;
   }

   private CompletableFuture<Suggestions> m_116782_(SuggestionsBuilder p_116783_, TagCollection<Block> p_116784_, String p_116785_) {
      boolean flag = false;
      if (this.f_116757_ != null && !this.f_116757_.m_135815_().isEmpty()) {
         Tag<Block> tag = p_116784_.m_13404_(this.f_116757_);
         if (tag != null) {
            for(Block block : tag.m_6497_()) {
               Property<?> property = block.m_49965_().m_61081_(p_116785_);
               if (property != null) {
                  m_116786_(p_116783_, property);
               }

               if (!flag) {
                  for(Property<?> property1 : block.m_49965_().m_61092_()) {
                     if (!this.f_116752_.containsKey(property1.m_61708_())) {
                        flag = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      if (flag) {
         p_116783_.suggest(String.valueOf(','));
      }

      p_116783_.suggest(String.valueOf(']'));
      return p_116783_.buildFuture();
   }

   private CompletableFuture<Suggestions> m_116843_(SuggestionsBuilder p_116844_, TagCollection<Block> p_116845_) {
      if (p_116844_.getRemaining().isEmpty()) {
         Tag<Block> tag = p_116845_.m_13404_(this.f_116757_);
         if (tag != null) {
            boolean flag = false;
            boolean flag1 = false;

            for(Block block : tag.m_6497_()) {
               flag |= !block.m_49965_().m_61092_().isEmpty();
               flag1 |= block.m_49966_().m_155947_();
               if (flag && flag1) {
                  break;
               }
            }

            if (flag) {
               p_116844_.suggest(String.valueOf('['));
            }

            if (flag1) {
               p_116844_.suggest(String.valueOf('{'));
            }
         }
      }

      return this.m_116850_(p_116844_, p_116845_);
   }

   private CompletableFuture<Suggestions> m_116847_(SuggestionsBuilder p_116848_, TagCollection<Block> p_116849_) {
      if (p_116848_.getRemaining().isEmpty()) {
         if (!this.f_116755_.m_60734_().m_49965_().m_61092_().isEmpty()) {
            p_116848_.suggest(String.valueOf('['));
         }

         if (this.f_116755_.m_155947_()) {
            p_116848_.suggest(String.valueOf('{'));
         }
      }

      return p_116848_.buildFuture();
   }

   private CompletableFuture<Suggestions> m_116850_(SuggestionsBuilder p_116851_, TagCollection<Block> p_116852_) {
      return SharedSuggestionProvider.m_82926_(p_116852_.m_13406_(), p_116851_.createOffset(this.f_116758_).add(p_116851_));
   }

   private CompletableFuture<Suggestions> m_116853_(SuggestionsBuilder p_116854_, TagCollection<Block> p_116855_) {
      if (this.f_116750_) {
         SharedSuggestionProvider.m_82929_(p_116855_.m_13406_(), p_116854_, String.valueOf('#'));
      }

      SharedSuggestionProvider.m_82926_(Registry.f_122824_.m_6566_(), p_116854_);
      return p_116854_.buildFuture();
   }

   public void m_116826_() throws CommandSyntaxException {
      int i = this.f_116749_.getCursor();
      this.f_116753_ = ResourceLocation.m_135818_(this.f_116749_);
      Block block = Registry.f_122824_.m_6612_(this.f_116753_).orElseThrow(() -> {
         this.f_116749_.setCursor(i);
         return f_116742_.createWithContext(this.f_116749_, this.f_116753_.toString());
      });
      this.f_116754_ = block.m_49965_();
      this.f_116755_ = block.m_49966_();
   }

   public void m_116830_() throws CommandSyntaxException {
      if (!this.f_116750_) {
         throw f_116741_.create();
      } else {
         this.f_116759_ = this::m_116850_;
         this.f_116749_.expect('#');
         this.f_116758_ = this.f_116749_.getCursor();
         this.f_116757_ = ResourceLocation.m_135818_(this.f_116749_);
      }
   }

   public void m_116834_() throws CommandSyntaxException {
      this.f_116749_.skip();
      this.f_116759_ = this::m_116809_;
      this.f_116749_.skipWhitespace();

      while(true) {
         if (this.f_116749_.canRead() && this.f_116749_.peek() != ']') {
            this.f_116749_.skipWhitespace();
            int i = this.f_116749_.getCursor();
            String s = this.f_116749_.readString();
            Property<?> property = this.f_116754_.m_61081_(s);
            if (property == null) {
               this.f_116749_.setCursor(i);
               throw f_116743_.createWithContext(this.f_116749_, this.f_116753_.toString(), s);
            }

            if (this.f_116751_.containsKey(property)) {
               this.f_116749_.setCursor(i);
               throw f_116744_.createWithContext(this.f_116749_, this.f_116753_.toString(), s);
            }

            this.f_116749_.skipWhitespace();
            this.f_116759_ = this::m_116835_;
            if (!this.f_116749_.canRead() || this.f_116749_.peek() != '=') {
               throw f_116746_.createWithContext(this.f_116749_, this.f_116753_.toString(), s);
            }

            this.f_116749_.skip();
            this.f_116749_.skipWhitespace();
            this.f_116759_ = (p_116773_, p_116774_) -> {
               return m_116786_(p_116773_, property).buildFuture();
            };
            int j = this.f_116749_.getCursor();
            this.m_116775_(property, this.f_116749_.readString(), j);
            this.f_116759_ = this::m_116839_;
            this.f_116749_.skipWhitespace();
            if (!this.f_116749_.canRead()) {
               continue;
            }

            if (this.f_116749_.peek() == ',') {
               this.f_116749_.skip();
               this.f_116759_ = this::m_116823_;
               continue;
            }

            if (this.f_116749_.peek() != ']') {
               throw f_116747_.createWithContext(this.f_116749_);
            }
         }

         if (this.f_116749_.canRead()) {
            this.f_116749_.skip();
            return;
         }

         throw f_116747_.createWithContext(this.f_116749_);
      }
   }

   public void m_116838_() throws CommandSyntaxException {
      this.f_116749_.skip();
      this.f_116759_ = this::m_116816_;
      int i = -1;
      this.f_116749_.skipWhitespace();

      while(true) {
         if (this.f_116749_.canRead() && this.f_116749_.peek() != ']') {
            this.f_116749_.skipWhitespace();
            int j = this.f_116749_.getCursor();
            String s = this.f_116749_.readString();
            if (this.f_116752_.containsKey(s)) {
               this.f_116749_.setCursor(j);
               throw f_116744_.createWithContext(this.f_116749_, this.f_116753_.toString(), s);
            }

            this.f_116749_.skipWhitespace();
            if (!this.f_116749_.canRead() || this.f_116749_.peek() != '=') {
               this.f_116749_.setCursor(j);
               throw f_116746_.createWithContext(this.f_116749_, this.f_116753_.toString(), s);
            }

            this.f_116749_.skip();
            this.f_116749_.skipWhitespace();
            this.f_116759_ = (p_116800_, p_116801_) -> {
               return this.m_116782_(p_116800_, p_116801_, s);
            };
            i = this.f_116749_.getCursor();
            String s1 = this.f_116749_.readString();
            this.f_116752_.put(s, s1);
            this.f_116749_.skipWhitespace();
            if (!this.f_116749_.canRead()) {
               continue;
            }

            i = -1;
            if (this.f_116749_.peek() == ',') {
               this.f_116749_.skip();
               this.f_116759_ = this::m_116827_;
               continue;
            }

            if (this.f_116749_.peek() != ']') {
               throw f_116747_.createWithContext(this.f_116749_);
            }
         }

         if (this.f_116749_.canRead()) {
            this.f_116749_.skip();
            return;
         }

         if (i >= 0) {
            this.f_116749_.setCursor(i);
         }

         throw f_116747_.createWithContext(this.f_116749_);
      }
   }

   public void m_116842_() throws CommandSyntaxException {
      this.f_116756_ = (new TagParser(this.f_116749_)).m_129373_();
   }

   private <T extends Comparable<T>> void m_116775_(Property<T> p_116776_, String p_116777_, int p_116778_) throws CommandSyntaxException {
      Optional<T> optional = p_116776_.m_6215_(p_116777_);
      if (optional.isPresent()) {
         this.f_116755_ = this.f_116755_.m_61124_(p_116776_, optional.get());
         this.f_116751_.put(p_116776_, optional.get());
      } else {
         this.f_116749_.setCursor(p_116778_);
         throw f_116745_.createWithContext(this.f_116749_, this.f_116753_.toString(), p_116776_.m_61708_(), p_116777_);
      }
   }

   public static String m_116769_(BlockState p_116770_) {
      StringBuilder stringbuilder = new StringBuilder(Registry.f_122824_.m_7981_(p_116770_.m_60734_()).toString());
      if (!p_116770_.m_61147_().isEmpty()) {
         stringbuilder.append('[');
         boolean flag = false;

         for(Entry<Property<?>, Comparable<?>> entry : p_116770_.m_61148_().entrySet()) {
            if (flag) {
               stringbuilder.append(',');
            }

            m_116802_(stringbuilder, entry.getKey(), entry.getValue());
            flag = true;
         }

         stringbuilder.append(']');
      }

      return stringbuilder.toString();
   }

   private static <T extends Comparable<T>> void m_116802_(StringBuilder p_116803_, Property<T> p_116804_, Comparable<?> p_116805_) {
      p_116803_.append(p_116804_.m_61708_());
      p_116803_.append('=');
      p_116803_.append(p_116804_.m_6940_((T)p_116805_));
   }

   public CompletableFuture<Suggestions> m_116779_(SuggestionsBuilder p_116780_, TagCollection<Block> p_116781_) {
      return this.f_116759_.apply(p_116780_.createOffset(this.f_116749_.getCursor()), p_116781_);
   }

   public Map<String, String> m_116846_() {
      return this.f_116752_;
   }
}