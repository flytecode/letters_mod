package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowGolemHeadLayer extends RenderLayer<SnowGolem, SnowGolemModel<SnowGolem>> {
   public SnowGolemHeadLayer(RenderLayerParent<SnowGolem, SnowGolemModel<SnowGolem>> p_117481_) {
      super(p_117481_);
   }

   public void m_6494_(PoseStack p_117494_, MultiBufferSource p_117495_, int p_117496_, SnowGolem p_117497_, float p_117498_, float p_117499_, float p_117500_, float p_117501_, float p_117502_, float p_117503_) {
      if (p_117497_.m_29930_()) {
         Minecraft minecraft = Minecraft.m_91087_();
         boolean flag = minecraft.m_91314_(p_117497_) && p_117497_.m_20145_();
         if (!p_117497_.m_20145_() || flag) {
            p_117494_.m_85836_();
            this.m_117386_().m_103851_().m_104299_(p_117494_);
            float f = 0.625F;
            p_117494_.m_85837_(0.0D, -0.34375D, 0.0D);
            p_117494_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
            p_117494_.m_85841_(0.625F, -0.625F, -0.625F);
            ItemStack itemstack = new ItemStack(Blocks.f_50143_);
            if (flag) {
               BlockState blockstate = Blocks.f_50143_.m_49966_();
               BlockRenderDispatcher blockrenderdispatcher = minecraft.m_91289_();
               BakedModel bakedmodel = blockrenderdispatcher.m_110910_(blockstate);
               int i = LivingEntityRenderer.m_115338_(p_117497_, 0.0F);
               p_117494_.m_85837_(-0.5D, -0.5D, -0.5D);
               blockrenderdispatcher.m_110937_().m_111067_(p_117494_.m_85850_(), p_117495_.m_6299_(RenderType.m_110491_(TextureAtlas.f_118259_)), blockstate, bakedmodel, 0.0F, 0.0F, 0.0F, p_117496_, i);
            } else {
               minecraft.m_91291_().m_174242_(p_117497_, itemstack, ItemTransforms.TransformType.HEAD, false, p_117494_, p_117495_, p_117497_.f_19853_, p_117496_, LivingEntityRenderer.m_115338_(p_117497_, 0.0F), p_117497_.m_142049_());
            }

            p_117494_.m_85849_();
         }
      }
   }
}