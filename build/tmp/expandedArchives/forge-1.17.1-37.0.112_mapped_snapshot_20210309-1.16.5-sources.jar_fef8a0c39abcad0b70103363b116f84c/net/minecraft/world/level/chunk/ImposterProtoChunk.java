package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.BitSet;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class ImposterProtoChunk extends ProtoChunk {
   private final LevelChunk f_62685_;

   public ImposterProtoChunk(LevelChunk p_62687_) {
      super(p_62687_.m_7697_(), UpgradeData.f_63320_, p_62687_);
      this.f_62685_ = p_62687_;
   }

   @Nullable
   public BlockEntity m_7702_(BlockPos p_62744_) {
      return this.f_62685_.m_7702_(p_62744_);
   }

   @Nullable
   public BlockState m_8055_(BlockPos p_62749_) {
      return this.f_62685_.m_8055_(p_62749_);
   }

   public FluidState m_6425_(BlockPos p_62736_) {
      return this.f_62685_.m_6425_(p_62736_);
   }

   public int m_7469_() {
      return this.f_62685_.m_7469_();
   }

   @Nullable
   public BlockState m_6978_(BlockPos p_62722_, BlockState p_62723_, boolean p_62724_) {
      return null;
   }

   public void m_142169_(BlockEntity p_156358_) {
   }

   public void m_6286_(Entity p_62692_) {
   }

   public void m_7150_(ChunkStatus p_62698_) {
   }

   public LevelChunkSection[] m_7103_() {
      return this.f_62685_.m_7103_();
   }

   public void m_6511_(Heightmap.Types p_62706_, long[] p_62707_) {
   }

   private Heightmap.Types m_62741_(Heightmap.Types p_62742_) {
      if (p_62742_ == Heightmap.Types.WORLD_SURFACE_WG) {
         return Heightmap.Types.WORLD_SURFACE;
      } else {
         return p_62742_ == Heightmap.Types.OCEAN_FLOOR_WG ? Heightmap.Types.OCEAN_FLOOR : p_62742_;
      }
   }

   public int m_5885_(Heightmap.Types p_62702_, int p_62703_, int p_62704_) {
      return this.f_62685_.m_5885_(this.m_62741_(p_62702_), p_62703_, p_62704_);
   }

   public BlockPos m_142241_(Heightmap.Types p_156360_) {
      return this.f_62685_.m_142241_(this.m_62741_(p_156360_));
   }

   public ChunkPos m_7697_() {
      return this.f_62685_.m_7697_();
   }

   @Nullable
   public StructureStart<?> m_7253_(StructureFeature<?> p_62709_) {
      return this.f_62685_.m_7253_(p_62709_);
   }

   public void m_8078_(StructureFeature<?> p_62714_, StructureStart<?> p_62715_) {
   }

   public Map<StructureFeature<?>, StructureStart<?>> m_6633_() {
      return this.f_62685_.m_6633_();
   }

   public void m_8040_(Map<StructureFeature<?>, StructureStart<?>> p_62726_) {
   }

   public LongSet m_6705_(StructureFeature<?> p_62734_) {
      return this.f_62685_.m_6705_(p_62734_);
   }

   public void m_6306_(StructureFeature<?> p_62711_, long p_62712_) {
   }

   public Map<StructureFeature<?>, LongSet> m_7049_() {
      return this.f_62685_.m_7049_();
   }

   public void m_7946_(Map<StructureFeature<?>, LongSet> p_62738_) {
   }

   public ChunkBiomeContainer m_6221_() {
      return this.f_62685_.m_6221_();
   }

   public void m_8092_(boolean p_62730_) {
   }

   public boolean m_6344_() {
      return false;
   }

   public ChunkStatus m_6415_() {
      return this.f_62685_.m_6415_();
   }

   public void m_8114_(BlockPos p_62747_) {
   }

   public void m_8113_(BlockPos p_62752_) {
   }

   public void m_5604_(CompoundTag p_62728_) {
   }

   @Nullable
   public CompoundTag m_8049_(BlockPos p_62757_) {
      return this.f_62685_.m_8049_(p_62757_);
   }

   @Nullable
   public CompoundTag m_8051_(BlockPos p_62760_) {
      return this.f_62685_.m_8051_(p_62760_);
   }

   public void m_7329_(ChunkBiomeContainer p_62696_) {
   }

   public Stream<BlockPos> m_6267_() {
      return this.f_62685_.m_6267_();
   }

   public ProtoTickList<Block> m_5782_() {
      return new ProtoTickList<>((p_62694_) -> {
         return p_62694_.m_49966_().m_60795_();
      }, this.m_7697_(), this);
   }

   public ProtoTickList<Fluid> m_5783_() {
      return new ProtoTickList<>((p_62717_) -> {
         return p_62717_ == Fluids.f_76191_;
      }, this.m_7697_(), this);
   }

   public BitSet m_6548_(GenerationStep.Carving p_62700_) {
      throw (UnsupportedOperationException)Util.m_137570_(new UnsupportedOperationException("Meaningless in this context"));
   }

   public BitSet m_6547_(GenerationStep.Carving p_62732_) {
      throw (UnsupportedOperationException)Util.m_137570_(new UnsupportedOperationException("Meaningless in this context"));
   }

   public LevelChunk m_62768_() {
      return this.f_62685_;
   }

   public boolean m_6332_() {
      return this.f_62685_.m_6332_();
   }

   public void m_8094_(boolean p_62740_) {
      this.f_62685_.m_8094_(p_62740_);
   }
}