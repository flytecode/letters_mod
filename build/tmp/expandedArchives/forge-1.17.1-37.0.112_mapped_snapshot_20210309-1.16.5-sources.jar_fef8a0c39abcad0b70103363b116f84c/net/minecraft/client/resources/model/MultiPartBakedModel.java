package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

@OnlyIn(Dist.CLIENT)
public class MultiPartBakedModel implements net.minecraftforge.client.model.data.IDynamicBakedModel {
   private final List<Pair<Predicate<BlockState>, BakedModel>> f_119459_;
   protected final boolean f_119453_;
   protected final boolean f_119454_;
   protected final boolean f_119455_;
   protected final TextureAtlasSprite f_119456_;
   protected final ItemTransforms f_119457_;
   protected final ItemOverrides f_119458_;
   private final Map<BlockState, BitSet> f_119460_ = new Object2ObjectOpenCustomHashMap<>(Util.m_137583_());
   private final BakedModel defaultModel;

   public MultiPartBakedModel(List<Pair<Predicate<BlockState>, BakedModel>> p_119462_) {
      this.f_119459_ = p_119462_;
      BakedModel bakedmodel = p_119462_.iterator().next().getRight();
      this.defaultModel = bakedmodel;
      this.f_119453_ = bakedmodel.m_7541_();
      this.f_119454_ = bakedmodel.m_7539_();
      this.f_119455_ = bakedmodel.m_7547_();
      this.f_119456_ = bakedmodel.m_6160_();
      this.f_119457_ = bakedmodel.m_7442_();
      this.f_119458_ = bakedmodel.m_7343_();
   }

   // FORGE: Implement our overloads (here and below) so child models can have custom logic 
   public List<BakedQuad> getQuads(@Nullable BlockState p_119465_, @Nullable Direction p_119466_, Random p_119467_, net.minecraftforge.client.model.data.IModelData modelData) {
      if (p_119465_ == null) {
         return Collections.emptyList();
      } else {
         BitSet bitset = this.f_119460_.get(p_119465_);
         if (bitset == null) {
            bitset = new BitSet();

            for(int i = 0; i < this.f_119459_.size(); ++i) {
               Pair<Predicate<BlockState>, BakedModel> pair = this.f_119459_.get(i);
               if (pair.getLeft().test(p_119465_)) {
                  bitset.set(i);
               }
            }

            this.f_119460_.put(p_119465_, bitset);
         }

         List<BakedQuad> list = Lists.newArrayList();
         long k = p_119467_.nextLong();

         for(int j = 0; j < bitset.length(); ++j) {
            if (bitset.get(j)) {
               list.addAll(this.f_119459_.get(j).getRight().getQuads(p_119465_, p_119466_, new Random(k), net.minecraftforge.client.model.data.MultipartModelData.resolve(this.f_119459_.get(j).getRight(), modelData)));
            }
         }

         return list;
      }
   }

   public boolean m_7541_() {
      return this.f_119453_;
   }

   public boolean isAmbientOcclusion(BlockState state) {
      return this.defaultModel.isAmbientOcclusion(state);
   }

   public boolean m_7539_() {
      return this.f_119454_;
   }

   public boolean m_7547_() {
      return this.f_119455_;
   }

   public boolean m_7521_() {
      return false;
   }

   @Deprecated
   public TextureAtlasSprite m_6160_() {
      return this.f_119456_;
   }

   public TextureAtlasSprite getParticleIcon(net.minecraftforge.client.model.data.IModelData modelData) {
      return this.defaultModel.getParticleIcon(modelData);
   }

   @Deprecated
   public ItemTransforms m_7442_() {
      return this.f_119457_;
   }

   public BakedModel handlePerspective(net.minecraft.client.renderer.block.model.ItemTransforms.TransformType transformType, com.mojang.blaze3d.vertex.PoseStack poseStack) {
      return this.defaultModel.handlePerspective(transformType, poseStack);
   }

   public ItemOverrides m_7343_() {
      return this.f_119458_;
   }

   @Override
   public net.minecraftforge.client.model.data.IModelData getModelData(net.minecraft.world.level.BlockAndTintGetter world, net.minecraft.core.BlockPos pos, BlockState state, net.minecraftforge.client.model.data.IModelData tileData) {
      return net.minecraftforge.client.model.data.MultipartModelData.create(f_119459_, world, pos, state, tileData);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Builder {
      private final List<Pair<Predicate<BlockState>, BakedModel>> f_119474_ = Lists.newArrayList();

      public void m_119477_(Predicate<BlockState> p_119478_, BakedModel p_119479_) {
         this.f_119474_.add(Pair.of(p_119478_, p_119479_));
      }

      public BakedModel m_119476_() {
         return new MultiPartBakedModel(this.f_119474_);
      }
   }
}
