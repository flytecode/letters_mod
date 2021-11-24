package net.minecraft.world.level.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.level.block.state.BlockState;

public interface WeatheringCopper extends ChangeOverTimeBlock<WeatheringCopper.WeatherState> {
   Supplier<BiMap<Block, Block>> f_154886_ = Suppliers.memoize(() -> {
      return ImmutableBiMap.<Block, Block>builder().put(Blocks.f_152504_, Blocks.f_152503_).put(Blocks.f_152503_, Blocks.f_152502_).put(Blocks.f_152502_, Blocks.f_152501_).put(Blocks.f_152510_, Blocks.f_152509_).put(Blocks.f_152509_, Blocks.f_152508_).put(Blocks.f_152508_, Blocks.f_152507_).put(Blocks.f_152570_, Blocks.f_152569_).put(Blocks.f_152569_, Blocks.f_152568_).put(Blocks.f_152568_, Blocks.f_152567_).put(Blocks.f_152566_, Blocks.f_152565_).put(Blocks.f_152565_, Blocks.f_152564_).put(Blocks.f_152564_, Blocks.f_152563_).build();
   });
   Supplier<BiMap<Block, Block>> f_154887_ = Suppliers.memoize(() -> {
      return f_154886_.get().inverse();
   });

   static Optional<Block> m_154890_(Block p_154891_) {
      return Optional.ofNullable(f_154887_.get().get(p_154891_));
   }

   static Block m_154897_(Block p_154898_) {
      Block block = p_154898_;

      for(Block block1 = f_154887_.get().get(p_154898_); block1 != null; block1 = f_154887_.get().get(block1)) {
         block = block1;
      }

      return block;
   }

   static Optional<BlockState> m_154899_(BlockState p_154900_) {
      return m_154890_(p_154900_.m_60734_()).map((p_154903_) -> {
         return p_154903_.m_152465_(p_154900_);
      });
   }

   static Optional<Block> m_154904_(Block p_154905_) {
      return Optional.ofNullable(f_154886_.get().get(p_154905_));
   }

   static BlockState m_154906_(BlockState p_154907_) {
      return m_154897_(p_154907_.m_60734_()).m_152465_(p_154907_);
   }

   default Optional<BlockState> m_142123_(BlockState p_154893_) {
      return m_154904_(p_154893_.m_60734_()).map((p_154896_) -> {
         return p_154896_.m_152465_(p_154893_);
      });
   }

   default float m_142377_() {
      return this.m_142297_() == WeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
   }

   public static enum WeatherState {
      UNAFFECTED,
      EXPOSED,
      WEATHERED,
      OXIDIZED;
   }
}