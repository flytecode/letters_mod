package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;

public class WeightedStateProvider extends BlockStateProvider {
   public static final Codec<WeightedStateProvider> f_68808_ = SimpleWeightedRandomList.m_146264_(BlockState.f_61039_).comapFlatMap(WeightedStateProvider::m_161597_, (p_161600_) -> {
      return p_161600_.f_68809_;
   }).fieldOf("entries").codec();
   private final SimpleWeightedRandomList<BlockState> f_68809_;

   private static DataResult<WeightedStateProvider> m_161597_(SimpleWeightedRandomList<BlockState> p_161598_) {
      return p_161598_.m_146337_() ? DataResult.error("WeightedStateProvider with no states") : DataResult.success(new WeightedStateProvider(p_161598_));
   }

   public WeightedStateProvider(SimpleWeightedRandomList<BlockState> p_161596_) {
      this.f_68809_ = p_161596_;
   }

   public WeightedStateProvider(SimpleWeightedRandomList.Builder<BlockState> p_161594_) {
      this(p_161594_.m_146270_());
   }

   protected BlockStateProviderType<?> m_5923_() {
      return BlockStateProviderType.f_68753_;
   }

   public BlockState m_7112_(Random p_68823_, BlockPos p_68824_) {
      return this.f_68809_.m_146266_(p_68823_).orElseThrow(IllegalStateException::new);
   }
}