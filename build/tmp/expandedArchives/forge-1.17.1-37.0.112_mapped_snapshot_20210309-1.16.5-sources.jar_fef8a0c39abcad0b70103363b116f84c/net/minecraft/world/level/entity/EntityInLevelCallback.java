package net.minecraft.world.level.entity;

import net.minecraft.world.entity.Entity;

public interface EntityInLevelCallback {
   EntityInLevelCallback f_156799_ = new EntityInLevelCallback() {
      public void m_142044_() {
      }

      public void m_142472_(Entity.RemovalReason p_156805_) {
      }
   };

   void m_142044_();

   void m_142472_(Entity.RemovalReason p_156801_);
}