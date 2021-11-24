package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;

public class DamagePredicate {
   public static final DamagePredicate f_24902_ = DamagePredicate.Builder.m_24931_().m_24936_();
   private final MinMaxBounds.Doubles f_24903_;
   private final MinMaxBounds.Doubles f_24904_;
   private final EntityPredicate f_24905_;
   private final Boolean f_24906_;
   private final DamageSourcePredicate f_24907_;

   public DamagePredicate() {
      this.f_24903_ = MinMaxBounds.Doubles.f_154779_;
      this.f_24904_ = MinMaxBounds.Doubles.f_154779_;
      this.f_24905_ = EntityPredicate.f_36550_;
      this.f_24906_ = null;
      this.f_24907_ = DamageSourcePredicate.f_25420_;
   }

   public DamagePredicate(MinMaxBounds.Doubles p_24911_, MinMaxBounds.Doubles p_24912_, EntityPredicate p_24913_, @Nullable Boolean p_24914_, DamageSourcePredicate p_24915_) {
      this.f_24903_ = p_24911_;
      this.f_24904_ = p_24912_;
      this.f_24905_ = p_24913_;
      this.f_24906_ = p_24914_;
      this.f_24907_ = p_24915_;
   }

   public boolean m_24917_(ServerPlayer p_24918_, DamageSource p_24919_, float p_24920_, float p_24921_, boolean p_24922_) {
      if (this == f_24902_) {
         return true;
      } else if (!this.f_24903_.m_154810_((double)p_24920_)) {
         return false;
      } else if (!this.f_24904_.m_154810_((double)p_24921_)) {
         return false;
      } else if (!this.f_24905_.m_36611_(p_24918_, p_24919_.m_7639_())) {
         return false;
      } else if (this.f_24906_ != null && this.f_24906_ != p_24922_) {
         return false;
      } else {
         return this.f_24907_.m_25448_(p_24918_, p_24919_);
      }
   }

   public static DamagePredicate m_24923_(@Nullable JsonElement p_24924_) {
      if (p_24924_ != null && !p_24924_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_24924_, "damage");
         MinMaxBounds.Doubles minmaxbounds$doubles = MinMaxBounds.Doubles.m_154791_(jsonobject.get("dealt"));
         MinMaxBounds.Doubles minmaxbounds$doubles1 = MinMaxBounds.Doubles.m_154791_(jsonobject.get("taken"));
         Boolean obool = jsonobject.has("blocked") ? GsonHelper.m_13912_(jsonobject, "blocked") : null;
         EntityPredicate entitypredicate = EntityPredicate.m_36614_(jsonobject.get("source_entity"));
         DamageSourcePredicate damagesourcepredicate = DamageSourcePredicate.m_25451_(jsonobject.get("type"));
         return new DamagePredicate(minmaxbounds$doubles, minmaxbounds$doubles1, entitypredicate, obool, damagesourcepredicate);
      } else {
         return f_24902_;
      }
   }

   public JsonElement m_24916_() {
      if (this == f_24902_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("dealt", this.f_24903_.m_55328_());
         jsonobject.add("taken", this.f_24904_.m_55328_());
         jsonobject.add("source_entity", this.f_24905_.m_36606_());
         jsonobject.add("type", this.f_24907_.m_25443_());
         if (this.f_24906_ != null) {
            jsonobject.addProperty("blocked", this.f_24906_);
         }

         return jsonobject;
      }
   }

   public static class Builder {
      private MinMaxBounds.Doubles f_24925_ = MinMaxBounds.Doubles.f_154779_;
      private MinMaxBounds.Doubles f_24926_ = MinMaxBounds.Doubles.f_154779_;
      private EntityPredicate f_24927_ = EntityPredicate.f_36550_;
      private Boolean f_24928_;
      private DamageSourcePredicate f_24929_ = DamageSourcePredicate.f_25420_;

      public static DamagePredicate.Builder m_24931_() {
         return new DamagePredicate.Builder();
      }

      public DamagePredicate.Builder m_148145_(MinMaxBounds.Doubles p_148146_) {
         this.f_24925_ = p_148146_;
         return this;
      }

      public DamagePredicate.Builder m_148147_(MinMaxBounds.Doubles p_148148_) {
         this.f_24926_ = p_148148_;
         return this;
      }

      public DamagePredicate.Builder m_148143_(EntityPredicate p_148144_) {
         this.f_24927_ = p_148144_;
         return this;
      }

      public DamagePredicate.Builder m_24934_(Boolean p_24935_) {
         this.f_24928_ = p_24935_;
         return this;
      }

      public DamagePredicate.Builder m_148141_(DamageSourcePredicate p_148142_) {
         this.f_24929_ = p_148142_;
         return this;
      }

      public DamagePredicate.Builder m_24932_(DamageSourcePredicate.Builder p_24933_) {
         this.f_24929_ = p_24933_.m_25476_();
         return this;
      }

      public DamagePredicate m_24936_() {
         return new DamagePredicate(this.f_24925_, this.f_24926_, this.f_24927_, this.f_24928_, this.f_24929_);
      }
   }
}