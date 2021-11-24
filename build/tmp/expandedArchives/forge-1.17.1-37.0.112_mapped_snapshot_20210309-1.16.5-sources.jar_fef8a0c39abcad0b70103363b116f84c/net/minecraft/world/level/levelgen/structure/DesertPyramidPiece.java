package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class DesertPyramidPiece extends ScatteredFeaturePiece {
   private final boolean[] f_71081_ = new boolean[4];

   public DesertPyramidPiece(Random p_71086_, int p_71087_, int p_71088_) {
      super(StructurePieceType.f_67117_, p_71087_, 64, p_71088_, 21, 15, 21, m_163580_(p_71086_));
   }

   public DesertPyramidPiece(ServerLevel p_162410_, CompoundTag p_162411_) {
      super(StructurePieceType.f_67117_, p_162411_);
      this.f_71081_[0] = p_162411_.m_128471_("hasPlacedChest0");
      this.f_71081_[1] = p_162411_.m_128471_("hasPlacedChest1");
      this.f_71081_[2] = p_162411_.m_128471_("hasPlacedChest2");
      this.f_71081_[3] = p_162411_.m_128471_("hasPlacedChest3");
   }

   protected void m_142347_(ServerLevel p_162413_, CompoundTag p_162414_) {
      super.m_142347_(p_162413_, p_162414_);
      p_162414_.m_128379_("hasPlacedChest0", this.f_71081_[0]);
      p_162414_.m_128379_("hasPlacedChest1", this.f_71081_[1]);
      p_162414_.m_128379_("hasPlacedChest2", this.f_71081_[2]);
      p_162414_.m_128379_("hasPlacedChest3", this.f_71081_[3]);
   }

   public boolean m_7832_(WorldGenLevel p_71090_, StructureFeatureManager p_71091_, ChunkGenerator p_71092_, Random p_71093_, BoundingBox p_71094_, ChunkPos p_71095_, BlockPos p_71096_) {
      this.m_73441_(p_71090_, p_71094_, 0, -4, 0, this.f_72787_ - 1, 0, this.f_72789_ - 1, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);

      for(int i = 1; i <= 9; ++i) {
         this.m_73441_(p_71090_, p_71094_, i, i, i, this.f_72787_ - 1 - i, i, this.f_72789_ - 1 - i, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
         this.m_73441_(p_71090_, p_71094_, i + 1, i, i + 1, this.f_72787_ - 2 - i, i, this.f_72789_ - 2 - i, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      }

      for(int k1 = 0; k1 < this.f_72787_; ++k1) {
         for(int j = 0; j < this.f_72789_; ++j) {
            int k = -5;
            this.m_73528_(p_71090_, Blocks.f_50062_.m_49966_(), k1, -5, j, p_71094_);
         }
      }

      BlockState blockstate1 = Blocks.f_50263_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.NORTH);
      BlockState blockstate2 = Blocks.f_50263_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.SOUTH);
      BlockState blockstate3 = Blocks.f_50263_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.EAST);
      BlockState blockstate = Blocks.f_50263_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.WEST);
      this.m_73441_(p_71090_, p_71094_, 0, 0, 0, 4, 9, 4, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 1, 10, 1, 3, 10, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73434_(p_71090_, blockstate1, 2, 10, 0, p_71094_);
      this.m_73434_(p_71090_, blockstate2, 2, 10, 4, p_71094_);
      this.m_73434_(p_71090_, blockstate3, 0, 10, 2, p_71094_);
      this.m_73434_(p_71090_, blockstate, 4, 10, 2, p_71094_);
      this.m_73441_(p_71090_, p_71094_, this.f_72787_ - 5, 0, 0, this.f_72787_ - 1, 9, 4, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, this.f_72787_ - 4, 10, 1, this.f_72787_ - 2, 10, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73434_(p_71090_, blockstate1, this.f_72787_ - 3, 10, 0, p_71094_);
      this.m_73434_(p_71090_, blockstate2, this.f_72787_ - 3, 10, 4, p_71094_);
      this.m_73434_(p_71090_, blockstate3, this.f_72787_ - 5, 10, 2, p_71094_);
      this.m_73434_(p_71090_, blockstate, this.f_72787_ - 1, 10, 2, p_71094_);
      this.m_73441_(p_71090_, p_71094_, 8, 0, 0, 12, 4, 4, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 9, 1, 0, 11, 3, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 9, 1, 1, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 9, 2, 1, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 9, 3, 1, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 10, 3, 1, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 11, 3, 1, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 11, 2, 1, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 11, 1, 1, p_71094_);
      this.m_73441_(p_71090_, p_71094_, 4, 1, 1, 8, 3, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 4, 1, 2, 8, 2, 2, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 12, 1, 1, 16, 3, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 12, 1, 2, 16, 2, 2, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 5, 4, 5, this.f_72787_ - 6, 4, this.f_72789_ - 6, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 9, 4, 9, 11, 4, 11, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 8, 1, 8, 8, 3, 8, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 12, 1, 8, 12, 3, 8, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 8, 1, 12, 8, 3, 12, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 12, 1, 12, 12, 3, 12, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 1, 1, 5, 4, 4, 11, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, this.f_72787_ - 5, 1, 5, this.f_72787_ - 2, 4, 11, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 6, 7, 9, 6, 7, 11, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, this.f_72787_ - 7, 7, 9, this.f_72787_ - 7, 7, 11, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 5, 5, 9, 5, 7, 11, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, this.f_72787_ - 6, 5, 9, this.f_72787_ - 6, 7, 11, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 5, 5, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 5, 6, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 6, 6, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), this.f_72787_ - 6, 5, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), this.f_72787_ - 6, 6, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), this.f_72787_ - 7, 6, 10, p_71094_);
      this.m_73441_(p_71090_, p_71094_, 2, 4, 4, 2, 6, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, this.f_72787_ - 3, 4, 4, this.f_72787_ - 3, 6, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73434_(p_71090_, blockstate1, 2, 4, 5, p_71094_);
      this.m_73434_(p_71090_, blockstate1, 2, 3, 4, p_71094_);
      this.m_73434_(p_71090_, blockstate1, this.f_72787_ - 3, 4, 5, p_71094_);
      this.m_73434_(p_71090_, blockstate1, this.f_72787_ - 3, 3, 4, p_71094_);
      this.m_73441_(p_71090_, p_71094_, 1, 1, 3, 2, 2, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, this.f_72787_ - 3, 1, 3, this.f_72787_ - 2, 2, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73434_(p_71090_, Blocks.f_50062_.m_49966_(), 1, 1, 2, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50062_.m_49966_(), this.f_72787_ - 2, 1, 2, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50406_.m_49966_(), 1, 2, 2, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50406_.m_49966_(), this.f_72787_ - 2, 2, 2, p_71094_);
      this.m_73434_(p_71090_, blockstate, 2, 1, 2, p_71094_);
      this.m_73434_(p_71090_, blockstate3, this.f_72787_ - 3, 1, 2, p_71094_);
      this.m_73441_(p_71090_, p_71094_, 4, 3, 5, 4, 3, 17, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, this.f_72787_ - 5, 3, 5, this.f_72787_ - 5, 3, 17, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 3, 1, 5, 4, 2, 16, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, this.f_72787_ - 6, 1, 5, this.f_72787_ - 5, 2, 16, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);

      for(int l = 5; l <= 17; l += 2) {
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 4, 1, l, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), 4, 2, l, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), this.f_72787_ - 5, 1, l, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), this.f_72787_ - 5, 2, l, p_71094_);
      }

      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 10, 0, 7, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 10, 0, 8, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 9, 0, 9, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 11, 0, 9, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 8, 0, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 12, 0, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 7, 0, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 13, 0, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 9, 0, 11, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 11, 0, 11, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 10, 0, 12, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 10, 0, 13, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50298_.m_49966_(), 10, 0, 10, p_71094_);

      for(int l1 = 0; l1 <= this.f_72787_ - 1; l1 += this.f_72787_ - 1) {
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), l1, 2, 1, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 2, 2, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), l1, 2, 3, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), l1, 3, 1, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 3, 2, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), l1, 3, 3, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 4, 1, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), l1, 4, 2, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 4, 3, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), l1, 5, 1, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 5, 2, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), l1, 5, 3, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 6, 1, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), l1, 6, 2, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 6, 3, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 7, 1, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 7, 2, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), l1, 7, 3, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), l1, 8, 1, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), l1, 8, 2, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), l1, 8, 3, p_71094_);
      }

      for(int i2 = 2; i2 <= this.f_72787_ - 3; i2 += this.f_72787_ - 3 - 2) {
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), i2 - 1, 2, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2, 2, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), i2 + 1, 2, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), i2 - 1, 3, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2, 3, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), i2 + 1, 3, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2 - 1, 4, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), i2, 4, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2 + 1, 4, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), i2 - 1, 5, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2, 5, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), i2 + 1, 5, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2 - 1, 6, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), i2, 6, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2 + 1, 6, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2 - 1, 7, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2, 7, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), i2 + 1, 7, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), i2 - 1, 8, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), i2, 8, 0, p_71094_);
         this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), i2 + 1, 8, 0, p_71094_);
      }

      this.m_73441_(p_71090_, p_71094_, 8, 4, 0, 12, 6, 0, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 8, 6, 0, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 12, 6, 0, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 9, 5, 0, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), 10, 5, 0, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50288_.m_49966_(), 11, 5, 0, p_71094_);
      this.m_73441_(p_71090_, p_71094_, 8, -14, 8, 12, -11, 12, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 8, -10, 8, 12, -10, 12, Blocks.f_50063_.m_49966_(), Blocks.f_50063_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 8, -9, 8, 12, -9, 12, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 8, -8, 8, 12, -1, 12, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
      this.m_73441_(p_71090_, p_71094_, 9, -11, 9, 11, -1, 11, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73434_(p_71090_, Blocks.f_50165_.m_49966_(), 10, -11, 10, p_71094_);
      this.m_73441_(p_71090_, p_71094_, 9, -13, 9, 11, -13, 11, Blocks.f_50077_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 8, -11, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 8, -10, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), 7, -10, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 7, -11, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 12, -11, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 12, -10, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), 13, -10, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 13, -11, 10, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 10, -11, 8, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 10, -10, 8, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), 10, -10, 7, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 10, -11, 7, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 10, -11, 12, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50016_.m_49966_(), 10, -10, 12, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50063_.m_49966_(), 10, -10, 13, p_71094_);
      this.m_73434_(p_71090_, Blocks.f_50064_.m_49966_(), 10, -11, 13, p_71094_);

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (!this.f_71081_[direction.m_122416_()]) {
            int i1 = direction.m_122429_() * 2;
            int j1 = direction.m_122431_() * 2;
            this.f_71081_[direction.m_122416_()] = this.m_5606_(p_71090_, p_71094_, p_71093_, 10 + i1, -11, 10 + j1, BuiltInLootTables.f_78764_);
         }
      }

      return true;
   }
}