package net.minecraft.client.gui.spectator;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerMenuItem implements SpectatorMenuItem {
   private final GameProfile f_101752_;
   private final ResourceLocation f_101753_;
   private final Component f_101754_;

   public PlayerMenuItem(GameProfile p_101756_) {
      this.f_101752_ = p_101756_;
      Minecraft minecraft = Minecraft.m_91087_();
      Map<Type, MinecraftProfileTexture> map = minecraft.m_91109_().m_118815_(p_101756_);
      if (map.containsKey(Type.SKIN)) {
         this.f_101753_ = minecraft.m_91109_().m_118825_(map.get(Type.SKIN), Type.SKIN);
      } else {
         this.f_101753_ = DefaultPlayerSkin.m_118627_(Player.m_36198_(p_101756_));
      }

      this.f_101754_ = new TextComponent(p_101756_.getName());
   }

   public void m_7608_(SpectatorMenu p_101762_) {
      Minecraft.m_91087_().m_91403_().m_104955_(new ServerboundTeleportToEntityPacket(this.f_101752_.getId()));
   }

   public Component m_7869_() {
      return this.f_101754_;
   }

   public void m_6252_(PoseStack p_101758_, float p_101759_, int p_101760_) {
      RenderSystem.m_157456_(0, this.f_101753_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, (float)p_101760_ / 255.0F);
      GuiComponent.m_93160_(p_101758_, 2, 2, 12, 12, 8.0F, 8.0F, 8, 8, 64, 64);
      GuiComponent.m_93160_(p_101758_, 2, 2, 12, 12, 40.0F, 8.0F, 8, 8, 64, 64);
   }

   public boolean m_7304_() {
      return true;
   }
}