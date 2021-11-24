package net.minecraft.world.level.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;

public class LootPool {
   private final String name;
   final LootPoolEntryContainer[] f_79023_;
   final LootItemCondition[] f_79024_;
   private final Predicate<LootContext> f_79025_;
   final LootItemFunction[] f_79026_;
   private final BiFunction<ItemStack, LootContext, ItemStack> f_79027_;
   NumberProvider f_79028_;
   NumberProvider f_79029_;

   LootPool(LootPoolEntryContainer[] p_165128_, LootItemCondition[] p_165129_, LootItemFunction[] p_165130_, NumberProvider p_165131_, NumberProvider p_165132_, String name) {
      this.name = name;
      this.f_79023_ = p_165128_;
      this.f_79024_ = p_165129_;
      this.f_79025_ = LootItemConditions.m_81834_(p_165129_);
      this.f_79026_ = p_165130_;
      this.f_79027_ = LootItemFunctions.m_80770_(p_165130_);
      this.f_79028_ = p_165131_;
      this.f_79029_ = p_165132_;
   }

   private void m_79058_(Consumer<ItemStack> p_79059_, LootContext p_79060_) {
      Random random = p_79060_.m_78933_();
      List<LootPoolEntry> list = Lists.newArrayList();
      MutableInt mutableint = new MutableInt();

      for(LootPoolEntryContainer lootpoolentrycontainer : this.f_79023_) {
         lootpoolentrycontainer.m_6562_(p_79060_, (p_79048_) -> {
            int k = p_79048_.m_7067_(p_79060_.m_78945_());
            if (k > 0) {
               list.add(p_79048_);
               mutableint.add(k);
            }

         });
      }

      int i = list.size();
      if (mutableint.intValue() != 0 && i != 0) {
         if (i == 1) {
            list.get(0).m_6941_(p_79059_, p_79060_);
         } else {
            int j = random.nextInt(mutableint.intValue());

            for(LootPoolEntry lootpoolentry : list) {
               j -= lootpoolentry.m_7067_(p_79060_.m_78945_());
               if (j < 0) {
                  lootpoolentry.m_6941_(p_79059_, p_79060_);
                  return;
               }
            }

         }
      }
   }

   public void m_79053_(Consumer<ItemStack> p_79054_, LootContext p_79055_) {
      if (this.f_79025_.test(p_79055_)) {
         Consumer<ItemStack> consumer = LootItemFunction.m_80724_(this.f_79027_, p_79054_, p_79055_);
         int i = this.f_79028_.m_142683_(p_79055_) + Mth.m_14143_(this.f_79029_.m_142688_(p_79055_) * p_79055_.m_78945_());

         for(int j = 0; j < i; ++j) {
            this.m_79058_(consumer, p_79055_);
         }

      }
   }

   public void m_79051_(ValidationContext p_79052_) {
      for(int i = 0; i < this.f_79024_.length; ++i) {
         this.f_79024_[i].m_6169_(p_79052_.m_79365_(".condition[" + i + "]"));
      }

      for(int j = 0; j < this.f_79026_.length; ++j) {
         this.f_79026_[j].m_6169_(p_79052_.m_79365_(".functions[" + j + "]"));
      }

      for(int k = 0; k < this.f_79023_.length; ++k) {
         this.f_79023_[k].m_6165_(p_79052_.m_79365_(".entries[" + k + "]"));
      }

      this.f_79028_.m_6169_(p_79052_.m_79365_(".rolls"));
      this.f_79029_.m_6169_(p_79052_.m_79365_(".bonusRolls"));
   }
   //======================== FORGE START =============================================
   private boolean isFrozen = false;
   public void freeze() { this.isFrozen = true; }
   public boolean isFrozen(){ return this.isFrozen; }
   private void checkFrozen() {
      if (this.isFrozen())
         throw new RuntimeException("Attempted to modify LootPool after being frozen!");
   }
   public String getName(){ return this.name; }
   public NumberProvider getRolls()      { return this.f_79028_; }
   public NumberProvider getBonusRolls() { return this.f_79029_; }
   public void setRolls     (NumberProvider v){ checkFrozen(); this.f_79028_ = v; }
   public void setBonusRolls(NumberProvider v){ checkFrozen(); this.f_79029_ = v; }
   //======================== FORGE END ===============================================

   public static LootPool.Builder m_79043_() {
      return new LootPool.Builder();
   }

   public static class Builder implements FunctionUserBuilder<LootPool.Builder>, ConditionUserBuilder<LootPool.Builder> {
      private final List<LootPoolEntryContainer> f_79067_ = Lists.newArrayList();
      private final List<LootItemCondition> f_79068_ = Lists.newArrayList();
      private final List<LootItemFunction> f_79069_ = Lists.newArrayList();
      private NumberProvider f_79070_ = ConstantValue.m_165692_(1.0F);
      private NumberProvider f_79071_ = ConstantValue.m_165692_(0.0F);
      private String name;

      public LootPool.Builder m_165133_(NumberProvider p_165134_) {
         this.f_79070_ = p_165134_;
         return this;
      }

      public LootPool.Builder m_5476_() {
         return this;
      }

      public LootPool.Builder m_165135_(NumberProvider p_165136_) {
         this.f_79071_ = p_165136_;
         return this;
      }

      public LootPool.Builder m_79076_(LootPoolEntryContainer.Builder<?> p_79077_) {
         this.f_79067_.add(p_79077_.m_7512_());
         return this;
      }

      public LootPool.Builder m_6509_(LootItemCondition.Builder p_79081_) {
         this.f_79068_.add(p_79081_.m_6409_());
         return this;
      }

      public LootPool.Builder m_5577_(LootItemFunction.Builder p_79079_) {
         this.f_79069_.add(p_79079_.m_7453_());
         return this;
      }

      public LootPool.Builder name(String name) {
         this.name = name;
         return this;
      }

      @Deprecated(forRemoval = true, since = "1.17.1") // FORGE: Use #setBonusRolls instead with UniformGenerator
      public LootPool.Builder bonusRolls(float min, float max) {
         this.f_79071_ = net.minecraft.world.level.storage.loot.providers.number.UniformGenerator.m_165780_(min, max);
         return this;
      }

      public LootPool m_79082_() {
         if (this.f_79070_ == null) {
            throw new IllegalArgumentException("Rolls not set");
         } else {
            return new LootPool(this.f_79067_.toArray(new LootPoolEntryContainer[0]), this.f_79068_.toArray(new LootItemCondition[0]), this.f_79069_.toArray(new LootItemFunction[0]), this.f_79070_, this.f_79071_, name);
         }
      }
   }

   public static class Serializer implements JsonDeserializer<LootPool>, JsonSerializer<LootPool> {
      public LootPool deserialize(JsonElement p_79090_, Type p_79091_, JsonDeserializationContext p_79092_) throws JsonParseException {
         JsonObject jsonobject = GsonHelper.m_13918_(p_79090_, "loot pool");
         LootPoolEntryContainer[] alootpoolentrycontainer = GsonHelper.m_13836_(jsonobject, "entries", p_79092_, LootPoolEntryContainer[].class);
         LootItemCondition[] alootitemcondition = GsonHelper.m_13845_(jsonobject, "conditions", new LootItemCondition[0], p_79092_, LootItemCondition[].class);
         LootItemFunction[] alootitemfunction = GsonHelper.m_13845_(jsonobject, "functions", new LootItemFunction[0], p_79092_, LootItemFunction[].class);
         NumberProvider numberprovider = GsonHelper.m_13836_(jsonobject, "rolls", p_79092_, NumberProvider.class);
         NumberProvider numberprovider1 = GsonHelper.m_13845_(jsonobject, "bonus_rolls", ConstantValue.m_165692_(0.0F), p_79092_, NumberProvider.class);
         return new LootPool(alootpoolentrycontainer, alootitemcondition, alootitemfunction, numberprovider, numberprovider1, net.minecraftforge.common.ForgeHooks.readPoolName(jsonobject));
      }

      public JsonElement serialize(LootPool p_79094_, Type p_79095_, JsonSerializationContext p_79096_) {
         JsonObject jsonobject = new JsonObject();
         if (p_79094_.name != null && !p_79094_.name.startsWith("custom#"))
            jsonobject.add("name", p_79096_.serialize(p_79094_.name));
         jsonobject.add("rolls", p_79096_.serialize(p_79094_.f_79028_));
         jsonobject.add("bonus_rolls", p_79096_.serialize(p_79094_.f_79029_));
         jsonobject.add("entries", p_79096_.serialize(p_79094_.f_79023_));
         if (!ArrayUtils.isEmpty((Object[])p_79094_.f_79024_)) {
            jsonobject.add("conditions", p_79096_.serialize(p_79094_.f_79024_));
         }

         if (!ArrayUtils.isEmpty((Object[])p_79094_.f_79026_)) {
            jsonobject.add("functions", p_79096_.serialize(p_79094_.f_79026_));
         }

         return jsonobject;
      }
   }
}
