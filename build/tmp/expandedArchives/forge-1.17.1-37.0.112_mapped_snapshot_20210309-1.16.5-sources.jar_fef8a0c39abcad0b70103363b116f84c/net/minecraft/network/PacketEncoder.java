package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class PacketEncoder extends MessageToByteEncoder<Packet<?>> {
   private static final Logger f_130538_ = LogManager.getLogger();
   private static final Marker f_130539_ = MarkerManager.getMarker("PACKET_SENT", Connection.f_129460_);
   private final PacketFlow f_130540_;

   public PacketEncoder(PacketFlow p_130543_) {
      this.f_130540_ = p_130543_;
   }

   protected void encode(ChannelHandlerContext p_130545_, Packet<?> p_130546_, ByteBuf p_130547_) throws Exception {
      ConnectionProtocol connectionprotocol = p_130545_.channel().attr(Connection.f_129461_).get();
      if (connectionprotocol == null) {
         throw new RuntimeException("ConnectionProtocol unknown: " + p_130546_);
      } else {
         Integer integer = connectionprotocol.m_129597_(this.f_130540_, p_130546_);
         if (f_130538_.isDebugEnabled()) {
            f_130538_.debug(f_130539_, "OUT: [{}:{}] {}", p_130545_.channel().attr(Connection.f_129461_).get(), integer, p_130546_.getClass().getName());
         }

         if (integer == null) {
            throw new IOException("Can't serialize unregistered packet");
         } else {
            FriendlyByteBuf friendlybytebuf = new FriendlyByteBuf(p_130547_);
            friendlybytebuf.m_130130_(integer);

            try {
               int i = friendlybytebuf.writerIndex();
               p_130546_.m_5779_(friendlybytebuf);
               int j = friendlybytebuf.writerIndex() - i;
               if (j > 8388608) {
                  throw new IllegalArgumentException("Packet too big (is " + j + ", should be less than 8388608): " + p_130546_);
               }
            } catch (Throwable throwable) {
               f_130538_.error("Error encoding packet", throwable); // Forge: Get Minecraft to spit out more information about the Throwable we got.
               if (p_130546_.m_6588_()) {
                  throw new SkipPacketException(throwable);
               } else {
                  throw throwable;
               }
            }
         }
      }
   }
}
