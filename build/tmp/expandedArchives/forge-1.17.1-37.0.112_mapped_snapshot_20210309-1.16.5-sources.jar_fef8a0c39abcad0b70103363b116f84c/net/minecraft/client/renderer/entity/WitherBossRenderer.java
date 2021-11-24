package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitherBossRenderer extends MobRenderer<WitherBoss, WitherBossModel<WitherBoss>> {
   private static final ResourceLocation f_116422_ = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation f_116423_ = new ResourceLocation("textures/entity/wither/wither.png");

   public WitherBossRenderer(EntityRendererProvider.Context p_174445_) {
      super(p_174445_, new WitherBossModel<>(p_174445_.m_174023_(ModelLayers.f_171214_)), 1.0F);
      this.m_115326_(new WitherArmorLayer(this, p_174445_.m_174027_()));
   }

   protected int m_6086_(WitherBoss p_116443_, BlockPos p_116444_) {
      return 15;
   }

   public ResourceLocation m_5478_(WitherBoss p_116437_) {
      int i = p_116437_.m_31502_();
      return i > 0 && (i > 80 || i / 5 % 2 != 1) ? f_116422_ : f_116423_;
   }

   protected void m_7546_(WitherBoss p_116439_, PoseStack p_116440_, float p_116441_) {
      float f = 2.0F;
      int i = p_116439_.m_31502_();
      if (i > 0) {
         f -= ((float)i - p_116441_) / 220.0F * 0.5F;
      }

      p_116440_.m_85841_(f, f, f);
   }
}