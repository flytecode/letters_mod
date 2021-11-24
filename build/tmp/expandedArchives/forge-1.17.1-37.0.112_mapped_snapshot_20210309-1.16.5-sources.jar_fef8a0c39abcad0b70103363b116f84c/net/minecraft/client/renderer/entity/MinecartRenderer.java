package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MinecartRenderer<T extends AbstractMinecart> extends EntityRenderer<T> {
   private static final ResourceLocation f_115402_ = new ResourceLocation("textures/entity/minecart.png");
   protected final EntityModel<T> f_115401_;

   public MinecartRenderer(EntityRendererProvider.Context p_174300_, ModelLayerLocation p_174301_) {
      super(p_174300_);
      this.f_114477_ = 0.7F;
      this.f_115401_ = new MinecartModel<>(p_174300_.m_174023_(p_174301_));
   }

   public void m_7392_(T p_115418_, float p_115419_, float p_115420_, PoseStack p_115421_, MultiBufferSource p_115422_, int p_115423_) {
      super.m_7392_(p_115418_, p_115419_, p_115420_, p_115421_, p_115422_, p_115423_);
      p_115421_.m_85836_();
      long i = (long)p_115418_.m_142049_() * 493286711L;
      i = i * i * 4392167121L + i * 98761L;
      float f = (((float)(i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float f1 = (((float)(i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float f2 = (((float)(i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      p_115421_.m_85837_((double)f, (double)f1, (double)f2);
      double d0 = Mth.m_14139_((double)p_115420_, p_115418_.f_19790_, p_115418_.m_20185_());
      double d1 = Mth.m_14139_((double)p_115420_, p_115418_.f_19791_, p_115418_.m_20186_());
      double d2 = Mth.m_14139_((double)p_115420_, p_115418_.f_19792_, p_115418_.m_20189_());
      double d3 = (double)0.3F;
      Vec3 vec3 = p_115418_.m_38179_(d0, d1, d2);
      float f3 = Mth.m_14179_(p_115420_, p_115418_.f_19860_, p_115418_.m_146909_());
      if (vec3 != null) {
         Vec3 vec31 = p_115418_.m_38096_(d0, d1, d2, (double)0.3F);
         Vec3 vec32 = p_115418_.m_38096_(d0, d1, d2, (double)-0.3F);
         if (vec31 == null) {
            vec31 = vec3;
         }

         if (vec32 == null) {
            vec32 = vec3;
         }

         p_115421_.m_85837_(vec3.f_82479_ - d0, (vec31.f_82480_ + vec32.f_82480_) / 2.0D - d1, vec3.f_82481_ - d2);
         Vec3 vec33 = vec32.m_82520_(-vec31.f_82479_, -vec31.f_82480_, -vec31.f_82481_);
         if (vec33.m_82553_() != 0.0D) {
            vec33 = vec33.m_82541_();
            p_115419_ = (float)(Math.atan2(vec33.f_82481_, vec33.f_82479_) * 180.0D / Math.PI);
            f3 = (float)(Math.atan(vec33.f_82480_) * 73.0D);
         }
      }

      p_115421_.m_85837_(0.0D, 0.375D, 0.0D);
      p_115421_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - p_115419_));
      p_115421_.m_85845_(Vector3f.f_122227_.m_122240_(-f3));
      float f5 = (float)p_115418_.m_38176_() - p_115420_;
      float f6 = p_115418_.m_38169_() - p_115420_;
      if (f6 < 0.0F) {
         f6 = 0.0F;
      }

      if (f5 > 0.0F) {
         p_115421_.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14031_(f5) * f5 * f6 / 10.0F * (float)p_115418_.m_38177_()));
      }

      int j = p_115418_.m_38183_();
      BlockState blockstate = p_115418_.m_38178_();
      if (blockstate.m_60799_() != RenderShape.INVISIBLE) {
         p_115421_.m_85836_();
         float f4 = 0.75F;
         p_115421_.m_85841_(0.75F, 0.75F, 0.75F);
         p_115421_.m_85837_(-0.5D, (double)((float)(j - 8) / 16.0F), 0.5D);
         p_115421_.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
         this.m_7002_(p_115418_, p_115420_, blockstate, p_115421_, p_115422_, p_115423_);
         p_115421_.m_85849_();
      }

      p_115421_.m_85841_(-1.0F, -1.0F, 1.0F);
      this.f_115401_.m_6973_(p_115418_, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer vertexconsumer = p_115422_.m_6299_(this.f_115401_.m_103119_(this.m_5478_(p_115418_)));
      this.f_115401_.m_7695_(p_115421_, vertexconsumer, p_115423_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_115421_.m_85849_();
   }

   public ResourceLocation m_5478_(T p_115416_) {
      return f_115402_;
   }

   protected void m_7002_(T p_115424_, float p_115425_, BlockState p_115426_, PoseStack p_115427_, MultiBufferSource p_115428_, int p_115429_) {
      Minecraft.m_91087_().m_91289_().m_110912_(p_115426_, p_115427_, p_115428_, p_115429_, OverlayTexture.f_118083_);
   }
}