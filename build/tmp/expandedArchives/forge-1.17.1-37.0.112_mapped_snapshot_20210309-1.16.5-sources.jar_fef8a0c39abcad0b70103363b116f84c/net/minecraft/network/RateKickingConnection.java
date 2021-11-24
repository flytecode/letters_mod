package net.minecraft.network;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RateKickingConnection extends Connection {
   private static final Logger f_130553_ = LogManager.getLogger();
   private static final Component f_130554_ = new TranslatableComponent("disconnect.exceeded_packet_rate");
   private final int f_130555_;

   public RateKickingConnection(int p_130558_) {
      super(PacketFlow.SERVERBOUND);
      this.f_130555_ = p_130558_;
   }

   protected void m_7073_() {
      super.m_7073_();
      float f = this.m_129542_();
      if (f > (float)this.f_130555_) {
         f_130553_.warn("Player exceeded rate-limit (sent {} packets per second)", (float)f);
         this.m_129514_(new ClientboundDisconnectPacket(f_130554_), (p_130560_) -> {
            this.m_129507_(f_130554_);
         });
         this.m_129540_();
      }

   }
}