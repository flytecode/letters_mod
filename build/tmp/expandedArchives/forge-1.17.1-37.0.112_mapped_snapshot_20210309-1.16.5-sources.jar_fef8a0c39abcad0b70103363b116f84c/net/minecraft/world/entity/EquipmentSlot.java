package net.minecraft.world.entity;

public enum EquipmentSlot {
   MAINHAND(EquipmentSlot.Type.HAND, 0, 0, "mainhand"),
   OFFHAND(EquipmentSlot.Type.HAND, 1, 5, "offhand"),
   FEET(EquipmentSlot.Type.ARMOR, 0, 1, "feet"),
   LEGS(EquipmentSlot.Type.ARMOR, 1, 2, "legs"),
   CHEST(EquipmentSlot.Type.ARMOR, 2, 3, "chest"),
   HEAD(EquipmentSlot.Type.ARMOR, 3, 4, "head");

   private final EquipmentSlot.Type f_20730_;
   private final int f_20731_;
   private final int f_20732_;
   private final String f_20733_;

   private EquipmentSlot(EquipmentSlot.Type p_20739_, int p_20740_, int p_20741_, String p_20742_) {
      this.f_20730_ = p_20739_;
      this.f_20731_ = p_20740_;
      this.f_20732_ = p_20741_;
      this.f_20733_ = p_20742_;
   }

   public EquipmentSlot.Type m_20743_() {
      return this.f_20730_;
   }

   public int m_20749_() {
      return this.f_20731_;
   }

   public int m_147068_(int p_147069_) {
      return p_147069_ + this.f_20731_;
   }

   public int m_20750_() {
      return this.f_20732_;
   }

   public String m_20751_() {
      return this.f_20733_;
   }

   public static EquipmentSlot m_20747_(String p_20748_) {
      for(EquipmentSlot equipmentslot : values()) {
         if (equipmentslot.m_20751_().equals(p_20748_)) {
            return equipmentslot;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + p_20748_ + "'");
   }

   public static EquipmentSlot m_20744_(EquipmentSlot.Type p_20745_, int p_20746_) {
      for(EquipmentSlot equipmentslot : values()) {
         if (equipmentslot.m_20743_() == p_20745_ && equipmentslot.m_20749_() == p_20746_) {
            return equipmentslot;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + p_20745_ + "': " + p_20746_);
   }

   public static enum Type {
      HAND,
      ARMOR;
   }
}