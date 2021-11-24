package net.minecraft.client.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SkullModelBase extends Model {
   public SkullModelBase() {
      super(RenderType::m_110473_);
   }

   public abstract void m_142698_(float p_170950_, float p_170951_, float p_170952_);
}