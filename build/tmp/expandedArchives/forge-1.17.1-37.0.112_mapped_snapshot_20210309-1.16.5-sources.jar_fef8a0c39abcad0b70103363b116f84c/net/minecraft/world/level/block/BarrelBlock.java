package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class BarrelBlock extends BaseEntityBlock {
   public static final DirectionProperty f_49042_ = BlockStateProperties.f_61372_;
   public static final BooleanProperty f_49043_ = BlockStateProperties.f_61446_;

   public BarrelBlock(BlockBehaviour.Properties p_49046_) {
      super(p_49046_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_49042_, Direction.NORTH).m_61124_(f_49043_, Boolean.valueOf(false)));
   }

   public InteractionResult m_6227_(BlockState p_49069_, Level p_49070_, BlockPos p_49071_, Player p_49072_, InteractionHand p_49073_, BlockHitResult p_49074_) {
      if (p_49070_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         BlockEntity blockentity = p_49070_.m_7702_(p_49071_);
         if (blockentity instanceof BarrelBlockEntity) {
            p_49072_.m_5893_((BarrelBlockEntity)blockentity);
            p_49072_.m_36220_(Stats.f_12971_);
            PiglinAi.m_34873_(p_49072_, true);
         }

         return InteractionResult.CONSUME;
      }
   }

   public void m_6810_(BlockState p_49076_, Level p_49077_, BlockPos p_49078_, BlockState p_49079_, boolean p_49080_) {
      if (!p_49076_.m_60713_(p_49079_.m_60734_())) {
         BlockEntity blockentity = p_49077_.m_7702_(p_49078_);
         if (blockentity instanceof Container) {
            Containers.m_19002_(p_49077_, p_49078_, (Container)blockentity);
            p_49077_.m_46717_(p_49078_, this);
         }

         super.m_6810_(p_49076_, p_49077_, p_49078_, p_49079_, p_49080_);
      }
   }

   public void m_7458_(BlockState p_49060_, ServerLevel p_49061_, BlockPos p_49062_, Random p_49063_) {
      BlockEntity blockentity = p_49061_.m_7702_(p_49062_);
      if (blockentity instanceof BarrelBlockEntity) {
         ((BarrelBlockEntity)blockentity).m_58619_();
      }

   }

   @Nullable
   public BlockEntity m_142194_(BlockPos p_152102_, BlockState p_152103_) {
      return new BarrelBlockEntity(p_152102_, p_152103_);
   }

   public RenderShape m_7514_(BlockState p_49090_) {
      return RenderShape.MODEL;
   }

   public void m_6402_(Level p_49052_, BlockPos p_49053_, BlockState p_49054_, @Nullable LivingEntity p_49055_, ItemStack p_49056_) {
      if (p_49056_.m_41788_()) {
         BlockEntity blockentity = p_49052_.m_7702_(p_49053_);
         if (blockentity instanceof BarrelBlockEntity) {
            ((BarrelBlockEntity)blockentity).m_58638_(p_49056_.m_41786_());
         }
      }

   }

   public boolean m_7278_(BlockState p_49058_) {
      return true;
   }

   public int m_6782_(BlockState p_49065_, Level p_49066_, BlockPos p_49067_) {
      return AbstractContainerMenu.m_38918_(p_49066_.m_7702_(p_49067_));
   }

   public BlockState m_6843_(BlockState p_49085_, Rotation p_49086_) {
      return p_49085_.m_61124_(f_49042_, p_49086_.m_55954_(p_49085_.m_61143_(f_49042_)));
   }

   public BlockState m_6943_(BlockState p_49082_, Mirror p_49083_) {
      return p_49082_.m_60717_(p_49083_.m_54846_(p_49082_.m_61143_(f_49042_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49088_) {
      p_49088_.m_61104_(f_49042_, f_49043_);
   }

   public BlockState m_5573_(BlockPlaceContext p_49048_) {
      return this.m_49966_().m_61124_(f_49042_, p_49048_.m_7820_().m_122424_());
   }
}