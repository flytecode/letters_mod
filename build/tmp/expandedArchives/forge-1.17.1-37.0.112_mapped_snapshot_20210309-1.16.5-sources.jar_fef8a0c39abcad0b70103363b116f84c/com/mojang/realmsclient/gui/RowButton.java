package com.mojang.realmsclient.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RowButton {
   public final int f_88007_;
   public final int f_88008_;
   public final int f_88009_;
   public final int f_88010_;

   public RowButton(int p_88012_, int p_88013_, int p_88014_, int p_88015_) {
      this.f_88007_ = p_88012_;
      this.f_88008_ = p_88013_;
      this.f_88009_ = p_88014_;
      this.f_88010_ = p_88015_;
   }

   public void m_88018_(PoseStack p_88019_, int p_88020_, int p_88021_, int p_88022_, int p_88023_) {
      int i = p_88020_ + this.f_88009_;
      int j = p_88021_ + this.f_88010_;
      boolean flag = false;
      if (p_88022_ >= i && p_88022_ <= i + this.f_88007_ && p_88023_ >= j && p_88023_ <= j + this.f_88008_) {
         flag = true;
      }

      this.m_7537_(p_88019_, i, j, flag);
   }

   protected abstract void m_7537_(PoseStack p_88024_, int p_88025_, int p_88026_, boolean p_88027_);

   public int m_88016_() {
      return this.f_88009_ + this.f_88007_;
   }

   public int m_88043_() {
      return this.f_88010_ + this.f_88008_;
   }

   public abstract void m_7516_(int p_88017_);

   public static void m_88028_(PoseStack p_88029_, List<RowButton> p_88030_, RealmsObjectSelectionList<?> p_88031_, int p_88032_, int p_88033_, int p_88034_, int p_88035_) {
      for(RowButton rowbutton : p_88030_) {
         if (p_88031_.m_5759_() > rowbutton.m_88016_()) {
            rowbutton.m_88018_(p_88029_, p_88032_, p_88033_, p_88034_, p_88035_);
         }
      }

   }

   public static void m_88036_(RealmsObjectSelectionList<?> p_88037_, ObjectSelectionList.Entry<?> p_88038_, List<RowButton> p_88039_, int p_88040_, double p_88041_, double p_88042_) {
      if (p_88040_ == 0) {
         int i = p_88037_.m_6702_().indexOf(p_88038_);
         if (i > -1) {
            p_88037_.m_7109_(i);
            int j = p_88037_.m_5747_();
            int k = p_88037_.m_7610_(i);
            int l = (int)(p_88041_ - (double)j);
            int i1 = (int)(p_88042_ - (double)k);

            for(RowButton rowbutton : p_88039_) {
               if (l >= rowbutton.f_88009_ && l <= rowbutton.m_88016_() && i1 >= rowbutton.f_88010_ && i1 <= rowbutton.m_88043_()) {
                  rowbutton.m_7516_(i);
               }
            }
         }
      }

   }
}