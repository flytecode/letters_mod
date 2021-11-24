package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OutOfMemoryScreen extends Screen {
   public OutOfMemoryScreen() {
      super(new TextComponent("Out of memory!"));
   }

   protected void m_7856_() {
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 4 + 120 + 12, 150, 20, new TranslatableComponent("gui.toTitle"), (p_96304_) -> {
         this.f_96541_.m_91152_(new TitleScreen());
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, this.f_96544_ / 4 + 120 + 12, 150, 20, new TranslatableComponent("menu.quit"), (p_96300_) -> {
         this.f_96541_.m_91395_();
      }));
   }

   public boolean m_6913_() {
      return false;
   }

   public void m_6305_(PoseStack p_96295_, int p_96296_, int p_96297_, float p_96298_) {
      this.m_7333_(p_96295_);
      m_93215_(p_96295_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, this.f_96544_ / 4 - 60 + 20, 16777215);
      m_93236_(p_96295_, this.f_96547_, "Minecraft has run out of memory.", this.f_96543_ / 2 - 140, this.f_96544_ / 4 - 60 + 60 + 0, 10526880);
      m_93236_(p_96295_, this.f_96547_, "This could be caused by a bug in the game or by the", this.f_96543_ / 2 - 140, this.f_96544_ / 4 - 60 + 60 + 18, 10526880);
      m_93236_(p_96295_, this.f_96547_, "Java Virtual Machine not being allocated enough", this.f_96543_ / 2 - 140, this.f_96544_ / 4 - 60 + 60 + 27, 10526880);
      m_93236_(p_96295_, this.f_96547_, "memory.", this.f_96543_ / 2 - 140, this.f_96544_ / 4 - 60 + 60 + 36, 10526880);
      m_93236_(p_96295_, this.f_96547_, "To prevent level corruption, the current game has quit.", this.f_96543_ / 2 - 140, this.f_96544_ / 4 - 60 + 60 + 54, 10526880);
      m_93236_(p_96295_, this.f_96547_, "We've tried to free up enough memory to let you go back to", this.f_96543_ / 2 - 140, this.f_96544_ / 4 - 60 + 60 + 63, 10526880);
      m_93236_(p_96295_, this.f_96547_, "the main menu and back to playing, but this may not have worked.", this.f_96543_ / 2 - 140, this.f_96544_ / 4 - 60 + 60 + 72, 10526880);
      m_93236_(p_96295_, this.f_96547_, "Please restart the game if you see this message again.", this.f_96543_ / 2 - 140, this.f_96544_ / 4 - 60 + 60 + 81, 10526880);
      super.m_6305_(p_96295_, p_96296_, p_96297_, p_96298_);
   }
}