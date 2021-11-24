package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrossedArmsItemLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public CrossedArmsItemLayer(RenderLayerParent<T, M> p_116686_) {
      super(p_116686_);
   }

   public void m_6494_(PoseStack p_116699_, MultiBufferSource p_116700_, int p_116701_, T p_116702_, float p_116703_, float p_116704_, float p_116705_, float p_116706_, float p_116707_, float p_116708_) {
      p_116699_.m_85836_();
      p_116699_.m_85837_(0.0D, (double)0.4F, (double)-0.4F);
      p_116699_.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
      ItemStack itemstack = p_116702_.m_6844_(EquipmentSlot.MAINHAND);
      Minecraft.m_91087_().m_91292_().m_109322_(p_116702_, itemstack, ItemTransforms.TransformType.GROUND, false, p_116699_, p_116700_, p_116701_);
      p_116699_.m_85849_();
   }
}