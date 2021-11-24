package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.network.chat.TranslatableComponent;

public class TagParser {
   public static final SimpleCommandExceptionType f_129334_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.nbt.trailing"));
   public static final SimpleCommandExceptionType f_129335_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.nbt.expected.key"));
   public static final SimpleCommandExceptionType f_129336_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.nbt.expected.value"));
   public static final Dynamic2CommandExceptionType f_129337_ = new Dynamic2CommandExceptionType((p_129366_, p_129367_) -> {
      return new TranslatableComponent("argument.nbt.list.mixed", p_129366_, p_129367_);
   });
   public static final Dynamic2CommandExceptionType f_129338_ = new Dynamic2CommandExceptionType((p_129357_, p_129358_) -> {
      return new TranslatableComponent("argument.nbt.array.mixed", p_129357_, p_129358_);
   });
   public static final DynamicCommandExceptionType f_129339_ = new DynamicCommandExceptionType((p_129355_) -> {
      return new TranslatableComponent("argument.nbt.array.invalid", p_129355_);
   });
   public static final char f_178209_ = ',';
   public static final char f_178210_ = ':';
   private static final char f_178211_ = '[';
   private static final char f_178212_ = ']';
   private static final char f_178213_ = '}';
   private static final char f_178214_ = '{';
   private static final Pattern f_129340_ = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
   private static final Pattern f_129341_ = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
   private static final Pattern f_129342_ = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
   private static final Pattern f_129343_ = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
   private static final Pattern f_129344_ = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
   private static final Pattern f_129345_ = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
   private static final Pattern f_129346_ = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
   private final StringReader f_129347_;

   public static CompoundTag m_129359_(String p_129360_) throws CommandSyntaxException {
      return (new TagParser(new StringReader(p_129360_))).m_129351_();
   }

   @VisibleForTesting
   CompoundTag m_129351_() throws CommandSyntaxException {
      CompoundTag compoundtag = this.m_129373_();
      this.f_129347_.skipWhitespace();
      if (this.f_129347_.canRead()) {
         throw f_129334_.createWithContext(this.f_129347_);
      } else {
         return compoundtag;
      }
   }

   public TagParser(StringReader p_129350_) {
      this.f_129347_ = p_129350_;
   }

   protected String m_129364_() throws CommandSyntaxException {
      this.f_129347_.skipWhitespace();
      if (!this.f_129347_.canRead()) {
         throw f_129335_.createWithContext(this.f_129347_);
      } else {
         return this.f_129347_.readString();
      }
   }

   protected Tag m_129370_() throws CommandSyntaxException {
      this.f_129347_.skipWhitespace();
      int i = this.f_129347_.getCursor();
      if (StringReader.isQuotedStringStart(this.f_129347_.peek())) {
         return StringTag.m_129297_(this.f_129347_.readQuotedString());
      } else {
         String s = this.f_129347_.readUnquotedString();
         if (s.isEmpty()) {
            this.f_129347_.setCursor(i);
            throw f_129336_.createWithContext(this.f_129347_);
         } else {
            return this.m_129368_(s);
         }
      }
   }

   private Tag m_129368_(String p_129369_) {
      try {
         if (f_129342_.matcher(p_129369_).matches()) {
            return FloatTag.m_128566_(Float.parseFloat(p_129369_.substring(0, p_129369_.length() - 1)));
         }

         if (f_129343_.matcher(p_129369_).matches()) {
            return ByteTag.m_128266_(Byte.parseByte(p_129369_.substring(0, p_129369_.length() - 1)));
         }

         if (f_129344_.matcher(p_129369_).matches()) {
            return LongTag.m_128882_(Long.parseLong(p_129369_.substring(0, p_129369_.length() - 1)));
         }

         if (f_129345_.matcher(p_129369_).matches()) {
            return ShortTag.m_129258_(Short.parseShort(p_129369_.substring(0, p_129369_.length() - 1)));
         }

         if (f_129346_.matcher(p_129369_).matches()) {
            return IntTag.m_128679_(Integer.parseInt(p_129369_));
         }

         if (f_129341_.matcher(p_129369_).matches()) {
            return DoubleTag.m_128500_(Double.parseDouble(p_129369_.substring(0, p_129369_.length() - 1)));
         }

         if (f_129340_.matcher(p_129369_).matches()) {
            return DoubleTag.m_128500_(Double.parseDouble(p_129369_));
         }

         if ("true".equalsIgnoreCase(p_129369_)) {
            return ByteTag.f_128257_;
         }

         if ("false".equalsIgnoreCase(p_129369_)) {
            return ByteTag.f_128256_;
         }
      } catch (NumberFormatException numberformatexception) {
      }

      return StringTag.m_129297_(p_129369_);
   }

   public Tag m_129371_() throws CommandSyntaxException {
      this.f_129347_.skipWhitespace();
      if (!this.f_129347_.canRead()) {
         throw f_129336_.createWithContext(this.f_129347_);
      } else {
         char c0 = this.f_129347_.peek();
         if (c0 == '{') {
            return this.m_129373_();
         } else {
            return c0 == '[' ? this.m_129372_() : this.m_129370_();
         }
      }
   }

   protected Tag m_129372_() throws CommandSyntaxException {
      return this.f_129347_.canRead(3) && !StringReader.isQuotedStringStart(this.f_129347_.peek(1)) && this.f_129347_.peek(2) == ';' ? this.m_129375_() : this.m_129374_();
   }

   public CompoundTag m_129373_() throws CommandSyntaxException {
      this.m_129352_('{');
      CompoundTag compoundtag = new CompoundTag();
      this.f_129347_.skipWhitespace();

      while(this.f_129347_.canRead() && this.f_129347_.peek() != '}') {
         int i = this.f_129347_.getCursor();
         String s = this.m_129364_();
         if (s.isEmpty()) {
            this.f_129347_.setCursor(i);
            throw f_129335_.createWithContext(this.f_129347_);
         }

         this.m_129352_(':');
         compoundtag.m_128365_(s, this.m_129371_());
         if (!this.m_129376_()) {
            break;
         }

         if (!this.f_129347_.canRead()) {
            throw f_129335_.createWithContext(this.f_129347_);
         }
      }

      this.m_129352_('}');
      return compoundtag;
   }

   private Tag m_129374_() throws CommandSyntaxException {
      this.m_129352_('[');
      this.f_129347_.skipWhitespace();
      if (!this.f_129347_.canRead()) {
         throw f_129336_.createWithContext(this.f_129347_);
      } else {
         ListTag listtag = new ListTag();
         TagType<?> tagtype = null;

         while(this.f_129347_.peek() != ']') {
            int i = this.f_129347_.getCursor();
            Tag tag = this.m_129371_();
            TagType<?> tagtype1 = tag.m_6458_();
            if (tagtype == null) {
               tagtype = tagtype1;
            } else if (tagtype1 != tagtype) {
               this.f_129347_.setCursor(i);
               throw f_129337_.createWithContext(this.f_129347_, tagtype1.m_5986_(), tagtype.m_5986_());
            }

            listtag.add(tag);
            if (!this.m_129376_()) {
               break;
            }

            if (!this.f_129347_.canRead()) {
               throw f_129336_.createWithContext(this.f_129347_);
            }
         }

         this.m_129352_(']');
         return listtag;
      }
   }

   private Tag m_129375_() throws CommandSyntaxException {
      this.m_129352_('[');
      int i = this.f_129347_.getCursor();
      char c0 = this.f_129347_.read();
      this.f_129347_.read();
      this.f_129347_.skipWhitespace();
      if (!this.f_129347_.canRead()) {
         throw f_129336_.createWithContext(this.f_129347_);
      } else if (c0 == 'B') {
         return new ByteArrayTag(this.m_129361_(ByteArrayTag.f_128185_, ByteTag.f_128255_));
      } else if (c0 == 'L') {
         return new LongArrayTag(this.m_129361_(LongArrayTag.f_128800_, LongTag.f_128873_));
      } else if (c0 == 'I') {
         return new IntArrayTag(this.m_129361_(IntArrayTag.f_128599_, IntTag.f_128670_));
      } else {
         this.f_129347_.setCursor(i);
         throw f_129339_.createWithContext(this.f_129347_, String.valueOf(c0));
      }
   }

   private <T extends Number> List<T> m_129361_(TagType<?> p_129362_, TagType<?> p_129363_) throws CommandSyntaxException {
      List<T> list = Lists.newArrayList();

      while(true) {
         if (this.f_129347_.peek() != ']') {
            int i = this.f_129347_.getCursor();
            Tag tag = this.m_129371_();
            TagType<?> tagtype = tag.m_6458_();
            if (tagtype != p_129363_) {
               this.f_129347_.setCursor(i);
               throw f_129338_.createWithContext(this.f_129347_, tagtype.m_5986_(), p_129362_.m_5986_());
            }

            if (p_129363_ == ByteTag.f_128255_) {
               list.add((T)(Byte)((NumericTag)tag).m_7063_());
            } else if (p_129363_ == LongTag.f_128873_) {
               list.add((T)(Long)((NumericTag)tag).m_7046_());
            } else {
               list.add((T)(Integer)((NumericTag)tag).m_7047_());
            }

            if (this.m_129376_()) {
               if (!this.f_129347_.canRead()) {
                  throw f_129336_.createWithContext(this.f_129347_);
               }
               continue;
            }
         }

         this.m_129352_(']');
         return list;
      }
   }

   private boolean m_129376_() {
      this.f_129347_.skipWhitespace();
      if (this.f_129347_.canRead() && this.f_129347_.peek() == ',') {
         this.f_129347_.skip();
         this.f_129347_.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   private void m_129352_(char p_129353_) throws CommandSyntaxException {
      this.f_129347_.skipWhitespace();
      this.f_129347_.expect(p_129353_);
   }
}