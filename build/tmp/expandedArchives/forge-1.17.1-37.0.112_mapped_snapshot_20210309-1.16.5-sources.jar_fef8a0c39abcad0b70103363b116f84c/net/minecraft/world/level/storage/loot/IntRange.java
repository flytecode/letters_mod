package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class IntRange {
   @Nullable
   final NumberProvider f_165001_;
   @Nullable
   final NumberProvider f_165002_;
   private final IntRange.IntLimiter f_165003_;
   private final IntRange.IntChecker f_165004_;

   public Set<LootContextParam<?>> m_165008_() {
      Builder<LootContextParam<?>> builder = ImmutableSet.builder();
      if (this.f_165001_ != null) {
         builder.addAll(this.f_165001_.m_6231_());
      }

      if (this.f_165002_ != null) {
         builder.addAll(this.f_165002_.m_6231_());
      }

      return builder.build();
   }

   IntRange(@Nullable NumberProvider p_165006_, @Nullable NumberProvider p_165007_) {
      this.f_165001_ = p_165006_;
      this.f_165002_ = p_165007_;
      if (p_165006_ == null) {
         if (p_165007_ == null) {
            this.f_165003_ = (p_165050_, p_165051_) -> {
               return p_165051_;
            };
            this.f_165004_ = (p_165043_, p_165044_) -> {
               return true;
            };
         } else {
            this.f_165003_ = (p_165054_, p_165055_) -> {
               return Math.min(p_165007_.m_142683_(p_165054_), p_165055_);
            };
            this.f_165004_ = (p_165047_, p_165048_) -> {
               return p_165048_ <= p_165007_.m_142683_(p_165047_);
            };
         }
      } else if (p_165007_ == null) {
         this.f_165003_ = (p_165033_, p_165034_) -> {
            return Math.max(p_165006_.m_142683_(p_165033_), p_165034_);
         };
         this.f_165004_ = (p_165019_, p_165020_) -> {
            return p_165020_ >= p_165006_.m_142683_(p_165019_);
         };
      } else {
         this.f_165003_ = (p_165038_, p_165039_) -> {
            return Mth.m_14045_(p_165039_, p_165006_.m_142683_(p_165038_), p_165007_.m_142683_(p_165038_));
         };
         this.f_165004_ = (p_165024_, p_165025_) -> {
            return p_165025_ >= p_165006_.m_142683_(p_165024_) && p_165025_ <= p_165007_.m_142683_(p_165024_);
         };
      }

   }

   public static IntRange m_165009_(int p_165010_) {
      ConstantValue constantvalue = ConstantValue.m_165692_((float)p_165010_);
      return new IntRange(constantvalue, constantvalue);
   }

   public static IntRange m_165011_(int p_165012_, int p_165013_) {
      return new IntRange(ConstantValue.m_165692_((float)p_165012_), ConstantValue.m_165692_((float)p_165013_));
   }

   public static IntRange m_165026_(int p_165027_) {
      return new IntRange(ConstantValue.m_165692_((float)p_165027_), (NumberProvider)null);
   }

   public static IntRange m_165040_(int p_165041_) {
      return new IntRange((NumberProvider)null, ConstantValue.m_165692_((float)p_165041_));
   }

   public int m_165014_(LootContext p_165015_, int p_165016_) {
      return this.f_165003_.m_165059_(p_165015_, p_165016_);
   }

   public boolean m_165028_(LootContext p_165029_, int p_165030_) {
      return this.f_165004_.m_165056_(p_165029_, p_165030_);
   }

   @FunctionalInterface
   interface IntChecker {
      boolean m_165056_(LootContext p_165057_, int p_165058_);
   }

   @FunctionalInterface
   interface IntLimiter {
      int m_165059_(LootContext p_165060_, int p_165061_);
   }

   public static class Serializer implements JsonDeserializer<IntRange>, JsonSerializer<IntRange> {
      public IntRange deserialize(JsonElement p_165064_, Type p_165065_, JsonDeserializationContext p_165066_) {
         if (p_165064_.isJsonPrimitive()) {
            return IntRange.m_165009_(p_165064_.getAsInt());
         } else {
            JsonObject jsonobject = GsonHelper.m_13918_(p_165064_, "value");
            NumberProvider numberprovider = jsonobject.has("min") ? GsonHelper.m_13836_(jsonobject, "min", p_165066_, NumberProvider.class) : null;
            NumberProvider numberprovider1 = jsonobject.has("max") ? GsonHelper.m_13836_(jsonobject, "max", p_165066_, NumberProvider.class) : null;
            return new IntRange(numberprovider, numberprovider1);
         }
      }

      public JsonElement serialize(IntRange p_165068_, Type p_165069_, JsonSerializationContext p_165070_) {
         JsonObject jsonobject = new JsonObject();
         if (Objects.equals(p_165068_.f_165002_, p_165068_.f_165001_)) {
            return p_165070_.serialize(p_165068_.f_165001_);
         } else {
            if (p_165068_.f_165002_ != null) {
               jsonobject.add("max", p_165070_.serialize(p_165068_.f_165002_));
            }

            if (p_165068_.f_165001_ != null) {
               jsonobject.add("min", p_165070_.serialize(p_165068_.f_165001_));
            }

            return jsonobject;
         }
      }
   }
}