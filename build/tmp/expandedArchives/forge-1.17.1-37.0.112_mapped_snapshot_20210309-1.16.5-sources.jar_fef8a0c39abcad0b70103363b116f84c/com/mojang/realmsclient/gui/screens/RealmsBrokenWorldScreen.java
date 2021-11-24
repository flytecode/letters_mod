package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsWorldSlotButton;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.task.OpenServerTask;
import com.mojang.realmsclient.util.task.SwitchSlotTask;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsBrokenWorldScreen extends RealmsScreen {
   private static final Logger f_88283_ = LogManager.getLogger();
   private static final int f_167363_ = 80;
   private final Screen f_88284_;
   private final RealmsMainScreen f_88285_;
   private RealmsServer f_88286_;
   private final long f_88287_;
   private final Component[] f_88289_ = new Component[]{new TranslatableComponent("mco.brokenworld.message.line1"), new TranslatableComponent("mco.brokenworld.message.line2")};
   private int f_88290_;
   private int f_88291_;
   private final List<Integer> f_88292_ = Lists.newArrayList();
   private int f_88293_;

   public RealmsBrokenWorldScreen(Screen p_88296_, RealmsMainScreen p_88297_, long p_88298_, boolean p_88299_) {
      super(p_88299_ ? new TranslatableComponent("mco.brokenworld.minigame.title") : new TranslatableComponent("mco.brokenworld.title"));
      this.f_88284_ = p_88296_;
      this.f_88285_ = p_88297_;
      this.f_88287_ = p_88298_;
   }

   public void m_7856_() {
      this.f_88290_ = this.f_96543_ / 2 - 150;
      this.f_88291_ = this.f_96543_ / 2 + 190;
      this.m_142416_(new Button(this.f_88291_ - 80 + 8, m_120774_(13) - 5, 70, 20, CommonComponents.f_130660_, (p_88333_) -> {
         this.m_88351_();
      }));
      if (this.f_88286_ == null) {
         this.m_88313_(this.f_88287_);
      } else {
         this.m_88350_();
      }

      this.f_96541_.f_91068_.m_90926_(true);
   }

   public Component m_142562_() {
      return ComponentUtils.m_178433_(Stream.concat(Stream.of(this.f_96539_), Stream.of(this.f_88289_)).collect(Collectors.toList()), new TextComponent(" "));
   }

   private void m_88350_() {
      for(Entry<Integer, RealmsWorldOptions> entry : this.f_88286_.f_87481_.entrySet()) {
         int i = entry.getKey();
         boolean flag = i != this.f_88286_.f_87486_ || this.f_88286_.f_87485_ == RealmsServer.WorldType.MINIGAME;
         Button button;
         if (flag) {
            button = new Button(this.m_88301_(i), m_120774_(8), 80, 20, new TranslatableComponent("mco.brokenworld.play"), (p_88347_) -> {
               if ((this.f_88286_.f_87481_.get(i)).f_87611_) {
                  RealmsResetWorldScreen realmsresetworldscreen = new RealmsResetWorldScreen(this, this.f_88286_, new TranslatableComponent("mco.configure.world.switch.slot"), new TranslatableComponent("mco.configure.world.switch.slot.subtitle"), 10526880, CommonComponents.f_130656_, this::m_88300_, () -> {
                     this.f_96541_.m_91152_(this);
                     this.m_88300_();
                  });
                  realmsresetworldscreen.m_89343_(i);
                  realmsresetworldscreen.m_89389_(new TranslatableComponent("mco.create.world.reset.title"));
                  this.f_96541_.m_91152_(realmsresetworldscreen);
               } else {
                  this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_88284_, new SwitchSlotTask(this.f_88286_.f_87473_, i, this::m_88300_)));
               }

            });
         } else {
            button = new Button(this.m_88301_(i), m_120774_(8), 80, 20, new TranslatableComponent("mco.brokenworld.download"), (p_88339_) -> {
               Component component = new TranslatableComponent("mco.configure.world.restore.download.question.line1");
               Component component1 = new TranslatableComponent("mco.configure.world.restore.download.question.line2");
               this.f_96541_.m_91152_(new RealmsLongConfirmationScreen((p_167370_) -> {
                  if (p_167370_) {
                     this.m_88335_(i);
                  } else {
                     this.f_96541_.m_91152_(this);
                  }

               }, RealmsLongConfirmationScreen.Type.Info, component, component1, true));
            });
         }

         if (this.f_88292_.contains(i)) {
            button.f_93623_ = false;
            button.m_93666_(new TranslatableComponent("mco.brokenworld.downloaded"));
         }

         this.m_142416_(button);
         this.m_142416_(new Button(this.m_88301_(i), m_120774_(10), 80, 20, new TranslatableComponent("mco.brokenworld.reset"), (p_88309_) -> {
            RealmsResetWorldScreen realmsresetworldscreen = new RealmsResetWorldScreen(this, this.f_88286_, this::m_88300_, () -> {
               this.f_96541_.m_91152_(this);
               this.m_88300_();
            });
            if (i != this.f_88286_.f_87486_ || this.f_88286_.f_87485_ == RealmsServer.WorldType.MINIGAME) {
               realmsresetworldscreen.m_89343_(i);
            }

            this.f_96541_.m_91152_(realmsresetworldscreen);
         }));
      }

   }

   public void m_96624_() {
      ++this.f_88293_;
   }

   public void m_6305_(PoseStack p_88316_, int p_88317_, int p_88318_, float p_88319_) {
      this.m_7333_(p_88316_);
      super.m_6305_(p_88316_, p_88317_, p_88318_, p_88319_);
      m_93215_(p_88316_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 16777215);

      for(int i = 0; i < this.f_88289_.length; ++i) {
         m_93215_(p_88316_, this.f_96547_, this.f_88289_[i], this.f_96543_ / 2, m_120774_(-1) + 3 + i * 12, 10526880);
      }

      if (this.f_88286_ != null) {
         for(Entry<Integer, RealmsWorldOptions> entry : this.f_88286_.f_87481_.entrySet()) {
            if ((entry.getValue()).f_87609_ != null && (entry.getValue()).f_87608_ != -1L) {
               this.m_88320_(p_88316_, this.m_88301_(entry.getKey()), m_120774_(1) + 5, p_88317_, p_88318_, this.f_88286_.f_87486_ == entry.getKey() && !this.m_88352_(), entry.getValue().m_87626_(entry.getKey()), entry.getKey(), (entry.getValue()).f_87608_, (entry.getValue()).f_87609_, (entry.getValue()).f_87611_);
            } else {
               this.m_88320_(p_88316_, this.m_88301_(entry.getKey()), m_120774_(1) + 5, p_88317_, p_88318_, this.f_88286_.f_87486_ == entry.getKey() && !this.m_88352_(), entry.getValue().m_87626_(entry.getKey()), entry.getKey(), -1L, (String)null, (entry.getValue()).f_87611_);
            }
         }

      }
   }

   private int m_88301_(int p_88302_) {
      return this.f_88290_ + (p_88302_ - 1) * 110;
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public boolean m_7933_(int p_88304_, int p_88305_, int p_88306_) {
      if (p_88304_ == 256) {
         this.m_88351_();
         return true;
      } else {
         return super.m_7933_(p_88304_, p_88305_, p_88306_);
      }
   }

   private void m_88351_() {
      this.f_96541_.m_91152_(this.f_88284_);
   }

   private void m_88313_(long p_88314_) {
      (new Thread(() -> {
         RealmsClient realmsclient = RealmsClient.m_87169_();

         try {
            this.f_88286_ = realmsclient.m_87174_(p_88314_);
            this.m_88350_();
         } catch (RealmsServiceException realmsserviceexception) {
            f_88283_.error("Couldn't get own world");
            this.f_96541_.m_91152_(new RealmsGenericErrorScreen(Component.m_130674_(realmsserviceexception.getMessage()), this.f_88284_));
         }

      })).start();
   }

   public void m_88300_() {
      (new Thread(() -> {
         RealmsClient realmsclient = RealmsClient.m_87169_();
         if (this.f_88286_.f_87477_ == RealmsServer.State.CLOSED) {
            this.f_96541_.execute(() -> {
               this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this, new OpenServerTask(this.f_88286_, this, this.f_88285_, true, this.f_96541_)));
            });
         } else {
            try {
               RealmsServer realmsserver = realmsclient.m_87174_(this.f_88287_);
               this.f_96541_.execute(() -> {
                  this.f_88285_.m_86660_().m_86515_(realmsserver, this);
               });
            } catch (RealmsServiceException realmsserviceexception) {
               f_88283_.error("Couldn't get own world");
               this.f_96541_.execute(() -> {
                  this.f_96541_.m_91152_(this.f_88284_);
               });
            }
         }

      })).start();
   }

   private void m_88335_(int p_88336_) {
      RealmsClient realmsclient = RealmsClient.m_87169_();

      try {
         WorldDownload worlddownload = realmsclient.m_87209_(this.f_88286_.f_87473_, p_88336_);
         RealmsDownloadLatestWorldScreen realmsdownloadlatestworldscreen = new RealmsDownloadLatestWorldScreen(this, worlddownload, this.f_88286_.m_87495_(p_88336_), (p_88312_) -> {
            if (p_88312_) {
               this.f_88292_.add(p_88336_);
               this.m_169413_();
               this.m_88350_();
            } else {
               this.f_96541_.m_91152_(this);
            }

         });
         this.f_96541_.m_91152_(realmsdownloadlatestworldscreen);
      } catch (RealmsServiceException realmsserviceexception) {
         f_88283_.error("Couldn't download world data");
         this.f_96541_.m_91152_(new RealmsGenericErrorScreen(realmsserviceexception, this));
      }

   }

   private boolean m_88352_() {
      return this.f_88286_ != null && this.f_88286_.f_87485_ == RealmsServer.WorldType.MINIGAME;
   }

   private void m_88320_(PoseStack p_88321_, int p_88322_, int p_88323_, int p_88324_, int p_88325_, boolean p_88326_, String p_88327_, int p_88328_, long p_88329_, @Nullable String p_88330_, boolean p_88331_) {
      if (p_88331_) {
         RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87918_);
      } else if (p_88330_ != null && p_88329_ != -1L) {
         RealmsTextureManager.m_90190_(String.valueOf(p_88329_), p_88330_);
      } else if (p_88328_ == 1) {
         RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87919_);
      } else if (p_88328_ == 2) {
         RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87920_);
      } else if (p_88328_ == 3) {
         RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87921_);
      } else {
         RealmsTextureManager.m_90190_(String.valueOf(this.f_88286_.f_87488_), this.f_88286_.f_87489_);
      }

      if (!p_88326_) {
         RenderSystem.m_157429_(0.56F, 0.56F, 0.56F, 1.0F);
      } else if (p_88326_) {
         float f = 0.9F + 0.1F * Mth.m_14089_((float)this.f_88293_ * 0.2F);
         RenderSystem.m_157429_(f, f, f, 1.0F);
      }

      GuiComponent.m_93133_(p_88321_, p_88322_ + 3, p_88323_ + 3, 0.0F, 0.0F, 74, 74, 74, 74);
      RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87917_);
      if (p_88326_) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.m_157429_(0.56F, 0.56F, 0.56F, 1.0F);
      }

      GuiComponent.m_93133_(p_88321_, p_88322_, p_88323_, 0.0F, 0.0F, 80, 80, 80, 80);
      m_93208_(p_88321_, this.f_96547_, p_88327_, p_88322_ + 40, p_88323_ + 66, 16777215);
   }
}