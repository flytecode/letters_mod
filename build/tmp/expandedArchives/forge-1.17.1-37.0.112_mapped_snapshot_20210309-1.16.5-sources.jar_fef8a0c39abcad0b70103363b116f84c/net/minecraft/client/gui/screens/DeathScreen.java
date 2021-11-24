package net.minecraft.client.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeathScreen extends Screen {
   private int f_95906_;
   private final Component f_95907_;
   private final boolean f_95908_;
   private Component f_95909_;
   private final List<Button> f_169295_ = Lists.newArrayList();

   public DeathScreen(@Nullable Component p_95911_, boolean p_95912_) {
      super(new TranslatableComponent(p_95912_ ? "deathScreen.title.hardcore" : "deathScreen.title"));
      this.f_95907_ = p_95911_;
      this.f_95908_ = p_95912_;
   }

   protected void m_7856_() {
      this.f_95906_ = 0;
      this.f_169295_.clear();
      this.f_169295_.add(this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 72, 200, 20, this.f_95908_ ? new TranslatableComponent("deathScreen.spectate") : new TranslatableComponent("deathScreen.respawn"), (p_95930_) -> {
         this.f_96541_.f_91074_.m_7583_();
         this.f_96541_.m_91152_((Screen)null);
      })));
      this.f_169295_.add(this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 96, 200, 20, new TranslatableComponent("deathScreen.titleScreen"), (p_95925_) -> {
         if (this.f_95908_) {
            m_95931_(true);
            this.m_95934_();
         } else {
            ConfirmScreen confirmscreen = new ConfirmScreen(this::m_95931_, new TranslatableComponent("deathScreen.quit.confirm"), TextComponent.f_131282_, new TranslatableComponent("deathScreen.titleScreen"), new TranslatableComponent("deathScreen.respawn"));
            this.f_96541_.m_91152_(confirmscreen);
            confirmscreen.m_95663_(20);
         }
      })));

      for(Button button : this.f_169295_) {
         button.f_93623_ = false;
      }

      this.f_95909_ = (new TranslatableComponent("deathScreen.score")).m_130946_(": ").m_7220_((new TextComponent(Integer.toString(this.f_96541_.f_91074_.m_36344_()))).m_130940_(ChatFormatting.YELLOW));
   }

   public boolean m_6913_() {
      return false;
   }

   private void m_95931_(boolean p_95932_) {
      if (p_95932_) {
         this.m_95934_();
      } else {
         this.f_96541_.f_91074_.m_7583_();
         this.f_96541_.m_91152_((Screen)null);
      }

   }

   private void m_95934_() {
      if (this.f_96541_.f_91073_ != null) {
         this.f_96541_.f_91073_.m_7462_();
      }

      this.f_96541_.m_91320_(new GenericDirtMessageScreen(new TranslatableComponent("menu.savingLevel")));
      this.f_96541_.m_91152_(new TitleScreen());
   }

   public void m_6305_(PoseStack p_95920_, int p_95921_, int p_95922_, float p_95923_) {
      this.m_93179_(p_95920_, 0, 0, this.f_96543_, this.f_96544_, 1615855616, -1602211792);
      p_95920_.m_85836_();
      p_95920_.m_85841_(2.0F, 2.0F, 2.0F);
      m_93215_(p_95920_, this.f_96547_, this.f_96539_, this.f_96543_ / 2 / 2, 30, 16777215);
      p_95920_.m_85849_();
      if (this.f_95907_ != null) {
         m_93215_(p_95920_, this.f_96547_, this.f_95907_, this.f_96543_ / 2, 85, 16777215);
      }

      m_93215_(p_95920_, this.f_96547_, this.f_95909_, this.f_96543_ / 2, 100, 16777215);
      if (this.f_95907_ != null && p_95922_ > 85 && p_95922_ < 85 + 9) {
         Style style = this.m_95917_(p_95921_);
         this.m_96570_(p_95920_, style, p_95921_, p_95922_);
      }

      super.m_6305_(p_95920_, p_95921_, p_95922_, p_95923_);
   }

   @Nullable
   private Style m_95917_(int p_95918_) {
      if (this.f_95907_ == null) {
         return null;
      } else {
         int i = this.f_96541_.f_91062_.m_92852_(this.f_95907_);
         int j = this.f_96543_ / 2 - i / 2;
         int k = this.f_96543_ / 2 + i / 2;
         return p_95918_ >= j && p_95918_ <= k ? this.f_96541_.f_91062_.m_92865_().m_92386_(this.f_95907_, p_95918_ - j) : null;
      }
   }

   public boolean m_6375_(double p_95914_, double p_95915_, int p_95916_) {
      if (this.f_95907_ != null && p_95915_ > 85.0D && p_95915_ < (double)(85 + 9)) {
         Style style = this.m_95917_((int)p_95914_);
         if (style != null && style.m_131182_() != null && style.m_131182_().m_130622_() == ClickEvent.Action.OPEN_URL) {
            this.m_5561_(style);
            return false;
         }
      }

      return super.m_6375_(p_95914_, p_95915_, p_95916_);
   }

   public boolean m_7043_() {
      return false;
   }

   public void m_96624_() {
      super.m_96624_();
      ++this.f_95906_;
      if (this.f_95906_ == 20) {
         for(Button button : this.f_169295_) {
            button.f_93623_ = true;
         }
      }

   }
}
