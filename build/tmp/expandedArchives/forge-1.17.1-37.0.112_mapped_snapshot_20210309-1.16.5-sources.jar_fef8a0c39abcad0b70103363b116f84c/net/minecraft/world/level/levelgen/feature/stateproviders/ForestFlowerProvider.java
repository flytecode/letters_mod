package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ForestFlowerProvider extends BlockStateProvider {
   public static final Codec<ForestFlowerProvider> f_68765_;
   private static final BlockState[] f_68767_ = new BlockState[]{Blocks.f_50111_.m_49966_(), Blocks.f_50112_.m_49966_(), Blocks.f_50114_.m_49966_(), Blocks.f_50115_.m_49966_(), Blocks.f_50116_.m_49966_(), Blocks.f_50117_.m_49966_(), Blocks.f_50118_.m_49966_(), Blocks.f_50119_.m_49966_(), Blocks.f_50120_.m_49966_(), Blocks.f_50121_.m_49966_(), Blocks.f_50071_.m_49966_()};
   public static final ForestFlowerProvider f_68766_ = new ForestFlowerProvider();

   protected BlockStateProviderType<?> m_5923_() {
      return BlockStateProviderType.f_68755_;
   }

   public BlockState m_7112_(Random p_68772_, BlockPos p_68773_) {
      double d0 = Mth.m_14008_((1.0D + Biome.f_47433_.m_75449_((double)p_68773_.m_123341_() / 48.0D, (double)p_68773_.m_123343_() / 48.0D, false)) / 2.0D, 0.0D, 0.9999D);
      return f_68767_[(int)(d0 * (double)f_68767_.length)];
   }

   static {
      f_68765_ = Codec.unit(() -> {
         return f_68766_;
      });
   }
}