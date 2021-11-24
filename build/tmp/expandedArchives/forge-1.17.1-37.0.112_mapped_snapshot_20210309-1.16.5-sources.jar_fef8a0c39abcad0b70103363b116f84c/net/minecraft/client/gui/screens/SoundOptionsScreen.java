package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.VolumeSlider;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoundOptionsScreen extends OptionsSubScreen {
   public SoundOptionsScreen(Screen p_96702_, Options p_96703_) {
      super(p_96702_, p_96703_, new TranslatableComponent("options.sounds.title"));
   }

   protected void m_7856_() {
      int i = 0;
      this.m_142416_(new VolumeSlider(this.f_96541_, this.f_96543_ / 2 - 155 + i % 2 * 160, this.f_96544_ / 6 - 12 + 24 * (i >> 1), SoundSource.MASTER, 310));
      i = i + 2;

      for(SoundSource soundsource : SoundSource.values()) {
         if (soundsource != SoundSource.MASTER) {
            this.m_142416_(new VolumeSlider(this.f_96541_, this.f_96543_ / 2 - 155 + i % 2 * 160, this.f_96544_ / 6 - 12 + 24 * (i >> 1), soundsource, 150));
            ++i;
         }
      }

      int j = this.f_96543_ / 2 - 75;
      int k = this.f_96544_ / 6 - 12;
      ++i;
      this.m_142416_(Option.f_91644_.m_7496_(this.f_96282_, j, k + 24 * (i >> 1), 150));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 168, 200, 20, CommonComponents.f_130655_, (p_96713_) -> {
         this.f_96541_.m_91152_(this.f_96281_);
      }));
   }

   public void m_6305_(PoseStack p_96705_, int p_96706_, int p_96707_, float p_96708_) {
      this.m_7333_(p_96705_);
      m_93215_(p_96705_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      super.m_6305_(p_96705_, p_96706_, p_96707_, p_96708_);
   }
}