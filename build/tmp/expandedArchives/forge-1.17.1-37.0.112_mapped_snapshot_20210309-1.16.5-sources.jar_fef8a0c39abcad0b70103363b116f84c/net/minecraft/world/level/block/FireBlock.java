package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FireBlock extends BaseFireBlock {
   public static final int f_153264_ = 15;
   public static final IntegerProperty f_53408_ = BlockStateProperties.f_61410_;
   public static final BooleanProperty f_53409_ = PipeBlock.f_55148_;
   public static final BooleanProperty f_53410_ = PipeBlock.f_55149_;
   public static final BooleanProperty f_53411_ = PipeBlock.f_55150_;
   public static final BooleanProperty f_53412_ = PipeBlock.f_55151_;
   public static final BooleanProperty f_53413_ = PipeBlock.f_55152_;
   private static final Map<Direction, BooleanProperty> f_53414_ = PipeBlock.f_55154_.entrySet().stream().filter((p_53467_) -> {
      return p_53467_.getKey() != Direction.DOWN;
   }).collect(Util.m_137448_());
   private static final VoxelShape f_53415_ = Block.m_49796_(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   private static final VoxelShape f_53416_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
   private static final VoxelShape f_53417_ = Block.m_49796_(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   private static final VoxelShape f_53418_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
   private static final VoxelShape f_53419_ = Block.m_49796_(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
   private final Map<BlockState, VoxelShape> f_53420_;
   private static final int f_153256_ = 60;
   private static final int f_153257_ = 30;
   private static final int f_153258_ = 15;
   private static final int f_153259_ = 5;
   private static final int f_153260_ = 100;
   private static final int f_153261_ = 60;
   private static final int f_153262_ = 20;
   private static final int f_153263_ = 5;
   private final Object2IntMap<Block> f_53421_ = new Object2IntOpenHashMap<>();
   private final Object2IntMap<Block> f_53422_ = new Object2IntOpenHashMap<>();

   public FireBlock(BlockBehaviour.Properties p_53425_) {
      super(p_53425_, 1.0F);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_53408_, Integer.valueOf(0)).m_61124_(f_53409_, Boolean.valueOf(false)).m_61124_(f_53410_, Boolean.valueOf(false)).m_61124_(f_53411_, Boolean.valueOf(false)).m_61124_(f_53412_, Boolean.valueOf(false)).m_61124_(f_53413_, Boolean.valueOf(false)));
      this.f_53420_ = ImmutableMap.copyOf(this.f_49792_.m_61056_().stream().filter((p_53497_) -> {
         return p_53497_.m_61143_(f_53408_) == 0;
      }).collect(Collectors.toMap(Function.identity(), FireBlock::m_53490_)));
   }

   private static VoxelShape m_53490_(BlockState p_53491_) {
      VoxelShape voxelshape = Shapes.m_83040_();
      if (p_53491_.m_61143_(f_53413_)) {
         voxelshape = f_53415_;
      }

      if (p_53491_.m_61143_(f_53409_)) {
         voxelshape = Shapes.m_83110_(voxelshape, f_53418_);
      }

      if (p_53491_.m_61143_(f_53411_)) {
         voxelshape = Shapes.m_83110_(voxelshape, f_53419_);
      }

      if (p_53491_.m_61143_(f_53410_)) {
         voxelshape = Shapes.m_83110_(voxelshape, f_53417_);
      }

      if (p_53491_.m_61143_(f_53412_)) {
         voxelshape = Shapes.m_83110_(voxelshape, f_53416_);
      }

      return voxelshape.m_83281_() ? f_49237_ : voxelshape;
   }

   public BlockState m_7417_(BlockState p_53458_, Direction p_53459_, BlockState p_53460_, LevelAccessor p_53461_, BlockPos p_53462_, BlockPos p_53463_) {
      return this.m_7898_(p_53458_, p_53461_, p_53462_) ? this.m_53437_(p_53461_, p_53462_, p_53458_.m_61143_(f_53408_)) : Blocks.f_50016_.m_49966_();
   }

   public VoxelShape m_5940_(BlockState p_53474_, BlockGetter p_53475_, BlockPos p_53476_, CollisionContext p_53477_) {
      return this.f_53420_.get(p_53474_.m_61124_(f_53408_, Integer.valueOf(0)));
   }

   public BlockState m_5573_(BlockPlaceContext p_53427_) {
      return this.m_53470_(p_53427_.m_43725_(), p_53427_.m_8083_());
   }

   protected BlockState m_53470_(BlockGetter p_53471_, BlockPos p_53472_) {
      BlockPos blockpos = p_53472_.m_7495_();
      BlockState blockstate = p_53471_.m_8055_(blockpos);
      if (!this.canCatchFire(p_53471_, p_53472_, Direction.UP) && !blockstate.m_60783_(p_53471_, blockpos, Direction.UP)) {
         BlockState blockstate1 = this.m_49966_();

         for(Direction direction : Direction.values()) {
            BooleanProperty booleanproperty = f_53414_.get(direction);
            if (booleanproperty != null) {
               blockstate1 = blockstate1.m_61124_(booleanproperty, Boolean.valueOf(this.canCatchFire(p_53471_, p_53472_.m_142300_(direction), direction.m_122424_())));
            }
         }

         return blockstate1;
      } else {
         return this.m_49966_();
      }
   }

   public boolean m_7898_(BlockState p_53454_, LevelReader p_53455_, BlockPos p_53456_) {
      BlockPos blockpos = p_53456_.m_7495_();
      return p_53455_.m_8055_(blockpos).m_60783_(p_53455_, blockpos, Direction.UP) || this.m_53485_(p_53455_, p_53456_);
   }

   public void m_7458_(BlockState p_53449_, ServerLevel p_53450_, BlockPos p_53451_, Random p_53452_) {
      p_53450_.m_6219_().m_5945_(p_53451_, this, m_53468_(p_53450_.f_46441_));
      if (p_53450_.m_46469_().m_46207_(GameRules.f_46131_)) {
         if (!p_53449_.m_60710_(p_53450_, p_53451_)) {
            p_53450_.m_7471_(p_53451_, false);
         }

         BlockState blockstate = p_53450_.m_8055_(p_53451_.m_7495_());
         boolean flag = blockstate.isFireSource(p_53450_, p_53451_, Direction.UP);
         int i = p_53449_.m_61143_(f_53408_);
         if (!flag && p_53450_.m_46471_() && this.m_53428_(p_53450_, p_53451_) && p_53452_.nextFloat() < 0.2F + (float)i * 0.03F) {
            p_53450_.m_7471_(p_53451_, false);
         } else {
            int j = Math.min(15, i + p_53452_.nextInt(3) / 2);
            if (i != j) {
               p_53449_ = p_53449_.m_61124_(f_53408_, Integer.valueOf(j));
               p_53450_.m_7731_(p_53451_, p_53449_, 4);
            }

            if (!flag) {
               if (!this.m_53485_(p_53450_, p_53451_)) {
                  BlockPos blockpos = p_53451_.m_7495_();
                  if (!p_53450_.m_8055_(blockpos).m_60783_(p_53450_, blockpos, Direction.UP) || i > 3) {
                     p_53450_.m_7471_(p_53451_, false);
                  }

                  return;
               }

               if (i == 15 && p_53452_.nextInt(4) == 0 && !this.canCatchFire(p_53450_, p_53451_.m_7495_(), Direction.UP)) {
                  p_53450_.m_7471_(p_53451_, false);
                  return;
               }
            }

            boolean flag1 = p_53450_.m_46761_(p_53451_);
            int k = flag1 ? -50 : 0;
            this.tryCatchFire(p_53450_, p_53451_.m_142126_(), 300 + k, p_53452_, i, Direction.WEST);
            this.tryCatchFire(p_53450_, p_53451_.m_142125_(), 300 + k, p_53452_, i, Direction.EAST);
            this.tryCatchFire(p_53450_, p_53451_.m_7495_(), 250 + k, p_53452_, i, Direction.UP);
            this.tryCatchFire(p_53450_, p_53451_.m_7494_(), 250 + k, p_53452_, i, Direction.DOWN);
            this.tryCatchFire(p_53450_, p_53451_.m_142127_(), 300 + k, p_53452_, i, Direction.SOUTH);
            this.tryCatchFire(p_53450_, p_53451_.m_142128_(), 300 + k, p_53452_, i, Direction.NORTH);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int l = -1; l <= 1; ++l) {
               for(int i1 = -1; i1 <= 1; ++i1) {
                  for(int j1 = -1; j1 <= 4; ++j1) {
                     if (l != 0 || j1 != 0 || i1 != 0) {
                        int k1 = 100;
                        if (j1 > 1) {
                           k1 += (j1 - 1) * 100;
                        }

                        blockpos$mutableblockpos.m_122154_(p_53451_, l, j1, i1);
                        int l1 = this.m_53441_(p_53450_, blockpos$mutableblockpos);
                        if (l1 > 0) {
                           int i2 = (l1 + 40 + p_53450_.m_46791_().m_19028_() * 7) / (i + 30);
                           if (flag1) {
                              i2 /= 2;
                           }

                           if (i2 > 0 && p_53452_.nextInt(k1) <= i2 && (!p_53450_.m_46471_() || !this.m_53428_(p_53450_, blockpos$mutableblockpos))) {
                              int j2 = Math.min(15, i + p_53452_.nextInt(5) / 4);
                              p_53450_.m_7731_(blockpos$mutableblockpos, this.m_53437_(p_53450_, blockpos$mutableblockpos, j2), 3);
                           }
                        }
                     }
                  }
               }
            }

         }
      }
   }

   protected boolean m_53428_(Level p_53429_, BlockPos p_53430_) {
      return p_53429_.m_46758_(p_53430_) || p_53429_.m_46758_(p_53430_.m_142125_()) || p_53429_.m_46758_(p_53430_.m_142126_()) || p_53429_.m_46758_(p_53430_.m_142127_()) || p_53429_.m_46758_(p_53430_.m_142128_());
   }

   @Deprecated //Forge: Use IForgeBlockState.getFlammability, Public for default implementation only.
   public int m_53492_(BlockState p_53493_) {
      return p_53493_.m_61138_(BlockStateProperties.f_61362_) && p_53493_.m_61143_(BlockStateProperties.f_61362_) ? 0 : this.f_53422_.getInt(p_53493_.m_60734_());
   }

   @Deprecated //Forge: Use IForgeBlockState.getFireSpreadSpeed
   public int m_53494_(BlockState p_53495_) {
      return p_53495_.m_61138_(BlockStateProperties.f_61362_) && p_53495_.m_61143_(BlockStateProperties.f_61362_) ? 0 : this.f_53421_.getInt(p_53495_.m_60734_());
   }

   private void tryCatchFire(Level p_53432_, BlockPos p_53433_, int p_53434_, Random p_53435_, int p_53436_, Direction face) {
      int i = p_53432_.m_8055_(p_53433_).getFlammability(p_53432_, p_53433_, face);
      if (p_53435_.nextInt(p_53434_) < i) {
         BlockState blockstate = p_53432_.m_8055_(p_53433_);
         if (p_53435_.nextInt(p_53436_ + 10) < 5 && !p_53432_.m_46758_(p_53433_)) {
            int j = Math.min(p_53436_ + p_53435_.nextInt(5) / 4, 15);
            p_53432_.m_7731_(p_53433_, this.m_53437_(p_53432_, p_53433_, j), 3);
         } else {
            p_53432_.m_7471_(p_53433_, false);
         }

         blockstate.catchFire(p_53432_, p_53433_, face, null);
      }

   }

   private BlockState m_53437_(LevelAccessor p_53438_, BlockPos p_53439_, int p_53440_) {
      BlockState blockstate = m_49245_(p_53438_, p_53439_);
      return blockstate.m_60713_(Blocks.f_50083_) ? blockstate.m_61124_(f_53408_, Integer.valueOf(p_53440_)) : blockstate;
   }

   private boolean m_53485_(BlockGetter p_53486_, BlockPos p_53487_) {
      for(Direction direction : Direction.values()) {
         if (this.canCatchFire(p_53486_, p_53487_.m_142300_(direction), direction.m_122424_())) {
            return true;
         }
      }

      return false;
   }

   private int m_53441_(LevelReader p_53442_, BlockPos p_53443_) {
      if (!p_53442_.m_46859_(p_53443_)) {
         return 0;
      } else {
         int i = 0;

         for(Direction direction : Direction.values()) {
            BlockState blockstate = p_53442_.m_8055_(p_53443_.m_142300_(direction));
            i = Math.max(blockstate.getFireSpreadSpeed(p_53442_, p_53443_.m_142300_(direction), direction.m_122424_()), i);
         }

         return i;
      }
   }

   @Deprecated //Forge: Use canCatchFire with more context
   protected boolean m_7599_(BlockState p_53489_) {
      return this.m_53494_(p_53489_) > 0;
   }

   public void m_6807_(BlockState p_53479_, Level p_53480_, BlockPos p_53481_, BlockState p_53482_, boolean p_53483_) {
      super.m_6807_(p_53479_, p_53480_, p_53481_, p_53482_, p_53483_);
      p_53480_.m_6219_().m_5945_(p_53481_, this, m_53468_(p_53480_.f_46441_));
   }

   private static int m_53468_(Random p_53469_) {
      return 30 + p_53469_.nextInt(10);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53465_) {
      p_53465_.m_61104_(f_53408_, f_53409_, f_53410_, f_53411_, f_53412_, f_53413_);
   }

   private void m_53444_(Block p_53445_, int p_53446_, int p_53447_) {
      if (p_53445_ == Blocks.f_50016_) throw new IllegalArgumentException("Tried to set air on fire... This is bad.");
      this.f_53421_.put(p_53445_, p_53446_);
      this.f_53422_.put(p_53445_, p_53447_);
   }

   /**
    * Side sensitive version that calls the block function.
    *
    * @param world The current world
    * @param pos Block position
    * @param face The side the fire is coming from
    * @return True if the face can catch fire.
    */
   public boolean canCatchFire(BlockGetter world, BlockPos pos, Direction face) {
      return world.m_8055_(pos).isFlammable(world, pos, face);
   }

   public static void m_53484_() {
      FireBlock fireblock = (FireBlock)Blocks.f_50083_;
      fireblock.m_53444_(Blocks.f_50705_, 5, 20);
      fireblock.m_53444_(Blocks.f_50741_, 5, 20);
      fireblock.m_53444_(Blocks.f_50742_, 5, 20);
      fireblock.m_53444_(Blocks.f_50743_, 5, 20);
      fireblock.m_53444_(Blocks.f_50744_, 5, 20);
      fireblock.m_53444_(Blocks.f_50745_, 5, 20);
      fireblock.m_53444_(Blocks.f_50398_, 5, 20);
      fireblock.m_53444_(Blocks.f_50399_, 5, 20);
      fireblock.m_53444_(Blocks.f_50400_, 5, 20);
      fireblock.m_53444_(Blocks.f_50401_, 5, 20);
      fireblock.m_53444_(Blocks.f_50402_, 5, 20);
      fireblock.m_53444_(Blocks.f_50403_, 5, 20);
      fireblock.m_53444_(Blocks.f_50192_, 5, 20);
      fireblock.m_53444_(Blocks.f_50474_, 5, 20);
      fireblock.m_53444_(Blocks.f_50475_, 5, 20);
      fireblock.m_53444_(Blocks.f_50476_, 5, 20);
      fireblock.m_53444_(Blocks.f_50478_, 5, 20);
      fireblock.m_53444_(Blocks.f_50477_, 5, 20);
      fireblock.m_53444_(Blocks.f_50132_, 5, 20);
      fireblock.m_53444_(Blocks.f_50479_, 5, 20);
      fireblock.m_53444_(Blocks.f_50480_, 5, 20);
      fireblock.m_53444_(Blocks.f_50481_, 5, 20);
      fireblock.m_53444_(Blocks.f_50483_, 5, 20);
      fireblock.m_53444_(Blocks.f_50482_, 5, 20);
      fireblock.m_53444_(Blocks.f_50086_, 5, 20);
      fireblock.m_53444_(Blocks.f_50270_, 5, 20);
      fireblock.m_53444_(Blocks.f_50269_, 5, 20);
      fireblock.m_53444_(Blocks.f_50271_, 5, 20);
      fireblock.m_53444_(Blocks.f_50372_, 5, 20);
      fireblock.m_53444_(Blocks.f_50373_, 5, 20);
      fireblock.m_53444_(Blocks.f_49999_, 5, 5);
      fireblock.m_53444_(Blocks.f_50000_, 5, 5);
      fireblock.m_53444_(Blocks.f_50001_, 5, 5);
      fireblock.m_53444_(Blocks.f_50002_, 5, 5);
      fireblock.m_53444_(Blocks.f_50003_, 5, 5);
      fireblock.m_53444_(Blocks.f_50004_, 5, 5);
      fireblock.m_53444_(Blocks.f_50010_, 5, 5);
      fireblock.m_53444_(Blocks.f_50005_, 5, 5);
      fireblock.m_53444_(Blocks.f_50006_, 5, 5);
      fireblock.m_53444_(Blocks.f_50007_, 5, 5);
      fireblock.m_53444_(Blocks.f_50008_, 5, 5);
      fireblock.m_53444_(Blocks.f_50009_, 5, 5);
      fireblock.m_53444_(Blocks.f_50044_, 5, 5);
      fireblock.m_53444_(Blocks.f_50045_, 5, 5);
      fireblock.m_53444_(Blocks.f_50046_, 5, 5);
      fireblock.m_53444_(Blocks.f_50047_, 5, 5);
      fireblock.m_53444_(Blocks.f_50048_, 5, 5);
      fireblock.m_53444_(Blocks.f_50049_, 5, 5);
      fireblock.m_53444_(Blocks.f_50011_, 5, 5);
      fireblock.m_53444_(Blocks.f_50012_, 5, 5);
      fireblock.m_53444_(Blocks.f_50013_, 5, 5);
      fireblock.m_53444_(Blocks.f_50014_, 5, 5);
      fireblock.m_53444_(Blocks.f_50015_, 5, 5);
      fireblock.m_53444_(Blocks.f_50043_, 5, 5);
      fireblock.m_53444_(Blocks.f_50050_, 30, 60);
      fireblock.m_53444_(Blocks.f_50051_, 30, 60);
      fireblock.m_53444_(Blocks.f_50052_, 30, 60);
      fireblock.m_53444_(Blocks.f_50053_, 30, 60);
      fireblock.m_53444_(Blocks.f_50054_, 30, 60);
      fireblock.m_53444_(Blocks.f_50055_, 30, 60);
      fireblock.m_53444_(Blocks.f_50078_, 30, 20);
      fireblock.m_53444_(Blocks.f_50077_, 15, 100);
      fireblock.m_53444_(Blocks.f_50034_, 60, 100);
      fireblock.m_53444_(Blocks.f_50035_, 60, 100);
      fireblock.m_53444_(Blocks.f_50036_, 60, 100);
      fireblock.m_53444_(Blocks.f_50355_, 60, 100);
      fireblock.m_53444_(Blocks.f_50356_, 60, 100);
      fireblock.m_53444_(Blocks.f_50357_, 60, 100);
      fireblock.m_53444_(Blocks.f_50358_, 60, 100);
      fireblock.m_53444_(Blocks.f_50359_, 60, 100);
      fireblock.m_53444_(Blocks.f_50360_, 60, 100);
      fireblock.m_53444_(Blocks.f_50111_, 60, 100);
      fireblock.m_53444_(Blocks.f_50112_, 60, 100);
      fireblock.m_53444_(Blocks.f_50113_, 60, 100);
      fireblock.m_53444_(Blocks.f_50114_, 60, 100);
      fireblock.m_53444_(Blocks.f_50115_, 60, 100);
      fireblock.m_53444_(Blocks.f_50116_, 60, 100);
      fireblock.m_53444_(Blocks.f_50117_, 60, 100);
      fireblock.m_53444_(Blocks.f_50118_, 60, 100);
      fireblock.m_53444_(Blocks.f_50119_, 60, 100);
      fireblock.m_53444_(Blocks.f_50120_, 60, 100);
      fireblock.m_53444_(Blocks.f_50121_, 60, 100);
      fireblock.m_53444_(Blocks.f_50071_, 60, 100);
      fireblock.m_53444_(Blocks.f_50070_, 60, 100);
      fireblock.m_53444_(Blocks.f_50041_, 30, 60);
      fireblock.m_53444_(Blocks.f_50042_, 30, 60);
      fireblock.m_53444_(Blocks.f_50096_, 30, 60);
      fireblock.m_53444_(Blocks.f_50097_, 30, 60);
      fireblock.m_53444_(Blocks.f_50098_, 30, 60);
      fireblock.m_53444_(Blocks.f_50099_, 30, 60);
      fireblock.m_53444_(Blocks.f_50100_, 30, 60);
      fireblock.m_53444_(Blocks.f_50101_, 30, 60);
      fireblock.m_53444_(Blocks.f_50102_, 30, 60);
      fireblock.m_53444_(Blocks.f_50103_, 30, 60);
      fireblock.m_53444_(Blocks.f_50104_, 30, 60);
      fireblock.m_53444_(Blocks.f_50105_, 30, 60);
      fireblock.m_53444_(Blocks.f_50106_, 30, 60);
      fireblock.m_53444_(Blocks.f_50107_, 30, 60);
      fireblock.m_53444_(Blocks.f_50108_, 30, 60);
      fireblock.m_53444_(Blocks.f_50109_, 30, 60);
      fireblock.m_53444_(Blocks.f_50191_, 15, 100);
      fireblock.m_53444_(Blocks.f_50353_, 5, 5);
      fireblock.m_53444_(Blocks.f_50335_, 60, 20);
      fireblock.m_53444_(Blocks.f_50716_, 15, 20);
      fireblock.m_53444_(Blocks.f_50336_, 60, 20);
      fireblock.m_53444_(Blocks.f_50337_, 60, 20);
      fireblock.m_53444_(Blocks.f_50338_, 60, 20);
      fireblock.m_53444_(Blocks.f_50339_, 60, 20);
      fireblock.m_53444_(Blocks.f_50340_, 60, 20);
      fireblock.m_53444_(Blocks.f_50341_, 60, 20);
      fireblock.m_53444_(Blocks.f_50342_, 60, 20);
      fireblock.m_53444_(Blocks.f_50343_, 60, 20);
      fireblock.m_53444_(Blocks.f_50344_, 60, 20);
      fireblock.m_53444_(Blocks.f_50345_, 60, 20);
      fireblock.m_53444_(Blocks.f_50346_, 60, 20);
      fireblock.m_53444_(Blocks.f_50347_, 60, 20);
      fireblock.m_53444_(Blocks.f_50348_, 60, 20);
      fireblock.m_53444_(Blocks.f_50349_, 60, 20);
      fireblock.m_53444_(Blocks.f_50350_, 60, 20);
      fireblock.m_53444_(Blocks.f_50351_, 60, 20);
      fireblock.m_53444_(Blocks.f_50577_, 30, 60);
      fireblock.m_53444_(Blocks.f_50571_, 60, 60);
      fireblock.m_53444_(Blocks.f_50616_, 60, 60);
      fireblock.m_53444_(Blocks.f_50624_, 30, 20);
      fireblock.m_53444_(Blocks.f_50715_, 5, 20);
      fireblock.m_53444_(Blocks.f_50685_, 60, 100);
      fireblock.m_53444_(Blocks.f_50718_, 5, 20);
      fireblock.m_53444_(Blocks.f_50717_, 30, 20);
      fireblock.m_53444_(Blocks.f_152470_, 30, 60);
      fireblock.m_53444_(Blocks.f_152471_, 30, 60);
      fireblock.m_53444_(Blocks.f_152538_, 15, 60);
      fireblock.m_53444_(Blocks.f_152539_, 15, 60);
      fireblock.m_53444_(Blocks.f_152540_, 60, 100);
      fireblock.m_53444_(Blocks.f_152541_, 30, 60);
      fireblock.m_53444_(Blocks.f_152542_, 30, 60);
      fireblock.m_53444_(Blocks.f_152545_, 60, 100);
      fireblock.m_53444_(Blocks.f_152546_, 60, 100);
      fireblock.m_53444_(Blocks.f_152547_, 60, 100);
      fireblock.m_53444_(Blocks.f_152548_, 30, 60);
      fireblock.m_53444_(Blocks.f_152475_, 15, 100);
   }
}
