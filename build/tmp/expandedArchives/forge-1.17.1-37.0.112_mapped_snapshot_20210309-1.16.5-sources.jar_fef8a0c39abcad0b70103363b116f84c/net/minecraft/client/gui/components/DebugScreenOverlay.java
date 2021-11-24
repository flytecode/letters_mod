package net.minecraft.client.gui.components;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DebugScreenOverlay extends GuiComponent {
   private static final int f_168988_ = 14737632;
   private static final int f_168989_ = 2;
   private static final int f_168990_ = 2;
   private static final int f_168991_ = 2;
   private static final Map<Heightmap.Types, String> f_94029_ = Util.m_137469_(new EnumMap<>(Heightmap.Types.class), (p_94070_) -> {
      p_94070_.put(Heightmap.Types.WORLD_SURFACE_WG, "SW");
      p_94070_.put(Heightmap.Types.WORLD_SURFACE, "S");
      p_94070_.put(Heightmap.Types.OCEAN_FLOOR_WG, "OW");
      p_94070_.put(Heightmap.Types.OCEAN_FLOOR, "O");
      p_94070_.put(Heightmap.Types.MOTION_BLOCKING, "M");
      p_94070_.put(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, "ML");
   });
   private final Minecraft f_94030_;
   private final Font f_94031_;
   protected HitResult f_94032_;
   protected HitResult f_94033_;
   @Nullable
   private ChunkPos f_94034_;
   @Nullable
   private LevelChunk f_94035_;
   @Nullable
   private CompletableFuture<LevelChunk> f_94036_;
   private static final int f_168992_ = -65536;
   private static final int f_168993_ = -256;
   private static final int f_168994_ = -16711936;

   public DebugScreenOverlay(Minecraft p_94039_) {
      this.f_94030_ = p_94039_;
      this.f_94031_ = p_94039_.f_91062_;
   }

   public void m_94040_() {
      this.f_94036_ = null;
      this.f_94035_ = null;
   }

   public void m_94056_(PoseStack p_94057_) {
      this.f_94030_.m_91307_().m_6180_("debug");
      Entity entity = this.f_94030_.m_91288_();
      this.f_94032_ = entity.m_19907_(20.0D, 0.0F, false);
      this.f_94033_ = entity.m_19907_(20.0D, 0.0F, true);
      this.m_94076_(p_94057_);
      this.m_94079_(p_94057_);
      if (this.f_94030_.f_91066_.f_92065_) {
         int i = this.f_94030_.m_91268_().m_85445_();
         this.m_94058_(p_94057_, this.f_94030_.m_91293_(), 0, i / 2, true);
         IntegratedServer integratedserver = this.f_94030_.m_91092_();
         if (integratedserver != null) {
            this.m_94058_(p_94057_, integratedserver.m_129904_(), i - Math.min(i / 2, 240), i / 2, false);
         }
      }

      this.f_94030_.m_91307_().m_7238_();
   }

   protected void m_94076_(PoseStack p_94077_) {
      List<String> list = this.m_94075_();
      list.add("");
      boolean flag = this.f_94030_.m_91092_() != null;
      list.add("Debug: Pie [shift]: " + (this.f_94030_.f_91066_.f_92064_ ? "visible" : "hidden") + (flag ? " FPS + TPS" : " FPS") + " [alt]: " + (this.f_94030_.f_91066_.f_92065_ ? "visible" : "hidden"));
      list.add("For help: press F3 + Q");

      for(int i = 0; i < list.size(); ++i) {
         String s = list.get(i);
         if (!Strings.isNullOrEmpty(s)) {
            int j = 9;
            int k = this.f_94031_.m_92895_(s);
            int l = 2;
            int i1 = 2 + j * i;
            m_93172_(p_94077_, 1, i1 - 1, 2 + k + 1, i1 + j - 1, -1873784752);
            this.f_94031_.m_92883_(p_94077_, s, 2.0F, (float)i1, 14737632);
         }
      }

   }

   protected void m_94079_(PoseStack p_94080_) {
      List<String> list = this.m_94078_();

      for(int i = 0; i < list.size(); ++i) {
         String s = list.get(i);
         if (!Strings.isNullOrEmpty(s)) {
            int j = 9;
            int k = this.f_94031_.m_92895_(s);
            int l = this.f_94030_.m_91268_().m_85445_() - 2 - k;
            int i1 = 2 + j * i;
            m_93172_(p_94080_, l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
            this.f_94031_.m_92883_(p_94080_, s, (float)l, (float)i1, 14737632);
         }
      }

   }

   protected List<String> m_94075_() {
      IntegratedServer integratedserver = this.f_94030_.m_91092_();
      Connection connection = this.f_94030_.m_91403_().m_6198_();
      float f = connection.m_129543_();
      float f1 = connection.m_129542_();
      String s;
      if (integratedserver != null) {
         s = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", integratedserver.m_129903_(), f, f1);
      } else {
         s = String.format("\"%s\" server, %.0f tx, %.0f rx", this.f_94030_.f_91074_.m_108629_(), f, f1);
      }

      BlockPos blockpos = this.f_94030_.m_91288_().m_142538_();
      if (this.f_94030_.m_91299_()) {
         return Lists.newArrayList("Minecraft " + SharedConstants.m_136187_().getName() + " (" + this.f_94030_.m_91388_() + "/" + ClientBrandRetriever.m_129629_() + ")", this.f_94030_.f_90977_, s, this.f_94030_.f_91060_.m_109820_(), this.f_94030_.f_91060_.m_109822_(), "P: " + this.f_94030_.f_91061_.m_107403_() + ". T: " + this.f_94030_.f_91073_.m_104813_(), this.f_94030_.f_91073_.m_46464_(), "", String.format("Chunk-relative: %d %d %d", blockpos.m_123341_() & 15, blockpos.m_123342_() & 15, blockpos.m_123343_() & 15));
      } else {
         Entity entity = this.f_94030_.m_91288_();
         Direction direction = entity.m_6350_();
         String s1;
         switch(direction) {
         case NORTH:
            s1 = "Towards negative Z";
            break;
         case SOUTH:
            s1 = "Towards positive Z";
            break;
         case WEST:
            s1 = "Towards negative X";
            break;
         case EAST:
            s1 = "Towards positive X";
            break;
         default:
            s1 = "Invalid";
         }

         ChunkPos chunkpos = new ChunkPos(blockpos);
         if (!Objects.equals(this.f_94034_, chunkpos)) {
            this.f_94034_ = chunkpos;
            this.m_94040_();
         }

         Level level = this.m_94083_();
         LongSet longset = (LongSet)(level instanceof ServerLevel ? ((ServerLevel)level).m_8902_() : LongSets.EMPTY_SET);
         List<String> list = Lists.newArrayList("Minecraft " + SharedConstants.m_136187_().getName() + " (" + this.f_94030_.m_91388_() + "/" + ClientBrandRetriever.m_129629_() + ("release".equalsIgnoreCase(this.f_94030_.m_91389_()) ? "" : "/" + this.f_94030_.m_91389_()) + ")", this.f_94030_.f_90977_, s, this.f_94030_.f_91060_.m_109820_(), this.f_94030_.f_91060_.m_109822_(), "P: " + this.f_94030_.f_91061_.m_107403_() + ". T: " + this.f_94030_.f_91073_.m_104813_(), this.f_94030_.f_91073_.m_46464_());
         String s2 = this.m_94082_();
         if (s2 != null) {
            list.add(s2);
         }

         list.add(this.f_94030_.f_91073_.m_46472_().m_135782_() + " FC: " + longset.size());
         list.add("");
         list.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", this.f_94030_.m_91288_().m_20185_(), this.f_94030_.m_91288_().m_20186_(), this.f_94030_.m_91288_().m_20189_()));
         list.add(String.format("Block: %d %d %d", blockpos.m_123341_(), blockpos.m_123342_(), blockpos.m_123343_()));
         list.add(String.format("Chunk: %d %d %d in %d %d %d", blockpos.m_123341_() & 15, blockpos.m_123342_() & 15, blockpos.m_123343_() & 15, SectionPos.m_123171_(blockpos.m_123341_()), SectionPos.m_123171_(blockpos.m_123342_()), SectionPos.m_123171_(blockpos.m_123343_())));
         list.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, s1, Mth.m_14177_(entity.m_146908_()), Mth.m_14177_(entity.m_146909_())));
         LevelChunk levelchunk = this.m_94085_();
         if (levelchunk.m_6430_()) {
            list.add("Waiting for chunk...");
         } else {
            int i = this.f_94030_.f_91073_.m_7726_().m_7827_().m_75831_(blockpos, 0);
            int j = this.f_94030_.f_91073_.m_45517_(LightLayer.SKY, blockpos);
            int k = this.f_94030_.f_91073_.m_45517_(LightLayer.BLOCK, blockpos);
            list.add("Client Light: " + i + " (" + j + " sky, " + k + " block)");
            LevelChunk levelchunk1 = this.m_94084_();
            StringBuilder stringbuilder = new StringBuilder("CH");

            for(Heightmap.Types heightmap$types : Heightmap.Types.values()) {
               if (heightmap$types.m_64297_()) {
                  stringbuilder.append(" ").append(f_94029_.get(heightmap$types)).append(": ").append(levelchunk.m_5885_(heightmap$types, blockpos.m_123341_(), blockpos.m_123343_()));
               }
            }

            list.add(stringbuilder.toString());
            stringbuilder.setLength(0);
            stringbuilder.append("SH");

            for(Heightmap.Types heightmap$types1 : Heightmap.Types.values()) {
               if (heightmap$types1.m_64298_()) {
                  stringbuilder.append(" ").append(f_94029_.get(heightmap$types1)).append(": ");
                  if (levelchunk1 != null) {
                     stringbuilder.append(levelchunk1.m_5885_(heightmap$types1, blockpos.m_123341_(), blockpos.m_123343_()));
                  } else {
                     stringbuilder.append("??");
                  }
               }
            }

            list.add(stringbuilder.toString());
            if (blockpos.m_123342_() >= this.f_94030_.f_91073_.m_141937_() && blockpos.m_123342_() < this.f_94030_.f_91073_.m_151558_()) {
               list.add("Biome: " + this.f_94030_.f_91073_.m_5962_().m_175515_(Registry.f_122885_).m_7981_(this.f_94030_.f_91073_.m_46857_(blockpos)));
               long i1 = 0L;
               float f2 = 0.0F;
               if (levelchunk1 != null) {
                  f2 = level.m_46940_();
                  i1 = levelchunk1.m_6319_();
               }

               DifficultyInstance difficultyinstance = new DifficultyInstance(level.m_46791_(), level.m_46468_(), i1, f2);
               list.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f (Day %d)", difficultyinstance.m_19056_(), difficultyinstance.m_19057_(), this.f_94030_.f_91073_.m_46468_() / 24000L));
            }
         }

         ServerLevel serverlevel = this.m_94081_();
         if (serverlevel != null) {
            NaturalSpawner.SpawnState naturalspawner$spawnstate = serverlevel.m_7726_().m_8485_();
            if (naturalspawner$spawnstate != null) {
               Object2IntMap<MobCategory> object2intmap = naturalspawner$spawnstate.m_47148_();
               int l = naturalspawner$spawnstate.m_47126_();
               list.add("SC: " + l + ", " + (String)Stream.of(MobCategory.values()).map((p_94068_) -> {
                  return Character.toUpperCase(p_94068_.m_21607_().charAt(0)) + ": " + object2intmap.getInt(p_94068_);
               }).collect(Collectors.joining(", ")));
            } else {
               list.add("SC: N/A");
            }
         }

         PostChain postchain = this.f_94030_.f_91063_.m_109149_();
         if (postchain != null) {
            list.add("Shader: " + postchain.m_110022_());
         }

         list.add(this.f_94030_.m_91106_().m_120408_() + String.format(" (Mood %d%%)", Math.round(this.f_94030_.f_91074_.m_108762_() * 100.0F)));
         return list;
      }
   }

   @Nullable
   private ServerLevel m_94081_() {
      IntegratedServer integratedserver = this.f_94030_.m_91092_();
      return integratedserver != null ? integratedserver.m_129880_(this.f_94030_.f_91073_.m_46472_()) : null;
   }

   @Nullable
   private String m_94082_() {
      ServerLevel serverlevel = this.m_94081_();
      return serverlevel != null ? serverlevel.m_46464_() : null;
   }

   private Level m_94083_() {
      return DataFixUtils.orElse(Optional.ofNullable(this.f_94030_.m_91092_()).flatMap((p_94065_) -> {
         return Optional.ofNullable(p_94065_.m_129880_(this.f_94030_.f_91073_.m_46472_()));
      }), this.f_94030_.f_91073_);
   }

   @Nullable
   private LevelChunk m_94084_() {
      if (this.f_94036_ == null) {
         ServerLevel serverlevel = this.m_94081_();
         if (serverlevel != null) {
            this.f_94036_ = serverlevel.m_7726_().m_8431_(this.f_94034_.f_45578_, this.f_94034_.f_45579_, ChunkStatus.f_62326_, false).thenApply((p_94055_) -> {
               return p_94055_.map((p_168998_) -> {
                  return (LevelChunk)p_168998_;
               }, (p_168996_) -> {
                  return null;
               });
            });
         }

         if (this.f_94036_ == null) {
            this.f_94036_ = CompletableFuture.completedFuture(this.m_94085_());
         }
      }

      return this.f_94036_.getNow((LevelChunk)null);
   }

   private LevelChunk m_94085_() {
      if (this.f_94035_ == null) {
         this.f_94035_ = this.f_94030_.f_91073_.m_6325_(this.f_94034_.f_45578_, this.f_94034_.f_45579_);
      }

      return this.f_94035_;
   }

   protected List<String> m_94078_() {
      long i = Runtime.getRuntime().maxMemory();
      long j = Runtime.getRuntime().totalMemory();
      long k = Runtime.getRuntime().freeMemory();
      long l = j - k;
      List<String> list = Lists.newArrayList(String.format("Java: %s %dbit", System.getProperty("java.version"), this.f_94030_.m_91103_() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", l * 100L / i, m_94050_(l), m_94050_(i)), String.format("Allocated: % 2d%% %03dMB", j * 100L / i, m_94050_(j)), "", String.format("CPU: %s", GlUtil.m_84819_()), "", String.format("Display: %dx%d (%s)", Minecraft.m_91087_().m_91268_().m_85441_(), Minecraft.m_91087_().m_91268_().m_85442_(), GlUtil.m_84818_()), GlUtil.m_84820_(), GlUtil.m_84821_());
      if (this.f_94030_.m_91299_()) {
         return list;
      } else {
         if (this.f_94032_.m_6662_() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)this.f_94032_).m_82425_();
            BlockState blockstate = this.f_94030_.f_91073_.m_8055_(blockpos);
            list.add("");
            list.add(ChatFormatting.UNDERLINE + "Targeted Block: " + blockpos.m_123341_() + ", " + blockpos.m_123342_() + ", " + blockpos.m_123343_());
            list.add(String.valueOf((Object)Registry.f_122824_.m_7981_(blockstate.m_60734_())));

            for(Entry<Property<?>, Comparable<?>> entry : blockstate.m_61148_().entrySet()) {
               list.add(this.m_94071_(entry));
            }

            for(ResourceLocation resourcelocation : blockstate.m_60734_().getTags()) {
               list.add("#" + resourcelocation);
            }
         }

         if (this.f_94033_.m_6662_() == HitResult.Type.BLOCK) {
            BlockPos blockpos1 = ((BlockHitResult)this.f_94033_).m_82425_();
            FluidState fluidstate = this.f_94030_.f_91073_.m_6425_(blockpos1);
            list.add("");
            list.add(ChatFormatting.UNDERLINE + "Targeted Fluid: " + blockpos1.m_123341_() + ", " + blockpos1.m_123342_() + ", " + blockpos1.m_123343_());
            list.add(String.valueOf((Object)Registry.f_122822_.m_7981_(fluidstate.m_76152_())));

            for(Entry<Property<?>, Comparable<?>> entry1 : fluidstate.m_61148_().entrySet()) {
               list.add(this.m_94071_(entry1));
            }

            for(ResourceLocation resourcelocation1 : fluidstate.m_76152_().getTags()) {
               list.add("#" + resourcelocation1);
            }
         }

         Entity entity = this.f_94030_.f_91076_;
         if (entity != null) {
            list.add("");
            list.add(ChatFormatting.UNDERLINE + "Targeted Entity");
            list.add(String.valueOf((Object)Registry.f_122826_.m_7981_(entity.m_6095_())));
            entity.m_6095_().getTags().forEach(t -> list.add("#" + t));
         }

         return list;
      }
   }

   private String m_94071_(Entry<Property<?>, Comparable<?>> p_94072_) {
      Property<?> property = p_94072_.getKey();
      Comparable<?> comparable = p_94072_.getValue();
      String s = Util.m_137453_(property, comparable);
      if (Boolean.TRUE.equals(comparable)) {
         s = ChatFormatting.GREEN + s;
      } else if (Boolean.FALSE.equals(comparable)) {
         s = ChatFormatting.RED + s;
      }

      return property.m_61708_() + ": " + s;
   }

   private void m_94058_(PoseStack p_94059_, FrameTimer p_94060_, int p_94061_, int p_94062_, boolean p_94063_) {
      RenderSystem.m_69465_();
      int i = p_94060_.m_13754_();
      int j = p_94060_.m_13761_();
      long[] along = p_94060_.m_13764_();
      int l = p_94061_;
      int i1 = Math.max(0, along.length - p_94062_);
      int j1 = along.length - i1;
      int k = p_94060_.m_13762_(i + i1);
      long k1 = 0L;
      int l1 = Integer.MAX_VALUE;
      int i2 = Integer.MIN_VALUE;

      for(int j2 = 0; j2 < j1; ++j2) {
         int k2 = (int)(along[p_94060_.m_13762_(k + j2)] / 1000000L);
         l1 = Math.min(l1, k2);
         i2 = Math.max(i2, k2);
         k1 += (long)k2;
      }

      int k4 = this.f_94030_.m_91268_().m_85446_();
      m_93172_(p_94059_, p_94061_, k4 - 60, p_94061_ + j1, k4, -1873784752);
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      RenderSystem.m_69478_();
      RenderSystem.m_69472_();
      RenderSystem.m_69453_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);

      for(Matrix4f matrix4f = Transformation.m_121093_().m_121104_(); k != j; k = p_94060_.m_13762_(k + 1)) {
         int l2 = p_94060_.m_13757_(along[k], p_94063_ ? 30 : 60, p_94063_ ? 60 : 20);
         int i3 = p_94063_ ? 100 : 60;
         int j3 = this.m_94045_(Mth.m_14045_(l2, 0, i3), 0, i3 / 2, i3);
         int k3 = j3 >> 24 & 255;
         int l3 = j3 >> 16 & 255;
         int i4 = j3 >> 8 & 255;
         int j4 = j3 & 255;
         bufferbuilder.m_85982_(matrix4f, (float)(l + 1), (float)k4, 0.0F).m_6122_(l3, i4, j4, k3).m_5752_();
         bufferbuilder.m_85982_(matrix4f, (float)(l + 1), (float)(k4 - l2 + 1), 0.0F).m_6122_(l3, i4, j4, k3).m_5752_();
         bufferbuilder.m_85982_(matrix4f, (float)l, (float)(k4 - l2 + 1), 0.0F).m_6122_(l3, i4, j4, k3).m_5752_();
         bufferbuilder.m_85982_(matrix4f, (float)l, (float)k4, 0.0F).m_6122_(l3, i4, j4, k3).m_5752_();
         ++l;
      }

      bufferbuilder.m_85721_();
      BufferUploader.m_85761_(bufferbuilder);
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
      if (p_94063_) {
         m_93172_(p_94059_, p_94061_ + 1, k4 - 30 + 1, p_94061_ + 14, k4 - 30 + 10, -1873784752);
         this.f_94031_.m_92883_(p_94059_, "60 FPS", (float)(p_94061_ + 2), (float)(k4 - 30 + 2), 14737632);
         this.m_93154_(p_94059_, p_94061_, p_94061_ + j1 - 1, k4 - 30, -1);
         m_93172_(p_94059_, p_94061_ + 1, k4 - 60 + 1, p_94061_ + 14, k4 - 60 + 10, -1873784752);
         this.f_94031_.m_92883_(p_94059_, "30 FPS", (float)(p_94061_ + 2), (float)(k4 - 60 + 2), 14737632);
         this.m_93154_(p_94059_, p_94061_, p_94061_ + j1 - 1, k4 - 60, -1);
      } else {
         m_93172_(p_94059_, p_94061_ + 1, k4 - 60 + 1, p_94061_ + 14, k4 - 60 + 10, -1873784752);
         this.f_94031_.m_92883_(p_94059_, "20 TPS", (float)(p_94061_ + 2), (float)(k4 - 60 + 2), 14737632);
         this.m_93154_(p_94059_, p_94061_, p_94061_ + j1 - 1, k4 - 60, -1);
      }

      this.m_93154_(p_94059_, p_94061_, p_94061_ + j1 - 1, k4 - 1, -1);
      this.m_93222_(p_94059_, p_94061_, k4 - 60, k4, -1);
      this.m_93222_(p_94059_, p_94061_ + j1 - 1, k4 - 60, k4, -1);
      if (p_94063_ && this.f_94030_.f_91066_.f_92113_ > 0 && this.f_94030_.f_91066_.f_92113_ <= 250) {
         this.m_93154_(p_94059_, p_94061_, p_94061_ + j1 - 1, k4 - 1 - (int)(1800.0D / (double)this.f_94030_.f_91066_.f_92113_), -16711681);
      }

      String s = l1 + " ms min";
      String s1 = k1 / (long)j1 + " ms avg";
      String s2 = i2 + " ms max";
      this.f_94031_.m_92750_(p_94059_, s, (float)(p_94061_ + 2), (float)(k4 - 60 - 9), 14737632);
      this.f_94031_.m_92750_(p_94059_, s1, (float)(p_94061_ + j1 / 2 - this.f_94031_.m_92895_(s1) / 2), (float)(k4 - 60 - 9), 14737632);
      this.f_94031_.m_92750_(p_94059_, s2, (float)(p_94061_ + j1 - this.f_94031_.m_92895_(s2)), (float)(k4 - 60 - 9), 14737632);
      RenderSystem.m_69482_();
   }

   private int m_94045_(int p_94046_, int p_94047_, int p_94048_, int p_94049_) {
      return p_94046_ < p_94048_ ? this.m_94041_(-16711936, -256, (float)p_94046_ / (float)p_94048_) : this.m_94041_(-256, -65536, (float)(p_94046_ - p_94048_) / (float)(p_94049_ - p_94048_));
   }

   private int m_94041_(int p_94042_, int p_94043_, float p_94044_) {
      int i = p_94042_ >> 24 & 255;
      int j = p_94042_ >> 16 & 255;
      int k = p_94042_ >> 8 & 255;
      int l = p_94042_ & 255;
      int i1 = p_94043_ >> 24 & 255;
      int j1 = p_94043_ >> 16 & 255;
      int k1 = p_94043_ >> 8 & 255;
      int l1 = p_94043_ & 255;
      int i2 = Mth.m_14045_((int)Mth.m_14179_(p_94044_, (float)i, (float)i1), 0, 255);
      int j2 = Mth.m_14045_((int)Mth.m_14179_(p_94044_, (float)j, (float)j1), 0, 255);
      int k2 = Mth.m_14045_((int)Mth.m_14179_(p_94044_, (float)k, (float)k1), 0, 255);
      int l2 = Mth.m_14045_((int)Mth.m_14179_(p_94044_, (float)l, (float)l1), 0, 255);
      return i2 << 24 | j2 << 16 | k2 << 8 | l2;
   }

   private static long m_94050_(long p_94051_) {
      return p_94051_ / 1024L / 1024L;
   }
}
