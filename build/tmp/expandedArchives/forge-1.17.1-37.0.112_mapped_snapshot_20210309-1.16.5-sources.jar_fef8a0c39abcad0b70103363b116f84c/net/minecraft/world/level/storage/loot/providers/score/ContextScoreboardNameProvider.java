package net.minecraft.world.level.storage.loot.providers.score;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class ContextScoreboardNameProvider implements ScoreboardNameProvider {
   final LootContext.EntityTarget f_165803_;

   ContextScoreboardNameProvider(LootContext.EntityTarget p_165805_) {
      this.f_165803_ = p_165805_;
   }

   public static ScoreboardNameProvider m_165807_(LootContext.EntityTarget p_165808_) {
      return new ContextScoreboardNameProvider(p_165808_);
   }

   public LootScoreProviderType m_142680_() {
      return ScoreboardNameProviders.f_165869_;
   }

   @Nullable
   public String m_142600_(LootContext p_165810_) {
      Entity entity = p_165810_.m_78953_(this.f_165803_.m_79003_());
      return entity != null ? entity.m_6302_() : null;
   }

   public Set<LootContextParam<?>> m_142636_() {
      return ImmutableSet.of(this.f_165803_.m_79003_());
   }

   public static class InlineSerializer implements GsonAdapterFactory.InlineSerializer<ContextScoreboardNameProvider> {
      public JsonElement m_142413_(ContextScoreboardNameProvider p_165817_, JsonSerializationContext p_165818_) {
         return p_165818_.serialize(p_165817_.f_165803_);
      }

      public ContextScoreboardNameProvider m_142268_(JsonElement p_165823_, JsonDeserializationContext p_165824_) {
         LootContext.EntityTarget lootcontext$entitytarget = p_165824_.deserialize(p_165823_, LootContext.EntityTarget.class);
         return new ContextScoreboardNameProvider(lootcontext$entitytarget);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ContextScoreboardNameProvider> {
      public void m_6170_(JsonObject p_165830_, ContextScoreboardNameProvider p_165831_, JsonSerializationContext p_165832_) {
         p_165830_.addProperty("target", p_165831_.f_165803_.name());
      }

      public ContextScoreboardNameProvider m_7561_(JsonObject p_165838_, JsonDeserializationContext p_165839_) {
         LootContext.EntityTarget lootcontext$entitytarget = GsonHelper.m_13836_(p_165838_, "target", p_165839_, LootContext.EntityTarget.class);
         return new ContextScoreboardNameProvider(lootcontext$entitytarget);
      }
   }
}