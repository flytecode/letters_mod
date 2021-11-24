package net.minecraft.nbt;

import java.io.DataOutput;
import java.io.IOException;

public interface Tag {
   int f_178189_ = 64;
   int f_178190_ = 96;
   int f_178191_ = 32;
   int f_178192_ = 224;
   byte f_178193_ = 0;
   byte f_178194_ = 1;
   byte f_178195_ = 2;
   byte f_178196_ = 3;
   byte f_178197_ = 4;
   byte f_178198_ = 5;
   byte f_178199_ = 6;
   byte f_178200_ = 7;
   byte f_178201_ = 8;
   byte f_178202_ = 9;
   byte f_178203_ = 10;
   byte f_178204_ = 11;
   byte f_178205_ = 12;
   byte f_178206_ = 99;
   int f_178207_ = 512;

   void m_6434_(DataOutput p_129329_) throws IOException;

   String toString();

   byte m_7060_();

   TagType<?> m_6458_();

   Tag m_6426_();

   default String m_7916_() {
      return (new StringTagVisitor()).m_178187_(this);
   }

   void m_142327_(TagVisitor p_178208_);
}