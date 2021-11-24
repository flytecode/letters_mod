package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;

public class DamageSourcePredicate {
   public static final DamageSourcePredicate f_25420_ = DamageSourcePredicate.Builder.m_25471_().m_25476_();
   private final Boolean f_25421_;
   private final Boolean f_25422_;
   private final Boolean f_25423_;
   private final Boolean f_25424_;
   private final Boolean f_25425_;
   private final Boolean f_25426_;
   private final Boolean f_25427_;
   private final Boolean f_25428_;
   private final EntityPredicate f_25429_;
   private final EntityPredicate f_25430_;

   public DamageSourcePredicate(@Nullable Boolean p_25433_, @Nullable Boolean p_25434_, @Nullable Boolean p_25435_, @Nullable Boolean p_25436_, @Nullable Boolean p_25437_, @Nullable Boolean p_25438_, @Nullable Boolean p_25439_, @Nullable Boolean p_25440_, EntityPredicate p_25441_, EntityPredicate p_25442_) {
      this.f_25421_ = p_25433_;
      this.f_25422_ = p_25434_;
      this.f_25423_ = p_25435_;
      this.f_25424_ = p_25436_;
      this.f_25425_ = p_25437_;
      this.f_25426_ = p_25438_;
      this.f_25427_ = p_25439_;
      this.f_25428_ = p_25440_;
      this.f_25429_ = p_25441_;
      this.f_25430_ = p_25442_;
   }

   public boolean m_25448_(ServerPlayer p_25449_, DamageSource p_25450_) {
      return this.m_25444_(p_25449_.m_9236_(), p_25449_.m_20182_(), p_25450_);
   }

   public boolean m_25444_(ServerLevel p_25445_, Vec3 p_25446_, DamageSource p_25447_) {
      if (this == f_25420_) {
         return true;
      } else if (this.f_25421_ != null && this.f_25421_ != p_25447_.m_19360_()) {
         return false;
      } else if (this.f_25422_ != null && this.f_25422_ != p_25447_.m_19372_()) {
         return false;
      } else if (this.f_25423_ != null && this.f_25423_ != p_25447_.m_19376_()) {
         return false;
      } else if (this.f_25424_ != null && this.f_25424_ != p_25447_.m_19378_()) {
         return false;
      } else if (this.f_25425_ != null && this.f_25425_ != p_25447_.m_19379_()) {
         return false;
      } else if (this.f_25426_ != null && this.f_25426_ != p_25447_.m_19384_()) {
         return false;
      } else if (this.f_25427_ != null && this.f_25427_ != p_25447_.m_19387_()) {
         return false;
      } else if (this.f_25428_ != null && this.f_25428_ != (p_25447_ == DamageSource.f_19306_)) {
         return false;
      } else if (!this.f_25429_.m_36607_(p_25445_, p_25446_, p_25447_.m_7640_())) {
         return false;
      } else {
         return this.f_25430_.m_36607_(p_25445_, p_25446_, p_25447_.m_7639_());
      }
   }

   public static DamageSourcePredicate m_25451_(@Nullable JsonElement p_25452_) {
      if (p_25452_ != null && !p_25452_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_25452_, "damage type");
         Boolean obool = m_25453_(jsonobject, "is_projectile");
         Boolean obool1 = m_25453_(jsonobject, "is_explosion");
         Boolean obool2 = m_25453_(jsonobject, "bypasses_armor");
         Boolean obool3 = m_25453_(jsonobject, "bypasses_invulnerability");
         Boolean obool4 = m_25453_(jsonobject, "bypasses_magic");
         Boolean obool5 = m_25453_(jsonobject, "is_fire");
         Boolean obool6 = m_25453_(jsonobject, "is_magic");
         Boolean obool7 = m_25453_(jsonobject, "is_lightning");
         EntityPredicate entitypredicate = EntityPredicate.m_36614_(jsonobject.get("direct_entity"));
         EntityPredicate entitypredicate1 = EntityPredicate.m_36614_(jsonobject.get("source_entity"));
         return new DamageSourcePredicate(obool, obool1, obool2, obool3, obool4, obool5, obool6, obool7, entitypredicate, entitypredicate1);
      } else {
         return f_25420_;
      }
   }

   @Nullable
   private static Boolean m_25453_(JsonObject p_25454_, String p_25455_) {
      return p_25454_.has(p_25455_) ? GsonHelper.m_13912_(p_25454_, p_25455_) : null;
   }

   public JsonElement m_25443_() {
      if (this == f_25420_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         this.m_25456_(jsonobject, "is_projectile", this.f_25421_);
         this.m_25456_(jsonobject, "is_explosion", this.f_25422_);
         this.m_25456_(jsonobject, "bypasses_armor", this.f_25423_);
         this.m_25456_(jsonobject, "bypasses_invulnerability", this.f_25424_);
         this.m_25456_(jsonobject, "bypasses_magic", this.f_25425_);
         this.m_25456_(jsonobject, "is_fire", this.f_25426_);
         this.m_25456_(jsonobject, "is_magic", this.f_25427_);
         this.m_25456_(jsonobject, "is_lightning", this.f_25428_);
         jsonobject.add("direct_entity", this.f_25429_.m_36606_());
         jsonobject.add("source_entity", this.f_25430_.m_36606_());
         return jsonobject;
      }
   }

   private void m_25456_(JsonObject p_25457_, String p_25458_, @Nullable Boolean p_25459_) {
      if (p_25459_ != null) {
         p_25457_.addProperty(p_25458_, p_25459_);
      }

   }

   public static class Builder {
      private Boolean f_25460_;
      private Boolean f_25461_;
      private Boolean f_25462_;
      private Boolean f_25463_;
      private Boolean f_25464_;
      private Boolean f_25465_;
      private Boolean f_25466_;
      private Boolean f_25467_;
      private EntityPredicate f_25468_ = EntityPredicate.f_36550_;
      private EntityPredicate f_25469_ = EntityPredicate.f_36550_;

      public static DamageSourcePredicate.Builder m_25471_() {
         return new DamageSourcePredicate.Builder();
      }

      public DamageSourcePredicate.Builder m_25474_(Boolean p_25475_) {
         this.f_25460_ = p_25475_;
         return this;
      }

      public DamageSourcePredicate.Builder m_148235_(Boolean p_148236_) {
         this.f_25461_ = p_148236_;
         return this;
      }

      public DamageSourcePredicate.Builder m_148237_(Boolean p_148238_) {
         this.f_25462_ = p_148238_;
         return this;
      }

      public DamageSourcePredicate.Builder m_148239_(Boolean p_148240_) {
         this.f_25463_ = p_148240_;
         return this;
      }

      public DamageSourcePredicate.Builder m_148241_(Boolean p_148242_) {
         this.f_25464_ = p_148242_;
         return this;
      }

      public DamageSourcePredicate.Builder m_148243_(Boolean p_148244_) {
         this.f_25465_ = p_148244_;
         return this;
      }

      public DamageSourcePredicate.Builder m_148245_(Boolean p_148246_) {
         this.f_25466_ = p_148246_;
         return this;
      }

      public DamageSourcePredicate.Builder m_25477_(Boolean p_25478_) {
         this.f_25467_ = p_25478_;
         return this;
      }

      public DamageSourcePredicate.Builder m_148229_(EntityPredicate p_148230_) {
         this.f_25468_ = p_148230_;
         return this;
      }

      public DamageSourcePredicate.Builder m_25472_(EntityPredicate.Builder p_25473_) {
         this.f_25468_ = p_25473_.m_36662_();
         return this;
      }

      public DamageSourcePredicate.Builder m_148233_(EntityPredicate p_148234_) {
         this.f_25469_ = p_148234_;
         return this;
      }

      public DamageSourcePredicate.Builder m_148231_(EntityPredicate.Builder p_148232_) {
         this.f_25469_ = p_148232_.m_36662_();
         return this;
      }

      public DamageSourcePredicate m_25476_() {
         return new DamageSourcePredicate(this.f_25460_, this.f_25461_, this.f_25462_, this.f_25463_, this.f_25464_, this.f_25465_, this.f_25466_, this.f_25467_, this.f_25468_, this.f_25469_);
      }
   }
}