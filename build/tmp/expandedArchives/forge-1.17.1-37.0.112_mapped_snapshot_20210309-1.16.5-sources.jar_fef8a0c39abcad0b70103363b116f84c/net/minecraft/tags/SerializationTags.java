package net.minecraft.tags;

public class SerializationTags {
   private static volatile TagContainer f_13197_ = StaticTags.m_144354_();

   public static TagContainer m_13199_() {
      return f_13197_;
   }

   public static void m_13202_(TagContainer p_13203_) {
      f_13197_ = p_13203_;
   }
}