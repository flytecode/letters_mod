package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocationPredicate {
   private static final Logger f_52593_ = LogManager.getLogger();
   public static final LocationPredicate f_52592_ = new LocationPredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, (ResourceKey<Biome>)null, (StructureFeature<?>)null, (ResourceKey<Level>)null, (Boolean)null, LightPredicate.f_51335_, BlockPredicate.f_17902_, FluidPredicate.f_41094_);
   private final MinMaxBounds.Doubles f_52594_;
   private final MinMaxBounds.Doubles f_52595_;
   private final MinMaxBounds.Doubles f_52596_;
   @Nullable
   private final ResourceKey<Biome> f_52597_;
   @Nullable
   private final StructureFeature<?> f_52598_;
   @Nullable
   private final ResourceKey<Level> f_52599_;
   @Nullable
   private final Boolean f_52600_;
   private final LightPredicate f_52601_;
   private final BlockPredicate f_52602_;
   private final FluidPredicate f_52603_;

   public LocationPredicate(MinMaxBounds.Doubles p_52606_, MinMaxBounds.Doubles p_52607_, MinMaxBounds.Doubles p_52608_, @Nullable ResourceKey<Biome> p_52609_, @Nullable StructureFeature<?> p_52610_, @Nullable ResourceKey<Level> p_52611_, @Nullable Boolean p_52612_, LightPredicate p_52613_, BlockPredicate p_52614_, FluidPredicate p_52615_) {
      this.f_52594_ = p_52606_;
      this.f_52595_ = p_52607_;
      this.f_52596_ = p_52608_;
      this.f_52597_ = p_52609_;
      this.f_52598_ = p_52610_;
      this.f_52599_ = p_52611_;
      this.f_52600_ = p_52612_;
      this.f_52601_ = p_52613_;
      this.f_52602_ = p_52614_;
      this.f_52603_ = p_52615_;
   }

   public static LocationPredicate m_52634_(ResourceKey<Biome> p_52635_) {
      return new LocationPredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, p_52635_, (StructureFeature<?>)null, (ResourceKey<Level>)null, (Boolean)null, LightPredicate.f_51335_, BlockPredicate.f_17902_, FluidPredicate.f_41094_);
   }

   public static LocationPredicate m_52638_(ResourceKey<Level> p_52639_) {
      return new LocationPredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, (ResourceKey<Biome>)null, (StructureFeature<?>)null, p_52639_, (Boolean)null, LightPredicate.f_51335_, BlockPredicate.f_17902_, FluidPredicate.f_41094_);
   }

   public static LocationPredicate m_52627_(StructureFeature<?> p_52628_) {
      return new LocationPredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, (ResourceKey<Biome>)null, p_52628_, (ResourceKey<Level>)null, (Boolean)null, LightPredicate.f_51335_, BlockPredicate.f_17902_, FluidPredicate.f_41094_);
   }

   public boolean m_52617_(ServerLevel p_52618_, double p_52619_, double p_52620_, double p_52621_) {
      if (!this.f_52594_.m_154810_(p_52619_)) {
         return false;
      } else if (!this.f_52595_.m_154810_(p_52620_)) {
         return false;
      } else if (!this.f_52596_.m_154810_(p_52621_)) {
         return false;
      } else if (this.f_52599_ != null && this.f_52599_ != p_52618_.m_46472_()) {
         return false;
      } else {
         BlockPos blockpos = new BlockPos(p_52619_, p_52620_, p_52621_);
         boolean flag = p_52618_.m_46749_(blockpos);
         Optional<ResourceKey<Biome>> optional = p_52618_.m_5962_().m_175515_(Registry.f_122885_).m_7854_(p_52618_.m_46857_(blockpos));
         if (!optional.isPresent()) {
            return false;
         } else if (this.f_52597_ == null || flag && this.f_52597_ == optional.get()) {
            if (this.f_52598_ == null || flag && p_52618_.m_8595_().m_47285_(blockpos, true, this.f_52598_).m_73603_()) {
               if (this.f_52600_ == null || flag && this.f_52600_ == CampfireBlock.m_51248_(p_52618_, blockpos)) {
                  if (!this.f_52601_.m_51341_(p_52618_, blockpos)) {
                     return false;
                  } else if (!this.f_52602_.m_17914_(p_52618_, blockpos)) {
                     return false;
                  } else {
                     return this.f_52603_.m_41104_(p_52618_, blockpos);
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public JsonElement m_52616_() {
      if (this == f_52592_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (!this.f_52594_.m_55327_() || !this.f_52595_.m_55327_() || !this.f_52596_.m_55327_()) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.add("x", this.f_52594_.m_55328_());
            jsonobject1.add("y", this.f_52595_.m_55328_());
            jsonobject1.add("z", this.f_52596_.m_55328_());
            jsonobject.add("position", jsonobject1);
         }

         if (this.f_52599_ != null) {
            Level.f_46427_.encodeStart(JsonOps.INSTANCE, this.f_52599_).resultOrPartial(f_52593_::error).ifPresent((p_52633_) -> {
               jsonobject.add("dimension", p_52633_);
            });
         }

         if (this.f_52598_ != null) {
            jsonobject.addProperty("feature", this.f_52598_.m_67098_());
         }

         if (this.f_52597_ != null) {
            jsonobject.addProperty("biome", this.f_52597_.m_135782_().toString());
         }

         if (this.f_52600_ != null) {
            jsonobject.addProperty("smokey", this.f_52600_);
         }

         jsonobject.add("light", this.f_52601_.m_51340_());
         jsonobject.add("block", this.f_52602_.m_17913_());
         jsonobject.add("fluid", this.f_52603_.m_41103_());
         return jsonobject;
      }
   }

   public static LocationPredicate m_52629_(@Nullable JsonElement p_52630_) {
      if (p_52630_ != null && !p_52630_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_52630_, "location");
         JsonObject jsonobject1 = GsonHelper.m_13841_(jsonobject, "position", new JsonObject());
         MinMaxBounds.Doubles minmaxbounds$doubles = MinMaxBounds.Doubles.m_154791_(jsonobject1.get("x"));
         MinMaxBounds.Doubles minmaxbounds$doubles1 = MinMaxBounds.Doubles.m_154791_(jsonobject1.get("y"));
         MinMaxBounds.Doubles minmaxbounds$doubles2 = MinMaxBounds.Doubles.m_154791_(jsonobject1.get("z"));
         ResourceKey<Level> resourcekey = jsonobject.has("dimension") ? ResourceLocation.f_135803_.parse(JsonOps.INSTANCE, jsonobject.get("dimension")).resultOrPartial(f_52593_::error).map((p_52637_) -> {
            return ResourceKey.m_135785_(Registry.f_122819_, p_52637_);
         }).orElse((ResourceKey<Level>)null) : null;
         StructureFeature<?> structurefeature = jsonobject.has("feature") ? StructureFeature.f_67012_.get(GsonHelper.m_13906_(jsonobject, "feature")) : null;
         ResourceKey<Biome> resourcekey1 = null;
         if (jsonobject.has("biome")) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(jsonobject, "biome"));
            resourcekey1 = ResourceKey.m_135785_(Registry.f_122885_, resourcelocation);
         }

         Boolean obool = jsonobject.has("smokey") ? jsonobject.get("smokey").getAsBoolean() : null;
         LightPredicate lightpredicate = LightPredicate.m_51344_(jsonobject.get("light"));
         BlockPredicate blockpredicate = BlockPredicate.m_17917_(jsonobject.get("block"));
         FluidPredicate fluidpredicate = FluidPredicate.m_41107_(jsonobject.get("fluid"));
         return new LocationPredicate(minmaxbounds$doubles, minmaxbounds$doubles1, minmaxbounds$doubles2, resourcekey1, structurefeature, resourcekey, obool, lightpredicate, blockpredicate, fluidpredicate);
      } else {
         return f_52592_;
      }
   }

   public static class Builder {
      private MinMaxBounds.Doubles f_52640_ = MinMaxBounds.Doubles.f_154779_;
      private MinMaxBounds.Doubles f_52641_ = MinMaxBounds.Doubles.f_154779_;
      private MinMaxBounds.Doubles f_52642_ = MinMaxBounds.Doubles.f_154779_;
      @Nullable
      private ResourceKey<Biome> f_52643_;
      @Nullable
      private StructureFeature<?> f_52644_;
      @Nullable
      private ResourceKey<Level> f_52645_;
      @Nullable
      private Boolean f_52646_;
      private LightPredicate f_52647_ = LightPredicate.f_51335_;
      private BlockPredicate f_52648_ = BlockPredicate.f_17902_;
      private FluidPredicate f_52649_ = FluidPredicate.f_41094_;

      public static LocationPredicate.Builder m_52651_() {
         return new LocationPredicate.Builder();
      }

      public LocationPredicate.Builder m_153970_(MinMaxBounds.Doubles p_153971_) {
         this.f_52640_ = p_153971_;
         return this;
      }

      public LocationPredicate.Builder m_153974_(MinMaxBounds.Doubles p_153975_) {
         this.f_52641_ = p_153975_;
         return this;
      }

      public LocationPredicate.Builder m_153978_(MinMaxBounds.Doubles p_153979_) {
         this.f_52642_ = p_153979_;
         return this;
      }

      public LocationPredicate.Builder m_52656_(@Nullable ResourceKey<Biome> p_52657_) {
         this.f_52643_ = p_52657_;
         return this;
      }

      public LocationPredicate.Builder m_153972_(@Nullable StructureFeature<?> p_153973_) {
         this.f_52644_ = p_153973_;
         return this;
      }

      public LocationPredicate.Builder m_153976_(@Nullable ResourceKey<Level> p_153977_) {
         this.f_52645_ = p_153977_;
         return this;
      }

      public LocationPredicate.Builder m_153968_(LightPredicate p_153969_) {
         this.f_52647_ = p_153969_;
         return this;
      }

      public LocationPredicate.Builder m_52652_(BlockPredicate p_52653_) {
         this.f_52648_ = p_52653_;
         return this;
      }

      public LocationPredicate.Builder m_153966_(FluidPredicate p_153967_) {
         this.f_52649_ = p_153967_;
         return this;
      }

      public LocationPredicate.Builder m_52654_(Boolean p_52655_) {
         this.f_52646_ = p_52655_;
         return this;
      }

      public LocationPredicate m_52658_() {
         return new LocationPredicate(this.f_52640_, this.f_52641_, this.f_52642_, this.f_52643_, this.f_52644_, this.f_52645_, this.f_52646_, this.f_52647_, this.f_52648_, this.f_52649_);
      }
   }
}