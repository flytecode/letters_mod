package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class DimensionArgument implements ArgumentType<ResourceLocation> {
   private static final Collection<String> f_88801_ = Stream.of(Level.f_46428_, Level.f_46429_).map((p_88814_) -> {
      return p_88814_.m_135782_().toString();
   }).collect(Collectors.toList());
   private static final DynamicCommandExceptionType f_88802_ = new DynamicCommandExceptionType((p_88812_) -> {
      return new TranslatableComponent("argument.dimension.invalid", p_88812_);
   });

   public ResourceLocation parse(StringReader p_88807_) throws CommandSyntaxException {
      return ResourceLocation.m_135818_(p_88807_);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_88817_, SuggestionsBuilder p_88818_) {
      return p_88817_.getSource() instanceof SharedSuggestionProvider ? SharedSuggestionProvider.m_82957_(((SharedSuggestionProvider)p_88817_.getSource()).m_6553_().stream().map(ResourceKey::m_135782_), p_88818_) : Suggestions.empty();
   }

   public Collection<String> getExamples() {
      return f_88801_;
   }

   public static DimensionArgument m_88805_() {
      return new DimensionArgument();
   }

   public static ServerLevel m_88808_(CommandContext<CommandSourceStack> p_88809_, String p_88810_) throws CommandSyntaxException {
      ResourceLocation resourcelocation = p_88809_.getArgument(p_88810_, ResourceLocation.class);
      ResourceKey<Level> resourcekey = ResourceKey.m_135785_(Registry.f_122819_, resourcelocation);
      ServerLevel serverlevel = p_88809_.getSource().m_81377_().m_129880_(resourcekey);
      if (serverlevel == null) {
         throw f_88802_.create(resourcelocation);
      } else {
         return serverlevel;
      }
   }
}