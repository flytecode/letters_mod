package net.minecraft.stats;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class StatType<T> extends net.minecraftforge.registries.ForgeRegistryEntry<StatType<?>> implements Iterable<Stat<T>> {
   private final Registry<T> f_12888_;
   private final Map<T, Stat<T>> f_12889_ = new IdentityHashMap<>();
   @Nullable
   private Component f_12890_;

   public StatType(Registry<T> p_12892_) {
      this.f_12888_ = p_12892_;
   }

   public boolean m_12897_(T p_12898_) {
      return this.f_12889_.containsKey(p_12898_);
   }

   public Stat<T> m_12899_(T p_12900_, StatFormatter p_12901_) {
      return this.f_12889_.computeIfAbsent(p_12900_, (p_12896_) -> {
         return new Stat<>(this, p_12896_, p_12901_);
      });
   }

   public Registry<T> m_12893_() {
      return this.f_12888_;
   }

   public Iterator<Stat<T>> iterator() {
      return this.f_12889_.values().iterator();
   }

   public Stat<T> m_12902_(T p_12903_) {
      return this.m_12899_(p_12903_, StatFormatter.f_12873_);
   }

   public String m_12904_() {
      return "stat_type." + Registry.f_122867_.m_7981_(this).toString().replace(':', '.');
   }

   public Component m_12905_() {
      if (this.f_12890_ == null) {
         this.f_12890_ = new TranslatableComponent(this.m_12904_());
      }

      return this.f_12890_;
   }
}
