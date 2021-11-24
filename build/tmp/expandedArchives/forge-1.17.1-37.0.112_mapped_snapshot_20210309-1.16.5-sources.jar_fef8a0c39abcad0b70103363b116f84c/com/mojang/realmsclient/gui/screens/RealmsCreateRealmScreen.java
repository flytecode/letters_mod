package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.util.task.WorldCreationTask;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsCreateRealmScreen extends RealmsScreen {
   private static final Component f_88564_ = new TranslatableComponent("mco.configure.world.name");
   private static final Component f_88565_ = new TranslatableComponent("mco.configure.world.description");
   private final RealmsServer f_88566_;
   private final RealmsMainScreen f_88567_;
   private EditBox f_88568_;
   private EditBox f_88569_;
   private Button f_88570_;

   public RealmsCreateRealmScreen(RealmsServer p_88574_, RealmsMainScreen p_88575_) {
      super(new TranslatableComponent("mco.selectServer.create"));
      this.f_88566_ = p_88574_;
      this.f_88567_ = p_88575_;
   }

   public void m_96624_() {
      if (this.f_88568_ != null) {
         this.f_88568_.m_94120_();
      }

      if (this.f_88569_ != null) {
         this.f_88569_.m_94120_();
      }

   }

   public void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_88570_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 17, 97, 20, new TranslatableComponent("mco.create.world"), (p_88592_) -> {
         this.m_88595_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 4 + 120 + 17, 95, 20, CommonComponents.f_130656_, (p_88589_) -> {
         this.f_96541_.m_91152_(this.f_88567_);
      }));
      this.f_88570_.f_93623_ = false;
      this.f_88568_ = new EditBox(this.f_96541_.f_91062_, this.f_96543_ / 2 - 100, 65, 200, 20, (EditBox)null, new TranslatableComponent("mco.configure.world.name"));
      this.m_7787_(this.f_88568_);
      this.m_94718_(this.f_88568_);
      this.f_88569_ = new EditBox(this.f_96541_.f_91062_, this.f_96543_ / 2 - 100, 115, 200, 20, (EditBox)null, new TranslatableComponent("mco.configure.world.description"));
      this.m_7787_(this.f_88569_);
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public boolean m_5534_(char p_88577_, int p_88578_) {
      boolean flag = super.m_5534_(p_88577_, p_88578_);
      this.f_88570_.f_93623_ = this.m_88596_();
      return flag;
   }

   public boolean m_7933_(int p_88580_, int p_88581_, int p_88582_) {
      if (p_88580_ == 256) {
         this.f_96541_.m_91152_(this.f_88567_);
         return true;
      } else {
         boolean flag = super.m_7933_(p_88580_, p_88581_, p_88582_);
         this.f_88570_.f_93623_ = this.m_88596_();
         return flag;
      }
   }

   private void m_88595_() {
      if (this.m_88596_()) {
         RealmsResetWorldScreen realmsresetworldscreen = new RealmsResetWorldScreen(this.f_88567_, this.f_88566_, new TranslatableComponent("mco.selectServer.create"), new TranslatableComponent("mco.create.world.subtitle"), 10526880, new TranslatableComponent("mco.create.world.skip"), () -> {
            this.f_96541_.execute(() -> {
               this.f_96541_.m_91152_(this.f_88567_.m_86660_());
            });
         }, () -> {
            this.f_96541_.m_91152_(this.f_88567_.m_86660_());
         });
         realmsresetworldscreen.m_89389_(new TranslatableComponent("mco.create.world.reset.title"));
         this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_88567_, new WorldCreationTask(this.f_88566_.f_87473_, this.f_88568_.m_94155_(), this.f_88569_.m_94155_(), realmsresetworldscreen)));
      }

   }

   private boolean m_88596_() {
      return !this.f_88568_.m_94155_().trim().isEmpty();
   }

   public void m_6305_(PoseStack p_88584_, int p_88585_, int p_88586_, float p_88587_) {
      this.m_7333_(p_88584_);
      m_93215_(p_88584_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 11, 16777215);
      this.f_96547_.m_92889_(p_88584_, f_88564_, (float)(this.f_96543_ / 2 - 100), 52.0F, 10526880);
      this.f_96547_.m_92889_(p_88584_, f_88565_, (float)(this.f_96543_ / 2 - 100), 102.0F, 10526880);
      if (this.f_88568_ != null) {
         this.f_88568_.m_6305_(p_88584_, p_88585_, p_88586_, p_88587_);
      }

      if (this.f_88569_ != null) {
         this.f_88569_.m_6305_(p_88584_, p_88585_, p_88586_, p_88587_);
      }

      super.m_6305_(p_88584_, p_88585_, p_88586_, p_88587_);
   }
}