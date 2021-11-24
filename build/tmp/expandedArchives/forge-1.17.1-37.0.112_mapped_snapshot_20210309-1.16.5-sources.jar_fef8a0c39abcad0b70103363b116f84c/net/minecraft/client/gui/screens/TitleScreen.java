package net.minecraft.client.gui.screens;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class TitleScreen extends Screen {
   private static final Logger f_96717_ = LogManager.getLogger();
   private static final String f_169439_ = "Demo_World";
   public static final String f_169438_ = "Copyright Mojang AB. Do not distribute!";
   public static final CubeMap f_96716_ = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
   private static final ResourceLocation f_96718_ = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
   private static final ResourceLocation f_96719_ = new ResourceLocation("textures/gui/accessibility.png");
   private final boolean f_96720_;
   @Nullable
   private String f_96721_;
   private Button f_96722_;
   private static final ResourceLocation f_96723_ = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation f_96724_ = new ResourceLocation("textures/gui/title/edition.png");
   private Screen f_96726_;
   private int f_96727_;
   private int f_96728_;
   private final PanoramaRenderer f_96729_ = new PanoramaRenderer(f_96716_);
   private final boolean f_96714_;
   private long f_96715_;
   private net.minecraftforge.client.gui.NotificationModUpdateScreen modUpdateNotification;

   public TitleScreen() {
      this(false);
   }

   public TitleScreen(boolean p_96733_) {
      super(new TranslatableComponent("narrator.screen.title"));
      this.f_96714_ = p_96733_;
      this.f_96720_ = (double)(new Random()).nextFloat() < 1.0E-4D;
   }

   private boolean m_96789_() {
      return this.f_96541_.f_91066_.f_92046_ && this.f_96726_ != null;
   }

   public void m_96624_() {
      if (this.m_96789_()) {
         this.f_96726_.m_96624_();
      }

   }

   public static CompletableFuture<Void> m_96754_(TextureManager p_96755_, Executor p_96756_) {
      return CompletableFuture.allOf(p_96755_.m_118501_(f_96723_, p_96756_), p_96755_.m_118501_(f_96724_, p_96756_), p_96755_.m_118501_(f_96718_, p_96756_), f_96716_.m_108854_(p_96755_, p_96756_));
   }

   public boolean m_7043_() {
      return false;
   }

   public boolean m_6913_() {
      return false;
   }

   protected void m_7856_() {
      if (this.f_96721_ == null) {
         this.f_96721_ = this.f_96541_.m_91310_().m_118867_();
      }

      this.f_96727_ = this.f_96547_.m_92895_("Copyright Mojang AB. Do not distribute!");
      this.f_96728_ = this.f_96543_ - this.f_96727_ - 2;
      int i = 24;
      int j = this.f_96544_ / 4 + 48;
      Button modButton = null;
      if (this.f_96541_.m_91402_()) {
         this.m_96772_(j, 24);
      } else {
         this.m_96763_(j, 24);
         modButton = this.m_142416_(new Button(this.f_96543_ / 2 - 100, j + 24 * 2, 98, 20, new TranslatableComponent("fml.menu.mods"), button -> {
            this.f_96541_.m_91152_(new net.minecraftforge.fmlclient.gui.screen.ModListScreen(this));
         }));
      }
      modUpdateNotification = net.minecraftforge.client.gui.NotificationModUpdateScreen.init(this, modButton);

      this.m_142416_(new ImageButton(this.f_96543_ / 2 - 124, j + 72 + 12, 20, 20, 0, 106, 20, Button.f_93617_, 256, 256, (p_96791_) -> {
         this.f_96541_.m_91152_(new LanguageSelectScreen(this, this.f_96541_.f_91066_, this.f_96541_.m_91102_()));
      }, new TranslatableComponent("narrator.button.language")));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, j + 72 + 12, 98, 20, new TranslatableComponent("menu.options"), (p_96788_) -> {
         this.f_96541_.m_91152_(new OptionsScreen(this, this.f_96541_.f_91066_));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 2, j + 72 + 12, 98, 20, new TranslatableComponent("menu.quit"), (p_96786_) -> {
         this.f_96541_.m_91395_();
      }));
      this.m_142416_(new ImageButton(this.f_96543_ / 2 + 104, j + 72 + 12, 20, 20, 0, 0, 20, f_96719_, 32, 64, (p_96784_) -> {
         this.f_96541_.m_91152_(new AccessibilityOptionsScreen(this, this.f_96541_.f_91066_));
      }, new TranslatableComponent("narrator.button.accessibility")));
      this.f_96541_.m_91372_(false);
      if (this.f_96541_.f_91066_.f_92046_ && this.f_96726_ == null) {
         this.f_96726_ = new RealmsNotificationsScreen();
      }

      if (this.m_96789_()) {
         this.f_96726_.m_6575_(this.f_96541_, this.f_96543_, this.f_96544_);
      }

   }

   private void m_96763_(int p_96764_, int p_96765_) {
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, p_96764_, 200, 20, new TranslatableComponent("menu.singleplayer"), (p_96781_) -> {
         this.f_96541_.m_91152_(new SelectWorldScreen(this));
      }));
      boolean flag = this.f_96541_.m_91400_();
      Button.OnTooltip button$ontooltip = flag ? Button.f_93716_ : new Button.OnTooltip() {
         private final Component f_169452_ = new TranslatableComponent("title.multiplayer.disabled");

         public void m_93752_(Button p_169458_, PoseStack p_169459_, int p_169460_, int p_169461_) {
            if (!p_169458_.f_93623_) {
               TitleScreen.this.m_96617_(p_169459_, TitleScreen.this.f_96541_.f_91062_.m_92923_(this.f_169452_, Math.max(TitleScreen.this.f_96543_ / 2 - 43, 170)), p_169460_, p_169461_);
            }

         }

         public void m_142753_(Consumer<Component> p_169456_) {
            p_169456_.accept(this.f_169452_);
         }
      };
      (this.m_142416_(new Button(this.f_96543_ / 2 - 100, p_96764_ + p_96765_ * 1, 200, 20, new TranslatableComponent("menu.multiplayer"), (p_169450_) -> {
         Screen screen = (Screen)(this.f_96541_.f_91066_.f_92083_ ? new JoinMultiplayerScreen(this) : new SafetyScreen(this));
         this.f_96541_.m_91152_(screen);
      }, button$ontooltip))).f_93623_ = flag;
      (this.m_142416_(new Button(this.f_96543_ / 2 + 2, p_96764_ + p_96765_ * 2, 98, 20, new TranslatableComponent("menu.online"), (p_96771_) -> {
         this.m_96793_();
      }, button$ontooltip))).f_93623_ = flag;
   }

   private void m_96772_(int p_96773_, int p_96774_) {
      boolean flag = this.m_96792_();
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, p_96773_, 200, 20, new TranslatableComponent("menu.playdemo"), (p_169444_) -> {
         if (flag) {
            this.f_96541_.m_91200_("Demo_World");
         } else {
            RegistryAccess.RegistryHolder registryaccess$registryholder = RegistryAccess.m_123086_();
            this.f_96541_.m_91202_("Demo_World", MinecraftServer.f_129743_, registryaccess$registryholder, WorldGenSettings.m_64645_(registryaccess$registryholder));
         }

      }));
      this.f_96722_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, p_96773_ + p_96774_ * 1, 200, 20, new TranslatableComponent("menu.resetdemo"), (p_169441_) -> {
         LevelStorageSource levelstoragesource = this.f_96541_.m_91392_();

         try {
            LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = levelstoragesource.m_78260_("Demo_World");

            try {
               LevelSummary levelsummary = levelstoragesource$levelstorageaccess.m_78308_();
               if (levelsummary != null) {
                  this.f_96541_.m_91152_(new ConfirmScreen(this::m_96777_, new TranslatableComponent("selectWorld.deleteQuestion"), new TranslatableComponent("selectWorld.deleteWarning", levelsummary.m_78361_()), new TranslatableComponent("selectWorld.deleteButton"), CommonComponents.f_130656_));
               }
            } catch (Throwable throwable1) {
               if (levelstoragesource$levelstorageaccess != null) {
                  try {
                     levelstoragesource$levelstorageaccess.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (levelstoragesource$levelstorageaccess != null) {
               levelstoragesource$levelstorageaccess.close();
            }
         } catch (IOException ioexception) {
            SystemToast.m_94852_(this.f_96541_, "Demo_World");
            f_96717_.warn("Failed to access demo world", (Throwable)ioexception);
         }

      }));
      this.f_96722_.f_93623_ = flag;
   }

   private boolean m_96792_() {
      try {
         LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.f_96541_.m_91392_().m_78260_("Demo_World");

         boolean flag;
         try {
            flag = levelstoragesource$levelstorageaccess.m_78308_() != null;
         } catch (Throwable throwable1) {
            if (levelstoragesource$levelstorageaccess != null) {
               try {
                  levelstoragesource$levelstorageaccess.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (levelstoragesource$levelstorageaccess != null) {
            levelstoragesource$levelstorageaccess.close();
         }

         return flag;
      } catch (IOException ioexception) {
         SystemToast.m_94852_(this.f_96541_, "Demo_World");
         f_96717_.warn("Failed to read demo world data", (Throwable)ioexception);
         return false;
      }
   }

   private void m_96793_() {
      this.f_96541_.m_91152_(new RealmsMainScreen(this));
   }

   public void m_6305_(PoseStack p_96739_, int p_96740_, int p_96741_, float p_96742_) {
      if (this.f_96715_ == 0L && this.f_96714_) {
         this.f_96715_ = Util.m_137550_();
      }

      float f = this.f_96714_ ? (float)(Util.m_137550_() - this.f_96715_) / 1000.0F : 1.0F;
      this.f_96729_.m_110003_(p_96742_, Mth.m_14036_(f, 0.0F, 1.0F));
      int i = 274;
      int j = this.f_96543_ / 2 - 137;
      int k = 30;
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_96718_);
      RenderSystem.m_69478_();
      RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, this.f_96714_ ? (float)Mth.m_14167_(Mth.m_14036_(f, 0.0F, 1.0F)) : 1.0F);
      m_93160_(p_96739_, 0, 0, this.f_96543_, this.f_96544_, 0.0F, 0.0F, 16, 128, 16, 128);
      float f1 = this.f_96714_ ? Mth.m_14036_(f - 1.0F, 0.0F, 1.0F) : 1.0F;
      int l = Mth.m_14167_(f1 * 255.0F) << 24;
      if ((l & -67108864) != 0) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, f_96723_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, f1);
         if (this.f_96720_) {
            this.m_93101_(j, 30, (p_169447_, p_169448_) -> {
               this.m_93228_(p_96739_, p_169447_ + 0, p_169448_, 0, 0, 99, 44);
               this.m_93228_(p_96739_, p_169447_ + 99, p_169448_, 129, 0, 27, 44);
               this.m_93228_(p_96739_, p_169447_ + 99 + 26, p_169448_, 126, 0, 3, 44);
               this.m_93228_(p_96739_, p_169447_ + 99 + 26 + 3, p_169448_, 99, 0, 26, 44);
               this.m_93228_(p_96739_, p_169447_ + 155, p_169448_, 0, 45, 155, 44);
            });
         } else {
            this.m_93101_(j, 30, (p_96768_, p_96769_) -> {
               this.m_93228_(p_96739_, p_96768_ + 0, p_96769_, 0, 0, 155, 44);
               this.m_93228_(p_96739_, p_96768_ + 155, p_96769_, 0, 45, 155, 44);
            });
         }

         RenderSystem.m_157456_(0, f_96724_);
         m_93133_(p_96739_, j + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);
         net.minecraftforge.client.ForgeHooksClient.renderMainMenu(this, p_96739_, this.f_96547_, this.f_96543_, this.f_96544_, l);
         if (this.f_96721_ != null) {
            p_96739_.m_85836_();
            p_96739_.m_85837_((double)(this.f_96543_ / 2 + 90), 70.0D, 0.0D);
            p_96739_.m_85845_(Vector3f.f_122227_.m_122240_(-20.0F));
            float f2 = 1.8F - Mth.m_14154_(Mth.m_14031_((float)(Util.m_137550_() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
            f2 = f2 * 100.0F / (float)(this.f_96547_.m_92895_(this.f_96721_) + 32);
            p_96739_.m_85841_(f2, f2, f2);
            m_93208_(p_96739_, this.f_96547_, this.f_96721_, 0, -8, 16776960 | l);
            p_96739_.m_85849_();
         }

         String s = "Minecraft " + SharedConstants.m_136187_().getName();
         if (this.f_96541_.m_91402_()) {
            s = s + " Demo";
         } else {
            s = s + ("release".equalsIgnoreCase(this.f_96541_.m_91389_()) ? "" : "/" + this.f_96541_.m_91389_());
         }

         if (this.f_96541_.m_91361_()) {
            s = s + I18n.m_118938_("menu.modded");
         }

         net.minecraftforge.fmllegacy.BrandingControl.forEachLine(true, true, (brdline, brd) ->
            m_93236_(p_96739_, this.f_96547_, brd, 2, this.f_96544_ - ( 10 + brdline * (this.f_96547_.f_92710_ + 1)), 16777215 | l)
         );

         net.minecraftforge.fmllegacy.BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
            m_93236_(p_96739_, this.f_96547_, brd, this.f_96543_ - f_96547_.m_92895_(brd), this.f_96544_ - (10 + (brdline + 1) * ( this.f_96547_.f_92710_ + 1)), 16777215 | l)
         );

         m_93236_(p_96739_, this.f_96547_, "Copyright Mojang AB. Do not distribute!", this.f_96728_, this.f_96544_ - 10, 16777215 | l);
         if (p_96740_ > this.f_96728_ && p_96740_ < this.f_96728_ + this.f_96727_ && p_96741_ > this.f_96544_ - 10 && p_96741_ < this.f_96544_) {
            m_93172_(p_96739_, this.f_96728_, this.f_96544_ - 1, this.f_96728_ + this.f_96727_, this.f_96544_, 16777215 | l);
         }

         for(GuiEventListener guieventlistener : this.m_6702_()) {
            if (guieventlistener instanceof AbstractWidget) {
               ((AbstractWidget)guieventlistener).m_93650_(f1);
            }
         }

         super.m_6305_(p_96739_, p_96740_, p_96741_, p_96742_);
         if (this.m_96789_() && f1 >= 1.0F) {
            this.f_96726_.m_6305_(p_96739_, p_96740_, p_96741_, p_96742_);
         }
         modUpdateNotification.m_6305_(p_96739_, p_96740_, p_96741_, p_96742_);

      }
   }

   public boolean m_6375_(double p_96735_, double p_96736_, int p_96737_) {
      if (super.m_6375_(p_96735_, p_96736_, p_96737_)) {
         return true;
      } else if (this.m_96789_() && this.f_96726_.m_6375_(p_96735_, p_96736_, p_96737_)) {
         return true;
      } else {
         if (p_96735_ > (double)this.f_96728_ && p_96735_ < (double)(this.f_96728_ + this.f_96727_) && p_96736_ > (double)(this.f_96544_ - 10) && p_96736_ < (double)this.f_96544_) {
            this.f_96541_.m_91152_(new WinScreen(false, Runnables.doNothing()));
         }

         return false;
      }
   }

   public void m_7861_() {
      if (this.f_96726_ != null) {
         this.f_96726_.m_7861_();
      }

   }

   private void m_96777_(boolean p_96778_) {
      if (p_96778_) {
         try {
            LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.f_96541_.m_91392_().m_78260_("Demo_World");

            try {
               levelstoragesource$levelstorageaccess.m_78311_();
            } catch (Throwable throwable1) {
               if (levelstoragesource$levelstorageaccess != null) {
                  try {
                     levelstoragesource$levelstorageaccess.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (levelstoragesource$levelstorageaccess != null) {
               levelstoragesource$levelstorageaccess.close();
            }
         } catch (IOException ioexception) {
            SystemToast.m_94866_(this.f_96541_, "Demo_World");
            f_96717_.warn("Failed to delete demo world", (Throwable)ioexception);
         }
      }

      this.f_96541_.m_91152_(this);
   }
}
