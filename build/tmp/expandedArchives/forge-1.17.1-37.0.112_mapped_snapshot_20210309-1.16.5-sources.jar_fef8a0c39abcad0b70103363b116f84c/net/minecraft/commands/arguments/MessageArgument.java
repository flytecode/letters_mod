package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public class MessageArgument implements ArgumentType<MessageArgument.Message> {
   private static final Collection<String> f_96829_ = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

   public static MessageArgument m_96832_() {
      return new MessageArgument();
   }

   public static Component m_96835_(CommandContext<CommandSourceStack> p_96836_, String p_96837_) throws CommandSyntaxException {
      return p_96836_.getArgument(p_96837_, MessageArgument.Message.class).m_96849_(p_96836_.getSource(), p_96836_.getSource().m_6761_(2));
   }

   public MessageArgument.Message parse(StringReader p_96834_) throws CommandSyntaxException {
      return MessageArgument.Message.m_96846_(p_96834_, true);
   }

   public Collection<String> getExamples() {
      return f_96829_;
   }

   public static class Message {
      private final String f_96841_;
      private final MessageArgument.Part[] f_96842_;

      public Message(String p_96844_, MessageArgument.Part[] p_96845_) {
         this.f_96841_ = p_96844_;
         this.f_96842_ = p_96845_;
      }

      public String m_169112_() {
         return this.f_96841_;
      }

      public MessageArgument.Part[] m_169113_() {
         return this.f_96842_;
      }

      public Component m_96849_(CommandSourceStack p_96850_, boolean p_96851_) throws CommandSyntaxException {
         if (this.f_96842_.length != 0 && p_96851_) {
            MutableComponent mutablecomponent = new TextComponent(this.f_96841_.substring(0, this.f_96842_[0].m_96859_()));
            int i = this.f_96842_[0].m_96859_();

            for(MessageArgument.Part messageargument$part : this.f_96842_) {
               Component component = messageargument$part.m_96860_(p_96850_);
               if (i < messageargument$part.m_96859_()) {
                  mutablecomponent.m_130946_(this.f_96841_.substring(i, messageargument$part.m_96859_()));
               }

               if (component != null) {
                  mutablecomponent.m_7220_(component);
               }

               i = messageargument$part.m_96862_();
            }

            if (i < this.f_96841_.length()) {
               mutablecomponent.m_130946_(this.f_96841_.substring(i, this.f_96841_.length()));
            }

            return mutablecomponent;
         } else {
            return new TextComponent(this.f_96841_);
         }
      }

      public static MessageArgument.Message m_96846_(StringReader p_96847_, boolean p_96848_) throws CommandSyntaxException {
         String s = p_96847_.getString().substring(p_96847_.getCursor(), p_96847_.getTotalLength());
         if (!p_96848_) {
            p_96847_.setCursor(p_96847_.getTotalLength());
            return new MessageArgument.Message(s, new MessageArgument.Part[0]);
         } else {
            List<MessageArgument.Part> list = Lists.newArrayList();
            int i = p_96847_.getCursor();

            while(true) {
               int j;
               EntitySelector entityselector;
               while(true) {
                  if (!p_96847_.canRead()) {
                     return new MessageArgument.Message(s, list.toArray(new MessageArgument.Part[list.size()]));
                  }

                  if (p_96847_.peek() == '@') {
                     j = p_96847_.getCursor();

                     try {
                        EntitySelectorParser entityselectorparser = new EntitySelectorParser(p_96847_);
                        entityselector = entityselectorparser.m_121377_();
                        break;
                     } catch (CommandSyntaxException commandsyntaxexception) {
                        if (commandsyntaxexception.getType() != EntitySelectorParser.f_121193_ && commandsyntaxexception.getType() != EntitySelectorParser.f_121191_) {
                           throw commandsyntaxexception;
                        }

                        p_96847_.setCursor(j + 1);
                     }
                  } else {
                     p_96847_.skip();
                  }
               }

               list.add(new MessageArgument.Part(j - i, p_96847_.getCursor() - i, entityselector));
            }
         }
      }
   }

   public static class Part {
      private final int f_96852_;
      private final int f_96853_;
      private final EntitySelector f_96854_;

      public Part(int p_96856_, int p_96857_, EntitySelector p_96858_) {
         this.f_96852_ = p_96856_;
         this.f_96853_ = p_96857_;
         this.f_96854_ = p_96858_;
      }

      public int m_96859_() {
         return this.f_96852_;
      }

      public int m_96862_() {
         return this.f_96853_;
      }

      public EntitySelector m_169114_() {
         return this.f_96854_;
      }

      @Nullable
      public Component m_96860_(CommandSourceStack p_96861_) throws CommandSyntaxException {
         return EntitySelector.m_175103_(this.f_96854_.m_121160_(p_96861_));
      }
   }
}