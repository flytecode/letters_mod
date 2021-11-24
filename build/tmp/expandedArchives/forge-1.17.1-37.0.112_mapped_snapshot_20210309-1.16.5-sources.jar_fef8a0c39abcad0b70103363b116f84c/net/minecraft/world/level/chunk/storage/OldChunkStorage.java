package net.minecraft.world.level.chunk.storage;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.OldDataLayer;

public class OldChunkStorage {
   private static final int f_156596_ = 7;
   private static final LevelHeightAccessor f_156597_ = new LevelHeightAccessor() {
      public int m_141937_() {
         return 0;
      }

      public int m_141928_() {
         return 128;
      }
   };

   public static OldChunkStorage.OldLevelChunk m_63591_(CompoundTag p_63592_) {
      int i = p_63592_.m_128451_("xPos");
      int j = p_63592_.m_128451_("zPos");
      OldChunkStorage.OldLevelChunk oldchunkstorage$oldlevelchunk = new OldChunkStorage.OldLevelChunk(i, j);
      oldchunkstorage$oldlevelchunk.f_63599_ = p_63592_.m_128463_("Blocks");
      oldchunkstorage$oldlevelchunk.f_63598_ = new OldDataLayer(p_63592_.m_128463_("Data"), 7);
      oldchunkstorage$oldlevelchunk.f_63597_ = new OldDataLayer(p_63592_.m_128463_("SkyLight"), 7);
      oldchunkstorage$oldlevelchunk.f_63596_ = new OldDataLayer(p_63592_.m_128463_("BlockLight"), 7);
      oldchunkstorage$oldlevelchunk.f_63595_ = p_63592_.m_128463_("HeightMap");
      oldchunkstorage$oldlevelchunk.f_63594_ = p_63592_.m_128471_("TerrainPopulated");
      oldchunkstorage$oldlevelchunk.f_63600_ = p_63592_.m_128437_("Entities", 10);
      oldchunkstorage$oldlevelchunk.f_63601_ = p_63592_.m_128437_("TileEntities", 10);
      oldchunkstorage$oldlevelchunk.f_63602_ = p_63592_.m_128437_("TileTicks", 10);

      try {
         oldchunkstorage$oldlevelchunk.f_63593_ = p_63592_.m_128454_("LastUpdate");
      } catch (ClassCastException classcastexception) {
         oldchunkstorage$oldlevelchunk.f_63593_ = (long)p_63592_.m_128451_("LastUpdate");
      }

      return oldchunkstorage$oldlevelchunk;
   }

   public static void m_63586_(RegistryAccess.RegistryHolder p_63587_, OldChunkStorage.OldLevelChunk p_63588_, CompoundTag p_63589_, BiomeSource p_63590_) {
      p_63589_.m_128405_("xPos", p_63588_.f_63603_);
      p_63589_.m_128405_("zPos", p_63588_.f_63604_);
      p_63589_.m_128356_("LastUpdate", p_63588_.f_63593_);
      int[] aint = new int[p_63588_.f_63595_.length];

      for(int i = 0; i < p_63588_.f_63595_.length; ++i) {
         aint[i] = p_63588_.f_63595_[i];
      }

      p_63589_.m_128385_("HeightMap", aint);
      p_63589_.m_128379_("TerrainPopulated", p_63588_.f_63594_);
      ListTag listtag = new ListTag();

      for(int j = 0; j < 8; ++j) {
         boolean flag = true;

         for(int k = 0; k < 16 && flag; ++k) {
            for(int l = 0; l < 16 && flag; ++l) {
               for(int i1 = 0; i1 < 16; ++i1) {
                  int j1 = k << 11 | i1 << 7 | l + (j << 4);
                  int k1 = p_63588_.f_63599_[j1];
                  if (k1 != 0) {
                     flag = false;
                     break;
                  }
               }
            }
         }

         if (!flag) {
            byte[] abyte = new byte[4096];
            DataLayer datalayer = new DataLayer();
            DataLayer datalayer1 = new DataLayer();
            DataLayer datalayer2 = new DataLayer();

            for(int l2 = 0; l2 < 16; ++l2) {
               for(int l1 = 0; l1 < 16; ++l1) {
                  for(int i2 = 0; i2 < 16; ++i2) {
                     int j2 = l2 << 11 | i2 << 7 | l1 + (j << 4);
                     int k2 = p_63588_.f_63599_[j2];
                     abyte[l1 << 8 | i2 << 4 | l2] = (byte)(k2 & 255);
                     datalayer.m_62564_(l2, l1, i2, p_63588_.f_63598_.m_63056_(l2, l1 + (j << 4), i2));
                     datalayer1.m_62564_(l2, l1, i2, p_63588_.f_63597_.m_63056_(l2, l1 + (j << 4), i2));
                     datalayer2.m_62564_(l2, l1, i2, p_63588_.f_63596_.m_63056_(l2, l1 + (j << 4), i2));
                  }
               }
            }

            CompoundTag compoundtag = new CompoundTag();
            compoundtag.m_128344_("Y", (byte)(j & 255));
            compoundtag.m_128382_("Blocks", abyte);
            compoundtag.m_128382_("Data", datalayer.m_7877_());
            compoundtag.m_128382_("SkyLight", datalayer1.m_7877_());
            compoundtag.m_128382_("BlockLight", datalayer2.m_7877_());
            listtag.add(compoundtag);
         }
      }

      p_63589_.m_128365_("Sections", listtag);
      p_63589_.m_128385_("Biomes", (new ChunkBiomeContainer(p_63587_.m_175515_(Registry.f_122885_), f_156597_, new ChunkPos(p_63588_.f_63603_, p_63588_.f_63604_), p_63590_)).m_62131_());
      p_63589_.m_128365_("Entities", p_63588_.f_63600_);
      p_63589_.m_128365_("TileEntities", p_63588_.f_63601_);
      if (p_63588_.f_63602_ != null) {
         p_63589_.m_128365_("TileTicks", p_63588_.f_63602_);
      }

      p_63589_.m_128379_("convertedFromAlphaFormat", true);
   }

   public static class OldLevelChunk {
      public long f_63593_;
      public boolean f_63594_;
      public byte[] f_63595_;
      public OldDataLayer f_63596_;
      public OldDataLayer f_63597_;
      public OldDataLayer f_63598_;
      public byte[] f_63599_;
      public ListTag f_63600_;
      public ListTag f_63601_;
      public ListTag f_63602_;
      public final int f_63603_;
      public final int f_63604_;

      public OldLevelChunk(int p_63606_, int p_63607_) {
         this.f_63603_ = p_63606_;
         this.f_63604_ = p_63607_;
      }
   }
}