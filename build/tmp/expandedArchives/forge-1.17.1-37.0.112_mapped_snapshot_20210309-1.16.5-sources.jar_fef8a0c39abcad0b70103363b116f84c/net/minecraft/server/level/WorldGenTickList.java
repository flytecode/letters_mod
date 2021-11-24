package net.minecraft.server.level;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.TickPriority;

public class WorldGenTickList<T> implements TickList<T> {
   private final Function<BlockPos, TickList<T>> f_9603_;

   public WorldGenTickList(Function<BlockPos, TickList<T>> p_9605_) {
      this.f_9603_ = p_9605_;
   }

   public boolean m_5916_(BlockPos p_9607_, T p_9608_) {
      return this.f_9603_.apply(p_9607_).m_5916_(p_9607_, p_9608_);
   }

   public void m_7663_(BlockPos p_9610_, T p_9611_, int p_9612_, TickPriority p_9613_) {
      this.f_9603_.apply(p_9610_).m_7663_(p_9610_, p_9611_, p_9612_, p_9613_);
   }

   public boolean m_5913_(BlockPos p_9615_, T p_9616_) {
      return false;
   }

   public int m_142536_() {
      return 0;
   }
}