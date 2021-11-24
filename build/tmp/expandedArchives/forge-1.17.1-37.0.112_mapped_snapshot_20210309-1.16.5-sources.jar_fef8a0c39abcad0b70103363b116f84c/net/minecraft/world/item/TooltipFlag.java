package net.minecraft.world.item;

public interface TooltipFlag {
   boolean m_7050_();

   public static enum Default implements TooltipFlag {
      NORMAL(false),
      ADVANCED(true);

      private final boolean f_43368_;

      private Default(boolean p_43374_) {
         this.f_43368_ = p_43374_;
      }

      public boolean m_7050_() {
         return this.f_43368_;
      }
   }
}