package net.minecraft.world.level.levelgen;

import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class DepthBasedReplacingBaseStoneSource implements BaseStoneSource {
   private static final int f_158253_ = -8;
   private static final int f_158254_ = 0;
   private final WorldgenRandom f_158255_;
   private final long f_158256_;
   private final BlockState f_158257_;
   private final BlockState f_158258_;
   private final NoiseGeneratorSettings f_158259_;

   public DepthBasedReplacingBaseStoneSource(long p_158261_, BlockState p_158262_, BlockState p_158263_, NoiseGeneratorSettings p_158264_) {
      this.f_158255_ = new WorldgenRandom(p_158261_);
      this.f_158256_ = p_158261_;
      this.f_158257_ = p_158262_;
      this.f_158258_ = p_158263_;
      this.f_158259_ = p_158264_;
   }

   public BlockState m_142722_(int p_158266_, int p_158267_, int p_158268_) {
      if (!this.f_158259_.m_158569_()) {
         return this.f_158257_;
      } else if (p_158267_ < -8) {
         return this.f_158258_;
      } else if (p_158267_ > 0) {
         return this.f_158257_;
      } else {
         double d0 = Mth.m_144914_((double)p_158267_, -8.0D, 0.0D, 1.0D, 0.0D);
         this.f_158255_.m_158961_(this.f_158256_, p_158266_, p_158267_, p_158268_);
         return (double)this.f_158255_.nextFloat() < d0 ? this.f_158258_ : this.f_158257_;
      }
   }
}