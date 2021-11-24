package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.ProgressListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ProgressScreen extends Screen implements ProgressListener {
   @Nullable
   private Component f_96506_;
   @Nullable
   private Component f_96507_;
   private int f_96508_;
   private boolean f_96509_;
   private final boolean f_169362_;

   public ProgressScreen(boolean p_169364_) {
      super(NarratorChatListener.f_93310_);
      this.f_169362_ = p_169364_;
   }

   public boolean m_6913_() {
      return false;
   }

   public void m_6309_(Component p_96520_) {
      this.m_6308_(p_96520_);
   }

   public void m_6308_(Component p_96523_) {
      this.f_96506_ = p_96523_;
      this.m_6307_(new TranslatableComponent("progress.working"));
   }

   public void m_6307_(Component p_96525_) {
      this.f_96507_ = p_96525_;
      this.m_6952_(0);
   }

   public void m_6952_(int p_96513_) {
      this.f_96508_ = p_96513_;
   }

   public void m_7730_() {
      this.f_96509_ = true;
   }

   public void m_6305_(PoseStack p_96515_, int p_96516_, int p_96517_, float p_96518_) {
      if (this.f_96509_) {
         if (this.f_169362_) {
            this.f_96541_.m_91152_((Screen)null);
         }

      } else {
         this.m_7333_(p_96515_);
         if (this.f_96506_ != null) {
            m_93215_(p_96515_, this.f_96547_, this.f_96506_, this.f_96543_ / 2, 70, 16777215);
         }

         if (this.f_96507_ != null && this.f_96508_ != 0) {
            m_93215_(p_96515_, this.f_96547_, (new TextComponent("")).m_7220_(this.f_96507_).m_130946_(" " + this.f_96508_ + "%"), this.f_96543_ / 2, 90, 16777215);
         }

         super.m_6305_(p_96515_, p_96516_, p_96517_, p_96518_);
      }
   }
}