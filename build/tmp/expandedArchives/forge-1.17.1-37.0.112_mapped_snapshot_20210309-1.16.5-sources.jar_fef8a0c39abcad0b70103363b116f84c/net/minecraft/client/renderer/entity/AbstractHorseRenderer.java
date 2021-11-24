package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HorseModel;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractHorseRenderer<T extends AbstractHorse, M extends HorseModel<T>> extends MobRenderer<T, M> {
   private final float f_113744_;

   public AbstractHorseRenderer(EntityRendererProvider.Context p_173906_, M p_173907_, float p_173908_) {
      super(p_173906_, p_173907_, 0.75F);
      this.f_113744_ = p_173908_;
   }

   protected void m_7546_(T p_113754_, PoseStack p_113755_, float p_113756_) {
      p_113755_.m_85841_(this.f_113744_, this.f_113744_, this.f_113744_);
      super.m_7546_(p_113754_, p_113755_, p_113756_);
   }
}