package net.minecraft.client;

import com.mojang.bridge.game.GameSession;
import java.util.UUID;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Session implements GameSession {
   private final int f_92319_;
   private final boolean f_92320_;
   private final String f_92321_;
   private final String f_92322_;
   private final UUID f_92323_;

   public Session(ClientLevel p_92325_, LocalPlayer p_92326_, ClientPacketListener p_92327_) {
      this.f_92319_ = p_92327_.m_105142_().size();
      this.f_92320_ = !p_92327_.m_6198_().m_129531_();
      this.f_92321_ = p_92325_.m_46791_().m_19036_();
      PlayerInfo playerinfo = p_92327_.m_104949_(p_92326_.m_142081_());
      if (playerinfo != null) {
         this.f_92322_ = playerinfo.m_105325_().m_46405_();
      } else {
         this.f_92322_ = "unknown";
      }

      this.f_92323_ = p_92327_.m_105150_();
   }

   public int getPlayerCount() {
      return this.f_92319_;
   }

   public boolean isRemoteServer() {
      return this.f_92320_;
   }

   public String getDifficulty() {
      return this.f_92321_;
   }

   public String getGameMode() {
      return this.f_92322_;
   }

   public UUID getSessionId() {
      return this.f_92323_;
   }
}