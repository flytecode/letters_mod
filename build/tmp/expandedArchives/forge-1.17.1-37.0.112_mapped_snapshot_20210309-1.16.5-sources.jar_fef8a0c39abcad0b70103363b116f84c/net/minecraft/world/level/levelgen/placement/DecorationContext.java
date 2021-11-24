package net.minecraft.world.level.levelgen.placement;

import java.util.BitSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class DecorationContext extends WorldGenerationContext {
   private final WorldGenLevel f_70581_;

   public DecorationContext(WorldGenLevel p_70584_, ChunkGenerator p_70585_) {
      super(p_70585_, p_70584_);
      this.f_70581_ = p_70584_;
   }

   public int m_70590_(Heightmap.Types p_70591_, int p_70592_, int p_70593_) {
      return this.f_70581_.m_6924_(p_70591_, p_70592_, p_70593_);
   }

   public BitSet m_70587_(ChunkPos p_70588_, GenerationStep.Carving p_70589_) {
      return ((ProtoChunk)this.f_70581_.m_6325_(p_70588_.f_45578_, p_70588_.f_45579_)).m_6547_(p_70589_);
   }

   public BlockState m_70594_(BlockPos p_70595_) {
      return this.f_70581_.m_8055_(p_70595_);
   }

   public int m_162167_() {
      return this.f_70581_.m_141937_();
   }

   public WorldGenLevel m_162168_() {
      return this.f_70581_;
   }
}