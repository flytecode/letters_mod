package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.dto.Backup;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsBackupInfoScreen extends RealmsScreen {
   private static final Component f_167352_ = new TextComponent("UNKNOWN");
   private final Screen f_88044_;
   final Backup f_88045_;
   private RealmsBackupInfoScreen.BackupInfoList f_88046_;

   public RealmsBackupInfoScreen(Screen p_88048_, Backup p_88049_) {
      super(new TextComponent("Changes from last backup"));
      this.f_88044_ = p_88048_;
      this.f_88045_ = p_88049_;
   }

   public void m_96624_() {
   }

   public void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 24, 200, 20, CommonComponents.f_130660_, (p_88066_) -> {
         this.f_96541_.m_91152_(this.f_88044_);
      }));
      this.f_88046_ = new RealmsBackupInfoScreen.BackupInfoList(this.f_96541_);
      this.m_7787_(this.f_88046_);
      this.m_94725_(this.f_88046_);
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public boolean m_7933_(int p_88051_, int p_88052_, int p_88053_) {
      if (p_88051_ == 256) {
         this.f_96541_.m_91152_(this.f_88044_);
         return true;
      } else {
         return super.m_7933_(p_88051_, p_88052_, p_88053_);
      }
   }

   public void m_6305_(PoseStack p_88055_, int p_88056_, int p_88057_, float p_88058_) {
      this.m_7333_(p_88055_);
      this.f_88046_.m_6305_(p_88055_, p_88056_, p_88057_, p_88058_);
      m_93215_(p_88055_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 10, 16777215);
      super.m_6305_(p_88055_, p_88056_, p_88057_, p_88058_);
   }

   Component m_88067_(String p_88068_, String p_88069_) {
      String s = p_88068_.toLowerCase(Locale.ROOT);
      if (s.contains("game") && s.contains("mode")) {
         return this.m_88075_(p_88069_);
      } else {
         return (Component)(s.contains("game") && s.contains("difficulty") ? this.m_88073_(p_88069_) : new TextComponent(p_88069_));
      }
   }

   private Component m_88073_(String p_88074_) {
      try {
         return RealmsSlotOptionsScreen.f_89870_.get(Integer.parseInt(p_88074_)).m_19033_();
      } catch (Exception exception) {
         return f_167352_;
      }
   }

   private Component m_88075_(String p_88076_) {
      try {
         return RealmsSlotOptionsScreen.f_89871_.get(Integer.parseInt(p_88076_)).m_151500_();
      } catch (Exception exception) {
         return f_167352_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   class BackupInfoList extends ObjectSelectionList<RealmsBackupInfoScreen.BackupInfoListEntry> {
      public BackupInfoList(Minecraft p_88082_) {
         super(p_88082_, RealmsBackupInfoScreen.this.f_96543_, RealmsBackupInfoScreen.this.f_96544_, 32, RealmsBackupInfoScreen.this.f_96544_ - 64, 36);
         this.m_93471_(false);
         if (RealmsBackupInfoScreen.this.f_88045_.f_87393_ != null) {
            RealmsBackupInfoScreen.this.f_88045_.f_87393_.forEach((p_88084_, p_88085_) -> {
               this.m_7085_(RealmsBackupInfoScreen.this.new BackupInfoListEntry(p_88084_, p_88085_));
            });
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   class BackupInfoListEntry extends ObjectSelectionList.Entry<RealmsBackupInfoScreen.BackupInfoListEntry> {
      private final String f_88087_;
      private final String f_88088_;

      public BackupInfoListEntry(String p_88091_, String p_88092_) {
         this.f_88087_ = p_88091_;
         this.f_88088_ = p_88092_;
      }

      public void m_6311_(PoseStack p_88094_, int p_88095_, int p_88096_, int p_88097_, int p_88098_, int p_88099_, int p_88100_, int p_88101_, boolean p_88102_, float p_88103_) {
         Font font = RealmsBackupInfoScreen.this.f_96541_.f_91062_;
         GuiComponent.m_93236_(p_88094_, font, this.f_88087_, p_88097_, p_88096_, 10526880);
         GuiComponent.m_93243_(p_88094_, font, RealmsBackupInfoScreen.this.m_88067_(this.f_88087_, this.f_88088_), p_88097_, p_88096_ + 12, 16777215);
      }

      public Component m_142172_() {
         return new TranslatableComponent("narrator.select", this.f_88087_ + " " + this.f_88088_);
      }
   }
}