package net.minecraft.server.level;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

public class PlayerRespawnLogic {
   @Nullable
   protected static BlockPos m_8264_(ServerLevel p_8265_, int p_8266_, int p_8267_, boolean p_8268_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_8266_, p_8265_.m_141937_(), p_8267_);
      Biome biome = p_8265_.m_46857_(blockpos$mutableblockpos);
      boolean flag = p_8265_.m_6042_().m_63946_();
      BlockState blockstate = biome.m_47536_().m_47824_().m_6743_();
      if (p_8268_ && !blockstate.m_60620_(BlockTags.f_13048_)) {
         return null;
      } else {
         LevelChunk levelchunk = p_8265_.m_6325_(SectionPos.m_123171_(p_8266_), SectionPos.m_123171_(p_8267_));
         int i = flag ? p_8265_.m_7726_().m_8481_().m_142051_(p_8265_) : levelchunk.m_5885_(Heightmap.Types.MOTION_BLOCKING, p_8266_ & 15, p_8267_ & 15);
         if (i < p_8265_.m_141937_()) {
            return null;
         } else {
            int j = levelchunk.m_5885_(Heightmap.Types.WORLD_SURFACE, p_8266_ & 15, p_8267_ & 15);
            if (j <= i && j > levelchunk.m_5885_(Heightmap.Types.OCEAN_FLOOR, p_8266_ & 15, p_8267_ & 15)) {
               return null;
            } else {
               for(int k = i + 1; k >= p_8265_.m_141937_(); --k) {
                  blockpos$mutableblockpos.m_122178_(p_8266_, k, p_8267_);
                  BlockState blockstate1 = p_8265_.m_8055_(blockpos$mutableblockpos);
                  if (!blockstate1.m_60819_().m_76178_()) {
                     break;
                  }

                  if (blockstate1.equals(blockstate)) {
                     return blockpos$mutableblockpos.m_7494_().m_7949_();
                  }
               }

               return null;
            }
         }
      }
   }

   @Nullable
   public static BlockPos m_8269_(ServerLevel p_8270_, ChunkPos p_8271_, boolean p_8272_) {
      for(int i = p_8271_.m_45604_(); i <= p_8271_.m_45608_(); ++i) {
         for(int j = p_8271_.m_45605_(); j <= p_8271_.m_45609_(); ++j) {
            BlockPos blockpos = m_8264_(p_8270_, i, j, p_8272_);
            if (blockpos != null) {
               return blockpos;
            }
         }
      }

      return null;
   }
}