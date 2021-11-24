package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;

public class EntityEquipmentPredicate {
   public static final EntityEquipmentPredicate f_32176_ = new EntityEquipmentPredicate(ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_);
   public static final EntityEquipmentPredicate f_32177_ = new EntityEquipmentPredicate(ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42660_).m_45075_(Raid.m_37779_().m_41783_()).m_45077_(), ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_);
   private final ItemPredicate f_32178_;
   private final ItemPredicate f_32179_;
   private final ItemPredicate f_32180_;
   private final ItemPredicate f_32181_;
   private final ItemPredicate f_32182_;
   private final ItemPredicate f_32183_;

   public EntityEquipmentPredicate(ItemPredicate p_32186_, ItemPredicate p_32187_, ItemPredicate p_32188_, ItemPredicate p_32189_, ItemPredicate p_32190_, ItemPredicate p_32191_) {
      this.f_32178_ = p_32186_;
      this.f_32179_ = p_32187_;
      this.f_32180_ = p_32188_;
      this.f_32181_ = p_32189_;
      this.f_32182_ = p_32190_;
      this.f_32183_ = p_32191_;
   }

   public boolean m_32193_(@Nullable Entity p_32194_) {
      if (this == f_32176_) {
         return true;
      } else if (!(p_32194_ instanceof LivingEntity)) {
         return false;
      } else {
         LivingEntity livingentity = (LivingEntity)p_32194_;
         if (!this.f_32178_.m_45049_(livingentity.m_6844_(EquipmentSlot.HEAD))) {
            return false;
         } else if (!this.f_32179_.m_45049_(livingentity.m_6844_(EquipmentSlot.CHEST))) {
            return false;
         } else if (!this.f_32180_.m_45049_(livingentity.m_6844_(EquipmentSlot.LEGS))) {
            return false;
         } else if (!this.f_32181_.m_45049_(livingentity.m_6844_(EquipmentSlot.FEET))) {
            return false;
         } else if (!this.f_32182_.m_45049_(livingentity.m_6844_(EquipmentSlot.MAINHAND))) {
            return false;
         } else {
            return this.f_32183_.m_45049_(livingentity.m_6844_(EquipmentSlot.OFFHAND));
         }
      }
   }

   public static EntityEquipmentPredicate m_32195_(@Nullable JsonElement p_32196_) {
      if (p_32196_ != null && !p_32196_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_32196_, "equipment");
         ItemPredicate itempredicate = ItemPredicate.m_45051_(jsonobject.get("head"));
         ItemPredicate itempredicate1 = ItemPredicate.m_45051_(jsonobject.get("chest"));
         ItemPredicate itempredicate2 = ItemPredicate.m_45051_(jsonobject.get("legs"));
         ItemPredicate itempredicate3 = ItemPredicate.m_45051_(jsonobject.get("feet"));
         ItemPredicate itempredicate4 = ItemPredicate.m_45051_(jsonobject.get("mainhand"));
         ItemPredicate itempredicate5 = ItemPredicate.m_45051_(jsonobject.get("offhand"));
         return new EntityEquipmentPredicate(itempredicate, itempredicate1, itempredicate2, itempredicate3, itempredicate4, itempredicate5);
      } else {
         return f_32176_;
      }
   }

   public JsonElement m_32192_() {
      if (this == f_32176_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("head", this.f_32178_.m_45048_());
         jsonobject.add("chest", this.f_32179_.m_45048_());
         jsonobject.add("legs", this.f_32180_.m_45048_());
         jsonobject.add("feet", this.f_32181_.m_45048_());
         jsonobject.add("mainhand", this.f_32182_.m_45048_());
         jsonobject.add("offhand", this.f_32183_.m_45048_());
         return jsonobject;
      }
   }

   public static class Builder {
      private ItemPredicate f_32197_ = ItemPredicate.f_45028_;
      private ItemPredicate f_32198_ = ItemPredicate.f_45028_;
      private ItemPredicate f_32199_ = ItemPredicate.f_45028_;
      private ItemPredicate f_32200_ = ItemPredicate.f_45028_;
      private ItemPredicate f_32201_ = ItemPredicate.f_45028_;
      private ItemPredicate f_32202_ = ItemPredicate.f_45028_;

      public static EntityEquipmentPredicate.Builder m_32204_() {
         return new EntityEquipmentPredicate.Builder();
      }

      public EntityEquipmentPredicate.Builder m_32205_(ItemPredicate p_32206_) {
         this.f_32197_ = p_32206_;
         return this;
      }

      public EntityEquipmentPredicate.Builder m_32208_(ItemPredicate p_32209_) {
         this.f_32198_ = p_32209_;
         return this;
      }

      public EntityEquipmentPredicate.Builder m_32210_(ItemPredicate p_32211_) {
         this.f_32199_ = p_32211_;
         return this;
      }

      public EntityEquipmentPredicate.Builder m_32212_(ItemPredicate p_32213_) {
         this.f_32200_ = p_32213_;
         return this;
      }

      public EntityEquipmentPredicate.Builder m_149928_(ItemPredicate p_149929_) {
         this.f_32201_ = p_149929_;
         return this;
      }

      public EntityEquipmentPredicate.Builder m_149930_(ItemPredicate p_149931_) {
         this.f_32202_ = p_149931_;
         return this;
      }

      public EntityEquipmentPredicate m_32207_() {
         return new EntityEquipmentPredicate(this.f_32197_, this.f_32198_, this.f_32199_, this.f_32200_, this.f_32201_, this.f_32202_);
      }
   }
}