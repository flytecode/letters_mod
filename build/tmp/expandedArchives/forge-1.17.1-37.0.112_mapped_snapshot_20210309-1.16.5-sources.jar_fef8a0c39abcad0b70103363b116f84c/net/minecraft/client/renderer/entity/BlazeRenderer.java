package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlazeRenderer extends MobRenderer<Blaze, BlazeModel<Blaze>> {
   private static final ResourceLocation f_113898_ = new ResourceLocation("textures/entity/blaze.png");

   public BlazeRenderer(EntityRendererProvider.Context p_173933_) {
      super(p_173933_, new BlazeModel<>(p_173933_.m_174023_(ModelLayers.f_171270_)), 0.5F);
   }

   protected int m_6086_(Blaze p_113910_, BlockPos p_113911_) {
      return 15;
   }

   public ResourceLocation m_5478_(Blaze p_113908_) {
      return f_113898_;
   }
}