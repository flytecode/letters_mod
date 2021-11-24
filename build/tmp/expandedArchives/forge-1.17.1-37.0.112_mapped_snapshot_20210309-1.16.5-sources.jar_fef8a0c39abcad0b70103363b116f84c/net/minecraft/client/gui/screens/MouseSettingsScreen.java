package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MouseSettingsScreen extends OptionsSubScreen {
   private OptionsList f_96218_;
   private static final Option[] f_96219_ = new Option[]{Option.f_91677_, Option.f_91641_, Option.f_91673_, Option.f_91637_, Option.f_91648_};

   public MouseSettingsScreen(Screen p_96222_, Options p_96223_) {
      super(p_96222_, p_96223_, new TranslatableComponent("options.mouse_settings.title"));
   }

   protected void m_7856_() {
      this.f_96218_ = new OptionsList(this.f_96541_, this.f_96543_, this.f_96544_, 32, this.f_96544_ - 32, 25);
      if (InputConstants.m_84826_()) {
         this.f_96218_.m_94476_(Stream.concat(Arrays.stream(f_96219_), Stream.of(Option.f_91674_)).toArray((p_96225_) -> {
            return new Option[p_96225_];
         }));
      } else {
         this.f_96218_.m_94476_(f_96219_);
      }

      this.m_7787_(this.f_96218_);
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 27, 200, 20, CommonComponents.f_130655_, (p_96232_) -> {
         this.f_96282_.m_92169_();
         this.f_96541_.m_91152_(this.f_96281_);
      }));
   }

   public void m_6305_(PoseStack p_96227_, int p_96228_, int p_96229_, float p_96230_) {
      this.m_7333_(p_96227_);
      this.f_96218_.m_6305_(p_96227_, p_96228_, p_96229_, p_96230_);
      m_93215_(p_96227_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 5, 16777215);
      super.m_6305_(p_96227_, p_96228_, p_96229_, p_96230_);
   }
}