package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.exception.RealmsServiceException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsSubscriptionInfoScreen extends RealmsScreen {
   static final Logger f_89963_ = LogManager.getLogger();
   private static final Component f_89964_ = new TranslatableComponent("mco.configure.world.subscription.title");
   private static final Component f_89965_ = new TranslatableComponent("mco.configure.world.subscription.start");
   private static final Component f_89966_ = new TranslatableComponent("mco.configure.world.subscription.timeleft");
   private static final Component f_89967_ = new TranslatableComponent("mco.configure.world.subscription.recurring.daysleft");
   private static final Component f_89968_ = new TranslatableComponent("mco.configure.world.subscription.expired");
   private static final Component f_89969_ = new TranslatableComponent("mco.configure.world.subscription.less_than_a_day");
   private static final Component f_89970_ = new TranslatableComponent("mco.configure.world.subscription.month");
   private static final Component f_89971_ = new TranslatableComponent("mco.configure.world.subscription.months");
   private static final Component f_89972_ = new TranslatableComponent("mco.configure.world.subscription.day");
   private static final Component f_89973_ = new TranslatableComponent("mco.configure.world.subscription.days");
   private static final Component f_182537_ = new TranslatableComponent("mco.configure.world.subscription.unknown");
   private final Screen f_89974_;
   final RealmsServer f_89975_;
   final Screen f_89976_;
   private Component f_89960_ = f_182537_;
   private Component f_89961_ = f_182537_;
   @Nullable
   private Subscription.SubscriptionType f_89962_;
   private static final String f_167548_ = "https://aka.ms/ExtendJavaRealms";

   public RealmsSubscriptionInfoScreen(Screen p_89979_, RealmsServer p_89980_, Screen p_89981_) {
      super(NarratorChatListener.f_93310_);
      this.f_89974_ = p_89979_;
      this.f_89975_ = p_89980_;
      this.f_89976_ = p_89981_;
   }

   public void m_7856_() {
      this.m_89989_(this.f_89975_.f_87473_);
      this.f_96541_.f_91068_.m_90926_(true);
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, m_120774_(6), 200, 20, new TranslatableComponent("mco.configure.world.subscription.extend"), (p_90010_) -> {
         String s = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + this.f_89975_.f_87474_ + "&profileId=" + this.f_96541_.m_91094_().m_92545_();
         this.f_96541_.f_91068_.m_90911_(s);
         Util.m_137581_().m_137646_(s);
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, m_120774_(12), 200, 20, CommonComponents.f_130660_, (p_90006_) -> {
         this.f_96541_.m_91152_(this.f_89974_);
      }));
      if (this.f_89975_.f_87482_) {
         this.m_142416_(new Button(this.f_96543_ / 2 - 100, m_120774_(10), 200, 20, new TranslatableComponent("mco.configure.world.delete.button"), (p_89999_) -> {
            Component component = new TranslatableComponent("mco.configure.world.delete.question.line1");
            Component component1 = new TranslatableComponent("mco.configure.world.delete.question.line2");
            this.f_96541_.m_91152_(new RealmsLongConfirmationScreen(this::m_90011_, RealmsLongConfirmationScreen.Type.Warning, component, component1, true));
         }));
      }

   }

   public Component m_142562_() {
      return CommonComponents.m_178396_(f_89964_, f_89965_, this.f_89961_, f_89966_, this.f_89960_);
   }

   private void m_90011_(boolean p_90012_) {
      if (p_90012_) {
         (new Thread("Realms-delete-realm") {
            public void run() {
               try {
                  RealmsClient realmsclient = RealmsClient.m_87169_();
                  realmsclient.m_87254_(RealmsSubscriptionInfoScreen.this.f_89975_.f_87473_);
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsSubscriptionInfoScreen.f_89963_.error("Couldn't delete world");
                  RealmsSubscriptionInfoScreen.f_89963_.error(realmsserviceexception);
               }

               RealmsSubscriptionInfoScreen.this.f_96541_.execute(() -> {
                  RealmsSubscriptionInfoScreen.this.f_96541_.m_91152_(RealmsSubscriptionInfoScreen.this.f_89976_);
               });
            }
         }).start();
      }

      this.f_96541_.m_91152_(this);
   }

   private void m_89989_(long p_89990_) {
      RealmsClient realmsclient = RealmsClient.m_87169_();

      try {
         Subscription subscription = realmsclient.m_87248_(p_89990_);
         this.f_89960_ = this.m_89983_(subscription.f_87667_);
         this.f_89961_ = m_182538_(subscription.f_87666_);
         this.f_89962_ = subscription.f_87668_;
      } catch (RealmsServiceException realmsserviceexception) {
         f_89963_.error("Couldn't get subscription");
         this.f_96541_.m_91152_(new RealmsGenericErrorScreen(realmsserviceexception, this.f_89974_));
      }

   }

   private static Component m_182538_(long p_182539_) {
      Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
      calendar.setTimeInMillis(p_182539_);
      return new TextComponent(DateFormat.getDateTimeInstance().format(calendar.getTime()));
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public boolean m_7933_(int p_89986_, int p_89987_, int p_89988_) {
      if (p_89986_ == 256) {
         this.f_96541_.m_91152_(this.f_89974_);
         return true;
      } else {
         return super.m_7933_(p_89986_, p_89987_, p_89988_);
      }
   }

   public void m_6305_(PoseStack p_89992_, int p_89993_, int p_89994_, float p_89995_) {
      this.m_7333_(p_89992_);
      int i = this.f_96543_ / 2 - 100;
      m_93215_(p_89992_, this.f_96547_, f_89964_, this.f_96543_ / 2, 17, 16777215);
      this.f_96547_.m_92889_(p_89992_, f_89965_, (float)i, (float)m_120774_(0), 10526880);
      this.f_96547_.m_92889_(p_89992_, this.f_89961_, (float)i, (float)m_120774_(1), 16777215);
      if (this.f_89962_ == Subscription.SubscriptionType.NORMAL) {
         this.f_96547_.m_92889_(p_89992_, f_89966_, (float)i, (float)m_120774_(3), 10526880);
      } else if (this.f_89962_ == Subscription.SubscriptionType.RECURRING) {
         this.f_96547_.m_92889_(p_89992_, f_89967_, (float)i, (float)m_120774_(3), 10526880);
      }

      this.f_96547_.m_92889_(p_89992_, this.f_89960_, (float)i, (float)m_120774_(4), 16777215);
      super.m_6305_(p_89992_, p_89993_, p_89994_, p_89995_);
   }

   private Component m_89983_(int p_89984_) {
      if (p_89984_ < 0 && this.f_89975_.f_87482_) {
         return f_89968_;
      } else if (p_89984_ <= 1) {
         return f_89969_;
      } else {
         int i = p_89984_ / 30;
         int j = p_89984_ % 30;
         MutableComponent mutablecomponent = new TextComponent("");
         if (i > 0) {
            mutablecomponent.m_130946_(Integer.toString(i)).m_130946_(" ");
            if (i == 1) {
               mutablecomponent.m_7220_(f_89970_);
            } else {
               mutablecomponent.m_7220_(f_89971_);
            }
         }

         if (j > 0) {
            if (i > 0) {
               mutablecomponent.m_130946_(", ");
            }

            mutablecomponent.m_130946_(Integer.toString(j)).m_130946_(" ");
            if (j == 1) {
               mutablecomponent.m_7220_(f_89972_);
            } else {
               mutablecomponent.m_7220_(f_89973_);
            }
         }

         return mutablecomponent;
      }
   }
}