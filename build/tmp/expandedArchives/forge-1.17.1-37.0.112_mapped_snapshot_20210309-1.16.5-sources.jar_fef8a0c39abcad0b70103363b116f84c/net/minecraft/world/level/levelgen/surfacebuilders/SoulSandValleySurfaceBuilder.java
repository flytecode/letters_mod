package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SoulSandValleySurfaceBuilder extends NetherCappedSurfaceBuilder {
   private static final BlockState f_75157_ = Blocks.f_50135_.m_49966_();
   private static final BlockState f_75158_ = Blocks.f_50136_.m_49966_();
   private static final BlockState f_75159_ = Blocks.f_49994_.m_49966_();
   private static final ImmutableList<BlockState> f_75160_ = ImmutableList.of(f_75157_, f_75158_);

   public SoulSandValleySurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_75163_) {
      super(p_75163_);
   }

   protected ImmutableList<BlockState> m_6920_() {
      return f_75160_;
   }

   protected ImmutableList<BlockState> m_6919_() {
      return f_75160_;
   }

   protected BlockState m_6711_() {
      return f_75159_;
   }
}