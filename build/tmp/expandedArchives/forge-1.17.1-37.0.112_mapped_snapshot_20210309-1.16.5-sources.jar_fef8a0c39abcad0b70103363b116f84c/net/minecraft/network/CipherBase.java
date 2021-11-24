package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class CipherBase {
   private final Cipher f_129399_;
   private byte[] f_129400_ = new byte[0];
   private byte[] f_129401_ = new byte[0];

   protected CipherBase(Cipher p_129403_) {
      this.f_129399_ = p_129403_;
   }

   private byte[] m_129404_(ByteBuf p_129405_) {
      int i = p_129405_.readableBytes();
      if (this.f_129400_.length < i) {
         this.f_129400_ = new byte[i];
      }

      p_129405_.readBytes(this.f_129400_, 0, i);
      return this.f_129400_;
   }

   protected ByteBuf m_129409_(ChannelHandlerContext p_129410_, ByteBuf p_129411_) throws ShortBufferException {
      int i = p_129411_.readableBytes();
      byte[] abyte = this.m_129404_(p_129411_);
      ByteBuf bytebuf = p_129410_.alloc().heapBuffer(this.f_129399_.getOutputSize(i));
      bytebuf.writerIndex(this.f_129399_.update(abyte, 0, i, bytebuf.array(), bytebuf.arrayOffset()));
      return bytebuf;
   }

   protected void m_129406_(ByteBuf p_129407_, ByteBuf p_129408_) throws ShortBufferException {
      int i = p_129407_.readableBytes();
      byte[] abyte = this.m_129404_(p_129407_);
      int j = this.f_129399_.getOutputSize(i);
      if (this.f_129401_.length < j) {
         this.f_129401_ = new byte[j];
      }

      p_129408_.writeBytes(this.f_129401_, 0, this.f_129399_.update(abyte, 0, i, this.f_129401_));
   }
}