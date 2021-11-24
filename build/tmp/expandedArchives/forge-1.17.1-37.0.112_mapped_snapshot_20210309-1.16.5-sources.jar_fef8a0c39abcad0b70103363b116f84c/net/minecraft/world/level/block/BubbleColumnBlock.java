package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BubbleColumnBlock extends Block implements BucketPickup {
   public static final BooleanProperty f_50956_ = BlockStateProperties.f_61430_;
   private static final int f_152700_ = 5;

   public BubbleColumnBlock(BlockBehaviour.Properties p_50959_) {
      super(p_50959_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_50956_, Boolean.valueOf(true)));
   }

   public void m_7892_(BlockState p_50976_, Level p_50977_, BlockPos p_50978_, Entity p_50979_) {
      BlockState blockstate = p_50977_.m_8055_(p_50978_.m_7494_());
      if (blockstate.m_60795_()) {
         p_50979_.m_6845_(p_50976_.m_61143_(f_50956_));
         if (!p_50977_.f_46443_) {
            ServerLevel serverlevel = (ServerLevel)p_50977_;

            for(int i = 0; i < 2; ++i) {
               serverlevel.m_8767_(ParticleTypes.f_123769_, (double)p_50978_.m_123341_() + p_50977_.f_46441_.nextDouble(), (double)(p_50978_.m_123342_() + 1), (double)p_50978_.m_123343_() + p_50977_.f_46441_.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
               serverlevel.m_8767_(ParticleTypes.f_123795_, (double)p_50978_.m_123341_() + p_50977_.f_46441_.nextDouble(), (double)(p_50978_.m_123342_() + 1), (double)p_50978_.m_123343_() + p_50977_.f_46441_.nextDouble(), 1, 0.0D, 0.01D, 0.0D, 0.2D);
            }
         }
      } else {
         p_50979_.m_20321_(p_50976_.m_61143_(f_50956_));
      }

   }

   public void m_7458_(BlockState p_50971_, ServerLevel p_50972_, BlockPos p_50973_, Random p_50974_) {
      m_152702_(p_50972_, p_50973_, p_50971_, p_50972_.m_8055_(p_50973_.m_7495_()));
   }

   public FluidState m_5888_(BlockState p_51016_) {
      return Fluids.f_76193_.m_76068_(false);
   }

   public static void m_152707_(LevelAccessor p_152708_, BlockPos p_152709_, BlockState p_152710_) {
      m_152702_(p_152708_, p_152709_, p_152708_.m_8055_(p_152709_), p_152710_);
   }

   public static void m_152702_(LevelAccessor p_152703_, BlockPos p_152704_, BlockState p_152705_, BlockState p_152706_) {
      if (m_152715_(p_152705_)) {
         BlockState blockstate = m_152717_(p_152706_);
         p_152703_.m_7731_(p_152704_, blockstate, 2);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = p_152704_.m_122032_().m_122173_(Direction.UP);

         while(m_152715_(p_152703_.m_8055_(blockpos$mutableblockpos))) {
            if (!p_152703_.m_7731_(blockpos$mutableblockpos, blockstate, 2)) {
               return;
            }

            blockpos$mutableblockpos.m_122173_(Direction.UP);
         }

      }
   }

   private static boolean m_152715_(BlockState p_152716_) {
      return p_152716_.m_60713_(Blocks.f_50628_) || p_152716_.m_60713_(Blocks.f_49990_) && p_152716_.m_60819_().m_76186_() >= 8 && p_152716_.m_60819_().m_76170_();
   }

   private static BlockState m_152717_(BlockState p_152718_) {
      if (p_152718_.m_60713_(Blocks.f_50628_)) {
         return p_152718_;
      } else if (p_152718_.m_60713_(Blocks.f_50135_)) {
         return Blocks.f_50628_.m_49966_().m_61124_(f_50956_, Boolean.valueOf(false));
      } else {
         return p_152718_.m_60713_(Blocks.f_50450_) ? Blocks.f_50628_.m_49966_().m_61124_(f_50956_, Boolean.valueOf(true)) : Blocks.f_49990_.m_49966_();
      }
   }

   public void m_7100_(BlockState p_50981_, Level p_50982_, BlockPos p_50983_, Random p_50984_) {
      double d0 = (double)p_50983_.m_123341_();
      double d1 = (double)p_50983_.m_123342_();
      double d2 = (double)p_50983_.m_123343_();
      if (p_50981_.m_61143_(f_50956_)) {
         p_50982_.m_7107_(ParticleTypes.f_123773_, d0 + 0.5D, d1 + 0.8D, d2, 0.0D, 0.0D, 0.0D);
         if (p_50984_.nextInt(200) == 0) {
            p_50982_.m_7785_(d0, d1, d2, SoundEvents.f_11776_, SoundSource.BLOCKS, 0.2F + p_50984_.nextFloat() * 0.2F, 0.9F + p_50984_.nextFloat() * 0.15F, false);
         }
      } else {
         p_50982_.m_7107_(ParticleTypes.f_123774_, d0 + 0.5D, d1, d2 + 0.5D, 0.0D, 0.04D, 0.0D);
         p_50982_.m_7107_(ParticleTypes.f_123774_, d0 + (double)p_50984_.nextFloat(), d1 + (double)p_50984_.nextFloat(), d2 + (double)p_50984_.nextFloat(), 0.0D, 0.04D, 0.0D);
         if (p_50984_.nextInt(200) == 0) {
            p_50982_.m_7785_(d0, d1, d2, SoundEvents.f_11774_, SoundSource.BLOCKS, 0.2F + p_50984_.nextFloat() * 0.2F, 0.9F + p_50984_.nextFloat() * 0.15F, false);
         }
      }

   }

   public BlockState m_7417_(BlockState p_50990_, Direction p_50991_, BlockState p_50992_, LevelAccessor p_50993_, BlockPos p_50994_, BlockPos p_50995_) {
      p_50993_.m_6217_().m_5945_(p_50994_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_50993_));
      if (!p_50990_.m_60710_(p_50993_, p_50994_) || p_50991_ == Direction.DOWN || p_50991_ == Direction.UP && !p_50992_.m_60713_(Blocks.f_50628_) && m_152715_(p_50992_)) {
         p_50993_.m_6219_().m_5945_(p_50994_, this, 5);
      }

      return super.m_7417_(p_50990_, p_50991_, p_50992_, p_50993_, p_50994_, p_50995_);
   }

   public boolean m_7898_(BlockState p_50986_, LevelReader p_50987_, BlockPos p_50988_) {
      BlockState blockstate = p_50987_.m_8055_(p_50988_.m_7495_());
      return blockstate.m_60713_(Blocks.f_50628_) || blockstate.m_60713_(Blocks.f_50450_) || blockstate.m_60713_(Blocks.f_50135_);
   }

   public VoxelShape m_5940_(BlockState p_51005_, BlockGetter p_51006_, BlockPos p_51007_, CollisionContext p_51008_) {
      return Shapes.m_83040_();
   }

   public RenderShape m_7514_(BlockState p_51003_) {
      return RenderShape.INVISIBLE;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_50997_) {
      p_50997_.m_61104_(f_50956_);
   }

   public ItemStack m_142598_(LevelAccessor p_152712_, BlockPos p_152713_, BlockState p_152714_) {
      p_152712_.m_7731_(p_152713_, Blocks.f_50016_.m_49966_(), 11);
      return new ItemStack(Items.f_42447_);
   }

   public Optional<SoundEvent> m_142298_() {
      return Fluids.f_76193_.m_142520_();
   }
}