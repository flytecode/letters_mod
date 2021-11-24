package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SalmonRenderer extends MobRenderer<Salmon, SalmonModel<Salmon>> {
   private static final ResourceLocation f_115813_ = new ResourceLocation("textures/entity/fish/salmon.png");

   public SalmonRenderer(EntityRendererProvider.Context p_174364_) {
      super(p_174364_, new SalmonModel<>(p_174364_.m_174023_(ModelLayers.f_171176_)), 0.4F);
   }

   public ResourceLocation m_5478_(Salmon p_115826_) {
      return f_115813_;
   }

   protected void m_7523_(Salmon p_115828_, PoseStack p_115829_, float p_115830_, float p_115831_, float p_115832_) {
      super.m_7523_(p_115828_, p_115829_, p_115830_, p_115831_, p_115832_);
      float f = 1.0F;
      float f1 = 1.0F;
      if (!p_115828_.m_20069_()) {
         f = 1.3F;
         f1 = 1.7F;
      }

      float f2 = f * 4.3F * Mth.m_14031_(f1 * 0.6F * p_115830_);
      p_115829_.m_85845_(Vector3f.f_122225_.m_122240_(f2));
      p_115829_.m_85837_(0.0D, 0.0D, (double)-0.4F);
      if (!p_115828_.m_20069_()) {
         p_115829_.m_85837_((double)0.2F, (double)0.1F, 0.0D);
         p_115829_.m_85845_(Vector3f.f_122227_.m_122240_(90.0F));
      }

   }
}