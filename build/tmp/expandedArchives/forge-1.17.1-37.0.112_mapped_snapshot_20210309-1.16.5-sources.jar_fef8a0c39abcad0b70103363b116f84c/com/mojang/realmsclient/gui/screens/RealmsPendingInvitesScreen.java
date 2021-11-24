package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RowButton;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.RealmsUtil;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsPendingInvitesScreen extends RealmsScreen {
   static final Logger f_88874_ = LogManager.getLogger();
   static final ResourceLocation f_88875_ = new ResourceLocation("realms", "textures/gui/realms/accept_icon.png");
   static final ResourceLocation f_88876_ = new ResourceLocation("realms", "textures/gui/realms/reject_icon.png");
   private static final Component f_88877_ = new TranslatableComponent("mco.invites.nopending");
   static final Component f_88878_ = new TranslatableComponent("mco.invites.button.accept");
   static final Component f_88879_ = new TranslatableComponent("mco.invites.button.reject");
   private final Screen f_88880_;
   @Nullable
   Component f_88881_;
   boolean f_88882_;
   RealmsPendingInvitesScreen.PendingInvitationSelectionList f_88883_;
   int f_88885_ = -1;
   private Button f_88886_;
   private Button f_88887_;

   public RealmsPendingInvitesScreen(Screen p_88890_) {
      super(new TranslatableComponent("mco.invites.title"));
      this.f_88880_ = p_88890_;
   }

   public void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_88883_ = new RealmsPendingInvitesScreen.PendingInvitationSelectionList();
      (new Thread("Realms-pending-invitations-fetcher") {
         public void run() {
            RealmsClient realmsclient = RealmsClient.m_87169_();

            try {
               List<PendingInvite> list = realmsclient.m_87261_().f_87432_;
               List<RealmsPendingInvitesScreen.Entry> list1 = list.stream().map((p_88969_) -> {
                  return RealmsPendingInvitesScreen.this.new Entry(p_88969_);
               }).collect(Collectors.toList());
               RealmsPendingInvitesScreen.this.f_96541_.execute(() -> {
                  RealmsPendingInvitesScreen.this.f_88883_.m_5988_(list1);
               });
            } catch (RealmsServiceException realmsserviceexception) {
               RealmsPendingInvitesScreen.f_88874_.error("Couldn't list invites");
            } finally {
               RealmsPendingInvitesScreen.this.f_88882_ = true;
            }

         }
      }).start();
      this.m_7787_(this.f_88883_);
      this.f_88886_ = this.m_142416_(new Button(this.f_96543_ / 2 - 174, this.f_96544_ - 32, 100, 20, new TranslatableComponent("mco.invites.button.accept"), (p_88940_) -> {
         this.m_88932_(this.f_88885_);
         this.f_88885_ = -1;
         this.m_88957_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 50, this.f_96544_ - 32, 100, 20, CommonComponents.f_130655_, (p_88930_) -> {
         this.f_96541_.m_91152_(new RealmsMainScreen(this.f_88880_));
      }));
      this.f_88887_ = this.m_142416_(new Button(this.f_96543_ / 2 + 74, this.f_96544_ - 32, 100, 20, new TranslatableComponent("mco.invites.button.reject"), (p_88920_) -> {
         this.m_88922_(this.f_88885_);
         this.f_88885_ = -1;
         this.m_88957_();
      }));
      this.m_88957_();
   }

   public boolean m_7933_(int p_88895_, int p_88896_, int p_88897_) {
      if (p_88895_ == 256) {
         this.f_96541_.m_91152_(new RealmsMainScreen(this.f_88880_));
         return true;
      } else {
         return super.m_7933_(p_88895_, p_88896_, p_88897_);
      }
   }

   void m_88892_(int p_88893_) {
      this.f_88883_.m_89057_(p_88893_);
   }

   void m_88922_(final int p_88923_) {
      if (p_88923_ < this.f_88883_.m_5773_()) {
         (new Thread("Realms-reject-invitation") {
            public void run() {
               try {
                  RealmsClient realmsclient = RealmsClient.m_87169_();
                  realmsclient.m_87219_((RealmsPendingInvitesScreen.this.f_88883_.m_6702_().get(p_88923_)).f_88992_.f_87422_);
                  RealmsPendingInvitesScreen.this.f_96541_.execute(() -> {
                     RealmsPendingInvitesScreen.this.m_88892_(p_88923_);
                  });
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsPendingInvitesScreen.f_88874_.error("Couldn't reject invite");
               }

            }
         }).start();
      }

   }

   void m_88932_(final int p_88933_) {
      if (p_88933_ < this.f_88883_.m_5773_()) {
         (new Thread("Realms-accept-invitation") {
            public void run() {
               try {
                  RealmsClient realmsclient = RealmsClient.m_87169_();
                  realmsclient.m_87201_((RealmsPendingInvitesScreen.this.f_88883_.m_6702_().get(p_88933_)).f_88992_.f_87422_);
                  RealmsPendingInvitesScreen.this.f_96541_.execute(() -> {
                     RealmsPendingInvitesScreen.this.m_88892_(p_88933_);
                  });
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsPendingInvitesScreen.f_88874_.error("Couldn't accept invite");
               }

            }
         }).start();
      }

   }

   public void m_6305_(PoseStack p_88899_, int p_88900_, int p_88901_, float p_88902_) {
      this.f_88881_ = null;
      this.m_7333_(p_88899_);
      this.f_88883_.m_6305_(p_88899_, p_88900_, p_88901_, p_88902_);
      m_93215_(p_88899_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 12, 16777215);
      if (this.f_88881_ != null) {
         this.m_88903_(p_88899_, this.f_88881_, p_88900_, p_88901_);
      }

      if (this.f_88883_.m_5773_() == 0 && this.f_88882_) {
         m_93215_(p_88899_, this.f_96547_, f_88877_, this.f_96543_ / 2, this.f_96544_ / 2 - 20, 16777215);
      }

      super.m_6305_(p_88899_, p_88900_, p_88901_, p_88902_);
   }

   protected void m_88903_(PoseStack p_88904_, @Nullable Component p_88905_, int p_88906_, int p_88907_) {
      if (p_88905_ != null) {
         int i = p_88906_ + 12;
         int j = p_88907_ - 12;
         int k = this.f_96547_.m_92852_(p_88905_);
         this.m_93179_(p_88904_, i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824);
         this.f_96547_.m_92763_(p_88904_, p_88905_, (float)i, (float)j, 16777215);
      }
   }

   void m_88957_() {
      this.f_88886_.f_93624_ = this.m_88962_(this.f_88885_);
      this.f_88887_.f_93624_ = this.m_88962_(this.f_88885_);
   }

   private boolean m_88962_(int p_88963_) {
      return p_88963_ != -1;
   }

   @OnlyIn(Dist.CLIENT)
   class Entry extends ObjectSelectionList.Entry<RealmsPendingInvitesScreen.Entry> {
      private static final int f_167427_ = 38;
      final PendingInvite f_88992_;
      private final List<RowButton> f_88993_;

      Entry(PendingInvite p_88996_) {
         this.f_88992_ = p_88996_;
         this.f_88993_ = Arrays.asList(new RealmsPendingInvitesScreen.Entry.AcceptRowButton(), new RealmsPendingInvitesScreen.Entry.RejectRowButton());
      }

      public void m_6311_(PoseStack p_89006_, int p_89007_, int p_89008_, int p_89009_, int p_89010_, int p_89011_, int p_89012_, int p_89013_, boolean p_89014_, float p_89015_) {
         this.m_89016_(p_89006_, this.f_88992_, p_89009_, p_89008_, p_89012_, p_89013_);
      }

      public boolean m_6375_(double p_88998_, double p_88999_, int p_89000_) {
         RowButton.m_88036_(RealmsPendingInvitesScreen.this.f_88883_, this, this.f_88993_, p_89000_, p_88998_, p_88999_);
         return true;
      }

      private void m_89016_(PoseStack p_89017_, PendingInvite p_89018_, int p_89019_, int p_89020_, int p_89021_, int p_89022_) {
         RealmsPendingInvitesScreen.this.f_96547_.m_92883_(p_89017_, p_89018_.f_87423_, (float)(p_89019_ + 38), (float)(p_89020_ + 1), 16777215);
         RealmsPendingInvitesScreen.this.f_96547_.m_92883_(p_89017_, p_89018_.f_87424_, (float)(p_89019_ + 38), (float)(p_89020_ + 12), 7105644);
         RealmsPendingInvitesScreen.this.f_96547_.m_92883_(p_89017_, RealmsUtil.m_90223_(p_89018_.f_87426_), (float)(p_89019_ + 38), (float)(p_89020_ + 24), 7105644);
         RowButton.m_88028_(p_89017_, this.f_88993_, RealmsPendingInvitesScreen.this.f_88883_, p_89019_, p_89020_, p_89021_, p_89022_);
         RealmsTextureManager.m_90187_(p_89018_.f_87425_, () -> {
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            GuiComponent.m_93160_(p_89017_, p_89019_, p_89020_, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
            GuiComponent.m_93160_(p_89017_, p_89019_, p_89020_, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
         });
      }

      public Component m_142172_() {
         Component component = CommonComponents.m_178396_(new TextComponent(this.f_88992_.f_87423_), new TextComponent(this.f_88992_.f_87424_), new TextComponent(RealmsUtil.m_90223_(this.f_88992_.f_87426_)));
         return new TranslatableComponent("narrator.select", component);
      }

      @OnlyIn(Dist.CLIENT)
      class AcceptRowButton extends RowButton {
         AcceptRowButton() {
            super(15, 15, 215, 5);
         }

         protected void m_7537_(PoseStack p_89031_, int p_89032_, int p_89033_, boolean p_89034_) {
            RenderSystem.m_157456_(0, RealmsPendingInvitesScreen.f_88875_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            float f = p_89034_ ? 19.0F : 0.0F;
            GuiComponent.m_93133_(p_89031_, p_89032_, p_89033_, f, 0.0F, 18, 18, 37, 18);
            if (p_89034_) {
               RealmsPendingInvitesScreen.this.f_88881_ = RealmsPendingInvitesScreen.f_88878_;
            }

         }

         public void m_7516_(int p_89029_) {
            RealmsPendingInvitesScreen.this.m_88932_(p_89029_);
         }
      }

      @OnlyIn(Dist.CLIENT)
      class RejectRowButton extends RowButton {
         RejectRowButton() {
            super(15, 15, 235, 5);
         }

         protected void m_7537_(PoseStack p_89041_, int p_89042_, int p_89043_, boolean p_89044_) {
            RenderSystem.m_157456_(0, RealmsPendingInvitesScreen.f_88876_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            float f = p_89044_ ? 19.0F : 0.0F;
            GuiComponent.m_93133_(p_89041_, p_89042_, p_89043_, f, 0.0F, 18, 18, 37, 18);
            if (p_89044_) {
               RealmsPendingInvitesScreen.this.f_88881_ = RealmsPendingInvitesScreen.f_88879_;
            }

         }

         public void m_7516_(int p_89039_) {
            RealmsPendingInvitesScreen.this.m_88922_(p_89039_);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   class PendingInvitationSelectionList extends RealmsObjectSelectionList<RealmsPendingInvitesScreen.Entry> {
      public PendingInvitationSelectionList() {
         super(RealmsPendingInvitesScreen.this.f_96543_, RealmsPendingInvitesScreen.this.f_96544_, 32, RealmsPendingInvitesScreen.this.f_96544_ - 40, 36);
      }

      public void m_89057_(int p_89058_) {
         this.m_93514_(p_89058_);
      }

      public int m_5775_() {
         return this.m_5773_() * 36;
      }

      public int m_5759_() {
         return 260;
      }

      public boolean m_5694_() {
         return RealmsPendingInvitesScreen.this.m_7222_() == this;
      }

      public void m_7733_(PoseStack p_89051_) {
         RealmsPendingInvitesScreen.this.m_7333_(p_89051_);
      }

      public void m_7109_(int p_89049_) {
         super.m_7109_(p_89049_);
         this.m_89060_(p_89049_);
      }

      public void m_89060_(int p_89061_) {
         RealmsPendingInvitesScreen.this.f_88885_ = p_89061_;
         RealmsPendingInvitesScreen.this.m_88957_();
      }

      public void m_6987_(@Nullable RealmsPendingInvitesScreen.Entry p_89053_) {
         super.m_6987_(p_89053_);
         RealmsPendingInvitesScreen.this.f_88885_ = this.m_6702_().indexOf(p_89053_);
         RealmsPendingInvitesScreen.this.m_88957_();
      }
   }
}