package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MagmaBlock extends Block {
   private static final int f_153775_ = 20;

   public MagmaBlock(BlockBehaviour.Properties p_54800_) {
      super(p_54800_);
   }

   public void m_141947_(Level p_153777_, BlockPos p_153778_, BlockState p_153779_, Entity p_153780_) {
      if (!p_153780_.m_5825_() && p_153780_ instanceof LivingEntity && !EnchantmentHelper.m_44938_((LivingEntity)p_153780_)) {
         p_153780_.m_6469_(DamageSource.f_19309_, 1.0F);
      }

      super.m_141947_(p_153777_, p_153778_, p_153779_, p_153780_);
   }

   public void m_7458_(BlockState p_54806_, ServerLevel p_54807_, BlockPos p_54808_, Random p_54809_) {
      BubbleColumnBlock.m_152707_(p_54807_, p_54808_.m_7494_(), p_54806_);
   }

   public BlockState m_7417_(BlockState p_54811_, Direction p_54812_, BlockState p_54813_, LevelAccessor p_54814_, BlockPos p_54815_, BlockPos p_54816_) {
      if (p_54812_ == Direction.UP && p_54813_.m_60713_(Blocks.f_49990_)) {
         p_54814_.m_6219_().m_5945_(p_54815_, this, 20);
      }

      return super.m_7417_(p_54811_, p_54812_, p_54813_, p_54814_, p_54815_, p_54816_);
   }

   public void m_7455_(BlockState p_54818_, ServerLevel p_54819_, BlockPos p_54820_, Random p_54821_) {
      BlockPos blockpos = p_54820_.m_7494_();
      if (p_54819_.m_6425_(p_54820_).m_76153_(FluidTags.f_13131_)) {
         p_54819_.m_5594_((Player)null, p_54820_, SoundEvents.f_11937_, SoundSource.BLOCKS, 0.5F, 2.6F + (p_54819_.f_46441_.nextFloat() - p_54819_.f_46441_.nextFloat()) * 0.8F);
         p_54819_.m_8767_(ParticleTypes.f_123755_, (double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_() + 0.25D, (double)blockpos.m_123343_() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);
      }

   }

   public void m_6807_(BlockState p_54823_, Level p_54824_, BlockPos p_54825_, BlockState p_54826_, boolean p_54827_) {
      p_54824_.m_6219_().m_5945_(p_54825_, this, 20);
   }
}