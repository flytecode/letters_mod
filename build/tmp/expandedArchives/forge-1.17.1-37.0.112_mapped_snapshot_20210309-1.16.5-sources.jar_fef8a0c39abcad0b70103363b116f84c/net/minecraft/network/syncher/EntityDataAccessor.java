package net.minecraft.network.syncher;

public class EntityDataAccessor<T> {
   private final int f_135010_;
   private final EntityDataSerializer<T> f_135011_;

   public EntityDataAccessor(int p_135013_, EntityDataSerializer<T> p_135014_) {
      this.f_135010_ = p_135013_;
      this.f_135011_ = p_135014_;
   }

   public int m_135015_() {
      return this.f_135010_;
   }

   public EntityDataSerializer<T> m_135016_() {
      return this.f_135011_;
   }

   public boolean equals(Object p_135018_) {
      if (this == p_135018_) {
         return true;
      } else if (p_135018_ != null && this.getClass() == p_135018_.getClass()) {
         EntityDataAccessor<?> entitydataaccessor = (EntityDataAccessor)p_135018_;
         return this.f_135010_ == entitydataaccessor.f_135010_;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.f_135010_;
   }

   public String toString() {
      return "<entity data: " + this.f_135010_ + ">";
   }
}