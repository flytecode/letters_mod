package net.minecraft.client.gui.screens.controls;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.MouseSettingsScreen;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ControlsScreen extends OptionsSubScreen {
   public KeyMapping f_97514_;
   public long f_97515_;
   private ControlList f_97516_;
   private Button f_97517_;

   public ControlsScreen(Screen p_97519_, Options p_97520_) {
      super(p_97519_, p_97520_, new TranslatableComponent("controls.title"));
   }

   protected void m_7856_() {
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, 18, 150, 20, new TranslatableComponent("options.mouse_settings"), (p_97540_) -> {
         this.f_96541_.m_91152_(new MouseSettingsScreen(this, this.f_96282_));
      }));
      this.m_142416_(Option.f_91631_.m_7496_(this.f_96282_, this.f_96543_ / 2 - 155 + 160, 18, 150));
      this.f_97516_ = new ControlList(this, this.f_96541_);
      this.m_7787_(this.f_97516_);
      this.f_97517_ = this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 29, 150, 20, new TranslatableComponent("controls.resetAll"), (p_97538_) -> {
         for(KeyMapping keymapping : this.f_96282_.f_92059_) {
            keymapping.setToDefault();
         }

         KeyMapping.m_90854_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, this.f_96544_ - 29, 150, 20, CommonComponents.f_130655_, (p_97535_) -> {
         this.f_96541_.m_91152_(this.f_96281_);
      }));
   }

   public boolean m_6375_(double p_97522_, double p_97523_, int p_97524_) {
      if (this.f_97514_ != null) {
         this.f_96282_.m_92159_(this.f_97514_, InputConstants.Type.MOUSE.m_84895_(p_97524_));
         this.f_97514_ = null;
         KeyMapping.m_90854_();
         return true;
      } else {
         return super.m_6375_(p_97522_, p_97523_, p_97524_);
      }
   }

   public boolean m_7933_(int p_97526_, int p_97527_, int p_97528_) {
      if (this.f_97514_ != null) {
         if (p_97526_ == 256) {
            this.f_97514_.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.getActiveModifier(), InputConstants.f_84822_);
            this.f_96282_.m_92159_(this.f_97514_, InputConstants.f_84822_);
         } else {
            this.f_97514_.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.getActiveModifier(), InputConstants.m_84827_(p_97526_, p_97527_));
            this.f_96282_.m_92159_(this.f_97514_, InputConstants.m_84827_(p_97526_, p_97527_));
         }

         if (!net.minecraftforge.client.settings.KeyModifier.isKeyCodeModifier(this.f_97514_.getKey()))
         this.f_97514_ = null;
         this.f_97515_ = Util.m_137550_();
         KeyMapping.m_90854_();
         return true;
      } else {
         return super.m_7933_(p_97526_, p_97527_, p_97528_);
      }
   }

   public void m_6305_(PoseStack p_97530_, int p_97531_, int p_97532_, float p_97533_) {
      this.m_7333_(p_97530_);
      this.f_97516_.m_6305_(p_97530_, p_97531_, p_97532_, p_97533_);
      m_93215_(p_97530_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 16777215);
      boolean flag = false;

      for(KeyMapping keymapping : this.f_96282_.f_92059_) {
         if (!keymapping.m_90864_()) {
            flag = true;
            break;
         }
      }

      this.f_97517_.f_93623_ = flag;
      super.m_6305_(p_97530_, p_97531_, p_97532_, p_97533_);
   }
}
