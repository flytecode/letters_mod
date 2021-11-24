package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;

public class ServerboundUseItemPacket implements Packet<ServerGamePacketListener> {
   private final InteractionHand f_134707_;

   public ServerboundUseItemPacket(InteractionHand p_134710_) {
      this.f_134707_ = p_134710_;
   }

   public ServerboundUseItemPacket(FriendlyByteBuf p_179798_) {
      this.f_134707_ = p_179798_.m_130066_(InteractionHand.class);
   }

   public void m_5779_(FriendlyByteBuf p_134719_) {
      p_134719_.m_130068_(this.f_134707_);
   }

   public void m_5797_(ServerGamePacketListener p_134716_) {
      p_134716_.m_5760_(this);
   }

   public InteractionHand m_134717_() {
      return this.f_134707_;
   }
}