package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextComponentTagVisitor implements TagVisitor {
   private static final Logger f_178229_ = LogManager.getLogger();
   private static final int f_178230_ = 8;
   private static final ByteCollection f_178231_ = new ByteOpenHashSet(Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6));
   private static final ChatFormatting f_178232_ = ChatFormatting.AQUA;
   private static final ChatFormatting f_178233_ = ChatFormatting.GREEN;
   private static final ChatFormatting f_178234_ = ChatFormatting.GOLD;
   private static final ChatFormatting f_178235_ = ChatFormatting.RED;
   private static final Pattern f_178236_ = Pattern.compile("[A-Za-z0-9._+-]+");
   private static final String f_178237_ = String.valueOf(':');
   private static final String f_178238_ = String.valueOf(',');
   private static final String f_178239_ = "[";
   private static final String f_178240_ = "]";
   private static final String f_178241_ = ";";
   private static final String f_178242_ = " ";
   private static final String f_178243_ = "{";
   private static final String f_178244_ = "}";
   private static final String f_178245_ = "\n";
   private final String f_178246_;
   private final int f_178247_;
   private Component f_178248_;

   public TextComponentTagVisitor(String p_178251_, int p_178252_) {
      this.f_178246_ = p_178251_;
      this.f_178247_ = p_178252_;
   }

   public Component m_178281_(Tag p_178282_) {
      p_178282_.m_142327_(this);
      return this.f_178248_;
   }

   public void m_142614_(StringTag p_178280_) {
      String s = StringTag.m_129303_(p_178280_.m_7916_());
      String s1 = s.substring(0, 1);
      Component component = (new TextComponent(s.substring(1, s.length() - 1))).m_130940_(f_178233_);
      this.f_178248_ = (new TextComponent(s1)).m_7220_(component).m_130946_(s1);
   }

   public void m_141946_(ByteTag p_178258_) {
      Component component = (new TextComponent("b")).m_130940_(f_178235_);
      this.f_178248_ = (new TextComponent(String.valueOf((Object)p_178258_.m_8103_()))).m_7220_(component).m_130940_(f_178234_);
   }

   public void m_142183_(ShortTag p_178278_) {
      Component component = (new TextComponent("s")).m_130940_(f_178235_);
      this.f_178248_ = (new TextComponent(String.valueOf((Object)p_178278_.m_8103_()))).m_7220_(component).m_130940_(f_178234_);
   }

   public void m_142045_(IntTag p_178270_) {
      this.f_178248_ = (new TextComponent(String.valueOf((Object)p_178270_.m_8103_()))).m_130940_(f_178234_);
   }

   public void m_142046_(LongTag p_178276_) {
      Component component = (new TextComponent("L")).m_130940_(f_178235_);
      this.f_178248_ = (new TextComponent(String.valueOf((Object)p_178276_.m_8103_()))).m_7220_(component).m_130940_(f_178234_);
   }

   public void m_142181_(FloatTag p_178266_) {
      Component component = (new TextComponent("f")).m_130940_(f_178235_);
      this.f_178248_ = (new TextComponent(String.valueOf(p_178266_.m_7057_()))).m_7220_(component).m_130940_(f_178234_);
   }

   public void m_142121_(DoubleTag p_178262_) {
      Component component = (new TextComponent("d")).m_130940_(f_178235_);
      this.f_178248_ = (new TextComponent(String.valueOf(p_178262_.m_7061_()))).m_7220_(component).m_130940_(f_178234_);
   }

   public void m_142154_(ByteArrayTag p_178256_) {
      Component component = (new TextComponent("B")).m_130940_(f_178235_);
      MutableComponent mutablecomponent = (new TextComponent("[")).m_7220_(component).m_130946_(";");
      byte[] abyte = p_178256_.m_128227_();

      for(int i = 0; i < abyte.length; ++i) {
         MutableComponent mutablecomponent1 = (new TextComponent(String.valueOf((int)abyte[i]))).m_130940_(f_178234_);
         mutablecomponent.m_130946_(" ").m_7220_(mutablecomponent1).m_7220_(component);
         if (i != abyte.length - 1) {
            mutablecomponent.m_130946_(f_178238_);
         }
      }

      mutablecomponent.m_130946_("]");
      this.f_178248_ = mutablecomponent;
   }

   public void m_142251_(IntArrayTag p_178268_) {
      Component component = (new TextComponent("I")).m_130940_(f_178235_);
      MutableComponent mutablecomponent = (new TextComponent("[")).m_7220_(component).m_130946_(";");
      int[] aint = p_178268_.m_128648_();

      for(int i = 0; i < aint.length; ++i) {
         mutablecomponent.m_130946_(" ").m_7220_((new TextComponent(String.valueOf(aint[i]))).m_130940_(f_178234_));
         if (i != aint.length - 1) {
            mutablecomponent.m_130946_(f_178238_);
         }
      }

      mutablecomponent.m_130946_("]");
      this.f_178248_ = mutablecomponent;
   }

   public void m_142309_(LongArrayTag p_178274_) {
      Component component = (new TextComponent("L")).m_130940_(f_178235_);
      MutableComponent mutablecomponent = (new TextComponent("[")).m_7220_(component).m_130946_(";");
      long[] along = p_178274_.m_128851_();

      for(int i = 0; i < along.length; ++i) {
         Component component1 = (new TextComponent(String.valueOf(along[i]))).m_130940_(f_178234_);
         mutablecomponent.m_130946_(" ").m_7220_(component1).m_7220_(component);
         if (i != along.length - 1) {
            mutablecomponent.m_130946_(f_178238_);
         }
      }

      mutablecomponent.m_130946_("]");
      this.f_178248_ = mutablecomponent;
   }

   public void m_142447_(ListTag p_178272_) {
      if (p_178272_.isEmpty()) {
         this.f_178248_ = new TextComponent("[]");
      } else if (f_178231_.contains(p_178272_.m_7264_()) && p_178272_.size() <= 8) {
         String s = f_178238_ + " ";
         MutableComponent mutablecomponent2 = new TextComponent("[");

         for(int j = 0; j < p_178272_.size(); ++j) {
            if (j != 0) {
               mutablecomponent2.m_130946_(s);
            }

            mutablecomponent2.m_7220_((new TextComponentTagVisitor(this.f_178246_, this.f_178247_)).m_178281_(p_178272_.get(j)));
         }

         mutablecomponent2.m_130946_("]");
         this.f_178248_ = mutablecomponent2;
      } else {
         MutableComponent mutablecomponent = new TextComponent("[");
         if (!this.f_178246_.isEmpty()) {
            mutablecomponent.m_130946_("\n");
         }

         for(int i = 0; i < p_178272_.size(); ++i) {
            MutableComponent mutablecomponent1 = new TextComponent(Strings.repeat(this.f_178246_, this.f_178247_ + 1));
            mutablecomponent1.m_7220_((new TextComponentTagVisitor(this.f_178246_, this.f_178247_ + 1)).m_178281_(p_178272_.get(i)));
            if (i != p_178272_.size() - 1) {
               mutablecomponent1.m_130946_(f_178238_).m_130946_(this.f_178246_.isEmpty() ? " " : "\n");
            }

            mutablecomponent.m_7220_(mutablecomponent1);
         }

         if (!this.f_178246_.isEmpty()) {
            mutablecomponent.m_130946_("\n").m_130946_(Strings.repeat(this.f_178246_, this.f_178247_));
         }

         mutablecomponent.m_130946_("]");
         this.f_178248_ = mutablecomponent;
      }
   }

   public void m_142303_(CompoundTag p_178260_) {
      if (p_178260_.m_128456_()) {
         this.f_178248_ = new TextComponent("{}");
      } else {
         MutableComponent mutablecomponent = new TextComponent("{");
         Collection<String> collection = p_178260_.m_128431_();
         if (f_178229_.isDebugEnabled()) {
            List<String> list = Lists.newArrayList(p_178260_.m_128431_());
            Collections.sort(list);
            collection = list;
         }

         if (!this.f_178246_.isEmpty()) {
            mutablecomponent.m_130946_("\n");
         }

         MutableComponent mutablecomponent1;
         for(Iterator<String> iterator = collection.iterator(); iterator.hasNext(); mutablecomponent.m_7220_(mutablecomponent1)) {
            String s = iterator.next();
            mutablecomponent1 = (new TextComponent(Strings.repeat(this.f_178246_, this.f_178247_ + 1))).m_7220_(m_178253_(s)).m_130946_(f_178237_).m_130946_(" ").m_7220_((new TextComponentTagVisitor(this.f_178246_, this.f_178247_ + 1)).m_178281_(p_178260_.m_128423_(s)));
            if (iterator.hasNext()) {
               mutablecomponent1.m_130946_(f_178238_).m_130946_(this.f_178246_.isEmpty() ? " " : "\n");
            }
         }

         if (!this.f_178246_.isEmpty()) {
            mutablecomponent.m_130946_("\n").m_130946_(Strings.repeat(this.f_178246_, this.f_178247_));
         }

         mutablecomponent.m_130946_("}");
         this.f_178248_ = mutablecomponent;
      }
   }

   protected static Component m_178253_(String p_178254_) {
      if (f_178236_.matcher(p_178254_).matches()) {
         return (new TextComponent(p_178254_)).m_130940_(f_178232_);
      } else {
         String s = StringTag.m_129303_(p_178254_);
         String s1 = s.substring(0, 1);
         Component component = (new TextComponent(s.substring(1, s.length() - 1))).m_130940_(f_178232_);
         return (new TextComponent(s1)).m_7220_(component).m_130946_(s1);
      }
   }

   public void m_142384_(EndTag p_178264_) {
      this.f_178248_ = TextComponent.f_131282_;
   }
}