package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.level.block.state.properties.Property;

public final class Selector {
   private static final Selector f_125479_ = new Selector(ImmutableList.of());
   private static final Comparator<Property.Value<?>> f_125480_ = Comparator.comparing((p_125494_) -> {
      return p_125494_.m_61721_().m_61708_();
   });
   private final List<Property.Value<?>> f_125481_;

   public Selector m_125486_(Property.Value<?> p_125487_) {
      return new Selector(ImmutableList.<Property.Value<?>>builder().addAll(this.f_125481_).add(p_125487_).build());
   }

   public Selector m_125488_(Selector p_125489_) {
      return new Selector(ImmutableList.<Property.Value<?>>builder().addAll(this.f_125481_).addAll(p_125489_.f_125481_).build());
   }

   private Selector(List<Property.Value<?>> p_125484_) {
      this.f_125481_ = p_125484_;
   }

   public static Selector m_125485_() {
      return f_125479_;
   }

   public static Selector m_125490_(Property.Value<?>... p_125491_) {
      return new Selector(ImmutableList.copyOf(p_125491_));
   }

   public boolean equals(Object p_125496_) {
      return this == p_125496_ || p_125496_ instanceof Selector && this.f_125481_.equals(((Selector)p_125496_).f_125481_);
   }

   public int hashCode() {
      return this.f_125481_.hashCode();
   }

   public String m_125492_() {
      return this.f_125481_.stream().sorted(f_125480_).map(Property.Value::toString).collect(Collectors.joining(","));
   }

   public String toString() {
      return this.m_125492_();
   }
}