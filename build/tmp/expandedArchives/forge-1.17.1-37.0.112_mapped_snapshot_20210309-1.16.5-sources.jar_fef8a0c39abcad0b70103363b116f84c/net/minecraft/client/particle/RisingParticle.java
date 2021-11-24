package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RisingParticle extends TextureSheetParticle {
   protected RisingParticle(ClientLevel p_107631_, double p_107632_, double p_107633_, double p_107634_, double p_107635_, double p_107636_, double p_107637_) {
      super(p_107631_, p_107632_, p_107633_, p_107634_, p_107635_, p_107636_, p_107637_);
      this.f_172258_ = 0.96F;
      this.f_107215_ = this.f_107215_ * (double)0.01F + p_107635_;
      this.f_107216_ = this.f_107216_ * (double)0.01F + p_107636_;
      this.f_107217_ = this.f_107217_ * (double)0.01F + p_107637_;
      this.f_107212_ += (double)((this.f_107223_.nextFloat() - this.f_107223_.nextFloat()) * 0.05F);
      this.f_107213_ += (double)((this.f_107223_.nextFloat() - this.f_107223_.nextFloat()) * 0.05F);
      this.f_107214_ += (double)((this.f_107223_.nextFloat() - this.f_107223_.nextFloat()) * 0.05F);
      this.f_107225_ = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
   }
}