package net.minecraft.client.sounds;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WeighedSoundEvents implements Weighted<Sound> {
   private final List<Weighted<Sound>> f_120441_ = Lists.newArrayList();
   private final Random f_120442_ = new Random();
   private final ResourceLocation f_120443_;
   @Nullable
   private final Component f_120444_;

   public WeighedSoundEvents(ResourceLocation p_120446_, @Nullable String p_120447_) {
      this.f_120443_ = p_120446_;
      this.f_120444_ = p_120447_ == null ? null : new TranslatableComponent(p_120447_);
   }

   public int m_7789_() {
      int i = 0;

      for(Weighted<Sound> weighted : this.f_120441_) {
         i += weighted.m_7789_();
      }

      return i;
   }

   public Sound m_6776_() {
      int i = this.m_7789_();
      if (!this.f_120441_.isEmpty() && i != 0) {
         int j = this.f_120442_.nextInt(i);

         for(Weighted<Sound> weighted : this.f_120441_) {
            j -= weighted.m_7789_();
            if (j < 0) {
               return weighted.m_6776_();
            }
         }

         return SoundManager.f_120344_;
      } else {
         return SoundManager.f_120344_;
      }
   }

   public void m_120451_(Weighted<Sound> p_120452_) {
      this.f_120441_.add(p_120452_);
   }

   public ResourceLocation m_174998_() {
      return this.f_120443_;
   }

   @Nullable
   public Component m_120453_() {
      return this.f_120444_;
   }

   public void m_8054_(SoundEngine p_120450_) {
      for(Weighted<Sound> weighted : this.f_120441_) {
         weighted.m_8054_(p_120450_);
      }

   }
}