package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EvokerFangsRenderer extends EntityRenderer<EvokerFangs> {
   private static final ResourceLocation f_114511_ = new ResourceLocation("textures/entity/illager/evoker_fangs.png");
   private final EvokerFangsModel<EvokerFangs> f_114512_;

   public EvokerFangsRenderer(EntityRendererProvider.Context p_174100_) {
      super(p_174100_);
      this.f_114512_ = new EvokerFangsModel<>(p_174100_.m_174023_(ModelLayers.f_171147_));
   }

   public void m_7392_(EvokerFangs p_114528_, float p_114529_, float p_114530_, PoseStack p_114531_, MultiBufferSource p_114532_, int p_114533_) {
      float f = p_114528_.m_36936_(p_114530_);
      if (f != 0.0F) {
         float f1 = 2.0F;
         if (f > 0.9F) {
            f1 = (float)((double)f1 * ((1.0D - (double)f) / (double)0.1F));
         }

         p_114531_.m_85836_();
         p_114531_.m_85845_(Vector3f.f_122225_.m_122240_(90.0F - p_114528_.m_146908_()));
         p_114531_.m_85841_(-f1, -f1, f1);
         float f2 = 0.03125F;
         p_114531_.m_85837_(0.0D, (double)-0.626F, 0.0D);
         p_114531_.m_85841_(0.5F, 0.5F, 0.5F);
         this.f_114512_.m_6973_(p_114528_, f, 0.0F, 0.0F, p_114528_.m_146908_(), p_114528_.m_146909_());
         VertexConsumer vertexconsumer = p_114532_.m_6299_(this.f_114512_.m_103119_(f_114511_));
         this.f_114512_.m_7695_(p_114531_, vertexconsumer, p_114533_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         p_114531_.m_85849_();
         super.m_7392_(p_114528_, p_114529_, p_114530_, p_114531_, p_114532_, p_114533_);
      }
   }

   public ResourceLocation m_5478_(EvokerFangs p_114526_) {
      return f_114511_;
   }
}