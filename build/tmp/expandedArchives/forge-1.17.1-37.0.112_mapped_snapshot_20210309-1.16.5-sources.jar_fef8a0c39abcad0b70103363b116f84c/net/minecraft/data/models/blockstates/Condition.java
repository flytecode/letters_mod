package net.minecraft.data.models.blockstates;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public interface Condition extends Supplier<JsonElement> {
   void m_7619_(StateDefinition<?, ?> p_125136_);

   static Condition.TerminalCondition m_125135_() {
      return new Condition.TerminalCondition();
   }

   static Condition m_176293_(Condition... p_176294_) {
      return new Condition.CompositeCondition(Condition.Operation.AND, Arrays.asList(p_176294_));
   }

   static Condition m_125137_(Condition... p_125138_) {
      return new Condition.CompositeCondition(Condition.Operation.OR, Arrays.asList(p_125138_));
   }

   public static class CompositeCondition implements Condition {
      private final Condition.Operation f_125139_;
      private final List<Condition> f_125140_;

      CompositeCondition(Condition.Operation p_125142_, List<Condition> p_125143_) {
         this.f_125139_ = p_125142_;
         this.f_125140_ = p_125143_;
      }

      public void m_7619_(StateDefinition<?, ?> p_125149_) {
         this.f_125140_.forEach((p_125152_) -> {
            p_125152_.m_7619_(p_125149_);
         });
      }

      public JsonElement get() {
         JsonArray jsonarray = new JsonArray();
         this.f_125140_.stream().map(Supplier::get).forEach(jsonarray::add);
         JsonObject jsonobject = new JsonObject();
         jsonobject.add(this.f_125139_.f_125157_, jsonarray);
         return jsonobject;
      }
   }

   public static enum Operation {
      AND("AND"),
      OR("OR");

      final String f_125157_;

      private Operation(String p_125163_) {
         this.f_125157_ = p_125163_;
      }
   }

   public static class TerminalCondition implements Condition {
      private final Map<Property<?>, String> f_125169_ = Maps.newHashMap();

      private static <T extends Comparable<T>> String m_125186_(Property<T> p_125187_, Stream<T> p_125188_) {
         return p_125188_.map(p_125187_::m_6940_).collect(Collectors.joining("|"));
      }

      private static <T extends Comparable<T>> String m_125194_(Property<T> p_125195_, T p_125196_, T[] p_125197_) {
         return m_125186_(p_125195_, Stream.concat(Stream.of(p_125196_), Stream.of(p_125197_)));
      }

      private <T extends Comparable<T>> void m_125183_(Property<T> p_125184_, String p_125185_) {
         String s = this.f_125169_.put(p_125184_, p_125185_);
         if (s != null) {
            throw new IllegalStateException("Tried to replace " + p_125184_ + " value from " + s + " to " + p_125185_);
         }
      }

      public final <T extends Comparable<T>> Condition.TerminalCondition m_125176_(Property<T> p_125177_, T p_125178_) {
         this.m_125183_(p_125177_, p_125177_.m_6940_(p_125178_));
         return this;
      }

      @SafeVarargs
      public final <T extends Comparable<T>> Condition.TerminalCondition m_125179_(Property<T> p_125180_, T p_125181_, T... p_125182_) {
         this.m_125183_(p_125180_, m_125194_(p_125180_, p_125181_, p_125182_));
         return this;
      }

      public final <T extends Comparable<T>> Condition.TerminalCondition m_176296_(Property<T> p_176297_, T p_176298_) {
         this.m_125183_(p_176297_, "!" + p_176297_.m_6940_(p_176298_));
         return this;
      }

      @SafeVarargs
      public final <T extends Comparable<T>> Condition.TerminalCondition m_176299_(Property<T> p_176300_, T p_176301_, T... p_176302_) {
         this.m_125183_(p_176300_, "!" + m_125194_(p_176300_, p_176301_, p_176302_));
         return this;
      }

      public JsonElement get() {
         JsonObject jsonobject = new JsonObject();
         this.f_125169_.forEach((p_125191_, p_125192_) -> {
            jsonobject.addProperty(p_125191_.m_61708_(), p_125192_);
         });
         return jsonobject;
      }

      public void m_7619_(StateDefinition<?, ?> p_125172_) {
         List<Property<?>> list = this.f_125169_.keySet().stream().filter((p_125175_) -> {
            return p_125172_.m_61081_(p_125175_.m_61708_()) != p_125175_;
         }).collect(Collectors.toList());
         if (!list.isEmpty()) {
            throw new IllegalStateException("Properties " + list + " are missing from " + p_125172_);
         }
      }
   }
}