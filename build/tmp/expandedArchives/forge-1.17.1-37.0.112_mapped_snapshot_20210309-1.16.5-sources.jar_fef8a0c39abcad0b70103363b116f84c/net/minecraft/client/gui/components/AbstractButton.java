package net.minecraft.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractButton extends AbstractWidget {
   public AbstractButton(int p_93365_, int p_93366_, int p_93367_, int p_93368_, Component p_93369_) {
      super(p_93365_, p_93366_, p_93367_, p_93368_, p_93369_);
   }

   public abstract void m_5691_();

   public void m_5716_(double p_93371_, double p_93372_) {
      this.m_5691_();
   }

   public boolean m_7933_(int p_93374_, int p_93375_, int p_93376_) {
      if (this.f_93623_ && this.f_93624_) {
         if (p_93374_ != 257 && p_93374_ != 32 && p_93374_ != 335) {
            return false;
         } else {
            this.m_7435_(Minecraft.m_91087_().m_91106_());
            this.m_5691_();
            return true;
         }
      } else {
         return false;
      }
   }
}