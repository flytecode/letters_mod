package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.math.Vector3f;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedStoneWireBlock extends Block {
   public static final EnumProperty<RedstoneSide> f_55496_ = BlockStateProperties.f_61383_;
   public static final EnumProperty<RedstoneSide> f_55497_ = BlockStateProperties.f_61382_;
   public static final EnumProperty<RedstoneSide> f_55498_ = BlockStateProperties.f_61384_;
   public static final EnumProperty<RedstoneSide> f_55499_ = BlockStateProperties.f_61385_;
   public static final IntegerProperty f_55500_ = BlockStateProperties.f_61426_;
   public static final Map<Direction, EnumProperty<RedstoneSide>> f_55501_ = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, f_55496_, Direction.EAST, f_55497_, Direction.SOUTH, f_55498_, Direction.WEST, f_55499_));
   protected static final int f_154304_ = 1;
   protected static final int f_154305_ = 3;
   protected static final int f_154306_ = 13;
   protected static final int f_154307_ = 3;
   protected static final int f_154308_ = 13;
   private static final VoxelShape f_55502_ = Block.m_49796_(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
   private static final Map<Direction, VoxelShape> f_55503_ = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.m_49796_(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.m_49796_(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.m_49796_(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.m_49796_(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
   private static final Map<Direction, VoxelShape> f_55504_ = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Shapes.m_83110_(f_55503_.get(Direction.NORTH), Block.m_49796_(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, Shapes.m_83110_(f_55503_.get(Direction.SOUTH), Block.m_49796_(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, Shapes.m_83110_(f_55503_.get(Direction.EAST), Block.m_49796_(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, Shapes.m_83110_(f_55503_.get(Direction.WEST), Block.m_49796_(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
   private static final Map<BlockState, VoxelShape> f_55505_ = Maps.newHashMap();
   private static final Vec3[] f_55506_ = Util.m_137469_(new Vec3[16], (p_154319_) -> {
      for(int i = 0; i <= 15; ++i) {
         float f = (float)i / 15.0F;
         float f1 = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
         float f2 = Mth.m_14036_(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
         float f3 = Mth.m_14036_(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
         p_154319_[i] = new Vec3((double)f1, (double)f2, (double)f3);
      }

   });
   private static final float f_154303_ = 0.2F;
   private final BlockState f_55507_;
   private boolean f_55508_ = true;

   public RedStoneWireBlock(BlockBehaviour.Properties p_55511_) {
      super(p_55511_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_55496_, RedstoneSide.NONE).m_61124_(f_55497_, RedstoneSide.NONE).m_61124_(f_55498_, RedstoneSide.NONE).m_61124_(f_55499_, RedstoneSide.NONE).m_61124_(f_55500_, Integer.valueOf(0)));
      this.f_55507_ = this.m_49966_().m_61124_(f_55496_, RedstoneSide.SIDE).m_61124_(f_55497_, RedstoneSide.SIDE).m_61124_(f_55498_, RedstoneSide.SIDE).m_61124_(f_55499_, RedstoneSide.SIDE);

      for(BlockState blockstate : this.m_49965_().m_61056_()) {
         if (blockstate.m_61143_(f_55500_) == 0) {
            f_55505_.put(blockstate, this.m_55642_(blockstate));
         }
      }

   }

   private VoxelShape m_55642_(BlockState p_55643_) {
      VoxelShape voxelshape = f_55502_;

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         RedstoneSide redstoneside = p_55643_.m_61143_(f_55501_.get(direction));
         if (redstoneside == RedstoneSide.SIDE) {
            voxelshape = Shapes.m_83110_(voxelshape, f_55503_.get(direction));
         } else if (redstoneside == RedstoneSide.UP) {
            voxelshape = Shapes.m_83110_(voxelshape, f_55504_.get(direction));
         }
      }

      return voxelshape;
   }

   public VoxelShape m_5940_(BlockState p_55620_, BlockGetter p_55621_, BlockPos p_55622_, CollisionContext p_55623_) {
      return f_55505_.get(p_55620_.m_61124_(f_55500_, Integer.valueOf(0)));
   }

   public BlockState m_5573_(BlockPlaceContext p_55513_) {
      return this.m_55514_(p_55513_.m_43725_(), this.f_55507_, p_55513_.m_8083_());
   }

   private BlockState m_55514_(BlockGetter p_55515_, BlockState p_55516_, BlockPos p_55517_) {
      boolean flag = m_55646_(p_55516_);
      p_55516_ = this.m_55608_(p_55515_, this.m_49966_().m_61124_(f_55500_, p_55516_.m_61143_(f_55500_)), p_55517_);
      if (flag && m_55646_(p_55516_)) {
         return p_55516_;
      } else {
         boolean flag1 = p_55516_.m_61143_(f_55496_).m_61761_();
         boolean flag2 = p_55516_.m_61143_(f_55498_).m_61761_();
         boolean flag3 = p_55516_.m_61143_(f_55497_).m_61761_();
         boolean flag4 = p_55516_.m_61143_(f_55499_).m_61761_();
         boolean flag5 = !flag1 && !flag2;
         boolean flag6 = !flag3 && !flag4;
         if (!flag4 && flag5) {
            p_55516_ = p_55516_.m_61124_(f_55499_, RedstoneSide.SIDE);
         }

         if (!flag3 && flag5) {
            p_55516_ = p_55516_.m_61124_(f_55497_, RedstoneSide.SIDE);
         }

         if (!flag1 && flag6) {
            p_55516_ = p_55516_.m_61124_(f_55496_, RedstoneSide.SIDE);
         }

         if (!flag2 && flag6) {
            p_55516_ = p_55516_.m_61124_(f_55498_, RedstoneSide.SIDE);
         }

         return p_55516_;
      }
   }

   private BlockState m_55608_(BlockGetter p_55609_, BlockState p_55610_, BlockPos p_55611_) {
      boolean flag = !p_55609_.m_8055_(p_55611_.m_7494_()).m_60796_(p_55609_, p_55611_);

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (!p_55610_.m_61143_(f_55501_.get(direction)).m_61761_()) {
            RedstoneSide redstoneside = this.m_55522_(p_55609_, p_55611_, direction, flag);
            p_55610_ = p_55610_.m_61124_(f_55501_.get(direction), redstoneside);
         }
      }

      return p_55610_;
   }

   public BlockState m_7417_(BlockState p_55598_, Direction p_55599_, BlockState p_55600_, LevelAccessor p_55601_, BlockPos p_55602_, BlockPos p_55603_) {
      if (p_55599_ == Direction.DOWN) {
         return p_55598_;
      } else if (p_55599_ == Direction.UP) {
         return this.m_55514_(p_55601_, p_55598_, p_55602_);
      } else {
         RedstoneSide redstoneside = this.m_55518_(p_55601_, p_55602_, p_55599_);
         return redstoneside.m_61761_() == p_55598_.m_61143_(f_55501_.get(p_55599_)).m_61761_() && !m_55644_(p_55598_) ? p_55598_.m_61124_(f_55501_.get(p_55599_), redstoneside) : this.m_55514_(p_55601_, this.f_55507_.m_61124_(f_55500_, p_55598_.m_61143_(f_55500_)).m_61124_(f_55501_.get(p_55599_), redstoneside), p_55602_);
      }
   }

   private static boolean m_55644_(BlockState p_55645_) {
      return p_55645_.m_61143_(f_55496_).m_61761_() && p_55645_.m_61143_(f_55498_).m_61761_() && p_55645_.m_61143_(f_55497_).m_61761_() && p_55645_.m_61143_(f_55499_).m_61761_();
   }

   private static boolean m_55646_(BlockState p_55647_) {
      return !p_55647_.m_61143_(f_55496_).m_61761_() && !p_55647_.m_61143_(f_55498_).m_61761_() && !p_55647_.m_61143_(f_55497_).m_61761_() && !p_55647_.m_61143_(f_55499_).m_61761_();
   }

   public void m_7742_(BlockState p_55579_, LevelAccessor p_55580_, BlockPos p_55581_, int p_55582_, int p_55583_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         RedstoneSide redstoneside = p_55579_.m_61143_(f_55501_.get(direction));
         if (redstoneside != RedstoneSide.NONE && !p_55580_.m_8055_(blockpos$mutableblockpos.m_122159_(p_55581_, direction)).m_60713_(this)) {
            blockpos$mutableblockpos.m_122173_(Direction.DOWN);
            BlockState blockstate = p_55580_.m_8055_(blockpos$mutableblockpos);
            if (!blockstate.m_60713_(Blocks.f_50455_)) {
               BlockPos blockpos = blockpos$mutableblockpos.m_142300_(direction.m_122424_());
               BlockState blockstate1 = blockstate.m_60728_(direction.m_122424_(), p_55580_.m_8055_(blockpos), p_55580_, blockpos$mutableblockpos, blockpos);
               m_49908_(blockstate, blockstate1, p_55580_, blockpos$mutableblockpos, p_55582_, p_55583_);
            }

            blockpos$mutableblockpos.m_122159_(p_55581_, direction).m_122173_(Direction.UP);
            BlockState blockstate3 = p_55580_.m_8055_(blockpos$mutableblockpos);
            if (!blockstate3.m_60713_(Blocks.f_50455_)) {
               BlockPos blockpos1 = blockpos$mutableblockpos.m_142300_(direction.m_122424_());
               BlockState blockstate2 = blockstate3.m_60728_(direction.m_122424_(), p_55580_.m_8055_(blockpos1), p_55580_, blockpos$mutableblockpos, blockpos1);
               m_49908_(blockstate3, blockstate2, p_55580_, blockpos$mutableblockpos, p_55582_, p_55583_);
            }
         }
      }

   }

   private RedstoneSide m_55518_(BlockGetter p_55519_, BlockPos p_55520_, Direction p_55521_) {
      return this.m_55522_(p_55519_, p_55520_, p_55521_, !p_55519_.m_8055_(p_55520_.m_7494_()).m_60796_(p_55519_, p_55520_));
   }

   private RedstoneSide m_55522_(BlockGetter p_55523_, BlockPos p_55524_, Direction p_55525_, boolean p_55526_) {
      BlockPos blockpos = p_55524_.m_142300_(p_55525_);
      BlockState blockstate = p_55523_.m_8055_(blockpos);
      if (p_55526_) {
         boolean flag = this.m_55612_(p_55523_, blockpos, blockstate);
         if (flag && p_55523_.m_8055_(blockpos.m_7494_()).canRedstoneConnectTo(p_55523_, blockpos.m_7494_(), null)) {
            if (blockstate.m_60783_(p_55523_, blockpos, p_55525_.m_122424_())) {
               return RedstoneSide.UP;
            }

            return RedstoneSide.SIDE;
         }
      }

      if (blockstate.canRedstoneConnectTo(p_55523_, blockpos, p_55525_)) {
          return RedstoneSide.SIDE;
      } else if (blockstate.m_60796_(p_55523_, blockpos)) {
          return RedstoneSide.NONE;
      } else {
          BlockPos blockPosBelow = blockpos.m_7495_();
          return p_55523_.m_8055_(blockPosBelow).canRedstoneConnectTo(p_55523_, blockPosBelow, null) ? RedstoneSide.SIDE : RedstoneSide.NONE;
      }
   }

   public boolean m_7898_(BlockState p_55585_, LevelReader p_55586_, BlockPos p_55587_) {
      BlockPos blockpos = p_55587_.m_7495_();
      BlockState blockstate = p_55586_.m_8055_(blockpos);
      return this.m_55612_(p_55586_, blockpos, blockstate);
   }

   private boolean m_55612_(BlockGetter p_55613_, BlockPos p_55614_, BlockState p_55615_) {
      return p_55615_.m_60783_(p_55613_, p_55614_, Direction.UP) || p_55615_.m_60713_(Blocks.f_50332_);
   }

   private void m_55530_(Level p_55531_, BlockPos p_55532_, BlockState p_55533_) {
      int i = this.m_55527_(p_55531_, p_55532_);
      if (p_55533_.m_61143_(f_55500_) != i) {
         if (p_55531_.m_8055_(p_55532_) == p_55533_) {
            p_55531_.m_7731_(p_55532_, p_55533_.m_61124_(f_55500_, Integer.valueOf(i)), 2);
         }

         Set<BlockPos> set = Sets.newHashSet();
         set.add(p_55532_);

         for(Direction direction : Direction.values()) {
            set.add(p_55532_.m_142300_(direction));
         }

         for(BlockPos blockpos : set) {
            p_55531_.m_46672_(blockpos, this);
         }
      }

   }

   private int m_55527_(Level p_55528_, BlockPos p_55529_) {
      this.f_55508_ = false;
      int i = p_55528_.m_46755_(p_55529_);
      this.f_55508_ = true;
      int j = 0;
      if (i < 15) {
         for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = p_55529_.m_142300_(direction);
            BlockState blockstate = p_55528_.m_8055_(blockpos);
            j = Math.max(j, this.m_55648_(blockstate));
            BlockPos blockpos1 = p_55529_.m_7494_();
            if (blockstate.m_60796_(p_55528_, blockpos) && !p_55528_.m_8055_(blockpos1).m_60796_(p_55528_, blockpos1)) {
               j = Math.max(j, this.m_55648_(p_55528_.m_8055_(blockpos.m_7494_())));
            } else if (!blockstate.m_60796_(p_55528_, blockpos)) {
               j = Math.max(j, this.m_55648_(p_55528_.m_8055_(blockpos.m_7495_())));
            }
         }
      }

      return Math.max(i, j - 1);
   }

   private int m_55648_(BlockState p_55649_) {
      return p_55649_.m_60713_(this) ? p_55649_.m_61143_(f_55500_) : 0;
   }

   private void m_55616_(Level p_55617_, BlockPos p_55618_) {
      if (p_55617_.m_8055_(p_55618_).m_60713_(this)) {
         p_55617_.m_46672_(p_55618_, this);

         for(Direction direction : Direction.values()) {
            p_55617_.m_46672_(p_55618_.m_142300_(direction), this);
         }

      }
   }

   public void m_6807_(BlockState p_55630_, Level p_55631_, BlockPos p_55632_, BlockState p_55633_, boolean p_55634_) {
      if (!p_55633_.m_60713_(p_55630_.m_60734_()) && !p_55631_.f_46443_) {
         this.m_55530_(p_55631_, p_55632_, p_55630_);

         for(Direction direction : Direction.Plane.VERTICAL) {
            p_55631_.m_46672_(p_55632_.m_142300_(direction), this);
         }

         this.m_55637_(p_55631_, p_55632_);
      }
   }

   public void m_6810_(BlockState p_55568_, Level p_55569_, BlockPos p_55570_, BlockState p_55571_, boolean p_55572_) {
      if (!p_55572_ && !p_55568_.m_60713_(p_55571_.m_60734_())) {
         super.m_6810_(p_55568_, p_55569_, p_55570_, p_55571_, p_55572_);
         if (!p_55569_.f_46443_) {
            for(Direction direction : Direction.values()) {
               p_55569_.m_46672_(p_55570_.m_142300_(direction), this);
            }

            this.m_55530_(p_55569_, p_55570_, p_55568_);
            this.m_55637_(p_55569_, p_55570_);
         }
      }
   }

   private void m_55637_(Level p_55638_, BlockPos p_55639_) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         this.m_55616_(p_55638_, p_55639_.m_142300_(direction));
      }

      for(Direction direction1 : Direction.Plane.HORIZONTAL) {
         BlockPos blockpos = p_55639_.m_142300_(direction1);
         if (p_55638_.m_8055_(blockpos).m_60796_(p_55638_, blockpos)) {
            this.m_55616_(p_55638_, blockpos.m_7494_());
         } else {
            this.m_55616_(p_55638_, blockpos.m_7495_());
         }
      }

   }

   public void m_6861_(BlockState p_55561_, Level p_55562_, BlockPos p_55563_, Block p_55564_, BlockPos p_55565_, boolean p_55566_) {
      if (!p_55562_.f_46443_) {
         if (p_55561_.m_60710_(p_55562_, p_55563_)) {
            this.m_55530_(p_55562_, p_55563_, p_55561_);
         } else {
            m_49950_(p_55561_, p_55562_, p_55563_);
            p_55562_.m_7471_(p_55563_, false);
         }

      }
   }

   public int m_6376_(BlockState p_55625_, BlockGetter p_55626_, BlockPos p_55627_, Direction p_55628_) {
      return !this.f_55508_ ? 0 : p_55625_.m_60746_(p_55626_, p_55627_, p_55628_);
   }

   public int m_6378_(BlockState p_55549_, BlockGetter p_55550_, BlockPos p_55551_, Direction p_55552_) {
      if (this.f_55508_ && p_55552_ != Direction.DOWN) {
         int i = p_55549_.m_61143_(f_55500_);
         if (i == 0) {
            return 0;
         } else {
            return p_55552_ != Direction.UP && !this.m_55514_(p_55550_, p_55549_, p_55551_).m_61143_(f_55501_.get(p_55552_.m_122424_())).m_61761_() ? 0 : i;
         }
      } else {
         return 0;
      }
   }

   protected static boolean m_55640_(BlockState p_55641_) {
      return m_55594_(p_55641_, (Direction)null);
   }

   protected static boolean m_55594_(BlockState p_55595_, @Nullable Direction p_55596_) {
      if (p_55595_.m_60713_(Blocks.f_50088_)) {
         return true;
      } else if (p_55595_.m_60713_(Blocks.f_50146_)) {
         Direction direction = p_55595_.m_61143_(RepeaterBlock.f_54117_);
         return direction == p_55596_ || direction.m_122424_() == p_55596_;
      } else if (p_55595_.m_60713_(Blocks.f_50455_)) {
         return p_55596_ == p_55595_.m_61143_(ObserverBlock.f_52588_);
      } else {
         return p_55595_.m_60803_() && p_55596_ != null;
      }
   }

   public boolean m_7899_(BlockState p_55636_) {
      return this.f_55508_;
   }

   public static int m_55606_(int p_55607_) {
      Vec3 vec3 = f_55506_[p_55607_];
      return Mth.m_14159_((float)vec3.m_7096_(), (float)vec3.m_7098_(), (float)vec3.m_7094_());
   }

   private void m_154309_(Level p_154310_, Random p_154311_, BlockPos p_154312_, Vec3 p_154313_, Direction p_154314_, Direction p_154315_, float p_154316_, float p_154317_) {
      float f = p_154317_ - p_154316_;
      if (!(p_154311_.nextFloat() >= 0.2F * f)) {
         float f1 = 0.4375F;
         float f2 = p_154316_ + f * p_154311_.nextFloat();
         double d0 = 0.5D + (double)(0.4375F * (float)p_154314_.m_122429_()) + (double)(f2 * (float)p_154315_.m_122429_());
         double d1 = 0.5D + (double)(0.4375F * (float)p_154314_.m_122430_()) + (double)(f2 * (float)p_154315_.m_122430_());
         double d2 = 0.5D + (double)(0.4375F * (float)p_154314_.m_122431_()) + (double)(f2 * (float)p_154315_.m_122431_());
         p_154310_.m_7106_(new DustParticleOptions(new Vector3f(p_154313_), 1.0F), (double)p_154312_.m_123341_() + d0, (double)p_154312_.m_123342_() + d1, (double)p_154312_.m_123343_() + d2, 0.0D, 0.0D, 0.0D);
      }
   }

   public void m_7100_(BlockState p_55574_, Level p_55575_, BlockPos p_55576_, Random p_55577_) {
      int i = p_55574_.m_61143_(f_55500_);
      if (i != 0) {
         for(Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneside = p_55574_.m_61143_(f_55501_.get(direction));
            switch(redstoneside) {
            case UP:
               this.m_154309_(p_55575_, p_55577_, p_55576_, f_55506_[i], direction, Direction.UP, -0.5F, 0.5F);
            case SIDE:
               this.m_154309_(p_55575_, p_55577_, p_55576_, f_55506_[i], Direction.DOWN, direction, 0.0F, 0.5F);
               break;
            case NONE:
            default:
               this.m_154309_(p_55575_, p_55577_, p_55576_, f_55506_[i], Direction.DOWN, direction, 0.0F, 0.3F);
            }
         }

      }
   }

   public BlockState m_6843_(BlockState p_55592_, Rotation p_55593_) {
      switch(p_55593_) {
      case CLOCKWISE_180:
         return p_55592_.m_61124_(f_55496_, p_55592_.m_61143_(f_55498_)).m_61124_(f_55497_, p_55592_.m_61143_(f_55499_)).m_61124_(f_55498_, p_55592_.m_61143_(f_55496_)).m_61124_(f_55499_, p_55592_.m_61143_(f_55497_));
      case COUNTERCLOCKWISE_90:
         return p_55592_.m_61124_(f_55496_, p_55592_.m_61143_(f_55497_)).m_61124_(f_55497_, p_55592_.m_61143_(f_55498_)).m_61124_(f_55498_, p_55592_.m_61143_(f_55499_)).m_61124_(f_55499_, p_55592_.m_61143_(f_55496_));
      case CLOCKWISE_90:
         return p_55592_.m_61124_(f_55496_, p_55592_.m_61143_(f_55499_)).m_61124_(f_55497_, p_55592_.m_61143_(f_55496_)).m_61124_(f_55498_, p_55592_.m_61143_(f_55497_)).m_61124_(f_55499_, p_55592_.m_61143_(f_55498_));
      default:
         return p_55592_;
      }
   }

   public BlockState m_6943_(BlockState p_55589_, Mirror p_55590_) {
      switch(p_55590_) {
      case LEFT_RIGHT:
         return p_55589_.m_61124_(f_55496_, p_55589_.m_61143_(f_55498_)).m_61124_(f_55498_, p_55589_.m_61143_(f_55496_));
      case FRONT_BACK:
         return p_55589_.m_61124_(f_55497_, p_55589_.m_61143_(f_55499_)).m_61124_(f_55499_, p_55589_.m_61143_(f_55497_));
      default:
         return super.m_6943_(p_55589_, p_55590_);
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55605_) {
      p_55605_.m_61104_(f_55496_, f_55497_, f_55498_, f_55499_, f_55500_);
   }

   public InteractionResult m_6227_(BlockState p_55554_, Level p_55555_, BlockPos p_55556_, Player p_55557_, InteractionHand p_55558_, BlockHitResult p_55559_) {
      if (!p_55557_.m_150110_().f_35938_) {
         return InteractionResult.PASS;
      } else {
         if (m_55644_(p_55554_) || m_55646_(p_55554_)) {
            BlockState blockstate = m_55644_(p_55554_) ? this.m_49966_() : this.f_55507_;
            blockstate = blockstate.m_61124_(f_55500_, p_55554_.m_61143_(f_55500_));
            blockstate = this.m_55514_(p_55555_, blockstate, p_55556_);
            if (blockstate != p_55554_) {
               p_55555_.m_7731_(p_55556_, blockstate, 3);
               this.m_55534_(p_55555_, p_55556_, p_55554_, blockstate);
               return InteractionResult.SUCCESS;
            }
         }

         return InteractionResult.PASS;
      }
   }

   private void m_55534_(Level p_55535_, BlockPos p_55536_, BlockState p_55537_, BlockState p_55538_) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BlockPos blockpos = p_55536_.m_142300_(direction);
         if (p_55537_.m_61143_(f_55501_.get(direction)).m_61761_() != p_55538_.m_61143_(f_55501_.get(direction)).m_61761_() && p_55535_.m_8055_(blockpos).m_60796_(p_55535_, blockpos)) {
            p_55535_.m_46590_(blockpos, p_55538_.m_60734_(), direction.m_122424_());
         }
      }

   }
}
