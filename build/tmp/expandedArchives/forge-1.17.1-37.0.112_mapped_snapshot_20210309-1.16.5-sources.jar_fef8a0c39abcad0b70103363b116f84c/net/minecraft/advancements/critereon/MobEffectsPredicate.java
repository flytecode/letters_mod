package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectsPredicate {
   public static final MobEffectsPredicate f_56547_ = new MobEffectsPredicate(Collections.emptyMap());
   private final Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> f_56548_;

   public MobEffectsPredicate(Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> p_56551_) {
      this.f_56548_ = p_56551_;
   }

   public static MobEffectsPredicate m_56552_() {
      return new MobEffectsPredicate(Maps.newLinkedHashMap());
   }

   public MobEffectsPredicate m_56553_(MobEffect p_56554_) {
      this.f_56548_.put(p_56554_, new MobEffectsPredicate.MobEffectInstancePredicate());
      return this;
   }

   public MobEffectsPredicate m_154977_(MobEffect p_154978_, MobEffectsPredicate.MobEffectInstancePredicate p_154979_) {
      this.f_56548_.put(p_154978_, p_154979_);
      return this;
   }

   public boolean m_56555_(Entity p_56556_) {
      if (this == f_56547_) {
         return true;
      } else {
         return p_56556_ instanceof LivingEntity ? this.m_56561_(((LivingEntity)p_56556_).m_21221_()) : false;
      }
   }

   public boolean m_56557_(LivingEntity p_56558_) {
      return this == f_56547_ ? true : this.m_56561_(p_56558_.m_21221_());
   }

   public boolean m_56561_(Map<MobEffect, MobEffectInstance> p_56562_) {
      if (this == f_56547_) {
         return true;
      } else {
         for(Entry<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> entry : this.f_56548_.entrySet()) {
            MobEffectInstance mobeffectinstance = p_56562_.get(entry.getKey());
            if (!entry.getValue().m_56577_(mobeffectinstance)) {
               return false;
            }
         }

         return true;
      }
   }

   public static MobEffectsPredicate m_56559_(@Nullable JsonElement p_56560_) {
      if (p_56560_ != null && !p_56560_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_56560_, "effects");
         Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> map = Maps.newLinkedHashMap();

         for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
            ResourceLocation resourcelocation = new ResourceLocation(entry.getKey());
            MobEffect mobeffect = Registry.f_122823_.m_6612_(resourcelocation).orElseThrow(() -> {
               return new JsonSyntaxException("Unknown effect '" + resourcelocation + "'");
            });
            MobEffectsPredicate.MobEffectInstancePredicate mobeffectspredicate$mobeffectinstancepredicate = MobEffectsPredicate.MobEffectInstancePredicate.m_56579_(GsonHelper.m_13918_(entry.getValue(), entry.getKey()));
            map.put(mobeffect, mobeffectspredicate$mobeffectinstancepredicate);
         }

         return new MobEffectsPredicate(map);
      } else {
         return f_56547_;
      }
   }

   public JsonElement m_56565_() {
      if (this == f_56547_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();

         for(Entry<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> entry : this.f_56548_.entrySet()) {
            jsonobject.add(Registry.f_122823_.m_7981_(entry.getKey()).toString(), entry.getValue().m_56576_());
         }

         return jsonobject;
      }
   }

   public static class MobEffectInstancePredicate {
      private final MinMaxBounds.Ints f_56566_;
      private final MinMaxBounds.Ints f_56567_;
      @Nullable
      private final Boolean f_56568_;
      @Nullable
      private final Boolean f_56569_;

      public MobEffectInstancePredicate(MinMaxBounds.Ints p_56572_, MinMaxBounds.Ints p_56573_, @Nullable Boolean p_56574_, @Nullable Boolean p_56575_) {
         this.f_56566_ = p_56572_;
         this.f_56567_ = p_56573_;
         this.f_56568_ = p_56574_;
         this.f_56569_ = p_56575_;
      }

      public MobEffectInstancePredicate() {
         this(MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, (Boolean)null, (Boolean)null);
      }

      public boolean m_56577_(@Nullable MobEffectInstance p_56578_) {
         if (p_56578_ == null) {
            return false;
         } else if (!this.f_56566_.m_55390_(p_56578_.m_19564_())) {
            return false;
         } else if (!this.f_56567_.m_55390_(p_56578_.m_19557_())) {
            return false;
         } else if (this.f_56568_ != null && this.f_56568_ != p_56578_.m_19571_()) {
            return false;
         } else {
            return this.f_56569_ == null || this.f_56569_ == p_56578_.m_19572_();
         }
      }

      public JsonElement m_56576_() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("amplifier", this.f_56566_.m_55328_());
         jsonobject.add("duration", this.f_56567_.m_55328_());
         jsonobject.addProperty("ambient", this.f_56568_);
         jsonobject.addProperty("visible", this.f_56569_);
         return jsonobject;
      }

      public static MobEffectsPredicate.MobEffectInstancePredicate m_56579_(JsonObject p_56580_) {
         MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(p_56580_.get("amplifier"));
         MinMaxBounds.Ints minmaxbounds$ints1 = MinMaxBounds.Ints.m_55373_(p_56580_.get("duration"));
         Boolean obool = p_56580_.has("ambient") ? GsonHelper.m_13912_(p_56580_, "ambient") : null;
         Boolean obool1 = p_56580_.has("visible") ? GsonHelper.m_13912_(p_56580_, "visible") : null;
         return new MobEffectsPredicate.MobEffectInstancePredicate(minmaxbounds$ints, minmaxbounds$ints1, obool, obool1);
      }
   }
}