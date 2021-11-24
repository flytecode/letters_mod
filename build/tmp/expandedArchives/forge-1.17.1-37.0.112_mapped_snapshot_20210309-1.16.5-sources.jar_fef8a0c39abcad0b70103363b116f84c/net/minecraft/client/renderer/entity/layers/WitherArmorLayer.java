package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitherArmorLayer extends EnergySwirlLayer<WitherBoss, WitherBossModel<WitherBoss>> {
   private static final ResourceLocation f_117695_ = new ResourceLocation("textures/entity/wither/wither_armor.png");
   private final WitherBossModel<WitherBoss> f_117696_;

   public WitherArmorLayer(RenderLayerParent<WitherBoss, WitherBossModel<WitherBoss>> p_174554_, EntityModelSet p_174555_) {
      super(p_174554_);
      this.f_117696_ = new WitherBossModel<>(p_174555_.m_171103_(ModelLayers.f_171215_));
   }

   protected float m_7631_(float p_117702_) {
      return Mth.m_14089_(p_117702_ * 0.02F) * 3.0F;
   }

   protected ResourceLocation m_7029_() {
      return f_117695_;
   }

   protected EntityModel<WitherBoss> m_7193_() {
      return this.f_117696_;
   }
}