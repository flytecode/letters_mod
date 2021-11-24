package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public class WaterloggedVegetationPatchFeature extends VegetationPatchFeature {
   public WaterloggedVegetationPatchFeature(Codec<VegetationPatchConfiguration> p_160635_) {
      super(p_160635_);
   }

   protected Set<BlockPos> m_142619_(WorldGenLevel p_160643_, VegetationPatchConfiguration p_160644_, Random p_160645_, BlockPos p_160646_, Predicate<BlockState> p_160647_, int p_160648_, int p_160649_) {
      Set<BlockPos> set = super.m_142619_(p_160643_, p_160644_, p_160645_, p_160646_, p_160647_, p_160648_, p_160649_);
      Set<BlockPos> set1 = new HashSet<>();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(BlockPos blockpos : set) {
         if (!m_160655_(p_160643_, set, blockpos, blockpos$mutableblockpos)) {
            set1.add(blockpos);
         }
      }

      for(BlockPos blockpos1 : set1) {
         p_160643_.m_7731_(blockpos1, Blocks.f_49990_.m_49966_(), 2);
      }

      return set1;
   }

   private static boolean m_160655_(WorldGenLevel p_160656_, Set<BlockPos> p_160657_, BlockPos p_160658_, BlockPos.MutableBlockPos p_160659_) {
      return m_160650_(p_160656_, p_160658_, p_160659_, Direction.NORTH) || m_160650_(p_160656_, p_160658_, p_160659_, Direction.EAST) || m_160650_(p_160656_, p_160658_, p_160659_, Direction.SOUTH) || m_160650_(p_160656_, p_160658_, p_160659_, Direction.WEST) || m_160650_(p_160656_, p_160658_, p_160659_, Direction.DOWN);
   }

   private static boolean m_160650_(WorldGenLevel p_160651_, BlockPos p_160652_, BlockPos.MutableBlockPos p_160653_, Direction p_160654_) {
      p_160653_.m_122159_(p_160652_, p_160654_);
      return !p_160651_.m_8055_(p_160653_).m_60783_(p_160651_, p_160653_, p_160654_.m_122424_());
   }

   protected boolean m_142229_(WorldGenLevel p_160637_, VegetationPatchConfiguration p_160638_, ChunkGenerator p_160639_, Random p_160640_, BlockPos p_160641_) {
      if (super.m_142229_(p_160637_, p_160638_, p_160639_, p_160640_, p_160641_.m_7495_())) {
         BlockState blockstate = p_160637_.m_8055_(p_160641_);
         if (blockstate.m_61138_(BlockStateProperties.f_61362_) && !blockstate.m_61143_(BlockStateProperties.f_61362_)) {
            p_160637_.m_7731_(p_160641_, blockstate.m_61124_(BlockStateProperties.f_61362_, Boolean.valueOf(true)), 2);
         }

         return true;
      } else {
         return false;
      }
   }
}