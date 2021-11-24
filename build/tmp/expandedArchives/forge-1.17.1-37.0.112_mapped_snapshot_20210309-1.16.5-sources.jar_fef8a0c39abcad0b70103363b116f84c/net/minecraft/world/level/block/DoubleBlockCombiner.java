package net.minecraft.world.level.block;

import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class DoubleBlockCombiner {
   public static <S extends BlockEntity> DoubleBlockCombiner.NeighborCombineResult<S> m_52822_(BlockEntityType<S> p_52823_, Function<BlockState, DoubleBlockCombiner.BlockType> p_52824_, Function<BlockState, Direction> p_52825_, DirectionProperty p_52826_, BlockState p_52827_, LevelAccessor p_52828_, BlockPos p_52829_, BiPredicate<LevelAccessor, BlockPos> p_52830_) {
      S s = p_52823_.m_58949_(p_52828_, p_52829_);
      if (s == null) {
         return DoubleBlockCombiner.Combiner::m_6502_;
      } else if (p_52830_.test(p_52828_, p_52829_)) {
         return DoubleBlockCombiner.Combiner::m_6502_;
      } else {
         DoubleBlockCombiner.BlockType doubleblockcombiner$blocktype = p_52824_.apply(p_52827_);
         boolean flag = doubleblockcombiner$blocktype == DoubleBlockCombiner.BlockType.SINGLE;
         boolean flag1 = doubleblockcombiner$blocktype == DoubleBlockCombiner.BlockType.FIRST;
         if (flag) {
            return new DoubleBlockCombiner.NeighborCombineResult.Single<>(s);
         } else {
            BlockPos blockpos = p_52829_.m_142300_(p_52825_.apply(p_52827_));
            BlockState blockstate = p_52828_.m_8055_(blockpos);
            if (blockstate.m_60713_(p_52827_.m_60734_())) {
               DoubleBlockCombiner.BlockType doubleblockcombiner$blocktype1 = p_52824_.apply(blockstate);
               if (doubleblockcombiner$blocktype1 != DoubleBlockCombiner.BlockType.SINGLE && doubleblockcombiner$blocktype != doubleblockcombiner$blocktype1 && blockstate.m_61143_(p_52826_) == p_52827_.m_61143_(p_52826_)) {
                  if (p_52830_.test(p_52828_, blockpos)) {
                     return DoubleBlockCombiner.Combiner::m_6502_;
                  }

                  S s1 = p_52823_.m_58949_(p_52828_, blockpos);
                  if (s1 != null) {
                     S s2 = flag1 ? s : s1;
                     S s3 = flag1 ? s1 : s;
                     return new DoubleBlockCombiner.NeighborCombineResult.Double<>(s2, s3);
                  }
               }
            }

            return new DoubleBlockCombiner.NeighborCombineResult.Single<>(s);
         }
      }
   }

   public static enum BlockType {
      SINGLE,
      FIRST,
      SECOND;
   }

   public interface Combiner<S, T> {
      T m_6959_(S p_52843_, S p_52844_);

      T m_7693_(S p_52842_);

      T m_6502_();
   }

   public interface NeighborCombineResult<S> {
      <T> T m_5649_(DoubleBlockCombiner.Combiner<? super S, T> p_52845_);

      public static final class Double<S> implements DoubleBlockCombiner.NeighborCombineResult<S> {
         private final S f_52846_;
         private final S f_52847_;

         public Double(S p_52849_, S p_52850_) {
            this.f_52846_ = p_52849_;
            this.f_52847_ = p_52850_;
         }

         public <T> T m_5649_(DoubleBlockCombiner.Combiner<? super S, T> p_52852_) {
            return p_52852_.m_6959_(this.f_52846_, this.f_52847_);
         }
      }

      public static final class Single<S> implements DoubleBlockCombiner.NeighborCombineResult<S> {
         private final S f_52853_;

         public Single(S p_52855_) {
            this.f_52853_ = p_52855_;
         }

         public <T> T m_5649_(DoubleBlockCombiner.Combiner<? super S, T> p_52857_) {
            return p_52857_.m_7693_(this.f_52853_);
         }
      }
   }
}