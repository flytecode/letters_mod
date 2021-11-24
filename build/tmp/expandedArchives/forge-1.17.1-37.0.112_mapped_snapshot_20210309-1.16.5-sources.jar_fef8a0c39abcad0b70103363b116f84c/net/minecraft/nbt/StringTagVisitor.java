package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class StringTagVisitor implements TagVisitor {
   private static final Pattern f_178155_ = Pattern.compile("[A-Za-z0-9._+-]+");
   private final StringBuilder f_178156_ = new StringBuilder();

   public String m_178187_(Tag p_178188_) {
      p_178188_.m_142327_(this);
      return this.f_178156_.toString();
   }

   public void m_142614_(StringTag p_178186_) {
      this.f_178156_.append(StringTag.m_129303_(p_178186_.m_7916_()));
   }

   public void m_141946_(ByteTag p_178164_) {
      this.f_178156_.append((Object)p_178164_.m_8103_()).append('b');
   }

   public void m_142183_(ShortTag p_178184_) {
      this.f_178156_.append((Object)p_178184_.m_8103_()).append('s');
   }

   public void m_142045_(IntTag p_178176_) {
      this.f_178156_.append((Object)p_178176_.m_8103_());
   }

   public void m_142046_(LongTag p_178182_) {
      this.f_178156_.append((Object)p_178182_.m_8103_()).append('L');
   }

   public void m_142181_(FloatTag p_178172_) {
      this.f_178156_.append(p_178172_.m_7057_()).append('f');
   }

   public void m_142121_(DoubleTag p_178168_) {
      this.f_178156_.append(p_178168_.m_7061_()).append('d');
   }

   public void m_142154_(ByteArrayTag p_178162_) {
      this.f_178156_.append("[B;");
      byte[] abyte = p_178162_.m_128227_();

      for(int i = 0; i < abyte.length; ++i) {
         if (i != 0) {
            this.f_178156_.append(',');
         }

         this.f_178156_.append((int)abyte[i]).append('B');
      }

      this.f_178156_.append(']');
   }

   public void m_142251_(IntArrayTag p_178174_) {
      this.f_178156_.append("[I;");
      int[] aint = p_178174_.m_128648_();

      for(int i = 0; i < aint.length; ++i) {
         if (i != 0) {
            this.f_178156_.append(',');
         }

         this.f_178156_.append(aint[i]);
      }

      this.f_178156_.append(']');
   }

   public void m_142309_(LongArrayTag p_178180_) {
      this.f_178156_.append("[L;");
      long[] along = p_178180_.m_128851_();

      for(int i = 0; i < along.length; ++i) {
         if (i != 0) {
            this.f_178156_.append(',');
         }

         this.f_178156_.append(along[i]).append('L');
      }

      this.f_178156_.append(']');
   }

   public void m_142447_(ListTag p_178178_) {
      this.f_178156_.append('[');

      for(int i = 0; i < p_178178_.size(); ++i) {
         if (i != 0) {
            this.f_178156_.append(',');
         }

         this.f_178156_.append((new StringTagVisitor()).m_178187_(p_178178_.get(i)));
      }

      this.f_178156_.append(']');
   }

   public void m_142303_(CompoundTag p_178166_) {
      this.f_178156_.append('{');
      List<String> list = Lists.newArrayList(p_178166_.m_128431_());
      Collections.sort(list);

      for(String s : list) {
         if (this.f_178156_.length() != 1) {
            this.f_178156_.append(',');
         }

         this.f_178156_.append(m_178159_(s)).append(':').append((new StringTagVisitor()).m_178187_(p_178166_.m_128423_(s)));
      }

      this.f_178156_.append('}');
   }

   protected static String m_178159_(String p_178160_) {
      return f_178155_.matcher(p_178160_).matches() ? p_178160_ : StringTag.m_129303_(p_178160_);
   }

   public void m_142384_(EndTag p_178170_) {
      this.f_178156_.append("END");
   }
}