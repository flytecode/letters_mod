package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VoidStartPlatformFeature extends Feature<NoneFeatureConfiguration> {
   private static final BlockPos f_160629_ = new BlockPos(8, 3, 8);
   private static final ChunkPos f_67351_ = new ChunkPos(f_160629_);
   private static final int f_160630_ = 16;
   private static final int f_160631_ = 1;

   public VoidStartPlatformFeature(Codec<NoneFeatureConfiguration> p_67354_) {
      super(p_67354_);
   }

   private static int m_67355_(int p_67356_, int p_67357_, int p_67358_, int p_67359_) {
      return Math.max(Math.abs(p_67356_ - p_67358_), Math.abs(p_67357_ - p_67359_));
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160633_) {
      WorldGenLevel worldgenlevel = p_160633_.m_159774_();
      ChunkPos chunkpos = new ChunkPos(p_160633_.m_159777_());
      if (m_67355_(chunkpos.f_45578_, chunkpos.f_45579_, f_67351_.f_45578_, f_67351_.f_45579_) > 1) {
         return true;
      } else {
         BlockPos blockpos = f_160629_.m_175288_(p_160633_.m_159777_().m_123342_() + f_160629_.m_123342_());
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int i = chunkpos.m_45605_(); i <= chunkpos.m_45609_(); ++i) {
            for(int j = chunkpos.m_45604_(); j <= chunkpos.m_45608_(); ++j) {
               if (m_67355_(blockpos.m_123341_(), blockpos.m_123343_(), j, i) <= 16) {
                  blockpos$mutableblockpos.m_122178_(j, blockpos.m_123342_(), i);
                  if (blockpos$mutableblockpos.equals(blockpos)) {
                     worldgenlevel.m_7731_(blockpos$mutableblockpos, Blocks.f_50652_.m_49966_(), 2);
                  } else {
                     worldgenlevel.m_7731_(blockpos$mutableblockpos, Blocks.f_50069_.m_49966_(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}