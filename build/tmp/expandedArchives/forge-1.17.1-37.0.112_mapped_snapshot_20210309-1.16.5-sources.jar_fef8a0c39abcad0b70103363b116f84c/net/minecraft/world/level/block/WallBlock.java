package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty f_57949_ = BlockStateProperties.f_61366_;
   public static final EnumProperty<WallSide> f_57950_ = BlockStateProperties.f_61378_;
   public static final EnumProperty<WallSide> f_57951_ = BlockStateProperties.f_61379_;
   public static final EnumProperty<WallSide> f_57952_ = BlockStateProperties.f_61380_;
   public static final EnumProperty<WallSide> f_57953_ = BlockStateProperties.f_61381_;
   public static final BooleanProperty f_57954_ = BlockStateProperties.f_61362_;
   private final Map<BlockState, VoxelShape> f_57955_;
   private final Map<BlockState, VoxelShape> f_57956_;
   private static final int f_154876_ = 3;
   private static final int f_154877_ = 14;
   private static final int f_154878_ = 4;
   private static final int f_154879_ = 1;
   private static final int f_154880_ = 7;
   private static final int f_154881_ = 9;
   private static final VoxelShape f_57957_ = Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
   private static final VoxelShape f_57958_ = Block.m_49796_(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 9.0D);
   private static final VoxelShape f_57959_ = Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 16.0D);
   private static final VoxelShape f_57960_ = Block.m_49796_(0.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
   private static final VoxelShape f_57961_ = Block.m_49796_(7.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);

   public WallBlock(BlockBehaviour.Properties p_57964_) {
      super(p_57964_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57949_, Boolean.valueOf(true)).m_61124_(f_57951_, WallSide.NONE).m_61124_(f_57950_, WallSide.NONE).m_61124_(f_57952_, WallSide.NONE).m_61124_(f_57953_, WallSide.NONE).m_61124_(f_57954_, Boolean.valueOf(false)));
      this.f_57955_ = this.m_57965_(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
      this.f_57956_ = this.m_57965_(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
   }

   private static VoxelShape m_58033_(VoxelShape p_58034_, WallSide p_58035_, VoxelShape p_58036_, VoxelShape p_58037_) {
      if (p_58035_ == WallSide.TALL) {
         return Shapes.m_83110_(p_58034_, p_58037_);
      } else {
         return p_58035_ == WallSide.LOW ? Shapes.m_83110_(p_58034_, p_58036_) : p_58034_;
      }
   }

   private Map<BlockState, VoxelShape> m_57965_(float p_57966_, float p_57967_, float p_57968_, float p_57969_, float p_57970_, float p_57971_) {
      float f = 8.0F - p_57966_;
      float f1 = 8.0F + p_57966_;
      float f2 = 8.0F - p_57967_;
      float f3 = 8.0F + p_57967_;
      VoxelShape voxelshape = Block.m_49796_((double)f, 0.0D, (double)f, (double)f1, (double)p_57968_, (double)f1);
      VoxelShape voxelshape1 = Block.m_49796_((double)f2, (double)p_57969_, 0.0D, (double)f3, (double)p_57970_, (double)f3);
      VoxelShape voxelshape2 = Block.m_49796_((double)f2, (double)p_57969_, (double)f2, (double)f3, (double)p_57970_, 16.0D);
      VoxelShape voxelshape3 = Block.m_49796_(0.0D, (double)p_57969_, (double)f2, (double)f3, (double)p_57970_, (double)f3);
      VoxelShape voxelshape4 = Block.m_49796_((double)f2, (double)p_57969_, (double)f2, 16.0D, (double)p_57970_, (double)f3);
      VoxelShape voxelshape5 = Block.m_49796_((double)f2, (double)p_57969_, 0.0D, (double)f3, (double)p_57971_, (double)f3);
      VoxelShape voxelshape6 = Block.m_49796_((double)f2, (double)p_57969_, (double)f2, (double)f3, (double)p_57971_, 16.0D);
      VoxelShape voxelshape7 = Block.m_49796_(0.0D, (double)p_57969_, (double)f2, (double)f3, (double)p_57971_, (double)f3);
      VoxelShape voxelshape8 = Block.m_49796_((double)f2, (double)p_57969_, (double)f2, 16.0D, (double)p_57971_, (double)f3);
      Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

      for(Boolean obool : f_57949_.m_6908_()) {
         for(WallSide wallside : f_57950_.m_6908_()) {
            for(WallSide wallside1 : f_57951_.m_6908_()) {
               for(WallSide wallside2 : f_57953_.m_6908_()) {
                  for(WallSide wallside3 : f_57952_.m_6908_()) {
                     VoxelShape voxelshape9 = Shapes.m_83040_();
                     voxelshape9 = m_58033_(voxelshape9, wallside, voxelshape4, voxelshape8);
                     voxelshape9 = m_58033_(voxelshape9, wallside2, voxelshape3, voxelshape7);
                     voxelshape9 = m_58033_(voxelshape9, wallside1, voxelshape1, voxelshape5);
                     voxelshape9 = m_58033_(voxelshape9, wallside3, voxelshape2, voxelshape6);
                     if (obool) {
                        voxelshape9 = Shapes.m_83110_(voxelshape9, voxelshape);
                     }

                     BlockState blockstate = this.m_49966_().m_61124_(f_57949_, obool).m_61124_(f_57950_, wallside).m_61124_(f_57953_, wallside2).m_61124_(f_57951_, wallside1).m_61124_(f_57952_, wallside3);
                     builder.put(blockstate.m_61124_(f_57954_, Boolean.valueOf(false)), voxelshape9);
                     builder.put(blockstate.m_61124_(f_57954_, Boolean.valueOf(true)), voxelshape9);
                  }
               }
            }
         }
      }

      return builder.build();
   }

   public VoxelShape m_5940_(BlockState p_58050_, BlockGetter p_58051_, BlockPos p_58052_, CollisionContext p_58053_) {
      return this.f_57955_.get(p_58050_);
   }

   public VoxelShape m_5939_(BlockState p_58055_, BlockGetter p_58056_, BlockPos p_58057_, CollisionContext p_58058_) {
      return this.f_57956_.get(p_58055_);
   }

   public boolean m_7357_(BlockState p_57996_, BlockGetter p_57997_, BlockPos p_57998_, PathComputationType p_57999_) {
      return false;
   }

   private boolean m_58020_(BlockState p_58021_, boolean p_58022_, Direction p_58023_) {
      Block block = p_58021_.m_60734_();
      boolean flag = block instanceof FenceGateBlock && FenceGateBlock.m_53378_(p_58021_, p_58023_);
      return p_58021_.m_60620_(BlockTags.f_13032_) || !m_152463_(p_58021_) && p_58022_ || block instanceof IronBarsBlock || flag;
   }

   public BlockState m_5573_(BlockPlaceContext p_57973_) {
      LevelReader levelreader = p_57973_.m_43725_();
      BlockPos blockpos = p_57973_.m_8083_();
      FluidState fluidstate = p_57973_.m_43725_().m_6425_(p_57973_.m_8083_());
      BlockPos blockpos1 = blockpos.m_142127_();
      BlockPos blockpos2 = blockpos.m_142126_();
      BlockPos blockpos3 = blockpos.m_142128_();
      BlockPos blockpos4 = blockpos.m_142125_();
      BlockPos blockpos5 = blockpos.m_7494_();
      BlockState blockstate = levelreader.m_8055_(blockpos1);
      BlockState blockstate1 = levelreader.m_8055_(blockpos2);
      BlockState blockstate2 = levelreader.m_8055_(blockpos3);
      BlockState blockstate3 = levelreader.m_8055_(blockpos4);
      BlockState blockstate4 = levelreader.m_8055_(blockpos5);
      boolean flag = this.m_58020_(blockstate, blockstate.m_60783_(levelreader, blockpos1, Direction.SOUTH), Direction.SOUTH);
      boolean flag1 = this.m_58020_(blockstate1, blockstate1.m_60783_(levelreader, blockpos2, Direction.WEST), Direction.WEST);
      boolean flag2 = this.m_58020_(blockstate2, blockstate2.m_60783_(levelreader, blockpos3, Direction.NORTH), Direction.NORTH);
      boolean flag3 = this.m_58020_(blockstate3, blockstate3.m_60783_(levelreader, blockpos4, Direction.EAST), Direction.EAST);
      BlockState blockstate5 = this.m_49966_().m_61124_(f_57954_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
      return this.m_57979_(levelreader, blockstate5, blockpos5, blockstate4, flag, flag1, flag2, flag3);
   }

   public BlockState m_7417_(BlockState p_58014_, Direction p_58015_, BlockState p_58016_, LevelAccessor p_58017_, BlockPos p_58018_, BlockPos p_58019_) {
      if (p_58014_.m_61143_(f_57954_)) {
         p_58017_.m_6217_().m_5945_(p_58018_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_58017_));
      }

      if (p_58015_ == Direction.DOWN) {
         return super.m_7417_(p_58014_, p_58015_, p_58016_, p_58017_, p_58018_, p_58019_);
      } else {
         return p_58015_ == Direction.UP ? this.m_57974_(p_58017_, p_58014_, p_58019_, p_58016_) : this.m_57988_(p_58017_, p_58018_, p_58014_, p_58019_, p_58016_, p_58015_);
      }
   }

   private static boolean m_58010_(BlockState p_58011_, Property<WallSide> p_58012_) {
      return p_58011_.m_61143_(p_58012_) != WallSide.NONE;
   }

   private static boolean m_58038_(VoxelShape p_58039_, VoxelShape p_58040_) {
      return !Shapes.m_83157_(p_58040_, p_58039_, BooleanOp.f_82685_);
   }

   private BlockState m_57974_(LevelReader p_57975_, BlockState p_57976_, BlockPos p_57977_, BlockState p_57978_) {
      boolean flag = m_58010_(p_57976_, f_57951_);
      boolean flag1 = m_58010_(p_57976_, f_57950_);
      boolean flag2 = m_58010_(p_57976_, f_57952_);
      boolean flag3 = m_58010_(p_57976_, f_57953_);
      return this.m_57979_(p_57975_, p_57976_, p_57977_, p_57978_, flag, flag1, flag2, flag3);
   }

   private BlockState m_57988_(LevelReader p_57989_, BlockPos p_57990_, BlockState p_57991_, BlockPos p_57992_, BlockState p_57993_, Direction p_57994_) {
      Direction direction = p_57994_.m_122424_();
      boolean flag = p_57994_ == Direction.NORTH ? this.m_58020_(p_57993_, p_57993_.m_60783_(p_57989_, p_57992_, direction), direction) : m_58010_(p_57991_, f_57951_);
      boolean flag1 = p_57994_ == Direction.EAST ? this.m_58020_(p_57993_, p_57993_.m_60783_(p_57989_, p_57992_, direction), direction) : m_58010_(p_57991_, f_57950_);
      boolean flag2 = p_57994_ == Direction.SOUTH ? this.m_58020_(p_57993_, p_57993_.m_60783_(p_57989_, p_57992_, direction), direction) : m_58010_(p_57991_, f_57952_);
      boolean flag3 = p_57994_ == Direction.WEST ? this.m_58020_(p_57993_, p_57993_.m_60783_(p_57989_, p_57992_, direction), direction) : m_58010_(p_57991_, f_57953_);
      BlockPos blockpos = p_57990_.m_7494_();
      BlockState blockstate = p_57989_.m_8055_(blockpos);
      return this.m_57979_(p_57989_, p_57991_, blockpos, blockstate, flag, flag1, flag2, flag3);
   }

   private BlockState m_57979_(LevelReader p_57980_, BlockState p_57981_, BlockPos p_57982_, BlockState p_57983_, boolean p_57984_, boolean p_57985_, boolean p_57986_, boolean p_57987_) {
      VoxelShape voxelshape = p_57983_.m_60812_(p_57980_, p_57982_).m_83263_(Direction.DOWN);
      BlockState blockstate = this.m_58024_(p_57981_, p_57984_, p_57985_, p_57986_, p_57987_, voxelshape);
      return blockstate.m_61124_(f_57949_, Boolean.valueOf(this.m_58006_(blockstate, p_57983_, voxelshape)));
   }

   private boolean m_58006_(BlockState p_58007_, BlockState p_58008_, VoxelShape p_58009_) {
      boolean flag = p_58008_.m_60734_() instanceof WallBlock && p_58008_.m_61143_(f_57949_);
      if (flag) {
         return true;
      } else {
         WallSide wallside = p_58007_.m_61143_(f_57951_);
         WallSide wallside1 = p_58007_.m_61143_(f_57952_);
         WallSide wallside2 = p_58007_.m_61143_(f_57950_);
         WallSide wallside3 = p_58007_.m_61143_(f_57953_);
         boolean flag1 = wallside1 == WallSide.NONE;
         boolean flag2 = wallside3 == WallSide.NONE;
         boolean flag3 = wallside2 == WallSide.NONE;
         boolean flag4 = wallside == WallSide.NONE;
         boolean flag5 = flag4 && flag1 && flag2 && flag3 || flag4 != flag1 || flag2 != flag3;
         if (flag5) {
            return true;
         } else {
            boolean flag6 = wallside == WallSide.TALL && wallside1 == WallSide.TALL || wallside2 == WallSide.TALL && wallside3 == WallSide.TALL;
            if (flag6) {
               return false;
            } else {
               return p_58008_.m_60620_(BlockTags.f_13081_) || m_58038_(p_58009_, f_57957_);
            }
         }
      }
   }

   private BlockState m_58024_(BlockState p_58025_, boolean p_58026_, boolean p_58027_, boolean p_58028_, boolean p_58029_, VoxelShape p_58030_) {
      return p_58025_.m_61124_(f_57951_, this.m_58041_(p_58026_, p_58030_, f_57958_)).m_61124_(f_57950_, this.m_58041_(p_58027_, p_58030_, f_57961_)).m_61124_(f_57952_, this.m_58041_(p_58028_, p_58030_, f_57959_)).m_61124_(f_57953_, this.m_58041_(p_58029_, p_58030_, f_57960_));
   }

   private WallSide m_58041_(boolean p_58042_, VoxelShape p_58043_, VoxelShape p_58044_) {
      if (p_58042_) {
         return m_58038_(p_58043_, p_58044_) ? WallSide.TALL : WallSide.LOW;
      } else {
         return WallSide.NONE;
      }
   }

   public FluidState m_5888_(BlockState p_58060_) {
      return p_58060_.m_61143_(f_57954_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_58060_);
   }

   public boolean m_7420_(BlockState p_58046_, BlockGetter p_58047_, BlockPos p_58048_) {
      return !p_58046_.m_61143_(f_57954_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_58032_) {
      p_58032_.m_61104_(f_57949_, f_57951_, f_57950_, f_57953_, f_57952_, f_57954_);
   }

   public BlockState m_6843_(BlockState p_58004_, Rotation p_58005_) {
      switch(p_58005_) {
      case CLOCKWISE_180:
         return p_58004_.m_61124_(f_57951_, p_58004_.m_61143_(f_57952_)).m_61124_(f_57950_, p_58004_.m_61143_(f_57953_)).m_61124_(f_57952_, p_58004_.m_61143_(f_57951_)).m_61124_(f_57953_, p_58004_.m_61143_(f_57950_));
      case COUNTERCLOCKWISE_90:
         return p_58004_.m_61124_(f_57951_, p_58004_.m_61143_(f_57950_)).m_61124_(f_57950_, p_58004_.m_61143_(f_57952_)).m_61124_(f_57952_, p_58004_.m_61143_(f_57953_)).m_61124_(f_57953_, p_58004_.m_61143_(f_57951_));
      case CLOCKWISE_90:
         return p_58004_.m_61124_(f_57951_, p_58004_.m_61143_(f_57953_)).m_61124_(f_57950_, p_58004_.m_61143_(f_57951_)).m_61124_(f_57952_, p_58004_.m_61143_(f_57950_)).m_61124_(f_57953_, p_58004_.m_61143_(f_57952_));
      default:
         return p_58004_;
      }
   }

   public BlockState m_6943_(BlockState p_58001_, Mirror p_58002_) {
      switch(p_58002_) {
      case LEFT_RIGHT:
         return p_58001_.m_61124_(f_57951_, p_58001_.m_61143_(f_57952_)).m_61124_(f_57952_, p_58001_.m_61143_(f_57951_));
      case FRONT_BACK:
         return p_58001_.m_61124_(f_57950_, p_58001_.m_61143_(f_57953_)).m_61124_(f_57953_, p_58001_.m_61143_(f_57950_));
      default:
         return super.m_6943_(p_58001_, p_58002_);
      }
   }
}