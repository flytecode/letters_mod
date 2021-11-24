package net.minecraft.world.level.block.piston;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class PistonStructureResolver {
   public static final int f_155936_ = 12;
   private final Level f_60409_;
   private final BlockPos f_60410_;
   private final boolean f_60411_;
   private final BlockPos f_60412_;
   private final Direction f_60413_;
   private final List<BlockPos> f_60414_ = Lists.newArrayList();
   private final List<BlockPos> f_60415_ = Lists.newArrayList();
   private final Direction f_60416_;

   public PistonStructureResolver(Level p_60418_, BlockPos p_60419_, Direction p_60420_, boolean p_60421_) {
      this.f_60409_ = p_60418_;
      this.f_60410_ = p_60419_;
      this.f_60416_ = p_60420_;
      this.f_60411_ = p_60421_;
      if (p_60421_) {
         this.f_60413_ = p_60420_;
         this.f_60412_ = p_60419_.m_142300_(p_60420_);
      } else {
         this.f_60413_ = p_60420_.m_122424_();
         this.f_60412_ = p_60419_.m_5484_(p_60420_, 2);
      }

   }

   public boolean m_60422_() {
      this.f_60414_.clear();
      this.f_60415_.clear();
      BlockState blockstate = this.f_60409_.m_8055_(this.f_60412_);
      if (!PistonBaseBlock.m_60204_(blockstate, this.f_60409_, this.f_60412_, this.f_60413_, false, this.f_60416_)) {
         if (this.f_60411_ && blockstate.m_60811_() == PushReaction.DESTROY) {
            this.f_60415_.add(this.f_60412_);
            return true;
         } else {
            return false;
         }
      } else if (!this.m_60433_(this.f_60412_, this.f_60413_)) {
         return false;
      } else {
         for(int i = 0; i < this.f_60414_.size(); ++i) {
            BlockPos blockpos = this.f_60414_.get(i);
            if (this.f_60409_.m_8055_(blockpos).isStickyBlock() && !this.m_60431_(blockpos)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean m_60433_(BlockPos p_60434_, Direction p_60435_) {
      BlockState blockstate = this.f_60409_.m_8055_(p_60434_);
      if (f_60409_.m_46859_(p_60434_)) {
         return true;
      } else if (!PistonBaseBlock.m_60204_(blockstate, this.f_60409_, p_60434_, this.f_60413_, false, p_60435_)) {
         return true;
      } else if (p_60434_.equals(this.f_60410_)) {
         return true;
      } else if (this.f_60414_.contains(p_60434_)) {
         return true;
      } else {
         int i = 1;
         if (i + this.f_60414_.size() > 12) {
            return false;
         } else {
            BlockState oldState;
            while(blockstate.isStickyBlock()) {
               BlockPos blockpos = p_60434_.m_5484_(this.f_60413_.m_122424_(), i);
               oldState = blockstate;
               blockstate = this.f_60409_.m_8055_(blockpos);
               if (blockstate.m_60795_() || !oldState.canStickTo(blockstate) || !PistonBaseBlock.m_60204_(blockstate, this.f_60409_, blockpos, this.f_60413_, false, this.f_60413_.m_122424_()) || blockpos.equals(this.f_60410_)) {
                  break;
               }

               ++i;
               if (i + this.f_60414_.size() > 12) {
                  return false;
               }
            }

            int l = 0;

            for(int i1 = i - 1; i1 >= 0; --i1) {
               this.f_60414_.add(p_60434_.m_5484_(this.f_60413_.m_122424_(), i1));
               ++l;
            }

            int j1 = 1;

            while(true) {
               BlockPos blockpos1 = p_60434_.m_5484_(this.f_60413_, j1);
               int j = this.f_60414_.indexOf(blockpos1);
               if (j > -1) {
                  this.m_60423_(l, j);

                  for(int k = 0; k <= j + l; ++k) {
                     BlockPos blockpos2 = this.f_60414_.get(k);
                     if (this.f_60409_.m_8055_(blockpos2).isStickyBlock() && !this.m_60431_(blockpos2)) {
                        return false;
                     }
                  }

                  return true;
               }

               blockstate = this.f_60409_.m_8055_(blockpos1);
               if (blockstate.m_60795_()) {
                  return true;
               }

               if (!PistonBaseBlock.m_60204_(blockstate, this.f_60409_, blockpos1, this.f_60413_, true, this.f_60413_) || blockpos1.equals(this.f_60410_)) {
                  return false;
               }

               if (blockstate.m_60811_() == PushReaction.DESTROY) {
                  this.f_60415_.add(blockpos1);
                  return true;
               }

               if (this.f_60414_.size() >= 12) {
                  return false;
               }

               this.f_60414_.add(blockpos1);
               ++l;
               ++j1;
            }
         }
      }
   }

   private void m_60423_(int p_60424_, int p_60425_) {
      List<BlockPos> list = Lists.newArrayList();
      List<BlockPos> list1 = Lists.newArrayList();
      List<BlockPos> list2 = Lists.newArrayList();
      list.addAll(this.f_60414_.subList(0, p_60425_));
      list1.addAll(this.f_60414_.subList(this.f_60414_.size() - p_60424_, this.f_60414_.size()));
      list2.addAll(this.f_60414_.subList(p_60425_, this.f_60414_.size() - p_60424_));
      this.f_60414_.clear();
      this.f_60414_.addAll(list);
      this.f_60414_.addAll(list1);
      this.f_60414_.addAll(list2);
   }

   private boolean m_60431_(BlockPos p_60432_) {
      BlockState blockstate = this.f_60409_.m_8055_(p_60432_);

      for(Direction direction : Direction.values()) {
         if (direction.m_122434_() != this.f_60413_.m_122434_()) {
            BlockPos blockpos = p_60432_.m_142300_(direction);
            BlockState blockstate1 = this.f_60409_.m_8055_(blockpos);
            if (blockstate1.canStickTo(blockstate) && !this.m_60433_(blockpos, direction)) {
               return false;
            }
         }
      }

      return true;
   }

   public Direction m_155942_() {
      return this.f_60413_;
   }

   public List<BlockPos> m_60436_() {
      return this.f_60414_;
   }

   public List<BlockPos> m_60437_() {
      return this.f_60415_;
   }
}
