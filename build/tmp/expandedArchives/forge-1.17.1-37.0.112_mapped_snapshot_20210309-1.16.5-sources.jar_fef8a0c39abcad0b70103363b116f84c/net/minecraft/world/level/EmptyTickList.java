package net.minecraft.world.level;

import net.minecraft.core.BlockPos;

public class EmptyTickList<T> implements TickList<T> {
   private static final EmptyTickList<Object> f_45873_ = new EmptyTickList<>();

   public static <T> EmptyTickList<T> m_45888_() {
      return (EmptyTickList<T>)f_45873_;
   }

   public boolean m_5916_(BlockPos p_45877_, T p_45878_) {
      return false;
   }

   public void m_5945_(BlockPos p_45880_, T p_45881_, int p_45882_) {
   }

   public void m_7663_(BlockPos p_45884_, T p_45885_, int p_45886_, TickPriority p_45887_) {
   }

   public boolean m_5913_(BlockPos p_45890_, T p_45891_) {
      return false;
   }

   public int m_142536_() {
      return 0;
   }
}