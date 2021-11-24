package net.minecraft.client;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.FormattedText;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentCollector {
   private final List<FormattedText> f_90672_ = Lists.newArrayList();

   public void m_90675_(FormattedText p_90676_) {
      this.f_90672_.add(p_90676_);
   }

   @Nullable
   public FormattedText m_90674_() {
      if (this.f_90672_.isEmpty()) {
         return null;
      } else {
         return this.f_90672_.size() == 1 ? this.f_90672_.get(0) : FormattedText.m_130768_(this.f_90672_);
      }
   }

   public FormattedText m_90677_() {
      FormattedText formattedtext = this.m_90674_();
      return formattedtext != null ? formattedtext : FormattedText.f_130760_;
   }

   public void m_167712_() {
      this.f_90672_.clear();
   }
}