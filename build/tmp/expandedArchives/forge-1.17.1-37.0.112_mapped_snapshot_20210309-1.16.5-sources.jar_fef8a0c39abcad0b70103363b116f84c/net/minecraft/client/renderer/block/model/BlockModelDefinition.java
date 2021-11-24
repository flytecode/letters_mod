package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.multipart.MultiPart;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockModelDefinition {
   private final Map<String, MultiVariant> f_111532_ = Maps.newLinkedHashMap();
   private MultiPart f_111533_;

   public static BlockModelDefinition m_111540_(BlockModelDefinition.Context p_111541_, Reader p_111542_) {
      return GsonHelper.m_13776_(p_111541_.f_111548_, p_111542_, BlockModelDefinition.class);
   }

   public BlockModelDefinition(Map<String, MultiVariant> p_111537_, MultiPart p_111538_) {
      this.f_111533_ = p_111538_;
      this.f_111532_.putAll(p_111537_);
   }

   public BlockModelDefinition(List<BlockModelDefinition> p_111535_) {
      BlockModelDefinition blockmodeldefinition = null;

      for(BlockModelDefinition blockmodeldefinition1 : p_111535_) {
         if (blockmodeldefinition1.m_111543_()) {
            this.f_111532_.clear();
            blockmodeldefinition = blockmodeldefinition1;
         }

         this.f_111532_.putAll(blockmodeldefinition1.f_111532_);
      }

      if (blockmodeldefinition != null) {
         this.f_111533_ = blockmodeldefinition.f_111533_;
      }

   }

   @VisibleForTesting
   public boolean m_173425_(String p_173426_) {
      return this.f_111532_.get(p_173426_) != null;
   }

   @VisibleForTesting
   public MultiVariant m_173428_(String p_173429_) {
      MultiVariant multivariant = this.f_111532_.get(p_173429_);
      if (multivariant == null) {
         throw new BlockModelDefinition.MissingVariantException();
      } else {
         return multivariant;
      }
   }

   public boolean equals(Object p_111546_) {
      if (this == p_111546_) {
         return true;
      } else {
         if (p_111546_ instanceof BlockModelDefinition) {
            BlockModelDefinition blockmodeldefinition = (BlockModelDefinition)p_111546_;
            if (this.f_111532_.equals(blockmodeldefinition.f_111532_)) {
               return this.m_111543_() ? this.f_111533_.equals(blockmodeldefinition.f_111533_) : !blockmodeldefinition.m_111543_();
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return 31 * this.f_111532_.hashCode() + (this.m_111543_() ? this.f_111533_.hashCode() : 0);
   }

   public Map<String, MultiVariant> m_111539_() {
      return this.f_111532_;
   }

   @VisibleForTesting
   public Set<MultiVariant> m_173427_() {
      Set<MultiVariant> set = Sets.newHashSet(this.f_111532_.values());
      if (this.m_111543_()) {
         set.addAll(this.f_111533_.m_111982_());
      }

      return set;
   }

   public boolean m_111543_() {
      return this.f_111533_ != null;
   }

   public MultiPart m_111544_() {
      return this.f_111533_;
   }

   @OnlyIn(Dist.CLIENT)
   public static final class Context {
      protected final Gson f_111548_ = (new GsonBuilder()).registerTypeAdapter(BlockModelDefinition.class, new BlockModelDefinition.Deserializer()).registerTypeAdapter(Variant.class, new Variant.Deserializer()).registerTypeAdapter(MultiVariant.class, new MultiVariant.Deserializer()).registerTypeAdapter(MultiPart.class, new MultiPart.Deserializer(this)).registerTypeAdapter(Selector.class, new Selector.Deserializer()).create();
      private StateDefinition<Block, BlockState> f_111549_;

      public StateDefinition<Block, BlockState> m_111551_() {
         return this.f_111549_;
      }

      public void m_111552_(StateDefinition<Block, BlockState> p_111553_) {
         this.f_111549_ = p_111553_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<BlockModelDefinition> {
      public BlockModelDefinition deserialize(JsonElement p_111559_, Type p_111560_, JsonDeserializationContext p_111561_) throws JsonParseException {
         JsonObject jsonobject = p_111559_.getAsJsonObject();
         Map<String, MultiVariant> map = this.m_111555_(p_111561_, jsonobject);
         MultiPart multipart = this.m_111562_(p_111561_, jsonobject);
         if (!map.isEmpty() || multipart != null && !multipart.m_111982_().isEmpty()) {
            return new BlockModelDefinition(map, multipart);
         } else {
            throw new JsonParseException("Neither 'variants' nor 'multipart' found");
         }
      }

      protected Map<String, MultiVariant> m_111555_(JsonDeserializationContext p_111556_, JsonObject p_111557_) {
         Map<String, MultiVariant> map = Maps.newHashMap();
         if (p_111557_.has("variants")) {
            JsonObject jsonobject = GsonHelper.m_13930_(p_111557_, "variants");

            for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
               map.put(entry.getKey(), p_111556_.deserialize(entry.getValue(), MultiVariant.class));
            }
         }

         return map;
      }

      @Nullable
      protected MultiPart m_111562_(JsonDeserializationContext p_111563_, JsonObject p_111564_) {
         if (!p_111564_.has("multipart")) {
            return null;
         } else {
            JsonArray jsonarray = GsonHelper.m_13933_(p_111564_, "multipart");
            return p_111563_.deserialize(jsonarray, MultiPart.class);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   protected class MissingVariantException extends RuntimeException {
   }
}