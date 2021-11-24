package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.StatsCounter;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PlayerPredicate {
   public static final PlayerPredicate f_62244_ = (new PlayerPredicate.Builder()).m_62313_();
   public static final int f_156743_ = 100;
   private final MinMaxBounds.Ints f_62245_;
   @Nullable
   private final GameType f_62246_;
   private final Map<Stat<?>, MinMaxBounds.Ints> f_62247_;
   private final Object2BooleanMap<ResourceLocation> f_62248_;
   private final Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> f_62249_;
   private final EntityPredicate f_156744_;

   private static PlayerPredicate.AdvancementPredicate m_62289_(JsonElement p_62290_) {
      if (p_62290_.isJsonPrimitive()) {
         boolean flag = p_62290_.getAsBoolean();
         return new PlayerPredicate.AdvancementDonePredicate(flag);
      } else {
         Object2BooleanMap<String> object2booleanmap = new Object2BooleanOpenHashMap<>();
         JsonObject jsonobject = GsonHelper.m_13918_(p_62290_, "criterion data");
         jsonobject.entrySet().forEach((p_62288_) -> {
            boolean flag1 = GsonHelper.m_13877_(p_62288_.getValue(), "criterion test");
            object2booleanmap.put(p_62288_.getKey(), flag1);
         });
         return new PlayerPredicate.AdvancementCriterionsPredicate(object2booleanmap);
      }
   }

   PlayerPredicate(MinMaxBounds.Ints p_156746_, @Nullable GameType p_156747_, Map<Stat<?>, MinMaxBounds.Ints> p_156748_, Object2BooleanMap<ResourceLocation> p_156749_, Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> p_156750_, EntityPredicate p_156751_) {
      this.f_62245_ = p_156746_;
      this.f_62246_ = p_156747_;
      this.f_62247_ = p_156748_;
      this.f_62248_ = p_156749_;
      this.f_62249_ = p_156750_;
      this.f_156744_ = p_156751_;
   }

   public boolean m_62270_(Entity p_62271_) {
      if (this == f_62244_) {
         return true;
      } else if (!(p_62271_ instanceof ServerPlayer)) {
         return false;
      } else {
         ServerPlayer serverplayer = (ServerPlayer)p_62271_;
         if (!this.f_62245_.m_55390_(serverplayer.f_36078_)) {
            return false;
         } else if (this.f_62246_ != null && this.f_62246_ != serverplayer.f_8941_.m_9290_()) {
            return false;
         } else {
            StatsCounter statscounter = serverplayer.m_8951_();

            for(Entry<Stat<?>, MinMaxBounds.Ints> entry : this.f_62247_.entrySet()) {
               int i = statscounter.m_13015_(entry.getKey());
               if (!entry.getValue().m_55390_(i)) {
                  return false;
               }
            }

            RecipeBook recipebook = serverplayer.m_8952_();

            for(it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<ResourceLocation> entry2 : this.f_62248_.object2BooleanEntrySet()) {
               if (recipebook.m_12711_(entry2.getKey()) != entry2.getBooleanValue()) {
                  return false;
               }
            }

            if (!this.f_62249_.isEmpty()) {
               PlayerAdvancements playeradvancements = serverplayer.m_8960_();
               ServerAdvancementManager serveradvancementmanager = serverplayer.m_20194_().m_129889_();

               for(Entry<ResourceLocation, PlayerPredicate.AdvancementPredicate> entry1 : this.f_62249_.entrySet()) {
                  Advancement advancement = serveradvancementmanager.m_136041_(entry1.getKey());
                  if (advancement == null || !entry1.getValue().test(playeradvancements.m_135996_(advancement))) {
                     return false;
                  }
               }
            }

            if (this.f_156744_ != EntityPredicate.f_36550_) {
               Vec3 vec3 = serverplayer.m_146892_();
               Vec3 vec31 = serverplayer.m_20252_(1.0F);
               Vec3 vec32 = vec3.m_82520_(vec31.f_82479_ * 100.0D, vec31.f_82480_ * 100.0D, vec31.f_82481_ * 100.0D);
               EntityHitResult entityhitresult = ProjectileUtil.m_150175_(serverplayer.f_19853_, serverplayer, vec3, vec32, (new AABB(vec3, vec32)).m_82400_(1.0D), (p_156765_) -> {
                  return !p_156765_.m_5833_();
               }, 0.0F);
               if (entityhitresult == null || entityhitresult.m_6662_() != HitResult.Type.ENTITY) {
                  return false;
               }

               Entity entity = entityhitresult.m_82443_();
               if (!this.f_156744_.m_36611_(serverplayer, entity) || !serverplayer.m_142582_(entity)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static PlayerPredicate m_62276_(@Nullable JsonElement p_62277_) {
      if (p_62277_ != null && !p_62277_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_62277_, "player");
         MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(jsonobject.get("level"));
         String s = GsonHelper.m_13851_(jsonobject, "gamemode", "");
         GameType gametype = GameType.m_46402_(s, (GameType)null);
         Map<Stat<?>, MinMaxBounds.Ints> map = Maps.newHashMap();
         JsonArray jsonarray = GsonHelper.m_13832_(jsonobject, "stats", (JsonArray)null);
         if (jsonarray != null) {
            for(JsonElement jsonelement : jsonarray) {
               JsonObject jsonobject1 = GsonHelper.m_13918_(jsonelement, "stats entry");
               ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(jsonobject1, "type"));
               StatType<?> stattype = Registry.f_122867_.m_7745_(resourcelocation);
               if (stattype == null) {
                  throw new JsonParseException("Invalid stat type: " + resourcelocation);
               }

               ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.m_13906_(jsonobject1, "stat"));
               Stat<?> stat = m_62267_(stattype, resourcelocation1);
               MinMaxBounds.Ints minmaxbounds$ints1 = MinMaxBounds.Ints.m_55373_(jsonobject1.get("value"));
               map.put(stat, minmaxbounds$ints1);
            }
         }

         Object2BooleanMap<ResourceLocation> object2booleanmap = new Object2BooleanOpenHashMap<>();
         JsonObject jsonobject2 = GsonHelper.m_13841_(jsonobject, "recipes", new JsonObject());

         for(Entry<String, JsonElement> entry : jsonobject2.entrySet()) {
            ResourceLocation resourcelocation2 = new ResourceLocation(entry.getKey());
            boolean flag = GsonHelper.m_13877_(entry.getValue(), "recipe present");
            object2booleanmap.put(resourcelocation2, flag);
         }

         Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> map1 = Maps.newHashMap();
         JsonObject jsonobject3 = GsonHelper.m_13841_(jsonobject, "advancements", new JsonObject());

         for(Entry<String, JsonElement> entry1 : jsonobject3.entrySet()) {
            ResourceLocation resourcelocation3 = new ResourceLocation(entry1.getKey());
            PlayerPredicate.AdvancementPredicate playerpredicate$advancementpredicate = m_62289_(entry1.getValue());
            map1.put(resourcelocation3, playerpredicate$advancementpredicate);
         }

         EntityPredicate entitypredicate = EntityPredicate.m_36614_(jsonobject.get("looking_at"));
         return new PlayerPredicate(minmaxbounds$ints, gametype, map, object2booleanmap, map1, entitypredicate);
      } else {
         return f_62244_;
      }
   }

   private static <T> Stat<T> m_62267_(StatType<T> p_62268_, ResourceLocation p_62269_) {
      Registry<T> registry = p_62268_.m_12893_();
      T t = registry.m_7745_(p_62269_);
      if (t == null) {
         throw new JsonParseException("Unknown object " + p_62269_ + " for stat type " + Registry.f_122867_.m_7981_(p_62268_));
      } else {
         return p_62268_.m_12902_(t);
      }
   }

   private static <T> ResourceLocation m_62265_(Stat<T> p_62266_) {
      return p_62266_.m_12859_().m_12893_().m_7981_(p_62266_.m_12867_());
   }

   public JsonElement m_62264_() {
      if (this == f_62244_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("level", this.f_62245_.m_55328_());
         if (this.f_62246_ != null) {
            jsonobject.addProperty("gamemode", this.f_62246_.m_46405_());
         }

         if (!this.f_62247_.isEmpty()) {
            JsonArray jsonarray = new JsonArray();
            this.f_62247_.forEach((p_156754_, p_156755_) -> {
               JsonObject jsonobject3 = new JsonObject();
               jsonobject3.addProperty("type", Registry.f_122867_.m_7981_(p_156754_.m_12859_()).toString());
               jsonobject3.addProperty("stat", m_62265_(p_156754_).toString());
               jsonobject3.add("value", p_156755_.m_55328_());
               jsonarray.add(jsonobject3);
            });
            jsonobject.add("stats", jsonarray);
         }

         if (!this.f_62248_.isEmpty()) {
            JsonObject jsonobject1 = new JsonObject();
            this.f_62248_.forEach((p_156762_, p_156763_) -> {
               jsonobject1.addProperty(p_156762_.toString(), p_156763_);
            });
            jsonobject.add("recipes", jsonobject1);
         }

         if (!this.f_62249_.isEmpty()) {
            JsonObject jsonobject2 = new JsonObject();
            this.f_62249_.forEach((p_156758_, p_156759_) -> {
               jsonobject2.add(p_156758_.toString(), p_156759_.m_7943_());
            });
            jsonobject.add("advancements", jsonobject2);
         }

         jsonobject.add("looking_at", this.f_156744_.m_36606_());
         return jsonobject;
      }
   }

   static class AdvancementCriterionsPredicate implements PlayerPredicate.AdvancementPredicate {
      private final Object2BooleanMap<String> f_62291_;

      public AdvancementCriterionsPredicate(Object2BooleanMap<String> p_62293_) {
         this.f_62291_ = p_62293_;
      }

      public JsonElement m_7943_() {
         JsonObject jsonobject = new JsonObject();
         this.f_62291_.forEach(jsonobject::addProperty);
         return jsonobject;
      }

      public boolean test(AdvancementProgress p_62296_) {
         for(it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String> entry : this.f_62291_.object2BooleanEntrySet()) {
            CriterionProgress criterionprogress = p_62296_.m_8214_(entry.getKey());
            if (criterionprogress == null || criterionprogress.m_12911_() != entry.getBooleanValue()) {
               return false;
            }
         }

         return true;
      }
   }

   static class AdvancementDonePredicate implements PlayerPredicate.AdvancementPredicate {
      private final boolean f_62299_;

      public AdvancementDonePredicate(boolean p_62301_) {
         this.f_62299_ = p_62301_;
      }

      public JsonElement m_7943_() {
         return new JsonPrimitive(this.f_62299_);
      }

      public boolean test(AdvancementProgress p_62304_) {
         return p_62304_.m_8193_() == this.f_62299_;
      }
   }

   interface AdvancementPredicate extends Predicate<AdvancementProgress> {
      JsonElement m_7943_();
   }

   public static class Builder {
      private MinMaxBounds.Ints f_62307_ = MinMaxBounds.Ints.f_55364_;
      @Nullable
      private GameType f_62308_;
      private final Map<Stat<?>, MinMaxBounds.Ints> f_62309_ = Maps.newHashMap();
      private final Object2BooleanMap<ResourceLocation> f_62310_ = new Object2BooleanOpenHashMap<>();
      private final Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> f_62311_ = Maps.newHashMap();
      private EntityPredicate f_156766_ = EntityPredicate.f_36550_;

      public static PlayerPredicate.Builder m_156767_() {
         return new PlayerPredicate.Builder();
      }

      public PlayerPredicate.Builder m_156775_(MinMaxBounds.Ints p_156776_) {
         this.f_62307_ = p_156776_;
         return this;
      }

      public PlayerPredicate.Builder m_156768_(Stat<?> p_156769_, MinMaxBounds.Ints p_156770_) {
         this.f_62309_.put(p_156769_, p_156770_);
         return this;
      }

      public PlayerPredicate.Builder m_156780_(ResourceLocation p_156781_, boolean p_156782_) {
         this.f_62310_.put(p_156781_, p_156782_);
         return this;
      }

      public PlayerPredicate.Builder m_156773_(GameType p_156774_) {
         this.f_62308_ = p_156774_;
         return this;
      }

      public PlayerPredicate.Builder m_156771_(EntityPredicate p_156772_) {
         this.f_156766_ = p_156772_;
         return this;
      }

      public PlayerPredicate.Builder m_156783_(ResourceLocation p_156784_, boolean p_156785_) {
         this.f_62311_.put(p_156784_, new PlayerPredicate.AdvancementDonePredicate(p_156785_));
         return this;
      }

      public PlayerPredicate.Builder m_156777_(ResourceLocation p_156778_, Map<String, Boolean> p_156779_) {
         this.f_62311_.put(p_156778_, new PlayerPredicate.AdvancementCriterionsPredicate(new Object2BooleanOpenHashMap<>(p_156779_)));
         return this;
      }

      public PlayerPredicate m_62313_() {
         return new PlayerPredicate(this.f_62307_, this.f_62308_, this.f_62309_, this.f_62310_, this.f_62311_, this.f_156766_);
      }
   }
}