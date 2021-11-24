package net.minecraft.resources;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.Registry;

public class ResourceKey<T> implements Comparable<ResourceKey<?>> {
   private static final Map<String, ResourceKey<?>> f_135775_ = Collections.synchronizedMap(Maps.newIdentityHashMap());
   private final ResourceLocation f_135776_;
   private final ResourceLocation f_135777_;

   public static <T> ResourceKey<T> m_135785_(ResourceKey<? extends Registry<T>> p_135786_, ResourceLocation p_135787_) {
      return m_135790_(p_135786_.f_135777_, p_135787_);
   }

   public static <T> ResourceKey<Registry<T>> m_135788_(ResourceLocation p_135789_) {
      return m_135790_(Registry.f_122895_, p_135789_);
   }

   private static <T> ResourceKey<T> m_135790_(ResourceLocation p_135791_, ResourceLocation p_135792_) {
      String s = (p_135791_ + ":" + p_135792_).intern();
      return (ResourceKey<T>)f_135775_.computeIfAbsent(s, (p_135796_) -> {
         return new ResourceKey(p_135791_, p_135792_);
      });
   }

   private ResourceKey(ResourceLocation p_135780_, ResourceLocation p_135781_) {
      this.f_135776_ = p_135780_;
      this.f_135777_ = p_135781_;
   }

   public String toString() {
      return "ResourceKey[" + this.f_135776_ + " / " + this.f_135777_ + "]";
   }

   public boolean m_135783_(ResourceKey<? extends Registry<?>> p_135784_) {
      return this.f_135776_.equals(p_135784_.m_135782_());
   }

   public ResourceLocation m_135782_() {
      return this.f_135777_;
   }

   public static <T> Function<ResourceLocation, ResourceKey<T>> m_135797_(ResourceKey<? extends Registry<T>> p_135798_) {
      return (p_135801_) -> {
         return m_135785_(p_135798_, p_135801_);
      };
   }

   public ResourceLocation getRegistryName() {
      return this.f_135776_;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      return f_135776_.equals(((ResourceKey<?>) o).f_135776_) && f_135777_.equals(((ResourceKey<?>) o).f_135777_);
   }

   @Override
   public int compareTo(ResourceKey<?> o) {
      int ret = this.getRegistryName().compareTo(o.getRegistryName());
      if (ret == 0) ret = this.m_135782_().compareTo(o.m_135782_());
      return ret;
   }
}
