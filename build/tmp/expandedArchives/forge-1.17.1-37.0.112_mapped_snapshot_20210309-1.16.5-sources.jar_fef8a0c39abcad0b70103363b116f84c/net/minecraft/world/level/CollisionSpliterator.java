package net.minecraft.world.level;

import java.util.Objects;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CollisionSpliterator extends AbstractSpliterator<VoxelShape> {
   @Nullable
   private final Entity f_45788_;
   private final AABB f_45789_;
   private final CollisionContext f_45790_;
   private final Cursor3D f_45791_;
   private final BlockPos.MutableBlockPos f_45792_;
   private final VoxelShape f_45793_;
   private final CollisionGetter f_45794_;
   private boolean f_45795_;
   private final BiPredicate<BlockState, BlockPos> f_45796_;

   public CollisionSpliterator(CollisionGetter p_45798_, @Nullable Entity p_45799_, AABB p_45800_) {
      this(p_45798_, p_45799_, p_45800_, (p_45810_, p_45811_) -> {
         return true;
      });
   }

   public CollisionSpliterator(CollisionGetter p_45802_, @Nullable Entity p_45803_, AABB p_45804_, BiPredicate<BlockState, BlockPos> p_45805_) {
      super(Long.MAX_VALUE, 1280);
      this.f_45790_ = p_45803_ == null ? CollisionContext.m_82749_() : CollisionContext.m_82750_(p_45803_);
      this.f_45792_ = new BlockPos.MutableBlockPos();
      this.f_45793_ = Shapes.m_83064_(p_45804_);
      this.f_45794_ = p_45802_;
      this.f_45795_ = p_45803_ != null;
      this.f_45788_ = p_45803_;
      this.f_45789_ = p_45804_;
      this.f_45796_ = p_45805_;
      int i = Mth.m_14107_(p_45804_.f_82288_ - 1.0E-7D) - 1;
      int j = Mth.m_14107_(p_45804_.f_82291_ + 1.0E-7D) + 1;
      int k = Mth.m_14107_(p_45804_.f_82289_ - 1.0E-7D) - 1;
      int l = Mth.m_14107_(p_45804_.f_82292_ + 1.0E-7D) + 1;
      int i1 = Mth.m_14107_(p_45804_.f_82290_ - 1.0E-7D) - 1;
      int j1 = Mth.m_14107_(p_45804_.f_82293_ + 1.0E-7D) + 1;
      this.f_45791_ = new Cursor3D(i, k, i1, j, l, j1);
   }

   public boolean tryAdvance(Consumer<? super VoxelShape> p_45826_) {
      return this.f_45795_ && this.m_45823_(p_45826_) || this.m_45818_(p_45826_);
   }

   boolean m_45818_(Consumer<? super VoxelShape> p_45819_) {
      while(true) {
         if (this.f_45791_.m_122304_()) {
            int i = this.f_45791_.m_122305_();
            int j = this.f_45791_.m_122306_();
            int k = this.f_45791_.m_122307_();
            int l = this.f_45791_.m_122308_();
            if (l == 3) {
               continue;
            }

            BlockGetter blockgetter = this.m_45806_(i, k);
            if (blockgetter == null) {
               continue;
            }

            this.f_45792_.m_122178_(i, j, k);
            BlockState blockstate = blockgetter.m_8055_(this.f_45792_);
            if (!this.f_45796_.test(blockstate, this.f_45792_) || l == 1 && !blockstate.m_60779_() || l == 2 && !blockstate.m_60713_(Blocks.f_50110_)) {
               continue;
            }

            VoxelShape voxelshape = blockstate.m_60742_(this.f_45794_, this.f_45792_, this.f_45790_);
            if (voxelshape == Shapes.m_83144_()) {
               if (!this.f_45789_.m_82314_((double)i, (double)j, (double)k, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D)) {
                  continue;
               }

               p_45819_.accept(voxelshape.m_83216_((double)i, (double)j, (double)k));
               return true;
            }

            VoxelShape voxelshape1 = voxelshape.m_83216_((double)i, (double)j, (double)k);
            if (!Shapes.m_83157_(voxelshape1, this.f_45793_, BooleanOp.f_82689_)) {
               continue;
            }

            p_45819_.accept(voxelshape1);
            return true;
         }

         return false;
      }
   }

   @Nullable
   private BlockGetter m_45806_(int p_45807_, int p_45808_) {
      int i = SectionPos.m_123171_(p_45807_);
      int j = SectionPos.m_123171_(p_45808_);
      return this.f_45794_.m_7925_(i, j);
   }

   boolean m_45823_(Consumer<? super VoxelShape> p_45824_) {
      Objects.requireNonNull(this.f_45788_);
      this.f_45795_ = false;
      WorldBorder worldborder = this.f_45794_.m_6857_();
      AABB aabb = this.f_45788_.m_142469_();
      if (!m_45812_(worldborder, aabb)) {
         VoxelShape voxelshape = worldborder.m_61946_();
         if (!m_45820_(voxelshape, aabb) && m_45815_(voxelshape, aabb)) {
            p_45824_.accept(voxelshape);
            return true;
         }
      }

      return false;
   }

   private static boolean m_45815_(VoxelShape p_45816_, AABB p_45817_) {
      return Shapes.m_83157_(p_45816_, Shapes.m_83064_(p_45817_.m_82400_(1.0E-7D)), BooleanOp.f_82689_);
   }

   private static boolean m_45820_(VoxelShape p_45821_, AABB p_45822_) {
      return Shapes.m_83157_(p_45821_, Shapes.m_83064_(p_45822_.m_82406_(1.0E-7D)), BooleanOp.f_82689_);
   }

   public static boolean m_45812_(WorldBorder p_45813_, AABB p_45814_) {
      double d0 = (double)Mth.m_14107_(p_45813_.m_61955_());
      double d1 = (double)Mth.m_14107_(p_45813_.m_61956_());
      double d2 = (double)Mth.m_14165_(p_45813_.m_61957_());
      double d3 = (double)Mth.m_14165_(p_45813_.m_61958_());
      return p_45814_.f_82288_ > d0 && p_45814_.f_82288_ < d2 && p_45814_.f_82290_ > d1 && p_45814_.f_82290_ < d3 && p_45814_.f_82291_ > d0 && p_45814_.f_82291_ < d2 && p_45814_.f_82293_ > d1 && p_45814_.f_82293_ < d3;
   }
}