package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BuiltInModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class BlockModel implements UnbakedModel {
   private static final Logger f_111420_ = LogManager.getLogger();
   private static final FaceBakery f_111421_ = new FaceBakery();
   @VisibleForTesting
   static final Gson f_111415_ = (new GsonBuilder()).registerTypeAdapter(BlockModel.class, new BlockModel.Deserializer()).registerTypeAdapter(BlockElement.class, new BlockElement.Deserializer()).registerTypeAdapter(BlockElementFace.class, new BlockElementFace.Deserializer()).registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer()).registerTypeAdapter(ItemTransform.class, new ItemTransform.Deserializer()).registerTypeAdapter(ItemTransforms.class, new ItemTransforms.Deserializer()).registerTypeAdapter(ItemOverride.class, new ItemOverride.Deserializer()).create();
   private static final char f_173419_ = '#';
   public static final String f_173418_ = "particle";
   private final List<BlockElement> f_111422_;
   @Nullable
   private final BlockModel.GuiLight f_111423_;
   public final boolean f_111424_;
   private final ItemTransforms f_111425_;
   private final List<ItemOverride> f_111426_;
   public String f_111416_ = "";
   @VisibleForTesting
   public final Map<String, Either<Material, String>> f_111417_;
   @Nullable
   public BlockModel f_111418_;
   @Nullable
   protected ResourceLocation f_111419_;
   public final net.minecraftforge.client.model.BlockModelConfiguration customData = new net.minecraftforge.client.model.BlockModelConfiguration(this);

   public static BlockModel m_111461_(Reader p_111462_) {
      return GsonHelper.m_13776_(net.minecraftforge.client.model.ModelLoaderRegistry.ExpandedBlockModelDeserializer.INSTANCE, p_111462_, BlockModel.class);
   }

   public static BlockModel m_111463_(String p_111464_) {
      return m_111461_(new StringReader(p_111464_));
   }

   public BlockModel(@Nullable ResourceLocation p_111429_, List<BlockElement> p_111430_, Map<String, Either<Material, String>> p_111431_, boolean p_111432_, @Nullable BlockModel.GuiLight p_111433_, ItemTransforms p_111434_, List<ItemOverride> p_111435_) {
      this.f_111422_ = p_111430_;
      this.f_111424_ = p_111432_;
      this.f_111423_ = p_111433_;
      this.f_111417_ = p_111431_;
      this.f_111419_ = p_111429_;
      this.f_111425_ = p_111434_;
      this.f_111426_ = p_111435_;
   }

   @Deprecated
   public List<BlockElement> m_111436_() {
      if (customData.hasCustomGeometry()) return java.util.Collections.emptyList();
      return this.f_111422_.isEmpty() && this.f_111418_ != null ? this.f_111418_.m_111436_() : this.f_111422_;
   }

   @Nullable
   public ResourceLocation getParentLocation() { return f_111419_; }

   public boolean m_111476_() {
      return this.f_111418_ != null ? this.f_111418_.m_111476_() : this.f_111424_;
   }

   public BlockModel.GuiLight m_111479_() {
      if (this.f_111423_ != null) {
         return this.f_111423_;
      } else {
         return this.f_111418_ != null ? this.f_111418_.m_111479_() : BlockModel.GuiLight.SIDE;
      }
   }

   public boolean m_173420_() {
      return this.f_111419_ == null || this.f_111418_ != null && this.f_111418_.m_173420_();
   }

   public List<ItemOverride> m_111484_() {
      return this.f_111426_;
   }

   private ItemOverrides m_111446_(ModelBakery p_111447_, BlockModel p_111448_) {
      return this.f_111426_.isEmpty() ? ItemOverrides.f_111734_ : new ItemOverrides(p_111447_, p_111448_, p_111447_::m_119341_, this.f_111426_);
   }

   public ItemOverrides getOverrides(ModelBakery p_111447_, BlockModel p_111448_, Function<Material, TextureAtlasSprite> textureGetter) {
      return this.f_111426_.isEmpty() ? ItemOverrides.f_111734_ : new ItemOverrides(p_111447_, p_111448_, p_111447_::m_119341_, textureGetter, this.f_111426_);
   }

   public Collection<ResourceLocation> m_7970_() {
      Set<ResourceLocation> set = Sets.newHashSet();

      for(ItemOverride itemoverride : this.f_111426_) {
         set.add(itemoverride.m_111718_());
      }

      if (this.f_111419_ != null) {
         set.add(this.f_111419_);
      }

      return set;
   }

   public Collection<Material> m_5500_(Function<ResourceLocation, UnbakedModel> p_111469_, Set<Pair<String, String>> p_111470_) {
      Set<UnbakedModel> set = Sets.newLinkedHashSet();

      for(BlockModel blockmodel = this; blockmodel.f_111419_ != null && blockmodel.f_111418_ == null; blockmodel = blockmodel.f_111418_) {
         set.add(blockmodel);
         UnbakedModel unbakedmodel = p_111469_.apply(blockmodel.f_111419_);
         if (unbakedmodel == null) {
            f_111420_.warn("No parent '{}' while loading model '{}'", this.f_111419_, blockmodel);
         }

         if (set.contains(unbakedmodel)) {
            f_111420_.warn("Found 'parent' loop while loading model '{}' in chain: {} -> {}", blockmodel, set.stream().map(Object::toString).collect(Collectors.joining(" -> ")), this.f_111419_);
            unbakedmodel = null;
         }

         if (unbakedmodel == null) {
            blockmodel.f_111419_ = ModelBakery.f_119230_;
            unbakedmodel = p_111469_.apply(blockmodel.f_111419_);
         }

         if (!(unbakedmodel instanceof BlockModel)) {
            throw new IllegalStateException("BlockModel parent has to be a block model.");
         }

         blockmodel.f_111418_ = (BlockModel)unbakedmodel;
      }

      Set<Material> set1 = Sets.newHashSet(this.m_111480_("particle"));

      if (customData.hasCustomGeometry())
         set1.addAll(customData.getTextureDependencies(p_111469_, p_111470_));
      else
      for(BlockElement blockelement : this.m_111436_()) {
         for(BlockElementFace blockelementface : blockelement.f_111310_.values()) {
            Material material = this.m_111480_(blockelementface.f_111356_);
            if (Objects.equals(material.m_119203_(), MissingTextureAtlasSprite.m_118071_())) {
               p_111470_.add(Pair.of(blockelementface.f_111356_, this.f_111416_));
            }

            set1.add(material);
         }
      }

      this.f_111426_.forEach((p_111475_) -> {
         UnbakedModel unbakedmodel1 = p_111469_.apply(p_111475_.m_111718_());
         if (!Objects.equals(unbakedmodel1, this)) {
            set1.addAll(unbakedmodel1.m_5500_(p_111469_, p_111470_));
         }
      });
      if (this.m_111490_() == ModelBakery.f_119232_) {
         ItemModelGenerator.f_111635_.forEach((p_111467_) -> {
            set1.add(this.m_111480_(p_111467_));
         });
      }

      return set1;
   }

   @Deprecated //Forge: Use Boolean variant
   public BakedModel m_7611_(ModelBakery p_111457_, Function<Material, TextureAtlasSprite> p_111458_, ModelState p_111459_, ResourceLocation p_111460_) {
      return this.m_111449_(p_111457_, this, p_111458_, p_111459_, p_111460_, true);
   }

   public BakedModel m_111449_(ModelBakery p_111450_, BlockModel p_111451_, Function<Material, TextureAtlasSprite> p_111452_, ModelState p_111453_, ResourceLocation p_111454_, boolean p_111455_) {
      return net.minecraftforge.client.model.ModelLoaderRegistry.bakeHelper(this, p_111450_, p_111451_, p_111452_, p_111453_, p_111454_, p_111455_);
   }

   @Deprecated //Forge: exposed for our callbacks only. Use the above function.
   public BakedModel bakeVanilla(ModelBakery p_111450_, BlockModel p_111451_, Function<Material, TextureAtlasSprite> p_111452_, ModelState p_111453_, ResourceLocation p_111454_, boolean p_111455_) {
      TextureAtlasSprite textureatlassprite = p_111452_.apply(this.m_111480_("particle"));
      if (this.m_111490_() == ModelBakery.f_119233_) {
         return new BuiltInModel(this.m_111491_(), this.m_111446_(p_111450_, p_111451_), textureatlassprite, this.m_111479_().m_111526_());
      } else {
         SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(this, this.m_111446_(p_111450_, p_111451_), p_111455_)).m_119528_(textureatlassprite);

         for(BlockElement blockelement : this.m_111436_()) {
            for(Direction direction : blockelement.f_111310_.keySet()) {
               BlockElementFace blockelementface = blockelement.f_111310_.get(direction);
               TextureAtlasSprite textureatlassprite1 = p_111452_.apply(this.m_111480_(blockelementface.f_111356_));
               if (blockelementface.f_111354_ == null) {
                  simplebakedmodel$builder.m_119526_(m_111437_(blockelement, blockelementface, textureatlassprite1, direction, p_111453_, p_111454_));
               } else {
                  simplebakedmodel$builder.m_119530_(Direction.m_122384_(p_111453_.m_6189_().m_121104_(), blockelementface.f_111354_), m_111437_(blockelement, blockelementface, textureatlassprite1, direction, p_111453_, p_111454_));
               }
            }
         }

         return simplebakedmodel$builder.m_119533_();
      }
   }

   private static BakedQuad m_111437_(BlockElement p_111438_, BlockElementFace p_111439_, TextureAtlasSprite p_111440_, Direction p_111441_, ModelState p_111442_, ResourceLocation p_111443_) {
      return f_111421_.m_111600_(p_111438_.f_111308_, p_111438_.f_111309_, p_111439_, p_111440_, p_111441_, p_111442_, p_111438_.f_111311_, p_111438_.f_111312_, p_111443_);
   }

   public static BakedQuad makeBakedQuad(BlockElement p_111438_, BlockElementFace p_111439_, TextureAtlasSprite p_111440_, Direction p_111441_, ModelState p_111442_, ResourceLocation p_111443_) {
      return m_111437_(p_111438_, p_111439_, p_111440_, p_111441_, p_111442_, p_111443_);
   }

   public boolean m_111477_(String p_111478_) {
      return !MissingTextureAtlasSprite.m_118071_().equals(this.m_111480_(p_111478_).m_119203_());
   }

   public Material m_111480_(String p_111481_) {
      if (m_111488_(p_111481_)) {
         p_111481_ = p_111481_.substring(1);
      }

      List<String> list = Lists.newArrayList();

      while(true) {
         Either<Material, String> either = this.m_111485_(p_111481_);
         Optional<Material> optional = either.left();
         if (optional.isPresent()) {
            return optional.get();
         }

         p_111481_ = either.right().get();
         if (list.contains(p_111481_)) {
            f_111420_.warn("Unable to resolve texture due to reference chain {}->{} in {}", Joiner.on("->").join(list), p_111481_, this.f_111416_);
            return new Material(TextureAtlas.f_118259_, MissingTextureAtlasSprite.m_118071_());
         }

         list.add(p_111481_);
      }
   }

   private Either<Material, String> m_111485_(String p_111486_) {
      for(BlockModel blockmodel = this; blockmodel != null; blockmodel = blockmodel.f_111418_) {
         Either<Material, String> either = blockmodel.f_111417_.get(p_111486_);
         if (either != null) {
            return either;
         }
      }

      return Either.left(new Material(TextureAtlas.f_118259_, MissingTextureAtlasSprite.m_118071_()));
   }

   static boolean m_111488_(String p_111489_) {
      return p_111489_.charAt(0) == '#';
   }

   public BlockModel m_111490_() {
      return this.f_111418_ == null ? this : this.f_111418_.m_111490_();
   }

   public ItemTransforms m_111491_() {
      ItemTransform itemtransform = this.m_111444_(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
      ItemTransform itemtransform1 = this.m_111444_(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
      ItemTransform itemtransform2 = this.m_111444_(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
      ItemTransform itemtransform3 = this.m_111444_(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
      ItemTransform itemtransform4 = this.m_111444_(ItemTransforms.TransformType.HEAD);
      ItemTransform itemtransform5 = this.m_111444_(ItemTransforms.TransformType.GUI);
      ItemTransform itemtransform6 = this.m_111444_(ItemTransforms.TransformType.GROUND);
      ItemTransform itemtransform7 = this.m_111444_(ItemTransforms.TransformType.FIXED);
      return new ItemTransforms(itemtransform, itemtransform1, itemtransform2, itemtransform3, itemtransform4, itemtransform5, itemtransform6, itemtransform7);
   }

   private ItemTransform m_111444_(ItemTransforms.TransformType p_111445_) {
      return this.f_111418_ != null && !this.f_111425_.m_111810_(p_111445_) ? this.f_111418_.m_111444_(p_111445_) : this.f_111425_.m_111808_(p_111445_);
   }

   public String toString() {
      return this.f_111416_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<BlockModel> {
      private static final boolean f_173421_ = true;

      public BlockModel deserialize(JsonElement p_111498_, Type p_111499_, JsonDeserializationContext p_111500_) throws JsonParseException {
         JsonObject jsonobject = p_111498_.getAsJsonObject();
         List<BlockElement> list = this.m_111506_(p_111500_, jsonobject);
         String s = this.m_111511_(jsonobject);
         Map<String, Either<Material, String>> map = this.m_111509_(jsonobject);
         boolean flag = this.m_111501_(jsonobject);
         ItemTransforms itemtransforms = ItemTransforms.f_111786_;
         if (jsonobject.has("display")) {
            JsonObject jsonobject1 = GsonHelper.m_13930_(jsonobject, "display");
            itemtransforms = p_111500_.deserialize(jsonobject1, ItemTransforms.class);
         }

         List<ItemOverride> list1 = this.m_111494_(p_111500_, jsonobject);
         BlockModel.GuiLight blockmodel$guilight = null;
         if (jsonobject.has("gui_light")) {
            blockmodel$guilight = BlockModel.GuiLight.m_111527_(GsonHelper.m_13906_(jsonobject, "gui_light"));
         }

         ResourceLocation resourcelocation = s.isEmpty() ? null : new ResourceLocation(s);
         return new BlockModel(resourcelocation, list, map, flag, blockmodel$guilight, itemtransforms, list1);
      }

      protected List<ItemOverride> m_111494_(JsonDeserializationContext p_111495_, JsonObject p_111496_) {
         List<ItemOverride> list = Lists.newArrayList();
         if (p_111496_.has("overrides")) {
            for(JsonElement jsonelement : GsonHelper.m_13933_(p_111496_, "overrides")) {
               list.add(p_111495_.deserialize(jsonelement, ItemOverride.class));
            }
         }

         return list;
      }

      private Map<String, Either<Material, String>> m_111509_(JsonObject p_111510_) {
         ResourceLocation resourcelocation = TextureAtlas.f_118259_;
         Map<String, Either<Material, String>> map = Maps.newHashMap();
         if (p_111510_.has("textures")) {
            JsonObject jsonobject = GsonHelper.m_13930_(p_111510_, "textures");

            for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
               map.put(entry.getKey(), m_111503_(resourcelocation, entry.getValue().getAsString()));
            }
         }

         return map;
      }

      private static Either<Material, String> m_111503_(ResourceLocation p_111504_, String p_111505_) {
         if (BlockModel.m_111488_(p_111505_)) {
            return Either.right(p_111505_.substring(1));
         } else {
            ResourceLocation resourcelocation = ResourceLocation.m_135820_(p_111505_);
            if (resourcelocation == null) {
               throw new JsonParseException(p_111505_ + " is not valid resource location");
            } else {
               return Either.left(new Material(p_111504_, resourcelocation));
            }
         }
      }

      private String m_111511_(JsonObject p_111512_) {
         return GsonHelper.m_13851_(p_111512_, "parent", "");
      }

      protected boolean m_111501_(JsonObject p_111502_) {
         return GsonHelper.m_13855_(p_111502_, "ambientocclusion", true);
      }

      protected List<BlockElement> m_111506_(JsonDeserializationContext p_111507_, JsonObject p_111508_) {
         List<BlockElement> list = Lists.newArrayList();
         if (p_111508_.has("elements")) {
            for(JsonElement jsonelement : GsonHelper.m_13933_(p_111508_, "elements")) {
               list.add(p_111507_.deserialize(jsonelement, BlockElement.class));
            }
         }

         return list;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum GuiLight {
      FRONT("front"),
      SIDE("side");

      private final String f_111519_;

      private GuiLight(String p_111525_) {
         this.f_111519_ = p_111525_;
      }

      public static BlockModel.GuiLight m_111527_(String p_111528_) {
         for(BlockModel.GuiLight blockmodel$guilight : values()) {
            if (blockmodel$guilight.f_111519_.equals(p_111528_)) {
               return blockmodel$guilight;
            }
         }

         throw new IllegalArgumentException("Invalid gui light: " + p_111528_);
      }

      public boolean m_111526_() {
         return this == SIDE;
      }

      public String getSerializedName() { return f_111519_; }
   }

   @OnlyIn(Dist.CLIENT)
   public static class LoopException extends RuntimeException {
      public LoopException(String p_173424_) {
         super(p_173424_);
      }
   }
}
