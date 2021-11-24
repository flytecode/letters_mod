package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PlainFlowerProvider extends BlockStateProvider {
   public static final Codec<PlainFlowerProvider> f_68775_;
   public static final PlainFlowerProvider f_68776_ = new PlainFlowerProvider();
   private static final BlockState[] f_68777_ = new BlockState[]{Blocks.f_50117_.m_49966_(), Blocks.f_50116_.m_49966_(), Blocks.f_50119_.m_49966_(), Blocks.f_50118_.m_49966_()};
   private static final BlockState[] f_68778_ = new BlockState[]{Blocks.f_50112_.m_49966_(), Blocks.f_50115_.m_49966_(), Blocks.f_50120_.m_49966_(), Blocks.f_50121_.m_49966_()};

   protected BlockStateProviderType<?> m_5923_() {
      return BlockStateProviderType.f_68754_;
   }

   public BlockState m_7112_(Random p_68783_, BlockPos p_68784_) {
      double d0 = Biome.f_47433_.m_75449_((double)p_68784_.m_123341_() / 200.0D, (double)p_68784_.m_123343_() / 200.0D, false);
      if (d0 < -0.8D) {
         return Util.m_137545_(f_68777_, p_68783_);
      } else {
         return p_68783_.nextInt(3) > 0 ? Util.m_137545_(f_68778_, p_68783_) : Blocks.f_50111_.m_49966_();
      }
   }

   static {
      f_68775_ = Codec.unit(() -> {
         return f_68776_;
      });
   }
}