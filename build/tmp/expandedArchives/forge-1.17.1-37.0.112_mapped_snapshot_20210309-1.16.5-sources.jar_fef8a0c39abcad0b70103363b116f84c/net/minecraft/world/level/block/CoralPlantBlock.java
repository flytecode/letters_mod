package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoralPlantBlock extends BaseCoralPlantTypeBlock {
   private final Block f_52172_;
   protected static final float f_153100_ = 6.0F;
   protected static final VoxelShape f_52171_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);

   public CoralPlantBlock(Block p_52175_, BlockBehaviour.Properties p_52176_) {
      super(p_52176_);
      this.f_52172_ = p_52175_;
   }

   public void m_6807_(BlockState p_52195_, Level p_52196_, BlockPos p_52197_, BlockState p_52198_, boolean p_52199_) {
      this.m_49164_(p_52195_, p_52196_, p_52197_);
   }

   public void m_7458_(BlockState p_52178_, ServerLevel p_52179_, BlockPos p_52180_, Random p_52181_) {
      if (!m_49186_(p_52178_, p_52179_, p_52180_)) {
         p_52179_.m_7731_(p_52180_, this.f_52172_.m_49966_().m_61124_(f_49158_, Boolean.valueOf(false)), 2);
      }

   }

   public BlockState m_7417_(BlockState p_52183_, Direction p_52184_, BlockState p_52185_, LevelAccessor p_52186_, BlockPos p_52187_, BlockPos p_52188_) {
      if (p_52184_ == Direction.DOWN && !p_52183_.m_60710_(p_52186_, p_52187_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         this.m_49164_(p_52183_, p_52186_, p_52187_);
         if (p_52183_.m_61143_(f_49158_)) {
            p_52186_.m_6217_().m_5945_(p_52187_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_52186_));
         }

         return super.m_7417_(p_52183_, p_52184_, p_52185_, p_52186_, p_52187_, p_52188_);
      }
   }

   public VoxelShape m_5940_(BlockState p_52190_, BlockGetter p_52191_, BlockPos p_52192_, CollisionContext p_52193_) {
      return f_52171_;
   }
}