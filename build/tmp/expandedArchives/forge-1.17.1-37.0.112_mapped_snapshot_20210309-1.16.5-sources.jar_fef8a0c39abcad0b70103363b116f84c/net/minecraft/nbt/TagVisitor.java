package net.minecraft.nbt;

public interface TagVisitor {
   void m_142614_(StringTag p_178228_);

   void m_141946_(ByteTag p_178217_);

   void m_142183_(ShortTag p_178227_);

   void m_142045_(IntTag p_178223_);

   void m_142046_(LongTag p_178226_);

   void m_142181_(FloatTag p_178221_);

   void m_142121_(DoubleTag p_178219_);

   void m_142154_(ByteArrayTag p_178216_);

   void m_142251_(IntArrayTag p_178222_);

   void m_142309_(LongArrayTag p_178225_);

   void m_142447_(ListTag p_178224_);

   void m_142303_(CompoundTag p_178218_);

   void m_142384_(EndTag p_178220_);
}