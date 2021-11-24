package com.mojang.blaze3d.audio;

import com.google.common.collect.Sets;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryStack;

@OnlyIn(Dist.CLIENT)
public class Library {
   private static final int f_166127_ = 3;
   static final Logger f_83685_ = LogManager.getLogger();
   private static final int f_166128_ = 30;
   private long f_83686_;
   private long f_83687_;
   private static final Library.ChannelPool f_83688_ = new Library.ChannelPool() {
      @Nullable
      public Channel m_5574_() {
         return null;
      }

      public boolean m_5658_(Channel p_83708_) {
         return false;
      }

      public void m_6471_() {
      }

      public int m_6492_() {
         return 0;
      }

      public int m_6500_() {
         return 0;
      }
   };
   private Library.ChannelPool f_83689_ = f_83688_;
   private Library.ChannelPool f_83690_ = f_83688_;
   private final Listener f_83691_ = new Listener();

   public void m_83694_() {
      this.f_83686_ = m_83704_();
      ALCCapabilities alccapabilities = ALC.createCapabilities(this.f_83686_);
      if (OpenAlUtil.m_83784_(this.f_83686_, "Get capabilities")) {
         throw new IllegalStateException("Failed to get OpenAL capabilities");
      } else if (!alccapabilities.OpenALC11) {
         throw new IllegalStateException("OpenAL 1.1 not supported");
      } else {
         this.f_83687_ = ALC10.alcCreateContext(this.f_83686_, (IntBuffer)null);
         ALC10.alcMakeContextCurrent(this.f_83687_);
         int i = this.m_83703_();
         int j = Mth.m_14045_((int)Mth.m_14116_((float)i), 2, 8);
         int k = Mth.m_14045_(i - j, 8, 255);
         this.f_83689_ = new Library.CountingChannelPool(k);
         this.f_83690_ = new Library.CountingChannelPool(j);
         ALCapabilities alcapabilities = AL.createCapabilities(alccapabilities);
         OpenAlUtil.m_83787_("Initialization");
         if (!alcapabilities.AL_EXT_source_distance_model) {
            throw new IllegalStateException("AL_EXT_source_distance_model is not supported");
         } else {
            AL10.alEnable(512);
            if (!alcapabilities.AL_EXT_LINEAR_DISTANCE) {
               throw new IllegalStateException("AL_EXT_LINEAR_DISTANCE is not supported");
            } else {
               OpenAlUtil.m_83787_("Enable per-source distance models");
               f_83685_.info("OpenAL initialized.");
            }
         }
      }
   }

   private int m_83703_() {
      MemoryStack memorystack = MemoryStack.stackPush();

      int i1;
      label58: {
         try {
            int i = ALC10.alcGetInteger(this.f_83686_, 4098);
            if (OpenAlUtil.m_83784_(this.f_83686_, "Get attributes size")) {
               throw new IllegalStateException("Failed to get OpenAL attributes");
            }

            IntBuffer intbuffer = memorystack.mallocInt(i);
            ALC10.alcGetIntegerv(this.f_83686_, 4099, intbuffer);
            if (OpenAlUtil.m_83784_(this.f_83686_, "Get attributes")) {
               throw new IllegalStateException("Failed to get OpenAL attributes");
            }

            int j = 0;

            while(j < i) {
               int k = intbuffer.get(j++);
               if (k == 0) {
                  break;
               }

               int l = intbuffer.get(j++);
               if (k == 4112) {
                  i1 = l;
                  break label58;
               }
            }
         } catch (Throwable throwable1) {
            if (memorystack != null) {
               try {
                  memorystack.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (memorystack != null) {
            memorystack.close();
         }

         return 30;
      }

      if (memorystack != null) {
         memorystack.close();
      }

      return i1;
   }

   private static long m_83704_() {
      for(int i = 0; i < 3; ++i) {
         long j = ALC10.alcOpenDevice((ByteBuffer)null);
         if (j != 0L && !OpenAlUtil.m_83784_(j, "Open device")) {
            return j;
         }
      }

      throw new IllegalStateException("Failed to open OpenAL device");
   }

   public void m_83699_() {
      this.f_83689_.m_6471_();
      this.f_83690_.m_6471_();
      ALC10.alcDestroyContext(this.f_83687_);
      if (this.f_83686_ != 0L) {
         ALC10.alcCloseDevice(this.f_83686_);
      }

   }

   public Listener m_83700_() {
      return this.f_83691_;
   }

   @Nullable
   public Channel m_83697_(Library.Pool p_83698_) {
      return (p_83698_ == Library.Pool.STREAMING ? this.f_83690_ : this.f_83689_).m_5574_();
   }

   public void m_83695_(Channel p_83696_) {
      if (!this.f_83689_.m_5658_(p_83696_) && !this.f_83690_.m_5658_(p_83696_)) {
         throw new IllegalStateException("Tried to release unknown channel");
      }
   }

   public String m_83701_() {
      return String.format("Sounds: %d/%d + %d/%d", this.f_83689_.m_6500_(), this.f_83689_.m_6492_(), this.f_83690_.m_6500_(), this.f_83690_.m_6492_());
   }

   @OnlyIn(Dist.CLIENT)
   interface ChannelPool {
      @Nullable
      Channel m_5574_();

      boolean m_5658_(Channel p_83712_);

      void m_6471_();

      int m_6492_();

      int m_6500_();
   }

   @OnlyIn(Dist.CLIENT)
   static class CountingChannelPool implements Library.ChannelPool {
      private final int f_83713_;
      private final Set<Channel> f_83714_ = Sets.newIdentityHashSet();

      public CountingChannelPool(int p_83716_) {
         this.f_83713_ = p_83716_;
      }

      @Nullable
      public Channel m_5574_() {
         if (this.f_83714_.size() >= this.f_83713_) {
            Library.f_83685_.warn("Maximum sound pool size {} reached", (int)this.f_83713_);
            return null;
         } else {
            Channel channel = Channel.m_83649_();
            if (channel != null) {
               this.f_83714_.add(channel);
            }

            return channel;
         }
      }

      public boolean m_5658_(Channel p_83719_) {
         if (!this.f_83714_.remove(p_83719_)) {
            return false;
         } else {
            p_83719_.m_83665_();
            return true;
         }
      }

      public void m_6471_() {
         this.f_83714_.forEach(Channel::m_83665_);
         this.f_83714_.clear();
      }

      public int m_6492_() {
         return this.f_83713_;
      }

      public int m_6500_() {
         return this.f_83714_.size();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Pool {
      STATIC,
      STREAMING;
   }
}