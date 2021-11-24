package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Tilt;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigDripleafBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
   private static final BooleanProperty f_152200_ = BlockStateProperties.f_61362_;
   private static final EnumProperty<Tilt> f_152201_ = BlockStateProperties.f_155996_;
   private static final int f_152202_ = -1;
   private static final Object2IntMap<Tilt> f_152203_ = Util.m_137469_(new Object2IntArrayMap<>(), (p_152305_) -> {
      p_152305_.defaultReturnValue(-1);
      p_152305_.put(Tilt.UNSTABLE, 10);
      p_152305_.put(Tilt.PARTIAL, 10);
      p_152305_.put(Tilt.FULL, 100);
   });
   private static final int f_152204_ = 5;
   private static final int f_152205_ = 6;
   private static final int f_152206_ = 11;
   private static final int f_152207_ = 13;
   private static final Map<Tilt, VoxelShape> f_152208_ = ImmutableMap.of(Tilt.NONE, Block.m_49796_(0.0D, 11.0D, 0.0D, 16.0D, 15.0D, 16.0D), Tilt.UNSTABLE, Block.m_49796_(0.0D, 11.0D, 0.0D, 16.0D, 15.0D, 16.0D), Tilt.PARTIAL, Block.m_49796_(0.0D, 11.0D, 0.0D, 16.0D, 13.0D, 16.0D), Tilt.FULL, Shapes.m_83040_());
   private static final VoxelShape f_152209_ = Block.m_49796_(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   private static final Map<Direction, VoxelShape> f_152210_ = ImmutableMap.of(Direction.NORTH, Shapes.m_83148_(BigDripleafStemBlock.f_152321_, f_152209_, BooleanOp.f_82685_), Direction.SOUTH, Shapes.m_83148_(BigDripleafStemBlock.f_152322_, f_152209_, BooleanOp.f_82685_), Direction.EAST, Shapes.m_83148_(BigDripleafStemBlock.f_152323_, f_152209_, BooleanOp.f_82685_), Direction.WEST, Shapes.m_83148_(BigDripleafStemBlock.f_152324_, f_152209_, BooleanOp.f_82685_));
   private final Map<BlockState, VoxelShape> f_152211_;

   public BigDripleafBlock(BlockBehaviour.Properties p_152214_) {
      super(p_152214_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_152200_, Boolean.valueOf(false)).m_61124_(f_54117_, Direction.NORTH).m_61124_(f_152201_, Tilt.NONE));
      this.f_152211_ = this.m_152458_(BigDripleafBlock::m_152317_);
   }

   private static VoxelShape m_152317_(BlockState p_152318_) {
      return Shapes.m_83110_(f_152208_.get(p_152318_.m_61143_(f_152201_)), f_152210_.get(p_152318_.m_61143_(f_54117_)));
   }

   public static void m_152246_(LevelAccessor p_152247_, Random p_152248_, BlockPos p_152249_, Direction p_152250_) {
      int i = Mth.m_14072_(p_152248_, 2, 5);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_152249_.m_122032_();
      int j = 0;

      while(j < i && m_152251_(p_152247_, blockpos$mutableblockpos, p_152247_.m_8055_(blockpos$mutableblockpos))) {
         ++j;
         blockpos$mutableblockpos.m_122173_(Direction.UP);
      }

      int k = p_152249_.m_123342_() + j - 1;
      blockpos$mutableblockpos.m_142448_(p_152249_.m_123342_());

      while(blockpos$mutableblockpos.m_123342_() < k) {
         BigDripleafStemBlock.m_152349_(p_152247_, blockpos$mutableblockpos, p_152247_.m_6425_(blockpos$mutableblockpos), p_152250_);
         blockpos$mutableblockpos.m_122173_(Direction.UP);
      }

      m_152241_(p_152247_, blockpos$mutableblockpos, p_152247_.m_6425_(blockpos$mutableblockpos), p_152250_);
   }

   private static boolean m_152319_(BlockState p_152320_) {
      return p_152320_.m_60795_() || p_152320_.m_60713_(Blocks.f_49990_) || p_152320_.m_60713_(Blocks.f_152547_);
   }

   protected static boolean m_152251_(LevelHeightAccessor p_152252_, BlockPos p_152253_, BlockState p_152254_) {
      return !p_152252_.m_151570_(p_152253_) && m_152319_(p_152254_);
   }

   protected static boolean m_152241_(LevelAccessor p_152242_, BlockPos p_152243_, FluidState p_152244_, Direction p_152245_) {
      BlockState blockstate = Blocks.f_152545_.m_49966_().m_61124_(f_152200_, Boolean.valueOf(p_152244_.m_164512_(Fluids.f_76193_))).m_61124_(f_54117_, p_152245_);
      return p_152242_.m_7731_(p_152243_, blockstate, 3);
   }

   public void m_5581_(Level p_152228_, BlockState p_152229_, BlockHitResult p_152230_, Projectile p_152231_) {
      this.m_152282_(p_152229_, p_152228_, p_152230_.m_82425_(), Tilt.FULL, SoundEvents.f_144131_);
   }

   public FluidState m_5888_(BlockState p_152312_) {
      return p_152312_.m_61143_(f_152200_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_152312_);
   }

   public boolean m_7898_(BlockState p_152289_, LevelReader p_152290_, BlockPos p_152291_) {
      BlockPos blockpos = p_152291_.m_7495_();
      BlockState blockstate = p_152290_.m_8055_(blockpos);
      return blockstate.m_60713_(Blocks.f_152546_) || blockstate.m_60713_(this) || blockstate.m_60783_(p_152290_, blockpos, Direction.UP);
   }

   public BlockState m_7417_(BlockState p_152293_, Direction p_152294_, BlockState p_152295_, LevelAccessor p_152296_, BlockPos p_152297_, BlockPos p_152298_) {
      if (p_152294_ == Direction.DOWN && !p_152293_.m_60710_(p_152296_, p_152297_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         if (p_152293_.m_61143_(f_152200_)) {
            p_152296_.m_6217_().m_5945_(p_152297_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_152296_));
         }

         return p_152294_ == Direction.UP && p_152295_.m_60713_(this) ? Blocks.f_152546_.m_152465_(p_152293_) : super.m_7417_(p_152293_, p_152294_, p_152295_, p_152296_, p_152297_, p_152298_);
      }
   }

   public boolean m_7370_(BlockGetter p_152223_, BlockPos p_152224_, BlockState p_152225_, boolean p_152226_) {
      BlockState blockstate = p_152223_.m_8055_(p_152224_.m_7494_());
      return m_152319_(blockstate);
   }

   public boolean m_5491_(Level p_152237_, Random p_152238_, BlockPos p_152239_, BlockState p_152240_) {
      return true;
   }

   public void m_7719_(ServerLevel p_152216_, Random p_152217_, BlockPos p_152218_, BlockState p_152219_) {
      BlockPos blockpos = p_152218_.m_7494_();
      BlockState blockstate = p_152216_.m_8055_(blockpos);
      if (m_152251_(p_152216_, blockpos, blockstate)) {
         Direction direction = p_152219_.m_61143_(f_54117_);
         BigDripleafStemBlock.m_152349_(p_152216_, p_152218_, p_152219_.m_60819_(), direction);
         m_152241_(p_152216_, blockpos, blockstate.m_60819_(), direction);
      }

   }

   public void m_7892_(BlockState p_152266_, Level p_152267_, BlockPos p_152268_, Entity p_152269_) {
      if (!p_152267_.f_46443_) {
         if (p_152266_.m_61143_(f_152201_) == Tilt.NONE && m_152301_(p_152268_, p_152269_) && !p_152267_.m_46753_(p_152268_)) {
            this.m_152282_(p_152266_, p_152267_, p_152268_, Tilt.UNSTABLE, (SoundEvent)null);
         }

      }
   }

   public void m_7458_(BlockState p_152256_, ServerLevel p_152257_, BlockPos p_152258_, Random p_152259_) {
      if (p_152257_.m_46753_(p_152258_)) {
         m_152313_(p_152256_, p_152257_, p_152258_);
      } else {
         Tilt tilt = p_152256_.m_61143_(f_152201_);
         if (tilt == Tilt.UNSTABLE) {
            this.m_152282_(p_152256_, p_152257_, p_152258_, Tilt.PARTIAL, SoundEvents.f_144131_);
         } else if (tilt == Tilt.PARTIAL) {
            this.m_152282_(p_152256_, p_152257_, p_152258_, Tilt.FULL, SoundEvents.f_144131_);
         } else if (tilt == Tilt.FULL) {
            m_152313_(p_152256_, p_152257_, p_152258_);
         }

      }
   }

   public void m_6861_(BlockState p_152271_, Level p_152272_, BlockPos p_152273_, Block p_152274_, BlockPos p_152275_, boolean p_152276_) {
      if (p_152272_.m_46753_(p_152273_)) {
         m_152313_(p_152271_, p_152272_, p_152273_);
      }

   }

   private static void m_152232_(Level p_152233_, BlockPos p_152234_, SoundEvent p_152235_) {
      float f = Mth.m_144924_(p_152233_.f_46441_, 0.8F, 1.2F);
      p_152233_.m_5594_((Player)null, p_152234_, p_152235_, SoundSource.BLOCKS, 1.0F, f);
   }

   private static boolean m_152301_(BlockPos p_152302_, Entity p_152303_) {
      return p_152303_.m_20096_() && p_152303_.m_20182_().f_82480_ > (double)((float)p_152302_.m_123342_() + 0.6875F);
   }

   private void m_152282_(BlockState p_152283_, Level p_152284_, BlockPos p_152285_, Tilt p_152286_, @Nullable SoundEvent p_152287_) {
      m_152277_(p_152283_, p_152284_, p_152285_, p_152286_);
      if (p_152287_ != null) {
         m_152232_(p_152284_, p_152285_, p_152287_);
      }

      int i = f_152203_.getInt(p_152286_);
      if (i != -1) {
         p_152284_.m_6219_().m_5945_(p_152285_, this, i);
      }

   }

   private static void m_152313_(BlockState p_152314_, Level p_152315_, BlockPos p_152316_) {
      m_152277_(p_152314_, p_152315_, p_152316_, Tilt.NONE);
      if (p_152314_.m_61143_(f_152201_) != Tilt.NONE) {
         m_152232_(p_152315_, p_152316_, SoundEvents.f_144132_);
      }

   }

   private static void m_152277_(BlockState p_152278_, Level p_152279_, BlockPos p_152280_, Tilt p_152281_) {
      p_152279_.m_7731_(p_152280_, p_152278_.m_61124_(f_152201_, p_152281_), 2);
      if (p_152281_.m_156084_()) {
         p_152279_.m_151555_(GameEvent.f_157792_, p_152280_);
      }

   }

   public VoxelShape m_5939_(BlockState p_152307_, BlockGetter p_152308_, BlockPos p_152309_, CollisionContext p_152310_) {
      return f_152208_.get(p_152307_.m_61143_(f_152201_));
   }

   public VoxelShape m_5940_(BlockState p_152261_, BlockGetter p_152262_, BlockPos p_152263_, CollisionContext p_152264_) {
      return this.f_152211_.get(p_152261_);
   }

   public BlockState m_5573_(BlockPlaceContext p_152221_) {
      BlockState blockstate = p_152221_.m_43725_().m_8055_(p_152221_.m_8083_().m_7495_());
      FluidState fluidstate = p_152221_.m_43725_().m_6425_(p_152221_.m_8083_());
      boolean flag = blockstate.m_60713_(Blocks.f_152545_) || blockstate.m_60713_(Blocks.f_152546_);
      return this.m_49966_().m_61124_(f_152200_, Boolean.valueOf(fluidstate.m_164512_(Fluids.f_76193_))).m_61124_(f_54117_, flag ? blockstate.m_61143_(f_54117_) : p_152221_.m_8125_().m_122424_());
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152300_) {
      p_152300_.m_61104_(f_152200_, f_54117_, f_152201_);
   }
}