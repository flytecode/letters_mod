package com.mojang.realmsclient;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.realmsclient.client.Ping;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerPlayerList;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.dto.RegionPingResult;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import com.mojang.realmsclient.gui.screens.RealmsClientOutdatedScreen;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsCreateRealmScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsParentalConsentScreen;
import com.mojang.realmsclient.gui.screens.RealmsPendingInvitesScreen;
import com.mojang.realmsclient.util.RealmsPersistence;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.task.GetServerDetailsTask;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsMainScreen extends RealmsScreen {
   static final Logger f_86257_ = LogManager.getLogger();
   private static final ResourceLocation f_86300_ = new ResourceLocation("realms", "textures/gui/realms/on_icon.png");
   private static final ResourceLocation f_86301_ = new ResourceLocation("realms", "textures/gui/realms/off_icon.png");
   private static final ResourceLocation f_86302_ = new ResourceLocation("realms", "textures/gui/realms/expired_icon.png");
   private static final ResourceLocation f_86303_ = new ResourceLocation("realms", "textures/gui/realms/expires_soon_icon.png");
   private static final ResourceLocation f_86304_ = new ResourceLocation("realms", "textures/gui/realms/leave_icon.png");
   private static final ResourceLocation f_86305_ = new ResourceLocation("realms", "textures/gui/realms/invitation_icons.png");
   private static final ResourceLocation f_86306_ = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
   static final ResourceLocation f_86307_ = new ResourceLocation("realms", "textures/gui/realms/world_icon.png");
   private static final ResourceLocation f_86308_ = new ResourceLocation("realms", "textures/gui/title/realms.png");
   private static final ResourceLocation f_86309_ = new ResourceLocation("realms", "textures/gui/realms/configure_icon.png");
   private static final ResourceLocation f_86310_ = new ResourceLocation("realms", "textures/gui/realms/questionmark.png");
   private static final ResourceLocation f_86311_ = new ResourceLocation("realms", "textures/gui/realms/news_icon.png");
   private static final ResourceLocation f_86312_ = new ResourceLocation("realms", "textures/gui/realms/popup.png");
   private static final ResourceLocation f_86231_ = new ResourceLocation("realms", "textures/gui/realms/darken.png");
   static final ResourceLocation f_86232_ = new ResourceLocation("realms", "textures/gui/realms/cross_icon.png");
   private static final ResourceLocation f_86233_ = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
   static final ResourceLocation f_86234_ = new ResourceLocation("minecraft", "textures/gui/widgets.png");
   static final Component f_86235_ = new TranslatableComponent("mco.invites.nopending");
   static final Component f_86236_ = new TranslatableComponent("mco.invites.pending");
   static final List<Component> f_86237_ = ImmutableList.of(new TranslatableComponent("mco.trial.message.line1"), new TranslatableComponent("mco.trial.message.line2"));
   static final Component f_86238_ = new TranslatableComponent("mco.selectServer.uninitialized");
   static final Component f_86239_ = new TranslatableComponent("mco.selectServer.expiredList");
   static final Component f_86240_ = new TranslatableComponent("mco.selectServer.expiredRenew");
   static final Component f_86241_ = new TranslatableComponent("mco.selectServer.expiredTrial");
   static final Component f_86242_ = new TranslatableComponent("mco.selectServer.expiredSubscribe");
   static final Component f_86243_ = (new TranslatableComponent("mco.selectServer.minigame")).m_130946_(" ");
   private static final Component f_86244_ = new TranslatableComponent("mco.selectServer.popup");
   private static final Component f_86245_ = new TranslatableComponent("mco.selectServer.expired");
   private static final Component f_86246_ = new TranslatableComponent("mco.selectServer.expires.soon");
   private static final Component f_86247_ = new TranslatableComponent("mco.selectServer.expires.day");
   private static final Component f_86248_ = new TranslatableComponent("mco.selectServer.open");
   private static final Component f_86249_ = new TranslatableComponent("mco.selectServer.closed");
   private static final Component f_86250_ = new TranslatableComponent("mco.selectServer.leave");
   private static final Component f_86251_ = new TranslatableComponent("mco.selectServer.configure");
   private static final Component f_86252_ = new TranslatableComponent("mco.selectServer.info");
   private static final Component f_86253_ = new TranslatableComponent("mco.news");
   static final Component f_167175_ = new TranslatableComponent("gui.narrate.button", f_86238_);
   static final Component f_167173_ = CommonComponents.m_178391_(f_86237_);
   private static List<ResourceLocation> f_86254_ = ImmutableList.of();
   static final RealmsDataFetcher f_86255_ = new RealmsDataFetcher(Minecraft.m_91087_(), RealmsClient.m_87169_());
   static boolean f_86256_;
   private static int f_86274_ = -1;
   static volatile boolean f_86275_;
   static volatile boolean f_86276_;
   static volatile boolean f_86277_;
   static Screen f_86278_;
   private static boolean f_86279_;
   private final RateLimiter f_86280_;
   private boolean f_86281_;
   final Screen f_86282_;
   volatile RealmsMainScreen.RealmSelectionList f_86283_;
   private boolean f_167174_;
   long f_86284_ = -1L;
   Button f_86285_;
   private Button f_86286_;
   private Button f_86287_;
   private Button f_86288_;
   private Button f_86289_;
   private List<Component> f_86290_;
   List<RealmsServer> f_86291_ = Lists.newArrayList();
   volatile int f_86292_;
   int f_86293_;
   private boolean f_86294_;
   boolean f_86295_;
   private boolean f_86296_;
   private volatile boolean f_86297_;
   private volatile boolean f_86298_;
   private volatile boolean f_86299_;
   volatile boolean f_86258_;
   volatile String f_86259_;
   private int f_86260_;
   private int f_86261_;
   private boolean f_86262_;
   private List<KeyCombo> f_86263_;
   int f_86264_;
   private ReentrantLock f_86265_ = new ReentrantLock();
   private MultiLineLabel f_86266_ = MultiLineLabel.f_94331_;
   RealmsMainScreen.HoveredElement f_86267_;
   private Button f_86268_;
   @Nullable
   private RealmsMainScreen.PendingInvitesButton f_86269_;
   private Button f_86270_;
   private Button f_86271_;
   private Button f_86272_;
   private Button f_86273_;

   public RealmsMainScreen(Screen p_86315_) {
      super(NarratorChatListener.f_93310_);
      this.f_86282_ = p_86315_;
      this.f_86280_ = RateLimiter.create((double)0.016666668F);
   }

   private boolean m_86318_() {
      if (m_86321_() && this.f_86294_) {
         if (this.f_86297_ && !this.f_86298_) {
            return true;
         } else {
            for(RealmsServer realmsserver : this.f_86291_) {
               if (realmsserver.f_87479_.equals(this.f_96541_.m_91094_().m_92545_())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean m_86528_() {
      if (m_86321_() && this.f_86294_) {
         if (this.f_86295_) {
            return true;
         } else {
            return this.f_86297_ && !this.f_86298_ && this.f_86291_.isEmpty() ? true : this.f_86291_.isEmpty();
         }
      } else {
         return false;
      }
   }

   public void m_7856_() {
      this.f_86263_ = Lists.newArrayList(new KeyCombo(new char[]{'3', '2', '1', '4', '5', '6'}, () -> {
         f_86256_ = !f_86256_;
      }), new KeyCombo(new char[]{'9', '8', '7', '1', '2', '3'}, () -> {
         if (RealmsClient.f_87157_ == RealmsClient.Environment.STAGE) {
            this.m_86351_();
         } else {
            this.m_86345_();
         }

      }), new KeyCombo(new char[]{'9', '8', '7', '4', '5', '6'}, () -> {
         if (RealmsClient.f_87157_ == RealmsClient.Environment.LOCAL) {
            this.m_86351_();
         } else {
            this.m_86348_();
         }

      }));
      if (f_86278_ != null) {
         this.f_96541_.m_91152_(f_86278_);
      } else {
         this.f_86265_ = new ReentrantLock();
         if (f_86277_ && !m_86321_()) {
            this.m_86342_();
         }

         this.m_86336_();
         this.m_86339_();
         if (!this.f_86281_) {
            this.f_96541_.m_91372_(false);
         }

         this.f_96541_.f_91068_.m_90926_(true);
         if (m_86321_()) {
            f_86255_.m_87849_();
         }

         this.f_86299_ = false;
         if (m_86321_() && this.f_86294_) {
            this.m_86570_();
         }

         this.f_86283_ = new RealmsMainScreen.RealmSelectionList();
         if (f_86274_ != -1) {
            this.f_86283_.m_93410_((double)f_86274_);
         }

         this.m_7787_(this.f_86283_);
         this.f_167174_ = true;
         this.m_94725_(this.f_86283_);
         this.f_86266_ = MultiLineLabel.m_94341_(this.f_96547_, f_86244_, 100);
      }
   }

   private static boolean m_86321_() {
      return f_86276_ && f_86275_;
   }

   public void m_86570_() {
      this.f_86289_ = this.m_142416_(new Button(this.f_96543_ / 2 - 202, this.f_96544_ - 32, 90, 20, new TranslatableComponent("mco.selectServer.leave"), (p_86679_) -> {
         this.m_86669_(this.m_86404_(this.f_86284_));
      }));
      this.f_86288_ = this.m_142416_(new Button(this.f_96543_ / 2 - 190, this.f_96544_ - 32, 90, 20, new TranslatableComponent("mco.selectServer.configure"), (p_86672_) -> {
         this.m_86656_(this.m_86404_(this.f_86284_));
      }));
      this.f_86285_ = this.m_142416_(new Button(this.f_96543_ / 2 - 93, this.f_96544_ - 32, 90, 20, new TranslatableComponent("mco.selectServer.play"), (p_86659_) -> {
         RealmsServer realmsserver1 = this.m_86404_(this.f_86284_);
         if (realmsserver1 != null) {
            this.m_86515_(realmsserver1, this);
         }
      }));
      this.f_86286_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 32, 90, 20, CommonComponents.f_130660_, (p_86647_) -> {
         if (!this.f_86296_) {
            this.f_96541_.m_91152_(this.f_86282_);
         }

      }));
      this.f_86287_ = this.m_142416_(new Button(this.f_96543_ / 2 + 100, this.f_96544_ - 32, 90, 20, new TranslatableComponent("mco.selectServer.expiredRenew"), (p_86622_) -> {
         this.m_86333_();
      }));
      this.f_86269_ = this.m_142416_(new RealmsMainScreen.PendingInvitesButton());
      this.f_86270_ = this.m_142416_(new RealmsMainScreen.NewsButton());
      this.f_86268_ = this.m_142416_(new RealmsMainScreen.ShowPopupButton());
      this.f_86273_ = this.m_142416_(new RealmsMainScreen.CloseButton());
      this.f_86271_ = this.m_142416_(new Button(this.f_96543_ / 2 + 52, this.m_86366_() + 137 - 20, 98, 20, new TranslatableComponent("mco.selectServer.trial"), (p_86597_) -> {
         if (this.f_86297_ && !this.f_86298_) {
            Util.m_137581_().m_137646_("https://aka.ms/startjavarealmstrial");
            this.f_96541_.m_91152_(this.f_86282_);
         }
      }));
      this.f_86272_ = this.m_142416_(new Button(this.f_96543_ / 2 + 52, this.m_86366_() + 160 - 20, 98, 20, new TranslatableComponent("mco.selectServer.buy"), (p_86565_) -> {
         Util.m_137581_().m_137646_("https://aka.ms/BuyJavaRealms");
      }));
      RealmsServer realmsserver = this.m_86404_(this.f_86284_);
      this.m_86513_(realmsserver);
   }

   void m_86513_(@Nullable RealmsServer p_86514_) {
      this.f_86285_.f_93623_ = this.m_86562_(p_86514_) && !this.m_86528_();
      this.f_86287_.f_93624_ = this.m_86594_(p_86514_);
      this.f_86288_.f_93624_ = this.m_86619_(p_86514_);
      this.f_86289_.f_93624_ = this.m_86644_(p_86514_);
      boolean flag = this.m_86528_() && this.f_86297_ && !this.f_86298_;
      this.f_86271_.f_93624_ = flag;
      this.f_86271_.f_93623_ = flag;
      this.f_86272_.f_93624_ = this.m_86528_();
      this.f_86273_.f_93624_ = this.m_86528_() && this.f_86295_;
      this.f_86287_.f_93623_ = !this.m_86528_();
      this.f_86288_.f_93623_ = !this.m_86528_();
      this.f_86289_.f_93623_ = !this.m_86528_();
      this.f_86270_.f_93623_ = true;
      this.f_86269_.f_93623_ = true;
      this.f_86286_.f_93623_ = true;
      this.f_86268_.f_93623_ = !this.m_86528_();
   }

   private boolean m_86324_() {
      return (!this.m_86528_() || this.f_86295_) && m_86321_() && this.f_86294_;
   }

   private boolean m_86562_(@Nullable RealmsServer p_86563_) {
      return p_86563_ != null && !p_86563_.f_87482_ && p_86563_.f_87477_ == RealmsServer.State.OPEN;
   }

   private boolean m_86594_(@Nullable RealmsServer p_86595_) {
      return p_86595_ != null && p_86595_.f_87482_ && this.m_86683_(p_86595_);
   }

   private boolean m_86619_(@Nullable RealmsServer p_86620_) {
      return p_86620_ != null && this.m_86683_(p_86620_);
   }

   private boolean m_86644_(@Nullable RealmsServer p_86645_) {
      return p_86645_ != null && !this.m_86683_(p_86645_);
   }

   public void m_96624_() {
      super.m_96624_();
      if (this.f_86269_ != null) {
         this.f_86269_.m_86821_();
      }

      this.f_86296_ = false;
      ++this.f_86293_;
      --this.f_86264_;
      if (this.f_86264_ < 0) {
         this.f_86264_ = 0;
      }

      if (m_86321_()) {
         f_86255_.m_87841_();
         if (f_86255_.m_87820_(RealmsDataFetcher.Task.SERVER_LIST)) {
            List<RealmsServer> list = f_86255_.m_87850_();
            this.f_86283_.m_7178_();
            boolean flag = !this.f_86294_;
            if (flag) {
               this.f_86294_ = true;
            }

            if (list != null) {
               boolean flag1 = false;

               for(RealmsServer realmsserver : list) {
                  if (this.m_86688_(realmsserver)) {
                     flag1 = true;
                  }
               }

               this.f_86291_ = list;
               if (this.m_86318_()) {
                  this.f_86283_.m_86843_(new RealmsMainScreen.TrialEntry());
               }

               for(RealmsServer realmsserver1 : this.f_86291_) {
                  this.f_86283_.m_7085_(new RealmsMainScreen.ServerEntry(realmsserver1));
               }

               if (!f_86279_ && flag1) {
                  f_86279_ = true;
                  this.m_86327_();
               }
            }

            if (flag) {
               this.m_86570_();
            } else {
               this.m_86513_(this.m_86404_(this.f_86284_));
            }
         }

         if (f_86255_.m_87820_(RealmsDataFetcher.Task.PENDING_INVITE)) {
            this.f_86292_ = f_86255_.m_87851_();
            if (this.f_86292_ > 0 && this.f_86280_.tryAcquire(1)) {
               NarratorChatListener.f_93311_.m_168785_(new TranslatableComponent("mco.configure.world.invite.narration", this.f_86292_));
            }
         }

         if (f_86255_.m_87820_(RealmsDataFetcher.Task.TRIAL_AVAILABLE) && !this.f_86298_) {
            boolean flag2 = f_86255_.m_87852_();
            if (flag2 != this.f_86297_ && this.m_86528_()) {
               this.f_86297_ = flag2;
               this.f_86299_ = false;
            } else {
               this.f_86297_ = flag2;
            }
         }

         if (f_86255_.m_87820_(RealmsDataFetcher.Task.LIVE_STATS)) {
            RealmsServerPlayerLists realmsserverplayerlists = f_86255_.m_87853_();

            for(RealmsServerPlayerList realmsserverplayerlist : realmsserverplayerlists.f_87592_) {
               for(RealmsServer realmsserver2 : this.f_86291_) {
                  if (realmsserver2.f_87473_ == realmsserverplayerlist.f_87582_) {
                     realmsserver2.m_87506_(realmsserverplayerlist);
                     break;
                  }
               }
            }
         }

         if (f_86255_.m_87820_(RealmsDataFetcher.Task.UNREAD_NEWS)) {
            this.f_86258_ = f_86255_.m_87854_();
            this.f_86259_ = f_86255_.m_87855_();
         }

         f_86255_.m_87848_();
         if (this.m_86528_()) {
            ++this.f_86261_;
         }

         if (this.f_86268_ != null) {
            this.f_86268_.f_93624_ = this.m_86324_();
         }

      }
   }

   private void m_86327_() {
      (new Thread(() -> {
         List<RegionPingResult> list = Ping.m_87125_();
         RealmsClient realmsclient = RealmsClient.m_87169_();
         PingResult pingresult = new PingResult();
         pingresult.f_87438_ = list;
         pingresult.f_87439_ = this.m_86330_();

         try {
            realmsclient.m_87199_(pingresult);
         } catch (Throwable throwable) {
            f_86257_.warn("Could not send ping result to Realms: ", throwable);
         }

      })).start();
   }

   private List<Long> m_86330_() {
      List<Long> list = Lists.newArrayList();

      for(RealmsServer realmsserver : this.f_86291_) {
         if (this.m_86688_(realmsserver)) {
            list.add(realmsserver.f_87473_);
         }
      }

      return list;
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
      this.m_86354_();
   }

   public void m_167190_(boolean p_167191_) {
      this.f_86298_ = p_167191_;
   }

   void m_86333_() {
      RealmsServer realmsserver = this.m_86404_(this.f_86284_);
      if (realmsserver != null) {
         String s = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + realmsserver.f_87474_ + "&profileId=" + this.f_96541_.m_91094_().m_92545_() + "&ref=" + (realmsserver.f_87483_ ? "expiredTrial" : "expiredRealm");
         this.f_96541_.f_91068_.m_90911_(s);
         Util.m_137581_().m_137646_(s);
      }
   }

   private void m_86336_() {
      if (!f_86277_) {
         f_86277_ = true;
         (new Thread("MCO Compatability Checker #1") {
            public void run() {
               RealmsClient realmsclient = RealmsClient.m_87169_();

               try {
                  RealmsClient.CompatibleVersionResponse realmsclient$compatibleversionresponse = realmsclient.m_87259_();
                  if (realmsclient$compatibleversionresponse == RealmsClient.CompatibleVersionResponse.OUTDATED) {
                     RealmsMainScreen.f_86278_ = new RealmsClientOutdatedScreen(RealmsMainScreen.this.f_86282_, true);
                     RealmsMainScreen.this.f_96541_.execute(() -> {
                        RealmsMainScreen.this.f_96541_.m_91152_(RealmsMainScreen.f_86278_);
                     });
                     return;
                  }

                  if (realmsclient$compatibleversionresponse == RealmsClient.CompatibleVersionResponse.OTHER) {
                     RealmsMainScreen.f_86278_ = new RealmsClientOutdatedScreen(RealmsMainScreen.this.f_86282_, false);
                     RealmsMainScreen.this.f_96541_.execute(() -> {
                        RealmsMainScreen.this.f_96541_.m_91152_(RealmsMainScreen.f_86278_);
                     });
                     return;
                  }

                  RealmsMainScreen.this.m_86342_();
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsMainScreen.f_86277_ = false;
                  RealmsMainScreen.f_86257_.error("Couldn't connect to realms", (Throwable)realmsserviceexception);
                  if (realmsserviceexception.f_87773_ == 401) {
                     RealmsMainScreen.f_86278_ = new RealmsGenericErrorScreen(new TranslatableComponent("mco.error.invalid.session.title"), new TranslatableComponent("mco.error.invalid.session.message"), RealmsMainScreen.this.f_86282_);
                     RealmsMainScreen.this.f_96541_.execute(() -> {
                        RealmsMainScreen.this.f_96541_.m_91152_(RealmsMainScreen.f_86278_);
                     });
                  } else {
                     RealmsMainScreen.this.f_96541_.execute(() -> {
                        RealmsMainScreen.this.f_96541_.m_91152_(new RealmsGenericErrorScreen(realmsserviceexception, RealmsMainScreen.this.f_86282_));
                     });
                  }
               }

            }
         }).start();
      }

   }

   private void m_86339_() {
   }

   void m_86342_() {
      (new Thread("MCO Compatability Checker #1") {
         public void run() {
            RealmsClient realmsclient = RealmsClient.m_87169_();

            try {
               Boolean obool = realmsclient.m_87247_();
               if (obool) {
                  RealmsMainScreen.f_86257_.info("Realms is available for this user");
                  RealmsMainScreen.f_86275_ = true;
               } else {
                  RealmsMainScreen.f_86257_.info("Realms is not available for this user");
                  RealmsMainScreen.f_86275_ = false;
                  RealmsMainScreen.this.f_96541_.execute(() -> {
                     RealmsMainScreen.this.f_96541_.m_91152_(new RealmsParentalConsentScreen(RealmsMainScreen.this.f_86282_));
                  });
               }

               RealmsMainScreen.f_86276_ = true;
            } catch (RealmsServiceException realmsserviceexception) {
               RealmsMainScreen.f_86257_.error("Couldn't connect to realms", (Throwable)realmsserviceexception);
               RealmsMainScreen.this.f_96541_.execute(() -> {
                  RealmsMainScreen.this.f_96541_.m_91152_(new RealmsGenericErrorScreen(realmsserviceexception, RealmsMainScreen.this.f_86282_));
               });
            }

         }
      }).start();
   }

   private void m_86345_() {
      if (RealmsClient.f_87157_ != RealmsClient.Environment.STAGE) {
         (new Thread("MCO Stage Availability Checker #1") {
            public void run() {
               RealmsClient realmsclient = RealmsClient.m_87169_();

               try {
                  Boolean obool = realmsclient.m_87253_();
                  if (obool) {
                     RealmsClient.m_87206_();
                     RealmsMainScreen.f_86257_.info("Switched to stage");
                     RealmsMainScreen.f_86255_.m_87849_();
                  }
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsMainScreen.f_86257_.error("Couldn't connect to Realms: {}", (Object)realmsserviceexception.toString());
               }

            }
         }).start();
      }

   }

   private void m_86348_() {
      if (RealmsClient.f_87157_ != RealmsClient.Environment.LOCAL) {
         (new Thread("MCO Local Availability Checker #1") {
            public void run() {
               RealmsClient realmsclient = RealmsClient.m_87169_();

               try {
                  Boolean obool = realmsclient.m_87253_();
                  if (obool) {
                     RealmsClient.m_87229_();
                     RealmsMainScreen.f_86257_.info("Switched to local");
                     RealmsMainScreen.f_86255_.m_87849_();
                  }
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsMainScreen.f_86257_.error("Couldn't connect to Realms: {}", (Object)realmsserviceexception.toString());
               }

            }
         }).start();
      }

   }

   private void m_86351_() {
      RealmsClient.m_87221_();
      f_86255_.m_87849_();
   }

   private void m_86354_() {
      f_86255_.m_87856_();
   }

   void m_86656_(@Nullable RealmsServer p_86657_) {
      if (p_86657_ != null && (this.f_96541_.m_91094_().m_92545_().equals(p_86657_.f_87479_) || f_86256_)) {
         this.m_86357_();
         this.f_96541_.m_91152_(new RealmsConfigureWorldScreen(this, p_86657_.f_87473_));
      }

   }

   void m_86669_(@Nullable RealmsServer p_86670_) {
      if (p_86670_ != null && !this.f_96541_.m_91094_().m_92545_().equals(p_86670_.f_87479_)) {
         this.m_86357_();
         Component component = new TranslatableComponent("mco.configure.world.leave.question.line1");
         Component component1 = new TranslatableComponent("mco.configure.world.leave.question.line2");
         this.f_96541_.m_91152_(new RealmsLongConfirmationScreen(this::m_86623_, RealmsLongConfirmationScreen.Type.Info, component, component1, true));
      }

   }

   private void m_86357_() {
      f_86274_ = (int)this.f_86283_.m_93517_();
   }

   @Nullable
   RealmsServer m_86404_(long p_86405_) {
      for(RealmsServer realmsserver : this.f_86291_) {
         if (realmsserver.f_87473_ == p_86405_) {
            return realmsserver;
         }
      }

      return null;
   }

   private void m_86623_(boolean p_86624_) {
      if (p_86624_) {
         final long i = this.f_86284_;
         (new Thread("Realms-leave-server") {
            public void run() {
               try {
                  RealmsServer realmsserver = RealmsMainScreen.this.m_86404_(i);
                  if (realmsserver != null) {
                     RealmsClient realmsclient = RealmsClient.m_87169_();
                     realmsclient.m_87222_(realmsserver.f_87473_);
                     RealmsMainScreen.this.f_96541_.execute(() -> {
                        RealmsMainScreen.this.m_86676_(realmsserver);
                     });
                  }
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsMainScreen.f_86257_.error("Couldn't configure world");
                  RealmsMainScreen.this.f_96541_.execute(() -> {
                     RealmsMainScreen.this.f_96541_.m_91152_(new RealmsGenericErrorScreen(realmsserviceexception, RealmsMainScreen.this));
                  });
               }

            }
         }).start();
      }

      this.f_96541_.m_91152_(this);
   }

   void m_86676_(RealmsServer p_86677_) {
      f_86255_.m_87818_(p_86677_);
      this.f_86291_.remove(p_86677_);
      this.f_86283_.m_6702_().removeIf((p_86447_) -> {
         return p_86447_ instanceof RealmsMainScreen.ServerEntry && ((RealmsMainScreen.ServerEntry)p_86447_).f_86853_.f_87473_ == this.f_86284_;
      });
      this.f_86283_.m_6987_((RealmsMainScreen.Entry)null);
      this.m_86513_((RealmsServer)null);
      this.f_86284_ = -1L;
      this.f_86285_.f_93623_ = false;
   }

   public void m_86529_() {
      this.f_86284_ = -1L;
   }

   public boolean m_7933_(int p_86401_, int p_86402_, int p_86403_) {
      if (p_86401_ == 256) {
         this.f_86263_.forEach(KeyCombo::m_86227_);
         this.m_86360_();
         return true;
      } else {
         return super.m_7933_(p_86401_, p_86402_, p_86403_);
      }
   }

   void m_86360_() {
      if (this.m_86528_() && this.f_86295_) {
         this.f_86295_ = false;
      } else {
         this.f_96541_.m_91152_(this.f_86282_);
      }

   }

   public boolean m_5534_(char p_86388_, int p_86389_) {
      this.f_86263_.forEach((p_86392_) -> {
         p_86392_.m_86228_(p_86388_);
      });
      return true;
   }

   public void m_6305_(PoseStack p_86413_, int p_86414_, int p_86415_, float p_86416_) {
      this.f_86267_ = RealmsMainScreen.HoveredElement.NONE;
      this.f_86290_ = null;
      this.m_7333_(p_86413_);
      this.f_86283_.m_6305_(p_86413_, p_86414_, p_86415_, p_86416_);
      this.m_86408_(p_86413_, this.f_96543_ / 2 - 50, 7);
      if (RealmsClient.f_87157_ == RealmsClient.Environment.STAGE) {
         this.m_86574_(p_86413_);
      }

      if (RealmsClient.f_87157_ == RealmsClient.Environment.LOCAL) {
         this.m_86531_(p_86413_);
      }

      if (this.m_86528_()) {
         this.m_86533_(p_86413_, p_86414_, p_86415_);
      } else {
         if (this.f_86299_) {
            this.m_86513_((RealmsServer)null);
            if (!this.f_167174_) {
               this.m_7787_(this.f_86283_);
               this.f_167174_ = true;
            }

            RealmsServer realmsserver = this.m_86404_(this.f_86284_);
            this.f_86285_.f_93623_ = this.m_86562_(realmsserver);
         }

         this.f_86299_ = false;
      }

      super.m_6305_(p_86413_, p_86414_, p_86415_, p_86416_);
      if (this.f_86290_ != null) {
         this.m_86441_(p_86413_, this.f_86290_, p_86414_, p_86415_);
      }

      if (this.f_86297_ && !this.f_86298_ && this.m_86528_()) {
         RenderSystem.m_157456_(0, f_86233_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         int k = 8;
         int i = 8;
         int j = 0;
         if ((Util.m_137550_() / 800L & 1L) == 1L) {
            j = 8;
         }

         GuiComponent.m_93133_(p_86413_, this.f_86271_.f_93620_ + this.f_86271_.m_5711_() - 8 - 4, this.f_86271_.f_93621_ + this.f_86271_.m_93694_() / 2 - 4, 0.0F, (float)j, 8, 8, 8, 16);
      }

   }

   private void m_86408_(PoseStack p_86409_, int p_86410_, int p_86411_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_86308_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      p_86409_.m_85836_();
      p_86409_.m_85841_(0.5F, 0.5F, 0.5F);
      GuiComponent.m_93133_(p_86409_, p_86410_ * 2, p_86411_ * 2 - 5, 0.0F, 0.0F, 200, 50, 200, 50);
      p_86409_.m_85849_();
   }

   public boolean m_6375_(double p_86397_, double p_86398_, int p_86399_) {
      if (this.m_86393_(p_86397_, p_86398_) && this.f_86295_) {
         this.f_86295_ = false;
         this.f_86296_ = true;
         return true;
      } else {
         return super.m_6375_(p_86397_, p_86398_, p_86399_);
      }
   }

   private boolean m_86393_(double p_86394_, double p_86395_) {
      int i = this.m_86363_();
      int j = this.m_86366_();
      return p_86394_ < (double)(i - 5) || p_86394_ > (double)(i + 315) || p_86395_ < (double)(j - 5) || p_86395_ > (double)(j + 171);
   }

   private void m_86533_(PoseStack p_86534_, int p_86535_, int p_86536_) {
      int i = this.m_86363_();
      int j = this.m_86366_();
      if (!this.f_86299_) {
         this.f_86260_ = 0;
         this.f_86261_ = 0;
         this.f_86262_ = true;
         this.m_86513_((RealmsServer)null);
         if (this.f_167174_) {
            this.m_169411_(this.f_86283_);
            this.f_167174_ = false;
         }

         NarratorChatListener.f_93311_.m_168785_(f_86244_);
      }

      if (this.f_86294_) {
         this.f_86299_ = true;
      }

      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 0.7F);
      RenderSystem.m_69478_();
      RenderSystem.m_157456_(0, f_86231_);
      int k = 0;
      int l = 32;
      GuiComponent.m_93133_(p_86534_, 0, 32, 0.0F, 0.0F, this.f_96543_, this.f_96544_ - 40 - 32, 310, 166);
      RenderSystem.m_69461_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_86312_);
      GuiComponent.m_93133_(p_86534_, i, j, 0.0F, 0.0F, 310, 166, 310, 166);
      if (!f_86254_.isEmpty()) {
         RenderSystem.m_157456_(0, f_86254_.get(this.f_86260_));
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.m_93133_(p_86534_, i + 7, j + 7, 0.0F, 0.0F, 195, 152, 195, 152);
         if (this.f_86261_ % 95 < 5) {
            if (!this.f_86262_) {
               this.f_86260_ = (this.f_86260_ + 1) % f_86254_.size();
               this.f_86262_ = true;
            }
         } else {
            this.f_86262_ = false;
         }
      }

      this.f_86266_.m_6508_(p_86534_, this.f_96543_ / 2 + 52, j + 7, 10, 5000268);
   }

   int m_86363_() {
      return (this.f_96543_ - 310) / 2;
   }

   int m_86366_() {
      return this.f_96544_ / 2 - 80;
   }

   void m_86424_(PoseStack p_86425_, int p_86426_, int p_86427_, int p_86428_, int p_86429_, boolean p_86430_, boolean p_86431_) {
      int i = this.f_86292_;
      boolean flag = this.m_86571_((double)p_86426_, (double)p_86427_);
      boolean flag1 = p_86431_ && p_86430_;
      if (flag1) {
         float f = 0.25F + (1.0F + Mth.m_14031_((float)this.f_86293_ * 0.5F)) * 0.25F;
         int j = -16777216 | (int)(f * 64.0F) << 16 | (int)(f * 64.0F) << 8 | (int)(f * 64.0F) << 0;
         this.m_93179_(p_86425_, p_86428_ - 2, p_86429_ - 2, p_86428_ + 18, p_86429_ + 18, j, j);
         j = -16777216 | (int)(f * 255.0F) << 16 | (int)(f * 255.0F) << 8 | (int)(f * 255.0F) << 0;
         this.m_93179_(p_86425_, p_86428_ - 2, p_86429_ - 2, p_86428_ + 18, p_86429_ - 1, j, j);
         this.m_93179_(p_86425_, p_86428_ - 2, p_86429_ - 2, p_86428_ - 1, p_86429_ + 18, j, j);
         this.m_93179_(p_86425_, p_86428_ + 17, p_86429_ - 2, p_86428_ + 18, p_86429_ + 18, j, j);
         this.m_93179_(p_86425_, p_86428_ - 2, p_86429_ + 17, p_86428_ + 18, p_86429_ + 18, j, j);
      }

      RenderSystem.m_157456_(0, f_86306_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      boolean flag3 = p_86431_ && p_86430_;
      float f2 = flag3 ? 16.0F : 0.0F;
      GuiComponent.m_93133_(p_86425_, p_86428_, p_86429_ - 6, f2, 0.0F, 15, 25, 31, 25);
      boolean flag2 = p_86431_ && i != 0;
      if (flag2) {
         int k = (Math.min(i, 6) - 1) * 8;
         int l = (int)(Math.max(0.0F, Math.max(Mth.m_14031_((float)(10 + this.f_86293_) * 0.57F), Mth.m_14089_((float)this.f_86293_ * 0.35F))) * -6.0F);
         RenderSystem.m_157456_(0, f_86305_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         float f1 = flag ? 8.0F : 0.0F;
         GuiComponent.m_93133_(p_86425_, p_86428_ + 4, p_86429_ + 4 + l, (float)k, f1, 8, 8, 48, 16);
      }

      int j1 = p_86426_ + 12;
      boolean flag4 = p_86431_ && flag;
      if (flag4) {
         Component component = i == 0 ? f_86235_ : f_86236_;
         int i1 = this.f_96547_.m_92852_(component);
         this.m_93179_(p_86425_, j1 - 3, p_86427_ - 3, j1 + i1 + 3, p_86427_ + 8 + 3, -1073741824, -1073741824);
         this.f_96547_.m_92763_(p_86425_, component, (float)j1, (float)p_86427_, -1);
      }

   }

   private boolean m_86571_(double p_86572_, double p_86573_) {
      int i = this.f_96543_ / 2 + 50;
      int j = this.f_96543_ / 2 + 66;
      int k = 11;
      int l = 23;
      if (this.f_86292_ != 0) {
         i -= 3;
         j += 3;
         k -= 5;
         l += 5;
      }

      return (double)i <= p_86572_ && p_86572_ <= (double)j && (double)k <= p_86573_ && p_86573_ <= (double)l;
   }

   public void m_86515_(@Nullable RealmsServer p_86516_, Screen p_86517_) {
      if (p_86516_ != null) {
         try {
            if (!this.f_86265_.tryLock(1L, TimeUnit.SECONDS)) {
               return;
            }

            if (this.f_86265_.getHoldCount() > 1) {
               return;
            }
         } catch (InterruptedException interruptedexception) {
            return;
         }

         this.f_86281_ = true;
         this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(p_86517_, new GetServerDetailsTask(this, p_86517_, p_86516_, this.f_86265_)));
      }

   }

   boolean m_86683_(RealmsServer p_86684_) {
      return p_86684_.f_87479_ != null && p_86684_.f_87479_.equals(this.f_96541_.m_91094_().m_92545_());
   }

   private boolean m_86688_(RealmsServer p_86689_) {
      return this.m_86683_(p_86689_) && !p_86689_.f_87482_;
   }

   void m_86576_(PoseStack p_86577_, int p_86578_, int p_86579_, int p_86580_, int p_86581_) {
      RenderSystem.m_157456_(0, f_86302_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      GuiComponent.m_93133_(p_86577_, p_86578_, p_86579_, 0.0F, 0.0F, 10, 28, 10, 28);
      if (p_86580_ >= p_86578_ && p_86580_ <= p_86578_ + 9 && p_86581_ >= p_86579_ && p_86581_ <= p_86579_ + 27 && p_86581_ < this.f_96544_ - 40 && p_86581_ > 32 && !this.m_86528_()) {
         this.m_86526_(f_86245_);
      }

   }

   void m_86537_(PoseStack p_86538_, int p_86539_, int p_86540_, int p_86541_, int p_86542_, int p_86543_) {
      RenderSystem.m_157456_(0, f_86303_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.f_86293_ % 20 < 10) {
         GuiComponent.m_93133_(p_86538_, p_86539_, p_86540_, 0.0F, 0.0F, 10, 28, 20, 28);
      } else {
         GuiComponent.m_93133_(p_86538_, p_86539_, p_86540_, 10.0F, 0.0F, 10, 28, 20, 28);
      }

      if (p_86541_ >= p_86539_ && p_86541_ <= p_86539_ + 9 && p_86542_ >= p_86540_ && p_86542_ <= p_86540_ + 27 && p_86542_ < this.f_96544_ - 40 && p_86542_ > 32 && !this.m_86528_()) {
         if (p_86543_ <= 0) {
            this.m_86526_(f_86246_);
         } else if (p_86543_ == 1) {
            this.m_86526_(f_86247_);
         } else {
            this.m_86526_(new TranslatableComponent("mco.selectServer.expires.days", p_86543_));
         }
      }

   }

   void m_86601_(PoseStack p_86602_, int p_86603_, int p_86604_, int p_86605_, int p_86606_) {
      RenderSystem.m_157456_(0, f_86300_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      GuiComponent.m_93133_(p_86602_, p_86603_, p_86604_, 0.0F, 0.0F, 10, 28, 10, 28);
      if (p_86605_ >= p_86603_ && p_86605_ <= p_86603_ + 9 && p_86606_ >= p_86604_ && p_86606_ <= p_86604_ + 27 && p_86606_ < this.f_96544_ - 40 && p_86606_ > 32 && !this.m_86528_()) {
         this.m_86526_(f_86248_);
      }

   }

   void m_86626_(PoseStack p_86627_, int p_86628_, int p_86629_, int p_86630_, int p_86631_) {
      RenderSystem.m_157456_(0, f_86301_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      GuiComponent.m_93133_(p_86627_, p_86628_, p_86629_, 0.0F, 0.0F, 10, 28, 10, 28);
      if (p_86630_ >= p_86628_ && p_86630_ <= p_86628_ + 9 && p_86631_ >= p_86629_ && p_86631_ <= p_86629_ + 27 && p_86631_ < this.f_96544_ - 40 && p_86631_ > 32 && !this.m_86528_()) {
         this.m_86526_(f_86249_);
      }

   }

   void m_86648_(PoseStack p_86649_, int p_86650_, int p_86651_, int p_86652_, int p_86653_) {
      boolean flag = false;
      if (p_86652_ >= p_86650_ && p_86652_ <= p_86650_ + 28 && p_86653_ >= p_86651_ && p_86653_ <= p_86651_ + 28 && p_86653_ < this.f_96544_ - 40 && p_86653_ > 32 && !this.m_86528_()) {
         flag = true;
      }

      RenderSystem.m_157456_(0, f_86304_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      float f = flag ? 28.0F : 0.0F;
      GuiComponent.m_93133_(p_86649_, p_86650_, p_86651_, f, 0.0F, 28, 28, 56, 28);
      if (flag) {
         this.m_86526_(f_86250_);
         this.f_86267_ = RealmsMainScreen.HoveredElement.LEAVE;
      }

   }

   void m_86661_(PoseStack p_86662_, int p_86663_, int p_86664_, int p_86665_, int p_86666_) {
      boolean flag = false;
      if (p_86665_ >= p_86663_ && p_86665_ <= p_86663_ + 28 && p_86666_ >= p_86664_ && p_86666_ <= p_86664_ + 28 && p_86666_ < this.f_96544_ - 40 && p_86666_ > 32 && !this.m_86528_()) {
         flag = true;
      }

      RenderSystem.m_157456_(0, f_86309_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      float f = flag ? 28.0F : 0.0F;
      GuiComponent.m_93133_(p_86662_, p_86663_, p_86664_, f, 0.0F, 28, 28, 56, 28);
      if (flag) {
         this.m_86526_(f_86251_);
         this.f_86267_ = RealmsMainScreen.HoveredElement.CONFIGURE;
      }

   }

   protected void m_86441_(PoseStack p_86442_, List<Component> p_86443_, int p_86444_, int p_86445_) {
      if (!p_86443_.isEmpty()) {
         int i = 0;
         int j = 0;

         for(Component component : p_86443_) {
            int k = this.f_96547_.m_92852_(component);
            if (k > j) {
               j = k;
            }
         }

         int i1 = p_86444_ - j - 5;
         int j1 = p_86445_;
         if (i1 < 0) {
            i1 = p_86444_ + 12;
         }

         for(Component component1 : p_86443_) {
            int l = j1 - (i == 0 ? 3 : 0) + i;
            this.m_93179_(p_86442_, i1 - 3, l, i1 + j + 3, j1 + 8 + 3 + i, -1073741824, -1073741824);
            this.f_96547_.m_92763_(p_86442_, component1, (float)i1, (float)(j1 + i), 16777215);
            i += 10;
         }

      }
   }

   void m_86417_(PoseStack p_86418_, int p_86419_, int p_86420_, int p_86421_, int p_86422_, boolean p_86423_) {
      boolean flag = false;
      if (p_86419_ >= p_86421_ && p_86419_ <= p_86421_ + 20 && p_86420_ >= p_86422_ && p_86420_ <= p_86422_ + 20) {
         flag = true;
      }

      RenderSystem.m_157456_(0, f_86310_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      float f = p_86423_ ? 20.0F : 0.0F;
      GuiComponent.m_93133_(p_86418_, p_86421_, p_86422_, f, 0.0F, 20, 20, 40, 20);
      if (flag) {
         this.m_86526_(f_86252_);
      }

   }

   void m_86432_(PoseStack p_86433_, int p_86434_, int p_86435_, boolean p_86436_, int p_86437_, int p_86438_, boolean p_86439_, boolean p_86440_) {
      boolean flag = false;
      if (p_86434_ >= p_86437_ && p_86434_ <= p_86437_ + 20 && p_86435_ >= p_86438_ && p_86435_ <= p_86438_ + 20) {
         flag = true;
      }

      RenderSystem.m_157456_(0, f_86311_);
      if (p_86440_) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.m_157429_(0.5F, 0.5F, 0.5F, 1.0F);
      }

      boolean flag1 = p_86440_ && p_86439_;
      float f = flag1 ? 20.0F : 0.0F;
      GuiComponent.m_93133_(p_86433_, p_86437_, p_86438_, f, 0.0F, 20, 20, 40, 20);
      if (flag && p_86440_) {
         this.m_86526_(f_86253_);
      }

      if (p_86436_ && p_86440_) {
         int i = flag ? 0 : (int)(Math.max(0.0F, Math.max(Mth.m_14031_((float)(10 + this.f_86293_) * 0.57F), Mth.m_14089_((float)this.f_86293_ * 0.35F))) * -6.0F);
         RenderSystem.m_157456_(0, f_86305_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.m_93133_(p_86433_, p_86437_ + 10, p_86438_ + 2 + i, 40.0F, 0.0F, 8, 8, 48, 16);
      }

   }

   private void m_86531_(PoseStack p_86532_) {
      String s = "LOCAL!";
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      p_86532_.m_85836_();
      p_86532_.m_85837_((double)(this.f_96543_ / 2 - 25), 20.0D, 0.0D);
      p_86532_.m_85845_(Vector3f.f_122227_.m_122240_(-20.0F));
      p_86532_.m_85841_(1.5F, 1.5F, 1.5F);
      this.f_96547_.m_92883_(p_86532_, "LOCAL!", 0.0F, 0.0F, 8388479);
      p_86532_.m_85849_();
   }

   private void m_86574_(PoseStack p_86575_) {
      String s = "STAGE!";
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      p_86575_.m_85836_();
      p_86575_.m_85837_((double)(this.f_96543_ / 2 - 25), 20.0D, 0.0D);
      p_86575_.m_85845_(Vector3f.f_122227_.m_122240_(-20.0F));
      p_86575_.m_85841_(1.5F, 1.5F, 1.5F);
      this.f_96547_.m_92883_(p_86575_, "STAGE!", 0.0F, 0.0F, -256);
      p_86575_.m_85849_();
   }

   public RealmsMainScreen m_86660_() {
      RealmsMainScreen realmsmainscreen = new RealmsMainScreen(this.f_86282_);
      realmsmainscreen.m_6575_(this.f_96541_, this.f_96543_, this.f_96544_);
      return realmsmainscreen;
   }

   public void m_167200_() {
      if (this.m_86528_() && this.f_86295_) {
         this.f_86295_ = false;
      }

   }

   public static void m_86406_(ResourceManager p_86407_) {
      Collection<ResourceLocation> collection = p_86407_.m_6540_("textures/gui/images", (p_86567_) -> {
         return p_86567_.endsWith(".png");
      });
      f_86254_ = collection.stream().filter((p_86523_) -> {
         return p_86523_.m_135827_().equals("realms");
      }).collect(ImmutableList.toImmutableList());
   }

   void m_86526_(Component... p_86527_) {
      this.f_86290_ = Arrays.asList(p_86527_);
   }

   private void m_167188_(Iterable<Component> p_167189_) {
      this.f_86290_ = ImmutableList.copyOf(p_167189_);
   }

   private void m_86518_(Button p_86519_) {
      this.f_96541_.m_91152_(new RealmsPendingInvitesScreen(this.f_86282_));
   }

   @OnlyIn(Dist.CLIENT)
   class CloseButton extends Button {
      public CloseButton() {
         super(RealmsMainScreen.this.m_86363_() + 4, RealmsMainScreen.this.m_86366_() + 4, 12, 12, new TranslatableComponent("mco.selectServer.close"), null);
      }

      @Override
      public void m_5691_() {
            RealmsMainScreen.this.m_86360_();
      }

      public void m_6303_(PoseStack p_86777_, int p_86778_, int p_86779_, float p_86780_) {
         RenderSystem.m_157456_(0, RealmsMainScreen.f_86232_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         float f = this.m_5702_() ? 12.0F : 0.0F;
         m_93133_(p_86777_, this.f_93620_, this.f_93621_, 0.0F, f, 12, 12, 12, 24);
         if (this.m_5953_((double)p_86778_, (double)p_86779_)) {
            RealmsMainScreen.this.m_86526_(this.m_6035_());
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   abstract class Entry extends ObjectSelectionList.Entry<RealmsMainScreen.Entry> {
   }

   @OnlyIn(Dist.CLIENT)
   static enum HoveredElement {
      NONE,
      EXPIRED,
      LEAVE,
      CONFIGURE;
   }

   @OnlyIn(Dist.CLIENT)
   class NewsButton extends Button {
      public NewsButton() {
         super(RealmsMainScreen.this.f_96543_ - 62, 6, 20, 20, new TranslatableComponent("mco.news"), null);
      }

      @Override
      public void m_5691_() {
            if (RealmsMainScreen.this.f_86259_ != null) {
               Util.m_137581_().m_137646_(RealmsMainScreen.this.f_86259_);
               if (RealmsMainScreen.this.f_86258_) {
                  RealmsPersistence.RealmsPersistenceData realmspersistence$realmspersistencedata = RealmsPersistence.m_90171_();
                  realmspersistence$realmspersistencedata.f_90176_ = false;
                  RealmsMainScreen.this.f_86258_ = false;
                  RealmsPersistence.m_90172_(realmspersistence$realmspersistencedata);
               }

            }
      }

      public void m_6303_(PoseStack p_86806_, int p_86807_, int p_86808_, float p_86809_) {
         RealmsMainScreen.this.m_86432_(p_86806_, p_86807_, p_86808_, RealmsMainScreen.this.f_86258_, this.f_93620_, this.f_93621_, this.m_5702_(), this.f_93623_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class PendingInvitesButton extends Button {
      public PendingInvitesButton() {
         super(RealmsMainScreen.this.f_96543_ / 2 + 47, 6, 22, 22, TextComponent.f_131282_, RealmsMainScreen.this::m_86518_);
      }

      @Override
      public void m_5691_() {
         RealmsMainScreen.this.m_86518_(this);
      }

      public void m_86821_() {
         this.m_93666_(RealmsMainScreen.this.f_86292_ == 0 ? RealmsMainScreen.f_86235_ : RealmsMainScreen.f_86236_);
      }

      public void m_6303_(PoseStack p_86817_, int p_86818_, int p_86819_, float p_86820_) {
         RealmsMainScreen.this.m_86424_(p_86817_, p_86818_, p_86819_, this.f_93620_, this.f_93621_, this.m_5702_(), this.f_93623_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class RealmSelectionList extends RealmsObjectSelectionList<RealmsMainScreen.Entry> {
      private boolean f_86823_;

      public RealmSelectionList() {
         super(RealmsMainScreen.this.f_96543_, RealmsMainScreen.this.f_96544_, 32, RealmsMainScreen.this.f_96544_ - 40, 36);
      }

      public void m_7178_() {
         super.m_7178_();
         this.f_86823_ = false;
      }

      public int m_86843_(RealmsMainScreen.Entry p_86844_) {
         this.f_86823_ = true;
         return this.m_7085_(p_86844_);
      }

      public boolean m_5694_() {
         return RealmsMainScreen.this.m_7222_() == this;
      }

      public boolean m_7933_(int p_86840_, int p_86841_, int p_86842_) {
         if (p_86840_ != 257 && p_86840_ != 32 && p_86840_ != 335) {
            return super.m_7933_(p_86840_, p_86841_, p_86842_);
         } else {
            RealmsMainScreen.Entry realmsmainscreen$entry = this.m_93511_();
            return realmsmainscreen$entry == null ? super.m_7933_(p_86840_, p_86841_, p_86842_) : realmsmainscreen$entry.m_6375_(0.0D, 0.0D, 0);
         }
      }

      public boolean m_6375_(double p_86828_, double p_86829_, int p_86830_) {
         if (p_86830_ == 0 && p_86828_ < (double)this.m_5756_() && p_86829_ >= (double)this.f_93390_ && p_86829_ <= (double)this.f_93391_) {
            int i = RealmsMainScreen.this.f_86283_.m_5747_();
            int j = this.m_5756_();
            int k = (int)Math.floor(p_86829_ - (double)this.f_93390_) - this.f_93395_ + (int)this.m_93517_() - 4;
            int l = k / this.f_93387_;
            if (p_86828_ >= (double)i && p_86828_ <= (double)j && l >= 0 && k >= 0 && l < this.m_5773_()) {
               this.m_7980_(k, l, p_86828_, p_86829_, this.f_93388_);
               RealmsMainScreen.this.f_86264_ += 7;
               this.m_7109_(l);
            }

            return true;
         } else {
            return super.m_6375_(p_86828_, p_86829_, p_86830_);
         }
      }

      public void m_7109_(int p_86832_) {
         this.m_120767_(p_86832_);
         if (p_86832_ != -1) {
            RealmsServer realmsserver;
            if (this.f_86823_) {
               if (p_86832_ == 0) {
                  realmsserver = null;
               } else {
                  if (p_86832_ - 1 >= RealmsMainScreen.this.f_86291_.size()) {
                     RealmsMainScreen.this.f_86284_ = -1L;
                     return;
                  }

                  realmsserver = RealmsMainScreen.this.f_86291_.get(p_86832_ - 1);
               }
            } else {
               if (p_86832_ >= RealmsMainScreen.this.f_86291_.size()) {
                  RealmsMainScreen.this.f_86284_ = -1L;
                  return;
               }

               realmsserver = RealmsMainScreen.this.f_86291_.get(p_86832_);
            }

            RealmsMainScreen.this.m_86513_(realmsserver);
            if (realmsserver == null) {
               RealmsMainScreen.this.f_86284_ = -1L;
            } else if (realmsserver.f_87477_ == RealmsServer.State.UNINITIALIZED) {
               RealmsMainScreen.this.f_86284_ = -1L;
            } else {
               RealmsMainScreen.this.f_86284_ = realmsserver.f_87473_;
               if (RealmsMainScreen.this.f_86264_ >= 10 && RealmsMainScreen.this.f_86285_.f_93623_) {
                  RealmsMainScreen.this.m_86515_(RealmsMainScreen.this.m_86404_(RealmsMainScreen.this.f_86284_), RealmsMainScreen.this);
               }

            }
         }
      }

      public void m_6987_(@Nullable RealmsMainScreen.Entry p_86849_) {
         super.m_6987_(p_86849_);
         int i = this.m_6702_().indexOf(p_86849_) - (this.f_86823_ ? 1 : 0);
         if (i >= 0 && i < RealmsMainScreen.this.f_86291_.size()) {
            RealmsServer realmsserver = RealmsMainScreen.this.f_86291_.get(i);
            RealmsMainScreen.this.f_86284_ = realmsserver.f_87473_;
            RealmsMainScreen.this.m_86513_(realmsserver);
         }

      }

      public void m_7980_(int p_86834_, int p_86835_, double p_86836_, double p_86837_, int p_86838_) {
         if (this.f_86823_) {
            if (p_86835_ == 0) {
               RealmsMainScreen.this.f_86295_ = true;
               return;
            }

            --p_86835_;
         }

         if (p_86835_ < RealmsMainScreen.this.f_86291_.size()) {
            RealmsServer realmsserver = RealmsMainScreen.this.f_86291_.get(p_86835_);
            if (realmsserver != null) {
               if (realmsserver.f_87477_ == RealmsServer.State.UNINITIALIZED) {
                  RealmsMainScreen.this.f_86284_ = -1L;
                  Minecraft.m_91087_().m_91152_(new RealmsCreateRealmScreen(realmsserver, RealmsMainScreen.this));
               } else {
                  RealmsMainScreen.this.f_86284_ = realmsserver.f_87473_;
               }

               if (RealmsMainScreen.this.f_86267_ == RealmsMainScreen.HoveredElement.CONFIGURE) {
                  RealmsMainScreen.this.f_86284_ = realmsserver.f_87473_;
                  RealmsMainScreen.this.m_86656_(realmsserver);
               } else if (RealmsMainScreen.this.f_86267_ == RealmsMainScreen.HoveredElement.LEAVE) {
                  RealmsMainScreen.this.f_86284_ = realmsserver.f_87473_;
                  RealmsMainScreen.this.m_86669_(realmsserver);
               } else if (RealmsMainScreen.this.f_86267_ == RealmsMainScreen.HoveredElement.EXPIRED) {
                  RealmsMainScreen.this.m_86333_();
               }

            }
         }
      }

      public int m_5775_() {
         return this.m_5773_() * 36;
      }

      public int m_5759_() {
         return 300;
      }
   }

   @OnlyIn(Dist.CLIENT)
   class ServerEntry extends RealmsMainScreen.Entry {
      private static final int f_167228_ = 36;
      final RealmsServer f_86853_;

      public ServerEntry(RealmsServer p_86856_) {
         this.f_86853_ = p_86856_;
      }

      public void m_6311_(PoseStack p_86866_, int p_86867_, int p_86868_, int p_86869_, int p_86870_, int p_86871_, int p_86872_, int p_86873_, boolean p_86874_, float p_86875_) {
         this.m_86878_(this.f_86853_, p_86866_, p_86869_, p_86868_, p_86872_, p_86873_);
      }

      public boolean m_6375_(double p_86858_, double p_86859_, int p_86860_) {
         if (this.f_86853_.f_87477_ == RealmsServer.State.UNINITIALIZED) {
            RealmsMainScreen.this.f_86284_ = -1L;
            RealmsMainScreen.this.f_96541_.m_91152_(new RealmsCreateRealmScreen(this.f_86853_, RealmsMainScreen.this));
         } else {
            RealmsMainScreen.this.f_86284_ = this.f_86853_.f_87473_;
         }

         return true;
      }

      private void m_86878_(RealmsServer p_86879_, PoseStack p_86880_, int p_86881_, int p_86882_, int p_86883_, int p_86884_) {
         this.m_86885_(p_86879_, p_86880_, p_86881_ + 36, p_86882_, p_86883_, p_86884_);
      }

      private void m_86885_(RealmsServer p_86886_, PoseStack p_86887_, int p_86888_, int p_86889_, int p_86890_, int p_86891_) {
         if (p_86886_.f_87477_ == RealmsServer.State.UNINITIALIZED) {
            RenderSystem.m_157456_(0, RealmsMainScreen.f_86307_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            GuiComponent.m_93133_(p_86887_, p_86888_ + 10, p_86889_ + 6, 0.0F, 0.0F, 40, 20, 40, 20);
            float f = 0.5F + (1.0F + Mth.m_14031_((float)RealmsMainScreen.this.f_86293_ * 0.25F)) * 0.25F;
            int k2 = -16777216 | (int)(127.0F * f) << 16 | (int)(255.0F * f) << 8 | (int)(127.0F * f);
            GuiComponent.m_93215_(p_86887_, RealmsMainScreen.this.f_96547_, RealmsMainScreen.f_86238_, p_86888_ + 10 + 40 + 75, p_86889_ + 12, k2);
         } else {
            int i = 225;
            int j = 2;
            if (p_86886_.f_87482_) {
               RealmsMainScreen.this.m_86576_(p_86887_, p_86888_ + 225 - 14, p_86889_ + 2, p_86890_, p_86891_);
            } else if (p_86886_.f_87477_ == RealmsServer.State.CLOSED) {
               RealmsMainScreen.this.m_86626_(p_86887_, p_86888_ + 225 - 14, p_86889_ + 2, p_86890_, p_86891_);
            } else if (RealmsMainScreen.this.m_86683_(p_86886_) && p_86886_.f_87484_ < 7) {
               RealmsMainScreen.this.m_86537_(p_86887_, p_86888_ + 225 - 14, p_86889_ + 2, p_86890_, p_86891_, p_86886_.f_87484_);
            } else if (p_86886_.f_87477_ == RealmsServer.State.OPEN) {
               RealmsMainScreen.this.m_86601_(p_86887_, p_86888_ + 225 - 14, p_86889_ + 2, p_86890_, p_86891_);
            }

            if (!RealmsMainScreen.this.m_86683_(p_86886_) && !RealmsMainScreen.f_86256_) {
               RealmsMainScreen.this.m_86648_(p_86887_, p_86888_ + 225, p_86889_ + 2, p_86890_, p_86891_);
            } else {
               RealmsMainScreen.this.m_86661_(p_86887_, p_86888_ + 225, p_86889_ + 2, p_86890_, p_86891_);
            }

            if (!"0".equals(p_86886_.f_87490_.f_87579_)) {
               String s = ChatFormatting.GRAY + p_86886_.f_87490_.f_87579_;
               RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, s, (float)(p_86888_ + 207 - RealmsMainScreen.this.f_96547_.m_92895_(s)), (float)(p_86889_ + 3), 8421504);
               if (p_86890_ >= p_86888_ + 207 - RealmsMainScreen.this.f_96547_.m_92895_(s) && p_86890_ <= p_86888_ + 207 && p_86891_ >= p_86889_ + 1 && p_86891_ <= p_86889_ + 10 && p_86891_ < RealmsMainScreen.this.f_96544_ - 40 && p_86891_ > 32 && !RealmsMainScreen.this.m_86528_()) {
                  RealmsMainScreen.this.m_86526_(new TextComponent(p_86886_.f_87490_.f_87580_));
               }
            }

            if (RealmsMainScreen.this.m_86683_(p_86886_) && p_86886_.f_87482_) {
               RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
               RenderSystem.m_69478_();
               RenderSystem.m_157456_(0, RealmsMainScreen.f_86234_);
               RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
               Component component;
               Component component1;
               if (p_86886_.f_87483_) {
                  component = RealmsMainScreen.f_86241_;
                  component1 = RealmsMainScreen.f_86242_;
               } else {
                  component = RealmsMainScreen.f_86239_;
                  component1 = RealmsMainScreen.f_86240_;
               }

               int l = RealmsMainScreen.this.f_96547_.m_92852_(component1) + 17;
               int i1 = 16;
               int j1 = p_86888_ + RealmsMainScreen.this.f_96547_.m_92852_(component) + 8;
               int k1 = p_86889_ + 13;
               boolean flag = false;
               if (p_86890_ >= j1 && p_86890_ < j1 + l && p_86891_ > k1 && p_86891_ <= k1 + 16 && p_86891_ < RealmsMainScreen.this.f_96544_ - 40 && p_86891_ > 32 && !RealmsMainScreen.this.m_86528_()) {
                  flag = true;
                  RealmsMainScreen.this.f_86267_ = RealmsMainScreen.HoveredElement.EXPIRED;
               }

               int l1 = flag ? 2 : 1;
               GuiComponent.m_93133_(p_86887_, j1, k1, 0.0F, (float)(46 + l1 * 20), l / 2, 8, 256, 256);
               GuiComponent.m_93133_(p_86887_, j1 + l / 2, k1, (float)(200 - l / 2), (float)(46 + l1 * 20), l / 2, 8, 256, 256);
               GuiComponent.m_93133_(p_86887_, j1, k1 + 8, 0.0F, (float)(46 + l1 * 20 + 12), l / 2, 8, 256, 256);
               GuiComponent.m_93133_(p_86887_, j1 + l / 2, k1 + 8, (float)(200 - l / 2), (float)(46 + l1 * 20 + 12), l / 2, 8, 256, 256);
               RenderSystem.m_69461_();
               int i2 = p_86889_ + 11 + 5;
               int j2 = flag ? 16777120 : 16777215;
               RealmsMainScreen.this.f_96547_.m_92889_(p_86887_, component, (float)(p_86888_ + 2), (float)(i2 + 1), 15553363);
               GuiComponent.m_93215_(p_86887_, RealmsMainScreen.this.f_96547_, component1, j1 + l / 2, i2 + 1, j2);
            } else {
               if (p_86886_.f_87485_ == RealmsServer.WorldType.MINIGAME) {
                  int l2 = 13413468;
                  int k = RealmsMainScreen.this.f_96547_.m_92852_(RealmsMainScreen.f_86243_);
                  RealmsMainScreen.this.f_96547_.m_92889_(p_86887_, RealmsMainScreen.f_86243_, (float)(p_86888_ + 2), (float)(p_86889_ + 12), 13413468);
                  RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, p_86886_.m_87517_(), (float)(p_86888_ + 2 + k), (float)(p_86889_ + 12), 7105644);
               } else {
                  RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, p_86886_.m_87494_(), (float)(p_86888_ + 2), (float)(p_86889_ + 12), 7105644);
               }

               if (!RealmsMainScreen.this.m_86683_(p_86886_)) {
                  RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, p_86886_.f_87478_, (float)(p_86888_ + 2), (float)(p_86889_ + 12 + 11), 5000268);
               }
            }

            RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, p_86886_.m_87512_(), (float)(p_86888_ + 2), (float)(p_86889_ + 1), 16777215);
            RealmsTextureManager.m_90187_(p_86886_.f_87479_, () -> {
               RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
               GuiComponent.m_93160_(p_86887_, p_86888_ - 36, p_86889_, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
               GuiComponent.m_93160_(p_86887_, p_86888_ - 36, p_86889_, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
            });
         }
      }

      public Component m_142172_() {
         return (Component)(this.f_86853_.f_87477_ == RealmsServer.State.UNINITIALIZED ? RealmsMainScreen.f_167175_ : new TranslatableComponent("narrator.select", this.f_86853_.f_87475_));
      }
   }

   @OnlyIn(Dist.CLIENT)
   class ShowPopupButton extends Button {
      public ShowPopupButton() {
         super(RealmsMainScreen.this.f_96543_ - 37, 6, 20, 20, new TranslatableComponent("mco.selectServer.info"), null);
      }

      @Override
      public void m_5691_() {
            RealmsMainScreen.this.f_86295_ = !RealmsMainScreen.this.f_86295_;
      }

      public void m_6303_(PoseStack p_86899_, int p_86900_, int p_86901_, float p_86902_) {
         RealmsMainScreen.this.m_86417_(p_86899_, p_86900_, p_86901_, this.f_93620_, this.f_93621_, this.m_5702_());
      }
   }

   @OnlyIn(Dist.CLIENT)
   class TrialEntry extends RealmsMainScreen.Entry {
      public void m_6311_(PoseStack p_86921_, int p_86922_, int p_86923_, int p_86924_, int p_86925_, int p_86926_, int p_86927_, int p_86928_, boolean p_86929_, float p_86930_) {
         this.m_86913_(p_86921_, p_86922_, p_86924_, p_86923_, p_86927_, p_86928_);
      }

      public boolean m_6375_(double p_86910_, double p_86911_, int p_86912_) {
         RealmsMainScreen.this.f_86295_ = true;
         return true;
      }

      private void m_86913_(PoseStack p_86914_, int p_86915_, int p_86916_, int p_86917_, int p_86918_, int p_86919_) {
         int i = p_86917_ + 8;
         int j = 0;
         boolean flag = false;
         if (p_86916_ <= p_86918_ && p_86918_ <= (int)RealmsMainScreen.this.f_86283_.m_93517_() && p_86917_ <= p_86919_ && p_86919_ <= p_86917_ + 32) {
            flag = true;
         }

         int k = 8388479;
         if (flag && !RealmsMainScreen.this.m_86528_()) {
            k = 6077788;
         }

         for(Component component : RealmsMainScreen.f_86237_) {
            GuiComponent.m_93215_(p_86914_, RealmsMainScreen.this.f_96547_, component, RealmsMainScreen.this.f_96543_ / 2, i + j, k);
            j += 10;
         }

      }

      public Component m_142172_() {
         return RealmsMainScreen.f_167173_;
      }
   }
}