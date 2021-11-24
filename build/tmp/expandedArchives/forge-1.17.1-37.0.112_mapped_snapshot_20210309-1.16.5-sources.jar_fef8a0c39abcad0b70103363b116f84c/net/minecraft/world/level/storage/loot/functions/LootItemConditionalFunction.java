package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootItemConditionalFunction implements LootItemFunction {
   protected final LootItemCondition[] f_80676_;
   private final Predicate<LootContext> f_80675_;

   protected LootItemConditionalFunction(LootItemCondition[] p_80678_) {
      this.f_80676_ = p_80678_;
      this.f_80675_ = LootItemConditions.m_81834_(p_80678_);
   }

   public final ItemStack apply(ItemStack p_80689_, LootContext p_80690_) {
      return this.f_80675_.test(p_80690_) ? this.m_7372_(p_80689_, p_80690_) : p_80689_;
   }

   protected abstract ItemStack m_7372_(ItemStack p_80679_, LootContext p_80680_);

   public void m_6169_(ValidationContext p_80682_) {
      LootItemFunction.super.m_6169_(p_80682_);

      for(int i = 0; i < this.f_80676_.length; ++i) {
         this.f_80676_[i].m_6169_(p_80682_.m_79365_(".conditions[" + i + "]"));
      }

   }

   protected static LootItemConditionalFunction.Builder<?> m_80683_(Function<LootItemCondition[], LootItemFunction> p_80684_) {
      return new LootItemConditionalFunction.DummyBuilder(p_80684_);
   }

   public abstract static class Builder<T extends LootItemConditionalFunction.Builder<T>> implements LootItemFunction.Builder, ConditionUserBuilder<T> {
      private final List<LootItemCondition> f_80691_ = Lists.newArrayList();

      public T m_6509_(LootItemCondition.Builder p_80694_) {
         this.f_80691_.add(p_80694_.m_6409_());
         return this.m_6477_();
      }

      public final T m_5476_() {
         return this.m_6477_();
      }

      protected abstract T m_6477_();

      protected LootItemCondition[] m_80699_() {
         return this.f_80691_.toArray(new LootItemCondition[0]);
      }
   }

   static final class DummyBuilder extends LootItemConditionalFunction.Builder<LootItemConditionalFunction.DummyBuilder> {
      private final Function<LootItemCondition[], LootItemFunction> f_80700_;

      public DummyBuilder(Function<LootItemCondition[], LootItemFunction> p_80702_) {
         this.f_80700_ = p_80702_;
      }

      protected LootItemConditionalFunction.DummyBuilder m_6477_() {
         return this;
      }

      public LootItemFunction m_7453_() {
         return this.f_80700_.apply(this.m_80699_());
      }
   }

   public abstract static class Serializer<T extends LootItemConditionalFunction> implements net.minecraft.world.level.storage.loot.Serializer<T> {
      public void m_6170_(JsonObject p_80711_, T p_80712_, JsonSerializationContext p_80713_) {
         if (!ArrayUtils.isEmpty((Object[])p_80712_.f_80676_)) {
            p_80711_.add("conditions", p_80713_.serialize(p_80712_.f_80676_));
         }

      }

      public final T m_7561_(JsonObject p_80719_, JsonDeserializationContext p_80720_) {
         LootItemCondition[] alootitemcondition = GsonHelper.m_13845_(p_80719_, "conditions", new LootItemCondition[0], p_80720_, LootItemCondition[].class);
         return this.m_6821_(p_80719_, p_80720_, alootitemcondition);
      }

      public abstract T m_6821_(JsonObject p_80721_, JsonDeserializationContext p_80722_, LootItemCondition[] p_80723_);
   }
}