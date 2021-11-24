package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedstoneWallTorchBlock extends RedstoneTorchBlock {
   public static final DirectionProperty f_55740_ = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty f_55741_ = RedstoneTorchBlock.f_55674_;

   public RedstoneWallTorchBlock(BlockBehaviour.Properties p_55744_) {
      super(p_55744_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_55740_, Direction.NORTH).m_61124_(f_55741_, Boolean.valueOf(true)));
   }

   public String m_7705_() {
      return this.m_5456_().m_5524_();
   }

   public VoxelShape m_5940_(BlockState p_55781_, BlockGetter p_55782_, BlockPos p_55783_, CollisionContext p_55784_) {
      return WallTorchBlock.m_58156_(p_55781_);
   }

   public boolean m_7898_(BlockState p_55762_, LevelReader p_55763_, BlockPos p_55764_) {
      return Blocks.f_50082_.m_7898_(p_55762_, p_55763_, p_55764_);
   }

   public BlockState m_7417_(BlockState p_55772_, Direction p_55773_, BlockState p_55774_, LevelAccessor p_55775_, BlockPos p_55776_, BlockPos p_55777_) {
      return Blocks.f_50082_.m_7417_(p_55772_, p_55773_, p_55774_, p_55775_, p_55776_, p_55777_);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_55746_) {
      BlockState blockstate = Blocks.f_50082_.m_5573_(p_55746_);
      return blockstate == null ? null : this.m_49966_().m_61124_(f_55740_, blockstate.m_61143_(f_55740_));
   }

   public void m_7100_(BlockState p_55757_, Level p_55758_, BlockPos p_55759_, Random p_55760_) {
      if (p_55757_.m_61143_(f_55741_)) {
         Direction direction = p_55757_.m_61143_(f_55740_).m_122424_();
         double d0 = 0.27D;
         double d1 = (double)p_55759_.m_123341_() + 0.5D + (p_55760_.nextDouble() - 0.5D) * 0.2D + 0.27D * (double)direction.m_122429_();
         double d2 = (double)p_55759_.m_123342_() + 0.7D + (p_55760_.nextDouble() - 0.5D) * 0.2D + 0.22D;
         double d3 = (double)p_55759_.m_123343_() + 0.5D + (p_55760_.nextDouble() - 0.5D) * 0.2D + 0.27D * (double)direction.m_122431_();
         p_55758_.m_7106_(this.f_57488_, d1, d2, d3, 0.0D, 0.0D, 0.0D);
      }
   }

   protected boolean m_6918_(Level p_55748_, BlockPos p_55749_, BlockState p_55750_) {
      Direction direction = p_55750_.m_61143_(f_55740_).m_122424_();
      return p_55748_.m_46616_(p_55749_.m_142300_(direction), direction);
   }

   public int m_6378_(BlockState p_55752_, BlockGetter p_55753_, BlockPos p_55754_, Direction p_55755_) {
      return p_55752_.m_61143_(f_55741_) && p_55752_.m_61143_(f_55740_) != p_55755_ ? 15 : 0;
   }

   public BlockState m_6843_(BlockState p_55769_, Rotation p_55770_) {
      return Blocks.f_50082_.m_6843_(p_55769_, p_55770_);
   }

   public BlockState m_6943_(BlockState p_55766_, Mirror p_55767_) {
      return Blocks.f_50082_.m_6943_(p_55766_, p_55767_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55779_) {
      p_55779_.m_61104_(f_55740_, f_55741_);
   }
}