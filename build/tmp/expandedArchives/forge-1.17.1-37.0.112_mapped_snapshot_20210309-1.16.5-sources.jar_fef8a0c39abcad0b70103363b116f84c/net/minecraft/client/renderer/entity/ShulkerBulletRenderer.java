package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerBulletRenderer extends EntityRenderer<ShulkerBullet> {
   private static final ResourceLocation f_115841_ = new ResourceLocation("textures/entity/shulker/spark.png");
   private static final RenderType f_115842_ = RenderType.m_110473_(f_115841_);
   private final ShulkerBulletModel<ShulkerBullet> f_115843_;

   public ShulkerBulletRenderer(EntityRendererProvider.Context p_174368_) {
      super(p_174368_);
      this.f_115843_ = new ShulkerBulletModel<>(p_174368_.m_174023_(ModelLayers.f_171181_));
   }

   protected int m_6086_(ShulkerBullet p_115869_, BlockPos p_115870_) {
      return 15;
   }

   public void m_7392_(ShulkerBullet p_115862_, float p_115863_, float p_115864_, PoseStack p_115865_, MultiBufferSource p_115866_, int p_115867_) {
      p_115865_.m_85836_();
      float f = Mth.m_14201_(p_115862_.f_19859_, p_115862_.m_146908_(), p_115864_);
      float f1 = Mth.m_14179_(p_115864_, p_115862_.f_19860_, p_115862_.m_146909_());
      float f2 = (float)p_115862_.f_19797_ + p_115864_;
      p_115865_.m_85837_(0.0D, (double)0.15F, 0.0D);
      p_115865_.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14031_(f2 * 0.1F) * 180.0F));
      p_115865_.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14089_(f2 * 0.1F) * 180.0F));
      p_115865_.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14031_(f2 * 0.15F) * 360.0F));
      p_115865_.m_85841_(-0.5F, -0.5F, 0.5F);
      this.f_115843_.m_6973_(p_115862_, 0.0F, 0.0F, 0.0F, f, f1);
      VertexConsumer vertexconsumer = p_115866_.m_6299_(this.f_115843_.m_103119_(f_115841_));
      this.f_115843_.m_7695_(p_115865_, vertexconsumer, p_115867_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_115865_.m_85841_(1.5F, 1.5F, 1.5F);
      VertexConsumer vertexconsumer1 = p_115866_.m_6299_(f_115842_);
      this.f_115843_.m_7695_(p_115865_, vertexconsumer1, p_115867_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.15F);
      p_115865_.m_85849_();
      super.m_7392_(p_115862_, p_115863_, p_115864_, p_115865_, p_115866_, p_115867_);
   }

   public ResourceLocation m_5478_(ShulkerBullet p_115860_) {
      return f_115841_;
   }
}