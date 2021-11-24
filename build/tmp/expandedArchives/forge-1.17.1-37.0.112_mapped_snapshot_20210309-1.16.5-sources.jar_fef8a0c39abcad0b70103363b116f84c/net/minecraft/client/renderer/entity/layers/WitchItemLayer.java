package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitchItemLayer<T extends LivingEntity> extends CrossedArmsItemLayer<T, WitchModel<T>> {
   public WitchItemLayer(RenderLayerParent<T, WitchModel<T>> p_117672_) {
      super(p_117672_);
   }

   public void m_6494_(PoseStack p_117685_, MultiBufferSource p_117686_, int p_117687_, T p_117688_, float p_117689_, float p_117690_, float p_117691_, float p_117692_, float p_117693_, float p_117694_) {
      ItemStack itemstack = p_117688_.m_21205_();
      p_117685_.m_85836_();
      if (itemstack.m_150930_(Items.f_42589_)) {
         this.m_117386_().m_5585_().m_104299_(p_117685_);
         this.m_117386_().m_104073_().m_104299_(p_117685_);
         p_117685_.m_85837_(0.0625D, 0.25D, 0.0D);
         p_117685_.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         p_117685_.m_85845_(Vector3f.f_122223_.m_122240_(140.0F));
         p_117685_.m_85845_(Vector3f.f_122227_.m_122240_(10.0F));
         p_117685_.m_85837_(0.0D, (double)-0.4F, (double)0.4F);
      }

      super.m_6494_(p_117685_, p_117686_, p_117687_, p_117688_, p_117689_, p_117690_, p_117691_, p_117692_, p_117693_, p_117694_);
      p_117685_.m_85849_();
   }
}