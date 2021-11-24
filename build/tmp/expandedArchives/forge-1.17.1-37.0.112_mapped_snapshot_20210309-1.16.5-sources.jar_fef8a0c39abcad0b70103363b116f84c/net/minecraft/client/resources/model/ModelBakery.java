package net.minecraft.client.resources.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.multipart.MultiPart;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.texture.AtlasSet;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ModelBakery {
   public static final Material f_119219_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/fire_0"));
   public static final Material f_119220_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/fire_1"));
   public static final Material f_119221_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/lava_flow"));
   public static final Material f_119222_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/water_flow"));
   public static final Material f_119223_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/water_overlay"));
   public static final Material f_119224_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/banner_base"));
   public static final Material f_119225_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/shield_base"));
   public static final Material f_119226_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/shield_base_nopattern"));
   public static final int f_174875_ = 10;
   public static final List<ResourceLocation> f_119227_ = IntStream.range(0, 10).mapToObj((p_119253_) -> {
      return new ResourceLocation("block/destroy_stage_" + p_119253_);
   }).collect(Collectors.toList());
   public static final List<ResourceLocation> f_119228_ = f_119227_.stream().map((p_119371_) -> {
      return new ResourceLocation("textures/" + p_119371_.m_135815_() + ".png");
   }).collect(Collectors.toList());
   public static final List<RenderType> f_119229_ = f_119228_.stream().map(RenderType::m_110494_).collect(Collectors.toList());
   protected static final Set<Material> f_119234_ = Util.m_137469_(Sets.newHashSet(), (p_119313_) -> {
      p_119313_.add(f_119222_);
      p_119313_.add(f_119221_);
      p_119313_.add(f_119223_);
      p_119313_.add(f_119219_);
      p_119313_.add(f_119220_);
      p_119313_.add(BellRenderer.f_112227_);
      p_119313_.add(ConduitRenderer.f_112378_);
      p_119313_.add(ConduitRenderer.f_112379_);
      p_119313_.add(ConduitRenderer.f_112380_);
      p_119313_.add(ConduitRenderer.f_112381_);
      p_119313_.add(ConduitRenderer.f_112382_);
      p_119313_.add(ConduitRenderer.f_112383_);
      p_119313_.add(EnchantTableRenderer.f_112405_);
      p_119313_.add(f_119224_);
      p_119313_.add(f_119225_);
      p_119313_.add(f_119226_);

      for(ResourceLocation resourcelocation : f_119227_) {
         p_119313_.add(new Material(TextureAtlas.f_118259_, resourcelocation));
      }

      p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39693_));
      p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39694_));
      p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39695_));
      p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39696_));
      p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39697_));
      Sheets.m_110780_(p_119313_::add);
   });
   static final int f_174876_ = -1;
   private static final int f_174877_ = 0;
   private static final Logger f_119235_ = LogManager.getLogger();
   private static final String f_174878_ = "builtin/";
   private static final String f_174879_ = "builtin/generated";
   private static final String f_174880_ = "builtin/entity";
   private static final String f_174881_ = "missing";
   public static final ModelResourceLocation f_119230_ = new ModelResourceLocation("builtin/missing", "missing");
   private static final String f_119236_ = f_119230_.toString();
   @VisibleForTesting
   public static final String f_119231_ = ("{    'textures': {       'particle': '" + MissingTextureAtlasSprite.m_118071_().m_135815_() + "',       'missingno': '" + MissingTextureAtlasSprite.m_118071_().m_135815_() + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}").replace('\'', '"');
   private static final Map<String, String> f_119237_ = Maps.newHashMap(ImmutableMap.of("missing", f_119231_));
   private static final Splitter f_119238_ = Splitter.on(',');
   private static final Splitter f_119239_ = Splitter.on('=').limit(2);
   public static final BlockModel f_119232_ = Util.m_137469_(BlockModel.m_111463_("{\"gui_light\": \"front\"}"), (p_119359_) -> {
      p_119359_.f_111416_ = "generation marker";
   });
   public static final BlockModel f_119233_ = Util.m_137469_(BlockModel.m_111463_("{\"gui_light\": \"side\"}"), (p_119297_) -> {
      p_119297_.f_111416_ = "block entity marker";
   });
   private static final StateDefinition<Block, BlockState> f_119240_ = (new StateDefinition.Builder<Block, BlockState>(Blocks.f_50016_)).m_61104_(BooleanProperty.m_61465_("map")).m_61101_(Block::m_49966_, BlockState::new);
   private static final ItemModelGenerator f_119241_ = new ItemModelGenerator();
   private static final Map<ResourceLocation, StateDefinition<Block, BlockState>> f_119242_ = ImmutableMap.of(new ResourceLocation("item_frame"), f_119240_, new ResourceLocation("glow_item_frame"), f_119240_);
   protected final ResourceManager f_119243_;
   @Nullable
   private AtlasSet f_119244_;
   private final BlockColors f_119209_;
   private final Set<ResourceLocation> f_119210_ = Sets.newHashSet();
   private final BlockModelDefinition.Context f_119211_ = new BlockModelDefinition.Context();
   private final Map<ResourceLocation, UnbakedModel> f_119212_ = Maps.newHashMap();
   private final Map<Triple<ResourceLocation, Transformation, Boolean>, BakedModel> f_119213_ = Maps.newHashMap();
   private final Map<ResourceLocation, UnbakedModel> f_119214_ = Maps.newHashMap();
   private final Map<ResourceLocation, BakedModel> f_119215_ = Maps.newHashMap();
   private Map<ResourceLocation, Pair<TextureAtlas, TextureAtlas.Preparations>> f_119216_;
   private int f_119217_ = 1;
   private final Object2IntMap<BlockState> f_119218_ = Util.m_137469_(new Object2IntOpenHashMap<>(), (p_119309_) -> {
      p_119309_.defaultReturnValue(-1);
   });

   public ModelBakery(ResourceManager p_119247_, BlockColors p_119248_, ProfilerFiller p_119249_, int p_119250_) {
      this(p_119247_, p_119248_, true);
      processLoading(p_119249_, p_119250_);
   }

   protected ModelBakery(ResourceManager p_119247_, BlockColors p_119248_, boolean vanillaBakery) {
      this.f_119243_ = p_119247_;
      this.f_119209_ = p_119248_;
   }

   protected void processLoading(ProfilerFiller p_119249_, int p_119250_) {
      net.minecraftforge.client.model.ModelLoaderRegistry.onModelLoadingStart();
      p_119249_.m_6180_("missing_model");

      try {
         this.f_119212_.put(f_119230_, this.m_119364_(f_119230_));
         this.m_119306_(f_119230_);
      } catch (IOException ioexception) {
         f_119235_.error("Error loading missing model, should never happen :(", (Throwable)ioexception);
         throw new RuntimeException(ioexception);
      }

      p_119249_.m_6182_("static_definitions");
      f_119242_.forEach((p_119347_, p_119348_) -> {
         p_119348_.m_61056_().forEach((p_174905_) -> {
            this.m_119306_(BlockModelShaper.m_110889_(p_119347_, p_174905_));
         });
      });
      p_119249_.m_6182_("blocks");

      for(Block block : Registry.f_122824_) {
         block.m_49965_().m_61056_().forEach((p_119264_) -> {
            this.m_119306_(BlockModelShaper.m_110895_(p_119264_));
         });
      }

      p_119249_.m_6182_("items");

      for(ResourceLocation resourcelocation : Registry.f_122827_.m_6566_()) {
         this.m_119306_(new ModelResourceLocation(resourcelocation, "inventory"));
      }

      p_119249_.m_6182_("special");
      this.m_119306_(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
      this.m_119306_(new ModelResourceLocation("minecraft:spyglass_in_hand#inventory"));
      for (ResourceLocation rl : getSpecialModels()) {
         addModelToCache(rl);
      }
      p_119249_.m_6182_("textures");
      Set<Pair<String, String>> set = Sets.newLinkedHashSet();
      Set<Material> set1 = this.f_119214_.values().stream().flatMap((p_119340_) -> {
         return p_119340_.m_5500_(this::m_119341_, set).stream();
      }).collect(Collectors.toSet());
      set1.addAll(f_119234_);
      net.minecraftforge.client.ForgeHooksClient.gatherFluidTextures(set1);
      set.stream().filter((p_119357_) -> {
         return !p_119357_.getSecond().equals(f_119236_);
      }).forEach((p_119292_) -> {
         f_119235_.warn("Unable to resolve texture reference: {} in {}", p_119292_.getFirst(), p_119292_.getSecond());
      });
      Map<ResourceLocation, List<Material>> map = set1.stream().collect(Collectors.groupingBy(Material::m_119193_));
      p_119249_.m_6182_("stitching");
      this.f_119216_ = Maps.newHashMap();

      for(Entry<ResourceLocation, List<Material>> entry : map.entrySet()) {
         TextureAtlas textureatlas = new TextureAtlas(entry.getKey());
         TextureAtlas.Preparations textureatlas$preparations = textureatlas.m_118307_(this.f_119243_, entry.getValue().stream().map(Material::m_119203_), p_119249_, p_119250_);
         this.f_119216_.put(entry.getKey(), Pair.of(textureatlas, textureatlas$preparations));
      }

      p_119249_.m_7238_();
   }

   public AtlasSet m_119298_(TextureManager p_119299_, ProfilerFiller p_119300_) {
      p_119300_.m_6180_("atlas");

      for(Pair<TextureAtlas, TextureAtlas.Preparations> pair : this.f_119216_.values()) {
         TextureAtlas textureatlas = pair.getFirst();
         TextureAtlas.Preparations textureatlas$preparations = pair.getSecond();
         textureatlas.m_118312_(textureatlas$preparations);
         p_119299_.m_118495_(textureatlas.m_118330_(), textureatlas);
         p_119299_.m_174784_(textureatlas.m_118330_());
         textureatlas.m_118322_(textureatlas$preparations);
      }

      this.f_119244_ = new AtlasSet(this.f_119216_.values().stream().map(Pair::getFirst).collect(Collectors.toList()));
      p_119300_.m_6182_("baking");
      this.f_119214_.keySet().forEach((p_119369_) -> {
         BakedModel bakedmodel = null;

         try {
            bakedmodel = this.m_119349_(p_119369_, BlockModelRotation.X0_Y0);
         } catch (Exception exception) {
            exception.printStackTrace();
            f_119235_.warn("Unable to bake model: '{}': {}", p_119369_, exception);
         }

         if (bakedmodel != null) {
            this.f_119215_.put(p_119369_, bakedmodel);
         }

      });
      p_119300_.m_7238_();
      return this.f_119244_;
   }

   private static Predicate<BlockState> m_119273_(StateDefinition<Block, BlockState> p_119274_, String p_119275_) {
      Map<Property<?>, Comparable<?>> map = Maps.newHashMap();

      for(String s : f_119238_.split(p_119275_)) {
         Iterator<String> iterator = f_119239_.split(s).iterator();
         if (iterator.hasNext()) {
            String s1 = iterator.next();
            Property<?> property = p_119274_.m_61081_(s1);
            if (property != null && iterator.hasNext()) {
               String s2 = iterator.next();
               Comparable<?> comparable = m_119276_(property, s2);
               if (comparable == null) {
                  throw new RuntimeException("Unknown value: '" + s2 + "' for blockstate property: '" + s1 + "' " + property.m_6908_());
               }

               map.put(property, comparable);
            } else if (!s1.isEmpty()) {
               throw new RuntimeException("Unknown blockstate property: '" + s1 + "'");
            }
         }
      }

      Block block = p_119274_.m_61091_();
      return (p_119262_) -> {
         if (p_119262_ != null && p_119262_.m_60713_(block)) {
            for(Entry<Property<?>, Comparable<?>> entry : map.entrySet()) {
               if (!Objects.equals(p_119262_.m_61143_(entry.getKey()), entry.getValue())) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      };
   }

   @Nullable
   static <T extends Comparable<T>> T m_119276_(Property<T> p_119277_, String p_119278_) {
      return p_119277_.m_6215_(p_119278_).orElse((T)null);
   }

   public UnbakedModel m_119341_(ResourceLocation p_119342_) {
      if (this.f_119212_.containsKey(p_119342_)) {
         return this.f_119212_.get(p_119342_);
      } else if (this.f_119210_.contains(p_119342_)) {
         throw new IllegalStateException("Circular reference while loading " + p_119342_);
      } else {
         this.f_119210_.add(p_119342_);
         UnbakedModel unbakedmodel = this.f_119212_.get(f_119230_);

         while(!this.f_119210_.isEmpty()) {
            ResourceLocation resourcelocation = this.f_119210_.iterator().next();

            try {
               if (!this.f_119212_.containsKey(resourcelocation)) {
                  this.m_119362_(resourcelocation);
               }
            } catch (ModelBakery.BlockStateDefinitionException modelbakery$blockstatedefinitionexception) {
               f_119235_.warn(modelbakery$blockstatedefinitionexception.getMessage());
               this.f_119212_.put(resourcelocation, unbakedmodel);
            } catch (Exception exception) {
               f_119235_.warn("Unable to load model: '{}' referenced from: {}: {}", resourcelocation, p_119342_, exception);
               this.f_119212_.put(resourcelocation, unbakedmodel);
            } finally {
               this.f_119210_.remove(resourcelocation);
            }
         }

         return this.f_119212_.getOrDefault(p_119342_, unbakedmodel);
      }
   }

   private void m_119362_(ResourceLocation p_119363_) throws Exception {
      if (!(p_119363_ instanceof ModelResourceLocation)) {
         this.m_119352_(p_119363_, this.m_119364_(p_119363_));
      } else {
         ModelResourceLocation modelresourcelocation = (ModelResourceLocation)p_119363_;
         if (Objects.equals(modelresourcelocation.m_119448_(), "inventory")) {
            ResourceLocation resourcelocation2 = new ResourceLocation(p_119363_.m_135827_(), "item/" + p_119363_.m_135815_());
            BlockModel blockmodel = this.m_119364_(resourcelocation2);
            this.m_119352_(modelresourcelocation, blockmodel);
            this.f_119212_.put(resourcelocation2, blockmodel);
         } else {
            ResourceLocation resourcelocation = new ResourceLocation(p_119363_.m_135827_(), p_119363_.m_135815_());
            StateDefinition<Block, BlockState> statedefinition = Optional.ofNullable(f_119242_.get(resourcelocation)).orElseGet(() -> {
               return Registry.f_122824_.m_7745_(resourcelocation).m_49965_();
            });
            this.f_119211_.m_111552_(statedefinition);
            List<Property<?>> list = ImmutableList.copyOf(this.f_119209_.m_92575_(statedefinition.m_61091_()));
            ImmutableList<BlockState> immutablelist = statedefinition.m_61056_();
            Map<ModelResourceLocation, BlockState> map = Maps.newHashMap();
            immutablelist.forEach((p_119330_) -> {
               map.put(BlockModelShaper.m_110889_(resourcelocation, p_119330_), p_119330_);
            });
            Map<BlockState, Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>>> map1 = Maps.newHashMap();
            ResourceLocation resourcelocation1 = new ResourceLocation(p_119363_.m_135827_(), "blockstates/" + p_119363_.m_135815_() + ".json");
            UnbakedModel unbakedmodel = this.f_119212_.get(f_119230_);
            ModelBakery.ModelGroupKey modelbakery$modelgroupkey = new ModelBakery.ModelGroupKey(ImmutableList.of(unbakedmodel), ImmutableList.of());
            Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>> pair = Pair.of(unbakedmodel, () -> {
               return modelbakery$modelgroupkey;
            });

            try {
               List<Pair<String, BlockModelDefinition>> list1;
               try {
                  list1 = this.f_119243_.m_7396_(resourcelocation1).stream().map((p_119258_) -> {
                     try {
                        InputStream inputstream = p_119258_.m_6679_();

                        Pair<String, BlockModelDefinition> pair2;
                        try {
                           pair2 = Pair.of(p_119258_.m_7816_(), BlockModelDefinition.m_111540_(this.f_119211_, new InputStreamReader(inputstream, StandardCharsets.UTF_8)));
                        } catch (Throwable throwable1) {
                           if (inputstream != null) {
                              try {
                                 inputstream.close();
                              } catch (Throwable throwable) {
                                 throwable1.addSuppressed(throwable);
                              }
                           }

                           throw throwable1;
                        }

                        if (inputstream != null) {
                           inputstream.close();
                        }

                        return pair2;
                     } catch (Exception exception1) {
                        throw new ModelBakery.BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s' in resourcepack: '%s': %s", p_119258_.m_7843_(), p_119258_.m_7816_(), exception1.getMessage()));
                     }
                  }).collect(Collectors.toList());
               } catch (IOException ioexception) {
                  f_119235_.warn("Exception loading blockstate definition: {}: {}", resourcelocation1, ioexception);
                  return;
               }

               for(Pair<String, BlockModelDefinition> pair1 : list1) {
                  BlockModelDefinition blockmodeldefinition = pair1.getSecond();
                  Map<BlockState, Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>>> map2 = Maps.newIdentityHashMap();
                  MultiPart multipart;
                  if (blockmodeldefinition.m_111543_()) {
                     multipart = blockmodeldefinition.m_111544_();
                     immutablelist.forEach((p_119326_) -> {
                        map2.put(p_119326_, Pair.of(multipart, () -> {
                           return ModelBakery.ModelGroupKey.m_119379_(p_119326_, multipart, list);
                        }));
                     });
                  } else {
                     multipart = null;
                  }

                  blockmodeldefinition.m_111539_().forEach((p_119289_, p_119290_) -> {
                     try {
                        immutablelist.stream().filter(m_119273_(statedefinition, p_119289_)).forEach((p_174902_) -> {
                           Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>> pair2 = map2.put(p_174902_, Pair.of(p_119290_, () -> {
                              return ModelBakery.ModelGroupKey.m_119383_(p_174902_, p_119290_, list);
                           }));
                           if (pair2 != null && pair2.getFirst() != multipart) {
                              map2.put(p_174902_, pair);
                              throw new RuntimeException("Overlapping definition with: " + (String)blockmodeldefinition.m_111539_().entrySet().stream().filter((p_174892_) -> {
                                 return p_174892_.getValue() == pair2.getFirst();
                              }).findFirst().get().getKey());
                           }
                        });
                     } catch (Exception exception1) {
                        f_119235_.warn("Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}", resourcelocation1, pair1.getFirst(), p_119289_, exception1.getMessage());
                     }

                  });
                  map1.putAll(map2);
               }

            } catch (ModelBakery.BlockStateDefinitionException modelbakery$blockstatedefinitionexception) {
               throw modelbakery$blockstatedefinitionexception;
            } catch (Exception exception) {
               throw new ModelBakery.BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s': %s", resourcelocation1, exception));
            } finally {
               HashMap<ModelBakery.ModelGroupKey,Set<BlockState>> hashmap = Maps.newHashMap();
               map.forEach((p_119336_, p_119337_) -> {
                  Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>> pair2 = map1.get(p_119337_);
                  if (pair2 == null) {
                     f_119235_.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", resourcelocation1, p_119336_);
                     pair2 = pair;
                  }

                  this.m_119352_(p_119336_, pair2.getFirst());

                  try {
                     ModelBakery.ModelGroupKey modelbakery$modelgroupkey1 = pair2.getSecond().get();
                     hashmap.computeIfAbsent(modelbakery$modelgroupkey1, (p_174894_) -> {
                        return Sets.newIdentityHashSet();
                     }).add(p_119337_);
                  } catch (Exception exception1) {
                     f_119235_.warn("Exception evaluating model definition: '{}'", p_119336_, exception1);
                  }

               });
               hashmap.forEach((p_119304_, p_119305_) -> {
                  Iterator<BlockState> iterator = p_119305_.iterator();

                  while(iterator.hasNext()) {
                     BlockState blockstate = iterator.next();
                     if (blockstate.m_60799_() != RenderShape.MODEL) {
                        iterator.remove();
                        this.f_119218_.put(blockstate, 0);
                     }
                  }

                  if (p_119305_.size() > 1) {
                     this.m_119310_(p_119305_);
                  }

               });
            }
         }
      }
   }

   private void m_119352_(ResourceLocation p_119353_, UnbakedModel p_119354_) {
      this.f_119212_.put(p_119353_, p_119354_);
      this.f_119210_.addAll(p_119354_.m_7970_());
   }

   // Same as loadTopLevel but without restricting to MRL's
   private void addModelToCache(ResourceLocation locationIn) {
      UnbakedModel unbakedmodel = this.m_119341_(locationIn);
      this.f_119212_.put(locationIn, unbakedmodel);
      this.f_119214_.put(locationIn, unbakedmodel);
   }

   private void m_119306_(ModelResourceLocation p_119307_) {
      UnbakedModel unbakedmodel = this.m_119341_(p_119307_);
      this.f_119212_.put(p_119307_, unbakedmodel);
      this.f_119214_.put(p_119307_, unbakedmodel);
   }

   private void m_119310_(Iterable<BlockState> p_119311_) {
      int i = this.f_119217_++;
      p_119311_.forEach((p_119256_) -> {
         this.f_119218_.put(p_119256_, i);
      });
   }

   @Nullable
   @Deprecated
   public BakedModel m_119349_(ResourceLocation p_119350_, ModelState p_119351_) {
      return bake(p_119350_, p_119351_, this.f_119244_::m_117971_);
   }

   @Nullable
   public BakedModel bake(ResourceLocation p_119350_, ModelState p_119351_, java.util.function.Function<Material, net.minecraft.client.renderer.texture.TextureAtlasSprite> sprites) {
      Triple<ResourceLocation, Transformation, Boolean> triple = Triple.of(p_119350_, p_119351_.m_6189_(), p_119351_.m_7538_());
      if (this.f_119213_.containsKey(triple)) {
         return this.f_119213_.get(triple);
      } else if (this.f_119244_ == null) {
         throw new IllegalStateException("bake called too early");
      } else {
         UnbakedModel unbakedmodel = this.m_119341_(p_119350_);
         if (unbakedmodel instanceof BlockModel) {
            BlockModel blockmodel = (BlockModel)unbakedmodel;
            if (blockmodel.m_111490_() == f_119232_) {
               return f_119241_.m_111670_(sprites, blockmodel).m_111449_(this, blockmodel, this.f_119244_::m_117971_, p_119351_, p_119350_, false);
            }
         }

         BakedModel bakedmodel = unbakedmodel.m_7611_(this, sprites, p_119351_, p_119350_);
         this.f_119213_.put(triple, bakedmodel);
         return bakedmodel;
      }
   }

   protected BlockModel m_119364_(ResourceLocation p_119365_) throws IOException {
      Reader reader = null;
      Resource resource = null;

      BlockModel blockmodel;
      try {
         String s = p_119365_.m_135815_();
         if (!"builtin/generated".equals(s)) {
            if ("builtin/entity".equals(s)) {
               return f_119233_;
            }

            if (s.startsWith("builtin/")) {
               String s2 = s.substring("builtin/".length());
               String s1 = f_119237_.get(s2);
               if (s1 == null) {
                  throw new FileNotFoundException(p_119365_.toString());
               }

               reader = new StringReader(s1);
            } else {
               resource = this.f_119243_.m_142591_(new ResourceLocation(p_119365_.m_135827_(), "models/" + p_119365_.m_135815_() + ".json"));
               reader = new InputStreamReader(resource.m_6679_(), StandardCharsets.UTF_8);
            }

            blockmodel = BlockModel.m_111461_(reader);
            blockmodel.f_111416_ = p_119365_.toString();
            return blockmodel;
         }

         blockmodel = f_119232_;
      } finally {
         IOUtils.closeQuietly(reader);
         IOUtils.closeQuietly((Closeable)resource);
      }

      return blockmodel;
   }

   public Map<ResourceLocation, BakedModel> m_119251_() {
      return this.f_119215_;
   }

   public Object2IntMap<BlockState> m_119355_() {
      return this.f_119218_;
   }

   public Set<ResourceLocation> getSpecialModels() {
      return java.util.Collections.emptySet();
   }

   @OnlyIn(Dist.CLIENT)
   static class BlockStateDefinitionException extends RuntimeException {
      public BlockStateDefinitionException(String p_119373_) {
         super(p_119373_);
      }
   }

   public AtlasSet getSpriteMap() {
      return this.f_119244_;
   }

   @OnlyIn(Dist.CLIENT)
   static class ModelGroupKey {
      private final List<UnbakedModel> f_119374_;
      private final List<Object> f_119375_;

      public ModelGroupKey(List<UnbakedModel> p_119377_, List<Object> p_119378_) {
         this.f_119374_ = p_119377_;
         this.f_119375_ = p_119378_;
      }

      public boolean equals(Object p_119395_) {
         if (this == p_119395_) {
            return true;
         } else if (!(p_119395_ instanceof ModelBakery.ModelGroupKey)) {
            return false;
         } else {
            ModelBakery.ModelGroupKey modelbakery$modelgroupkey = (ModelBakery.ModelGroupKey)p_119395_;
            return Objects.equals(this.f_119374_, modelbakery$modelgroupkey.f_119374_) && Objects.equals(this.f_119375_, modelbakery$modelgroupkey.f_119375_);
         }
      }

      public int hashCode() {
         return 31 * this.f_119374_.hashCode() + this.f_119375_.hashCode();
      }

      public static ModelBakery.ModelGroupKey m_119379_(BlockState p_119380_, MultiPart p_119381_, Collection<Property<?>> p_119382_) {
         StateDefinition<Block, BlockState> statedefinition = p_119380_.m_60734_().m_49965_();
         List<UnbakedModel> list = p_119381_.m_111967_().stream().filter((p_119393_) -> {
            return p_119393_.m_112021_(statedefinition).test(p_119380_);
         }).map(Selector::m_112020_).collect(ImmutableList.toImmutableList());
         List<Object> list1 = m_119387_(p_119380_, p_119382_);
         return new ModelBakery.ModelGroupKey(list, list1);
      }

      public static ModelBakery.ModelGroupKey m_119383_(BlockState p_119384_, UnbakedModel p_119385_, Collection<Property<?>> p_119386_) {
         List<Object> list = m_119387_(p_119384_, p_119386_);
         return new ModelBakery.ModelGroupKey(ImmutableList.of(p_119385_), list);
      }

      private static List<Object> m_119387_(BlockState p_119388_, Collection<Property<?>> p_119389_) {
         return p_119389_.stream().map(p_119388_::m_61143_).collect(ImmutableList.toImmutableList());
      }
   }
}
