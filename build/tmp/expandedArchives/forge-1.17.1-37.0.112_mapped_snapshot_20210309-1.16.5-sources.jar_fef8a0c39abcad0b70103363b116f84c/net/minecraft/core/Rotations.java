package net.minecraft.core;

import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;

public class Rotations {
   protected final float f_123146_;
   protected final float f_123147_;
   protected final float f_123148_;

   public Rotations(float p_123150_, float p_123151_, float p_123152_) {
      this.f_123146_ = !Float.isInfinite(p_123150_) && !Float.isNaN(p_123150_) ? p_123150_ % 360.0F : 0.0F;
      this.f_123147_ = !Float.isInfinite(p_123151_) && !Float.isNaN(p_123151_) ? p_123151_ % 360.0F : 0.0F;
      this.f_123148_ = !Float.isInfinite(p_123152_) && !Float.isNaN(p_123152_) ? p_123152_ % 360.0F : 0.0F;
   }

   public Rotations(ListTag p_123154_) {
      this(p_123154_.m_128775_(0), p_123154_.m_128775_(1), p_123154_.m_128775_(2));
   }

   public ListTag m_123155_() {
      ListTag listtag = new ListTag();
      listtag.add(FloatTag.m_128566_(this.f_123146_));
      listtag.add(FloatTag.m_128566_(this.f_123147_));
      listtag.add(FloatTag.m_128566_(this.f_123148_));
      return listtag;
   }

   public boolean equals(Object p_123160_) {
      if (!(p_123160_ instanceof Rotations)) {
         return false;
      } else {
         Rotations rotations = (Rotations)p_123160_;
         return this.f_123146_ == rotations.f_123146_ && this.f_123147_ == rotations.f_123147_ && this.f_123148_ == rotations.f_123148_;
      }
   }

   public float m_123156_() {
      return this.f_123146_;
   }

   public float m_123157_() {
      return this.f_123147_;
   }

   public float m_123158_() {
      return this.f_123148_;
   }

   public float m_175532_() {
      return Mth.m_14177_(this.f_123146_);
   }

   public float m_175533_() {
      return Mth.m_14177_(this.f_123147_);
   }

   public float m_175534_() {
      return Mth.m_14177_(this.f_123148_);
   }
}