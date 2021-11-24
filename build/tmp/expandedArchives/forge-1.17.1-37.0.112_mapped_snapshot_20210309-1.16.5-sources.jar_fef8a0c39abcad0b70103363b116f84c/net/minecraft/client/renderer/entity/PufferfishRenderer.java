package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PufferfishBigModel;
import net.minecraft.client.model.PufferfishMidModel;
import net.minecraft.client.model.PufferfishSmallModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PufferfishRenderer extends MobRenderer<Pufferfish, EntityModel<Pufferfish>> {
   private static final ResourceLocation f_115737_ = new ResourceLocation("textures/entity/fish/pufferfish.png");
   private int f_115738_ = 3;
   private final EntityModel<Pufferfish> f_115739_;
   private final EntityModel<Pufferfish> f_115740_;
   private final EntityModel<Pufferfish> f_115741_ = this.m_7200_();

   public PufferfishRenderer(EntityRendererProvider.Context p_174358_) {
      super(p_174358_, new PufferfishBigModel<>(p_174358_.m_174023_(ModelLayers.f_171171_)), 0.2F);
      this.f_115740_ = new PufferfishMidModel<>(p_174358_.m_174023_(ModelLayers.f_171172_));
      this.f_115739_ = new PufferfishSmallModel<>(p_174358_.m_174023_(ModelLayers.f_171173_));
   }

   public ResourceLocation m_5478_(Pufferfish p_115775_) {
      return f_115737_;
   }

   public void m_7392_(Pufferfish p_115777_, float p_115778_, float p_115779_, PoseStack p_115780_, MultiBufferSource p_115781_, int p_115782_) {
      int i = p_115777_.m_29631_();
      if (i != this.f_115738_) {
         if (i == 0) {
            this.f_115290_ = this.f_115739_;
         } else if (i == 1) {
            this.f_115290_ = this.f_115740_;
         } else {
            this.f_115290_ = this.f_115741_;
         }
      }

      this.f_115738_ = i;
      this.f_114477_ = 0.1F + 0.1F * (float)i;
      super.m_7392_(p_115777_, p_115778_, p_115779_, p_115780_, p_115781_, p_115782_);
   }

   protected void m_7523_(Pufferfish p_115784_, PoseStack p_115785_, float p_115786_, float p_115787_, float p_115788_) {
      p_115785_.m_85837_(0.0D, (double)(Mth.m_14089_(p_115786_ * 0.05F) * 0.08F), 0.0D);
      super.m_7523_(p_115784_, p_115785_, p_115786_, p_115787_, p_115788_);
   }
}