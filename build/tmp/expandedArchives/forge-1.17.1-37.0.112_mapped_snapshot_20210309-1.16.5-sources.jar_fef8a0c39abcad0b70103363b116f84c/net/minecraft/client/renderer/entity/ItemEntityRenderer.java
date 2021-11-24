package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Random;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemEntityRenderer extends EntityRenderer<ItemEntity> {
   private static final float f_174189_ = 0.15F;
   private static final int f_174190_ = 48;
   private static final int f_174191_ = 32;
   private static final int f_174192_ = 16;
   private static final int f_174193_ = 1;
   private static final float f_174194_ = 0.0F;
   private static final float f_174195_ = 0.0F;
   private static final float f_174196_ = 0.09375F;
   private final ItemRenderer f_115019_;
   private final Random f_115020_ = new Random();

   public ItemEntityRenderer(EntityRendererProvider.Context p_174198_) {
      super(p_174198_);
      this.f_115019_ = p_174198_.m_174025_();
      this.f_114477_ = 0.15F;
      this.f_114478_ = 0.75F;
   }

   protected int m_115042_(ItemStack p_115043_) {
      int i = 1;
      if (p_115043_.m_41613_() > 48) {
         i = 5;
      } else if (p_115043_.m_41613_() > 32) {
         i = 4;
      } else if (p_115043_.m_41613_() > 16) {
         i = 3;
      } else if (p_115043_.m_41613_() > 1) {
         i = 2;
      }

      return i;
   }

   public void m_7392_(ItemEntity p_115036_, float p_115037_, float p_115038_, PoseStack p_115039_, MultiBufferSource p_115040_, int p_115041_) {
      p_115039_.m_85836_();
      ItemStack itemstack = p_115036_.m_32055_();
      int i = itemstack.m_41619_() ? 187 : Item.m_41393_(itemstack.m_41720_()) + itemstack.m_41773_();
      this.f_115020_.setSeed((long)i);
      BakedModel bakedmodel = this.f_115019_.m_174264_(itemstack, p_115036_.f_19853_, (LivingEntity)null, p_115036_.m_142049_());
      boolean flag = bakedmodel.m_7539_();
      int j = this.m_115042_(itemstack);
      float f = 0.25F;
      float f1 = Mth.m_14031_(((float)p_115036_.m_32059_() + p_115038_) / 10.0F + p_115036_.f_31983_) * 0.1F + 0.1F;
      float f2 = shouldBob() ? bakedmodel.m_7442_().m_111808_(ItemTransforms.TransformType.GROUND).f_111757_.m_122260_() : 0;
      p_115039_.m_85837_(0.0D, (double)(f1 + 0.25F * f2), 0.0D);
      float f3 = p_115036_.m_32008_(p_115038_);
      p_115039_.m_85845_(Vector3f.f_122225_.m_122270_(f3));
      if (!flag) {
         float f7 = -0.0F * (float)(j - 1) * 0.5F;
         float f8 = -0.0F * (float)(j - 1) * 0.5F;
         float f9 = -0.09375F * (float)(j - 1) * 0.5F;
         p_115039_.m_85837_((double)f7, (double)f8, (double)f9);
      }

      for(int k = 0; k < j; ++k) {
         p_115039_.m_85836_();
         if (k > 0) {
            if (flag) {
               float f11 = (this.f_115020_.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float f13 = (this.f_115020_.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float f10 = (this.f_115020_.nextFloat() * 2.0F - 1.0F) * 0.15F;
               p_115039_.m_85837_(shouldSpreadItems() ? f11 : 0, shouldSpreadItems() ? f13 : 0, shouldSpreadItems() ? f10 : 0);
            } else {
               float f12 = (this.f_115020_.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               float f14 = (this.f_115020_.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               p_115039_.m_85837_(shouldSpreadItems() ? f12 : 0, shouldSpreadItems() ? f14 : 0, 0.0D);
            }
         }

         this.f_115019_.m_115143_(itemstack, ItemTransforms.TransformType.GROUND, false, p_115039_, p_115040_, p_115041_, OverlayTexture.f_118083_, bakedmodel);
         p_115039_.m_85849_();
         if (!flag) {
            p_115039_.m_85837_(0.0, 0.0, 0.09375F);
         }
      }

      p_115039_.m_85849_();
      super.m_7392_(p_115036_, p_115037_, p_115038_, p_115039_, p_115040_, p_115041_);
   }

   public ResourceLocation m_5478_(ItemEntity p_115034_) {
      return TextureAtlas.f_118259_;
   }

   /*==================================== FORGE START ===========================================*/

   /**
    * @return If items should spread out when rendered in 3D
    */
   public boolean shouldSpreadItems() {
      return true;
   }

   /**
    * @return If items should have a bob effect
    */
   public boolean shouldBob() {
      return true;
   }
   /*==================================== FORGE END =============================================*/
}
