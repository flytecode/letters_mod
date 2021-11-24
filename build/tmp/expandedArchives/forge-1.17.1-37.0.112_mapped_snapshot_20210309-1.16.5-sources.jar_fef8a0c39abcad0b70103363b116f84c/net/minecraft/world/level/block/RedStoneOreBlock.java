package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RedStoneOreBlock extends Block {
   public static final BooleanProperty f_55450_ = RedstoneTorchBlock.f_55674_;

   public RedStoneOreBlock(BlockBehaviour.Properties p_55453_) {
      super(p_55453_);
      this.m_49959_(this.m_49966_().m_61124_(f_55450_, Boolean.valueOf(false)));
   }

   public void m_6256_(BlockState p_55467_, Level p_55468_, BlockPos p_55469_, Player p_55470_) {
      m_55492_(p_55467_, p_55468_, p_55469_);
      super.m_6256_(p_55467_, p_55468_, p_55469_, p_55470_);
   }

   public void m_141947_(Level p_154299_, BlockPos p_154300_, BlockState p_154301_, Entity p_154302_) {
      m_55492_(p_154301_, p_154299_, p_154300_);
      super.m_141947_(p_154299_, p_154300_, p_154301_, p_154302_);
   }

   public InteractionResult m_6227_(BlockState p_55472_, Level p_55473_, BlockPos p_55474_, Player p_55475_, InteractionHand p_55476_, BlockHitResult p_55477_) {
      if (p_55473_.f_46443_) {
         m_55454_(p_55473_, p_55474_);
      } else {
         m_55492_(p_55472_, p_55473_, p_55474_);
      }

      ItemStack itemstack = p_55475_.m_21120_(p_55476_);
      return itemstack.m_41720_() instanceof BlockItem && (new BlockPlaceContext(p_55475_, p_55476_, itemstack, p_55477_)).m_7059_() ? InteractionResult.PASS : InteractionResult.SUCCESS;
   }

   private static void m_55492_(BlockState p_55493_, Level p_55494_, BlockPos p_55495_) {
      m_55454_(p_55494_, p_55495_);
      if (!p_55493_.m_61143_(f_55450_)) {
         p_55494_.m_7731_(p_55495_, p_55493_.m_61124_(f_55450_, Boolean.valueOf(true)), 3);
      }

   }

   public boolean m_6724_(BlockState p_55486_) {
      return p_55486_.m_61143_(f_55450_);
   }

   public void m_7455_(BlockState p_55488_, ServerLevel p_55489_, BlockPos p_55490_, Random p_55491_) {
      if (p_55488_.m_61143_(f_55450_)) {
         p_55489_.m_7731_(p_55490_, p_55488_.m_61124_(f_55450_, Boolean.valueOf(false)), 3);
      }

   }

   public void m_8101_(BlockState p_55462_, ServerLevel p_55463_, BlockPos p_55464_, ItemStack p_55465_) {
      super.m_8101_(p_55462_, p_55463_, p_55464_, p_55465_);
   }

   @Override
   public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos, int fortune, int silktouch) {
      return silktouch == 0 ? 1 + RANDOM.nextInt(5) : 0;
   }

   public void m_7100_(BlockState p_55479_, Level p_55480_, BlockPos p_55481_, Random p_55482_) {
      if (p_55479_.m_61143_(f_55450_)) {
         m_55454_(p_55480_, p_55481_);
      }

   }

   private static void m_55454_(Level p_55455_, BlockPos p_55456_) {
      double d0 = 0.5625D;
      Random random = p_55455_.f_46441_;

      for(Direction direction : Direction.values()) {
         BlockPos blockpos = p_55456_.m_142300_(direction);
         if (!p_55455_.m_8055_(blockpos).m_60804_(p_55455_, blockpos)) {
            Direction.Axis direction$axis = direction.m_122434_();
            double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.m_122429_() : (double)random.nextFloat();
            double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.m_122430_() : (double)random.nextFloat();
            double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.m_122431_() : (double)random.nextFloat();
            p_55455_.m_7106_(DustParticleOptions.f_123656_, (double)p_55456_.m_123341_() + d1, (double)p_55456_.m_123342_() + d2, (double)p_55456_.m_123343_() + d3, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55484_) {
      p_55484_.m_61104_(f_55450_);
   }
}
