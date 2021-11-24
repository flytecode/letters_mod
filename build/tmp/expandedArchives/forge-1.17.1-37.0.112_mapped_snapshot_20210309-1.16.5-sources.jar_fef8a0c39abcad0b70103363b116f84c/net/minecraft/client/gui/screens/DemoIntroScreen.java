package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DemoIntroScreen extends Screen {
   private static final ResourceLocation f_95935_ = new ResourceLocation("textures/gui/demo_background.png");
   private MultiLineLabel f_95936_ = MultiLineLabel.f_94331_;
   private MultiLineLabel f_95937_ = MultiLineLabel.f_94331_;

   public DemoIntroScreen() {
      super(new TranslatableComponent("demo.help.title"));
   }

   protected void m_7856_() {
      int i = -16;
      this.m_142416_(new Button(this.f_96543_ / 2 - 116, this.f_96544_ / 2 + 62 + -16, 114, 20, new TranslatableComponent("demo.help.buy"), (p_95951_) -> {
         p_95951_.f_93623_ = false;
         Util.m_137581_().m_137646_("http://www.minecraft.net/store?source=demo");
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 2, this.f_96544_ / 2 + 62 + -16, 114, 20, new TranslatableComponent("demo.help.later"), (p_95948_) -> {
         this.f_96541_.m_91152_((Screen)null);
         this.f_96541_.f_91067_.m_91601_();
      }));
      Options options = this.f_96541_.f_91066_;
      this.f_95936_ = MultiLineLabel.m_94350_(this.f_96547_, new TranslatableComponent("demo.help.movementShort", options.f_92085_.m_90863_(), options.f_92086_.m_90863_(), options.f_92087_.m_90863_(), options.f_92088_.m_90863_()), new TranslatableComponent("demo.help.movementMouse"), new TranslatableComponent("demo.help.jump", options.f_92089_.m_90863_()), new TranslatableComponent("demo.help.inventory", options.f_92092_.m_90863_()));
      this.f_95937_ = MultiLineLabel.m_94341_(this.f_96547_, new TranslatableComponent("demo.help.fullWrapped"), 218);
   }

   public void m_7333_(PoseStack p_95941_) {
      super.m_7333_(p_95941_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_95935_);
      int i = (this.f_96543_ - 248) / 2;
      int j = (this.f_96544_ - 166) / 2;
      this.m_93228_(p_95941_, i, j, 0, 0, 248, 166);
   }

   public void m_6305_(PoseStack p_95943_, int p_95944_, int p_95945_, float p_95946_) {
      this.m_7333_(p_95943_);
      int i = (this.f_96543_ - 248) / 2 + 10;
      int j = (this.f_96544_ - 166) / 2 + 8;
      this.f_96547_.m_92889_(p_95943_, this.f_96539_, (float)i, (float)j, 2039583);
      j = this.f_95936_.m_6508_(p_95943_, i, j + 12, 12, 5197647);
      this.f_95937_.m_6508_(p_95943_, i, j + 20, 9, 2039583);
      super.m_6305_(p_95943_, p_95944_, p_95945_, p_95946_);
   }
}