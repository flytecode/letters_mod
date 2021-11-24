package net.minecraft;

import com.mojang.bridge.game.GameVersion;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import java.time.Duration;
import javax.annotation.Nullable;
import net.minecraft.commands.BrigadierExceptions;

public class SharedConstants {
   @Deprecated
   public static final boolean f_142912_ = false;
   @Deprecated
   public static final int f_142951_ = 2730;
   @Deprecated
   public static final String f_142952_ = "1.17.1";
   @Deprecated
   public static final String f_142953_ = "1.17.1";
   @Deprecated
   public static final int f_142954_ = 756;
   @Deprecated
   public static final int f_142955_ = 40;
   public static final int f_142956_ = 2678;
   private static final int f_142925_ = 30;
   @Deprecated
   public static final int f_142957_ = 7;
   @Deprecated
   public static final int f_142958_ = 7;
   public static final String f_142959_ = "DataVersion";
   public static final boolean f_142960_ = false;
   public static final boolean f_142961_ = false;
   public static final boolean f_142962_ = false;
   public static final boolean f_142963_ = false;
   public static final boolean f_142964_ = false;
   public static final boolean f_142965_ = false;
   public static final boolean f_142966_ = false;
   public static final boolean f_142967_ = false;
   public static final boolean f_142968_ = false;
   public static final boolean f_142969_ = false;
   public static final boolean f_142970_ = false;
   public static final boolean f_142971_ = false;
   public static final boolean f_142972_ = false;
   public static final boolean f_142973_ = false;
   public static final boolean f_142974_ = false;
   public static final boolean f_142975_ = false;
   public static final boolean f_142886_ = false;
   public static final boolean f_142887_ = false;
   public static final boolean f_142888_ = false;
   public static final boolean f_142889_ = false;
   public static final boolean f_142890_ = false;
   public static final boolean f_142891_ = false;
   public static final boolean f_142892_ = false;
   public static final boolean f_142893_ = false;
   public static final boolean f_142894_ = false;
   public static final boolean f_142895_ = false;
   public static final boolean f_142896_ = false;
   public static final boolean f_142897_ = false;
   public static final boolean f_142898_ = false;
   public static final boolean f_142899_ = false;
   public static final boolean f_142900_ = false;
   public static final boolean f_142901_ = false;
   public static final boolean f_142902_ = false;
   public static final boolean f_142903_ = false;
   public static final boolean f_142904_ = false;
   public static final boolean f_142905_ = false;
   public static final boolean f_142906_ = false;
   public static final boolean f_142907_ = false;
   public static final boolean f_142908_ = false;
   public static final boolean f_142909_ = false;
   public static final boolean f_142910_ = false;
   public static final boolean f_142911_ = false;
   public static final boolean f_142926_ = false;
   public static final boolean f_142927_ = false;
   public static final boolean f_142928_ = false;
   public static final boolean f_142929_ = false;
   public static final boolean f_142930_ = false;
   public static final boolean f_142931_ = false;
   public static final boolean f_142932_ = false;
   public static final boolean f_142933_ = false;
   public static final boolean f_142934_ = false;
   public static final boolean f_142935_ = false;
   public static final boolean f_142936_ = false;
   public static final boolean f_142937_ = false;
   public static final boolean f_142938_ = false;
   public static final boolean f_142939_ = false;
   public static final boolean f_142940_ = false;
   public static final boolean f_142941_ = false;
   public static final boolean f_142942_ = false;
   public static final boolean f_142943_ = false;
   public static final int f_142944_ = 25565;
   public static final boolean f_142945_ = false;
   public static final boolean f_142946_ = false;
   public static final int f_142947_ = 0;
   public static final int f_142948_ = 0;
   public static final Level f_136180_ = Level.DISABLED;
   public static final boolean f_142949_ = false;
   public static final boolean f_142950_ = false;
   public static final boolean f_142913_ = false;
   public static final boolean f_142914_ = false;
   public static final float f_142915_ = 0.15F;
   public static final long f_136181_ = Duration.ofMillis(300L).toNanos();
   public static boolean f_136182_ = true;
   public static boolean f_136183_;
   public static final int f_142916_ = 16;
   public static final int f_142917_ = 256;
   public static final int f_142918_ = 32500;
   public static final char[] f_136184_ = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};
   public static final int f_142919_ = 20;
   public static final int f_142920_ = 1200;
   public static final int f_142921_ = 24000;
   public static final float f_142922_ = 1365.3334F;
   public static final float f_142923_ = 0.87890625F;
   public static final float f_142924_ = 17.578125F;
   @Nullable
   private static GameVersion f_136185_;

   public static boolean m_136188_(char p_136189_) {
      return p_136189_ != 167 && p_136189_ >= ' ' && p_136189_ != 127;
   }

   public static String m_136190_(String p_136191_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(char c0 : p_136191_.toCharArray()) {
         if (m_136188_(c0)) {
            stringbuilder.append(c0);
         }
      }

      return stringbuilder.toString();
   }

   public static void m_142978_(GameVersion p_142979_) {
      if (f_136185_ == null) {
         f_136185_ = p_142979_;
      } else if (p_142979_ != f_136185_) {
         throw new IllegalStateException("Cannot override the current game version!");
      }

   }

   public static void m_142977_() {
      if (f_136185_ == null) {
         f_136185_ = DetectedVersion.m_132490_();
      }

   }

   public static GameVersion m_136187_() {
      if (f_136185_ == null) {
         throw new IllegalStateException("Game version not set");
      } else {
         return f_136185_;
      }
   }

   public static int m_136192_() {
      return 756;
   }

   static {
      if (System.getProperty("io.netty.leakDetection.level") == null) // Forge: allow level to be manually specified
      ResourceLeakDetector.setLevel(f_136180_);
      CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = false;
      CommandSyntaxException.BUILT_IN_EXCEPTIONS = new BrigadierExceptions();
   }
}
