package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemFrameRenderer<T extends ItemFrame> extends EntityRenderer<T> {
   private static final ModelResourceLocation f_115044_ = new ModelResourceLocation("item_frame", "map=false");
   private static final ModelResourceLocation f_115045_ = new ModelResourceLocation("item_frame", "map=true");
   private static final ModelResourceLocation f_174201_ = new ModelResourceLocation("glow_item_frame", "map=false");
   private static final ModelResourceLocation f_174202_ = new ModelResourceLocation("glow_item_frame", "map=true");
   public static final int f_174199_ = 5;
   public static final int f_174200_ = 30;
   private final Minecraft f_115046_ = Minecraft.m_91087_();
   private final ItemRenderer f_115047_;

   public ItemFrameRenderer(EntityRendererProvider.Context p_174204_) {
      super(p_174204_);
      this.f_115047_ = p_174204_.m_174025_();
   }

   protected int m_6086_(T p_174216_, BlockPos p_174217_) {
      return p_174216_.m_6095_() == EntityType.f_147033_ ? Math.max(5, super.m_6086_(p_174216_, p_174217_)) : super.m_6086_(p_174216_, p_174217_);
   }

   public void m_7392_(T p_115076_, float p_115077_, float p_115078_, PoseStack p_115079_, MultiBufferSource p_115080_, int p_115081_) {
      super.m_7392_(p_115076_, p_115077_, p_115078_, p_115079_, p_115080_, p_115081_);
      p_115079_.m_85836_();
      Direction direction = p_115076_.m_6350_();
      Vec3 vec3 = this.m_7860_(p_115076_, p_115078_);
      p_115079_.m_85837_(-vec3.m_7096_(), -vec3.m_7098_(), -vec3.m_7094_());
      double d0 = 0.46875D;
      p_115079_.m_85837_((double)direction.m_122429_() * 0.46875D, (double)direction.m_122430_() * 0.46875D, (double)direction.m_122431_() * 0.46875D);
      p_115079_.m_85845_(Vector3f.f_122223_.m_122240_(p_115076_.m_146909_()));
      p_115079_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - p_115076_.m_146908_()));
      boolean flag = p_115076_.m_20145_();
      ItemStack itemstack = p_115076_.m_31822_();
      if (!flag) {
         BlockRenderDispatcher blockrenderdispatcher = this.f_115046_.m_91289_();
         ModelManager modelmanager = blockrenderdispatcher.m_110907_().m_110881_();
         ModelResourceLocation modelresourcelocation = p_115076_.m_31822_().m_41720_() instanceof MapItem ? f_115045_ : f_115044_;
         p_115079_.m_85836_();
         p_115079_.m_85837_(-0.5D, -0.5D, -0.5D);
         blockrenderdispatcher.m_110937_().m_111067_(p_115079_.m_85850_(), p_115080_.m_6299_(Sheets.m_110789_()), (BlockState)null, modelmanager.m_119422_(modelresourcelocation), 1.0F, 1.0F, 1.0F, p_115081_, OverlayTexture.f_118083_);
         p_115079_.m_85849_();
      }

      if (!itemstack.m_41619_()) {
         MapItemSavedData mapitemsaveddata = MapItem.m_42853_(itemstack, p_115076_.f_19853_);
         if (flag) {
            p_115079_.m_85837_(0.0D, 0.0D, 0.5D);
         } else {
            p_115079_.m_85837_(0.0D, 0.0D, 0.4375D);
         }

         int j = mapitemsaveddata != null ? p_115076_.m_31823_() % 4 * 2 : p_115076_.m_31823_();
         p_115079_.m_85845_(Vector3f.f_122227_.m_122240_((float)j * 360.0F / 8.0F));
         if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderItemInFrameEvent(p_115076_, this, p_115079_, p_115080_, p_115081_))) {
         if (mapitemsaveddata != null) {
            p_115079_.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
            float f = 0.0078125F;
            p_115079_.m_85841_(0.0078125F, 0.0078125F, 0.0078125F);
            p_115079_.m_85837_(-64.0D, -64.0D, 0.0D);
            Integer integer = MapItem.m_151131_(itemstack);
            p_115079_.m_85837_(0.0D, 0.0D, -1.0D);
            if (mapitemsaveddata != null) {
               int i = this.m_174208_(p_115076_, 15728850, p_115081_);
               this.f_115046_.f_91063_.m_109151_().m_168771_(p_115079_, p_115080_, integer, mapitemsaveddata, true, i);
            }
         } else {
            int k = this.m_174208_(p_115076_, 15728880, p_115081_);
            p_115079_.m_85841_(0.5F, 0.5F, 0.5F);
            this.f_115047_.m_174269_(itemstack, ItemTransforms.TransformType.FIXED, k, OverlayTexture.f_118083_, p_115079_, p_115080_, p_115076_.m_142049_());
         }
         }
      }

      p_115079_.m_85849_();
   }

   private int m_174208_(T p_174209_, int p_174210_, int p_174211_) {
      return p_174209_.m_6095_() == EntityType.f_147033_ ? p_174210_ : p_174211_;
   }

   private ModelResourceLocation m_174212_(T p_174213_, ItemStack p_174214_) {
      boolean flag = p_174213_.m_6095_() == EntityType.f_147033_;
      if (p_174214_.m_150930_(Items.f_42573_)) {
         return flag ? f_174202_ : f_115045_;
      } else {
         return flag ? f_174201_ : f_115044_;
      }
   }

   public Vec3 m_7860_(T p_115073_, float p_115074_) {
      return new Vec3((double)((float)p_115073_.m_6350_().m_122429_() * 0.3F), -0.25D, (double)((float)p_115073_.m_6350_().m_122431_() * 0.3F));
   }

   public ResourceLocation m_5478_(T p_115071_) {
      return TextureAtlas.f_118259_;
   }

   protected boolean m_6512_(T p_115091_) {
      if (Minecraft.m_91404_() && !p_115091_.m_31822_().m_41619_() && p_115091_.m_31822_().m_41788_() && this.f_114476_.f_114359_ == p_115091_) {
         double d0 = this.f_114476_.m_114471_(p_115091_);
         float f = p_115091_.m_20163_() ? 32.0F : 64.0F;
         return d0 < (double)(f * f);
      } else {
         return false;
      }
   }

   protected void m_7649_(T p_115083_, Component p_115084_, PoseStack p_115085_, MultiBufferSource p_115086_, int p_115087_) {
      super.m_7649_(p_115083_, p_115083_.m_31822_().m_41786_(), p_115085_, p_115086_, p_115087_);
   }
}
