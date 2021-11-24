package net.minecraft.client.resources.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElytraOnPlayerSoundInstance extends AbstractTickableSoundInstance {
   public static final int f_174926_ = 20;
   private final LocalPlayer f_119670_;
   private int f_119671_;

   public ElytraOnPlayerSoundInstance(LocalPlayer p_119673_) {
      super(SoundEvents.f_11886_, SoundSource.PLAYERS);
      this.f_119670_ = p_119673_;
      this.f_119578_ = true;
      this.f_119579_ = 0;
      this.f_119573_ = 0.1F;
   }

   public void m_7788_() {
      ++this.f_119671_;
      if (!this.f_119670_.m_146910_() && (this.f_119671_ <= 20 || this.f_119670_.m_21255_())) {
         this.f_119575_ = (double)((float)this.f_119670_.m_20185_());
         this.f_119576_ = (double)((float)this.f_119670_.m_20186_());
         this.f_119577_ = (double)((float)this.f_119670_.m_20189_());
         float f = (float)this.f_119670_.m_20184_().m_82556_();
         if ((double)f >= 1.0E-7D) {
            this.f_119573_ = Mth.m_14036_(f / 4.0F, 0.0F, 1.0F);
         } else {
            this.f_119573_ = 0.0F;
         }

         if (this.f_119671_ < 20) {
            this.f_119573_ = 0.0F;
         } else if (this.f_119671_ < 40) {
            this.f_119573_ = (float)((double)this.f_119573_ * ((double)(this.f_119671_ - 20) / 20.0D));
         }

         float f1 = 0.8F;
         if (this.f_119573_ > 0.8F) {
            this.f_119574_ = 1.0F + (this.f_119573_ - 0.8F);
         } else {
            this.f_119574_ = 1.0F;
         }

      } else {
         this.m_119609_();
      }
   }
}