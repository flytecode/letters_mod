package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PointedDripstoneBlock extends Block implements Fallable, SimpleWaterloggedBlock {
   public static final DirectionProperty f_154009_ = BlockStateProperties.f_155997_;
   public static final EnumProperty<DripstoneThickness> f_154010_ = BlockStateProperties.f_155998_;
   public static final BooleanProperty f_154011_ = BlockStateProperties.f_61362_;
   private static final int f_154012_ = 11;
   private static final int f_154013_ = Integer.MAX_VALUE;
   private static final int f_154014_ = 2;
   private static final float f_154015_ = 0.02F;
   private static final float f_154016_ = 0.12F;
   private static final int f_154017_ = 11;
   private static final float f_154018_ = 0.17578125F;
   private static final float f_154019_ = 0.05859375F;
   private static final double f_154020_ = 0.6D;
   private static final float f_154021_ = 1.0F;
   private static final int f_154022_ = 40;
   private static final int f_153994_ = 6;
   private static final float f_153995_ = 2.0F;
   private static final int f_153996_ = 2;
   private static final float f_153997_ = 5.0F;
   private static final float f_153998_ = 0.011377778F;
   private static final int f_153999_ = 7;
   private static final int f_154000_ = 10;
   private static final float f_154001_ = 0.6875F;
   private static final VoxelShape f_154002_ = Block.m_49796_(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
   private static final VoxelShape f_154003_ = Block.m_49796_(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
   private static final VoxelShape f_154004_ = Block.m_49796_(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
   private static final VoxelShape f_154005_ = Block.m_49796_(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
   private static final VoxelShape f_154006_ = Block.m_49796_(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
   private static final VoxelShape f_154007_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
   private static final float f_154008_ = 0.125F;

   public PointedDripstoneBlock(BlockBehaviour.Properties p_154025_) {
      super(p_154025_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_154009_, Direction.UP).m_61124_(f_154010_, DripstoneThickness.TIP).m_61124_(f_154011_, Boolean.valueOf(false)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_154157_) {
      p_154157_.m_61104_(f_154009_, f_154010_, f_154011_);
   }

   public boolean m_7898_(BlockState p_154137_, LevelReader p_154138_, BlockPos p_154139_) {
      return m_154221_(p_154138_, p_154139_, p_154137_.m_61143_(f_154009_));
   }

   public BlockState m_7417_(BlockState p_154147_, Direction p_154148_, BlockState p_154149_, LevelAccessor p_154150_, BlockPos p_154151_, BlockPos p_154152_) {
      if (p_154147_.m_61143_(f_154011_)) {
         p_154150_.m_6217_().m_5945_(p_154151_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_154150_));
      }

      if (p_154148_ != Direction.UP && p_154148_ != Direction.DOWN) {
         return p_154147_;
      } else {
         Direction direction = p_154147_.m_61143_(f_154009_);
         if (direction == Direction.DOWN && p_154150_.m_6219_().m_5916_(p_154151_, this)) {
            return p_154147_;
         } else if (p_154148_ == direction.m_122424_() && !this.m_7898_(p_154147_, p_154150_, p_154151_)) {
            if (direction == Direction.DOWN) {
               this.m_154126_(p_154147_, p_154150_, p_154151_);
            } else {
               p_154150_.m_6219_().m_5945_(p_154151_, this, 1);
            }

            return p_154147_;
         } else {
            boolean flag = p_154147_.m_61143_(f_154010_) == DripstoneThickness.TIP_MERGE;
            DripstoneThickness dripstonethickness = m_154092_(p_154150_, p_154151_, direction, flag);
            return p_154147_.m_61124_(f_154010_, dripstonethickness);
         }
      }
   }

   public void m_5581_(Level p_154042_, BlockState p_154043_, BlockHitResult p_154044_, Projectile p_154045_) {
      BlockPos blockpos = p_154044_.m_82425_();
      if (!p_154042_.f_46443_ && p_154045_.m_142265_(p_154042_, blockpos) && p_154045_ instanceof ThrownTrident && p_154045_.m_20184_().m_82553_() > 0.6D) {
         p_154042_.m_46961_(blockpos, true);
      }

   }

   public void m_142072_(Level p_154047_, BlockState p_154048_, BlockPos p_154049_, Entity p_154050_, float p_154051_) {
      if (p_154048_.m_61143_(f_154009_) == Direction.UP && p_154048_.m_61143_(f_154010_) == DripstoneThickness.TIP) {
         p_154050_.m_142535_(p_154051_ + 2.0F, 2.0F, DamageSource.f_146703_);
      } else {
         super.m_142072_(p_154047_, p_154048_, p_154049_, p_154050_, p_154051_);
      }

   }

   public void m_7100_(BlockState p_154122_, Level p_154123_, BlockPos p_154124_, Random p_154125_) {
      if (m_154238_(p_154122_)) {
         float f = p_154125_.nextFloat();
         if (!(f > 0.12F)) {
            m_154181_(p_154123_, p_154124_, p_154122_).filter((p_154031_) -> {
               return f < 0.02F || m_154158_(p_154031_);
            }).ifPresent((p_154220_) -> {
               m_154071_(p_154123_, p_154124_, p_154122_, p_154220_);
            });
         }
      }
   }

   public void m_7458_(BlockState p_154107_, ServerLevel p_154108_, BlockPos p_154109_, Random p_154110_) {
      if (m_154242_(p_154107_) && !this.m_7898_(p_154107_, p_154108_, p_154109_)) {
         p_154108_.m_46961_(p_154109_, true);
      } else {
         m_154097_(p_154107_, p_154108_, p_154109_);
      }

   }

   public void m_7455_(BlockState p_154199_, ServerLevel p_154200_, BlockPos p_154201_, Random p_154202_) {
      m_154101_(p_154199_, p_154200_, p_154201_, p_154202_.nextFloat());
      if (p_154202_.nextFloat() < 0.011377778F && m_154203_(p_154199_, p_154200_, p_154201_)) {
         m_154225_(p_154199_, p_154200_, p_154201_, p_154202_);
      }

   }

   @VisibleForTesting
   public static void m_154101_(BlockState p_154102_, ServerLevel p_154103_, BlockPos p_154104_, float p_154105_) {
      if (!(p_154105_ > 0.17578125F) || !(p_154105_ > 0.05859375F)) {
         if (m_154203_(p_154102_, p_154103_, p_154104_)) {
            Fluid fluid = m_154178_(p_154103_, p_154104_);
            float f;
            if (fluid == Fluids.f_76193_) {
               f = 0.17578125F;
            } else {
               if (fluid != Fluids.f_76195_) {
                  return;
               }

               f = 0.05859375F;
            }

            if (!(p_154105_ >= f)) {
               BlockPos blockpos = m_154130_(p_154102_, p_154103_, p_154104_, 11, false);
               if (blockpos != null) {
                  BlockPos blockpos1 = m_154076_(p_154103_, blockpos, fluid);
                  if (blockpos1 != null) {
                     p_154103_.m_46796_(1504, blockpos, 0);
                     int i = blockpos.m_123342_() - blockpos1.m_123342_();
                     int j = 50 + i;
                     BlockState blockstate = p_154103_.m_8055_(blockpos1);
                     p_154103_.m_6219_().m_5945_(blockpos1, blockstate.m_60734_(), j);
                  }
               }
            }
         }
      }
   }

   public PushReaction m_5537_(BlockState p_154237_) {
      return PushReaction.DESTROY;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_154040_) {
      LevelAccessor levelaccessor = p_154040_.m_43725_();
      BlockPos blockpos = p_154040_.m_8083_();
      Direction direction = p_154040_.m_151260_().m_122424_();
      Direction direction1 = m_154190_(levelaccessor, blockpos, direction);
      if (direction1 == null) {
         return null;
      } else {
         boolean flag = !p_154040_.m_7078_();
         DripstoneThickness dripstonethickness = m_154092_(levelaccessor, blockpos, direction1, flag);
         return dripstonethickness == null ? null : this.m_49966_().m_61124_(f_154009_, direction1).m_61124_(f_154010_, dripstonethickness).m_61124_(f_154011_, Boolean.valueOf(levelaccessor.m_6425_(blockpos).m_76152_() == Fluids.f_76193_));
      }
   }

   public FluidState m_5888_(BlockState p_154235_) {
      return p_154235_.m_61143_(f_154011_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_154235_);
   }

   public VoxelShape m_7952_(BlockState p_154170_, BlockGetter p_154171_, BlockPos p_154172_) {
      return Shapes.m_83040_();
   }

   public VoxelShape m_5940_(BlockState p_154117_, BlockGetter p_154118_, BlockPos p_154119_, CollisionContext p_154120_) {
      DripstoneThickness dripstonethickness = p_154117_.m_61143_(f_154010_);
      VoxelShape voxelshape;
      if (dripstonethickness == DripstoneThickness.TIP_MERGE) {
         voxelshape = f_154002_;
      } else if (dripstonethickness == DripstoneThickness.TIP) {
         if (p_154117_.m_61143_(f_154009_) == Direction.DOWN) {
            voxelshape = f_154004_;
         } else {
            voxelshape = f_154003_;
         }
      } else if (dripstonethickness == DripstoneThickness.FRUSTUM) {
         voxelshape = f_154005_;
      } else if (dripstonethickness == DripstoneThickness.MIDDLE) {
         voxelshape = f_154006_;
      } else {
         voxelshape = f_154007_;
      }

      Vec3 vec3 = p_154117_.m_60824_(p_154118_, p_154119_);
      return voxelshape.m_83216_(vec3.f_82479_, 0.0D, vec3.f_82481_);
   }

   public boolean m_180643_(BlockState p_181235_, BlockGetter p_181236_, BlockPos p_181237_) {
      return false;
   }

   public BlockBehaviour.OffsetType m_5858_() {
      return BlockBehaviour.OffsetType.XZ;
   }

   public float m_142740_() {
      return 0.125F;
   }

   public void m_142525_(Level p_154059_, BlockPos p_154060_, FallingBlockEntity p_154061_) {
      if (!p_154061_.m_20067_()) {
         p_154059_.m_46796_(1045, p_154060_, 0);
      }

   }

   public DamageSource m_142088_() {
      return DamageSource.f_146702_;
   }

   public Predicate<Entity> m_142398_() {
      return EntitySelector.f_20406_.and(EntitySelector.f_20403_);
   }

   private void m_154126_(BlockState p_154127_, LevelAccessor p_154128_, BlockPos p_154129_) {
      BlockPos blockpos = m_154130_(p_154127_, p_154128_, p_154129_, Integer.MAX_VALUE, true);
      if (blockpos != null) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.m_122032_();

         while(m_154240_(p_154128_.m_8055_(blockpos$mutableblockpos))) {
            p_154128_.m_6219_().m_5945_(blockpos$mutableblockpos, this, 2);
            blockpos$mutableblockpos.m_122173_(Direction.UP);
         }

      }
   }

   private static int m_154174_(ServerLevel p_154175_, BlockPos p_154176_, int p_154177_) {
      int i = 1;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_154176_.m_122032_().m_122173_(Direction.UP);

      while(i < p_154177_ && m_154240_(p_154175_.m_8055_(blockpos$mutableblockpos))) {
         ++i;
         blockpos$mutableblockpos.m_122173_(Direction.UP);
      }

      return i;
   }

   private static void m_154097_(BlockState p_154098_, ServerLevel p_154099_, BlockPos p_154100_) {
      Vec3 vec3 = Vec3.m_82539_(p_154100_);
      FallingBlockEntity fallingblockentity = new FallingBlockEntity(p_154099_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, p_154098_);
      if (m_154153_(p_154098_, true)) {
         int i = m_154174_(p_154099_, p_154100_, 6);
         float f = 1.0F * (float)i;
         fallingblockentity.m_149656_(f, 40);
      }

      p_154099_.m_7967_(fallingblockentity);
   }

   @VisibleForTesting
   public static void m_154225_(BlockState p_154226_, ServerLevel p_154227_, BlockPos p_154228_, Random p_154229_) {
      BlockState blockstate = p_154227_.m_8055_(p_154228_.m_6630_(1));
      BlockState blockstate1 = p_154227_.m_8055_(p_154228_.m_6630_(2));
      if (m_154140_(blockstate, blockstate1)) {
         BlockPos blockpos = m_154130_(p_154226_, p_154227_, p_154228_, 7, false);
         if (blockpos != null) {
            BlockState blockstate2 = p_154227_.m_8055_(blockpos);
            if (m_154238_(blockstate2) && m_154194_(blockstate2, p_154227_, blockpos)) {
               if (p_154229_.nextBoolean()) {
                  m_154035_(p_154227_, blockpos, Direction.DOWN);
               } else {
                  m_154032_(p_154227_, blockpos);
               }

            }
         }
      }
   }

   private static void m_154032_(ServerLevel p_154033_, BlockPos p_154034_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_154034_.m_122032_();

      for(int i = 0; i < 10; ++i) {
         blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         BlockState blockstate = p_154033_.m_8055_(blockpos$mutableblockpos);
         if (!blockstate.m_60819_().m_76178_()) {
            return;
         }

         if (m_154143_(blockstate, Direction.UP) && m_154194_(blockstate, p_154033_, blockpos$mutableblockpos)) {
            m_154035_(p_154033_, blockpos$mutableblockpos, Direction.UP);
            return;
         }

         if (m_154221_(p_154033_, blockpos$mutableblockpos, Direction.UP) && !p_154033_.m_46801_(blockpos$mutableblockpos.m_7495_())) {
            m_154035_(p_154033_, blockpos$mutableblockpos.m_7495_(), Direction.UP);
            return;
         }
      }

   }

   private static void m_154035_(ServerLevel p_154036_, BlockPos p_154037_, Direction p_154038_) {
      BlockPos blockpos = p_154037_.m_142300_(p_154038_);
      BlockState blockstate = p_154036_.m_8055_(blockpos);
      if (m_154143_(blockstate, p_154038_.m_122424_())) {
         m_154230_(blockstate, p_154036_, blockpos);
      } else if (blockstate.m_60795_() || blockstate.m_60713_(Blocks.f_49990_)) {
         m_154087_(p_154036_, blockpos, p_154038_, DripstoneThickness.TIP);
      }

   }

   private static void m_154087_(LevelAccessor p_154088_, BlockPos p_154089_, Direction p_154090_, DripstoneThickness p_154091_) {
      BlockState blockstate = Blocks.f_152588_.m_49966_().m_61124_(f_154009_, p_154090_).m_61124_(f_154010_, p_154091_).m_61124_(f_154011_, Boolean.valueOf(p_154088_.m_6425_(p_154089_).m_76152_() == Fluids.f_76193_));
      p_154088_.m_7731_(p_154089_, blockstate, 3);
   }

   private static void m_154230_(BlockState p_154231_, LevelAccessor p_154232_, BlockPos p_154233_) {
      BlockPos blockpos;
      BlockPos blockpos1;
      if (p_154231_.m_61143_(f_154009_) == Direction.UP) {
         blockpos1 = p_154233_;
         blockpos = p_154233_.m_7494_();
      } else {
         blockpos = p_154233_;
         blockpos1 = p_154233_.m_7495_();
      }

      m_154087_(p_154232_, blockpos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
      m_154087_(p_154232_, blockpos1, Direction.UP, DripstoneThickness.TIP_MERGE);
   }

   public static void m_154062_(Level p_154063_, BlockPos p_154064_, BlockState p_154065_) {
      m_154181_(p_154063_, p_154064_, p_154065_).ifPresent((p_154189_) -> {
         m_154071_(p_154063_, p_154064_, p_154065_, p_154189_);
      });
   }

   private static void m_154071_(Level p_154072_, BlockPos p_154073_, BlockState p_154074_, Fluid p_154075_) {
      Vec3 vec3 = p_154074_.m_60824_(p_154072_, p_154073_);
      double d0 = 0.0625D;
      double d1 = (double)p_154073_.m_123341_() + 0.5D + vec3.f_82479_;
      double d2 = (double)((float)(p_154073_.m_123342_() + 1) - 0.6875F) - 0.0625D;
      double d3 = (double)p_154073_.m_123343_() + 0.5D + vec3.f_82481_;
      Fluid fluid = m_154052_(p_154072_, p_154075_);
      ParticleOptions particleoptions = fluid.m_76108_(FluidTags.f_13132_) ? ParticleTypes.f_175822_ : ParticleTypes.f_175824_;
      p_154072_.m_7106_(particleoptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
   }

   @Nullable
   private static BlockPos m_154130_(BlockState p_154131_, LevelAccessor p_154132_, BlockPos p_154133_, int p_154134_, boolean p_154135_) {
      if (m_154153_(p_154131_, p_154135_)) {
         return p_154133_;
      } else {
         Direction direction = p_154131_.m_61143_(f_154009_);
         Predicate<BlockState> predicate = (p_154212_) -> {
            return p_154212_.m_60713_(Blocks.f_152588_) && p_154212_.m_61143_(f_154009_) == direction;
         };
         return m_154080_(p_154132_, p_154133_, direction.m_122421_(), predicate, (p_154168_) -> {
            return m_154153_(p_154168_, p_154135_);
         }, p_154134_).orElse((BlockPos)null);
      }
   }

   @Nullable
   private static Direction m_154190_(LevelReader p_154191_, BlockPos p_154192_, Direction p_154193_) {
      Direction direction;
      if (m_154221_(p_154191_, p_154192_, p_154193_)) {
         direction = p_154193_;
      } else {
         if (!m_154221_(p_154191_, p_154192_, p_154193_.m_122424_())) {
            return null;
         }

         direction = p_154193_.m_122424_();
      }

      return direction;
   }

   private static DripstoneThickness m_154092_(LevelReader p_154093_, BlockPos p_154094_, Direction p_154095_, boolean p_154096_) {
      Direction direction = p_154095_.m_122424_();
      BlockState blockstate = p_154093_.m_8055_(p_154094_.m_142300_(p_154095_));
      if (m_154207_(blockstate, direction)) {
         return !p_154096_ && blockstate.m_61143_(f_154010_) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
      } else if (!m_154207_(blockstate, p_154095_)) {
         return DripstoneThickness.TIP;
      } else {
         DripstoneThickness dripstonethickness = blockstate.m_61143_(f_154010_);
         if (dripstonethickness != DripstoneThickness.TIP && dripstonethickness != DripstoneThickness.TIP_MERGE) {
            BlockState blockstate1 = p_154093_.m_8055_(p_154094_.m_142300_(direction));
            return !m_154207_(blockstate1, p_154095_) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
         } else {
            return DripstoneThickness.FRUSTUM;
         }
      }
   }

   public static boolean m_154238_(BlockState p_154239_) {
      return m_154240_(p_154239_) && p_154239_.m_61143_(f_154010_) == DripstoneThickness.TIP && !p_154239_.m_61143_(f_154011_);
   }

   private static boolean m_154194_(BlockState p_154195_, ServerLevel p_154196_, BlockPos p_154197_) {
      Direction direction = p_154195_.m_61143_(f_154009_);
      BlockPos blockpos = p_154197_.m_142300_(direction);
      BlockState blockstate = p_154196_.m_8055_(blockpos);
      if (!blockstate.m_60819_().m_76178_()) {
         return false;
      } else {
         return blockstate.m_60795_() ? true : m_154143_(blockstate, direction.m_122424_());
      }
   }

   private static Optional<BlockPos> m_154066_(Level p_154067_, BlockPos p_154068_, BlockState p_154069_, int p_154070_) {
      Direction direction = p_154069_.m_61143_(f_154009_);
      Predicate<BlockState> predicate = (p_154165_) -> {
         return p_154165_.m_60713_(Blocks.f_152588_) && p_154165_.m_61143_(f_154009_) == direction;
      };
      return m_154080_(p_154067_, p_154068_, direction.m_122424_().m_122421_(), predicate, (p_154245_) -> {
         return !p_154245_.m_60713_(Blocks.f_152588_);
      }, p_154070_);
   }

   private static boolean m_154221_(LevelReader p_154222_, BlockPos p_154223_, Direction p_154224_) {
      BlockPos blockpos = p_154223_.m_142300_(p_154224_.m_122424_());
      BlockState blockstate = p_154222_.m_8055_(blockpos);
      return blockstate.m_60783_(p_154222_, blockpos, p_154224_) || m_154207_(blockstate, p_154224_);
   }

   private static boolean m_154153_(BlockState p_154154_, boolean p_154155_) {
      if (!p_154154_.m_60713_(Blocks.f_152588_)) {
         return false;
      } else {
         DripstoneThickness dripstonethickness = p_154154_.m_61143_(f_154010_);
         return dripstonethickness == DripstoneThickness.TIP || p_154155_ && dripstonethickness == DripstoneThickness.TIP_MERGE;
      }
   }

   private static boolean m_154143_(BlockState p_154144_, Direction p_154145_) {
      return m_154153_(p_154144_, false) && p_154144_.m_61143_(f_154009_) == p_154145_;
   }

   private static boolean m_154240_(BlockState p_154241_) {
      return m_154207_(p_154241_, Direction.DOWN);
   }

   private static boolean m_154242_(BlockState p_154243_) {
      return m_154207_(p_154243_, Direction.UP);
   }

   private static boolean m_154203_(BlockState p_154204_, LevelReader p_154205_, BlockPos p_154206_) {
      return m_154240_(p_154204_) && !p_154205_.m_8055_(p_154206_.m_7494_()).m_60713_(Blocks.f_152588_);
   }

   public boolean m_7357_(BlockState p_154112_, BlockGetter p_154113_, BlockPos p_154114_, PathComputationType p_154115_) {
      return false;
   }

   private static boolean m_154207_(BlockState p_154208_, Direction p_154209_) {
      return p_154208_.m_60713_(Blocks.f_152588_) && p_154208_.m_61143_(f_154009_) == p_154209_;
   }

   @Nullable
   private static BlockPos m_154076_(Level p_154077_, BlockPos p_154078_, Fluid p_154079_) {
      Predicate<BlockState> predicate = (p_154162_) -> {
         return p_154162_.m_60734_() instanceof AbstractCauldronBlock && ((AbstractCauldronBlock)p_154162_.m_60734_()).m_142087_(p_154079_);
      };
      return m_154080_(p_154077_, p_154078_, Direction.DOWN.m_122421_(), BlockBehaviour.BlockStateBase::m_60795_, predicate, 11).orElse((BlockPos)null);
   }

   @Nullable
   public static BlockPos m_154055_(Level p_154056_, BlockPos p_154057_) {
      return m_154080_(p_154056_, p_154057_, Direction.UP.m_122421_(), BlockBehaviour.BlockStateBase::m_60795_, PointedDripstoneBlock::m_154238_, 11).orElse((BlockPos)null);
   }

   public static Fluid m_154178_(Level p_154179_, BlockPos p_154180_) {
      return m_154181_(p_154179_, p_154180_, p_154179_.m_8055_(p_154180_)).filter(PointedDripstoneBlock::m_154158_).orElse(Fluids.f_76191_);
   }

   private static Optional<Fluid> m_154181_(Level p_154182_, BlockPos p_154183_, BlockState p_154184_) {
      return !m_154240_(p_154184_) ? Optional.empty() : m_154066_(p_154182_, p_154183_, p_154184_, 11).map((p_154215_) -> {
         return p_154182_.m_6425_(p_154215_.m_7494_()).m_76152_();
      });
   }

   private static boolean m_154158_(Fluid p_154159_) {
      return p_154159_ == Fluids.f_76195_ || p_154159_ == Fluids.f_76193_;
   }

   private static boolean m_154140_(BlockState p_154141_, BlockState p_154142_) {
      return p_154141_.m_60713_(Blocks.f_152537_) && p_154142_.m_60713_(Blocks.f_49990_) && p_154142_.m_60819_().m_76170_();
   }

   private static Fluid m_154052_(Level p_154053_, Fluid p_154054_) {
      if (p_154054_.m_6212_(Fluids.f_76191_)) {
         return p_154053_.m_6042_().m_63951_() ? Fluids.f_76195_ : Fluids.f_76193_;
      } else {
         return p_154054_;
      }
   }

   private static Optional<BlockPos> m_154080_(LevelAccessor p_154081_, BlockPos p_154082_, Direction.AxisDirection p_154083_, Predicate<BlockState> p_154084_, Predicate<BlockState> p_154085_, int p_154086_) {
      Direction direction = Direction.m_122390_(p_154083_, Direction.Axis.Y);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_154082_.m_122032_();

      for(int i = 1; i < p_154086_; ++i) {
         blockpos$mutableblockpos.m_122173_(direction);
         BlockState blockstate = p_154081_.m_8055_(blockpos$mutableblockpos);
         if (p_154085_.test(blockstate)) {
            return Optional.of(blockpos$mutableblockpos.m_7949_());
         }

         if (p_154081_.m_151562_(blockpos$mutableblockpos.m_123342_()) || !p_154084_.test(blockstate)) {
            return Optional.empty();
         }
      }

      return Optional.empty();
   }
}