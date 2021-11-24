package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.crafting.Ingredient;

public interface ArmorMaterial {
   int m_7366_(EquipmentSlot p_40410_);

   int m_7365_(EquipmentSlot p_40411_);

   int m_6646_();

   SoundEvent m_7344_();

   Ingredient m_6230_();

   String m_6082_();

   float m_6651_();

   float m_6649_();
}