package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class CoralFanBlock extends BaseCoralFanBlock {
   private final Block f_52149_;

   public CoralFanBlock(Block p_52151_, BlockBehaviour.Properties p_52152_) {
      super(p_52152_);
      this.f_52149_ = p_52151_;
   }

   public void m_6807_(BlockState p_52166_, Level p_52167_, BlockPos p_52168_, BlockState p_52169_, boolean p_52170_) {
      this.m_49164_(p_52166_, p_52167_, p_52168_);
   }

   public void m_7458_(BlockState p_52154_, ServerLevel p_52155_, BlockPos p_52156_, Random p_52157_) {
      if (!m_49186_(p_52154_, p_52155_, p_52156_)) {
         p_52155_.m_7731_(p_52156_, this.f_52149_.m_49966_().m_61124_(f_49158_, Boolean.valueOf(false)), 2);
      }

   }

   public BlockState m_7417_(BlockState p_52159_, Direction p_52160_, BlockState p_52161_, LevelAccessor p_52162_, BlockPos p_52163_, BlockPos p_52164_) {
      if (p_52160_ == Direction.DOWN && !p_52159_.m_60710_(p_52162_, p_52163_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         this.m_49164_(p_52159_, p_52162_, p_52163_);
         if (p_52159_.m_61143_(f_49158_)) {
            p_52162_.m_6217_().m_5945_(p_52163_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_52162_));
         }

         return super.m_7417_(p_52159_, p_52160_, p_52161_, p_52162_, p_52163_, p_52164_);
      }
   }
}