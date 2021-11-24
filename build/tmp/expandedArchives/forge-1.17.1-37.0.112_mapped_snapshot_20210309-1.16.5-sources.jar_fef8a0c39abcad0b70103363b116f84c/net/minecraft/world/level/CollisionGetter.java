package net.minecraft.world.level;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CollisionGetter extends BlockGetter {
   WorldBorder m_6857_();

   @Nullable
   BlockGetter m_7925_(int p_45774_, int p_45775_);

   default boolean m_5450_(@Nullable Entity p_45750_, VoxelShape p_45751_) {
      return true;
   }

   default boolean m_45752_(BlockState p_45753_, BlockPos p_45754_, CollisionContext p_45755_) {
      VoxelShape voxelshape = p_45753_.m_60742_(this, p_45754_, p_45755_);
      return voxelshape.m_83281_() || this.m_5450_((Entity)null, voxelshape.m_83216_((double)p_45754_.m_123341_(), (double)p_45754_.m_123342_(), (double)p_45754_.m_123343_()));
   }

   default boolean m_45784_(Entity p_45785_) {
      return this.m_5450_(p_45785_, Shapes.m_83064_(p_45785_.m_142469_()));
   }

   default boolean m_45772_(AABB p_45773_) {
      return this.m_45768_((Entity)null, p_45773_, (p_45780_) -> {
         return true;
      });
   }

   default boolean m_45786_(Entity p_45787_) {
      return this.m_45768_(p_45787_, p_45787_.m_142469_(), (p_45760_) -> {
         return true;
      });
   }

   default boolean m_45756_(Entity p_45757_, AABB p_45758_) {
      return this.m_45768_(p_45757_, p_45758_, (p_45745_) -> {
         return true;
      });
   }

   default boolean m_45768_(@Nullable Entity p_45769_, AABB p_45770_, Predicate<Entity> p_45771_) {
      return this.m_7786_(p_45769_, p_45770_, p_45771_).allMatch(VoxelShape::m_83281_);
   }

   Stream<VoxelShape> m_5454_(@Nullable Entity p_45776_, AABB p_45777_, Predicate<Entity> p_45778_);

   default Stream<VoxelShape> m_7786_(@Nullable Entity p_45781_, AABB p_45782_, Predicate<Entity> p_45783_) {
      return Stream.concat(this.m_45761_(p_45781_, p_45782_), this.m_5454_(p_45781_, p_45782_, p_45783_));
   }

   default Stream<VoxelShape> m_45761_(@Nullable Entity p_45762_, AABB p_45763_) {
      return StreamSupport.stream(new CollisionSpliterator(this, p_45762_, p_45763_), false);
   }

   default boolean m_151414_(@Nullable Entity p_151415_, AABB p_151416_, BiPredicate<BlockState, BlockPos> p_151417_) {
      return !this.m_45764_(p_151415_, p_151416_, p_151417_).allMatch(VoxelShape::m_83281_);
   }

   default Stream<VoxelShape> m_45764_(@Nullable Entity p_45765_, AABB p_45766_, BiPredicate<BlockState, BlockPos> p_45767_) {
      return StreamSupport.stream(new CollisionSpliterator(this, p_45765_, p_45766_, p_45767_), false);
   }

   default Optional<Vec3> m_151418_(@Nullable Entity p_151419_, VoxelShape p_151420_, Vec3 p_151421_, double p_151422_, double p_151423_, double p_151424_) {
      if (p_151420_.m_83281_()) {
         return Optional.empty();
      } else {
         AABB aabb = p_151420_.m_83215_().m_82377_(p_151422_, p_151423_, p_151424_);
         VoxelShape voxelshape = this.m_45761_(p_151419_, aabb).flatMap((p_151426_) -> {
            return p_151426_.m_83299_().stream();
         }).map((p_151413_) -> {
            return p_151413_.m_82377_(p_151422_ / 2.0D, p_151423_ / 2.0D, p_151424_ / 2.0D);
         }).map(Shapes::m_83064_).reduce(Shapes.m_83040_(), Shapes::m_83110_);
         VoxelShape voxelshape1 = Shapes.m_83113_(p_151420_, voxelshape, BooleanOp.f_82685_);
         return voxelshape1.m_166067_(p_151421_);
      }
   }
}