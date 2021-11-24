package net.minecraft.world.entity.player;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;

public enum ChatVisiblity {
   FULL(0, "options.chat.visibility.full"),
   SYSTEM(1, "options.chat.visibility.system"),
   HIDDEN(2, "options.chat.visibility.hidden");

   private static final ChatVisiblity[] f_35955_ = Arrays.stream(values()).sorted(Comparator.comparingInt(ChatVisiblity::m_35965_)).toArray((p_35970_) -> {
      return new ChatVisiblity[p_35970_];
   });
   private final int f_35956_;
   private final String f_35957_;

   private ChatVisiblity(int p_35963_, String p_35964_) {
      this.f_35956_ = p_35963_;
      this.f_35957_ = p_35964_;
   }

   public int m_35965_() {
      return this.f_35956_;
   }

   public String m_35968_() {
      return this.f_35957_;
   }

   public static ChatVisiblity m_35966_(int p_35967_) {
      return f_35955_[Mth.m_14100_(p_35967_, f_35955_.length)];
   }
}