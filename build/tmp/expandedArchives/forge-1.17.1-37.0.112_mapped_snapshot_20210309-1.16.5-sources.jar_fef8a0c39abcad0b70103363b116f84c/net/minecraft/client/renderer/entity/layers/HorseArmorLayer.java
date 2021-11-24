package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.DyeableHorseArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HorseArmorLayer extends RenderLayer<Horse, HorseModel<Horse>> {
   private final HorseModel<Horse> f_117017_;

   public HorseArmorLayer(RenderLayerParent<Horse, HorseModel<Horse>> p_174496_, EntityModelSet p_174497_) {
      super(p_174496_);
      this.f_117017_ = new HorseModel<>(p_174497_.m_171103_(ModelLayers.f_171187_));
   }

   public void m_6494_(PoseStack p_117032_, MultiBufferSource p_117033_, int p_117034_, Horse p_117035_, float p_117036_, float p_117037_, float p_117038_, float p_117039_, float p_117040_, float p_117041_) {
      ItemStack itemstack = p_117035_.m_30722_();
      if (itemstack.m_41720_() instanceof HorseArmorItem) {
         HorseArmorItem horsearmoritem = (HorseArmorItem)itemstack.m_41720_();
         this.m_117386_().m_102624_(this.f_117017_);
         this.f_117017_.m_6839_(p_117035_, p_117036_, p_117037_, p_117038_);
         this.f_117017_.m_6973_(p_117035_, p_117036_, p_117037_, p_117039_, p_117040_, p_117041_);
         float f;
         float f1;
         float f2;
         if (horsearmoritem instanceof DyeableHorseArmorItem) {
            int i = ((DyeableHorseArmorItem)horsearmoritem).m_41121_(itemstack);
            f = (float)(i >> 16 & 255) / 255.0F;
            f1 = (float)(i >> 8 & 255) / 255.0F;
            f2 = (float)(i & 255) / 255.0F;
         } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
         }

         VertexConsumer vertexconsumer = p_117033_.m_6299_(RenderType.m_110458_(horsearmoritem.m_41367_()));
         this.f_117017_.m_7695_(p_117032_, vertexconsumer, p_117034_, OverlayTexture.f_118083_, f, f1, f2, 1.0F);
      }
   }
}