package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkinCustomizationScreen extends OptionsSubScreen {
   public SkinCustomizationScreen(Screen p_96684_, Options p_96685_) {
      super(p_96684_, p_96685_, new TranslatableComponent("options.skinCustomisation.title"));
   }

   protected void m_7856_() {
      int i = 0;

      for(PlayerModelPart playermodelpart : PlayerModelPart.values()) {
         this.m_142416_(CycleButton.m_168916_(this.f_96282_.m_168416_(playermodelpart)).m_168936_(this.f_96543_ / 2 - 155 + i % 2 * 160, this.f_96544_ / 6 + 24 * (i >> 1), 150, 20, playermodelpart.m_36447_(), (p_169436_, p_169437_) -> {
            this.f_96282_.m_168418_(playermodelpart, p_169437_);
         }));
         ++i;
      }

      this.m_142416_(Option.f_91684_.m_7496_(this.f_96282_, this.f_96543_ / 2 - 155 + i % 2 * 160, this.f_96544_ / 6 + 24 * (i >> 1), 150));
      ++i;
      if (i % 2 == 1) {
         ++i;
      }

      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 24 * (i >> 1), 200, 20, CommonComponents.f_130655_, (p_96700_) -> {
         this.f_96541_.m_91152_(this.f_96281_);
      }));
   }

   public void m_6305_(PoseStack p_96692_, int p_96693_, int p_96694_, float p_96695_) {
      this.m_7333_(p_96692_);
      m_93215_(p_96692_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 16777215);
      super.m_6305_(p_96692_, p_96693_, p_96694_, p_96695_);
   }
}