package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Slime;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeRenderer extends MobRenderer<Slime, SlimeModel<Slime>> {
   private static final ResourceLocation f_115942_ = new ResourceLocation("textures/entity/slime/slime.png");

   public SlimeRenderer(EntityRendererProvider.Context p_174391_) {
      super(p_174391_, new SlimeModel<>(p_174391_.m_174023_(ModelLayers.f_171241_)), 0.25F);
      this.m_115326_(new SlimeOuterLayer<>(this, p_174391_.m_174027_()));
   }

   public void m_7392_(Slime p_115976_, float p_115977_, float p_115978_, PoseStack p_115979_, MultiBufferSource p_115980_, int p_115981_) {
      this.f_114477_ = 0.25F * (float)p_115976_.m_33632_();
      super.m_7392_(p_115976_, p_115977_, p_115978_, p_115979_, p_115980_, p_115981_);
   }

   protected void m_7546_(Slime p_115983_, PoseStack p_115984_, float p_115985_) {
      float f = 0.999F;
      p_115984_.m_85841_(0.999F, 0.999F, 0.999F);
      p_115984_.m_85837_(0.0D, (double)0.001F, 0.0D);
      float f1 = (float)p_115983_.m_33632_();
      float f2 = Mth.m_14179_(p_115985_, p_115983_.f_33585_, p_115983_.f_33584_) / (f1 * 0.5F + 1.0F);
      float f3 = 1.0F / (f2 + 1.0F);
      p_115984_.m_85841_(f3 * f1, 1.0F / f3 * f1, f3 * f1);
   }

   public ResourceLocation m_5478_(Slime p_115974_) {
      return f_115942_;
   }
}