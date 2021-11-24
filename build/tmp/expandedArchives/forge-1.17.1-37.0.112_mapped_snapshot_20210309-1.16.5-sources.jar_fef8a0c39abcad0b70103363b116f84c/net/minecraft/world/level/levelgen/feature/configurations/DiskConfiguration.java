package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;

public class DiskConfiguration implements FeatureConfiguration {
   public static final Codec<DiskConfiguration> f_67618_ = RecordCodecBuilder.create((p_67632_) -> {
      return p_67632_.group(BlockState.f_61039_.fieldOf("state").forGetter((p_160757_) -> {
         return p_160757_.f_67619_;
      }), IntProvider.m_146545_(0, 8).fieldOf("radius").forGetter((p_160755_) -> {
         return p_160755_.f_67620_;
      }), Codec.intRange(0, 4).fieldOf("half_height").forGetter((p_160753_) -> {
         return p_160753_.f_67621_;
      }), BlockState.f_61039_.listOf().fieldOf("targets").forGetter((p_160751_) -> {
         return p_160751_.f_67622_;
      })).apply(p_67632_, DiskConfiguration::new);
   });
   public final BlockState f_67619_;
   public final IntProvider f_67620_;
   public final int f_67621_;
   public final List<BlockState> f_67622_;

   public DiskConfiguration(BlockState p_160746_, IntProvider p_160747_, int p_160748_, List<BlockState> p_160749_) {
      this.f_67619_ = p_160746_;
      this.f_67620_ = p_160747_;
      this.f_67621_ = p_160748_;
      this.f_67622_ = p_160749_;
   }
}