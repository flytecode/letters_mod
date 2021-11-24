package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BasaltDeltasSurfaceBuilder extends NetherCappedSurfaceBuilder {
   private static final BlockState f_74751_ = Blocks.f_50137_.m_49966_();
   private static final BlockState f_74752_ = Blocks.f_50730_.m_49966_();
   private static final BlockState f_74753_ = Blocks.f_49994_.m_49966_();
   private static final ImmutableList<BlockState> f_74754_ = ImmutableList.of(f_74751_, f_74752_);
   private static final ImmutableList<BlockState> f_74755_ = ImmutableList.of(f_74751_);

   public BasaltDeltasSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_74758_) {
      super(p_74758_);
   }

   protected ImmutableList<BlockState> m_6920_() {
      return f_74754_;
   }

   protected ImmutableList<BlockState> m_6919_() {
      return f_74755_;
   }

   protected BlockState m_6711_() {
      return f_74753_;
   }
}