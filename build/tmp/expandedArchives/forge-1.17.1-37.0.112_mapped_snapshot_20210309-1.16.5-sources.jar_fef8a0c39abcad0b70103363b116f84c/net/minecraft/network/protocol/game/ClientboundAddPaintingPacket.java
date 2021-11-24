package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;

public class ClientboundAddPaintingPacket implements Packet<ClientGamePacketListener> {
   private final int f_131566_;
   private final UUID f_131567_;
   private final BlockPos f_131568_;
   private final Direction f_131569_;
   private final int f_131570_;

   public ClientboundAddPaintingPacket(Painting p_131573_) {
      this.f_131566_ = p_131573_.m_142049_();
      this.f_131567_ = p_131573_.m_142081_();
      this.f_131568_ = p_131573_.m_31748_();
      this.f_131569_ = p_131573_.m_6350_();
      this.f_131570_ = Registry.f_122831_.m_7447_(p_131573_.f_31902_);
   }

   public ClientboundAddPaintingPacket(FriendlyByteBuf p_178568_) {
      this.f_131566_ = p_178568_.m_130242_();
      this.f_131567_ = p_178568_.m_130259_();
      this.f_131570_ = p_178568_.m_130242_();
      this.f_131568_ = p_178568_.m_130135_();
      this.f_131569_ = Direction.m_122407_(p_178568_.readUnsignedByte());
   }

   public void m_5779_(FriendlyByteBuf p_131582_) {
      p_131582_.m_130130_(this.f_131566_);
      p_131582_.m_130077_(this.f_131567_);
      p_131582_.m_130130_(this.f_131570_);
      p_131582_.m_130064_(this.f_131568_);
      p_131582_.writeByte(this.f_131569_.m_122416_());
   }

   public void m_5797_(ClientGamePacketListener p_131579_) {
      p_131579_.m_6433_(this);
   }

   public int m_131580_() {
      return this.f_131566_;
   }

   public UUID m_131583_() {
      return this.f_131567_;
   }

   public BlockPos m_131584_() {
      return this.f_131568_;
   }

   public Direction m_131585_() {
      return this.f_131569_;
   }

   public Motive m_131586_() {
      return Registry.f_122831_.m_7942_(this.f_131570_);
   }
}