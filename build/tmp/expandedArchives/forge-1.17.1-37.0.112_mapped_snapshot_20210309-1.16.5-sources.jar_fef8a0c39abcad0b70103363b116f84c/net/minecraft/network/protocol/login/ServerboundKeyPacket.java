package net.minecraft.network.protocol.login;

import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;

public class ServerboundKeyPacket implements Packet<ServerLoginPacketListener> {
   private final byte[] f_134852_;
   private final byte[] f_134853_;

   public ServerboundKeyPacket(SecretKey p_134856_, PublicKey p_134857_, byte[] p_134858_) throws CryptException {
      this.f_134852_ = Crypt.m_13594_(p_134857_, p_134856_.getEncoded());
      this.f_134853_ = Crypt.m_13594_(p_134857_, p_134858_);
   }

   public ServerboundKeyPacket(FriendlyByteBuf p_179829_) {
      this.f_134852_ = p_179829_.m_130052_();
      this.f_134853_ = p_179829_.m_130052_();
   }

   public void m_5779_(FriendlyByteBuf p_134870_) {
      p_134870_.m_130087_(this.f_134852_);
      p_134870_.m_130087_(this.f_134853_);
   }

   public void m_5797_(ServerLoginPacketListener p_134866_) {
      p_134866_.m_8072_(this);
   }

   public SecretKey m_134859_(PrivateKey p_134860_) throws CryptException {
      return Crypt.m_13597_(p_134860_, this.f_134852_);
   }

   public byte[] m_134867_(PrivateKey p_134868_) throws CryptException {
      return Crypt.m_13605_(p_134868_, this.f_134853_);
   }
}