package net.minecraft.world.entity.vehicle;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DismountHelper {
   public static int[][] m_38467_(Direction p_38468_) {
      Direction direction = p_38468_.m_122427_();
      Direction direction1 = direction.m_122424_();
      Direction direction2 = p_38468_.m_122424_();
      return new int[][]{{direction.m_122429_(), direction.m_122431_()}, {direction1.m_122429_(), direction1.m_122431_()}, {direction2.m_122429_() + direction.m_122429_(), direction2.m_122431_() + direction.m_122431_()}, {direction2.m_122429_() + direction1.m_122429_(), direction2.m_122431_() + direction1.m_122431_()}, {p_38468_.m_122429_() + direction.m_122429_(), p_38468_.m_122431_() + direction.m_122431_()}, {p_38468_.m_122429_() + direction1.m_122429_(), p_38468_.m_122431_() + direction1.m_122431_()}, {direction2.m_122429_(), direction2.m_122431_()}, {p_38468_.m_122429_(), p_38468_.m_122431_()}};
   }

   public static boolean m_38439_(double p_38440_) {
      return !Double.isInfinite(p_38440_) && p_38440_ < 1.0D;
   }

   public static boolean m_38456_(CollisionGetter p_38457_, LivingEntity p_38458_, AABB p_38459_) {
      return p_38457_.m_45761_(p_38458_, p_38459_).allMatch(VoxelShape::m_83281_);
   }

   public static boolean m_150279_(CollisionGetter p_150280_, Vec3 p_150281_, LivingEntity p_150282_, Pose p_150283_) {
      return m_38456_(p_150280_, p_150282_, p_150282_.m_21270_(p_150283_).m_82383_(p_150281_));
   }

   public static VoxelShape m_38446_(BlockGetter p_38447_, BlockPos p_38448_) {
      BlockState blockstate = p_38447_.m_8055_(p_38448_);
      return !blockstate.m_60620_(BlockTags.f_13082_) && (!(blockstate.m_60734_() instanceof TrapDoorBlock) || !blockstate.m_61143_(TrapDoorBlock.f_57514_)) ? blockstate.m_60812_(p_38447_, p_38448_) : Shapes.m_83040_();
   }

   public static double m_38463_(BlockPos p_38464_, int p_38465_, Function<BlockPos, VoxelShape> p_38466_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_38464_.m_122032_();
      int i = 0;

      while(i < p_38465_) {
         VoxelShape voxelshape = p_38466_.apply(blockpos$mutableblockpos);
         if (!voxelshape.m_83281_()) {
            return (double)(p_38464_.m_123342_() + i) + voxelshape.m_83288_(Direction.Axis.Y);
         }

         ++i;
         blockpos$mutableblockpos.m_122173_(Direction.UP);
      }

      return Double.POSITIVE_INFINITY;
   }

   @Nullable
   public static Vec3 m_38441_(EntityType<?> p_38442_, CollisionGetter p_38443_, BlockPos p_38444_, boolean p_38445_) {
      if (p_38445_ && p_38442_.m_20630_(p_38443_.m_8055_(p_38444_))) {
         return null;
      } else {
         double d0 = p_38443_.m_45564_(m_38446_(p_38443_, p_38444_), () -> {
            return m_38446_(p_38443_, p_38444_.m_7495_());
         });
         if (!m_38439_(d0)) {
            return null;
         } else if (p_38445_ && d0 <= 0.0D && p_38442_.m_20630_(p_38443_.m_8055_(p_38444_.m_7495_()))) {
            return null;
         } else {
            Vec3 vec3 = Vec3.m_82514_(p_38444_, d0);
            return p_38443_.m_45761_((Entity)null, p_38442_.m_20680_().m_20393_(vec3)).allMatch(VoxelShape::m_83281_) ? vec3 : null;
         }
      }
   }
}