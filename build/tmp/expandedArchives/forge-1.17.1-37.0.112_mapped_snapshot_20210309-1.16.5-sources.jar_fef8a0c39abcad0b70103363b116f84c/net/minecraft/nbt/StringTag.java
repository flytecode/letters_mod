package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class StringTag implements Tag {
   private static final int f_178148_ = 288;
   public static final TagType<StringTag> f_129288_ = new TagType<StringTag>() {
      public StringTag m_7300_(DataInput p_129315_, int p_129316_, NbtAccounter p_129317_) throws IOException {
         p_129317_.m_6800_(288L);
         String s = p_129315_.readUTF();
         p_129317_.readUTF(s);
         return StringTag.m_129297_(s);
      }

      public String m_5987_() {
         return "STRING";
      }

      public String m_5986_() {
         return "TAG_String";
      }

      public boolean m_7064_() {
         return true;
      }
   };
   private static final StringTag f_129289_ = new StringTag("");
   private static final char f_178149_ = '"';
   private static final char f_178150_ = '\'';
   private static final char f_178151_ = '\\';
   private static final char f_178152_ = '\u0000';
   private final String f_129290_;

   private StringTag(String p_129293_) {
      Objects.requireNonNull(p_129293_, "Null string not allowed");
      this.f_129290_ = p_129293_;
   }

   public static StringTag m_129297_(String p_129298_) {
      return p_129298_.isEmpty() ? f_129289_ : new StringTag(p_129298_);
   }

   public void m_6434_(DataOutput p_129296_) throws IOException {
      p_129296_.writeUTF(this.f_129290_);
   }

   public byte m_7060_() {
      return 8;
   }

   public TagType<StringTag> m_6458_() {
      return f_129288_;
   }

   public String toString() {
      return Tag.super.m_7916_();
   }

   public StringTag m_6426_() {
      return this;
   }

   public boolean equals(Object p_129308_) {
      if (this == p_129308_) {
         return true;
      } else {
         return p_129308_ instanceof StringTag && Objects.equals(this.f_129290_, ((StringTag)p_129308_).f_129290_);
      }
   }

   public int hashCode() {
      return this.f_129290_.hashCode();
   }

   public String m_7916_() {
      return this.f_129290_;
   }

   public void m_142327_(TagVisitor p_178154_) {
      p_178154_.m_142614_(this);
   }

   public static String m_129303_(String p_129304_) {
      StringBuilder stringbuilder = new StringBuilder(" ");
      char c0 = 0;

      for(int i = 0; i < p_129304_.length(); ++i) {
         char c1 = p_129304_.charAt(i);
         if (c1 == '\\') {
            stringbuilder.append('\\');
         } else if (c1 == '"' || c1 == '\'') {
            if (c0 == 0) {
               c0 = (char)(c1 == '"' ? 39 : 34);
            }

            if (c0 == c1) {
               stringbuilder.append('\\');
            }
         }

         stringbuilder.append(c1);
      }

      if (c0 == 0) {
         c0 = '"';
      }

      stringbuilder.setCharAt(0, c0);
      stringbuilder.append(c0);
      return stringbuilder.toString();
   }
}
