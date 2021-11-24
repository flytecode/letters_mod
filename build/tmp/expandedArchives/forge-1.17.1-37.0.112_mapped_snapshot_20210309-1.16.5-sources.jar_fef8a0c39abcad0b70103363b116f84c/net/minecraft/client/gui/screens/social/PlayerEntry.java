package net.minecraft.client.gui.screens.social;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerEntry extends ContainerObjectSelectionList.Entry<PlayerEntry> {
   private static final int f_170065_ = 10;
   private static final int f_170066_ = 150;
   private final Minecraft f_100534_;
   private final List<AbstractWidget> f_100535_;
   private final UUID f_100536_;
   private final String f_100537_;
   private final Supplier<ResourceLocation> f_100538_;
   private boolean f_100539_;
   @Nullable
   private Button f_100540_;
   @Nullable
   private Button f_100541_;
   final Component f_170067_;
   final Component f_170068_;
   final List<FormattedCharSequence> f_100542_;
   final List<FormattedCharSequence> f_100543_;
   float f_100544_;
   private static final Component f_100545_ = (new TranslatableComponent("gui.socialInteractions.status_hidden")).m_130940_(ChatFormatting.ITALIC);
   private static final Component f_100546_ = (new TranslatableComponent("gui.socialInteractions.status_blocked")).m_130940_(ChatFormatting.ITALIC);
   private static final Component f_100547_ = (new TranslatableComponent("gui.socialInteractions.status_offline")).m_130940_(ChatFormatting.ITALIC);
   private static final Component f_100548_ = (new TranslatableComponent("gui.socialInteractions.status_hidden_offline")).m_130940_(ChatFormatting.ITALIC);
   private static final Component f_100549_ = (new TranslatableComponent("gui.socialInteractions.status_blocked_offline")).m_130940_(ChatFormatting.ITALIC);
   private static final int f_170069_ = 24;
   private static final int f_170061_ = 4;
   private static final int f_170062_ = 20;
   private static final int f_170063_ = 0;
   private static final int f_170064_ = 38;
   public static final int f_100529_ = FastColor.ARGB32.m_13660_(190, 0, 0, 0);
   public static final int f_100530_ = FastColor.ARGB32.m_13660_(255, 74, 74, 74);
   public static final int f_100531_ = FastColor.ARGB32.m_13660_(255, 48, 48, 48);
   public static final int f_100532_ = FastColor.ARGB32.m_13660_(255, 255, 255, 255);
   public static final int f_100533_ = FastColor.ARGB32.m_13660_(140, 255, 255, 255);

   public PlayerEntry(final Minecraft p_100552_, final SocialInteractionsScreen p_100553_, UUID p_100554_, String p_100555_, Supplier<ResourceLocation> p_100556_) {
      this.f_100534_ = p_100552_;
      this.f_100536_ = p_100554_;
      this.f_100537_ = p_100555_;
      this.f_100538_ = p_100556_;
      this.f_170067_ = new TranslatableComponent("gui.socialInteractions.tooltip.hide", p_100555_);
      this.f_170068_ = new TranslatableComponent("gui.socialInteractions.tooltip.show", p_100555_);
      this.f_100542_ = p_100552_.f_91062_.m_92923_(this.f_170067_, 150);
      this.f_100543_ = p_100552_.f_91062_.m_92923_(this.f_170068_, 150);
      PlayerSocialManager playersocialmanager = p_100552_.m_91266_();
      if (!p_100552_.f_91074_.m_36316_().getId().equals(p_100554_) && !playersocialmanager.m_100688_(p_100554_)) {
         this.f_100540_ = new ImageButton(0, 0, 20, 20, 0, 38, 20, SocialInteractionsScreen.f_100736_, 256, 256, (p_100612_) -> {
            playersocialmanager.m_100680_(p_100554_);
            this.m_100596_(true, new TranslatableComponent("gui.socialInteractions.hidden_in_chat", p_100555_));
         }, new Button.OnTooltip() {
            public void m_93752_(Button p_170090_, PoseStack p_170091_, int p_170092_, int p_170093_) {
               PlayerEntry.this.f_100544_ += p_100552_.m_91297_();
               if (PlayerEntry.this.f_100544_ >= 10.0F) {
                  p_100553_.m_100777_(() -> {
                     PlayerEntry.m_100588_(p_100553_, p_170091_, PlayerEntry.this.f_100542_, p_170092_, p_170093_);
                  });
               }

            }

            public void m_142753_(Consumer<Component> p_170088_) {
               p_170088_.accept(PlayerEntry.this.f_170067_);
            }
         }, new TranslatableComponent("gui.socialInteractions.hide")) {
            protected MutableComponent m_5646_() {
               return PlayerEntry.this.m_100594_(super.m_5646_());
            }
         };
         this.f_100541_ = new ImageButton(0, 0, 20, 20, 20, 38, 20, SocialInteractionsScreen.f_100736_, 256, 256, (p_170074_) -> {
            playersocialmanager.m_100682_(p_100554_);
            this.m_100596_(false, new TranslatableComponent("gui.socialInteractions.shown_in_chat", p_100555_));
         }, new Button.OnTooltip() {
            public void m_93752_(Button p_170109_, PoseStack p_170110_, int p_170111_, int p_170112_) {
               PlayerEntry.this.f_100544_ += p_100552_.m_91297_();
               if (PlayerEntry.this.f_100544_ >= 10.0F) {
                  p_100553_.m_100777_(() -> {
                     PlayerEntry.m_100588_(p_100553_, p_170110_, PlayerEntry.this.f_100543_, p_170111_, p_170112_);
                  });
               }

            }

            public void m_142753_(Consumer<Component> p_170107_) {
               p_170107_.accept(PlayerEntry.this.f_170068_);
            }
         }, new TranslatableComponent("gui.socialInteractions.show")) {
            protected MutableComponent m_5646_() {
               return PlayerEntry.this.m_100594_(super.m_5646_());
            }
         };
         this.f_100541_.f_93624_ = playersocialmanager.m_100686_(p_100554_);
         this.f_100540_.f_93624_ = !this.f_100541_.f_93624_;
         this.f_100535_ = ImmutableList.of(this.f_100540_, this.f_100541_);
      } else {
         this.f_100535_ = ImmutableList.of();
      }

   }

   public void m_6311_(PoseStack p_100558_, int p_100559_, int p_100560_, int p_100561_, int p_100562_, int p_100563_, int p_100564_, int p_100565_, boolean p_100566_, float p_100567_) {
      int i = p_100561_ + 4;
      int j = p_100560_ + (p_100563_ - 24) / 2;
      int k = i + 24 + 4;
      Component component = this.m_100621_();
      int l;
      if (component == TextComponent.f_131282_) {
         GuiComponent.m_93172_(p_100558_, p_100561_, p_100560_, p_100561_ + p_100562_, p_100560_ + p_100563_, f_100530_);
         l = p_100560_ + (p_100563_ - 9) / 2;
      } else {
         GuiComponent.m_93172_(p_100558_, p_100561_, p_100560_, p_100561_ + p_100562_, p_100560_ + p_100563_, f_100531_);
         l = p_100560_ + (p_100563_ - (9 + 9)) / 2;
         this.f_100534_.f_91062_.m_92889_(p_100558_, component, (float)k, (float)(l + 12), f_100533_);
      }

      RenderSystem.m_157456_(0, this.f_100538_.get());
      GuiComponent.m_93160_(p_100558_, i, j, 24, 24, 8.0F, 8.0F, 8, 8, 64, 64);
      RenderSystem.m_69478_();
      GuiComponent.m_93160_(p_100558_, i, j, 24, 24, 40.0F, 8.0F, 8, 8, 64, 64);
      RenderSystem.m_69461_();
      this.f_100534_.f_91062_.m_92883_(p_100558_, this.f_100537_, (float)k, (float)l, f_100532_);
      if (this.f_100539_) {
         GuiComponent.m_93172_(p_100558_, i, j, i + 24, j + 24, f_100529_);
      }

      if (this.f_100540_ != null && this.f_100541_ != null) {
         float f = this.f_100544_;
         this.f_100540_.f_93620_ = p_100561_ + (p_100562_ - this.f_100540_.m_5711_() - 4);
         this.f_100540_.f_93621_ = p_100560_ + (p_100563_ - this.f_100540_.m_93694_()) / 2;
         this.f_100540_.m_6305_(p_100558_, p_100564_, p_100565_, p_100567_);
         this.f_100541_.f_93620_ = p_100561_ + (p_100562_ - this.f_100541_.m_5711_() - 4);
         this.f_100541_.f_93621_ = p_100560_ + (p_100563_ - this.f_100541_.m_93694_()) / 2;
         this.f_100541_.m_6305_(p_100558_, p_100564_, p_100565_, p_100567_);
         if (f == this.f_100544_) {
            this.f_100544_ = 0.0F;
         }
      }

   }

   public List<? extends GuiEventListener> m_6702_() {
      return this.f_100535_;
   }

   public List<? extends NarratableEntry> m_142437_() {
      return this.f_100535_;
   }

   public String m_100600_() {
      return this.f_100537_;
   }

   public UUID m_100618_() {
      return this.f_100536_;
   }

   public void m_100619_(boolean p_100620_) {
      this.f_100539_ = p_100620_;
   }

   private void m_100596_(boolean p_100597_, Component p_100598_) {
      this.f_100541_.f_93624_ = p_100597_;
      this.f_100540_.f_93624_ = !p_100597_;
      this.f_100534_.f_91065_.m_93076_().m_93785_(p_100598_);
      NarratorChatListener.f_93311_.m_168785_(p_100598_);
   }

   MutableComponent m_100594_(MutableComponent p_100595_) {
      Component component = this.m_100621_();
      return component == TextComponent.f_131282_ ? (new TextComponent(this.f_100537_)).m_130946_(", ").m_7220_(p_100595_) : (new TextComponent(this.f_100537_)).m_130946_(", ").m_7220_(component).m_130946_(", ").m_7220_(p_100595_);
   }

   private Component m_100621_() {
      boolean flag = this.f_100534_.m_91266_().m_100686_(this.f_100536_);
      boolean flag1 = this.f_100534_.m_91266_().m_100688_(this.f_100536_);
      if (flag1 && this.f_100539_) {
         return f_100549_;
      } else if (flag && this.f_100539_) {
         return f_100548_;
      } else if (flag1) {
         return f_100546_;
      } else if (flag) {
         return f_100545_;
      } else {
         return this.f_100539_ ? f_100547_ : TextComponent.f_131282_;
      }
   }

   static void m_100588_(SocialInteractionsScreen p_100589_, PoseStack p_100590_, List<FormattedCharSequence> p_100591_, int p_100592_, int p_100593_) {
      p_100589_.m_96617_(p_100590_, p_100591_, p_100592_, p_100593_);
      p_100589_.m_100777_((Runnable)null);
   }
}