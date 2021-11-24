package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FishingHookRenderer extends EntityRenderer<FishingHook> {
   private static final ResourceLocation f_114678_ = new ResourceLocation("textures/entity/fishing_hook.png");
   private static final RenderType f_114679_ = RenderType.m_110452_(f_114678_);
   private static final double f_174115_ = 960.0D;

   public FishingHookRenderer(EntityRendererProvider.Context p_174117_) {
      super(p_174117_);
   }

   public void m_7392_(FishingHook p_114705_, float p_114706_, float p_114707_, PoseStack p_114708_, MultiBufferSource p_114709_, int p_114710_) {
      Player player = p_114705_.m_37168_();
      if (player != null) {
         p_114708_.m_85836_();
         p_114708_.m_85836_();
         p_114708_.m_85841_(0.5F, 0.5F, 0.5F);
         p_114708_.m_85845_(this.f_114476_.m_114470_());
         p_114708_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
         PoseStack.Pose posestack$pose = p_114708_.m_85850_();
         Matrix4f matrix4f = posestack$pose.m_85861_();
         Matrix3f matrix3f = posestack$pose.m_85864_();
         VertexConsumer vertexconsumer = p_114709_.m_6299_(f_114679_);
         m_114711_(vertexconsumer, matrix4f, matrix3f, p_114710_, 0.0F, 0, 0, 1);
         m_114711_(vertexconsumer, matrix4f, matrix3f, p_114710_, 1.0F, 0, 1, 1);
         m_114711_(vertexconsumer, matrix4f, matrix3f, p_114710_, 1.0F, 1, 1, 0);
         m_114711_(vertexconsumer, matrix4f, matrix3f, p_114710_, 0.0F, 1, 0, 0);
         p_114708_.m_85849_();
         int i = player.m_5737_() == HumanoidArm.RIGHT ? 1 : -1;
         ItemStack itemstack = player.m_21205_();
         if (!itemstack.m_150930_(Items.f_42523_)) {
            i = -i;
         }

         float f = player.m_21324_(p_114707_);
         float f1 = Mth.m_14031_(Mth.m_14116_(f) * (float)Math.PI);
         float f2 = Mth.m_14179_(p_114707_, player.f_20884_, player.f_20883_) * ((float)Math.PI / 180F);
         double d0 = (double)Mth.m_14031_(f2);
         double d1 = (double)Mth.m_14089_(f2);
         double d2 = (double)i * 0.35D;
         double d3 = 0.8D;
         double d4;
         double d5;
         double d6;
         float f3;
         if ((this.f_114476_.f_114360_ == null || this.f_114476_.f_114360_.m_92176_().m_90612_()) && player == Minecraft.m_91087_().f_91074_) {
            double d7 = 960.0D / this.f_114476_.f_114360_.f_92068_;
            Vec3 vec3 = this.f_114476_.f_114358_.m_167684_().m_167695_((float)i * 0.525F, -0.1F);
            vec3 = vec3.m_82490_(d7);
            vec3 = vec3.m_82524_(f1 * 0.5F);
            vec3 = vec3.m_82496_(-f1 * 0.7F);
            d4 = Mth.m_14139_((double)p_114707_, player.f_19854_, player.m_20185_()) + vec3.f_82479_;
            d5 = Mth.m_14139_((double)p_114707_, player.f_19855_, player.m_20186_()) + vec3.f_82480_;
            d6 = Mth.m_14139_((double)p_114707_, player.f_19856_, player.m_20189_()) + vec3.f_82481_;
            f3 = player.m_20192_();
         } else {
            d4 = Mth.m_14139_((double)p_114707_, player.f_19854_, player.m_20185_()) - d1 * d2 - d0 * 0.8D;
            d5 = player.f_19855_ + (double)player.m_20192_() + (player.m_20186_() - player.f_19855_) * (double)p_114707_ - 0.45D;
            d6 = Mth.m_14139_((double)p_114707_, player.f_19856_, player.m_20189_()) - d0 * d2 + d1 * 0.8D;
            f3 = player.m_6047_() ? -0.1875F : 0.0F;
         }

         double d9 = Mth.m_14139_((double)p_114707_, p_114705_.f_19854_, p_114705_.m_20185_());
         double d10 = Mth.m_14139_((double)p_114707_, p_114705_.f_19855_, p_114705_.m_20186_()) + 0.25D;
         double d8 = Mth.m_14139_((double)p_114707_, p_114705_.f_19856_, p_114705_.m_20189_());
         float f4 = (float)(d4 - d9);
         float f5 = (float)(d5 - d10) + f3;
         float f6 = (float)(d6 - d8);
         VertexConsumer vertexconsumer1 = p_114709_.m_6299_(RenderType.m_173247_());
         PoseStack.Pose posestack$pose1 = p_114708_.m_85850_();
         int j = 16;

         for(int k = 0; k <= 16; ++k) {
            m_174118_(f4, f5, f6, vertexconsumer1, posestack$pose1, m_114690_(k, 16), m_114690_(k + 1, 16));
         }

         p_114708_.m_85849_();
         super.m_7392_(p_114705_, p_114706_, p_114707_, p_114708_, p_114709_, p_114710_);
      }
   }

   private static float m_114690_(int p_114691_, int p_114692_) {
      return (float)p_114691_ / (float)p_114692_;
   }

   private static void m_114711_(VertexConsumer p_114712_, Matrix4f p_114713_, Matrix3f p_114714_, int p_114715_, float p_114716_, int p_114717_, int p_114718_, int p_114719_) {
      p_114712_.m_85982_(p_114713_, p_114716_ - 0.5F, (float)p_114717_ - 0.5F, 0.0F).m_6122_(255, 255, 255, 255).m_7421_((float)p_114718_, (float)p_114719_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114715_).m_85977_(p_114714_, 0.0F, 1.0F, 0.0F).m_5752_();
   }

   private static void m_174118_(float p_174119_, float p_174120_, float p_174121_, VertexConsumer p_174122_, PoseStack.Pose p_174123_, float p_174124_, float p_174125_) {
      float f = p_174119_ * p_174124_;
      float f1 = p_174120_ * (p_174124_ * p_174124_ + p_174124_) * 0.5F + 0.25F;
      float f2 = p_174121_ * p_174124_;
      float f3 = p_174119_ * p_174125_ - f;
      float f4 = p_174120_ * (p_174125_ * p_174125_ + p_174125_) * 0.5F + 0.25F - f1;
      float f5 = p_174121_ * p_174125_ - f2;
      float f6 = Mth.m_14116_(f3 * f3 + f4 * f4 + f5 * f5);
      f3 = f3 / f6;
      f4 = f4 / f6;
      f5 = f5 / f6;
      p_174122_.m_85982_(p_174123_.m_85861_(), f, f1, f2).m_6122_(0, 0, 0, 255).m_85977_(p_174123_.m_85864_(), f3, f4, f5).m_5752_();
   }

   public ResourceLocation m_5478_(FishingHook p_114703_) {
      return f_114678_;
   }
}