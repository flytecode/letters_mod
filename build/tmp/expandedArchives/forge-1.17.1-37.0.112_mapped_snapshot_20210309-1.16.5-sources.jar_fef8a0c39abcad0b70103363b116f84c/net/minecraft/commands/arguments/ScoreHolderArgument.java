package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;

public class ScoreHolderArgument implements ArgumentType<ScoreHolderArgument.Result> {
   public static final SuggestionProvider<CommandSourceStack> f_108210_ = (p_108221_, p_108222_) -> {
      StringReader stringreader = new StringReader(p_108222_.getInput());
      stringreader.setCursor(p_108222_.getStart());
      EntitySelectorParser entityselectorparser = new EntitySelectorParser(stringreader);

      try {
         entityselectorparser.m_121377_();
      } catch (CommandSyntaxException commandsyntaxexception) {
      }

      return entityselectorparser.m_121249_(p_108222_, (p_171606_) -> {
         SharedSuggestionProvider.m_82970_(p_108221_.getSource().m_5982_(), p_171606_);
      });
   };
   private static final Collection<String> f_108211_ = Arrays.asList("Player", "0123", "*", "@e");
   private static final SimpleCommandExceptionType f_108212_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.scoreHolder.empty"));
   private static final byte f_171603_ = 1;
   final boolean f_108213_;

   public ScoreHolderArgument(boolean p_108216_) {
      this.f_108213_ = p_108216_;
   }

   public static String m_108223_(CommandContext<CommandSourceStack> p_108224_, String p_108225_) throws CommandSyntaxException {
      return m_108243_(p_108224_, p_108225_).iterator().next();
   }

   public static Collection<String> m_108243_(CommandContext<CommandSourceStack> p_108244_, String p_108245_) throws CommandSyntaxException {
      return m_108226_(p_108244_, p_108245_, Collections::emptyList);
   }

   public static Collection<String> m_108246_(CommandContext<CommandSourceStack> p_108247_, String p_108248_) throws CommandSyntaxException {
      return m_108226_(p_108247_, p_108248_, p_108247_.getSource().m_81377_().m_129896_()::m_83482_);
   }

   public static Collection<String> m_108226_(CommandContext<CommandSourceStack> p_108227_, String p_108228_, Supplier<Collection<String>> p_108229_) throws CommandSyntaxException {
      Collection<String> collection = p_108227_.getArgument(p_108228_, ScoreHolderArgument.Result.class).m_6582_(p_108227_.getSource(), p_108229_);
      if (collection.isEmpty()) {
         throw EntityArgument.f_91439_.create();
      } else {
         return collection;
      }
   }

   public static ScoreHolderArgument m_108217_() {
      return new ScoreHolderArgument(false);
   }

   public static ScoreHolderArgument m_108239_() {
      return new ScoreHolderArgument(true);
   }

   public ScoreHolderArgument.Result parse(StringReader p_108219_) throws CommandSyntaxException {
      if (p_108219_.canRead() && p_108219_.peek() == '@') {
         EntitySelectorParser entityselectorparser = new EntitySelectorParser(p_108219_);
         EntitySelector entityselector = entityselectorparser.m_121377_();
         if (!this.f_108213_ && entityselector.m_121138_() > 1) {
            throw EntityArgument.f_91436_.create();
         } else {
            return new ScoreHolderArgument.SelectorResult(entityselector);
         }
      } else {
         int i = p_108219_.getCursor();

         while(p_108219_.canRead() && p_108219_.peek() != ' ') {
            p_108219_.skip();
         }

         String s = p_108219_.getString().substring(i, p_108219_.getCursor());
         if (s.equals("*")) {
            return (p_108231_, p_108232_) -> {
               Collection<String> collection1 = p_108232_.get();
               if (collection1.isEmpty()) {
                  throw f_108212_.create();
               } else {
                  return collection1;
               }
            };
         } else {
            Collection<String> collection = Collections.singleton(s);
            return (p_108237_, p_108238_) -> {
               return collection;
            };
         }
      }
   }

   public Collection<String> getExamples() {
      return f_108211_;
   }

   @FunctionalInterface
   public interface Result {
      Collection<String> m_6582_(CommandSourceStack p_108252_, Supplier<Collection<String>> p_108253_) throws CommandSyntaxException;
   }

   public static class SelectorResult implements ScoreHolderArgument.Result {
      private final EntitySelector f_108254_;

      public SelectorResult(EntitySelector p_108256_) {
         this.f_108254_ = p_108256_;
      }

      public Collection<String> m_6582_(CommandSourceStack p_108258_, Supplier<Collection<String>> p_108259_) throws CommandSyntaxException {
         List<? extends Entity> list = this.f_108254_.m_121160_(p_108258_);
         if (list.isEmpty()) {
            throw EntityArgument.f_91439_.create();
         } else {
            List<String> list1 = Lists.newArrayList();

            for(Entity entity : list) {
               list1.add(entity.m_6302_());
            }

            return list1;
         }
      }
   }

   public static class Serializer implements ArgumentSerializer<ScoreHolderArgument> {
      public void m_6017_(ScoreHolderArgument p_108271_, FriendlyByteBuf p_108272_) {
         byte b0 = 0;
         if (p_108271_.f_108213_) {
            b0 = (byte)(b0 | 1);
         }

         p_108272_.writeByte(b0);
      }

      public ScoreHolderArgument m_7813_(FriendlyByteBuf p_108274_) {
         byte b0 = p_108274_.readByte();
         boolean flag = (b0 & 1) != 0;
         return new ScoreHolderArgument(flag);
      }

      public void m_6964_(ScoreHolderArgument p_108268_, JsonObject p_108269_) {
         p_108269_.addProperty("amount", p_108268_.f_108213_ ? "multiple" : "single");
      }
   }
}