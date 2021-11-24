package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ApplyBonusCount extends LootItemConditionalFunction {
   static final Map<ResourceLocation, ApplyBonusCount.FormulaDeserializer> f_79898_ = Maps.newHashMap();
   final Enchantment f_79899_;
   final ApplyBonusCount.Formula f_79900_;

   ApplyBonusCount(LootItemCondition[] p_79903_, Enchantment p_79904_, ApplyBonusCount.Formula p_79905_) {
      super(p_79903_);
      this.f_79899_ = p_79904_;
      this.f_79900_ = p_79905_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80750_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81463_);
   }

   public ItemStack m_7372_(ItemStack p_79913_, LootContext p_79914_) {
      ItemStack itemstack = p_79914_.m_78953_(LootContextParams.f_81463_);
      if (itemstack != null) {
         int i = EnchantmentHelper.m_44843_(this.f_79899_, itemstack);
         int j = this.f_79900_.m_6432_(p_79914_.m_78933_(), p_79913_.m_41613_(), i);
         p_79913_.m_41764_(j);
      }

      return p_79913_;
   }

   public static LootItemConditionalFunction.Builder<?> m_79917_(Enchantment p_79918_, float p_79919_, int p_79920_) {
      return m_80683_((p_79928_) -> {
         return new ApplyBonusCount(p_79928_, p_79918_, new ApplyBonusCount.BinomialWithBonusCount(p_79920_, p_79919_));
      });
   }

   public static LootItemConditionalFunction.Builder<?> m_79915_(Enchantment p_79916_) {
      return m_80683_((p_79943_) -> {
         return new ApplyBonusCount(p_79943_, p_79916_, new ApplyBonusCount.OreDrops());
      });
   }

   public static LootItemConditionalFunction.Builder<?> m_79939_(Enchantment p_79940_) {
      return m_80683_((p_79935_) -> {
         return new ApplyBonusCount(p_79935_, p_79940_, new ApplyBonusCount.UniformBonusCount(1));
      });
   }

   public static LootItemConditionalFunction.Builder<?> m_79921_(Enchantment p_79922_, int p_79923_) {
      return m_80683_((p_79932_) -> {
         return new ApplyBonusCount(p_79932_, p_79922_, new ApplyBonusCount.UniformBonusCount(p_79923_));
      });
   }

   static {
      f_79898_.put(ApplyBonusCount.BinomialWithBonusCount.f_79947_, ApplyBonusCount.BinomialWithBonusCount::m_79955_);
      f_79898_.put(ApplyBonusCount.OreDrops.f_79973_, ApplyBonusCount.OreDrops::m_79979_);
      f_79898_.put(ApplyBonusCount.UniformBonusCount.f_80012_, ApplyBonusCount.UniformBonusCount::m_80018_);
   }

   static final class BinomialWithBonusCount implements ApplyBonusCount.Formula {
      public static final ResourceLocation f_79947_ = new ResourceLocation("binomial_with_bonus_count");
      private final int f_79948_;
      private final float f_79949_;

      public BinomialWithBonusCount(int p_79952_, float p_79953_) {
         this.f_79948_ = p_79952_;
         this.f_79949_ = p_79953_;
      }

      public int m_6432_(Random p_79962_, int p_79963_, int p_79964_) {
         for(int i = 0; i < p_79964_ + this.f_79948_; ++i) {
            if (p_79962_.nextFloat() < this.f_79949_) {
               ++p_79963_;
            }
         }

         return p_79963_;
      }

      public void m_6417_(JsonObject p_79959_, JsonSerializationContext p_79960_) {
         p_79959_.addProperty("extra", this.f_79948_);
         p_79959_.addProperty("probability", this.f_79949_);
      }

      public static ApplyBonusCount.Formula m_79955_(JsonObject p_79956_, JsonDeserializationContext p_79957_) {
         int i = GsonHelper.m_13927_(p_79956_, "extra");
         float f = GsonHelper.m_13915_(p_79956_, "probability");
         return new ApplyBonusCount.BinomialWithBonusCount(i, f);
      }

      public ResourceLocation m_5713_() {
         return f_79947_;
      }
   }

   interface Formula {
      int m_6432_(Random p_79967_, int p_79968_, int p_79969_);

      void m_6417_(JsonObject p_79965_, JsonSerializationContext p_79966_);

      ResourceLocation m_5713_();
   }

   interface FormulaDeserializer {
      ApplyBonusCount.Formula m_79970_(JsonObject p_79971_, JsonDeserializationContext p_79972_);
   }

   static final class OreDrops implements ApplyBonusCount.Formula {
      public static final ResourceLocation f_79973_ = new ResourceLocation("ore_drops");

      public int m_6432_(Random p_79986_, int p_79987_, int p_79988_) {
         if (p_79988_ > 0) {
            int i = p_79986_.nextInt(p_79988_ + 2) - 1;
            if (i < 0) {
               i = 0;
            }

            return p_79987_ * (i + 1);
         } else {
            return p_79987_;
         }
      }

      public void m_6417_(JsonObject p_79983_, JsonSerializationContext p_79984_) {
      }

      public static ApplyBonusCount.Formula m_79979_(JsonObject p_79980_, JsonDeserializationContext p_79981_) {
         return new ApplyBonusCount.OreDrops();
      }

      public ResourceLocation m_5713_() {
         return f_79973_;
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<ApplyBonusCount> {
      public void m_6170_(JsonObject p_79995_, ApplyBonusCount p_79996_, JsonSerializationContext p_79997_) {
         super.m_6170_(p_79995_, p_79996_, p_79997_);
         p_79995_.addProperty("enchantment", Registry.f_122825_.m_7981_(p_79996_.f_79899_).toString());
         p_79995_.addProperty("formula", p_79996_.f_79900_.m_5713_().toString());
         JsonObject jsonobject = new JsonObject();
         p_79996_.f_79900_.m_6417_(jsonobject, p_79997_);
         if (jsonobject.size() > 0) {
            p_79995_.add("parameters", jsonobject);
         }

      }

      public ApplyBonusCount m_6821_(JsonObject p_79991_, JsonDeserializationContext p_79992_, LootItemCondition[] p_79993_) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_79991_, "enchantment"));
         Enchantment enchantment = Registry.f_122825_.m_6612_(resourcelocation).orElseThrow(() -> {
            return new JsonParseException("Invalid enchantment id: " + resourcelocation);
         });
         ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.m_13906_(p_79991_, "formula"));
         ApplyBonusCount.FormulaDeserializer applybonuscount$formuladeserializer = ApplyBonusCount.f_79898_.get(resourcelocation1);
         if (applybonuscount$formuladeserializer == null) {
            throw new JsonParseException("Invalid formula id: " + resourcelocation1);
         } else {
            ApplyBonusCount.Formula applybonuscount$formula;
            if (p_79991_.has("parameters")) {
               applybonuscount$formula = applybonuscount$formuladeserializer.m_79970_(GsonHelper.m_13930_(p_79991_, "parameters"), p_79992_);
            } else {
               applybonuscount$formula = applybonuscount$formuladeserializer.m_79970_(new JsonObject(), p_79992_);
            }

            return new ApplyBonusCount(p_79993_, enchantment, applybonuscount$formula);
         }
      }
   }

   static final class UniformBonusCount implements ApplyBonusCount.Formula {
      public static final ResourceLocation f_80012_ = new ResourceLocation("uniform_bonus_count");
      private final int f_80013_;

      public UniformBonusCount(int p_80016_) {
         this.f_80013_ = p_80016_;
      }

      public int m_6432_(Random p_80025_, int p_80026_, int p_80027_) {
         return p_80026_ + p_80025_.nextInt(this.f_80013_ * p_80027_ + 1);
      }

      public void m_6417_(JsonObject p_80022_, JsonSerializationContext p_80023_) {
         p_80022_.addProperty("bonusMultiplier", this.f_80013_);
      }

      public static ApplyBonusCount.Formula m_80018_(JsonObject p_80019_, JsonDeserializationContext p_80020_) {
         int i = GsonHelper.m_13927_(p_80019_, "bonusMultiplier");
         return new ApplyBonusCount.UniformBonusCount(i);
      }

      public ResourceLocation m_5713_() {
         return f_80012_;
      }
   }
}