package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;

public class ServerboundUseItemOnPacket implements Packet<ServerGamePacketListener> {
   private final BlockHitResult f_134691_;
   private final InteractionHand f_134692_;

   public ServerboundUseItemOnPacket(InteractionHand p_134695_, BlockHitResult p_134696_) {
      this.f_134692_ = p_134695_;
      this.f_134691_ = p_134696_;
   }

   public ServerboundUseItemOnPacket(FriendlyByteBuf p_179796_) {
      this.f_134692_ = p_179796_.m_130066_(InteractionHand.class);
      this.f_134691_ = p_179796_.m_130283_();
   }

   public void m_5779_(FriendlyByteBuf p_134705_) {
      p_134705_.m_130068_(this.f_134692_);
      p_134705_.m_130062_(this.f_134691_);
   }

   public void m_5797_(ServerGamePacketListener p_134702_) {
      p_134702_.m_6371_(this);
   }

   public InteractionHand m_134703_() {
      return this.f_134692_;
   }

   public BlockHitResult m_134706_() {
      return this.f_134691_;
   }
}