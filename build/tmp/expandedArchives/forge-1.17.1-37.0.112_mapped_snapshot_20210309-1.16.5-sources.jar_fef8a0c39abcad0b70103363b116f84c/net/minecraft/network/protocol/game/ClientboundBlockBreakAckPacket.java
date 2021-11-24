package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientboundBlockBreakAckPacket implements Packet<ClientGamePacketListener> {
   private static final Logger f_131647_ = LogManager.getLogger();
   private final BlockPos f_131648_;
   private final BlockState f_131649_;
   private final ServerboundPlayerActionPacket.Action f_131646_;
   private final boolean f_131650_;

   public ClientboundBlockBreakAckPacket(BlockPos p_131654_, BlockState p_131655_, ServerboundPlayerActionPacket.Action p_131656_, boolean p_131657_, String p_131658_) {
      this.f_131648_ = p_131654_.m_7949_();
      this.f_131649_ = p_131655_;
      this.f_131646_ = p_131656_;
      this.f_131650_ = p_131657_;
   }

   public ClientboundBlockBreakAckPacket(FriendlyByteBuf p_178604_) {
      this.f_131648_ = p_178604_.m_130135_();
      this.f_131649_ = Block.f_49791_.m_7942_(p_178604_.m_130242_());
      this.f_131646_ = p_178604_.m_130066_(ServerboundPlayerActionPacket.Action.class);
      this.f_131650_ = p_178604_.readBoolean();
   }

   public void m_5779_(FriendlyByteBuf p_131667_) {
      p_131667_.m_130064_(this.f_131648_);
      p_131667_.m_130130_(Block.m_49956_(this.f_131649_));
      p_131667_.m_130068_(this.f_131646_);
      p_131667_.writeBoolean(this.f_131650_);
   }

   public void m_5797_(ClientGamePacketListener p_131664_) {
      p_131664_.m_6695_(this);
   }

   public BlockState m_131665_() {
      return this.f_131649_;
   }

   public BlockPos m_131668_() {
      return this.f_131648_;
   }

   public boolean m_131669_() {
      return this.f_131650_;
   }

   public ServerboundPlayerActionPacket.Action m_131670_() {
      return this.f_131646_;
   }
}