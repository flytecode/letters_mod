package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerItemInHandLayer<T extends Player, M extends EntityModel<T> & ArmedModel & HeadedModel> extends ItemInHandLayer<T, M> {
   private static final float f_174513_ = (-(float)Math.PI / 6F);
   private static final float f_174514_ = ((float)Math.PI / 2F);

   public PlayerItemInHandLayer(RenderLayerParent<T, M> p_174516_) {
      super(p_174516_);
   }

   protected void m_117184_(LivingEntity p_174525_, ItemStack p_174526_, ItemTransforms.TransformType p_174527_, HumanoidArm p_174528_, PoseStack p_174529_, MultiBufferSource p_174530_, int p_174531_) {
      if (p_174526_.m_150930_(Items.f_151059_) && p_174525_.m_21211_() == p_174526_ && p_174525_.f_20913_ == 0) {
         this.m_174517_(p_174525_, p_174526_, p_174528_, p_174529_, p_174530_, p_174531_);
      } else {
         super.m_117184_(p_174525_, p_174526_, p_174527_, p_174528_, p_174529_, p_174530_, p_174531_);
      }

   }

   private void m_174517_(LivingEntity p_174518_, ItemStack p_174519_, HumanoidArm p_174520_, PoseStack p_174521_, MultiBufferSource p_174522_, int p_174523_) {
      p_174521_.m_85836_();
      ModelPart modelpart = this.m_117386_().m_5585_();
      float f = modelpart.f_104203_;
      modelpart.f_104203_ = Mth.m_14036_(modelpart.f_104203_, (-(float)Math.PI / 6F), ((float)Math.PI / 2F));
      modelpart.m_104299_(p_174521_);
      modelpart.f_104203_ = f;
      CustomHeadLayer.m_174483_(p_174521_, false);
      boolean flag = p_174520_ == HumanoidArm.LEFT;
      p_174521_.m_85837_((double)((flag ? -2.5F : 2.5F) / 16.0F), -0.0625D, 0.0D);
      Minecraft.m_91087_().m_91292_().m_109322_(p_174518_, p_174519_, ItemTransforms.TransformType.HEAD, false, p_174521_, p_174522_, p_174523_);
      p_174521_.m_85849_();
   }
}