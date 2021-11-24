package net.minecraft.server.packs.repository;

import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public enum PackCompatibility {
   TOO_OLD("old"),
   TOO_NEW("new"),
   COMPATIBLE("compatible");

   private final Component f_10481_;
   private final Component f_10482_;

   private PackCompatibility(String p_10488_) {
      this.f_10481_ = (new TranslatableComponent("pack.incompatible." + p_10488_)).m_130940_(ChatFormatting.GRAY);
      this.f_10482_ = new TranslatableComponent("pack.incompatible.confirm." + p_10488_);
   }

   public boolean m_10489_() {
      return this == COMPATIBLE;
   }

   public static PackCompatibility m_143882_(int p_143883_, PackType p_143884_) {
      int i = p_143884_.m_143756_(SharedConstants.m_136187_());
      if (p_143883_ < i) {
         return TOO_OLD;
      } else {
         return p_143883_ > i ? TOO_NEW : COMPATIBLE;
      }
   }

   public static PackCompatibility m_143885_(PackMetadataSection p_143886_, PackType p_143887_) {
      return m_143882_(p_143886_.m_10374_(), p_143887_);
   }

   public Component m_10492_() {
      return this.f_10481_;
   }

   public Component m_10493_() {
      return this.f_10482_;
   }
}