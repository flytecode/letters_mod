package net.minecraft.client;

import com.mojang.bridge.Bridge;
import com.mojang.bridge.game.GameSession;
import com.mojang.bridge.game.GameVersion;
import com.mojang.bridge.game.Language;
import com.mojang.bridge.game.PerformanceMetrics;
import com.mojang.bridge.game.RunningGame;
import com.mojang.bridge.launcher.Launcher;
import com.mojang.bridge.launcher.SessionEventListener;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.FrameTimer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Game implements RunningGame {
   private final Minecraft f_90734_;
   @Nullable
   private final Launcher f_90735_;
   private SessionEventListener f_90736_ = SessionEventListener.NONE;

   public Game(Minecraft p_90738_) {
      this.f_90734_ = p_90738_;
      this.f_90735_ = Bridge.getLauncher();
      if (this.f_90735_ != null) {
         this.f_90735_.registerGame(this);
      }

   }

   public GameVersion getVersion() {
      return SharedConstants.m_136187_();
   }

   public Language getSelectedLanguage() {
      return this.f_90734_.m_91102_().m_118983_();
   }

   @Nullable
   public GameSession getCurrentSession() {
      ClientLevel clientlevel = this.f_90734_.f_91073_;
      return clientlevel == null ? null : new Session(clientlevel, this.f_90734_.f_91074_, this.f_90734_.f_91074_.f_108617_);
   }

   public PerformanceMetrics getPerformanceMetrics() {
      FrameTimer frametimer = this.f_90734_.m_91293_();
      long i = 2147483647L;
      long j = -2147483648L;
      long k = 0L;

      for(long l : frametimer.m_13764_()) {
         i = Math.min(i, l);
         j = Math.max(j, l);
         k += l;
      }

      return new Game.Metrics((int)i, (int)j, (int)(k / (long)frametimer.m_13764_().length), frametimer.m_13764_().length);
   }

   public void setSessionEventListener(SessionEventListener p_90746_) {
      this.f_90736_ = p_90746_;
   }

   public void m_90739_() {
      this.f_90736_.onStartGameSession(this.getCurrentSession());
   }

   public void m_90740_() {
      this.f_90736_.onLeaveGameSession(this.getCurrentSession());
   }

   @OnlyIn(Dist.CLIENT)
   static class Metrics implements PerformanceMetrics {
      private final int f_90747_;
      private final int f_90748_;
      private final int f_90749_;
      private final int f_90750_;

      public Metrics(int p_90752_, int p_90753_, int p_90754_, int p_90755_) {
         this.f_90747_ = p_90752_;
         this.f_90748_ = p_90753_;
         this.f_90749_ = p_90754_;
         this.f_90750_ = p_90755_;
      }

      public int getMinTime() {
         return this.f_90747_;
      }

      public int getMaxTime() {
         return this.f_90748_;
      }

      public int getAverageTime() {
         return this.f_90749_;
      }

      public int getSampleCount() {
         return this.f_90750_;
      }
   }
}