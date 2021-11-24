package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VibrationSignalParticle extends TextureSheetParticle {
   private final VibrationPath f_172461_;
   private float f_172462_;
   private float f_172460_;

   VibrationSignalParticle(ClientLevel p_172464_, VibrationPath p_172465_, int p_172466_) {
      super(p_172464_, (double)((float)p_172465_.m_157948_().m_123341_() + 0.5F), (double)((float)p_172465_.m_157948_().m_123342_() + 0.5F), (double)((float)p_172465_.m_157948_().m_123343_() + 0.5F), 0.0D, 0.0D, 0.0D);
      this.f_107663_ = 0.3F;
      this.f_172461_ = p_172465_;
      this.f_107225_ = p_172466_;
   }

   public void m_5744_(VertexConsumer p_172475_, Camera p_172476_, float p_172477_) {
      float f = Mth.m_14031_(((float)this.f_107224_ + p_172477_ - ((float)Math.PI * 2F)) * 0.05F) * 2.0F;
      float f1 = Mth.m_14179_(p_172477_, this.f_172460_, this.f_172462_);
      float f2 = 1.0472F;
      this.m_172478_(p_172475_, p_172476_, p_172477_, (p_172487_) -> {
         p_172487_.m_80148_(Vector3f.f_122225_.m_122270_(f1));
         p_172487_.m_80148_(Vector3f.f_122223_.m_122270_(-1.0472F));
         p_172487_.m_80148_(Vector3f.f_122225_.m_122270_(f));
      });
      this.m_172478_(p_172475_, p_172476_, p_172477_, (p_172473_) -> {
         p_172473_.m_80148_(Vector3f.f_122225_.m_122270_(-(float)Math.PI + f1));
         p_172473_.m_80148_(Vector3f.f_122223_.m_122270_(1.0472F));
         p_172473_.m_80148_(Vector3f.f_122225_.m_122270_(f));
      });
   }

   private void m_172478_(VertexConsumer p_172479_, Camera p_172480_, float p_172481_, Consumer<Quaternion> p_172482_) {
      Vec3 vec3 = p_172480_.m_90583_();
      float f = (float)(Mth.m_14139_((double)p_172481_, this.f_107209_, this.f_107212_) - vec3.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)p_172481_, this.f_107210_, this.f_107213_) - vec3.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)p_172481_, this.f_107211_, this.f_107214_) - vec3.m_7094_());
      Vector3f vector3f = new Vector3f(0.5F, 0.5F, 0.5F);
      vector3f.m_122278_();
      Quaternion quaternion = new Quaternion(vector3f, 0.0F, true);
      p_172482_.accept(quaternion);
      Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
      vector3f1.m_122251_(quaternion);
      Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
      float f3 = this.m_5902_(p_172481_);

      for(int i = 0; i < 4; ++i) {
         Vector3f vector3f2 = avector3f[i];
         vector3f2.m_122251_(quaternion);
         vector3f2.m_122261_(f3);
         vector3f2.m_122272_(f, f1, f2);
      }

      float f6 = this.m_5970_();
      float f7 = this.m_5952_();
      float f4 = this.m_5951_();
      float f5 = this.m_5950_();
      int j = this.m_6355_(p_172481_);
      p_172479_.m_5483_((double)avector3f[0].m_122239_(), (double)avector3f[0].m_122260_(), (double)avector3f[0].m_122269_()).m_7421_(f7, f5).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      p_172479_.m_5483_((double)avector3f[1].m_122239_(), (double)avector3f[1].m_122260_(), (double)avector3f[1].m_122269_()).m_7421_(f7, f4).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      p_172479_.m_5483_((double)avector3f[2].m_122239_(), (double)avector3f[2].m_122260_(), (double)avector3f[2].m_122269_()).m_7421_(f6, f4).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      p_172479_.m_5483_((double)avector3f[3].m_122239_(), (double)avector3f[3].m_122260_(), (double)avector3f[3].m_122269_()).m_7421_(f6, f5).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
   }

   public int m_6355_(float p_172469_) {
      return 240;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   public void m_5989_() {
      super.m_5989_();
      Optional<BlockPos> optional = this.f_172461_.m_157951_().m_142502_(this.f_107208_);
      if (!optional.isPresent()) {
         this.m_107274_();
      } else {
         double d0 = (double)this.f_107224_ / (double)this.f_107225_;
         BlockPos blockpos = this.f_172461_.m_157948_();
         BlockPos blockpos1 = optional.get();
         this.f_107212_ = Mth.m_14139_(d0, (double)blockpos.m_123341_() + 0.5D, (double)blockpos1.m_123341_() + 0.5D);
         this.f_107213_ = Mth.m_14139_(d0, (double)blockpos.m_123342_() + 0.5D, (double)blockpos1.m_123342_() + 0.5D);
         this.f_107214_ = Mth.m_14139_(d0, (double)blockpos.m_123343_() + 0.5D, (double)blockpos1.m_123343_() + 0.5D);
         this.f_172460_ = this.f_172462_;
         this.f_172462_ = (float)Mth.m_14136_(this.f_107212_ - (double)blockpos1.m_123341_(), this.f_107214_ - (double)blockpos1.m_123343_());
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<VibrationParticleOption> {
      private final SpriteSet f_172488_;

      public Provider(SpriteSet p_172490_) {
         this.f_172488_ = p_172490_;
      }

      public Particle m_6966_(VibrationParticleOption p_172501_, ClientLevel p_172502_, double p_172503_, double p_172504_, double p_172505_, double p_172506_, double p_172507_, double p_172508_) {
         VibrationSignalParticle vibrationsignalparticle = new VibrationSignalParticle(p_172502_, p_172501_.m_175856_(), p_172501_.m_175856_().m_157938_());
         vibrationsignalparticle.m_108335_(this.f_172488_);
         vibrationsignalparticle.m_107271_(1.0F);
         return vibrationsignalparticle;
      }
   }
}