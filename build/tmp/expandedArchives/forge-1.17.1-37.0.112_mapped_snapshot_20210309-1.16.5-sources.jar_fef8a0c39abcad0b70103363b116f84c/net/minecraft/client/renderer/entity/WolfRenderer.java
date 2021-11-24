package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfRenderer extends MobRenderer<Wolf, WolfModel<Wolf>> {
   private static final ResourceLocation f_116493_ = new ResourceLocation("textures/entity/wolf/wolf.png");
   private static final ResourceLocation f_116494_ = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
   private static final ResourceLocation f_116495_ = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

   public WolfRenderer(EntityRendererProvider.Context p_174452_) {
      super(p_174452_, new WolfModel<>(p_174452_.m_174023_(ModelLayers.f_171221_)), 0.5F);
      this.m_115326_(new WolfCollarLayer(this));
   }

   protected float m_6930_(Wolf p_116528_, float p_116529_) {
      return p_116528_.m_30427_();
   }

   public void m_7392_(Wolf p_116531_, float p_116532_, float p_116533_, PoseStack p_116534_, MultiBufferSource p_116535_, int p_116536_) {
      if (p_116531_.m_30426_()) {
         float f = p_116531_.m_30446_(p_116533_);
         this.f_115290_.m_102419_(f, f, f);
      }

      super.m_7392_(p_116531_, p_116532_, p_116533_, p_116534_, p_116535_, p_116536_);
      if (p_116531_.m_30426_()) {
         this.f_115290_.m_102419_(1.0F, 1.0F, 1.0F);
      }

   }

   public ResourceLocation m_5478_(Wolf p_116526_) {
      if (p_116526_.m_21824_()) {
         return f_116494_;
      } else {
         return p_116526_.m_21660_() ? f_116495_ : f_116493_;
      }
   }
}