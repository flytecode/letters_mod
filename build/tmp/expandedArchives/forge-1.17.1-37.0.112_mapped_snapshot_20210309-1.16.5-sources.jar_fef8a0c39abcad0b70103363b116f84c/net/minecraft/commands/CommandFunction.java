package net.minecraft.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerFunctionManager;

public class CommandFunction {
   private final CommandFunction.Entry[] f_77976_;
   final ResourceLocation f_77977_;

   public CommandFunction(ResourceLocation p_77979_, CommandFunction.Entry[] p_77980_) {
      this.f_77977_ = p_77979_;
      this.f_77976_ = p_77980_;
   }

   public ResourceLocation m_77981_() {
      return this.f_77977_;
   }

   public CommandFunction.Entry[] m_77989_() {
      return this.f_77976_;
   }

   public static CommandFunction m_77984_(ResourceLocation p_77985_, CommandDispatcher<CommandSourceStack> p_77986_, CommandSourceStack p_77987_, List<String> p_77988_) {
      List<CommandFunction.Entry> list = Lists.newArrayListWithCapacity(p_77988_.size());

      for(int i = 0; i < p_77988_.size(); ++i) {
         int j = i + 1;
         String s = p_77988_.get(i).trim();
         StringReader stringreader = new StringReader(s);
         if (stringreader.canRead() && stringreader.peek() != '#') {
            if (stringreader.peek() == '/') {
               stringreader.skip();
               if (stringreader.peek() == '/') {
                  throw new IllegalArgumentException("Unknown or invalid command '" + s + "' on line " + j + " (if you intended to make a comment, use '#' not '//')");
               }

               String s1 = stringreader.readUnquotedString();
               throw new IllegalArgumentException("Unknown or invalid command '" + s + "' on line " + j + " (did you mean '" + s1 + "'? Do not use a preceding forwards slash.)");
            }

            try {
               ParseResults<CommandSourceStack> parseresults = p_77986_.parse(stringreader, p_77987_);
               if (parseresults.getReader().canRead()) {
                  throw Commands.m_82097_(parseresults);
               }

               list.add(new CommandFunction.CommandEntry(parseresults));
            } catch (CommandSyntaxException commandsyntaxexception) {
               throw new IllegalArgumentException("Whilst parsing command on line " + j + ": " + commandsyntaxexception.getMessage());
            }
         }
      }

      return new CommandFunction(p_77985_, list.toArray(new CommandFunction.Entry[0]));
   }

   public static class CacheableFunction {
      public static final CommandFunction.CacheableFunction f_77990_ = new CommandFunction.CacheableFunction((ResourceLocation)null);
      @Nullable
      private final ResourceLocation f_77991_;
      private boolean f_77992_;
      private Optional<CommandFunction> f_77993_ = Optional.empty();

      public CacheableFunction(@Nullable ResourceLocation p_77998_) {
         this.f_77991_ = p_77998_;
      }

      public CacheableFunction(CommandFunction p_77996_) {
         this.f_77992_ = true;
         this.f_77991_ = null;
         this.f_77993_ = Optional.of(p_77996_);
      }

      public Optional<CommandFunction> m_78002_(ServerFunctionManager p_78003_) {
         if (!this.f_77992_) {
            if (this.f_77991_ != null) {
               this.f_77993_ = p_78003_.m_136118_(this.f_77991_);
            }

            this.f_77992_ = true;
         }

         return this.f_77993_;
      }

      @Nullable
      public ResourceLocation m_77999_() {
         return this.f_77993_.map((p_78001_) -> {
            return p_78001_.f_77977_;
         }).orElse(this.f_77991_);
      }
   }

   public static class CommandEntry implements CommandFunction.Entry {
      private final ParseResults<CommandSourceStack> f_78004_;

      public CommandEntry(ParseResults<CommandSourceStack> p_78006_) {
         this.f_78004_ = p_78006_;
      }

      public void m_142134_(ServerFunctionManager p_164879_, CommandSourceStack p_164880_, Deque<ServerFunctionManager.QueuedCommand> p_164881_, int p_164882_, int p_164883_, @Nullable ServerFunctionManager.TraceCallbacks p_164884_) throws CommandSyntaxException {
         if (p_164884_ != null) {
            String s = this.f_78004_.getReader().getString();
            p_164884_.m_142256_(p_164883_, s);
            int i = this.m_164875_(p_164879_, p_164880_);
            p_164884_.m_142279_(p_164883_, s, i);
         } else {
            this.m_164875_(p_164879_, p_164880_);
         }

      }

      private int m_164875_(ServerFunctionManager p_164876_, CommandSourceStack p_164877_) throws CommandSyntaxException {
         return p_164876_.m_136127_().execute(new ParseResults<>(this.f_78004_.getContext().withSource(p_164877_), this.f_78004_.getReader(), this.f_78004_.getExceptions()));
      }

      public String toString() {
         return this.f_78004_.getReader().getString();
      }
   }

   @FunctionalInterface
   public interface Entry {
      void m_142134_(ServerFunctionManager p_164885_, CommandSourceStack p_164886_, Deque<ServerFunctionManager.QueuedCommand> p_164887_, int p_164888_, int p_164889_, @Nullable ServerFunctionManager.TraceCallbacks p_164890_) throws CommandSyntaxException;
   }

   public static class FunctionEntry implements CommandFunction.Entry {
      private final CommandFunction.CacheableFunction f_78017_;

      public FunctionEntry(CommandFunction p_78019_) {
         this.f_78017_ = new CommandFunction.CacheableFunction(p_78019_);
      }

      public void m_142134_(ServerFunctionManager p_164902_, CommandSourceStack p_164903_, Deque<ServerFunctionManager.QueuedCommand> p_164904_, int p_164905_, int p_164906_, @Nullable ServerFunctionManager.TraceCallbacks p_164907_) {
         Util.m_137521_(this.f_78017_.m_78002_(p_164902_), (p_164900_) -> {
            CommandFunction.Entry[] acommandfunction$entry = p_164900_.m_77989_();
            if (p_164907_ != null) {
               p_164907_.m_142147_(p_164906_, p_164900_.m_77981_(), acommandfunction$entry.length);
            }

            int i = p_164905_ - p_164904_.size();
            int j = Math.min(acommandfunction$entry.length, i);

            for(int k = j - 1; k >= 0; --k) {
               p_164904_.addFirst(new ServerFunctionManager.QueuedCommand(p_164903_, p_164906_ + 1, acommandfunction$entry[k]));
            }

         }, () -> {
            if (p_164907_ != null) {
               p_164907_.m_142147_(p_164906_, this.f_78017_.m_77999_(), -1);
            }

         });
      }

      public String toString() {
         return "function " + this.f_78017_.m_77999_();
      }
   }
}