package net.minecraft.nbt;

public class TagTypes {
   private static final TagType<?>[] f_129395_ = new TagType[]{EndTag.f_128533_, ByteTag.f_128255_, ShortTag.f_129244_, IntTag.f_128670_, LongTag.f_128873_, FloatTag.f_128560_, DoubleTag.f_128494_, ByteArrayTag.f_128185_, StringTag.f_129288_, ListTag.f_128714_, CompoundTag.f_128326_, IntArrayTag.f_128599_, LongArrayTag.f_128800_};

   public static TagType<?> m_129397_(int p_129398_) {
      return p_129398_ >= 0 && p_129398_ < f_129395_.length ? f_129395_[p_129398_] : TagType.m_129377_(p_129398_);
   }
}