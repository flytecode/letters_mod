package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RepeaterBlock extends DiodeBlock {
   public static final BooleanProperty f_55797_ = BlockStateProperties.f_61444_;
   public static final IntegerProperty f_55798_ = BlockStateProperties.f_61413_;

   public RepeaterBlock(BlockBehaviour.Properties p_55801_) {
      super(p_55801_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54117_, Direction.NORTH).m_61124_(f_55798_, Integer.valueOf(1)).m_61124_(f_55797_, Boolean.valueOf(false)).m_61124_(f_52496_, Boolean.valueOf(false)));
   }

   public InteractionResult m_6227_(BlockState p_55809_, Level p_55810_, BlockPos p_55811_, Player p_55812_, InteractionHand p_55813_, BlockHitResult p_55814_) {
      if (!p_55812_.m_150110_().f_35938_) {
         return InteractionResult.PASS;
      } else {
         p_55810_.m_7731_(p_55811_, p_55809_.m_61122_(f_55798_), 3);
         return InteractionResult.m_19078_(p_55810_.f_46443_);
      }
   }

   protected int m_6112_(BlockState p_55830_) {
      return p_55830_.m_61143_(f_55798_) * 2;
   }

   public BlockState m_5573_(BlockPlaceContext p_55803_) {
      BlockState blockstate = super.m_5573_(p_55803_);
      return blockstate.m_61124_(f_55797_, Boolean.valueOf(this.m_7346_(p_55803_.m_43725_(), p_55803_.m_8083_(), blockstate)));
   }

   public BlockState m_7417_(BlockState p_55821_, Direction p_55822_, BlockState p_55823_, LevelAccessor p_55824_, BlockPos p_55825_, BlockPos p_55826_) {
      return !p_55824_.m_5776_() && p_55822_.m_122434_() != p_55821_.m_61143_(f_54117_).m_122434_() ? p_55821_.m_61124_(f_55797_, Boolean.valueOf(this.m_7346_(p_55824_, p_55825_, p_55821_))) : super.m_7417_(p_55821_, p_55822_, p_55823_, p_55824_, p_55825_, p_55826_);
   }

   public boolean m_7346_(LevelReader p_55805_, BlockPos p_55806_, BlockState p_55807_) {
      return this.m_52547_(p_55805_, p_55806_, p_55807_) > 0;
   }

   protected boolean m_6137_(BlockState p_55832_) {
      return m_52586_(p_55832_);
   }

   public void m_7100_(BlockState p_55816_, Level p_55817_, BlockPos p_55818_, Random p_55819_) {
      if (p_55816_.m_61143_(f_52496_)) {
         Direction direction = p_55816_.m_61143_(f_54117_);
         double d0 = (double)p_55818_.m_123341_() + 0.5D + (p_55819_.nextDouble() - 0.5D) * 0.2D;
         double d1 = (double)p_55818_.m_123342_() + 0.4D + (p_55819_.nextDouble() - 0.5D) * 0.2D;
         double d2 = (double)p_55818_.m_123343_() + 0.5D + (p_55819_.nextDouble() - 0.5D) * 0.2D;
         float f = -5.0F;
         if (p_55819_.nextBoolean()) {
            f = (float)(p_55816_.m_61143_(f_55798_) * 2 - 1);
         }

         f = f / 16.0F;
         double d3 = (double)(f * (float)direction.m_122429_());
         double d4 = (double)(f * (float)direction.m_122431_());
         p_55817_.m_7106_(DustParticleOptions.f_123656_, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55828_) {
      p_55828_.m_61104_(f_54117_, f_55798_, f_55797_, f_52496_);
   }
}