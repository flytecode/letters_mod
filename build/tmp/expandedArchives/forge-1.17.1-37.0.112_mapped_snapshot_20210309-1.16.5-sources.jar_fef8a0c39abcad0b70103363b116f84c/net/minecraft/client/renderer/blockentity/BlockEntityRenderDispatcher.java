package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockEntityRenderDispatcher implements ResourceManagerReloadListener {
   private Map<BlockEntityType<?>, BlockEntityRenderer<?>> f_112251_ = ImmutableMap.of();
   public final Font f_112253_;
   private final EntityModelSet f_173556_;
   public Level f_112248_;
   public Camera f_112249_;
   public HitResult f_112250_;
   private final Supplier<BlockRenderDispatcher> f_173557_;

   public BlockEntityRenderDispatcher(Font p_173559_, EntityModelSet p_173560_, Supplier<BlockRenderDispatcher> p_173561_) {
      this.f_112253_ = p_173559_;
      this.f_173556_ = p_173560_;
      this.f_173557_ = p_173561_;
   }

   @Nullable
   public <E extends BlockEntity> BlockEntityRenderer<E> m_112265_(E p_112266_) {
      return (BlockEntityRenderer<E>) this.f_112251_.get(p_112266_.m_58903_());
   }

   public void m_173564_(Level p_173565_, Camera p_173566_, HitResult p_173567_) {
      if (this.f_112248_ != p_173565_) {
         this.m_112257_(p_173565_);
      }

      this.f_112249_ = p_173566_;
      this.f_112250_ = p_173567_;
   }

   public <E extends BlockEntity> void m_112267_(E p_112268_, float p_112269_, PoseStack p_112270_, MultiBufferSource p_112271_) {
      BlockEntityRenderer<E> blockentityrenderer = this.m_112265_(p_112268_);
      if (blockentityrenderer != null) {
         if (p_112268_.m_58898_() && p_112268_.m_58903_().m_155262_(p_112268_.m_58900_())) {
            if (blockentityrenderer.m_142756_(p_112268_, this.f_112249_.m_90583_())) {
               m_112278_(p_112268_, () -> {
                  m_112284_(blockentityrenderer, p_112268_, p_112269_, p_112270_, p_112271_);
               });
            }
         }
      }
   }

   private static <T extends BlockEntity> void m_112284_(BlockEntityRenderer<T> p_112285_, T p_112286_, float p_112287_, PoseStack p_112288_, MultiBufferSource p_112289_) {
      Level level = p_112286_.m_58904_();
      int i;
      if (level != null) {
         i = LevelRenderer.m_109541_(level, p_112286_.m_58899_());
      } else {
         i = 15728880;
      }

      p_112285_.m_6922_(p_112286_, p_112287_, p_112288_, p_112289_, i, OverlayTexture.f_118083_);
   }

   public <E extends BlockEntity> boolean m_112272_(E p_112273_, PoseStack p_112274_, MultiBufferSource p_112275_, int p_112276_, int p_112277_) {
      BlockEntityRenderer<E> blockentityrenderer = this.m_112265_(p_112273_);
      if (blockentityrenderer == null) {
         return true;
      } else {
         m_112278_(p_112273_, () -> {
            blockentityrenderer.m_6922_(p_112273_, 0.0F, p_112274_, p_112275_, p_112276_, p_112277_);
         });
         return false;
      }
   }

   private static void m_112278_(BlockEntity p_112279_, Runnable p_112280_) {
      try {
         p_112280_.run();
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.m_127521_(throwable, "Rendering Block Entity");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block Entity Details");
         p_112279_.m_58886_(crashreportcategory);
         throw new ReportedException(crashreport);
      }
   }

   public void m_112257_(@Nullable Level p_112258_) {
      this.f_112248_ = p_112258_;
      if (p_112258_ == null) {
         this.f_112249_ = null;
      }

   }

   public void m_6213_(ResourceManager p_173563_) {
      BlockEntityRendererProvider.Context blockentityrendererprovider$context = new BlockEntityRendererProvider.Context(this, this.f_173557_.get(), this.f_173556_, this.f_112253_);
      this.f_112251_ = BlockEntityRenderers.m_173598_(blockentityrendererprovider$context);
   }
}