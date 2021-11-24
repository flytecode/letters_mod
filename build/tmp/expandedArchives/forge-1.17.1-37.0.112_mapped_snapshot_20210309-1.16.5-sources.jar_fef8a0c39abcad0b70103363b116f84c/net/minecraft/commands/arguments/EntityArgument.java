package net.minecraft.commands.arguments;

import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class EntityArgument implements ArgumentType<EntitySelector> {
   private static final Collection<String> f_91442_ = Arrays.asList("Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498");
   public static final SimpleCommandExceptionType f_91436_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.entity.toomany"));
   public static final SimpleCommandExceptionType f_91437_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.player.toomany"));
   public static final SimpleCommandExceptionType f_91438_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.player.entities"));
   public static final SimpleCommandExceptionType f_91439_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.entity.notfound.entity"));
   public static final SimpleCommandExceptionType f_91440_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.entity.notfound.player"));
   public static final SimpleCommandExceptionType f_91441_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.entity.selector.not_allowed"));
   private static final byte f_167701_ = 1;
   private static final byte f_167702_ = 2;
   final boolean f_91443_;
   final boolean f_91444_;

   protected EntityArgument(boolean p_91447_, boolean p_91448_) {
      this.f_91443_ = p_91447_;
      this.f_91444_ = p_91448_;
   }

   public static EntityArgument m_91449_() {
      return new EntityArgument(true, false);
   }

   public static Entity m_91452_(CommandContext<CommandSourceStack> p_91453_, String p_91454_) throws CommandSyntaxException {
      return p_91453_.getArgument(p_91454_, EntitySelector.class).m_121139_(p_91453_.getSource());
   }

   public static EntityArgument m_91460_() {
      return new EntityArgument(false, false);
   }

   public static Collection<? extends Entity> m_91461_(CommandContext<CommandSourceStack> p_91462_, String p_91463_) throws CommandSyntaxException {
      Collection<? extends Entity> collection = m_91467_(p_91462_, p_91463_);
      if (collection.isEmpty()) {
         throw f_91439_.create();
      } else {
         return collection;
      }
   }

   public static Collection<? extends Entity> m_91467_(CommandContext<CommandSourceStack> p_91468_, String p_91469_) throws CommandSyntaxException {
      return p_91468_.getArgument(p_91469_, EntitySelector.class).m_121160_(p_91468_.getSource());
   }

   public static Collection<ServerPlayer> m_91471_(CommandContext<CommandSourceStack> p_91472_, String p_91473_) throws CommandSyntaxException {
      return p_91472_.getArgument(p_91473_, EntitySelector.class).m_121166_(p_91472_.getSource());
   }

   public static EntityArgument m_91466_() {
      return new EntityArgument(true, true);
   }

   public static ServerPlayer m_91474_(CommandContext<CommandSourceStack> p_91475_, String p_91476_) throws CommandSyntaxException {
      return p_91475_.getArgument(p_91476_, EntitySelector.class).m_121163_(p_91475_.getSource());
   }

   public static EntityArgument m_91470_() {
      return new EntityArgument(false, true);
   }

   public static Collection<ServerPlayer> m_91477_(CommandContext<CommandSourceStack> p_91478_, String p_91479_) throws CommandSyntaxException {
      List<ServerPlayer> list = p_91478_.getArgument(p_91479_, EntitySelector.class).m_121166_(p_91478_.getSource());
      if (list.isEmpty()) {
         throw f_91440_.create();
      } else {
         return list;
      }
   }

   public EntitySelector parse(StringReader p_91451_) throws CommandSyntaxException {
      int i = 0;
      EntitySelectorParser entityselectorparser = new EntitySelectorParser(p_91451_);
      EntitySelector entityselector = entityselectorparser.m_121377_();
      if (entityselector.m_121138_() > 1 && this.f_91443_) {
         if (this.f_91444_) {
            p_91451_.setCursor(0);
            throw f_91437_.createWithContext(p_91451_);
         } else {
            p_91451_.setCursor(0);
            throw f_91436_.createWithContext(p_91451_);
         }
      } else if (entityselector.m_121159_() && this.f_91444_ && !entityselector.m_121162_()) {
         p_91451_.setCursor(0);
         throw f_91438_.createWithContext(p_91451_);
      } else {
         return entityselector;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_91482_, SuggestionsBuilder p_91483_) {
      if (p_91482_.getSource() instanceof SharedSuggestionProvider) {
         StringReader stringreader = new StringReader(p_91483_.getInput());
         stringreader.setCursor(p_91483_.getStart());
         SharedSuggestionProvider sharedsuggestionprovider = (SharedSuggestionProvider)p_91482_.getSource();
         EntitySelectorParser entityselectorparser = new EntitySelectorParser(stringreader, sharedsuggestionprovider.m_6761_(2));

         try {
            entityselectorparser.m_121377_();
         } catch (CommandSyntaxException commandsyntaxexception) {
         }

         return entityselectorparser.m_121249_(p_91483_, (p_91457_) -> {
            Collection<String> collection = sharedsuggestionprovider.m_5982_();
            Iterable<String> iterable = (Iterable<String>)(this.f_91444_ ? collection : Iterables.concat(collection, sharedsuggestionprovider.m_6264_()));
            SharedSuggestionProvider.m_82970_(iterable, p_91457_);
         });
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return f_91442_;
   }

   public static class Serializer implements ArgumentSerializer<EntityArgument> {
      public void m_6017_(EntityArgument p_91497_, FriendlyByteBuf p_91498_) {
         byte b0 = 0;
         if (p_91497_.f_91443_) {
            b0 = (byte)(b0 | 1);
         }

         if (p_91497_.f_91444_) {
            b0 = (byte)(b0 | 2);
         }

         p_91498_.writeByte(b0);
      }

      public EntityArgument m_7813_(FriendlyByteBuf p_91500_) {
         byte b0 = p_91500_.readByte();
         return new EntityArgument((b0 & 1) != 0, (b0 & 2) != 0);
      }

      public void m_6964_(EntityArgument p_91494_, JsonObject p_91495_) {
         p_91495_.addProperty("amount", p_91494_.f_91443_ ? "single" : "multiple");
         p_91495_.addProperty("type", p_91494_.f_91444_ ? "players" : "entities");
      }
   }
}