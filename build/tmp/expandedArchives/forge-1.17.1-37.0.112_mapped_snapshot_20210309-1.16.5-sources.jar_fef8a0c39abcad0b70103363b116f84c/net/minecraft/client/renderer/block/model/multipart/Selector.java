package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Streams;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Selector {
   private final Condition f_112015_;
   private final MultiVariant f_112016_;

   public Selector(Condition p_112018_, MultiVariant p_112019_) {
      if (p_112018_ == null) {
         throw new IllegalArgumentException("Missing condition for selector");
      } else if (p_112019_ == null) {
         throw new IllegalArgumentException("Missing variant for selector");
      } else {
         this.f_112015_ = p_112018_;
         this.f_112016_ = p_112019_;
      }
   }

   public MultiVariant m_112020_() {
      return this.f_112016_;
   }

   public Predicate<BlockState> m_112021_(StateDefinition<Block, BlockState> p_112022_) {
      return this.f_112015_.m_7289_(p_112022_);
   }

   public boolean equals(Object p_112024_) {
      return this == p_112024_;
   }

   public int hashCode() {
      return System.identityHashCode(this);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<Selector> {
      public Selector deserialize(JsonElement p_112030_, Type p_112031_, JsonDeserializationContext p_112032_) throws JsonParseException {
         JsonObject jsonobject = p_112030_.getAsJsonObject();
         return new Selector(this.m_112039_(jsonobject), p_112032_.deserialize(jsonobject.get("apply"), MultiVariant.class));
      }

      private Condition m_112039_(JsonObject p_112040_) {
         return p_112040_.has("when") ? m_112033_(GsonHelper.m_13930_(p_112040_, "when")) : Condition.f_111922_;
      }

      @VisibleForTesting
      static Condition m_112033_(JsonObject p_112034_) {
         Set<Entry<String, JsonElement>> set = p_112034_.entrySet();
         if (set.isEmpty()) {
            throw new JsonParseException("No elements found in selector");
         } else if (set.size() == 1) {
            if (p_112034_.has("OR")) {
               List<Condition> list1 = Streams.stream(GsonHelper.m_13933_(p_112034_, "OR")).map((p_112038_) -> {
                  return m_112033_(p_112038_.getAsJsonObject());
               }).collect(Collectors.toList());
               return new OrCondition(list1);
            } else if (p_112034_.has("AND")) {
               List<Condition> list = Streams.stream(GsonHelper.m_13933_(p_112034_, "AND")).map((p_112028_) -> {
                  return m_112033_(p_112028_.getAsJsonObject());
               }).collect(Collectors.toList());
               return new AndCondition(list);
            } else {
               return m_112035_(set.iterator().next());
            }
         } else {
            return new AndCondition(set.stream().map(Selector.Deserializer::m_112035_).collect(Collectors.toList()));
         }
      }

      private static Condition m_112035_(Entry<String, JsonElement> p_112036_) {
         return new KeyValueCondition(p_112036_.getKey(), p_112036_.getValue().getAsString());
      }
   }
}