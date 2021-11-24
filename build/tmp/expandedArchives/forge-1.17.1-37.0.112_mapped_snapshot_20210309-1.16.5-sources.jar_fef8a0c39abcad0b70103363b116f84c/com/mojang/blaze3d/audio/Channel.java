package com.mojang.blaze3d.audio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;

@OnlyIn(Dist.CLIENT)
public class Channel {
   private static final Logger f_83641_ = LogManager.getLogger();
   private static final int f_166125_ = 4;
   public static final int f_166124_ = 1;
   private final int f_83642_;
   private final AtomicBoolean f_83643_ = new AtomicBoolean(true);
   private int f_83644_ = 16384;
   @Nullable
   private AudioStream f_83645_;

   @Nullable
   static Channel m_83649_() {
      int[] aint = new int[1];
      AL10.alGenSources(aint);
      return OpenAlUtil.m_83787_("Allocate new source") ? null : new Channel(aint[0]);
   }

   private Channel(int p_83648_) {
      this.f_83642_ = p_83648_;
   }

   public void m_83665_() {
      if (this.f_83643_.compareAndSet(true, false)) {
         AL10.alSourceStop(this.f_83642_);
         OpenAlUtil.m_83787_("Stop");
         if (this.f_83645_ != null) {
            try {
               this.f_83645_.close();
            } catch (IOException ioexception) {
               f_83641_.error("Failed to close audio stream", (Throwable)ioexception);
            }

            this.m_83684_();
            this.f_83645_ = null;
         }

         AL10.alDeleteSources(new int[]{this.f_83642_});
         OpenAlUtil.m_83787_("Cleanup");
      }

   }

   public void m_83672_() {
      AL10.alSourcePlay(this.f_83642_);
   }

   private int m_83683_() {
      return !this.f_83643_.get() ? 4116 : AL10.alGetSourcei(this.f_83642_, 4112);
   }

   public void m_83677_() {
      if (this.m_83683_() == 4114) {
         AL10.alSourcePause(this.f_83642_);
      }

   }

   public void m_83678_() {
      if (this.m_83683_() == 4115) {
         AL10.alSourcePlay(this.f_83642_);
      }

   }

   public void m_83679_() {
      if (this.f_83643_.get()) {
         AL10.alSourceStop(this.f_83642_);
         OpenAlUtil.m_83787_("Stop");
      }

   }

   public boolean m_166126_() {
      return this.m_83683_() == 4114;
   }

   public boolean m_83680_() {
      return this.m_83683_() == 4116;
   }

   public void m_83654_(Vec3 p_83655_) {
      AL10.alSourcefv(this.f_83642_, 4100, new float[]{(float)p_83655_.f_82479_, (float)p_83655_.f_82480_, (float)p_83655_.f_82481_});
   }

   public void m_83650_(float p_83651_) {
      AL10.alSourcef(this.f_83642_, 4099, p_83651_);
   }

   public void m_83663_(boolean p_83664_) {
      AL10.alSourcei(this.f_83642_, 4103, p_83664_ ? 1 : 0);
   }

   public void m_83666_(float p_83667_) {
      AL10.alSourcef(this.f_83642_, 4106, p_83667_);
   }

   public void m_83681_() {
      AL10.alSourcei(this.f_83642_, 53248, 0);
   }

   public void m_83673_(float p_83674_) {
      AL10.alSourcei(this.f_83642_, 53248, 53251);
      AL10.alSourcef(this.f_83642_, 4131, p_83674_);
      AL10.alSourcef(this.f_83642_, 4129, 1.0F);
      AL10.alSourcef(this.f_83642_, 4128, 0.0F);
   }

   public void m_83670_(boolean p_83671_) {
      AL10.alSourcei(this.f_83642_, 514, p_83671_ ? 1 : 0);
   }

   public void m_83656_(SoundBuffer p_83657_) {
      p_83657_.m_83800_().ifPresent((p_83676_) -> {
         AL10.alSourcei(this.f_83642_, 4105, p_83676_);
      });
   }

   public void m_83658_(AudioStream p_83659_) {
      this.f_83645_ = p_83659_;
      AudioFormat audioformat = p_83659_.m_6206_();
      this.f_83644_ = m_83660_(audioformat, 1);
      this.m_83652_(4);
   }

   private static int m_83660_(AudioFormat p_83661_, int p_83662_) {
      return (int)((float)(p_83662_ * p_83661_.getSampleSizeInBits()) / 8.0F * (float)p_83661_.getChannels() * p_83661_.getSampleRate());
   }

   private void m_83652_(int p_83653_) {
      if (this.f_83645_ != null) {
         try {
            for(int i = 0; i < p_83653_; ++i) {
               ByteBuffer bytebuffer = this.f_83645_.m_7118_(this.f_83644_);
               if (bytebuffer != null) {
                  (new SoundBuffer(bytebuffer, this.f_83645_.m_6206_())).m_83802_().ifPresent((p_83669_) -> {
                     AL10.alSourceQueueBuffers(this.f_83642_, new int[]{p_83669_});
                  });
               }
            }
         } catch (IOException ioexception) {
            f_83641_.error("Failed to read from audio stream", (Throwable)ioexception);
         }
      }

   }

   public void m_83682_() {
      if (this.f_83645_ != null) {
         int i = this.m_83684_();
         this.m_83652_(i);
      }

   }

   private int m_83684_() {
      int i = AL10.alGetSourcei(this.f_83642_, 4118);
      if (i > 0) {
         int[] aint = new int[i];
         AL10.alSourceUnqueueBuffers(this.f_83642_, aint);
         OpenAlUtil.m_83787_("Unqueue buffers");
         AL10.alDeleteBuffers(aint);
         OpenAlUtil.m_83787_("Remove processed buffers");
      }

      return i;
   }
}