package net.minecraft.world.level.block;

import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StairBlock extends Block implements SimpleWaterloggedBlock {
   public static final DirectionProperty f_56841_ = HorizontalDirectionalBlock.f_54117_;
   public static final EnumProperty<Half> f_56842_ = BlockStateProperties.f_61402_;
   public static final EnumProperty<StairsShape> f_56843_ = BlockStateProperties.f_61398_;
   public static final BooleanProperty f_56844_ = BlockStateProperties.f_61362_;
   protected static final VoxelShape f_56845_ = SlabBlock.f_56356_;
   protected static final VoxelShape f_56846_ = SlabBlock.f_56355_;
   protected static final VoxelShape f_56847_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
   protected static final VoxelShape f_56848_ = Block.m_49796_(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
   protected static final VoxelShape f_56849_ = Block.m_49796_(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
   protected static final VoxelShape f_56850_ = Block.m_49796_(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_56851_ = Block.m_49796_(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
   protected static final VoxelShape f_56852_ = Block.m_49796_(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
   protected static final VoxelShape f_56853_ = Block.m_49796_(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
   protected static final VoxelShape f_56854_ = Block.m_49796_(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape[] f_56855_ = m_56933_(f_56845_, f_56847_, f_56851_, f_56848_, f_56852_);
   protected static final VoxelShape[] f_56856_ = m_56933_(f_56846_, f_56849_, f_56853_, f_56850_, f_56854_);
   private static final int[] f_56857_ = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
   private final Block f_56858_;
   private final BlockState f_56859_;

   private static VoxelShape[] m_56933_(VoxelShape p_56934_, VoxelShape p_56935_, VoxelShape p_56936_, VoxelShape p_56937_, VoxelShape p_56938_) {
      return IntStream.range(0, 16).mapToObj((p_56945_) -> {
         return m_56864_(p_56945_, p_56934_, p_56935_, p_56936_, p_56937_, p_56938_);
      }).toArray((p_56949_) -> {
         return new VoxelShape[p_56949_];
      });
   }

   private static VoxelShape m_56864_(int p_56865_, VoxelShape p_56866_, VoxelShape p_56867_, VoxelShape p_56868_, VoxelShape p_56869_, VoxelShape p_56870_) {
      VoxelShape voxelshape = p_56866_;
      if ((p_56865_ & 1) != 0) {
         voxelshape = Shapes.m_83110_(p_56866_, p_56867_);
      }

      if ((p_56865_ & 2) != 0) {
         voxelshape = Shapes.m_83110_(voxelshape, p_56868_);
      }

      if ((p_56865_ & 4) != 0) {
         voxelshape = Shapes.m_83110_(voxelshape, p_56869_);
      }

      if ((p_56865_ & 8) != 0) {
         voxelshape = Shapes.m_83110_(voxelshape, p_56870_);
      }

      return voxelshape;
   }

   @Deprecated // Forge: Use the other constructor that takes a Supplier
   public StairBlock(BlockState p_56862_, BlockBehaviour.Properties p_56863_) {
      super(p_56863_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_56841_, Direction.NORTH).m_61124_(f_56842_, Half.BOTTOM).m_61124_(f_56843_, StairsShape.STRAIGHT).m_61124_(f_56844_, Boolean.valueOf(false)));
      this.f_56858_ = p_56862_.m_60734_();
      this.f_56859_ = p_56862_;
      this.stateSupplier = () -> p_56862_;
   }

   public StairBlock(java.util.function.Supplier<BlockState> state, BlockBehaviour.Properties properties) {
      super(properties);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_56841_, Direction.NORTH).m_61124_(f_56842_, Half.BOTTOM).m_61124_(f_56843_, StairsShape.STRAIGHT).m_61124_(f_56844_, Boolean.valueOf(false)));
      this.f_56858_ = Blocks.f_50016_; // These are unused, fields are redirected
      this.f_56859_ = Blocks.f_50016_.m_49966_();
      this.stateSupplier = state;
   }

   public boolean m_7923_(BlockState p_56967_) {
      return true;
   }

   public VoxelShape m_5940_(BlockState p_56956_, BlockGetter p_56957_, BlockPos p_56958_, CollisionContext p_56959_) {
      return (p_56956_.m_61143_(f_56842_) == Half.TOP ? f_56855_ : f_56856_)[f_56857_[this.m_56982_(p_56956_)]];
   }

   private int m_56982_(BlockState p_56983_) {
      return p_56983_.m_61143_(f_56843_).ordinal() * 4 + p_56983_.m_61143_(f_56841_).m_122416_();
   }

   public void m_7100_(BlockState p_56914_, Level p_56915_, BlockPos p_56916_, Random p_56917_) {
      this.f_56858_.m_7100_(p_56914_, p_56915_, p_56916_, p_56917_);
   }

   public void m_6256_(BlockState p_56896_, Level p_56897_, BlockPos p_56898_, Player p_56899_) {
      this.f_56859_.m_60686_(p_56897_, p_56898_, p_56899_);
   }

   public void m_6786_(LevelAccessor p_56882_, BlockPos p_56883_, BlockState p_56884_) {
      this.f_56858_.m_6786_(p_56882_, p_56883_, p_56884_);
   }

   public float m_7325_() {
      return this.f_56858_.m_7325_();
   }

   public void m_6807_(BlockState p_56961_, Level p_56962_, BlockPos p_56963_, BlockState p_56964_, boolean p_56965_) {
      if (!p_56961_.m_60713_(p_56961_.m_60734_())) {
         this.f_56859_.m_60690_(p_56962_, p_56963_, Blocks.f_50016_, p_56963_, false);
         this.f_56858_.m_6807_(this.f_56859_, p_56962_, p_56963_, p_56964_, false);
      }
   }

   public void m_6810_(BlockState p_56908_, Level p_56909_, BlockPos p_56910_, BlockState p_56911_, boolean p_56912_) {
      if (!p_56908_.m_60713_(p_56911_.m_60734_())) {
         this.f_56859_.m_60753_(p_56909_, p_56910_, p_56911_, p_56912_);
      }
   }

   public void m_141947_(Level p_154720_, BlockPos p_154721_, BlockState p_154722_, Entity p_154723_) {
      this.f_56858_.m_141947_(p_154720_, p_154721_, p_154722_, p_154723_);
   }

   public boolean m_6724_(BlockState p_56947_) {
      return this.f_56858_.m_6724_(p_56947_);
   }

   public void m_7455_(BlockState p_56951_, ServerLevel p_56952_, BlockPos p_56953_, Random p_56954_) {
      this.f_56858_.m_7455_(p_56951_, p_56952_, p_56953_, p_56954_);
   }

   public void m_7458_(BlockState p_56886_, ServerLevel p_56887_, BlockPos p_56888_, Random p_56889_) {
      this.f_56858_.m_7458_(p_56886_, p_56887_, p_56888_, p_56889_);
   }

   public InteractionResult m_6227_(BlockState p_56901_, Level p_56902_, BlockPos p_56903_, Player p_56904_, InteractionHand p_56905_, BlockHitResult p_56906_) {
      return this.f_56859_.m_60664_(p_56902_, p_56904_, p_56905_, p_56906_);
   }

   public void m_7592_(Level p_56878_, BlockPos p_56879_, Explosion p_56880_) {
      this.f_56858_.m_7592_(p_56878_, p_56879_, p_56880_);
   }

   public BlockState m_5573_(BlockPlaceContext p_56872_) {
      Direction direction = p_56872_.m_43719_();
      BlockPos blockpos = p_56872_.m_8083_();
      FluidState fluidstate = p_56872_.m_43725_().m_6425_(blockpos);
      BlockState blockstate = this.m_49966_().m_61124_(f_56841_, p_56872_.m_8125_()).m_61124_(f_56842_, direction != Direction.DOWN && (direction == Direction.UP || !(p_56872_.m_43720_().f_82480_ - (double)blockpos.m_123342_() > 0.5D)) ? Half.BOTTOM : Half.TOP).m_61124_(f_56844_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
      return blockstate.m_61124_(f_56843_, m_56976_(blockstate, p_56872_.m_43725_(), blockpos));
   }

   public BlockState m_7417_(BlockState p_56925_, Direction p_56926_, BlockState p_56927_, LevelAccessor p_56928_, BlockPos p_56929_, BlockPos p_56930_) {
      if (p_56925_.m_61143_(f_56844_)) {
         p_56928_.m_6217_().m_5945_(p_56929_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56928_));
      }

      return p_56926_.m_122434_().m_122479_() ? p_56925_.m_61124_(f_56843_, m_56976_(p_56925_, p_56928_, p_56929_)) : super.m_7417_(p_56925_, p_56926_, p_56927_, p_56928_, p_56929_, p_56930_);
   }

   private static StairsShape m_56976_(BlockState p_56977_, BlockGetter p_56978_, BlockPos p_56979_) {
      Direction direction = p_56977_.m_61143_(f_56841_);
      BlockState blockstate = p_56978_.m_8055_(p_56979_.m_142300_(direction));
      if (m_56980_(blockstate) && p_56977_.m_61143_(f_56842_) == blockstate.m_61143_(f_56842_)) {
         Direction direction1 = blockstate.m_61143_(f_56841_);
         if (direction1.m_122434_() != p_56977_.m_61143_(f_56841_).m_122434_() && m_56970_(p_56977_, p_56978_, p_56979_, direction1.m_122424_())) {
            if (direction1 == direction.m_122428_()) {
               return StairsShape.OUTER_LEFT;
            }

            return StairsShape.OUTER_RIGHT;
         }
      }

      BlockState blockstate1 = p_56978_.m_8055_(p_56979_.m_142300_(direction.m_122424_()));
      if (m_56980_(blockstate1) && p_56977_.m_61143_(f_56842_) == blockstate1.m_61143_(f_56842_)) {
         Direction direction2 = blockstate1.m_61143_(f_56841_);
         if (direction2.m_122434_() != p_56977_.m_61143_(f_56841_).m_122434_() && m_56970_(p_56977_, p_56978_, p_56979_, direction2)) {
            if (direction2 == direction.m_122428_()) {
               return StairsShape.INNER_LEFT;
            }

            return StairsShape.INNER_RIGHT;
         }
      }

      return StairsShape.STRAIGHT;
   }

   private static boolean m_56970_(BlockState p_56971_, BlockGetter p_56972_, BlockPos p_56973_, Direction p_56974_) {
      BlockState blockstate = p_56972_.m_8055_(p_56973_.m_142300_(p_56974_));
      return !m_56980_(blockstate) || blockstate.m_61143_(f_56841_) != p_56971_.m_61143_(f_56841_) || blockstate.m_61143_(f_56842_) != p_56971_.m_61143_(f_56842_);
   }

   public static boolean m_56980_(BlockState p_56981_) {
      return p_56981_.m_60734_() instanceof StairBlock;
   }

   public BlockState m_6843_(BlockState p_56922_, Rotation p_56923_) {
      return p_56922_.m_61124_(f_56841_, p_56923_.m_55954_(p_56922_.m_61143_(f_56841_)));
   }

   public BlockState m_6943_(BlockState p_56919_, Mirror p_56920_) {
      Direction direction = p_56919_.m_61143_(f_56841_);
      StairsShape stairsshape = p_56919_.m_61143_(f_56843_);
      switch(p_56920_) {
      case LEFT_RIGHT:
         if (direction.m_122434_() == Direction.Axis.Z) {
            switch(stairsshape) {
            case INNER_LEFT:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.INNER_RIGHT);
            case INNER_RIGHT:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.INNER_LEFT);
            case OUTER_LEFT:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.OUTER_RIGHT);
            case OUTER_RIGHT:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.OUTER_LEFT);
            default:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180);
            }
         }
         break;
      case FRONT_BACK:
         if (direction.m_122434_() == Direction.Axis.X) {
            switch(stairsshape) {
            case INNER_LEFT:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.INNER_LEFT);
            case INNER_RIGHT:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.INNER_RIGHT);
            case OUTER_LEFT:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.OUTER_RIGHT);
            case OUTER_RIGHT:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.OUTER_LEFT);
            case STRAIGHT:
               return p_56919_.m_60717_(Rotation.CLOCKWISE_180);
            }
         }
      }

      return super.m_6943_(p_56919_, p_56920_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56932_) {
      p_56932_.m_61104_(f_56841_, f_56842_, f_56843_, f_56844_);
   }

   public FluidState m_5888_(BlockState p_56969_) {
      return p_56969_.m_61143_(f_56844_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_56969_);
   }

   public boolean m_7357_(BlockState p_56891_, BlockGetter p_56892_, BlockPos p_56893_, PathComputationType p_56894_) {
      return false;
   }

   // Forge Start
   private final java.util.function.Supplier<BlockState> stateSupplier;
   private Block getModelBlock() {
       return getModelState().m_60734_();
   }
   private BlockState getModelState() {
       return stateSupplier.get();
   }
   // Forge end
}
