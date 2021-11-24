package net.minecraft.client.sounds;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MusicManager {
   private static final int f_174979_ = 100;
   private final Random f_120177_ = new Random();
   private final Minecraft f_120178_;
   @Nullable
   private SoundInstance f_120179_;
   private int f_120180_ = 100;

   public MusicManager(Minecraft p_120182_) {
      this.f_120178_ = p_120182_;
   }

   public void m_120183_() {
      Music music = this.f_120178_.m_91107_();
      if (this.f_120179_ != null) {
         if (!music.m_11631_().m_11660_().equals(this.f_120179_.m_7904_()) && music.m_11642_()) {
            this.f_120178_.m_91106_().m_120399_(this.f_120179_);
            this.f_120180_ = Mth.m_14072_(this.f_120177_, 0, music.m_11636_() / 2);
         }

         if (!this.f_120178_.m_91106_().m_120403_(this.f_120179_)) {
            this.f_120179_ = null;
            this.f_120180_ = Math.min(this.f_120180_, Mth.m_14072_(this.f_120177_, music.m_11636_(), music.m_11639_()));
         }
      }

      this.f_120180_ = Math.min(this.f_120180_, music.m_11639_());
      if (this.f_120179_ == null && this.f_120180_-- <= 0) {
         this.m_120184_(music);
      }

   }

   public void m_120184_(Music p_120185_) {
      this.f_120179_ = SimpleSoundInstance.m_119745_(p_120185_.m_11631_());
      if (this.f_120179_.m_5891_() != SoundManager.f_120344_) {
         this.f_120178_.m_91106_().m_120367_(this.f_120179_);
      }

      this.f_120180_ = Integer.MAX_VALUE;
   }

   public void m_120186_() {
      if (this.f_120179_ != null) {
         this.f_120178_.m_91106_().m_120399_(this.f_120179_);
         this.f_120179_ = null;
      }

      this.f_120180_ += 100;
   }

   public boolean m_120187_(Music p_120188_) {
      return this.f_120179_ == null ? false : p_120188_.m_11631_().m_11660_().equals(this.f_120179_.m_7904_());
   }
}