package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProtoChunk implements ChunkAccess {
   private static final Logger f_63147_ = LogManager.getLogger();
   private final ChunkPos f_63148_;
   private volatile boolean f_63149_;
   @Nullable
   private ChunkBiomeContainer f_63150_;
   @Nullable
   private volatile LevelLightEngine f_63151_;
   private final Map<Heightmap.Types, Heightmap> f_63152_ = Maps.newEnumMap(Heightmap.Types.class);
   private volatile ChunkStatus f_63153_ = ChunkStatus.f_62314_;
   private final Map<BlockPos, BlockEntity> f_63154_ = Maps.newHashMap();
   private final Map<BlockPos, CompoundTag> f_63155_ = Maps.newHashMap();
   private final LevelChunkSection[] f_63156_;
   private final List<CompoundTag> f_63157_ = Lists.newArrayList();
   private final List<BlockPos> f_63158_ = Lists.newArrayList();
   private final ShortList[] f_63159_;
   private final Map<StructureFeature<?>, StructureStart<?>> f_63160_ = Maps.newHashMap();
   private final Map<StructureFeature<?>, LongSet> f_63161_ = Maps.newHashMap();
   private final UpgradeData f_63162_;
   private final ProtoTickList<Block> f_63163_;
   private final ProtoTickList<Fluid> f_63164_;
   private final LevelHeightAccessor f_156475_;
   private long f_63165_;
   private final Map<GenerationStep.Carving, BitSet> f_63166_ = new Object2ObjectArrayMap<>();
   private volatile boolean f_63167_;

   public ProtoChunk(ChunkPos p_156477_, UpgradeData p_156478_, LevelHeightAccessor p_156479_) {
      this(p_156477_, p_156478_, (LevelChunkSection[])null, new ProtoTickList<>((p_63185_) -> {
         return p_63185_ == null || p_63185_.m_49966_().m_60795_();
      }, p_156477_, p_156479_), new ProtoTickList<>((p_63212_) -> {
         return p_63212_ == null || p_63212_ == Fluids.f_76191_;
      }, p_156477_, p_156479_), p_156479_);
   }

   public ProtoChunk(ChunkPos p_156481_, UpgradeData p_156482_, @Nullable LevelChunkSection[] p_156483_, ProtoTickList<Block> p_156484_, ProtoTickList<Fluid> p_156485_, LevelHeightAccessor p_156486_) {
      this.f_63148_ = p_156481_;
      this.f_63162_ = p_156482_;
      this.f_63163_ = p_156484_;
      this.f_63164_ = p_156485_;
      this.f_156475_ = p_156486_;
      this.f_63156_ = new LevelChunkSection[p_156486_.m_151559_()];
      if (p_156483_ != null) {
         if (this.f_63156_.length == p_156483_.length) {
            System.arraycopy(p_156483_, 0, this.f_63156_, 0, this.f_63156_.length);
         } else {
            f_63147_.warn("Could not set level chunk sections, array length is {} instead of {}", p_156483_.length, this.f_63156_.length);
         }
      }

      this.f_63159_ = new ShortList[p_156486_.m_151559_()];
   }

   public BlockState m_8055_(BlockPos p_63264_) {
      int i = p_63264_.m_123342_();
      if (this.m_151562_(i)) {
         return Blocks.f_50626_.m_49966_();
      } else {
         LevelChunkSection levelchunksection = this.m_7103_()[this.m_151564_(i)];
         return LevelChunkSection.m_63000_(levelchunksection) ? Blocks.f_50016_.m_49966_() : levelchunksection.m_62982_(p_63264_.m_123341_() & 15, i & 15, p_63264_.m_123343_() & 15);
      }
   }

   public FluidState m_6425_(BlockPos p_63239_) {
      int i = p_63239_.m_123342_();
      if (this.m_151562_(i)) {
         return Fluids.f_76191_.m_76145_();
      } else {
         LevelChunkSection levelchunksection = this.m_7103_()[this.m_151564_(i)];
         return LevelChunkSection.m_63000_(levelchunksection) ? Fluids.f_76191_.m_76145_() : levelchunksection.m_63007_(p_63239_.m_123341_() & 15, i & 15, p_63239_.m_123343_() & 15);
      }
   }

   public Stream<BlockPos> m_6267_() {
      return this.f_63158_.stream();
   }

   public ShortList[] m_63291_() {
      ShortList[] ashortlist = new ShortList[this.m_151559_()];

      for(BlockPos blockpos : this.f_63158_) {
         ChunkAccess.m_62095_(ashortlist, this.m_151564_(blockpos.m_123342_())).add(m_63280_(blockpos));
      }

      return ashortlist;
   }

   public void m_63244_(short p_63245_, int p_63246_) {
      this.m_63277_(m_63227_(p_63245_, this.m_151568_(p_63246_), this.f_63148_));
   }

   public void m_63277_(BlockPos p_63278_) {
      this.f_63158_.add(p_63278_.m_7949_());
   }

   @Nullable
   public BlockState m_6978_(BlockPos p_63217_, BlockState p_63218_, boolean p_63219_) {
      int i = p_63217_.m_123341_();
      int j = p_63217_.m_123342_();
      int k = p_63217_.m_123343_();
      if (j >= this.m_141937_() && j < this.m_151558_()) {
         int l = this.m_151564_(j);
         if (this.f_63156_[l] == LevelChunk.f_62770_ && p_63218_.m_60713_(Blocks.f_50016_)) {
            return p_63218_;
         } else {
            if (p_63218_.getLightEmission(this, p_63217_) > 0) {
               this.f_63158_.add(new BlockPos((i & 15) + this.m_7697_().m_45604_(), j, (k & 15) + this.m_7697_().m_45605_()));
            }

            LevelChunkSection levelchunksection = this.m_156115_(l);
            BlockState blockstate = levelchunksection.m_62986_(i & 15, j & 15, k & 15, p_63218_);
            if (this.f_63153_.m_62427_(ChunkStatus.f_62322_) && p_63218_ != blockstate && (p_63218_.m_60739_(this, p_63217_) != blockstate.m_60739_(this, p_63217_) || p_63218_.getLightEmission(this, p_63217_) != blockstate.getLightEmission(this, p_63217_) || p_63218_.m_60787_() || blockstate.m_60787_())) {
               this.f_63151_.m_142202_(p_63217_);
            }

            EnumSet<Heightmap.Types> enumset = this.m_6415_().m_62500_();
            EnumSet<Heightmap.Types> enumset1 = null;

            for(Heightmap.Types heightmap$types : enumset) {
               Heightmap heightmap = this.f_63152_.get(heightmap$types);
               if (heightmap == null) {
                  if (enumset1 == null) {
                     enumset1 = EnumSet.noneOf(Heightmap.Types.class);
                  }

                  enumset1.add(heightmap$types);
               }
            }

            if (enumset1 != null) {
               Heightmap.m_64256_(this, enumset1);
            }

            for(Heightmap.Types heightmap$types1 : enumset) {
               this.f_63152_.get(heightmap$types1).m_64249_(i & 15, j, k & 15, p_63218_);
            }

            return blockstate;
         }
      } else {
         return Blocks.f_50626_.m_49966_();
      }
   }

   public void m_142169_(BlockEntity p_156488_) {
      this.f_63154_.put(p_156488_.m_58899_(), p_156488_);
   }

   public Set<BlockPos> m_5928_() {
      Set<BlockPos> set = Sets.newHashSet(this.f_63155_.keySet());
      set.addAll(this.f_63154_.keySet());
      return set;
   }

   @Nullable
   public BlockEntity m_7702_(BlockPos p_63257_) {
      return this.f_63154_.get(p_63257_);
   }

   public Map<BlockPos, BlockEntity> m_63292_() {
      return this.f_63154_;
   }

   public void m_63242_(CompoundTag p_63243_) {
      this.f_63157_.add(p_63243_);
   }

   public void m_6286_(Entity p_63183_) {
      if (!p_63183_.m_20159_()) {
         CompoundTag compoundtag = new CompoundTag();
         p_63183_.m_20223_(compoundtag);
         this.m_63242_(compoundtag);
      }
   }

   public List<CompoundTag> m_63293_() {
      return this.f_63157_;
   }

   public void m_7329_(ChunkBiomeContainer p_63186_) {
      this.f_63150_ = p_63186_;
   }

   @Nullable
   public ChunkBiomeContainer m_6221_() {
      return this.f_63150_;
   }

   public void m_8092_(boolean p_63232_) {
      this.f_63149_ = p_63232_;
   }

   public boolean m_6344_() {
      return this.f_63149_;
   }

   public ChunkStatus m_6415_() {
      return this.f_63153_;
   }

   public void m_7150_(ChunkStatus p_63187_) {
      this.f_63153_ = p_63187_;
      this.m_8092_(true);
   }

   public LevelChunkSection[] m_7103_() {
      return this.f_63156_;
   }

   public Collection<Entry<Heightmap.Types, Heightmap>> m_6890_() {
      return Collections.unmodifiableSet(this.f_63152_.entrySet());
   }

   public Heightmap m_6005_(Heightmap.Types p_63193_) {
      return this.f_63152_.computeIfAbsent(p_63193_, (p_63253_) -> {
         return new Heightmap(this, p_63253_);
      });
   }

   public int m_5885_(Heightmap.Types p_63195_, int p_63196_, int p_63197_) {
      Heightmap heightmap = this.f_63152_.get(p_63195_);
      if (heightmap == null) {
         Heightmap.m_64256_(this, EnumSet.of(p_63195_));
         heightmap = this.f_63152_.get(p_63195_);
      }

      return heightmap.m_64242_(p_63196_ & 15, p_63197_ & 15) - 1;
   }

   public BlockPos m_142241_(Heightmap.Types p_156490_) {
      int i = this.m_141937_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j = this.f_63148_.m_45604_(); j <= this.f_63148_.m_45608_(); ++j) {
         for(int k = this.f_63148_.m_45605_(); k <= this.f_63148_.m_45609_(); ++k) {
            int l = this.m_5885_(p_156490_, j & 15, k & 15);
            if (l > i) {
               i = l;
               blockpos$mutableblockpos.m_122178_(j, l, k);
            }
         }
      }

      return blockpos$mutableblockpos.m_7949_();
   }

   public ChunkPos m_7697_() {
      return this.f_63148_;
   }

   @Nullable
   public StructureStart<?> m_7253_(StructureFeature<?> p_63202_) {
      return this.f_63160_.get(p_63202_);
   }

   public void m_8078_(StructureFeature<?> p_63207_, StructureStart<?> p_63208_) {
      this.f_63160_.put(p_63207_, p_63208_);
      this.f_63149_ = true;
   }

   public Map<StructureFeature<?>, StructureStart<?>> m_6633_() {
      return Collections.unmodifiableMap(this.f_63160_);
   }

   public void m_8040_(Map<StructureFeature<?>, StructureStart<?>> p_63221_) {
      this.f_63160_.clear();
      this.f_63160_.putAll(p_63221_);
      this.f_63149_ = true;
   }

   public LongSet m_6705_(StructureFeature<?> p_63237_) {
      return this.f_63161_.computeIfAbsent(p_63237_, (p_63260_) -> {
         return new LongOpenHashSet();
      });
   }

   public void m_6306_(StructureFeature<?> p_63204_, long p_63205_) {
      this.f_63161_.computeIfAbsent(p_63204_, (p_63255_) -> {
         return new LongOpenHashSet();
      }).add(p_63205_);
      this.f_63149_ = true;
   }

   public Map<StructureFeature<?>, LongSet> m_7049_() {
      return Collections.unmodifiableMap(this.f_63161_);
   }

   public void m_7946_(Map<StructureFeature<?>, LongSet> p_63241_) {
      this.f_63161_.clear();
      this.f_63161_.putAll(p_63241_);
      this.f_63149_ = true;
   }

   public static short m_63280_(BlockPos p_63281_) {
      int i = p_63281_.m_123341_();
      int j = p_63281_.m_123342_();
      int k = p_63281_.m_123343_();
      int l = i & 15;
      int i1 = j & 15;
      int j1 = k & 15;
      return (short)(l | i1 << 4 | j1 << 8);
   }

   public static BlockPos m_63227_(short p_63228_, int p_63229_, ChunkPos p_63230_) {
      int i = SectionPos.m_175554_(p_63230_.f_45578_, p_63228_ & 15);
      int j = SectionPos.m_175554_(p_63229_, p_63228_ >>> 4 & 15);
      int k = SectionPos.m_175554_(p_63230_.f_45579_, p_63228_ >>> 8 & 15);
      return new BlockPos(i, j, k);
   }

   public void m_8113_(BlockPos p_63266_) {
      if (!this.m_151570_(p_63266_)) {
         ChunkAccess.m_62095_(this.f_63159_, this.m_151564_(p_63266_.m_123342_())).add(m_63280_(p_63266_));
      }

   }

   public ShortList[] m_6720_() {
      return this.f_63159_;
   }

   public void m_6561_(short p_63225_, int p_63226_) {
      ChunkAccess.m_62095_(this.f_63159_, p_63226_).add(p_63225_);
   }

   public ProtoTickList<Block> m_5782_() {
      return this.f_63163_;
   }

   public ProtoTickList<Fluid> m_5783_() {
      return this.f_63164_;
   }

   public UpgradeData m_7387_() {
      return this.f_63162_;
   }

   public void m_6141_(long p_63234_) {
      this.f_63165_ = p_63234_;
   }

   public long m_6319_() {
      return this.f_63165_;
   }

   public void m_5604_(CompoundTag p_63223_) {
      this.f_63155_.put(new BlockPos(p_63223_.m_128451_("x"), p_63223_.m_128451_("y"), p_63223_.m_128451_("z")), p_63223_);
   }

   public Map<BlockPos, CompoundTag> m_63294_() {
      return Collections.unmodifiableMap(this.f_63155_);
   }

   public CompoundTag m_8049_(BlockPos p_63272_) {
      return this.f_63155_.get(p_63272_);
   }

   @Nullable
   public CompoundTag m_8051_(BlockPos p_63275_) {
      BlockEntity blockentity = this.m_7702_(p_63275_);
      return blockentity != null ? blockentity.m_6945_(new CompoundTag()) : this.f_63155_.get(p_63275_);
   }

   public void m_8114_(BlockPos p_63262_) {
      this.f_63154_.remove(p_63262_);
      this.f_63155_.remove(p_63262_);
   }

   @Nullable
   public BitSet m_6548_(GenerationStep.Carving p_63188_) {
      return this.f_63166_.get(p_63188_);
   }

   public BitSet m_6547_(GenerationStep.Carving p_63235_) {
      return this.f_63166_.computeIfAbsent(p_63235_, (p_63251_) -> {
         return new BitSet(65536);
      });
   }

   public void m_63189_(GenerationStep.Carving p_63190_, BitSet p_63191_) {
      this.f_63166_.put(p_63190_, p_63191_);
   }

   public void m_63209_(LevelLightEngine p_63210_) {
      this.f_63151_ = p_63210_;
   }

   public boolean m_6332_() {
      return this.f_63167_;
   }

   public void m_8094_(boolean p_63248_) {
      this.f_63167_ = p_63248_;
      this.m_8092_(true);
   }

   public int m_141937_() {
      return this.f_156475_.m_141937_();
   }

   public int m_141928_() {
      return this.f_156475_.m_141928_();
   }
}
