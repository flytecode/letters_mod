package net.minecraft.world.level.storage.loot.providers.score;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class FixedScoreboardNameProvider implements ScoreboardNameProvider {
   final String f_165840_;

   FixedScoreboardNameProvider(String p_165842_) {
      this.f_165840_ = p_165842_;
   }

   public static ScoreboardNameProvider m_165846_(String p_165847_) {
      return new FixedScoreboardNameProvider(p_165847_);
   }

   public LootScoreProviderType m_142680_() {
      return ScoreboardNameProviders.f_165868_;
   }

   public String m_165849_() {
      return this.f_165840_;
   }

   @Nullable
   public String m_142600_(LootContext p_165845_) {
      return this.f_165840_;
   }

   public Set<LootContextParam<?>> m_142636_() {
      return ImmutableSet.of();
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<FixedScoreboardNameProvider> {
      public void m_6170_(JsonObject p_165855_, FixedScoreboardNameProvider p_165856_, JsonSerializationContext p_165857_) {
         p_165855_.addProperty("name", p_165856_.f_165840_);
      }

      public FixedScoreboardNameProvider m_7561_(JsonObject p_165863_, JsonDeserializationContext p_165864_) {
         String s = GsonHelper.m_13906_(p_165863_, "name");
         return new FixedScoreboardNameProvider(s);
      }
   }
}