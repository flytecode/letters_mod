package net.minecraft.client.multiplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientChunkCache extends ChunkSource {
   static final Logger f_104407_ = LogManager.getLogger();
   private final LevelChunk f_104408_;
   private final LevelLightEngine f_104409_;
   volatile ClientChunkCache.Storage f_104410_;
   final ClientLevel f_104411_;

   public ClientChunkCache(ClientLevel p_104414_, int p_104415_) {
      this.f_104411_ = p_104414_;
      this.f_104408_ = new EmptyLevelChunk(p_104414_, new ChunkPos(0, 0));
      this.f_104409_ = new LevelLightEngine(this, true, p_104414_.m_6042_().m_63935_());
      this.f_104410_ = new ClientChunkCache.Storage(m_104448_(p_104415_));
   }

   public LevelLightEngine m_7827_() {
      return this.f_104409_;
   }

   private static boolean m_104438_(@Nullable LevelChunk p_104439_, int p_104440_, int p_104441_) {
      if (p_104439_ == null) {
         return false;
      } else {
         ChunkPos chunkpos = p_104439_.m_7697_();
         return chunkpos.f_45578_ == p_104440_ && chunkpos.f_45579_ == p_104441_;
      }
   }

   public void m_104455_(int p_104456_, int p_104457_) {
      if (this.f_104410_.m_104500_(p_104456_, p_104457_)) {
         int i = this.f_104410_.m_104481_(p_104456_, p_104457_);
         LevelChunk levelchunk = this.f_104410_.m_104479_(i);
         if (m_104438_(levelchunk, p_104456_, p_104457_)) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkEvent.Unload(levelchunk));
            this.f_104410_.m_104487_(i, levelchunk, (LevelChunk)null);
         }

      }
   }

   @Nullable
   public LevelChunk m_7587_(int p_104451_, int p_104452_, ChunkStatus p_104453_, boolean p_104454_) {
      if (this.f_104410_.m_104500_(p_104451_, p_104452_)) {
         LevelChunk levelchunk = this.f_104410_.m_104479_(this.f_104410_.m_104481_(p_104451_, p_104452_));
         if (m_104438_(levelchunk, p_104451_, p_104452_)) {
            return levelchunk;
         }
      }

      return p_104454_ ? this.f_104408_ : null;
   }

   public BlockGetter m_7653_() {
      return this.f_104411_;
   }

   @Nullable
   public LevelChunk m_171615_(int p_171616_, int p_171617_, ChunkBiomeContainer p_171618_, FriendlyByteBuf p_171619_, CompoundTag p_171620_, BitSet p_171621_) {
      if (!this.f_104410_.m_104500_(p_171616_, p_171617_)) {
         f_104407_.warn("Ignoring chunk since it's not in the view range: {}, {}", p_171616_, p_171617_);
         return null;
      } else {
         int i = this.f_104410_.m_104481_(p_171616_, p_171617_);
         LevelChunk levelchunk = this.f_104410_.f_104466_.get(i);
         ChunkPos chunkpos = new ChunkPos(p_171616_, p_171617_);
         if (!m_104438_(levelchunk, p_171616_, p_171617_)) {
            levelchunk = new LevelChunk(this.f_104411_, chunkpos, p_171618_);
            levelchunk.m_156383_(p_171618_, p_171619_, p_171620_, p_171621_);
            this.f_104410_.m_104484_(i, levelchunk);
         } else {
            levelchunk.m_156383_(p_171618_, p_171619_, p_171620_, p_171621_);
         }

         LevelChunkSection[] alevelchunksection = levelchunk.m_7103_();
         LevelLightEngine levellightengine = this.m_7827_();
         levellightengine.m_141940_(chunkpos, true);

         for(int j = 0; j < alevelchunksection.length; ++j) {
            LevelChunkSection levelchunksection = alevelchunksection[j];
            int k = this.f_104411_.m_151568_(j);
            levellightengine.m_6191_(SectionPos.m_123173_(p_171616_, k, p_171617_), LevelChunkSection.m_63000_(levelchunksection));
         }

         this.f_104411_.m_171649_(chunkpos);
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkEvent.Load(levelchunk));
         return levelchunk;
      }
   }

   public void m_142483_(BooleanSupplier p_104447_) {
   }

   public void m_104459_(int p_104460_, int p_104461_) {
      this.f_104410_.f_104469_ = p_104460_;
      this.f_104410_.f_104470_ = p_104461_;
   }

   public void m_104416_(int p_104417_) {
      int i = this.f_104410_.f_104467_;
      int j = m_104448_(p_104417_);
      if (i != j) {
         ClientChunkCache.Storage clientchunkcache$storage = new ClientChunkCache.Storage(j);
         clientchunkcache$storage.f_104469_ = this.f_104410_.f_104469_;
         clientchunkcache$storage.f_104470_ = this.f_104410_.f_104470_;

         for(int k = 0; k < this.f_104410_.f_104466_.length(); ++k) {
            LevelChunk levelchunk = this.f_104410_.f_104466_.get(k);
            if (levelchunk != null) {
               ChunkPos chunkpos = levelchunk.m_7697_();
               if (clientchunkcache$storage.m_104500_(chunkpos.f_45578_, chunkpos.f_45579_)) {
                  clientchunkcache$storage.m_104484_(clientchunkcache$storage.m_104481_(chunkpos.f_45578_, chunkpos.f_45579_), levelchunk);
               }
            }
         }

         this.f_104410_ = clientchunkcache$storage;
      }

   }

   private static int m_104448_(int p_104449_) {
      return Math.max(2, p_104449_) + 3;
   }

   public String m_6754_() {
      return this.f_104410_.f_104466_.length() + ", " + this.m_142061_();
   }

   public int m_142061_() {
      return this.f_104410_.f_104471_;
   }

   public void m_6506_(LightLayer p_104436_, SectionPos p_104437_) {
      Minecraft.m_91087_().f_91060_.m_109770_(p_104437_.m_123170_(), p_104437_.m_123206_(), p_104437_.m_123222_());
   }

   @OnlyIn(Dist.CLIENT)
   final class Storage {
      final AtomicReferenceArray<LevelChunk> f_104466_;
      final int f_104467_;
      private final int f_104468_;
      volatile int f_104469_;
      volatile int f_104470_;
      int f_104471_;

      Storage(int p_104474_) {
         this.f_104467_ = p_104474_;
         this.f_104468_ = p_104474_ * 2 + 1;
         this.f_104466_ = new AtomicReferenceArray<>(this.f_104468_ * this.f_104468_);
      }

      int m_104481_(int p_104482_, int p_104483_) {
         return Math.floorMod(p_104483_, this.f_104468_) * this.f_104468_ + Math.floorMod(p_104482_, this.f_104468_);
      }

      protected void m_104484_(int p_104485_, @Nullable LevelChunk p_104486_) {
         LevelChunk levelchunk = this.f_104466_.getAndSet(p_104485_, p_104486_);
         if (levelchunk != null) {
            --this.f_104471_;
            ClientChunkCache.this.f_104411_.m_104665_(levelchunk);
         }

         if (p_104486_ != null) {
            ++this.f_104471_;
         }

      }

      protected LevelChunk m_104487_(int p_104488_, LevelChunk p_104489_, @Nullable LevelChunk p_104490_) {
         if (this.f_104466_.compareAndSet(p_104488_, p_104489_, p_104490_) && p_104490_ == null) {
            --this.f_104471_;
         }

         ClientChunkCache.this.f_104411_.m_104665_(p_104489_);
         return p_104489_;
      }

      boolean m_104500_(int p_104501_, int p_104502_) {
         return Math.abs(p_104501_ - this.f_104469_) <= this.f_104467_ && Math.abs(p_104502_ - this.f_104470_) <= this.f_104467_;
      }

      @Nullable
      protected LevelChunk m_104479_(int p_104480_) {
         return this.f_104466_.get(p_104480_);
      }

      private void m_171622_(String p_171623_) {
         try {
            FileOutputStream fileoutputstream = new FileOutputStream(new File(p_171623_));

            try {
               int i = ClientChunkCache.this.f_104410_.f_104467_;

               for(int j = this.f_104470_ - i; j <= this.f_104470_ + i; ++j) {
                  for(int k = this.f_104469_ - i; k <= this.f_104469_ + i; ++k) {
                     LevelChunk levelchunk = ClientChunkCache.this.f_104410_.f_104466_.get(ClientChunkCache.this.f_104410_.m_104481_(k, j));
                     if (levelchunk != null) {
                        ChunkPos chunkpos = levelchunk.m_7697_();
                        fileoutputstream.write((chunkpos.f_45578_ + "\t" + chunkpos.f_45579_ + "\t" + levelchunk.m_6430_() + "\n").getBytes(StandardCharsets.UTF_8));
                     }
                  }
               }
            } catch (Throwable throwable1) {
               try {
                  fileoutputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }

               throw throwable1;
            }

            fileoutputstream.close();
         } catch (IOException ioexception) {
            ClientChunkCache.f_104407_.error(ioexception);
         }

      }
   }
}
