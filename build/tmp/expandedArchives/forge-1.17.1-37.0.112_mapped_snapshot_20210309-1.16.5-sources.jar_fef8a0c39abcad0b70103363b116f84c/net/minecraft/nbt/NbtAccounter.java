package net.minecraft.nbt;

public class NbtAccounter {
   public static final NbtAccounter f_128917_ = new NbtAccounter(0L) {
      public void m_6800_(long p_128927_) {
      }
   };
   private final long f_128918_;
   private long f_128919_;

   public NbtAccounter(long p_128922_) {
      this.f_128918_ = p_128922_;
   }

   public void m_6800_(long p_128923_) {
      this.f_128919_ += p_128923_ / 8L;
      if (this.f_128919_ > this.f_128918_) {
         throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.f_128919_ + "bytes where max allowed: " + this.f_128918_);
      }
   }

   /*
    * UTF8 is not a simple encoding system, each character can be either
    * 1, 2, or 3 bytes. Depending on where it's numerical value falls.
    * We have to count up each character individually to see the true
    * length of the data.
    *
    * Basic concept is that it uses the MSB of each byte as a 'read more' signal.
    * So it has to shift each 7-bit segment.
    *
    * This will accurately count the correct byte length to encode this string, plus the 2 bytes for it's length prefix.
    */
   public String readUTF(String data) {
      m_6800_(16); //Header length
      if (data == null)
         return data;

      int len = data.length();
      int utflen = 0;

      for (int i = 0; i < len; i++) {
          int c = data.charAt(i);
          if ((c >= 0x0001) && (c <= 0x007F)) utflen += 1;
          else if (c > 0x07FF)                utflen += 3;
          else                                utflen += 2;
      }
      m_6800_(8 * utflen);

      return data;
   }
}
