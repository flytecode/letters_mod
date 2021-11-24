package net.minecraft.sounds;

public enum SoundSource {
   MASTER("master"),
   MUSIC("music"),
   RECORDS("record"),
   WEATHER("weather"),
   BLOCKS("block"),
   HOSTILE("hostile"),
   NEUTRAL("neutral"),
   PLAYERS("player"),
   AMBIENT("ambient"),
   VOICE("voice");

   private final String f_12669_;

   private SoundSource(String p_12675_) {
      this.f_12669_ = p_12675_;
   }

   public String m_12676_() {
      return this.f_12669_;
   }
}