package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SaplingBlock extends BushBlock implements BonemealableBlock {
   public static final IntegerProperty f_55973_ = BlockStateProperties.f_61387_;
   protected static final float f_154380_ = 6.0F;
   protected static final VoxelShape f_55974_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
   private final AbstractTreeGrower f_55975_;

   public SaplingBlock(AbstractTreeGrower p_55978_, BlockBehaviour.Properties p_55979_) {
      super(p_55979_);
      this.f_55975_ = p_55978_;
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_55973_, Integer.valueOf(0)));
   }

   public VoxelShape m_5940_(BlockState p_56008_, BlockGetter p_56009_, BlockPos p_56010_, CollisionContext p_56011_) {
      return f_55974_;
   }

   public void m_7455_(BlockState p_56003_, ServerLevel p_56004_, BlockPos p_56005_, Random p_56006_) {
      if (p_56004_.m_46803_(p_56005_.m_7494_()) >= 9 && p_56006_.nextInt(7) == 0) {
      if (!p_56004_.isAreaLoaded(p_56005_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
         this.m_55980_(p_56004_, p_56005_, p_56003_, p_56006_);
      }

   }

   public void m_55980_(ServerLevel p_55981_, BlockPos p_55982_, BlockState p_55983_, Random p_55984_) {
      if (p_55983_.m_61143_(f_55973_) == 0) {
         p_55981_.m_7731_(p_55982_, p_55983_.m_61122_(f_55973_), 4);
      } else {
         if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(p_55981_, p_55984_, p_55982_)) return;
         this.f_55975_.m_6334_(p_55981_, p_55981_.m_7726_().m_8481_(), p_55982_, p_55983_, p_55984_);
      }

   }

   public boolean m_7370_(BlockGetter p_55991_, BlockPos p_55992_, BlockState p_55993_, boolean p_55994_) {
      return true;
   }

   public boolean m_5491_(Level p_55996_, Random p_55997_, BlockPos p_55998_, BlockState p_55999_) {
      return (double)p_55996_.f_46441_.nextFloat() < 0.45D;
   }

   public void m_7719_(ServerLevel p_55986_, Random p_55987_, BlockPos p_55988_, BlockState p_55989_) {
      this.m_55980_(p_55986_, p_55988_, p_55989_, p_55987_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56001_) {
      p_56001_.m_61104_(f_55973_);
   }
}
