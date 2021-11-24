package net.minecraft.network.chat;

import java.util.function.UnaryOperator;
import net.minecraft.ChatFormatting;

public interface MutableComponent extends Component {
   MutableComponent m_6270_(Style p_130943_);

   default MutableComponent m_130946_(String p_130947_) {
      return this.m_7220_(new TextComponent(p_130947_));
   }

   MutableComponent m_7220_(Component p_130942_);

   default MutableComponent m_130938_(UnaryOperator<Style> p_130939_) {
      this.m_6270_(p_130939_.apply(this.m_7383_()));
      return this;
   }

   default MutableComponent m_130948_(Style p_130949_) {
      this.m_6270_(p_130949_.m_131146_(this.m_7383_()));
      return this;
   }

   default MutableComponent m_130944_(ChatFormatting... p_130945_) {
      this.m_6270_(this.m_7383_().m_131152_(p_130945_));
      return this;
   }

   default MutableComponent m_130940_(ChatFormatting p_130941_) {
      this.m_6270_(this.m_7383_().m_131157_(p_130941_));
      return this;
   }
}