package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.Util;

public class SnbtPrinterTagVisitor implements TagVisitor {
   private static final Map<String, List<String>> f_178088_ = Util.m_137469_(Maps.newHashMap(), (p_178114_) -> {
      p_178114_.put("{}", Lists.newArrayList("DataVersion", "author", "size", "data", "entities", "palette", "palettes"));
      p_178114_.put("{}.data.[].{}", Lists.newArrayList("pos", "state", "nbt"));
      p_178114_.put("{}.entities.[].{}", Lists.newArrayList("blockPos", "pos"));
   });
   private static final Set<String> f_178089_ = Sets.newHashSet("{}.size.[]", "{}.data.[].{}", "{}.palette.[].{}", "{}.entities.[].{}");
   private static final Pattern f_178090_ = Pattern.compile("[A-Za-z0-9._+-]+");
   private static final String f_178091_ = String.valueOf(':');
   private static final String f_178092_ = String.valueOf(',');
   private static final String f_178093_ = "[";
   private static final String f_178094_ = "]";
   private static final String f_178095_ = ";";
   private static final String f_178096_ = " ";
   private static final String f_178097_ = "{";
   private static final String f_178098_ = "}";
   private static final String f_178099_ = "\n";
   private final String f_178100_;
   private final int f_178101_;
   private final List<String> f_178102_;
   private String f_178103_;

   public SnbtPrinterTagVisitor() {
      this("    ", 0, Lists.newArrayList());
   }

   public SnbtPrinterTagVisitor(String p_178107_, int p_178108_, List<String> p_178109_) {
      this.f_178100_ = p_178107_;
      this.f_178101_ = p_178108_;
      this.f_178102_ = p_178109_;
   }

   public String m_178141_(Tag p_178142_) {
      p_178142_.m_142327_(this);
      return this.f_178103_;
   }

   public void m_142614_(StringTag p_178140_) {
      this.f_178103_ = StringTag.m_129303_(p_178140_.m_7916_());
   }

   public void m_141946_(ByteTag p_178118_) {
      this.f_178103_ = p_178118_.m_8103_() + "b";
   }

   public void m_142183_(ShortTag p_178138_) {
      this.f_178103_ = p_178138_.m_8103_() + "s";
   }

   public void m_142045_(IntTag p_178130_) {
      this.f_178103_ = String.valueOf((Object)p_178130_.m_8103_());
   }

   public void m_142046_(LongTag p_178136_) {
      this.f_178103_ = p_178136_.m_8103_() + "L";
   }

   public void m_142181_(FloatTag p_178126_) {
      this.f_178103_ = p_178126_.m_7057_() + "f";
   }

   public void m_142121_(DoubleTag p_178122_) {
      this.f_178103_ = p_178122_.m_7061_() + "d";
   }

   public void m_142154_(ByteArrayTag p_178116_) {
      StringBuilder stringbuilder = (new StringBuilder("[")).append("B").append(";");
      byte[] abyte = p_178116_.m_128227_();

      for(int i = 0; i < abyte.length; ++i) {
         stringbuilder.append(" ").append((int)abyte[i]).append("B");
         if (i != abyte.length - 1) {
            stringbuilder.append(f_178092_);
         }
      }

      stringbuilder.append("]");
      this.f_178103_ = stringbuilder.toString();
   }

   public void m_142251_(IntArrayTag p_178128_) {
      StringBuilder stringbuilder = (new StringBuilder("[")).append("I").append(";");
      int[] aint = p_178128_.m_128648_();

      for(int i = 0; i < aint.length; ++i) {
         stringbuilder.append(" ").append(aint[i]);
         if (i != aint.length - 1) {
            stringbuilder.append(f_178092_);
         }
      }

      stringbuilder.append("]");
      this.f_178103_ = stringbuilder.toString();
   }

   public void m_142309_(LongArrayTag p_178134_) {
      String s = "L";
      StringBuilder stringbuilder = (new StringBuilder("[")).append("L").append(";");
      long[] along = p_178134_.m_128851_();

      for(int i = 0; i < along.length; ++i) {
         stringbuilder.append(" ").append(along[i]).append("L");
         if (i != along.length - 1) {
            stringbuilder.append(f_178092_);
         }
      }

      stringbuilder.append("]");
      this.f_178103_ = stringbuilder.toString();
   }

   public void m_142447_(ListTag p_178132_) {
      if (p_178132_.isEmpty()) {
         this.f_178103_ = "[]";
      } else {
         StringBuilder stringbuilder = new StringBuilder("[");
         this.m_178144_("[]");
         String s = f_178089_.contains(this.m_178110_()) ? "" : this.f_178100_;
         if (!s.isEmpty()) {
            stringbuilder.append("\n");
         }

         for(int i = 0; i < p_178132_.size(); ++i) {
            stringbuilder.append(Strings.repeat(s, this.f_178101_ + 1));
            stringbuilder.append((new SnbtPrinterTagVisitor(s, this.f_178101_ + 1, this.f_178102_)).m_178141_(p_178132_.get(i)));
            if (i != p_178132_.size() - 1) {
               stringbuilder.append(f_178092_).append(s.isEmpty() ? " " : "\n");
            }
         }

         if (!s.isEmpty()) {
            stringbuilder.append("\n").append(Strings.repeat(s, this.f_178101_));
         }

         stringbuilder.append("]");
         this.f_178103_ = stringbuilder.toString();
         this.m_178143_();
      }
   }

   public void m_142303_(CompoundTag p_178120_) {
      if (p_178120_.m_128456_()) {
         this.f_178103_ = "{}";
      } else {
         StringBuilder stringbuilder = new StringBuilder("{");
         this.m_178144_("{}");
         String s = f_178089_.contains(this.m_178110_()) ? "" : this.f_178100_;
         if (!s.isEmpty()) {
            stringbuilder.append("\n");
         }

         Collection<String> collection = this.m_178146_(p_178120_);
         Iterator<String> iterator = collection.iterator();

         while(iterator.hasNext()) {
            String s1 = iterator.next();
            Tag tag = p_178120_.m_128423_(s1);
            this.m_178144_(s1);
            stringbuilder.append(Strings.repeat(s, this.f_178101_ + 1)).append(m_178111_(s1)).append(f_178091_).append(" ").append((new SnbtPrinterTagVisitor(s, this.f_178101_ + 1, this.f_178102_)).m_178141_(tag));
            this.m_178143_();
            if (iterator.hasNext()) {
               stringbuilder.append(f_178092_).append(s.isEmpty() ? " " : "\n");
            }
         }

         if (!s.isEmpty()) {
            stringbuilder.append("\n").append(Strings.repeat(s, this.f_178101_));
         }

         stringbuilder.append("}");
         this.f_178103_ = stringbuilder.toString();
         this.m_178143_();
      }
   }

   private void m_178143_() {
      this.f_178102_.remove(this.f_178102_.size() - 1);
   }

   private void m_178144_(String p_178145_) {
      this.f_178102_.add(p_178145_);
   }

   protected List<String> m_178146_(CompoundTag p_178147_) {
      Set<String> set = Sets.newHashSet(p_178147_.m_128431_());
      List<String> list = Lists.newArrayList();
      List<String> list1 = f_178088_.get(this.m_178110_());
      if (list1 != null) {
         for(String s : list1) {
            if (set.remove(s)) {
               list.add(s);
            }
         }

         if (!set.isEmpty()) {
            set.stream().sorted().forEach(list::add);
         }
      } else {
         list.addAll(set);
         Collections.sort(list);
      }

      return list;
   }

   public String m_178110_() {
      return String.join(".", this.f_178102_);
   }

   protected static String m_178111_(String p_178112_) {
      return f_178090_.matcher(p_178112_).matches() ? p_178112_ : StringTag.m_129303_(p_178112_);
   }

   public void m_142384_(EndTag p_178124_) {
   }
}