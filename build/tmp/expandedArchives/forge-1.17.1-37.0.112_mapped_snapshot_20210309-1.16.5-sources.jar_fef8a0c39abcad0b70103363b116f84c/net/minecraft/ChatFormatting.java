package net.minecraft;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum ChatFormatting {
   BLACK("BLACK", '0', 0, 0),
   DARK_BLUE("DARK_BLUE", '1', 1, 170),
   DARK_GREEN("DARK_GREEN", '2', 2, 43520),
   DARK_AQUA("DARK_AQUA", '3', 3, 43690),
   DARK_RED("DARK_RED", '4', 4, 11141120),
   DARK_PURPLE("DARK_PURPLE", '5', 5, 11141290),
   GOLD("GOLD", '6', 6, 16755200),
   GRAY("GRAY", '7', 7, 11184810),
   DARK_GRAY("DARK_GRAY", '8', 8, 5592405),
   BLUE("BLUE", '9', 9, 5592575),
   GREEN("GREEN", 'a', 10, 5635925),
   AQUA("AQUA", 'b', 11, 5636095),
   RED("RED", 'c', 12, 16733525),
   LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 16733695),
   YELLOW("YELLOW", 'e', 14, 16777045),
   WHITE("WHITE", 'f', 15, 16777215),
   OBFUSCATED("OBFUSCATED", 'k', true),
   BOLD("BOLD", 'l', true),
   STRIKETHROUGH("STRIKETHROUGH", 'm', true),
   UNDERLINE("UNDERLINE", 'n', true),
   ITALIC("ITALIC", 'o', true),
   RESET("RESET", 'r', -1, (Integer)null);

   public static final char f_178509_ = '\u00a7';
   private static final Map<String, ChatFormatting> f_126619_ = Arrays.stream(values()).collect(Collectors.toMap((p_126660_) -> {
      return m_126662_(p_126660_.f_126621_);
   }, (p_126652_) -> {
      return p_126652_;
   }));
   private static final Pattern f_126620_ = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
   private final String f_126621_;
   private final char f_126622_;
   private final boolean f_126592_;
   private final String f_126593_;
   private final int f_126594_;
   @Nullable
   private final Integer f_126595_;

   private static String m_126662_(String p_126663_) {
      return p_126663_.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
   }

   private ChatFormatting(String p_126627_, @Nullable char p_126628_, int p_126629_, Integer p_126630_) {
      this(p_126627_, p_126628_, false, p_126629_, p_126630_);
   }

   private ChatFormatting(String p_126634_, char p_126635_, boolean p_126636_) {
      this(p_126634_, p_126635_, p_126636_, -1, (Integer)null);
   }

   private ChatFormatting(String p_126640_, char p_126641_, @Nullable boolean p_126642_, int p_126643_, Integer p_126644_) {
      this.f_126621_ = p_126640_;
      this.f_126622_ = p_126641_;
      this.f_126592_ = p_126642_;
      this.f_126594_ = p_126643_;
      this.f_126595_ = p_126644_;
      this.f_126593_ = "\u00a7" + p_126641_;
   }

   public char m_178510_() {
      return this.f_126622_;
   }

   public int m_126656_() {
      return this.f_126594_;
   }

   public boolean m_126661_() {
      return this.f_126592_;
   }

   public boolean m_126664_() {
      return !this.f_126592_ && this != RESET;
   }

   @Nullable
   public Integer m_126665_() {
      return this.f_126595_;
   }

   public String m_126666_() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   public String toString() {
      return this.f_126593_;
   }

   @Nullable
   public static String m_126649_(@Nullable String p_126650_) {
      return p_126650_ == null ? null : f_126620_.matcher(p_126650_).replaceAll("");
   }

   @Nullable
   public static ChatFormatting m_126657_(@Nullable String p_126658_) {
      return p_126658_ == null ? null : f_126619_.get(m_126662_(p_126658_));
   }

   @Nullable
   public static ChatFormatting m_126647_(int p_126648_) {
      if (p_126648_ < 0) {
         return RESET;
      } else {
         for(ChatFormatting chatformatting : values()) {
            if (chatformatting.m_126656_() == p_126648_) {
               return chatformatting;
            }
         }

         return null;
      }
   }

   @Nullable
   public static ChatFormatting m_126645_(char p_126646_) {
      char c0 = Character.toString(p_126646_).toLowerCase(Locale.ROOT).charAt(0);

      for(ChatFormatting chatformatting : values()) {
         if (chatformatting.f_126622_ == c0) {
            return chatformatting;
         }
      }

      return null;
   }

   public static Collection<String> m_126653_(boolean p_126654_, boolean p_126655_) {
      List<String> list = Lists.newArrayList();

      for(ChatFormatting chatformatting : values()) {
         if ((!chatformatting.m_126664_() || p_126654_) && (!chatformatting.m_126661_() || p_126655_)) {
            list.add(chatformatting.m_126666_());
         }
      }

      return list;
   }
}