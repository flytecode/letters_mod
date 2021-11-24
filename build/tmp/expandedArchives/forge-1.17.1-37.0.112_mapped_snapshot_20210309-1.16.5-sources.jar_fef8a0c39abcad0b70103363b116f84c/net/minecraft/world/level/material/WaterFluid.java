package net.minecraft.world.level.material;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public abstract class WaterFluid extends FlowingFluid {
   public Fluid m_5615_() {
      return Fluids.f_76192_;
   }

   public Fluid m_5613_() {
      return Fluids.f_76193_;
   }

   public Item m_6859_() {
      return Items.f_42447_;
   }

   public void m_7450_(Level p_76445_, BlockPos p_76446_, FluidState p_76447_, Random p_76448_) {
      if (!p_76447_.m_76170_() && !p_76447_.m_61143_(f_75947_)) {
         if (p_76448_.nextInt(64) == 0) {
            p_76445_.m_7785_((double)p_76446_.m_123341_() + 0.5D, (double)p_76446_.m_123342_() + 0.5D, (double)p_76446_.m_123343_() + 0.5D, SoundEvents.f_12540_, SoundSource.BLOCKS, p_76448_.nextFloat() * 0.25F + 0.75F, p_76448_.nextFloat() + 0.5F, false);
         }
      } else if (p_76448_.nextInt(10) == 0) {
         p_76445_.m_7106_(ParticleTypes.f_123768_, (double)p_76446_.m_123341_() + p_76448_.nextDouble(), (double)p_76446_.m_123342_() + p_76448_.nextDouble(), (double)p_76446_.m_123343_() + p_76448_.nextDouble(), 0.0D, 0.0D, 0.0D);
      }

   }

   @Nullable
   public ParticleOptions m_7792_() {
      return ParticleTypes.f_123803_;
   }

   protected boolean m_6760_() {
      return true;
   }

   protected void m_7456_(LevelAccessor p_76450_, BlockPos p_76451_, BlockState p_76452_) {
      BlockEntity blockentity = p_76452_.m_155947_() ? p_76450_.m_7702_(p_76451_) : null;
      Block.m_49892_(p_76452_, p_76450_, p_76451_, blockentity);
   }

   public int m_6719_(LevelReader p_76464_) {
      return 4;
   }

   public BlockState m_5804_(FluidState p_76466_) {
      return Blocks.f_49990_.m_49966_().m_61124_(LiquidBlock.f_54688_, Integer.valueOf(m_76092_(p_76466_)));
   }

   public boolean m_6212_(Fluid p_76456_) {
      return p_76456_ == Fluids.f_76193_ || p_76456_ == Fluids.f_76192_;
   }

   public int m_6713_(LevelReader p_76469_) {
      return 1;
   }

   public int m_6718_(LevelReader p_76454_) {
      return 5;
   }

   public boolean m_5486_(FluidState p_76458_, BlockGetter p_76459_, BlockPos p_76460_, Fluid p_76461_, Direction p_76462_) {
      return p_76462_ == Direction.DOWN && !p_76461_.m_76108_(FluidTags.f_13131_);
   }

   protected float m_6752_() {
      return 100.0F;
   }

   public Optional<SoundEvent> m_142520_() {
      return Optional.of(SoundEvents.f_11781_);
   }

   public static class Flowing extends WaterFluid {
      protected void m_7180_(StateDefinition.Builder<Fluid, FluidState> p_76476_) {
         super.m_7180_(p_76476_);
         p_76476_.m_61104_(f_75948_);
      }

      public int m_7430_(FluidState p_76480_) {
         return p_76480_.m_61143_(f_75948_);
      }

      public boolean m_7444_(FluidState p_76478_) {
         return false;
      }
   }

   public static class Source extends WaterFluid {
      public int m_7430_(FluidState p_76485_) {
         return 8;
      }

      public boolean m_7444_(FluidState p_76483_) {
         return true;
      }
   }
}