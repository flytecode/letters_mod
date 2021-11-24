package net.minecraft.world.level.block;

import java.util.Random;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class GlowLichenBlock extends MultifaceBlock implements BonemealableBlock, SimpleWaterloggedBlock {
   private static final BooleanProperty f_153279_ = BlockStateProperties.f_61362_;

   public GlowLichenBlock(BlockBehaviour.Properties p_153282_) {
      super(p_153282_);
      this.m_49959_(this.m_49966_().m_61124_(f_153279_, Boolean.valueOf(false)));
   }

   public static ToIntFunction<BlockState> m_181222_(int p_181223_) {
      return (p_181221_) -> {
         return MultifaceBlock.m_153960_(p_181221_) ? p_181223_ : 0;
      };
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153309_) {
      super.m_7926_(p_153309_);
      p_153309_.m_61104_(f_153279_);
   }

   public BlockState m_7417_(BlockState p_153302_, Direction p_153303_, BlockState p_153304_, LevelAccessor p_153305_, BlockPos p_153306_, BlockPos p_153307_) {
      if (p_153302_.m_61143_(f_153279_)) {
         p_153305_.m_6217_().m_5945_(p_153306_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_153305_));
      }

      return super.m_7417_(p_153302_, p_153303_, p_153304_, p_153305_, p_153306_, p_153307_);
   }

   public boolean m_6864_(BlockState p_153299_, BlockPlaceContext p_153300_) {
      return !p_153300_.m_43722_().m_150930_(Items.f_151025_) || super.m_6864_(p_153299_, p_153300_);
   }

   public boolean m_7370_(BlockGetter p_153289_, BlockPos p_153290_, BlockState p_153291_, boolean p_153292_) {
      return Stream.of(f_153806_).anyMatch((p_153316_) -> {
         return this.m_153948_(p_153291_, p_153289_, p_153290_, p_153316_.m_122424_());
      });
   }

   public boolean m_5491_(Level p_153294_, Random p_153295_, BlockPos p_153296_, BlockState p_153297_) {
      return true;
   }

   public void m_7719_(ServerLevel p_153284_, Random p_153285_, BlockPos p_153286_, BlockState p_153287_) {
      this.m_153935_(p_153287_, p_153284_, p_153286_, p_153285_);
   }

   public FluidState m_5888_(BlockState p_153311_) {
      return p_153311_.m_61143_(f_153279_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_153311_);
   }

   public boolean m_7420_(BlockState p_181225_, BlockGetter p_181226_, BlockPos p_181227_) {
      return p_181225_.m_60819_().m_76178_();
   }
}