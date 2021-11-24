package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;

public interface TagType<T extends Tag> {
   T m_7300_(DataInput p_129379_, int p_129380_, NbtAccounter p_129381_) throws IOException;

   default boolean m_7064_() {
      return false;
   }

   String m_5987_();

   String m_5986_();

   static TagType<EndTag> m_129377_(final int p_129378_) {
      return new TagType<EndTag>() {
         public EndTag m_7300_(DataInput p_129387_, int p_129388_, NbtAccounter p_129389_) {
            throw new IllegalArgumentException("Invalid tag id: " + p_129378_);
         }

         public String m_5987_() {
            return "INVALID[" + p_129378_ + "]";
         }

         public String m_5986_() {
            return "UNKNOWN_" + p_129378_;
         }
      };
   }
}