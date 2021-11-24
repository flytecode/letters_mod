package net.minecraft.client.gui.screens.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GameModeSwitcherScreen extends Screen {
   static final ResourceLocation f_97541_ = new ResourceLocation("textures/gui/container/gamemode_switcher.png");
   private static final int f_169582_ = 128;
   private static final int f_169583_ = 128;
   private static final int f_169584_ = 26;
   private static final int f_169585_ = 5;
   private static final int f_169586_ = 31;
   private static final int f_169587_ = 5;
   private static final int f_97542_ = GameModeSwitcherScreen.GameModeIcon.values().length * 31 - 5;
   private static final Component f_97543_ = new TranslatableComponent("debug.gamemodes.select_next", (new TranslatableComponent("debug.gamemodes.press_f4")).m_130940_(ChatFormatting.AQUA));
   private final Optional<GameModeSwitcherScreen.GameModeIcon> f_97544_;
   private Optional<GameModeSwitcherScreen.GameModeIcon> f_97545_ = Optional.empty();
   private int f_97546_;
   private int f_97547_;
   private boolean f_97548_;
   private final List<GameModeSwitcherScreen.GameModeSlot> f_97549_ = Lists.newArrayList();

   public GameModeSwitcherScreen() {
      super(NarratorChatListener.f_93310_);
      this.f_97544_ = GameModeSwitcherScreen.GameModeIcon.m_97612_(this.m_97575_());
   }

   private GameType m_97575_() {
      MultiPlayerGameMode multiplayergamemode = Minecraft.m_91087_().f_91072_;
      GameType gametype = multiplayergamemode.m_105294_();
      if (gametype != null) {
         return gametype;
      } else {
         return multiplayergamemode.m_105295_() == GameType.CREATIVE ? GameType.SURVIVAL : GameType.CREATIVE;
      }
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_97545_ = this.f_97544_.isPresent() ? this.f_97544_ : GameModeSwitcherScreen.GameModeIcon.m_97612_(this.f_96541_.f_91072_.m_105295_());

      for(int i = 0; i < GameModeSwitcherScreen.GameModeIcon.f_97585_.length; ++i) {
         GameModeSwitcherScreen.GameModeIcon gamemodeswitcherscreen$gamemodeicon = GameModeSwitcherScreen.GameModeIcon.f_97585_[i];
         this.f_97549_.add(new GameModeSwitcherScreen.GameModeSlot(gamemodeswitcherscreen$gamemodeicon, this.f_96543_ / 2 - f_97542_ / 2 + i * 31, this.f_96544_ / 2 - 31));
      }

   }

   public void m_6305_(PoseStack p_97557_, int p_97558_, int p_97559_, float p_97560_) {
      if (!this.m_97577_()) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         p_97557_.m_85836_();
         RenderSystem.m_69478_();
         RenderSystem.m_157456_(0, f_97541_);
         int i = this.f_96543_ / 2 - 62;
         int j = this.f_96544_ / 2 - 31 - 27;
         m_93133_(p_97557_, i, j, 0.0F, 0.0F, 125, 75, 128, 128);
         p_97557_.m_85849_();
         super.m_6305_(p_97557_, p_97558_, p_97559_, p_97560_);
         this.f_97545_.ifPresent((p_97563_) -> {
            m_93215_(p_97557_, this.f_96547_, p_97563_.m_97597_(), this.f_96543_ / 2, this.f_96544_ / 2 - 31 - 20, -1);
         });
         m_93215_(p_97557_, this.f_96547_, f_97543_, this.f_96543_ / 2, this.f_96544_ / 2 + 5, 16777215);
         if (!this.f_97548_) {
            this.f_97546_ = p_97558_;
            this.f_97547_ = p_97559_;
            this.f_97548_ = true;
         }

         boolean flag = this.f_97546_ == p_97558_ && this.f_97547_ == p_97559_;

         for(GameModeSwitcherScreen.GameModeSlot gamemodeswitcherscreen$gamemodeslot : this.f_97549_) {
            gamemodeswitcherscreen$gamemodeslot.m_6305_(p_97557_, p_97558_, p_97559_, p_97560_);
            this.f_97545_.ifPresent((p_97569_) -> {
               gamemodeswitcherscreen$gamemodeslot.m_97643_(p_97569_ == gamemodeswitcherscreen$gamemodeslot.f_97623_);
            });
            if (!flag && gamemodeswitcherscreen$gamemodeslot.m_5702_()) {
               this.f_97545_ = Optional.of(gamemodeswitcherscreen$gamemodeslot.f_97623_);
            }
         }

      }
   }

   private void m_97576_() {
      m_97564_(this.f_96541_, this.f_97545_);
   }

   private static void m_97564_(Minecraft p_97565_, Optional<GameModeSwitcherScreen.GameModeIcon> p_97566_) {
      if (p_97565_.f_91072_ != null && p_97565_.f_91074_ != null && p_97566_.isPresent()) {
         Optional<GameModeSwitcherScreen.GameModeIcon> optional = GameModeSwitcherScreen.GameModeIcon.m_97612_(p_97565_.f_91072_.m_105295_());
         GameModeSwitcherScreen.GameModeIcon gamemodeswitcherscreen$gamemodeicon = p_97566_.get();
         if (optional.isPresent() && p_97565_.f_91074_.m_20310_(2) && gamemodeswitcherscreen$gamemodeicon != optional.get()) {
            p_97565_.f_91074_.m_108739_(gamemodeswitcherscreen$gamemodeicon.m_97611_());
         }

      }
   }

   private boolean m_97577_() {
      if (!InputConstants.m_84830_(this.f_96541_.m_91268_().m_85439_(), 292)) {
         this.m_97576_();
         this.f_96541_.m_91152_((Screen)null);
         return true;
      } else {
         return false;
      }
   }

   public boolean m_7933_(int p_97553_, int p_97554_, int p_97555_) {
      if (p_97553_ == 293 && this.f_97545_.isPresent()) {
         this.f_97548_ = false;
         this.f_97545_ = this.f_97545_.get().m_97616_();
         return true;
      } else {
         return super.m_7933_(p_97553_, p_97554_, p_97555_);
      }
   }

   public boolean m_7043_() {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   static enum GameModeIcon {
      CREATIVE(new TranslatableComponent("gameMode.creative"), "/gamemode creative", new ItemStack(Blocks.f_50440_)),
      SURVIVAL(new TranslatableComponent("gameMode.survival"), "/gamemode survival", new ItemStack(Items.f_42383_)),
      ADVENTURE(new TranslatableComponent("gameMode.adventure"), "/gamemode adventure", new ItemStack(Items.f_42676_)),
      SPECTATOR(new TranslatableComponent("gameMode.spectator"), "/gamemode spectator", new ItemStack(Items.f_42545_));

      protected static final GameModeSwitcherScreen.GameModeIcon[] f_97585_ = values();
      private static final int f_169591_ = 16;
      protected static final int f_169590_ = 5;
      final Component f_97586_;
      final String f_97587_;
      final ItemStack f_97588_;

      private GameModeIcon(Component p_97594_, String p_97595_, ItemStack p_97596_) {
         this.f_97586_ = p_97594_;
         this.f_97587_ = p_97595_;
         this.f_97588_ = p_97596_;
      }

      void m_97607_(ItemRenderer p_97608_, int p_97609_, int p_97610_) {
         p_97608_.m_115203_(this.f_97588_, p_97609_, p_97610_);
      }

      Component m_97597_() {
         return this.f_97586_;
      }

      String m_97611_() {
         return this.f_97587_;
      }

      Optional<GameModeSwitcherScreen.GameModeIcon> m_97616_() {
         switch(this) {
         case CREATIVE:
            return Optional.of(SURVIVAL);
         case SURVIVAL:
            return Optional.of(ADVENTURE);
         case ADVENTURE:
            return Optional.of(SPECTATOR);
         default:
            return Optional.of(CREATIVE);
         }
      }

      static Optional<GameModeSwitcherScreen.GameModeIcon> m_97612_(GameType p_97613_) {
         switch(p_97613_) {
         case SPECTATOR:
            return Optional.of(SPECTATOR);
         case SURVIVAL:
            return Optional.of(SURVIVAL);
         case CREATIVE:
            return Optional.of(CREATIVE);
         case ADVENTURE:
            return Optional.of(ADVENTURE);
         default:
            return Optional.empty();
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public class GameModeSlot extends AbstractWidget {
      final GameModeSwitcherScreen.GameModeIcon f_97623_;
      private boolean f_97624_;

      public GameModeSlot(GameModeSwitcherScreen.GameModeIcon p_97627_, int p_97628_, int p_97629_) {
         super(p_97628_, p_97629_, 26, 26, p_97627_.m_97597_());
         this.f_97623_ = p_97627_;
      }

      public void m_6303_(PoseStack p_97636_, int p_97637_, int p_97638_, float p_97639_) {
         Minecraft minecraft = Minecraft.m_91087_();
         this.m_97630_(p_97636_, minecraft.m_91097_());
         this.f_97623_.m_97607_(GameModeSwitcherScreen.this.f_96542_, this.f_93620_ + 5, this.f_93621_ + 5);
         if (this.f_97624_) {
            this.m_97640_(p_97636_, minecraft.m_91097_());
         }

      }

      public void m_142291_(NarrationElementOutput p_169594_) {
         this.m_168802_(p_169594_);
      }

      public boolean m_5702_() {
         return super.m_5702_() || this.f_97624_;
      }

      public void m_97643_(boolean p_97644_) {
         this.f_97624_ = p_97644_;
      }

      private void m_97630_(PoseStack p_97631_, TextureManager p_97632_) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, GameModeSwitcherScreen.f_97541_);
         p_97631_.m_85836_();
         p_97631_.m_85837_((double)this.f_93620_, (double)this.f_93621_, 0.0D);
         m_93133_(p_97631_, 0, 0, 0.0F, 75.0F, 26, 26, 128, 128);
         p_97631_.m_85849_();
      }

      private void m_97640_(PoseStack p_97641_, TextureManager p_97642_) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, GameModeSwitcherScreen.f_97541_);
         p_97641_.m_85836_();
         p_97641_.m_85837_((double)this.f_93620_, (double)this.f_93621_, 0.0D);
         m_93133_(p_97641_, 0, 0, 26.0F, 75.0F, 26, 26, 128, 128);
         p_97641_.m_85849_();
      }
   }
}