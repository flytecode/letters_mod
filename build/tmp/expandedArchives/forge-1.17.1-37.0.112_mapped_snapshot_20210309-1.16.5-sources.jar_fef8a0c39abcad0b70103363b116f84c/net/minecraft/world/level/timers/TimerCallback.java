package net.minecraft.world.level.timers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

@FunctionalInterface
public interface TimerCallback<T> {
   void m_5821_(T p_82213_, TimerQueue<T> p_82214_, long p_82215_);

   public abstract static class Serializer<T, C extends TimerCallback<T>> {
      private final ResourceLocation f_82216_;
      private final Class<?> f_82217_;

      public Serializer(ResourceLocation p_82219_, Class<?> p_82220_) {
         this.f_82216_ = p_82219_;
         this.f_82217_ = p_82220_;
      }

      public ResourceLocation m_82221_() {
         return this.f_82216_;
      }

      public Class<?> m_82224_() {
         return this.f_82217_;
      }

      public abstract void m_6585_(CompoundTag p_82222_, C p_82223_);

      public abstract C m_6006_(CompoundTag p_82225_);
   }
}