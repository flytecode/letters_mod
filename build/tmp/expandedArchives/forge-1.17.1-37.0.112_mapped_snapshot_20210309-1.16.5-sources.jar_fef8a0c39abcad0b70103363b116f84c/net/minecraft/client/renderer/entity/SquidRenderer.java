package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.SquidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Squid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SquidRenderer<T extends Squid> extends MobRenderer<T, SquidModel<T>> {
   private static final ResourceLocation f_116014_ = new ResourceLocation("textures/entity/squid/squid.png");

   public SquidRenderer(EntityRendererProvider.Context p_174406_, SquidModel<T> p_174407_) {
      super(p_174406_, p_174407_, 0.7F);
   }

   public ResourceLocation m_5478_(T p_116030_) {
      return f_116014_;
   }

   protected void m_7523_(T p_116035_, PoseStack p_116036_, float p_116037_, float p_116038_, float p_116039_) {
      float f = Mth.m_14179_(p_116039_, p_116035_.f_29950_, p_116035_.f_29938_);
      float f1 = Mth.m_14179_(p_116039_, p_116035_.f_29939_, p_116035_.f_29951_);
      p_116036_.m_85837_(0.0D, 0.5D, 0.0D);
      p_116036_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - p_116038_));
      p_116036_.m_85845_(Vector3f.f_122223_.m_122240_(f));
      p_116036_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
      p_116036_.m_85837_(0.0D, (double)-1.2F, 0.0D);
   }

   protected float m_6930_(T p_116032_, float p_116033_) {
      return Mth.m_14179_(p_116033_, p_116032_.f_29943_, p_116032_.f_29942_);
   }
}