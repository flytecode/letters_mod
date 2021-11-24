package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;

public class SwamplandHutPiece extends ScatteredFeaturePiece {
   private boolean f_73634_;
   private boolean f_73635_;

   public SwamplandHutPiece(Random p_73640_, int p_73641_, int p_73642_) {
      super(StructurePieceType.f_67116_, p_73641_, 64, p_73642_, 7, 7, 9, m_163580_(p_73640_));
   }

   public SwamplandHutPiece(ServerLevel p_163653_, CompoundTag p_163654_) {
      super(StructurePieceType.f_67116_, p_163654_);
      this.f_73634_ = p_163654_.m_128471_("Witch");
      this.f_73635_ = p_163654_.m_128471_("Cat");
   }

   protected void m_142347_(ServerLevel p_163656_, CompoundTag p_163657_) {
      super.m_142347_(p_163656_, p_163657_);
      p_163657_.m_128379_("Witch", this.f_73634_);
      p_163657_.m_128379_("Cat", this.f_73635_);
   }

   public boolean m_7832_(WorldGenLevel p_73647_, StructureFeatureManager p_73648_, ChunkGenerator p_73649_, Random p_73650_, BoundingBox p_73651_, ChunkPos p_73652_, BlockPos p_73653_) {
      if (!this.m_72803_(p_73647_, p_73651_, 0)) {
         return false;
      } else {
         this.m_73441_(p_73647_, p_73651_, 1, 1, 1, 5, 1, 7, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 1, 4, 2, 5, 4, 7, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 2, 1, 0, 4, 1, 0, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 2, 2, 2, 3, 3, 2, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 1, 2, 3, 1, 3, 6, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 5, 2, 3, 5, 3, 6, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 2, 2, 7, 4, 3, 7, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 1, 0, 2, 1, 3, 2, Blocks.f_49999_.m_49966_(), Blocks.f_49999_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 5, 0, 2, 5, 3, 2, Blocks.f_49999_.m_49966_(), Blocks.f_49999_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 1, 0, 7, 1, 3, 7, Blocks.f_49999_.m_49966_(), Blocks.f_49999_.m_49966_(), false);
         this.m_73441_(p_73647_, p_73651_, 5, 0, 7, 5, 3, 7, Blocks.f_49999_.m_49966_(), Blocks.f_49999_.m_49966_(), false);
         this.m_73434_(p_73647_, Blocks.f_50132_.m_49966_(), 2, 3, 2, p_73651_);
         this.m_73434_(p_73647_, Blocks.f_50132_.m_49966_(), 3, 3, 7, p_73651_);
         this.m_73434_(p_73647_, Blocks.f_50016_.m_49966_(), 1, 3, 4, p_73651_);
         this.m_73434_(p_73647_, Blocks.f_50016_.m_49966_(), 5, 3, 4, p_73651_);
         this.m_73434_(p_73647_, Blocks.f_50016_.m_49966_(), 5, 3, 5, p_73651_);
         this.m_73434_(p_73647_, Blocks.f_50245_.m_49966_(), 1, 3, 5, p_73651_);
         this.m_73434_(p_73647_, Blocks.f_50091_.m_49966_(), 3, 2, 6, p_73651_);
         this.m_73434_(p_73647_, Blocks.f_50256_.m_49966_(), 4, 2, 6, p_73651_);
         this.m_73434_(p_73647_, Blocks.f_50132_.m_49966_(), 1, 2, 1, p_73651_);
         this.m_73434_(p_73647_, Blocks.f_50132_.m_49966_(), 5, 2, 1, p_73651_);
         BlockState blockstate = Blocks.f_50269_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.NORTH);
         BlockState blockstate1 = Blocks.f_50269_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.EAST);
         BlockState blockstate2 = Blocks.f_50269_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.WEST);
         BlockState blockstate3 = Blocks.f_50269_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.SOUTH);
         this.m_73441_(p_73647_, p_73651_, 0, 4, 1, 6, 4, 1, blockstate, blockstate, false);
         this.m_73441_(p_73647_, p_73651_, 0, 4, 2, 0, 4, 7, blockstate1, blockstate1, false);
         this.m_73441_(p_73647_, p_73651_, 6, 4, 2, 6, 4, 7, blockstate2, blockstate2, false);
         this.m_73441_(p_73647_, p_73651_, 0, 4, 8, 6, 4, 8, blockstate3, blockstate3, false);
         this.m_73434_(p_73647_, blockstate.m_61124_(StairBlock.f_56843_, StairsShape.OUTER_RIGHT), 0, 4, 1, p_73651_);
         this.m_73434_(p_73647_, blockstate.m_61124_(StairBlock.f_56843_, StairsShape.OUTER_LEFT), 6, 4, 1, p_73651_);
         this.m_73434_(p_73647_, blockstate3.m_61124_(StairBlock.f_56843_, StairsShape.OUTER_LEFT), 0, 4, 8, p_73651_);
         this.m_73434_(p_73647_, blockstate3.m_61124_(StairBlock.f_56843_, StairsShape.OUTER_RIGHT), 6, 4, 8, p_73651_);

         for(int i = 2; i <= 7; i += 5) {
            for(int j = 1; j <= 5; j += 4) {
               this.m_73528_(p_73647_, Blocks.f_49999_.m_49966_(), j, -1, i, p_73651_);
            }
         }

         if (!this.f_73634_) {
            BlockPos blockpos = this.m_163582_(2, 2, 5);
            if (p_73651_.m_71051_(blockpos)) {
               this.f_73634_ = true;
               Witch witch = EntityType.f_20495_.m_20615_(p_73647_.m_6018_());
               witch.m_21530_();
               witch.m_7678_((double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_(), (double)blockpos.m_123343_() + 0.5D, 0.0F, 0.0F);
               witch.m_6518_(p_73647_, p_73647_.m_6436_(blockpos), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
               p_73647_.m_47205_(witch);
            }
         }

         this.m_73643_(p_73647_, p_73651_);
         return true;
      }
   }

   private void m_73643_(ServerLevelAccessor p_73644_, BoundingBox p_73645_) {
      if (!this.f_73635_) {
         BlockPos blockpos = this.m_163582_(2, 2, 5);
         if (p_73645_.m_71051_(blockpos)) {
            this.f_73635_ = true;
            Cat cat = EntityType.f_20553_.m_20615_(p_73644_.m_6018_());
            cat.m_21530_();
            cat.m_7678_((double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_(), (double)blockpos.m_123343_() + 0.5D, 0.0F, 0.0F);
            cat.m_6518_(p_73644_, p_73644_.m_6436_(blockpos), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
            p_73644_.m_47205_(cat);
         }
      }

   }
}