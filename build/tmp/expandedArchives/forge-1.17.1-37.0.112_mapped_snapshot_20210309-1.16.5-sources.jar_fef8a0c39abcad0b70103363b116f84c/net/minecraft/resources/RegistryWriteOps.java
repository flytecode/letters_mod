package net.minecraft.resources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;

public class RegistryWriteOps<T> extends DelegatingOps<T> {
   private final RegistryAccess f_179906_;

   public static <T> RegistryWriteOps<T> m_135767_(DynamicOps<T> p_135768_, RegistryAccess p_135769_) {
      return new RegistryWriteOps<>(p_135768_, p_135769_);
   }

   private RegistryWriteOps(DynamicOps<T> p_135765_, RegistryAccess p_135766_) {
      super(p_135765_);
      this.f_179906_ = p_135766_;
   }

   protected <E> DataResult<T> m_135770_(E p_135771_, T p_135772_, ResourceKey<? extends Registry<E>> p_135773_, Codec<E> p_135774_) {
      Optional<WritableRegistry<E>> optional = this.f_179906_.m_142664_(p_135773_);
      if (optional.isPresent()) {
         WritableRegistry<E> writableregistry = optional.get();
         Optional<ResourceKey<E>> optional1 = writableregistry.m_7854_(p_135771_);
         if (optional1.isPresent()) {
            ResourceKey<E> resourcekey = optional1.get();
            return ResourceLocation.f_135803_.encode(resourcekey.m_135782_(), this.f_135465_, p_135772_);
         }
      }

      return p_135774_.encode(p_135771_, this, p_135772_);
   }
}