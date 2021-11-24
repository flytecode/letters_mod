package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.Ops;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.util.RealmsTextureManager;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
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
public class RealmsPlayerScreen extends RealmsScreen {
   private static final Logger f_89073_ = LogManager.getLogger();
   private static final ResourceLocation f_89074_ = new ResourceLocation("realms", "textures/gui/realms/op_icon.png");
   private static final ResourceLocation f_89075_ = new ResourceLocation("realms", "textures/gui/realms/user_icon.png");
   private static final ResourceLocation f_89076_ = new ResourceLocation("realms", "textures/gui/realms/cross_player_icon.png");
   private static final ResourceLocation f_89077_ = new ResourceLocation("minecraft", "textures/gui/options_background.png");
   private static final Component f_89078_ = new TranslatableComponent("mco.configure.world.invites.normal.tooltip");
   private static final Component f_89079_ = new TranslatableComponent("mco.configure.world.invites.ops.tooltip");
   private static final Component f_89080_ = new TranslatableComponent("mco.configure.world.invites.remove.tooltip");
   private static final Component f_89081_ = new TranslatableComponent("mco.configure.world.invited");
   private Component f_89082_;
   private final RealmsConfigureWorldScreen f_89083_;
   final RealmsServer f_89084_;
   private RealmsPlayerScreen.InvitedObjectSelectionList f_89085_;
   int f_89086_;
   int f_89063_;
   private int f_89064_;
   private Button f_89065_;
   private Button f_89066_;
   private int f_89067_ = -1;
   private String f_89068_;
   int f_89069_ = -1;
   private boolean f_89070_;
   RealmsPlayerScreen.UserAction f_89072_ = RealmsPlayerScreen.UserAction.NONE;

   public RealmsPlayerScreen(RealmsConfigureWorldScreen p_89089_, RealmsServer p_89090_) {
      super(new TranslatableComponent("mco.configure.world.players.title"));
      this.f_89083_ = p_89089_;
      this.f_89084_ = p_89090_;
   }

   public void m_7856_() {
      this.f_89086_ = this.f_96543_ / 2 - 160;
      this.f_89063_ = 150;
      this.f_89064_ = this.f_96543_ / 2 + 12;
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_89085_ = new RealmsPlayerScreen.InvitedObjectSelectionList();
      this.f_89085_.m_93507_(this.f_89086_);
      this.m_7787_(this.f_89085_);

      for(PlayerInfo playerinfo : this.f_89084_.f_87480_) {
         this.f_89085_.m_89243_(playerinfo);
      }

      this.m_142416_(new Button(this.f_89064_, m_120774_(1), this.f_89063_ + 10, 20, new TranslatableComponent("mco.configure.world.buttons.invite"), (p_89176_) -> {
         this.f_96541_.m_91152_(new RealmsInviteScreen(this.f_89083_, this, this.f_89084_));
      }));
      this.f_89065_ = this.m_142416_(new Button(this.f_89064_, m_120774_(7), this.f_89063_ + 10, 20, new TranslatableComponent("mco.configure.world.invites.remove.tooltip"), (p_89161_) -> {
         this.m_89196_(this.f_89069_);
      }));
      this.f_89066_ = this.m_142416_(new Button(this.f_89064_, m_120774_(9), this.f_89063_ + 10, 20, new TranslatableComponent("mco.configure.world.invites.ops.tooltip"), (p_89139_) -> {
         if (this.f_89084_.f_87480_.get(this.f_89069_).m_87457_()) {
            this.m_89194_(this.f_89069_);
         } else {
            this.m_89192_(this.f_89069_);
         }

      }));
      this.m_142416_(new Button(this.f_89064_ + this.f_89063_ / 2 + 2, m_120774_(12), this.f_89063_ / 2 + 10 - 2, 20, CommonComponents.f_130660_, (p_89122_) -> {
         this.m_89189_();
      }));
      this.m_89188_();
   }

   void m_89188_() {
      this.f_89065_.f_93624_ = this.m_89190_(this.f_89069_);
      this.f_89066_.f_93624_ = this.m_89190_(this.f_89069_);
   }

   private boolean m_89190_(int p_89191_) {
      return p_89191_ != -1;
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public boolean m_7933_(int p_89094_, int p_89095_, int p_89096_) {
      if (p_89094_ == 256) {
         this.m_89189_();
         return true;
      } else {
         return super.m_7933_(p_89094_, p_89095_, p_89096_);
      }
   }

   private void m_89189_() {
      if (this.f_89070_) {
         this.f_96541_.m_91152_(this.f_89083_.m_88486_());
      } else {
         this.f_96541_.m_91152_(this.f_89083_);
      }

   }

   void m_89192_(int p_89193_) {
      this.m_89188_();
      RealmsClient realmsclient = RealmsClient.m_87169_();
      String s = this.f_89084_.f_87480_.get(p_89193_).m_87452_();

      try {
         this.m_89107_(realmsclient.m_87238_(this.f_89084_.f_87473_, s));
      } catch (RealmsServiceException realmsserviceexception) {
         f_89073_.error("Couldn't op the user");
      }

   }

   void m_89194_(int p_89195_) {
      this.m_89188_();
      RealmsClient realmsclient = RealmsClient.m_87169_();
      String s = this.f_89084_.f_87480_.get(p_89195_).m_87452_();

      try {
         this.m_89107_(realmsclient.m_87244_(this.f_89084_.f_87473_, s));
      } catch (RealmsServiceException realmsserviceexception) {
         f_89073_.error("Couldn't deop the user");
      }

   }

   private void m_89107_(Ops p_89108_) {
      for(PlayerInfo playerinfo : this.f_89084_.f_87480_) {
         playerinfo.m_87450_(p_89108_.f_87418_.contains(playerinfo.m_87447_()));
      }

   }

   void m_89196_(int p_89197_) {
      this.m_89188_();
      if (p_89197_ >= 0 && p_89197_ < this.f_89084_.f_87480_.size()) {
         PlayerInfo playerinfo = this.f_89084_.f_87480_.get(p_89197_);
         this.f_89068_ = playerinfo.m_87452_();
         this.f_89067_ = p_89197_;
         RealmsConfirmScreen realmsconfirmscreen = new RealmsConfirmScreen((p_89163_) -> {
            if (p_89163_) {
               RealmsClient realmsclient = RealmsClient.m_87169_();

               try {
                  realmsclient.m_87183_(this.f_89084_.f_87473_, this.f_89068_);
               } catch (RealmsServiceException realmsserviceexception) {
                  f_89073_.error("Couldn't uninvite user");
               }

               this.m_89198_(this.f_89067_);
               this.f_89069_ = -1;
               this.m_89188_();
            }

            this.f_89070_ = true;
            this.f_96541_.m_91152_(this);
         }, new TextComponent("Question"), (new TranslatableComponent("mco.configure.world.uninvite.question")).m_130946_(" '").m_130946_(playerinfo.m_87447_()).m_130946_("' ?"));
         this.f_96541_.m_91152_(realmsconfirmscreen);
      }

   }

   private void m_89198_(int p_89199_) {
      this.f_89084_.f_87480_.remove(p_89199_);
   }

   public void m_6305_(PoseStack p_89098_, int p_89099_, int p_89100_, float p_89101_) {
      this.f_89082_ = null;
      this.f_89072_ = RealmsPlayerScreen.UserAction.NONE;
      this.m_7333_(p_89098_);
      if (this.f_89085_ != null) {
         this.f_89085_.m_6305_(p_89098_, p_89099_, p_89100_, p_89101_);
      }

      m_93215_(p_89098_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 16777215);
      int i = m_120774_(12) + 20;
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      RenderSystem.m_157427_(GameRenderer::m_172820_);
      RenderSystem.m_157456_(0, f_89077_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      float f = 32.0F;
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_5483_(0.0D, (double)this.f_96544_, 0.0D).m_7421_(0.0F, (float)(this.f_96544_ - i) / 32.0F + 0.0F).m_6122_(64, 64, 64, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_96543_, (double)this.f_96544_, 0.0D).m_7421_((float)this.f_96543_ / 32.0F, (float)(this.f_96544_ - i) / 32.0F + 0.0F).m_6122_(64, 64, 64, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_96543_, (double)i, 0.0D).m_7421_((float)this.f_96543_ / 32.0F, 0.0F).m_6122_(64, 64, 64, 255).m_5752_();
      bufferbuilder.m_5483_(0.0D, (double)i, 0.0D).m_7421_(0.0F, 0.0F).m_6122_(64, 64, 64, 255).m_5752_();
      tesselator.m_85914_();
      if (this.f_89084_ != null && this.f_89084_.f_87480_ != null) {
         this.f_96547_.m_92889_(p_89098_, (new TextComponent("")).m_7220_(f_89081_).m_130946_(" (").m_130946_(Integer.toString(this.f_89084_.f_87480_.size())).m_130946_(")"), (float)this.f_89086_, (float)m_120774_(0), 10526880);
      } else {
         this.f_96547_.m_92889_(p_89098_, f_89081_, (float)this.f_89086_, (float)m_120774_(0), 10526880);
      }

      super.m_6305_(p_89098_, p_89099_, p_89100_, p_89101_);
      if (this.f_89084_ != null) {
         this.m_89102_(p_89098_, this.f_89082_, p_89099_, p_89100_);
      }
   }

   protected void m_89102_(PoseStack p_89103_, @Nullable Component p_89104_, int p_89105_, int p_89106_) {
      if (p_89104_ != null) {
         int i = p_89105_ + 12;
         int j = p_89106_ - 12;
         int k = this.f_96547_.m_92852_(p_89104_);
         this.m_93179_(p_89103_, i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824);
         this.f_96547_.m_92763_(p_89103_, p_89104_, (float)i, (float)j, 16777215);
      }
   }

   void m_89142_(PoseStack p_89143_, int p_89144_, int p_89145_, int p_89146_, int p_89147_) {
      boolean flag = p_89146_ >= p_89144_ && p_89146_ <= p_89144_ + 9 && p_89147_ >= p_89145_ && p_89147_ <= p_89145_ + 9 && p_89147_ < m_120774_(12) + 20 && p_89147_ > m_120774_(1);
      RenderSystem.m_157456_(0, f_89076_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      float f = flag ? 7.0F : 0.0F;
      GuiComponent.m_93133_(p_89143_, p_89144_, p_89145_, 0.0F, f, 8, 7, 8, 14);
      if (flag) {
         this.f_89082_ = f_89080_;
         this.f_89072_ = RealmsPlayerScreen.UserAction.REMOVE;
      }

   }

   void m_89164_(PoseStack p_89165_, int p_89166_, int p_89167_, int p_89168_, int p_89169_) {
      boolean flag = p_89168_ >= p_89166_ && p_89168_ <= p_89166_ + 9 && p_89169_ >= p_89167_ && p_89169_ <= p_89167_ + 9 && p_89169_ < m_120774_(12) + 20 && p_89169_ > m_120774_(1);
      RenderSystem.m_157456_(0, f_89074_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      float f = flag ? 8.0F : 0.0F;
      GuiComponent.m_93133_(p_89165_, p_89166_, p_89167_, 0.0F, f, 8, 8, 8, 16);
      if (flag) {
         this.f_89082_ = f_89079_;
         this.f_89072_ = RealmsPlayerScreen.UserAction.TOGGLE_OP;
      }

   }

   void m_89178_(PoseStack p_89179_, int p_89180_, int p_89181_, int p_89182_, int p_89183_) {
      boolean flag = p_89182_ >= p_89180_ && p_89182_ <= p_89180_ + 9 && p_89183_ >= p_89181_ && p_89183_ <= p_89181_ + 9 && p_89183_ < m_120774_(12) + 20 && p_89183_ > m_120774_(1);
      RenderSystem.m_157456_(0, f_89075_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      float f = flag ? 8.0F : 0.0F;
      GuiComponent.m_93133_(p_89179_, p_89180_, p_89181_, 0.0F, f, 8, 8, 8, 16);
      if (flag) {
         this.f_89082_ = f_89078_;
         this.f_89072_ = RealmsPlayerScreen.UserAction.TOGGLE_OP;
      }

   }

   @OnlyIn(Dist.CLIENT)
   class Entry extends ObjectSelectionList.Entry<RealmsPlayerScreen.Entry> {
      private final PlayerInfo f_89201_;

      public Entry(PlayerInfo p_89204_) {
         this.f_89201_ = p_89204_;
      }

      public void m_6311_(PoseStack p_89209_, int p_89210_, int p_89211_, int p_89212_, int p_89213_, int p_89214_, int p_89215_, int p_89216_, boolean p_89217_, float p_89218_) {
         this.m_89219_(p_89209_, this.f_89201_, p_89212_, p_89211_, p_89215_, p_89216_);
      }

      private void m_89219_(PoseStack p_89220_, PlayerInfo p_89221_, int p_89222_, int p_89223_, int p_89224_, int p_89225_) {
         int i;
         if (!p_89221_.m_87460_()) {
            i = 10526880;
         } else if (p_89221_.m_87461_()) {
            i = 8388479;
         } else {
            i = 16777215;
         }

         RealmsPlayerScreen.this.f_96547_.m_92883_(p_89220_, p_89221_.m_87447_(), (float)(RealmsPlayerScreen.this.f_89086_ + 3 + 12), (float)(p_89223_ + 1), i);
         if (p_89221_.m_87457_()) {
            RealmsPlayerScreen.this.m_89164_(p_89220_, RealmsPlayerScreen.this.f_89086_ + RealmsPlayerScreen.this.f_89063_ - 10, p_89223_ + 1, p_89224_, p_89225_);
         } else {
            RealmsPlayerScreen.this.m_89178_(p_89220_, RealmsPlayerScreen.this.f_89086_ + RealmsPlayerScreen.this.f_89063_ - 10, p_89223_ + 1, p_89224_, p_89225_);
         }

         RealmsPlayerScreen.this.m_89142_(p_89220_, RealmsPlayerScreen.this.f_89086_ + RealmsPlayerScreen.this.f_89063_ - 22, p_89223_ + 2, p_89224_, p_89225_);
         RealmsTextureManager.m_90187_(p_89221_.m_87452_(), () -> {
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            GuiComponent.m_93160_(p_89220_, RealmsPlayerScreen.this.f_89086_ + 2 + 2, p_89223_ + 1, 8, 8, 8.0F, 8.0F, 8, 8, 64, 64);
            GuiComponent.m_93160_(p_89220_, RealmsPlayerScreen.this.f_89086_ + 2 + 2, p_89223_ + 1, 8, 8, 40.0F, 8.0F, 8, 8, 64, 64);
         });
      }

      public Component m_142172_() {
         return new TranslatableComponent("narrator.select", this.f_89201_.m_87447_());
      }
   }

   @OnlyIn(Dist.CLIENT)
   class InvitedObjectSelectionList extends RealmsObjectSelectionList<RealmsPlayerScreen.Entry> {
      public InvitedObjectSelectionList() {
         super(RealmsPlayerScreen.this.f_89063_ + 10, RealmsPlayerScreen.m_120774_(12) + 20, RealmsPlayerScreen.m_120774_(1), RealmsPlayerScreen.m_120774_(12) + 20, 13);
      }

      public void m_89243_(PlayerInfo p_89244_) {
         this.m_7085_(RealmsPlayerScreen.this.new Entry(p_89244_));
      }

      public int m_5759_() {
         return (int)((double)this.f_93388_ * 1.0D);
      }

      public boolean m_5694_() {
         return RealmsPlayerScreen.this.m_7222_() == this;
      }

      public boolean m_6375_(double p_89230_, double p_89231_, int p_89232_) {
         if (p_89232_ == 0 && p_89230_ < (double)this.m_5756_() && p_89231_ >= (double)this.f_93390_ && p_89231_ <= (double)this.f_93391_) {
            int i = RealmsPlayerScreen.this.f_89086_;
            int j = RealmsPlayerScreen.this.f_89086_ + RealmsPlayerScreen.this.f_89063_;
            int k = (int)Math.floor(p_89231_ - (double)this.f_93390_) - this.f_93395_ + (int)this.m_93517_() - 4;
            int l = k / this.f_93387_;
            if (p_89230_ >= (double)i && p_89230_ <= (double)j && l >= 0 && k >= 0 && l < this.m_5773_()) {
               this.m_7109_(l);
               this.m_7980_(k, l, p_89230_, p_89231_, this.f_93388_);
            }

            return true;
         } else {
            return super.m_6375_(p_89230_, p_89231_, p_89232_);
         }
      }

      public void m_7980_(int p_89236_, int p_89237_, double p_89238_, double p_89239_, int p_89240_) {
         if (p_89237_ >= 0 && p_89237_ <= RealmsPlayerScreen.this.f_89084_.f_87480_.size() && RealmsPlayerScreen.this.f_89072_ != RealmsPlayerScreen.UserAction.NONE) {
            if (RealmsPlayerScreen.this.f_89072_ == RealmsPlayerScreen.UserAction.TOGGLE_OP) {
               if (RealmsPlayerScreen.this.f_89084_.f_87480_.get(p_89237_).m_87457_()) {
                  RealmsPlayerScreen.this.m_89194_(p_89237_);
               } else {
                  RealmsPlayerScreen.this.m_89192_(p_89237_);
               }
            } else if (RealmsPlayerScreen.this.f_89072_ == RealmsPlayerScreen.UserAction.REMOVE) {
               RealmsPlayerScreen.this.m_89196_(p_89237_);
            }

         }
      }

      public void m_7109_(int p_89234_) {
         super.m_7109_(p_89234_);
         this.m_89250_(p_89234_);
      }

      public void m_89250_(int p_89251_) {
         RealmsPlayerScreen.this.f_89069_ = p_89251_;
         RealmsPlayerScreen.this.m_89188_();
      }

      public void m_6987_(@Nullable RealmsPlayerScreen.Entry p_89246_) {
         super.m_6987_(p_89246_);
         RealmsPlayerScreen.this.f_89069_ = this.m_6702_().indexOf(p_89246_);
         RealmsPlayerScreen.this.m_89188_();
      }

      public void m_7733_(PoseStack p_89242_) {
         RealmsPlayerScreen.this.m_7333_(p_89242_);
      }

      public int m_5756_() {
         return RealmsPlayerScreen.this.f_89086_ + this.f_93388_ - 5;
      }

      public int m_5775_() {
         return this.m_5773_() * 13;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static enum UserAction {
      TOGGLE_OP,
      REMOVE,
      NONE;
   }
}