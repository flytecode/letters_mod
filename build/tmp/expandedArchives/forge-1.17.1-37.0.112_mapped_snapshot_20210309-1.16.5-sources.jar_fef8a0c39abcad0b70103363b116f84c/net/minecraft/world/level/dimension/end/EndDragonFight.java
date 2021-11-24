package net.minecraft.world.level.dimension.end;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.Features;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockPredicate;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EndDragonFight {
   private static final Logger f_64058_ = LogManager.getLogger();
   private static final int f_156737_ = 1200;
   private static final int f_156738_ = 100;
   private static final int f_156739_ = 20;
   private static final int f_156740_ = 8;
   public static final int f_156735_ = 9;
   private static final int f_156741_ = 20;
   private static final int f_156742_ = 96;
   public static final int f_156736_ = 128;
   private static final Predicate<Entity> f_64059_ = EntitySelector.f_20402_.and(EntitySelector.m_20410_(0.0D, 128.0D, 0.0D, 192.0D));
   private final ServerBossEvent f_64060_ = (ServerBossEvent)(new ServerBossEvent(new TranslatableComponent("entity.minecraft.ender_dragon"), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS)).m_7005_(true).m_7006_(true);
   private final ServerLevel f_64061_;
   private final List<Integer> f_64062_ = Lists.newArrayList();
   private final BlockPattern f_64063_;
   private int f_64064_;
   private int f_64065_;
   private int f_64066_;
   private int f_64067_;
   private boolean f_64068_;
   private boolean f_64069_;
   private UUID f_64070_;
   private boolean f_64071_ = true;
   private BlockPos f_64072_;
   private DragonRespawnAnimation f_64073_;
   private int f_64074_;
   private List<EndCrystal> f_64075_;

   public EndDragonFight(ServerLevel p_64078_, long p_64079_, CompoundTag p_64080_) {
      this.f_64061_ = p_64078_;
      if (p_64080_.m_128441_("NeedsStateScanning")) {
         this.f_64071_ = p_64080_.m_128471_("NeedsStateScanning");
      }

      if (p_64080_.m_128425_("DragonKilled", 99)) {
         if (p_64080_.m_128403_("Dragon")) {
            this.f_64070_ = p_64080_.m_128342_("Dragon");
         }

         this.f_64068_ = p_64080_.m_128471_("DragonKilled");
         this.f_64069_ = p_64080_.m_128471_("PreviouslyKilled");
         this.f_64071_ = !p_64080_.m_128471_("LegacyScanPerformed"); // Forge: fix MC-105080
         if (p_64080_.m_128471_("IsRespawning")) {
            this.f_64073_ = DragonRespawnAnimation.START;
         }

         if (p_64080_.m_128425_("ExitPortalLocation", 10)) {
            this.f_64072_ = NbtUtils.m_129239_(p_64080_.m_128469_("ExitPortalLocation"));
         }
      } else {
         this.f_64068_ = true;
         this.f_64069_ = true;
      }

      if (p_64080_.m_128425_("Gateways", 9)) {
         ListTag listtag = p_64080_.m_128437_("Gateways", 3);

         for(int i = 0; i < listtag.size(); ++i) {
            this.f_64062_.add(listtag.m_128763_(i));
         }
      } else {
         this.f_64062_.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
         Collections.shuffle(this.f_64062_, new Random(p_64079_));
      }

      this.f_64063_ = BlockPatternBuilder.m_61243_().m_61247_("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").m_61247_("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").m_61247_("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").m_61247_("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ").m_61247_("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ").m_61244_('#', BlockInWorld.m_61169_(BlockPredicate.m_61275_(Blocks.f_50752_))).m_61249_();
   }

   public CompoundTag m_64081_() {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128379_("NeedsStateScanning", this.f_64071_);
      if (this.f_64070_ != null) {
         compoundtag.m_128362_("Dragon", this.f_64070_);

      compoundtag.m_128379_("DragonKilled", this.f_64068_);
      compoundtag.m_128379_("PreviouslyKilled", this.f_64069_);
      compoundtag.m_128379_("LegacyScanPerformed", !this.f_64071_); // Forge: fix MC-105080
      if (this.f_64072_ != null) {
         compoundtag.m_128365_("ExitPortalLocation", NbtUtils.m_129224_(this.f_64072_));
      }
      }

      ListTag listtag = new ListTag();

      for(int i : this.f_64062_) {
         listtag.add(IntTag.m_128679_(i));
      }

      compoundtag.m_128365_("Gateways", listtag);
      return compoundtag;
   }

   public void m_64095_() {
      this.f_64060_.m_8321_(!this.f_64068_);
      if (++this.f_64067_ >= 20) {
         this.m_64107_();
         this.f_64067_ = 0;
      }

      if (!this.f_64060_.m_8324_().isEmpty()) {
         this.f_64061_.m_7726_().m_8387_(TicketType.f_9443_, new ChunkPos(0, 0), 9, Unit.INSTANCE);
         boolean flag = this.m_64106_();
         if (this.f_64071_ && flag) {
            this.m_64102_();
            this.f_64071_ = false;
         }

         if (this.f_64073_ != null) {
            if (this.f_64075_ == null && flag) {
               this.f_64073_ = null;
               this.m_64100_();
            }

            this.f_64073_.m_6363_(this.f_64061_, this, this.f_64075_, this.f_64074_++, this.f_64072_);
         }

         if (!this.f_64068_) {
            if ((this.f_64070_ == null || ++this.f_64064_ >= 1200) && flag) {
               this.m_64103_();
               this.f_64064_ = 0;
            }

            if (++this.f_64066_ >= 100 && flag) {
               this.m_64108_();
               this.f_64066_ = 0;
            }
         }
      } else {
         this.f_64061_.m_7726_().m_8438_(TicketType.f_9443_, new ChunkPos(0, 0), 9, Unit.INSTANCE);
      }

   }

   private void m_64102_() {
      f_64058_.info("Scanning for legacy world dragon fight...");
      boolean flag = this.m_64104_();
      if (flag) {
         f_64058_.info("Found that the dragon has been killed in this world already.");
         this.f_64069_ = true;
      } else {
         f_64058_.info("Found that the dragon has not yet been killed in this world.");
         this.f_64069_ = false;
         if (this.m_64105_() == null) {
            this.m_64093_(false);
         }
      }

      List<? extends EnderDragon> list = this.f_64061_.m_8857_();
      if (list.isEmpty()) {
         this.f_64068_ = true;
      } else {
         EnderDragon enderdragon = list.get(0);
         this.f_64070_ = enderdragon.m_142081_();
         f_64058_.info("Found that there's a dragon still alive ({})", (Object)enderdragon);
         this.f_64068_ = false;
         if (!flag) {
            f_64058_.info("But we didn't have a portal, let's remove it.");
            enderdragon.m_146870_();
            this.f_64070_ = null;
         }
      }

      if (!this.f_64069_ && this.f_64068_) {
         this.f_64068_ = false;
      }

   }

   private void m_64103_() {
      List<? extends EnderDragon> list = this.f_64061_.m_8857_();
      if (list.isEmpty()) {
         f_64058_.debug("Haven't seen the dragon, respawning it");
         this.m_64110_();
      } else {
         f_64058_.debug("Haven't seen our dragon, but found another one to use.");
         this.f_64070_ = list.get(0).m_142081_();
      }

   }

   protected void m_64087_(DragonRespawnAnimation p_64088_) {
      if (this.f_64073_ == null) {
         throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
      } else {
         this.f_64074_ = 0;
         if (p_64088_ == DragonRespawnAnimation.END) {
            this.f_64073_ = null;
            this.f_64068_ = false;
            EnderDragon enderdragon = this.m_64110_();

            for(ServerPlayer serverplayer : this.f_64060_.m_8324_()) {
               CriteriaTriggers.f_10580_.m_68256_(serverplayer, enderdragon);
            }
         } else {
            this.f_64073_ = p_64088_;
         }

      }
   }

   private boolean m_64104_() {
      for(int i = -8; i <= 8; ++i) {
         for(int j = -8; j <= 8; ++j) {
            LevelChunk levelchunk = this.f_64061_.m_6325_(i, j);

            for(BlockEntity blockentity : levelchunk.m_62954_().values()) {
               if (blockentity instanceof TheEndPortalBlockEntity) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private BlockPattern.BlockPatternMatch m_64105_() {
      for(int i = -8; i <= 8; ++i) {
         for(int j = -8; j <= 8; ++j) {
            LevelChunk levelchunk = this.f_64061_.m_6325_(i, j);

            for(BlockEntity blockentity : levelchunk.m_62954_().values()) {
               if (blockentity instanceof TheEndPortalBlockEntity) {
                  BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = this.f_64063_.m_61184_(this.f_64061_, blockentity.m_58899_());
                  if (blockpattern$blockpatternmatch != null) {
                     BlockPos blockpos = blockpattern$blockpatternmatch.m_61229_(3, 3, 3).m_61176_();
                     if (this.f_64072_ == null) {
                        this.f_64072_ = blockpos;
                     }

                     return blockpattern$blockpatternmatch;
                  }
               }
            }
         }
      }

      int k = this.f_64061_.m_5452_(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.f_65714_).m_123342_();

      for(int l = k; l >= this.f_64061_.m_141937_(); --l) {
         BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch1 = this.f_64063_.m_61184_(this.f_64061_, new BlockPos(EndPodiumFeature.f_65714_.m_123341_(), l, EndPodiumFeature.f_65714_.m_123343_()));
         if (blockpattern$blockpatternmatch1 != null) {
            if (this.f_64072_ == null) {
               this.f_64072_ = blockpattern$blockpatternmatch1.m_61229_(3, 3, 3).m_61176_();
            }

            return blockpattern$blockpatternmatch1;
         }
      }

      return null;
   }

   private boolean m_64106_() {
      for(int i = -8; i <= 8; ++i) {
         for(int j = 8; j <= 8; ++j) {
            ChunkAccess chunkaccess = this.f_64061_.m_6522_(i, j, ChunkStatus.f_62326_, false);
            if (!(chunkaccess instanceof LevelChunk)) {
               return false;
            }

            ChunkHolder.FullChunkStatus chunkholder$fullchunkstatus = ((LevelChunk)chunkaccess).m_6708_();
            if (!chunkholder$fullchunkstatus.m_140114_(ChunkHolder.FullChunkStatus.TICKING)) {
               return false;
            }
         }
      }

      return true;
   }

   private void m_64107_() {
      Set<ServerPlayer> set = Sets.newHashSet();

      for(ServerPlayer serverplayer : this.f_64061_.m_8795_(f_64059_)) {
         this.f_64060_.m_6543_(serverplayer);
         set.add(serverplayer);
      }

      Set<ServerPlayer> set1 = Sets.newHashSet(this.f_64060_.m_8324_());
      set1.removeAll(set);

      for(ServerPlayer serverplayer1 : set1) {
         this.f_64060_.m_6539_(serverplayer1);
      }

   }

   private void m_64108_() {
      this.f_64066_ = 0;
      this.f_64065_ = 0;

      for(SpikeFeature.EndSpike spikefeature$endspike : SpikeFeature.m_66858_(this.f_64061_)) {
         this.f_64065_ += this.f_64061_.m_45976_(EndCrystal.class, spikefeature$endspike.m_66905_()).size();
      }

      f_64058_.debug("Found {} end crystals still alive", (int)this.f_64065_);
   }

   public void m_64085_(EnderDragon p_64086_) {
      if (p_64086_.m_142081_().equals(this.f_64070_)) {
         this.f_64060_.m_142711_(0.0F);
         this.f_64060_.m_8321_(false);
         this.m_64093_(true);
         this.m_64109_();
         if (!this.f_64069_) {
            this.f_64061_.m_46597_(this.f_64061_.m_5452_(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.f_65714_), Blocks.f_50260_.m_49966_());
         }

         this.f_64069_ = true;
         this.f_64068_ = true;
      }

   }

   private void m_64109_() {
      if (!this.f_64062_.isEmpty()) {
         int i = this.f_64062_.remove(this.f_64062_.size() - 1);
         int j = Mth.m_14107_(96.0D * Math.cos(2.0D * (-Math.PI + 0.15707963267948966D * (double)i)));
         int k = Mth.m_14107_(96.0D * Math.sin(2.0D * (-Math.PI + 0.15707963267948966D * (double)i)));
         this.m_64089_(new BlockPos(j, 75, k));
      }
   }

   private void m_64089_(BlockPos p_64090_) {
      this.f_64061_.m_46796_(3000, p_64090_, 0);
      Features.f_126993_.m_65385_(this.f_64061_, this.f_64061_.m_7726_().m_8481_(), new Random(), p_64090_);
   }

   private void m_64093_(boolean p_64094_) {
      EndPodiumFeature endpodiumfeature = new EndPodiumFeature(p_64094_);
      if (this.f_64072_ == null) {
         for(this.f_64072_ = this.f_64061_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_).m_7495_(); this.f_64061_.m_8055_(this.f_64072_).m_60713_(Blocks.f_50752_) && this.f_64072_.m_123342_() > this.f_64061_.m_5736_(); this.f_64072_ = this.f_64072_.m_7495_()) {
         }
      }

      endpodiumfeature.m_65815_(FeatureConfiguration.f_67737_).m_65385_(this.f_64061_, this.f_64061_.m_7726_().m_8481_(), new Random(), this.f_64072_);
   }

   private EnderDragon m_64110_() {
      this.f_64061_.m_46745_(new BlockPos(0, 128, 0));
      EnderDragon enderdragon = EntityType.f_20565_.m_20615_(this.f_64061_);
      enderdragon.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
      enderdragon.m_7678_(0.0D, 128.0D, 0.0D, this.f_64061_.f_46441_.nextFloat() * 360.0F, 0.0F);
      this.f_64061_.m_7967_(enderdragon);
      this.f_64070_ = enderdragon.m_142081_();
      return enderdragon;
   }

   public void m_64096_(EnderDragon p_64097_) {
      if (p_64097_.m_142081_().equals(this.f_64070_)) {
         this.f_64060_.m_142711_(p_64097_.m_21223_() / p_64097_.m_21233_());
         this.f_64064_ = 0;
         if (p_64097_.m_8077_()) {
            this.f_64060_.m_6456_(p_64097_.m_5446_());
         }
      }

   }

   public int m_64098_() {
      return this.f_64065_;
   }

   public void m_64082_(EndCrystal p_64083_, DamageSource p_64084_) {
      if (this.f_64073_ != null && this.f_64075_.contains(p_64083_)) {
         f_64058_.debug("Aborting respawn sequence");
         this.f_64073_ = null;
         this.f_64074_ = 0;
         this.m_64101_();
         this.m_64093_(true);
      } else {
         this.m_64108_();
         Entity entity = this.f_64061_.m_8791_(this.f_64070_);
         if (entity instanceof EnderDragon) {
            ((EnderDragon)entity).m_31124_(p_64083_, p_64083_.m_142538_(), p_64084_);
         }
      }

   }

   public boolean m_64099_() {
      return this.f_64069_;
   }

   public void m_64100_() {
      if (this.f_64068_ && this.f_64073_ == null) {
         BlockPos blockpos = this.f_64072_;
         if (blockpos == null) {
            f_64058_.debug("Tried to respawn, but need to find the portal first.");
            BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = this.m_64105_();
            if (blockpattern$blockpatternmatch == null) {
               f_64058_.debug("Couldn't find a portal, so we made one.");
               this.m_64093_(true);
            } else {
               f_64058_.debug("Found the exit portal & saved its location for next time.");
            }

            blockpos = this.f_64072_;
         }

         List<EndCrystal> list1 = Lists.newArrayList();
         BlockPos blockpos1 = blockpos.m_6630_(1);

         for(Direction direction : Direction.Plane.HORIZONTAL) {
            List<EndCrystal> list = this.f_64061_.m_45976_(EndCrystal.class, new AABB(blockpos1.m_5484_(direction, 2)));
            if (list.isEmpty()) {
               return;
            }

            list1.addAll(list);
         }

         f_64058_.debug("Found all crystals, respawning dragon.");
         this.m_64091_(list1);
      }

   }

   private void m_64091_(List<EndCrystal> p_64092_) {
      if (this.f_64068_ && this.f_64073_ == null) {
         for(BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = this.m_64105_(); blockpattern$blockpatternmatch != null; blockpattern$blockpatternmatch = this.m_64105_()) {
            for(int i = 0; i < this.f_64063_.m_61203_(); ++i) {
               for(int j = 0; j < this.f_64063_.m_61202_(); ++j) {
                  for(int k = 0; k < this.f_64063_.m_61183_(); ++k) {
                     BlockInWorld blockinworld = blockpattern$blockpatternmatch.m_61229_(i, j, k);
                     if (blockinworld.m_61168_().m_60713_(Blocks.f_50752_) || blockinworld.m_61168_().m_60713_(Blocks.f_50257_)) {
                        this.f_64061_.m_46597_(blockinworld.m_61176_(), Blocks.f_50259_.m_49966_());
                     }
                  }
               }
            }
         }

         this.f_64073_ = DragonRespawnAnimation.START;
         this.f_64074_ = 0;
         this.m_64093_(false);
         this.f_64075_ = p_64092_;
      }

   }

   public void m_64101_() {
      for(SpikeFeature.EndSpike spikefeature$endspike : SpikeFeature.m_66858_(this.f_64061_)) {
         for(EndCrystal endcrystal : this.f_64061_.m_45976_(EndCrystal.class, spikefeature$endspike.m_66905_())) {
            endcrystal.m_20331_(false);
            endcrystal.m_31052_((BlockPos)null);
         }
      }
   }

   public void addPlayer(ServerPlayer player) {
      this.f_64060_.m_6543_(player);
   }

   public void removePlayer(ServerPlayer player) {
      this.f_64060_.m_6539_(player);
   }
}
