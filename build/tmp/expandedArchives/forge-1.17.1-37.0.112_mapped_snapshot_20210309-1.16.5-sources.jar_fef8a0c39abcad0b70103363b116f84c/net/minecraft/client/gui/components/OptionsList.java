package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionsList extends ContainerObjectSelectionList<OptionsList.Entry> {
   public OptionsList(Minecraft p_94465_, int p_94466_, int p_94467_, int p_94468_, int p_94469_, int p_94470_) {
      super(p_94465_, p_94466_, p_94467_, p_94468_, p_94469_, p_94470_);
      this.f_93394_ = false;
   }

   public int m_94471_(Option p_94472_) {
      return this.m_7085_(OptionsList.Entry.m_94506_(this.f_93386_.f_91066_, this.f_93388_, p_94472_));
   }

   public void m_94473_(Option p_94474_, @Nullable Option p_94475_) {
      this.m_7085_(OptionsList.Entry.m_94510_(this.f_93386_.f_91066_, this.f_93388_, p_94474_, p_94475_));
   }

   public void m_94476_(Option[] p_94477_) {
      for(int i = 0; i < p_94477_.length; i += 2) {
         this.m_94473_(p_94477_[i], i < p_94477_.length - 1 ? p_94477_[i + 1] : null);
      }

   }

   public int m_5759_() {
      return 400;
   }

   protected int m_5756_() {
      return super.m_5756_() + 32;
   }

   @Nullable
   public AbstractWidget m_94478_(Option p_94479_) {
      for(OptionsList.Entry optionslist$entry : this.m_6702_()) {
         AbstractWidget abstractwidget = optionslist$entry.f_169045_.get(p_94479_);
         if (abstractwidget != null) {
            return abstractwidget;
         }
      }

      return null;
   }

   public Optional<AbstractWidget> m_94480_(double p_94481_, double p_94482_) {
      for(OptionsList.Entry optionslist$entry : this.m_6702_()) {
         for(AbstractWidget abstractwidget : optionslist$entry.f_94485_) {
            if (abstractwidget.m_5953_(p_94481_, p_94482_)) {
               return Optional.of(abstractwidget);
            }
         }
      }

      return Optional.empty();
   }

   @OnlyIn(Dist.CLIENT)
   protected static class Entry extends ContainerObjectSelectionList.Entry<OptionsList.Entry> {
      final Map<Option, AbstractWidget> f_169045_;
      final List<AbstractWidget> f_94485_;

      private Entry(Map<Option, AbstractWidget> p_169047_) {
         this.f_169045_ = p_169047_;
         this.f_94485_ = ImmutableList.copyOf(p_169047_.values());
      }

      public static OptionsList.Entry m_94506_(Options p_94507_, int p_94508_, Option p_94509_) {
         return new OptionsList.Entry(ImmutableMap.of(p_94509_, p_94509_.m_7496_(p_94507_, p_94508_ / 2 - 155, 0, 310)));
      }

      public static OptionsList.Entry m_94510_(Options p_94511_, int p_94512_, Option p_94513_, @Nullable Option p_94514_) {
         AbstractWidget abstractwidget = p_94513_.m_7496_(p_94511_, p_94512_ / 2 - 155, 0, 150);
         return p_94514_ == null ? new OptionsList.Entry(ImmutableMap.of(p_94513_, abstractwidget)) : new OptionsList.Entry(ImmutableMap.of(p_94513_, abstractwidget, p_94514_, p_94514_.m_7496_(p_94511_, p_94512_ / 2 - 155 + 160, 0, 150)));
      }

      public void m_6311_(PoseStack p_94496_, int p_94497_, int p_94498_, int p_94499_, int p_94500_, int p_94501_, int p_94502_, int p_94503_, boolean p_94504_, float p_94505_) {
         this.f_94485_.forEach((p_94494_) -> {
            p_94494_.f_93621_ = p_94498_;
            p_94494_.m_6305_(p_94496_, p_94502_, p_94503_, p_94505_);
         });
      }

      public List<? extends GuiEventListener> m_6702_() {
         return this.f_94485_;
      }

      public List<? extends NarratableEntry> m_142437_() {
         return this.f_94485_;
      }
   }
}