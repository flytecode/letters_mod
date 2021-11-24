package net.minecraft.world.level.block.piston;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public class PistonMath {
   public static AABB m_60328_(AABB p_60329_, Direction p_60330_, double p_60331_) {
      double d0 = p_60331_ * (double)p_60330_.m_122421_().m_122540_();
      double d1 = Math.min(d0, 0.0D);
      double d2 = Math.max(d0, 0.0D);
      switch(p_60330_) {
      case WEST:
         return new AABB(p_60329_.f_82288_ + d1, p_60329_.f_82289_, p_60329_.f_82290_, p_60329_.f_82288_ + d2, p_60329_.f_82292_, p_60329_.f_82293_);
      case EAST:
         return new AABB(p_60329_.f_82291_ + d1, p_60329_.f_82289_, p_60329_.f_82290_, p_60329_.f_82291_ + d2, p_60329_.f_82292_, p_60329_.f_82293_);
      case DOWN:
         return new AABB(p_60329_.f_82288_, p_60329_.f_82289_ + d1, p_60329_.f_82290_, p_60329_.f_82291_, p_60329_.f_82289_ + d2, p_60329_.f_82293_);
      case UP:
      default:
         return new AABB(p_60329_.f_82288_, p_60329_.f_82292_ + d1, p_60329_.f_82290_, p_60329_.f_82291_, p_60329_.f_82292_ + d2, p_60329_.f_82293_);
      case NORTH:
         return new AABB(p_60329_.f_82288_, p_60329_.f_82289_, p_60329_.f_82290_ + d1, p_60329_.f_82291_, p_60329_.f_82292_, p_60329_.f_82290_ + d2);
      case SOUTH:
         return new AABB(p_60329_.f_82288_, p_60329_.f_82289_, p_60329_.f_82293_ + d1, p_60329_.f_82291_, p_60329_.f_82292_, p_60329_.f_82293_ + d2);
      }
   }
}