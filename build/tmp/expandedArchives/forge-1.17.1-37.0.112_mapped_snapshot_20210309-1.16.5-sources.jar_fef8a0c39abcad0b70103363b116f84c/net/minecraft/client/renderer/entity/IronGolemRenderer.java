package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.IronGolemCrackinessLayer;
import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronGolemRenderer extends MobRenderer<IronGolem, IronGolemModel<IronGolem>> {
   private static final ResourceLocation f_114999_ = new ResourceLocation("textures/entity/iron_golem/iron_golem.png");

   public IronGolemRenderer(EntityRendererProvider.Context p_174188_) {
      super(p_174188_, new IronGolemModel<>(p_174188_.m_174023_(ModelLayers.f_171192_)), 0.7F);
      this.m_115326_(new IronGolemCrackinessLayer(this));
      this.m_115326_(new IronGolemFlowerLayer(this));
   }

   public ResourceLocation m_5478_(IronGolem p_115012_) {
      return f_114999_;
   }

   protected void m_7523_(IronGolem p_115014_, PoseStack p_115015_, float p_115016_, float p_115017_, float p_115018_) {
      super.m_7523_(p_115014_, p_115015_, p_115016_, p_115017_, p_115018_);
      if (!((double)p_115014_.f_20924_ < 0.01D)) {
         float f = 13.0F;
         float f1 = p_115014_.f_20925_ - p_115014_.f_20924_ * (1.0F - p_115018_) + 6.0F;
         float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
         p_115015_.m_85845_(Vector3f.f_122227_.m_122240_(6.5F * f2));
      }
   }
}