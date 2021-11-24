package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArmorStandRenderer extends LivingEntityRenderer<ArmorStand, ArmorStandArmorModel> {
   public static final ResourceLocation f_113780_ = new ResourceLocation("textures/entity/armorstand/wood.png");

   public ArmorStandRenderer(EntityRendererProvider.Context p_173915_) {
      super(p_173915_, new ArmorStandModel(p_173915_.m_174023_(ModelLayers.f_171155_)), 0.0F);
      this.m_115326_(new HumanoidArmorLayer<>(this, new ArmorStandArmorModel(p_173915_.m_174023_(ModelLayers.f_171208_)), new ArmorStandArmorModel(p_173915_.m_174023_(ModelLayers.f_171261_))));
      this.m_115326_(new ItemInHandLayer<>(this));
      this.m_115326_(new ElytraLayer<>(this, p_173915_.m_174027_()));
      this.m_115326_(new CustomHeadLayer<>(this, p_173915_.m_174027_()));
   }

   public ResourceLocation m_5478_(ArmorStand p_113798_) {
      return f_113780_;
   }

   protected void m_7523_(ArmorStand p_113800_, PoseStack p_113801_, float p_113802_, float p_113803_, float p_113804_) {
      p_113801_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - p_113803_));
      float f = (float)(p_113800_.f_19853_.m_46467_() - p_113800_.f_31528_) + p_113804_;
      if (f < 5.0F) {
         p_113801_.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14031_(f / 1.5F * (float)Math.PI) * 3.0F));
      }

   }

   protected boolean m_6512_(ArmorStand p_113815_) {
      double d0 = this.f_114476_.m_114471_(p_113815_);
      float f = p_113815_.m_6047_() ? 32.0F : 64.0F;
      return d0 >= (double)(f * f) ? false : p_113815_.m_20151_();
   }

   @Nullable
   protected RenderType m_7225_(ArmorStand p_113806_, boolean p_113807_, boolean p_113808_, boolean p_113809_) {
      if (!p_113806_.m_31677_()) {
         return super.m_7225_(p_113806_, p_113807_, p_113808_, p_113809_);
      } else {
         ResourceLocation resourcelocation = this.m_5478_(p_113806_);
         if (p_113808_) {
            return RenderType.m_110454_(resourcelocation, false);
         } else {
            return p_113807_ ? RenderType.m_110443_(resourcelocation, false) : null;
         }
      }
   }
}