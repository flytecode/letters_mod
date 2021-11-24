package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation f_116934_ = new ResourceLocation("textures/entity/elytra.png");
   private final ElytraModel<T> f_116935_;

   public ElytraLayer(RenderLayerParent<T, M> p_174493_, EntityModelSet p_174494_) {
      super(p_174493_);
      this.f_116935_ = new ElytraModel<>(p_174494_.m_171103_(ModelLayers.f_171141_));
   }

   public void m_6494_(PoseStack p_116951_, MultiBufferSource p_116952_, int p_116953_, T p_116954_, float p_116955_, float p_116956_, float p_116957_, float p_116958_, float p_116959_, float p_116960_) {
      ItemStack itemstack = p_116954_.m_6844_(EquipmentSlot.CHEST);
      if (shouldRender(itemstack, p_116954_)) {
         ResourceLocation resourcelocation;
         if (p_116954_ instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)p_116954_;
            if (abstractclientplayer.m_108562_() && abstractclientplayer.m_108563_() != null) {
               resourcelocation = abstractclientplayer.m_108563_();
            } else if (abstractclientplayer.m_108555_() && abstractclientplayer.m_108561_() != null && abstractclientplayer.m_36170_(PlayerModelPart.CAPE)) {
               resourcelocation = abstractclientplayer.m_108561_();
            } else {
               resourcelocation = getElytraTexture(itemstack, p_116954_);
            }
         } else {
            resourcelocation = getElytraTexture(itemstack, p_116954_);
         }

         p_116951_.m_85836_();
         p_116951_.m_85837_(0.0D, 0.0D, 0.125D);
         this.m_117386_().m_102624_(this.f_116935_);
         this.f_116935_.m_6973_(p_116954_, p_116955_, p_116956_, p_116958_, p_116959_, p_116960_);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(p_116952_, RenderType.m_110431_(resourcelocation), false, itemstack.m_41790_());
         this.f_116935_.m_7695_(p_116951_, vertexconsumer, p_116953_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         p_116951_.m_85849_();
      }
   }

   /**
    * Determines if the ElytraLayer should render.
    * ItemStack and Entity are provided for modder convenience,
    * For example, using the same ElytraLayer for multiple custom Elytra.
    *
    * @param stack  The Elytra ItemStack
    * @param entity The entity being rendered.
    * @return If the ElytraLayer should render.
    */
   public boolean shouldRender(ItemStack stack, T entity) {
      return stack.m_41720_() == Items.f_42741_;
   }

   /**
    * Gets the texture to use with this ElytraLayer.
    * This assumes the vanilla Elytra model.
    *
    * @param stack  The Elytra ItemStack.
    * @param entity The entity being rendered.
    * @return The texture.
    */
   public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
      return f_116934_;
   }
}
