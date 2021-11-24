package net.minecraft.commands.synchronization;

import com.google.common.collect.Maps;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class SuggestionProviders {
   private static final Map<ResourceLocation, SuggestionProvider<SharedSuggestionProvider>> f_121646_ = Maps.newHashMap();
   private static final ResourceLocation f_121647_ = new ResourceLocation("ask_server");
   public static final SuggestionProvider<SharedSuggestionProvider> f_121641_ = m_121658_(f_121647_, (p_121673_, p_121674_) -> {
      return p_121673_.getSource().m_5497_(p_121673_, p_121674_);
   });
   public static final SuggestionProvider<CommandSourceStack> f_121642_ = m_121658_(new ResourceLocation("all_recipes"), (p_121670_, p_121671_) -> {
      return SharedSuggestionProvider.m_82957_(p_121670_.getSource().m_6860_(), p_121671_);
   });
   public static final SuggestionProvider<CommandSourceStack> f_121643_ = m_121658_(new ResourceLocation("available_sounds"), (p_121667_, p_121668_) -> {
      return SharedSuggestionProvider.m_82926_(p_121667_.getSource().m_5984_(), p_121668_);
   });
   public static final SuggestionProvider<CommandSourceStack> f_121644_ = m_121658_(new ResourceLocation("available_biomes"), (p_121662_, p_121663_) -> {
      return SharedSuggestionProvider.m_82926_(p_121662_.getSource().m_5894_().m_175515_(Registry.f_122885_).m_6566_(), p_121663_);
   });
   public static final SuggestionProvider<CommandSourceStack> f_121645_ = m_121658_(new ResourceLocation("summonable_entities"), (p_121652_, p_121653_) -> {
      return SharedSuggestionProvider.m_82960_(Registry.f_122826_.m_123024_().filter(EntityType::m_20654_), p_121653_, EntityType::m_20613_, (p_175213_) -> {
         return new TranslatableComponent(Util.m_137492_("entity", EntityType.m_20613_(p_175213_)));
      });
   });

   public static <S extends SharedSuggestionProvider> SuggestionProvider<S> m_121658_(ResourceLocation p_121659_, SuggestionProvider<SharedSuggestionProvider> p_121660_) {
      if (f_121646_.containsKey(p_121659_)) {
         throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + p_121659_);
      } else {
         f_121646_.put(p_121659_, p_121660_);
         return (SuggestionProvider<S>)new SuggestionProviders.Wrapper(p_121659_, p_121660_);
      }
   }

   public static SuggestionProvider<SharedSuggestionProvider> m_121656_(ResourceLocation p_121657_) {
      return f_121646_.getOrDefault(p_121657_, f_121641_);
   }

   public static ResourceLocation m_121654_(SuggestionProvider<SharedSuggestionProvider> p_121655_) {
      return p_121655_ instanceof SuggestionProviders.Wrapper ? ((SuggestionProviders.Wrapper)p_121655_).f_121676_ : f_121647_;
   }

   public static SuggestionProvider<SharedSuggestionProvider> m_121664_(SuggestionProvider<SharedSuggestionProvider> p_121665_) {
      return p_121665_ instanceof SuggestionProviders.Wrapper ? p_121665_ : f_121641_;
   }

   protected static class Wrapper implements SuggestionProvider<SharedSuggestionProvider> {
      private final SuggestionProvider<SharedSuggestionProvider> f_121675_;
      final ResourceLocation f_121676_;

      public Wrapper(ResourceLocation p_121678_, SuggestionProvider<SharedSuggestionProvider> p_121679_) {
         this.f_121675_ = p_121679_;
         this.f_121676_ = p_121678_;
      }

      public CompletableFuture<Suggestions> getSuggestions(CommandContext<SharedSuggestionProvider> p_121683_, SuggestionsBuilder p_121684_) throws CommandSyntaxException {
         return this.f_121675_.getSuggestions(p_121683_, p_121684_);
      }
   }
}