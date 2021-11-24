package net.minecraft.client.multiplayer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.EmptyTickList;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.TransientEntitySectionManager;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientLevel extends Level {
   private static final double f_171629_ = 0.05D;
   final EntityTickList f_171630_ = new EntityTickList();
   private final TransientEntitySectionManager<Entity> f_171631_ = new TransientEntitySectionManager<>(Entity.class, new ClientLevel.EntityCallbacks());
   private final ClientPacketListener f_104561_;
   private final LevelRenderer f_104562_;
   private final ClientLevel.ClientLevelData f_104563_;
   private final DimensionSpecialEffects f_104564_;
   private final Minecraft f_104565_ = Minecraft.m_91087_();
   final List<AbstractClientPlayer> f_104566_ = Lists.newArrayList();
   private Scoreboard f_104555_ = new Scoreboard();
   private final Map<String, MapItemSavedData> f_104556_ = Maps.newHashMap();
   private static final long f_171628_ = 16777215L;
   private int f_104557_;
   private final Object2ObjectArrayMap<ColorResolver, BlockTintCache> f_104558_ = Util.m_137469_(new Object2ObjectArrayMap<>(3), (p_104723_) -> {
      p_104723_.put(BiomeColors.f_108789_, new BlockTintCache());
      p_104723_.put(BiomeColors.f_108790_, new BlockTintCache());
      p_104723_.put(BiomeColors.f_108791_, new BlockTintCache());
   });
   private final ClientChunkCache f_104559_;

   public ClientLevel(ClientPacketListener p_104568_, ClientLevel.ClientLevelData p_104569_, ResourceKey<Level> p_104570_, DimensionType p_104571_, int p_104572_, Supplier<ProfilerFiller> p_104573_, LevelRenderer p_104574_, boolean p_104575_, long p_104576_) {
      super(p_104569_, p_104570_, p_104571_, p_104573_, true, p_104575_, p_104576_);
      this.f_104561_ = p_104568_;
      this.f_104559_ = new ClientChunkCache(this, p_104572_);
      this.f_104563_ = p_104569_;
      this.f_104562_ = p_104574_;
      this.f_104564_ = DimensionSpecialEffects.m_108876_(p_104571_);
      this.m_104752_(new BlockPos(8, 64, 8), 0.0F);
      this.m_46465_();
      this.m_46466_();
      this.gatherCapabilities();
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.WorldEvent.Load(this));
   }

   public DimensionSpecialEffects m_104583_() {
      return this.f_104564_;
   }

   public void m_104726_(BooleanSupplier p_104727_) {
      this.m_6857_().m_61969_();
      this.m_104826_();
      this.m_46473_().m_6180_("blocks");
      this.f_104559_.m_142483_(p_104727_);
      this.m_46473_().m_7238_();
   }

   private void m_104826_() {
      this.m_104637_(this.f_46442_.m_6793_() + 1L);
      if (this.f_46442_.m_5470_().m_46207_(GameRules.f_46140_)) {
         this.m_104746_(this.f_46442_.m_6792_() + 1L);
      }

   }

   public void m_104637_(long p_104638_) {
      this.f_104563_.m_104849_(p_104638_);
   }

   public void m_104746_(long p_104747_) {
      if (p_104747_ < 0L) {
         p_104747_ = -p_104747_;
         this.m_46469_().m_46170_(GameRules.f_46140_).m_46246_(false, (MinecraftServer)null);
      } else {
         this.m_46469_().m_46170_(GameRules.f_46140_).m_46246_(true, (MinecraftServer)null);
      }

      this.f_104563_.m_104863_(p_104747_);
   }

   public Iterable<Entity> m_104735_() {
      return this.m_142646_().m_142273_();
   }

   public void m_104804_() {
      ProfilerFiller profilerfiller = this.m_46473_();
      profilerfiller.m_6180_("entities");
      this.f_171630_.m_156910_((p_171683_) -> {
         if (!p_171683_.m_146910_() && !p_171683_.m_20159_()) {
            this.m_46653_(this::m_104639_, p_171683_);
         }
      });
      profilerfiller.m_7238_();
      this.m_46463_();
   }

   public void m_104639_(Entity p_104640_) {
      p_104640_.m_146867_();
      ++p_104640_.f_19797_;
      this.m_46473_().m_6521_(() -> {
         return Registry.f_122826_.m_7981_(p_104640_.m_6095_()).toString();
      });
      if (p_104640_.canUpdate())
      p_104640_.m_8119_();
      this.m_46473_().m_7238_();

      for(Entity entity : p_104640_.m_20197_()) {
         this.m_104641_(p_104640_, entity);
      }

   }

   private void m_104641_(Entity p_104642_, Entity p_104643_) {
      if (!p_104643_.m_146910_() && p_104643_.m_20202_() == p_104642_) {
         if (p_104643_ instanceof Player || this.f_171630_.m_156914_(p_104643_)) {
            p_104643_.m_146867_();
            ++p_104643_.f_19797_;
            p_104643_.m_6083_();

            for(Entity entity : p_104643_.m_20197_()) {
               this.m_104641_(p_104643_, entity);
            }

         }
      } else {
         p_104643_.m_8127_();
      }
   }

   public void m_104665_(LevelChunk p_104666_) {
      p_104666_.m_156368_();
      this.f_104559_.m_7827_().m_141940_(p_104666_.m_7697_(), false);
      this.f_171631_.m_157658_(p_104666_.m_7697_());
   }

   public void m_171649_(ChunkPos p_171650_) {
      this.f_104558_.forEach((p_171653_, p_171654_) -> {
         p_171654_.m_92655_(p_171650_.f_45578_, p_171650_.f_45579_);
      });
      this.f_171631_.m_157651_(p_171650_);
   }

   public void m_104810_() {
      this.f_104558_.forEach((p_171675_, p_171676_) -> {
         p_171676_.m_92654_();
      });
   }

   public boolean m_7232_(int p_104737_, int p_104738_) {
      return true;
   }

   public int m_104813_() {
      return this.f_171631_.m_157657_();
   }

   public void m_104630_(int p_104631_, AbstractClientPlayer p_104632_) {
      this.m_104739_(p_104631_, p_104632_);
   }

   public void m_104627_(int p_104628_, Entity p_104629_) {
      this.m_104739_(p_104628_, p_104629_);
   }

   private void m_104739_(int p_104740_, Entity p_104741_) {
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.EntityJoinWorldEvent(p_104741_, this))) return;
      this.m_171642_(p_104740_, Entity.RemovalReason.DISCARDED);
      this.f_171631_.m_157653_(p_104741_);
      p_104741_.onAddedToWorld();
   }

   public void m_171642_(int p_171643_, Entity.RemovalReason p_171644_) {
      Entity entity = this.m_142646_().m_142597_(p_171643_);
      if (entity != null) {
         entity.m_142467_(p_171644_);
         entity.m_142036_();
      }

   }

   @Nullable
   public Entity m_6815_(int p_104609_) {
      return this.m_142646_().m_142597_(p_104609_);
   }

   public void m_104755_(BlockPos p_104756_, BlockState p_104757_) {
      this.m_7731_(p_104756_, p_104757_, 19);
   }

   public void m_7462_() {
      this.f_104561_.m_6198_().m_129507_(new TranslatableComponent("multiplayer.status.quitting"));
   }

   public void m_104784_(int p_104785_, int p_104786_, int p_104787_) {
      int i = 32;
      Random random = new Random();
      ClientLevel.MarkerParticleStatus clientlevel$markerparticlestatus = this.m_171685_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j = 0; j < 667; ++j) {
         this.m_171634_(p_104785_, p_104786_, p_104787_, 16, random, clientlevel$markerparticlestatus, blockpos$mutableblockpos);
         this.m_171634_(p_104785_, p_104786_, p_104787_, 32, random, clientlevel$markerparticlestatus, blockpos$mutableblockpos);
      }

   }

   @Nullable
   private ClientLevel.MarkerParticleStatus m_171685_() {
      if (this.f_104565_.f_91072_.m_105295_() == GameType.CREATIVE) {
         ItemStack itemstack = this.f_104565_.f_91074_.m_21205_();
         if (itemstack.m_41720_() == Items.f_42127_) {
            return ClientLevel.MarkerParticleStatus.BARRIER;
         }

         if (itemstack.m_41720_() == Items.f_151033_) {
            return ClientLevel.MarkerParticleStatus.LIGHT;
         }
      }

      return null;
   }

   public void m_171634_(int p_171635_, int p_171636_, int p_171637_, int p_171638_, Random p_171639_, @Nullable ClientLevel.MarkerParticleStatus p_171640_, BlockPos.MutableBlockPos p_171641_) {
      int i = p_171635_ + this.f_46441_.nextInt(p_171638_) - this.f_46441_.nextInt(p_171638_);
      int j = p_171636_ + this.f_46441_.nextInt(p_171638_) - this.f_46441_.nextInt(p_171638_);
      int k = p_171637_ + this.f_46441_.nextInt(p_171638_) - this.f_46441_.nextInt(p_171638_);
      p_171641_.m_122178_(i, j, k);
      BlockState blockstate = this.m_8055_(p_171641_);
      blockstate.m_60734_().m_7100_(blockstate, this, p_171641_, p_171639_);
      FluidState fluidstate = this.m_6425_(p_171641_);
      if (!fluidstate.m_76178_()) {
         fluidstate.m_76166_(this, p_171641_, p_171639_);
         ParticleOptions particleoptions = fluidstate.m_76189_();
         if (particleoptions != null && this.f_46441_.nextInt(10) == 0) {
            boolean flag = blockstate.m_60783_(this, p_171641_, Direction.DOWN);
            BlockPos blockpos = p_171641_.m_7495_();
            this.m_104689_(blockpos, this.m_8055_(blockpos), particleoptions, flag);
         }
      }

      if (p_171640_ != null && blockstate.m_60734_() == p_171640_.f_171721_) {
         this.m_7106_(p_171640_.f_171722_, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 0.0D, 0.0D, 0.0D);
      }

      if (!blockstate.m_60838_(this, p_171641_)) {
         this.m_46857_(p_171641_).m_47562_().ifPresent((p_171665_) -> {
            if (p_171665_.m_47424_(this.f_46441_)) {
               this.m_7106_(p_171665_.m_47419_(), (double)p_171641_.m_123341_() + this.f_46441_.nextDouble(), (double)p_171641_.m_123342_() + this.f_46441_.nextDouble(), (double)p_171641_.m_123343_() + this.f_46441_.nextDouble(), 0.0D, 0.0D, 0.0D);
            }

         });
      }

   }

   private void m_104689_(BlockPos p_104690_, BlockState p_104691_, ParticleOptions p_104692_, boolean p_104693_) {
      if (p_104691_.m_60819_().m_76178_()) {
         VoxelShape voxelshape = p_104691_.m_60812_(this, p_104690_);
         double d0 = voxelshape.m_83297_(Direction.Axis.Y);
         if (d0 < 1.0D) {
            if (p_104693_) {
               this.m_104592_((double)p_104690_.m_123341_(), (double)(p_104690_.m_123341_() + 1), (double)p_104690_.m_123343_(), (double)(p_104690_.m_123343_() + 1), (double)(p_104690_.m_123342_() + 1) - 0.05D, p_104692_);
            }
         } else if (!p_104691_.m_60620_(BlockTags.f_13049_)) {
            double d1 = voxelshape.m_83288_(Direction.Axis.Y);
            if (d1 > 0.0D) {
               this.m_104694_(p_104690_, p_104692_, voxelshape, (double)p_104690_.m_123342_() + d1 - 0.05D);
            } else {
               BlockPos blockpos = p_104690_.m_7495_();
               BlockState blockstate = this.m_8055_(blockpos);
               VoxelShape voxelshape1 = blockstate.m_60812_(this, blockpos);
               double d2 = voxelshape1.m_83297_(Direction.Axis.Y);
               if (d2 < 1.0D && blockstate.m_60819_().m_76178_()) {
                  this.m_104694_(p_104690_, p_104692_, voxelshape, (double)p_104690_.m_123342_() - 0.05D);
               }
            }
         }

      }
   }

   private void m_104694_(BlockPos p_104695_, ParticleOptions p_104696_, VoxelShape p_104697_, double p_104698_) {
      this.m_104592_((double)p_104695_.m_123341_() + p_104697_.m_83288_(Direction.Axis.X), (double)p_104695_.m_123341_() + p_104697_.m_83297_(Direction.Axis.X), (double)p_104695_.m_123343_() + p_104697_.m_83288_(Direction.Axis.Z), (double)p_104695_.m_123343_() + p_104697_.m_83297_(Direction.Axis.Z), p_104698_, p_104696_);
   }

   private void m_104592_(double p_104593_, double p_104594_, double p_104595_, double p_104596_, double p_104597_, ParticleOptions p_104598_) {
      this.m_7106_(p_104598_, Mth.m_14139_(this.f_46441_.nextDouble(), p_104593_, p_104594_), p_104597_, Mth.m_14139_(this.f_46441_.nextDouble(), p_104595_, p_104596_), 0.0D, 0.0D, 0.0D);
   }

   public CrashReportCategory m_6026_(CrashReport p_104729_) {
      CrashReportCategory crashreportcategory = super.m_6026_(p_104729_);
      crashreportcategory.m_128165_("Server brand", () -> {
         return this.f_104565_.f_91074_.m_108629_();
      });
      crashreportcategory.m_128165_("Server type", () -> {
         return this.f_104565_.m_91092_() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
      });
      return crashreportcategory;
   }

   public void m_6263_(@Nullable Player p_104645_, double p_104646_, double p_104647_, double p_104648_, SoundEvent p_104649_, SoundSource p_104650_, float p_104651_, float p_104652_) {
      net.minecraftforge.event.entity.PlaySoundAtEntityEvent event = net.minecraftforge.event.ForgeEventFactory.onPlaySoundAtEntity(p_104645_, p_104649_, p_104650_, p_104651_, p_104652_);
      if (event.isCanceled() || event.getSound() == null) return;
      p_104649_ = event.getSound();
      p_104650_ = event.getCategory();
      p_104651_ = event.getVolume();
      if (p_104645_ == this.f_104565_.f_91074_) {
         this.m_7785_(p_104646_, p_104647_, p_104648_, p_104649_, p_104650_, p_104651_, p_104652_, false);
      }

   }

   public void m_6269_(@Nullable Player p_104659_, Entity p_104660_, SoundEvent p_104661_, SoundSource p_104662_, float p_104663_, float p_104664_) {
      net.minecraftforge.event.entity.PlaySoundAtEntityEvent event = net.minecraftforge.event.ForgeEventFactory.onPlaySoundAtEntity(p_104659_, p_104661_, p_104662_, p_104663_, p_104664_);
      if (event.isCanceled() || event.getSound() == null) return;
      p_104661_ = event.getSound();
      p_104662_ = event.getCategory();
      p_104663_ = event.getVolume();
      if (p_104659_ == this.f_104565_.f_91074_) {
         this.f_104565_.m_91106_().m_120367_(new EntityBoundSoundInstance(p_104661_, p_104662_, p_104663_, p_104664_, p_104660_));
      }

   }

   public void m_104677_(BlockPos p_104678_, SoundEvent p_104679_, SoundSource p_104680_, float p_104681_, float p_104682_, boolean p_104683_) {
      this.m_7785_((double)p_104678_.m_123341_() + 0.5D, (double)p_104678_.m_123342_() + 0.5D, (double)p_104678_.m_123343_() + 0.5D, p_104679_, p_104680_, p_104681_, p_104682_, p_104683_);
   }

   public void m_7785_(double p_104600_, double p_104601_, double p_104602_, SoundEvent p_104603_, SoundSource p_104604_, float p_104605_, float p_104606_, boolean p_104607_) {
      double d0 = this.f_104565_.f_91063_.m_109153_().m_90583_().m_82531_(p_104600_, p_104601_, p_104602_);
      SimpleSoundInstance simplesoundinstance = new SimpleSoundInstance(p_104603_, p_104604_, p_104605_, p_104606_, p_104600_, p_104601_, p_104602_);
      if (p_104607_ && d0 > 100.0D) {
         double d1 = Math.sqrt(d0) / 40.0D;
         this.f_104565_.m_91106_().m_120369_(simplesoundinstance, (int)(d1 * 20.0D));
      } else {
         this.f_104565_.m_91106_().m_120367_(simplesoundinstance);
      }

   }

   public void m_7228_(double p_104585_, double p_104586_, double p_104587_, double p_104588_, double p_104589_, double p_104590_, @Nullable CompoundTag p_104591_) {
      this.f_104565_.f_91061_.m_107344_(new FireworkParticles.Starter(this, p_104585_, p_104586_, p_104587_, p_104588_, p_104589_, p_104590_, this.f_104565_.f_91061_, p_104591_));
   }

   public void m_5503_(Packet<?> p_104734_) {
      this.f_104561_.m_104955_(p_104734_);
   }

   public RecipeManager m_7465_() {
      return this.f_104561_.m_105141_();
   }

   public void m_104669_(Scoreboard p_104670_) {
      this.f_104555_ = p_104670_;
   }

   public TickList<Block> m_6219_() {
      return EmptyTickList.m_45888_();
   }

   public TickList<Fluid> m_6217_() {
      return EmptyTickList.m_45888_();
   }

   public ClientChunkCache m_7726_() {
      return this.f_104559_;
   }

   @Nullable
   public MapItemSavedData m_7489_(String p_104725_) {
      return this.f_104556_.get(p_104725_);
   }

   public void m_142325_(String p_171670_, MapItemSavedData p_171671_) {
      this.f_104556_.put(p_171670_, p_171671_);
   }

   public int m_7354_() {
      return 0;
   }

   public Scoreboard m_6188_() {
      return this.f_104555_;
   }

   public TagContainer m_5999_() {
      return this.f_104561_.m_105148_();
   }

   public RegistryAccess m_5962_() {
      return this.f_104561_.m_105152_();
   }

   public void m_7260_(BlockPos p_104685_, BlockState p_104686_, BlockState p_104687_, int p_104688_) {
      this.f_104562_.m_109544_(this, p_104685_, p_104686_, p_104687_, p_104688_);
   }

   public void m_6550_(BlockPos p_104759_, BlockState p_104760_, BlockState p_104761_) {
      this.f_104562_.m_109721_(p_104759_, p_104760_, p_104761_);
   }

   public void m_104793_(int p_104794_, int p_104795_, int p_104796_) {
      this.f_104562_.m_109490_(p_104794_, p_104795_, p_104796_);
   }

   public void m_6801_(int p_104634_, BlockPos p_104635_, int p_104636_) {
      this.f_104562_.m_109774_(p_104634_, p_104635_, p_104636_);
   }

   public void m_6798_(int p_104743_, BlockPos p_104744_, int p_104745_) {
      this.f_104562_.m_109506_(p_104743_, p_104744_, p_104745_);
   }

   public void m_5898_(@Nullable Player p_104654_, int p_104655_, BlockPos p_104656_, int p_104657_) {
      try {
         this.f_104562_.m_109532_(p_104654_, p_104655_, p_104656_, p_104657_);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.m_127521_(throwable, "Playing level event");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Level event being played");
         crashreportcategory.m_128159_("Block coordinates", CrashReportCategory.m_178947_(this, p_104656_));
         crashreportcategory.m_128159_("Event source", p_104654_);
         crashreportcategory.m_128159_("Event type", p_104655_);
         crashreportcategory.m_128159_("Event data", p_104657_);
         throw new ReportedException(crashreport);
      }
   }

   public void m_7106_(ParticleOptions p_104706_, double p_104707_, double p_104708_, double p_104709_, double p_104710_, double p_104711_, double p_104712_) {
      this.f_104562_.m_109743_(p_104706_, p_104706_.m_6012_().m_123742_(), p_104707_, p_104708_, p_104709_, p_104710_, p_104711_, p_104712_);
   }

   public void m_6493_(ParticleOptions p_104714_, boolean p_104715_, double p_104716_, double p_104717_, double p_104718_, double p_104719_, double p_104720_, double p_104721_) {
      this.f_104562_.m_109743_(p_104714_, p_104714_.m_6012_().m_123742_() || p_104715_, p_104716_, p_104717_, p_104718_, p_104719_, p_104720_, p_104721_);
   }

   public void m_7107_(ParticleOptions p_104766_, double p_104767_, double p_104768_, double p_104769_, double p_104770_, double p_104771_, double p_104772_) {
      this.f_104562_.m_109752_(p_104766_, false, true, p_104767_, p_104768_, p_104769_, p_104770_, p_104771_, p_104772_);
   }

   public void m_6485_(ParticleOptions p_104774_, boolean p_104775_, double p_104776_, double p_104777_, double p_104778_, double p_104779_, double p_104780_, double p_104781_) {
      this.f_104562_.m_109752_(p_104774_, p_104774_.m_6012_().m_123742_() || p_104775_, true, p_104776_, p_104777_, p_104778_, p_104779_, p_104780_, p_104781_);
   }

   public List<AbstractClientPlayer> m_6907_() {
      return this.f_104566_;
   }

   public Biome m_6159_(int p_104611_, int p_104612_, int p_104613_) {
      return this.m_5962_().m_175515_(Registry.f_122885_).m_123013_(Biomes.f_48202_);
   }

   public float m_104805_(float p_104806_) {
      float f = this.m_46942_(p_104806_);
      float f1 = 1.0F - (Mth.m_14089_(f * ((float)Math.PI * 2F)) * 2.0F + 0.2F);
      f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
      f1 = 1.0F - f1;
      f1 = (float)((double)f1 * (1.0D - (double)(this.m_46722_(p_104806_) * 5.0F) / 16.0D));
      f1 = (float)((double)f1 * (1.0D - (double)(this.m_46661_(p_104806_) * 5.0F) / 16.0D));
      return f1 * 0.8F + 0.2F;
   }

   public Vec3 m_171660_(Vec3 p_171661_, float p_171662_) {
      float f = this.m_46942_(p_171662_);
      Vec3 vec3 = p_171661_.m_82492_(2.0D, 2.0D, 2.0D).m_82490_(0.25D);
      BiomeManager biomemanager = this.m_7062_();
      Vec3 vec31 = CubicSampler.m_130038_(vec3, (p_171657_, p_171658_, p_171659_) -> {
         return Vec3.m_82501_(biomemanager.m_47873_(p_171657_, p_171658_, p_171659_).m_47463_());
      });
      float f1 = Mth.m_14089_(f * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
      f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
      float f2 = (float)vec31.f_82479_ * f1;
      float f3 = (float)vec31.f_82480_ * f1;
      float f4 = (float)vec31.f_82481_ * f1;
      float f5 = this.m_46722_(p_171662_);
      if (f5 > 0.0F) {
         float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
         float f7 = 1.0F - f5 * 0.75F;
         f2 = f2 * f7 + f6 * (1.0F - f7);
         f3 = f3 * f7 + f6 * (1.0F - f7);
         f4 = f4 * f7 + f6 * (1.0F - f7);
      }

      float f9 = this.m_46661_(p_171662_);
      if (f9 > 0.0F) {
         float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
         float f8 = 1.0F - f9 * 0.75F;
         f2 = f2 * f8 + f10 * (1.0F - f8);
         f3 = f3 * f8 + f10 * (1.0F - f8);
         f4 = f4 * f8 + f10 * (1.0F - f8);
      }

      if (this.f_104557_ > 0) {
         float f11 = (float)this.f_104557_ - p_171662_;
         if (f11 > 1.0F) {
            f11 = 1.0F;
         }

         f11 = f11 * 0.45F;
         f2 = f2 * (1.0F - f11) + 0.8F * f11;
         f3 = f3 * (1.0F - f11) + 0.8F * f11;
         f4 = f4 * (1.0F - f11) + 1.0F * f11;
      }

      return new Vec3((double)f2, (double)f3, (double)f4);
   }

   public Vec3 m_104808_(float p_104809_) {
      float f = this.m_46942_(p_104809_);
      float f1 = Mth.m_14089_(f * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
      f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
      float f2 = 1.0F;
      float f3 = 1.0F;
      float f4 = 1.0F;
      float f5 = this.m_46722_(p_104809_);
      if (f5 > 0.0F) {
         float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
         float f7 = 1.0F - f5 * 0.95F;
         f2 = f2 * f7 + f6 * (1.0F - f7);
         f3 = f3 * f7 + f6 * (1.0F - f7);
         f4 = f4 * f7 + f6 * (1.0F - f7);
      }

      f2 = f2 * (f1 * 0.9F + 0.1F);
      f3 = f3 * (f1 * 0.9F + 0.1F);
      f4 = f4 * (f1 * 0.85F + 0.15F);
      float f9 = this.m_46661_(p_104809_);
      if (f9 > 0.0F) {
         float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
         float f8 = 1.0F - f9 * 0.95F;
         f2 = f2 * f8 + f10 * (1.0F - f8);
         f3 = f3 * f8 + f10 * (1.0F - f8);
         f4 = f4 * f8 + f10 * (1.0F - f8);
      }

      return new Vec3((double)f2, (double)f3, (double)f4);
   }

   public float m_104811_(float p_104812_) {
      float f = this.m_46942_(p_104812_);
      float f1 = 1.0F - (Mth.m_14089_(f * ((float)Math.PI * 2F)) * 2.0F + 0.25F);
      f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
      return f1 * f1 * 0.5F;
   }

   public int m_104819_() {
      return this.f_104557_;
   }

   public void m_6580_(int p_104783_) {
      this.f_104557_ = p_104783_;
   }

   public float m_7717_(Direction p_104703_, boolean p_104704_) {
      boolean flag = this.m_104583_().m_108885_();
      if (!p_104704_) {
         return flag ? 0.9F : 1.0F;
      } else {
         switch(p_104703_) {
         case DOWN:
            return flag ? 0.9F : 0.5F;
         case UP:
            return flag ? 0.9F : 1.0F;
         case NORTH:
         case SOUTH:
            return 0.8F;
         case WEST:
         case EAST:
            return 0.6F;
         default:
            return 1.0F;
         }
      }
   }

   public int m_6171_(BlockPos p_104700_, ColorResolver p_104701_) {
      BlockTintCache blocktintcache = this.f_104558_.get(p_104701_);
      return blocktintcache.m_92658_(p_104700_, () -> {
         return this.m_104762_(p_104700_, p_104701_);
      });
   }

   public int m_104762_(BlockPos p_104763_, ColorResolver p_104764_) {
      int i = Minecraft.m_91087_().f_91066_.f_92032_;
      if (i == 0) {
         return p_104764_.m_130045_(this.m_46857_(p_104763_), (double)p_104763_.m_123341_(), (double)p_104763_.m_123343_());
      } else {
         int j = (i * 2 + 1) * (i * 2 + 1);
         int k = 0;
         int l = 0;
         int i1 = 0;
         Cursor3D cursor3d = new Cursor3D(p_104763_.m_123341_() - i, p_104763_.m_123342_(), p_104763_.m_123343_() - i, p_104763_.m_123341_() + i, p_104763_.m_123342_(), p_104763_.m_123343_() + i);

         int j1;
         for(BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(); cursor3d.m_122304_(); i1 += j1 & 255) {
            blockpos$mutableblockpos.m_122178_(cursor3d.m_122305_(), cursor3d.m_122306_(), cursor3d.m_122307_());
            j1 = p_104764_.m_130045_(this.m_46857_(blockpos$mutableblockpos), (double)blockpos$mutableblockpos.m_123341_(), (double)blockpos$mutableblockpos.m_123343_());
            k += (j1 & 16711680) >> 16;
            l += (j1 & '\uff00') >> 8;
         }

         return (k / j & 255) << 16 | (l / j & 255) << 8 | i1 / j & 255;
      }
   }

   public BlockPos m_104822_() {
      BlockPos blockpos = new BlockPos(this.f_46442_.m_6789_(), this.f_46442_.m_6527_(), this.f_46442_.m_6526_());
      if (!this.m_6857_().m_61937_(blockpos)) {
         blockpos = this.m_5452_(Heightmap.Types.MOTION_BLOCKING, new BlockPos(this.m_6857_().m_6347_(), 0.0D, this.m_6857_().m_6345_()));
      }

      return blockpos;
   }

   public float m_104823_() {
      return this.f_46442_.m_6790_();
   }

   public void m_104752_(BlockPos p_104753_, float p_104754_) {
      this.f_46442_.m_7250_(p_104753_, p_104754_);
   }

   public String toString() {
      return "ClientLevel";
   }

   public ClientLevel.ClientLevelData m_6106_() {
      return this.f_104563_;
   }

   public void m_142346_(@Nullable Entity p_171646_, GameEvent p_171647_, BlockPos p_171648_) {
   }

   protected Map<String, MapItemSavedData> m_171684_() {
      return ImmutableMap.copyOf(this.f_104556_);
   }

   protected void m_171672_(Map<String, MapItemSavedData> p_171673_) {
      this.f_104556_.putAll(p_171673_);
   }

   protected LevelEntityGetter<Entity> m_142646_() {
      return this.f_171631_.m_157645_();
   }

   public String m_46464_() {
      return "Chunks[C] W: " + this.f_104559_.m_6754_() + " E: " + this.f_171631_.m_157664_();
   }

   public void m_142052_(BlockPos p_171667_, BlockState p_171668_) {
      this.f_104565_.f_91061_.m_107355_(p_171667_, p_171668_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class ClientLevelData implements WritableLevelData {
      private final boolean f_104830_;
      private final GameRules f_104831_;
      private final boolean f_104832_;
      private int f_104833_;
      private int f_104834_;
      private int f_104835_;
      private float f_104836_;
      private long f_104837_;
      private long f_104838_;
      private boolean f_104839_;
      private Difficulty f_104840_;
      private boolean f_104841_;

      public ClientLevelData(Difficulty p_104843_, boolean p_104844_, boolean p_104845_) {
         this.f_104840_ = p_104843_;
         this.f_104830_ = p_104844_;
         this.f_104832_ = p_104845_;
         this.f_104831_ = new GameRules();
      }

      public int m_6789_() {
         return this.f_104833_;
      }

      public int m_6527_() {
         return this.f_104834_;
      }

      public int m_6526_() {
         return this.f_104835_;
      }

      public float m_6790_() {
         return this.f_104836_;
      }

      public long m_6793_() {
         return this.f_104837_;
      }

      public long m_6792_() {
         return this.f_104838_;
      }

      public void m_6395_(int p_104862_) {
         this.f_104833_ = p_104862_;
      }

      public void m_6397_(int p_104869_) {
         this.f_104834_ = p_104869_;
      }

      public void m_6400_(int p_104872_) {
         this.f_104835_ = p_104872_;
      }

      public void m_7113_(float p_104848_) {
         this.f_104836_ = p_104848_;
      }

      public void m_104849_(long p_104850_) {
         this.f_104837_ = p_104850_;
      }

      public void m_104863_(long p_104864_) {
         this.f_104838_ = p_104864_;
      }

      public void m_7250_(BlockPos p_104854_, float p_104855_) {
         this.f_104833_ = p_104854_.m_123341_();
         this.f_104834_ = p_104854_.m_123342_();
         this.f_104835_ = p_104854_.m_123343_();
         this.f_104836_ = p_104855_;
      }

      public boolean m_6534_() {
         return false;
      }

      public boolean m_6533_() {
         return this.f_104839_;
      }

      public void m_5565_(boolean p_104866_) {
         this.f_104839_ = p_104866_;
      }

      public boolean m_5466_() {
         return this.f_104830_;
      }

      public GameRules m_5470_() {
         return this.f_104831_;
      }

      public Difficulty m_5472_() {
         return this.f_104840_;
      }

      public boolean m_5474_() {
         return this.f_104841_;
      }

      public void m_142471_(CrashReportCategory p_171690_, LevelHeightAccessor p_171691_) {
         WritableLevelData.super.m_142471_(p_171690_, p_171691_);
      }

      public void m_104851_(Difficulty p_104852_) {
         net.minecraftforge.common.ForgeHooks.onDifficultyChange(p_104852_, this.f_104840_);
         this.f_104840_ = p_104852_;
      }

      public void m_104858_(boolean p_104859_) {
         this.f_104841_ = p_104859_;
      }

      public double m_171687_(LevelHeightAccessor p_171688_) {
         return this.f_104832_ ? (double)p_171688_.m_141937_() : 63.0D;
      }

      public double m_104876_() {
         return this.f_104832_ ? 1.0D : 0.03125D;
      }
   }

   @OnlyIn(Dist.CLIENT)
   final class EntityCallbacks implements LevelCallback<Entity> {
      public void m_141989_(Entity p_171696_) {
      }

      public void m_141986_(Entity p_171700_) {
      }

      public void m_141987_(Entity p_171704_) {
         ClientLevel.this.f_171630_.m_156908_(p_171704_);
      }

      public void m_141983_(Entity p_171708_) {
         ClientLevel.this.f_171630_.m_156912_(p_171708_);
      }

      public void m_141985_(Entity p_171712_) {
         if (p_171712_ instanceof AbstractClientPlayer) {
            ClientLevel.this.f_104566_.add((AbstractClientPlayer)p_171712_);
         }

      }

      public void m_141981_(Entity p_171716_) {
         p_171716_.m_19877_();
         ClientLevel.this.f_104566_.remove(p_171716_);

          p_171716_.onRemovedFromWorld();
          net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.EntityLeaveWorldEvent(p_171716_, ClientLevel.this));
      }
   }

   @OnlyIn(Dist.CLIENT)
   static enum MarkerParticleStatus {
      BARRIER(Blocks.f_50375_, ParticleTypes.f_123793_),
      LIGHT(Blocks.f_152480_, ParticleTypes.f_175835_);

      final Block f_171721_;
      final ParticleOptions f_171722_;

      private MarkerParticleStatus(Block p_171728_, ParticleOptions p_171729_) {
         this.f_171721_ = p_171728_;
         this.f_171722_ = p_171729_;
      }
   }
}
