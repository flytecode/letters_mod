package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;

public class ColumnFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<ColumnFeatureConfiguration> f_67553_ = RecordCodecBuilder.create((p_67563_) -> {
      return p_67563_.group(IntProvider.m_146545_(0, 3).fieldOf("reach").forGetter((p_160722_) -> {
         return p_160722_.f_67554_;
      }), IntProvider.m_146545_(1, 10).fieldOf("height").forGetter((p_160719_) -> {
         return p_160719_.f_67555_;
      })).apply(p_67563_, ColumnFeatureConfiguration::new);
   });
   private final IntProvider f_67554_;
   private final IntProvider f_67555_;

   public ColumnFeatureConfiguration(IntProvider p_160715_, IntProvider p_160716_) {
      this.f_67554_ = p_160715_;
      this.f_67555_ = p_160716_;
   }

   public IntProvider m_160717_() {
      return this.f_67554_;
   }

   public IntProvider m_160720_() {
      return this.f_67555_;
   }
}