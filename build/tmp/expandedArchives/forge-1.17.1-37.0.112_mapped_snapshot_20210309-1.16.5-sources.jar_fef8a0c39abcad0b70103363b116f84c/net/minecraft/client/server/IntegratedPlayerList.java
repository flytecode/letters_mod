package net.minecraft.client.server;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IntegratedPlayerList extends PlayerList {
   private CompoundTag f_120001_;

   public IntegratedPlayerList(IntegratedServer p_120003_, RegistryAccess.RegistryHolder p_120004_, PlayerDataStorage p_120005_) {
      super(p_120003_, p_120004_, p_120005_, 8);
      this.m_11217_(10);
   }

   protected void m_6765_(ServerPlayer p_120011_) {
      if (p_120011_.m_7755_().getString().equals(this.m_7873_().m_129791_())) {
         this.f_120001_ = p_120011_.m_20240_(new CompoundTag());
      }

      super.m_6765_(p_120011_);
   }

   public Component m_6418_(SocketAddress p_120007_, GameProfile p_120008_) {
      return (Component)(p_120008_.getName().equalsIgnoreCase(this.m_7873_().m_129791_()) && this.m_11255_(p_120008_.getName()) != null ? new TranslatableComponent("multiplayer.disconnect.name_taken") : super.m_6418_(p_120007_, p_120008_));
   }

   public IntegratedServer m_7873_() {
      return (IntegratedServer)super.m_7873_();
   }

   public CompoundTag m_6960_() {
      return this.f_120001_;
   }
}