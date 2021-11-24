package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class ClientboundSectionBlocksUpdatePacket implements Packet<ClientGamePacketListener> {
   private static final int f_179194_ = 12;
   private final SectionPos f_132980_;
   private final short[] f_132981_;
   private final BlockState[] f_132982_;
   private final boolean f_132983_;

   public ClientboundSectionBlocksUpdatePacket(SectionPos p_132986_, ShortSet p_132987_, LevelChunkSection p_132988_, boolean p_132989_) {
      this.f_132980_ = p_132986_;
      this.f_132983_ = p_132989_;
      int i = p_132987_.size();
      this.f_132981_ = new short[i];
      this.f_132982_ = new BlockState[i];
      int j = 0;

      for(short short1 : p_132987_) {
         this.f_132981_[j] = short1;
         this.f_132982_[j] = p_132988_.m_62982_(SectionPos.m_123204_(short1), SectionPos.m_123220_(short1), SectionPos.m_123227_(short1));
         ++j;
      }

   }

   public ClientboundSectionBlocksUpdatePacket(FriendlyByteBuf p_179196_) {
      this.f_132980_ = SectionPos.m_123184_(p_179196_.readLong());
      this.f_132983_ = p_179196_.readBoolean();
      int i = p_179196_.m_130242_();
      this.f_132981_ = new short[i];
      this.f_132982_ = new BlockState[i];

      for(int j = 0; j < i; ++j) {
         long k = p_179196_.m_130258_();
         this.f_132981_[j] = (short)((int)(k & 4095L));
         this.f_132982_[j] = Block.f_49791_.m_7942_((int)(k >>> 12));
      }

   }

   public void m_5779_(FriendlyByteBuf p_133002_) {
      p_133002_.writeLong(this.f_132980_.m_123252_());
      p_133002_.writeBoolean(this.f_132983_);
      p_133002_.m_130130_(this.f_132981_.length);

      for(int i = 0; i < this.f_132981_.length; ++i) {
         p_133002_.m_130103_((long)(Block.m_49956_(this.f_132982_[i]) << 12 | this.f_132981_[i]));
      }

   }

   public void m_5797_(ClientGamePacketListener p_132999_) {
      p_132999_.m_5771_(this);
   }

   public void m_132992_(BiConsumer<BlockPos, BlockState> p_132993_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i < this.f_132981_.length; ++i) {
         short short1 = this.f_132981_[i];
         blockpos$mutableblockpos.m_122178_(this.f_132980_.m_123232_(short1), this.f_132980_.m_123237_(short1), this.f_132980_.m_123242_(short1));
         p_132993_.accept(blockpos$mutableblockpos, this.f_132982_[i]);
      }

   }

   public boolean m_133000_() {
      return this.f_132983_;
   }
}