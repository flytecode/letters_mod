package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TntRenderer extends EntityRenderer<PrimedTnt> {
   public TntRenderer(EntityRendererProvider.Context p_174426_) {
      super(p_174426_);
      this.f_114477_ = 0.5F;
   }

   public void m_7392_(PrimedTnt p_116177_, float p_116178_, float p_116179_, PoseStack p_116180_, MultiBufferSource p_116181_, int p_116182_) {
      p_116180_.m_85836_();
      p_116180_.m_85837_(0.0D, 0.5D, 0.0D);
      int i = p_116177_.m_32100_();
      if ((float)i - p_116179_ + 1.0F < 10.0F) {
         float f = 1.0F - ((float)i - p_116179_ + 1.0F) / 10.0F;
         f = Mth.m_14036_(f, 0.0F, 1.0F);
         f = f * f;
         f = f * f;
         float f1 = 1.0F + f * 0.3F;
         p_116180_.m_85841_(f1, f1, f1);
      }

      p_116180_.m_85845_(Vector3f.f_122225_.m_122240_(-90.0F));
      p_116180_.m_85837_(-0.5D, -0.5D, 0.5D);
      p_116180_.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
      TntMinecartRenderer.m_116157_(Blocks.f_50077_.m_49966_(), p_116180_, p_116181_, p_116182_, i / 5 % 2 == 0);
      p_116180_.m_85849_();
      super.m_7392_(p_116177_, p_116178_, p_116179_, p_116180_, p_116181_, p_116182_);
   }

   public ResourceLocation m_5478_(PrimedTnt p_116175_) {
      return TextureAtlas.f_118259_;
   }
}