package net.minecraft.network.protocol.game;

import java.util.EnumSet;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundPlayerPositionPacket implements Packet<ClientGamePacketListener> {
   private final double f_132796_;
   private final double f_132797_;
   private final double f_132798_;
   private final float f_132799_;
   private final float f_132800_;
   private final Set<ClientboundPlayerPositionPacket.RelativeArgument> f_132801_;
   private final int f_132802_;
   private final boolean f_179147_;

   public ClientboundPlayerPositionPacket(double p_179149_, double p_179150_, double p_179151_, float p_179152_, float p_179153_, Set<ClientboundPlayerPositionPacket.RelativeArgument> p_179154_, int p_179155_, boolean p_179156_) {
      this.f_132796_ = p_179149_;
      this.f_132797_ = p_179150_;
      this.f_132798_ = p_179151_;
      this.f_132799_ = p_179152_;
      this.f_132800_ = p_179153_;
      this.f_132801_ = p_179154_;
      this.f_132802_ = p_179155_;
      this.f_179147_ = p_179156_;
   }

   public ClientboundPlayerPositionPacket(FriendlyByteBuf p_179158_) {
      this.f_132796_ = p_179158_.readDouble();
      this.f_132797_ = p_179158_.readDouble();
      this.f_132798_ = p_179158_.readDouble();
      this.f_132799_ = p_179158_.readFloat();
      this.f_132800_ = p_179158_.readFloat();
      this.f_132801_ = ClientboundPlayerPositionPacket.RelativeArgument.m_132840_(p_179158_.readUnsignedByte());
      this.f_132802_ = p_179158_.m_130242_();
      this.f_179147_ = p_179158_.readBoolean();
   }

   public void m_5779_(FriendlyByteBuf p_132820_) {
      p_132820_.writeDouble(this.f_132796_);
      p_132820_.writeDouble(this.f_132797_);
      p_132820_.writeDouble(this.f_132798_);
      p_132820_.writeFloat(this.f_132799_);
      p_132820_.writeFloat(this.f_132800_);
      p_132820_.writeByte(ClientboundPlayerPositionPacket.RelativeArgument.m_132842_(this.f_132801_));
      p_132820_.m_130130_(this.f_132802_);
      p_132820_.writeBoolean(this.f_179147_);
   }

   public void m_5797_(ClientGamePacketListener p_132817_) {
      p_132817_.m_5682_(this);
   }

   public double m_132818_() {
      return this.f_132796_;
   }

   public double m_132821_() {
      return this.f_132797_;
   }

   public double m_132822_() {
      return this.f_132798_;
   }

   public float m_132823_() {
      return this.f_132799_;
   }

   public float m_132824_() {
      return this.f_132800_;
   }

   public int m_132825_() {
      return this.f_132802_;
   }

   public boolean m_179159_() {
      return this.f_179147_;
   }

   public Set<ClientboundPlayerPositionPacket.RelativeArgument> m_132826_() {
      return this.f_132801_;
   }

   public static enum RelativeArgument {
      X(0),
      Y(1),
      Z(2),
      Y_ROT(3),
      X_ROT(4);

      private final int f_132832_;

      private RelativeArgument(int p_132838_) {
         this.f_132832_ = p_132838_;
      }

      private int m_132839_() {
         return 1 << this.f_132832_;
      }

      private boolean m_132844_(int p_132845_) {
         return (p_132845_ & this.m_132839_()) == this.m_132839_();
      }

      public static Set<ClientboundPlayerPositionPacket.RelativeArgument> m_132840_(int p_132841_) {
         Set<ClientboundPlayerPositionPacket.RelativeArgument> set = EnumSet.noneOf(ClientboundPlayerPositionPacket.RelativeArgument.class);

         for(ClientboundPlayerPositionPacket.RelativeArgument clientboundplayerpositionpacket$relativeargument : values()) {
            if (clientboundplayerpositionpacket$relativeargument.m_132844_(p_132841_)) {
               set.add(clientboundplayerpositionpacket$relativeargument);
            }
         }

         return set;
      }

      public static int m_132842_(Set<ClientboundPlayerPositionPacket.RelativeArgument> p_132843_) {
         int i = 0;

         for(ClientboundPlayerPositionPacket.RelativeArgument clientboundplayerpositionpacket$relativeargument : p_132843_) {
            i |= clientboundplayerpositionpacket$relativeargument.m_132839_();
         }

         return i;
      }
   }
}