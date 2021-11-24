package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PredicateManager extends SimpleJsonResourceReloadListener {
   private static final Logger f_79227_ = LogManager.getLogger();
   private static final Gson f_79228_ = Deserializers.m_78798_().create();
   private Map<ResourceLocation, LootItemCondition> f_79229_ = ImmutableMap.of();

   public PredicateManager() {
      super(f_79228_, "predicates");
   }

   @Nullable
   public LootItemCondition m_79252_(ResourceLocation p_79253_) {
      return this.f_79229_.get(p_79253_);
   }

   protected void m_5787_(Map<ResourceLocation, JsonElement> p_79249_, ResourceManager p_79250_, ProfilerFiller p_79251_) {
      Builder<ResourceLocation, LootItemCondition> builder = ImmutableMap.builder();
      p_79249_.forEach((p_79235_, p_79236_) -> {
         try {
            if (p_79236_.isJsonArray()) {
               LootItemCondition[] alootitemcondition = f_79228_.fromJson(p_79236_, LootItemCondition[].class);
               builder.put(p_79235_, new PredicateManager.CompositePredicate(alootitemcondition));
            } else {
               LootItemCondition lootitemcondition = f_79228_.fromJson(p_79236_, LootItemCondition.class);
               builder.put(p_79235_, lootitemcondition);
            }
         } catch (Exception exception) {
            f_79227_.error("Couldn't parse loot table {}", p_79235_, exception);
         }

      });
      Map<ResourceLocation, LootItemCondition> map = builder.build();
      ValidationContext validationcontext = new ValidationContext(LootContextParamSets.f_81420_, map::get, (p_79255_) -> {
         return null;
      });
      map.forEach((p_79239_, p_79240_) -> {
         p_79240_.m_6169_(validationcontext.m_79367_("{" + p_79239_ + "}", p_79239_));
      });
      validationcontext.m_79352_().forEach((p_79246_, p_79247_) -> {
         f_79227_.warn("Found validation problem in {}: {}", p_79246_, p_79247_);
      });
      this.f_79229_ = map;
   }

   public Set<ResourceLocation> m_79232_() {
      return Collections.unmodifiableSet(this.f_79229_.keySet());
   }

   static class CompositePredicate implements LootItemCondition {
      private final LootItemCondition[] f_79256_;
      private final Predicate<LootContext> f_79257_;

      CompositePredicate(LootItemCondition[] p_79259_) {
         this.f_79256_ = p_79259_;
         this.f_79257_ = LootItemConditions.m_81834_(p_79259_);
      }

      public final boolean test(LootContext p_79264_) {
         return this.f_79257_.test(p_79264_);
      }

      public void m_6169_(ValidationContext p_79266_) {
         LootItemCondition.super.m_6169_(p_79266_);

         for(int i = 0; i < this.f_79256_.length; ++i) {
            this.f_79256_[i].m_6169_(p_79266_.m_79365_(".term[" + i + "]"));
         }

      }

      public LootItemConditionType m_7940_() {
         throw new UnsupportedOperationException();
      }
   }
}