package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EntityFlagsPredicate {
   public static final EntityFlagsPredicate f_33682_ = (new EntityFlagsPredicate.Builder()).m_33716_();
   @Nullable
   private final Boolean f_33683_;
   @Nullable
   private final Boolean f_33684_;
   @Nullable
   private final Boolean f_33685_;
   @Nullable
   private final Boolean f_33686_;
   @Nullable
   private final Boolean f_33687_;

   public EntityFlagsPredicate(@Nullable Boolean p_33690_, @Nullable Boolean p_33691_, @Nullable Boolean p_33692_, @Nullable Boolean p_33693_, @Nullable Boolean p_33694_) {
      this.f_33683_ = p_33690_;
      this.f_33684_ = p_33691_;
      this.f_33685_ = p_33692_;
      this.f_33686_ = p_33693_;
      this.f_33687_ = p_33694_;
   }

   public boolean m_33696_(Entity p_33697_) {
      if (this.f_33683_ != null && p_33697_.m_6060_() != this.f_33683_) {
         return false;
      } else if (this.f_33684_ != null && p_33697_.m_6047_() != this.f_33684_) {
         return false;
      } else if (this.f_33685_ != null && p_33697_.m_20142_() != this.f_33685_) {
         return false;
      } else if (this.f_33686_ != null && p_33697_.m_6069_() != this.f_33686_) {
         return false;
      } else {
         return this.f_33687_ == null || !(p_33697_ instanceof LivingEntity) || ((LivingEntity)p_33697_).m_6162_() == this.f_33687_;
      }
   }

   @Nullable
   private static Boolean m_33700_(JsonObject p_33701_, String p_33702_) {
      return p_33701_.has(p_33702_) ? GsonHelper.m_13912_(p_33701_, p_33702_) : null;
   }

   public static EntityFlagsPredicate m_33698_(@Nullable JsonElement p_33699_) {
      if (p_33699_ != null && !p_33699_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_33699_, "entity flags");
         Boolean obool = m_33700_(jsonobject, "is_on_fire");
         Boolean obool1 = m_33700_(jsonobject, "is_sneaking");
         Boolean obool2 = m_33700_(jsonobject, "is_sprinting");
         Boolean obool3 = m_33700_(jsonobject, "is_swimming");
         Boolean obool4 = m_33700_(jsonobject, "is_baby");
         return new EntityFlagsPredicate(obool, obool1, obool2, obool3, obool4);
      } else {
         return f_33682_;
      }
   }

   private void m_33703_(JsonObject p_33704_, String p_33705_, @Nullable Boolean p_33706_) {
      if (p_33706_ != null) {
         p_33704_.addProperty(p_33705_, p_33706_);
      }

   }

   public JsonElement m_33695_() {
      if (this == f_33682_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         this.m_33703_(jsonobject, "is_on_fire", this.f_33683_);
         this.m_33703_(jsonobject, "is_sneaking", this.f_33684_);
         this.m_33703_(jsonobject, "is_sprinting", this.f_33685_);
         this.m_33703_(jsonobject, "is_swimming", this.f_33686_);
         this.m_33703_(jsonobject, "is_baby", this.f_33687_);
         return jsonobject;
      }
   }

   public static class Builder {
      @Nullable
      private Boolean f_33707_;
      @Nullable
      private Boolean f_33708_;
      @Nullable
      private Boolean f_33709_;
      @Nullable
      private Boolean f_33710_;
      @Nullable
      private Boolean f_33711_;

      public static EntityFlagsPredicate.Builder m_33713_() {
         return new EntityFlagsPredicate.Builder();
      }

      public EntityFlagsPredicate.Builder m_33714_(@Nullable Boolean p_33715_) {
         this.f_33707_ = p_33715_;
         return this;
      }

      public EntityFlagsPredicate.Builder m_150057_(@Nullable Boolean p_150058_) {
         this.f_33708_ = p_150058_;
         return this;
      }

      public EntityFlagsPredicate.Builder m_150059_(@Nullable Boolean p_150060_) {
         this.f_33709_ = p_150060_;
         return this;
      }

      public EntityFlagsPredicate.Builder m_150061_(@Nullable Boolean p_150062_) {
         this.f_33710_ = p_150062_;
         return this;
      }

      public EntityFlagsPredicate.Builder m_33717_(@Nullable Boolean p_33718_) {
         this.f_33711_ = p_33718_;
         return this;
      }

      public EntityFlagsPredicate m_33716_() {
         return new EntityFlagsPredicate(this.f_33707_, this.f_33708_, this.f_33709_, this.f_33710_, this.f_33711_);
      }
   }
}