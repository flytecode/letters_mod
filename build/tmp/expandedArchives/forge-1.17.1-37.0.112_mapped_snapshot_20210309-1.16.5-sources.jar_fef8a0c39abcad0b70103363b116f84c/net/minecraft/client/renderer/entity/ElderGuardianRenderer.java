package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElderGuardianRenderer extends GuardianRenderer {
   public static final ResourceLocation f_114116_ = new ResourceLocation("textures/entity/guardian_elder.png");

   public ElderGuardianRenderer(EntityRendererProvider.Context p_173966_) {
      super(p_173966_, 1.2F, ModelLayers.f_171140_);
   }

   protected void m_7546_(Guardian p_114129_, PoseStack p_114130_, float p_114131_) {
      p_114130_.m_85841_(ElderGuardian.f_32457_, ElderGuardian.f_32457_, ElderGuardian.f_32457_);
   }

   public ResourceLocation m_5478_(Guardian p_114127_) {
      return f_114116_;
   }
}