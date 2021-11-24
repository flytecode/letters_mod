package net.minecraft.client.gui.components.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface GuiEventListener {
   default void m_94757_(double p_94758_, double p_94759_) {
   }

   default boolean m_6375_(double p_94737_, double p_94738_, int p_94739_) {
      return false;
   }

   default boolean m_6348_(double p_94753_, double p_94754_, int p_94755_) {
      return false;
   }

   default boolean m_7979_(double p_94740_, double p_94741_, int p_94742_, double p_94743_, double p_94744_) {
      return false;
   }

   default boolean m_6050_(double p_94734_, double p_94735_, double p_94736_) {
      return false;
   }

   default boolean m_7933_(int p_94745_, int p_94746_, int p_94747_) {
      return false;
   }

   default boolean m_7920_(int p_94750_, int p_94751_, int p_94752_) {
      return false;
   }

   default boolean m_5534_(char p_94732_, int p_94733_) {
      return false;
   }

   default boolean m_5755_(boolean p_94756_) {
      return false;
   }

   default boolean m_5953_(double p_94748_, double p_94749_) {
      return false;
   }
}