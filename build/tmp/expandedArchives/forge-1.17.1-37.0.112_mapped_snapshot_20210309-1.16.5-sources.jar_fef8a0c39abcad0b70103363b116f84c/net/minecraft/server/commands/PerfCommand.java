package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.function.Consumer;
import net.minecraft.FileUtil;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FileZipper;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PerfCommand {
   private static final Logger f_180432_ = LogManager.getLogger();
   private static final SimpleCommandExceptionType f_180433_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.perf.notRunning"));
   private static final SimpleCommandExceptionType f_180434_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.perf.alreadyRunning"));

   public static void m_180437_(CommandDispatcher<CommandSourceStack> p_180438_) {
      p_180438_.register(Commands.m_82127_("perf").requires((p_180462_) -> {
         return p_180462_.m_6761_(4);
      }).then(Commands.m_82127_("start").executes((p_180455_) -> {
         return m_180441_(p_180455_.getSource());
      })).then(Commands.m_82127_("stop").executes((p_180440_) -> {
         return m_180456_(p_180440_.getSource());
      })));
   }

   private static int m_180441_(CommandSourceStack p_180442_) throws CommandSyntaxException {
      MinecraftServer minecraftserver = p_180442_.m_81377_();
      if (minecraftserver.m_177927_()) {
         throw f_180434_.create();
      } else {
         Consumer<ProfileResults> consumer = (p_180460_) -> {
            m_180443_(p_180442_, p_180460_);
         };
         Consumer<Path> consumer1 = (p_180453_) -> {
            m_180446_(p_180442_, p_180453_, minecraftserver);
         };
         minecraftserver.m_177923_(consumer, consumer1);
         p_180442_.m_81354_(new TranslatableComponent("commands.perf.started"), false);
         return 0;
      }
   }

   private static int m_180456_(CommandSourceStack p_180457_) throws CommandSyntaxException {
      MinecraftServer minecraftserver = p_180457_.m_81377_();
      if (!minecraftserver.m_177927_()) {
         throw f_180433_.create();
      } else {
         minecraftserver.m_177929_();
         return 0;
      }
   }

   private static void m_180446_(CommandSourceStack p_180447_, Path p_180448_, MinecraftServer p_180449_) {
      String s = String.format("%s-%s-%s", (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()), p_180449_.m_129910_().m_5462_(), SharedConstants.m_136187_().getId());

      String s1;
      try {
         s1 = FileUtil.m_133730_(MetricsPersister.f_146209_, s, ".zip");
      } catch (IOException ioexception1) {
         p_180447_.m_81352_(new TranslatableComponent("commands.perf.reportFailed"));
         f_180432_.error(ioexception1);
         return;
      }

      FileZipper filezipper = new FileZipper(MetricsPersister.f_146209_.resolve(s1));

      try {
         filezipper.m_144703_(Paths.get("system.txt"), p_180449_.m_177935_(new SystemReport()).m_143515_());
         filezipper.m_144698_(p_180448_);
      } catch (Throwable throwable1) {
         try {
            filezipper.close();
         } catch (Throwable throwable) {
            throwable1.addSuppressed(throwable);
         }

         throw throwable1;
      }

      filezipper.close();

      try {
         FileUtils.forceDelete(p_180448_.toFile());
      } catch (IOException ioexception) {
         f_180432_.warn("Failed to delete temporary profiling file {}", p_180448_, ioexception);
      }

      p_180447_.m_81354_(new TranslatableComponent("commands.perf.reportSaved", s1), false);
   }

   private static void m_180443_(CommandSourceStack p_180444_, ProfileResults p_180445_) {
      int i = p_180445_.m_7315_();
      double d0 = (double)p_180445_.m_18577_() / (double)TimeUtil.f_145016_;
      p_180444_.m_81354_(new TranslatableComponent("commands.perf.stopped", String.format(Locale.ROOT, "%.2f", d0), i, String.format(Locale.ROOT, "%.2f", (double)i / d0)), false);
   }
}