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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public abstract class LavaFluid extends FlowingFluid {
   public static final float f_164528_ = 0.44444445F;

   public Fluid m_5615_() {
      return Fluids.f_76194_;
   }

   public Fluid m_5613_() {
      return Fluids.f_76195_;
   }

   public Item m_6859_() {
      return Items.f_42448_;
   }

   public void m_7450_(Level p_76208_, BlockPos p_76209_, FluidState p_76210_, Random p_76211_) {
      BlockPos blockpos = p_76209_.m_7494_();
      if (p_76208_.m_8055_(blockpos).m_60795_() && !p_76208_.m_8055_(blockpos).m_60804_(p_76208_, blockpos)) {
         if (p_76211_.nextInt(100) == 0) {
            double d0 = (double)p_76209_.m_123341_() + p_76211_.nextDouble();
            double d1 = (double)p_76209_.m_123342_() + 1.0D;
            double d2 = (double)p_76209_.m_123343_() + p_76211_.nextDouble();
            p_76208_.m_7106_(ParticleTypes.f_123756_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            p_76208_.m_7785_(d0, d1, d2, SoundEvents.f_12032_, SoundSource.BLOCKS, 0.2F + p_76211_.nextFloat() * 0.2F, 0.9F + p_76211_.nextFloat() * 0.15F, false);
         }

         if (p_76211_.nextInt(200) == 0) {
            p_76208_.m_7785_((double)p_76209_.m_123341_(), (double)p_76209_.m_123342_(), (double)p_76209_.m_123343_(), SoundEvents.f_12030_, SoundSource.BLOCKS, 0.2F + p_76211_.nextFloat() * 0.2F, 0.9F + p_76211_.nextFloat() * 0.15F, false);
         }
      }

   }

   public void m_7449_(Level p_76239_, BlockPos p_76240_, FluidState p_76241_, Random p_76242_) {
      if (p_76239_.m_46469_().m_46207_(GameRules.f_46131_)) {
         int i = p_76242_.nextInt(3);
         if (i > 0) {
            BlockPos blockpos = p_76240_;

            for(int j = 0; j < i; ++j) {
               blockpos = blockpos.m_142082_(p_76242_.nextInt(3) - 1, 1, p_76242_.nextInt(3) - 1);
               if (!p_76239_.m_46749_(blockpos)) {
                  return;
               }

               BlockState blockstate = p_76239_.m_8055_(blockpos);
               if (blockstate.m_60795_()) {
                  if (this.m_76227_(p_76239_, blockpos)) {
                     p_76239_.m_46597_(blockpos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_76239_, blockpos, p_76240_, Blocks.f_50083_.m_49966_()));
                     return;
                  }
               } else if (blockstate.m_60767_().m_76334_()) {
                  return;
               }
            }
         } else {
            for(int k = 0; k < 3; ++k) {
               BlockPos blockpos1 = p_76240_.m_142082_(p_76242_.nextInt(3) - 1, 0, p_76242_.nextInt(3) - 1);
               if (!p_76239_.m_46749_(blockpos1)) {
                  return;
               }

               if (p_76239_.m_46859_(blockpos1.m_7494_()) && this.isFlammable(p_76239_, blockpos1, Direction.UP)) {
                  p_76239_.m_46597_(blockpos1.m_7494_(), net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_76239_, blockpos1.m_7494_(), p_76240_, Blocks.f_50083_.m_49966_()));
               }
            }
         }

      }
   }

   private boolean m_76227_(LevelReader p_76228_, BlockPos p_76229_) {
      for(Direction direction : Direction.values()) {
         if (this.isFlammable(p_76228_, p_76229_.m_142300_(direction), direction.m_122424_())) {
            return true;
         }
      }

      return false;
   }

   /** @deprecated Forge: use {@link LavaFluid#isFlammable(LevelReader,BlockPos,Direction)} instead */
   @Deprecated
   private boolean m_76245_(LevelReader p_76246_, BlockPos p_76247_) {
      return p_76247_.m_123342_() >= p_76246_.m_141937_() && p_76247_.m_123342_() < p_76246_.m_151558_() && !p_76246_.m_46805_(p_76247_) ? false : p_76246_.m_8055_(p_76247_).m_60767_().m_76335_();
   }

   private boolean isFlammable(LevelReader level, BlockPos pos, Direction face) {
      return pos.m_123342_() >= level.m_141937_() && pos.m_123342_() < level.m_151558_() && !level.m_46805_(pos) ? false : level.m_8055_(pos).isFlammable(level, pos, face);
   }

   @Nullable
   public ParticleOptions m_7792_() {
      return ParticleTypes.f_123800_;
   }

   protected void m_7456_(LevelAccessor p_76216_, BlockPos p_76217_, BlockState p_76218_) {
      this.m_76212_(p_76216_, p_76217_);
   }

   public int m_6719_(LevelReader p_76244_) {
      return p_76244_.m_6042_().m_63951_() ? 4 : 2;
   }

   public BlockState m_5804_(FluidState p_76249_) {
      return Blocks.f_49991_.m_49966_().m_61124_(LiquidBlock.f_54688_, Integer.valueOf(m_76092_(p_76249_)));
   }

   public boolean m_6212_(Fluid p_76231_) {
      return p_76231_ == Fluids.f_76195_ || p_76231_ == Fluids.f_76194_;
   }

   public int m_6713_(LevelReader p_76252_) {
      return p_76252_.m_6042_().m_63951_() ? 1 : 2;
   }

   public boolean m_5486_(FluidState p_76233_, BlockGetter p_76234_, BlockPos p_76235_, Fluid p_76236_, Direction p_76237_) {
      return p_76233_.m_76155_(p_76234_, p_76235_) >= 0.44444445F && p_76236_.m_76108_(FluidTags.f_13131_);
   }

   public int m_6718_(LevelReader p_76226_) {
      return p_76226_.m_6042_().m_63951_() ? 10 : 30;
   }

   public int m_6886_(Level p_76203_, BlockPos p_76204_, FluidState p_76205_, FluidState p_76206_) {
      int i = this.m_6718_(p_76203_);
      if (!p_76205_.m_76178_() && !p_76206_.m_76178_() && !p_76205_.m_61143_(f_75947_) && !p_76206_.m_61143_(f_75947_) && p_76206_.m_76155_(p_76203_, p_76204_) > p_76205_.m_76155_(p_76203_, p_76204_) && p_76203_.m_5822_().nextInt(4) != 0) {
         i *= 4;
      }

      return i;
   }

   private void m_76212_(LevelAccessor p_76213_, BlockPos p_76214_) {
      p_76213_.m_46796_(1501, p_76214_, 0);
   }

   protected boolean m_6760_() {
      return false;
   }

   protected void m_6364_(LevelAccessor p_76220_, BlockPos p_76221_, BlockState p_76222_, Direction p_76223_, FluidState p_76224_) {
      if (p_76223_ == Direction.DOWN) {
         FluidState fluidstate = p_76220_.m_6425_(p_76221_);
         if (this.m_76108_(FluidTags.f_13132_) && fluidstate.m_76153_(FluidTags.f_13131_)) {
            if (p_76222_.m_60734_() instanceof LiquidBlock) {
               p_76220_.m_7731_(p_76221_, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_76220_, p_76221_, p_76221_, Blocks.f_50069_.m_49966_()), 3);
            }

            this.m_76212_(p_76220_, p_76221_);
            return;
         }
      }

      super.m_6364_(p_76220_, p_76221_, p_76222_, p_76223_, p_76224_);
   }

   protected boolean m_6685_() {
      return true;
   }

   protected float m_6752_() {
      return 100.0F;
   }

   public Optional<SoundEvent> m_142520_() {
      return Optional.of(SoundEvents.f_11783_);
   }

   public static class Flowing extends LavaFluid {
      protected void m_7180_(StateDefinition.Builder<Fluid, FluidState> p_76260_) {
         super.m_7180_(p_76260_);
         p_76260_.m_61104_(f_75948_);
      }

      public int m_7430_(FluidState p_76264_) {
         return p_76264_.m_61143_(f_75948_);
      }

      public boolean m_7444_(FluidState p_76262_) {
         return false;
      }
   }

   public static class Source extends LavaFluid {
      public int m_7430_(FluidState p_76269_) {
         return 8;
      }

      public boolean m_7444_(FluidState p_76267_) {
         return true;
      }
   }
}
