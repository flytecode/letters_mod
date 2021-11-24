package net.minecraft.server.packs;

import com.mojang.bridge.game.GameVersion;

public enum PackType {
   CLIENT_RESOURCES("assets", com.mojang.bridge.game.PackType.RESOURCE),
   SERVER_DATA("data", com.mojang.bridge.game.PackType.DATA);

   private final String f_10298_;
   private final com.mojang.bridge.game.PackType f_143750_;

   private PackType(String p_143754_, com.mojang.bridge.game.PackType p_143755_) {
      this.f_10298_ = p_143754_;
      this.f_143750_ = p_143755_;
   }

   public String m_10305_() {
      return this.f_10298_;
   }

   public int m_143756_(GameVersion p_143757_) {
      return p_143757_.getPackVersion(this.f_143750_);
   }
}