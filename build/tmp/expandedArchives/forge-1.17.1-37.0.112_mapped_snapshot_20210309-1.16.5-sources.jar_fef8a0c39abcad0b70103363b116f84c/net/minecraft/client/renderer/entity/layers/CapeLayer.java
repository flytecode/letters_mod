package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CapeLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   public CapeLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> p_116602_) {
      super(p_116602_);
   }

   public void m_6494_(PoseStack p_116615_, MultiBufferSource p_116616_, int p_116617_, AbstractClientPlayer p_116618_, float p_116619_, float p_116620_, float p_116621_, float p_116622_, float p_116623_, float p_116624_) {
      if (p_116618_.m_108555_() && !p_116618_.m_20145_() && p_116618_.m_36170_(PlayerModelPart.CAPE) && p_116618_.m_108561_() != null) {
         ItemStack itemstack = p_116618_.m_6844_(EquipmentSlot.CHEST);
         if (!itemstack.m_150930_(Items.f_42741_)) {
            p_116615_.m_85836_();
            p_116615_.m_85837_(0.0D, 0.0D, 0.125D);
            double d0 = Mth.m_14139_((double)p_116621_, p_116618_.f_36102_, p_116618_.f_36105_) - Mth.m_14139_((double)p_116621_, p_116618_.f_19854_, p_116618_.m_20185_());
            double d1 = Mth.m_14139_((double)p_116621_, p_116618_.f_36103_, p_116618_.f_36106_) - Mth.m_14139_((double)p_116621_, p_116618_.f_19855_, p_116618_.m_20186_());
            double d2 = Mth.m_14139_((double)p_116621_, p_116618_.f_36104_, p_116618_.f_36075_) - Mth.m_14139_((double)p_116621_, p_116618_.f_19856_, p_116618_.m_20189_());
            float f = p_116618_.f_20884_ + (p_116618_.f_20883_ - p_116618_.f_20884_);
            double d3 = (double)Mth.m_14031_(f * ((float)Math.PI / 180F));
            double d4 = (double)(-Mth.m_14089_(f * ((float)Math.PI / 180F)));
            float f1 = (float)d1 * 10.0F;
            f1 = Mth.m_14036_(f1, -6.0F, 32.0F);
            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
            f2 = Mth.m_14036_(f2, 0.0F, 150.0F);
            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
            f3 = Mth.m_14036_(f3, -20.0F, 20.0F);
            if (f2 < 0.0F) {
               f2 = 0.0F;
            }

            float f4 = Mth.m_14179_(p_116621_, p_116618_.f_36099_, p_116618_.f_36100_);
            f1 = f1 + Mth.m_14031_(Mth.m_14179_(p_116621_, p_116618_.f_19867_, p_116618_.f_19787_) * 6.0F) * 32.0F * f4;
            if (p_116618_.m_6047_()) {
               f1 += 25.0F;
            }

            p_116615_.m_85845_(Vector3f.f_122223_.m_122240_(6.0F + f2 / 2.0F + f1));
            p_116615_.m_85845_(Vector3f.f_122227_.m_122240_(f3 / 2.0F));
            p_116615_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - f3 / 2.0F));
            VertexConsumer vertexconsumer = p_116616_.m_6299_(RenderType.m_110446_(p_116618_.m_108561_()));
            this.m_117386_().m_103411_(p_116615_, vertexconsumer, p_116617_, OverlayTexture.f_118083_);
            p_116615_.m_85849_();
         }
      }
   }
}