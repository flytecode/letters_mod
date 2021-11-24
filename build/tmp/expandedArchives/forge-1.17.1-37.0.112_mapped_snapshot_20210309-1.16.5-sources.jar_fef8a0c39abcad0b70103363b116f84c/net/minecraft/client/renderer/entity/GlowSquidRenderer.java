package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SquidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.GlowSquid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlowSquidRenderer extends SquidRenderer<GlowSquid> {
   private static final ResourceLocation f_174133_ = new ResourceLocation("textures/entity/squid/glow_squid.png");

   public GlowSquidRenderer(EntityRendererProvider.Context p_174136_, SquidModel<GlowSquid> p_174137_) {
      super(p_174136_, p_174137_);
   }

   public ResourceLocation m_5478_(GlowSquid p_174144_) {
      return f_174133_;
   }

   protected int m_6086_(GlowSquid p_174146_, BlockPos p_174147_) {
      int i = (int)Mth.m_144920_(0.0F, 15.0F, 1.0F - (float)p_174146_.m_147128_() / 10.0F);
      return i == 15 ? 15 : Math.max(i, super.m_6086_(p_174146_, p_174147_));
   }
}