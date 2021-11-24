package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;

public class EntityPredicate {
   public static final EntityPredicate f_36550_ = new EntityPredicate(EntityTypePredicate.f_37636_, DistancePredicate.f_26241_, LocationPredicate.f_52592_, LocationPredicate.f_52592_, MobEffectsPredicate.f_56547_, NbtPredicate.f_57471_, EntityFlagsPredicate.f_33682_, EntityEquipmentPredicate.f_32176_, PlayerPredicate.f_62244_, FishingHookPredicate.f_39756_, LighthingBoltPredicate.f_153232_, (String)null, (ResourceLocation)null);
   private final EntityTypePredicate f_36551_;
   private final DistancePredicate f_36552_;
   private final LocationPredicate f_36553_;
   private final LocationPredicate f_150285_;
   private final MobEffectsPredicate f_36554_;
   private final NbtPredicate f_36555_;
   private final EntityFlagsPredicate f_36556_;
   private final EntityEquipmentPredicate f_36557_;
   private final PlayerPredicate f_36558_;
   private final FishingHookPredicate f_36559_;
   private final LighthingBoltPredicate f_150286_;
   private final EntityPredicate f_36560_;
   private final EntityPredicate f_150287_;
   private final EntityPredicate f_36561_;
   @Nullable
   private final String f_36562_;
   @Nullable
   private final ResourceLocation f_36563_;

   private EntityPredicate(EntityTypePredicate p_150306_, DistancePredicate p_150307_, LocationPredicate p_150308_, LocationPredicate p_150309_, MobEffectsPredicate p_150310_, NbtPredicate p_150311_, EntityFlagsPredicate p_150312_, EntityEquipmentPredicate p_150313_, PlayerPredicate p_150314_, FishingHookPredicate p_150315_, LighthingBoltPredicate p_150316_, @Nullable String p_150317_, @Nullable ResourceLocation p_150318_) {
      this.f_36551_ = p_150306_;
      this.f_36552_ = p_150307_;
      this.f_36553_ = p_150308_;
      this.f_150285_ = p_150309_;
      this.f_36554_ = p_150310_;
      this.f_36555_ = p_150311_;
      this.f_36556_ = p_150312_;
      this.f_36557_ = p_150313_;
      this.f_36558_ = p_150314_;
      this.f_36559_ = p_150315_;
      this.f_150286_ = p_150316_;
      this.f_150287_ = this;
      this.f_36560_ = this;
      this.f_36561_ = this;
      this.f_36562_ = p_150317_;
      this.f_36563_ = p_150318_;
   }

   EntityPredicate(EntityTypePredicate p_150289_, DistancePredicate p_150290_, LocationPredicate p_150291_, LocationPredicate p_150292_, MobEffectsPredicate p_150293_, NbtPredicate p_150294_, EntityFlagsPredicate p_150295_, EntityEquipmentPredicate p_150296_, PlayerPredicate p_150297_, FishingHookPredicate p_150298_, LighthingBoltPredicate p_150299_, EntityPredicate p_150300_, EntityPredicate p_150301_, EntityPredicate p_150302_, @Nullable String p_150303_, @Nullable ResourceLocation p_150304_) {
      this.f_36551_ = p_150289_;
      this.f_36552_ = p_150290_;
      this.f_36553_ = p_150291_;
      this.f_150285_ = p_150292_;
      this.f_36554_ = p_150293_;
      this.f_36555_ = p_150294_;
      this.f_36556_ = p_150295_;
      this.f_36557_ = p_150296_;
      this.f_36558_ = p_150297_;
      this.f_36559_ = p_150298_;
      this.f_150286_ = p_150299_;
      this.f_36560_ = p_150300_;
      this.f_150287_ = p_150301_;
      this.f_36561_ = p_150302_;
      this.f_36562_ = p_150303_;
      this.f_36563_ = p_150304_;
   }

   public boolean m_36611_(ServerPlayer p_36612_, @Nullable Entity p_36613_) {
      return this.m_36607_(p_36612_.m_9236_(), p_36612_.m_20182_(), p_36613_);
   }

   public boolean m_36607_(ServerLevel p_36608_, @Nullable Vec3 p_36609_, @Nullable Entity p_36610_) {
      if (this == f_36550_) {
         return true;
      } else if (p_36610_ == null) {
         return false;
      } else if (!this.f_36551_.m_7484_(p_36610_.m_6095_())) {
         return false;
      } else {
         if (p_36609_ == null) {
            if (this.f_36552_ != DistancePredicate.f_26241_) {
               return false;
            }
         } else if (!this.f_36552_.m_26255_(p_36609_.f_82479_, p_36609_.f_82480_, p_36609_.f_82481_, p_36610_.m_20185_(), p_36610_.m_20186_(), p_36610_.m_20189_())) {
            return false;
         }

         if (!this.f_36553_.m_52617_(p_36608_, p_36610_.m_20185_(), p_36610_.m_20186_(), p_36610_.m_20189_())) {
            return false;
         } else {
            if (this.f_150285_ != LocationPredicate.f_52592_) {
               Vec3 vec3 = Vec3.m_82512_(p_36610_.m_20097_());
               if (!this.f_150285_.m_52617_(p_36608_, vec3.m_7096_(), vec3.m_7098_(), vec3.m_7094_())) {
                  return false;
               }
            }

            if (!this.f_36554_.m_56555_(p_36610_)) {
               return false;
            } else if (!this.f_36555_.m_57477_(p_36610_)) {
               return false;
            } else if (!this.f_36556_.m_33696_(p_36610_)) {
               return false;
            } else if (!this.f_36557_.m_32193_(p_36610_)) {
               return false;
            } else if (!this.f_36558_.m_62270_(p_36610_)) {
               return false;
            } else if (!this.f_36559_.m_39762_(p_36610_)) {
               return false;
            } else if (!this.f_150286_.m_153246_(p_36610_, p_36608_, p_36609_)) {
               return false;
            } else if (!this.f_36560_.m_36607_(p_36608_, p_36609_, p_36610_.m_20202_())) {
               return false;
            } else if (this.f_150287_ != f_36550_ && p_36610_.m_20197_().stream().noneMatch((p_150322_) -> {
               return this.f_150287_.m_36607_(p_36608_, p_36609_, p_150322_);
            })) {
               return false;
            } else if (!this.f_36561_.m_36607_(p_36608_, p_36609_, p_36610_ instanceof Mob ? ((Mob)p_36610_).m_5448_() : null)) {
               return false;
            } else {
               if (this.f_36562_ != null) {
                  Team team = p_36610_.m_5647_();
                  if (team == null || !this.f_36562_.equals(team.m_5758_())) {
                     return false;
                  }
               }

               return this.f_36563_ == null || p_36610_ instanceof Cat && ((Cat)p_36610_).m_28162_().equals(this.f_36563_);
            }
         }
      }
   }

   public static EntityPredicate m_36614_(@Nullable JsonElement p_36615_) {
      if (p_36615_ != null && !p_36615_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_36615_, "entity");
         EntityTypePredicate entitytypepredicate = EntityTypePredicate.m_37643_(jsonobject.get("type"));
         DistancePredicate distancepredicate = DistancePredicate.m_26264_(jsonobject.get("distance"));
         LocationPredicate locationpredicate = LocationPredicate.m_52629_(jsonobject.get("location"));
         LocationPredicate locationpredicate1 = LocationPredicate.m_52629_(jsonobject.get("stepping_on"));
         MobEffectsPredicate mobeffectspredicate = MobEffectsPredicate.m_56559_(jsonobject.get("effects"));
         NbtPredicate nbtpredicate = NbtPredicate.m_57481_(jsonobject.get("nbt"));
         EntityFlagsPredicate entityflagspredicate = EntityFlagsPredicate.m_33698_(jsonobject.get("flags"));
         EntityEquipmentPredicate entityequipmentpredicate = EntityEquipmentPredicate.m_32195_(jsonobject.get("equipment"));
         PlayerPredicate playerpredicate = PlayerPredicate.m_62276_(jsonobject.get("player"));
         FishingHookPredicate fishinghookpredicate = FishingHookPredicate.m_39764_(jsonobject.get("fishing_hook"));
         EntityPredicate entitypredicate = m_36614_(jsonobject.get("vehicle"));
         EntityPredicate entitypredicate1 = m_36614_(jsonobject.get("passenger"));
         EntityPredicate entitypredicate2 = m_36614_(jsonobject.get("targeted_entity"));
         LighthingBoltPredicate lighthingboltpredicate = LighthingBoltPredicate.m_153252_(jsonobject.get("lightning_bolt"));
         String s = GsonHelper.m_13851_(jsonobject, "team", (String)null);
         ResourceLocation resourcelocation = jsonobject.has("catType") ? new ResourceLocation(GsonHelper.m_13906_(jsonobject, "catType")) : null;
         return (new EntityPredicate.Builder()).m_36646_(entitytypepredicate).m_36638_(distancepredicate).m_36650_(locationpredicate).m_150330_(locationpredicate1).m_36652_(mobeffectspredicate).m_36654_(nbtpredicate).m_36642_(entityflagspredicate).m_36640_(entityequipmentpredicate).m_36656_(playerpredicate).m_36648_(fishinghookpredicate).m_150326_(lighthingboltpredicate).m_36658_(s).m_36644_(entitypredicate).m_150328_(entitypredicate1).m_36663_(entitypredicate2).m_36665_(resourcelocation).m_36662_();
      } else {
         return f_36550_;
      }
   }

   public JsonElement m_36606_() {
      if (this == f_36550_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("type", this.f_36551_.m_5908_());
         jsonobject.add("distance", this.f_36552_.m_26254_());
         jsonobject.add("location", this.f_36553_.m_52616_());
         jsonobject.add("stepping_on", this.f_150285_.m_52616_());
         jsonobject.add("effects", this.f_36554_.m_56565_());
         jsonobject.add("nbt", this.f_36555_.m_57476_());
         jsonobject.add("flags", this.f_36556_.m_33695_());
         jsonobject.add("equipment", this.f_36557_.m_32192_());
         jsonobject.add("player", this.f_36558_.m_62264_());
         jsonobject.add("fishing_hook", this.f_36559_.m_39761_());
         jsonobject.add("lightning_bolt", this.f_150286_.m_153241_());
         jsonobject.add("vehicle", this.f_36560_.m_36606_());
         jsonobject.add("passenger", this.f_150287_.m_36606_());
         jsonobject.add("targeted_entity", this.f_36561_.m_36606_());
         jsonobject.addProperty("team", this.f_36562_);
         if (this.f_36563_ != null) {
            jsonobject.addProperty("catType", this.f_36563_.toString());
         }

         return jsonobject;
      }
   }

   public static LootContext m_36616_(ServerPlayer p_36617_, Entity p_36618_) {
      return (new LootContext.Builder(p_36617_.m_9236_())).m_78972_(LootContextParams.f_81455_, p_36618_).m_78972_(LootContextParams.f_81460_, p_36617_.m_20182_()).m_78977_(p_36617_.m_21187_()).m_78975_(LootContextParamSets.f_81419_);
   }

   public static class Builder {
      private EntityTypePredicate f_36619_ = EntityTypePredicate.f_37636_;
      private DistancePredicate f_36620_ = DistancePredicate.f_26241_;
      private LocationPredicate f_36621_ = LocationPredicate.f_52592_;
      private LocationPredicate f_150323_ = LocationPredicate.f_52592_;
      private MobEffectsPredicate f_36622_ = MobEffectsPredicate.f_56547_;
      private NbtPredicate f_36623_ = NbtPredicate.f_57471_;
      private EntityFlagsPredicate f_36624_ = EntityFlagsPredicate.f_33682_;
      private EntityEquipmentPredicate f_36625_ = EntityEquipmentPredicate.f_32176_;
      private PlayerPredicate f_36626_ = PlayerPredicate.f_62244_;
      private FishingHookPredicate f_36627_ = FishingHookPredicate.f_39756_;
      private LighthingBoltPredicate f_150324_ = LighthingBoltPredicate.f_153232_;
      private EntityPredicate f_36628_ = EntityPredicate.f_36550_;
      private EntityPredicate f_150325_ = EntityPredicate.f_36550_;
      private EntityPredicate f_36629_ = EntityPredicate.f_36550_;
      private String f_36630_;
      private ResourceLocation f_36631_;

      public static EntityPredicate.Builder m_36633_() {
         return new EntityPredicate.Builder();
      }

      public EntityPredicate.Builder m_36636_(EntityType<?> p_36637_) {
         this.f_36619_ = EntityTypePredicate.m_37647_(p_36637_);
         return this;
      }

      public EntityPredicate.Builder m_36634_(Tag<EntityType<?>> p_36635_) {
         this.f_36619_ = EntityTypePredicate.m_37640_(p_36635_);
         return this;
      }

      public EntityPredicate.Builder m_36660_(ResourceLocation p_36661_) {
         this.f_36631_ = p_36661_;
         return this;
      }

      public EntityPredicate.Builder m_36646_(EntityTypePredicate p_36647_) {
         this.f_36619_ = p_36647_;
         return this;
      }

      public EntityPredicate.Builder m_36638_(DistancePredicate p_36639_) {
         this.f_36620_ = p_36639_;
         return this;
      }

      public EntityPredicate.Builder m_36650_(LocationPredicate p_36651_) {
         this.f_36621_ = p_36651_;
         return this;
      }

      public EntityPredicate.Builder m_150330_(LocationPredicate p_150331_) {
         this.f_150323_ = p_150331_;
         return this;
      }

      public EntityPredicate.Builder m_36652_(MobEffectsPredicate p_36653_) {
         this.f_36622_ = p_36653_;
         return this;
      }

      public EntityPredicate.Builder m_36654_(NbtPredicate p_36655_) {
         this.f_36623_ = p_36655_;
         return this;
      }

      public EntityPredicate.Builder m_36642_(EntityFlagsPredicate p_36643_) {
         this.f_36624_ = p_36643_;
         return this;
      }

      public EntityPredicate.Builder m_36640_(EntityEquipmentPredicate p_36641_) {
         this.f_36625_ = p_36641_;
         return this;
      }

      public EntityPredicate.Builder m_36656_(PlayerPredicate p_36657_) {
         this.f_36626_ = p_36657_;
         return this;
      }

      public EntityPredicate.Builder m_36648_(FishingHookPredicate p_36649_) {
         this.f_36627_ = p_36649_;
         return this;
      }

      public EntityPredicate.Builder m_150326_(LighthingBoltPredicate p_150327_) {
         this.f_150324_ = p_150327_;
         return this;
      }

      public EntityPredicate.Builder m_36644_(EntityPredicate p_36645_) {
         this.f_36628_ = p_36645_;
         return this;
      }

      public EntityPredicate.Builder m_150328_(EntityPredicate p_150329_) {
         this.f_150325_ = p_150329_;
         return this;
      }

      public EntityPredicate.Builder m_36663_(EntityPredicate p_36664_) {
         this.f_36629_ = p_36664_;
         return this;
      }

      public EntityPredicate.Builder m_36658_(@Nullable String p_36659_) {
         this.f_36630_ = p_36659_;
         return this;
      }

      public EntityPredicate.Builder m_36665_(@Nullable ResourceLocation p_36666_) {
         this.f_36631_ = p_36666_;
         return this;
      }

      public EntityPredicate m_36662_() {
         return new EntityPredicate(this.f_36619_, this.f_36620_, this.f_36621_, this.f_150323_, this.f_36622_, this.f_36623_, this.f_36624_, this.f_36625_, this.f_36626_, this.f_36627_, this.f_150324_, this.f_36628_, this.f_150325_, this.f_36629_, this.f_36630_, this.f_36631_);
      }
   }

   public static class Composite {
      public static final EntityPredicate.Composite f_36667_ = new EntityPredicate.Composite(new LootItemCondition[0]);
      private final LootItemCondition[] f_36668_;
      private final Predicate<LootContext> f_36669_;

      private Composite(LootItemCondition[] p_36672_) {
         this.f_36668_ = p_36672_;
         this.f_36669_ = LootItemConditions.m_81834_(p_36672_);
      }

      public static EntityPredicate.Composite m_36690_(LootItemCondition... p_36691_) {
         return new EntityPredicate.Composite(p_36691_);
      }

      public static EntityPredicate.Composite m_36677_(JsonObject p_36678_, String p_36679_, DeserializationContext p_36680_) {
         JsonElement jsonelement = p_36678_.get(p_36679_);
         return m_36683_(p_36679_, p_36680_, jsonelement);
      }

      public static EntityPredicate.Composite[] m_36692_(JsonObject p_36693_, String p_36694_, DeserializationContext p_36695_) {
         JsonElement jsonelement = p_36693_.get(p_36694_);
         if (jsonelement != null && !jsonelement.isJsonNull()) {
            JsonArray jsonarray = GsonHelper.m_13924_(jsonelement, p_36694_);
            EntityPredicate.Composite[] aentitypredicate$composite = new EntityPredicate.Composite[jsonarray.size()];

            for(int i = 0; i < jsonarray.size(); ++i) {
               aentitypredicate$composite[i] = m_36683_(p_36694_ + "[" + i + "]", p_36695_, jsonarray.get(i));
            }

            return aentitypredicate$composite;
         } else {
            return new EntityPredicate.Composite[0];
         }
      }

      private static EntityPredicate.Composite m_36683_(String p_36684_, DeserializationContext p_36685_, @Nullable JsonElement p_36686_) {
         if (p_36686_ != null && p_36686_.isJsonArray()) {
            LootItemCondition[] alootitemcondition = p_36685_.m_25874_(p_36686_.getAsJsonArray(), p_36685_.m_25873_() + "/" + p_36684_, LootContextParamSets.f_81419_);
            return new EntityPredicate.Composite(alootitemcondition);
         } else {
            EntityPredicate entitypredicate = EntityPredicate.m_36614_(p_36686_);
            return m_36673_(entitypredicate);
         }
      }

      public static EntityPredicate.Composite m_36673_(EntityPredicate p_36674_) {
         if (p_36674_ == EntityPredicate.f_36550_) {
            return f_36667_;
         } else {
            LootItemCondition lootitemcondition = LootItemEntityPropertyCondition.m_81867_(LootContext.EntityTarget.THIS, p_36674_).m_6409_();
            return new EntityPredicate.Composite(new LootItemCondition[]{lootitemcondition});
         }
      }

      public boolean m_36681_(LootContext p_36682_) {
         return this.f_36669_.test(p_36682_);
      }

      public JsonElement m_36675_(SerializationContext p_36676_) {
         return (JsonElement)(this.f_36668_.length == 0 ? JsonNull.INSTANCE : p_36676_.m_64772_(this.f_36668_));
      }

      public static JsonElement m_36687_(EntityPredicate.Composite[] p_36688_, SerializationContext p_36689_) {
         if (p_36688_.length == 0) {
            return JsonNull.INSTANCE;
         } else {
            JsonArray jsonarray = new JsonArray();

            for(EntityPredicate.Composite entitypredicate$composite : p_36688_) {
               jsonarray.add(entitypredicate$composite.m_36675_(p_36689_));
            }

            return jsonarray;
         }
      }
   }
}