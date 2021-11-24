package net.minecraft.world.level.storage.loot.providers.nbt;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class StorageNbtProvider implements NbtProvider {
   final ResourceLocation f_165631_;

   StorageNbtProvider(ResourceLocation p_165633_) {
      this.f_165631_ = p_165633_;
   }

   public LootNbtProviderType m_142624_() {
      return NbtProviders.f_165623_;
   }

   @Nullable
   public Tag m_142301_(LootContext p_165636_) {
      return p_165636_.m_78952_().m_142572_().m_129897_().m_78044_(this.f_165631_);
   }

   public Set<LootContextParam<?>> m_142677_() {
      return ImmutableSet.of();
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<StorageNbtProvider> {
      public void m_6170_(JsonObject p_165643_, StorageNbtProvider p_165644_, JsonSerializationContext p_165645_) {
         p_165643_.addProperty("source", p_165644_.f_165631_.toString());
      }

      public StorageNbtProvider m_7561_(JsonObject p_165651_, JsonDeserializationContext p_165652_) {
         String s = GsonHelper.m_13906_(p_165651_, "source");
         return new StorageNbtProvider(new ResourceLocation(s));
      }
   }
}