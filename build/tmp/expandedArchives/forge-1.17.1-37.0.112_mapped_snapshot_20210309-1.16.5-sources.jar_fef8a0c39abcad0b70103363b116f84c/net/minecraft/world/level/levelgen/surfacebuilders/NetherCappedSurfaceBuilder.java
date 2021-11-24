package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public abstract class NetherCappedSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   private long f_74984_;
   private ImmutableMap<BlockState, PerlinNoise> f_74985_ = ImmutableMap.of();
   private ImmutableMap<BlockState, PerlinNoise> f_74986_ = ImmutableMap.of();
   private PerlinNoise f_74987_;

   public NetherCappedSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_74989_) {
      super(p_74989_);
   }

   public void m_142263_(Random p_164074_, ChunkAccess p_164075_, Biome p_164076_, int p_164077_, int p_164078_, int p_164079_, double p_164080_, BlockState p_164081_, BlockState p_164082_, int p_164083_, int p_164084_, long p_164085_, SurfaceBuilderBaseConfiguration p_164086_) {
      int i = p_164083_ + 1;
      int j = p_164077_ & 15;
      int k = p_164078_ & 15;
      int l = (int)(p_164080_ / 3.0D + 3.0D + p_164074_.nextDouble() * 0.25D);
      int i1 = (int)(p_164080_ / 3.0D + 3.0D + p_164074_.nextDouble() * 0.25D);
      double d0 = 0.03125D;
      boolean flag = this.f_74987_.m_75408_((double)p_164077_ * 0.03125D, 109.0D, (double)p_164078_ * 0.03125D) * 75.0D + p_164074_.nextDouble() > 0.0D;
      BlockState blockstate = this.f_74986_.entrySet().stream().max(Comparator.comparing((p_75030_) -> {
         return p_75030_.getValue().m_75408_((double)p_164077_, (double)p_164083_, (double)p_164078_);
      })).get().getKey();
      BlockState blockstate1 = this.f_74985_.entrySet().stream().max(Comparator.comparing((p_74994_) -> {
         return p_74994_.getValue().m_75408_((double)p_164077_, (double)p_164083_, (double)p_164078_);
      })).get().getKey();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockState blockstate2 = p_164075_.m_8055_(blockpos$mutableblockpos.m_122178_(j, 128, k));

      for(int j1 = 127; j1 >= p_164084_; --j1) {
         blockpos$mutableblockpos.m_122178_(j, j1, k);
         BlockState blockstate3 = p_164075_.m_8055_(blockpos$mutableblockpos);
         if (blockstate2.m_60713_(p_164081_.m_60734_()) && (blockstate3.m_60795_() || blockstate3 == p_164082_)) {
            for(int k1 = 0; k1 < l; ++k1) {
               blockpos$mutableblockpos.m_122173_(Direction.UP);
               if (!p_164075_.m_8055_(blockpos$mutableblockpos).m_60713_(p_164081_.m_60734_())) {
                  break;
               }

               p_164075_.m_6978_(blockpos$mutableblockpos, blockstate, false);
            }

            blockpos$mutableblockpos.m_122178_(j, j1, k);
         }

         if ((blockstate2.m_60795_() || blockstate2 == p_164082_) && blockstate3.m_60713_(p_164081_.m_60734_())) {
            for(int l1 = 0; l1 < i1 && p_164075_.m_8055_(blockpos$mutableblockpos).m_60713_(p_164081_.m_60734_()); ++l1) {
               if (flag && j1 >= i - 4 && j1 <= i + 1) {
                  p_164075_.m_6978_(blockpos$mutableblockpos, this.m_6711_(), false);
               } else {
                  p_164075_.m_6978_(blockpos$mutableblockpos, blockstate1, false);
               }

               blockpos$mutableblockpos.m_122173_(Direction.DOWN);
            }
         }

         blockstate2 = blockstate3;
      }

   }

   public void m_6190_(long p_74996_) {
      if (this.f_74984_ != p_74996_ || this.f_74987_ == null || this.f_74985_.isEmpty() || this.f_74986_.isEmpty()) {
         this.f_74985_ = m_74997_(this.m_6920_(), p_74996_);
         this.f_74986_ = m_74997_(this.m_6919_(), p_74996_ + (long)this.f_74985_.size());
         this.f_74987_ = new PerlinNoise(new WorldgenRandom(p_74996_ + (long)this.f_74985_.size() + (long)this.f_74986_.size()), ImmutableList.of(0));
      }

      this.f_74984_ = p_74996_;
   }

   private static ImmutableMap<BlockState, PerlinNoise> m_74997_(ImmutableList<BlockState> p_74998_, long p_74999_) {
      Builder<BlockState, PerlinNoise> builder = new Builder<>();

      for(BlockState blockstate : p_74998_) {
         builder.put(blockstate, new PerlinNoise(new WorldgenRandom(p_74999_), ImmutableList.of(-4)));
         ++p_74999_;
      }

      return builder.build();
   }

   protected abstract ImmutableList<BlockState> m_6920_();

   protected abstract ImmutableList<BlockState> m_6919_();

   protected abstract BlockState m_6711_();
}