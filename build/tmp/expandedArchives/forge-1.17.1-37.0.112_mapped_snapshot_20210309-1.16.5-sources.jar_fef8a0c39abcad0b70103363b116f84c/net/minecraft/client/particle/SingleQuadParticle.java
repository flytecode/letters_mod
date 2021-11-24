package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SingleQuadParticle extends Particle {
   protected float f_107663_ = 0.1F * (this.f_107223_.nextFloat() * 0.5F + 0.5F) * 2.0F;

   protected SingleQuadParticle(ClientLevel p_107665_, double p_107666_, double p_107667_, double p_107668_) {
      super(p_107665_, p_107666_, p_107667_, p_107668_);
   }

   protected SingleQuadParticle(ClientLevel p_107670_, double p_107671_, double p_107672_, double p_107673_, double p_107674_, double p_107675_, double p_107676_) {
      super(p_107670_, p_107671_, p_107672_, p_107673_, p_107674_, p_107675_, p_107676_);
   }

   public void m_5744_(VertexConsumer p_107678_, Camera p_107679_, float p_107680_) {
      Vec3 vec3 = p_107679_.m_90583_();
      float f = (float)(Mth.m_14139_((double)p_107680_, this.f_107209_, this.f_107212_) - vec3.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)p_107680_, this.f_107210_, this.f_107213_) - vec3.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)p_107680_, this.f_107211_, this.f_107214_) - vec3.m_7094_());
      Quaternion quaternion;
      if (this.f_107231_ == 0.0F) {
         quaternion = p_107679_.m_90591_();
      } else {
         quaternion = new Quaternion(p_107679_.m_90591_());
         float f3 = Mth.m_14179_(p_107680_, this.f_107204_, this.f_107231_);
         quaternion.m_80148_(Vector3f.f_122227_.m_122270_(f3));
      }

      Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
      vector3f1.m_122251_(quaternion);
      Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
      float f4 = this.m_5902_(p_107680_);

      for(int i = 0; i < 4; ++i) {
         Vector3f vector3f = avector3f[i];
         vector3f.m_122251_(quaternion);
         vector3f.m_122261_(f4);
         vector3f.m_122272_(f, f1, f2);
      }

      float f7 = this.m_5970_();
      float f8 = this.m_5952_();
      float f5 = this.m_5951_();
      float f6 = this.m_5950_();
      int j = this.m_6355_(p_107680_);
      p_107678_.m_5483_((double)avector3f[0].m_122239_(), (double)avector3f[0].m_122260_(), (double)avector3f[0].m_122269_()).m_7421_(f8, f6).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      p_107678_.m_5483_((double)avector3f[1].m_122239_(), (double)avector3f[1].m_122260_(), (double)avector3f[1].m_122269_()).m_7421_(f8, f5).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      p_107678_.m_5483_((double)avector3f[2].m_122239_(), (double)avector3f[2].m_122260_(), (double)avector3f[2].m_122269_()).m_7421_(f7, f5).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      p_107678_.m_5483_((double)avector3f[3].m_122239_(), (double)avector3f[3].m_122260_(), (double)avector3f[3].m_122269_()).m_7421_(f7, f6).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
   }

   public float m_5902_(float p_107681_) {
      return this.f_107663_;
   }

   public Particle m_6569_(float p_107683_) {
      this.f_107663_ *= p_107683_;
      return super.m_6569_(p_107683_);
   }

   protected abstract float m_5970_();

   protected abstract float m_5952_();

   protected abstract float m_5951_();

   protected abstract float m_5950_();
}