package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class TimeCheck implements LootItemCondition {
   @Nullable
   final Long f_82023_;
   final IntRange f_82024_;

   TimeCheck(@Nullable Long p_165507_, IntRange p_165508_) {
      this.f_82023_ = p_165507_;
      this.f_82024_ = p_165508_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81826_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return this.f_82024_.m_165008_();
   }

   public boolean test(LootContext p_82033_) {
      ServerLevel serverlevel = p_82033_.m_78952_();
      long i = serverlevel.m_46468_();
      if (this.f_82023_ != null) {
         i %= this.f_82023_;
      }

      return this.f_82024_.m_165028_(p_82033_, (int)i);
   }

   public static TimeCheck.Builder m_165509_(IntRange p_165510_) {
      return new TimeCheck.Builder(p_165510_);
   }

   public static class Builder implements LootItemCondition.Builder {
      @Nullable
      private Long f_165512_;
      private final IntRange f_165513_;

      public Builder(IntRange p_165515_) {
         this.f_165513_ = p_165515_;
      }

      public TimeCheck.Builder m_165516_(long p_165517_) {
         this.f_165512_ = p_165517_;
         return this;
      }

      public TimeCheck m_6409_() {
         return new TimeCheck(this.f_165512_, this.f_165513_);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<TimeCheck> {
      public void m_6170_(JsonObject p_82046_, TimeCheck p_82047_, JsonSerializationContext p_82048_) {
         p_82046_.addProperty("period", p_82047_.f_82023_);
         p_82046_.add("value", p_82048_.serialize(p_82047_.f_82024_));
      }

      public TimeCheck m_7561_(JsonObject p_82054_, JsonDeserializationContext p_82055_) {
         Long olong = p_82054_.has("period") ? GsonHelper.m_13921_(p_82054_, "period") : null;
         IntRange intrange = GsonHelper.m_13836_(p_82054_, "value", p_82055_, IntRange.class);
         return new TimeCheck(olong, intrange);
      }
   }
}