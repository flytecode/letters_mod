package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BaseFireBlock extends Block {
   private static final int f_152137_ = 8;
   private final float f_49238_;
   protected static final float f_152136_ = 1.0F;
   protected static final VoxelShape f_49237_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

   public BaseFireBlock(BlockBehaviour.Properties p_49241_, float p_49242_) {
      super(p_49241_);
      this.f_49238_ = p_49242_;
   }

   public BlockState m_5573_(BlockPlaceContext p_49244_) {
      return m_49245_(p_49244_.m_43725_(), p_49244_.m_8083_());
   }

   public static BlockState m_49245_(BlockGetter p_49246_, BlockPos p_49247_) {
      BlockPos blockpos = p_49247_.m_7495_();
      BlockState blockstate = p_49246_.m_8055_(blockpos);
      return SoulFireBlock.m_154650_(blockstate) ? Blocks.f_50084_.m_49966_() : ((FireBlock)Blocks.f_50083_).m_53470_(p_49246_, p_49247_);
   }

   public VoxelShape m_5940_(BlockState p_49274_, BlockGetter p_49275_, BlockPos p_49276_, CollisionContext p_49277_) {
      return f_49237_;
   }

   public void m_7100_(BlockState p_49265_, Level p_49266_, BlockPos p_49267_, Random p_49268_) {
      if (p_49268_.nextInt(24) == 0) {
         p_49266_.m_7785_((double)p_49267_.m_123341_() + 0.5D, (double)p_49267_.m_123342_() + 0.5D, (double)p_49267_.m_123343_() + 0.5D, SoundEvents.f_11936_, SoundSource.BLOCKS, 1.0F + p_49268_.nextFloat(), p_49268_.nextFloat() * 0.7F + 0.3F, false);
      }

      BlockPos blockpos = p_49267_.m_7495_();
      BlockState blockstate = p_49266_.m_8055_(blockpos);
      if (!this.m_7599_(blockstate) && !blockstate.m_60783_(p_49266_, blockpos, Direction.UP)) {
         if (this.m_7599_(p_49266_.m_8055_(p_49267_.m_142125_()))) {
            for(int j = 0; j < 2; ++j) {
               double d3 = (double)p_49267_.m_123341_() + p_49268_.nextDouble() * (double)0.1F;
               double d8 = (double)p_49267_.m_123342_() + p_49268_.nextDouble();
               double d13 = (double)p_49267_.m_123343_() + p_49268_.nextDouble();
               p_49266_.m_7106_(ParticleTypes.f_123755_, d3, d8, d13, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.m_7599_(p_49266_.m_8055_(p_49267_.m_142126_()))) {
            for(int k = 0; k < 2; ++k) {
               double d4 = (double)(p_49267_.m_123341_() + 1) - p_49268_.nextDouble() * (double)0.1F;
               double d9 = (double)p_49267_.m_123342_() + p_49268_.nextDouble();
               double d14 = (double)p_49267_.m_123343_() + p_49268_.nextDouble();
               p_49266_.m_7106_(ParticleTypes.f_123755_, d4, d9, d14, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.m_7599_(p_49266_.m_8055_(p_49267_.m_142127_()))) {
            for(int l = 0; l < 2; ++l) {
               double d5 = (double)p_49267_.m_123341_() + p_49268_.nextDouble();
               double d10 = (double)p_49267_.m_123342_() + p_49268_.nextDouble();
               double d15 = (double)p_49267_.m_123343_() + p_49268_.nextDouble() * (double)0.1F;
               p_49266_.m_7106_(ParticleTypes.f_123755_, d5, d10, d15, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.m_7599_(p_49266_.m_8055_(p_49267_.m_142128_()))) {
            for(int i1 = 0; i1 < 2; ++i1) {
               double d6 = (double)p_49267_.m_123341_() + p_49268_.nextDouble();
               double d11 = (double)p_49267_.m_123342_() + p_49268_.nextDouble();
               double d16 = (double)(p_49267_.m_123343_() + 1) - p_49268_.nextDouble() * (double)0.1F;
               p_49266_.m_7106_(ParticleTypes.f_123755_, d6, d11, d16, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.m_7599_(p_49266_.m_8055_(p_49267_.m_7494_()))) {
            for(int j1 = 0; j1 < 2; ++j1) {
               double d7 = (double)p_49267_.m_123341_() + p_49268_.nextDouble();
               double d12 = (double)(p_49267_.m_123342_() + 1) - p_49268_.nextDouble() * (double)0.1F;
               double d17 = (double)p_49267_.m_123343_() + p_49268_.nextDouble();
               p_49266_.m_7106_(ParticleTypes.f_123755_, d7, d12, d17, 0.0D, 0.0D, 0.0D);
            }
         }
      } else {
         for(int i = 0; i < 3; ++i) {
            double d0 = (double)p_49267_.m_123341_() + p_49268_.nextDouble();
            double d1 = (double)p_49267_.m_123342_() + p_49268_.nextDouble() * 0.5D + 0.5D;
            double d2 = (double)p_49267_.m_123343_() + p_49268_.nextDouble();
            p_49266_.m_7106_(ParticleTypes.f_123755_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   protected abstract boolean m_7599_(BlockState p_49284_);

   public void m_7892_(BlockState p_49260_, Level p_49261_, BlockPos p_49262_, Entity p_49263_) {
      if (!p_49263_.m_5825_()) {
         p_49263_.m_7311_(p_49263_.m_20094_() + 1);
         if (p_49263_.m_20094_() == 0) {
            p_49263_.m_20254_(8);
         }

         p_49263_.m_6469_(DamageSource.f_19305_, this.f_49238_);
      }

      super.m_7892_(p_49260_, p_49261_, p_49262_, p_49263_);
   }

   public void m_6807_(BlockState p_49279_, Level p_49280_, BlockPos p_49281_, BlockState p_49282_, boolean p_49283_) {
      if (!p_49282_.m_60713_(p_49279_.m_60734_())) {
         if (m_49248_(p_49280_)) {
            Optional<PortalShape> optional = PortalShape.m_77708_(p_49280_, p_49281_, Direction.Axis.X);
            optional = net.minecraftforge.event.ForgeEventFactory.onTrySpawnPortal(p_49280_, p_49281_, optional);
            if (optional.isPresent()) {
               optional.get().m_77743_();
               return;
            }
         }

         if (!p_49279_.m_60710_(p_49280_, p_49281_)) {
            p_49280_.m_7471_(p_49281_, false);
         }

      }
   }

   private static boolean m_49248_(Level p_49249_) {
      return p_49249_.m_46472_() == Level.f_46428_ || p_49249_.m_46472_() == Level.f_46429_;
   }

   protected void m_142387_(Level p_152139_, Player p_152140_, BlockPos p_152141_, BlockState p_152142_) {
   }

   public void m_5707_(Level p_49251_, BlockPos p_49252_, BlockState p_49253_, Player p_49254_) {
      if (!p_49251_.m_5776_()) {
         p_49251_.m_5898_((Player)null, 1009, p_49252_, 0);
      }

      super.m_5707_(p_49251_, p_49252_, p_49253_, p_49254_);
   }

   public static boolean m_49255_(Level p_49256_, BlockPos p_49257_, Direction p_49258_) {
      BlockState blockstate = p_49256_.m_8055_(p_49257_);
      if (!blockstate.m_60795_()) {
         return false;
      } else {
         return m_49245_(p_49256_, p_49257_).m_60710_(p_49256_, p_49257_) || m_49269_(p_49256_, p_49257_, p_49258_);
      }
   }

   private static boolean m_49269_(Level p_49270_, BlockPos p_49271_, Direction p_49272_) {
      if (!m_49248_(p_49270_)) {
         return false;
      } else {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = p_49271_.m_122032_();
         boolean flag = false;

         for(Direction direction : Direction.values()) {
            if (p_49270_.m_8055_(blockpos$mutableblockpos.m_122190_(p_49271_).m_122173_(direction)).m_60713_(Blocks.f_50080_)) {
               flag = true;
               break;
            }
         }

         if (!flag) {
            return false;
         } else {
            Direction.Axis direction$axis = p_49272_.m_122434_().m_122479_() ? p_49272_.m_122428_().m_122434_() : Direction.Plane.HORIZONTAL.m_122562_(p_49270_.f_46441_);
            return PortalShape.m_77708_(p_49270_, p_49271_, direction$axis).isPresent();
         }
      }
   }
}
