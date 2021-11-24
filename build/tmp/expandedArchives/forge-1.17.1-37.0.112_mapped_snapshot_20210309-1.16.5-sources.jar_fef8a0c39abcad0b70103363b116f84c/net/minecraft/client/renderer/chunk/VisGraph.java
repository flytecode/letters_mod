package net.minecraft.client.renderer.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VisGraph {
   private static final int f_173723_ = 4;
   private static final int f_173724_ = 16;
   private static final int f_173725_ = 15;
   private static final int f_173726_ = 4096;
   private static final int f_173727_ = 0;
   private static final int f_173728_ = 4;
   private static final int f_173729_ = 8;
   private static final int f_112949_ = (int)Math.pow(16.0D, 0.0D);
   private static final int f_112950_ = (int)Math.pow(16.0D, 1.0D);
   private static final int f_112951_ = (int)Math.pow(16.0D, 2.0D);
   private static final int f_173730_ = -1;
   private static final Direction[] f_112952_ = Direction.values();
   private final BitSet f_112953_ = new BitSet(4096);
   private static final int[] f_112954_ = Util.m_137469_(new int[1352], (p_112974_) -> {
      int i = 0;
      int j = 15;
      int k = 0;

      for(int l = 0; l < 16; ++l) {
         for(int i1 = 0; i1 < 16; ++i1) {
            for(int j1 = 0; j1 < 16; ++j1) {
               if (l == 0 || l == 15 || i1 == 0 || i1 == 15 || j1 == 0 || j1 == 15) {
                  p_112974_[k++] = m_112961_(l, i1, j1);
               }
            }
         }
      }

   });
   private int f_112955_ = 4096;

   public void m_112971_(BlockPos p_112972_) {
      this.f_112953_.set(m_112975_(p_112972_), true);
      --this.f_112955_;
   }

   private static int m_112975_(BlockPos p_112976_) {
      return m_112961_(p_112976_.m_123341_() & 15, p_112976_.m_123342_() & 15, p_112976_.m_123343_() & 15);
   }

   private static int m_112961_(int p_112962_, int p_112963_, int p_112964_) {
      return p_112962_ << 0 | p_112963_ << 8 | p_112964_ << 4;
   }

   public VisibilitySet m_112958_() {
      VisibilitySet visibilityset = new VisibilitySet();
      if (4096 - this.f_112955_ < 256) {
         visibilityset.m_112992_(true);
      } else if (this.f_112955_ == 0) {
         visibilityset.m_112992_(false);
      } else {
         for(int i : f_112954_) {
            if (!this.f_112953_.get(i)) {
               visibilityset.m_112990_(this.m_112959_(i));
            }
         }
      }

      return visibilityset;
   }

   private Set<Direction> m_112959_(int p_112960_) {
      Set<Direction> set = EnumSet.noneOf(Direction.class);
      IntPriorityQueue intpriorityqueue = new IntArrayFIFOQueue();
      intpriorityqueue.enqueue(p_112960_);
      this.f_112953_.set(p_112960_, true);

      while(!intpriorityqueue.isEmpty()) {
         int i = intpriorityqueue.dequeueInt();
         this.m_112968_(i, set);

         for(Direction direction : f_112952_) {
            int j = this.m_112965_(i, direction);
            if (j >= 0 && !this.f_112953_.get(j)) {
               this.f_112953_.set(j, true);
               intpriorityqueue.enqueue(j);
            }
         }
      }

      return set;
   }

   private void m_112968_(int p_112969_, Set<Direction> p_112970_) {
      int i = p_112969_ >> 0 & 15;
      if (i == 0) {
         p_112970_.add(Direction.WEST);
      } else if (i == 15) {
         p_112970_.add(Direction.EAST);
      }

      int j = p_112969_ >> 8 & 15;
      if (j == 0) {
         p_112970_.add(Direction.DOWN);
      } else if (j == 15) {
         p_112970_.add(Direction.UP);
      }

      int k = p_112969_ >> 4 & 15;
      if (k == 0) {
         p_112970_.add(Direction.NORTH);
      } else if (k == 15) {
         p_112970_.add(Direction.SOUTH);
      }

   }

   private int m_112965_(int p_112966_, Direction p_112967_) {
      switch(p_112967_) {
      case DOWN:
         if ((p_112966_ >> 8 & 15) == 0) {
            return -1;
         }

         return p_112966_ - f_112951_;
      case UP:
         if ((p_112966_ >> 8 & 15) == 15) {
            return -1;
         }

         return p_112966_ + f_112951_;
      case NORTH:
         if ((p_112966_ >> 4 & 15) == 0) {
            return -1;
         }

         return p_112966_ - f_112950_;
      case SOUTH:
         if ((p_112966_ >> 4 & 15) == 15) {
            return -1;
         }

         return p_112966_ + f_112950_;
      case WEST:
         if ((p_112966_ >> 0 & 15) == 0) {
            return -1;
         }

         return p_112966_ - f_112949_;
      case EAST:
         if ((p_112966_ >> 0 & 15) == 15) {
            return -1;
         }

         return p_112966_ + f_112949_;
      default:
         return -1;
      }
   }
}