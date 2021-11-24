package net.minecraft.world.level.saveddata.maps;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class MapDecoration {
   private final MapDecoration.Type f_77791_;
   private final byte f_77792_;
   private final byte f_77793_;
   private final byte f_77794_;
   @Nullable
   private final Component f_77795_;

   public MapDecoration(MapDecoration.Type p_77797_, byte p_77798_, byte p_77799_, byte p_77800_, @Nullable Component p_77801_) {
      this.f_77791_ = p_77797_;
      this.f_77792_ = p_77798_;
      this.f_77793_ = p_77799_;
      this.f_77794_ = p_77800_;
      this.f_77795_ = p_77801_;
   }

   public byte m_77802_() {
      return this.f_77791_.m_77853_();
   }

   public MapDecoration.Type m_77803_() {
      return this.f_77791_;
   }

   public byte m_77804_() {
      return this.f_77792_;
   }

   public byte m_77805_() {
      return this.f_77793_;
   }

   public byte m_77806_() {
      return this.f_77794_;
   }

   public boolean m_77809_() {
      return this.f_77791_.m_77856_();
   }

   @Nullable
   public Component m_77810_() {
      return this.f_77795_;
   }

   public boolean equals(Object p_77808_) {
      if (this == p_77808_) {
         return true;
      } else if (!(p_77808_ instanceof MapDecoration)) {
         return false;
      } else {
         MapDecoration mapdecoration = (MapDecoration)p_77808_;
         return this.f_77791_ == mapdecoration.f_77791_ && this.f_77794_ == mapdecoration.f_77794_ && this.f_77792_ == mapdecoration.f_77792_ && this.f_77793_ == mapdecoration.f_77793_ && Objects.equals(this.f_77795_, mapdecoration.f_77795_);
      }
   }

   public int hashCode() {
      int i = this.f_77791_.m_77853_();
      i = 31 * i + this.f_77792_;
      i = 31 * i + this.f_77793_;
      i = 31 * i + this.f_77794_;
      return 31 * i + Objects.hashCode(this.f_77795_);
   }

   /**
    * Renders this decoration, useful for custom sprite sheets.
    * @param index The index of this icon in the MapData's list. Used by vanilla to offset the Z-coordinate to prevent Z-fighting
    * @return false to run vanilla logic for this decoration, true to skip it
    */
   public boolean render(int index) {
      return false;
   }

   public static enum Type {
      PLAYER(false, true),
      FRAME(true, true),
      RED_MARKER(false, true),
      BLUE_MARKER(false, true),
      TARGET_X(true, false),
      TARGET_POINT(true, false),
      PLAYER_OFF_MAP(false, true),
      PLAYER_OFF_LIMITS(false, true),
      MANSION(true, 5393476, false),
      MONUMENT(true, 3830373, false),
      BANNER_WHITE(true, true),
      BANNER_ORANGE(true, true),
      BANNER_MAGENTA(true, true),
      BANNER_LIGHT_BLUE(true, true),
      BANNER_YELLOW(true, true),
      BANNER_LIME(true, true),
      BANNER_PINK(true, true),
      BANNER_GRAY(true, true),
      BANNER_LIGHT_GRAY(true, true),
      BANNER_CYAN(true, true),
      BANNER_PURPLE(true, true),
      BANNER_BLUE(true, true),
      BANNER_BROWN(true, true),
      BANNER_GREEN(true, true),
      BANNER_RED(true, true),
      BANNER_BLACK(true, true),
      RED_X(true, false);

      private final byte f_77813_;
      private final boolean f_77814_;
      private final int f_77815_;
      private final boolean f_181294_;

      private Type(boolean p_181304_, boolean p_181305_) {
         this(p_181304_, -1, p_181305_);
      }

      private Type(boolean p_181298_, int p_181299_, boolean p_181300_) {
         this.f_181294_ = p_181300_;
         this.f_77813_ = (byte)this.ordinal();
         this.f_77814_ = p_181298_;
         this.f_77815_ = p_181299_;
      }

      public byte m_77853_() {
         return this.f_77813_;
      }

      public boolean m_77856_() {
         return this.f_77814_;
      }

      public boolean m_77857_() {
         return this.f_77815_ >= 0;
      }

      public int m_77858_() {
         return this.f_77815_;
      }

      public static MapDecoration.Type m_77854_(byte p_77855_) {
         return values()[Mth.m_14045_(p_77855_, 0, values().length - 1)];
      }

      public boolean m_181306_() {
         return this.f_181294_;
      }
   }
}
