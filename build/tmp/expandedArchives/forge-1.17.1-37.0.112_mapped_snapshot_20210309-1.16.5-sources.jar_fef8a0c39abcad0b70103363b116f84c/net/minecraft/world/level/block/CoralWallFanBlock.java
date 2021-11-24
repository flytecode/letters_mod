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

public class CoralWallFanBlock extends BaseCoralWallFanBlock {
   private final Block f_52200_;

   public CoralWallFanBlock(Block p_52202_, BlockBehaviour.Properties p_52203_) {
      super(p_52203_);
      this.f_52200_ = p_52202_;
   }

   public void m_6807_(BlockState p_52217_, Level p_52218_, BlockPos p_52219_, BlockState p_52220_, boolean p_52221_) {
      this.m_49164_(p_52217_, p_52218_, p_52219_);
   }

   public void m_7458_(BlockState p_52205_, ServerLevel p_52206_, BlockPos p_52207_, Random p_52208_) {
      if (!m_49186_(p_52205_, p_52206_, p_52207_)) {
         p_52206_.m_7731_(p_52207_, this.f_52200_.m_49966_().m_61124_(f_49158_, Boolean.valueOf(false)).m_61124_(f_49192_, p_52205_.m_61143_(f_49192_)), 2);
      }

   }

   public BlockState m_7417_(BlockState p_52210_, Direction p_52211_, BlockState p_52212_, LevelAccessor p_52213_, BlockPos p_52214_, BlockPos p_52215_) {
      if (p_52211_.m_122424_() == p_52210_.m_61143_(f_49192_) && !p_52210_.m_60710_(p_52213_, p_52214_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         if (p_52210_.m_61143_(f_49158_)) {
            p_52213_.m_6217_().m_5945_(p_52214_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_52213_));
         }

         this.m_49164_(p_52210_, p_52213_, p_52214_);
         return super.m_7417_(p_52210_, p_52211_, p_52212_, p_52213_, p_52214_, p_52215_);
      }
   }
}