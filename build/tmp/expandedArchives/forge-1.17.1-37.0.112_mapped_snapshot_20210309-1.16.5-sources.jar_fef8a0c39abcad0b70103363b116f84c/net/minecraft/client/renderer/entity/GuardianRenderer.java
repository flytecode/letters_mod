package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuardianRenderer extends MobRenderer<Guardian, GuardianModel> {
   private static final ResourceLocation f_114778_ = new ResourceLocation("textures/entity/guardian.png");
   private static final ResourceLocation f_114779_ = new ResourceLocation("textures/entity/guardian_beam.png");
   private static final RenderType f_114780_ = RenderType.m_110458_(f_114779_);

   public GuardianRenderer(EntityRendererProvider.Context p_174159_) {
      this(p_174159_, 0.5F, ModelLayers.f_171183_);
   }

   protected GuardianRenderer(EntityRendererProvider.Context p_174161_, float p_174162_, ModelLayerLocation p_174163_) {
      super(p_174161_, new GuardianModel(p_174161_.m_174023_(p_174163_)), p_174162_);
   }

   public boolean m_5523_(Guardian p_114836_, Frustum p_114837_, double p_114838_, double p_114839_, double p_114840_) {
      if (super.m_5523_(p_114836_, p_114837_, p_114838_, p_114839_, p_114840_)) {
         return true;
      } else {
         if (p_114836_.m_32855_()) {
            LivingEntity livingentity = p_114836_.m_32856_();
            if (livingentity != null) {
               Vec3 vec3 = this.m_114802_(livingentity, (double)livingentity.m_20206_() * 0.5D, 1.0F);
               Vec3 vec31 = this.m_114802_(p_114836_, (double)p_114836_.m_20192_(), 1.0F);
               return p_114837_.m_113029_(new AABB(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_));
            }
         }

         return false;
      }
   }

   private Vec3 m_114802_(LivingEntity p_114803_, double p_114804_, float p_114805_) {
      double d0 = Mth.m_14139_((double)p_114805_, p_114803_.f_19790_, p_114803_.m_20185_());
      double d1 = Mth.m_14139_((double)p_114805_, p_114803_.f_19791_, p_114803_.m_20186_()) + p_114804_;
      double d2 = Mth.m_14139_((double)p_114805_, p_114803_.f_19792_, p_114803_.m_20189_());
      return new Vec3(d0, d1, d2);
   }

   public void m_7392_(Guardian p_114829_, float p_114830_, float p_114831_, PoseStack p_114832_, MultiBufferSource p_114833_, int p_114834_) {
      super.m_7392_(p_114829_, p_114830_, p_114831_, p_114832_, p_114833_, p_114834_);
      LivingEntity livingentity = p_114829_.m_32856_();
      if (livingentity != null) {
         float f = p_114829_.m_32812_(p_114831_);
         float f1 = (float)p_114829_.f_19853_.m_46467_() + p_114831_;
         float f2 = f1 * 0.5F % 1.0F;
         float f3 = p_114829_.m_20192_();
         p_114832_.m_85836_();
         p_114832_.m_85837_(0.0D, (double)f3, 0.0D);
         Vec3 vec3 = this.m_114802_(livingentity, (double)livingentity.m_20206_() * 0.5D, p_114831_);
         Vec3 vec31 = this.m_114802_(p_114829_, (double)f3, p_114831_);
         Vec3 vec32 = vec3.m_82546_(vec31);
         float f4 = (float)(vec32.m_82553_() + 1.0D);
         vec32 = vec32.m_82541_();
         float f5 = (float)Math.acos(vec32.f_82480_);
         float f6 = (float)Math.atan2(vec32.f_82481_, vec32.f_82479_);
         p_114832_.m_85845_(Vector3f.f_122225_.m_122240_((((float)Math.PI / 2F) - f6) * (180F / (float)Math.PI)));
         p_114832_.m_85845_(Vector3f.f_122223_.m_122240_(f5 * (180F / (float)Math.PI)));
         int i = 1;
         float f7 = f1 * 0.05F * -1.5F;
         float f8 = f * f;
         int j = 64 + (int)(f8 * 191.0F);
         int k = 32 + (int)(f8 * 191.0F);
         int l = 128 - (int)(f8 * 64.0F);
         float f9 = 0.2F;
         float f10 = 0.282F;
         float f11 = Mth.m_14089_(f7 + 2.3561945F) * 0.282F;
         float f12 = Mth.m_14031_(f7 + 2.3561945F) * 0.282F;
         float f13 = Mth.m_14089_(f7 + ((float)Math.PI / 4F)) * 0.282F;
         float f14 = Mth.m_14031_(f7 + ((float)Math.PI / 4F)) * 0.282F;
         float f15 = Mth.m_14089_(f7 + 3.926991F) * 0.282F;
         float f16 = Mth.m_14031_(f7 + 3.926991F) * 0.282F;
         float f17 = Mth.m_14089_(f7 + 5.4977875F) * 0.282F;
         float f18 = Mth.m_14031_(f7 + 5.4977875F) * 0.282F;
         float f19 = Mth.m_14089_(f7 + (float)Math.PI) * 0.2F;
         float f20 = Mth.m_14031_(f7 + (float)Math.PI) * 0.2F;
         float f21 = Mth.m_14089_(f7 + 0.0F) * 0.2F;
         float f22 = Mth.m_14031_(f7 + 0.0F) * 0.2F;
         float f23 = Mth.m_14089_(f7 + ((float)Math.PI / 2F)) * 0.2F;
         float f24 = Mth.m_14031_(f7 + ((float)Math.PI / 2F)) * 0.2F;
         float f25 = Mth.m_14089_(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
         float f26 = Mth.m_14031_(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
         float f27 = 0.0F;
         float f28 = 0.4999F;
         float f29 = -1.0F + f2;
         float f30 = f4 * 2.5F + f29;
         VertexConsumer vertexconsumer = p_114833_.m_6299_(f_114780_);
         PoseStack.Pose posestack$pose = p_114832_.m_85850_();
         Matrix4f matrix4f = posestack$pose.m_85861_();
         Matrix3f matrix3f = posestack$pose.m_85864_();
         m_114841_(vertexconsumer, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);
         float f31 = 0.0F;
         if (p_114829_.f_19797_ % 2 == 0) {
            f31 = 0.5F;
         }

         m_114841_(vertexconsumer, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
         m_114841_(vertexconsumer, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);
         p_114832_.m_85849_();
      }

   }

   private static void m_114841_(VertexConsumer p_114842_, Matrix4f p_114843_, Matrix3f p_114844_, float p_114845_, float p_114846_, float p_114847_, int p_114848_, int p_114849_, int p_114850_, float p_114851_, float p_114852_) {
      p_114842_.m_85982_(p_114843_, p_114845_, p_114846_, p_114847_).m_6122_(p_114848_, p_114849_, p_114850_, 255).m_7421_(p_114851_, p_114852_).m_86008_(OverlayTexture.f_118083_).m_85969_(15728880).m_85977_(p_114844_, 0.0F, 1.0F, 0.0F).m_5752_();
   }

   public ResourceLocation m_5478_(Guardian p_114827_) {
      return f_114778_;
   }
}