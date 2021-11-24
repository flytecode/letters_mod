package net.minecraft.world.level.entity;

import net.minecraft.server.level.ChunkHolder;

public enum Visibility {
   HIDDEN(false, false),
   TRACKED(true, false),
   TICKING(true, true);

   private final boolean f_157682_;
   private final boolean f_157683_;

   private Visibility(boolean p_157689_, boolean p_157690_) {
      this.f_157682_ = p_157689_;
      this.f_157683_ = p_157690_;
   }

   public boolean m_157691_() {
      return this.f_157683_;
   }

   public boolean m_157694_() {
      return this.f_157682_;
   }

   public static Visibility m_157692_(ChunkHolder.FullChunkStatus p_157693_) {
      if (p_157693_.m_140114_(ChunkHolder.FullChunkStatus.ENTITY_TICKING)) {
         return TICKING;
      } else {
         return p_157693_.m_140114_(ChunkHolder.FullChunkStatus.BORDER) ? TRACKED : HIDDEN;
      }
   }
}