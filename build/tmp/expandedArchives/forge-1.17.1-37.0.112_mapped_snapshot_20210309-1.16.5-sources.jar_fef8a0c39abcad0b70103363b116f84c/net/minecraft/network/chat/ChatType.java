package net.minecraft.network.chat;

public enum ChatType {
   CHAT((byte)0, false),
   SYSTEM((byte)1, true),
   GAME_INFO((byte)2, true);

   private final byte f_130601_;
   private final boolean f_130602_;

   private ChatType(byte p_130608_, boolean p_130609_) {
      this.f_130601_ = p_130608_;
      this.f_130602_ = p_130609_;
   }

   public byte m_130610_() {
      return this.f_130601_;
   }

   public static ChatType m_130611_(byte p_130612_) {
      for(ChatType chattype : values()) {
         if (p_130612_ == chattype.f_130601_) {
            return chattype;
         }
      }

      return CHAT;
   }

   public boolean m_130613_() {
      return this.f_130602_;
   }
}