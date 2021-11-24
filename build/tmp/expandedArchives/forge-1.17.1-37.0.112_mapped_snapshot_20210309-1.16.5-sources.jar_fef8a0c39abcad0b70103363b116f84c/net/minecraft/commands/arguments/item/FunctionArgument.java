package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;

public class FunctionArgument implements ArgumentType<FunctionArgument.Result> {
   private static final Collection<String> f_120902_ = Arrays.asList("foo", "foo:bar", "#foo");
   private static final DynamicCommandExceptionType f_120903_ = new DynamicCommandExceptionType((p_120927_) -> {
      return new TranslatableComponent("arguments.function.tag.unknown", p_120927_);
   });
   private static final DynamicCommandExceptionType f_120904_ = new DynamicCommandExceptionType((p_120917_) -> {
      return new TranslatableComponent("arguments.function.unknown", p_120917_);
   });

   public static FunctionArgument m_120907_() {
      return new FunctionArgument();
   }

   public FunctionArgument.Result parse(StringReader p_120909_) throws CommandSyntaxException {
      if (p_120909_.canRead() && p_120909_.peek() == '#') {
         p_120909_.skip();
         final ResourceLocation resourcelocation1 = ResourceLocation.m_135818_(p_120909_);
         return new FunctionArgument.Result() {
            public Collection<CommandFunction> m_7588_(CommandContext<CommandSourceStack> p_120943_) throws CommandSyntaxException {
               Tag<CommandFunction> tag = FunctionArgument.m_120931_(p_120943_, resourcelocation1);
               return tag.m_6497_();
            }

            public Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> m_5911_(CommandContext<CommandSourceStack> p_120945_) throws CommandSyntaxException {
               return Pair.of(resourcelocation1, Either.right(FunctionArgument.m_120931_(p_120945_, resourcelocation1)));
            }
         };
      } else {
         final ResourceLocation resourcelocation = ResourceLocation.m_135818_(p_120909_);
         return new FunctionArgument.Result() {
            public Collection<CommandFunction> m_7588_(CommandContext<CommandSourceStack> p_120952_) throws CommandSyntaxException {
               return Collections.singleton(FunctionArgument.m_120928_(p_120952_, resourcelocation));
            }

            public Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> m_5911_(CommandContext<CommandSourceStack> p_120954_) throws CommandSyntaxException {
               return Pair.of(resourcelocation, Either.left(FunctionArgument.m_120928_(p_120954_, resourcelocation)));
            }
         };
      }
   }

   static CommandFunction m_120928_(CommandContext<CommandSourceStack> p_120929_, ResourceLocation p_120930_) throws CommandSyntaxException {
      return p_120929_.getSource().m_81377_().m_129890_().m_136118_(p_120930_).orElseThrow(() -> {
         return f_120904_.create(p_120930_.toString());
      });
   }

   static Tag<CommandFunction> m_120931_(CommandContext<CommandSourceStack> p_120932_, ResourceLocation p_120933_) throws CommandSyntaxException {
      Tag<CommandFunction> tag = p_120932_.getSource().m_81377_().m_129890_().m_136123_(p_120933_);
      if (tag == null) {
         throw f_120903_.create(p_120933_.toString());
      } else {
         return tag;
      }
   }

   public static Collection<CommandFunction> m_120910_(CommandContext<CommandSourceStack> p_120911_, String p_120912_) throws CommandSyntaxException {
      return p_120911_.getArgument(p_120912_, FunctionArgument.Result.class).m_7588_(p_120911_);
   }

   public static Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> m_120920_(CommandContext<CommandSourceStack> p_120921_, String p_120922_) throws CommandSyntaxException {
      return p_120921_.getArgument(p_120922_, FunctionArgument.Result.class).m_5911_(p_120921_);
   }

   public Collection<String> getExamples() {
      return f_120902_;
   }

   public interface Result {
      Collection<CommandFunction> m_7588_(CommandContext<CommandSourceStack> p_120955_) throws CommandSyntaxException;

      Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> m_5911_(CommandContext<CommandSourceStack> p_120956_) throws CommandSyntaxException;
   }
}