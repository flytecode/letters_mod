package net.minecraft.client.resources.model;

import com.mojang.math.Transformation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ModelState extends net.minecraftforge.client.extensions.IForgeModelState {
   default Transformation m_6189_() {
      return Transformation.m_121093_();
   }

   default boolean m_7538_() {
      return false;
   }
}
