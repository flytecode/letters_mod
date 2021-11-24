package net.minecraft.client.renderer.debug;

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
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeeDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final boolean f_173737_ = true;
   private static final boolean f_173738_ = true;
   private static final boolean f_173739_ = true;
   private static final boolean f_173740_ = true;
   private static final boolean f_173741_ = true;
   private static final boolean f_173742_ = false;
   private static final boolean f_173743_ = true;
   private static final boolean f_173744_ = true;
   private static final boolean f_173745_ = true;
   private static final boolean f_173746_ = true;
   private static final boolean f_173747_ = true;
   private static final boolean f_173748_ = true;
   private static final boolean f_173749_ = true;
   private static final boolean f_173750_ = true;
   private static final int f_173751_ = 30;
   private static final int f_173752_ = 30;
   private static final int f_173753_ = 8;
   private static final int f_173754_ = 20;
   private static final float f_173755_ = 0.02F;
   private static final int f_173756_ = -1;
   private static final int f_173757_ = -256;
   private static final int f_173758_ = -23296;
   private static final int f_173759_ = -16711936;
   private static final int f_173760_ = -3355444;
   private static final int f_173761_ = -98404;
   private static final int f_173762_ = -65536;
   private final Minecraft f_113048_;
   private final Map<BlockPos, BeeDebugRenderer.HiveInfo> f_113049_ = Maps.newHashMap();
   private final Map<UUID, BeeDebugRenderer.BeeInfo> f_113050_ = Maps.newHashMap();
   private UUID f_113051_;

   public BeeDebugRenderer(Minecraft p_113053_) {
      this.f_113048_ = p_113053_;
   }

   public void m_5630_() {
      this.f_113049_.clear();
      this.f_113050_.clear();
      this.f_113051_ = null;
   }

   public void m_113071_(BeeDebugRenderer.HiveInfo p_113072_) {
      this.f_113049_.put(p_113072_.f_113180_, p_113072_);
   }

   public void m_113066_(BeeDebugRenderer.BeeInfo p_113067_) {
      this.f_113050_.put(p_113067_.f_113157_, p_113067_);
   }

   public void m_173763_(int p_173764_) {
      this.f_113050_.values().removeIf((p_173767_) -> {
         return p_173767_.f_113158_ == p_173764_;
      });
   }

   public void m_7790_(PoseStack p_113061_, MultiBufferSource p_113062_, double p_113063_, double p_113064_, double p_113065_) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69472_();
      this.m_113136_();
      this.m_113126_();
      this.m_113141_();
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
      if (!this.f_113048_.f_91074_.m_5833_()) {
         this.m_113156_();
      }

   }

   private void m_113126_() {
      this.f_113050_.entrySet().removeIf((p_113132_) -> {
         return this.f_113048_.f_91073_.m_6815_((p_113132_.getValue()).f_113158_) == null;
      });
   }

   private void m_113136_() {
      long i = this.f_113048_.f_91073_.m_46467_() - 20L;
      this.f_113049_.entrySet().removeIf((p_113057_) -> {
         return (p_113057_.getValue()).f_113185_ < i;
      });
   }

   private void m_113141_() {
      BlockPos blockpos = this.m_113154_().m_90588_();
      this.f_113050_.values().forEach((p_113153_) -> {
         if (this.m_113147_(p_113153_)) {
            this.m_113137_(p_113153_);
         }

      });
      this.m_113151_();

      for(BlockPos blockpos1 : this.f_113049_.keySet()) {
         if (blockpos.m_123314_(blockpos1, 30.0D)) {
            m_113076_(blockpos1);
         }
      }

      Map<BlockPos, Set<UUID>> map = this.m_113146_();
      this.f_113049_.values().forEach((p_113098_) -> {
         if (blockpos.m_123314_(p_113098_.f_113180_, 30.0D)) {
            Set<UUID> set = map.get(p_113098_.f_113180_);
            this.m_113073_(p_113098_, (Collection<UUID>)(set == null ? Sets.newHashSet() : set));
         }

      });
      this.m_113155_().forEach((p_113090_, p_113091_) -> {
         if (blockpos.m_123314_(p_113090_, 30.0D)) {
            this.m_113092_(p_113090_, p_113091_);
         }

      });
   }

   private Map<BlockPos, Set<UUID>> m_113146_() {
      Map<BlockPos, Set<UUID>> map = Maps.newHashMap();
      this.f_113050_.values().forEach((p_113135_) -> {
         p_113135_.f_113165_.forEach((p_173771_) -> {
            map.computeIfAbsent(p_173771_, (p_173777_) -> {
               return Sets.newHashSet();
            }).add(p_113135_.m_113174_());
         });
      });
      return map;
   }

   private void m_113151_() {
      Map<BlockPos, Set<UUID>> map = Maps.newHashMap();
      this.f_113050_.values().stream().filter(BeeDebugRenderer.BeeInfo::m_113178_).forEach((p_113121_) -> {
         map.computeIfAbsent(p_113121_.f_113162_, (p_173775_) -> {
            return Sets.newHashSet();
         }).add(p_113121_.m_113174_());
      });
      map.entrySet().forEach((p_113118_) -> {
         BlockPos blockpos = p_113118_.getKey();
         Set<UUID> set = p_113118_.getValue();
         Set<String> set1 = set.stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet());
         int i = 1;
         m_113110_(set1.toString(), blockpos, i++, -256);
         m_113110_("Flower", blockpos, i++, -1);
         float f = 0.05F;
         m_113078_(blockpos, 0.05F, 0.8F, 0.8F, 0.0F, 0.3F);
      });
   }

   private static String m_113115_(Collection<UUID> p_113116_) {
      if (p_113116_.isEmpty()) {
         return "-";
      } else {
         return p_113116_.size() > 3 ? p_113116_.size() + " bees" : p_113116_.stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet()).toString();
      }
   }

   private static void m_113076_(BlockPos p_113077_) {
      float f = 0.05F;
      m_113078_(p_113077_, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
   }

   private void m_113092_(BlockPos p_113093_, List<String> p_113094_) {
      float f = 0.05F;
      m_113078_(p_113093_, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
      m_113110_("" + p_113094_, p_113093_, 0, -256);
      m_113110_("Ghost Hive", p_113093_, 1, -65536);
   }

   private static void m_113078_(BlockPos p_113079_, float p_113080_, float p_113081_, float p_113082_, float p_113083_, float p_113084_) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      DebugRenderer.m_113463_(p_113079_, p_113080_, p_113081_, p_113082_, p_113083_, p_113084_);
   }

   private void m_113073_(BeeDebugRenderer.HiveInfo p_113074_, Collection<UUID> p_113075_) {
      int i = 0;
      if (!p_113075_.isEmpty()) {
         m_113105_("Blacklisted by " + m_113115_(p_113075_), p_113074_, i++, -65536);
      }

      m_113105_("Out: " + m_113115_(this.m_113129_(p_113074_.f_113180_)), p_113074_, i++, -3355444);
      if (p_113074_.f_113182_ == 0) {
         m_113105_("In: -", p_113074_, i++, -256);
      } else if (p_113074_.f_113182_ == 1) {
         m_113105_("In: 1 bee", p_113074_, i++, -256);
      } else {
         m_113105_("In: " + p_113074_.f_113182_ + " bees", p_113074_, i++, -256);
      }

      m_113105_("Honey: " + p_113074_.f_113183_, p_113074_, i++, -23296);
      m_113105_(p_113074_.f_113181_ + (p_113074_.f_113184_ ? " (sedated)" : ""), p_113074_, i++, -1);
   }

   private void m_113127_(BeeDebugRenderer.BeeInfo p_113128_) {
      if (p_113128_.f_113160_ != null) {
         PathfindingRenderer.m_113620_(p_113128_.f_113160_, 0.5F, false, false, this.m_113154_().m_90583_().m_7096_(), this.m_113154_().m_90583_().m_7098_(), this.m_113154_().m_90583_().m_7094_());
      }

   }

   private void m_113137_(BeeDebugRenderer.BeeInfo p_113138_) {
      boolean flag = this.m_113142_(p_113138_);
      int i = 0;
      m_113099_(p_113138_.f_113159_, i++, p_113138_.toString(), -1, 0.03F);
      if (p_113138_.f_113161_ == null) {
         m_113099_(p_113138_.f_113159_, i++, "No hive", -98404, 0.02F);
      } else {
         m_113099_(p_113138_.f_113159_, i++, "Hive: " + this.m_113068_(p_113138_, p_113138_.f_113161_), -256, 0.02F);
      }

      if (p_113138_.f_113162_ == null) {
         m_113099_(p_113138_.f_113159_, i++, "No flower", -98404, 0.02F);
      } else {
         m_113099_(p_113138_.f_113159_, i++, "Flower: " + this.m_113068_(p_113138_, p_113138_.f_113162_), -256, 0.02F);
      }

      for(String s : p_113138_.f_113164_) {
         m_113099_(p_113138_.f_113159_, i++, s, -16711936, 0.02F);
      }

      if (flag) {
         this.m_113127_(p_113138_);
      }

      if (p_113138_.f_113163_ > 0) {
         int j = p_113138_.f_113163_ < 600 ? -3355444 : -23296;
         m_113099_(p_113138_.f_113159_, i++, "Travelling: " + p_113138_.f_113163_ + " ticks", j, 0.02F);
      }

   }

   private static void m_113105_(String p_113106_, BeeDebugRenderer.HiveInfo p_113107_, int p_113108_, int p_113109_) {
      BlockPos blockpos = p_113107_.f_113180_;
      m_113110_(p_113106_, blockpos, p_113108_, p_113109_);
   }

   private static void m_113110_(String p_113111_, BlockPos p_113112_, int p_113113_, int p_113114_) {
      double d0 = 1.3D;
      double d1 = 0.2D;
      double d2 = (double)p_113112_.m_123341_() + 0.5D;
      double d3 = (double)p_113112_.m_123342_() + 1.3D + (double)p_113113_ * 0.2D;
      double d4 = (double)p_113112_.m_123343_() + 0.5D;
      DebugRenderer.m_113490_(p_113111_, d2, d3, d4, p_113114_, 0.02F, true, 0.0F, true);
   }

   private static void m_113099_(Position p_113100_, int p_113101_, String p_113102_, int p_113103_, float p_113104_) {
      double d0 = 2.4D;
      double d1 = 0.25D;
      BlockPos blockpos = new BlockPos(p_113100_);
      double d2 = (double)blockpos.m_123341_() + 0.5D;
      double d3 = p_113100_.m_7098_() + 2.4D + (double)p_113101_ * 0.25D;
      double d4 = (double)blockpos.m_123343_() + 0.5D;
      float f = 0.5F;
      DebugRenderer.m_113490_(p_113102_, d2, d3, d4, p_113103_, p_113104_, false, 0.5F, true);
   }

   private Camera m_113154_() {
      return this.f_113048_.f_91063_.m_109153_();
   }

   private Set<String> m_173772_(BeeDebugRenderer.HiveInfo p_173773_) {
      return this.m_113129_(p_173773_.f_113180_).stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet());
   }

   private String m_113068_(BeeDebugRenderer.BeeInfo p_113069_, BlockPos p_113070_) {
      double d0 = Math.sqrt(p_113070_.m_123299_(p_113069_.f_113159_.m_7096_(), p_113069_.f_113159_.m_7098_(), p_113069_.f_113159_.m_7094_(), true));
      double d1 = (double)Math.round(d0 * 10.0D) / 10.0D;
      return p_113070_.m_123344_() + " (dist " + d1 + ")";
   }

   private boolean m_113142_(BeeDebugRenderer.BeeInfo p_113143_) {
      return Objects.equals(this.f_113051_, p_113143_.f_113157_);
   }

   private boolean m_113147_(BeeDebugRenderer.BeeInfo p_113148_) {
      Player player = this.f_113048_.f_91074_;
      BlockPos blockpos = new BlockPos(player.m_20185_(), p_113148_.f_113159_.m_7098_(), player.m_20189_());
      BlockPos blockpos1 = new BlockPos(p_113148_.f_113159_);
      return blockpos.m_123314_(blockpos1, 30.0D);
   }

   private Collection<UUID> m_113129_(BlockPos p_113130_) {
      return this.f_113050_.values().stream().filter((p_113087_) -> {
         return p_113087_.m_113175_(p_113130_);
      }).map(BeeDebugRenderer.BeeInfo::m_113174_).collect(Collectors.toSet());
   }

   private Map<BlockPos, List<String>> m_113155_() {
      Map<BlockPos, List<String>> map = Maps.newHashMap();

      for(BeeDebugRenderer.BeeInfo beedebugrenderer$beeinfo : this.f_113050_.values()) {
         if (beedebugrenderer$beeinfo.f_113161_ != null && !this.f_113049_.containsKey(beedebugrenderer$beeinfo.f_113161_)) {
            map.computeIfAbsent(beedebugrenderer$beeinfo.f_113161_, (p_113140_) -> {
               return Lists.newArrayList();
            }).add(beedebugrenderer$beeinfo.m_113177_());
         }
      }

      return map;
   }

   private void m_113156_() {
      DebugRenderer.m_113448_(this.f_113048_.m_91288_(), 8).ifPresent((p_113059_) -> {
         this.f_113051_ = p_113059_.m_142081_();
      });
   }

   @OnlyIn(Dist.CLIENT)
   public static class BeeInfo {
      public final UUID f_113157_;
      public final int f_113158_;
      public final Position f_113159_;
      @Nullable
      public final Path f_113160_;
      @Nullable
      public final BlockPos f_113161_;
      @Nullable
      public final BlockPos f_113162_;
      public final int f_113163_;
      public final List<String> f_113164_ = Lists.newArrayList();
      public final Set<BlockPos> f_113165_ = Sets.newHashSet();

      public BeeInfo(UUID p_113167_, int p_113168_, Position p_113169_, Path p_113170_, BlockPos p_113171_, BlockPos p_113172_, int p_113173_) {
         this.f_113157_ = p_113167_;
         this.f_113158_ = p_113168_;
         this.f_113159_ = p_113169_;
         this.f_113160_ = p_113170_;
         this.f_113161_ = p_113171_;
         this.f_113162_ = p_113172_;
         this.f_113163_ = p_113173_;
      }

      public boolean m_113175_(BlockPos p_113176_) {
         return this.f_113161_ != null && this.f_113161_.equals(p_113176_);
      }

      public UUID m_113174_() {
         return this.f_113157_;
      }

      public String m_113177_() {
         return DebugEntityNameGenerator.m_133668_(this.f_113157_);
      }

      public String toString() {
         return this.m_113177_();
      }

      public boolean m_113178_() {
         return this.f_113162_ != null;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class HiveInfo {
      public final BlockPos f_113180_;
      public final String f_113181_;
      public final int f_113182_;
      public final int f_113183_;
      public final boolean f_113184_;
      public final long f_113185_;

      public HiveInfo(BlockPos p_113187_, String p_113188_, int p_113189_, int p_113190_, boolean p_113191_, long p_113192_) {
         this.f_113180_ = p_113187_;
         this.f_113181_ = p_113188_;
         this.f_113182_ = p_113189_;
         this.f_113183_ = p_113190_;
         this.f_113184_ = p_113191_;
         this.f_113185_ = p_113192_;
      }
   }
}