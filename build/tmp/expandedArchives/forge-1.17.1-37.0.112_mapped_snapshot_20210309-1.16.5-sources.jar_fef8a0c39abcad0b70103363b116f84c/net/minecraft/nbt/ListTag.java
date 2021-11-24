package net.minecraft.nbt;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ListTag extends CollectionTag<Tag> {
   private static final int f_177988_ = 296;
   public static final TagType<ListTag> f_128714_ = new TagType<ListTag>() {
      public ListTag m_7300_(DataInput p_128792_, int p_128793_, NbtAccounter p_128794_) throws IOException {
         p_128794_.m_6800_(296L);
         if (p_128793_ > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            byte b0 = p_128792_.readByte();
            int i = p_128792_.readInt();
            if (b0 == 0 && i > 0) {
               throw new RuntimeException("Missing type on ListTag");
            } else {
               p_128794_.m_6800_(32L * (long)i);
               TagType<?> tagtype = TagTypes.m_129397_(b0);
               List<Tag> list = Lists.newArrayListWithCapacity(i);

               for(int j = 0; j < i; ++j) {
                  list.add(tagtype.m_7300_(p_128792_, p_128793_ + 1, p_128794_));
               }

               return new ListTag(list, b0);
            }
         }
      }

      public String m_5987_() {
         return "LIST";
      }

      public String m_5986_() {
         return "TAG_List";
      }
   };
   private final List<Tag> f_128716_;
   private byte f_128717_;

   ListTag(List<Tag> p_128721_, byte p_128722_) {
      this.f_128716_ = p_128721_;
      this.f_128717_ = p_128722_;
   }

   public ListTag() {
      this(Lists.newArrayList(), (byte)0);
   }

   public void m_6434_(DataOutput p_128734_) throws IOException {
      if (this.f_128716_.isEmpty()) {
         this.f_128717_ = 0;
      } else {
         this.f_128717_ = this.f_128716_.get(0).m_7060_();
      }

      p_128734_.writeByte(this.f_128717_);
      p_128734_.writeInt(this.f_128716_.size());

      for(Tag tag : this.f_128716_) {
         tag.m_6434_(p_128734_);
      }

   }

   public byte m_7060_() {
      return 9;
   }

   public TagType<ListTag> m_6458_() {
      return f_128714_;
   }

   public String toString() {
      return this.m_7916_();
   }

   private void m_128769_() {
      if (this.f_128716_.isEmpty()) {
         this.f_128717_ = 0;
      }

   }

   public Tag remove(int p_128751_) {
      Tag tag = this.f_128716_.remove(p_128751_);
      this.m_128769_();
      return tag;
   }

   public boolean isEmpty() {
      return this.f_128716_.isEmpty();
   }

   public CompoundTag m_128728_(int p_128729_) {
      if (p_128729_ >= 0 && p_128729_ < this.f_128716_.size()) {
         Tag tag = this.f_128716_.get(p_128729_);
         if (tag.m_7060_() == 10) {
            return (CompoundTag)tag;
         }
      }

      return new CompoundTag();
   }

   public ListTag m_128744_(int p_128745_) {
      if (p_128745_ >= 0 && p_128745_ < this.f_128716_.size()) {
         Tag tag = this.f_128716_.get(p_128745_);
         if (tag.m_7060_() == 9) {
            return (ListTag)tag;
         }
      }

      return new ListTag();
   }

   public short m_128757_(int p_128758_) {
      if (p_128758_ >= 0 && p_128758_ < this.f_128716_.size()) {
         Tag tag = this.f_128716_.get(p_128758_);
         if (tag.m_7060_() == 2) {
            return ((ShortTag)tag).m_7053_();
         }
      }

      return 0;
   }

   public int m_128763_(int p_128764_) {
      if (p_128764_ >= 0 && p_128764_ < this.f_128716_.size()) {
         Tag tag = this.f_128716_.get(p_128764_);
         if (tag.m_7060_() == 3) {
            return ((IntTag)tag).m_7047_();
         }
      }

      return 0;
   }

   public int[] m_128767_(int p_128768_) {
      if (p_128768_ >= 0 && p_128768_ < this.f_128716_.size()) {
         Tag tag = this.f_128716_.get(p_128768_);
         if (tag.m_7060_() == 11) {
            return ((IntArrayTag)tag).m_128648_();
         }
      }

      return new int[0];
   }

   public long[] m_177991_(int p_177992_) {
      if (p_177992_ >= 0 && p_177992_ < this.f_128716_.size()) {
         Tag tag = this.f_128716_.get(p_177992_);
         if (tag.m_7060_() == 11) {
            return ((LongArrayTag)tag).m_128851_();
         }
      }

      return new long[0];
   }

   public double m_128772_(int p_128773_) {
      if (p_128773_ >= 0 && p_128773_ < this.f_128716_.size()) {
         Tag tag = this.f_128716_.get(p_128773_);
         if (tag.m_7060_() == 6) {
            return ((DoubleTag)tag).m_7061_();
         }
      }

      return 0.0D;
   }

   public float m_128775_(int p_128776_) {
      if (p_128776_ >= 0 && p_128776_ < this.f_128716_.size()) {
         Tag tag = this.f_128716_.get(p_128776_);
         if (tag.m_7060_() == 5) {
            return ((FloatTag)tag).m_7057_();
         }
      }

      return 0.0F;
   }

   public String m_128778_(int p_128779_) {
      if (p_128779_ >= 0 && p_128779_ < this.f_128716_.size()) {
         Tag tag = this.f_128716_.get(p_128779_);
         return tag.m_7060_() == 8 ? tag.m_7916_() : tag.toString();
      } else {
         return "";
      }
   }

   public int size() {
      return this.f_128716_.size();
   }

   public Tag get(int p_128781_) {
      return this.f_128716_.get(p_128781_);
   }

   public Tag set(int p_128760_, Tag p_128761_) {
      Tag tag = this.get(p_128760_);
      if (!this.m_7615_(p_128760_, p_128761_)) {
         throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", p_128761_.m_7060_(), this.f_128717_));
      } else {
         return tag;
      }
   }

   public void add(int p_128753_, Tag p_128754_) {
      if (!this.m_7614_(p_128753_, p_128754_)) {
         throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", p_128754_.m_7060_(), this.f_128717_));
      }
   }

   public boolean m_7615_(int p_128731_, Tag p_128732_) {
      if (this.m_128738_(p_128732_)) {
         this.f_128716_.set(p_128731_, p_128732_);
         return true;
      } else {
         return false;
      }
   }

   public boolean m_7614_(int p_128747_, Tag p_128748_) {
      if (this.m_128738_(p_128748_)) {
         this.f_128716_.add(p_128747_, p_128748_);
         return true;
      } else {
         return false;
      }
   }

   private boolean m_128738_(Tag p_128739_) {
      if (p_128739_.m_7060_() == 0) {
         return false;
      } else if (this.f_128717_ == 0) {
         this.f_128717_ = p_128739_.m_7060_();
         return true;
      } else {
         return this.f_128717_ == p_128739_.m_7060_();
      }
   }

   public ListTag m_6426_() {
      Iterable<Tag> iterable = (Iterable<Tag>)(TagTypes.m_129397_(this.f_128717_).m_7064_() ? this.f_128716_ : Iterables.transform(this.f_128716_, Tag::m_6426_));
      List<Tag> list = Lists.newArrayList(iterable);
      return new ListTag(list, this.f_128717_);
   }

   public boolean equals(Object p_128766_) {
      if (this == p_128766_) {
         return true;
      } else {
         return p_128766_ instanceof ListTag && Objects.equals(this.f_128716_, ((ListTag)p_128766_).f_128716_);
      }
   }

   public int hashCode() {
      return this.f_128716_.hashCode();
   }

   public void m_142327_(TagVisitor p_177990_) {
      p_177990_.m_142447_(this);
   }

   public byte m_7264_() {
      return this.f_128717_;
   }

   public void clear() {
      this.f_128716_.clear();
      this.f_128717_ = 0;
   }
}