package net.minecraft.network.protocol.game;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.ServerScoreboard;

public class ClientboundSetScorePacket implements Packet<ClientGamePacketListener> {
   private final String f_133323_;
   @Nullable
   private final String f_133324_;
   private final int f_133325_;
   private final ServerScoreboard.Method f_133326_;

   public ClientboundSetScorePacket(ServerScoreboard.Method p_133329_, @Nullable String p_133330_, String p_133331_, int p_133332_) {
      if (p_133329_ != ServerScoreboard.Method.REMOVE && p_133330_ == null) {
         throw new IllegalArgumentException("Need an objective name");
      } else {
         this.f_133323_ = p_133331_;
         this.f_133324_ = p_133330_;
         this.f_133325_ = p_133332_;
         this.f_133326_ = p_133329_;
      }
   }

   public ClientboundSetScorePacket(FriendlyByteBuf p_179373_) {
      this.f_133323_ = p_179373_.m_130136_(40);
      this.f_133326_ = p_179373_.m_130066_(ServerScoreboard.Method.class);
      String s = p_179373_.m_130136_(16);
      this.f_133324_ = Objects.equals(s, "") ? null : s;
      if (this.f_133326_ != ServerScoreboard.Method.REMOVE) {
         this.f_133325_ = p_179373_.m_130242_();
      } else {
         this.f_133325_ = 0;
      }

   }

   public void m_5779_(FriendlyByteBuf p_133341_) {
      p_133341_.m_130070_(this.f_133323_);
      p_133341_.m_130068_(this.f_133326_);
      p_133341_.m_130070_(this.f_133324_ == null ? "" : this.f_133324_);
      if (this.f_133326_ != ServerScoreboard.Method.REMOVE) {
         p_133341_.m_130130_(this.f_133325_);
      }

   }

   public void m_5797_(ClientGamePacketListener p_133338_) {
      p_133338_.m_7519_(this);
   }

   public String m_133339_() {
      return this.f_133323_;
   }

   @Nullable
   public String m_133342_() {
      return this.f_133324_;
   }

   public int m_133343_() {
      return this.f_133325_;
   }

   public ServerScoreboard.Method m_133344_() {
      return this.f_133326_;
   }
}