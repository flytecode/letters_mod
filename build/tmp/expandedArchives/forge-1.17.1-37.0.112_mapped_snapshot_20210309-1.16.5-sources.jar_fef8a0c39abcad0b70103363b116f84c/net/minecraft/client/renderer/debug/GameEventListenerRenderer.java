package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GameEventListenerRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_173816_;
   private static final int f_173817_ = 32;
   private static final float f_173818_ = 1.0F;
   private final List<GameEventListenerRenderer.TrackedGameEvent> f_173819_ = Lists.newArrayList();
   private final List<GameEventListenerRenderer.TrackedListener> f_173820_ = Lists.newArrayList();

   public GameEventListenerRenderer(Minecraft p_173822_) {
      this.f_173816_ = p_173822_;
   }

   public void m_7790_(PoseStack p_173846_, MultiBufferSource p_173847_, double p_173848_, double p_173849_, double p_173850_) {
      Level level = this.f_173816_.f_91073_;
      if (level == null) {
         this.f_173819_.clear();
         this.f_173820_.clear();
      } else {
         BlockPos blockpos = new BlockPos(p_173848_, 0.0D, p_173850_);
         this.f_173819_.removeIf(GameEventListenerRenderer.TrackedGameEvent::m_173868_);
         this.f_173820_.removeIf((p_173826_) -> {
            return p_173826_.m_173882_(level, blockpos);
         });
         RenderSystem.m_69472_();
         RenderSystem.m_69482_();
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         VertexConsumer vertexconsumer = p_173847_.m_6299_(RenderType.m_110504_());

         for(GameEventListenerRenderer.TrackedListener gameeventlistenerrenderer$trackedlistener : this.f_173820_) {
            gameeventlistenerrenderer$trackedlistener.m_173875_(level).ifPresent((p_173858_) -> {
               int i = p_173858_.m_123341_() - gameeventlistenerrenderer$trackedlistener.m_142078_();
               int j = p_173858_.m_123342_() - gameeventlistenerrenderer$trackedlistener.m_142078_();
               int k = p_173858_.m_123343_() - gameeventlistenerrenderer$trackedlistener.m_142078_();
               int l = p_173858_.m_123341_() + gameeventlistenerrenderer$trackedlistener.m_142078_();
               int i1 = p_173858_.m_123342_() + gameeventlistenerrenderer$trackedlistener.m_142078_();
               int j1 = p_173858_.m_123343_() + gameeventlistenerrenderer$trackedlistener.m_142078_();
               Vector3f vector3f = new Vector3f(1.0F, 1.0F, 0.0F);
               LevelRenderer.m_109654_(p_173846_, vertexconsumer, Shapes.m_83064_(new AABB((double)i, (double)j, (double)k, (double)l, (double)i1, (double)j1)), -p_173848_, -p_173849_, -p_173850_, vector3f.m_122239_(), vector3f.m_122260_(), vector3f.m_122269_(), 0.35F);
            });
         }

         RenderSystem.m_157427_(GameRenderer::m_172811_);
         Tesselator tesselator = Tesselator.m_85913_();
         BufferBuilder bufferbuilder = tesselator.m_85915_();
         bufferbuilder.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);

         for(GameEventListenerRenderer.TrackedListener gameeventlistenerrenderer$trackedlistener1 : this.f_173820_) {
            gameeventlistenerrenderer$trackedlistener1.m_173875_(level).ifPresent((p_173844_) -> {
               Vector3f vector3f = new Vector3f(1.0F, 1.0F, 0.0F);
               LevelRenderer.m_109556_(bufferbuilder, (double)((float)p_173844_.m_123341_() - 0.25F) - p_173848_, (double)p_173844_.m_123342_() - p_173849_, (double)((float)p_173844_.m_123343_() - 0.25F) - p_173850_, (double)((float)p_173844_.m_123341_() + 0.25F) - p_173848_, (double)p_173844_.m_123342_() - p_173849_ + 1.0D, (double)((float)p_173844_.m_123343_() + 0.25F) - p_173850_, vector3f.m_122239_(), vector3f.m_122260_(), vector3f.m_122269_(), 0.35F);
            });
         }

         tesselator.m_85914_();
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         RenderSystem.m_69832_(2.0F);
         RenderSystem.m_69458_(false);

         for(GameEventListenerRenderer.TrackedListener gameeventlistenerrenderer$trackedlistener2 : this.f_173820_) {
            gameeventlistenerrenderer$trackedlistener2.m_173875_(level).ifPresent((p_173860_) -> {
               DebugRenderer.m_113483_("Listener Origin", (double)p_173860_.m_123341_(), (double)((float)p_173860_.m_123342_() + 1.8F), (double)p_173860_.m_123343_(), -1, 0.025F);
               DebugRenderer.m_113483_((new BlockPos(p_173860_)).toString(), (double)p_173860_.m_123341_(), (double)((float)p_173860_.m_123342_() + 1.5F), (double)p_173860_.m_123343_(), -6959665, 0.025F);
            });
         }

         for(GameEventListenerRenderer.TrackedGameEvent gameeventlistenerrenderer$trackedgameevent : this.f_173819_) {
            Vec3 vec3 = gameeventlistenerrenderer$trackedgameevent.f_173863_;
            double d0 = (double)0.2F;
            double d1 = vec3.f_82479_ - (double)0.2F;
            double d2 = vec3.f_82480_ - (double)0.2F;
            double d3 = vec3.f_82481_ - (double)0.2F;
            double d4 = vec3.f_82479_ + (double)0.2F;
            double d5 = vec3.f_82480_ + (double)0.2F + 0.5D;
            double d6 = vec3.f_82481_ + (double)0.2F;
            m_173833_(new AABB(d1, d2, d3, d4, d5, d6), 1.0F, 1.0F, 1.0F, 0.2F);
            DebugRenderer.m_113483_(gameeventlistenerrenderer$trackedgameevent.f_173862_.m_157821_(), vec3.f_82479_, vec3.f_82480_ + (double)0.85F, vec3.f_82481_, -7564911, 0.0075F);
         }

         RenderSystem.m_69458_(true);
         RenderSystem.m_69493_();
         RenderSystem.m_69461_();
      }
   }

   private static void m_173833_(AABB p_173834_, float p_173835_, float p_173836_, float p_173837_, float p_173838_) {
      Camera camera = Minecraft.m_91087_().f_91063_.m_109153_();
      if (camera.m_90593_()) {
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         Vec3 vec3 = camera.m_90583_().m_82548_();
         DebugRenderer.m_113451_(p_173834_.m_82383_(vec3), p_173835_, p_173836_, p_173837_, p_173838_);
      }
   }

   public void m_173827_(GameEvent p_173828_, BlockPos p_173829_) {
      this.f_173819_.add(new GameEventListenerRenderer.TrackedGameEvent(Util.m_137550_(), p_173828_, Vec3.m_82539_(p_173829_)));
   }

   public void m_173830_(PositionSource p_173831_, int p_173832_) {
      this.f_173820_.add(new GameEventListenerRenderer.TrackedListener(p_173831_, p_173832_));
   }

   @OnlyIn(Dist.CLIENT)
   static class TrackedGameEvent {
      public final long f_173861_;
      public final GameEvent f_173862_;
      public final Vec3 f_173863_;

      public TrackedGameEvent(long p_173865_, GameEvent p_173866_, Vec3 p_173867_) {
         this.f_173861_ = p_173865_;
         this.f_173862_ = p_173866_;
         this.f_173863_ = p_173867_;
      }

      public boolean m_173868_() {
         return Util.m_137550_() - this.f_173861_ > 3000L;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class TrackedListener implements GameEventListener {
      public final PositionSource f_173869_;
      public final int f_173870_;

      public TrackedListener(PositionSource p_173872_, int p_173873_) {
         this.f_173869_ = p_173872_;
         this.f_173870_ = p_173873_;
      }

      public boolean m_173882_(Level p_173883_, BlockPos p_173884_) {
         Optional<BlockPos> optional = this.f_173869_.m_142502_(p_173883_);
         return !optional.isPresent() || optional.get().m_123331_(p_173884_) <= 1024.0D;
      }

      public Optional<BlockPos> m_173875_(Level p_173876_) {
         return this.f_173869_.m_142502_(p_173876_);
      }

      public PositionSource m_142460_() {
         return this.f_173869_;
      }

      public int m_142078_() {
         return this.f_173870_;
      }

      public boolean m_142721_(Level p_173878_, GameEvent p_173879_, @Nullable Entity p_173880_, BlockPos p_173881_) {
         return false;
      }
   }
}