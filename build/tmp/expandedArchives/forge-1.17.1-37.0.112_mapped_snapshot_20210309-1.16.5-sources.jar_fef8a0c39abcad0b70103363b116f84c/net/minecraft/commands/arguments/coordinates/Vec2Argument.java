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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class Vec2Argument implements ArgumentType<Coordinates> {
   private static final Collection<String> f_120817_ = Arrays.asList("0 0", "~ ~", "0.1 -0.5", "~1 ~-2");
   public static final SimpleCommandExceptionType f_120816_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos2d.incomplete"));
   private final boolean f_120818_;

   public Vec2Argument(boolean p_120821_) {
      this.f_120818_ = p_120821_;
   }

   public static Vec2Argument m_120822_() {
      return new Vec2Argument(true);
   }

   public static Vec2Argument m_174954_(boolean p_174955_) {
      return new Vec2Argument(p_174955_);
   }

   public static Vec2 m_120825_(CommandContext<CommandSourceStack> p_120826_, String p_120827_) {
      Vec3 vec3 = p_120826_.getArgument(p_120827_, Coordinates.class).m_6955_(p_120826_.getSource());
      return new Vec2((float)vec3.f_82479_, (float)vec3.f_82481_);
   }

   public Coordinates parse(StringReader p_120824_) throws CommandSyntaxException {
      int i = p_120824_.getCursor();
      if (!p_120824_.canRead()) {
         throw f_120816_.createWithContext(p_120824_);
      } else {
         WorldCoordinate worldcoordinate = WorldCoordinate.m_120871_(p_120824_, this.f_120818_);
         if (p_120824_.canRead() && p_120824_.peek() == ' ') {
            p_120824_.skip();
            WorldCoordinate worldcoordinate1 = WorldCoordinate.m_120871_(p_120824_, this.f_120818_);
            return new WorldCoordinates(worldcoordinate, new WorldCoordinate(true, 0.0D), worldcoordinate1);
         } else {
            p_120824_.setCursor(i);
            throw f_120816_.createWithContext(p_120824_);
         }
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_120830_, SuggestionsBuilder p_120831_) {
      if (!(p_120830_.getSource() instanceof SharedSuggestionProvider)) {
         return Suggestions.empty();
      } else {
         String s = p_120831_.getRemaining();
         Collection<SharedSuggestionProvider.TextCoordinates> collection;
         if (!s.isEmpty() && s.charAt(0) == '^') {
            collection = Collections.singleton(SharedSuggestionProvider.TextCoordinates.f_82987_);
         } else {
            collection = ((SharedSuggestionProvider)p_120830_.getSource()).m_6284_();
         }

         return SharedSuggestionProvider.m_82976_(s, collection, p_120831_, Commands.m_82120_(this::parse));
      }
   }

   public Collection<String> getExamples() {
      return f_120817_;
   }
}