package net.minecraft.commands.arguments;

import com.google.common.collect.Maps;
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
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class EntityAnchorArgument implements ArgumentType<EntityAnchorArgument.Anchor> {
   private static final Collection<String> f_90346_ = Arrays.asList("eyes", "feet");
   private static final DynamicCommandExceptionType f_90347_ = new DynamicCommandExceptionType((p_90357_) -> {
      return new TranslatableComponent("argument.anchor.invalid", p_90357_);
   });

   public static EntityAnchorArgument.Anchor m_90353_(CommandContext<CommandSourceStack> p_90354_, String p_90355_) {
      return p_90354_.getArgument(p_90355_, EntityAnchorArgument.Anchor.class);
   }

   public static EntityAnchorArgument m_90350_() {
      return new EntityAnchorArgument();
   }

   public EntityAnchorArgument.Anchor parse(StringReader p_90352_) throws CommandSyntaxException {
      int i = p_90352_.getCursor();
      String s = p_90352_.readUnquotedString();
      EntityAnchorArgument.Anchor entityanchorargument$anchor = EntityAnchorArgument.Anchor.m_90384_(s);
      if (entityanchorargument$anchor == null) {
         p_90352_.setCursor(i);
         throw f_90347_.createWithContext(p_90352_, s);
      } else {
         return entityanchorargument$anchor;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_90360_, SuggestionsBuilder p_90361_) {
      return SharedSuggestionProvider.m_82970_(EntityAnchorArgument.Anchor.f_90366_.keySet(), p_90361_);
   }

   public Collection<String> getExamples() {
      return f_90346_;
   }

   public static enum Anchor {
      FEET("feet", (p_90389_, p_90390_) -> {
         return p_90389_;
      }),
      EYES("eyes", (p_90382_, p_90383_) -> {
         return new Vec3(p_90382_.f_82479_, p_90382_.f_82480_ + (double)p_90383_.m_20192_(), p_90382_.f_82481_);
      });

      static final Map<String, EntityAnchorArgument.Anchor> f_90366_ = Util.m_137469_(Maps.newHashMap(), (p_90387_) -> {
         for(EntityAnchorArgument.Anchor entityanchorargument$anchor : values()) {
            p_90387_.put(entityanchorargument$anchor.f_90367_, entityanchorargument$anchor);
         }

      });
      private final String f_90367_;
      private final BiFunction<Vec3, Entity, Vec3> f_90368_;

      private Anchor(String p_90374_, BiFunction<Vec3, Entity, Vec3> p_90375_) {
         this.f_90367_ = p_90374_;
         this.f_90368_ = p_90375_;
      }

      @Nullable
      public static EntityAnchorArgument.Anchor m_90384_(String p_90385_) {
         return f_90366_.get(p_90385_);
      }

      public Vec3 m_90377_(Entity p_90378_) {
         return this.f_90368_.apply(p_90378_.m_20182_(), p_90378_);
      }

      public Vec3 m_90379_(CommandSourceStack p_90380_) {
         Entity entity = p_90380_.m_81373_();
         return entity == null ? p_90380_.m_81371_() : this.f_90368_.apply(p_90380_.m_81371_(), entity);
      }
   }
}