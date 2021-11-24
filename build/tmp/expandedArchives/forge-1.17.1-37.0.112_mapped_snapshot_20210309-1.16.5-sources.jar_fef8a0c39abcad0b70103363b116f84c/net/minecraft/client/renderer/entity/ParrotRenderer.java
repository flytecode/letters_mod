package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParrotRenderer extends MobRenderer<Parrot, ParrotModel> {
   public static final ResourceLocation[] f_115648_ = new ResourceLocation[]{new ResourceLocation("textures/entity/parrot/parrot_red_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_green.png"), new ResourceLocation("textures/entity/parrot/parrot_yellow_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_grey.png")};

   public ParrotRenderer(EntityRendererProvider.Context p_174336_) {
      super(p_174336_, new ParrotModel(p_174336_.m_174023_(ModelLayers.f_171203_)), 0.3F);
   }

   public ResourceLocation m_5478_(Parrot p_115658_) {
      return f_115648_[p_115658_.m_29440_()];
   }

   public float m_6930_(Parrot p_115660_, float p_115661_) {
      float f = Mth.m_14179_(p_115661_, p_115660_.f_29353_, p_115660_.f_29350_);
      float f1 = Mth.m_14179_(p_115661_, p_115660_.f_29352_, p_115660_.f_29351_);
      return (Mth.m_14031_(f) + 1.0F) * f1;
   }
}