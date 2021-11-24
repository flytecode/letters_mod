package net.minecraft.world.level.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ChunkTickList;
import net.minecraft.world.level.EmptyTickList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.EuclideanGameEventDispatcher;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelChunk extends net.minecraftforge.common.capabilities.CapabilityProvider<LevelChunk> implements ChunkAccess, net.minecraftforge.common.extensions.IForgeLevelChunk {
   static final Logger f_62771_ = LogManager.getLogger();
   private static final TickingBlockEntity f_156361_ = new TickingBlockEntity() {
      public void m_142224_() {
      }

      public boolean m_142220_() {
         return true;
      }

      public BlockPos m_142689_() {
         return BlockPos.f_121853_;
      }

      public String m_142280_() {
         return "<null>";
      }
   };
   @Nullable
   public static final LevelChunkSection f_62770_ = null;
   private final LevelChunkSection[] f_62772_;
   private ChunkBiomeContainer f_62773_;
   private final Map<BlockPos, CompoundTag> f_62774_ = Maps.newHashMap();
   private final Map<BlockPos, LevelChunk.RebindableTickingBlockEntityWrapper> f_156362_ = Maps.newHashMap();
   private boolean f_62775_;
   final Level f_62776_;
   private final Map<Heightmap.Types, Heightmap> f_62777_ = Maps.newEnumMap(Heightmap.Types.class);
   private final UpgradeData f_62778_;
   private final Map<BlockPos, BlockEntity> f_62779_ = Maps.newHashMap();
   private final Map<StructureFeature<?>, StructureStart<?>> f_62781_ = Maps.newHashMap();
   private final Map<StructureFeature<?>, LongSet> f_62782_ = Maps.newHashMap();
   private final ShortList[] f_62783_;
   private TickList<Block> f_62784_;
   private TickList<Fluid> f_62785_;
   private volatile boolean f_62788_;
   private long f_62789_;
   @Nullable
   private Supplier<ChunkHolder.FullChunkStatus> f_62790_;
   @Nullable
   private Consumer<LevelChunk> f_62791_;
   private final ChunkPos f_62792_;
   private volatile boolean f_62793_;
   private final Int2ObjectMap<GameEventDispatcher> f_156363_;

   public LevelChunk(Level p_62796_, ChunkPos p_62797_, ChunkBiomeContainer p_62798_) {
      this(p_62796_, p_62797_, p_62798_, UpgradeData.f_63320_, EmptyTickList.m_45888_(), EmptyTickList.m_45888_(), 0L, (LevelChunkSection[])null, (Consumer<LevelChunk>)null);
   }

   public LevelChunk(Level p_62800_, ChunkPos p_62801_, ChunkBiomeContainer p_62802_, UpgradeData p_62803_, TickList<Block> p_62804_, TickList<Fluid> p_62805_, long p_62806_, @Nullable LevelChunkSection[] p_62807_, @Nullable Consumer<LevelChunk> p_62808_) {
      super(LevelChunk.class);
      this.f_62776_ = p_62800_;
      this.f_62792_ = p_62801_;
      this.f_62778_ = p_62803_;
      this.f_156363_ = new Int2ObjectOpenHashMap<>();

      for(Heightmap.Types heightmap$types : Heightmap.Types.values()) {
         if (ChunkStatus.f_62326_.m_62500_().contains(heightmap$types)) {
            this.f_62777_.put(heightmap$types, new Heightmap(this, heightmap$types));
         }
      }

      this.f_62773_ = p_62802_;
      this.f_62784_ = p_62804_;
      this.f_62785_ = p_62805_;
      this.f_62789_ = p_62806_;
      this.f_62791_ = p_62808_;
      this.f_62772_ = new LevelChunkSection[p_62800_.m_151559_()];
      if (p_62807_ != null) {
         if (this.f_62772_.length == p_62807_.length) {
            System.arraycopy(p_62807_, 0, this.f_62772_, 0, this.f_62772_.length);
         } else {
            f_62771_.warn("Could not set level chunk sections, array length is {} instead of {}", p_62807_.length, this.f_62772_.length);
         }
      }

      this.f_62783_ = new ShortList[p_62800_.m_151559_()];
      this.gatherCapabilities();
   }

   public LevelChunk(ServerLevel p_156365_, ProtoChunk p_156366_, @Nullable Consumer<LevelChunk> p_156367_) {
      this(p_156365_, p_156366_.m_7697_(), p_156366_.m_6221_(), p_156366_.m_7387_(), p_156366_.m_5782_(), p_156366_.m_5783_(), p_156366_.m_6319_(), p_156366_.m_7103_(), p_156367_);

      for(BlockEntity blockentity : p_156366_.m_63292_().values()) {
         this.m_142169_(blockentity);
      }

      this.f_62774_.putAll(p_156366_.m_63294_());

      for(int i = 0; i < p_156366_.m_6720_().length; ++i) {
         this.f_62783_[i] = p_156366_.m_6720_()[i];
      }

      this.m_8040_(p_156366_.m_6633_());
      this.m_7946_(p_156366_.m_7049_());

      for(Entry<Heightmap.Types, Heightmap> entry : p_156366_.m_6890_()) {
         if (ChunkStatus.f_62326_.m_62500_().contains(entry.getKey())) {
            this.m_6511_(entry.getKey(), entry.getValue().m_64239_());
         }
      }

      this.m_8094_(p_156366_.m_6332_());
      this.f_62788_ = true;
   }

   public GameEventDispatcher m_142336_(int p_156372_) {
      return this.f_156363_.computeIfAbsent(p_156372_, (p_156395_) -> {
         return new EuclideanGameEventDispatcher(this.f_62776_);
      });
   }

   public Heightmap m_6005_(Heightmap.Types p_62845_) {
      return this.f_62777_.computeIfAbsent(p_62845_, (p_62908_) -> {
         return new Heightmap(this, p_62908_);
      });
   }

   public Set<BlockPos> m_5928_() {
      Set<BlockPos> set = Sets.newHashSet(this.f_62774_.keySet());
      set.addAll(this.f_62779_.keySet());
      return set;
   }

   public LevelChunkSection[] m_7103_() {
      return this.f_62772_;
   }

   public BlockState m_8055_(BlockPos p_62923_) {
      int i = p_62923_.m_123341_();
      int j = p_62923_.m_123342_();
      int k = p_62923_.m_123343_();
      if (this.f_62776_.m_46659_()) {
         BlockState blockstate = null;
         if (j == 60) {
            blockstate = Blocks.f_50375_.m_49966_();
         }

         if (j == 70) {
            blockstate = DebugLevelSource.m_64148_(i, k);
         }

         return blockstate == null ? Blocks.f_50016_.m_49966_() : blockstate;
      } else {
         try {
            int l = this.m_151564_(j);
            if (l >= 0 && l < this.f_62772_.length) {
               LevelChunkSection levelchunksection = this.f_62772_[l];
               if (!LevelChunkSection.m_63000_(levelchunksection)) {
                  return levelchunksection.m_62982_(i & 15, j & 15, k & 15);
               }
            }

            return Blocks.f_50016_.m_49966_();
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.m_127521_(throwable, "Getting block state");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being got");
            crashreportcategory.m_128165_("Location", () -> {
               return CrashReportCategory.m_178942_(this, i, j, k);
            });
            throw new ReportedException(crashreport);
         }
      }
   }

   public FluidState m_6425_(BlockPos p_62895_) {
      return this.m_62814_(p_62895_.m_123341_(), p_62895_.m_123342_(), p_62895_.m_123343_());
   }

   public FluidState m_62814_(int p_62815_, int p_62816_, int p_62817_) {
      try {
         int i = this.m_151564_(p_62816_);
         if (i >= 0 && i < this.f_62772_.length) {
            LevelChunkSection levelchunksection = this.f_62772_[i];
            if (!LevelChunkSection.m_63000_(levelchunksection)) {
               return levelchunksection.m_63007_(p_62815_ & 15, p_62816_ & 15, p_62817_ & 15);
            }
         }

         return Fluids.f_76191_.m_76145_();
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.m_127521_(throwable, "Getting fluid state");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being got");
         crashreportcategory.m_128165_("Location", () -> {
            return CrashReportCategory.m_178942_(this, p_62815_, p_62816_, p_62817_);
         });
         throw new ReportedException(crashreport);
      }
   }

   @Nullable
   public BlockState m_6978_(BlockPos p_62865_, BlockState p_62866_, boolean p_62867_) {
      int i = p_62865_.m_123342_();
      int j = this.m_151564_(i);
      LevelChunkSection levelchunksection = this.f_62772_[j];
      if (levelchunksection == f_62770_) {
         if (p_62866_.m_60795_()) {
            return null;
         }

         levelchunksection = new LevelChunkSection(SectionPos.m_123171_(i));
         this.f_62772_[j] = levelchunksection;
      }

      boolean flag = levelchunksection.m_63013_();
      int k = p_62865_.m_123341_() & 15;
      int l = i & 15;
      int i1 = p_62865_.m_123343_() & 15;
      BlockState blockstate = levelchunksection.m_62986_(k, l, i1, p_62866_);
      if (blockstate == p_62866_) {
         return null;
      } else {
         Block block = p_62866_.m_60734_();
         this.f_62777_.get(Heightmap.Types.MOTION_BLOCKING).m_64249_(k, i, i1, p_62866_);
         this.f_62777_.get(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES).m_64249_(k, i, i1, p_62866_);
         this.f_62777_.get(Heightmap.Types.OCEAN_FLOOR).m_64249_(k, i, i1, p_62866_);
         this.f_62777_.get(Heightmap.Types.WORLD_SURFACE).m_64249_(k, i, i1, p_62866_);
         boolean flag1 = levelchunksection.m_63013_();
         if (flag != flag1) {
            this.f_62776_.m_7726_().m_7827_().m_75834_(p_62865_, flag1);
         }

         boolean flag2 = blockstate.m_155947_();
         if (!this.f_62776_.f_46443_) {
            blockstate.m_60753_(this.f_62776_, p_62865_, p_62866_, p_62867_);
         } else if ((!blockstate.m_60713_(block) || !p_62866_.m_155947_()) && flag2) {
            this.m_8114_(p_62865_);
         }

         if (!levelchunksection.m_62982_(k, l, i1).m_60713_(block)) {
            return null;
         } else {
            if (!this.f_62776_.f_46443_ && !this.f_62776_.captureBlockSnapshots) {
               p_62866_.m_60696_(this.f_62776_, p_62865_, blockstate, p_62867_);
            }

            if (p_62866_.m_155947_()) {
               BlockEntity blockentity = this.m_5685_(p_62865_, LevelChunk.EntityCreationType.CHECK);
               if (blockentity == null) {
                  blockentity = ((EntityBlock)block).m_142194_(p_62865_, p_62866_);
                  if (blockentity != null) {
                     this.m_142170_(blockentity);
                  }
               } else {
                  blockentity.m_155250_(p_62866_);
                  this.m_156406_(blockentity);
               }
            }

            this.f_62788_ = true;
            return blockstate;
         }
      }
   }

   @Deprecated
   public void m_6286_(Entity p_62826_) {
   }

   public int m_5885_(Heightmap.Types p_62847_, int p_62848_, int p_62849_) {
      return this.f_62777_.get(p_62847_).m_64242_(p_62848_ & 15, p_62849_ & 15) - 1;
   }

   public BlockPos m_142241_(Heightmap.Types p_156393_) {
      ChunkPos chunkpos = this.m_7697_();
      int i = this.m_141937_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j = chunkpos.m_45604_(); j <= chunkpos.m_45608_(); ++j) {
         for(int k = chunkpos.m_45605_(); k <= chunkpos.m_45609_(); ++k) {
            int l = this.m_5885_(p_156393_, j & 15, k & 15);
            if (l > i) {
               i = l;
               blockpos$mutableblockpos.m_122178_(j, l, k);
            }
         }
      }

      return blockpos$mutableblockpos.m_7949_();
   }

   @Nullable
   private BlockEntity m_62934_(BlockPos p_62935_) {
      BlockState blockstate = this.m_8055_(p_62935_);
      return !blockstate.m_155947_() ? null : ((EntityBlock)blockstate.m_60734_()).m_142194_(p_62935_, blockstate);
   }

   @Nullable
   public BlockEntity m_7702_(BlockPos p_62912_) {
      return this.m_5685_(p_62912_, LevelChunk.EntityCreationType.CHECK);
   }

   @Nullable
   public BlockEntity m_5685_(BlockPos p_62868_, LevelChunk.EntityCreationType p_62869_) {
      BlockEntity blockentity = this.f_62779_.get(p_62868_);
      if (blockentity != null && blockentity.m_58901_()) {
         f_62779_.remove(p_62868_);
         blockentity = null;
      }
      if (blockentity == null) {
         CompoundTag compoundtag = this.f_62774_.remove(p_62868_);
         if (compoundtag != null) {
            BlockEntity blockentity1 = this.m_62870_(p_62868_, compoundtag);
            if (blockentity1 != null) {
               return blockentity1;
            }
         }
      }

      if (blockentity == null) {
         if (p_62869_ == LevelChunk.EntityCreationType.IMMEDIATE) {
            blockentity = this.m_62934_(p_62868_);
            if (blockentity != null) {
               this.m_142170_(blockentity);
            }
         }
      }

      return blockentity;
   }

   public void m_142170_(BlockEntity p_156391_) {
      this.m_142169_(p_156391_);
      if (this.m_156370_()) {
         this.m_156404_(p_156391_);
         this.m_156406_(p_156391_);
         p_156391_.onLoad();
      }

   }

   private boolean m_156370_() {
      return this.f_62775_ || this.f_62776_.m_5776_();
   }

   boolean m_156410_(BlockPos p_156411_) {
      if (!this.f_62776_.m_6857_().m_61937_(p_156411_)) {
         return false;
      } else if (!(this.f_62776_ instanceof ServerLevel)) {
         return true;
      } else {
         return this.m_6708_().m_140114_(ChunkHolder.FullChunkStatus.TICKING) && ((ServerLevel)this.f_62776_).m_143319_(ChunkPos.m_151388_(p_156411_));
      }
   }

   public void m_142169_(BlockEntity p_156374_) {
      BlockPos blockpos = p_156374_.m_58899_();
      if (this.m_8055_(blockpos).m_155947_()) {
         p_156374_.m_142339_(this.f_62776_);
         p_156374_.m_6339_();
         BlockEntity blockentity = this.f_62779_.put(blockpos.m_7949_(), p_156374_);
         if (blockentity != null && blockentity != p_156374_) {
            blockentity.m_7651_();
         }

      }
   }

   public void m_5604_(CompoundTag p_62882_) {
      this.f_62774_.put(new BlockPos(p_62882_.m_128451_("x"), p_62882_.m_128451_("y"), p_62882_.m_128451_("z")), p_62882_);
   }

   @Nullable
   public CompoundTag m_8051_(BlockPos p_62932_) {
      BlockEntity blockentity = this.m_7702_(p_62932_);
      if (blockentity != null && !blockentity.m_58901_()) {
         try {
         CompoundTag compoundtag1 = blockentity.m_6945_(new CompoundTag());
         compoundtag1.m_128379_("keepPacked", false);
         return compoundtag1;
         } catch (Exception e) {
            LogManager.getLogger().error("A BlockEntity type {} has thrown an exception trying to write state. It will not persist, Report this to the mod author", blockentity.getClass().getName(), e);
            return null;
         }
      } else {
         CompoundTag compoundtag = this.f_62774_.get(p_62932_);
         if (compoundtag != null) {
            compoundtag = compoundtag.m_6426_();
            compoundtag.m_128379_("keepPacked", true);
         }

         return compoundtag;
      }
   }

   public void m_8114_(BlockPos p_62919_) {
      if (this.m_156370_()) {
         BlockEntity blockentity = this.f_62779_.remove(p_62919_);
         if (blockentity != null) {
            this.m_156396_(blockentity);
            blockentity.m_7651_();
         }
      }

      this.m_156412_(p_62919_);
   }

   private <T extends BlockEntity> void m_156396_(T p_156397_) {
      if (!this.f_62776_.f_46443_) {
         Block block = p_156397_.m_58900_().m_60734_();
         if (block instanceof EntityBlock) {
            GameEventListener gameeventlistener = ((EntityBlock)block).m_142226_(this.f_62776_, p_156397_);
            if (gameeventlistener != null) {
               int i = SectionPos.m_123171_(p_156397_.m_58899_().m_123342_());
               GameEventDispatcher gameeventdispatcher = this.m_142336_(i);
               gameeventdispatcher.m_142500_(gameeventlistener);
               if (gameeventdispatcher.m_142086_()) {
                  this.f_156363_.remove(i);
               }
            }
         }

      }
   }

   private void m_156412_(BlockPos p_156413_) {
      LevelChunk.RebindableTickingBlockEntityWrapper levelchunk$rebindabletickingblockentitywrapper = this.f_156362_.remove(p_156413_);
      if (levelchunk$rebindabletickingblockentitywrapper != null) {
         levelchunk$rebindabletickingblockentitywrapper.m_156449_(f_156361_);
      }

   }

   public void m_62952_() {
      if (this.f_62791_ != null) {
         this.f_62791_.accept(this);
         this.f_62791_ = null;
      }

   }

   public void m_6427_() {
      this.f_62788_ = true;
   }

   public boolean m_6430_() {
      return false;
   }

   public ChunkPos m_7697_() {
      return this.f_62792_;
   }

   public void m_156383_(@Nullable ChunkBiomeContainer p_156384_, FriendlyByteBuf p_156385_, CompoundTag p_156386_, BitSet p_156387_) {
      boolean flag = p_156384_ != null;
      if (flag) {
         this.f_62779_.values().forEach(this::m_156400_);
         this.f_62779_.clear();
      } else {
         this.f_62779_.values().removeIf((p_156390_) -> {
            int j = this.m_151564_(p_156390_.m_58899_().m_123342_());
            if (p_156387_.get(j)) {
               p_156390_.m_7651_();
               return true;
            } else {
               return false;
            }
         });
      }

      for(int i = 0; i < this.f_62772_.length; ++i) {
         LevelChunkSection levelchunksection = this.f_62772_[i];
         if (!p_156387_.get(i)) {
            if (flag && levelchunksection != f_62770_) {
               this.f_62772_[i] = f_62770_;
            }
         } else {
            if (levelchunksection == f_62770_) {
               levelchunksection = new LevelChunkSection(this.m_151568_(i));
               this.f_62772_[i] = levelchunksection;
            }

            levelchunksection.m_63004_(p_156385_);
         }
      }

      if (p_156384_ != null) {
         this.f_62773_ = p_156384_;
      }

      for(Heightmap.Types heightmap$types : Heightmap.Types.values()) {
         String s = heightmap$types.m_64294_();
         if (p_156386_.m_128425_(s, 12)) {
            this.m_6511_(heightmap$types, p_156386_.m_128467_(s));
         }
      }

   }

   private void m_156400_(BlockEntity p_156401_) {
      p_156401_.m_7651_();
      this.f_156362_.remove(p_156401_.m_58899_());
   }

   public ChunkBiomeContainer m_6221_() {
      return this.f_62773_;
   }

   public void m_62913_(boolean p_62914_) {
      this.f_62775_ = p_62914_;
   }

   public Level m_62953_() {
      return this.f_62776_;
   }

   public Collection<Entry<Heightmap.Types, Heightmap>> m_6890_() {
      return Collections.unmodifiableSet(this.f_62777_.entrySet());
   }

   public Map<BlockPos, BlockEntity> m_62954_() {
      return this.f_62779_;
   }

   public CompoundTag m_8049_(BlockPos p_62929_) {
      return this.f_62774_.get(p_62929_);
   }

   public Stream<BlockPos> m_6267_() {
      return StreamSupport.stream(BlockPos.m_121976_(this.f_62792_.m_45604_(), this.m_141937_(), this.f_62792_.m_45605_(), this.f_62792_.m_45608_(), this.m_151558_() - 1, this.f_62792_.m_45609_()).spliterator(), false).filter((p_156419_) -> {
         return this.m_8055_(p_156419_).getLightEmission(m_62953_(), p_156419_) != 0;
      });
   }

   public TickList<Block> m_5782_() {
      return this.f_62784_;
   }

   public TickList<Fluid> m_5783_() {
      return this.f_62785_;
   }

   public void m_8092_(boolean p_62884_) {
      this.f_62788_ = p_62884_;
   }

   public boolean m_6344_() {
      return this.f_62788_;
   }

   @Nullable
   public StructureStart<?> m_7253_(StructureFeature<?> p_62854_) {
      return this.f_62781_.get(p_62854_);
   }

   public void m_8078_(StructureFeature<?> p_62859_, StructureStart<?> p_62860_) {
      this.f_62781_.put(p_62859_, p_62860_);
   }

   public Map<StructureFeature<?>, StructureStart<?>> m_6633_() {
      return this.f_62781_;
   }

   public void m_8040_(Map<StructureFeature<?>, StructureStart<?>> p_62878_) {
      this.f_62781_.clear();
      this.f_62781_.putAll(p_62878_);
   }

   public LongSet m_6705_(StructureFeature<?> p_62893_) {
      return this.f_62782_.computeIfAbsent(p_62893_, (p_156403_) -> {
         return new LongOpenHashSet();
      });
   }

   public void m_6306_(StructureFeature<?> p_62856_, long p_62857_) {
      this.f_62782_.computeIfAbsent(p_62856_, (p_156399_) -> {
         return new LongOpenHashSet();
      }).add(p_62857_);
   }

   public Map<StructureFeature<?>, LongSet> m_7049_() {
      return this.f_62782_;
   }

   public void m_7946_(Map<StructureFeature<?>, LongSet> p_62897_) {
      this.f_62782_.clear();
      this.f_62782_.putAll(p_62897_);
   }

   public long m_6319_() {
      return this.f_62789_;
   }

   public void m_6141_(long p_62890_) {
      this.f_62789_ = p_62890_;
   }

   public void m_62812_() {
      ChunkPos chunkpos = this.m_7697_();

      for(int i = 0; i < this.f_62783_.length; ++i) {
         if (this.f_62783_[i] != null) {
            for(Short oshort : this.f_62783_[i]) {
               BlockPos blockpos = ProtoChunk.m_63227_(oshort, this.m_151568_(i), chunkpos);
               BlockState blockstate = this.m_8055_(blockpos);
               BlockState blockstate1 = Block.m_49931_(blockstate, this.f_62776_, blockpos);
               this.f_62776_.m_7731_(blockpos, blockstate1, 20);
            }

            this.f_62783_[i].clear();
         }
      }

      this.m_62813_();

      for(BlockPos blockpos1 : ImmutableList.copyOf(this.f_62774_.keySet())) {
         this.m_7702_(blockpos1);
      }

      this.f_62774_.clear();
      this.f_62778_.m_63341_(this);
   }

   @Nullable
   private BlockEntity m_62870_(BlockPos p_62871_, CompoundTag p_62872_) {
      BlockState blockstate = this.m_8055_(p_62871_);
      BlockEntity blockentity;
      if ("DUMMY".equals(p_62872_.m_128461_("id"))) {
         if (blockstate.m_155947_()) {
            blockentity = ((EntityBlock)blockstate.m_60734_()).m_142194_(p_62871_, blockstate);
         } else {
            blockentity = null;
            f_62771_.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", p_62871_, blockstate);
         }
      } else {
         blockentity = BlockEntity.m_155241_(p_62871_, blockstate, p_62872_);
      }

      if (blockentity != null) {
         blockentity.m_142339_(this.f_62776_);
         this.m_142170_(blockentity);
      } else {
         f_62771_.warn("Tried to load a block entity for block {} but failed at location {}", blockstate, p_62871_);
      }

      return blockentity;
   }

   public UpgradeData m_7387_() {
      return this.f_62778_;
   }

   public ShortList[] m_6720_() {
      return this.f_62783_;
   }

   public void m_62813_() {
      if (this.f_62784_ instanceof ProtoTickList) {
         ((ProtoTickList)this.f_62784_).m_63305_(this.f_62776_.m_6219_(), (p_156417_) -> {
            return this.m_8055_((BlockPos)p_156417_).m_60734_();
         });
         this.f_62784_ = EmptyTickList.m_45888_();
      } else if (this.f_62784_ instanceof ChunkTickList) {
         ((ChunkTickList)this.f_62784_).m_45643_(this.f_62776_.m_6219_());
         this.f_62784_ = EmptyTickList.m_45888_();
      }

      if (this.f_62785_ instanceof ProtoTickList) {
         ((ProtoTickList)this.f_62785_).m_63305_(this.f_62776_.m_6217_(), (p_156415_) -> {
            return this.m_6425_((BlockPos)p_156415_).m_76152_();
         });
         this.f_62785_ = EmptyTickList.m_45888_();
      } else if (this.f_62785_ instanceof ChunkTickList) {
         ((ChunkTickList)this.f_62785_).m_45643_(this.f_62776_.m_6217_());
         this.f_62785_ = EmptyTickList.m_45888_();
      }

   }

   public void m_62823_(ServerLevel p_62824_) {
      if (this.f_62784_ == EmptyTickList.<Block>m_45888_()) {
         this.f_62784_ = new ChunkTickList<>(Registry.f_122824_::m_7981_, p_62824_.m_6219_().m_47223_(this.f_62792_, true, false), p_62824_.m_46467_());
         this.m_8092_(true);
      }

      if (this.f_62785_ == EmptyTickList.<Fluid>m_45888_()) {
         this.f_62785_ = new ChunkTickList<>(Registry.f_122822_::m_7981_, p_62824_.m_6217_().m_47223_(this.f_62792_, true, false), p_62824_.m_46467_());
         this.m_8092_(true);
      }

   }

   public int m_141937_() {
      return this.f_62776_.m_141937_();
   }

   public int m_141928_() {
      return this.f_62776_.m_141928_();
   }

   public ChunkStatus m_6415_() {
      return ChunkStatus.f_62326_;
   }

   public ChunkHolder.FullChunkStatus m_6708_() {
      return this.f_62790_ == null ? ChunkHolder.FullChunkStatus.BORDER : this.f_62790_.get();
   }

   public void m_62879_(Supplier<ChunkHolder.FullChunkStatus> p_62880_) {
      this.f_62790_ = p_62880_;
   }

   public boolean m_6332_() {
      return this.f_62793_;
   }

   public void m_8094_(boolean p_62899_) {
      this.f_62793_ = p_62899_;
      this.m_8092_(true);
   }

   public void m_156368_() {
      this.f_62779_.values().forEach(this::m_156400_);
   }

   public void m_156369_() {
      this.f_62776_.addFreshBlockEntities(this.f_62779_.values());
      this.f_62779_.values().forEach((p_156409_) -> {
         this.m_156404_(p_156409_);
         this.m_156406_(p_156409_);
      });
   }

   private <T extends BlockEntity> void m_156404_(T p_156405_) {
      if (!this.f_62776_.f_46443_) {
         Block block = p_156405_.m_58900_().m_60734_();
         if (block instanceof EntityBlock) {
            GameEventListener gameeventlistener = ((EntityBlock)block).m_142226_(this.f_62776_, p_156405_);
            if (gameeventlistener != null) {
               GameEventDispatcher gameeventdispatcher = this.m_142336_(SectionPos.m_123171_(p_156405_.m_58899_().m_123342_()));
               gameeventdispatcher.m_142501_(gameeventlistener);
            }
         }

      }
   }

   private <T extends BlockEntity> void m_156406_(T p_156407_) {
      BlockState blockstate = p_156407_.m_58900_();
      BlockEntityTicker<T> blockentityticker = (BlockEntityTicker<T>)blockstate.m_155944_(this.f_62776_, p_156407_.m_58903_());
      if (blockentityticker == null) {
         this.m_156412_(p_156407_.m_58899_());
      } else {
         this.f_156362_.compute(p_156407_.m_58899_(), (p_156381_, p_156382_) -> {
            TickingBlockEntity tickingblockentity = this.m_156375_(p_156407_, blockentityticker);
            if (p_156382_ != null) {
               p_156382_.m_156449_(tickingblockentity);
               return p_156382_;
            } else if (this.m_156370_()) {
               LevelChunk.RebindableTickingBlockEntityWrapper levelchunk$rebindabletickingblockentitywrapper = new LevelChunk.RebindableTickingBlockEntityWrapper(tickingblockentity);
               this.f_62776_.m_151525_(levelchunk$rebindabletickingblockentitywrapper);
               return levelchunk$rebindabletickingblockentitywrapper;
            } else {
               return null;
            }
         });
      }

   }

   private <T extends BlockEntity> TickingBlockEntity m_156375_(T p_156376_, BlockEntityTicker<T> p_156377_) {
      return new LevelChunk.BoundTickingBlockEntity<>(p_156376_, p_156377_);
   }

   class BoundTickingBlockEntity<T extends BlockEntity> implements TickingBlockEntity {
      private final T f_156428_;
      private final BlockEntityTicker<T> f_156429_;
      private boolean f_156430_;

      BoundTickingBlockEntity(T p_156433_, BlockEntityTicker<T> p_156434_) {
         this.f_156428_ = p_156433_;
         this.f_156429_ = p_156434_;
      }

      public void m_142224_() {
         if (!this.f_156428_.m_58901_() && this.f_156428_.m_58898_()) {
            BlockPos blockpos = this.f_156428_.m_58899_();
            if (LevelChunk.this.m_156410_(blockpos)) {
               try {
                  ProfilerFiller profilerfiller = LevelChunk.this.f_62776_.m_46473_();
                  net.minecraftforge.server.timings.TimeTracker.BLOCK_ENTITY_UPDATE.trackStart(f_156428_);
                  profilerfiller.m_6521_(this::m_142280_);
                  BlockState blockstate = LevelChunk.this.m_8055_(blockpos);
                  if (this.f_156428_.m_58903_().m_155262_(blockstate)) {
                     this.f_156429_.m_155252_(LevelChunk.this.f_62776_, this.f_156428_.m_58899_(), blockstate, this.f_156428_);
                     this.f_156430_ = false;
                  } else if (!this.f_156430_) {
                     this.f_156430_ = true;
                     LevelChunk.f_62771_.warn("Block entity {} @ {} state {} invalid for ticking:", this::m_142280_, this::m_142689_, () -> {
                        return blockstate;
                     });
                  }

                  profilerfiller.m_7238_();
               } catch (Throwable throwable) {
                  CrashReport crashreport = CrashReport.m_127521_(throwable, "Ticking block entity");
                  CrashReportCategory crashreportcategory = crashreport.m_127514_("Block entity being ticked");
                  this.f_156428_.m_58886_(crashreportcategory);

                  if (net.minecraftforge.common.ForgeConfig.SERVER.removeErroringBlockEntities.get()) {
                     LogManager.getLogger().fatal("{}", crashreport.m_127526_());
                     f_156428_.m_7651_();
                     LevelChunk.this.m_8114_(f_156428_.m_58899_());
                  } else
                  throw new ReportedException(crashreport);
               }
            }
         }

      }

      public boolean m_142220_() {
         return this.f_156428_.m_58901_();
      }

      public BlockPos m_142689_() {
         return this.f_156428_.m_58899_();
      }

      public String m_142280_() {
         return BlockEntityType.m_58954_(this.f_156428_.m_58903_()).toString();
      }

      public String toString() {
         return "Level ticker for " + this.m_142280_() + "@" + this.m_142689_();
      }
   }

   public static enum EntityCreationType {
      IMMEDIATE,
      QUEUED,
      CHECK;
   }

   /**
    * <strong>FOR INTERNAL USE ONLY</strong>
    * <p>
    * Only public for use in {@link net.minecraft.world.level.chunk.storage.ChunkSerializer}.
    */
   @java.lang.Deprecated
   @javax.annotation.Nullable
   public final CompoundTag writeCapsToNBT() {
      return this.serializeCaps();
   }

   /**
    * <strong>FOR INTERNAL USE ONLY</strong>
    * <p>
    * Only public for use in {@link net.minecraft.world.level.chunk.storage.ChunkSerializer}.
    */
   @java.lang.Deprecated
   public final void readCapsFromNBT(CompoundTag tag) {
      this.deserializeCaps(tag);
   }

   @Override
   public Level getWorldForge() {
      return m_62953_();
   }

   class RebindableTickingBlockEntityWrapper implements TickingBlockEntity {
      private TickingBlockEntity f_156444_;

      RebindableTickingBlockEntityWrapper(TickingBlockEntity p_156447_) {
         this.f_156444_ = p_156447_;
      }

      void m_156449_(TickingBlockEntity p_156450_) {
         this.f_156444_ = p_156450_;
      }

      public void m_142224_() {
         this.f_156444_.m_142224_();
      }

      public boolean m_142220_() {
         return this.f_156444_.m_142220_();
      }

      public BlockPos m_142689_() {
         return this.f_156444_.m_142689_();
      }

      public String m_142280_() {
         return this.f_156444_.m_142280_();
      }

      public String toString() {
         return this.f_156444_.toString() + " <wrapped>";
      }
   }
}
