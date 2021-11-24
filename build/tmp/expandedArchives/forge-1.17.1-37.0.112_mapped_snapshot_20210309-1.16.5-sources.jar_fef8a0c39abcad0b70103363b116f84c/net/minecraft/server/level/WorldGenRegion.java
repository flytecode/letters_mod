package net.minecraft.server.level;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenRegion implements WorldGenLevel {
   private static final Logger f_9474_ = LogManager.getLogger();
   private final List<ChunkAccess> f_9475_;
   private final ChunkPos f_143479_;
   private final int f_9478_;
   private final ServerLevel f_9479_;
   private final long f_9480_;
   private final LevelData f_9481_;
   private final Random f_9482_;
   private final DimensionType f_9483_;
   private final TickList<Block> f_9484_ = new WorldGenTickList<>((p_9597_) -> {
      return this.m_46865_(p_9597_).m_5782_();
   });
   private final TickList<Fluid> f_9485_ = new WorldGenTickList<>((p_9595_) -> {
      return this.m_46865_(p_9595_).m_5783_();
   });
   private final BiomeManager f_9486_;
   private final ChunkPos f_9487_;
   private final ChunkPos f_9488_;
   private final StructureFeatureManager f_9489_;
   private final ChunkStatus f_143480_;
   private final int f_143481_;
   @Nullable
   private Supplier<String> f_143482_;

   public WorldGenRegion(ServerLevel p_143484_, List<ChunkAccess> p_143485_, ChunkStatus p_143486_, int p_143487_) {
      this.f_143480_ = p_143486_;
      this.f_143481_ = p_143487_;
      int i = Mth.m_14107_(Math.sqrt((double)p_143485_.size()));
      if (i * i != p_143485_.size()) {
         throw (IllegalStateException)Util.m_137570_(new IllegalStateException("Cache size is not a square."));
      } else {
         ChunkPos chunkpos = p_143485_.get(p_143485_.size() / 2).m_7697_();
         this.f_9475_ = p_143485_;
         this.f_143479_ = chunkpos;
         this.f_9478_ = i;
         this.f_9479_ = p_143484_;
         this.f_9480_ = p_143484_.m_7328_();
         this.f_9481_ = p_143484_.m_6106_();
         this.f_9482_ = p_143484_.m_5822_();
         this.f_9483_ = p_143484_.m_6042_();
         this.f_9486_ = new BiomeManager(this, BiomeManager.m_47877_(this.f_9480_), p_143484_.m_6042_().m_63966_());
         this.f_9487_ = p_143485_.get(0).m_7697_();
         this.f_9488_ = p_143485_.get(p_143485_.size() - 1).m_7697_();
         this.f_9489_ = p_143484_.m_8595_().m_47272_(this);
      }
   }

   public ChunkPos m_143488_() {
      return this.f_143479_;
   }

   public void m_143497_(@Nullable Supplier<String> p_143498_) {
      this.f_143482_ = p_143498_;
   }

   public ChunkAccess m_6325_(int p_9507_, int p_9508_) {
      return this.m_46819_(p_9507_, p_9508_, ChunkStatus.f_62314_);
   }

   @Nullable
   public ChunkAccess m_6522_(int p_9514_, int p_9515_, ChunkStatus p_9516_, boolean p_9517_) {
      ChunkAccess chunkaccess;
      if (this.m_7232_(p_9514_, p_9515_)) {
         int i = p_9514_ - this.f_9487_.f_45578_;
         int j = p_9515_ - this.f_9487_.f_45579_;
         chunkaccess = this.f_9475_.get(i + j * this.f_9478_);
         if (chunkaccess.m_6415_().m_62427_(p_9516_)) {
            return chunkaccess;
         }
      } else {
         chunkaccess = null;
      }

      if (!p_9517_) {
         return null;
      } else {
         f_9474_.error("Requested chunk : {} {}", p_9514_, p_9515_);
         f_9474_.error("Region bounds : {} {} | {} {}", this.f_9487_.f_45578_, this.f_9487_.f_45579_, this.f_9488_.f_45578_, this.f_9488_.f_45579_);
         if (chunkaccess != null) {
            throw (RuntimeException)Util.m_137570_(new RuntimeException(String.format("Chunk is not of correct status. Expecting %s, got %s | %s %s", p_9516_, chunkaccess.m_6415_(), p_9514_, p_9515_)));
         } else {
            throw (RuntimeException)Util.m_137570_(new RuntimeException(String.format("We are asking a region for a chunk out of bound | %s %s", p_9514_, p_9515_)));
         }
      }
   }

   public boolean m_7232_(int p_9574_, int p_9575_) {
      return p_9574_ >= this.f_9487_.f_45578_ && p_9574_ <= this.f_9488_.f_45578_ && p_9575_ >= this.f_9487_.f_45579_ && p_9575_ <= this.f_9488_.f_45579_;
   }

   public BlockState m_8055_(BlockPos p_9587_) {
      return this.m_6325_(SectionPos.m_123171_(p_9587_.m_123341_()), SectionPos.m_123171_(p_9587_.m_123343_())).m_8055_(p_9587_);
   }

   public FluidState m_6425_(BlockPos p_9577_) {
      return this.m_46865_(p_9577_).m_6425_(p_9577_);
   }

   @Nullable
   public Player m_5788_(double p_9501_, double p_9502_, double p_9503_, double p_9504_, Predicate<Entity> p_9505_) {
      return null;
   }

   public int m_7445_() {
      return 0;
   }

   public BiomeManager m_7062_() {
      return this.f_9486_;
   }

   public Biome m_6159_(int p_9510_, int p_9511_, int p_9512_) {
      return this.f_9479_.m_6159_(p_9510_, p_9511_, p_9512_);
   }

   public float m_7717_(Direction p_9555_, boolean p_9556_) {
      return 1.0F;
   }

   public LevelLightEngine m_5518_() {
      return this.f_9479_.m_5518_();
   }

   public boolean m_7740_(BlockPos p_9550_, boolean p_9551_, @Nullable Entity p_9552_, int p_9553_) {
      BlockState blockstate = this.m_8055_(p_9550_);
      if (blockstate.m_60795_()) {
         return false;
      } else {
         if (p_9551_) {
            BlockEntity blockentity = blockstate.m_155947_() ? this.m_7702_(p_9550_) : null;
            Block.m_49881_(blockstate, this.f_9479_, p_9550_, blockentity, p_9552_, ItemStack.f_41583_);
         }

         return this.m_6933_(p_9550_, Blocks.f_50016_.m_49966_(), 3, p_9553_);
      }
   }

   @Nullable
   public BlockEntity m_7702_(BlockPos p_9582_) {
      ChunkAccess chunkaccess = this.m_46865_(p_9582_);
      BlockEntity blockentity = chunkaccess.m_7702_(p_9582_);
      if (blockentity != null) {
         return blockentity;
      } else {
         CompoundTag compoundtag = chunkaccess.m_8049_(p_9582_);
         BlockState blockstate = chunkaccess.m_8055_(p_9582_);
         if (compoundtag != null) {
            if ("DUMMY".equals(compoundtag.m_128461_("id"))) {
               if (!blockstate.m_155947_()) {
                  return null;
               }

               blockentity = ((EntityBlock)blockstate.m_60734_()).m_142194_(p_9582_, blockstate);
            } else {
               blockentity = BlockEntity.m_155241_(p_9582_, blockstate, compoundtag);
            }

            if (blockentity != null) {
               chunkaccess.m_142169_(blockentity);
               return blockentity;
            }
         }

         if (blockstate.m_155947_()) {
            f_9474_.warn("Tried to access a block entity before it was created. {}", (Object)p_9582_);
         }

         return null;
      }
   }

   public boolean m_180807_(BlockPos p_181031_) {
      int i = SectionPos.m_123171_(p_181031_.m_123341_());
      int j = SectionPos.m_123171_(p_181031_.m_123343_());
      int k = Math.abs(this.f_143479_.f_45578_ - i);
      int l = Math.abs(this.f_143479_.f_45579_ - j);
      if (k <= this.f_143481_ && l <= this.f_143481_) {
         return true;
      } else {
         Util.m_143785_("Detected setBlock in a far chunk [" + i + ", " + j + "], pos: " + p_181031_ + ", status: " + this.f_143480_ + (this.f_143482_ == null ? "" : ", currently generating: " + (String)this.f_143482_.get()));
         return false;
      }
   }

   public boolean m_6933_(BlockPos p_9539_, BlockState p_9540_, int p_9541_, int p_9542_) {
      if (!this.m_180807_(p_9539_)) {
         return false;
      } else {
         ChunkAccess chunkaccess = this.m_46865_(p_9539_);
         BlockState blockstate = chunkaccess.m_6978_(p_9539_, p_9540_, false);
         if (blockstate != null) {
            this.f_9479_.m_6559_(p_9539_, blockstate, p_9540_);
         }

         if (p_9540_.m_155947_()) {
            if (chunkaccess.m_6415_().m_62494_() == ChunkStatus.ChunkType.LEVELCHUNK) {
               BlockEntity blockentity = ((EntityBlock)p_9540_.m_60734_()).m_142194_(p_9539_, p_9540_);
               if (blockentity != null) {
                  chunkaccess.m_142169_(blockentity);
               } else {
                  chunkaccess.m_8114_(p_9539_);
               }
            } else {
               CompoundTag compoundtag = new CompoundTag();
               compoundtag.m_128405_("x", p_9539_.m_123341_());
               compoundtag.m_128405_("y", p_9539_.m_123342_());
               compoundtag.m_128405_("z", p_9539_.m_123343_());
               compoundtag.m_128359_("id", "DUMMY");
               chunkaccess.m_5604_(compoundtag);
            }
         } else if (blockstate != null && blockstate.m_155947_()) {
            chunkaccess.m_8114_(p_9539_);
         }

         if (p_9540_.m_60835_(this, p_9539_)) {
            this.m_9591_(p_9539_);
         }

         return true;
      }
   }

   private void m_9591_(BlockPos p_9592_) {
      this.m_46865_(p_9592_).m_8113_(p_9592_);
   }

   public boolean m_7967_(Entity p_9580_) {
      int i = SectionPos.m_123171_(p_9580_.m_146903_());
      int j = SectionPos.m_123171_(p_9580_.m_146907_());
      this.m_6325_(i, j).m_6286_(p_9580_);
      return true;
   }

   public boolean m_7471_(BlockPos p_9547_, boolean p_9548_) {
      return this.m_7731_(p_9547_, Blocks.f_50016_.m_49966_(), 3);
   }

   public WorldBorder m_6857_() {
      return this.f_9479_.m_6857_();
   }

   public boolean m_5776_() {
      return false;
   }

   @Deprecated
   public ServerLevel m_6018_() {
      return this.f_9479_;
   }

   public RegistryAccess m_5962_() {
      return this.f_9479_.m_5962_();
   }

   public LevelData m_6106_() {
      return this.f_9481_;
   }

   public DifficultyInstance m_6436_(BlockPos p_9585_) {
      if (!this.m_7232_(SectionPos.m_123171_(p_9585_.m_123341_()), SectionPos.m_123171_(p_9585_.m_123343_()))) {
         throw new RuntimeException("We are asking a region for a chunk out of bound");
      } else {
         return new DifficultyInstance(this.f_9479_.m_46791_(), this.f_9479_.m_46468_(), 0L, this.f_9479_.m_46940_());
      }
   }

   @Nullable
   public MinecraftServer m_142572_() {
      return this.f_9479_.m_142572_();
   }

   public ChunkSource m_7726_() {
      return this.f_9479_.m_7726_();
   }

   public long m_7328_() {
      return this.f_9480_;
   }

   public TickList<Block> m_6219_() {
      return this.f_9484_;
   }

   public TickList<Fluid> m_6217_() {
      return this.f_9485_;
   }

   public int m_5736_() {
      return this.f_9479_.m_5736_();
   }

   public Random m_5822_() {
      return this.f_9482_;
   }

   public int m_6924_(Heightmap.Types p_9535_, int p_9536_, int p_9537_) {
      return this.m_6325_(SectionPos.m_123171_(p_9536_), SectionPos.m_123171_(p_9537_)).m_5885_(p_9535_, p_9536_ & 15, p_9537_ & 15) + 1;
   }

   public void m_5594_(@Nullable Player p_9528_, BlockPos p_9529_, SoundEvent p_9530_, SoundSource p_9531_, float p_9532_, float p_9533_) {
   }

   public void m_7106_(ParticleOptions p_9561_, double p_9562_, double p_9563_, double p_9564_, double p_9565_, double p_9566_, double p_9567_) {
   }

   public void m_5898_(@Nullable Player p_9523_, int p_9524_, BlockPos p_9525_, int p_9526_) {
   }

   public void m_142346_(@Nullable Entity p_143490_, GameEvent p_143491_, BlockPos p_143492_) {
   }

   public DimensionType m_6042_() {
      return this.f_9483_;
   }

   public boolean m_7433_(BlockPos p_9544_, Predicate<BlockState> p_9545_) {
      return p_9545_.test(this.m_8055_(p_9544_));
   }

   public boolean m_142433_(BlockPos p_143500_, Predicate<FluidState> p_143501_) {
      return p_143501_.test(this.m_6425_(p_143500_));
   }

   public <T extends Entity> List<T> m_142425_(EntityTypeTest<Entity, T> p_143494_, AABB p_143495_, Predicate<? super T> p_143496_) {
      return Collections.emptyList();
   }

   public List<Entity> m_6249_(@Nullable Entity p_9519_, AABB p_9520_, @Nullable Predicate<? super Entity> p_9521_) {
      return Collections.emptyList();
   }

   public List<Player> m_6907_() {
      return Collections.emptyList();
   }

   public Stream<? extends StructureStart<?>> m_7526_(SectionPos p_9558_, StructureFeature<?> p_9559_) {
      return this.f_9489_.m_47289_(p_9558_, p_9559_);
   }

   public int m_141937_() {
      return this.f_9479_.m_141937_();
   }

   public int m_141928_() {
      return this.f_9479_.m_141928_();
   }
}