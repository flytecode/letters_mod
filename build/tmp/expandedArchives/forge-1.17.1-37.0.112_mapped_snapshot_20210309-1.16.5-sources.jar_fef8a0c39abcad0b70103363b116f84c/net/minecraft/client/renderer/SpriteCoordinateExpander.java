package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpriteCoordinateExpander implements VertexConsumer {
   private final VertexConsumer f_110795_;
   private final TextureAtlasSprite f_110796_;

   public SpriteCoordinateExpander(VertexConsumer p_110798_, TextureAtlasSprite p_110799_) {
      this.f_110795_ = p_110798_;
      this.f_110796_ = p_110799_;
   }

   public VertexConsumer m_5483_(double p_110801_, double p_110802_, double p_110803_) {
      return this.f_110795_.m_5483_(p_110801_, p_110802_, p_110803_);
   }

   public VertexConsumer m_6122_(int p_110826_, int p_110827_, int p_110828_, int p_110829_) {
      return this.f_110795_.m_6122_(p_110826_, p_110827_, p_110828_, p_110829_);
   }

   public VertexConsumer m_7421_(float p_110805_, float p_110806_) {
      return this.f_110795_.m_7421_(this.f_110796_.m_118367_((double)(p_110805_ * 16.0F)), this.f_110796_.m_118393_((double)(p_110806_ * 16.0F)));
   }

   public VertexConsumer m_7122_(int p_110823_, int p_110824_) {
      return this.f_110795_.m_7122_(p_110823_, p_110824_);
   }

   public VertexConsumer m_7120_(int p_110835_, int p_110836_) {
      return this.f_110795_.m_7120_(p_110835_, p_110836_);
   }

   public VertexConsumer m_5601_(float p_110831_, float p_110832_, float p_110833_) {
      return this.f_110795_.m_5601_(p_110831_, p_110832_, p_110833_);
   }

   public void m_5752_() {
      this.f_110795_.m_5752_();
   }

   public void m_142461_(int p_173392_, int p_173393_, int p_173394_, int p_173395_) {
      this.f_110795_.m_142461_(p_173392_, p_173393_, p_173394_, p_173395_);
   }

   public void m_141991_() {
      this.f_110795_.m_141991_();
   }

   public void m_5954_(float p_110808_, float p_110809_, float p_110810_, float p_110811_, float p_110812_, float p_110813_, float p_110814_, float p_110815_, float p_110816_, int p_110817_, int p_110818_, float p_110819_, float p_110820_, float p_110821_) {
      this.f_110795_.m_5954_(p_110808_, p_110809_, p_110810_, p_110811_, p_110812_, p_110813_, p_110814_, this.f_110796_.m_118367_((double)(p_110815_ * 16.0F)), this.f_110796_.m_118393_((double)(p_110816_ * 16.0F)), p_110817_, p_110818_, p_110819_, p_110820_, p_110821_);
   }
}