package net.minecraft.server.level;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.shorts.ShortArraySet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.util.DebugBuffer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;

public class ChunkHolder {
   public static final Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> f_139995_ = Either.right(ChunkHolder.ChunkLoadingFailure.f_140101_);
   public static final CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> f_139996_ = CompletableFuture.completedFuture(f_139995_);
   public static final Either<LevelChunk, ChunkHolder.ChunkLoadingFailure> f_139997_ = Either.right(ChunkHolder.ChunkLoadingFailure.f_140101_);
   private static final CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> f_139998_ = CompletableFuture.completedFuture(f_139997_);
   private static final List<ChunkStatus> f_139999_ = ChunkStatus.m_62349_();
   private static final ChunkHolder.FullChunkStatus[] f_140000_ = ChunkHolder.FullChunkStatus.values();
   private static final int f_142982_ = 64;
   private final AtomicReferenceArray<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> f_140001_ = new AtomicReferenceArray<>(f_139999_.size());
   private final LevelHeightAccessor f_142983_;
   private volatile CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> f_140002_ = f_139998_;
   private volatile CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> f_140003_ = f_139998_;
   private volatile CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> f_140004_ = f_139998_;
   private CompletableFuture<ChunkAccess> f_140005_ = CompletableFuture.completedFuture((ChunkAccess)null);
   @Nullable
   private final DebugBuffer<ChunkHolder.ChunkSaveDebug> f_142984_ = null;
   private int f_140006_;
   private int f_140007_;
   private int f_140008_;
   final ChunkPos f_140009_;
   private boolean f_140010_;
   private final ShortSet[] f_140011_;
   private final BitSet f_140012_ = new BitSet();
   private final BitSet f_140013_ = new BitSet();
   private final LevelLightEngine f_140014_;
   private final ChunkHolder.LevelChangeListener f_140015_;
   private final ChunkHolder.PlayerProvider f_140016_;
   private boolean f_140017_;
   private boolean f_140018_;
   LevelChunk currentlyLoading; // Forge: Used to bypass future chain when loading chunks.
   private CompletableFuture<Void> f_142981_ = CompletableFuture.completedFuture((Void)null);

   public ChunkHolder(ChunkPos p_142986_, int p_142987_, LevelHeightAccessor p_142988_, LevelLightEngine p_142989_, ChunkHolder.LevelChangeListener p_142990_, ChunkHolder.PlayerProvider p_142991_) {
      this.f_140009_ = p_142986_;
      this.f_142983_ = p_142988_;
      this.f_140014_ = p_142989_;
      this.f_140015_ = p_142990_;
      this.f_140016_ = p_142991_;
      this.f_140006_ = ChunkMap.f_140127_ + 1;
      this.f_140007_ = this.f_140006_;
      this.f_140008_ = this.f_140006_;
      this.m_140027_(p_142987_);
      this.f_140011_ = new ShortSet[p_142988_.m_151559_()];
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140047_(ChunkStatus p_140048_) {
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture = this.f_140001_.get(p_140048_.m_62445_());
      return completablefuture == null ? f_139996_ : completablefuture;
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140080_(ChunkStatus p_140081_) {
      return m_140074_(this.f_140007_).m_62427_(p_140081_) ? this.m_140047_(p_140081_) : f_139996_;
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> m_140026_() {
      return this.f_140003_;
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> m_140073_() {
      return this.f_140004_;
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> m_140082_() {
      return this.f_140002_;
   }

   @Nullable
   public LevelChunk m_140085_() {
      CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> completablefuture = this.m_140026_();
      Either<LevelChunk, ChunkHolder.ChunkLoadingFailure> either = completablefuture.getNow((Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>)null);
      return either == null ? null : either.left().orElse((LevelChunk)null);
   }

   @Nullable
   public ChunkStatus m_140088_() {
      for(int i = f_139999_.size() - 1; i >= 0; --i) {
         ChunkStatus chunkstatus = f_139999_.get(i);
         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture = this.m_140047_(chunkstatus);
         if (completablefuture.getNow(f_139995_).left().isPresent()) {
            return chunkstatus;
         }
      }

      return null;
   }

   @Nullable
   public ChunkAccess m_140089_() {
      for(int i = f_139999_.size() - 1; i >= 0; --i) {
         ChunkStatus chunkstatus = f_139999_.get(i);
         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture = this.m_140047_(chunkstatus);
         if (!completablefuture.isCompletedExceptionally()) {
            Optional<ChunkAccess> optional = completablefuture.getNow(f_139995_).left();
            if (optional.isPresent()) {
               return optional.get();
            }
         }
      }

      return null;
   }

   public CompletableFuture<ChunkAccess> m_140090_() {
      return this.f_140005_;
   }

   public void m_140056_(BlockPos p_140057_) {
      LevelChunk levelchunk = this.m_140085_();
      if (levelchunk != null) {
         int i = this.f_142983_.m_151564_(p_140057_.m_123342_());
         if (this.f_140011_[i] == null) {
            this.f_140010_ = true;
            this.f_140011_[i] = new ShortArraySet();
         }

         this.f_140011_[i].add(SectionPos.m_123218_(p_140057_));
      }
   }

   public void m_140036_(LightLayer p_140037_, int p_140038_) {
      LevelChunk levelchunk = this.m_140085_();
      if (levelchunk != null) {
         levelchunk.m_8092_(true);
         int i = this.f_140014_.m_164447_();
         int j = this.f_140014_.m_164448_();
         if (p_140038_ >= i && p_140038_ <= j) {
            int k = p_140038_ - i;
            if (p_140037_ == LightLayer.SKY) {
               this.f_140013_.set(k);
            } else {
               this.f_140012_.set(k);
            }

         }
      }
   }

   public void m_140054_(LevelChunk p_140055_) {
      if (this.f_140010_ || !this.f_140013_.isEmpty() || !this.f_140012_.isEmpty()) {
         Level level = p_140055_.m_62953_();
         int i = 0;

         for(int j = 0; j < this.f_140011_.length; ++j) {
            i += this.f_140011_[j] != null ? this.f_140011_[j].size() : 0;
         }

         this.f_140018_ |= i >= 64;
         if (!this.f_140013_.isEmpty() || !this.f_140012_.isEmpty()) {
            this.m_140063_(new ClientboundLightUpdatePacket(p_140055_.m_7697_(), this.f_140014_, this.f_140013_, this.f_140012_, true), !this.f_140018_);
            this.f_140013_.clear();
            this.f_140012_.clear();
         }

         for(int l = 0; l < this.f_140011_.length; ++l) {
            ShortSet shortset = this.f_140011_[l];
            if (shortset != null) {
               int k = this.f_142983_.m_151568_(l);
               SectionPos sectionpos = SectionPos.m_123196_(p_140055_.m_7697_(), k);
               if (shortset.size() == 1) {
                  BlockPos blockpos = sectionpos.m_123245_(shortset.iterator().nextShort());
                  BlockState blockstate = level.m_8055_(blockpos);
                  this.m_140063_(new ClientboundBlockUpdatePacket(blockpos, blockstate), false);
                  this.m_140032_(level, blockpos, blockstate);
               } else {
                  LevelChunkSection levelchunksection = p_140055_.m_7103_()[l];
                  ClientboundSectionBlocksUpdatePacket clientboundsectionblocksupdatepacket = new ClientboundSectionBlocksUpdatePacket(sectionpos, shortset, levelchunksection, this.f_140018_);
                  this.m_140063_(clientboundsectionblocksupdatepacket, false);
                  clientboundsectionblocksupdatepacket.m_132992_((p_140078_, p_140079_) -> {
                     this.m_140032_(level, p_140078_, p_140079_);
                  });
               }

               this.f_140011_[l] = null;
            }
         }

         this.f_140010_ = false;
      }
   }

   private void m_140032_(Level p_140033_, BlockPos p_140034_, BlockState p_140035_) {
      if (p_140035_.m_155947_()) {
         this.m_140029_(p_140033_, p_140034_);
      }

   }

   private void m_140029_(Level p_140030_, BlockPos p_140031_) {
      BlockEntity blockentity = p_140030_.m_7702_(p_140031_);
      if (blockentity != null) {
         ClientboundBlockEntityDataPacket clientboundblockentitydatapacket = blockentity.m_7033_();
         if (clientboundblockentitydatapacket != null) {
            this.m_140063_(clientboundblockentitydatapacket, false);
         }
      }

   }

   private void m_140063_(Packet<?> p_140064_, boolean p_140065_) {
      this.f_140016_.m_5960_(this.f_140009_, p_140065_).forEach((p_140062_) -> {
         p_140062_.f_8906_.m_141995_(p_140064_);
      });
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140049_(ChunkStatus p_140050_, ChunkMap p_140051_) {
      int i = p_140050_.m_62445_();
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture = this.f_140001_.get(i);
      if (completablefuture != null) {
         Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> either = completablefuture.getNow((Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>)null);
         boolean flag = either != null && either.right().isPresent();
         if (!flag) {
            return completablefuture;
         }
      }

      if (m_140074_(this.f_140007_).m_62427_(p_140050_)) {
         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture1 = p_140051_.m_140292_(this, p_140050_);
         this.m_143017_(completablefuture1, "schedule " + p_140050_);
         this.f_140001_.set(i, completablefuture1);
         return completablefuture1;
      } else {
         return completablefuture == null ? f_139996_ : completablefuture;
      }
   }

   private void m_143017_(CompletableFuture<? extends Either<? extends ChunkAccess, ChunkHolder.ChunkLoadingFailure>> p_143018_, String p_143019_) {
      if (this.f_142984_ != null) {
         this.f_142984_.m_144625_(new ChunkHolder.ChunkSaveDebug(Thread.currentThread(), p_143018_, p_143019_));
      }

      this.f_140005_ = this.f_140005_.thenCombine(p_143018_, (p_140042_, p_140043_) -> {
         return p_140043_.map((p_143007_) -> {
            return p_143007_;
         }, (p_143010_) -> {
            return p_140042_;
         });
      });
   }

   public ChunkHolder.FullChunkStatus m_140091_() {
      return m_140083_(this.f_140007_);
   }

   public ChunkPos m_140092_() {
      return this.f_140009_;
   }

   public int m_140093_() {
      return this.f_140007_;
   }

   public int m_140094_() {
      return this.f_140008_;
   }

   private void m_140086_(int p_140087_) {
      this.f_140008_ = p_140087_;
   }

   public void m_140027_(int p_140028_) {
      this.f_140007_ = p_140028_;
   }

   private void m_142998_(ChunkMap p_142999_, CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> p_143000_, Executor p_143001_, ChunkHolder.FullChunkStatus p_143002_) {
      this.f_142981_.cancel(false);
      CompletableFuture<Void> completablefuture = new CompletableFuture<>();
      completablefuture.thenRunAsync(() -> {
         p_142999_.m_143075_(this.f_140009_, p_143002_);
      }, p_143001_);
      this.f_142981_ = completablefuture;
      p_143000_.thenAccept((p_143016_) -> {
         p_143016_.ifLeft((p_143013_) -> {
            completablefuture.complete((Void)null);
         });
      });
   }

   private void m_142992_(ChunkMap p_142993_, ChunkHolder.FullChunkStatus p_142994_) {
      this.f_142981_.cancel(false);
      p_142993_.m_143075_(this.f_140009_, p_142994_);
   }

   protected void m_143003_(ChunkMap p_143004_, Executor p_143005_) {
      ChunkStatus chunkstatus = m_140074_(this.f_140006_);
      ChunkStatus chunkstatus1 = m_140074_(this.f_140007_);
      boolean flag = this.f_140006_ <= ChunkMap.f_140127_;
      boolean flag1 = this.f_140007_ <= ChunkMap.f_140127_;
      ChunkHolder.FullChunkStatus chunkholder$fullchunkstatus = m_140083_(this.f_140006_);
      ChunkHolder.FullChunkStatus chunkholder$fullchunkstatus1 = m_140083_(this.f_140007_);
      if (flag) {
         Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> either = Either.right(new ChunkHolder.ChunkLoadingFailure() {
            public String toString() {
               return "Unloaded ticket level " + ChunkHolder.this.f_140009_;
            }
         });

         for(int i = flag1 ? chunkstatus1.m_62445_() + 1 : 0; i <= chunkstatus.m_62445_(); ++i) {
            CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture = this.f_140001_.get(i);
            if (completablefuture == null) {
               this.f_140001_.set(i, CompletableFuture.completedFuture(either));
            }
         }
      }

      boolean flag5 = chunkholder$fullchunkstatus.m_140114_(ChunkHolder.FullChunkStatus.BORDER);
      boolean flag6 = chunkholder$fullchunkstatus1.m_140114_(ChunkHolder.FullChunkStatus.BORDER);
      this.f_140017_ |= flag6;
      if (!flag5 && flag6) {
         this.f_140002_ = p_143004_.m_143109_(this);
         this.m_142998_(p_143004_, this.f_140002_, p_143005_, ChunkHolder.FullChunkStatus.BORDER);
         this.m_143017_(this.f_140002_, "full");
      }

      if (flag5 && !flag6) {
         CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> completablefuture1 = this.f_140002_;
         this.f_140002_ = f_139998_;
         this.m_143017_(completablefuture1.thenApply((p_142997_) -> {
            return p_142997_.ifLeft(p_143004_::m_140270_);
         }), "unfull");
      }

      boolean flag7 = chunkholder$fullchunkstatus.m_140114_(ChunkHolder.FullChunkStatus.TICKING);
      boolean flag2 = chunkholder$fullchunkstatus1.m_140114_(ChunkHolder.FullChunkStatus.TICKING);
      if (!flag7 && flag2) {
         this.f_140003_ = p_143004_.m_143053_(this);
         this.m_142998_(p_143004_, this.f_140003_, p_143005_, ChunkHolder.FullChunkStatus.TICKING);
         this.m_143017_(this.f_140003_, "ticking");
      }

      if (flag7 && !flag2) {
         this.f_140003_.complete(f_139997_);
         this.f_140003_ = f_139998_;
      }

      boolean flag3 = chunkholder$fullchunkstatus.m_140114_(ChunkHolder.FullChunkStatus.ENTITY_TICKING);
      boolean flag4 = chunkholder$fullchunkstatus1.m_140114_(ChunkHolder.FullChunkStatus.ENTITY_TICKING);
      if (!flag3 && flag4) {
         if (this.f_140004_ != f_139998_) {
            throw (IllegalStateException)Util.m_137570_(new IllegalStateException());
         }

         this.f_140004_ = p_143004_.m_143117_(this.f_140009_);
         this.m_142998_(p_143004_, this.f_140004_, p_143005_, ChunkHolder.FullChunkStatus.ENTITY_TICKING);
         this.m_143017_(this.f_140004_, "entity ticking");
      }

      if (flag3 && !flag4) {
         this.f_140004_.complete(f_139997_);
         this.f_140004_ = f_139998_;
      }

      if (!chunkholder$fullchunkstatus1.m_140114_(chunkholder$fullchunkstatus)) {
         this.m_142992_(p_143004_, chunkholder$fullchunkstatus1);
      }

      this.f_140015_.m_6250_(this.f_140009_, this::m_140094_, this.f_140007_, this::m_140086_);
      this.f_140006_ = this.f_140007_;
   }

   public static ChunkStatus m_140074_(int p_140075_) {
      return p_140075_ < 33 ? ChunkStatus.f_62326_ : ChunkStatus.m_156185_(p_140075_ - 33);
   }

   public static ChunkHolder.FullChunkStatus m_140083_(int p_140084_) {
      return f_140000_[Mth.m_14045_(33 - p_140084_ + 1, 0, f_140000_.length - 1)];
   }

   public boolean m_140095_() {
      return this.f_140017_;
   }

   public void m_140096_() {
      this.f_140017_ = m_140083_(this.f_140007_).m_140114_(ChunkHolder.FullChunkStatus.BORDER);
   }

   public void m_140052_(ImposterProtoChunk p_140053_) {
      for(int i = 0; i < this.f_140001_.length(); ++i) {
         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture = this.f_140001_.get(i);
         if (completablefuture != null) {
            Optional<ChunkAccess> optional = completablefuture.getNow(f_139995_).left();
            if (optional.isPresent() && optional.get() instanceof ProtoChunk) {
               this.f_140001_.set(i, CompletableFuture.completedFuture(Either.left(p_140053_)));
            }
         }
      }

      this.m_143017_(CompletableFuture.completedFuture(Either.left(p_140053_.m_62768_())), "replaceProto");
   }

   public interface ChunkLoadingFailure {
      ChunkHolder.ChunkLoadingFailure f_140101_ = new ChunkHolder.ChunkLoadingFailure() {
         public String toString() {
            return "UNLOADED";
         }
      };
   }

   static final class ChunkSaveDebug {
      private final Thread f_143023_;
      private final CompletableFuture<? extends Either<? extends ChunkAccess, ChunkHolder.ChunkLoadingFailure>> f_143024_;
      private final String f_143025_;

      ChunkSaveDebug(Thread p_143027_, CompletableFuture<? extends Either<? extends ChunkAccess, ChunkHolder.ChunkLoadingFailure>> p_143028_, String p_143029_) {
         this.f_143023_ = p_143027_;
         this.f_143024_ = p_143028_;
         this.f_143025_ = p_143029_;
      }
   }

   public static enum FullChunkStatus {
      INACCESSIBLE,
      BORDER,
      TICKING,
      ENTITY_TICKING;

      public boolean m_140114_(ChunkHolder.FullChunkStatus p_140115_) {
         return this.ordinal() >= p_140115_.ordinal();
      }
   }

   @FunctionalInterface
   public interface LevelChangeListener {
      void m_6250_(ChunkPos p_140119_, IntSupplier p_140120_, int p_140121_, IntConsumer p_140122_);
   }

   public interface PlayerProvider {
      Stream<ServerPlayer> m_5960_(ChunkPos p_140123_, boolean p_140124_);
   }
}
