package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VineBlock extends Block implements net.minecraftforge.common.IForgeShearable {
   public static final BooleanProperty f_57833_ = PipeBlock.f_55152_;
   public static final BooleanProperty f_57834_ = PipeBlock.f_55148_;
   public static final BooleanProperty f_57835_ = PipeBlock.f_55149_;
   public static final BooleanProperty f_57836_ = PipeBlock.f_55150_;
   public static final BooleanProperty f_57837_ = PipeBlock.f_55151_;
   public static final Map<Direction, BooleanProperty> f_57838_ = PipeBlock.f_55154_.entrySet().stream().filter((p_57886_) -> {
      return p_57886_.getKey() != Direction.DOWN;
   }).collect(Util.m_137448_());
   protected static final float f_154875_ = 1.0F;
   private static final VoxelShape f_57839_ = Block.m_49796_(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   private static final VoxelShape f_57840_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
   private static final VoxelShape f_57841_ = Block.m_49796_(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   private static final VoxelShape f_57842_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
   private static final VoxelShape f_57843_ = Block.m_49796_(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
   private final Map<BlockState, VoxelShape> f_57844_;

   public VineBlock(BlockBehaviour.Properties p_57847_) {
      super(p_57847_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57833_, Boolean.valueOf(false)).m_61124_(f_57834_, Boolean.valueOf(false)).m_61124_(f_57835_, Boolean.valueOf(false)).m_61124_(f_57836_, Boolean.valueOf(false)).m_61124_(f_57837_, Boolean.valueOf(false)));
      this.f_57844_ = ImmutableMap.copyOf(this.f_49792_.m_61056_().stream().collect(Collectors.toMap(Function.identity(), VineBlock::m_57905_)));
   }

   private static VoxelShape m_57905_(BlockState p_57906_) {
      VoxelShape voxelshape = Shapes.m_83040_();
      if (p_57906_.m_61143_(f_57833_)) {
         voxelshape = f_57839_;
      }

      if (p_57906_.m_61143_(f_57834_)) {
         voxelshape = Shapes.m_83110_(voxelshape, f_57842_);
      }

      if (p_57906_.m_61143_(f_57836_)) {
         voxelshape = Shapes.m_83110_(voxelshape, f_57843_);
      }

      if (p_57906_.m_61143_(f_57835_)) {
         voxelshape = Shapes.m_83110_(voxelshape, f_57841_);
      }

      if (p_57906_.m_61143_(f_57837_)) {
         voxelshape = Shapes.m_83110_(voxelshape, f_57840_);
      }

      return voxelshape.m_83281_() ? Shapes.m_83144_() : voxelshape;
   }

   public VoxelShape m_5940_(BlockState p_57897_, BlockGetter p_57898_, BlockPos p_57899_, CollisionContext p_57900_) {
      return this.f_57844_.get(p_57897_);
   }

   public boolean m_7420_(BlockState p_181239_, BlockGetter p_181240_, BlockPos p_181241_) {
      return true;
   }

   public boolean m_7898_(BlockState p_57861_, LevelReader p_57862_, BlockPos p_57863_) {
      return this.m_57907_(this.m_57901_(p_57861_, p_57862_, p_57863_));
   }

   private boolean m_57907_(BlockState p_57908_) {
      return this.m_57909_(p_57908_) > 0;
   }

   private int m_57909_(BlockState p_57910_) {
      int i = 0;

      for(BooleanProperty booleanproperty : f_57838_.values()) {
         if (p_57910_.m_61143_(booleanproperty)) {
            ++i;
         }
      }

      return i;
   }

   private boolean m_57887_(BlockGetter p_57888_, BlockPos p_57889_, Direction p_57890_) {
      if (p_57890_ == Direction.DOWN) {
         return false;
      } else {
         BlockPos blockpos = p_57889_.m_142300_(p_57890_);
         if (m_57853_(p_57888_, blockpos, p_57890_)) {
            return true;
         } else if (p_57890_.m_122434_() == Direction.Axis.Y) {
            return false;
         } else {
            BooleanProperty booleanproperty = f_57838_.get(p_57890_);
            BlockState blockstate = p_57888_.m_8055_(p_57889_.m_7494_());
            return blockstate.m_60713_(this) && blockstate.m_61143_(booleanproperty);
         }
      }
   }

   public static boolean m_57853_(BlockGetter p_57854_, BlockPos p_57855_, Direction p_57856_) {
      BlockState blockstate = p_57854_.m_8055_(p_57855_);
      return Block.m_49918_(blockstate.m_60812_(p_57854_, p_57855_), p_57856_.m_122424_());
   }

   private BlockState m_57901_(BlockState p_57902_, BlockGetter p_57903_, BlockPos p_57904_) {
      BlockPos blockpos = p_57904_.m_7494_();
      if (p_57902_.m_61143_(f_57833_)) {
         p_57902_ = p_57902_.m_61124_(f_57833_, Boolean.valueOf(m_57853_(p_57903_, blockpos, Direction.DOWN)));
      }

      BlockState blockstate = null;

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BooleanProperty booleanproperty = m_57883_(direction);
         if (p_57902_.m_61143_(booleanproperty)) {
            boolean flag = this.m_57887_(p_57903_, p_57904_, direction);
            if (!flag) {
               if (blockstate == null) {
                  blockstate = p_57903_.m_8055_(blockpos);
               }

               flag = blockstate.m_60713_(this) && blockstate.m_61143_(booleanproperty);
            }

            p_57902_ = p_57902_.m_61124_(booleanproperty, Boolean.valueOf(flag));
         }
      }

      return p_57902_;
   }

   public BlockState m_7417_(BlockState p_57875_, Direction p_57876_, BlockState p_57877_, LevelAccessor p_57878_, BlockPos p_57879_, BlockPos p_57880_) {
      if (p_57876_ == Direction.DOWN) {
         return super.m_7417_(p_57875_, p_57876_, p_57877_, p_57878_, p_57879_, p_57880_);
      } else {
         BlockState blockstate = this.m_57901_(p_57875_, p_57878_, p_57879_);
         return !this.m_57907_(blockstate) ? Blocks.f_50016_.m_49966_() : blockstate;
      }
   }

   public void m_7455_(BlockState p_57892_, ServerLevel p_57893_, BlockPos p_57894_, Random p_57895_) {
      if (p_57893_.f_46441_.nextInt(4) == 0 && p_57893_.isAreaLoaded(p_57894_, 4)) { // Forge: check area to prevent loading unloaded chunks
         Direction direction = Direction.m_122404_(p_57895_);
         BlockPos blockpos = p_57894_.m_7494_();
         if (direction.m_122434_().m_122479_() && !p_57892_.m_61143_(m_57883_(direction))) {
            if (this.m_57850_(p_57893_, p_57894_)) {
               BlockPos blockpos4 = p_57894_.m_142300_(direction);
               BlockState blockstate4 = p_57893_.m_8055_(blockpos4);
               if (blockstate4.m_60795_()) {
                  Direction direction3 = direction.m_122427_();
                  Direction direction4 = direction.m_122428_();
                  boolean flag = p_57892_.m_61143_(m_57883_(direction3));
                  boolean flag1 = p_57892_.m_61143_(m_57883_(direction4));
                  BlockPos blockpos2 = blockpos4.m_142300_(direction3);
                  BlockPos blockpos3 = blockpos4.m_142300_(direction4);
                  if (flag && m_57853_(p_57893_, blockpos2, direction3)) {
                     p_57893_.m_7731_(blockpos4, this.m_49966_().m_61124_(m_57883_(direction3), Boolean.valueOf(true)), 2);
                  } else if (flag1 && m_57853_(p_57893_, blockpos3, direction4)) {
                     p_57893_.m_7731_(blockpos4, this.m_49966_().m_61124_(m_57883_(direction4), Boolean.valueOf(true)), 2);
                  } else {
                     Direction direction1 = direction.m_122424_();
                     if (flag && p_57893_.m_46859_(blockpos2) && m_57853_(p_57893_, p_57894_.m_142300_(direction3), direction1)) {
                        p_57893_.m_7731_(blockpos2, this.m_49966_().m_61124_(m_57883_(direction1), Boolean.valueOf(true)), 2);
                     } else if (flag1 && p_57893_.m_46859_(blockpos3) && m_57853_(p_57893_, p_57894_.m_142300_(direction4), direction1)) {
                        p_57893_.m_7731_(blockpos3, this.m_49966_().m_61124_(m_57883_(direction1), Boolean.valueOf(true)), 2);
                     } else if ((double)p_57895_.nextFloat() < 0.05D && m_57853_(p_57893_, blockpos4.m_7494_(), Direction.UP)) {
                        p_57893_.m_7731_(blockpos4, this.m_49966_().m_61124_(f_57833_, Boolean.valueOf(true)), 2);
                     }
                  }
               } else if (m_57853_(p_57893_, blockpos4, direction)) {
                  p_57893_.m_7731_(p_57894_, p_57892_.m_61124_(m_57883_(direction), Boolean.valueOf(true)), 2);
               }

            }
         } else {
            if (direction == Direction.UP && p_57894_.m_123342_() < p_57893_.m_151558_() - 1) {
               if (this.m_57887_(p_57893_, p_57894_, direction)) {
                  p_57893_.m_7731_(p_57894_, p_57892_.m_61124_(f_57833_, Boolean.valueOf(true)), 2);
                  return;
               }

               if (p_57893_.m_46859_(blockpos)) {
                  if (!this.m_57850_(p_57893_, p_57894_)) {
                     return;
                  }

                  BlockState blockstate3 = p_57892_;

                  for(Direction direction2 : Direction.Plane.HORIZONTAL) {
                     if (p_57895_.nextBoolean() || !m_57853_(p_57893_, blockpos.m_142300_(direction2), direction2)) {
                        blockstate3 = blockstate3.m_61124_(m_57883_(direction2), Boolean.valueOf(false));
                     }
                  }

                  if (this.m_57911_(blockstate3)) {
                     p_57893_.m_7731_(blockpos, blockstate3, 2);
                  }

                  return;
               }
            }

            if (p_57894_.m_123342_() > p_57893_.m_141937_()) {
               BlockPos blockpos1 = p_57894_.m_7495_();
               BlockState blockstate = p_57893_.m_8055_(blockpos1);
               if (blockstate.m_60795_() || blockstate.m_60713_(this)) {
                  BlockState blockstate1 = blockstate.m_60795_() ? this.m_49966_() : blockstate;
                  BlockState blockstate2 = this.m_57870_(p_57892_, blockstate1, p_57895_);
                  if (blockstate1 != blockstate2 && this.m_57911_(blockstate2)) {
                     p_57893_.m_7731_(blockpos1, blockstate2, 2);
                  }
               }
            }

         }
      }
   }

   private BlockState m_57870_(BlockState p_57871_, BlockState p_57872_, Random p_57873_) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (p_57873_.nextBoolean()) {
            BooleanProperty booleanproperty = m_57883_(direction);
            if (p_57871_.m_61143_(booleanproperty)) {
               p_57872_ = p_57872_.m_61124_(booleanproperty, Boolean.valueOf(true));
            }
         }
      }

      return p_57872_;
   }

   private boolean m_57911_(BlockState p_57912_) {
      return p_57912_.m_61143_(f_57834_) || p_57912_.m_61143_(f_57835_) || p_57912_.m_61143_(f_57836_) || p_57912_.m_61143_(f_57837_);
   }

   private boolean m_57850_(BlockGetter p_57851_, BlockPos p_57852_) {
      int i = 4;
      Iterable<BlockPos> iterable = BlockPos.m_121976_(p_57852_.m_123341_() - 4, p_57852_.m_123342_() - 1, p_57852_.m_123343_() - 4, p_57852_.m_123341_() + 4, p_57852_.m_123342_() + 1, p_57852_.m_123343_() + 4);
      int j = 5;

      for(BlockPos blockpos : iterable) {
         if (p_57851_.m_8055_(blockpos).m_60713_(this)) {
            --j;
            if (j <= 0) {
               return false;
            }
         }
      }

      return true;
   }

   public boolean m_6864_(BlockState p_57858_, BlockPlaceContext p_57859_) {
      BlockState blockstate = p_57859_.m_43725_().m_8055_(p_57859_.m_8083_());
      if (blockstate.m_60713_(this)) {
         return this.m_57909_(blockstate) < f_57838_.size();
      } else {
         return super.m_6864_(p_57858_, p_57859_);
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_57849_) {
      BlockState blockstate = p_57849_.m_43725_().m_8055_(p_57849_.m_8083_());
      boolean flag = blockstate.m_60713_(this);
      BlockState blockstate1 = flag ? blockstate : this.m_49966_();

      for(Direction direction : p_57849_.m_6232_()) {
         if (direction != Direction.DOWN) {
            BooleanProperty booleanproperty = m_57883_(direction);
            boolean flag1 = flag && blockstate.m_61143_(booleanproperty);
            if (!flag1 && this.m_57887_(p_57849_.m_43725_(), p_57849_.m_8083_(), direction)) {
               return blockstate1.m_61124_(booleanproperty, Boolean.valueOf(true));
            }
         }
      }

      return flag ? blockstate1 : null;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57882_) {
      p_57882_.m_61104_(f_57833_, f_57834_, f_57835_, f_57836_, f_57837_);
   }

   public BlockState m_6843_(BlockState p_57868_, Rotation p_57869_) {
      switch(p_57869_) {
      case CLOCKWISE_180:
         return p_57868_.m_61124_(f_57834_, p_57868_.m_61143_(f_57836_)).m_61124_(f_57835_, p_57868_.m_61143_(f_57837_)).m_61124_(f_57836_, p_57868_.m_61143_(f_57834_)).m_61124_(f_57837_, p_57868_.m_61143_(f_57835_));
      case COUNTERCLOCKWISE_90:
         return p_57868_.m_61124_(f_57834_, p_57868_.m_61143_(f_57835_)).m_61124_(f_57835_, p_57868_.m_61143_(f_57836_)).m_61124_(f_57836_, p_57868_.m_61143_(f_57837_)).m_61124_(f_57837_, p_57868_.m_61143_(f_57834_));
      case CLOCKWISE_90:
         return p_57868_.m_61124_(f_57834_, p_57868_.m_61143_(f_57837_)).m_61124_(f_57835_, p_57868_.m_61143_(f_57834_)).m_61124_(f_57836_, p_57868_.m_61143_(f_57835_)).m_61124_(f_57837_, p_57868_.m_61143_(f_57836_));
      default:
         return p_57868_;
      }
   }

   public BlockState m_6943_(BlockState p_57865_, Mirror p_57866_) {
      switch(p_57866_) {
      case LEFT_RIGHT:
         return p_57865_.m_61124_(f_57834_, p_57865_.m_61143_(f_57836_)).m_61124_(f_57836_, p_57865_.m_61143_(f_57834_));
      case FRONT_BACK:
         return p_57865_.m_61124_(f_57835_, p_57865_.m_61143_(f_57837_)).m_61124_(f_57837_, p_57865_.m_61143_(f_57835_));
      default:
         return super.m_6943_(p_57865_, p_57866_);
      }
   }

   public static BooleanProperty m_57883_(Direction p_57884_) {
      return f_57838_.get(p_57884_);
   }
}
