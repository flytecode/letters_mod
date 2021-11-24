package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantHeadBlock extends GrowingPlantBlock implements BonemealableBlock {
   public static final IntegerProperty f_53924_ = BlockStateProperties.f_61411_;
   public static final int f_153328_ = 25;
   private final double f_53925_;

   protected GrowingPlantHeadBlock(BlockBehaviour.Properties p_53928_, Direction p_53929_, VoxelShape p_53930_, boolean p_53931_, double p_53932_) {
      super(p_53928_, p_53929_, p_53930_, p_53931_);
      this.f_53925_ = p_53932_;
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_53924_, Integer.valueOf(0)));
   }

   public BlockState m_7722_(LevelAccessor p_53949_) {
      return this.m_49966_().m_61124_(f_53924_, Integer.valueOf(p_53949_.m_5822_().nextInt(25)));
   }

   public boolean m_6724_(BlockState p_53961_) {
      return p_53961_.m_61143_(f_53924_) < 25;
   }

   public void m_7455_(BlockState p_53963_, ServerLevel p_53964_, BlockPos p_53965_, Random p_53966_) {
      if (p_53963_.m_61143_(f_53924_) < 25 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_53964_, p_53965_.m_142300_(this.f_53859_), p_53964_.m_8055_(p_53965_.m_142300_(this.f_53859_)),p_53966_.nextDouble() < this.f_53925_)) {
         BlockPos blockpos = p_53965_.m_142300_(this.f_53859_);
         if (this.m_5971_(p_53964_.m_8055_(blockpos))) {
            p_53964_.m_46597_(blockpos, this.m_142452_(p_53963_, p_53964_.f_46441_));
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_53964_, blockpos, p_53964_.m_8055_(blockpos));
         }
      }

   }

   protected BlockState m_142452_(BlockState p_153331_, Random p_153332_) {
      return p_153331_.m_61122_(f_53924_);
   }

   protected BlockState m_142643_(BlockState p_153329_, BlockState p_153330_) {
      return p_153330_;
   }

   public BlockState m_7417_(BlockState p_53951_, Direction p_53952_, BlockState p_53953_, LevelAccessor p_53954_, BlockPos p_53955_, BlockPos p_53956_) {
      if (p_53952_ == this.f_53859_.m_122424_() && !p_53951_.m_60710_(p_53954_, p_53955_)) {
         p_53954_.m_6219_().m_5945_(p_53955_, this, 1);
      }

      if (p_53952_ != this.f_53859_ || !p_53953_.m_60713_(this) && !p_53953_.m_60713_(this.m_7777_())) {
         if (this.f_53860_) {
            p_53954_.m_6217_().m_5945_(p_53955_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_53954_));
         }

         return super.m_7417_(p_53951_, p_53952_, p_53953_, p_53954_, p_53955_, p_53956_);
      } else {
         return this.m_142643_(p_53951_, this.m_7777_().m_49966_());
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53958_) {
      p_53958_.m_61104_(f_53924_);
   }

   public boolean m_7370_(BlockGetter p_53939_, BlockPos p_53940_, BlockState p_53941_, boolean p_53942_) {
      return this.m_5971_(p_53939_.m_8055_(p_53940_.m_142300_(this.f_53859_)));
   }

   public boolean m_5491_(Level p_53944_, Random p_53945_, BlockPos p_53946_, BlockState p_53947_) {
      return true;
   }

   public void m_7719_(ServerLevel p_53934_, Random p_53935_, BlockPos p_53936_, BlockState p_53937_) {
      BlockPos blockpos = p_53936_.m_142300_(this.f_53859_);
      int i = Math.min(p_53937_.m_61143_(f_53924_) + 1, 25);
      int j = this.m_7363_(p_53935_);

      for(int k = 0; k < j && this.m_5971_(p_53934_.m_8055_(blockpos)); ++k) {
         p_53934_.m_46597_(blockpos, p_53937_.m_61124_(f_53924_, Integer.valueOf(i)));
         blockpos = blockpos.m_142300_(this.f_53859_);
         i = Math.min(i + 1, 25);
      }

   }

   protected abstract int m_7363_(Random p_53959_);

   protected abstract boolean m_5971_(BlockState p_53968_);

   protected GrowingPlantHeadBlock m_7272_() {
      return this;
   }
}
