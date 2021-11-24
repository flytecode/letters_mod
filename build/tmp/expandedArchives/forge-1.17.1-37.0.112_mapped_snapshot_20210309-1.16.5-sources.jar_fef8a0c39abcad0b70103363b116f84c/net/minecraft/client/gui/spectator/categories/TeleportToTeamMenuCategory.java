package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TeleportToTeamMenuCategory implements SpectatorMenuCategory, SpectatorMenuItem {
   private static final Component f_101875_ = new TranslatableComponent("spectatorMenu.team_teleport");
   private static final Component f_101876_ = new TranslatableComponent("spectatorMenu.team_teleport.prompt");
   private final List<SpectatorMenuItem> f_101877_ = Lists.newArrayList();

   public TeleportToTeamMenuCategory() {
      Minecraft minecraft = Minecraft.m_91087_();

      for(PlayerTeam playerteam : minecraft.f_91073_.m_6188_().m_83491_()) {
         this.f_101877_.add(new TeleportToTeamMenuCategory.TeamSelectionItem(playerteam));
      }

   }

   public List<SpectatorMenuItem> m_5919_() {
      return this.f_101877_;
   }

   public Component m_5878_() {
      return f_101876_;
   }

   public void m_7608_(SpectatorMenu p_101886_) {
      p_101886_.m_101794_(this);
   }

   public Component m_7869_() {
      return f_101875_;
   }

   public void m_6252_(PoseStack p_101882_, float p_101883_, int p_101884_) {
      RenderSystem.m_157456_(0, SpectatorGui.f_94760_);
      GuiComponent.m_93133_(p_101882_, 0, 0, 16.0F, 0.0F, 16, 16, 256, 256);
   }

   public boolean m_7304_() {
      for(SpectatorMenuItem spectatormenuitem : this.f_101877_) {
         if (spectatormenuitem.m_7304_()) {
            return true;
         }
      }

      return false;
   }

   @OnlyIn(Dist.CLIENT)
   class TeamSelectionItem implements SpectatorMenuItem {
      private final PlayerTeam f_101891_;
      private final ResourceLocation f_101892_;
      private final List<PlayerInfo> f_101893_;

      public TeamSelectionItem(PlayerTeam p_101896_) {
         this.f_101891_ = p_101896_;
         this.f_101893_ = Lists.newArrayList();

         for(String s : p_101896_.m_6809_()) {
            PlayerInfo playerinfo = Minecraft.m_91087_().m_91403_().m_104938_(s);
            if (playerinfo != null) {
               this.f_101893_.add(playerinfo);
            }
         }

         if (this.f_101893_.isEmpty()) {
            this.f_101892_ = DefaultPlayerSkin.m_118626_();
         } else {
            String s1 = this.f_101893_.get((new Random()).nextInt(this.f_101893_.size())).m_105312_().getName();
            this.f_101892_ = AbstractClientPlayer.m_108556_(s1);
            AbstractClientPlayer.m_172521_(this.f_101892_, s1);
         }

      }

      public void m_7608_(SpectatorMenu p_101902_) {
         p_101902_.m_101794_(new TeleportToPlayerMenuCategory(this.f_101893_));
      }

      public Component m_7869_() {
         return this.f_101891_.m_83364_();
      }

      public void m_6252_(PoseStack p_101898_, float p_101899_, int p_101900_) {
         Integer integer = this.f_101891_.m_7414_().m_126665_();
         if (integer != null) {
            float f = (float)(integer >> 16 & 255) / 255.0F;
            float f1 = (float)(integer >> 8 & 255) / 255.0F;
            float f2 = (float)(integer & 255) / 255.0F;
            GuiComponent.m_93172_(p_101898_, 1, 1, 15, 15, Mth.m_14159_(f * p_101899_, f1 * p_101899_, f2 * p_101899_) | p_101900_ << 24);
         }

         RenderSystem.m_157456_(0, this.f_101892_);
         RenderSystem.m_157429_(p_101899_, p_101899_, p_101899_, (float)p_101900_ / 255.0F);
         GuiComponent.m_93160_(p_101898_, 2, 2, 12, 12, 8.0F, 8.0F, 8, 8, 64, 64);
         GuiComponent.m_93160_(p_101898_, 2, 2, 12, 12, 40.0F, 8.0F, 8, 8, 64, 64);
      }

      public boolean m_7304_() {
         return !this.f_101893_.isEmpty();
      }
   }
}