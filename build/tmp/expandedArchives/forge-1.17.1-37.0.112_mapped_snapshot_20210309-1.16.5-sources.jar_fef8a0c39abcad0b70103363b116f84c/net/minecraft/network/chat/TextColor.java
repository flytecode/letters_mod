package net.minecraft.network.chat;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;

public final class TextColor {
   private static final String f_178538_ = "#";
   private static final Map<ChatFormatting, TextColor> f_131255_ = Stream.of(ChatFormatting.values()).filter(ChatFormatting::m_126664_).collect(ImmutableMap.toImmutableMap(Function.identity(), (p_131276_) -> {
      return new TextColor(p_131276_.m_126665_(), p_131276_.m_126666_());
   }));
   private static final Map<String, TextColor> f_131256_ = f_131255_.values().stream().collect(ImmutableMap.toImmutableMap((p_131273_) -> {
      return p_131273_.f_131258_;
   }, Function.identity()));
   private final int f_131257_;
   @Nullable
   private final String f_131258_;

   private TextColor(int p_131263_, String p_131264_) {
      this.f_131257_ = p_131263_;
      this.f_131258_ = p_131264_;
   }

   private TextColor(int p_131261_) {
      this.f_131257_ = p_131261_;
      this.f_131258_ = null;
   }

   public int m_131265_() {
      return this.f_131257_;
   }

   public String m_131274_() {
      return this.f_131258_ != null ? this.f_131258_ : this.m_131277_();
   }

   private String m_131277_() {
      return String.format("#%06X", this.f_131257_);
   }

   public boolean equals(Object p_131279_) {
      if (this == p_131279_) {
         return true;
      } else if (p_131279_ != null && this.getClass() == p_131279_.getClass()) {
         TextColor textcolor = (TextColor)p_131279_;
         return this.f_131257_ == textcolor.f_131257_;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(this.f_131257_, this.f_131258_);
   }

   public String toString() {
      return this.f_131258_ != null ? this.f_131258_ : this.m_131277_();
   }

   @Nullable
   public static TextColor m_131270_(ChatFormatting p_131271_) {
      return f_131255_.get(p_131271_);
   }

   public static TextColor m_131266_(int p_131267_) {
      return new TextColor(p_131267_);
   }

   @Nullable
   public static TextColor m_131268_(String p_131269_) {
      if (p_131269_.startsWith("#")) {
         try {
            int i = Integer.parseInt(p_131269_.substring(1), 16);
            return m_131266_(i);
         } catch (NumberFormatException numberformatexception) {
            return null;
         }
      } else {
         return f_131256_.get(p_131269_);
      }
   }
}