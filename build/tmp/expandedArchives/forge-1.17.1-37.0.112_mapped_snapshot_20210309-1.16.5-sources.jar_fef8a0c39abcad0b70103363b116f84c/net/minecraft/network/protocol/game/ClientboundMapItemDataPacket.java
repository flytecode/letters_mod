package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class ClientboundMapItemDataPacket implements Packet<ClientGamePacketListener> {
   private final int f_132415_;
   private final byte f_132416_;
   private final boolean f_132418_;
   @Nullable
   private final List<MapDecoration> f_132419_;
   @Nullable
   private final MapItemSavedData.MapPatch f_178968_;

   public ClientboundMapItemDataPacket(int p_178970_, byte p_178971_, boolean p_178972_, @Nullable Collection<MapDecoration> p_178973_, @Nullable MapItemSavedData.MapPatch p_178974_) {
      this.f_132415_ = p_178970_;
      this.f_132416_ = p_178971_;
      this.f_132418_ = p_178972_;
      this.f_132419_ = p_178973_ != null ? Lists.newArrayList(p_178973_) : null;
      this.f_178968_ = p_178974_;
   }

   public ClientboundMapItemDataPacket(FriendlyByteBuf p_178976_) {
      this.f_132415_ = p_178976_.m_130242_();
      this.f_132416_ = p_178976_.readByte();
      this.f_132418_ = p_178976_.readBoolean();
      if (p_178976_.readBoolean()) {
         this.f_132419_ = p_178976_.m_178366_((p_178981_) -> {
            MapDecoration.Type mapdecoration$type = p_178981_.m_130066_(MapDecoration.Type.class);
            return new MapDecoration(mapdecoration$type, p_178981_.readByte(), p_178981_.readByte(), (byte)(p_178981_.readByte() & 15), p_178981_.readBoolean() ? p_178981_.m_130238_() : null);
         });
      } else {
         this.f_132419_ = null;
      }

      int i = p_178976_.readUnsignedByte();
      if (i > 0) {
         int j = p_178976_.readUnsignedByte();
         int k = p_178976_.readUnsignedByte();
         int l = p_178976_.readUnsignedByte();
         byte[] abyte = p_178976_.m_130052_();
         this.f_178968_ = new MapItemSavedData.MapPatch(k, l, i, j, abyte);
      } else {
         this.f_178968_ = null;
      }

   }

   public void m_5779_(FriendlyByteBuf p_132447_) {
      p_132447_.m_130130_(this.f_132415_);
      p_132447_.writeByte(this.f_132416_);
      p_132447_.writeBoolean(this.f_132418_);
      if (this.f_132419_ != null) {
         p_132447_.writeBoolean(true);
         p_132447_.m_178352_(this.f_132419_, (p_178978_, p_178979_) -> {
            p_178978_.m_130068_(p_178979_.m_77803_());
            p_178978_.writeByte(p_178979_.m_77804_());
            p_178978_.writeByte(p_178979_.m_77805_());
            p_178978_.writeByte(p_178979_.m_77806_() & 15);
            if (p_178979_.m_77810_() != null) {
               p_178978_.writeBoolean(true);
               p_178978_.m_130083_(p_178979_.m_77810_());
            } else {
               p_178978_.writeBoolean(false);
            }

         });
      } else {
         p_132447_.writeBoolean(false);
      }

      if (this.f_178968_ != null) {
         p_132447_.writeByte(this.f_178968_.f_164823_);
         p_132447_.writeByte(this.f_178968_.f_164824_);
         p_132447_.writeByte(this.f_178968_.f_164821_);
         p_132447_.writeByte(this.f_178968_.f_164822_);
         p_132447_.m_130087_(this.f_178968_.f_164825_);
      } else {
         p_132447_.writeByte(0);
      }

   }

   public void m_5797_(ClientGamePacketListener p_132444_) {
      p_132444_.m_7633_(this);
   }

   public int m_132445_() {
      return this.f_132415_;
   }

   public void m_132437_(MapItemSavedData p_132438_) {
      if (this.f_132419_ != null) {
         p_132438_.m_164801_(this.f_132419_);
      }

      if (this.f_178968_ != null) {
         this.f_178968_.m_164832_(p_132438_);
      }

   }

   public byte m_178982_() {
      return this.f_132416_;
   }

   public boolean m_178983_() {
      return this.f_132418_;
   }
}