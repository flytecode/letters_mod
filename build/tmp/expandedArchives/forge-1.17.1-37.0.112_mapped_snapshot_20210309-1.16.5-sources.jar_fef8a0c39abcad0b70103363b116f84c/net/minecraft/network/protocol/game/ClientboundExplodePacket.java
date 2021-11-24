package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class ClientboundExplodePacket implements Packet<ClientGamePacketListener> {
   private final double f_132105_;
   private final double f_132106_;
   private final double f_132107_;
   private final float f_132108_;
   private final List<BlockPos> f_132109_;
   private final float f_132110_;
   private final float f_132111_;
   private final float f_132112_;

   public ClientboundExplodePacket(double p_132115_, double p_132116_, double p_132117_, float p_132118_, List<BlockPos> p_132119_, @Nullable Vec3 p_132120_) {
      this.f_132105_ = p_132115_;
      this.f_132106_ = p_132116_;
      this.f_132107_ = p_132117_;
      this.f_132108_ = p_132118_;
      this.f_132109_ = Lists.newArrayList(p_132119_);
      if (p_132120_ != null) {
         this.f_132110_ = (float)p_132120_.f_82479_;
         this.f_132111_ = (float)p_132120_.f_82480_;
         this.f_132112_ = (float)p_132120_.f_82481_;
      } else {
         this.f_132110_ = 0.0F;
         this.f_132111_ = 0.0F;
         this.f_132112_ = 0.0F;
      }

   }

   public ClientboundExplodePacket(FriendlyByteBuf p_178845_) {
      this.f_132105_ = (double)p_178845_.readFloat();
      this.f_132106_ = (double)p_178845_.readFloat();
      this.f_132107_ = (double)p_178845_.readFloat();
      this.f_132108_ = p_178845_.readFloat();
      int i = Mth.m_14107_(this.f_132105_);
      int j = Mth.m_14107_(this.f_132106_);
      int k = Mth.m_14107_(this.f_132107_);
      this.f_132109_ = p_178845_.m_178366_((p_178850_) -> {
         int l = p_178850_.readByte() + i;
         int i1 = p_178850_.readByte() + j;
         int j1 = p_178850_.readByte() + k;
         return new BlockPos(l, i1, j1);
      });
      this.f_132110_ = p_178845_.readFloat();
      this.f_132111_ = p_178845_.readFloat();
      this.f_132112_ = p_178845_.readFloat();
   }

   public void m_5779_(FriendlyByteBuf p_132129_) {
      p_132129_.writeFloat((float)this.f_132105_);
      p_132129_.writeFloat((float)this.f_132106_);
      p_132129_.writeFloat((float)this.f_132107_);
      p_132129_.writeFloat(this.f_132108_);
      int i = Mth.m_14107_(this.f_132105_);
      int j = Mth.m_14107_(this.f_132106_);
      int k = Mth.m_14107_(this.f_132107_);
      p_132129_.m_178352_(this.f_132109_, (p_178855_, p_178856_) -> {
         int l = p_178856_.m_123341_() - i;
         int i1 = p_178856_.m_123342_() - j;
         int j1 = p_178856_.m_123343_() - k;
         p_178855_.writeByte(l);
         p_178855_.writeByte(i1);
         p_178855_.writeByte(j1);
      });
      p_132129_.writeFloat(this.f_132110_);
      p_132129_.writeFloat(this.f_132111_);
      p_132129_.writeFloat(this.f_132112_);
   }

   public void m_5797_(ClientGamePacketListener p_132126_) {
      p_132126_.m_7345_(this);
   }

   public float m_132127_() {
      return this.f_132110_;
   }

   public float m_132130_() {
      return this.f_132111_;
   }

   public float m_132131_() {
      return this.f_132112_;
   }

   public double m_132132_() {
      return this.f_132105_;
   }

   public double m_132133_() {
      return this.f_132106_;
   }

   public double m_132134_() {
      return this.f_132107_;
   }

   public float m_132135_() {
      return this.f_132108_;
   }

   public List<BlockPos> m_132136_() {
      return this.f_132109_;
   }
}