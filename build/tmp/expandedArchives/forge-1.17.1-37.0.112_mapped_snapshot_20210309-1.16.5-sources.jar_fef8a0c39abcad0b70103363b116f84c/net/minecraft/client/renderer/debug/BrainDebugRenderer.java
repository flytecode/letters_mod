package net.minecraft.client.renderer.debug;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class BrainDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final Logger f_113193_ = LogManager.getLogger();
   private static final boolean f_173785_ = true;
   private static final boolean f_173786_ = false;
   private static final boolean f_173787_ = false;
   private static final boolean f_173788_ = false;
   private static final boolean f_173789_ = false;
   private static final boolean f_173790_ = false;
   private static final boolean f_173791_ = false;
   private static final boolean f_173792_ = false;
   private static final boolean f_173793_ = true;
   private static final boolean f_173794_ = true;
   private static final boolean f_173795_ = true;
   private static final boolean f_173796_ = true;
   private static final boolean f_173797_ = true;
   private static final boolean f_173798_ = true;
   private static final boolean f_173799_ = true;
   private static final boolean f_173800_ = true;
   private static final boolean f_173801_ = true;
   private static final boolean f_173802_ = true;
   private static final boolean f_173803_ = true;
   private static final boolean f_173804_ = true;
   private static final int f_173805_ = 30;
   private static final int f_173806_ = 30;
   private static final int f_173807_ = 8;
   private static final float f_173808_ = 0.02F;
   private static final int f_173809_ = -1;
   private static final int f_173778_ = -256;
   private static final int f_173779_ = -16711681;
   private static final int f_173780_ = -16711936;
   private static final int f_173781_ = -3355444;
   private static final int f_173782_ = -98404;
   private static final int f_173783_ = -65536;
   private static final int f_173784_ = -23296;
   private final Minecraft f_113194_;
   private final Map<BlockPos, BrainDebugRenderer.PoiInfo> f_113195_ = Maps.newHashMap();
   private final Map<UUID, BrainDebugRenderer.BrainDump> f_113196_ = Maps.newHashMap();
   @Nullable
   private UUID f_113197_;

   public BrainDebugRenderer(Minecraft p_113200_) {
      this.f_113194_ = p_113200_;
   }

   public void m_5630_() {
      this.f_113195_.clear();
      this.f_113196_.clear();
      this.f_113197_ = null;
   }

   public void m_113226_(BrainDebugRenderer.PoiInfo p_113227_) {
      this.f_113195_.put(p_113227_.f_113333_, p_113227_);
   }

   public void m_113228_(BlockPos p_113229_) {
      this.f_113195_.remove(p_113229_);
   }

   public void m_113230_(BlockPos p_113231_, int p_113232_) {
      BrainDebugRenderer.PoiInfo braindebugrenderer$poiinfo = this.f_113195_.get(p_113231_);
      if (braindebugrenderer$poiinfo == null) {
         f_113193_.warn("Strange, setFreeTicketCount was called for an unknown POI: {}", (Object)p_113231_);
      } else {
         braindebugrenderer$poiinfo.f_113335_ = p_113232_;
      }
   }

   public void m_113219_(BrainDebugRenderer.BrainDump p_113220_) {
      this.f_113196_.put(p_113220_.f_113293_, p_113220_);
   }

   public void m_173810_(int p_173811_) {
      this.f_113196_.values().removeIf((p_173814_) -> {
         return p_173814_.f_113294_ == p_173811_;
      });
   }

   public void m_7790_(PoseStack p_113214_, MultiBufferSource p_113215_, double p_113216_, double p_113217_, double p_113218_) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69472_();
      this.m_113264_();
      this.m_113202_(p_113216_, p_113217_, p_113218_);
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
      if (!this.f_113194_.f_91074_.m_5833_()) {
         this.m_113286_();
      }

   }

   private void m_113264_() {
      this.f_113196_.entrySet().removeIf((p_113263_) -> {
         Entity entity = this.f_113194_.f_91073_.m_6815_((p_113263_.getValue()).f_113294_);
         return entity == null || entity.m_146910_();
      });
   }

   private void m_113202_(double p_113203_, double p_113204_, double p_113205_) {
      BlockPos blockpos = new BlockPos(p_113203_, p_113204_, p_113205_);
      this.f_113196_.values().forEach((p_113210_) -> {
         if (this.m_113280_(p_113210_)) {
            this.m_113267_(p_113210_, p_113203_, p_113204_, p_113205_);
         }

      });

      for(BlockPos blockpos1 : this.f_113195_.keySet()) {
         if (blockpos.m_123314_(blockpos1, 30.0D)) {
            m_113274_(blockpos1);
         }
      }

      this.f_113195_.values().forEach((p_113238_) -> {
         if (blockpos.m_123314_(p_113238_.f_113333_, 30.0D)) {
            this.m_113272_(p_113238_);
         }

      });
      this.m_113279_().forEach((p_113241_, p_113242_) -> {
         if (blockpos.m_123314_(p_113241_, 30.0D)) {
            this.m_113243_(p_113241_, p_113242_);
         }

      });
   }

   private static void m_113274_(BlockPos p_113275_) {
      float f = 0.05F;
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      DebugRenderer.m_113463_(p_113275_, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
   }

   private void m_113243_(BlockPos p_113244_, List<String> p_113245_) {
      float f = 0.05F;
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      DebugRenderer.m_113463_(p_113244_, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
      m_113257_("" + p_113245_, p_113244_, 0, -256);
      m_113257_("Ghost POI", p_113244_, 1, -65536);
   }

   private void m_113272_(BrainDebugRenderer.PoiInfo p_113273_) {
      int i = 0;
      Set<String> set = this.m_113282_(p_113273_);
      if (set.size() < 4) {
         m_113252_("Owners: " + set, p_113273_, i, -256);
      } else {
         m_113252_(set.size() + " ticket holders", p_113273_, i, -256);
      }

      ++i;
      Set<String> set1 = this.m_113287_(p_113273_);
      if (set1.size() < 4) {
         m_113252_("Candidates: " + set1, p_113273_, i, -23296);
      } else {
         m_113252_(set1.size() + " potential owners", p_113273_, i, -23296);
      }

      ++i;
      m_113252_("Free tickets: " + p_113273_.f_113335_, p_113273_, i, -256);
      ++i;
      m_113252_(p_113273_.f_113334_, p_113273_, i, -1);
   }

   private void m_113221_(BrainDebugRenderer.BrainDump p_113222_, double p_113223_, double p_113224_, double p_113225_) {
      if (p_113222_.f_113302_ != null) {
         PathfindingRenderer.m_113620_(p_113222_.f_113302_, 0.5F, false, false, p_113223_, p_113224_, p_113225_);
      }

   }

   private void m_113267_(BrainDebugRenderer.BrainDump p_113268_, double p_113269_, double p_113270_, double p_113271_) {
      boolean flag = this.m_113265_(p_113268_);
      int i = 0;
      m_113246_(p_113268_.f_113300_, i, p_113268_.f_113295_, -1, 0.03F);
      ++i;
      if (flag) {
         m_113246_(p_113268_.f_113300_, i, p_113268_.f_113296_ + " " + p_113268_.f_113297_ + " xp", -1, 0.02F);
         ++i;
      }

      if (flag) {
         int j = p_113268_.f_113298_ < p_113268_.f_113299_ ? -23296 : -1;
         m_113246_(p_113268_.f_113300_, i, "health: " + String.format("%.1f", p_113268_.f_113298_) + " / " + String.format("%.1f", p_113268_.f_113299_), j, 0.02F);
         ++i;
      }

      if (flag && !p_113268_.f_113301_.equals("")) {
         m_113246_(p_113268_.f_113300_, i, p_113268_.f_113301_, -98404, 0.02F);
         ++i;
      }

      if (flag) {
         for(String s : p_113268_.f_113305_) {
            m_113246_(p_113268_.f_113300_, i, s, -16711681, 0.02F);
            ++i;
         }
      }

      if (flag) {
         for(String s1 : p_113268_.f_113304_) {
            m_113246_(p_113268_.f_113300_, i, s1, -16711936, 0.02F);
            ++i;
         }
      }

      if (p_113268_.f_113303_) {
         m_113246_(p_113268_.f_113300_, i, "Wants Golem", -23296, 0.02F);
         ++i;
      }

      if (flag) {
         for(String s2 : p_113268_.f_113307_) {
            if (s2.startsWith(p_113268_.f_113295_)) {
               m_113246_(p_113268_.f_113300_, i, s2, -1, 0.02F);
            } else {
               m_113246_(p_113268_.f_113300_, i, s2, -23296, 0.02F);
            }

            ++i;
         }
      }

      if (flag) {
         for(String s3 : Lists.reverse(p_113268_.f_113306_)) {
            m_113246_(p_113268_.f_113300_, i, s3, -3355444, 0.02F);
            ++i;
         }
      }

      if (flag) {
         this.m_113221_(p_113268_, p_113269_, p_113270_, p_113271_);
      }

   }

   private static void m_113252_(String p_113253_, BrainDebugRenderer.PoiInfo p_113254_, int p_113255_, int p_113256_) {
      BlockPos blockpos = p_113254_.f_113333_;
      m_113257_(p_113253_, blockpos, p_113255_, p_113256_);
   }

   private static void m_113257_(String p_113258_, BlockPos p_113259_, int p_113260_, int p_113261_) {
      double d0 = 1.3D;
      double d1 = 0.2D;
      double d2 = (double)p_113259_.m_123341_() + 0.5D;
      double d3 = (double)p_113259_.m_123342_() + 1.3D + (double)p_113260_ * 0.2D;
      double d4 = (double)p_113259_.m_123343_() + 0.5D;
      DebugRenderer.m_113490_(p_113258_, d2, d3, d4, p_113261_, 0.02F, true, 0.0F, true);
   }

   private static void m_113246_(Position p_113247_, int p_113248_, String p_113249_, int p_113250_, float p_113251_) {
      double d0 = 2.4D;
      double d1 = 0.25D;
      BlockPos blockpos = new BlockPos(p_113247_);
      double d2 = (double)blockpos.m_123341_() + 0.5D;
      double d3 = p_113247_.m_7098_() + 2.4D + (double)p_113248_ * 0.25D;
      double d4 = (double)blockpos.m_123343_() + 0.5D;
      float f = 0.5F;
      DebugRenderer.m_113490_(p_113249_, d2, d3, d4, p_113250_, p_113251_, false, 0.5F, true);
   }

   private Set<String> m_113282_(BrainDebugRenderer.PoiInfo p_113283_) {
      return this.m_113284_(p_113283_.f_113333_).stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet());
   }

   private Set<String> m_113287_(BrainDebugRenderer.PoiInfo p_113288_) {
      return this.m_113289_(p_113288_.f_113333_).stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet());
   }

   private boolean m_113265_(BrainDebugRenderer.BrainDump p_113266_) {
      return Objects.equals(this.f_113197_, p_113266_.f_113293_);
   }

   private boolean m_113280_(BrainDebugRenderer.BrainDump p_113281_) {
      Player player = this.f_113194_.f_91074_;
      BlockPos blockpos = new BlockPos(player.m_20185_(), p_113281_.f_113300_.m_7098_(), player.m_20189_());
      BlockPos blockpos1 = new BlockPos(p_113281_.f_113300_);
      return blockpos.m_123314_(blockpos1, 30.0D);
   }

   private Collection<UUID> m_113284_(BlockPos p_113285_) {
      return this.f_113196_.values().stream().filter((p_113278_) -> {
         return p_113278_.m_113326_(p_113285_);
      }).map(BrainDebugRenderer.BrainDump::m_113322_).collect(Collectors.toSet());
   }

   private Collection<UUID> m_113289_(BlockPos p_113290_) {
      return this.f_113196_.values().stream().filter((p_113235_) -> {
         return p_113235_.m_113331_(p_113290_);
      }).map(BrainDebugRenderer.BrainDump::m_113322_).collect(Collectors.toSet());
   }

   private Map<BlockPos, List<String>> m_113279_() {
      Map<BlockPos, List<String>> map = Maps.newHashMap();

      for(BrainDebugRenderer.BrainDump braindebugrenderer$braindump : this.f_113196_.values()) {
         for(BlockPos blockpos : Iterables.concat(braindebugrenderer$braindump.f_113308_, braindebugrenderer$braindump.f_113309_)) {
            if (!this.f_113195_.containsKey(blockpos)) {
               map.computeIfAbsent(blockpos, (p_113292_) -> {
                  return Lists.newArrayList();
               }).add(braindebugrenderer$braindump.f_113295_);
            }
         }
      }

      return map;
   }

   private void m_113286_() {
      DebugRenderer.m_113448_(this.f_113194_.m_91288_(), 8).ifPresent((p_113212_) -> {
         this.f_113197_ = p_113212_.m_142081_();
      });
   }

   @OnlyIn(Dist.CLIENT)
   public static class BrainDump {
      public final UUID f_113293_;
      public final int f_113294_;
      public final String f_113295_;
      public final String f_113296_;
      public final int f_113297_;
      public final float f_113298_;
      public final float f_113299_;
      public final Position f_113300_;
      public final String f_113301_;
      public final Path f_113302_;
      public final boolean f_113303_;
      public final List<String> f_113304_ = Lists.newArrayList();
      public final List<String> f_113305_ = Lists.newArrayList();
      public final List<String> f_113306_ = Lists.newArrayList();
      public final List<String> f_113307_ = Lists.newArrayList();
      public final Set<BlockPos> f_113308_ = Sets.newHashSet();
      public final Set<BlockPos> f_113309_ = Sets.newHashSet();

      public BrainDump(UUID p_113311_, int p_113312_, String p_113313_, String p_113314_, int p_113315_, float p_113316_, float p_113317_, Position p_113318_, String p_113319_, @Nullable Path p_113320_, boolean p_113321_) {
         this.f_113293_ = p_113311_;
         this.f_113294_ = p_113312_;
         this.f_113295_ = p_113313_;
         this.f_113296_ = p_113314_;
         this.f_113297_ = p_113315_;
         this.f_113298_ = p_113316_;
         this.f_113299_ = p_113317_;
         this.f_113300_ = p_113318_;
         this.f_113301_ = p_113319_;
         this.f_113302_ = p_113320_;
         this.f_113303_ = p_113321_;
      }

      boolean m_113326_(BlockPos p_113327_) {
         return this.f_113308_.stream().anyMatch(p_113327_::equals);
      }

      boolean m_113331_(BlockPos p_113332_) {
         return this.f_113309_.contains(p_113332_);
      }

      public UUID m_113322_() {
         return this.f_113293_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class PoiInfo {
      public final BlockPos f_113333_;
      public String f_113334_;
      public int f_113335_;

      public PoiInfo(BlockPos p_113337_, String p_113338_, int p_113339_) {
         this.f_113333_ = p_113337_;
         this.f_113334_ = p_113338_;
         this.f_113335_ = p_113339_;
      }
   }
}