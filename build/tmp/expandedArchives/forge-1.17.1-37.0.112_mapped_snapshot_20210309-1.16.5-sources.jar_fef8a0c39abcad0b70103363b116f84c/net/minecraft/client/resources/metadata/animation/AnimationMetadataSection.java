package net.minecraft.client.resources.metadata.animation;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnimationMetadataSection {
   public static final AnimationMetadataSectionSerializer f_119011_ = new AnimationMetadataSectionSerializer();
   public static final String f_174858_ = "animation";
   public static final int f_174859_ = 1;
   public static final int f_174860_ = -1;
   public static final AnimationMetadataSection f_119012_ = new AnimationMetadataSection(Lists.newArrayList(), -1, -1, 1, false) {
      public Pair<Integer, Integer> m_7117_(int p_119054_, int p_119055_) {
         return Pair.of(p_119054_, p_119055_);
      }
   };
   private final List<AnimationFrame> f_119013_;
   private final int f_119014_;
   private final int f_119015_;
   private final int f_119016_;
   private final boolean f_119017_;

   public AnimationMetadataSection(List<AnimationFrame> p_119020_, int p_119021_, int p_119022_, int p_119023_, boolean p_119024_) {
      this.f_119013_ = p_119020_;
      this.f_119014_ = p_119021_;
      this.f_119015_ = p_119022_;
      this.f_119016_ = p_119023_;
      this.f_119017_ = p_119024_;
   }

   private static boolean m_119033_(int p_119034_, int p_119035_) {
      return p_119034_ / p_119035_ * p_119035_ == p_119034_;
   }

   public Pair<Integer, Integer> m_7117_(int p_119028_, int p_119029_) {
      Pair<Integer, Integer> pair = this.m_119039_(p_119028_, p_119029_);
      int i = pair.getFirst();
      int j = pair.getSecond();
      if (m_119033_(p_119028_, i) && m_119033_(p_119029_, j)) {
         return pair;
      } else {
         throw new IllegalArgumentException(String.format("Image size %s,%s is not multiply of frame size %s,%s", p_119028_, p_119029_, i, j));
      }
   }

   private Pair<Integer, Integer> m_119039_(int p_119040_, int p_119041_) {
      if (this.f_119014_ != -1) {
         return this.f_119015_ != -1 ? Pair.of(this.f_119014_, this.f_119015_) : Pair.of(this.f_119014_, p_119041_);
      } else if (this.f_119015_ != -1) {
         return Pair.of(p_119040_, this.f_119015_);
      } else {
         int i = Math.min(p_119040_, p_119041_);
         return Pair.of(i, i);
      }
   }

   public int m_119026_(int p_119027_) {
      return this.f_119015_ == -1 ? p_119027_ : this.f_119015_;
   }

   public int m_119031_(int p_119032_) {
      return this.f_119014_ == -1 ? p_119032_ : this.f_119014_;
   }

   public int m_119030_() {
      return this.f_119016_;
   }

   public boolean m_119036_() {
      return this.f_119017_;
   }

   public void m_174861_(AnimationMetadataSection.FrameOutput p_174862_) {
      for(AnimationFrame animationframe : this.f_119013_) {
         p_174862_.m_174863_(animationframe.m_119010_(), animationframe.m_174856_(this.f_119016_));
      }

   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   public interface FrameOutput {
      void m_174863_(int p_174864_, int p_174865_);
   }
}