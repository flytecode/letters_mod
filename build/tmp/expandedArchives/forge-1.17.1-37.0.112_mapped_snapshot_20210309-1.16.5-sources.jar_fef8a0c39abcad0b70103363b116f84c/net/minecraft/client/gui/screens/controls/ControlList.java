package net.minecraft.client.gui.screens.controls;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;

@OnlyIn(Dist.CLIENT)
public class ControlList extends ContainerObjectSelectionList<ControlList.Entry> {
   final ControlsScreen f_97396_;
   int f_97397_;

   public ControlList(ControlsScreen p_97399_, Minecraft p_97400_) {
      super(p_97400_, p_97399_.f_96543_ + 45, p_97399_.f_96544_, 43, p_97399_.f_96544_ - 32, 20);
      this.f_97396_ = p_97399_;
      KeyMapping[] akeymapping = ArrayUtils.clone(p_97400_.f_91066_.f_92059_);
      Arrays.sort((Object[])akeymapping);
      String s = null;

      for(KeyMapping keymapping : akeymapping) {
         String s1 = keymapping.m_90858_();
         if (!s1.equals(s)) {
            s = s1;
            this.m_7085_(new ControlList.CategoryEntry(new TranslatableComponent(s1)));
         }

         Component component = new TranslatableComponent(keymapping.m_90860_());
         int i = p_97400_.f_91062_.m_92852_(component);
         if (i > this.f_97397_) {
            this.f_97397_ = i;
         }

         this.m_7085_(new ControlList.KeyEntry(keymapping, component));
      }

   }

   protected int m_5756_() {
      return super.m_5756_() + 15 + 20;
   }

   public int m_5759_() {
      return super.m_5759_() + 32;
   }

   @OnlyIn(Dist.CLIENT)
   public class CategoryEntry extends ControlList.Entry {
      final Component f_97424_;
      private final int f_97425_;

      public CategoryEntry(Component p_97428_) {
         this.f_97424_ = p_97428_;
         this.f_97425_ = ControlList.this.f_93386_.f_91062_.m_92852_(this.f_97424_);
      }

      public void m_6311_(PoseStack p_97430_, int p_97431_, int p_97432_, int p_97433_, int p_97434_, int p_97435_, int p_97436_, int p_97437_, boolean p_97438_, float p_97439_) {
         ControlList.this.f_93386_.f_91062_.m_92889_(p_97430_, this.f_97424_, (float)(ControlList.this.f_93386_.f_91080_.f_96543_ / 2 - this.f_97425_ / 2), (float)(p_97432_ + p_97435_ - 9 - 1), 16777215);
      }

      public boolean m_5755_(boolean p_97442_) {
         return false;
      }

      public List<? extends GuiEventListener> m_6702_() {
         return Collections.emptyList();
      }

      public List<? extends NarratableEntry> m_142437_() {
         return ImmutableList.of(new NarratableEntry() {
            public NarratableEntry.NarrationPriority m_142684_() {
               return NarratableEntry.NarrationPriority.HOVERED;
            }

            public void m_142291_(NarrationElementOutput p_169579_) {
               p_169579_.m_169146_(NarratedElementType.TITLE, CategoryEntry.this.f_97424_);
            }
         });
      }
   }

   @OnlyIn(Dist.CLIENT)
   public abstract static class Entry extends ContainerObjectSelectionList.Entry<ControlList.Entry> {
   }

   @OnlyIn(Dist.CLIENT)
   public class KeyEntry extends ControlList.Entry {
      private final KeyMapping f_97445_;
      private final Component f_97446_;
      private final Button f_97447_;
      private final Button f_97448_;

      KeyEntry(final KeyMapping p_97451_, final Component p_97452_) {
         this.f_97445_ = p_97451_;
         this.f_97446_ = p_97452_;
         this.f_97447_ = new Button(0, 0, 75 + 20 /*Forge: add space*/, 20, p_97452_, (p_97479_) -> {
            ControlList.this.f_97396_.f_97514_ = p_97451_;
         }) {
            protected MutableComponent m_5646_() {
               return p_97451_.m_90862_() ? new TranslatableComponent("narrator.controls.unbound", p_97452_) : new TranslatableComponent("narrator.controls.bound", p_97452_, super.m_5646_());
            }
         };
         this.f_97448_ = new Button(0, 0, 50, 20, new TranslatableComponent("controls.reset"), (p_97475_) -> {
            f_97445_.setToDefault();
            ControlList.this.f_93386_.f_91066_.m_92159_(p_97451_, p_97451_.m_90861_());
            KeyMapping.m_90854_();
         }) {
            protected MutableComponent m_5646_() {
               return new TranslatableComponent("narrator.controls.reset", p_97452_);
            }
         };
      }

      public void m_6311_(PoseStack p_97463_, int p_97464_, int p_97465_, int p_97466_, int p_97467_, int p_97468_, int p_97469_, int p_97470_, boolean p_97471_, float p_97472_) {
         boolean flag = ControlList.this.f_97396_.f_97514_ == this.f_97445_;
         float f = (float)(p_97466_ + 90 - ControlList.this.f_97397_);
         ControlList.this.f_93386_.f_91062_.m_92889_(p_97463_, this.f_97446_, f, (float)(p_97465_ + p_97468_ / 2 - 9 / 2), 16777215);
         this.f_97448_.f_93620_ = p_97466_ + 190 + 20;
         this.f_97448_.f_93621_ = p_97465_;
         this.f_97448_.f_93623_ = !this.f_97445_.m_90864_();
         this.f_97448_.m_6305_(p_97463_, p_97469_, p_97470_, p_97472_);
         this.f_97447_.f_93620_ = p_97466_ + 105;
         this.f_97447_.f_93621_ = p_97465_;
         this.f_97447_.m_93666_(this.f_97445_.m_90863_());
         boolean flag1 = false;
         boolean keyCodeModifierConflict = true; // less severe form of conflict, like SHIFT conflicting with SHIFT+G
         if (!this.f_97445_.m_90862_()) {
            for(KeyMapping keymapping : ControlList.this.f_93386_.f_91066_.f_92059_) {
               if (keymapping != this.f_97445_ && this.f_97445_.m_90850_(keymapping)) {
                  flag1 = true;
                  keyCodeModifierConflict &= keymapping.hasKeyCodeModifierConflict(keymapping);
               }
            }
         }

         if (flag) {
            this.f_97447_.m_93666_((new TextComponent("> ")).m_7220_(this.f_97447_.m_6035_().m_6881_().m_130940_(ChatFormatting.YELLOW)).m_130946_(" <").m_130940_(ChatFormatting.YELLOW));
         } else if (flag1) {
            this.f_97447_.m_93666_(this.f_97447_.m_6035_().m_6881_().m_130940_(keyCodeModifierConflict ? ChatFormatting.GOLD : ChatFormatting.RED));
         }

         this.f_97447_.m_6305_(p_97463_, p_97469_, p_97470_, p_97472_);
      }

      public List<? extends GuiEventListener> m_6702_() {
         return ImmutableList.of(this.f_97447_, this.f_97448_);
      }

      public List<? extends NarratableEntry> m_142437_() {
         return ImmutableList.of(this.f_97447_, this.f_97448_);
      }

      public boolean m_6375_(double p_97459_, double p_97460_, int p_97461_) {
         if (this.f_97447_.m_6375_(p_97459_, p_97460_, p_97461_)) {
            return true;
         } else {
            return this.f_97448_.m_6375_(p_97459_, p_97460_, p_97461_);
         }
      }

      public boolean m_6348_(double p_97481_, double p_97482_, int p_97483_) {
         return this.f_97447_.m_6348_(p_97481_, p_97482_, p_97483_) || this.f_97448_.m_6348_(p_97481_, p_97482_, p_97483_);
      }
   }
}
