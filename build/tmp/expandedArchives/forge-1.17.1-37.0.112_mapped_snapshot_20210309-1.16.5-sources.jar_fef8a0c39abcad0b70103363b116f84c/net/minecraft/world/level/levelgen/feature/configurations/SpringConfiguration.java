package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;

public class SpringConfiguration implements FeatureConfiguration {
   public static final Codec<SpringConfiguration> f_68123_ = RecordCodecBuilder.<SpringConfiguration>create((p_68139_) -> {
      return p_68139_.group(FluidState.f_76146_.fieldOf("state").forGetter((p_161205_) -> {
         return p_161205_.f_68124_;
      }), Codec.BOOL.fieldOf("requires_block_below").orElse(true).forGetter((p_161203_) -> {
         return p_161203_.f_68125_;
      }), Codec.INT.fieldOf("rock_count").orElse(4).forGetter((p_161201_) -> {
         return p_161201_.f_68126_;
      }), Codec.INT.fieldOf("hole_count").orElse(1).forGetter((p_161199_) -> {
         return p_161199_.f_68127_;
      }), Registry.f_122824_.listOf().fieldOf("valid_blocks").xmap(ImmutableSet::copyOf, ImmutableList::copyOf).forGetter((p_161197_) -> {
         return (ImmutableSet<Block>)p_161197_.f_68128_;
      })).apply(p_68139_, SpringConfiguration::new);
   });
   public final FluidState f_68124_;
   public final boolean f_68125_;
   public final int f_68126_;
   public final int f_68127_;
   public final Set<Block> f_68128_;

   public SpringConfiguration(FluidState p_68131_, boolean p_68132_, int p_68133_, int p_68134_, Set<Block> p_68135_) {
      this.f_68124_ = p_68131_;
      this.f_68125_ = p_68132_;
      this.f_68126_ = p_68133_;
      this.f_68127_ = p_68134_;
      this.f_68128_ = p_68135_;
   }
}