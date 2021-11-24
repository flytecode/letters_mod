package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;

public class BlockLightSectionStorage extends LayerLightSectionStorage<BlockLightSectionStorage.BlockDataLayerStorageMap> {
   protected BlockLightSectionStorage(LightChunkGetter p_75511_) {
      super(LightLayer.BLOCK, p_75511_, new BlockLightSectionStorage.BlockDataLayerStorageMap(new Long2ObjectOpenHashMap<>()));
   }

   protected int m_6181_(long p_75513_) {
      long i = SectionPos.m_123235_(p_75513_);
      DataLayer datalayer = this.m_75758_(i, false);
      return datalayer == null ? 0 : datalayer.m_62560_(SectionPos.m_123207_(BlockPos.m_121983_(p_75513_)), SectionPos.m_123207_(BlockPos.m_122008_(p_75513_)), SectionPos.m_123207_(BlockPos.m_122015_(p_75513_)));
   }

   protected static final class BlockDataLayerStorageMap extends DataLayerStorageMap<BlockLightSectionStorage.BlockDataLayerStorageMap> {
      public BlockDataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> p_75515_) {
         super(p_75515_);
      }

      public BlockLightSectionStorage.BlockDataLayerStorageMap m_5972_() {
         return new BlockLightSectionStorage.BlockDataLayerStorageMap(this.f_75518_.clone());
      }
   }
}