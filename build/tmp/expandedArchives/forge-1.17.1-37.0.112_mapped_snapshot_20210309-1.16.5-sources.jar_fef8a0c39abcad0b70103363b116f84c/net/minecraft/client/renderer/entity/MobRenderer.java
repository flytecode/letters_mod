package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class MobRenderer<T extends Mob, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {
   public static final int f_174302_ = 24;

   public MobRenderer(EntityRendererProvider.Context p_174304_, M p_174305_, float p_174306_) {
      super(p_174304_, p_174305_, p_174306_);
   }

   protected boolean m_6512_(T p_115506_) {
      return super.m_6512_(p_115506_) && (p_115506_.m_6052_() || p_115506_.m_8077_() && p_115506_ == this.f_114476_.f_114359_);
   }

   public boolean m_5523_(T p_115468_, Frustum p_115469_, double p_115470_, double p_115471_, double p_115472_) {
      if (super.m_5523_(p_115468_, p_115469_, p_115470_, p_115471_, p_115472_)) {
         return true;
      } else {
         Entity entity = p_115468_.m_21524_();
         return entity != null ? p_115469_.m_113029_(entity.m_6921_()) : false;
      }
   }

   public void m_7392_(T p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
      super.m_7392_(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
      Entity entity = p_115455_.m_21524_();
      if (entity != null) {
         this.m_115461_(p_115455_, p_115457_, p_115458_, p_115459_, entity);
      }
   }

   private <E extends Entity> void m_115461_(T p_115462_, float p_115463_, PoseStack p_115464_, MultiBufferSource p_115465_, E p_115466_) {
      p_115464_.m_85836_();
      Vec3 vec3 = p_115466_.m_7398_(p_115463_);
      double d0 = (double)(Mth.m_14179_(p_115463_, p_115462_.f_20883_, p_115462_.f_20884_) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
      Vec3 vec31 = p_115462_.m_7939_();
      double d1 = Math.cos(d0) * vec31.f_82481_ + Math.sin(d0) * vec31.f_82479_;
      double d2 = Math.sin(d0) * vec31.f_82481_ - Math.cos(d0) * vec31.f_82479_;
      double d3 = Mth.m_14139_((double)p_115463_, p_115462_.f_19854_, p_115462_.m_20185_()) + d1;
      double d4 = Mth.m_14139_((double)p_115463_, p_115462_.f_19855_, p_115462_.m_20186_()) + vec31.f_82480_;
      double d5 = Mth.m_14139_((double)p_115463_, p_115462_.f_19856_, p_115462_.m_20189_()) + d2;
      p_115464_.m_85837_(d1, vec31.f_82480_, d2);
      float f = (float)(vec3.f_82479_ - d3);
      float f1 = (float)(vec3.f_82480_ - d4);
      float f2 = (float)(vec3.f_82481_ - d5);
      float f3 = 0.025F;
      VertexConsumer vertexconsumer = p_115465_.m_6299_(RenderType.m_110475_());
      Matrix4f matrix4f = p_115464_.m_85850_().m_85861_();
      float f4 = Mth.m_14195_(f * f + f2 * f2) * 0.025F / 2.0F;
      float f5 = f2 * f4;
      float f6 = f * f4;
      BlockPos blockpos = new BlockPos(p_115462_.m_20299_(p_115463_));
      BlockPos blockpos1 = new BlockPos(p_115466_.m_20299_(p_115463_));
      int i = this.m_6086_(p_115462_, blockpos);
      int j = this.f_114476_.m_114382_(p_115466_).m_6086_(p_115466_, blockpos1);
      int k = p_115462_.f_19853_.m_45517_(LightLayer.SKY, blockpos);
      int l = p_115462_.f_19853_.m_45517_(LightLayer.SKY, blockpos1);

      for(int i1 = 0; i1 <= 24; ++i1) {
         m_174307_(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
      }

      for(int j1 = 24; j1 >= 0; --j1) {
         m_174307_(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
      }

      p_115464_.m_85849_();
   }

   private static void m_174307_(VertexConsumer p_174308_, Matrix4f p_174309_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_) {
      float f = (float)p_174321_ / 24.0F;
      int i = (int)Mth.m_14179_(f, (float)p_174313_, (float)p_174314_);
      int j = (int)Mth.m_14179_(f, (float)p_174315_, (float)p_174316_);
      int k = LightTexture.m_109885_(i, j);
      float f1 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
      float f2 = 0.5F * f1;
      float f3 = 0.4F * f1;
      float f4 = 0.3F * f1;
      float f5 = p_174310_ * f;
      float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
      float f7 = p_174312_ * f;
      p_174308_.m_85982_(p_174309_, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).m_85950_(f2, f3, f4, 1.0F).m_85969_(k).m_5752_();
      p_174308_.m_85982_(p_174309_, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).m_85950_(f2, f3, f4, 1.0F).m_85969_(k).m_5752_();
   }
}