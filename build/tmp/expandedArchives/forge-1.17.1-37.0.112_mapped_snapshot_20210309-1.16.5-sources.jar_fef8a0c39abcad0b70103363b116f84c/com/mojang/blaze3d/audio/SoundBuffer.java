package com.mojang.blaze3d.audio;

import java.nio.ByteBuffer;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.openal.AL10;

@OnlyIn(Dist.CLIENT)
public class SoundBuffer {
   @Nullable
   private ByteBuffer f_83793_;
   private final AudioFormat f_83794_;
   private boolean f_83795_;
   private int f_83796_;

   public SoundBuffer(ByteBuffer p_83798_, AudioFormat p_83799_) {
      this.f_83793_ = p_83798_;
      this.f_83794_ = p_83799_;
   }

   OptionalInt m_83800_() {
      if (!this.f_83795_) {
         if (this.f_83793_ == null) {
            return OptionalInt.empty();
         }

         int i = OpenAlUtil.m_83789_(this.f_83794_);
         int[] aint = new int[1];
         AL10.alGenBuffers(aint);
         if (OpenAlUtil.m_83787_("Creating buffer")) {
            return OptionalInt.empty();
         }

         AL10.alBufferData(aint[0], i, this.f_83793_, (int)this.f_83794_.getSampleRate());
         if (OpenAlUtil.m_83787_("Assigning buffer data")) {
            return OptionalInt.empty();
         }

         this.f_83796_ = aint[0];
         this.f_83795_ = true;
         this.f_83793_ = null;
      }

      return OptionalInt.of(this.f_83796_);
   }

   public void m_83801_() {
      if (this.f_83795_) {
         AL10.alDeleteBuffers(new int[]{this.f_83796_});
         if (OpenAlUtil.m_83787_("Deleting stream buffers")) {
            return;
         }
      }

      this.f_83795_ = false;
   }

   public OptionalInt m_83802_() {
      OptionalInt optionalint = this.m_83800_();
      this.f_83795_ = false;
      return optionalint;
   }
}