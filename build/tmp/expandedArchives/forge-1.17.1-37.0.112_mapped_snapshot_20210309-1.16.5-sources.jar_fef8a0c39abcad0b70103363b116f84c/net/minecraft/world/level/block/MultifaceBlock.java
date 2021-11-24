package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MultifaceBlock extends Block {
   private static final float f_153807_ = 1.0F;
   private static final VoxelShape f_153808_ = Block.m_49796_(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   private static final VoxelShape f_153809_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
   private static final VoxelShape f_153810_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
   private static final VoxelShape f_153811_ = Block.m_49796_(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   private static final VoxelShape f_153812_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
   private static final VoxelShape f_153813_ = Block.m_49796_(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
   private static final Map<Direction, BooleanProperty> f_153814_ = PipeBlock.f_55154_;
   private static final Map<Direction, VoxelShape> f_153815_ = Util.m_137469_(Maps.newEnumMap(Direction.class), (p_153923_) -> {
      p_153923_.put(Direction.NORTH, f_153812_);
      p_153923_.put(Direction.EAST, f_153811_);
      p_153923_.put(Direction.SOUTH, f_153813_);
      p_153923_.put(Direction.WEST, f_153810_);
      p_153923_.put(Direction.UP, f_153808_);
      p_153923_.put(Direction.DOWN, f_153809_);
   });
   protected static final Direction[] f_153806_ = Direction.values();
   private final ImmutableMap<BlockState, VoxelShape> f_153816_;
   private final boolean f_153817_;
   private final boolean f_153818_;
   private final boolean f_153819_;

   public MultifaceBlock(BlockBehaviour.Properties p_153822_) {
      super(p_153822_);
      this.m_49959_(m_153918_(this.f_49792_));
      this.f_153816_ = this.m_152458_(MultifaceBlock::m_153958_);
      this.f_153817_ = Direction.Plane.HORIZONTAL.m_122557_().allMatch(this::m_153920_);
      this.f_153818_ = Direction.Plane.HORIZONTAL.m_122557_().filter(Direction.Axis.X).filter(this::m_153920_).count() % 2L == 0L;
      this.f_153819_ = Direction.Plane.HORIZONTAL.m_122557_().filter(Direction.Axis.Z).filter(this::m_153920_).count() % 2L == 0L;
   }

   protected boolean m_153920_(Direction p_153921_) {
      return true;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153917_) {
      for(Direction direction : f_153806_) {
         if (this.m_153920_(direction)) {
            p_153917_.m_61104_(m_153933_(direction));
         }
      }

   }

   public BlockState m_7417_(BlockState p_153904_, Direction p_153905_, BlockState p_153906_, LevelAccessor p_153907_, BlockPos p_153908_, BlockPos p_153909_) {
      if (!m_153960_(p_153904_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         return m_153900_(p_153904_, p_153905_) && !m_153829_(p_153907_, p_153905_, p_153909_, p_153906_) ? m_153897_(p_153904_, m_153933_(p_153905_)) : p_153904_;
      }
   }

   public VoxelShape m_5940_(BlockState p_153851_, BlockGetter p_153852_, BlockPos p_153853_, CollisionContext p_153854_) {
      return this.f_153816_.get(p_153851_);
   }

   public boolean m_7898_(BlockState p_153888_, LevelReader p_153889_, BlockPos p_153890_) {
      boolean flag = false;

      for(Direction direction : f_153806_) {
         if (m_153900_(p_153888_, direction)) {
            BlockPos blockpos = p_153890_.m_142300_(direction);
            if (!m_153829_(p_153889_, direction, blockpos, p_153889_.m_8055_(blockpos))) {
               return false;
            }

            flag = true;
         }
      }

      return flag;
   }

   public boolean m_6864_(BlockState p_153848_, BlockPlaceContext p_153849_) {
      return m_153962_(p_153848_);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_153824_) {
      Level level = p_153824_.m_43725_();
      BlockPos blockpos = p_153824_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      return Arrays.stream(p_153824_.m_6232_()).map((p_153865_) -> {
         return this.m_153940_(blockstate, level, blockpos, p_153865_);
      }).filter(Objects::nonNull).findFirst().orElse((BlockState)null);
   }

   @Nullable
   public BlockState m_153940_(BlockState p_153941_, BlockGetter p_153942_, BlockPos p_153943_, Direction p_153944_) {
      if (!this.m_153920_(p_153944_)) {
         return null;
      } else {
         BlockState blockstate;
         if (p_153941_.m_60713_(this)) {
            if (m_153900_(p_153941_, p_153944_)) {
               return null;
            }

            blockstate = p_153941_;
         } else if (this.m_153964_() && p_153941_.m_60819_().m_164512_(Fluids.f_76193_)) {
            blockstate = this.m_49966_().m_61124_(BlockStateProperties.f_61362_, Boolean.valueOf(true));
         } else {
            blockstate = this.m_49966_();
         }

         BlockPos blockpos = p_153943_.m_142300_(p_153944_);
         return m_153829_(p_153942_, p_153944_, blockpos, p_153942_.m_8055_(blockpos)) ? blockstate.m_61124_(m_153933_(p_153944_), Boolean.valueOf(true)) : null;
      }
   }

   public BlockState m_6843_(BlockState p_153895_, Rotation p_153896_) {
      return !this.f_153817_ ? p_153895_ : this.m_153910_(p_153895_, p_153896_::m_55954_);
   }

   public BlockState m_6943_(BlockState p_153892_, Mirror p_153893_) {
      if (p_153893_ == Mirror.FRONT_BACK && !this.f_153818_) {
         return p_153892_;
      } else {
         return p_153893_ == Mirror.LEFT_RIGHT && !this.f_153819_ ? p_153892_ : this.m_153910_(p_153892_, p_153893_::m_54848_);
      }
   }

   private BlockState m_153910_(BlockState p_153911_, Function<Direction, Direction> p_153912_) {
      BlockState blockstate = p_153911_;

      for(Direction direction : f_153806_) {
         if (this.m_153920_(direction)) {
            blockstate = blockstate.m_61124_(m_153933_(p_153912_.apply(direction)), p_153911_.m_61143_(m_153933_(direction)));
         }
      }

      return blockstate;
   }

   public boolean m_153935_(BlockState p_153936_, ServerLevel p_153937_, BlockPos p_153938_, Random p_153939_) {
      List<Direction> list = Lists.newArrayList(f_153806_);
      Collections.shuffle(list);
      return list.stream().filter((p_153955_) -> {
         return m_153900_(p_153936_, p_153955_);
      }).anyMatch((p_153846_) -> {
         return this.m_153873_(p_153936_, p_153937_, p_153938_, p_153846_, p_153939_, false);
      });
   }

   public boolean m_153873_(BlockState p_153874_, LevelAccessor p_153875_, BlockPos p_153876_, Direction p_153877_, Random p_153878_, boolean p_153879_) {
      List<Direction> list = Arrays.asList(f_153806_);
      Collections.shuffle(list, p_153878_);
      return list.stream().anyMatch((p_153886_) -> {
         return this.m_153866_(p_153874_, p_153875_, p_153876_, p_153877_, p_153886_, p_153879_);
      });
   }

   public boolean m_153866_(BlockState p_153867_, LevelAccessor p_153868_, BlockPos p_153869_, Direction p_153870_, Direction p_153871_, boolean p_153872_) {
      Optional<Pair<BlockPos, Direction>> optional = this.m_153855_(p_153867_, p_153868_, p_153869_, p_153870_, p_153871_);
      if (optional.isPresent()) {
         Pair<BlockPos, Direction> pair = optional.get();
         return this.m_153834_(p_153868_, pair.getFirst(), pair.getSecond(), p_153872_);
      } else {
         return false;
      }
   }

   protected boolean m_153948_(BlockState p_153949_, BlockGetter p_153950_, BlockPos p_153951_, Direction p_153952_) {
      return Stream.of(f_153806_).anyMatch((p_153929_) -> {
         return this.m_153855_(p_153949_, p_153950_, p_153951_, p_153952_, p_153929_).isPresent();
      });
   }

   private Optional<Pair<BlockPos, Direction>> m_153855_(BlockState p_153856_, BlockGetter p_153857_, BlockPos p_153858_, Direction p_153859_, Direction p_153860_) {
      if (p_153860_.m_122434_() != p_153859_.m_122434_() && m_153900_(p_153856_, p_153859_) && !m_153900_(p_153856_, p_153860_)) {
         if (this.m_153825_(p_153857_, p_153858_, p_153860_)) {
            return Optional.of(Pair.of(p_153858_, p_153860_));
         } else {
            BlockPos blockpos = p_153858_.m_142300_(p_153860_);
            if (this.m_153825_(p_153857_, blockpos, p_153859_)) {
               return Optional.of(Pair.of(blockpos, p_153859_));
            } else {
               BlockPos blockpos1 = blockpos.m_142300_(p_153859_);
               Direction direction = p_153860_.m_122424_();
               return this.m_153825_(p_153857_, blockpos1, direction) ? Optional.of(Pair.of(blockpos1, direction)) : Optional.empty();
            }
         }
      } else {
         return Optional.empty();
      }
   }

   private boolean m_153825_(BlockGetter p_153826_, BlockPos p_153827_, Direction p_153828_) {
      BlockState blockstate = p_153826_.m_8055_(p_153827_);
      if (!this.m_153956_(blockstate)) {
         return false;
      } else {
         BlockState blockstate1 = this.m_153940_(blockstate, p_153826_, p_153827_, p_153828_);
         return blockstate1 != null;
      }
   }

   private boolean m_153834_(LevelAccessor p_153835_, BlockPos p_153836_, Direction p_153837_, boolean p_153838_) {
      BlockState blockstate = p_153835_.m_8055_(p_153836_);
      BlockState blockstate1 = this.m_153940_(blockstate, p_153835_, p_153836_, p_153837_);
      if (blockstate1 != null) {
         if (p_153838_) {
            p_153835_.m_46865_(p_153836_).m_8113_(p_153836_);
         }

         return p_153835_.m_7731_(p_153836_, blockstate1, 2);
      } else {
         return false;
      }
   }

   private boolean m_153956_(BlockState p_153957_) {
      return p_153957_.m_60795_() || p_153957_.m_60713_(this) || p_153957_.m_60713_(Blocks.f_49990_) && p_153957_.m_60819_().m_76170_();
   }

   private static boolean m_153900_(BlockState p_153901_, Direction p_153902_) {
      BooleanProperty booleanproperty = m_153933_(p_153902_);
      return p_153901_.m_61138_(booleanproperty) && p_153901_.m_61143_(booleanproperty);
   }

   private static boolean m_153829_(BlockGetter p_153830_, Direction p_153831_, BlockPos p_153832_, BlockState p_153833_) {
      return Block.m_49918_(p_153833_.m_60812_(p_153830_, p_153832_), p_153831_.m_122424_());
   }

   private boolean m_153964_() {
      return this.f_49792_.m_61092_().contains(BlockStateProperties.f_61362_);
   }

   private static BlockState m_153897_(BlockState p_153898_, BooleanProperty p_153899_) {
      BlockState blockstate = p_153898_.m_61124_(p_153899_, Boolean.valueOf(false));
      return m_153960_(blockstate) ? blockstate : Blocks.f_50016_.m_49966_();
   }

   public static BooleanProperty m_153933_(Direction p_153934_) {
      return f_153814_.get(p_153934_);
   }

   private static BlockState m_153918_(StateDefinition<Block, BlockState> p_153919_) {
      BlockState blockstate = p_153919_.m_61090_();

      for(BooleanProperty booleanproperty : f_153814_.values()) {
         if (blockstate.m_61138_(booleanproperty)) {
            blockstate = blockstate.m_61124_(booleanproperty, Boolean.valueOf(false));
         }
      }

      return blockstate;
   }

   private static VoxelShape m_153958_(BlockState p_153959_) {
      VoxelShape voxelshape = Shapes.m_83040_();

      for(Direction direction : f_153806_) {
         if (m_153900_(p_153959_, direction)) {
            voxelshape = Shapes.m_83110_(voxelshape, f_153815_.get(direction));
         }
      }

      return voxelshape.m_83281_() ? Shapes.m_83144_() : voxelshape;
   }

   protected static boolean m_153960_(BlockState p_153961_) {
      return Arrays.stream(f_153806_).anyMatch((p_153947_) -> {
         return m_153900_(p_153961_, p_153947_);
      });
   }

   private static boolean m_153962_(BlockState p_153963_) {
      return Arrays.stream(f_153806_).anyMatch((p_153932_) -> {
         return !m_153900_(p_153963_, p_153932_);
      });
   }
}