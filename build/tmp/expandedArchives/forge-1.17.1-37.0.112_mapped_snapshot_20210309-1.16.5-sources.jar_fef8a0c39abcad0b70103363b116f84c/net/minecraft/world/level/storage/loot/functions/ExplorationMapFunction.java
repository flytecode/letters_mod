package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Locale;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExplorationMapFunction extends LootItemConditionalFunction {
   static final Logger f_80523_ = LogManager.getLogger();
   public static final StructureFeature<?> f_80521_ = StructureFeature.f_67027_;
   public static final String f_165201_ = "mansion";
   public static final MapDecoration.Type f_80522_ = MapDecoration.Type.MANSION;
   public static final byte f_165202_ = 2;
   public static final int f_165203_ = 50;
   public static final boolean f_165204_ = true;
   final StructureFeature<?> f_80524_;
   final MapDecoration.Type f_80525_;
   final byte f_80526_;
   final int f_80527_;
   final boolean f_80528_;

   ExplorationMapFunction(LootItemCondition[] p_80531_, StructureFeature<?> p_80532_, MapDecoration.Type p_80533_, byte p_80534_, int p_80535_, boolean p_80536_) {
      super(p_80531_);
      this.f_80524_ = p_80532_;
      this.f_80525_ = p_80533_;
      this.f_80526_ = p_80534_;
      this.f_80527_ = p_80535_;
      this.f_80528_ = p_80536_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80745_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81460_);
   }

   public ItemStack m_7372_(ItemStack p_80547_, LootContext p_80548_) {
      if (!p_80547_.m_150930_(Items.f_42676_)) {
         return p_80547_;
      } else {
         Vec3 vec3 = p_80548_.m_78953_(LootContextParams.f_81460_);
         if (vec3 != null) {
            ServerLevel serverlevel = p_80548_.m_78952_();
            BlockPos blockpos = serverlevel.m_8717_(this.f_80524_, new BlockPos(vec3), this.f_80527_, this.f_80528_);
            if (blockpos != null) {
               ItemStack itemstack = MapItem.m_42886_(serverlevel, blockpos.m_123341_(), blockpos.m_123343_(), this.f_80526_, true, true);
               MapItem.m_42850_(serverlevel, itemstack);
               MapItemSavedData.m_77925_(itemstack, blockpos, "+", this.f_80525_);
               itemstack.m_41714_(new TranslatableComponent("filled_map." + this.f_80524_.m_67098_().toLowerCase(Locale.ROOT)));
               return itemstack;
            }
         }

         return p_80547_;
      }
   }

   public static ExplorationMapFunction.Builder m_80554_() {
      return new ExplorationMapFunction.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<ExplorationMapFunction.Builder> {
      private StructureFeature<?> f_80562_ = ExplorationMapFunction.f_80521_;
      private MapDecoration.Type f_80563_ = ExplorationMapFunction.f_80522_;
      private byte f_80564_ = 2;
      private int f_80565_ = 50;
      private boolean f_80566_ = true;

      protected ExplorationMapFunction.Builder m_6477_() {
         return this;
      }

      public ExplorationMapFunction.Builder m_80571_(StructureFeature<?> p_80572_) {
         this.f_80562_ = p_80572_;
         return this;
      }

      public ExplorationMapFunction.Builder m_80573_(MapDecoration.Type p_80574_) {
         this.f_80563_ = p_80574_;
         return this;
      }

      public ExplorationMapFunction.Builder m_80569_(byte p_80570_) {
         this.f_80564_ = p_80570_;
         return this;
      }

      public ExplorationMapFunction.Builder m_165205_(int p_165206_) {
         this.f_80565_ = p_165206_;
         return this;
      }

      public ExplorationMapFunction.Builder m_80575_(boolean p_80576_) {
         this.f_80566_ = p_80576_;
         return this;
      }

      public LootItemFunction m_7453_() {
         return new ExplorationMapFunction(this.m_80699_(), this.f_80562_, this.f_80563_, this.f_80564_, this.f_80565_, this.f_80566_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<ExplorationMapFunction> {
      public void m_6170_(JsonObject p_80587_, ExplorationMapFunction p_80588_, JsonSerializationContext p_80589_) {
         super.m_6170_(p_80587_, p_80588_, p_80589_);
         if (!p_80588_.f_80524_.equals(ExplorationMapFunction.f_80521_)) {
            p_80587_.add("destination", p_80589_.serialize(p_80588_.f_80524_.m_67098_()));
         }

         if (p_80588_.f_80525_ != ExplorationMapFunction.f_80522_) {
            p_80587_.add("decoration", p_80589_.serialize(p_80588_.f_80525_.toString().toLowerCase(Locale.ROOT)));
         }

         if (p_80588_.f_80526_ != 2) {
            p_80587_.addProperty("zoom", p_80588_.f_80526_);
         }

         if (p_80588_.f_80527_ != 50) {
            p_80587_.addProperty("search_radius", p_80588_.f_80527_);
         }

         if (!p_80588_.f_80528_) {
            p_80587_.addProperty("skip_existing_chunks", p_80588_.f_80528_);
         }

      }

      public ExplorationMapFunction m_6821_(JsonObject p_80583_, JsonDeserializationContext p_80584_, LootItemCondition[] p_80585_) {
         StructureFeature<?> structurefeature = m_80580_(p_80583_);
         String s = p_80583_.has("decoration") ? GsonHelper.m_13906_(p_80583_, "decoration") : "mansion";
         MapDecoration.Type mapdecoration$type = ExplorationMapFunction.f_80522_;

         try {
            mapdecoration$type = MapDecoration.Type.valueOf(s.toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException illegalargumentexception) {
            ExplorationMapFunction.f_80523_.error("Error while parsing loot table decoration entry. Found {}. Defaulting to {}", s, ExplorationMapFunction.f_80522_);
         }

         byte b0 = GsonHelper.m_13816_(p_80583_, "zoom", (byte)2);
         int i = GsonHelper.m_13824_(p_80583_, "search_radius", 50);
         boolean flag = GsonHelper.m_13855_(p_80583_, "skip_existing_chunks", true);
         return new ExplorationMapFunction(p_80585_, structurefeature, mapdecoration$type, b0, i, flag);
      }

      private static StructureFeature<?> m_80580_(JsonObject p_80581_) {
         if (p_80581_.has("destination")) {
            String s = GsonHelper.m_13906_(p_80581_, "destination");
            StructureFeature<?> structurefeature = StructureFeature.f_67012_.get(s.toLowerCase(Locale.ROOT));
            if (structurefeature != null) {
               return structurefeature;
            }
         }

         return ExplorationMapFunction.f_80521_;
      }
   }
}