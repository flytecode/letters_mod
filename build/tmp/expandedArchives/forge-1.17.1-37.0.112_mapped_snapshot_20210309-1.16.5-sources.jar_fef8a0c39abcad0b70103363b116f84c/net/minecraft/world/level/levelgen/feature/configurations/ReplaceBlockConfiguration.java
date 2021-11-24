package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;

public class ReplaceBlockConfiguration implements FeatureConfiguration {
   public static final Codec<ReplaceBlockConfiguration> f_68023_ = RecordCodecBuilder.create((p_161087_) -> {
      return p_161087_.group(Codec.list(OreConfiguration.TargetBlockState.f_161031_).fieldOf("targets").forGetter((p_161089_) -> {
         return p_161089_.f_161083_;
      })).apply(p_161087_, ReplaceBlockConfiguration::new);
   });
   public final List<OreConfiguration.TargetBlockState> f_161083_;

   public ReplaceBlockConfiguration(BlockState p_68028_, BlockState p_68029_) {
      this(ImmutableList.of(OreConfiguration.m_161021_(new BlockStateMatchTest(p_68028_), p_68029_)));
   }

   public ReplaceBlockConfiguration(List<OreConfiguration.TargetBlockState> p_161085_) {
      this.f_161083_ = p_161085_;
   }
}