package net.minecraft.client.gui.screens.social;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SocialInteractionsScreen extends Screen {
   protected static final ResourceLocation f_100736_ = new ResourceLocation("textures/gui/social_interactions.png");
   private static final Component f_100737_ = new TranslatableComponent("gui.socialInteractions.tab_all");
   private static final Component f_100738_ = new TranslatableComponent("gui.socialInteractions.tab_hidden");
   private static final Component f_100739_ = new TranslatableComponent("gui.socialInteractions.tab_blocked");
   private static final Component f_100740_ = f_100737_.m_6879_().m_130940_(ChatFormatting.UNDERLINE);
   private static final Component f_100741_ = f_100738_.m_6879_().m_130940_(ChatFormatting.UNDERLINE);
   private static final Component f_100742_ = f_100739_.m_6879_().m_130940_(ChatFormatting.UNDERLINE);
   private static final Component f_100743_ = (new TranslatableComponent("gui.socialInteractions.search_hint")).m_130940_(ChatFormatting.ITALIC).m_130940_(ChatFormatting.GRAY);
   static final Component f_100744_ = (new TranslatableComponent("gui.socialInteractions.search_empty")).m_130940_(ChatFormatting.GRAY);
   private static final Component f_100745_ = (new TranslatableComponent("gui.socialInteractions.empty_hidden")).m_130940_(ChatFormatting.GRAY);
   private static final Component f_100746_ = (new TranslatableComponent("gui.socialInteractions.empty_blocked")).m_130940_(ChatFormatting.GRAY);
   private static final Component f_100747_ = new TranslatableComponent("gui.socialInteractions.blocking_hint");
   private static final String f_170139_ = "https://aka.ms/javablocking";
   private static final int f_170140_ = 8;
   private static final int f_170130_ = 16;
   private static final int f_170131_ = 236;
   private static final int f_170132_ = 16;
   private static final int f_170133_ = 64;
   public static final int f_170137_ = 88;
   public static final int f_170138_ = 78;
   private static final int f_170134_ = 238;
   private static final int f_170135_ = 20;
   private static final int f_170136_ = 36;
   SocialInteractionsPlayerList f_100748_;
   EditBox f_100749_;
   private String f_100726_ = "";
   private SocialInteractionsScreen.Page f_100727_ = SocialInteractionsScreen.Page.ALL;
   private Button f_100728_;
   private Button f_100729_;
   private Button f_100730_;
   private Button f_100731_;
   @Nullable
   private Component f_100732_;
   private int f_100733_;
   private boolean f_100734_;
   @Nullable
   private Runnable f_100735_;

   public SocialInteractionsScreen() {
      super(new TranslatableComponent("gui.socialInteractions.title"));
      this.m_100767_(Minecraft.m_91087_());
   }

   private int m_100799_() {
      return Math.max(52, this.f_96544_ - 128 - 16);
   }

   private int m_100800_() {
      return this.m_100799_() / 16;
   }

   private int m_100801_() {
      return 80 + this.m_100800_() * 16 - 8;
   }

   private int m_100802_() {
      return (this.f_96543_ - 238) / 2;
   }

   public Component m_142562_() {
      return (Component)(this.f_100732_ != null ? CommonComponents.m_178398_(super.m_142562_(), this.f_100732_) : super.m_142562_());
   }

   public void m_96624_() {
      super.m_96624_();
      this.f_100749_.m_94120_();
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      if (this.f_100734_) {
         this.f_100748_.m_93437_(this.f_96543_, this.f_96544_, 88, this.m_100801_());
      } else {
         this.f_100748_ = new SocialInteractionsPlayerList(this, this.f_96541_, this.f_96543_, this.f_96544_, 88, this.m_100801_(), 36);
      }

      int i = this.f_100748_.m_5759_() / 3;
      int j = this.f_100748_.m_5747_();
      int k = this.f_100748_.m_93520_();
      int l = this.f_96547_.m_92852_(f_100747_) + 40;
      int i1 = 64 + 16 * this.m_100800_();
      int j1 = (this.f_96543_ - l) / 2;
      this.f_100728_ = this.m_142416_(new Button(j, 45, i, 20, f_100737_, (p_100796_) -> {
         this.m_100771_(SocialInteractionsScreen.Page.ALL);
      }));
      this.f_100729_ = this.m_142416_(new Button((j + k - i) / 2 + 1, 45, i, 20, f_100738_, (p_100791_) -> {
         this.m_100771_(SocialInteractionsScreen.Page.HIDDEN);
      }));
      this.f_100730_ = this.m_142416_(new Button(k - i + 1, 45, i, 20, f_100739_, (p_100785_) -> {
         this.m_100771_(SocialInteractionsScreen.Page.BLOCKED);
      }));
      this.f_100731_ = this.m_142416_(new Button(j1, i1, l, 20, f_100747_, (p_100770_) -> {
         this.f_96541_.m_91152_(new ConfirmLinkScreen((p_170143_) -> {
            if (p_170143_) {
               Util.m_137581_().m_137646_("https://aka.ms/javablocking");
            }

            this.f_96541_.m_91152_(this);
         }, "https://aka.ms/javablocking", true));
      }));
      String s = this.f_100749_ != null ? this.f_100749_.m_94155_() : "";
      this.f_100749_ = new EditBox(this.f_96547_, this.m_100802_() + 28, 78, 196, 16, f_100743_) {
         protected MutableComponent m_5646_() {
            return !SocialInteractionsScreen.this.f_100749_.m_94155_().isEmpty() && SocialInteractionsScreen.this.f_100748_.m_100724_() ? super.m_5646_().m_130946_(", ").m_7220_(SocialInteractionsScreen.f_100744_) : super.m_5646_();
         }
      };
      this.f_100749_.m_94199_(16);
      this.f_100749_.m_94182_(false);
      this.f_100749_.m_94194_(true);
      this.f_100749_.m_94202_(16777215);
      this.f_100749_.m_94144_(s);
      this.f_100749_.m_94151_(this::m_100788_);
      this.m_7787_(this.f_100749_);
      this.m_7787_(this.f_100748_);
      this.f_100734_ = true;
      this.m_100771_(this.f_100727_);
   }

   private void m_100771_(SocialInteractionsScreen.Page p_100772_) {
      this.f_100727_ = p_100772_;
      this.f_100728_.m_93666_(f_100737_);
      this.f_100729_.m_93666_(f_100738_);
      this.f_100730_.m_93666_(f_100739_);
      Collection<UUID> collection;
      switch(p_100772_) {
      case ALL:
         this.f_100728_.m_93666_(f_100740_);
         collection = this.f_96541_.f_91074_.f_108617_.m_105143_();
         break;
      case HIDDEN:
         this.f_100729_.m_93666_(f_100741_);
         collection = this.f_96541_.m_91266_().m_100675_();
         break;
      case BLOCKED:
         this.f_100730_.m_93666_(f_100742_);
         PlayerSocialManager playersocialmanager = this.f_96541_.m_91266_();
         collection = this.f_96541_.f_91074_.f_108617_.m_105143_().stream().filter(playersocialmanager::m_100688_).collect(Collectors.toSet());
         break;
      default:
         collection = ImmutableList.of();
      }

      this.f_100748_.m_100719_(collection, this.f_100748_.m_93517_());
      if (!this.f_100749_.m_94155_().isEmpty() && this.f_100748_.m_100724_() && !this.f_100749_.m_93696_()) {
         NarratorChatListener.f_93311_.m_168785_(f_100744_);
      } else if (collection.isEmpty()) {
         if (p_100772_ == SocialInteractionsScreen.Page.HIDDEN) {
            NarratorChatListener.f_93311_.m_168785_(f_100745_);
         } else if (p_100772_ == SocialInteractionsScreen.Page.BLOCKED) {
            NarratorChatListener.f_93311_.m_168785_(f_100746_);
         }
      }

   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public void m_7333_(PoseStack p_100761_) {
      int i = this.m_100802_() + 3;
      super.m_7333_(p_100761_);
      RenderSystem.m_157456_(0, f_100736_);
      this.m_93228_(p_100761_, i, 64, 1, 1, 236, 8);
      int j = this.m_100800_();

      for(int k = 0; k < j; ++k) {
         this.m_93228_(p_100761_, i, 72 + 16 * k, 1, 10, 236, 16);
      }

      this.m_93228_(p_100761_, i, 72 + 16 * j, 1, 27, 236, 8);
      this.m_93228_(p_100761_, i + 10, 76, 243, 1, 12, 12);
   }

   public void m_6305_(PoseStack p_100763_, int p_100764_, int p_100765_, float p_100766_) {
      this.m_100767_(this.f_96541_);
      this.m_7333_(p_100763_);
      if (this.f_100732_ != null) {
         m_93243_(p_100763_, this.f_96541_.f_91062_, this.f_100732_, this.m_100802_() + 8, 35, -1);
      }

      if (!this.f_100748_.m_100724_()) {
         this.f_100748_.m_6305_(p_100763_, p_100764_, p_100765_, p_100766_);
      } else if (!this.f_100749_.m_94155_().isEmpty()) {
         m_93215_(p_100763_, this.f_96541_.f_91062_, f_100744_, this.f_96543_ / 2, (78 + this.m_100801_()) / 2, -1);
      } else if (this.f_100727_ == SocialInteractionsScreen.Page.HIDDEN) {
         m_93215_(p_100763_, this.f_96541_.f_91062_, f_100745_, this.f_96543_ / 2, (78 + this.m_100801_()) / 2, -1);
      } else if (this.f_100727_ == SocialInteractionsScreen.Page.BLOCKED) {
         m_93215_(p_100763_, this.f_96541_.f_91062_, f_100746_, this.f_96543_ / 2, (78 + this.m_100801_()) / 2, -1);
      }

      if (!this.f_100749_.m_93696_() && this.f_100749_.m_94155_().isEmpty()) {
         m_93243_(p_100763_, this.f_96541_.f_91062_, f_100743_, this.f_100749_.f_93620_, this.f_100749_.f_93621_, -1);
      } else {
         this.f_100749_.m_6305_(p_100763_, p_100764_, p_100765_, p_100766_);
      }

      this.f_100731_.f_93624_ = this.f_100727_ == SocialInteractionsScreen.Page.BLOCKED;
      super.m_6305_(p_100763_, p_100764_, p_100765_, p_100766_);
      if (this.f_100735_ != null) {
         this.f_100735_.run();
      }

   }

   public boolean m_6375_(double p_100753_, double p_100754_, int p_100755_) {
      if (this.f_100749_.m_93696_()) {
         this.f_100749_.m_6375_(p_100753_, p_100754_, p_100755_);
      }

      return super.m_6375_(p_100753_, p_100754_, p_100755_) || this.f_100748_.m_6375_(p_100753_, p_100754_, p_100755_);
   }

   public boolean m_7933_(int p_100757_, int p_100758_, int p_100759_) {
      if (!this.f_100749_.m_93696_() && this.f_96541_.f_91066_.f_92101_.m_90832_(p_100757_, p_100758_)) {
         this.f_96541_.m_91152_((Screen)null);
         return true;
      } else {
         return super.m_7933_(p_100757_, p_100758_, p_100759_);
      }
   }

   public boolean m_7043_() {
      return false;
   }

   private void m_100788_(String p_100789_) {
      p_100789_ = p_100789_.toLowerCase(Locale.ROOT);
      if (!p_100789_.equals(this.f_100726_)) {
         this.f_100748_.m_100717_(p_100789_);
         this.f_100726_ = p_100789_;
         this.m_100771_(this.f_100727_);
      }

   }

   private void m_100767_(Minecraft p_100768_) {
      int i = p_100768_.m_91403_().m_105142_().size();
      if (this.f_100733_ != i) {
         String s = "";
         ServerData serverdata = p_100768_.m_91089_();
         if (p_100768_.m_91090_()) {
            s = p_100768_.m_91092_().m_129916_();
         } else if (serverdata != null) {
            s = serverdata.f_105362_;
         }

         if (i > 1) {
            this.f_100732_ = new TranslatableComponent("gui.socialInteractions.server_label.multiple", s, i);
         } else {
            this.f_100732_ = new TranslatableComponent("gui.socialInteractions.server_label.single", s, i);
         }

         this.f_100733_ = i;
      }

   }

   public void m_100775_(PlayerInfo p_100776_) {
      this.f_100748_.m_100714_(p_100776_, this.f_100727_);
   }

   public void m_100779_(UUID p_100780_) {
      this.f_100748_.m_100722_(p_100780_);
   }

   public void m_100777_(@Nullable Runnable p_100778_) {
      this.f_100735_ = p_100778_;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Page {
      ALL,
      HIDDEN,
      BLOCKED;
   }
}