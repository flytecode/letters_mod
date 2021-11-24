package net.minecraft.world;

import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public abstract class BossEvent {
   private final UUID f_18847_;
   protected Component f_18840_;
   protected float f_146638_;
   protected BossEvent.BossBarColor f_18842_;
   protected BossEvent.BossBarOverlay f_18843_;
   protected boolean f_18844_;
   protected boolean f_18845_;
   protected boolean f_18846_;

   public BossEvent(UUID p_18849_, Component p_18850_, BossEvent.BossBarColor p_18851_, BossEvent.BossBarOverlay p_18852_) {
      this.f_18847_ = p_18849_;
      this.f_18840_ = p_18850_;
      this.f_18842_ = p_18851_;
      this.f_18843_ = p_18852_;
      this.f_146638_ = 1.0F;
   }

   public UUID m_18860_() {
      return this.f_18847_;
   }

   public Component m_18861_() {
      return this.f_18840_;
   }

   public void m_6456_(Component p_18856_) {
      this.f_18840_ = p_18856_;
   }

   public float m_142717_() {
      return this.f_146638_;
   }

   public void m_142711_(float p_146639_) {
      this.f_146638_ = p_146639_;
   }

   public BossEvent.BossBarColor m_18862_() {
      return this.f_18842_;
   }

   public void m_6451_(BossEvent.BossBarColor p_18854_) {
      this.f_18842_ = p_18854_;
   }

   public BossEvent.BossBarOverlay m_18863_() {
      return this.f_18843_;
   }

   public void m_5648_(BossEvent.BossBarOverlay p_18855_) {
      this.f_18843_ = p_18855_;
   }

   public boolean m_18864_() {
      return this.f_18844_;
   }

   public BossEvent m_7003_(boolean p_18857_) {
      this.f_18844_ = p_18857_;
      return this;
   }

   public boolean m_18865_() {
      return this.f_18845_;
   }

   public BossEvent m_7005_(boolean p_18858_) {
      this.f_18845_ = p_18858_;
      return this;
   }

   public BossEvent m_7006_(boolean p_18859_) {
      this.f_18846_ = p_18859_;
      return this;
   }

   public boolean m_18866_() {
      return this.f_18846_;
   }

   public static enum BossBarColor {
      PINK("pink", ChatFormatting.RED),
      BLUE("blue", ChatFormatting.BLUE),
      RED("red", ChatFormatting.DARK_RED),
      GREEN("green", ChatFormatting.GREEN),
      YELLOW("yellow", ChatFormatting.YELLOW),
      PURPLE("purple", ChatFormatting.DARK_BLUE),
      WHITE("white", ChatFormatting.WHITE);

      private final String f_18874_;
      private final ChatFormatting f_18875_;

      private BossBarColor(String p_18881_, ChatFormatting p_18882_) {
         this.f_18874_ = p_18881_;
         this.f_18875_ = p_18882_;
      }

      public ChatFormatting m_18883_() {
         return this.f_18875_;
      }

      public String m_18886_() {
         return this.f_18874_;
      }

      public static BossEvent.BossBarColor m_18884_(String p_18885_) {
         for(BossEvent.BossBarColor bossevent$bossbarcolor : values()) {
            if (bossevent$bossbarcolor.f_18874_.equals(p_18885_)) {
               return bossevent$bossbarcolor;
            }
         }

         return WHITE;
      }
   }

   public static enum BossBarOverlay {
      PROGRESS("progress"),
      NOTCHED_6("notched_6"),
      NOTCHED_10("notched_10"),
      NOTCHED_12("notched_12"),
      NOTCHED_20("notched_20");

      private final String f_18895_;

      private BossBarOverlay(String p_18901_) {
         this.f_18895_ = p_18901_;
      }

      public String m_18902_() {
         return this.f_18895_;
      }

      public static BossEvent.BossBarOverlay m_18903_(String p_18904_) {
         for(BossEvent.BossBarOverlay bossevent$bossbaroverlay : values()) {
            if (bossevent$bossbaroverlay.f_18895_.equals(p_18904_)) {
               return bossevent$bossbaroverlay;
            }
         }

         return PROGRESS;
      }
   }
}