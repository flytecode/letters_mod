package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Random;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockRenderDispatcher implements ResourceManagerReloadListener {
   private final BlockModelShaper f_110899_;
   private final ModelBlockRenderer f_110900_;
   private final BlockEntityWithoutLevelRenderer f_173397_;
   private final LiquidBlockRenderer f_110901_;
   private final Random f_110902_ = new Random();
   private final BlockColors f_110903_;

   public BlockRenderDispatcher(BlockModelShaper p_173399_, BlockEntityWithoutLevelRenderer p_173400_, BlockColors p_173401_) {
      this.f_110899_ = p_173399_;
      this.f_173397_ = p_173400_;
      this.f_110903_ = p_173401_;
      this.f_110900_ = new net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer(this.f_110903_);
      this.f_110901_ = new LiquidBlockRenderer();
   }

   public BlockModelShaper m_110907_() {
      return this.f_110899_;
   }

   @Deprecated //Forge: Model parameter
   public void m_110918_(BlockState p_110919_, BlockPos p_110920_, BlockAndTintGetter p_110921_, PoseStack p_110922_, VertexConsumer p_110923_) {
       renderBreakingTexture(p_110919_,p_110920_, p_110921_, p_110922_, p_110923_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public void renderBreakingTexture(BlockState p_110919_, BlockPos p_110920_, BlockAndTintGetter p_110921_, PoseStack p_110922_, VertexConsumer p_110923_, net.minecraftforge.client.model.data.IModelData modelData) {
      if (p_110919_.m_60799_() == RenderShape.MODEL) {
         BakedModel bakedmodel = this.f_110899_.m_110893_(p_110919_);
         long i = p_110919_.m_60726_(p_110920_);
         this.f_110900_.tesselateBlock(p_110921_, bakedmodel, p_110919_, p_110920_, p_110922_, p_110923_, true, this.f_110902_, i, OverlayTexture.f_118083_, modelData);
      }
   }

   @Deprecated //Forge: Model parameter
   public boolean m_110924_(BlockState p_110925_, BlockPos p_110926_, BlockAndTintGetter p_110927_, PoseStack p_110928_, VertexConsumer p_110929_, boolean p_110930_, Random p_110931_) {
       return renderBatched(p_110925_, p_110926_, p_110927_, p_110928_, p_110929_, p_110930_, p_110931_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public boolean renderBatched(BlockState p_110925_, BlockPos p_110926_, BlockAndTintGetter p_110927_, PoseStack p_110928_, VertexConsumer p_110929_, boolean p_110930_, Random p_110931_, net.minecraftforge.client.model.data.IModelData modelData) {
      try {
         RenderShape rendershape = p_110925_.m_60799_();
         return rendershape != RenderShape.MODEL ? false : this.f_110900_.tesselateBlock(p_110927_, this.m_110910_(p_110925_), p_110925_, p_110926_, p_110928_, p_110929_, p_110930_, p_110931_, p_110925_.m_60726_(p_110926_), OverlayTexture.f_118083_, modelData);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.m_127521_(throwable, "Tesselating block in world");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being tesselated");
         CrashReportCategory.m_178950_(crashreportcategory, p_110927_, p_110926_, p_110925_);
         throw new ReportedException(crashreport);
      }
   }

   public boolean m_110932_(BlockPos p_110933_, BlockAndTintGetter p_110934_, VertexConsumer p_110935_, FluidState p_110936_) {
      try {
         return this.f_110901_.m_110954_(p_110934_, p_110933_, p_110935_, p_110936_);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.m_127521_(throwable, "Tesselating liquid in world");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being tesselated");
         CrashReportCategory.m_178950_(crashreportcategory, p_110934_, p_110933_, (BlockState)null);
         throw new ReportedException(crashreport);
      }
   }

   public ModelBlockRenderer m_110937_() {
      return this.f_110900_;
   }

   public BakedModel m_110910_(BlockState p_110911_) {
      return this.f_110899_.m_110893_(p_110911_);
   }

   @Deprecated //Forge: Model parameter
   public void m_110912_(BlockState p_110913_, PoseStack p_110914_, MultiBufferSource p_110915_, int p_110916_, int p_110917_) {
      renderSingleBlock(p_110913_, p_110914_, p_110915_, p_110916_, p_110917_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public void renderSingleBlock(BlockState p_110913_, PoseStack p_110914_, MultiBufferSource p_110915_, int p_110916_, int p_110917_, net.minecraftforge.client.model.data.IModelData modelData) {
      RenderShape rendershape = p_110913_.m_60799_();
      if (rendershape != RenderShape.INVISIBLE) {
         switch(rendershape) {
         case MODEL:
            BakedModel bakedmodel = this.m_110910_(p_110913_);
            int i = this.f_110903_.m_92577_(p_110913_, (BlockAndTintGetter)null, (BlockPos)null, 0);
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            this.f_110900_.renderModel(p_110914_.m_85850_(), p_110915_.m_6299_(ItemBlockRenderTypes.m_109284_(p_110913_, false)), p_110913_, bakedmodel, f, f1, f2, p_110916_, p_110917_, modelData);
            break;
         case ENTITYBLOCK_ANIMATED:
            ItemStack stack = new ItemStack(p_110913_.m_60734_());
            net.minecraftforge.client.RenderProperties.get(stack).getItemStackRenderer().m_108829_(stack, ItemTransforms.TransformType.NONE, p_110914_, p_110915_, p_110916_, p_110917_);
         }

      }
   }

   public void m_6213_(ResourceManager p_110909_) {
      this.f_110901_.m_110944_();
   }
}
