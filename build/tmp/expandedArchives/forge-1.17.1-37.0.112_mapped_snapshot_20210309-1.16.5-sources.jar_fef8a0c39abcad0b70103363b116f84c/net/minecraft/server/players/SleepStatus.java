package net.minecraft.server.players;

import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class SleepStatus {
   private int f_143998_;
   private int f_143999_;

   public boolean m_144002_(int p_144003_) {
      return this.f_143999_ >= this.m_144010_(p_144003_);
   }

   public boolean m_144004_(int p_144005_, List<ServerPlayer> p_144006_) {
      int i = (int)p_144006_.stream().filter(Player::m_36317_).count();
      return i >= this.m_144010_(p_144005_);
   }

   public int m_144010_(int p_144011_) {
      return Math.max(1, Mth.m_14167_((float)(this.f_143998_ * p_144011_) / 100.0F));
   }

   public void m_144001_() {
      this.f_143999_ = 0;
   }

   public int m_144009_() {
      return this.f_143999_;
   }

   public boolean m_144007_(List<ServerPlayer> p_144008_) {
      int i = this.f_143998_;
      int j = this.f_143999_;
      this.f_143998_ = 0;
      this.f_143999_ = 0;

      for(ServerPlayer serverplayer : p_144008_) {
         if (!serverplayer.m_5833_()) {
            ++this.f_143998_;
            if (serverplayer.m_5803_()) {
               ++this.f_143999_;
            }
         }
      }

      return (j > 0 || this.f_143999_ > 0) && (i != this.f_143998_ || j != this.f_143999_);
   }
}