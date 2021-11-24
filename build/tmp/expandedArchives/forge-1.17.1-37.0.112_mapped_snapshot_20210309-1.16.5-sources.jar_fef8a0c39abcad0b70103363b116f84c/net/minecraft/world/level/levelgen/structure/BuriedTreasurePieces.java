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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class BuriedTreasurePieces {
   public static class BuriedTreasurePiece extends StructurePiece {
      public BuriedTreasurePiece(BlockPos p_71068_) {
         super(StructurePieceType.f_67133_, 0, new BoundingBox(p_71068_));
      }

      public BuriedTreasurePiece(ServerLevel p_162404_, CompoundTag p_162405_) {
         super(StructurePieceType.f_67133_, p_162405_);
      }

      protected void m_142347_(ServerLevel p_162407_, CompoundTag p_162408_) {
      }

      public boolean m_7832_(WorldGenLevel p_71070_, StructureFeatureManager p_71071_, ChunkGenerator p_71072_, Random p_71073_, BoundingBox p_71074_, ChunkPos p_71075_, BlockPos p_71076_) {
         int i = p_71070_.m_6924_(Heightmap.Types.OCEAN_FLOOR_WG, this.f_73383_.m_162395_(), this.f_73383_.m_162398_());
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(this.f_73383_.m_162395_(), i, this.f_73383_.m_162398_());

         while(blockpos$mutableblockpos.m_123342_() > p_71070_.m_141937_()) {
            BlockState blockstate = p_71070_.m_8055_(blockpos$mutableblockpos);
            BlockState blockstate1 = p_71070_.m_8055_(blockpos$mutableblockpos.m_7495_());
            if (blockstate1 == Blocks.f_50062_.m_49966_() || blockstate1 == Blocks.f_50069_.m_49966_() || blockstate1 == Blocks.f_50334_.m_49966_() || blockstate1 == Blocks.f_50122_.m_49966_() || blockstate1 == Blocks.f_50228_.m_49966_()) {
               BlockState blockstate2 = !blockstate.m_60795_() && !this.m_71077_(blockstate) ? blockstate : Blocks.f_49992_.m_49966_();

               for(Direction direction : Direction.values()) {
                  BlockPos blockpos = blockpos$mutableblockpos.m_142300_(direction);
                  BlockState blockstate3 = p_71070_.m_8055_(blockpos);
                  if (blockstate3.m_60795_() || this.m_71077_(blockstate3)) {
                     BlockPos blockpos1 = blockpos.m_7495_();
                     BlockState blockstate4 = p_71070_.m_8055_(blockpos1);
                     if ((blockstate4.m_60795_() || this.m_71077_(blockstate4)) && direction != Direction.UP) {
                        p_71070_.m_7731_(blockpos, blockstate1, 3);
                     } else {
                        p_71070_.m_7731_(blockpos, blockstate2, 3);
                     }
                  }
               }

               this.f_73383_ = new BoundingBox(blockpos$mutableblockpos);
               return this.m_73420_(p_71070_, p_71074_, p_71073_, blockpos$mutableblockpos, BuiltInLootTables.f_78692_, (BlockState)null);
            }

            blockpos$mutableblockpos.m_122184_(0, -1, 0);
         }

         return false;
      }

      private boolean m_71077_(BlockState p_71078_) {
         return p_71078_ == Blocks.f_49990_.m_49966_() || p_71078_ == Blocks.f_49991_.m_49966_();
      }
   }
}