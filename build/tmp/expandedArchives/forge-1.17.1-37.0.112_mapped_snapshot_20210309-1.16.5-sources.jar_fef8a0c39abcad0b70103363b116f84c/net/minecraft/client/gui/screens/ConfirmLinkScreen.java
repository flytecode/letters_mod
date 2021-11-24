package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfirmLinkScreen extends ConfirmScreen {
   private static final Component f_169239_ = new TranslatableComponent("chat.copy");
   private static final Component f_169240_ = new TranslatableComponent("chat.link.warning");
   private final String f_95628_;
   private final boolean f_95629_;

   public ConfirmLinkScreen(BooleanConsumer p_95631_, String p_95632_, boolean p_95633_) {
      super(p_95631_, new TranslatableComponent(p_95633_ ? "chat.link.confirmTrusted" : "chat.link.confirm"), new TextComponent(p_95632_));
      this.f_95647_ = (Component)(p_95633_ ? new TranslatableComponent("chat.link.open") : CommonComponents.f_130657_);
      this.f_95648_ = p_95633_ ? CommonComponents.f_130656_ : CommonComponents.f_130658_;
      this.f_95629_ = !p_95633_;
      this.f_95628_ = p_95632_;
   }

   protected void m_141972_(int p_169243_) {
      this.m_142416_(new Button(this.f_96543_ / 2 - 50 - 105, this.f_96544_ / 6 + 96, 100, 20, this.f_95647_, (p_169249_) -> {
         this.f_95649_.accept(true);
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 50, this.f_96544_ / 6 + 96, 100, 20, f_169239_, (p_169247_) -> {
         this.m_95646_();
         this.f_95649_.accept(false);
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 50 + 105, this.f_96544_ / 6 + 96, 100, 20, this.f_95648_, (p_169245_) -> {
         this.f_95649_.accept(false);
      }));
   }

   public void m_95646_() {
      this.f_96541_.f_91068_.m_90911_(this.f_95628_);
   }

   public void m_6305_(PoseStack p_95635_, int p_95636_, int p_95637_, float p_95638_) {
      super.m_6305_(p_95635_, p_95636_, p_95637_, p_95638_);
      if (this.f_95629_) {
         m_93215_(p_95635_, this.f_96547_, f_169240_, this.f_96543_ / 2, 110, 16764108);
      }

   }
}