package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetherPortalBlock extends Block {
   public static final EnumProperty<Direction.Axis> f_54904_ = BlockStateProperties.f_61364_;
   protected static final int f_153985_ = 2;
   protected static final VoxelShape f_54905_ = Block.m_49796_(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
   protected static final VoxelShape f_54906_ = Block.m_49796_(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

   public NetherPortalBlock(BlockBehaviour.Properties p_54909_) {
      super(p_54909_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54904_, Direction.Axis.X));
   }

   public VoxelShape m_5940_(BlockState p_54942_, BlockGetter p_54943_, BlockPos p_54944_, CollisionContext p_54945_) {
      switch((Direction.Axis)p_54942_.m_61143_(f_54904_)) {
      case Z:
         return f_54906_;
      case X:
      default:
         return f_54905_;
      }
   }

   public void m_7455_(BlockState p_54937_, ServerLevel p_54938_, BlockPos p_54939_, Random p_54940_) {
      if (p_54938_.m_6042_().m_63956_() && p_54938_.m_46469_().m_46207_(GameRules.f_46134_) && p_54940_.nextInt(2000) < p_54938_.m_46791_().m_19028_()) {
         while(p_54938_.m_8055_(p_54939_).m_60713_(this)) {
            p_54939_ = p_54939_.m_7495_();
         }

         if (p_54938_.m_8055_(p_54939_).m_60643_(p_54938_, p_54939_, EntityType.f_20531_)) {
            Entity entity = EntityType.f_20531_.m_20600_(p_54938_, (CompoundTag)null, (Component)null, (Player)null, p_54939_.m_7494_(), MobSpawnType.STRUCTURE, false, false);
            if (entity != null) {
               entity.m_20091_();
            }
         }
      }

   }

   public BlockState m_7417_(BlockState p_54928_, Direction p_54929_, BlockState p_54930_, LevelAccessor p_54931_, BlockPos p_54932_, BlockPos p_54933_) {
      Direction.Axis direction$axis = p_54929_.m_122434_();
      Direction.Axis direction$axis1 = p_54928_.m_61143_(f_54904_);
      boolean flag = direction$axis1 != direction$axis && direction$axis.m_122479_();
      return !flag && !p_54930_.m_60713_(this) && !(new PortalShape(p_54931_, p_54932_, direction$axis1)).m_77744_() ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_54928_, p_54929_, p_54930_, p_54931_, p_54932_, p_54933_);
   }

   public void m_7892_(BlockState p_54915_, Level p_54916_, BlockPos p_54917_, Entity p_54918_) {
      if (!p_54918_.m_20159_() && !p_54918_.m_20160_() && p_54918_.m_6072_()) {
         p_54918_.m_20221_(p_54917_);
      }

   }

   public void m_7100_(BlockState p_54920_, Level p_54921_, BlockPos p_54922_, Random p_54923_) {
      if (p_54923_.nextInt(100) == 0) {
         p_54921_.m_7785_((double)p_54922_.m_123341_() + 0.5D, (double)p_54922_.m_123342_() + 0.5D, (double)p_54922_.m_123343_() + 0.5D, SoundEvents.f_12286_, SoundSource.BLOCKS, 0.5F, p_54923_.nextFloat() * 0.4F + 0.8F, false);
      }

      for(int i = 0; i < 4; ++i) {
         double d0 = (double)p_54922_.m_123341_() + p_54923_.nextDouble();
         double d1 = (double)p_54922_.m_123342_() + p_54923_.nextDouble();
         double d2 = (double)p_54922_.m_123343_() + p_54923_.nextDouble();
         double d3 = ((double)p_54923_.nextFloat() - 0.5D) * 0.5D;
         double d4 = ((double)p_54923_.nextFloat() - 0.5D) * 0.5D;
         double d5 = ((double)p_54923_.nextFloat() - 0.5D) * 0.5D;
         int j = p_54923_.nextInt(2) * 2 - 1;
         if (!p_54921_.m_8055_(p_54922_.m_142125_()).m_60713_(this) && !p_54921_.m_8055_(p_54922_.m_142126_()).m_60713_(this)) {
            d0 = (double)p_54922_.m_123341_() + 0.5D + 0.25D * (double)j;
            d3 = (double)(p_54923_.nextFloat() * 2.0F * (float)j);
         } else {
            d2 = (double)p_54922_.m_123343_() + 0.5D + 0.25D * (double)j;
            d5 = (double)(p_54923_.nextFloat() * 2.0F * (float)j);
         }

         p_54921_.m_7106_(ParticleTypes.f_123760_, d0, d1, d2, d3, d4, d5);
      }

   }

   public ItemStack m_7397_(BlockGetter p_54911_, BlockPos p_54912_, BlockState p_54913_) {
      return ItemStack.f_41583_;
   }

   public BlockState m_6843_(BlockState p_54925_, Rotation p_54926_) {
      switch(p_54926_) {
      case COUNTERCLOCKWISE_90:
      case CLOCKWISE_90:
         switch((Direction.Axis)p_54925_.m_61143_(f_54904_)) {
         case Z:
            return p_54925_.m_61124_(f_54904_, Direction.Axis.X);
         case X:
            return p_54925_.m_61124_(f_54904_, Direction.Axis.Z);
         default:
            return p_54925_;
         }
      default:
         return p_54925_;
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54935_) {
      p_54935_.m_61104_(f_54904_);
   }
}