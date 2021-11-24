package net.minecraft.data.models.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ModelTemplate {
   private final Optional<ResourceLocation> f_125585_;
   private final Set<TextureSlot> f_125586_;
   private final Optional<String> f_125587_;

   public ModelTemplate(Optional<ResourceLocation> p_125589_, Optional<String> p_125590_, TextureSlot... p_125591_) {
      this.f_125585_ = p_125589_;
      this.f_125587_ = p_125590_;
      this.f_125586_ = ImmutableSet.copyOf(p_125591_);
   }

   public ResourceLocation m_125592_(Block p_125593_, TextureMapping p_125594_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_125595_) {
      return this.m_125612_(ModelLocationUtils.m_125578_(p_125593_, this.f_125587_.orElse("")), p_125594_, p_125595_);
   }

   public ResourceLocation m_125596_(Block p_125597_, String p_125598_, TextureMapping p_125599_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_125600_) {
      return this.m_125612_(ModelLocationUtils.m_125578_(p_125597_, p_125598_ + (String)this.f_125587_.orElse("")), p_125599_, p_125600_);
   }

   public ResourceLocation m_125616_(Block p_125617_, String p_125618_, TextureMapping p_125619_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_125620_) {
      return this.m_125612_(ModelLocationUtils.m_125578_(p_125617_, p_125618_), p_125619_, p_125620_);
   }

   public ResourceLocation m_125612_(ResourceLocation p_125613_, TextureMapping p_125614_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_125615_) {
      Map<TextureSlot, ResourceLocation> map = this.m_125608_(p_125614_);
      p_125615_.accept(p_125613_, () -> {
         JsonObject jsonobject = new JsonObject();
         this.f_125585_.ifPresent((p_176461_) -> {
            jsonobject.addProperty("parent", p_176461_.toString());
         });
         if (!map.isEmpty()) {
            JsonObject jsonobject1 = new JsonObject();
            map.forEach((p_176457_, p_176458_) -> {
               jsonobject1.addProperty(p_176457_.m_125897_(), p_176458_.toString());
            });
            jsonobject.add("textures", jsonobject1);
         }

         return jsonobject;
      });
      return p_125613_;
   }

   private Map<TextureSlot, ResourceLocation> m_125608_(TextureMapping p_125609_) {
      return Streams.concat(this.f_125586_.stream(), p_125609_.m_125742_()).collect(ImmutableMap.toImmutableMap(Function.identity(), p_125609_::m_125756_));
   }
}