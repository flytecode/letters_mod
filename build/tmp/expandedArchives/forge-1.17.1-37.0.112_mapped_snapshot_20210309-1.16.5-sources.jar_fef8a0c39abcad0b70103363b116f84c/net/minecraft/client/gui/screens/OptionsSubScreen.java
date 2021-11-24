package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionsSubScreen extends Screen {
   protected final Screen f_96281_;
   protected final Options f_96282_;

   public OptionsSubScreen(Screen p_96284_, Options p_96285_, Component p_96286_) {
      super(p_96286_);
      this.f_96281_ = p_96284_;
      this.f_96282_ = p_96285_;
   }

   public void m_7861_() {
      this.f_96541_.f_91066_.m_92169_();
   }

   public void m_7379_() {
      this.f_96541_.m_91152_(this.f_96281_);
   }

   public static List<FormattedCharSequence> m_96287_(OptionsList p_96288_, int p_96289_, int p_96290_) {
      Optional<AbstractWidget> optional = p_96288_.m_94480_((double)p_96289_, (double)p_96290_);
      return (List<FormattedCharSequence>)(optional.isPresent() && optional.get() instanceof TooltipAccessor ? ((TooltipAccessor)optional.get()).m_141932_() : ImmutableList.of());
   }
}