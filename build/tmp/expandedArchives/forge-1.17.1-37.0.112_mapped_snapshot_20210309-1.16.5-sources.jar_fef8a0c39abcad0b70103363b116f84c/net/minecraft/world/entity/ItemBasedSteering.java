package net.minecraft.world.entity;

import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;

public class ItemBasedSteering {
   private static final int f_147132_ = 140;
   private static final int f_147133_ = 700;
   private final SynchedEntityData f_20837_;
   private final EntityDataAccessor<Integer> f_20838_;
   private final EntityDataAccessor<Boolean> f_20839_;
   public boolean f_20834_;
   public int f_20835_;
   public int f_20836_;

   public ItemBasedSteering(SynchedEntityData p_20841_, EntityDataAccessor<Integer> p_20842_, EntityDataAccessor<Boolean> p_20843_) {
      this.f_20837_ = p_20841_;
      this.f_20838_ = p_20842_;
      this.f_20839_ = p_20843_;
   }

   public void m_20844_() {
      this.f_20834_ = true;
      this.f_20835_ = 0;
      this.f_20836_ = this.f_20837_.m_135370_(this.f_20838_);
   }

   public boolean m_20845_(Random p_20846_) {
      if (this.f_20834_) {
         return false;
      } else {
         this.f_20834_ = true;
         this.f_20835_ = 0;
         this.f_20836_ = p_20846_.nextInt(841) + 140;
         this.f_20837_.m_135381_(this.f_20838_, this.f_20836_);
         return true;
      }
   }

   public void m_20847_(CompoundTag p_20848_) {
      p_20848_.m_128379_("Saddle", this.m_20851_());
   }

   public void m_20852_(CompoundTag p_20853_) {
      this.m_20849_(p_20853_.m_128471_("Saddle"));
   }

   public void m_20849_(boolean p_20850_) {
      this.f_20837_.m_135381_(this.f_20839_, p_20850_);
   }

   public boolean m_20851_() {
      return this.f_20837_.m_135370_(this.f_20839_);
   }
}