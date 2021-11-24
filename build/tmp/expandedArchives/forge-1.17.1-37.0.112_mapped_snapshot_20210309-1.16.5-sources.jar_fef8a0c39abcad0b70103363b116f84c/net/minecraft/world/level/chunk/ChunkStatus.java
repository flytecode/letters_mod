package net.minecraft.world.level.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class ChunkStatus extends net.minecraftforge.registries.ForgeRegistryEntry<ChunkStatus> {
   private static final EnumSet<Heightmap.Types> f_62327_ = EnumSet.of(Heightmap.Types.OCEAN_FLOOR_WG, Heightmap.Types.WORLD_SURFACE_WG);
   private static final EnumSet<Heightmap.Types> f_62328_ = EnumSet.of(Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.WORLD_SURFACE, Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES);
   private static final ChunkStatus.LoadingTask f_62329_ = (p_62461_, p_62462_, p_62463_, p_62464_, p_62465_, p_62466_) -> {
      if (p_62466_ instanceof ProtoChunk && !p_62466_.m_6415_().m_62427_(p_62461_)) {
         ((ProtoChunk)p_62466_).m_7150_(p_62461_);
      }

      return CompletableFuture.completedFuture(Either.left(p_62466_));
   };
   public static final ChunkStatus f_62314_ = m_62414_("empty", (ChunkStatus)null, -1, f_62327_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156307_, p_156308_, p_156309_, p_156310_, p_156311_) -> {
   });
   public static final ChunkStatus f_62315_ = m_62399_("structure_starts", f_62314_, 0, f_62327_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156285_, p_156286_, p_156287_, p_156288_, p_156289_, p_156290_, p_156291_, p_156292_, p_156293_) -> {
      if (!p_156293_.m_6415_().m_62427_(p_156285_)) {
         if (p_156287_.m_142572_().m_129910_().m_5961_().m_64657_()) {
            p_156288_.m_62199_(p_156287_.m_5962_(), p_156287_.m_8595_(), p_156293_, p_156289_, p_156287_.m_7328_());
         }

         if (p_156293_ instanceof ProtoChunk) {
            ((ProtoChunk)p_156293_).m_7150_(p_156285_);
         }
      }

      return CompletableFuture.completedFuture(Either.left(p_156293_));
   });
   public static final ChunkStatus f_62316_ = m_62414_("structure_references", f_62315_, 8, f_62327_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156301_, p_156302_, p_156303_, p_156304_, p_156305_) -> {
      WorldGenRegion worldgenregion = new WorldGenRegion(p_156302_, p_156304_, p_156301_, -1);
      p_156303_.m_62177_(worldgenregion, p_156302_.m_8595_().m_47272_(worldgenregion), p_156305_);
   });
   public static final ChunkStatus f_62317_ = m_62414_("biomes", f_62316_, 0, f_62327_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156295_, p_156296_, p_156297_, p_156298_, p_156299_) -> {
      p_156297_.m_62196_(p_156296_.m_5962_().m_175515_(Registry.f_122885_), p_156299_);
   });
   public static final ChunkStatus f_62318_ = m_62399_("noise", f_62317_, 8, f_62327_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156269_, p_156270_, p_156271_, p_156272_, p_156273_, p_156274_, p_156275_, p_156276_, p_156277_) -> {
      if (!p_156277_.m_6415_().m_62427_(p_156269_)) {
         WorldGenRegion worldgenregion = new WorldGenRegion(p_156271_, p_156276_, p_156269_, 0);
         return p_156272_.m_142189_(p_156270_, p_156271_.m_8595_().m_47272_(worldgenregion), p_156277_).thenApply((p_156235_) -> {
            if (p_156235_ instanceof ProtoChunk) {
               ((ProtoChunk)p_156235_).m_7150_(p_156269_);
            }

            return Either.left(p_156235_);
         });
      } else {
         return CompletableFuture.completedFuture(Either.left(p_156277_));
      }
   });
   public static final ChunkStatus f_62319_ = m_62414_("surface", f_62318_, 0, f_62327_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156279_, p_156280_, p_156281_, p_156282_, p_156283_) -> {
      p_156281_.m_7338_(new WorldGenRegion(p_156280_, p_156282_, p_156279_, 0), p_156283_);
   });
   public static final ChunkStatus f_62320_ = m_62414_("carvers", f_62319_, 0, f_62327_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156263_, p_156264_, p_156265_, p_156266_, p_156267_) -> {
      p_156265_.m_6013_(p_156264_.m_7328_(), p_156264_.m_7062_(), p_156267_, GenerationStep.Carving.AIR);
   });
   public static final ChunkStatus f_62321_ = m_62414_("liquid_carvers", f_62320_, 0, f_62328_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156247_, p_156248_, p_156249_, p_156250_, p_156251_) -> {
      p_156249_.m_6013_(p_156248_.m_7328_(), p_156248_.m_7062_(), p_156251_, GenerationStep.Carving.LIQUID);
   });
   public static final ChunkStatus f_62322_ = m_62399_("features", f_62321_, 8, f_62328_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156253_, p_156254_, p_156255_, p_156256_, p_156257_, p_156258_, p_156259_, p_156260_, p_156261_) -> {
      ProtoChunk protochunk = (ProtoChunk)p_156261_;
      protochunk.m_63209_(p_156258_);
      if (!p_156261_.m_6415_().m_62427_(p_156253_)) {
         Heightmap.m_64256_(p_156261_, EnumSet.of(Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.WORLD_SURFACE));
         WorldGenRegion worldgenregion = new WorldGenRegion(p_156255_, p_156260_, p_156253_, 1);
         p_156256_.m_7399_(worldgenregion, p_156255_.m_8595_().m_47272_(worldgenregion));
         protochunk.m_7150_(p_156253_);
      }

      return CompletableFuture.completedFuture(Either.left(p_156261_));
   });
   public static final ChunkStatus f_62323_ = m_62406_("light", f_62322_, 1, f_62328_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156237_, p_156238_, p_156239_, p_156240_, p_156241_, p_156242_, p_156243_, p_156244_, p_156245_) -> {
      return m_62388_(p_156237_, p_156242_, p_156245_);
   }, (p_156227_, p_156228_, p_156229_, p_156230_, p_156231_, p_156232_) -> {
      return m_62388_(p_156227_, p_156230_, p_156232_);
   });
   public static final ChunkStatus f_62324_ = m_62414_("spawn", f_62323_, 0, f_62328_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156221_, p_156222_, p_156223_, p_156224_, p_156225_) -> {
      p_156223_.m_6929_(new WorldGenRegion(p_156222_, p_156224_, p_156221_, -1));
   });
   public static final ChunkStatus f_62325_ = m_62414_("heightmaps", f_62324_, 0, f_62328_, ChunkStatus.ChunkType.PROTOCHUNK, (p_156188_, p_156189_, p_156190_, p_156191_, p_156192_) -> {
   });
   public static final ChunkStatus f_62326_ = m_62406_("full", f_62325_, 0, f_62328_, ChunkStatus.ChunkType.LEVELCHUNK, (p_156201_, p_156202_, p_156203_, p_156204_, p_156205_, p_156206_, p_156207_, p_156208_, p_156209_) -> {
      return p_156207_.apply(p_156209_);
   }, (p_156194_, p_156195_, p_156196_, p_156197_, p_156198_, p_156199_) -> {
      return p_156198_.apply(p_156199_);
   });
   private static final List<ChunkStatus> f_62330_ = ImmutableList.of(f_62326_, f_62322_, f_62321_, f_62315_, f_62315_, f_62315_, f_62315_, f_62315_, f_62315_, f_62315_, f_62315_);
   private static final IntList f_62331_ = Util.m_137469_(new IntArrayList(m_62349_().size()), (p_156211_) -> {
      int i = 0;

      for(int j = m_62349_().size() - 1; j >= 0; --j) {
         while(i + 1 < f_62330_.size() && j <= f_62330_.get(i + 1).m_62445_()) {
            ++i;
         }

         p_156211_.add(0, i);
      }

   });
   private final String f_62332_;
   private final int f_62333_;
   private final ChunkStatus f_62334_;
   private final ChunkStatus.GenerationTask f_62335_;
   private final ChunkStatus.LoadingTask f_62336_;
   private final int f_62337_;
   private final ChunkStatus.ChunkType f_62338_;
   private final EnumSet<Heightmap.Types> f_62339_;

   private static CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_62388_(ChunkStatus p_62389_, ThreadedLevelLightEngine p_62390_, ChunkAccess p_62391_) {
      boolean flag = m_62392_(p_62389_, p_62391_);
      if (!p_62391_.m_6415_().m_62427_(p_62389_)) {
         ((ProtoChunk)p_62391_).m_7150_(p_62389_);
      }

      return p_62390_.m_9353_(p_62391_, flag).thenApply(Either::left);
   }

   private static ChunkStatus m_62414_(String p_62415_, @Nullable ChunkStatus p_62416_, int p_62417_, EnumSet<Heightmap.Types> p_62418_, ChunkStatus.ChunkType p_62419_, ChunkStatus.SimpleGenerationTask p_62420_) {
      return m_62399_(p_62415_, p_62416_, p_62417_, p_62418_, p_62419_, p_62420_);
   }

   private static ChunkStatus m_62399_(String p_62400_, @Nullable ChunkStatus p_62401_, int p_62402_, EnumSet<Heightmap.Types> p_62403_, ChunkStatus.ChunkType p_62404_, ChunkStatus.GenerationTask p_62405_) {
      return m_62406_(p_62400_, p_62401_, p_62402_, p_62403_, p_62404_, p_62405_, f_62329_);
   }

   private static ChunkStatus m_62406_(String p_62407_, @Nullable ChunkStatus p_62408_, int p_62409_, EnumSet<Heightmap.Types> p_62410_, ChunkStatus.ChunkType p_62411_, ChunkStatus.GenerationTask p_62412_, ChunkStatus.LoadingTask p_62413_) {
      return Registry.m_122961_(Registry.f_122833_, p_62407_, new ChunkStatus(p_62407_, p_62408_, p_62409_, p_62410_, p_62411_, p_62412_, p_62413_));
   }

   public static List<ChunkStatus> m_62349_() {
      List<ChunkStatus> list = Lists.newArrayList();

      ChunkStatus chunkstatus;
      for(chunkstatus = f_62326_; chunkstatus.m_62482_() != chunkstatus; chunkstatus = chunkstatus.m_62482_()) {
         list.add(chunkstatus);
      }

      list.add(chunkstatus);
      Collections.reverse(list);
      return list;
   }

   private static boolean m_62392_(ChunkStatus p_62393_, ChunkAccess p_62394_) {
      return p_62394_.m_6415_().m_62427_(p_62393_) && p_62394_.m_6332_();
   }

   public static ChunkStatus m_156185_(int p_156186_) {
      if (p_156186_ >= f_62330_.size()) {
         return f_62314_;
      } else {
         return p_156186_ < 0 ? f_62326_ : f_62330_.get(p_156186_);
      }
   }

   public static int m_62421_() {
      return f_62330_.size();
   }

   public static int m_62370_(ChunkStatus p_62371_) {
      return f_62331_.getInt(p_62371_.m_62445_());
   }

   public ChunkStatus(String p_62342_, @Nullable ChunkStatus p_62343_, int p_62344_, EnumSet<Heightmap.Types> p_62345_, ChunkStatus.ChunkType p_62346_, ChunkStatus.GenerationTask p_62347_, ChunkStatus.LoadingTask p_62348_) {
      this.f_62332_ = p_62342_;
      this.f_62334_ = p_62343_ == null ? this : p_62343_;
      this.f_62335_ = p_62347_;
      this.f_62336_ = p_62348_;
      this.f_62337_ = p_62344_;
      this.f_62338_ = p_62346_;
      this.f_62339_ = p_62345_;
      this.f_62333_ = p_62343_ == null ? 0 : p_62343_.m_62445_() + 1;
   }

   public int m_62445_() {
      return this.f_62333_;
   }

   public String m_62467_() {
      return this.f_62332_;
   }

   public ChunkStatus m_62482_() {
      return this.f_62334_;
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_156212_(Executor p_156213_, ServerLevel p_156214_, ChunkGenerator p_156215_, StructureManager p_156216_, ThreadedLevelLightEngine p_156217_, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> p_156218_, List<ChunkAccess> p_156219_) {
      return this.f_62335_.m_142357_(this, p_156213_, p_156214_, p_156215_, p_156216_, p_156217_, p_156218_, p_156219_, p_156219_.get(p_156219_.size() / 2));
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_62364_(ServerLevel p_62365_, StructureManager p_62366_, ThreadedLevelLightEngine p_62367_, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> p_62368_, ChunkAccess p_62369_) {
      return this.f_62336_.m_62530_(this, p_62365_, p_62366_, p_62367_, p_62368_, p_62369_);
   }

   public int m_62488_() {
      return this.f_62337_;
   }

   public ChunkStatus.ChunkType m_62494_() {
      return this.f_62338_;
   }

   public static ChunkStatus m_62397_(String p_62398_) {
      return Registry.f_122833_.m_7745_(ResourceLocation.m_135820_(p_62398_));
   }

   public EnumSet<Heightmap.Types> m_62500_() {
      return this.f_62339_;
   }

   public boolean m_62427_(ChunkStatus p_62428_) {
      return this.m_62445_() >= p_62428_.m_62445_();
   }

   public String toString() {
      return Registry.f_122833_.m_7981_(this).toString();
   }

   public static enum ChunkType {
      PROTOCHUNK,
      LEVELCHUNK;
   }

   interface GenerationTask {
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_142357_(ChunkStatus p_156313_, Executor p_156314_, ServerLevel p_156315_, ChunkGenerator p_156316_, StructureManager p_156317_, ThreadedLevelLightEngine p_156318_, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> p_156319_, List<ChunkAccess> p_156320_, ChunkAccess p_156321_);
   }

   interface LoadingTask {
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_62530_(ChunkStatus p_62531_, ServerLevel p_62532_, StructureManager p_62533_, ThreadedLevelLightEngine p_62534_, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> p_62535_, ChunkAccess p_62536_);
   }

   interface SimpleGenerationTask extends ChunkStatus.GenerationTask {
      default CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_142357_(ChunkStatus p_156329_, Executor p_156330_, ServerLevel p_156331_, ChunkGenerator p_156332_, StructureManager p_156333_, ThreadedLevelLightEngine p_156334_, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> p_156335_, List<ChunkAccess> p_156336_, ChunkAccess p_156337_) {
         if (!p_156337_.m_6415_().m_62427_(p_156329_)) {
            this.m_156322_(p_156329_, p_156331_, p_156332_, p_156336_, p_156337_);
            if (p_156337_ instanceof ProtoChunk) {
               ((ProtoChunk)p_156337_).m_7150_(p_156329_);
            }
         }

         return CompletableFuture.completedFuture(Either.left(p_156337_));
      }

      void m_156322_(ChunkStatus p_156323_, ServerLevel p_156324_, ChunkGenerator p_156325_, List<ChunkAccess> p_156326_, ChunkAccess p_156327_);
   }
}
