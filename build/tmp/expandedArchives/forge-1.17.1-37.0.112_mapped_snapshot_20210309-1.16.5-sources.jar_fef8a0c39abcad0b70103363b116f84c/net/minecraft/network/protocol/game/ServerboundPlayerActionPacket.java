package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundPlayerActionPacket implements Packet<ServerGamePacketListener> {
   private final BlockPos f_134267_;
   private final Direction f_134268_;
   private final ServerboundPlayerActionPacket.Action f_134269_;

   public ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action p_134272_, BlockPos p_134273_, Direction p_134274_) {
      this.f_134269_ = p_134272_;
      this.f_134267_ = p_134273_.m_7949_();
      this.f_134268_ = p_134274_;
   }

   public ServerboundPlayerActionPacket(FriendlyByteBuf p_179711_) {
      this.f_134269_ = p_179711_.m_130066_(ServerboundPlayerActionPacket.Action.class);
      this.f_134267_ = p_179711_.m_130135_();
      this.f_134268_ = Direction.m_122376_(p_179711_.readUnsignedByte());
   }

   public void m_5779_(FriendlyByteBuf p_134283_) {
      p_134283_.m_130068_(this.f_134269_);
      p_134283_.m_130064_(this.f_134267_);
      p_134283_.writeByte(this.f_134268_.m_122411_());
   }

   public void m_5797_(ServerGamePacketListener p_134280_) {
      p_134280_.m_7502_(this);
   }

   public BlockPos m_134281_() {
      return this.f_134267_;
   }

   public Direction m_134284_() {
      return this.f_134268_;
   }

   public ServerboundPlayerActionPacket.Action m_134285_() {
      return this.f_134269_;
   }

   public static enum Action {
      START_DESTROY_BLOCK,
      ABORT_DESTROY_BLOCK,
      STOP_DESTROY_BLOCK,
      DROP_ALL_ITEMS,
      DROP_ITEM,
      RELEASE_USE_ITEM,
      SWAP_ITEM_WITH_OFFHAND;
   }
}