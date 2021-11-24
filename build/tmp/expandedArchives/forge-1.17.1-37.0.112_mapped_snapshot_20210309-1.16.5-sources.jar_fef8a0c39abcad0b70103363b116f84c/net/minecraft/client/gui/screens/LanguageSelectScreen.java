package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LanguageSelectScreen extends OptionsSubScreen {
   private static final Component f_96078_ = (new TextComponent("(")).m_7220_(new TranslatableComponent("options.languageWarning")).m_130946_(")").m_130940_(ChatFormatting.GRAY);
   private LanguageSelectScreen.LanguageSelectionList f_96079_;
   final LanguageManager f_96080_;

   public LanguageSelectScreen(Screen p_96085_, Options p_96086_, LanguageManager p_96087_) {
      super(p_96085_, p_96086_, new TranslatableComponent("options.language"));
      this.f_96080_ = p_96087_;
   }

   protected void m_7856_() {
      this.f_96079_ = new LanguageSelectScreen.LanguageSelectionList(this.f_96541_);
      this.m_7787_(this.f_96079_);
      this.m_142416_(Option.f_91640_.m_7496_(this.f_96282_, this.f_96543_ / 2 - 155, this.f_96544_ - 38, 150));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, this.f_96544_ - 38, 150, 20, CommonComponents.f_130655_, (p_96099_) -> {
         LanguageSelectScreen.LanguageSelectionList.Entry languageselectscreen$languageselectionlist$entry = this.f_96079_.m_93511_();
         if (languageselectscreen$languageselectionlist$entry != null && !languageselectscreen$languageselectionlist$entry.f_96116_.getCode().equals(this.f_96080_.m_118983_().getCode())) {
            this.f_96080_.m_118974_(languageselectscreen$languageselectionlist$entry.f_96116_);
            this.f_96282_.f_92075_ = languageselectscreen$languageselectionlist$entry.f_96116_.getCode();
            this.f_96541_.m_91391_();
            this.f_96282_.m_92169_();
         }

         this.f_96541_.m_91152_(this.f_96281_);
      }));
      super.m_7856_();
   }

   public void m_6305_(PoseStack p_96089_, int p_96090_, int p_96091_, float p_96092_) {
      this.f_96079_.m_6305_(p_96089_, p_96090_, p_96091_, p_96092_);
      m_93215_(p_96089_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 16, 16777215);
      m_93215_(p_96089_, this.f_96547_, f_96078_, this.f_96543_ / 2, this.f_96544_ - 56, 8421504);
      super.m_6305_(p_96089_, p_96090_, p_96091_, p_96092_);
   }

   @OnlyIn(Dist.CLIENT)
   class LanguageSelectionList extends ObjectSelectionList<LanguageSelectScreen.LanguageSelectionList.Entry> {
      public LanguageSelectionList(Minecraft p_96103_) {
         super(p_96103_, LanguageSelectScreen.this.f_96543_, LanguageSelectScreen.this.f_96544_, 32, LanguageSelectScreen.this.f_96544_ - 65 + 4, 18);

         for(LanguageInfo languageinfo : LanguageSelectScreen.this.f_96080_.m_118984_()) {
            LanguageSelectScreen.LanguageSelectionList.Entry languageselectscreen$languageselectionlist$entry = new LanguageSelectScreen.LanguageSelectionList.Entry(languageinfo);
            this.m_7085_(languageselectscreen$languageselectionlist$entry);
            if (LanguageSelectScreen.this.f_96080_.m_118983_().getCode().equals(languageinfo.getCode())) {
               this.m_6987_(languageselectscreen$languageselectionlist$entry);
            }
         }

         if (this.m_93511_() != null) {
            this.m_93494_(this.m_93511_());
         }

      }

      protected int m_5756_() {
         return super.m_5756_() + 20;
      }

      public int m_5759_() {
         return super.m_5759_() + 50;
      }

      protected void m_7733_(PoseStack p_96105_) {
         LanguageSelectScreen.this.m_7333_(p_96105_);
      }

      protected boolean m_5694_() {
         return LanguageSelectScreen.this.m_7222_() == this;
      }

      @OnlyIn(Dist.CLIENT)
      public class Entry extends ObjectSelectionList.Entry<LanguageSelectScreen.LanguageSelectionList.Entry> {
         final LanguageInfo f_96116_;

         public Entry(LanguageInfo p_96119_) {
            this.f_96116_ = p_96119_;
         }

         public void m_6311_(PoseStack p_96126_, int p_96127_, int p_96128_, int p_96129_, int p_96130_, int p_96131_, int p_96132_, int p_96133_, boolean p_96134_, float p_96135_) {
            String s = this.f_96116_.toString();
            LanguageSelectScreen.this.f_96547_.m_92756_(p_96126_, s, (float)(LanguageSelectionList.this.f_93388_ / 2 - LanguageSelectScreen.this.f_96547_.m_92895_(s) / 2), (float)(p_96128_ + 1), 16777215, true);
         }

         public boolean m_6375_(double p_96122_, double p_96123_, int p_96124_) {
            if (p_96124_ == 0) {
               this.m_96120_();
               return true;
            } else {
               return false;
            }
         }

         private void m_96120_() {
            LanguageSelectionList.this.m_6987_(this);
         }

         public Component m_142172_() {
            return new TranslatableComponent("narrator.select", this.f_96116_);
         }
      }
   }
}