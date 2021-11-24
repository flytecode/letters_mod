package net.minecraft.world;

import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum Difficulty {
   PEACEFUL(0, "peaceful"),
   EASY(1, "easy"),
   NORMAL(2, "normal"),
   HARD(3, "hard");

   private static final Difficulty[] f_19018_ = Arrays.stream(values()).sorted(Comparator.comparingInt(Difficulty::m_19028_)).toArray((p_19035_) -> {
      return new Difficulty[p_19035_];
   });
   private final int f_19019_;
   private final String f_19020_;

   private Difficulty(int p_19026_, String p_19027_) {
      this.f_19019_ = p_19026_;
      this.f_19020_ = p_19027_;
   }

   public int m_19028_() {
      return this.f_19019_;
   }

   public Component m_19033_() {
      return new TranslatableComponent("options.difficulty." + this.f_19020_);
   }

   public static Difficulty m_19029_(int p_19030_) {
      return f_19018_[p_19030_ % f_19018_.length];
   }

   @Nullable
   public static Difficulty m_19031_(String p_19032_) {
      for(Difficulty difficulty : values()) {
         if (difficulty.f_19020_.equals(p_19032_)) {
            return difficulty;
         }
      }

      return null;
   }

   public String m_19036_() {
      return this.f_19020_;
   }
}