package net.minecraft.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Button extends AbstractButton {
   public static final Button.OnTooltip f_93716_ = (p_93740_, p_93741_, p_93742_, p_93743_) -> {
   };
   protected final Button.OnPress f_93717_;
   protected final Button.OnTooltip f_93718_;

   public Button(int p_93721_, int p_93722_, int p_93723_, int p_93724_, Component p_93725_, Button.OnPress p_93726_) {
      this(p_93721_, p_93722_, p_93723_, p_93724_, p_93725_, p_93726_, f_93716_);
   }

   public Button(int p_93728_, int p_93729_, int p_93730_, int p_93731_, Component p_93732_, Button.OnPress p_93733_, Button.OnTooltip p_93734_) {
      super(p_93728_, p_93729_, p_93730_, p_93731_, p_93732_);
      this.f_93717_ = p_93733_;
      this.f_93718_ = p_93734_;
   }

   public void m_5691_() {
      this.f_93717_.m_93750_(this);
   }

   public void m_6303_(PoseStack p_93746_, int p_93747_, int p_93748_, float p_93749_) {
      super.m_6303_(p_93746_, p_93747_, p_93748_, p_93749_);
      if (this.m_5702_()) {
         this.m_7428_(p_93746_, p_93747_, p_93748_);
      }

   }

   public void m_7428_(PoseStack p_93736_, int p_93737_, int p_93738_) {
      this.f_93718_.m_93752_(this, p_93736_, p_93737_, p_93738_);
   }

   public void m_142291_(NarrationElementOutput p_168838_) {
      this.m_168802_(p_168838_);
      this.f_93718_.m_142753_((p_168841_) -> {
         p_168838_.m_169146_(NarratedElementType.HINT, p_168841_);
      });
   }

   @OnlyIn(Dist.CLIENT)
   public interface OnPress {
      void m_93750_(Button p_93751_);
   }

   @OnlyIn(Dist.CLIENT)
   public interface OnTooltip {
      void m_93752_(Button p_93753_, PoseStack p_93754_, int p_93755_, int p_93756_);

      default void m_142753_(Consumer<Component> p_168842_) {
      }
   }
}