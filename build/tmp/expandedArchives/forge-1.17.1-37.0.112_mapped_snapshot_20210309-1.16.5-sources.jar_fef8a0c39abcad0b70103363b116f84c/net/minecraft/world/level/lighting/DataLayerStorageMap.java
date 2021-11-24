package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.world.level.chunk.DataLayer;

public abstract class DataLayerStorageMap<M extends DataLayerStorageMap<M>> {
   private static final int f_164421_ = 2;
   private final long[] f_75519_ = new long[2];
   private final DataLayer[] f_75520_ = new DataLayer[2];
   private boolean f_75521_;
   protected final Long2ObjectOpenHashMap<DataLayer> f_75518_;

   protected DataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> p_75523_) {
      this.f_75518_ = p_75523_;
      this.m_75531_();
      this.f_75521_ = true;
   }

   public abstract M m_5972_();

   public void m_75524_(long p_75525_) {
      this.f_75518_.put(p_75525_, this.f_75518_.get(p_75525_).m_62569_());
      this.m_75531_();
   }

   public boolean m_75529_(long p_75530_) {
      return this.f_75518_.containsKey(p_75530_);
   }

   @Nullable
   public DataLayer m_75532_(long p_75533_) {
      if (this.f_75521_) {
         for(int i = 0; i < 2; ++i) {
            if (p_75533_ == this.f_75519_[i]) {
               return this.f_75520_[i];
            }
         }
      }

      DataLayer datalayer = this.f_75518_.get(p_75533_);
      if (datalayer == null) {
         return null;
      } else {
         if (this.f_75521_) {
            for(int j = 1; j > 0; --j) {
               this.f_75519_[j] = this.f_75519_[j - 1];
               this.f_75520_[j] = this.f_75520_[j - 1];
            }

            this.f_75519_[0] = p_75533_;
            this.f_75520_[0] = datalayer;
         }

         return datalayer;
      }
   }

   @Nullable
   public DataLayer m_75535_(long p_75536_) {
      return this.f_75518_.remove(p_75536_);
   }

   public void m_75526_(long p_75527_, DataLayer p_75528_) {
      this.f_75518_.put(p_75527_, p_75528_);
   }

   public void m_75531_() {
      for(int i = 0; i < 2; ++i) {
         this.f_75519_[i] = Long.MAX_VALUE;
         this.f_75520_[i] = null;
      }

   }

   public void m_75534_() {
      this.f_75521_ = false;
   }
}