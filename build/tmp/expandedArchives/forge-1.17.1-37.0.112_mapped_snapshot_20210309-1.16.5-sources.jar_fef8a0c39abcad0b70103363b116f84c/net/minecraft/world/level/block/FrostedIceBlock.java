package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FrostedIceBlock extends IceBlock {
   public static final int f_153268_ = 3;
   public static final IntegerProperty f_53561_ = BlockStateProperties.f_61407_;
   private static final int f_153269_ = 4;
   private static final int f_153270_ = 2;

   public FrostedIceBlock(BlockBehaviour.Properties p_53564_) {
      super(p_53564_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_53561_, Integer.valueOf(0)));
   }

   public void m_7455_(BlockState p_53588_, ServerLevel p_53589_, BlockPos p_53590_, Random p_53591_) {
      this.m_7458_(p_53588_, p_53589_, p_53590_, p_53591_);
   }

   public void m_7458_(BlockState p_53574_, ServerLevel p_53575_, BlockPos p_53576_, Random p_53577_) {
      if ((p_53577_.nextInt(3) == 0 || this.m_53565_(p_53575_, p_53576_, 4)) && p_53575_.m_46803_(p_53576_) > 11 - p_53574_.m_61143_(f_53561_) - p_53574_.m_60739_(p_53575_, p_53576_) && this.m_53592_(p_53574_, p_53575_, p_53576_)) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(Direction direction : Direction.values()) {
            blockpos$mutableblockpos.m_122159_(p_53576_, direction);
            BlockState blockstate = p_53575_.m_8055_(blockpos$mutableblockpos);
            if (blockstate.m_60713_(this) && !this.m_53592_(blockstate, p_53575_, blockpos$mutableblockpos)) {
               p_53575_.m_6219_().m_5945_(blockpos$mutableblockpos, this, Mth.m_14072_(p_53577_, 20, 40));
            }
         }

      } else {
         p_53575_.m_6219_().m_5945_(p_53576_, this, Mth.m_14072_(p_53577_, 20, 40));
      }
   }

   private boolean m_53592_(BlockState p_53593_, Level p_53594_, BlockPos p_53595_) {
      int i = p_53593_.m_61143_(f_53561_);
      if (i < 3) {
         p_53594_.m_7731_(p_53595_, p_53593_.m_61124_(f_53561_, Integer.valueOf(i + 1)), 2);
         return false;
      } else {
         this.m_54168_(p_53593_, p_53594_, p_53595_);
         return true;
      }
   }

   public void m_6861_(BlockState p_53579_, Level p_53580_, BlockPos p_53581_, Block p_53582_, BlockPos p_53583_, boolean p_53584_) {
      if (p_53582_.m_49966_().m_60713_(this) && this.m_53565_(p_53580_, p_53581_, 2)) {
         this.m_54168_(p_53579_, p_53580_, p_53581_);
      }

      super.m_6861_(p_53579_, p_53580_, p_53581_, p_53582_, p_53583_, p_53584_);
   }

   private boolean m_53565_(BlockGetter p_53566_, BlockPos p_53567_, int p_53568_) {
      int i = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(Direction direction : Direction.values()) {
         blockpos$mutableblockpos.m_122159_(p_53567_, direction);
         if (p_53566_.m_8055_(blockpos$mutableblockpos).m_60713_(this)) {
            ++i;
            if (i >= p_53568_) {
               return false;
            }
         }
      }

      return true;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53586_) {
      p_53586_.m_61104_(f_53561_);
   }

   public ItemStack m_7397_(BlockGetter p_53570_, BlockPos p_53571_, BlockState p_53572_) {
      return ItemStack.f_41583_;
   }
}