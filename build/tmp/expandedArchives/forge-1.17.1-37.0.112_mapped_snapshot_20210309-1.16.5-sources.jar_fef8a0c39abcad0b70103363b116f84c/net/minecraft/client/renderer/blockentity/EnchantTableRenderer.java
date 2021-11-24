package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantTableRenderer implements BlockEntityRenderer<EnchantmentTableBlockEntity> {
   public static final Material f_112405_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/enchanting_table_book"));
   private final BookModel f_112406_;

   public EnchantTableRenderer(BlockEntityRendererProvider.Context p_173619_) {
      this.f_112406_ = new BookModel(p_173619_.m_173582_(ModelLayers.f_171271_));
   }

   public void m_6922_(EnchantmentTableBlockEntity p_112418_, float p_112419_, PoseStack p_112420_, MultiBufferSource p_112421_, int p_112422_, int p_112423_) {
      p_112420_.m_85836_();
      p_112420_.m_85837_(0.5D, 0.75D, 0.5D);
      float f = (float)p_112418_.f_59251_ + p_112419_;
      p_112420_.m_85837_(0.0D, (double)(0.1F + Mth.m_14031_(f * 0.1F) * 0.01F), 0.0D);

      float f1;
      for(f1 = p_112418_.f_59258_ - p_112418_.f_59259_; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F)) {
      }

      while(f1 < -(float)Math.PI) {
         f1 += ((float)Math.PI * 2F);
      }

      float f2 = p_112418_.f_59259_ + f1 * p_112419_;
      p_112420_.m_85845_(Vector3f.f_122225_.m_122270_(-f2));
      p_112420_.m_85845_(Vector3f.f_122227_.m_122240_(80.0F));
      float f3 = Mth.m_14179_(p_112419_, p_112418_.f_59253_, p_112418_.f_59252_);
      float f4 = Mth.m_14187_(f3 + 0.25F) * 1.6F - 0.3F;
      float f5 = Mth.m_14187_(f3 + 0.75F) * 1.6F - 0.3F;
      float f6 = Mth.m_14179_(p_112419_, p_112418_.f_59257_, p_112418_.f_59256_);
      this.f_112406_.m_102292_(f, Mth.m_14036_(f4, 0.0F, 1.0F), Mth.m_14036_(f5, 0.0F, 1.0F), f6);
      VertexConsumer vertexconsumer = f_112405_.m_119194_(p_112421_, RenderType::m_110446_);
      this.f_112406_.m_102316_(p_112420_, vertexconsumer, p_112422_, p_112423_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_112420_.m_85849_();
   }
}