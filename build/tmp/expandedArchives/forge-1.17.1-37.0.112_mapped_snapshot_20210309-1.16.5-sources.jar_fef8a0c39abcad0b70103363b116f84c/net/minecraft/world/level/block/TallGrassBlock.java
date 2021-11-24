package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TallGrassBlock extends BushBlock implements BonemealableBlock, net.minecraftforge.common.IForgeShearable {
   protected static final float f_154739_ = 6.0F;
   protected static final VoxelShape f_57315_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public TallGrassBlock(BlockBehaviour.Properties p_57318_) {
      super(p_57318_);
   }

   public VoxelShape m_5940_(BlockState p_57336_, BlockGetter p_57337_, BlockPos p_57338_, CollisionContext p_57339_) {
      return f_57315_;
   }

   public boolean m_7370_(BlockGetter p_57325_, BlockPos p_57326_, BlockState p_57327_, boolean p_57328_) {
      return true;
   }

   public boolean m_5491_(Level p_57330_, Random p_57331_, BlockPos p_57332_, BlockState p_57333_) {
      return true;
   }

   public void m_7719_(ServerLevel p_57320_, Random p_57321_, BlockPos p_57322_, BlockState p_57323_) {
      DoublePlantBlock doubleplantblock = (DoublePlantBlock)(p_57323_.m_60713_(Blocks.f_50035_) ? Blocks.f_50360_ : Blocks.f_50359_);
      if (doubleplantblock.m_49966_().m_60710_(p_57320_, p_57322_) && p_57320_.m_46859_(p_57322_.m_7494_())) {
         DoublePlantBlock.m_153173_(p_57320_, doubleplantblock.m_49966_(), p_57322_, 2);
      }

   }

   public BlockBehaviour.OffsetType m_5858_() {
      return BlockBehaviour.OffsetType.XYZ;
   }
}
