package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public final class BinomialDistributionGenerator implements NumberProvider {
   final NumberProvider f_165653_;
   final NumberProvider f_165654_;

   BinomialDistributionGenerator(NumberProvider p_165656_, NumberProvider p_165657_) {
      this.f_165653_ = p_165656_;
      this.f_165654_ = p_165657_;
   }

   public LootNumberProviderType m_142587_() {
      return NumberProviders.f_165733_;
   }

   public int m_142683_(LootContext p_165663_) {
      int i = this.f_165653_.m_142683_(p_165663_);
      float f = this.f_165654_.m_142688_(p_165663_);
      Random random = p_165663_.m_78933_();
      int j = 0;

      for(int k = 0; k < i; ++k) {
         if (random.nextFloat() < f) {
            ++j;
         }
      }

      return j;
   }

   public float m_142688_(LootContext p_165666_) {
      return (float)this.m_142683_(p_165666_);
   }

   public static BinomialDistributionGenerator m_165659_(int p_165660_, float p_165661_) {
      return new BinomialDistributionGenerator(ConstantValue.m_165692_((float)p_165660_), ConstantValue.m_165692_(p_165661_));
   }

   public Set<LootContextParam<?>> m_6231_() {
      return Sets.union(this.f_165653_.m_6231_(), this.f_165654_.m_6231_());
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BinomialDistributionGenerator> {
      public BinomialDistributionGenerator m_7561_(JsonObject p_165680_, JsonDeserializationContext p_165681_) {
         NumberProvider numberprovider = GsonHelper.m_13836_(p_165680_, "n", p_165681_, NumberProvider.class);
         NumberProvider numberprovider1 = GsonHelper.m_13836_(p_165680_, "p", p_165681_, NumberProvider.class);
         return new BinomialDistributionGenerator(numberprovider, numberprovider1);
      }

      public void m_6170_(JsonObject p_165672_, BinomialDistributionGenerator p_165673_, JsonSerializationContext p_165674_) {
         p_165672_.add("n", p_165674_.serialize(p_165673_.f_165653_));
         p_165672_.add("p", p_165674_.serialize(p_165673_.f_165654_));
      }
   }
}