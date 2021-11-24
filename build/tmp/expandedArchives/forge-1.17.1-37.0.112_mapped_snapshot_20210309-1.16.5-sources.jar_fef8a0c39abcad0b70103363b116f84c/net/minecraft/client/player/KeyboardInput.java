package net.minecraft.client.player;

import net.minecraft.client.Options;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KeyboardInput extends Input {
   private final Options f_108578_;
   private static final double f_172524_ = 0.3D;

   public KeyboardInput(Options p_108580_) {
      this.f_108578_ = p_108580_;
   }

   public void m_7606_(boolean p_108582_) {
      this.f_108568_ = this.f_108578_.f_92085_.m_90857_();
      this.f_108569_ = this.f_108578_.f_92087_.m_90857_();
      this.f_108570_ = this.f_108578_.f_92086_.m_90857_();
      this.f_108571_ = this.f_108578_.f_92088_.m_90857_();
      this.f_108567_ = this.f_108568_ == this.f_108569_ ? 0.0F : (this.f_108568_ ? 1.0F : -1.0F);
      this.f_108566_ = this.f_108570_ == this.f_108571_ ? 0.0F : (this.f_108570_ ? 1.0F : -1.0F);
      this.f_108572_ = this.f_108578_.f_92089_.m_90857_();
      this.f_108573_ = this.f_108578_.f_92090_.m_90857_();
      if (p_108582_) {
         this.f_108566_ = (float)((double)this.f_108566_ * 0.3D);
         this.f_108567_ = (float)((double)this.f_108567_ * 0.3D);
      }

   }
}