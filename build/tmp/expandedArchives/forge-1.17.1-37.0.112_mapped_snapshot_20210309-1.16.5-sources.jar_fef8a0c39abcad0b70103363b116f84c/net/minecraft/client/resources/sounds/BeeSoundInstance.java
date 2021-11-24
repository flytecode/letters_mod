package net.minecraft.client.resources.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Bee;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BeeSoundInstance extends AbstractTickableSoundInstance {
   private static final float f_174917_ = 0.0F;
   private static final float f_174918_ = 1.2F;
   private static final float f_174919_ = 0.0F;
   protected final Bee f_119618_;
   private boolean f_119619_;

   public BeeSoundInstance(Bee p_119621_, SoundEvent p_119622_, SoundSource p_119623_) {
      super(p_119622_, p_119623_);
      this.f_119618_ = p_119621_;
      this.f_119575_ = (double)((float)p_119621_.m_20185_());
      this.f_119576_ = (double)((float)p_119621_.m_20186_());
      this.f_119577_ = (double)((float)p_119621_.m_20189_());
      this.f_119578_ = true;
      this.f_119579_ = 0;
      this.f_119573_ = 0.0F;
   }

   public void m_7788_() {
      boolean flag = this.m_7774_();
      if (flag && !this.m_7801_()) {
         Minecraft.m_91087_().m_91106_().m_120372_(this.m_5958_());
         this.f_119619_ = true;
      }

      if (!this.f_119618_.m_146910_() && !this.f_119619_) {
         this.f_119575_ = (double)((float)this.f_119618_.m_20185_());
         this.f_119576_ = (double)((float)this.f_119618_.m_20186_());
         this.f_119577_ = (double)((float)this.f_119618_.m_20189_());
         float f = (float)this.f_119618_.m_20184_().m_165924_();
         if (f >= 0.01F) {
            this.f_119574_ = Mth.m_14179_(Mth.m_14036_(f, this.m_119627_(), this.m_119628_()), this.m_119627_(), this.m_119628_());
            this.f_119573_ = Mth.m_14179_(Mth.m_14036_(f, 0.0F, 0.5F), 0.0F, 1.2F);
         } else {
            this.f_119574_ = 0.0F;
            this.f_119573_ = 0.0F;
         }

      } else {
         this.m_119609_();
      }
   }

   private float m_119627_() {
      return this.f_119618_.m_6162_() ? 1.1F : 0.7F;
   }

   private float m_119628_() {
      return this.f_119618_.m_6162_() ? 1.5F : 1.1F;
   }

   public boolean m_7784_() {
      return true;
   }

   public boolean m_7767_() {
      return !this.f_119618_.m_20067_();
   }

   protected abstract AbstractTickableSoundInstance m_5958_();

   protected abstract boolean m_7774_();
}