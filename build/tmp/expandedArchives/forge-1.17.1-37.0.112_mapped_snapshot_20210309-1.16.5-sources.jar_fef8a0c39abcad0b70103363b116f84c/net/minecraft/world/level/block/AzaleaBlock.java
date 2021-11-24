package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.grower.AzaleaTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AzaleaBlock extends BushBlock implements BonemealableBlock {
   private static final AzaleaTreeGrower f_152063_ = new AzaleaTreeGrower();
   private static final VoxelShape f_152064_ = Shapes.m_83110_(Block.m_49796_(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.m_49796_(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D));

   public AzaleaBlock(BlockBehaviour.Properties p_152067_) {
      super(p_152067_);
   }

   public VoxelShape m_5940_(BlockState p_152084_, BlockGetter p_152085_, BlockPos p_152086_, CollisionContext p_152087_) {
      return f_152064_;
   }

   protected boolean m_6266_(BlockState p_152089_, BlockGetter p_152090_, BlockPos p_152091_) {
      return p_152089_.m_60713_(Blocks.f_50129_) || super.m_6266_(p_152089_, p_152090_, p_152091_);
   }

   public boolean m_7370_(BlockGetter p_152074_, BlockPos p_152075_, BlockState p_152076_, boolean p_152077_) {
      return p_152074_.m_6425_(p_152075_.m_7494_()).m_76178_();
   }

   public boolean m_5491_(Level p_152079_, Random p_152080_, BlockPos p_152081_, BlockState p_152082_) {
      return (double)p_152079_.f_46441_.nextFloat() < 0.45D;
   }

   public void m_7719_(ServerLevel p_152069_, Random p_152070_, BlockPos p_152071_, BlockState p_152072_) {
      f_152063_.m_6334_(p_152069_, p_152069_.m_7726_().m_8481_(), p_152071_, p_152072_, p_152070_);
   }
}