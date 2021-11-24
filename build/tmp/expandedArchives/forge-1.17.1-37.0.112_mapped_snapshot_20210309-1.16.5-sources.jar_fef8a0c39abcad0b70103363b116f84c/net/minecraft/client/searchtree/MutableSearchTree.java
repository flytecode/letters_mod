package net.minecraft.client.searchtree;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface MutableSearchTree<T> extends SearchTree<T> {
   void m_8080_(T p_119869_);

   void m_7716_();

   void m_7729_();
}