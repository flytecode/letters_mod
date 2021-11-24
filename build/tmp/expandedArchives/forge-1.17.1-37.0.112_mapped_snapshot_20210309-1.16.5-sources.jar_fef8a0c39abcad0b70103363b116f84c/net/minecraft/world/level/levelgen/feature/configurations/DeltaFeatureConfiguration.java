package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;

public class DeltaFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<DeltaFeatureConfiguration> f_67593_ = RecordCodecBuilder.create((p_67607_) -> {
      return p_67607_.group(BlockState.f_61039_.fieldOf("contents").forGetter((p_160743_) -> {
         return p_160743_.f_67594_;
      }), BlockState.f_61039_.fieldOf("rim").forGetter((p_160740_) -> {
         return p_160740_.f_67595_;
      }), IntProvider.m_146545_(0, 16).fieldOf("size").forGetter((p_160738_) -> {
         return p_160738_.f_67596_;
      }), IntProvider.m_146545_(0, 16).fieldOf("rim_size").forGetter((p_160736_) -> {
         return p_160736_.f_67597_;
      })).apply(p_67607_, DeltaFeatureConfiguration::new);
   });
   private final BlockState f_67594_;
   private final BlockState f_67595_;
   private final IntProvider f_67596_;
   private final IntProvider f_67597_;

   public DeltaFeatureConfiguration(BlockState p_160731_, BlockState p_160732_, IntProvider p_160733_, IntProvider p_160734_) {
      this.f_67594_ = p_160731_;
      this.f_67595_ = p_160732_;
      this.f_67596_ = p_160733_;
      this.f_67597_ = p_160734_;
   }

   public BlockState m_67608_() {
      return this.f_67594_;
   }

   public BlockState m_67611_() {
      return this.f_67595_;
   }

   public IntProvider m_160741_() {
      return this.f_67596_;
   }

   public IntProvider m_160744_() {
      return this.f_67597_;
   }
}