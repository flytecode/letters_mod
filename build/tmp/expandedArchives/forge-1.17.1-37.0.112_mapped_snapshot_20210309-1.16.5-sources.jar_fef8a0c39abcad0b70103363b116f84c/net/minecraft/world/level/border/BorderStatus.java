package net.minecraft.world.level.border;

public enum BorderStatus {
   GROWING(4259712),
   SHRINKING(16724016),
   STATIONARY(2138367);

   private final int f_61894_;

   private BorderStatus(int p_61900_) {
      this.f_61894_ = p_61900_;
   }

   public int m_61901_() {
      return this.f_61894_;
   }
}