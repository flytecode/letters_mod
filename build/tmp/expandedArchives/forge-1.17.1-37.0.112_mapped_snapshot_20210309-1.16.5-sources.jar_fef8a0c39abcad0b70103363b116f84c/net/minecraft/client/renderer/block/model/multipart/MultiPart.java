package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MultiPart implements UnbakedModel {
   private final StateDefinition<Block, BlockState> f_111962_;
   private final List<Selector> f_111963_;

   public MultiPart(StateDefinition<Block, BlockState> p_111965_, List<Selector> p_111966_) {
      this.f_111962_ = p_111965_;
      this.f_111963_ = p_111966_;
   }

   public List<Selector> m_111967_() {
      return this.f_111963_;
   }

   public Set<MultiVariant> m_111982_() {
      Set<MultiVariant> set = Sets.newHashSet();

      for(Selector selector : this.f_111963_) {
         set.add(selector.m_112020_());
      }

      return set;
   }

   public boolean equals(Object p_111984_) {
      if (this == p_111984_) {
         return true;
      } else if (!(p_111984_ instanceof MultiPart)) {
         return false;
      } else {
         MultiPart multipart = (MultiPart)p_111984_;
         return Objects.equals(this.f_111962_, multipart.f_111962_) && Objects.equals(this.f_111963_, multipart.f_111963_);
      }
   }

   public int hashCode() {
      return Objects.hash(this.f_111962_, this.f_111963_);
   }

   public Collection<ResourceLocation> m_7970_() {
      return this.m_111967_().stream().flatMap((p_111969_) -> {
         return p_111969_.m_112020_().m_7970_().stream();
      }).collect(Collectors.toSet());
   }

   public Collection<Material> m_5500_(Function<ResourceLocation, UnbakedModel> p_111976_, Set<Pair<String, String>> p_111977_) {
      return this.m_111967_().stream().flatMap((p_111981_) -> {
         return p_111981_.m_112020_().m_5500_(p_111976_, p_111977_).stream();
      }).collect(Collectors.toSet());
   }

   @Nullable
   public BakedModel m_7611_(ModelBakery p_111971_, Function<Material, TextureAtlasSprite> p_111972_, ModelState p_111973_, ResourceLocation p_111974_) {
      MultiPartBakedModel.Builder multipartbakedmodel$builder = new MultiPartBakedModel.Builder();

      for(Selector selector : this.m_111967_()) {
         BakedModel bakedmodel = selector.m_112020_().m_7611_(p_111971_, p_111972_, p_111973_, p_111974_);
         if (bakedmodel != null) {
            multipartbakedmodel$builder.m_119477_(selector.m_112021_(this.f_111962_), bakedmodel);
         }
      }

      return multipartbakedmodel$builder.m_119476_();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<MultiPart> {
      private final BlockModelDefinition.Context f_111987_;

      public Deserializer(BlockModelDefinition.Context p_111989_) {
         this.f_111987_ = p_111989_;
      }

      public MultiPart deserialize(JsonElement p_111994_, Type p_111995_, JsonDeserializationContext p_111996_) throws JsonParseException {
         return new MultiPart(this.f_111987_.m_111551_(), this.m_111990_(p_111996_, p_111994_.getAsJsonArray()));
      }

      private List<Selector> m_111990_(JsonDeserializationContext p_111991_, JsonArray p_111992_) {
         List<Selector> list = Lists.newArrayList();

         for(JsonElement jsonelement : p_111992_) {
            list.add(p_111991_.deserialize(jsonelement, Selector.class));
         }

         return list;
      }
   }
}