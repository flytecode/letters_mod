package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;

public class RailState {
   private final Level f_55414_;
   private final BlockPos f_55415_;
   private final BaseRailBlock f_55416_;
   private BlockState f_55417_;
   private final boolean f_55418_;
   private final List<BlockPos> f_55419_ = Lists.newArrayList();
   private final boolean canMakeSlopes;

   public RailState(Level p_55421_, BlockPos p_55422_, BlockState p_55423_) {
      this.f_55414_ = p_55421_;
      this.f_55415_ = p_55422_;
      this.f_55417_ = p_55423_;
      this.f_55416_ = (BaseRailBlock)p_55423_.m_60734_();
      RailShape railshape = this.f_55416_.getRailDirection(f_55417_, p_55421_, p_55422_, null);
      this.f_55418_ = !this.f_55416_.isFlexibleRail(f_55417_, p_55421_, p_55422_);
      this.canMakeSlopes = this.f_55416_.canMakeSlopes(f_55417_, p_55421_, p_55422_);
      this.m_55427_(railshape);
   }

   public List<BlockPos> m_55424_() {
      return this.f_55419_;
   }

   private void m_55427_(RailShape p_55428_) {
      this.f_55419_.clear();
      switch(p_55428_) {
      case NORTH_SOUTH:
         this.f_55419_.add(this.f_55415_.m_142127_());
         this.f_55419_.add(this.f_55415_.m_142128_());
         break;
      case EAST_WEST:
         this.f_55419_.add(this.f_55415_.m_142125_());
         this.f_55419_.add(this.f_55415_.m_142126_());
         break;
      case ASCENDING_EAST:
         this.f_55419_.add(this.f_55415_.m_142125_());
         this.f_55419_.add(this.f_55415_.m_142126_().m_7494_());
         break;
      case ASCENDING_WEST:
         this.f_55419_.add(this.f_55415_.m_142125_().m_7494_());
         this.f_55419_.add(this.f_55415_.m_142126_());
         break;
      case ASCENDING_NORTH:
         this.f_55419_.add(this.f_55415_.m_142127_().m_7494_());
         this.f_55419_.add(this.f_55415_.m_142128_());
         break;
      case ASCENDING_SOUTH:
         this.f_55419_.add(this.f_55415_.m_142127_());
         this.f_55419_.add(this.f_55415_.m_142128_().m_7494_());
         break;
      case SOUTH_EAST:
         this.f_55419_.add(this.f_55415_.m_142126_());
         this.f_55419_.add(this.f_55415_.m_142128_());
         break;
      case SOUTH_WEST:
         this.f_55419_.add(this.f_55415_.m_142125_());
         this.f_55419_.add(this.f_55415_.m_142128_());
         break;
      case NORTH_WEST:
         this.f_55419_.add(this.f_55415_.m_142125_());
         this.f_55419_.add(this.f_55415_.m_142127_());
         break;
      case NORTH_EAST:
         this.f_55419_.add(this.f_55415_.m_142126_());
         this.f_55419_.add(this.f_55415_.m_142127_());
      }

   }

   private void m_55445_() {
      for(int i = 0; i < this.f_55419_.size(); ++i) {
         RailState railstate = this.m_55438_(this.f_55419_.get(i));
         if (railstate != null && railstate.m_55425_(this)) {
            this.f_55419_.set(i, railstate.f_55415_);
         } else {
            this.f_55419_.remove(i--);
         }
      }

   }

   private boolean m_55429_(BlockPos p_55430_) {
      return BaseRailBlock.m_49364_(this.f_55414_, p_55430_) || BaseRailBlock.m_49364_(this.f_55414_, p_55430_.m_7494_()) || BaseRailBlock.m_49364_(this.f_55414_, p_55430_.m_7495_());
   }

   @Nullable
   private RailState m_55438_(BlockPos p_55439_) {
      BlockState blockstate = this.f_55414_.m_8055_(p_55439_);
      if (BaseRailBlock.m_49416_(blockstate)) {
         return new RailState(this.f_55414_, p_55439_, blockstate);
      } else {
         BlockPos blockpos = p_55439_.m_7494_();
         blockstate = this.f_55414_.m_8055_(blockpos);
         if (BaseRailBlock.m_49416_(blockstate)) {
            return new RailState(this.f_55414_, blockpos, blockstate);
         } else {
            blockpos = p_55439_.m_7495_();
            blockstate = this.f_55414_.m_8055_(blockpos);
            return BaseRailBlock.m_49416_(blockstate) ? new RailState(this.f_55414_, blockpos, blockstate) : null;
         }
      }
   }

   private boolean m_55425_(RailState p_55426_) {
      return this.m_55443_(p_55426_.f_55415_);
   }

   private boolean m_55443_(BlockPos p_55444_) {
      for(int i = 0; i < this.f_55419_.size(); ++i) {
         BlockPos blockpos = this.f_55419_.get(i);
         if (blockpos.m_123341_() == p_55444_.m_123341_() && blockpos.m_123343_() == p_55444_.m_123343_()) {
            return true;
         }
      }

      return false;
   }

   protected int m_55435_() {
      int i = 0;

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (this.m_55429_(this.f_55415_.m_142300_(direction))) {
            ++i;
         }
      }

      return i;
   }

   private boolean m_55436_(RailState p_55437_) {
      return this.m_55425_(p_55437_) || this.f_55419_.size() != 2;
   }

   private void m_55441_(RailState p_55442_) {
      this.f_55419_.add(p_55442_.f_55415_);
      BlockPos blockpos = this.f_55415_.m_142127_();
      BlockPos blockpos1 = this.f_55415_.m_142128_();
      BlockPos blockpos2 = this.f_55415_.m_142125_();
      BlockPos blockpos3 = this.f_55415_.m_142126_();
      boolean flag = this.m_55443_(blockpos);
      boolean flag1 = this.m_55443_(blockpos1);
      boolean flag2 = this.m_55443_(blockpos2);
      boolean flag3 = this.m_55443_(blockpos3);
      RailShape railshape = null;
      if (flag || flag1) {
         railshape = RailShape.NORTH_SOUTH;
      }

      if (flag2 || flag3) {
         railshape = RailShape.EAST_WEST;
      }

      if (!this.f_55418_) {
         if (flag1 && flag3 && !flag && !flag2) {
            railshape = RailShape.SOUTH_EAST;
         }

         if (flag1 && flag2 && !flag && !flag3) {
            railshape = RailShape.SOUTH_WEST;
         }

         if (flag && flag2 && !flag1 && !flag3) {
            railshape = RailShape.NORTH_WEST;
         }

         if (flag && flag3 && !flag1 && !flag2) {
            railshape = RailShape.NORTH_EAST;
         }
      }

      if (railshape == RailShape.NORTH_SOUTH && canMakeSlopes) {
         if (BaseRailBlock.m_49364_(this.f_55414_, blockpos.m_7494_())) {
            railshape = RailShape.ASCENDING_NORTH;
         }

         if (BaseRailBlock.m_49364_(this.f_55414_, blockpos1.m_7494_())) {
            railshape = RailShape.ASCENDING_SOUTH;
         }
      }

      if (railshape == RailShape.EAST_WEST && canMakeSlopes) {
         if (BaseRailBlock.m_49364_(this.f_55414_, blockpos3.m_7494_())) {
            railshape = RailShape.ASCENDING_EAST;
         }

         if (BaseRailBlock.m_49364_(this.f_55414_, blockpos2.m_7494_())) {
            railshape = RailShape.ASCENDING_WEST;
         }
      }

      if (railshape == null) {
         railshape = RailShape.NORTH_SOUTH;
      }

      this.f_55417_ = this.f_55417_.m_61124_(this.f_55416_.m_7978_(), railshape);
      this.f_55414_.m_7731_(this.f_55415_, this.f_55417_, 3);
   }

   private boolean m_55446_(BlockPos p_55447_) {
      RailState railstate = this.m_55438_(p_55447_);
      if (railstate == null) {
         return false;
      } else {
         railstate.m_55445_();
         return railstate.m_55436_(this);
      }
   }

   public RailState m_55431_(boolean p_55432_, boolean p_55433_, RailShape p_55434_) {
      BlockPos blockpos = this.f_55415_.m_142127_();
      BlockPos blockpos1 = this.f_55415_.m_142128_();
      BlockPos blockpos2 = this.f_55415_.m_142125_();
      BlockPos blockpos3 = this.f_55415_.m_142126_();
      boolean flag = this.m_55446_(blockpos);
      boolean flag1 = this.m_55446_(blockpos1);
      boolean flag2 = this.m_55446_(blockpos2);
      boolean flag3 = this.m_55446_(blockpos3);
      RailShape railshape = null;
      boolean flag4 = flag || flag1;
      boolean flag5 = flag2 || flag3;
      if (flag4 && !flag5) {
         railshape = RailShape.NORTH_SOUTH;
      }

      if (flag5 && !flag4) {
         railshape = RailShape.EAST_WEST;
      }

      boolean flag6 = flag1 && flag3;
      boolean flag7 = flag1 && flag2;
      boolean flag8 = flag && flag3;
      boolean flag9 = flag && flag2;
      if (!this.f_55418_) {
         if (flag6 && !flag && !flag2) {
            railshape = RailShape.SOUTH_EAST;
         }

         if (flag7 && !flag && !flag3) {
            railshape = RailShape.SOUTH_WEST;
         }

         if (flag9 && !flag1 && !flag3) {
            railshape = RailShape.NORTH_WEST;
         }

         if (flag8 && !flag1 && !flag2) {
            railshape = RailShape.NORTH_EAST;
         }
      }

      if (railshape == null) {
         if (flag4 && flag5) {
            railshape = p_55434_;
         } else if (flag4) {
            railshape = RailShape.NORTH_SOUTH;
         } else if (flag5) {
            railshape = RailShape.EAST_WEST;
         }

         if (!this.f_55418_) {
            if (p_55432_) {
               if (flag6) {
                  railshape = RailShape.SOUTH_EAST;
               }

               if (flag7) {
                  railshape = RailShape.SOUTH_WEST;
               }

               if (flag8) {
                  railshape = RailShape.NORTH_EAST;
               }

               if (flag9) {
                  railshape = RailShape.NORTH_WEST;
               }
            } else {
               if (flag9) {
                  railshape = RailShape.NORTH_WEST;
               }

               if (flag8) {
                  railshape = RailShape.NORTH_EAST;
               }

               if (flag7) {
                  railshape = RailShape.SOUTH_WEST;
               }

               if (flag6) {
                  railshape = RailShape.SOUTH_EAST;
               }
            }
         }
      }

      if (railshape == RailShape.NORTH_SOUTH && canMakeSlopes) {
         if (BaseRailBlock.m_49364_(this.f_55414_, blockpos.m_7494_())) {
            railshape = RailShape.ASCENDING_NORTH;
         }

         if (BaseRailBlock.m_49364_(this.f_55414_, blockpos1.m_7494_())) {
            railshape = RailShape.ASCENDING_SOUTH;
         }
      }

      if (railshape == RailShape.EAST_WEST && canMakeSlopes) {
         if (BaseRailBlock.m_49364_(this.f_55414_, blockpos3.m_7494_())) {
            railshape = RailShape.ASCENDING_EAST;
         }

         if (BaseRailBlock.m_49364_(this.f_55414_, blockpos2.m_7494_())) {
            railshape = RailShape.ASCENDING_WEST;
         }
      }

      if (railshape == null) {
         railshape = p_55434_;
      }

      this.m_55427_(railshape);
      this.f_55417_ = this.f_55417_.m_61124_(this.f_55416_.m_7978_(), railshape);
      if (p_55433_ || this.f_55414_.m_8055_(this.f_55415_) != this.f_55417_) {
         this.f_55414_.m_7731_(this.f_55415_, this.f_55417_, 3);

         for(int i = 0; i < this.f_55419_.size(); ++i) {
            RailState railstate = this.m_55438_(this.f_55419_.get(i));
            if (railstate != null) {
               railstate.m_55445_();
               if (railstate.m_55436_(this)) {
                  railstate.m_55441_(this);
               }
            }
         }
      }

      return this;
   }

   public BlockState m_55440_() {
      return this.f_55417_;
   }
}
