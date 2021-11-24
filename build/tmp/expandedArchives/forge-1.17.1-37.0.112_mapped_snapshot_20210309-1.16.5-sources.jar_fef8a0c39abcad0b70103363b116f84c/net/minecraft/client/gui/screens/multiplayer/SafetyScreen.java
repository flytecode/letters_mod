package net.minecraft.client.gui.screens.multiplayer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SafetyScreen extends Screen {
   private final Screen f_99734_;
   private static final Component f_99735_ = (new TranslatableComponent("multiplayerWarning.header")).m_130940_(ChatFormatting.BOLD);
   private static final Component f_99736_ = new TranslatableComponent("multiplayerWarning.message");
   private static final Component f_99737_ = new TranslatableComponent("multiplayerWarning.check");
   private static final Component f_99738_ = f_99735_.m_6881_().m_130946_("\n").m_7220_(f_99736_);
   private Checkbox f_99739_;
   private MultiLineLabel f_99740_ = MultiLineLabel.f_94331_;

   public SafetyScreen(Screen p_99743_) {
      super(NarratorChatListener.f_93310_);
      this.f_99734_ = p_99743_;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_99740_ = MultiLineLabel.m_94341_(this.f_96547_, f_99736_, this.f_96543_ - 50);
      int i = (this.f_99740_.m_5770_() + 1) * 9 * 2;
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, 100 + i, 150, 20, CommonComponents.f_130659_, (p_99754_) -> {
         if (this.f_99739_.m_93840_()) {
            this.f_96541_.f_91066_.f_92083_ = true;
            this.f_96541_.f_91066_.m_92169_();
         }

         this.f_96541_.m_91152_(new JoinMultiplayerScreen(this.f_99734_));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, 100 + i, 150, 20, CommonComponents.f_130660_, (p_99750_) -> {
         this.f_96541_.m_91152_(this.f_99734_);
      }));
      this.f_99739_ = new Checkbox(this.f_96543_ / 2 - 155 + 80, 76 + i, 150, 20, f_99737_, false);
      this.m_142416_(this.f_99739_);
   }

   public Component m_142562_() {
      return f_99738_;
   }

   public void m_6305_(PoseStack p_99745_, int p_99746_, int p_99747_, float p_99748_) {
      this.m_96626_(0);
      m_93243_(p_99745_, this.f_96547_, f_99735_, 25, 30, 16777215);
      this.f_99740_.m_6516_(p_99745_, 25, 70, 9 * 2, 16777215);
      super.m_6305_(p_99745_, p_99746_, p_99747_, p_99748_);
   }
}