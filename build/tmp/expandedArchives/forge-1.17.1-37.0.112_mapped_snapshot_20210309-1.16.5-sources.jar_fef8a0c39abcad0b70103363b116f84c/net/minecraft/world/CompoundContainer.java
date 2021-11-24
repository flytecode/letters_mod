package net.minecraft.world;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CompoundContainer implements Container {
   private final Container f_18910_;
   private final Container f_18911_;

   public CompoundContainer(Container p_18913_, Container p_18914_) {
      if (p_18913_ == null) {
         p_18913_ = p_18914_;
      }

      if (p_18914_ == null) {
         p_18914_ = p_18913_;
      }

      this.f_18910_ = p_18913_;
      this.f_18911_ = p_18914_;
   }

   public int m_6643_() {
      return this.f_18910_.m_6643_() + this.f_18911_.m_6643_();
   }

   public boolean m_7983_() {
      return this.f_18910_.m_7983_() && this.f_18911_.m_7983_();
   }

   public boolean m_18927_(Container p_18928_) {
      return this.f_18910_ == p_18928_ || this.f_18911_ == p_18928_;
   }

   public ItemStack m_8020_(int p_18920_) {
      return p_18920_ >= this.f_18910_.m_6643_() ? this.f_18911_.m_8020_(p_18920_ - this.f_18910_.m_6643_()) : this.f_18910_.m_8020_(p_18920_);
   }

   public ItemStack m_7407_(int p_18922_, int p_18923_) {
      return p_18922_ >= this.f_18910_.m_6643_() ? this.f_18911_.m_7407_(p_18922_ - this.f_18910_.m_6643_(), p_18923_) : this.f_18910_.m_7407_(p_18922_, p_18923_);
   }

   public ItemStack m_8016_(int p_18932_) {
      return p_18932_ >= this.f_18910_.m_6643_() ? this.f_18911_.m_8016_(p_18932_ - this.f_18910_.m_6643_()) : this.f_18910_.m_8016_(p_18932_);
   }

   public void m_6836_(int p_18925_, ItemStack p_18926_) {
      if (p_18925_ >= this.f_18910_.m_6643_()) {
         this.f_18911_.m_6836_(p_18925_ - this.f_18910_.m_6643_(), p_18926_);
      } else {
         this.f_18910_.m_6836_(p_18925_, p_18926_);
      }

   }

   public int m_6893_() {
      return this.f_18910_.m_6893_();
   }

   public void m_6596_() {
      this.f_18910_.m_6596_();
      this.f_18911_.m_6596_();
   }

   public boolean m_6542_(Player p_18930_) {
      return this.f_18910_.m_6542_(p_18930_) && this.f_18911_.m_6542_(p_18930_);
   }

   public void m_5856_(Player p_18940_) {
      this.f_18910_.m_5856_(p_18940_);
      this.f_18911_.m_5856_(p_18940_);
   }

   public void m_5785_(Player p_18937_) {
      this.f_18910_.m_5785_(p_18937_);
      this.f_18911_.m_5785_(p_18937_);
   }

   public boolean m_7013_(int p_18934_, ItemStack p_18935_) {
      return p_18934_ >= this.f_18910_.m_6643_() ? this.f_18911_.m_7013_(p_18934_ - this.f_18910_.m_6643_(), p_18935_) : this.f_18910_.m_7013_(p_18934_, p_18935_);
   }

   public void m_6211_() {
      this.f_18910_.m_6211_();
      this.f_18911_.m_6211_();
   }
}