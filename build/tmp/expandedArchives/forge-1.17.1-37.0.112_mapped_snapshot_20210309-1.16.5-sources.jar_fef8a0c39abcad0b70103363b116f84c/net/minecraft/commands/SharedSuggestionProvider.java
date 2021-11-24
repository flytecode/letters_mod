package net.minecraft.commands;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public interface SharedSuggestionProvider {
   Collection<String> m_5982_();

   default Collection<String> m_6264_() {
      return Collections.emptyList();
   }

   Collection<String> m_5983_();

   Collection<ResourceLocation> m_5984_();

   Stream<ResourceLocation> m_6860_();

   CompletableFuture<Suggestions> m_5497_(CommandContext<SharedSuggestionProvider> p_82912_, SuggestionsBuilder p_82913_);

   default Collection<SharedSuggestionProvider.TextCoordinates> m_6265_() {
      return Collections.singleton(SharedSuggestionProvider.TextCoordinates.f_82988_);
   }

   default Collection<SharedSuggestionProvider.TextCoordinates> m_6284_() {
      return Collections.singleton(SharedSuggestionProvider.TextCoordinates.f_82988_);
   }

   Set<ResourceKey<Level>> m_6553_();

   RegistryAccess m_5894_();

   boolean m_6761_(int p_82986_);

   static <T> void m_82944_(Iterable<T> p_82945_, String p_82946_, Function<T, ResourceLocation> p_82947_, Consumer<T> p_82948_) {
      boolean flag = p_82946_.indexOf(58) > -1;

      for(T t : p_82945_) {
         ResourceLocation resourcelocation = p_82947_.apply(t);
         if (flag) {
            String s = resourcelocation.toString();
            if (m_82949_(p_82946_, s)) {
               p_82948_.accept(t);
            }
         } else if (m_82949_(p_82946_, resourcelocation.m_135827_()) || resourcelocation.m_135827_().equals("minecraft") && m_82949_(p_82946_, resourcelocation.m_135815_())) {
            p_82948_.accept(t);
         }
      }

   }

   static <T> void m_82938_(Iterable<T> p_82939_, String p_82940_, String p_82941_, Function<T, ResourceLocation> p_82942_, Consumer<T> p_82943_) {
      if (p_82940_.isEmpty()) {
         p_82939_.forEach(p_82943_);
      } else {
         String s = Strings.commonPrefix(p_82940_, p_82941_);
         if (!s.isEmpty()) {
            String s1 = p_82940_.substring(s.length());
            m_82944_(p_82939_, s1, p_82942_, p_82943_);
         }
      }

   }

   static CompletableFuture<Suggestions> m_82929_(Iterable<ResourceLocation> p_82930_, SuggestionsBuilder p_82931_, String p_82932_) {
      String s = p_82931_.getRemaining().toLowerCase(Locale.ROOT);
      m_82938_(p_82930_, s, p_82932_, (p_82985_) -> {
         return p_82985_;
      }, (p_82917_) -> {
         p_82931_.suggest(p_82932_ + p_82917_);
      });
      return p_82931_.buildFuture();
   }

   static CompletableFuture<Suggestions> m_82926_(Iterable<ResourceLocation> p_82927_, SuggestionsBuilder p_82928_) {
      String s = p_82928_.getRemaining().toLowerCase(Locale.ROOT);
      m_82944_(p_82927_, s, (p_82966_) -> {
         return p_82966_;
      }, (p_82925_) -> {
         p_82928_.suggest(p_82925_.toString());
      });
      return p_82928_.buildFuture();
   }

   static <T> CompletableFuture<Suggestions> m_82933_(Iterable<T> p_82934_, SuggestionsBuilder p_82935_, Function<T, ResourceLocation> p_82936_, Function<T, Message> p_82937_) {
      String s = p_82935_.getRemaining().toLowerCase(Locale.ROOT);
      m_82944_(p_82934_, s, p_82936_, (p_82922_) -> {
         p_82935_.suggest(p_82936_.apply(p_82922_).toString(), p_82937_.apply(p_82922_));
      });
      return p_82935_.buildFuture();
   }

   static CompletableFuture<Suggestions> m_82957_(Stream<ResourceLocation> p_82958_, SuggestionsBuilder p_82959_) {
      return m_82926_(p_82958_::iterator, p_82959_);
   }

   static <T> CompletableFuture<Suggestions> m_82960_(Stream<T> p_82961_, SuggestionsBuilder p_82962_, Function<T, ResourceLocation> p_82963_, Function<T, Message> p_82964_) {
      return m_82933_(p_82961_::iterator, p_82962_, p_82963_, p_82964_);
   }

   static CompletableFuture<Suggestions> m_82952_(String p_82953_, Collection<SharedSuggestionProvider.TextCoordinates> p_82954_, SuggestionsBuilder p_82955_, Predicate<String> p_82956_) {
      List<String> list = Lists.newArrayList();
      if (Strings.isNullOrEmpty(p_82953_)) {
         for(SharedSuggestionProvider.TextCoordinates sharedsuggestionprovider$textcoordinates : p_82954_) {
            String s = sharedsuggestionprovider$textcoordinates.f_82989_ + " " + sharedsuggestionprovider$textcoordinates.f_82990_ + " " + sharedsuggestionprovider$textcoordinates.f_82991_;
            if (p_82956_.test(s)) {
               list.add(sharedsuggestionprovider$textcoordinates.f_82989_);
               list.add(sharedsuggestionprovider$textcoordinates.f_82989_ + " " + sharedsuggestionprovider$textcoordinates.f_82990_);
               list.add(s);
            }
         }
      } else {
         String[] astring = p_82953_.split(" ");
         if (astring.length == 1) {
            for(SharedSuggestionProvider.TextCoordinates sharedsuggestionprovider$textcoordinates1 : p_82954_) {
               String s1 = astring[0] + " " + sharedsuggestionprovider$textcoordinates1.f_82990_ + " " + sharedsuggestionprovider$textcoordinates1.f_82991_;
               if (p_82956_.test(s1)) {
                  list.add(astring[0] + " " + sharedsuggestionprovider$textcoordinates1.f_82990_);
                  list.add(s1);
               }
            }
         } else if (astring.length == 2) {
            for(SharedSuggestionProvider.TextCoordinates sharedsuggestionprovider$textcoordinates2 : p_82954_) {
               String s2 = astring[0] + " " + astring[1] + " " + sharedsuggestionprovider$textcoordinates2.f_82991_;
               if (p_82956_.test(s2)) {
                  list.add(s2);
               }
            }
         }
      }

      return m_82970_(list, p_82955_);
   }

   static CompletableFuture<Suggestions> m_82976_(String p_82977_, Collection<SharedSuggestionProvider.TextCoordinates> p_82978_, SuggestionsBuilder p_82979_, Predicate<String> p_82980_) {
      List<String> list = Lists.newArrayList();
      if (Strings.isNullOrEmpty(p_82977_)) {
         for(SharedSuggestionProvider.TextCoordinates sharedsuggestionprovider$textcoordinates : p_82978_) {
            String s = sharedsuggestionprovider$textcoordinates.f_82989_ + " " + sharedsuggestionprovider$textcoordinates.f_82991_;
            if (p_82980_.test(s)) {
               list.add(sharedsuggestionprovider$textcoordinates.f_82989_);
               list.add(s);
            }
         }
      } else {
         String[] astring = p_82977_.split(" ");
         if (astring.length == 1) {
            for(SharedSuggestionProvider.TextCoordinates sharedsuggestionprovider$textcoordinates1 : p_82978_) {
               String s1 = astring[0] + " " + sharedsuggestionprovider$textcoordinates1.f_82991_;
               if (p_82980_.test(s1)) {
                  list.add(s1);
               }
            }
         }
      }

      return m_82970_(list, p_82979_);
   }

   static CompletableFuture<Suggestions> m_82970_(Iterable<String> p_82971_, SuggestionsBuilder p_82972_) {
      String s = p_82972_.getRemaining().toLowerCase(Locale.ROOT);

      for(String s1 : p_82971_) {
         if (m_82949_(s, s1.toLowerCase(Locale.ROOT))) {
            p_82972_.suggest(s1);
         }
      }

      return p_82972_.buildFuture();
   }

   static CompletableFuture<Suggestions> m_82981_(Stream<String> p_82982_, SuggestionsBuilder p_82983_) {
      String s = p_82983_.getRemaining().toLowerCase(Locale.ROOT);
      p_82982_.filter((p_82975_) -> {
         return m_82949_(s, p_82975_.toLowerCase(Locale.ROOT));
      }).forEach(p_82983_::suggest);
      return p_82983_.buildFuture();
   }

   static CompletableFuture<Suggestions> m_82967_(String[] p_82968_, SuggestionsBuilder p_82969_) {
      String s = p_82969_.getRemaining().toLowerCase(Locale.ROOT);

      for(String s1 : p_82968_) {
         if (m_82949_(s, s1.toLowerCase(Locale.ROOT))) {
            p_82969_.suggest(s1);
         }
      }

      return p_82969_.buildFuture();
   }

   static <T> CompletableFuture<Suggestions> m_165916_(Iterable<T> p_165917_, SuggestionsBuilder p_165918_, Function<T, String> p_165919_, Function<T, Message> p_165920_) {
      String s = p_165918_.getRemaining().toLowerCase(Locale.ROOT);

      for(T t : p_165917_) {
         String s1 = p_165919_.apply(t);
         if (m_82949_(s, s1.toLowerCase(Locale.ROOT))) {
            p_165918_.suggest(s1, p_165920_.apply(t));
         }
      }

      return p_165918_.buildFuture();
   }

   static boolean m_82949_(String p_82950_, String p_82951_) {
      for(int i = 0; !p_82951_.startsWith(p_82950_, i); ++i) {
         i = p_82951_.indexOf(95, i);
         if (i < 0) {
            return false;
         }
      }

      return true;
   }

   public static class TextCoordinates {
      public static final SharedSuggestionProvider.TextCoordinates f_82987_ = new SharedSuggestionProvider.TextCoordinates("^", "^", "^");
      public static final SharedSuggestionProvider.TextCoordinates f_82988_ = new SharedSuggestionProvider.TextCoordinates("~", "~", "~");
      public final String f_82989_;
      public final String f_82990_;
      public final String f_82991_;

      public TextCoordinates(String p_82994_, String p_82995_, String p_82996_) {
         this.f_82989_ = p_82994_;
         this.f_82990_ = p_82995_;
         this.f_82991_ = p_82996_;
      }
   }
}