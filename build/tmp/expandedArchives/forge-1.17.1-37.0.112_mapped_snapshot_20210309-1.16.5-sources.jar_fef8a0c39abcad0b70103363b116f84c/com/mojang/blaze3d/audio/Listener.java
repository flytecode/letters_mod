package com.mojang.blaze3d.audio;

import com.mojang.math.Vector3f;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.openal.AL10;

@OnlyIn(Dist.CLIENT)
public class Listener {
   private float f_83733_ = 1.0F;
   private Vec3 f_83734_ = Vec3.f_82478_;

   public void m_83739_(Vec3 p_83740_) {
      this.f_83734_ = p_83740_;
      AL10.alListener3f(4100, (float)p_83740_.f_82479_, (float)p_83740_.f_82480_, (float)p_83740_.f_82481_);
   }

   public Vec3 m_83736_() {
      return this.f_83734_;
   }

   public void m_83741_(Vector3f p_83742_, Vector3f p_83743_) {
      AL10.alListenerfv(4111, new float[]{p_83742_.m_122239_(), p_83742_.m_122260_(), p_83742_.m_122269_(), p_83743_.m_122239_(), p_83743_.m_122260_(), p_83743_.m_122269_()});
   }

   public void m_83737_(float p_83738_) {
      AL10.alListenerf(4106, p_83738_);
      this.f_83733_ = p_83738_;
   }

   public float m_83744_() {
      return this.f_83733_;
   }

   public void m_83745_() {
      this.m_83739_(Vec3.f_82478_);
      this.m_83741_(Vector3f.f_122226_, Vector3f.f_122225_);
   }
}