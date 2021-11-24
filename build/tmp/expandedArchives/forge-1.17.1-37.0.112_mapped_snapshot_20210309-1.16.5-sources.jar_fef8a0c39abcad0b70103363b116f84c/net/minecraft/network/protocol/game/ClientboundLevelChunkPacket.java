package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.BitSet;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;

public class ClientboundLevelChunkPacket implements Packet<ClientGamePacketListener> {
   public static final int f_178896_ = 2097152;
   private final int f_132222_;
   private final int f_132223_;
   private final BitSet f_132224_;
   private final CompoundTag f_132225_;
   private final int[] f_132226_;
   private final byte[] f_132227_;
   private final List<CompoundTag> f_132228_;

   public ClientboundLevelChunkPacket(LevelChunk p_178898_) {
      ChunkPos chunkpos = p_178898_.m_7697_();
      this.f_132222_ = chunkpos.f_45578_;
      this.f_132223_ = chunkpos.f_45579_;
      this.f_132225_ = new CompoundTag();

      for(Entry<Heightmap.Types, Heightmap> entry : p_178898_.m_6890_()) {
         if (entry.getKey().m_64297_()) {
            this.f_132225_.m_128365_(entry.getKey().m_64294_(), new LongArrayTag(entry.getValue().m_64239_()));
         }
      }

      this.f_132226_ = p_178898_.m_6221_().m_62131_();
      this.f_132227_ = new byte[this.m_178901_(p_178898_)];
      this.f_132224_ = this.m_178903_(new FriendlyByteBuf(this.m_132257_()), p_178898_);
      this.f_132228_ = Lists.newArrayList();

      for(Entry<BlockPos, BlockEntity> entry1 : p_178898_.m_62954_().entrySet()) {
         BlockEntity blockentity = entry1.getValue();
         CompoundTag compoundtag = blockentity.m_5995_();
         this.f_132228_.add(compoundtag);
      }

   }

   public ClientboundLevelChunkPacket(FriendlyByteBuf p_178900_) {
      this.f_132222_ = p_178900_.readInt();
      this.f_132223_ = p_178900_.readInt();
      this.f_132224_ = p_178900_.m_178384_();
      this.f_132225_ = p_178900_.m_130260_();
      if (this.f_132225_ == null) {
         throw new RuntimeException("Can't read heightmap in packet for [" + this.f_132222_ + ", " + this.f_132223_ + "]");
      } else {
         this.f_132226_ = p_178900_.m_130116_(ChunkBiomeContainer.f_156118_);
         int i = p_178900_.m_130242_();
         if (i > 2097152) {
            throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
         } else {
            this.f_132227_ = new byte[i];
            p_178900_.readBytes(this.f_132227_);
            this.f_132228_ = p_178900_.m_178366_(FriendlyByteBuf::m_130260_);
         }
      }
   }

   public void m_5779_(FriendlyByteBuf p_132249_) {
      p_132249_.writeInt(this.f_132222_);
      p_132249_.writeInt(this.f_132223_);
      p_132249_.m_178350_(this.f_132224_);
      p_132249_.m_130079_(this.f_132225_);
      p_132249_.m_130089_(this.f_132226_);
      p_132249_.m_130130_(this.f_132227_.length);
      p_132249_.writeBytes(this.f_132227_);
      p_132249_.m_178352_(this.f_132228_, FriendlyByteBuf::m_130079_);
   }

   public void m_5797_(ClientGamePacketListener p_132246_) {
      p_132246_.m_5623_(this);
   }

   public FriendlyByteBuf m_132247_() {
      return new FriendlyByteBuf(Unpooled.wrappedBuffer(this.f_132227_));
   }

   private ByteBuf m_132257_() {
      ByteBuf bytebuf = Unpooled.wrappedBuffer(this.f_132227_);
      bytebuf.writerIndex(0);
      return bytebuf;
   }

   public BitSet m_178903_(FriendlyByteBuf p_178904_, LevelChunk p_178905_) {
      BitSet bitset = new BitSet();
      LevelChunkSection[] alevelchunksection = p_178905_.m_7103_();
      int i = 0;

      for(int j = alevelchunksection.length; i < j; ++i) {
         LevelChunkSection levelchunksection = alevelchunksection[i];
         if (levelchunksection != LevelChunk.f_62770_ && !levelchunksection.m_63013_()) {
            bitset.set(i);
            levelchunksection.m_63011_(p_178904_);
         }
      }

      return bitset;
   }

   protected int m_178901_(LevelChunk p_178902_) {
      int i = 0;
      LevelChunkSection[] alevelchunksection = p_178902_.m_7103_();
      int j = 0;

      for(int k = alevelchunksection.length; j < k; ++j) {
         LevelChunkSection levelchunksection = alevelchunksection[j];
         if (levelchunksection != LevelChunk.f_62770_ && !levelchunksection.m_63013_()) {
            i += levelchunksection.m_63020_();
         }
      }

      return i;
   }

   public int m_132250_() {
      return this.f_132222_;
   }

   public int m_132251_() {
      return this.f_132223_;
   }

   public BitSet m_178906_() {
      return this.f_132224_;
   }

   public CompoundTag m_132254_() {
      return this.f_132225_;
   }

   public List<CompoundTag> m_132255_() {
      return this.f_132228_;
   }

   public int[] m_132256_() {
      return this.f_132226_;
   }
}