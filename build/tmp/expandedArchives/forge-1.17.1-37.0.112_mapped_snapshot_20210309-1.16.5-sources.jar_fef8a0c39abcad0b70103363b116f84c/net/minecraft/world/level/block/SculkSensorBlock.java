package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkSensorBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final int f_154383_ = 40;
   public static final int f_154384_ = 1;
   public static final Object2IntMap<GameEvent> f_154385_ = Object2IntMaps.unmodifiable(Util.m_137469_(new Object2IntOpenHashMap<>(), (p_154469_) -> {
      p_154469_.put(GameEvent.f_157785_, 1);
      p_154469_.put(GameEvent.f_157815_, 2);
      p_154469_.put(GameEvent.f_157786_, 3);
      p_154469_.put(GameEvent.f_157807_, 4);
      p_154469_.put(GameEvent.f_157770_, 5);
      p_154469_.put(GameEvent.f_157784_, 6);
      p_154469_.put(GameEvent.f_157787_, 6);
      p_154469_.put(GameEvent.f_157773_, 6);
      p_154469_.put(GameEvent.f_157780_, 6);
      p_154469_.put(GameEvent.f_157792_, 6);
      p_154469_.put(GameEvent.f_157778_, 7);
      p_154469_.put(GameEvent.f_157805_, 7);
      p_154469_.put(GameEvent.f_157776_, 7);
      p_154469_.put(GameEvent.f_157777_, 8);
      p_154469_.put(GameEvent.f_157806_, 8);
      p_154469_.put(GameEvent.f_157771_, 8);
      p_154469_.put(GameEvent.f_157808_, 8);
      p_154469_.put(GameEvent.f_157811_, 9);
      p_154469_.put(GameEvent.f_157781_, 9);
      p_154469_.put(GameEvent.f_157779_, 9);
      p_154469_.put(GameEvent.f_157793_, 10);
      p_154469_.put(GameEvent.f_157801_, 10);
      p_154469_.put(GameEvent.f_157800_, 10);
      p_154469_.put(GameEvent.f_157795_, 10);
      p_154469_.put(GameEvent.f_157804_, 10);
      p_154469_.put(GameEvent.f_157796_, 11);
      p_154469_.put(GameEvent.f_157799_, 11);
      p_154469_.put(GameEvent.f_157798_, 11);
      p_154469_.put(GameEvent.f_157791_, 11);
      p_154469_.put(GameEvent.f_157810_, 12);
      p_154469_.put(GameEvent.f_157797_, 12);
      p_154469_.put(GameEvent.f_157769_, 12);
      p_154469_.put(GameEvent.f_157809_, 13);
      p_154469_.put(GameEvent.f_157794_, 13);
      p_154469_.put(GameEvent.f_157816_, 13);
      p_154469_.put(GameEvent.f_157814_, 14);
      p_154469_.put(GameEvent.f_157802_, 14);
      p_154469_.put(GameEvent.f_157774_, 14);
      p_154469_.put(GameEvent.f_157782_, 14);
      p_154469_.put(GameEvent.f_157775_, 15);
      p_154469_.put(GameEvent.f_157803_, 15);
      p_154469_.put(GameEvent.f_157813_, 15);
      p_154469_.put(GameEvent.f_157812_, 15);
      p_154469_.put(GameEvent.f_157772_, 15);
      p_154469_.put(GameEvent.f_157783_, 15);
   }));
   public static final EnumProperty<SculkSensorPhase> f_154386_ = BlockStateProperties.f_155999_;
   public static final IntegerProperty f_154387_ = BlockStateProperties.f_61426_;
   public static final BooleanProperty f_154388_ = BlockStateProperties.f_61362_;
   protected static final VoxelShape f_154389_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
   private final int f_154390_;

   public SculkSensorBlock(BlockBehaviour.Properties p_154393_, int p_154394_) {
      super(p_154393_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_154386_, SculkSensorPhase.INACTIVE).m_61124_(f_154387_, Integer.valueOf(0)).m_61124_(f_154388_, Boolean.valueOf(false)));
      this.f_154390_ = p_154394_;
   }

   public int m_154482_() {
      return this.f_154390_;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_154396_) {
      BlockPos blockpos = p_154396_.m_8083_();
      FluidState fluidstate = p_154396_.m_43725_().m_6425_(blockpos);
      return this.m_49966_().m_61124_(f_154388_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
   }

   public FluidState m_5888_(BlockState p_154479_) {
      return p_154479_.m_61143_(f_154388_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_154479_);
   }

   public void m_7458_(BlockState p_154422_, ServerLevel p_154423_, BlockPos p_154424_, Random p_154425_) {
      if (m_154487_(p_154422_) != SculkSensorPhase.ACTIVE) {
         if (m_154487_(p_154422_) == SculkSensorPhase.COOLDOWN) {
            p_154423_.m_7731_(p_154424_, p_154422_.m_61124_(f_154386_, SculkSensorPhase.INACTIVE), 3);
         }

      } else {
         m_154407_(p_154423_, p_154424_, p_154422_);
      }
   }

   public void m_6807_(BlockState p_154471_, Level p_154472_, BlockPos p_154473_, BlockState p_154474_, boolean p_154475_) {
      if (!p_154472_.m_5776_() && !p_154471_.m_60713_(p_154474_.m_60734_())) {
         if (p_154471_.m_61143_(f_154387_) > 0 && !p_154472_.m_6219_().m_5916_(p_154473_, this)) {
            p_154472_.m_7731_(p_154473_, p_154471_.m_61124_(f_154387_, Integer.valueOf(0)), 18);
         }

         p_154472_.m_6219_().m_5945_(new BlockPos(p_154473_), p_154471_.m_60734_(), 1);
      }
   }

   public void m_6810_(BlockState p_154446_, Level p_154447_, BlockPos p_154448_, BlockState p_154449_, boolean p_154450_) {
      if (!p_154446_.m_60713_(p_154449_.m_60734_())) {
         if (m_154487_(p_154446_) == SculkSensorPhase.ACTIVE) {
            m_154404_(p_154447_, p_154448_);
         }

         super.m_6810_(p_154446_, p_154447_, p_154448_, p_154449_, p_154450_);
      }
   }

   public BlockState m_7417_(BlockState p_154457_, Direction p_154458_, BlockState p_154459_, LevelAccessor p_154460_, BlockPos p_154461_, BlockPos p_154462_) {
      if (p_154457_.m_61143_(f_154388_)) {
         p_154460_.m_6217_().m_5945_(p_154461_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_154460_));
      }

      return super.m_7417_(p_154457_, p_154458_, p_154459_, p_154460_, p_154461_, p_154462_);
   }

   private static void m_154404_(Level p_154405_, BlockPos p_154406_) {
      p_154405_.m_46672_(p_154406_, Blocks.f_152500_);
      p_154405_.m_46672_(p_154406_.m_142300_(Direction.UP.m_122424_()), Blocks.f_152500_);
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos p_154466_, BlockState p_154467_) {
      return new SculkSensorBlockEntity(p_154466_, p_154467_);
   }

   @Nullable
   public <T extends BlockEntity> GameEventListener m_142226_(Level p_154398_, T p_154399_) {
      return p_154399_ instanceof SculkSensorBlockEntity ? ((SculkSensorBlockEntity)p_154399_).m_155655_() : null;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_154401_, BlockState p_154402_, BlockEntityType<T> p_154403_) {
      return !p_154401_.f_46443_ ? m_152132_(p_154403_, BlockEntityType.f_155257_, (p_154417_, p_154418_, p_154419_, p_154420_) -> {
         p_154420_.m_155655_().m_157898_(p_154417_);
      }) : null;
   }

   public RenderShape m_7514_(BlockState p_154477_) {
      return RenderShape.MODEL;
   }

   public VoxelShape m_5940_(BlockState p_154432_, BlockGetter p_154433_, BlockPos p_154434_, CollisionContext p_154435_) {
      return f_154389_;
   }

   public boolean m_7899_(BlockState p_154484_) {
      return true;
   }

   public int m_6378_(BlockState p_154437_, BlockGetter p_154438_, BlockPos p_154439_, Direction p_154440_) {
      return p_154437_.m_61143_(f_154387_);
   }

   public static SculkSensorPhase m_154487_(BlockState p_154488_) {
      return p_154488_.m_61143_(f_154386_);
   }

   public static boolean m_154489_(BlockState p_154490_) {
      return m_154487_(p_154490_) == SculkSensorPhase.INACTIVE;
   }

   public static void m_154407_(Level p_154408_, BlockPos p_154409_, BlockState p_154410_) {
      p_154408_.m_7731_(p_154409_, p_154410_.m_61124_(f_154386_, SculkSensorPhase.COOLDOWN).m_61124_(f_154387_, Integer.valueOf(0)), 3);
      p_154408_.m_6219_().m_5945_(new BlockPos(p_154409_), p_154410_.m_60734_(), 1);
      if (!p_154410_.m_61143_(f_154388_)) {
         p_154408_.m_5594_((Player)null, p_154409_, SoundEvents.f_144213_, SoundSource.BLOCKS, 1.0F, p_154408_.f_46441_.nextFloat() * 0.2F + 0.8F);
      }

      m_154404_(p_154408_, p_154409_);
   }

   public static void m_154411_(Level p_154412_, BlockPos p_154413_, BlockState p_154414_, int p_154415_) {
      p_154412_.m_7731_(p_154413_, p_154414_.m_61124_(f_154386_, SculkSensorPhase.ACTIVE).m_61124_(f_154387_, Integer.valueOf(p_154415_)), 3);
      p_154412_.m_6219_().m_5945_(new BlockPos(p_154413_), p_154414_.m_60734_(), 40);
      m_154404_(p_154412_, p_154413_);
      if (!p_154414_.m_61143_(f_154388_)) {
         p_154412_.m_6263_((Player)null, (double)p_154413_.m_123341_() + 0.5D, (double)p_154413_.m_123342_() + 0.5D, (double)p_154413_.m_123343_() + 0.5D, SoundEvents.f_144212_, SoundSource.BLOCKS, 1.0F, p_154412_.f_46441_.nextFloat() * 0.2F + 0.8F);
      }

   }

   public void m_7100_(BlockState p_154452_, Level p_154453_, BlockPos p_154454_, Random p_154455_) {
      if (m_154487_(p_154452_) == SculkSensorPhase.ACTIVE) {
         Direction direction = Direction.m_122404_(p_154455_);
         if (direction != Direction.UP && direction != Direction.DOWN) {
            double d0 = (double)p_154454_.m_123341_() + 0.5D + (direction.m_122429_() == 0 ? 0.5D - p_154455_.nextDouble() : (double)direction.m_122429_() * 0.6D);
            double d1 = (double)p_154454_.m_123342_() + 0.25D;
            double d2 = (double)p_154454_.m_123343_() + 0.5D + (direction.m_122431_() == 0 ? 0.5D - p_154455_.nextDouble() : (double)direction.m_122431_() * 0.6D);
            double d3 = (double)p_154455_.nextFloat() * 0.04D;
            p_154453_.m_7106_(DustColorTransitionOptions.f_175752_, d0, d1, d2, 0.0D, d3, 0.0D);
         }
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_154464_) {
      p_154464_.m_61104_(f_154386_, f_154387_, f_154388_);
   }

   public boolean m_7278_(BlockState p_154481_) {
      return true;
   }

   public int m_6782_(BlockState p_154442_, Level p_154443_, BlockPos p_154444_) {
      BlockEntity blockentity = p_154443_.m_7702_(p_154444_);
      if (blockentity instanceof SculkSensorBlockEntity) {
         SculkSensorBlockEntity sculksensorblockentity = (SculkSensorBlockEntity)blockentity;
         return m_154487_(p_154442_) == SculkSensorPhase.ACTIVE ? sculksensorblockentity.m_155656_() : 0;
      } else {
         return 0;
      }
   }

   public boolean m_7357_(BlockState p_154427_, BlockGetter p_154428_, BlockPos p_154429_, PathComputationType p_154430_) {
      return false;
   }

   public boolean m_7923_(BlockState p_154486_) {
      return true;
   }
}