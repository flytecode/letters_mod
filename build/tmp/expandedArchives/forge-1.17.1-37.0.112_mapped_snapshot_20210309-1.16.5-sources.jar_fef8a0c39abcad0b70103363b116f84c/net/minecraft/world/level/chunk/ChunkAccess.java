package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Fluid;
import org.apache.logging.log4j.LogManager;

public interface ChunkAccess extends BlockGetter, FeatureAccess {
   default GameEventDispatcher m_142336_(int p_156113_) {
      return GameEventDispatcher.f_157829_;
   }

   @Nullable
   BlockState m_6978_(BlockPos p_62087_, BlockState p_62088_, boolean p_62089_);

   void m_142169_(BlockEntity p_156114_);

   void m_6286_(Entity p_62078_);

   @Nullable
   default LevelChunkSection m_62074_() {
      LevelChunkSection[] alevelchunksection = this.m_7103_();

      for(int i = alevelchunksection.length - 1; i >= 0; --i) {
         LevelChunkSection levelchunksection = alevelchunksection[i];
         if (!LevelChunkSection.m_63000_(levelchunksection)) {
            return levelchunksection;
         }
      }

      return null;
   }

   default int m_62098_() {
      LevelChunkSection levelchunksection = this.m_62074_();
      return levelchunksection == null ? this.m_141937_() : levelchunksection.m_63017_();
   }

   Set<BlockPos> m_5928_();

   LevelChunkSection[] m_7103_();

   default LevelChunkSection m_156115_(int p_156116_) {
      LevelChunkSection[] alevelchunksection = this.m_7103_();
      if (alevelchunksection[p_156116_] == LevelChunk.f_62770_) {
         alevelchunksection[p_156116_] = new LevelChunkSection(this.m_151568_(p_156116_));
      }

      return alevelchunksection[p_156116_];
   }

   Collection<Entry<Heightmap.Types, Heightmap>> m_6890_();

   default void m_6511_(Heightmap.Types p_62083_, long[] p_62084_) {
      this.m_6005_(p_62083_).m_158364_(this, p_62083_, p_62084_);
   }

   Heightmap m_6005_(Heightmap.Types p_62079_);

   int m_5885_(Heightmap.Types p_62080_, int p_62081_, int p_62082_);

   BlockPos m_142241_(Heightmap.Types p_156117_);

   ChunkPos m_7697_();

   Map<StructureFeature<?>, StructureStart<?>> m_6633_();

   void m_8040_(Map<StructureFeature<?>, StructureStart<?>> p_62090_);

   default boolean m_5566_(int p_62075_, int p_62076_) {
      if (p_62075_ < this.m_141937_()) {
         p_62075_ = this.m_141937_();
      }

      if (p_62076_ >= this.m_151558_()) {
         p_62076_ = this.m_151558_() - 1;
      }

      for(int i = p_62075_; i <= p_62076_; i += 16) {
         if (!LevelChunkSection.m_63000_(this.m_7103_()[this.m_151564_(i)])) {
            return false;
         }
      }

      return true;
   }

   @Nullable
   ChunkBiomeContainer m_6221_();

   void m_8092_(boolean p_62094_);

   boolean m_6344_();

   ChunkStatus m_6415_();

   void m_8114_(BlockPos p_62101_);

   default void m_8113_(BlockPos p_62102_) {
      LogManager.getLogger().warn("Trying to mark a block for PostProcessing @ {}, but this operation is not supported.", (Object)p_62102_);
   }

   ShortList[] m_6720_();

   default void m_6561_(short p_62092_, int p_62093_) {
      m_62095_(this.m_6720_(), p_62093_).add(p_62092_);
   }

   default void m_5604_(CompoundTag p_62091_) {
      LogManager.getLogger().warn("Trying to set a BlockEntity, but this operation is not supported.");
   }

   @Nullable
   CompoundTag m_8049_(BlockPos p_62103_);

   @Nullable
   CompoundTag m_8051_(BlockPos p_62104_);

   Stream<BlockPos> m_6267_();

   TickList<Block> m_5782_();

   TickList<Fluid> m_5783_();

   UpgradeData m_7387_();

   void m_6141_(long p_62099_);

   long m_6319_();

   static ShortList m_62095_(ShortList[] p_62096_, int p_62097_) {
      if (p_62096_[p_62097_] == null) {
         p_62096_[p_62097_] = new ShortArrayList();
      }

      return p_62096_[p_62097_];
   }

   boolean m_6332_();

   void m_8094_(boolean p_62100_);

   @Nullable
   default net.minecraft.world.level.LevelAccessor getWorldForge() {
      return null;
   }
}
