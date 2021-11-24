package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SimpleOptionsSubScreen extends OptionsSubScreen {
   private final Option[] f_96666_;
   @Nullable
   private AbstractWidget f_96667_;
   private OptionsList f_96668_;

   public SimpleOptionsSubScreen(Screen p_96670_, Options p_96671_, Component p_96672_, Option[] p_96673_) {
      super(p_96670_, p_96671_, p_96672_);
      this.f_96666_ = p_96673_;
   }

   protected void m_7856_() {
      this.f_96668_ = new OptionsList(this.f_96541_, this.f_96543_, this.f_96544_, 32, this.f_96544_ - 32, 25);
      this.f_96668_.m_94476_(this.f_96666_);
      this.m_7787_(this.f_96668_);
      this.m_7853_();
      this.f_96667_ = this.f_96668_.m_94478_(Option.f_91627_);
      if (this.f_96667_ != null) {
         this.f_96667_.f_93623_ = NarratorChatListener.f_93311_.m_93316_();
      }

   }

   protected void m_7853_() {
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 27, 200, 20, CommonComponents.f_130655_, (p_96680_) -> {
         this.f_96541_.m_91152_(this.f_96281_);
      }));
   }

   public void m_6305_(PoseStack p_96675_, int p_96676_, int p_96677_, float p_96678_) {
      this.m_7333_(p_96675_);
      this.f_96668_.m_6305_(p_96675_, p_96676_, p_96677_, p_96678_);
      m_93215_(p_96675_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 16777215);
      super.m_6305_(p_96675_, p_96676_, p_96677_, p_96678_);
      List<FormattedCharSequence> list = m_96287_(this.f_96668_, p_96676_, p_96677_);
      this.m_96617_(p_96675_, list, p_96676_, p_96677_);
   }

   public void m_96682_() {
      if (this.f_96667_ instanceof CycleButton) {
         ((CycleButton)this.f_96667_).m_168892_(this.f_96282_.f_92074_);
      }

   }
}