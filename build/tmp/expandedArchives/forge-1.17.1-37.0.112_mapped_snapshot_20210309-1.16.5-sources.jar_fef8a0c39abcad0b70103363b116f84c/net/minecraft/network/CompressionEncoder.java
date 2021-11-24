package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class CompressionEncoder extends MessageToByteEncoder<ByteBuf> {
   private final byte[] f_129444_ = new byte[8192];
   private final Deflater f_129445_;
   private int f_129446_;
   private static final boolean DISABLE_PACKET_DEBUG = Boolean.parseBoolean(System.getProperty("forge.disablePacketCompressionDebug", "false"));
   private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger();

   public CompressionEncoder(int p_129448_) {
      this.f_129446_ = p_129448_;
      this.f_129445_ = new Deflater();
   }

   protected void encode(ChannelHandlerContext p_129452_, ByteBuf p_129453_, ByteBuf p_129454_) {
      int i = p_129453_.readableBytes();
      FriendlyByteBuf friendlybytebuf = new FriendlyByteBuf(p_129454_);
      if (i < this.f_129446_) {
         friendlybytebuf.m_130130_(0);
         friendlybytebuf.writeBytes(p_129453_);
      } else {
         if (!DISABLE_PACKET_DEBUG && i > 2097152) {
             p_129453_.markReaderIndex();
             LOGGER.error("Attempted to send packet over maximum protocol size: {} > 2097152\nData:\n{}", i,
                     net.minecraftforge.fmllegacy.common.network.ByteBufUtils.getContentDump(p_129453_));
             p_129453_.resetReaderIndex();
         }
         byte[] abyte = new byte[i];
         p_129453_.readBytes(abyte);
         friendlybytebuf.m_130130_(abyte.length);
         this.f_129445_.setInput(abyte, 0, i);
         this.f_129445_.finish();

         while(!this.f_129445_.finished()) {
            int j = this.f_129445_.deflate(this.f_129444_);
            friendlybytebuf.writeBytes(this.f_129444_, 0, j);
         }

         this.f_129445_.reset();
      }

   }

   public int m_178298_() {
      return this.f_129446_;
   }

   public void m_129449_(int p_129450_) {
      this.f_129446_ = p_129450_;
   }
}
