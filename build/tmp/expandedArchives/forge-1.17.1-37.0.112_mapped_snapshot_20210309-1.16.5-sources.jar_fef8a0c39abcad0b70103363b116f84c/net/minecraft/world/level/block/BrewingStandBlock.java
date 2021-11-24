package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrewingStandBlock extends BaseEntityBlock {
   public static final BooleanProperty[] f_50905_ = new BooleanProperty[]{BlockStateProperties.f_61436_, BlockStateProperties.f_61437_, BlockStateProperties.f_61438_};
   protected static final VoxelShape f_50906_ = Shapes.m_83110_(Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D), Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D));

   public BrewingStandBlock(BlockBehaviour.Properties p_50909_) {
      super(p_50909_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_50905_[0], Boolean.valueOf(false)).m_61124_(f_50905_[1], Boolean.valueOf(false)).m_61124_(f_50905_[2], Boolean.valueOf(false)));
   }

   public RenderShape m_7514_(BlockState p_50950_) {
      return RenderShape.MODEL;
   }

   public BlockEntity m_142194_(BlockPos p_152698_, BlockState p_152699_) {
      return new BrewingStandBlockEntity(p_152698_, p_152699_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152694_, BlockState p_152695_, BlockEntityType<T> p_152696_) {
      return p_152694_.f_46443_ ? null : m_152132_(p_152696_, BlockEntityType.f_58927_, BrewingStandBlockEntity::m_155285_);
   }

   public VoxelShape m_5940_(BlockState p_50952_, BlockGetter p_50953_, BlockPos p_50954_, CollisionContext p_50955_) {
      return f_50906_;
   }

   public InteractionResult m_6227_(BlockState p_50930_, Level p_50931_, BlockPos p_50932_, Player p_50933_, InteractionHand p_50934_, BlockHitResult p_50935_) {
      if (p_50931_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         BlockEntity blockentity = p_50931_.m_7702_(p_50932_);
         if (blockentity instanceof BrewingStandBlockEntity) {
            p_50933_.m_5893_((BrewingStandBlockEntity)blockentity);
            p_50933_.m_36220_(Stats.f_12948_);
         }

         return InteractionResult.CONSUME;
      }
   }

   public void m_6402_(Level p_50913_, BlockPos p_50914_, BlockState p_50915_, LivingEntity p_50916_, ItemStack p_50917_) {
      if (p_50917_.m_41788_()) {
         BlockEntity blockentity = p_50913_.m_7702_(p_50914_);
         if (blockentity instanceof BrewingStandBlockEntity) {
            ((BrewingStandBlockEntity)blockentity).m_58638_(p_50917_.m_41786_());
         }
      }

   }

   public void m_7100_(BlockState p_50943_, Level p_50944_, BlockPos p_50945_, Random p_50946_) {
      double d0 = (double)p_50945_.m_123341_() + 0.4D + (double)p_50946_.nextFloat() * 0.2D;
      double d1 = (double)p_50945_.m_123342_() + 0.7D + (double)p_50946_.nextFloat() * 0.3D;
      double d2 = (double)p_50945_.m_123343_() + 0.4D + (double)p_50946_.nextFloat() * 0.2D;
      p_50944_.m_7106_(ParticleTypes.f_123762_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
   }

   public void m_6810_(BlockState p_50937_, Level p_50938_, BlockPos p_50939_, BlockState p_50940_, boolean p_50941_) {
      if (!p_50937_.m_60713_(p_50940_.m_60734_())) {
         BlockEntity blockentity = p_50938_.m_7702_(p_50939_);
         if (blockentity instanceof BrewingStandBlockEntity) {
            Containers.m_19002_(p_50938_, p_50939_, (BrewingStandBlockEntity)blockentity);
         }

         super.m_6810_(p_50937_, p_50938_, p_50939_, p_50940_, p_50941_);
      }
   }

   public boolean m_7278_(BlockState p_50919_) {
      return true;
   }

   public int m_6782_(BlockState p_50926_, Level p_50927_, BlockPos p_50928_) {
      return AbstractContainerMenu.m_38918_(p_50927_.m_7702_(p_50928_));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_50948_) {
      p_50948_.m_61104_(f_50905_[0], f_50905_[1], f_50905_[2]);
   }

   public boolean m_7357_(BlockState p_50921_, BlockGetter p_50922_, BlockPos p_50923_, PathComputationType p_50924_) {
      return false;
   }
}