package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EndTag implements Tag {
   private static final int f_177861_ = 64;
   public static final TagType<EndTag> f_128533_ = new TagType<EndTag>() {
      public EndTag m_7300_(DataInput p_128550_, int p_128551_, NbtAccounter p_128552_) {
         p_128552_.m_6800_(64L);
         return EndTag.f_128534_;
      }

      public String m_5987_() {
         return "END";
      }

      public String m_5986_() {
         return "TAG_End";
      }

      public boolean m_7064_() {
         return true;
      }
   };
   public static final EndTag f_128534_ = new EndTag();

   private EndTag() {
   }

   public void m_6434_(DataOutput p_128539_) throws IOException {
   }

   public byte m_7060_() {
      return 0;
   }

   public TagType<EndTag> m_6458_() {
      return f_128533_;
   }

   public String toString() {
      return this.m_7916_();
   }

   public EndTag m_6426_() {
      return this;
   }

   public void m_142327_(TagVisitor p_177863_) {
      p_177863_.m_142384_(this);
   }
}