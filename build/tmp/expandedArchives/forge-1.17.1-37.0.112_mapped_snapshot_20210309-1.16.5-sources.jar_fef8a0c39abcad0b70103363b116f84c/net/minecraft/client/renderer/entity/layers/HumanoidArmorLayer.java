package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private static final Map<String, ResourceLocation> f_117070_ = Maps.newHashMap();
   private final A f_117071_;
   private final A f_117072_;

   public HumanoidArmorLayer(RenderLayerParent<T, M> p_117075_, A p_117076_, A p_117077_) {
      super(p_117075_);
      this.f_117071_ = p_117076_;
      this.f_117072_ = p_117077_;
   }

   public void m_6494_(PoseStack p_117096_, MultiBufferSource p_117097_, int p_117098_, T p_117099_, float p_117100_, float p_117101_, float p_117102_, float p_117103_, float p_117104_, float p_117105_) {
      this.m_117118_(p_117096_, p_117097_, p_117099_, EquipmentSlot.CHEST, p_117098_, this.m_117078_(EquipmentSlot.CHEST));
      this.m_117118_(p_117096_, p_117097_, p_117099_, EquipmentSlot.LEGS, p_117098_, this.m_117078_(EquipmentSlot.LEGS));
      this.m_117118_(p_117096_, p_117097_, p_117099_, EquipmentSlot.FEET, p_117098_, this.m_117078_(EquipmentSlot.FEET));
      this.m_117118_(p_117096_, p_117097_, p_117099_, EquipmentSlot.HEAD, p_117098_, this.m_117078_(EquipmentSlot.HEAD));
   }

   private void m_117118_(PoseStack p_117119_, MultiBufferSource p_117120_, T p_117121_, EquipmentSlot p_117122_, int p_117123_, A p_117124_) {
      ItemStack itemstack = p_117121_.m_6844_(p_117122_);
      if (itemstack.m_41720_() instanceof ArmorItem) {
         ArmorItem armoritem = (ArmorItem)itemstack.m_41720_();
         if (armoritem.m_40402_() == p_117122_) {
            p_117124_ = getArmorModelHook(p_117121_, itemstack, p_117122_, p_117124_);
            this.m_117386_().m_102872_(p_117124_);
            this.m_117125_(p_117124_, p_117122_);
            boolean flag = this.m_117128_(p_117122_);
            boolean flag1 = itemstack.m_41790_();
            if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) {
               int i = ((net.minecraft.world.item.DyeableLeatherItem)armoritem).m_41121_(itemstack);
               float f = (float)(i >> 16 & 255) / 255.0F;
               float f1 = (float)(i >> 8 & 255) / 255.0F;
               float f2 = (float)(i & 255) / 255.0F;
               this.renderModel(p_117119_, p_117120_, p_117123_, flag1, p_117124_, f, f1, f2, this.getArmorResource(p_117121_, itemstack, p_117122_, null));
               this.renderModel(p_117119_, p_117120_, p_117123_, flag1, p_117124_, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_117121_, itemstack, p_117122_, "overlay"));
            } else {
               this.renderModel(p_117119_, p_117120_, p_117123_, flag1, p_117124_, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_117121_, itemstack, p_117122_, null));
            }

         }
      }
   }

   protected void m_117125_(A p_117126_, EquipmentSlot p_117127_) {
      p_117126_.m_8009_(false);
      switch(p_117127_) {
      case HEAD:
         p_117126_.f_102808_.f_104207_ = true;
         p_117126_.f_102809_.f_104207_ = true;
         break;
      case CHEST:
         p_117126_.f_102810_.f_104207_ = true;
         p_117126_.f_102811_.f_104207_ = true;
         p_117126_.f_102812_.f_104207_ = true;
         break;
      case LEGS:
         p_117126_.f_102810_.f_104207_ = true;
         p_117126_.f_102813_.f_104207_ = true;
         p_117126_.f_102814_.f_104207_ = true;
         break;
      case FEET:
         p_117126_.f_102813_.f_104207_ = true;
         p_117126_.f_102814_.f_104207_ = true;
      }

   }

   private void m_117106_(PoseStack p_117107_, MultiBufferSource p_117108_, int p_117109_, ArmorItem p_117110_, boolean p_117111_, A p_117112_, boolean p_117113_, float p_117114_, float p_117115_, float p_117116_, @Nullable String p_117117_) {
       renderModel(p_117107_, p_117108_, p_117109_, p_117111_, p_117112_, p_117114_, p_117115_, p_117116_, this.m_117080_(p_117110_, p_117113_, p_117117_));
   }
   private void renderModel(PoseStack p_117107_, MultiBufferSource p_117108_, int p_117109_, boolean p_117111_, A p_117112_, float p_117114_, float p_117115_, float p_117116_, ResourceLocation armorResource) {
      VertexConsumer vertexconsumer = ItemRenderer.m_115184_(p_117108_, RenderType.m_110431_(armorResource), false, p_117111_);
      p_117112_.m_7695_(p_117107_, vertexconsumer, p_117109_, OverlayTexture.f_118083_, p_117114_, p_117115_, p_117116_, 1.0F);
   }

   private A m_117078_(EquipmentSlot p_117079_) {
      return (A)(this.m_117128_(p_117079_) ? this.f_117071_ : this.f_117072_);
   }

   private boolean m_117128_(EquipmentSlot p_117129_) {
      return p_117129_ == EquipmentSlot.LEGS;
   }

   @Deprecated //Use the more sensitive version getArmorResource below
   private ResourceLocation m_117080_(ArmorItem p_117081_, boolean p_117082_, @Nullable String p_117083_) {
      String s = "textures/models/armor/" + p_117081_.m_40401_().m_6082_() + "_layer_" + (p_117082_ ? 2 : 1) + (p_117083_ == null ? "" : "_" + p_117083_) + ".png";
      return f_117070_.computeIfAbsent(s, ResourceLocation::new);
   }

   /*=================================== FORGE START =========================================*/

   /**
    * Hook to allow item-sensitive armor model. for LayerBipedArmor.
    */
   protected A getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model) {
      return net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
   }

   /**
    * More generic ForgeHook version of the above function, it allows for Items to have more control over what texture they provide.
    *
    * @param entity Entity wearing the armor
    * @param stack ItemStack for the armor
    * @param slot Slot ID that the item is in
    * @param type Subtype, can be null or "overlay"
    * @return ResourceLocation pointing at the armor's texture
    */
   public ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
      ArmorItem item = (ArmorItem)stack.m_41720_();
      String texture = item.m_40401_().m_6082_();
      String domain = "minecraft";
      int idx = texture.indexOf(':');
      if (idx != -1) {
         domain = texture.substring(0, idx);
         texture = texture.substring(idx + 1);
      }
      String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (m_117128_(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

      s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
      ResourceLocation resourcelocation = f_117070_.get(s1);

      if (resourcelocation == null) {
         resourcelocation = new ResourceLocation(s1);
         f_117070_.put(s1, resourcelocation);
      }

      return resourcelocation;
   }
   /*=================================== FORGE END ===========================================*/
}
