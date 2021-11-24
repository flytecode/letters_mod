package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.PhantomEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhantomRenderer extends MobRenderer<Phantom, PhantomModel<Phantom>> {
   private static final ResourceLocation f_115662_ = new ResourceLocation("textures/entity/phantom.png");

   public PhantomRenderer(EntityRendererProvider.Context p_174338_) {
      super(p_174338_, new PhantomModel<>(p_174338_.m_174023_(ModelLayers.f_171204_)), 0.75F);
      this.m_115326_(new PhantomEyesLayer<>(this));
   }

   public ResourceLocation m_5478_(Phantom p_115679_) {
      return f_115662_;
   }

   protected void m_7546_(Phantom p_115681_, PoseStack p_115682_, float p_115683_) {
      int i = p_115681_.m_33172_();
      float f = 1.0F + 0.15F * (float)i;
      p_115682_.m_85841_(f, f, f);
      p_115682_.m_85837_(0.0D, 1.3125D, 0.1875D);
   }

   protected void m_7523_(Phantom p_115685_, PoseStack p_115686_, float p_115687_, float p_115688_, float p_115689_) {
      super.m_7523_(p_115685_, p_115686_, p_115687_, p_115688_, p_115689_);
      p_115686_.m_85845_(Vector3f.f_122223_.m_122240_(p_115685_.m_146909_()));
   }
}