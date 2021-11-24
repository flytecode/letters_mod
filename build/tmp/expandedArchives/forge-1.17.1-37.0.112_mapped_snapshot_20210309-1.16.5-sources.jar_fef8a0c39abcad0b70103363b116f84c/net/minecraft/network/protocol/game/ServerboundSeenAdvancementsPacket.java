package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

public class ServerboundSeenAdvancementsPacket implements Packet<ServerGamePacketListener> {
   private final ServerboundSeenAdvancementsPacket.Action f_134430_;
   @Nullable
   private final ResourceLocation f_134431_;

   public ServerboundSeenAdvancementsPacket(ServerboundSeenAdvancementsPacket.Action p_134434_, @Nullable ResourceLocation p_134435_) {
      this.f_134430_ = p_134434_;
      this.f_134431_ = p_134435_;
   }

   public static ServerboundSeenAdvancementsPacket m_134442_(Advancement p_134443_) {
      return new ServerboundSeenAdvancementsPacket(ServerboundSeenAdvancementsPacket.Action.OPENED_TAB, p_134443_.m_138327_());
   }

   public static ServerboundSeenAdvancementsPacket m_134444_() {
      return new ServerboundSeenAdvancementsPacket(ServerboundSeenAdvancementsPacket.Action.CLOSED_SCREEN, (ResourceLocation)null);
   }

   public ServerboundSeenAdvancementsPacket(FriendlyByteBuf p_179744_) {
      this.f_134430_ = p_179744_.m_130066_(ServerboundSeenAdvancementsPacket.Action.class);
      if (this.f_134430_ == ServerboundSeenAdvancementsPacket.Action.OPENED_TAB) {
         this.f_134431_ = p_179744_.m_130281_();
      } else {
         this.f_134431_ = null;
      }

   }

   public void m_5779_(FriendlyByteBuf p_134446_) {
      p_134446_.m_130068_(this.f_134430_);
      if (this.f_134430_ == ServerboundSeenAdvancementsPacket.Action.OPENED_TAB) {
         p_134446_.m_130085_(this.f_134431_);
      }

   }

   public void m_5797_(ServerGamePacketListener p_134441_) {
      p_134441_.m_6947_(this);
   }

   public ServerboundSeenAdvancementsPacket.Action m_134447_() {
      return this.f_134430_;
   }

   @Nullable
   public ResourceLocation m_134448_() {
      return this.f_134431_;
   }

   public static enum Action {
      OPENED_TAB,
      CLOSED_SCREEN;
   }
}