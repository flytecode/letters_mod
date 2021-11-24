package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BulkSectionAccess implements AutoCloseable {
   private final LevelAccessor f_156098_;
   private final Long2ObjectMap<LevelChunkSection> f_156099_ = new Long2ObjectOpenHashMap<>();
   @Nullable
   private LevelChunkSection f_156100_;
   private long f_156101_;

   public BulkSectionAccess(LevelAccessor p_156103_) {
      this.f_156098_ = p_156103_;
   }

   @Nullable
   public LevelChunkSection m_156104_(BlockPos p_156105_) {
      int i = this.f_156098_.m_151564_(p_156105_.m_123342_());
      if (i >= 0 && i < this.f_156098_.m_151559_()) {
         long j = SectionPos.m_175568_(p_156105_);
         if (this.f_156100_ == null || this.f_156101_ != j) {
            this.f_156100_ = this.f_156099_.computeIfAbsent(j, (p_156109_) -> {
               ChunkAccess chunkaccess = this.f_156098_.m_6325_(SectionPos.m_123171_(p_156105_.m_123341_()), SectionPos.m_123171_(p_156105_.m_123343_()));
               LevelChunkSection levelchunksection = chunkaccess.m_156115_(i);
               levelchunksection.m_62981_();
               return levelchunksection;
            });
            this.f_156101_ = j;
         }

         return this.f_156100_;
      } else {
         return LevelChunk.f_62770_;
      }
   }

   public BlockState m_156110_(BlockPos p_156111_) {
      LevelChunkSection levelchunksection = this.m_156104_(p_156111_);
      if (levelchunksection == LevelChunk.f_62770_) {
         return Blocks.f_50016_.m_49966_();
      } else {
         int i = SectionPos.m_123207_(p_156111_.m_123341_());
         int j = SectionPos.m_123207_(p_156111_.m_123342_());
         int k = SectionPos.m_123207_(p_156111_.m_123343_());
         return levelchunksection.m_62982_(i, j, k);
      }
   }

   public void close() {
      for(LevelChunkSection levelchunksection : this.f_156099_.values()) {
         levelchunksection.m_63006_();
      }

   }
}