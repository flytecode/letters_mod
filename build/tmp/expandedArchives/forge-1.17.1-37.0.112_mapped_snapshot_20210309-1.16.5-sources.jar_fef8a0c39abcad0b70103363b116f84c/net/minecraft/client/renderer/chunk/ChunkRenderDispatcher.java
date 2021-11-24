package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ChunkRenderDispatcher {
   private static final Logger f_112672_ = LogManager.getLogger();
   private static final int f_173707_ = 4;
   private static final VertexFormat f_173708_ = DefaultVertexFormat.f_85811_;
   private final PriorityQueue<ChunkRenderDispatcher.RenderChunk.ChunkCompileTask> f_112673_ = Queues.newPriorityQueue();
   private final Queue<ChunkBufferBuilderPack> f_112674_;
   private final Queue<Runnable> f_112675_ = Queues.newConcurrentLinkedQueue();
   private volatile int f_112676_;
   private volatile int f_112677_;
   final ChunkBufferBuilderPack f_112678_;
   private final ProcessorMailbox<Runnable> f_112679_;
   private final Executor f_112680_;
   Level f_112681_;
   final LevelRenderer f_112682_;
   private Vec3 f_112683_ = Vec3.f_82478_;

   public ChunkRenderDispatcher(Level p_112686_, LevelRenderer p_112687_, Executor p_112688_, boolean p_112689_, ChunkBufferBuilderPack p_112690_) {
      this(p_112686_, p_112687_, p_112688_, p_112689_, p_112690_, -1);
   }
   public ChunkRenderDispatcher(Level p_112686_, LevelRenderer p_112687_, Executor p_112688_, boolean p_112689_, ChunkBufferBuilderPack p_112690_, int countRenderBuilders) {
      this.f_112681_ = p_112686_;
      this.f_112682_ = p_112687_;
      int i = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3D) / (RenderType.m_110506_().stream().mapToInt(RenderType::m_110507_).sum() * 4) - 1);
      int j = Runtime.getRuntime().availableProcessors();
      int k = p_112689_ ? j : Math.min(j, 4);
      int l = countRenderBuilders < 0 ? Math.max(1, Math.min(k, i)) : countRenderBuilders;
      this.f_112678_ = p_112690_;
      List<ChunkBufferBuilderPack> list = Lists.newArrayListWithExpectedSize(l);

      try {
         for(int i1 = 0; i1 < l; ++i1) {
            list.add(new ChunkBufferBuilderPack());
         }
      } catch (OutOfMemoryError outofmemoryerror) {
         f_112672_.warn("Allocated only {}/{} buffers", list.size(), l);
         int j1 = Math.min(list.size() * 2 / 3, list.size() - 1);

         for(int k1 = 0; k1 < j1; ++k1) {
            list.remove(list.size() - 1);
         }

         System.gc();
      }

      this.f_112674_ = Queues.newArrayDeque(list);
      this.f_112677_ = this.f_112674_.size();
      this.f_112680_ = p_112688_;
      this.f_112679_ = ProcessorMailbox.m_18751_(p_112688_, "Chunk Renderer");
      this.f_112679_.m_6937_(this::m_112734_);
   }

   public void m_112691_(Level p_112692_) {
      this.f_112681_ = p_112692_;
   }

   private void m_112734_() {
      if (!this.f_112674_.isEmpty()) {
         ChunkRenderDispatcher.RenderChunk.ChunkCompileTask chunkrenderdispatcher$renderchunk$chunkcompiletask = this.f_112673_.poll();
         if (chunkrenderdispatcher$renderchunk$chunkcompiletask != null) {
            ChunkBufferBuilderPack chunkbufferbuilderpack = this.f_112674_.poll();
            this.f_112676_ = this.f_112673_.size();
            this.f_112677_ = this.f_112674_.size();
            CompletableFuture.runAsync(() -> {
            }, this.f_112680_).thenCompose((p_112714_) -> {
               return chunkrenderdispatcher$renderchunk$chunkcompiletask.m_5869_(chunkbufferbuilderpack);
            }).whenComplete((p_112704_, p_112705_) -> {
               if (p_112705_ != null) {
                  CrashReport crashreport = CrashReport.m_127521_(p_112705_, "Batching chunks");
                  Minecraft.m_91087_().m_91253_(Minecraft.m_91087_().m_91354_(crashreport));
               } else {
                  this.f_112679_.m_6937_(() -> {
                     if (p_112704_ == ChunkRenderDispatcher.ChunkTaskResult.SUCCESSFUL) {
                        chunkbufferbuilderpack.m_108838_();
                     } else {
                        chunkbufferbuilderpack.m_108841_();
                     }

                     this.f_112674_.add(chunkbufferbuilderpack);
                     this.f_112677_ = this.f_112674_.size();
                     this.m_112734_();
                  });
               }
            });
         }
      }
   }

   public String m_112719_() {
      return String.format("pC: %03d, pU: %02d, aB: %02d", this.f_112676_, this.f_112675_.size(), this.f_112677_);
   }

   public int m_173712_() {
      return this.f_112676_;
   }

   public int m_173713_() {
      return this.f_112675_.size();
   }

   public int m_173714_() {
      return this.f_112677_;
   }

   public void m_112693_(Vec3 p_112694_) {
      this.f_112683_ = p_112694_;
   }

   public Vec3 m_112727_() {
      return this.f_112683_;
   }

   public boolean m_112730_() {
      boolean flag;
      Runnable runnable;
      for(flag = false; (runnable = this.f_112675_.poll()) != null; flag = true) {
         runnable.run();
      }

      return flag;
   }

   public void m_112715_(ChunkRenderDispatcher.RenderChunk p_112716_) {
      p_112716_.m_112845_();
   }

   public void m_112731_() {
      this.m_112735_();
   }

   public void m_112709_(ChunkRenderDispatcher.RenderChunk.ChunkCompileTask p_112710_) {
      this.f_112679_.m_6937_(() -> {
         this.f_112673_.offer(p_112710_);
         this.f_112676_ = this.f_112673_.size();
         this.m_112734_();
      });
   }

   public CompletableFuture<Void> m_112695_(BufferBuilder p_112696_, VertexBuffer p_112697_) {
      return CompletableFuture.runAsync(() -> {
      }, this.f_112675_::add).thenCompose((p_112701_) -> {
         return this.m_112720_(p_112696_, p_112697_);
      });
   }

   private CompletableFuture<Void> m_112720_(BufferBuilder p_112721_, VertexBuffer p_112722_) {
      return p_112722_.m_85932_(p_112721_);
   }

   private void m_112735_() {
      while(!this.f_112673_.isEmpty()) {
         ChunkRenderDispatcher.RenderChunk.ChunkCompileTask chunkrenderdispatcher$renderchunk$chunkcompiletask = this.f_112673_.poll();
         if (chunkrenderdispatcher$renderchunk$chunkcompiletask != null) {
            chunkrenderdispatcher$renderchunk$chunkcompiletask.m_6204_();
         }
      }

      this.f_112676_ = 0;
   }

   public boolean m_112732_() {
      return this.f_112676_ == 0 && this.f_112675_.isEmpty();
   }

   public void m_112733_() {
      this.m_112735_();
      this.f_112679_.close();
      this.f_112674_.clear();
   }

   @OnlyIn(Dist.CLIENT)
   static enum ChunkTaskResult {
      SUCCESSFUL,
      CANCELLED;
   }

   @OnlyIn(Dist.CLIENT)
   public static class CompiledChunk {
      public static final ChunkRenderDispatcher.CompiledChunk f_112748_ = new ChunkRenderDispatcher.CompiledChunk() {
         public boolean m_7259_(Direction p_112782_, Direction p_112783_) {
            return false;
         }
      };
      final Set<RenderType> f_112749_ = new ObjectArraySet<>();
      final Set<RenderType> f_112750_ = new ObjectArraySet<>();
      boolean f_112751_ = true;
      final List<BlockEntity> f_112752_ = Lists.newArrayList();
      VisibilitySet f_112753_ = new VisibilitySet();
      @Nullable
      BufferBuilder.SortState f_112754_;

      public boolean m_112757_() {
         return this.f_112751_;
      }

      public boolean m_112758_(RenderType p_112759_) {
         return !this.f_112749_.contains(p_112759_);
      }

      public List<BlockEntity> m_112773_() {
         return this.f_112752_;
      }

      public boolean m_7259_(Direction p_112771_, Direction p_112772_) {
         return this.f_112753_.m_112983_(p_112771_, p_112772_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public class RenderChunk implements net.minecraftforge.client.extensions.IForgeRenderChunk {
      public static final int f_173716_ = 16;
      public final int f_173717_;
      public final AtomicReference<ChunkRenderDispatcher.CompiledChunk> f_112784_ = new AtomicReference<>(ChunkRenderDispatcher.CompiledChunk.f_112748_);
      @Nullable
      private ChunkRenderDispatcher.RenderChunk.RebuildTask f_112787_;
      @Nullable
      private ChunkRenderDispatcher.RenderChunk.ResortTransparencyTask f_112788_;
      private final Set<BlockEntity> f_112789_ = Sets.newHashSet();
      private final Map<RenderType, VertexBuffer> f_112790_ = RenderType.m_110506_().stream().collect(Collectors.toMap((p_112837_) -> {
         return p_112837_;
      }, (p_112834_) -> {
         return new VertexBuffer();
      }));
      public AABB f_112785_;
      private int f_112791_ = -1;
      private boolean f_112792_ = true;
      final BlockPos.MutableBlockPos f_112793_ = new BlockPos.MutableBlockPos(-1, -1, -1);
      private final BlockPos.MutableBlockPos[] f_112794_ = Util.m_137469_(new BlockPos.MutableBlockPos[6], (p_112831_) -> {
         for(int i = 0; i < p_112831_.length; ++i) {
            p_112831_[i] = new BlockPos.MutableBlockPos();
         }

      });
      private boolean f_112795_;

      public RenderChunk(int p_173720_) {
         this.f_173717_ = p_173720_;
      }

      private boolean m_112822_(BlockPos p_112823_) {
         return ChunkRenderDispatcher.this.f_112681_.m_6522_(SectionPos.m_123171_(p_112823_.m_123341_()), SectionPos.m_123171_(p_112823_.m_123343_()), ChunkStatus.f_62326_, false) != null;
      }

      public boolean m_112798_() {
         int i = 24;
         if (!(this.m_112832_() > 576.0D)) {
            return true;
         } else {
            return this.m_112822_(this.f_112794_[Direction.WEST.ordinal()]) && this.m_112822_(this.f_112794_[Direction.NORTH.ordinal()]) && this.m_112822_(this.f_112794_[Direction.EAST.ordinal()]) && this.m_112822_(this.f_112794_[Direction.SOUTH.ordinal()]);
         }
      }

      public boolean m_112799_(int p_112800_) {
         if (this.f_112791_ == p_112800_) {
            return false;
         } else {
            this.f_112791_ = p_112800_;
            return true;
         }
      }

      public VertexBuffer m_112807_(RenderType p_112808_) {
         return this.f_112790_.get(p_112808_);
      }

      public void m_112801_(int p_112802_, int p_112803_, int p_112804_) {
         if (p_112802_ != this.f_112793_.m_123341_() || p_112803_ != this.f_112793_.m_123342_() || p_112804_ != this.f_112793_.m_123343_()) {
            this.m_112846_();
            this.f_112793_.m_122178_(p_112802_, p_112803_, p_112804_);
            this.f_112785_ = new AABB((double)p_112802_, (double)p_112803_, (double)p_112804_, (double)(p_112802_ + 16), (double)(p_112803_ + 16), (double)(p_112804_ + 16));

            for(Direction direction : Direction.values()) {
               this.f_112794_[direction.ordinal()].m_122190_(this.f_112793_).m_122175_(direction, 16);
            }

         }
      }

      protected double m_112832_() {
         Camera camera = Minecraft.m_91087_().f_91063_.m_109153_();
         double d0 = this.f_112785_.f_82288_ + 8.0D - camera.m_90583_().f_82479_;
         double d1 = this.f_112785_.f_82289_ + 8.0D - camera.m_90583_().f_82480_;
         double d2 = this.f_112785_.f_82290_ + 8.0D - camera.m_90583_().f_82481_;
         return d0 * d0 + d1 * d1 + d2 * d2;
      }

      void m_112805_(BufferBuilder p_112806_) {
         p_112806_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85811_);
      }

      public ChunkRenderDispatcher.CompiledChunk m_112835_() {
         return this.f_112784_.get();
      }

      private void m_112846_() {
         this.m_112843_();
         this.f_112784_.set(ChunkRenderDispatcher.CompiledChunk.f_112748_);
         this.f_112792_ = true;
      }

      public void m_112838_() {
         this.m_112846_();
         this.f_112790_.values().forEach(VertexBuffer::close);
      }

      public BlockPos m_112839_() {
         return this.f_112793_;
      }

      public void m_112828_(boolean p_112829_) {
         boolean flag = this.f_112792_;
         this.f_112792_ = true;
         this.f_112795_ = p_112829_ | (flag && this.f_112795_);
      }

      public void m_112840_() {
         this.f_112792_ = false;
         this.f_112795_ = false;
      }

      public boolean m_112841_() {
         return this.f_112792_;
      }

      public boolean m_112842_() {
         return this.f_112792_ && this.f_112795_;
      }

      public BlockPos m_112824_(Direction p_112825_) {
         return this.f_112794_[p_112825_.ordinal()];
      }

      public boolean m_112809_(RenderType p_112810_, ChunkRenderDispatcher p_112811_) {
         ChunkRenderDispatcher.CompiledChunk chunkrenderdispatcher$compiledchunk = this.m_112835_();
         if (this.f_112788_ != null) {
            this.f_112788_.m_6204_();
         }

         if (!chunkrenderdispatcher$compiledchunk.f_112750_.contains(p_112810_)) {
            return false;
         } else {
            this.f_112788_ = new ChunkRenderDispatcher.RenderChunk.ResortTransparencyTask(new net.minecraft.world.level.ChunkPos(m_112839_()), this.m_112832_(), chunkrenderdispatcher$compiledchunk);
            p_112811_.m_112709_(this.f_112788_);
            return true;
         }
      }

      protected void m_112843_() {
         if (this.f_112787_ != null) {
            this.f_112787_.m_6204_();
            this.f_112787_ = null;
         }

         if (this.f_112788_ != null) {
            this.f_112788_.m_6204_();
            this.f_112788_ = null;
         }

      }

      public ChunkRenderDispatcher.RenderChunk.ChunkCompileTask m_112844_() {
         this.m_112843_();
         BlockPos blockpos = this.f_112793_.m_7949_();
         int i = 1;
         RenderChunkRegion renderchunkregion = createRegionRenderCache(ChunkRenderDispatcher.this.f_112681_, blockpos.m_142082_(-1, -1, -1), blockpos.m_142082_(16, 16, 16), 1);
         this.f_112787_ = new ChunkRenderDispatcher.RenderChunk.RebuildTask(new net.minecraft.world.level.ChunkPos(m_112839_()), this.m_112832_(), renderchunkregion);
         return this.f_112787_;
      }

      public void m_112820_(ChunkRenderDispatcher p_112821_) {
         ChunkRenderDispatcher.RenderChunk.ChunkCompileTask chunkrenderdispatcher$renderchunk$chunkcompiletask = this.m_112844_();
         p_112821_.m_112709_(chunkrenderdispatcher$renderchunk$chunkcompiletask);
      }

      void m_112826_(Set<BlockEntity> p_112827_) {
         Set<BlockEntity> set = Sets.newHashSet(p_112827_);
         Set<BlockEntity> set1 = Sets.newHashSet(this.f_112789_);
         set.removeAll(this.f_112789_);
         set1.removeAll(p_112827_);
         this.f_112789_.clear();
         this.f_112789_.addAll(p_112827_);
         ChunkRenderDispatcher.this.f_112682_.m_109762_(set1, set);
      }

      public void m_112845_() {
         ChunkRenderDispatcher.RenderChunk.ChunkCompileTask chunkrenderdispatcher$renderchunk$chunkcompiletask = this.m_112844_();
         chunkrenderdispatcher$renderchunk$chunkcompiletask.m_5869_(ChunkRenderDispatcher.this.f_112678_);
      }

      @OnlyIn(Dist.CLIENT)
      abstract class ChunkCompileTask implements Comparable<ChunkRenderDispatcher.RenderChunk.ChunkCompileTask> {
         protected final double f_112847_;
         protected final AtomicBoolean f_112848_ = new AtomicBoolean(false);
         protected java.util.Map<net.minecraft.core.BlockPos, net.minecraftforge.client.model.data.IModelData> modelData;

         public ChunkCompileTask(double p_112852_) {
            this(null, p_112852_);
         }

         public ChunkCompileTask(@Nullable net.minecraft.world.level.ChunkPos pos, double p_112852_) {
            this.f_112847_ = p_112852_;
            if (pos == null) {
               this.modelData = java.util.Collections.emptyMap();
            } else {
               this.modelData = net.minecraftforge.client.model.ModelDataManager.getModelData(net.minecraft.client.Minecraft.m_91087_().f_91073_, pos);
            }
         }

         public abstract CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> m_5869_(ChunkBufferBuilderPack p_112853_);

         public abstract void m_6204_();

         public int compareTo(ChunkRenderDispatcher.RenderChunk.ChunkCompileTask p_112855_) {
            return Doubles.compare(this.f_112847_, p_112855_.f_112847_);
         }

         public net.minecraftforge.client.model.data.IModelData getModelData(net.minecraft.core.BlockPos pos) {
            return modelData.getOrDefault(pos, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
         }
      }

      @OnlyIn(Dist.CLIENT)
      class RebuildTask extends ChunkRenderDispatcher.RenderChunk.ChunkCompileTask {
         @Nullable
         protected RenderChunkRegion f_112858_;

         @Deprecated
         public RebuildTask(@Nullable double p_112862_, RenderChunkRegion p_112863_) {
            this(null, p_112862_, p_112863_);
         }

         public RebuildTask(@Nullable net.minecraft.world.level.ChunkPos pos, double p_112862_, @Nullable RenderChunkRegion p_112863_) {
            super(pos, p_112862_);
            this.f_112858_ = p_112863_;
         }

         public CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> m_5869_(ChunkBufferBuilderPack p_112872_) {
            if (this.f_112848_.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (!RenderChunk.this.m_112798_()) {
               this.f_112858_ = null;
               RenderChunk.this.m_112828_(false);
               this.f_112848_.set(true);
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (this.f_112848_.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else {
               Vec3 vec3 = ChunkRenderDispatcher.this.m_112727_();
               float f = (float)vec3.f_82479_;
               float f1 = (float)vec3.f_82480_;
               float f2 = (float)vec3.f_82481_;
               ChunkRenderDispatcher.CompiledChunk chunkrenderdispatcher$compiledchunk = new ChunkRenderDispatcher.CompiledChunk();
               Set<BlockEntity> set = this.m_112865_(f, f1, f2, chunkrenderdispatcher$compiledchunk, p_112872_);
               RenderChunk.this.m_112826_(set);
               if (this.f_112848_.get()) {
                  return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
               } else {
                  List<CompletableFuture<Void>> list = Lists.newArrayList();
                  chunkrenderdispatcher$compiledchunk.f_112750_.forEach((p_112884_) -> {
                     list.add(ChunkRenderDispatcher.this.m_112695_(p_112872_.m_108839_(p_112884_), RenderChunk.this.m_112807_(p_112884_)));
                  });
                  return Util.m_143840_(list).handle((p_112875_, p_112876_) -> {
                     if (p_112876_ != null && !(p_112876_ instanceof CancellationException) && !(p_112876_ instanceof InterruptedException)) {
                        Minecraft.m_91087_().m_91253_(CrashReport.m_127521_(p_112876_, "Rendering chunk"));
                     }

                     if (this.f_112848_.get()) {
                        return ChunkRenderDispatcher.ChunkTaskResult.CANCELLED;
                     } else {
                        RenderChunk.this.f_112784_.set(chunkrenderdispatcher$compiledchunk);
                        return ChunkRenderDispatcher.ChunkTaskResult.SUCCESSFUL;
                     }
                  });
               }
            }
         }

         private Set<BlockEntity> m_112865_(float p_112866_, float p_112867_, float p_112868_, ChunkRenderDispatcher.CompiledChunk p_112869_, ChunkBufferBuilderPack p_112870_) {
            int i = 1;
            BlockPos blockpos = RenderChunk.this.f_112793_.m_7949_();
            BlockPos blockpos1 = blockpos.m_142082_(15, 15, 15);
            VisGraph visgraph = new VisGraph();
            Set<BlockEntity> set = Sets.newHashSet();
            RenderChunkRegion renderchunkregion = this.f_112858_;
            this.f_112858_ = null;
            PoseStack posestack = new PoseStack();
            if (renderchunkregion != null) {
               ModelBlockRenderer.m_111000_();
               Random random = new Random();
               BlockRenderDispatcher blockrenderdispatcher = Minecraft.m_91087_().m_91289_();

               for(BlockPos blockpos2 : BlockPos.m_121940_(blockpos, blockpos1)) {
                  BlockState blockstate = renderchunkregion.m_8055_(blockpos2);
                  if (blockstate.m_60804_(renderchunkregion, blockpos2)) {
                     visgraph.m_112971_(blockpos2);
                  }

                  if (blockstate.m_155947_()) {
                     BlockEntity blockentity = renderchunkregion.m_112927_(blockpos2, LevelChunk.EntityCreationType.CHECK);
                     if (blockentity != null) {
                        this.m_112877_(p_112869_, set, blockentity);
                     }
                  }

                  FluidState fluidstate = renderchunkregion.m_6425_(blockpos2);
                  net.minecraftforge.client.model.data.IModelData modelData = getModelData(blockpos2);
                  for (RenderType rendertype : RenderType.m_110506_()) {
                     net.minecraftforge.client.ForgeHooksClient.setRenderLayer(rendertype);
                  if (!fluidstate.m_76178_() && ItemBlockRenderTypes.canRenderInLayer(fluidstate, rendertype)) {
                     BufferBuilder bufferbuilder = p_112870_.m_108839_(rendertype);
                     if (p_112869_.f_112750_.add(rendertype)) {
                        RenderChunk.this.m_112805_(bufferbuilder);
                     }

                     if (blockrenderdispatcher.m_110932_(blockpos2, renderchunkregion, bufferbuilder, fluidstate)) {
                        p_112869_.f_112751_ = false;
                        p_112869_.f_112749_.add(rendertype);
                     }
                  }

                  if (blockstate.m_60799_() != RenderShape.INVISIBLE && ItemBlockRenderTypes.canRenderInLayer(blockstate, rendertype)) {
                     RenderType rendertype1 = rendertype;
                     BufferBuilder bufferbuilder2 = p_112870_.m_108839_(rendertype1);
                     if (p_112869_.f_112750_.add(rendertype1)) {
                        RenderChunk.this.m_112805_(bufferbuilder2);
                     }

                     posestack.m_85836_();
                     posestack.m_85837_((double)(blockpos2.m_123341_() & 15), (double)(blockpos2.m_123342_() & 15), (double)(blockpos2.m_123343_() & 15));
                     if (blockrenderdispatcher.renderBatched(blockstate, blockpos2, renderchunkregion, posestack, bufferbuilder2, true, random, modelData)) {
                        p_112869_.f_112751_ = false;
                        p_112869_.f_112749_.add(rendertype1);
                     }

                     posestack.m_85849_();
                  }
                  }
               }
               net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);

               if (p_112869_.f_112749_.contains(RenderType.m_110466_())) {
                  BufferBuilder bufferbuilder1 = p_112870_.m_108839_(RenderType.m_110466_());
                  bufferbuilder1.m_166771_(p_112866_ - (float)blockpos.m_123341_(), p_112867_ - (float)blockpos.m_123342_(), p_112868_ - (float)blockpos.m_123343_());
                  p_112869_.f_112754_ = bufferbuilder1.m_166770_();
               }

               p_112869_.f_112750_.stream().map(p_112870_::m_108839_).forEach(BufferBuilder::m_85721_);
               ModelBlockRenderer.m_111077_();
            }

            p_112869_.f_112753_ = visgraph.m_112958_();
            return set;
         }

         private <E extends BlockEntity> void m_112877_(ChunkRenderDispatcher.CompiledChunk p_112878_, Set<BlockEntity> p_112879_, E p_112880_) {
            BlockEntityRenderer<E> blockentityrenderer = Minecraft.m_91087_().m_167982_().m_112265_(p_112880_);
            if (blockentityrenderer != null) {
               if (blockentityrenderer.m_5932_(p_112880_)) {
                  p_112879_.add(p_112880_);
               }
               else p_112878_.f_112752_.add(p_112880_); //FORGE: Fix MC-112730
            }

         }

         public void m_6204_() {
            this.f_112858_ = null;
            if (this.f_112848_.compareAndSet(false, true)) {
               RenderChunk.this.m_112828_(false);
            }

         }
      }

      @OnlyIn(Dist.CLIENT)
      class ResortTransparencyTask extends ChunkRenderDispatcher.RenderChunk.ChunkCompileTask {
         private final ChunkRenderDispatcher.CompiledChunk f_112886_;

         @Deprecated
         public ResortTransparencyTask(double p_112889_, ChunkRenderDispatcher.CompiledChunk p_112890_) {
            this(null, p_112889_, p_112890_);
         }

         public ResortTransparencyTask(@Nullable net.minecraft.world.level.ChunkPos pos, double p_112889_, ChunkRenderDispatcher.CompiledChunk p_112890_) {
            super(pos, p_112889_);
            this.f_112886_ = p_112890_;
         }

         public CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> m_5869_(ChunkBufferBuilderPack p_112893_) {
            if (this.f_112848_.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (!RenderChunk.this.m_112798_()) {
               this.f_112848_.set(true);
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (this.f_112848_.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else {
               Vec3 vec3 = ChunkRenderDispatcher.this.m_112727_();
               float f = (float)vec3.f_82479_;
               float f1 = (float)vec3.f_82480_;
               float f2 = (float)vec3.f_82481_;
               BufferBuilder.SortState bufferbuilder$sortstate = this.f_112886_.f_112754_;
               if (bufferbuilder$sortstate != null && this.f_112886_.f_112749_.contains(RenderType.m_110466_())) {
                  BufferBuilder bufferbuilder = p_112893_.m_108839_(RenderType.m_110466_());
                  RenderChunk.this.m_112805_(bufferbuilder);
                  bufferbuilder.m_166775_(bufferbuilder$sortstate);
                  bufferbuilder.m_166771_(f - (float)RenderChunk.this.f_112793_.m_123341_(), f1 - (float)RenderChunk.this.f_112793_.m_123342_(), f2 - (float)RenderChunk.this.f_112793_.m_123343_());
                  this.f_112886_.f_112754_ = bufferbuilder.m_166770_();
                  bufferbuilder.m_85721_();
                  if (this.f_112848_.get()) {
                     return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
                  } else {
                     CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> completablefuture = ChunkRenderDispatcher.this.m_112695_(p_112893_.m_108839_(RenderType.m_110466_()), RenderChunk.this.m_112807_(RenderType.m_110466_())).thenApply((p_112898_) -> {
                        return ChunkRenderDispatcher.ChunkTaskResult.CANCELLED;
                     });
                     return completablefuture.handle((p_112895_, p_112896_) -> {
                        if (p_112896_ != null && !(p_112896_ instanceof CancellationException) && !(p_112896_ instanceof InterruptedException)) {
                           Minecraft.m_91087_().m_91253_(CrashReport.m_127521_(p_112896_, "Rendering chunk"));
                        }

                        return this.f_112848_.get() ? ChunkRenderDispatcher.ChunkTaskResult.CANCELLED : ChunkRenderDispatcher.ChunkTaskResult.SUCCESSFUL;
                     });
                  }
               } else {
                  return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
               }
            }
         }

         public void m_6204_() {
            this.f_112848_.set(true);
         }
      }
   }
}
