package net.minecraft.world.level;

public enum TickPriority {
   EXTREMELY_HIGH(-3),
   VERY_HIGH(-2),
   HIGH(-1),
   NORMAL(0),
   LOW(1),
   VERY_LOW(2),
   EXTREMELY_LOW(3);

   private final int f_47356_;

   private TickPriority(int p_47362_) {
      this.f_47356_ = p_47362_;
   }

   public static TickPriority m_47364_(int p_47365_) {
      for(TickPriority tickpriority : values()) {
         if (tickpriority.f_47356_ == p_47365_) {
            return tickpriority;
         }
      }

      return p_47365_ < EXTREMELY_HIGH.f_47356_ ? EXTREMELY_HIGH : EXTREMELY_LOW;
   }

   public int m_47363_() {
      return this.f_47356_;
   }
}