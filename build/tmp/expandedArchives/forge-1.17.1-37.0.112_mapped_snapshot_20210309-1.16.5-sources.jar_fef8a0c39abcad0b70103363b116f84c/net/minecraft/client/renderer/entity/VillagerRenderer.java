package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VillagerRenderer extends MobRenderer<Villager, VillagerModel<Villager>> {
   private static final ResourceLocation f_116300_ = new ResourceLocation("textures/entity/villager/villager.png");

   public VillagerRenderer(EntityRendererProvider.Context p_174437_) {
      super(p_174437_, new VillagerModel<>(p_174437_.m_174023_(ModelLayers.f_171210_)), 0.5F);
      this.m_115326_(new CustomHeadLayer<>(this, p_174437_.m_174027_()));
      this.m_115326_(new VillagerProfessionLayer<>(this, p_174437_.m_174026_(), "villager"));
      this.m_115326_(new CrossedArmsItemLayer<>(this));
   }

   public ResourceLocation m_5478_(Villager p_116312_) {
      return f_116300_;
   }

   protected void m_7546_(Villager p_116314_, PoseStack p_116315_, float p_116316_) {
      float f = 0.9375F;
      if (p_116314_.m_6162_()) {
         f = (float)((double)f * 0.5D);
         this.f_114477_ = 0.25F;
      } else {
         this.f_114477_ = 0.5F;
      }

      p_116315_.m_85841_(f, f, f);
   }
}