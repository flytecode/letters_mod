package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MobAppearanceParticle extends Particle {
   private final Model f_107111_;
   private final RenderType f_107112_ = RenderType.m_110473_(ElderGuardianRenderer.f_114116_);

   MobAppearanceParticle(ClientLevel p_107114_, double p_107115_, double p_107116_, double p_107117_) {
      super(p_107114_, p_107115_, p_107116_, p_107117_);
      this.f_107111_ = new GuardianModel(Minecraft.m_91087_().m_167973_().m_171103_(ModelLayers.f_171140_));
      this.f_107226_ = 0.0F;
      this.f_107225_ = 30;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }

   public void m_5744_(VertexConsumer p_107125_, Camera p_107126_, float p_107127_) {
      float f = ((float)this.f_107224_ + p_107127_) / (float)this.f_107225_;
      float f1 = 0.05F + 0.5F * Mth.m_14031_(f * (float)Math.PI);
      PoseStack posestack = new PoseStack();
      posestack.m_85845_(p_107126_.m_90591_());
      posestack.m_85845_(Vector3f.f_122223_.m_122240_(150.0F * f - 60.0F));
      posestack.m_85841_(-1.0F, -1.0F, 1.0F);
      posestack.m_85837_(0.0D, (double)-1.101F, 1.5D);
      MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.m_91087_().m_91269_().m_110104_();
      VertexConsumer vertexconsumer = multibuffersource$buffersource.m_6299_(this.f_107112_);
      this.f_107111_.m_7695_(posestack, vertexconsumer, 15728880, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, f1);
      multibuffersource$buffersource.m_109911_();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      public Particle m_6966_(SimpleParticleType p_107140_, ClientLevel p_107141_, double p_107142_, double p_107143_, double p_107144_, double p_107145_, double p_107146_, double p_107147_) {
         return new MobAppearanceParticle(p_107141_, p_107142_, p_107143_, p_107144_);
      }
   }
}