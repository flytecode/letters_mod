package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsInviteScreen extends RealmsScreen {
   private static final Logger f_88693_ = LogManager.getLogger();
   private static final Component f_88694_ = new TranslatableComponent("mco.configure.world.invite.profile.name");
   private static final Component f_88695_ = new TranslatableComponent("mco.configure.world.players.error");
   private EditBox f_88696_;
   private final RealmsServer f_88697_;
   private final RealmsConfigureWorldScreen f_88698_;
   private final Screen f_88699_;
   @Nullable
   private Component f_88700_;

   public RealmsInviteScreen(RealmsConfigureWorldScreen p_88703_, Screen p_88704_, RealmsServer p_88705_) {
      super(NarratorChatListener.f_93310_);
      this.f_88698_ = p_88703_;
      this.f_88699_ = p_88704_;
      this.f_88697_ = p_88705_;
   }

   public void m_96624_() {
      this.f_88696_.m_94120_();
   }

   public void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_88696_ = new EditBox(this.f_96541_.f_91062_, this.f_96543_ / 2 - 100, m_120774_(2), 200, 20, (EditBox)null, new TranslatableComponent("mco.configure.world.invite.profile.name"));
      this.m_7787_(this.f_88696_);
      this.m_94718_(this.f_88696_);
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, m_120774_(10), 200, 20, new TranslatableComponent("mco.configure.world.buttons.invite"), (p_88721_) -> {
         this.m_88724_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, m_120774_(12), 200, 20, CommonComponents.f_130656_, (p_88716_) -> {
         this.f_96541_.m_91152_(this.f_88699_);
      }));
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   private void m_88724_() {
      RealmsClient realmsclient = RealmsClient.m_87169_();
      if (this.f_88696_.m_94155_() != null && !this.f_88696_.m_94155_().isEmpty()) {
         try {
            RealmsServer realmsserver = realmsclient.m_87212_(this.f_88697_.f_87473_, this.f_88696_.m_94155_().trim());
            if (realmsserver != null) {
               this.f_88697_.f_87480_ = realmsserver.f_87480_;
               this.f_96541_.m_91152_(new RealmsPlayerScreen(this.f_88698_, this.f_88697_));
            } else {
               this.m_88717_(f_88695_);
            }
         } catch (Exception exception) {
            f_88693_.error("Couldn't invite user");
            this.m_88717_(f_88695_);
         }

      } else {
         this.m_88717_(f_88695_);
      }
   }

   private void m_88717_(Component p_88718_) {
      this.f_88700_ = p_88718_;
      NarratorChatListener.f_93311_.m_168785_(p_88718_);
   }

   public boolean m_7933_(int p_88707_, int p_88708_, int p_88709_) {
      if (p_88707_ == 256) {
         this.f_96541_.m_91152_(this.f_88699_);
         return true;
      } else {
         return super.m_7933_(p_88707_, p_88708_, p_88709_);
      }
   }

   public void m_6305_(PoseStack p_88711_, int p_88712_, int p_88713_, float p_88714_) {
      this.m_7333_(p_88711_);
      this.f_96547_.m_92889_(p_88711_, f_88694_, (float)(this.f_96543_ / 2 - 100), (float)m_120774_(1), 10526880);
      if (this.f_88700_ != null) {
         m_93215_(p_88711_, this.f_96547_, this.f_88700_, this.f_96543_ / 2, m_120774_(5), 16711680);
      }

      this.f_88696_.m_6305_(p_88711_, p_88712_, p_88713_, p_88714_);
      super.m_6305_(p_88711_, p_88712_, p_88713_, p_88714_);
   }
}