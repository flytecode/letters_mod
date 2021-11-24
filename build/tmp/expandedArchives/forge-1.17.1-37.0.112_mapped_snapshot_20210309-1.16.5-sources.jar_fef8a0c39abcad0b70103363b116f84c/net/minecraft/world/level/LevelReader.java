package net.minecraft.world.level;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

public interface LevelReader extends BlockAndTintGetter, CollisionGetter, BiomeManager.NoiseBiomeSource {
   @Nullable
   ChunkAccess m_6522_(int p_46823_, int p_46824_, ChunkStatus p_46825_, boolean p_46826_);

   @Deprecated
   boolean m_7232_(int p_46838_, int p_46839_);

   int m_6924_(Heightmap.Types p_46827_, int p_46828_, int p_46829_);

   int m_7445_();

   BiomeManager m_7062_();

   default Biome m_46857_(BlockPos p_46858_) {
      return this.m_7062_().m_47881_(p_46858_);
   }

   default Stream<BlockState> m_46847_(AABB p_46848_) {
      int i = Mth.m_14107_(p_46848_.f_82288_);
      int j = Mth.m_14107_(p_46848_.f_82291_);
      int k = Mth.m_14107_(p_46848_.f_82289_);
      int l = Mth.m_14107_(p_46848_.f_82292_);
      int i1 = Mth.m_14107_(p_46848_.f_82290_);
      int j1 = Mth.m_14107_(p_46848_.f_82293_);
      return this.m_46812_(i, k, i1, j, l, j1) ? this.m_45556_(p_46848_) : Stream.empty();
   }

   default int m_6171_(BlockPos p_46836_, ColorResolver p_46837_) {
      return p_46837_.m_130045_(this.m_46857_(p_46836_), (double)p_46836_.m_123341_(), (double)p_46836_.m_123343_());
   }

   default Biome m_7158_(int p_46841_, int p_46842_, int p_46843_) {
      ChunkAccess chunkaccess = this.m_6522_(QuartPos.m_175406_(p_46841_), QuartPos.m_175406_(p_46843_), ChunkStatus.f_62317_, false);
      return chunkaccess != null && chunkaccess.m_6221_() != null ? chunkaccess.m_6221_().m_7158_(p_46841_, p_46842_, p_46843_) : this.m_6159_(p_46841_, p_46842_, p_46843_);
   }

   Biome m_6159_(int p_46809_, int p_46810_, int p_46811_);

   boolean m_5776_();

   @Deprecated
   int m_5736_();

   DimensionType m_6042_();

   default int m_141937_() {
      return this.m_6042_().m_156732_();
   }

   default int m_141928_() {
      return this.m_6042_().m_156733_();
   }

   default BlockPos m_5452_(Heightmap.Types p_46830_, BlockPos p_46831_) {
      return new BlockPos(p_46831_.m_123341_(), this.m_6924_(p_46830_, p_46831_.m_123341_(), p_46831_.m_123343_()), p_46831_.m_123343_());
   }

   default boolean m_46859_(BlockPos p_46860_) {
      return this.m_8055_(p_46860_).m_60795_();
   }

   default boolean m_46861_(BlockPos p_46862_) {
      if (p_46862_.m_123342_() >= this.m_5736_()) {
         return this.m_45527_(p_46862_);
      } else {
         BlockPos blockpos = new BlockPos(p_46862_.m_123341_(), this.m_5736_(), p_46862_.m_123343_());
         if (!this.m_45527_(blockpos)) {
            return false;
         } else {
            for(BlockPos blockpos1 = blockpos.m_7495_(); blockpos1.m_123342_() > p_46862_.m_123342_(); blockpos1 = blockpos1.m_7495_()) {
               BlockState blockstate = this.m_8055_(blockpos1);
               if (blockstate.m_60739_(this, blockpos1) > 0 && !blockstate.m_60767_().m_76332_()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Deprecated
   default float m_46863_(BlockPos p_46864_) {
      return this.m_6042_().m_63902_(this.m_46803_(p_46864_));
   }

   default int m_46852_(BlockPos p_46853_, Direction p_46854_) {
      return this.m_8055_(p_46853_).m_60775_(this, p_46853_, p_46854_);
   }

   default ChunkAccess m_46865_(BlockPos p_46866_) {
      return this.m_6325_(SectionPos.m_123171_(p_46866_.m_123341_()), SectionPos.m_123171_(p_46866_.m_123343_()));
   }

   default ChunkAccess m_6325_(int p_46807_, int p_46808_) {
      return this.m_6522_(p_46807_, p_46808_, ChunkStatus.f_62326_, true);
   }

   default ChunkAccess m_46819_(int p_46820_, int p_46821_, ChunkStatus p_46822_) {
      return this.m_6522_(p_46820_, p_46821_, p_46822_, true);
   }

   @Nullable
   default BlockGetter m_7925_(int p_46845_, int p_46846_) {
      return this.m_6522_(p_46845_, p_46846_, ChunkStatus.f_62314_, false);
   }

   default boolean m_46801_(BlockPos p_46802_) {
      return this.m_6425_(p_46802_).m_76153_(FluidTags.f_13131_);
   }

   default boolean m_46855_(AABB p_46856_) {
      int i = Mth.m_14107_(p_46856_.f_82288_);
      int j = Mth.m_14165_(p_46856_.f_82291_);
      int k = Mth.m_14107_(p_46856_.f_82289_);
      int l = Mth.m_14165_(p_46856_.f_82292_);
      int i1 = Mth.m_14107_(p_46856_.f_82290_);
      int j1 = Mth.m_14165_(p_46856_.f_82293_);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               BlockState blockstate = this.m_8055_(blockpos$mutableblockpos.m_122178_(k1, l1, i2));
               if (!blockstate.m_60819_().m_76178_()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   default int m_46803_(BlockPos p_46804_) {
      return this.m_46849_(p_46804_, this.m_7445_());
   }

   default int m_46849_(BlockPos p_46850_, int p_46851_) {
      return p_46850_.m_123341_() >= -30000000 && p_46850_.m_123343_() >= -30000000 && p_46850_.m_123341_() < 30000000 && p_46850_.m_123343_() < 30000000 ? this.m_45524_(p_46850_, p_46851_) : 15;
   }

   @Deprecated
   default boolean m_151577_(int p_151578_, int p_151579_) {
      return this.m_7232_(SectionPos.m_123171_(p_151578_), SectionPos.m_123171_(p_151579_));
   }

   @Deprecated
   default boolean m_46805_(BlockPos p_46806_) {
      return this.m_151577_(p_46806_.m_123341_(), p_46806_.m_123343_());
   }

   default boolean isAreaLoaded(BlockPos center, int range) {
      return this.m_46832_(center.m_142082_(-range, -range, -range), center.m_142082_(range, range, range));
   }

   @Deprecated
   default boolean m_46832_(BlockPos p_46833_, BlockPos p_46834_) {
      return this.m_46812_(p_46833_.m_123341_(), p_46833_.m_123342_(), p_46833_.m_123343_(), p_46834_.m_123341_(), p_46834_.m_123342_(), p_46834_.m_123343_());
   }

   @Deprecated
   default boolean m_46812_(int p_46813_, int p_46814_, int p_46815_, int p_46816_, int p_46817_, int p_46818_) {
      return p_46817_ >= this.m_141937_() && p_46814_ < this.m_151558_() ? this.m_151572_(p_46813_, p_46815_, p_46816_, p_46818_) : false;
   }

   @Deprecated
   default boolean m_151572_(int p_151573_, int p_151574_, int p_151575_, int p_151576_) {
      int i = SectionPos.m_123171_(p_151573_);
      int j = SectionPos.m_123171_(p_151575_);
      int k = SectionPos.m_123171_(p_151574_);
      int l = SectionPos.m_123171_(p_151576_);

      for(int i1 = i; i1 <= j; ++i1) {
         for(int j1 = k; j1 <= l; ++j1) {
            if (!this.m_7232_(i1, j1)) {
               return false;
            }
         }
      }

      return true;
   }
}
