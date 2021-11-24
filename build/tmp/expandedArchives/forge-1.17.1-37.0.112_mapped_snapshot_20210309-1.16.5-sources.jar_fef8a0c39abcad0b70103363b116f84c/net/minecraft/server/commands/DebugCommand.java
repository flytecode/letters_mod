package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.ProfileResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugCommand {
   private static final Logger f_136900_ = LogManager.getLogger();
   private static final SimpleCommandExceptionType f_136901_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.debug.notRunning"));
   private static final SimpleCommandExceptionType f_136902_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.debug.alreadyRunning"));

   public static void m_136905_(CommandDispatcher<CommandSourceStack> p_136906_) {
      p_136906_.register(Commands.m_82127_("debug").requires((p_180073_) -> {
         return p_180073_.m_6761_(3);
      }).then(Commands.m_82127_("start").executes((p_180069_) -> {
         return m_136909_(p_180069_.getSource());
      })).then(Commands.m_82127_("stop").executes((p_136918_) -> {
         return m_136915_(p_136918_.getSource());
      })).then(Commands.m_82127_("function").requires((p_180071_) -> {
         return p_180071_.m_6761_(3);
      }).then(Commands.m_82129_("name", FunctionArgument.m_120907_()).suggests(FunctionCommand.f_137712_).executes((p_136908_) -> {
         return m_180065_(p_136908_.getSource(), FunctionArgument.m_120910_(p_136908_, "name"));
      }))));
   }

   private static int m_136909_(CommandSourceStack p_136910_) throws CommandSyntaxException {
      MinecraftServer minecraftserver = p_136910_.m_81377_();
      if (minecraftserver.m_177942_()) {
         throw f_136902_.create();
      } else {
         minecraftserver.m_177943_();
         p_136910_.m_81354_(new TranslatableComponent("commands.debug.started"), true);
         return 0;
      }
   }

   private static int m_136915_(CommandSourceStack p_136916_) throws CommandSyntaxException {
      MinecraftServer minecraftserver = p_136916_.m_81377_();
      if (!minecraftserver.m_177942_()) {
         throw f_136901_.create();
      } else {
         ProfileResults profileresults = minecraftserver.m_177944_();
         double d0 = (double)profileresults.m_18577_() / (double)TimeUtil.f_145016_;
         double d1 = (double)profileresults.m_7315_() / d0;
         p_136916_.m_81354_(new TranslatableComponent("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", d0), profileresults.m_7315_(), String.format("%.2f", d1)), true);
         return (int)d1;
      }
   }

   private static int m_180065_(CommandSourceStack p_180066_, Collection<CommandFunction> p_180067_) {
      int i = 0;
      MinecraftServer minecraftserver = p_180066_.m_81377_();
      String s = "debug-trace-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt";

      try {
         Path path = minecraftserver.m_129971_("debug").toPath();
         Files.createDirectories(path);
         Writer writer = Files.newBufferedWriter(path.resolve(s), StandardCharsets.UTF_8);

         try {
            PrintWriter printwriter = new PrintWriter(writer);

            for(CommandFunction commandfunction : p_180067_) {
               printwriter.println((Object)commandfunction.m_77981_());
               DebugCommand.Tracer debugcommand$tracer = new DebugCommand.Tracer(printwriter);
               i += p_180066_.m_81377_().m_129890_().m_179960_(commandfunction, p_180066_.m_165484_(debugcommand$tracer).m_81358_(2), debugcommand$tracer);
            }
         } catch (Throwable throwable1) {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (writer != null) {
            writer.close();
         }
      } catch (IOException | UncheckedIOException uncheckedioexception) {
         f_136900_.warn("Tracing failed", (Throwable)uncheckedioexception);
         p_180066_.m_81352_(new TranslatableComponent("commands.debug.function.traceFailed"));
      }

      if (p_180067_.size() == 1) {
         p_180066_.m_81354_(new TranslatableComponent("commands.debug.function.success.single", i, p_180067_.iterator().next().m_77981_(), s), true);
      } else {
         p_180066_.m_81354_(new TranslatableComponent("commands.debug.function.success.multiple", i, p_180067_.size(), s), true);
      }

      return i;
   }

   static class Tracer implements CommandSource, ServerFunctionManager.TraceCallbacks {
      public static final int f_180074_ = 1;
      private final PrintWriter f_180075_;
      private int f_180076_;
      private boolean f_180077_;

      Tracer(PrintWriter p_180079_) {
         this.f_180075_ = p_180079_;
      }

      private void m_180081_(int p_180082_) {
         this.m_180097_(p_180082_);
         this.f_180076_ = p_180082_;
      }

      private void m_180097_(int p_180098_) {
         for(int i = 0; i < p_180098_ + 1; ++i) {
            this.f_180075_.write("    ");
         }

      }

      private void m_180103_() {
         if (this.f_180077_) {
            this.f_180075_.println();
            this.f_180077_ = false;
         }

      }

      public void m_142256_(int p_180084_, String p_180085_) {
         this.m_180103_();
         this.m_180081_(p_180084_);
         this.f_180075_.print("[C] ");
         this.f_180075_.print(p_180085_);
         this.f_180077_ = true;
      }

      public void m_142279_(int p_180087_, String p_180088_, int p_180089_) {
         if (this.f_180077_) {
            this.f_180075_.print(" -> ");
            this.f_180075_.println(p_180089_);
            this.f_180077_ = false;
         } else {
            this.m_180081_(p_180087_);
            this.f_180075_.print("[R = ");
            this.f_180075_.print(p_180089_);
            this.f_180075_.print("] ");
            this.f_180075_.println(p_180088_);
         }

      }

      public void m_142147_(int p_180091_, ResourceLocation p_180092_, int p_180093_) {
         this.m_180103_();
         this.m_180081_(p_180091_);
         this.f_180075_.print("[F] ");
         this.f_180075_.print((Object)p_180092_);
         this.f_180075_.print(" size=");
         this.f_180075_.println(p_180093_);
      }

      public void m_142255_(int p_180100_, String p_180101_) {
         this.m_180103_();
         this.m_180081_(p_180100_ + 1);
         this.f_180075_.print("[E] ");
         this.f_180075_.print(p_180101_);
      }

      public void m_6352_(Component p_180095_, UUID p_180096_) {
         this.m_180103_();
         this.m_180097_(this.f_180076_ + 1);
         this.f_180075_.print("[M] ");
         if (p_180096_ != Util.f_137441_) {
            this.f_180075_.print((Object)p_180096_);
            this.f_180075_.print(": ");
         }

         this.f_180075_.println(p_180095_.getString());
      }

      public boolean m_6999_() {
         return true;
      }

      public boolean m_7028_() {
         return true;
      }

      public boolean m_6102_() {
         return false;
      }

      public boolean m_142559_() {
         return true;
      }
   }
}