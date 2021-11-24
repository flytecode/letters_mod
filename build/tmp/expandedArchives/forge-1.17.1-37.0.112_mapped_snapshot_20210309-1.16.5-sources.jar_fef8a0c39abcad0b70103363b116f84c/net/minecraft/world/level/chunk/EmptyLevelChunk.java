package net.minecraft.world.level.chunk;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class EmptyLevelChunk extends LevelChunk {
   public EmptyLevelChunk(Level p_62584_, ChunkPos p_62585_) {
      super(p_62584_, p_62585_, new EmptyLevelChunk.EmptyChunkBiomeContainer(p_62584_));
   }

   public BlockState m_8055_(BlockPos p_62625_) {
      return Blocks.f_50626_.m_49966_();
   }

   @Nullable
   public BlockState m_6978_(BlockPos p_62605_, BlockState p_62606_, boolean p_62607_) {
      return null;
   }

   public FluidState m_6425_(BlockPos p_62621_) {
      return Fluids.f_76191_.m_76145_();
   }

   public int m_7146_(BlockPos p_62628_) {
      return 0;
   }

   @Nullable
   public BlockEntity m_5685_(BlockPos p_62609_, LevelChunk.EntityCreationType p_62610_) {
      return null;
   }

   public void m_142170_(BlockEntity p_156346_) {
   }

   public void m_142169_(BlockEntity p_156344_) {
   }

   public void m_8114_(BlockPos p_62623_) {
   }

   public void m_6427_() {
   }

   public boolean m_6430_() {
      return true;
   }

   public boolean m_5566_(int p_62587_, int p_62588_) {
      return true;
   }

   public ChunkHolder.FullChunkStatus m_6708_() {
      return ChunkHolder.FullChunkStatus.BORDER;
   }

   static class EmptyChunkBiomeContainer extends ChunkBiomeContainer {
      private static final Biome[] f_156347_ = new Biome[0];

      public EmptyChunkBiomeContainer(Level p_156350_) {
         super(p_156350_.m_5962_().m_175515_(Registry.f_122885_), p_156350_, f_156347_);
      }

      public int[] m_62131_() {
         throw new UnsupportedOperationException("Can not write biomes of an empty chunk");
      }

      public Biome m_7158_(int p_156353_, int p_156354_, int p_156355_) {
         return Biomes.f_127321_;
      }
   }
}