package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Random;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CarriedBlockLayer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndermanRenderer extends MobRenderer<EnderMan, EndermanModel<EnderMan>> {
   private static final ResourceLocation f_114302_ = new ResourceLocation("textures/entity/enderman/enderman.png");
   private final Random f_114303_ = new Random();

   public EndermanRenderer(EntityRendererProvider.Context p_173992_) {
      super(p_173992_, new EndermanModel<>(p_173992_.m_174023_(ModelLayers.f_171142_)), 0.5F);
      this.m_115326_(new EnderEyesLayer<>(this));
      this.m_115326_(new CarriedBlockLayer(this));
   }

   public void m_7392_(EnderMan p_114339_, float p_114340_, float p_114341_, PoseStack p_114342_, MultiBufferSource p_114343_, int p_114344_) {
      BlockState blockstate = p_114339_.m_32530_();
      EndermanModel<EnderMan> endermanmodel = this.m_7200_();
      endermanmodel.f_102576_ = blockstate != null;
      endermanmodel.f_102577_ = p_114339_.m_32531_();
      super.m_7392_(p_114339_, p_114340_, p_114341_, p_114342_, p_114343_, p_114344_);
   }

   public Vec3 m_7860_(EnderMan p_114336_, float p_114337_) {
      if (p_114336_.m_32531_()) {
         double d0 = 0.02D;
         return new Vec3(this.f_114303_.nextGaussian() * 0.02D, 0.0D, this.f_114303_.nextGaussian() * 0.02D);
      } else {
         return super.m_7860_(p_114336_, p_114337_);
      }
   }

   public ResourceLocation m_5478_(EnderMan p_114334_) {
      return f_114302_;
   }
}