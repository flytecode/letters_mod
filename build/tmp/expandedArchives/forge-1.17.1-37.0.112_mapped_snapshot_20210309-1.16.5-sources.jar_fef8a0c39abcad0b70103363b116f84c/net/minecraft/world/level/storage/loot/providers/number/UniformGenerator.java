package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class UniformGenerator implements NumberProvider {
   final NumberProvider f_165774_;
   final NumberProvider f_165775_;

   UniformGenerator(NumberProvider p_165777_, NumberProvider p_165778_) {
      this.f_165774_ = p_165777_;
      this.f_165775_ = p_165778_;
   }

   public LootNumberProviderType m_142587_() {
      return NumberProviders.f_165732_;
   }

   public static UniformGenerator m_165780_(float p_165781_, float p_165782_) {
      return new UniformGenerator(ConstantValue.m_165692_(p_165781_), ConstantValue.m_165692_(p_165782_));
   }

   public int m_142683_(LootContext p_165784_) {
      return Mth.m_14072_(p_165784_.m_78933_(), this.f_165774_.m_142683_(p_165784_), this.f_165775_.m_142683_(p_165784_));
   }

   public float m_142688_(LootContext p_165787_) {
      return Mth.m_14068_(p_165787_.m_78933_(), this.f_165774_.m_142688_(p_165787_), this.f_165775_.m_142688_(p_165787_));
   }

   public Set<LootContextParam<?>> m_6231_() {
      return Sets.union(this.f_165774_.m_6231_(), this.f_165775_.m_6231_());
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<UniformGenerator> {
      public UniformGenerator m_7561_(JsonObject p_165801_, JsonDeserializationContext p_165802_) {
         NumberProvider numberprovider = GsonHelper.m_13836_(p_165801_, "min", p_165802_, NumberProvider.class);
         NumberProvider numberprovider1 = GsonHelper.m_13836_(p_165801_, "max", p_165802_, NumberProvider.class);
         return new UniformGenerator(numberprovider, numberprovider1);
      }

      public void m_6170_(JsonObject p_165793_, UniformGenerator p_165794_, JsonSerializationContext p_165795_) {
         p_165793_.add("min", p_165795_.serialize(p_165794_.f_165774_));
         p_165793_.add("max", p_165795_.serialize(p_165794_.f_165775_));
      }
   }
}