package net.minecraft.server.packs.repository;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public interface PackSource {
   PackSource f_10527_ = m_10532_();
   PackSource f_10528_ = m_10533_("pack.source.builtin");
   PackSource f_10529_ = m_10533_("pack.source.world");
   PackSource f_10530_ = m_10533_("pack.source.server");

   Component m_10540_(Component p_10541_);

   static PackSource m_10532_() {
      return (p_10536_) -> {
         return p_10536_;
      };
   }

   static PackSource m_10533_(String p_10534_) {
      Component component = new TranslatableComponent(p_10534_);
      return (p_10539_) -> {
         return (new TranslatableComponent("pack.nameAndSource", p_10539_, component)).m_130940_(ChatFormatting.GRAY);
      };
   }
}