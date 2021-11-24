package net.minecraft.world.level;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface BlockGetter extends LevelHeightAccessor {
   @Nullable
   BlockEntity m_7702_(BlockPos p_45570_);

   default <T extends BlockEntity> Optional<T> m_141902_(BlockPos p_151367_, BlockEntityType<T> p_151368_) {
      BlockEntity blockentity = this.m_7702_(p_151367_);
      return blockentity != null && blockentity.m_58903_() == p_151368_ ? Optional.of((T)blockentity) : Optional.empty();
   }

   BlockState m_8055_(BlockPos p_45571_);

   FluidState m_6425_(BlockPos p_45569_);

   default int m_7146_(BlockPos p_45572_) {
      return this.m_8055_(p_45572_).getLightEmission(this, p_45572_);
   }

   default int m_7469_() {
      return 15;
   }

   default Stream<BlockState> m_45556_(AABB p_45557_) {
      return BlockPos.m_121921_(p_45557_).map(this::m_8055_);
   }

   default BlockHitResult m_151353_(ClipBlockStateContext p_151354_) {
      return m_151361_(p_151354_.m_151405_(), p_151354_.m_151404_(), p_151354_, (p_151356_, p_151357_) -> {
         BlockState blockstate = this.m_8055_(p_151357_);
         Vec3 vec3 = p_151356_.m_151405_().m_82546_(p_151356_.m_151404_());
         return p_151356_.m_151406_().test(blockstate) ? new BlockHitResult(p_151356_.m_151404_(), Direction.m_122366_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_), new BlockPos(p_151356_.m_151404_()), false) : null;
      }, (p_151370_) -> {
         Vec3 vec3 = p_151370_.m_151405_().m_82546_(p_151370_.m_151404_());
         return BlockHitResult.m_82426_(p_151370_.m_151404_(), Direction.m_122366_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_), new BlockPos(p_151370_.m_151404_()));
      });
   }

   default BlockHitResult m_45547_(ClipContext p_45548_) {
      return m_151361_(p_45548_.m_45702_(), p_45548_.m_45693_(), p_45548_, (p_151359_, p_151360_) -> {
         BlockState blockstate = this.m_8055_(p_151360_);
         FluidState fluidstate = this.m_6425_(p_151360_);
         Vec3 vec3 = p_151359_.m_45702_();
         Vec3 vec31 = p_151359_.m_45693_();
         VoxelShape voxelshape = p_151359_.m_45694_(blockstate, this, p_151360_);
         BlockHitResult blockhitresult = this.m_45558_(vec3, vec31, p_151360_, voxelshape, blockstate);
         VoxelShape voxelshape1 = p_151359_.m_45698_(fluidstate, this, p_151360_);
         BlockHitResult blockhitresult1 = voxelshape1.m_83220_(vec3, vec31, p_151360_);
         double d0 = blockhitresult == null ? Double.MAX_VALUE : p_151359_.m_45702_().m_82557_(blockhitresult.m_82450_());
         double d1 = blockhitresult1 == null ? Double.MAX_VALUE : p_151359_.m_45702_().m_82557_(blockhitresult1.m_82450_());
         return d0 <= d1 ? blockhitresult : blockhitresult1;
      }, (p_151372_) -> {
         Vec3 vec3 = p_151372_.m_45702_().m_82546_(p_151372_.m_45693_());
         return BlockHitResult.m_82426_(p_151372_.m_45693_(), Direction.m_122366_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_), new BlockPos(p_151372_.m_45693_()));
      });
   }

   @Nullable
   default BlockHitResult m_45558_(Vec3 p_45559_, Vec3 p_45560_, BlockPos p_45561_, VoxelShape p_45562_, BlockState p_45563_) {
      BlockHitResult blockhitresult = p_45562_.m_83220_(p_45559_, p_45560_, p_45561_);
      if (blockhitresult != null) {
         BlockHitResult blockhitresult1 = p_45563_.m_60820_(this, p_45561_).m_83220_(p_45559_, p_45560_, p_45561_);
         if (blockhitresult1 != null && blockhitresult1.m_82450_().m_82546_(p_45559_).m_82556_() < blockhitresult.m_82450_().m_82546_(p_45559_).m_82556_()) {
            return blockhitresult.m_82432_(blockhitresult1.m_82434_());
         }
      }

      return blockhitresult;
   }

   default double m_45564_(VoxelShape p_45565_, Supplier<VoxelShape> p_45566_) {
      if (!p_45565_.m_83281_()) {
         return p_45565_.m_83297_(Direction.Axis.Y);
      } else {
         double d0 = p_45566_.get().m_83297_(Direction.Axis.Y);
         return d0 >= 1.0D ? d0 - 1.0D : Double.NEGATIVE_INFINITY;
      }
   }

   default double m_45573_(BlockPos p_45574_) {
      return this.m_45564_(this.m_8055_(p_45574_).m_60812_(this, p_45574_), () -> {
         BlockPos blockpos = p_45574_.m_7495_();
         return this.m_8055_(blockpos).m_60812_(this, blockpos);
      });
   }

   static <T, C> T m_151361_(Vec3 p_151362_, Vec3 p_151363_, C p_151364_, BiFunction<C, BlockPos, T> p_151365_, Function<C, T> p_151366_) {
      if (p_151362_.equals(p_151363_)) {
         return p_151366_.apply(p_151364_);
      } else {
         double d0 = Mth.m_14139_(-1.0E-7D, p_151363_.f_82479_, p_151362_.f_82479_);
         double d1 = Mth.m_14139_(-1.0E-7D, p_151363_.f_82480_, p_151362_.f_82480_);
         double d2 = Mth.m_14139_(-1.0E-7D, p_151363_.f_82481_, p_151362_.f_82481_);
         double d3 = Mth.m_14139_(-1.0E-7D, p_151362_.f_82479_, p_151363_.f_82479_);
         double d4 = Mth.m_14139_(-1.0E-7D, p_151362_.f_82480_, p_151363_.f_82480_);
         double d5 = Mth.m_14139_(-1.0E-7D, p_151362_.f_82481_, p_151363_.f_82481_);
         int i = Mth.m_14107_(d3);
         int j = Mth.m_14107_(d4);
         int k = Mth.m_14107_(d5);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(i, j, k);
         T t = p_151365_.apply(p_151364_, blockpos$mutableblockpos);
         if (t != null) {
            return t;
         } else {
            double d6 = d0 - d3;
            double d7 = d1 - d4;
            double d8 = d2 - d5;
            int l = Mth.m_14205_(d6);
            int i1 = Mth.m_14205_(d7);
            int j1 = Mth.m_14205_(d8);
            double d9 = l == 0 ? Double.MAX_VALUE : (double)l / d6;
            double d10 = i1 == 0 ? Double.MAX_VALUE : (double)i1 / d7;
            double d11 = j1 == 0 ? Double.MAX_VALUE : (double)j1 / d8;
            double d12 = d9 * (l > 0 ? 1.0D - Mth.m_14185_(d3) : Mth.m_14185_(d3));
            double d13 = d10 * (i1 > 0 ? 1.0D - Mth.m_14185_(d4) : Mth.m_14185_(d4));
            double d14 = d11 * (j1 > 0 ? 1.0D - Mth.m_14185_(d5) : Mth.m_14185_(d5));

            while(d12 <= 1.0D || d13 <= 1.0D || d14 <= 1.0D) {
               if (d12 < d13) {
                  if (d12 < d14) {
                     i += l;
                     d12 += d9;
                  } else {
                     k += j1;
                     d14 += d11;
                  }
               } else if (d13 < d14) {
                  j += i1;
                  d13 += d10;
               } else {
                  k += j1;
                  d14 += d11;
               }

               T t1 = p_151365_.apply(p_151364_, blockpos$mutableblockpos.m_122178_(i, j, k));
               if (t1 != null) {
                  return t1;
               }
            }

            return p_151366_.apply(p_151364_);
         }
      }
   }
}
