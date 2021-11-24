package net.minecraft.commands;

import java.util.UUID;
import net.minecraft.network.chat.Component;

public interface CommandSource {
   CommandSource f_80164_ = new CommandSource() {
      public void m_6352_(Component p_80172_, UUID p_80173_) {
      }

      public boolean m_6999_() {
         return false;
      }

      public boolean m_7028_() {
         return false;
      }

      public boolean m_6102_() {
         return false;
      }
   };

   void m_6352_(Component p_80166_, UUID p_80167_);

   boolean m_6999_();

   boolean m_7028_();

   boolean m_6102_();

   default boolean m_142559_() {
      return false;
   }
}