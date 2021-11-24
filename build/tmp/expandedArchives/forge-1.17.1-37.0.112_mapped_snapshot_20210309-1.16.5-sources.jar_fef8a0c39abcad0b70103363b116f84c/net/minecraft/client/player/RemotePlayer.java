package net.minecraft.client.player;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RemotePlayer extends AbstractClientPlayer {
   public RemotePlayer(ClientLevel p_108767_, GameProfile p_108768_) {
      super(p_108767_, p_108768_);
      this.f_19793_ = 1.0F;
      this.f_19794_ = true;
   }

   public boolean m_6783_(double p_108770_) {
      double d0 = this.m_142469_().m_82309_() * 10.0D;
      if (Double.isNaN(d0)) {
         d0 = 1.0D;
      }

      d0 = d0 * 64.0D * m_20150_();
      return p_108770_ < d0 * d0;
   }

   public boolean m_6469_(DamageSource p_108772_, float p_108773_) {
      net.minecraftforge.common.ForgeHooks.onPlayerAttack(this, p_108772_, p_108773_);
      return true;
   }

   public void m_8119_() {
      super.m_8119_();
      this.m_21043_(this, false);
   }

   public void m_8107_() {
      if (this.f_20903_ > 0) {
         double d0 = this.m_20185_() + (this.f_20904_ - this.m_20185_()) / (double)this.f_20903_;
         double d1 = this.m_20186_() + (this.f_20905_ - this.m_20186_()) / (double)this.f_20903_;
         double d2 = this.m_20189_() + (this.f_20906_ - this.m_20189_()) / (double)this.f_20903_;
         this.m_146922_(this.m_146908_() + (float)Mth.m_14175_(this.f_20907_ - (double)this.m_146908_()) / (float)this.f_20903_);
         this.m_146926_(this.m_146909_() + (float)(this.f_20908_ - (double)this.m_146909_()) / (float)this.f_20903_);
         --this.f_20903_;
         this.m_6034_(d0, d1, d2);
         this.m_19915_(this.m_146908_(), this.m_146909_());
      }

      if (this.f_20934_ > 0) {
         this.f_20885_ = (float)((double)this.f_20885_ + Mth.m_14175_(this.f_20933_ - (double)this.f_20885_) / (double)this.f_20934_);
         --this.f_20934_;
      }

      this.f_36099_ = this.f_36100_;
      this.m_21203_();
      float f;
      if (this.f_19861_ && !this.m_21224_()) {
         f = (float)Math.min(0.1D, this.m_20184_().m_165924_());
      } else {
         f = 0.0F;
      }

      this.f_36100_ += (f - this.f_36100_) * 0.4F;
      this.f_19853_.m_46473_().m_6180_("push");
      this.m_6138_();
      this.f_19853_.m_46473_().m_7238_();
   }

   protected void m_7594_() {
   }

   public void m_6352_(Component p_108775_, UUID p_108776_) {
      Minecraft minecraft = Minecraft.m_91087_();
      if (!minecraft.m_91246_(p_108776_)) {
         minecraft.f_91065_.m_93076_().m_93785_(p_108775_);
      }

   }
}
