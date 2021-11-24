package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DefaultedRegistry<T> extends MappedRegistry<T> {
   private final ResourceLocation f_122309_;
   private T f_122310_;

   public DefaultedRegistry(String p_122312_, ResourceKey<? extends Registry<T>> p_122313_, Lifecycle p_122314_) {
      super(p_122313_, p_122314_);
      this.f_122309_ = new ResourceLocation(p_122312_);
   }

   public <V extends T> V m_5748_(int p_122319_, ResourceKey<T> p_122320_, V p_122321_, Lifecycle p_122322_) {
      if (this.f_122309_.equals(p_122320_.m_135782_())) {
         this.f_122310_ = (T)p_122321_;
      }

      return super.m_5748_(p_122319_, p_122320_, p_122321_, p_122322_);
   }

   public int m_7447_(@Nullable T p_122324_) {
      int i = super.m_7447_(p_122324_);
      return i == -1 ? super.m_7447_(this.f_122310_) : i;
   }

   @Nonnull
   public ResourceLocation m_7981_(T p_122330_) {
      ResourceLocation resourcelocation = super.m_7981_(p_122330_);
      return resourcelocation == null ? this.f_122309_ : resourcelocation;
   }

   @Nonnull
   public T m_7745_(@Nullable ResourceLocation p_122328_) {
      T t = super.m_7745_(p_122328_);
      return (T)(t == null ? this.f_122310_ : t);
   }

   public Optional<T> m_6612_(@Nullable ResourceLocation p_122332_) {
      return Optional.ofNullable(super.m_7745_(p_122332_));
   }

   @Nonnull
   public T m_7942_(int p_122317_) {
      T t = super.m_7942_(p_122317_);
      return (T)(t == null ? this.f_122310_ : t);
   }

   @Nonnull
   public T m_142697_(Random p_122326_) {
      T t = super.m_142697_(p_122326_);
      return (T)(t == null ? this.f_122310_ : t);
   }

   public ResourceLocation m_122315_() {
      return this.f_122309_;
   }
}