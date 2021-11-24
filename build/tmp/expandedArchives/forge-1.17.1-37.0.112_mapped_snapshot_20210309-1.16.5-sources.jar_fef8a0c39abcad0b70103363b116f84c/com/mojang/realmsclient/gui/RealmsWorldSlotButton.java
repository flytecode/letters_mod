package com.mojang.realmsclient.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.util.RealmsTextureManager;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsWorldSlotButton extends Button {
   public static final ResourceLocation f_87917_ = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
   public static final ResourceLocation f_87918_ = new ResourceLocation("realms", "textures/gui/realms/empty_frame.png");
   public static final ResourceLocation f_87919_ = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_0.png");
   public static final ResourceLocation f_87920_ = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_2.png");
   public static final ResourceLocation f_87921_ = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_3.png");
   private static final Component f_87922_ = new TranslatableComponent("mco.configure.world.slot.tooltip.active");
   private static final Component f_87923_ = new TranslatableComponent("mco.configure.world.slot.tooltip.minigame");
   private static final Component f_87924_ = new TranslatableComponent("mco.configure.world.slot.tooltip");
   private final Supplier<RealmsServer> f_87925_;
   private final Consumer<Component> f_87926_;
   private final int f_87914_;
   private int f_87915_;
   @Nullable
   private RealmsWorldSlotButton.State f_87916_;

   public RealmsWorldSlotButton(int p_87929_, int p_87930_, int p_87931_, int p_87932_, Supplier<RealmsServer> p_87933_, Consumer<Component> p_87934_, int p_87935_, Button.OnPress p_87936_) {
      super(p_87929_, p_87930_, p_87931_, p_87932_, TextComponent.f_131282_, p_87936_);
      this.f_87925_ = p_87933_;
      this.f_87914_ = p_87935_;
      this.f_87926_ = p_87934_;
   }

   @Nullable
   public RealmsWorldSlotButton.State m_87937_() {
      return this.f_87916_;
   }

   public void m_87968_() {
      ++this.f_87915_;
      RealmsServer realmsserver = this.f_87925_.get();
      if (realmsserver != null) {
         RealmsWorldOptions realmsworldoptions = realmsserver.f_87481_.get(this.f_87914_);
         boolean flag2 = this.f_87914_ == 4;
         boolean flag;
         String s;
         long i;
         String s1;
         boolean flag1;
         if (flag2) {
            flag = realmsserver.f_87485_ == RealmsServer.WorldType.MINIGAME;
            s = "Minigame";
            i = (long)realmsserver.f_87488_;
            s1 = realmsserver.f_87489_;
            flag1 = realmsserver.f_87488_ == -1;
         } else {
            flag = realmsserver.f_87486_ == this.f_87914_ && realmsserver.f_87485_ != RealmsServer.WorldType.MINIGAME;
            s = realmsworldoptions.m_87626_(this.f_87914_);
            i = realmsworldoptions.f_87608_;
            s1 = realmsworldoptions.f_87609_;
            flag1 = realmsworldoptions.f_87611_;
         }

         RealmsWorldSlotButton.Action realmsworldslotbutton$action = m_87959_(realmsserver, flag, flag2);
         Pair<Component, Component> pair = this.m_87953_(realmsserver, s, flag1, flag2, realmsworldslotbutton$action);
         this.f_87916_ = new RealmsWorldSlotButton.State(flag, s, i, s1, flag1, flag2, realmsworldslotbutton$action, pair.getFirst());
         this.m_93666_(pair.getSecond());
      }
   }

   private static RealmsWorldSlotButton.Action m_87959_(RealmsServer p_87960_, boolean p_87961_, boolean p_87962_) {
      if (p_87961_) {
         if (!p_87960_.f_87482_ && p_87960_.f_87477_ != RealmsServer.State.UNINITIALIZED) {
            return RealmsWorldSlotButton.Action.JOIN;
         }
      } else {
         if (!p_87962_) {
            return RealmsWorldSlotButton.Action.SWITCH_SLOT;
         }

         if (!p_87960_.f_87482_) {
            return RealmsWorldSlotButton.Action.SWITCH_SLOT;
         }
      }

      return RealmsWorldSlotButton.Action.NOTHING;
   }

   private Pair<Component, Component> m_87953_(RealmsServer p_87954_, String p_87955_, boolean p_87956_, boolean p_87957_, RealmsWorldSlotButton.Action p_87958_) {
      if (p_87958_ == RealmsWorldSlotButton.Action.NOTHING) {
         return Pair.of((Component)null, new TextComponent(p_87955_));
      } else {
         Component component;
         if (p_87957_) {
            if (p_87956_) {
               component = TextComponent.f_131282_;
            } else {
               component = (new TextComponent(" ")).m_130946_(p_87955_).m_130946_(" ").m_130946_(p_87954_.f_87487_);
            }
         } else {
            component = (new TextComponent(" ")).m_130946_(p_87955_);
         }

         Component component1;
         if (p_87958_ == RealmsWorldSlotButton.Action.JOIN) {
            component1 = f_87922_;
         } else {
            component1 = p_87957_ ? f_87923_ : f_87924_;
         }

         Component component2 = component1.m_6881_().m_7220_(component);
         return Pair.of(component1, component2);
      }
   }

   public void m_6303_(PoseStack p_87964_, int p_87965_, int p_87966_, float p_87967_) {
      if (this.f_87916_ != null) {
         this.m_87938_(p_87964_, this.f_93620_, this.f_93621_, p_87965_, p_87966_, this.f_87916_.f_87983_, this.f_87916_.f_87984_, this.f_87914_, this.f_87916_.f_87985_, this.f_87916_.f_87986_, this.f_87916_.f_87980_, this.f_87916_.f_87981_, this.f_87916_.f_87982_, this.f_87916_.f_87987_);
      }
   }

   private void m_87938_(PoseStack p_87939_, int p_87940_, int p_87941_, int p_87942_, int p_87943_, boolean p_87944_, String p_87945_, int p_87946_, long p_87947_, @Nullable String p_87948_, boolean p_87949_, boolean p_87950_, RealmsWorldSlotButton.Action p_87951_, @Nullable Component p_87952_) {
      boolean flag = this.m_5702_();
      if (this.m_5953_((double)p_87942_, (double)p_87943_) && p_87952_ != null) {
         this.f_87926_.accept(p_87952_);
      }

      Minecraft minecraft = Minecraft.m_91087_();
      TextureManager texturemanager = minecraft.m_91097_();
      if (p_87950_) {
         RealmsTextureManager.m_90190_(String.valueOf(p_87947_), p_87948_);
      } else if (p_87949_) {
         RenderSystem.m_157456_(0, f_87918_);
      } else if (p_87948_ != null && p_87947_ != -1L) {
         RealmsTextureManager.m_90190_(String.valueOf(p_87947_), p_87948_);
      } else if (p_87946_ == 1) {
         RenderSystem.m_157456_(0, f_87919_);
      } else if (p_87946_ == 2) {
         RenderSystem.m_157456_(0, f_87920_);
      } else if (p_87946_ == 3) {
         RenderSystem.m_157456_(0, f_87921_);
      }

      if (p_87944_) {
         float f = 0.85F + 0.15F * Mth.m_14089_((float)this.f_87915_ * 0.2F);
         RenderSystem.m_157429_(f, f, f, 1.0F);
      } else {
         RenderSystem.m_157429_(0.56F, 0.56F, 0.56F, 1.0F);
      }

      m_93133_(p_87939_, p_87940_ + 3, p_87941_ + 3, 0.0F, 0.0F, 74, 74, 74, 74);
      RenderSystem.m_157456_(0, f_87917_);
      boolean flag1 = flag && p_87951_ != RealmsWorldSlotButton.Action.NOTHING;
      if (flag1) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      } else if (p_87944_) {
         RenderSystem.m_157429_(0.8F, 0.8F, 0.8F, 1.0F);
      } else {
         RenderSystem.m_157429_(0.56F, 0.56F, 0.56F, 1.0F);
      }

      m_93133_(p_87939_, p_87940_, p_87941_, 0.0F, 0.0F, 80, 80, 80, 80);
      m_93208_(p_87939_, minecraft.f_91062_, p_87945_, p_87940_ + 40, p_87941_ + 66, 16777215);
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Action {
      NOTHING,
      SWITCH_SLOT,
      JOIN;
   }

   @OnlyIn(Dist.CLIENT)
   public static class State {
      final boolean f_87983_;
      final String f_87984_;
      final long f_87985_;
      final String f_87986_;
      public final boolean f_87980_;
      public final boolean f_87981_;
      public final RealmsWorldSlotButton.Action f_87982_;
      @Nullable
      final Component f_87987_;

      State(boolean p_87989_, String p_87990_, long p_87991_, @Nullable String p_87992_, boolean p_87993_, boolean p_87994_, RealmsWorldSlotButton.Action p_87995_, @Nullable Component p_87996_) {
         this.f_87983_ = p_87989_;
         this.f_87984_ = p_87990_;
         this.f_87985_ = p_87991_;
         this.f_87986_ = p_87992_;
         this.f_87980_ = p_87993_;
         this.f_87981_ = p_87994_;
         this.f_87982_ = p_87995_;
         this.f_87987_ = p_87996_;
      }
   }
}