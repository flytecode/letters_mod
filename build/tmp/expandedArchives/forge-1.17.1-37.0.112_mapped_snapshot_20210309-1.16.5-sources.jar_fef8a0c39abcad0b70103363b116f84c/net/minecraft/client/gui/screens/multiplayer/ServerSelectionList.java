package net.minecraft.client.gui.screens.multiplayer;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.server.LanServer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ServerSelectionList extends ObjectSelectionList<ServerSelectionList.Entry> {
   static final Logger f_99756_ = LogManager.getLogger();
   static final ThreadPoolExecutor f_99757_ = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_99756_)).build());
   static final ResourceLocation f_99758_ = new ResourceLocation("textures/misc/unknown_server.png");
   static final ResourceLocation f_99759_ = new ResourceLocation("textures/gui/server_selection.png");
   static final Component f_99760_ = new TranslatableComponent("lanServer.scanning");
   static final Component f_99761_ = (new TranslatableComponent("multiplayer.status.cannot_resolve")).m_130940_(ChatFormatting.DARK_RED);
   static final Component f_99762_ = (new TranslatableComponent("multiplayer.status.cannot_connect")).m_130940_(ChatFormatting.DARK_RED);
   static final Component f_99763_ = new TranslatableComponent("multiplayer.status.incompatible");
   static final Component f_99764_ = new TranslatableComponent("multiplayer.status.no_connection");
   static final Component f_99765_ = new TranslatableComponent("multiplayer.status.pinging");
   private final JoinMultiplayerScreen f_99766_;
   private final List<ServerSelectionList.OnlineServerEntry> f_99767_ = Lists.newArrayList();
   private final ServerSelectionList.Entry f_99768_ = new ServerSelectionList.LANHeader();
   private final List<ServerSelectionList.NetworkServerEntry> f_99755_ = Lists.newArrayList();

   public ServerSelectionList(JoinMultiplayerScreen p_99771_, Minecraft p_99772_, int p_99773_, int p_99774_, int p_99775_, int p_99776_, int p_99777_) {
      super(p_99772_, p_99773_, p_99774_, p_99775_, p_99776_, p_99777_);
      this.f_99766_ = p_99771_;
   }

   private void m_99780_() {
      this.m_93516_();
      this.f_99767_.forEach((p_169979_) -> {
         this.m_7085_(p_169979_);
      });
      this.m_7085_(this.f_99768_);
      this.f_99755_.forEach((p_169976_) -> {
         this.m_7085_(p_169976_);
      });
   }

   public void m_6987_(@Nullable ServerSelectionList.Entry p_99790_) {
      super.m_6987_(p_99790_);
      this.f_99766_.m_99730_();
   }

   public boolean m_7933_(int p_99782_, int p_99783_, int p_99784_) {
      ServerSelectionList.Entry serverselectionlist$entry = this.m_93511_();
      return serverselectionlist$entry != null ? serverselectionlist$entry.m_7933_(p_99782_, p_99783_, p_99784_) : super.m_7933_(p_99782_, p_99783_, p_99784_);
   }

   protected void m_6778_(AbstractSelectionList.SelectionDirection p_99788_) {
      this.m_93464_(p_99788_, (p_169973_) -> {
         return !(p_169973_ instanceof ServerSelectionList.LANHeader);
      });
   }

   public void m_99797_(ServerList p_99798_) {
      this.f_99767_.clear();

      for(int i = 0; i < p_99798_.m_105445_(); ++i) {
         this.f_99767_.add(new ServerSelectionList.OnlineServerEntry(this.f_99766_, p_99798_.m_105432_(i)));
      }

      this.m_99780_();
   }

   public void m_99799_(List<LanServer> p_99800_) {
      this.f_99755_.clear();

      for(LanServer lanserver : p_99800_) {
         this.f_99755_.add(new ServerSelectionList.NetworkServerEntry(this.f_99766_, lanserver));
      }

      this.m_99780_();
   }

   protected int m_5756_() {
      return super.m_5756_() + 30;
   }

   public int m_5759_() {
      return super.m_5759_() + 85;
   }

   protected boolean m_5694_() {
      return this.f_99766_.m_7222_() == this;
   }

   @OnlyIn(Dist.CLIENT)
   public abstract static class Entry extends ObjectSelectionList.Entry<ServerSelectionList.Entry> {
   }

   @OnlyIn(Dist.CLIENT)
   public static class LANHeader extends ServerSelectionList.Entry {
      private final Minecraft f_99815_ = Minecraft.m_91087_();

      public void m_6311_(PoseStack p_99818_, int p_99819_, int p_99820_, int p_99821_, int p_99822_, int p_99823_, int p_99824_, int p_99825_, boolean p_99826_, float p_99827_) {
         int i = p_99820_ + p_99823_ / 2 - 9 / 2;
         this.f_99815_.f_91062_.m_92889_(p_99818_, ServerSelectionList.f_99760_, (float)(this.f_99815_.f_91080_.f_96543_ / 2 - this.f_99815_.f_91062_.m_92852_(ServerSelectionList.f_99760_) / 2), (float)i, 16777215);
         String s;
         switch((int)(Util.m_137550_() / 300L % 4L)) {
         case 0:
         default:
            s = "O o o";
            break;
         case 1:
         case 3:
            s = "o O o";
            break;
         case 2:
            s = "o o O";
         }

         this.f_99815_.f_91062_.m_92883_(p_99818_, s, (float)(this.f_99815_.f_91080_.f_96543_ / 2 - this.f_99815_.f_91062_.m_92895_(s) / 2), (float)(i + 9), 8421504);
      }

      public Component m_142172_() {
         return TextComponent.f_131282_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class NetworkServerEntry extends ServerSelectionList.Entry {
      private static final int f_169981_ = 32;
      private static final Component f_99830_ = new TranslatableComponent("lanServer.title");
      private static final Component f_99831_ = new TranslatableComponent("selectServer.hiddenAddress");
      private final JoinMultiplayerScreen f_99832_;
      protected final Minecraft f_99828_;
      protected final LanServer f_99829_;
      private long f_99833_;

      protected NetworkServerEntry(JoinMultiplayerScreen p_99836_, LanServer p_99837_) {
         this.f_99832_ = p_99836_;
         this.f_99829_ = p_99837_;
         this.f_99828_ = Minecraft.m_91087_();
      }

      public void m_6311_(PoseStack p_99844_, int p_99845_, int p_99846_, int p_99847_, int p_99848_, int p_99849_, int p_99850_, int p_99851_, boolean p_99852_, float p_99853_) {
         this.f_99828_.f_91062_.m_92889_(p_99844_, f_99830_, (float)(p_99847_ + 32 + 3), (float)(p_99846_ + 1), 16777215);
         this.f_99828_.f_91062_.m_92883_(p_99844_, this.f_99829_.m_120078_(), (float)(p_99847_ + 32 + 3), (float)(p_99846_ + 12), 8421504);
         if (this.f_99828_.f_91066_.f_92124_) {
            this.f_99828_.f_91062_.m_92889_(p_99844_, f_99831_, (float)(p_99847_ + 32 + 3), (float)(p_99846_ + 12 + 11), 3158064);
         } else {
            this.f_99828_.f_91062_.m_92883_(p_99844_, this.f_99829_.m_120079_(), (float)(p_99847_ + 32 + 3), (float)(p_99846_ + 12 + 11), 3158064);
         }

      }

      public boolean m_6375_(double p_99840_, double p_99841_, int p_99842_) {
         this.f_99832_.m_99700_(this);
         if (Util.m_137550_() - this.f_99833_ < 250L) {
            this.f_99832_.m_99729_();
         }

         this.f_99833_ = Util.m_137550_();
         return false;
      }

      public LanServer m_99838_() {
         return this.f_99829_;
      }

      public Component m_142172_() {
         return new TranslatableComponent("narrator.select", (new TextComponent("")).m_7220_(f_99830_).m_130946_(" ").m_130946_(this.f_99829_.m_120078_()));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public class OnlineServerEntry extends ServerSelectionList.Entry {
      private static final int f_169983_ = 32;
      private static final int f_169984_ = 32;
      private static final int f_169985_ = 0;
      private static final int f_169986_ = 32;
      private static final int f_169987_ = 64;
      private static final int f_169988_ = 96;
      private static final int f_169989_ = 0;
      private static final int f_169990_ = 32;
      private final JoinMultiplayerScreen f_99855_;
      private final Minecraft f_99856_;
      private final ServerData f_99857_;
      private final ResourceLocation f_99858_;
      private String f_99859_;
      @Nullable
      private DynamicTexture f_99860_;
      private long f_99861_;

      protected OnlineServerEntry(JoinMultiplayerScreen p_99864_, ServerData p_99865_) {
         this.f_99855_ = p_99864_;
         this.f_99857_ = p_99865_;
         this.f_99856_ = Minecraft.m_91087_();
         this.f_99858_ = new ResourceLocation("servers/" + Hashing.sha1().hashUnencodedChars(p_99865_.f_105363_) + "/icon");
         AbstractTexture abstracttexture = this.f_99856_.m_91097_().m_174786_(this.f_99858_, MissingTextureAtlasSprite.m_118080_());
         if (abstracttexture != MissingTextureAtlasSprite.m_118080_() && abstracttexture instanceof DynamicTexture) {
            this.f_99860_ = (DynamicTexture)abstracttexture;
         }

      }

      public void m_6311_(PoseStack p_99879_, int p_99880_, int p_99881_, int p_99882_, int p_99883_, int p_99884_, int p_99885_, int p_99886_, boolean p_99887_, float p_99888_) {
         if (!this.f_99857_.f_105369_) {
            this.f_99857_.f_105369_ = true;
            this.f_99857_.f_105366_ = -2L;
            this.f_99857_.f_105365_ = TextComponent.f_131282_;
            this.f_99857_.f_105364_ = TextComponent.f_131282_;
            ServerSelectionList.f_99757_.submit(() -> {
               try {
                  this.f_99855_.m_99731_().m_105459_(this.f_99857_, () -> {
                     this.f_99856_.execute(this::m_99866_);
                  });
               } catch (UnknownHostException unknownhostexception) {
                  this.f_99857_.f_105366_ = -1L;
                  this.f_99857_.f_105365_ = ServerSelectionList.f_99761_;
               } catch (Exception exception) {
                  this.f_99857_.f_105366_ = -1L;
                  this.f_99857_.f_105365_ = ServerSelectionList.f_99762_;
               }

            });
         }

         boolean flag = this.f_99857_.f_105367_ != SharedConstants.m_136187_().getProtocolVersion();
         this.f_99856_.f_91062_.m_92883_(p_99879_, this.f_99857_.f_105362_, (float)(p_99882_ + 32 + 3), (float)(p_99881_ + 1), 16777215);
         List<FormattedCharSequence> list = this.f_99856_.f_91062_.m_92923_(this.f_99857_.f_105365_, p_99883_ - 32 - 2);

         for(int i = 0; i < Math.min(list.size(), 2); ++i) {
            this.f_99856_.f_91062_.m_92877_(p_99879_, list.get(i), (float)(p_99882_ + 32 + 3), (float)(p_99881_ + 12 + 9 * i), 8421504);
         }

         Component component1 = (Component)(flag ? this.f_99857_.f_105368_.m_6881_().m_130940_(ChatFormatting.RED) : this.f_99857_.f_105364_);
         int j = this.f_99856_.f_91062_.m_92852_(component1);
         this.f_99856_.f_91062_.m_92889_(p_99879_, component1, (float)(p_99882_ + p_99883_ - j - 15 - 2), (float)(p_99881_ + 1), 8421504);
         int k = 0;
         int l;
         List<Component> list1;
         Component component;
         if (flag) {
            l = 5;
            component = ServerSelectionList.f_99763_;
            list1 = this.f_99857_.f_105370_;
         } else if (this.f_99857_.f_105369_ && this.f_99857_.f_105366_ != -2L) {
            if (this.f_99857_.f_105366_ < 0L) {
               l = 5;
            } else if (this.f_99857_.f_105366_ < 150L) {
               l = 0;
            } else if (this.f_99857_.f_105366_ < 300L) {
               l = 1;
            } else if (this.f_99857_.f_105366_ < 600L) {
               l = 2;
            } else if (this.f_99857_.f_105366_ < 1000L) {
               l = 3;
            } else {
               l = 4;
            }

            if (this.f_99857_.f_105366_ < 0L) {
               component = ServerSelectionList.f_99764_;
               list1 = Collections.emptyList();
            } else {
               component = new TranslatableComponent("multiplayer.status.ping", this.f_99857_.f_105366_);
               list1 = this.f_99857_.f_105370_;
            }
         } else {
            k = 1;
            l = (int)(Util.m_137550_() / 100L + (long)(p_99880_ * 2) & 7L);
            if (l > 4) {
               l = 8 - l;
            }

            component = ServerSelectionList.f_99765_;
            list1 = Collections.emptyList();
         }

         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157456_(0, GuiComponent.f_93098_);
         GuiComponent.m_93133_(p_99879_, p_99882_ + p_99883_ - 15, p_99881_, (float)(k * 10), (float)(176 + l * 8), 10, 8, 256, 256);
         String s = this.f_99857_.m_105388_();
         if (!Objects.equals(s, this.f_99859_)) {
            if (this.m_99896_(s)) {
               this.f_99859_ = s;
            } else {
               this.f_99857_.m_105383_((String)null);
               this.m_99866_();
            }
         }

         if (this.f_99860_ == null) {
            this.m_99889_(p_99879_, p_99882_, p_99881_, ServerSelectionList.f_99758_);
         } else {
            this.m_99889_(p_99879_, p_99882_, p_99881_, this.f_99858_);
         }

         int i1 = p_99885_ - p_99882_;
         int j1 = p_99886_ - p_99881_;
         if (i1 >= p_99883_ - 15 && i1 <= p_99883_ - 5 && j1 >= 0 && j1 <= 8) {
            this.f_99855_.m_99707_(Collections.singletonList(component));
         } else if (i1 >= p_99883_ - j - 15 - 2 && i1 <= p_99883_ - 15 - 2 && j1 >= 0 && j1 <= 8) {
            this.f_99855_.m_99707_(list1);
         }

         net.minecraftforge.client.ForgeHooksClient.drawForgePingInfo(this.f_99855_, f_99857_, p_99879_, p_99882_, p_99881_, p_99883_, i1, j1);

         if (this.f_99856_.f_91066_.f_92051_ || p_99887_) {
            RenderSystem.m_157456_(0, ServerSelectionList.f_99759_);
            GuiComponent.m_93172_(p_99879_, p_99882_, p_99881_, p_99882_ + 32, p_99881_ + 32, -1601138544);
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            int k1 = p_99885_ - p_99882_;
            int l1 = p_99886_ - p_99881_;
            if (this.m_99899_()) {
               if (k1 < 32 && k1 > 16) {
                  GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (p_99880_ > 0) {
               if (k1 < 16 && l1 < 16) {
                  GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 96.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 96.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (p_99880_ < this.f_99855_.m_99732_().m_105445_() - 1) {
               if (k1 < 16 && l1 > 16) {
                  GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 64.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 64.0F, 0.0F, 32, 32, 256, 256);
               }
            }
         }

      }

      public void m_99866_() {
         this.f_99855_.m_99732_().m_105442_();
      }

      protected void m_99889_(PoseStack p_99890_, int p_99891_, int p_99892_, ResourceLocation p_99893_) {
         RenderSystem.m_157456_(0, p_99893_);
         RenderSystem.m_69478_();
         GuiComponent.m_93133_(p_99890_, p_99891_, p_99892_, 0.0F, 0.0F, 32, 32, 32, 32);
         RenderSystem.m_69461_();
      }

      private boolean m_99899_() {
         return true;
      }

      private boolean m_99896_(@Nullable String p_99897_) {
         if (p_99897_ == null) {
            this.f_99856_.m_91097_().m_118513_(this.f_99858_);
            if (this.f_99860_ != null && this.f_99860_.m_117991_() != null) {
               this.f_99860_.m_117991_().close();
            }

            this.f_99860_ = null;
         } else {
            try {
               NativeImage nativeimage = NativeImage.m_85060_(p_99897_);
               Validate.validState(nativeimage.m_84982_() == 64, "Must be 64 pixels wide");
               Validate.validState(nativeimage.m_85084_() == 64, "Must be 64 pixels high");
               if (this.f_99860_ == null) {
                  this.f_99860_ = new DynamicTexture(nativeimage);
               } else {
                  this.f_99860_.m_117988_(nativeimage);
                  this.f_99860_.m_117985_();
               }

               this.f_99856_.m_91097_().m_118495_(this.f_99858_, this.f_99860_);
            } catch (Throwable throwable) {
               ServerSelectionList.f_99756_.error("Invalid icon for server {} ({})", this.f_99857_.f_105362_, this.f_99857_.f_105363_, throwable);
               return false;
            }
         }

         return true;
      }

      public boolean m_7933_(int p_99875_, int p_99876_, int p_99877_) {
         if (Screen.m_96638_()) {
            ServerSelectionList serverselectionlist = this.f_99855_.f_99673_;
            int i = serverselectionlist.m_6702_().indexOf(this);
            if (p_99875_ == 264 && i < this.f_99855_.m_99732_().m_105445_() - 1 || p_99875_ == 265 && i > 0) {
               this.m_99871_(i, p_99875_ == 264 ? i + 1 : i - 1);
               return true;
            }
         }

         return super.m_7933_(p_99875_, p_99876_, p_99877_);
      }

      private void m_99871_(int p_99872_, int p_99873_) {
         this.f_99855_.m_99732_().m_105434_(p_99872_, p_99873_);
         this.f_99855_.f_99673_.m_99797_(this.f_99855_.m_99732_());
         ServerSelectionList.Entry serverselectionlist$entry = this.f_99855_.f_99673_.m_6702_().get(p_99873_);
         this.f_99855_.f_99673_.m_6987_(serverselectionlist$entry);
         ServerSelectionList.this.m_93498_(serverselectionlist$entry);
      }

      public boolean m_6375_(double p_99868_, double p_99869_, int p_99870_) {
         double d0 = p_99868_ - (double)ServerSelectionList.this.m_5747_();
         double d1 = p_99869_ - (double)ServerSelectionList.this.m_7610_(ServerSelectionList.this.m_6702_().indexOf(this));
         if (d0 <= 32.0D) {
            if (d0 < 32.0D && d0 > 16.0D && this.m_99899_()) {
               this.f_99855_.m_99700_(this);
               this.f_99855_.m_99729_();
               return true;
            }

            int i = this.f_99855_.f_99673_.m_6702_().indexOf(this);
            if (d0 < 16.0D && d1 < 16.0D && i > 0) {
               this.m_99871_(i, i - 1);
               return true;
            }

            if (d0 < 16.0D && d1 > 16.0D && i < this.f_99855_.m_99732_().m_105445_() - 1) {
               this.m_99871_(i, i + 1);
               return true;
            }
         }

         this.f_99855_.m_99700_(this);
         if (Util.m_137550_() - this.f_99861_ < 250L) {
            this.f_99855_.m_99729_();
         }

         this.f_99861_ = Util.m_137550_();
         return false;
      }

      public ServerData m_99898_() {
         return this.f_99857_;
      }

      public Component m_142172_() {
         return new TranslatableComponent("narrator.select", this.f_99857_.f_105362_);
      }
   }
}
