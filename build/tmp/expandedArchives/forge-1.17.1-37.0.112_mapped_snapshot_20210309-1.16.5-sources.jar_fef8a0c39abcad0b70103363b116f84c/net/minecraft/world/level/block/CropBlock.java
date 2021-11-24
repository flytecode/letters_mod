package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CropBlock extends BushBlock implements BonemealableBlock {
   public static final int f_153107_ = 7;
   public static final IntegerProperty f_52244_ = BlockStateProperties.f_61409_;
   private static final VoxelShape[] f_52243_ = new VoxelShape[]{Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

   public CropBlock(BlockBehaviour.Properties p_52247_) {
      super(p_52247_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(this.m_7959_(), Integer.valueOf(0)));
   }

   public VoxelShape m_5940_(BlockState p_52297_, BlockGetter p_52298_, BlockPos p_52299_, CollisionContext p_52300_) {
      return f_52243_[p_52297_.m_61143_(this.m_7959_())];
   }

   protected boolean m_6266_(BlockState p_52302_, BlockGetter p_52303_, BlockPos p_52304_) {
      return p_52302_.m_60713_(Blocks.f_50093_);
   }

   public IntegerProperty m_7959_() {
      return f_52244_;
   }

   public int m_7419_() {
      return 7;
   }

   protected int m_52305_(BlockState p_52306_) {
      return p_52306_.m_61143_(this.m_7959_());
   }

   public BlockState m_52289_(int p_52290_) {
      return this.m_49966_().m_61124_(this.m_7959_(), Integer.valueOf(p_52290_));
   }

   public boolean m_52307_(BlockState p_52308_) {
      return p_52308_.m_61143_(this.m_7959_()) >= this.m_7419_();
   }

   public boolean m_6724_(BlockState p_52288_) {
      return !this.m_52307_(p_52288_);
   }

   public void m_7455_(BlockState p_52292_, ServerLevel p_52293_, BlockPos p_52294_, Random p_52295_) {
      if (!p_52293_.isAreaLoaded(p_52294_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
      if (p_52293_.m_45524_(p_52294_, 0) >= 9) {
         int i = this.m_52305_(p_52292_);
         if (i < this.m_7419_()) {
            float f = m_52272_(this, p_52293_, p_52294_);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_52293_, p_52294_, p_52292_, p_52295_.nextInt((int)(25.0F / f) + 1) == 0)) {
               p_52293_.m_7731_(p_52294_, this.m_52289_(i + 1), 2);
               net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_52293_, p_52294_, p_52292_);
            }
         }
      }

   }

   public void m_52263_(Level p_52264_, BlockPos p_52265_, BlockState p_52266_) {
      int i = this.m_52305_(p_52266_) + this.m_7125_(p_52264_);
      int j = this.m_7419_();
      if (i > j) {
         i = j;
      }

      p_52264_.m_7731_(p_52265_, this.m_52289_(i), 2);
   }

   protected int m_7125_(Level p_52262_) {
      return Mth.m_14072_(p_52262_.f_46441_, 2, 5);
   }

   protected static float m_52272_(Block p_52273_, BlockGetter p_52274_, BlockPos p_52275_) {
      float f = 1.0F;
      BlockPos blockpos = p_52275_.m_7495_();

      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            float f1 = 0.0F;
            BlockState blockstate = p_52274_.m_8055_(blockpos.m_142082_(i, 0, j));
            if (blockstate.canSustainPlant(p_52274_, blockpos.m_142082_(i, 0, j), net.minecraft.core.Direction.UP, (net.minecraftforge.common.IPlantable) p_52273_)) {
               f1 = 1.0F;
               if (blockstate.isFertile(p_52274_, p_52275_.m_142082_(i, 0, j))) {
                  f1 = 3.0F;
               }
            }

            if (i != 0 || j != 0) {
               f1 /= 4.0F;
            }

            f += f1;
         }
      }

      BlockPos blockpos1 = p_52275_.m_142127_();
      BlockPos blockpos2 = p_52275_.m_142128_();
      BlockPos blockpos3 = p_52275_.m_142125_();
      BlockPos blockpos4 = p_52275_.m_142126_();
      boolean flag = p_52274_.m_8055_(blockpos3).m_60713_(p_52273_) || p_52274_.m_8055_(blockpos4).m_60713_(p_52273_);
      boolean flag1 = p_52274_.m_8055_(blockpos1).m_60713_(p_52273_) || p_52274_.m_8055_(blockpos2).m_60713_(p_52273_);
      if (flag && flag1) {
         f /= 2.0F;
      } else {
         boolean flag2 = p_52274_.m_8055_(blockpos3.m_142127_()).m_60713_(p_52273_) || p_52274_.m_8055_(blockpos4.m_142127_()).m_60713_(p_52273_) || p_52274_.m_8055_(blockpos4.m_142128_()).m_60713_(p_52273_) || p_52274_.m_8055_(blockpos3.m_142128_()).m_60713_(p_52273_);
         if (flag2) {
            f /= 2.0F;
         }
      }

      return f;
   }

   public boolean m_7898_(BlockState p_52282_, LevelReader p_52283_, BlockPos p_52284_) {
      return (p_52283_.m_45524_(p_52284_, 0) >= 8 || p_52283_.m_45527_(p_52284_)) && super.m_7898_(p_52282_, p_52283_, p_52284_);
   }

   public void m_7892_(BlockState p_52277_, Level p_52278_, BlockPos p_52279_, Entity p_52280_) {
      if (p_52280_ instanceof Ravager && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(p_52278_, p_52280_)) {
         p_52278_.m_46953_(p_52279_, true, p_52280_);
      }

      super.m_7892_(p_52277_, p_52278_, p_52279_, p_52280_);
   }

   protected ItemLike m_6404_() {
      return Items.f_42404_;
   }

   public ItemStack m_7397_(BlockGetter p_52254_, BlockPos p_52255_, BlockState p_52256_) {
      return new ItemStack(this.m_6404_());
   }

   public boolean m_7370_(BlockGetter p_52258_, BlockPos p_52259_, BlockState p_52260_, boolean p_52261_) {
      return !this.m_52307_(p_52260_);
   }

   public boolean m_5491_(Level p_52268_, Random p_52269_, BlockPos p_52270_, BlockState p_52271_) {
      return true;
   }

   public void m_7719_(ServerLevel p_52249_, Random p_52250_, BlockPos p_52251_, BlockState p_52252_) {
      this.m_52263_(p_52249_, p_52251_, p_52252_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52286_) {
      p_52286_.m_61104_(f_52244_);
   }
}
