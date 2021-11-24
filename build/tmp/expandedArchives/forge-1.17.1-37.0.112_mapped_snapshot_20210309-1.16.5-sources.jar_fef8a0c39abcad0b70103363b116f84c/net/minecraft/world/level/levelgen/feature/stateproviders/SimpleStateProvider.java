package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleStateProvider extends BlockStateProvider {
   public static final Codec<SimpleStateProvider> f_68797_ = BlockState.f_61039_.fieldOf("state").xmap(SimpleStateProvider::new, (p_68804_) -> {
      return p_68804_.f_68798_;
   }).codec();
   private final BlockState f_68798_;

   public SimpleStateProvider(BlockState p_68801_) {
      this.f_68798_ = p_68801_;
   }

   protected BlockStateProviderType<?> m_5923_() {
      return BlockStateProviderType.f_68752_;
   }

   public BlockState m_7112_(Random p_68806_, BlockPos p_68807_) {
      return this.f_68798_;
   }
}