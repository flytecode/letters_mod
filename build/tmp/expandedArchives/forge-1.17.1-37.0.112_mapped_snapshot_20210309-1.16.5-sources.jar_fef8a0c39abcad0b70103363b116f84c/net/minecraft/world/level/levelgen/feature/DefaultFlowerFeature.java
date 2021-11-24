package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class DefaultFlowerFeature extends AbstractFlowerFeature<RandomPatchConfiguration> {
   public DefaultFlowerFeature(Codec<RandomPatchConfiguration> p_65517_) {
      super(p_65517_);
   }

   public boolean m_7007_(LevelAccessor p_65523_, BlockPos p_65524_, RandomPatchConfiguration p_65525_) {
      return !p_65525_.f_67906_.contains(p_65523_.m_8055_(p_65524_));
   }

   public int m_6340_(RandomPatchConfiguration p_65529_) {
      return p_65529_.f_67907_;
   }

   public BlockPos m_5543_(Random p_65535_, BlockPos p_65536_, RandomPatchConfiguration p_65537_) {
      return p_65536_.m_142082_(p_65535_.nextInt(p_65537_.f_67908_) - p_65535_.nextInt(p_65537_.f_67908_), p_65535_.nextInt(p_65537_.f_67909_) - p_65535_.nextInt(p_65537_.f_67909_), p_65535_.nextInt(p_65537_.f_67910_) - p_65535_.nextInt(p_65537_.f_67910_));
   }

   public BlockState m_7973_(Random p_65543_, BlockPos p_65544_, RandomPatchConfiguration p_65545_) {
      return p_65545_.f_67903_.m_7112_(p_65543_, p_65544_);
   }
}