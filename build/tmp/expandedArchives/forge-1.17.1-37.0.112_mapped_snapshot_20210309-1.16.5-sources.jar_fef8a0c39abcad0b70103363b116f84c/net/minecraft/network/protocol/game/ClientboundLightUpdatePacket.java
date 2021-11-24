package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.lighting.LevelLightEngine;

public class ClientboundLightUpdatePacket implements Packet<ClientGamePacketListener> {
   private final int f_132323_;
   private final int f_132324_;
   private final BitSet f_132325_;
   private final BitSet f_132326_;
   private final BitSet f_132327_;
   private final BitSet f_132328_;
   private final List<byte[]> f_132329_;
   private final List<byte[]> f_132330_;
   private final boolean f_132331_;

   public ClientboundLightUpdatePacket(ChunkPos p_178912_, LevelLightEngine p_178913_, @Nullable BitSet p_178914_, @Nullable BitSet p_178915_, boolean p_178916_) {
      this.f_132323_ = p_178912_.f_45578_;
      this.f_132324_ = p_178912_.f_45579_;
      this.f_132331_ = p_178916_;
      this.f_132325_ = new BitSet();
      this.f_132326_ = new BitSet();
      this.f_132327_ = new BitSet();
      this.f_132328_ = new BitSet();
      this.f_132329_ = Lists.newArrayList();
      this.f_132330_ = Lists.newArrayList();

      for(int i = 0; i < p_178913_.m_164446_(); ++i) {
         if (p_178914_ == null || p_178914_.get(i)) {
            m_178919_(p_178912_, p_178913_, LightLayer.SKY, i, this.f_132325_, this.f_132327_, this.f_132329_);
         }

         if (p_178915_ == null || p_178915_.get(i)) {
            m_178919_(p_178912_, p_178913_, LightLayer.BLOCK, i, this.f_132326_, this.f_132328_, this.f_132330_);
         }
      }

   }

   private static void m_178919_(ChunkPos p_178920_, LevelLightEngine p_178921_, LightLayer p_178922_, int p_178923_, BitSet p_178924_, BitSet p_178925_, List<byte[]> p_178926_) {
      DataLayer datalayer = p_178921_.m_75814_(p_178922_).m_8079_(SectionPos.m_123196_(p_178920_, p_178921_.m_164447_() + p_178923_));
      if (datalayer != null) {
         if (datalayer.m_62575_()) {
            p_178925_.set(p_178923_);
         } else {
            p_178924_.set(p_178923_);
            p_178926_.add((byte[])datalayer.m_7877_().clone());
         }
      }

   }

   public ClientboundLightUpdatePacket(FriendlyByteBuf p_178918_) {
      this.f_132323_ = p_178918_.m_130242_();
      this.f_132324_ = p_178918_.m_130242_();
      this.f_132331_ = p_178918_.readBoolean();
      this.f_132325_ = p_178918_.m_178384_();
      this.f_132326_ = p_178918_.m_178384_();
      this.f_132327_ = p_178918_.m_178384_();
      this.f_132328_ = p_178918_.m_178384_();
      this.f_132329_ = p_178918_.m_178366_((p_178930_) -> {
         return p_178930_.m_130101_(2048);
      });
      this.f_132330_ = p_178918_.m_178366_((p_178928_) -> {
         return p_178928_.m_130101_(2048);
      });
   }

   public void m_5779_(FriendlyByteBuf p_132351_) {
      p_132351_.m_130130_(this.f_132323_);
      p_132351_.m_130130_(this.f_132324_);
      p_132351_.writeBoolean(this.f_132331_);
      p_132351_.m_178350_(this.f_132325_);
      p_132351_.m_178350_(this.f_132326_);
      p_132351_.m_178350_(this.f_132327_);
      p_132351_.m_178350_(this.f_132328_);
      p_132351_.m_178352_(this.f_132329_, FriendlyByteBuf::m_130087_);
      p_132351_.m_178352_(this.f_132330_, FriendlyByteBuf::m_130087_);
   }

   public void m_5797_(ClientGamePacketListener p_132348_) {
      p_132348_.m_6496_(this);
   }

   public int m_132349_() {
      return this.f_132323_;
   }

   public int m_132352_() {
      return this.f_132324_;
   }

   public BitSet m_178931_() {
      return this.f_132325_;
   }

   public BitSet m_178932_() {
      return this.f_132327_;
   }

   public List<byte[]> m_132355_() {
      return this.f_132329_;
   }

   public BitSet m_178933_() {
      return this.f_132326_;
   }

   public BitSet m_178934_() {
      return this.f_132328_;
   }

   public List<byte[]> m_132358_() {
      return this.f_132330_;
   }

   public boolean m_132359_() {
      return this.f_132331_;
   }
}