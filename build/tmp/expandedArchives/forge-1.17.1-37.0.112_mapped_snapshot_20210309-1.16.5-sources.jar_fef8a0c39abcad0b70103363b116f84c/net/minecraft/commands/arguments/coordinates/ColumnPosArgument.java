package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ColumnPos;

public class ColumnPosArgument implements ArgumentType<Coordinates> {
   private static final Collection<String> f_118986_ = Arrays.asList("0 0", "~ ~", "~1 ~-2", "^ ^", "^-1 ^0");
   public static final SimpleCommandExceptionType f_118985_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos2d.incomplete"));

   public static ColumnPosArgument m_118989_() {
      return new ColumnPosArgument();
   }

   public static ColumnPos m_118992_(CommandContext<CommandSourceStack> p_118993_, String p_118994_) {
      BlockPos blockpos = p_118993_.getArgument(p_118994_, Coordinates.class).m_119568_(p_118993_.getSource());
      return new ColumnPos(blockpos.m_123341_(), blockpos.m_123343_());
   }

   public Coordinates parse(StringReader p_118991_) throws CommandSyntaxException {
      int i = p_118991_.getCursor();
      if (!p_118991_.canRead()) {
         throw f_118985_.createWithContext(p_118991_);
      } else {
         WorldCoordinate worldcoordinate = WorldCoordinate.m_120869_(p_118991_);
         if (p_118991_.canRead() && p_118991_.peek() == ' ') {
            p_118991_.skip();
            WorldCoordinate worldcoordinate1 = WorldCoordinate.m_120869_(p_118991_);
            return new WorldCoordinates(worldcoordinate, new WorldCoordinate(true, 0.0D), worldcoordinate1);
         } else {
            p_118991_.setCursor(i);
            throw f_118985_.createWithContext(p_118991_);
         }
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_118997_, SuggestionsBuilder p_118998_) {
      if (!(p_118997_.getSource() instanceof SharedSuggestionProvider)) {
         return Suggestions.empty();
      } else {
         String s = p_118998_.getRemaining();
         Collection<SharedSuggestionProvider.TextCoordinates> collection;
         if (!s.isEmpty() && s.charAt(0) == '^') {
            collection = Collections.singleton(SharedSuggestionProvider.TextCoordinates.f_82987_);
         } else {
            collection = ((SharedSuggestionProvider)p_118997_.getSource()).m_6265_();
         }

         return SharedSuggestionProvider.m_82976_(s, collection, p_118998_, Commands.m_82120_(this::parse));
      }
   }

   public Collection<String> getExamples() {
      return f_118986_;
   }
}