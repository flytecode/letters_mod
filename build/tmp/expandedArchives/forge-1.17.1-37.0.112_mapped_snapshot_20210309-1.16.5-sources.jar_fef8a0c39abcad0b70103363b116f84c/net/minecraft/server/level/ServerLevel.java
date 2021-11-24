package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddVibrationSignalPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.players.SleepStatus;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.Mth;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ReputationEventHandler;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockEventData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.ForcedChunksSavedData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerTickList;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.TickNextTickData;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.storage.EntityStorage;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.entity.EntityPersistentStorage;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListenerRegistrar;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.saveddata.maps.MapIndex;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLevel extends Level implements WorldGenLevel {
   public static final BlockPos f_8562_ = new BlockPos(100, 50, 0);
   private static final Logger f_8566_ = LogManager.getLogger();
   private static final int f_143242_ = 300;
   final List<ServerPlayer> f_8546_ = Lists.newArrayList();
   private final ServerChunkCache f_8547_;
   private final MinecraftServer f_8548_;
   private final ServerLevelData f_8549_;
   final EntityTickList f_143243_ = new EntityTickList();
   private final PersistentEntitySectionManager<Entity> f_143244_;
   public boolean f_8564_;
   private final SleepStatus f_143245_;
   private int f_8551_;
   private final PortalForcer f_8552_;
   private final ServerTickList<Block> f_8553_ = new ServerTickList<>(this, (p_8711_) -> {
      return p_8711_ == null || p_8711_.m_49966_().m_60795_();
   }, Registry.f_122824_::m_7981_, this::m_8821_);
   private final ServerTickList<Fluid> f_8554_ = new ServerTickList<>(this, (p_8728_) -> {
      return p_8728_ == null || p_8728_ == Fluids.f_76191_;
   }, Registry.f_122822_::m_7981_, this::m_8700_);
   final Set<Mob> f_143246_ = new ObjectOpenHashSet<>();
   protected final Raids f_8565_;
   private final ObjectLinkedOpenHashSet<BlockEventData> f_8556_ = new ObjectLinkedOpenHashSet<>();
   private boolean f_8557_;
   private final List<CustomSpawner> f_8558_;
   @Nullable
   private final EndDragonFight f_8559_;
   final Int2ObjectMap<net.minecraftforge.entity.PartEntity<?>> f_143247_ = new Int2ObjectOpenHashMap<>();
   private final StructureFeatureManager f_8560_;
   private final boolean f_8561_;
   private net.minecraftforge.common.util.WorldCapabilityData capabilityData;

   public ServerLevel(MinecraftServer p_8571_, Executor p_8572_, LevelStorageSource.LevelStorageAccess p_8573_, ServerLevelData p_8574_, ResourceKey<Level> p_8575_, DimensionType p_8576_, ChunkProgressListener p_8577_, ChunkGenerator p_8578_, boolean p_8579_, long p_8580_, List<CustomSpawner> p_8581_, boolean p_8582_) {
      super(p_8574_, p_8575_, p_8576_, p_8571_::m_129905_, false, p_8579_, p_8580_);
      this.f_8561_ = p_8582_;
      this.f_8548_ = p_8571_;
      this.f_8558_ = p_8581_;
      this.f_8549_ = p_8574_;
      boolean flag = p_8571_.m_6365_();
      DataFixer datafixer = p_8571_.m_129933_();
      EntityPersistentStorage<Entity> entitypersistentstorage = new EntityStorage(this, new File(p_8573_.m_78299_(p_8575_), "entities"), datafixer, flag, p_8571_);
      this.f_143244_ = new PersistentEntitySectionManager<>(Entity.class, new ServerLevel.EntityCallbacks(), entitypersistentstorage);
      this.f_8547_ = new ServerChunkCache(this, p_8573_, datafixer, p_8571_.m_129909_(), p_8572_, p_8578_, p_8571_.m_6846_().m_11312_(), flag, p_8577_, this.f_143244_::m_157524_, () -> {
         return p_8571_.m_129783_().m_8895_();
      });
      this.f_8552_ = new PortalForcer(this);
      this.m_46465_();
      this.m_46466_();
      this.m_6857_().m_61923_(p_8571_.m_6329_());
      this.f_8565_ = this.m_8895_().m_164861_((p_143314_) -> {
         return Raids.m_150235_(this, p_143314_);
      }, () -> {
         return new Raids(this);
      }, Raids.m_37968_(this.m_6042_()));
      if (!p_8571_.m_129792_()) {
         p_8574_.m_5458_(p_8571_.m_130008_());
      }

      this.f_8560_ = new StructureFeatureManager(this, p_8571_.m_129910_().m_5961_());
      if (this.m_6042_().m_63965_()) {
         this.f_8559_ = new EndDragonFight(this, p_8571_.m_129910_().m_5961_().m_64619_(), p_8571_.m_129910_().m_6564_());
      } else {
         this.f_8559_ = null;
      }

      this.f_143245_ = new SleepStatus();
      this.initCapabilities();
   }

   public void m_8606_(int p_8607_, int p_8608_, boolean p_8609_, boolean p_8610_) {
      this.f_8549_.m_6393_(p_8607_);
      this.f_8549_.m_6399_(p_8608_);
      this.f_8549_.m_6398_(p_8608_);
      this.f_8549_.m_5565_(p_8609_);
      this.f_8549_.m_5557_(p_8610_);
   }

   public Biome m_6159_(int p_8599_, int p_8600_, int p_8601_) {
      return this.m_7726_().m_8481_().m_62218_().m_7158_(p_8599_, p_8600_, p_8601_);
   }

   public StructureFeatureManager m_8595_() {
      return this.f_8560_;
   }

   public void m_8793_(BooleanSupplier p_8794_) {
      ProfilerFiller profilerfiller = this.m_46473_();
      this.f_8557_ = true;
      profilerfiller.m_6180_("world border");
      this.m_6857_().m_61969_();
      profilerfiller.m_6182_("weather");
      boolean flag = this.m_46471_();
      if (this.m_6042_().m_63935_()) {
         if (this.m_46469_().m_46207_(GameRules.f_46150_)) {
            int i = this.f_8549_.m_6537_();
            int j = this.f_8549_.m_6558_();
            int k = this.f_8549_.m_6531_();
            boolean flag1 = this.f_46442_.m_6534_();
            boolean flag2 = this.f_46442_.m_6533_();
            if (i > 0) {
               --i;
               j = flag1 ? 0 : 1;
               k = flag2 ? 0 : 1;
               flag1 = false;
               flag2 = false;
            } else {
               if (j > 0) {
                  --j;
                  if (j == 0) {
                     flag1 = !flag1;
                  }
               } else if (flag1) {
                  j = this.f_46441_.nextInt(12000) + 3600;
               } else {
                  j = this.f_46441_.nextInt(168000) + 12000;
               }

               if (k > 0) {
                  --k;
                  if (k == 0) {
                     flag2 = !flag2;
                  }
               } else if (flag2) {
                  k = this.f_46441_.nextInt(12000) + 12000;
               } else {
                  k = this.f_46441_.nextInt(168000) + 12000;
               }
            }

            this.f_8549_.m_6398_(j);
            this.f_8549_.m_6399_(k);
            this.f_8549_.m_6393_(i);
            this.f_8549_.m_5557_(flag1);
            this.f_8549_.m_5565_(flag2);
         }

         this.f_46439_ = this.f_46440_;
         if (this.f_46442_.m_6534_()) {
            this.f_46440_ = (float)((double)this.f_46440_ + 0.01D);
         } else {
            this.f_46440_ = (float)((double)this.f_46440_ - 0.01D);
         }

         this.f_46440_ = Mth.m_14036_(this.f_46440_, 0.0F, 1.0F);
         this.f_46437_ = this.f_46438_;
         if (this.f_46442_.m_6533_()) {
            this.f_46438_ = (float)((double)this.f_46438_ + 0.01D);
         } else {
            this.f_46438_ = (float)((double)this.f_46438_ - 0.01D);
         }

         this.f_46438_ = Mth.m_14036_(this.f_46438_, 0.0F, 1.0F);
      }

      if (this.f_46437_ != this.f_46438_) {
         this.f_8548_.m_6846_().m_11270_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132160_, this.f_46438_), this.m_46472_());
      }

      if (this.f_46439_ != this.f_46440_) {
         this.f_8548_.m_6846_().m_11270_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132161_, this.f_46440_), this.m_46472_());
      }

      /* The function in use here has been replaced in order to only send the weather info to players in the correct dimension,
       * rather than to all players on the server. This is what causes the client-side rain, as the
       * client believes that it has started raining locally, rather than in another dimension.
       */
      if (flag != this.m_46471_()) {
         if (flag) {
            this.f_8548_.m_6846_().m_11270_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132155_, 0.0F), this.m_46472_());
         } else {
            this.f_8548_.m_6846_().m_11270_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132154_, 0.0F), this.m_46472_());
         }

         this.f_8548_.m_6846_().m_11270_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132160_, this.f_46438_), this.m_46472_());
         this.f_8548_.m_6846_().m_11270_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132161_, this.f_46440_), this.m_46472_());
      }

      int l = this.m_46469_().m_46215_(GameRules.f_151486_);
      if (this.f_143245_.m_144002_(l) && this.f_143245_.m_144004_(l, this.f_8546_)) {
         if (this.m_46469_().m_46207_(GameRules.f_46140_)) {
            long k = this.m_46468_() + 24000L;
            this.m_8615_(net.minecraftforge.event.ForgeEventFactory.onSleepFinished(this, k - k % 24000L, this.m_46468_()));
         }

         this.m_8804_();
         if (this.m_46469_().m_46207_(GameRules.f_46150_)) {
            this.m_8805_();
         }
      }

      this.m_46465_();
      this.m_8809_();
      profilerfiller.m_6182_("tickPending");
      if (!this.m_46659_()) {
         this.f_8553_.m_47253_();
         this.f_8554_.m_47253_();
      }

      profilerfiller.m_6182_("raid");
      this.f_8565_.m_37957_();
      profilerfiller.m_6182_("chunkSource");
      this.m_7726_().m_142483_(p_8794_);
      profilerfiller.m_6182_("blockEvents");
      this.m_8807_();
      this.f_8557_ = false;
      profilerfiller.m_7238_();
      boolean flag3 = !this.f_8546_.isEmpty() || net.minecraftforge.common.world.ForgeChunkManager.hasForcedChunks(this); //Forge: Replace vanilla's has forced chunk check with forge's that checks both the vanilla and forge added ones
      if (flag3) {
         this.m_8886_();
      }

      if (flag3 || this.f_8551_++ < 300) {
         profilerfiller.m_6180_("entities");
         if (this.f_8559_ != null) {
            profilerfiller.m_6180_("dragonFight");
            this.f_8559_.m_64095_();
            profilerfiller.m_7238_();
         }

         this.f_143243_.m_156910_((p_143266_) -> {
            if (!p_143266_.m_146910_()) {
               if (this.m_143342_(p_143266_)) {
                  p_143266_.m_146870_();
               } else {
                  profilerfiller.m_6180_("checkDespawn");
                  p_143266_.m_6043_();
                  profilerfiller.m_7238_();
                  Entity entity = p_143266_.m_20202_();
                  if (entity != null) {
                     if (!entity.m_146910_() && entity.m_20363_(p_143266_)) {
                        return;
                     }

                     p_143266_.m_8127_();
                  }

                  profilerfiller.m_6180_("tick");
                  if (!p_143266_.m_146910_() && !(p_143266_ instanceof net.minecraftforge.entity.PartEntity)) {
                     this.m_46653_(this::m_8647_, p_143266_);
                  }
                  profilerfiller.m_7238_();
               }
            }
         });
         profilerfiller.m_7238_();
         this.m_46463_();
      }

      profilerfiller.m_6180_("entityManagement");
      this.f_143244_.m_157506_();
      profilerfiller.m_7238_();
   }

   protected void m_8809_() {
      if (this.f_8561_) {
         long i = this.f_46442_.m_6793_() + 1L;
         this.f_8549_.m_6253_(i);
         this.f_8549_.m_7540_().m_82256_(this.f_8548_, i);
         if (this.f_46442_.m_5470_().m_46207_(GameRules.f_46140_)) {
            this.m_8615_(this.f_46442_.m_6792_() + 1L);
         }

      }
   }

   public void m_8615_(long p_8616_) {
      this.f_8549_.m_6247_(p_8616_);
   }

   public void m_8799_(boolean p_8800_, boolean p_8801_) {
      for(CustomSpawner customspawner : this.f_8558_) {
         customspawner.m_7995_(this, p_8800_, p_8801_);
      }

   }

   private boolean m_143342_(Entity p_143343_) {
      if (this.f_8548_.m_6998_() || !(p_143343_ instanceof Animal) && !(p_143343_ instanceof WaterAnimal)) {
         return !this.f_8548_.m_6997_() && p_143343_ instanceof Npc;
      } else {
         return true;
      }
   }

   private void m_8804_() {
      this.f_143245_.m_144001_();
      this.f_8546_.stream().filter(LivingEntity::m_5803_).collect(Collectors.toList()).forEach((p_143339_) -> {
         p_143339_.m_6145_(false, false);
      });
   }

   public void m_8714_(LevelChunk p_8715_, int p_8716_) {
      ChunkPos chunkpos = p_8715_.m_7697_();
      boolean flag = this.m_46471_();
      int i = chunkpos.m_45604_();
      int j = chunkpos.m_45605_();
      ProfilerFiller profilerfiller = this.m_46473_();
      profilerfiller.m_6180_("thunder");
      if (flag && this.m_46470_() && this.f_46441_.nextInt(100000) == 0) {
         BlockPos blockpos = this.m_143288_(this.m_46496_(i, 0, j, 15));
         if (this.m_46758_(blockpos)) {
            DifficultyInstance difficultyinstance = this.m_6436_(blockpos);
            boolean flag1 = this.m_46469_().m_46207_(GameRules.f_46134_) && this.f_46441_.nextDouble() < (double)difficultyinstance.m_19056_() * 0.01D && !this.m_8055_(blockpos.m_7495_()).m_60713_(Blocks.f_152587_);
            if (flag1) {
               SkeletonHorse skeletonhorse = EntityType.f_20525_.m_20615_(this);
               skeletonhorse.m_30923_(true);
               skeletonhorse.m_146762_(0);
               skeletonhorse.m_6034_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_());
               this.m_7967_(skeletonhorse);
            }

            LightningBolt lightningbolt = EntityType.f_20465_.m_20615_(this);
            lightningbolt.m_20219_(Vec3.m_82539_(blockpos));
            lightningbolt.m_20874_(flag1);
            this.m_7967_(lightningbolt);
         }
      }

      profilerfiller.m_6182_("iceandsnow");
      if (this.f_46441_.nextInt(16) == 0) {
         BlockPos blockpos2 = this.m_5452_(Heightmap.Types.MOTION_BLOCKING, this.m_46496_(i, 0, j, 15));
         BlockPos blockpos3 = blockpos2.m_7495_();
         Biome biome = this.m_46857_(blockpos2);
         if (this.isAreaLoaded(blockpos2, 1)) // Forge: check area to avoid loading neighbors in unloaded chunks
         if (biome.m_47477_(this, blockpos3)) {
            this.m_46597_(blockpos3, Blocks.f_50126_.m_49966_());
         }

         if (flag) {
            if (biome.m_47519_(this, blockpos2)) {
               this.m_46597_(blockpos2, Blocks.f_50125_.m_49966_());
            }

            BlockState blockstate1 = this.m_8055_(blockpos3);
            Biome.Precipitation biome$precipitation = this.m_46857_(blockpos2).m_47530_();
            if (biome$precipitation == Biome.Precipitation.RAIN && biome.m_151696_(blockpos3)) {
               biome$precipitation = Biome.Precipitation.SNOW;
            }

            blockstate1.m_60734_().m_141997_(blockstate1, this, blockpos3, biome$precipitation);
         }
      }

      profilerfiller.m_6182_("tickBlocks");
      if (p_8716_ > 0) {
         for(LevelChunkSection levelchunksection : p_8715_.m_7103_()) {
            if (levelchunksection != LevelChunk.f_62770_ && levelchunksection.m_63014_()) {
               int l = levelchunksection.m_63017_();

               for(int k = 0; k < p_8716_; ++k) {
                  BlockPos blockpos1 = this.m_46496_(i, l, j, 15);
                  profilerfiller.m_6180_("randomTick");
                  BlockState blockstate = levelchunksection.m_62982_(blockpos1.m_123341_() - i, blockpos1.m_123342_() - l, blockpos1.m_123343_() - j);
                  if (blockstate.m_60823_()) {
                     blockstate.m_60735_(this, blockpos1, this.f_46441_);
                  }

                  FluidState fluidstate = blockstate.m_60819_();
                  if (fluidstate.m_76187_()) {
                     fluidstate.m_76174_(this, blockpos1, this.f_46441_);
                  }

                  profilerfiller.m_7238_();
               }
            }
         }
      }

      profilerfiller.m_7238_();
   }

   private Optional<BlockPos> m_143248_(BlockPos p_143249_) {
      Optional<BlockPos> optional = this.m_8904_().m_148658_((p_143274_) -> {
         return p_143274_ == PoiType.f_148687_;
      }, (p_143255_) -> {
         return p_143255_.m_123342_() == this.m_6018_().m_6924_(Heightmap.Types.WORLD_SURFACE, p_143255_.m_123341_(), p_143255_.m_123343_()) - 1;
      }, p_143249_, 128, PoiManager.Occupancy.ANY);
      return optional.map((p_143253_) -> {
         return p_143253_.m_6630_(1);
      });
   }

   protected BlockPos m_143288_(BlockPos p_143289_) {
      BlockPos blockpos = this.m_5452_(Heightmap.Types.MOTION_BLOCKING, p_143289_);
      Optional<BlockPos> optional = this.m_143248_(blockpos);
      if (optional.isPresent()) {
         return optional.get();
      } else {
         AABB aabb = (new AABB(blockpos, new BlockPos(blockpos.m_123341_(), this.m_151558_(), blockpos.m_123343_()))).m_82400_(3.0D);
         List<LivingEntity> list = this.m_6443_(LivingEntity.class, aabb, (p_143272_) -> {
            return p_143272_ != null && p_143272_.m_6084_() && this.m_45527_(p_143272_.m_142538_());
         });
         if (!list.isEmpty()) {
            return list.get(this.f_46441_.nextInt(list.size())).m_142538_();
         } else {
            if (blockpos.m_123342_() == this.m_141937_() - 1) {
               blockpos = blockpos.m_6630_(2);
            }

            return blockpos;
         }
      }
   }

   public boolean m_8874_() {
      return this.f_8557_;
   }

   public boolean m_143333_() {
      return this.m_46469_().m_46215_(GameRules.f_151486_) <= 100;
   }

   private void m_143315_() {
      if (this.m_143333_()) {
         if (!this.m_142572_().m_129792_() || this.m_142572_().m_6992_()) {
            int i = this.m_46469_().m_46215_(GameRules.f_151486_);
            Component component;
            if (this.f_143245_.m_144002_(i)) {
               component = new TranslatableComponent("sleep.skipping_night");
            } else {
               component = new TranslatableComponent("sleep.players_sleeping", this.f_143245_.m_144009_(), this.f_143245_.m_144010_(i));
            }

            for(ServerPlayer serverplayer : this.f_8546_) {
               serverplayer.m_5661_(component, true);
            }

         }
      }
   }

   public void m_8878_() {
      if (!this.f_8546_.isEmpty() && this.f_143245_.m_144007_(this.f_8546_)) {
         this.m_143315_();
      }

   }

   public ServerScoreboard m_6188_() {
      return this.f_8548_.m_129896_();
   }

   private void m_8805_() {
      this.f_8549_.m_6399_(0);
      this.f_8549_.m_5565_(false);
      this.f_8549_.m_6398_(0);
      this.f_8549_.m_5557_(false);
   }

   public void m_8886_() {
      this.f_8551_ = 0;
   }

   private void m_8700_(TickNextTickData<Fluid> p_8701_) {
      FluidState fluidstate = this.m_6425_(p_8701_.f_47323_);
      if (fluidstate.m_76152_() == p_8701_.m_47340_()) {
         fluidstate.m_76163_(this, p_8701_.f_47323_);
      }

   }

   private void m_8821_(TickNextTickData<Block> p_8822_) {
      BlockState blockstate = this.m_8055_(p_8822_.f_47323_);
      if (blockstate.m_60713_(p_8822_.m_47340_())) {
         blockstate.m_60616_(this, p_8822_.f_47323_, this.f_46441_);
      }

   }

   public void m_8647_(Entity p_8648_) {
      p_8648_.m_146867_();
      ProfilerFiller profilerfiller = this.m_46473_();
      ++p_8648_.f_19797_;
      this.m_46473_().m_6521_(() -> {
         return Registry.f_122826_.m_7981_(p_8648_.m_6095_()).toString();
      });
      profilerfiller.m_6174_("tickNonPassenger");
      p_8648_.m_8119_();
      this.m_46473_().m_7238_();

      for(Entity entity : p_8648_.m_20197_()) {
         this.m_8662_(p_8648_, entity);
      }

   }

   private void m_8662_(Entity p_8663_, Entity p_8664_) {
      if (!p_8664_.m_146910_() && p_8664_.m_20202_() == p_8663_) {
         if (p_8664_ instanceof Player || this.f_143243_.m_156914_(p_8664_)) {
            p_8664_.m_146867_();
            ++p_8664_.f_19797_;
            ProfilerFiller profilerfiller = this.m_46473_();
            profilerfiller.m_6521_(() -> {
               return p_8664_.m_6095_().getRegistryName() == null ? p_8664_.m_6095_().toString() : p_8664_.m_6095_().getRegistryName().toString();
            });
            profilerfiller.m_6174_("tickPassenger");
            if (p_8664_.canUpdate())
            p_8664_.m_6083_();
            profilerfiller.m_7238_();

            for(Entity entity : p_8664_.m_20197_()) {
               this.m_8662_(p_8664_, entity);
            }

         }
      } else {
         p_8664_.m_8127_();
      }
   }

   public boolean m_7966_(Player p_8696_, BlockPos p_8697_) {
      return !this.f_8548_.m_7762_(this, p_8697_, p_8696_) && this.m_6857_().m_61937_(p_8697_);
   }

   public void m_8643_(@Nullable ProgressListener p_8644_, boolean p_8645_, boolean p_8646_) {
      ServerChunkCache serverchunkcache = this.m_7726_();
      if (!p_8646_) {
         if (p_8644_ != null) {
            p_8644_.m_6309_(new TranslatableComponent("menu.savingLevel"));
         }

         this.m_8806_();
         if (p_8644_ != null) {
            p_8644_.m_6307_(new TranslatableComponent("menu.savingChunks"));
         }

         serverchunkcache.m_8419_(p_8645_);
         if (p_8645_) {
            this.f_143244_.m_157561_();
         } else {
            this.f_143244_.m_157554_();
         }

         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.WorldEvent.Save(this));
      }
   }

   private void m_8806_() {
      if (this.f_8559_ != null) {
         this.f_8548_.m_129910_().m_5915_(this.f_8559_.m_64081_());
      }

      this.m_7726_().m_8483_().m_78151_();
   }

   public <T extends Entity> List<? extends T> m_143280_(EntityTypeTest<Entity, T> p_143281_, Predicate<? super T> p_143282_) {
      List<T> list = Lists.newArrayList();
      this.m_142646_().m_142690_(p_143281_, (p_143310_) -> {
         if (p_143282_.test(p_143310_)) {
            list.add(p_143310_);
         }

      });
      return list;
   }

   public List<? extends EnderDragon> m_8857_() {
      return this.m_143280_(EntityType.f_20565_, LivingEntity::m_6084_);
   }

   public List<ServerPlayer> m_8795_(Predicate<? super ServerPlayer> p_8796_) {
      List<ServerPlayer> list = Lists.newArrayList();

      for(ServerPlayer serverplayer : this.f_8546_) {
         if (p_8796_.test(serverplayer)) {
            list.add(serverplayer);
         }
      }

      return list;
   }

   @Nullable
   public ServerPlayer m_8890_() {
      List<ServerPlayer> list = this.m_8795_(LivingEntity::m_6084_);
      return list.isEmpty() ? null : list.get(this.f_46441_.nextInt(list.size()));
   }

   public boolean m_7967_(Entity p_8837_) {
      return this.m_8872_(p_8837_);
   }

   public boolean m_8847_(Entity p_8848_) {
      return this.m_8872_(p_8848_);
   }

   public void m_143334_(Entity p_143335_) {
      this.m_8872_(p_143335_);
   }

   public void m_8622_(ServerPlayer p_8623_) {
      this.m_8853_(p_8623_);
   }

   public void m_8817_(ServerPlayer p_8818_) {
      this.m_8853_(p_8818_);
   }

   public void m_8834_(ServerPlayer p_8835_) {
      this.m_8853_(p_8835_);
   }

   public void m_8845_(ServerPlayer p_8846_) {
      this.m_8853_(p_8846_);
   }

   public void removePlayer(ServerPlayer p_8850_, boolean keepData) {
      p_8850_.m_146870_();
      this.removeEntity(p_8850_, keepData);
   }

   public void removeEntityComplete(Entity p_8865_, boolean keepData) {
      if(p_8865_.isMultipartEntity()) {
         for(net.minecraftforge.entity.PartEntity<?> parts : p_8865_.getParts()) {
            parts.m_146870_();
         }
      }

      this.m_7726_().m_8443_(p_8865_);
      if (p_8865_ instanceof ServerPlayer) {
         ServerPlayer serverplayerentity = (ServerPlayer)p_8865_;
         this.f_8546_.remove(serverplayerentity);
      }

      this.m_6188_().m_83420_(p_8865_);
      if (p_8865_ instanceof Mob) {
         this.f_143246_.remove(((Mob)p_8865_).m_21573_());
      }

      p_8865_.onRemovedFromWorld();
      p_8865_.m_146870_();
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.EntityLeaveWorldEvent(p_8865_, this));
   }

   public void removeEntity(Entity entity) {
      removeEntity(entity, false);
   }

   public void removeEntity(Entity p_8868_, boolean keepData) {
      if (this.f_8557_) {
         throw (IllegalStateException) net.minecraft.Util.m_137570_(new IllegalStateException("Removing entity while ticking!"));
      } else {
         removeEntityComplete(p_8868_, keepData);
      }
   }

   private void m_8853_(ServerPlayer p_8854_) {
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.EntityJoinWorldEvent(p_8854_, this))) return;
      Entity entity = this.m_142646_().m_142694_(p_8854_.m_142081_());
      if (entity != null) {
         f_8566_.warn("Force-added player with duplicate UUID {}", (Object)p_8854_.m_142081_().toString());
         entity.m_19877_();
         this.m_143261_((ServerPlayer)entity, Entity.RemovalReason.DISCARDED);
      }

      this.f_143244_.addNewEntityWithoutEvent(p_8854_);
      p_8854_.onAddedToWorld();
   }

   private boolean m_8872_(Entity p_8873_) {
      if (p_8873_.m_146910_()) {
         f_8566_.warn("Tried to add entity {} but it was marked as removed already", (Object)EntityType.m_20613_(p_8873_.m_6095_()));
         return false;
      } else {
         if (this.f_143244_.m_157533_(p_8873_)) {
            p_8873_.onAddedToWorld();
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean m_8860_(Entity p_8861_) {
      if (p_8861_.m_142428_().map(Entity::m_142081_).anyMatch(this.f_143244_::m_157550_)) {
         return false;
      } else {
         this.m_47205_(p_8861_);
         return true;
      }
   }

   public void m_8712_(LevelChunk p_8713_) {
      p_8713_.m_156368_();
   }

   public void m_143261_(ServerPlayer p_143262_, Entity.RemovalReason p_143263_) {
      p_143262_.m_142687_(p_143263_);
   }

   public void m_6801_(int p_8612_, BlockPos p_8613_, int p_8614_) {
      for(ServerPlayer serverplayer : this.f_8548_.m_6846_().m_11314_()) {
         if (serverplayer != null && serverplayer.f_19853_ == this && serverplayer.m_142049_() != p_8612_) {
            double d0 = (double)p_8613_.m_123341_() - serverplayer.m_20185_();
            double d1 = (double)p_8613_.m_123342_() - serverplayer.m_20186_();
            double d2 = (double)p_8613_.m_123343_() - serverplayer.m_20189_();
            if (d0 * d0 + d1 * d1 + d2 * d2 < 1024.0D) {
               serverplayer.f_8906_.m_141995_(new ClientboundBlockDestructionPacket(p_8612_, p_8613_, p_8614_));
            }
         }
      }

   }

   public void m_6263_(@Nullable Player p_8675_, double p_8676_, double p_8677_, double p_8678_, SoundEvent p_8679_, SoundSource p_8680_, float p_8681_, float p_8682_) {
      net.minecraftforge.event.entity.PlaySoundAtEntityEvent event = net.minecraftforge.event.ForgeEventFactory.onPlaySoundAtEntity(p_8675_, p_8679_, p_8680_, p_8681_, p_8682_);
      if (event.isCanceled() || event.getSound() == null) return;
      p_8679_ = event.getSound();
      p_8680_ = event.getCategory();
      p_8681_ = event.getVolume();
      this.f_8548_.m_6846_().m_11241_(p_8675_, p_8676_, p_8677_, p_8678_, p_8681_ > 1.0F ? (double)(16.0F * p_8681_) : 16.0D, this.m_46472_(), new ClientboundSoundPacket(p_8679_, p_8680_, p_8676_, p_8677_, p_8678_, p_8681_, p_8682_));
   }

   public void m_6269_(@Nullable Player p_8689_, Entity p_8690_, SoundEvent p_8691_, SoundSource p_8692_, float p_8693_, float p_8694_) {
      net.minecraftforge.event.entity.PlaySoundAtEntityEvent event = net.minecraftforge.event.ForgeEventFactory.onPlaySoundAtEntity(p_8689_, p_8691_, p_8692_, p_8693_, p_8694_);
      if (event.isCanceled() || event.getSound() == null) return;
      p_8691_ = event.getSound();
      p_8692_ = event.getCategory();
      p_8693_ = event.getVolume();
      this.f_8548_.m_6846_().m_11241_(p_8689_, p_8690_.m_20185_(), p_8690_.m_20186_(), p_8690_.m_20189_(), p_8693_ > 1.0F ? (double)(16.0F * p_8693_) : 16.0D, this.m_46472_(), new ClientboundSoundEntityPacket(p_8691_, p_8692_, p_8690_, p_8693_, p_8694_));
   }

   public void m_6798_(int p_8811_, BlockPos p_8812_, int p_8813_) {
      this.f_8548_.m_6846_().m_11268_(new ClientboundLevelEventPacket(p_8811_, p_8812_, p_8813_, true));
   }

   public void m_5898_(@Nullable Player p_8684_, int p_8685_, BlockPos p_8686_, int p_8687_) {
      this.f_8548_.m_6846_().m_11241_(p_8684_, (double)p_8686_.m_123341_(), (double)p_8686_.m_123342_(), (double)p_8686_.m_123343_(), 64.0D, this.m_46472_(), new ClientboundLevelEventPacket(p_8685_, p_8686_, p_8687_, false));
   }

   public int m_142475_() {
      return this.m_6042_().m_63964_();
   }

   public void m_142346_(@Nullable Entity p_143268_, GameEvent p_143269_, BlockPos p_143270_) {
      this.m_151513_(p_143268_, p_143269_, p_143270_, p_143269_.m_157827_());
   }

   public void m_7260_(BlockPos p_8755_, BlockState p_8756_, BlockState p_8757_, int p_8758_) {
      this.m_7726_().m_8450_(p_8755_);
      VoxelShape voxelshape = p_8756_.m_60812_(this, p_8755_);
      VoxelShape voxelshape1 = p_8757_.m_60812_(this, p_8755_);
      if (Shapes.m_83157_(voxelshape, voxelshape1, BooleanOp.f_82687_)) {
         for(Mob mob : this.f_143246_) {
            PathNavigation pathnavigation = mob.m_21573_();
            if (!pathnavigation.m_26568_()) {
               pathnavigation.m_26561_(p_8755_);
            }
         }

      }
   }

   public void m_7605_(Entity p_8650_, byte p_8651_) {
      this.m_7726_().m_8394_(p_8650_, new ClientboundEntityEventPacket(p_8650_, p_8651_));
   }

   public ServerChunkCache m_7726_() {
      return this.f_8547_;
   }

   public Explosion m_7703_(@Nullable Entity p_8653_, @Nullable DamageSource p_8654_, @Nullable ExplosionDamageCalculator p_8655_, double p_8656_, double p_8657_, double p_8658_, float p_8659_, boolean p_8660_, Explosion.BlockInteraction p_8661_) {
      Explosion explosion = new Explosion(this, p_8653_, p_8654_, p_8655_, p_8656_, p_8657_, p_8658_, p_8659_, p_8660_, p_8661_);
      if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this, explosion)) return explosion;
      explosion.m_46061_();
      explosion.m_46075_(false);
      if (p_8661_ == Explosion.BlockInteraction.NONE) {
         explosion.m_46080_();
      }

      for(ServerPlayer serverplayer : this.f_8546_) {
         if (serverplayer.m_20275_(p_8656_, p_8657_, p_8658_) < 4096.0D) {
            serverplayer.f_8906_.m_141995_(new ClientboundExplodePacket(p_8656_, p_8657_, p_8658_, p_8659_, explosion.m_46081_(), explosion.m_46078_().get(serverplayer)));
         }
      }

      return explosion;
   }

   public void m_7696_(BlockPos p_8746_, Block p_8747_, int p_8748_, int p_8749_) {
      this.f_8556_.add(new BlockEventData(p_8746_, p_8747_, p_8748_, p_8749_));
   }

   private void m_8807_() {
      while(!this.f_8556_.isEmpty()) {
         BlockEventData blockeventdata = this.f_8556_.removeFirst();
         if (this.m_8698_(blockeventdata)) {
            this.f_8548_.m_6846_().m_11241_((Player)null, (double)blockeventdata.m_45538_().m_123341_(), (double)blockeventdata.m_45538_().m_123342_(), (double)blockeventdata.m_45538_().m_123343_(), 64.0D, this.m_46472_(), new ClientboundBlockEventPacket(blockeventdata.m_45538_(), blockeventdata.m_45539_(), blockeventdata.m_45540_(), blockeventdata.m_45541_()));
         }
      }

   }

   private boolean m_8698_(BlockEventData p_8699_) {
      BlockState blockstate = this.m_8055_(p_8699_.m_45538_());
      return blockstate.m_60713_(p_8699_.m_45539_()) ? blockstate.m_60677_(this, p_8699_.m_45538_(), p_8699_.m_45540_(), p_8699_.m_45541_()) : false;
   }

   public ServerTickList<Block> m_6219_() {
      return this.f_8553_;
   }

   public ServerTickList<Fluid> m_6217_() {
      return this.f_8554_;
   }

   @Nonnull
   public MinecraftServer m_142572_() {
      return this.f_8548_;
   }

   public PortalForcer m_8871_() {
      return this.f_8552_;
   }

   public StructureManager m_8875_() {
      return this.f_8548_.m_129909_();
   }

   public void m_143283_(VibrationPath p_143284_) {
      BlockPos blockpos = p_143284_.m_157948_();
      ClientboundAddVibrationSignalPacket clientboundaddvibrationsignalpacket = new ClientboundAddVibrationSignalPacket(p_143284_);
      this.f_8546_.forEach((p_143296_) -> {
         this.m_8636_(p_143296_, false, (double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_(), clientboundaddvibrationsignalpacket);
      });
   }

   public <T extends ParticleOptions> int m_8767_(T p_8768_, double p_8769_, double p_8770_, double p_8771_, int p_8772_, double p_8773_, double p_8774_, double p_8775_, double p_8776_) {
      ClientboundLevelParticlesPacket clientboundlevelparticlespacket = new ClientboundLevelParticlesPacket(p_8768_, false, p_8769_, p_8770_, p_8771_, (float)p_8773_, (float)p_8774_, (float)p_8775_, (float)p_8776_, p_8772_);
      int i = 0;

      for(int j = 0; j < this.f_8546_.size(); ++j) {
         ServerPlayer serverplayer = this.f_8546_.get(j);
         if (this.m_8636_(serverplayer, false, p_8769_, p_8770_, p_8771_, clientboundlevelparticlespacket)) {
            ++i;
         }
      }

      return i;
   }

   public <T extends ParticleOptions> boolean m_8624_(ServerPlayer p_8625_, T p_8626_, boolean p_8627_, double p_8628_, double p_8629_, double p_8630_, int p_8631_, double p_8632_, double p_8633_, double p_8634_, double p_8635_) {
      Packet<?> packet = new ClientboundLevelParticlesPacket(p_8626_, p_8627_, p_8628_, p_8629_, p_8630_, (float)p_8632_, (float)p_8633_, (float)p_8634_, (float)p_8635_, p_8631_);
      return this.m_8636_(p_8625_, p_8627_, p_8628_, p_8629_, p_8630_, packet);
   }

   private boolean m_8636_(ServerPlayer p_8637_, boolean p_8638_, double p_8639_, double p_8640_, double p_8641_, Packet<?> p_8642_) {
      if (p_8637_.m_9236_() != this) {
         return false;
      } else {
         BlockPos blockpos = p_8637_.m_142538_();
         if (blockpos.m_123306_(new Vec3(p_8639_, p_8640_, p_8641_), p_8638_ ? 512.0D : 32.0D)) {
            p_8637_.f_8906_.m_141995_(p_8642_);
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   public Entity m_6815_(int p_8597_) {
      return this.m_142646_().m_142597_(p_8597_);
   }

   @Deprecated
   @Nullable
   public Entity m_143317_(int p_143318_) {
      Entity entity = this.m_142646_().m_142597_(p_143318_);
      return entity != null ? entity : this.f_143247_.get(p_143318_);
   }

   @Nullable
   public Entity m_8791_(UUID p_8792_) {
      return this.m_142646_().m_142694_(p_8792_);
   }

   @Nullable
   public BlockPos m_8717_(StructureFeature<?> p_8718_, BlockPos p_8719_, int p_8720_, boolean p_8721_) {
      return !this.f_8548_.m_129910_().m_5961_().m_64657_() ? null : this.m_7726_().m_8481_().m_62161_(this, p_8718_, p_8719_, p_8720_, p_8721_);
   }

   @Nullable
   public BlockPos m_8705_(Biome p_8706_, BlockPos p_8707_, int p_8708_, int p_8709_) {
      return this.m_7726_().m_8481_().m_62218_().m_7754_(p_8707_.m_123341_(), p_8707_.m_123342_(), p_8707_.m_123343_(), p_8708_, p_8709_, (p_143279_) -> {
         return p_143279_ == p_8706_;
      }, this.f_46441_, true);
   }

   public RecipeManager m_7465_() {
      return this.f_8548_.m_129894_();
   }

   public TagContainer m_5999_() {
      return this.f_8548_.m_129895_();
   }

   public boolean m_7441_() {
      return this.f_8564_;
   }

   public RegistryAccess m_5962_() {
      return this.f_8548_.m_129911_();
   }

   public DimensionDataStorage m_8895_() {
      return this.m_7726_().m_8483_();
   }

   @Nullable
   public MapItemSavedData m_7489_(String p_8785_) {
      return this.m_142572_().m_129783_().m_8895_().m_164858_(MapItemSavedData::m_164807_, p_8785_);
   }

   public void m_142325_(String p_143305_, MapItemSavedData p_143306_) {
      this.m_142572_().m_129783_().m_8895_().m_164855_(p_143305_, p_143306_);
   }

   public int m_7354_() {
      return this.m_142572_().m_129783_().m_8895_().m_164861_(MapIndex::m_164762_, MapIndex::new, "idcounts").m_77880_();
   }

   public void m_8733_(BlockPos p_8734_, float p_8735_) {
      ChunkPos chunkpos = new ChunkPos(new BlockPos(this.f_46442_.m_6789_(), 0, this.f_46442_.m_6526_()));
      this.f_46442_.m_7250_(p_8734_, p_8735_);
      this.m_7726_().m_8438_(TicketType.f_9442_, chunkpos, 11, Unit.INSTANCE);
      this.m_7726_().m_8387_(TicketType.f_9442_, new ChunkPos(p_8734_), 11, Unit.INSTANCE);
      this.m_142572_().m_6846_().m_11268_(new ClientboundSetDefaultSpawnPositionPacket(p_8734_, p_8735_));
   }

   public BlockPos m_8900_() {
      BlockPos blockpos = new BlockPos(this.f_46442_.m_6789_(), this.f_46442_.m_6527_(), this.f_46442_.m_6526_());
      if (!this.m_6857_().m_61937_(blockpos)) {
         blockpos = this.m_5452_(Heightmap.Types.MOTION_BLOCKING, new BlockPos(this.m_6857_().m_6347_(), 0.0D, this.m_6857_().m_6345_()));
      }

      return blockpos;
   }

   public float m_8901_() {
      return this.f_46442_.m_6790_();
   }

   public LongSet m_8902_() {
      ForcedChunksSavedData forcedchunkssaveddata = this.m_8895_().m_164858_(ForcedChunksSavedData::m_151483_, "chunks");
      return (LongSet)(forcedchunkssaveddata != null ? LongSets.unmodifiable(forcedchunkssaveddata.m_46116_()) : LongSets.EMPTY_SET);
   }

   public boolean m_8602_(int p_8603_, int p_8604_, boolean p_8605_) {
      ForcedChunksSavedData forcedchunkssaveddata = this.m_8895_().m_164861_(ForcedChunksSavedData::m_151483_, ForcedChunksSavedData::new, "chunks");
      ChunkPos chunkpos = new ChunkPos(p_8603_, p_8604_);
      long i = chunkpos.m_45588_();
      boolean flag;
      if (p_8605_) {
         flag = forcedchunkssaveddata.m_46116_().add(i);
         if (flag) {
            this.m_6325_(p_8603_, p_8604_);
         }
      } else {
         flag = forcedchunkssaveddata.m_46116_().remove(i);
      }

      forcedchunkssaveddata.m_77760_(flag);
      if (flag) {
         this.m_7726_().m_6692_(chunkpos, p_8605_);
      }

      return flag;
   }

   public List<ServerPlayer> m_6907_() {
      return this.f_8546_;
   }

   public void m_6559_(BlockPos p_8751_, BlockState p_8752_, BlockState p_8753_) {
      Optional<PoiType> optional = PoiType.m_27390_(p_8752_);
      Optional<PoiType> optional1 = PoiType.m_27390_(p_8753_);
      if (!Objects.equals(optional, optional1)) {
         BlockPos blockpos = p_8751_.m_7949_();
         optional.ifPresent((p_143331_) -> {
            this.m_142572_().execute(() -> {
               this.m_8904_().m_27079_(blockpos);
               DebugPackets.m_133716_(this, blockpos);
            });
         });
         optional1.ifPresent((p_143292_) -> {
            this.m_142572_().execute(() -> {
               this.m_8904_().m_27085_(blockpos, p_143292_);
               DebugPackets.m_133679_(this, blockpos);
            });
         });
      }
   }

   public PoiManager m_8904_() {
      return this.m_7726_().m_8484_();
   }

   public boolean m_8802_(BlockPos p_8803_) {
      return this.m_8736_(p_8803_, 1);
   }

   public boolean m_8762_(SectionPos p_8763_) {
      return this.m_8802_(p_8763_.m_123250_());
   }

   public boolean m_8736_(BlockPos p_8737_, int p_8738_) {
      if (p_8738_ > 6) {
         return false;
      } else {
         return this.m_8828_(SectionPos.m_123199_(p_8737_)) <= p_8738_;
      }
   }

   public int m_8828_(SectionPos p_8829_) {
      return this.m_8904_().m_27098_(p_8829_);
   }

   public Raids m_8905_() {
      return this.f_8565_;
   }

   @Nullable
   public Raid m_8832_(BlockPos p_8833_) {
      return this.f_8565_.m_37970_(p_8833_, 9216);
   }

   public boolean m_8843_(BlockPos p_8844_) {
      return this.m_8832_(p_8844_) != null;
   }

   public void m_8670_(ReputationEventType p_8671_, Entity p_8672_, ReputationEventHandler p_8673_) {
      p_8673_.m_6814_(p_8671_, p_8672_);
   }

   public void m_8786_(Path p_8787_) throws IOException {
      ChunkMap chunkmap = this.m_7726_().f_8325_;
      Writer writer = Files.newBufferedWriter(p_8787_.resolve("stats.txt"));

      try {
         writer.write(String.format("spawning_chunks: %d\n", chunkmap.m_143145_().m_140816_()));
         NaturalSpawner.SpawnState naturalspawner$spawnstate = this.m_7726_().m_8485_();
         if (naturalspawner$spawnstate != null) {
            for(Entry<MobCategory> entry : naturalspawner$spawnstate.m_47148_().object2IntEntrySet()) {
               writer.write(String.format("spawn_count.%s: %d\n", entry.getKey().m_21607_(), entry.getIntValue()));
            }
         }

         writer.write(String.format("entities: %s\n", this.f_143244_.m_157572_()));
         writer.write(String.format("block_entity_tickers: %d\n", this.f_151512_.size()));
         writer.write(String.format("block_ticks: %d\n", this.m_6219_().m_142536_()));
         writer.write(String.format("fluid_ticks: %d\n", this.m_6217_().m_142536_()));
         writer.write("distance_manager: " + chunkmap.m_143145_().m_140837_() + "\n");
         writer.write(String.format("pending_tasks: %d\n", this.m_7726_().m_8480_()));
      } catch (Throwable throwable11) {
         if (writer != null) {
            try {
               writer.close();
            } catch (Throwable throwable5) {
               throwable11.addSuppressed(throwable5);
            }
         }

         throw throwable11;
      }

      if (writer != null) {
         writer.close();
      }

      CrashReport crashreport = new CrashReport("Level dump", new Exception("dummy"));
      this.m_6026_(crashreport);
      Writer writer3 = Files.newBufferedWriter(p_8787_.resolve("example_crash.txt"));

      try {
         writer3.write(crashreport.m_127526_());
      } catch (Throwable throwable10) {
         if (writer3 != null) {
            try {
               writer3.close();
            } catch (Throwable throwable4) {
               throwable10.addSuppressed(throwable4);
            }
         }

         throw throwable10;
      }

      if (writer3 != null) {
         writer3.close();
      }

      Path path = p_8787_.resolve("chunks.csv");
      Writer writer4 = Files.newBufferedWriter(path);

      try {
         chunkmap.m_140274_(writer4);
      } catch (Throwable throwable9) {
         if (writer4 != null) {
            try {
               writer4.close();
            } catch (Throwable throwable3) {
               throwable9.addSuppressed(throwable3);
            }
         }

         throw throwable9;
      }

      if (writer4 != null) {
         writer4.close();
      }

      Path path1 = p_8787_.resolve("entity_chunks.csv");
      Writer writer5 = Files.newBufferedWriter(path1);

      try {
         this.f_143244_.m_157548_(writer5);
      } catch (Throwable throwable8) {
         if (writer5 != null) {
            try {
               writer5.close();
            } catch (Throwable throwable2) {
               throwable8.addSuppressed(throwable2);
            }
         }

         throw throwable8;
      }

      if (writer5 != null) {
         writer5.close();
      }

      Path path2 = p_8787_.resolve("entities.csv");
      Writer writer1 = Files.newBufferedWriter(path2);

      try {
         m_8781_(writer1, this.m_142646_().m_142273_());
      } catch (Throwable throwable7) {
         if (writer1 != null) {
            try {
               writer1.close();
            } catch (Throwable throwable1) {
               throwable7.addSuppressed(throwable1);
            }
         }

         throw throwable7;
      }

      if (writer1 != null) {
         writer1.close();
      }

      Path path3 = p_8787_.resolve("block_entities.csv");
      Writer writer2 = Files.newBufferedWriter(path3);

      try {
         this.m_143299_(writer2);
      } catch (Throwable throwable6) {
         if (writer2 != null) {
            try {
               writer2.close();
            } catch (Throwable throwable) {
               throwable6.addSuppressed(throwable);
            }
         }

         throw throwable6;
      }

      if (writer2 != null) {
         writer2.close();
      }

   }

   private static void m_8781_(Writer p_8782_, Iterable<Entity> p_8783_) throws IOException {
      CsvOutput csvoutput = CsvOutput.m_13619_().m_13630_("x").m_13630_("y").m_13630_("z").m_13630_("uuid").m_13630_("type").m_13630_("alive").m_13630_("display_name").m_13630_("custom_name").m_13628_(p_8782_);

      for(Entity entity : p_8783_) {
         Component component = entity.m_7770_();
         Component component1 = entity.m_5446_();
         csvoutput.m_13624_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_142081_(), Registry.f_122826_.m_7981_(entity.m_6095_()), entity.m_6084_(), component1.getString(), component != null ? component.getString() : null);
      }

   }

   private void m_143299_(Writer p_143300_) throws IOException {
      CsvOutput csvoutput = CsvOutput.m_13619_().m_13630_("x").m_13630_("y").m_13630_("z").m_13630_("type").m_13628_(p_143300_);

      for(TickingBlockEntity tickingblockentity : this.f_151512_) {
         BlockPos blockpos = tickingblockentity.m_142689_();
         csvoutput.m_13624_(blockpos.m_123341_(), blockpos.m_123342_(), blockpos.m_123343_(), tickingblockentity.m_142280_());
      }

   }

   @VisibleForTesting
   public void m_8722_(BoundingBox p_8723_) {
      this.f_8556_.removeIf((p_143287_) -> {
         return p_8723_.m_71051_(p_143287_.m_45538_());
      });
   }

   public void m_6289_(BlockPos p_8743_, Block p_8744_) {
      if (!this.m_46659_()) {
         this.m_46672_(p_8743_, p_8744_);
      }

   }

   public float m_7717_(Direction p_8760_, boolean p_8761_) {
      return 1.0F;
   }

   public Iterable<Entity> m_8583_() {
      return this.m_142646_().m_142273_();
   }

   public String toString() {
      return "ServerLevel[" + this.f_8549_.m_5462_() + "]";
   }

   public boolean m_8584_() {
      return this.f_8548_.m_129910_().m_5961_().m_64669_();
   }

   public long m_7328_() {
      return this.f_8548_.m_129910_().m_5961_().m_64619_();
   }

   @Nullable
   public EndDragonFight m_8586_() {
      return this.f_8559_;
   }

   public Stream<? extends StructureStart<?>> m_7526_(SectionPos p_8765_, StructureFeature<?> p_8766_) {
      return this.m_8595_().m_47289_(p_8765_, p_8766_);
   }

   public ServerLevel m_6018_() {
      return this;
   }

   @VisibleForTesting
   public String m_8590_() {
      return String.format("players: %s, entities: %s [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s", this.f_8546_.size(), this.f_143244_.m_157572_(), m_143301_(this.f_143244_.m_157567_().m_142273_(), (p_143346_) -> {
         return Registry.f_122826_.m_7981_(p_143346_.m_6095_()).toString();
      }), this.f_151512_.size(), m_143301_(this.f_151512_, TickingBlockEntity::m_142280_), this.m_6219_().m_142536_(), this.m_6217_().m_142536_(), this.m_46464_());
   }

   private static <T> String m_143301_(Iterable<T> p_143302_, Function<T, String> p_143303_) {
      try {
         Object2IntOpenHashMap<String> object2intopenhashmap = new Object2IntOpenHashMap<>();

         for(T t : p_143302_) {
            String s = p_143303_.apply(t);
            object2intopenhashmap.addTo(s, 1);
         }

         return object2intopenhashmap.object2IntEntrySet().stream().sorted(Comparator.comparing(Entry<String>::getIntValue).reversed()).limit(5L).map((p_143298_) -> {
            return (String)p_143298_.getKey() + ":" + p_143298_.getIntValue();
         }).collect(Collectors.joining(","));
      } catch (Exception exception) {
         return "";
      }
   }

   public static void m_8617_(ServerLevel p_8618_) {
      BlockPos blockpos = f_8562_;
      int i = blockpos.m_123341_();
      int j = blockpos.m_123342_() - 2;
      int k = blockpos.m_123343_();
      BlockPos.m_121976_(i - 2, j + 1, k - 2, i + 2, j + 3, k + 2).forEach((p_143323_) -> {
         p_8618_.m_46597_(p_143323_, Blocks.f_50016_.m_49966_());
      });
      BlockPos.m_121976_(i - 2, j, k - 2, i + 2, j, k + 2).forEach((p_143260_) -> {
         p_8618_.m_46597_(p_143260_, Blocks.f_50080_.m_49966_());
      });
   }

   protected void initCapabilities() {
      this.gatherCapabilities();
      capabilityData = this.m_8895_().m_164861_(e -> net.minecraftforge.common.util.WorldCapabilityData.load(e, getCapabilities()), () -> new net.minecraftforge.common.util.WorldCapabilityData(getCapabilities()), net.minecraftforge.common.util.WorldCapabilityData.ID);
      capabilityData.setCapabilities(getCapabilities());
   }

   public LevelEntityGetter<Entity> m_142646_() {
      return this.f_143244_.m_157567_();
   }

   public void m_143311_(Stream<Entity> p_143312_) {
      this.f_143244_.m_157552_(p_143312_);
   }

   public void m_143327_(Stream<Entity> p_143328_) {
      this.f_143244_.m_157559_(p_143328_);
   }

   public void close() throws IOException {
      super.close();
      this.f_143244_.close();
   }

   public String m_46464_() {
      return "Chunks[S] W: " + this.f_8547_.m_6754_() + " E: " + this.f_143244_.m_157572_();
   }

   public boolean m_143319_(long p_143320_) {
      return this.f_143244_.m_157507_(p_143320_);
   }

   public boolean m_143336_(BlockPos p_143337_) {
      long i = ChunkPos.m_151388_(p_143337_);
      return this.f_8547_.m_143239_(i) && this.m_143319_(i);
   }

   public boolean m_143340_(BlockPos p_143341_) {
      return this.f_143244_.m_157546_(p_143341_);
   }

   public boolean m_143275_(ChunkPos p_143276_) {
      return this.f_143244_.m_157522_(p_143276_);
   }

   final class EntityCallbacks implements LevelCallback<Entity> {
      public void m_141989_(Entity p_143355_) {
      }

      public void m_141986_(Entity p_143359_) {
         ServerLevel.this.m_6188_().m_83420_(p_143359_);
      }

      public void m_141987_(Entity p_143363_) {
         ServerLevel.this.f_143243_.m_156908_(p_143363_);
      }

      public void m_141983_(Entity p_143367_) {
         ServerLevel.this.f_143243_.m_156912_(p_143367_);
      }

      public void m_141985_(Entity p_143371_) {
         ServerLevel.this.m_7726_().m_8463_(p_143371_);
         if (p_143371_ instanceof ServerPlayer) {
            ServerLevel.this.f_8546_.add((ServerPlayer)p_143371_);
            ServerLevel.this.m_8878_();
         }

         if (p_143371_ instanceof Mob) {
            ServerLevel.this.f_143246_.add((Mob)p_143371_);
         }

         if (p_143371_.isMultipartEntity()) {
            for(net.minecraftforge.entity.PartEntity<?> enderdragonpart : p_143371_.getParts()) {
               ServerLevel.this.f_143247_.put(enderdragonpart.m_142049_(), enderdragonpart);
            }
         }

      }

      public void m_141981_(Entity p_143375_) {
         ServerLevel.this.m_7726_().m_8443_(p_143375_);
         if (p_143375_ instanceof ServerPlayer) {
            ServerPlayer serverplayer = (ServerPlayer)p_143375_;
            ServerLevel.this.f_8546_.remove(serverplayer);
            ServerLevel.this.m_8878_();
         }

         if (p_143375_ instanceof Mob) {
            ServerLevel.this.f_143246_.remove(p_143375_);
         }

         if (p_143375_.isMultipartEntity()) {
            for(net.minecraftforge.entity.PartEntity<?> enderdragonpart : p_143375_.getParts()) {
               ServerLevel.this.f_143247_.remove(enderdragonpart.m_142049_());
            }
         }

         GameEventListenerRegistrar gameeventlistenerregistrar = p_143375_.m_146887_();
         if (gameeventlistenerregistrar != null) {
            gameeventlistenerregistrar.m_157854_(p_143375_.f_19853_);
         }

      }
   }
}
