package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;

public class StatePropertiesPredicate {
   public static final StatePropertiesPredicate f_67658_ = new StatePropertiesPredicate(ImmutableList.of());
   private final List<StatePropertiesPredicate.PropertyMatcher> f_67659_;

   private static StatePropertiesPredicate.PropertyMatcher m_67686_(String p_67687_, JsonElement p_67688_) {
      if (p_67688_.isJsonPrimitive()) {
         String s2 = p_67688_.getAsString();
         return new StatePropertiesPredicate.ExactPropertyMatcher(p_67687_, s2);
      } else {
         JsonObject jsonobject = GsonHelper.m_13918_(p_67688_, "value");
         String s = jsonobject.has("min") ? m_67689_(jsonobject.get("min")) : null;
         String s1 = jsonobject.has("max") ? m_67689_(jsonobject.get("max")) : null;
         return (StatePropertiesPredicate.PropertyMatcher)(s != null && s.equals(s1) ? new StatePropertiesPredicate.ExactPropertyMatcher(p_67687_, s) : new StatePropertiesPredicate.RangedPropertyMatcher(p_67687_, s, s1));
      }
   }

   @Nullable
   private static String m_67689_(JsonElement p_67690_) {
      return p_67690_.isJsonNull() ? null : p_67690_.getAsString();
   }

   StatePropertiesPredicate(List<StatePropertiesPredicate.PropertyMatcher> p_67662_) {
      this.f_67659_ = ImmutableList.copyOf(p_67662_);
   }

   public <S extends StateHolder<?, S>> boolean m_67669_(StateDefinition<?, S> p_67670_, S p_67671_) {
      for(StatePropertiesPredicate.PropertyMatcher statepropertiespredicate$propertymatcher : this.f_67659_) {
         if (!statepropertiespredicate$propertymatcher.m_67718_(p_67670_, p_67671_)) {
            return false;
         }
      }

      return true;
   }

   public boolean m_67667_(BlockState p_67668_) {
      return this.m_67669_(p_67668_.m_60734_().m_49965_(), p_67668_);
   }

   public boolean m_67684_(FluidState p_67685_) {
      return this.m_67669_(p_67685_.m_76152_().m_76144_(), p_67685_);
   }

   public void m_67672_(StateDefinition<?, ?> p_67673_, Consumer<String> p_67674_) {
      this.f_67659_.forEach((p_67678_) -> {
         p_67678_.m_67721_(p_67673_, p_67674_);
      });
   }

   public static StatePropertiesPredicate m_67679_(@Nullable JsonElement p_67680_) {
      if (p_67680_ != null && !p_67680_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_67680_, "properties");
         List<StatePropertiesPredicate.PropertyMatcher> list = Lists.newArrayList();

         for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
            list.add(m_67686_(entry.getKey(), entry.getValue()));
         }

         return new StatePropertiesPredicate(list);
      } else {
         return f_67658_;
      }
   }

   public JsonElement m_67666_() {
      if (this == f_67658_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (!this.f_67659_.isEmpty()) {
            this.f_67659_.forEach((p_67683_) -> {
               jsonobject.add(p_67683_.m_67726_(), p_67683_.m_7682_());
            });
         }

         return jsonobject;
      }
   }

   public static class Builder {
      private final List<StatePropertiesPredicate.PropertyMatcher> f_67691_ = Lists.newArrayList();

      private Builder() {
      }

      public static StatePropertiesPredicate.Builder m_67693_() {
         return new StatePropertiesPredicate.Builder();
      }

      public StatePropertiesPredicate.Builder m_67700_(Property<?> p_67701_, String p_67702_) {
         this.f_67691_.add(new StatePropertiesPredicate.ExactPropertyMatcher(p_67701_.m_61708_(), p_67702_));
         return this;
      }

      public StatePropertiesPredicate.Builder m_67694_(Property<Integer> p_67695_, int p_67696_) {
         return this.m_67700_(p_67695_, Integer.toString(p_67696_));
      }

      public StatePropertiesPredicate.Builder m_67703_(Property<Boolean> p_67704_, boolean p_67705_) {
         return this.m_67700_(p_67704_, Boolean.toString(p_67705_));
      }

      public <T extends Comparable<T> & StringRepresentable> StatePropertiesPredicate.Builder m_67697_(Property<T> p_67698_, T p_67699_) {
         return this.m_67700_(p_67698_, p_67699_.m_7912_());
      }

      public StatePropertiesPredicate m_67706_() {
         return new StatePropertiesPredicate(this.f_67691_);
      }
   }

   static class ExactPropertyMatcher extends StatePropertiesPredicate.PropertyMatcher {
      private final String f_67707_;

      public ExactPropertyMatcher(String p_67709_, String p_67710_) {
         super(p_67709_);
         this.f_67707_ = p_67710_;
      }

      protected <T extends Comparable<T>> boolean m_7517_(StateHolder<?, ?> p_67713_, Property<T> p_67714_) {
         T t = p_67713_.m_61143_(p_67714_);
         Optional<T> optional = p_67714_.m_6215_(this.f_67707_);
         return optional.isPresent() && t.compareTo(optional.get()) == 0;
      }

      public JsonElement m_7682_() {
         return new JsonPrimitive(this.f_67707_);
      }
   }

   abstract static class PropertyMatcher {
      private final String f_67715_;

      public PropertyMatcher(String p_67717_) {
         this.f_67715_ = p_67717_;
      }

      public <S extends StateHolder<?, S>> boolean m_67718_(StateDefinition<?, S> p_67719_, S p_67720_) {
         Property<?> property = p_67719_.m_61081_(this.f_67715_);
         return property == null ? false : this.m_7517_(p_67720_, property);
      }

      protected abstract <T extends Comparable<T>> boolean m_7517_(StateHolder<?, ?> p_67724_, Property<T> p_67725_);

      public abstract JsonElement m_7682_();

      public String m_67726_() {
         return this.f_67715_;
      }

      public void m_67721_(StateDefinition<?, ?> p_67722_, Consumer<String> p_67723_) {
         Property<?> property = p_67722_.m_61081_(this.f_67715_);
         if (property == null) {
            p_67723_.accept(this.f_67715_);
         }

      }
   }

   static class RangedPropertyMatcher extends StatePropertiesPredicate.PropertyMatcher {
      @Nullable
      private final String f_67727_;
      @Nullable
      private final String f_67728_;

      public RangedPropertyMatcher(String p_67730_, @Nullable String p_67731_, @Nullable String p_67732_) {
         super(p_67730_);
         this.f_67727_ = p_67731_;
         this.f_67728_ = p_67732_;
      }

      protected <T extends Comparable<T>> boolean m_7517_(StateHolder<?, ?> p_67735_, Property<T> p_67736_) {
         T t = p_67735_.m_61143_(p_67736_);
         if (this.f_67727_ != null) {
            Optional<T> optional = p_67736_.m_6215_(this.f_67727_);
            if (!optional.isPresent() || t.compareTo(optional.get()) < 0) {
               return false;
            }
         }

         if (this.f_67728_ != null) {
            Optional<T> optional1 = p_67736_.m_6215_(this.f_67728_);
            if (!optional1.isPresent() || t.compareTo(optional1.get()) > 0) {
               return false;
            }
         }

         return true;
      }

      public JsonElement m_7682_() {
         JsonObject jsonobject = new JsonObject();
         if (this.f_67727_ != null) {
            jsonobject.addProperty("min", this.f_67727_);
         }

         if (this.f_67728_ != null) {
            jsonobject.addProperty("max", this.f_67728_);
         }

         return jsonobject;
      }
   }
}