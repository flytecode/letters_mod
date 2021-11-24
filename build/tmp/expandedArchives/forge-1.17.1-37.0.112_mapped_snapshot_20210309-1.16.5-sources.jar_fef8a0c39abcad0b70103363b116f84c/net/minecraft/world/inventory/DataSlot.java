package net.minecraft.world.inventory;

public abstract class DataSlot {
   private int f_39399_;

   public static DataSlot m_39403_(final ContainerData p_39404_, final int p_39405_) {
      return new DataSlot() {
         public int m_6501_() {
            return p_39404_.m_6413_(p_39405_);
         }

         public void m_6422_(int p_39416_) {
            p_39404_.m_8050_(p_39405_, p_39416_);
         }
      };
   }

   public static DataSlot m_39406_(final int[] p_39407_, final int p_39408_) {
      return new DataSlot() {
         public int m_6501_() {
            return p_39407_[p_39408_];
         }

         public void m_6422_(int p_39424_) {
            p_39407_[p_39408_] = p_39424_;
         }
      };
   }

   public static DataSlot m_39401_() {
      return new DataSlot() {
         private int f_39426_;

         public int m_6501_() {
            return this.f_39426_;
         }

         public void m_6422_(int p_39429_) {
            this.f_39426_ = p_39429_;
         }
      };
   }

   public abstract int m_6501_();

   public abstract void m_6422_(int p_39402_);

   public boolean m_39409_() {
      int i = this.m_6501_();
      boolean flag = i != this.f_39399_;
      this.f_39399_ = i;
      return flag;
   }
}