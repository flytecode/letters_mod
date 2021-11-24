package net.minecraft;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;
import net.minecraft.util.MemoryReserve;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReport {
   private static final Logger f_127499_ = LogManager.getLogger();
   private final String f_127500_;
   private final Throwable f_127501_;
   private final List<CrashReportCategory> f_127503_ = Lists.newArrayList();
   private File f_127504_;
   private boolean f_127505_ = true;
   private StackTraceElement[] f_127506_ = new StackTraceElement[0];
   private final SystemReport f_178624_ = new SystemReport();

   public CrashReport(String p_127509_, Throwable p_127510_) {
      this.f_127500_ = p_127509_;
      this.f_127501_ = p_127510_;
   }

   public String m_127511_() {
      return this.f_127500_;
   }

   public Throwable m_127524_() {
      return this.f_127501_;
   }

   public String m_178625_() {
      StringBuilder stringbuilder = new StringBuilder();
      this.m_127519_(stringbuilder);
      return stringbuilder.toString();
   }

   public void m_127519_(StringBuilder p_127520_) {
      if ((this.f_127506_ == null || this.f_127506_.length <= 0) && !this.f_127503_.isEmpty()) {
         this.f_127506_ = ArrayUtils.subarray(this.f_127503_.get(0).m_128143_(), 0, 1);
      }

      if (this.f_127506_ != null && this.f_127506_.length > 0) {
         p_127520_.append("-- Head --\n");
         p_127520_.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         p_127520_.append("Stacktrace:");
         p_127520_.append(net.minecraftforge.fmllegacy.CrashReportExtender.generateEnhancedStackTrace(this.f_127506_));
      }

      for(CrashReportCategory crashreportcategory : this.f_127503_) {
         crashreportcategory.m_128168_(p_127520_);
         p_127520_.append("\n\n");
      }

      net.minecraftforge.fmllegacy.CrashReportExtender.extendSystemReport(f_178624_);
      this.f_178624_.m_143525_(p_127520_);
   }

   public String m_127525_() {
      StringWriter stringwriter = null;
      PrintWriter printwriter = null;
      Throwable throwable = this.f_127501_;
      if (throwable.getMessage() == null) {
         if (throwable instanceof NullPointerException) {
            throwable = new NullPointerException(this.f_127500_);
         } else if (throwable instanceof StackOverflowError) {
            throwable = new StackOverflowError(this.f_127500_);
         } else if (throwable instanceof OutOfMemoryError) {
            throwable = new OutOfMemoryError(this.f_127500_);
         }

         throwable.setStackTrace(this.f_127501_.getStackTrace());
      }

      return net.minecraftforge.fmllegacy.CrashReportExtender.generateEnhancedStackTrace(throwable);
   }

   public String m_127526_() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("---- Minecraft Crash Report ----\n");
      net.minecraftforge.fmllegacy.CrashReportExtender.addCrashReportHeader(stringbuilder, this);
      stringbuilder.append("// ");
      stringbuilder.append(m_127531_());
      stringbuilder.append("\n\n");
      stringbuilder.append("Time: ");
      stringbuilder.append((new SimpleDateFormat()).format(new Date()));
      stringbuilder.append("\n");
      stringbuilder.append("Description: ");
      stringbuilder.append(this.f_127500_);
      stringbuilder.append("\n\n");
      stringbuilder.append(this.m_127525_());
      stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for(int i = 0; i < 87; ++i) {
         stringbuilder.append("-");
      }

      stringbuilder.append("\n\n");
      this.m_127519_(stringbuilder);
      return stringbuilder.toString();
   }

   public File m_127527_() {
      return this.f_127504_;
   }

   public boolean m_127512_(File p_127513_) {
      if (this.f_127504_ != null) {
         return false;
      } else {
         if (p_127513_.getParentFile() != null) {
            p_127513_.getParentFile().mkdirs();
         }

         Writer writer = null;

         boolean flag;
         try {
            writer = new OutputStreamWriter(new FileOutputStream(p_127513_), StandardCharsets.UTF_8);
            writer.write(this.m_127526_());
            this.f_127504_ = p_127513_;
            return true;
         } catch (Throwable throwable) {
            f_127499_.error("Could not save crash report to {}", p_127513_, throwable);
            flag = false;
         } finally {
            IOUtils.closeQuietly(writer);
         }

         return flag;
      }
   }

   public SystemReport m_178626_() {
      return this.f_178624_;
   }

   public CrashReportCategory m_127514_(String p_127515_) {
      return this.m_127516_(p_127515_, 1);
   }

   public CrashReportCategory m_127516_(String p_127517_, int p_127518_) {
      CrashReportCategory crashreportcategory = new CrashReportCategory(p_127517_);
      if (this.f_127505_) {
         int i = crashreportcategory.m_128148_(p_127518_);
         StackTraceElement[] astacktraceelement = this.f_127501_.getStackTrace();
         StackTraceElement stacktraceelement = null;
         StackTraceElement stacktraceelement1 = null;
         int j = astacktraceelement.length - i;
         if (j < 0) {
            System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
         }

         if (astacktraceelement != null && 0 <= j && j < astacktraceelement.length) {
            stacktraceelement = astacktraceelement[j];
            if (astacktraceelement.length + 1 - i < astacktraceelement.length) {
               stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
            }
         }

         this.f_127505_ = crashreportcategory.m_128156_(stacktraceelement, stacktraceelement1);
         if (i > 0 && !this.f_127503_.isEmpty()) {
            CrashReportCategory crashreportcategory1 = this.f_127503_.get(this.f_127503_.size() - 1);
            crashreportcategory1.m_128174_(i);
         } else if (astacktraceelement != null && astacktraceelement.length >= i && 0 <= j && j < astacktraceelement.length) {
            this.f_127506_ = new StackTraceElement[j];
            System.arraycopy(astacktraceelement, 0, this.f_127506_, 0, this.f_127506_.length);
         } else {
            this.f_127505_ = false;
         }
      }

      this.f_127503_.add(crashreportcategory);
      return crashreportcategory;
   }

   private static String m_127531_() {
      String[] astring = new String[]{"Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine."};

      try {
         return astring[(int)(Util.m_137569_() % (long)astring.length)];
      } catch (Throwable throwable) {
         return "Witty comment unavailable :(";
      }
   }

   public static CrashReport m_127521_(Throwable p_127522_, String p_127523_) {
      while(p_127522_ instanceof CompletionException && p_127522_.getCause() != null) {
         p_127522_ = p_127522_.getCause();
      }

      CrashReport crashreport;
      if (p_127522_ instanceof ReportedException) {
         crashreport = ((ReportedException)p_127522_).m_134761_();
      } else {
         crashreport = new CrashReport(p_127523_, p_127522_);
      }

      return crashreport;
   }

   public static void m_127529_() {
      MemoryReserve.m_182327_();
      (new CrashReport("Don't panic!", new Throwable())).m_127526_();
   }
}
