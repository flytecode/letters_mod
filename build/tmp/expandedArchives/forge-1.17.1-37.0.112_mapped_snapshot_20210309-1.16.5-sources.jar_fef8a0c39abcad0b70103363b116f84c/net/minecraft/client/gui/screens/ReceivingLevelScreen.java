package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReceivingLevelScreen extends Screen {
   private static final Component f_96526_ = new TranslatableComponent("multiplayer.downloadingTerrain");

   public ReceivingLevelScreen() {
      super(NarratorChatListener.f_93310_);
   }

   public boolean m_6913_() {
      return false;
   }

   public void m_6305_(PoseStack p_96530_, int p_96531_, int p_96532_, float p_96533_) {
      this.m_96626_(0);
      m_93215_(p_96530_, this.f_96547_, f_96526_, this.f_96543_ / 2, this.f_96544_ / 2 - 50, 16777215);
      super.m_6305_(p_96530_, p_96531_, p_96532_, p_96533_);
   }

   public boolean m_7043_() {
      return false;
   }
}