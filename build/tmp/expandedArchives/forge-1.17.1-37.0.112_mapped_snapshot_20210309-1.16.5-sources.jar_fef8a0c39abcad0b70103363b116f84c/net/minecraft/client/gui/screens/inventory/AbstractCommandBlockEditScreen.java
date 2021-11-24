package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractCommandBlockEditScreen extends Screen {
   private static final Component f_97652_ = new TranslatableComponent("advMode.setCommand");
   private static final Component f_97653_ = new TranslatableComponent("advMode.command");
   private static final Component f_97654_ = new TranslatableComponent("advMode.previousOutput");
   protected EditBox f_97646_;
   protected EditBox f_97647_;
   protected Button f_97648_;
   protected Button f_97649_;
   protected CycleButton<Boolean> f_97650_;
   CommandSuggestions f_97655_;

   public AbstractCommandBlockEditScreen() {
      super(NarratorChatListener.f_93310_);
   }

   public void m_96624_() {
      this.f_97646_.m_94120_();
   }

   abstract BaseCommandBlock m_6556_();

   abstract int m_7821_();

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_97648_ = this.m_142416_(new Button(this.f_96543_ / 2 - 4 - 150, this.f_96544_ / 4 + 120 + 12, 150, 20, CommonComponents.f_130655_, (p_97691_) -> {
         this.m_97695_();
      }));
      this.f_97649_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ / 4 + 120 + 12, 150, 20, CommonComponents.f_130656_, (p_97687_) -> {
         this.m_7379_();
      }));
      boolean flag = this.m_6556_().m_45440_();
      this.f_97650_ = this.m_142416_(CycleButton.m_168896_(new TextComponent("O"), new TextComponent("X")).m_168948_(flag).m_168929_().m_168936_(this.f_96543_ / 2 + 150 - 20, this.m_7821_(), 20, 20, new TranslatableComponent("advMode.trackOutput"), (p_169596_, p_169597_) -> {
         BaseCommandBlock basecommandblock = this.m_6556_();
         basecommandblock.m_45428_(p_169597_);
         this.m_169598_(p_169597_);
      }));
      this.f_97646_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 150, 50, 300, 20, new TranslatableComponent("advMode.command")) {
         protected MutableComponent m_5646_() {
            return super.m_5646_().m_130946_(AbstractCommandBlockEditScreen.this.f_97655_.m_93924_());
         }
      };
      this.f_97646_.m_94199_(32500);
      this.f_97646_.m_94151_(this::m_97688_);
      this.m_7787_(this.f_97646_);
      this.f_97647_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 150, this.m_7821_(), 276, 20, new TranslatableComponent("advMode.previousOutput"));
      this.f_97647_.m_94199_(32500);
      this.f_97647_.m_94186_(false);
      this.f_97647_.m_94144_("-");
      this.m_7787_(this.f_97647_);
      this.m_94718_(this.f_97646_);
      this.f_97646_.m_94178_(true);
      this.f_97655_ = new CommandSuggestions(this.f_96541_, this, this.f_97646_, this.f_96547_, true, true, 0, 7, false, Integer.MIN_VALUE);
      this.f_97655_.m_93922_(true);
      this.f_97655_.m_93881_();
      this.m_169598_(flag);
   }

   public void m_6574_(Minecraft p_97677_, int p_97678_, int p_97679_) {
      String s = this.f_97646_.m_94155_();
      this.m_6575_(p_97677_, p_97678_, p_97679_);
      this.f_97646_.m_94144_(s);
      this.f_97655_.m_93881_();
   }

   protected void m_169598_(boolean p_169599_) {
      this.f_97647_.m_94144_(p_169599_ ? this.m_6556_().m_45437_().getString() : "-");
   }

   protected void m_97695_() {
      BaseCommandBlock basecommandblock = this.m_6556_();
      this.m_6372_(basecommandblock);
      if (!basecommandblock.m_45440_()) {
         basecommandblock.m_45433_((Component)null);
      }

      this.f_96541_.m_91152_((Screen)null);
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   protected abstract void m_6372_(BaseCommandBlock p_97670_);

   private void m_97688_(String p_97689_) {
      this.f_97655_.m_93881_();
   }

   public boolean m_7933_(int p_97667_, int p_97668_, int p_97669_) {
      if (this.f_97655_.m_93888_(p_97667_, p_97668_, p_97669_)) {
         return true;
      } else if (super.m_7933_(p_97667_, p_97668_, p_97669_)) {
         return true;
      } else if (p_97667_ != 257 && p_97667_ != 335) {
         return false;
      } else {
         this.m_97695_();
         return true;
      }
   }

   public boolean m_6050_(double p_97659_, double p_97660_, double p_97661_) {
      return this.f_97655_.m_93882_(p_97661_) ? true : super.m_6050_(p_97659_, p_97660_, p_97661_);
   }

   public boolean m_6375_(double p_97663_, double p_97664_, int p_97665_) {
      return this.f_97655_.m_93884_(p_97663_, p_97664_, p_97665_) ? true : super.m_6375_(p_97663_, p_97664_, p_97665_);
   }

   public void m_6305_(PoseStack p_97672_, int p_97673_, int p_97674_, float p_97675_) {
      this.m_7333_(p_97672_);
      m_93215_(p_97672_, this.f_96547_, f_97652_, this.f_96543_ / 2, 20, 16777215);
      m_93243_(p_97672_, this.f_96547_, f_97653_, this.f_96543_ / 2 - 150, 40, 10526880);
      this.f_97646_.m_6305_(p_97672_, p_97673_, p_97674_, p_97675_);
      int i = 75;
      if (!this.f_97647_.m_94155_().isEmpty()) {
         i = i + (5 * 9 + 1 + this.m_7821_() - 135);
         m_93243_(p_97672_, this.f_96547_, f_97654_, this.f_96543_ / 2 - 150, i + 4, 10526880);
         this.f_97647_.m_6305_(p_97672_, p_97673_, p_97674_, p_97675_);
      }

      super.m_6305_(p_97672_, p_97673_, p_97674_, p_97675_);
      this.f_97655_.m_93900_(p_97672_, p_97673_, p_97674_);
   }
}