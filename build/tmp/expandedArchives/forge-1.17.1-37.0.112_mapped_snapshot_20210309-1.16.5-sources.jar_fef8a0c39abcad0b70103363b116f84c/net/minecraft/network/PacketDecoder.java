package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class PacketDecoder extends ByteToMessageDecoder {
   private static final Logger f_130528_ = LogManager.getLogger();
   private static final Marker f_130529_ = MarkerManager.getMarker("PACKET_RECEIVED", Connection.f_129460_);
   private final PacketFlow f_130530_;

   public PacketDecoder(PacketFlow p_130533_) {
      this.f_130530_ = p_130533_;
   }

   protected void decode(ChannelHandlerContext p_130535_, ByteBuf p_130536_, List<Object> p_130537_) throws Exception {
      if (p_130536_.readableBytes() != 0) {
         FriendlyByteBuf friendlybytebuf = new FriendlyByteBuf(p_130536_);
         int i = friendlybytebuf.m_130242_();
         Packet<?> packet = p_130535_.channel().attr(Connection.f_129461_).get().m_178321_(this.f_130530_, i, friendlybytebuf);
         if (packet == null) {
            throw new IOException("Bad packet id " + i);
         } else if (friendlybytebuf.readableBytes() > 0) {
            throw new IOException("Packet " + p_130535_.channel().attr(Connection.f_129461_).get().m_129582_() + "/" + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + friendlybytebuf.readableBytes() + " bytes extra whilst reading packet " + i);
         } else {
            p_130537_.add(packet);
            if (f_130528_.isDebugEnabled()) {
               f_130528_.debug(f_130529_, " IN: [{}:{}] {}", p_130535_.channel().attr(Connection.f_129461_).get(), i, packet.getClass().getName());
            }

         }
      }
   }
}