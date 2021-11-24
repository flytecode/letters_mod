package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootPoolSingletonContainer extends LootPoolEntryContainer {
   public static final int f_165150_ = 1;
   public static final int f_165151_ = 0;
   protected final int f_79675_;
   protected final int f_79676_;
   protected final LootItemFunction[] f_79677_;
   final BiFunction<ItemStack, LootContext, ItemStack> f_79678_;
   private final LootPoolEntry f_79679_ = new LootPoolSingletonContainer.EntryBase() {
      public void m_6941_(Consumer<ItemStack> p_79700_, LootContext p_79701_) {
         LootPoolSingletonContainer.this.m_6948_(LootItemFunction.m_80724_(LootPoolSingletonContainer.this.f_79678_, p_79700_, p_79701_), p_79701_);
      }
   };

   protected LootPoolSingletonContainer(int p_79681_, int p_79682_, LootItemCondition[] p_79683_, LootItemFunction[] p_79684_) {
      super(p_79683_);
      this.f_79675_ = p_79681_;
      this.f_79676_ = p_79682_;
      this.f_79677_ = p_79684_;
      this.f_79678_ = LootItemFunctions.m_80770_(p_79684_);
   }

   public void m_6165_(ValidationContext p_79686_) {
      super.m_6165_(p_79686_);

      for(int i = 0; i < this.f_79677_.length; ++i) {
         this.f_79677_[i].m_6169_(p_79686_.m_79365_(".functions[" + i + "]"));
      }

   }

   protected abstract void m_6948_(Consumer<ItemStack> p_79691_, LootContext p_79692_);

   public boolean m_6562_(LootContext p_79694_, Consumer<LootPoolEntry> p_79695_) {
      if (this.m_79639_(p_79694_)) {
         p_79695_.accept(this.f_79679_);
         return true;
      } else {
         return false;
      }
   }

   public static LootPoolSingletonContainer.Builder<?> m_79687_(LootPoolSingletonContainer.EntryConstructor p_79688_) {
      return new LootPoolSingletonContainer.DummyBuilder(p_79688_);
   }

   public abstract static class Builder<T extends LootPoolSingletonContainer.Builder<T>> extends LootPoolEntryContainer.Builder<T> implements FunctionUserBuilder<T> {
      protected int f_79702_ = 1;
      protected int f_79703_ = 0;
      private final List<LootItemFunction> f_79704_ = Lists.newArrayList();

      public T m_5577_(LootItemFunction.Builder p_79710_) {
         this.f_79704_.add(p_79710_.m_7453_());
         return this.m_6897_();
      }

      protected LootItemFunction[] m_79706_() {
         return this.f_79704_.toArray(new LootItemFunction[0]);
      }

      public T m_79707_(int p_79708_) {
         this.f_79702_ = p_79708_;
         return this.m_6897_();
      }

      public T m_79711_(int p_79712_) {
         this.f_79703_ = p_79712_;
         return this.m_6897_();
      }
   }

   static class DummyBuilder extends LootPoolSingletonContainer.Builder<LootPoolSingletonContainer.DummyBuilder> {
      private final LootPoolSingletonContainer.EntryConstructor f_79715_;

      public DummyBuilder(LootPoolSingletonContainer.EntryConstructor p_79717_) {
         this.f_79715_ = p_79717_;
      }

      protected LootPoolSingletonContainer.DummyBuilder m_6897_() {
         return this;
      }

      public LootPoolEntryContainer m_7512_() {
         return this.f_79715_.m_79726_(this.f_79702_, this.f_79703_, this.m_79651_(), this.m_79706_());
      }
   }

   protected abstract class EntryBase implements LootPoolEntry {
      public int m_7067_(float p_79725_) {
         return Math.max(Mth.m_14143_((float)LootPoolSingletonContainer.this.f_79675_ + (float)LootPoolSingletonContainer.this.f_79676_ * p_79725_), 0);
      }
   }

   @FunctionalInterface
   protected interface EntryConstructor {
      LootPoolSingletonContainer m_79726_(int p_79727_, int p_79728_, LootItemCondition[] p_79729_, LootItemFunction[] p_79730_);
   }

   public abstract static class Serializer<T extends LootPoolSingletonContainer> extends LootPoolEntryContainer.Serializer<T> {
      public void m_7219_(JsonObject p_79741_, T p_79742_, JsonSerializationContext p_79743_) {
         if (p_79742_.f_79675_ != 1) {
            p_79741_.addProperty("weight", p_79742_.f_79675_);
         }

         if (p_79742_.f_79676_ != 0) {
            p_79741_.addProperty("quality", p_79742_.f_79676_);
         }

         if (!ArrayUtils.isEmpty((Object[])p_79742_.f_79677_)) {
            p_79741_.add("functions", p_79743_.serialize(p_79742_.f_79677_));
         }

      }

      public final T m_5921_(JsonObject p_79733_, JsonDeserializationContext p_79734_, LootItemCondition[] p_79735_) {
         int i = GsonHelper.m_13824_(p_79733_, "weight", 1);
         int j = GsonHelper.m_13824_(p_79733_, "quality", 0);
         LootItemFunction[] alootitemfunction = GsonHelper.m_13845_(p_79733_, "functions", new LootItemFunction[0], p_79734_, LootItemFunction[].class);
         return this.m_7267_(p_79733_, p_79734_, i, j, p_79735_, alootitemfunction);
      }

      protected abstract T m_7267_(JsonObject p_79744_, JsonDeserializationContext p_79745_, int p_79746_, int p_79747_, LootItemCondition[] p_79748_, LootItemFunction[] p_79749_);
   }
}