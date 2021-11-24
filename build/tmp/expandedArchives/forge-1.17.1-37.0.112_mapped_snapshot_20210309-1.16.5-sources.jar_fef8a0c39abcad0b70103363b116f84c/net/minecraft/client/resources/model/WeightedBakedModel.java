package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WeightedBakedModel implements net.minecraftforge.client.model.data.IDynamicBakedModel {
   private final int f_119540_;
   private final List<WeightedEntry.Wrapper<BakedModel>> f_119541_;
   private final BakedModel f_119542_;

   public WeightedBakedModel(List<WeightedEntry.Wrapper<BakedModel>> p_119544_) {
      this.f_119541_ = p_119544_;
      this.f_119540_ = WeightedRandom.m_146312_(p_119544_);
      this.f_119542_ = p_119544_.get(0).m_146310_();
   }

   // FORGE: Implement our overloads (here and below) so child models can have custom logic 
   public List<BakedQuad> getQuads(@Nullable BlockState p_119547_, @Nullable Direction p_119548_, Random p_119549_, net.minecraftforge.client.model.data.IModelData modelData) {
       return WeightedRandom.m_146314_(this.f_119541_, Math.abs((int)p_119549_.nextLong()) % this.f_119540_).map((p_174916_) -> {
           return p_174916_.m_146310_().getQuads(p_119547_, p_119548_, p_119549_, modelData);
       }).orElse(Collections.emptyList());
   }

   public boolean m_7541_() {
      return this.f_119542_.m_7541_();
   }

   @Override
   public boolean isAmbientOcclusion(BlockState state) {
      return this.f_119542_.isAmbientOcclusion(state);
   }

   public boolean m_7539_() {
      return this.f_119542_.m_7539_();
   }

   public boolean m_7547_() {
      return this.f_119542_.m_7547_();
   }

   public boolean m_7521_() {
      return this.f_119542_.m_7521_();
   }

   public TextureAtlasSprite m_6160_() {
      return this.f_119542_.m_6160_();
   }

   public TextureAtlasSprite getParticleIcon(net.minecraftforge.client.model.data.IModelData modelData) {
      return this.f_119542_.getParticleIcon(modelData);
   }

   public ItemTransforms m_7442_() {
      return this.f_119542_.m_7442_();
   }

   public BakedModel handlePerspective(net.minecraft.client.renderer.block.model.ItemTransforms.TransformType transformType, com.mojang.blaze3d.vertex.PoseStack poseStack) {
      return this.f_119542_.handlePerspective(transformType, poseStack);
   }

   public ItemOverrides m_7343_() {
      return this.f_119542_.m_7343_();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Builder {
      private final List<WeightedEntry.Wrapper<BakedModel>> f_119556_ = Lists.newArrayList();

      public WeightedBakedModel.Builder m_119559_(@Nullable BakedModel p_119560_, int p_119561_) {
         if (p_119560_ != null) {
            this.f_119556_.add(WeightedEntry.m_146290_(p_119560_, p_119561_));
         }

         return this;
      }

      @Nullable
      public BakedModel m_119558_() {
         if (this.f_119556_.isEmpty()) {
            return null;
         } else {
            return (BakedModel)(this.f_119556_.size() == 1 ? this.f_119556_.get(0).m_146310_() : new WeightedBakedModel(this.f_119556_));
         }
      }
   }
}
