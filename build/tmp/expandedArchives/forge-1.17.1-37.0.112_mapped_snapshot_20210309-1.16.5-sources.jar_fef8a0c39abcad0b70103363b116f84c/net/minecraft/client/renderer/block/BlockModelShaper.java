package net.minecraft.client.renderer.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockModelShaper {
   private final Map<BlockState, BakedModel> f_110877_ = Maps.newIdentityHashMap();
   private final ModelManager f_110878_;

   public BlockModelShaper(ModelManager p_110880_) {
      this.f_110878_ = p_110880_;
   }

   @Deprecated
   public TextureAtlasSprite m_110882_(BlockState p_110883_) {
      return this.m_110893_(p_110883_).getParticleIcon(net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }

   public TextureAtlasSprite getTexture(BlockState p_110883_, net.minecraft.world.level.Level level, net.minecraft.core.BlockPos pos) {
      net.minecraftforge.client.model.data.IModelData data = net.minecraftforge.client.model.ModelDataManager.getModelData(level, pos);
      BakedModel model = this.m_110893_(p_110883_);
      return model.getParticleIcon(model.getModelData(level, pos, p_110883_, data == null ? net.minecraftforge.client.model.data.EmptyModelData.INSTANCE : data));
   }

   public BakedModel m_110893_(BlockState p_110894_) {
      BakedModel bakedmodel = this.f_110877_.get(p_110894_);
      if (bakedmodel == null) {
         bakedmodel = this.f_110878_.m_119409_();
      }

      return bakedmodel;
   }

   public ModelManager m_110881_() {
      return this.f_110878_;
   }

   public void m_110892_() {
      this.f_110877_.clear();

      for(Block block : Registry.f_122824_) {
         block.m_49965_().m_61056_().forEach((p_110898_) -> {
            this.f_110877_.put(p_110898_, this.f_110878_.m_119422_(m_110895_(p_110898_)));
         });
      }

   }

   public static ModelResourceLocation m_110895_(BlockState p_110896_) {
      return m_110889_(Registry.f_122824_.m_7981_(p_110896_.m_60734_()), p_110896_);
   }

   public static ModelResourceLocation m_110889_(ResourceLocation p_110890_, BlockState p_110891_) {
      return new ModelResourceLocation(p_110890_, m_110887_(p_110891_.m_61148_()));
   }

   public static String m_110887_(Map<Property<?>, Comparable<?>> p_110888_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(Entry<Property<?>, Comparable<?>> entry : p_110888_.entrySet()) {
         if (stringbuilder.length() != 0) {
            stringbuilder.append(',');
         }

         Property<?> property = entry.getKey();
         stringbuilder.append(property.m_61708_());
         stringbuilder.append('=');
         stringbuilder.append(m_110884_(property, entry.getValue()));
      }

      return stringbuilder.toString();
   }

   private static <T extends Comparable<T>> String m_110884_(Property<T> p_110885_, Comparable<?> p_110886_) {
      return p_110885_.m_6940_((T)p_110886_);
   }
}
