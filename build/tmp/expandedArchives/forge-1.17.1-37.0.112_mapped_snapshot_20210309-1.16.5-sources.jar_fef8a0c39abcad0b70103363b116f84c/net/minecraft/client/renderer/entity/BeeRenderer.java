package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.BeeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Bee;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeeRenderer extends MobRenderer<Bee, BeeModel<Bee>> {
   private static final ResourceLocation f_113887_ = new ResourceLocation("textures/entity/bee/bee_angry.png");
   private static final ResourceLocation f_113888_ = new ResourceLocation("textures/entity/bee/bee_angry_nectar.png");
   private static final ResourceLocation f_113889_ = new ResourceLocation("textures/entity/bee/bee.png");
   private static final ResourceLocation f_113890_ = new ResourceLocation("textures/entity/bee/bee_nectar.png");

   public BeeRenderer(EntityRendererProvider.Context p_173931_) {
      super(p_173931_, new BeeModel<>(p_173931_.m_174023_(ModelLayers.f_171268_)), 0.4F);
   }

   public ResourceLocation m_5478_(Bee p_113897_) {
      if (p_113897_.m_21660_()) {
         return p_113897_.m_27856_() ? f_113888_ : f_113887_;
      } else {
         return p_113897_.m_27856_() ? f_113890_ : f_113889_;
      }
   }
}