package net.minecraft.commands.arguments;

import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class ComponentArgument implements ArgumentType<Component> {
   private static final Collection<String> f_87111_ = Arrays.asList("\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]");
   public static final DynamicCommandExceptionType f_87110_ = new DynamicCommandExceptionType((p_87121_) -> {
      return new TranslatableComponent("argument.component.invalid", p_87121_);
   });

   private ComponentArgument() {
   }

   public static Component m_87117_(CommandContext<CommandSourceStack> p_87118_, String p_87119_) {
      return p_87118_.getArgument(p_87119_, Component.class);
   }

   public static ComponentArgument m_87114_() {
      return new ComponentArgument();
   }

   public Component parse(StringReader p_87116_) throws CommandSyntaxException {
      try {
         Component component = Component.Serializer.m_130699_(p_87116_);
         if (component == null) {
            throw f_87110_.createWithContext(p_87116_, "empty");
         } else {
            return component;
         }
      } catch (JsonParseException jsonparseexception) {
         String s = jsonparseexception.getCause() != null ? jsonparseexception.getCause().getMessage() : jsonparseexception.getMessage();
         throw f_87110_.createWithContext(p_87116_, s);
      }
   }

   public Collection<String> getExamples() {
      return f_87111_;
   }
}