package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;

public class WeatherCheck implements LootItemCondition {
   @Nullable
   final Boolean f_82056_;
   @Nullable
   final Boolean f_82057_;

   WeatherCheck(@Nullable Boolean p_82059_, @Nullable Boolean p_82060_) {
      this.f_82056_ = p_82059_;
      this.f_82057_ = p_82060_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81824_;
   }

   public boolean test(LootContext p_82066_) {
      ServerLevel serverlevel = p_82066_.m_78952_();
      if (this.f_82056_ != null && this.f_82056_ != serverlevel.m_46471_()) {
         return false;
      } else {
         return this.f_82057_ == null || this.f_82057_ == serverlevel.m_46470_();
      }
   }

   public static WeatherCheck.Builder m_165552_() {
      return new WeatherCheck.Builder();
   }

   public static class Builder implements LootItemCondition.Builder {
      @Nullable
      private Boolean f_165553_;
      @Nullable
      private Boolean f_165554_;

      public WeatherCheck.Builder m_165556_(@Nullable Boolean p_165557_) {
         this.f_165553_ = p_165557_;
         return this;
      }

      public WeatherCheck.Builder m_165559_(@Nullable Boolean p_165560_) {
         this.f_165554_ = p_165560_;
         return this;
      }

      public WeatherCheck m_6409_() {
         return new WeatherCheck(this.f_165553_, this.f_165554_);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<WeatherCheck> {
      public void m_6170_(JsonObject p_82079_, WeatherCheck p_82080_, JsonSerializationContext p_82081_) {
         p_82079_.addProperty("raining", p_82080_.f_82056_);
         p_82079_.addProperty("thundering", p_82080_.f_82057_);
      }

      public WeatherCheck m_7561_(JsonObject p_82087_, JsonDeserializationContext p_82088_) {
         Boolean obool = p_82087_.has("raining") ? GsonHelper.m_13912_(p_82087_, "raining") : null;
         Boolean obool1 = p_82087_.has("thundering") ? GsonHelper.m_13912_(p_82087_, "thundering") : null;
         return new WeatherCheck(obool, obool1);
      }
   }
}