package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Model {
   protected final Function<ResourceLocation, RenderType> f_103106_;

   public Model(Function<ResourceLocation, RenderType> p_103110_) {
      this.f_103106_ = p_103110_;
   }

   public final RenderType m_103119_(ResourceLocation p_103120_) {
      return this.f_103106_.apply(p_103120_);
   }

   public abstract void m_7695_(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_);
}