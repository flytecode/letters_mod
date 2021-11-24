package net.minecraft.core;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.util.valueproviders.FloatProviderType;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElementType;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.providers.nbt.LootNbtProviderType;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProviders;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.score.LootScoreProviderType;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProviders;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Attention Modders: This SHOULD NOT be used, you should use ForgeRegistries instead. As it has a cleaner modder facing API.
 * We will be wrapping all of these in our API as necessary for syncing and management.
 */
public abstract class Registry<T> implements Codec<T>, Keyable, IdMap<T> {
   protected static final Logger f_122894_ = LogManager.getLogger();
   private static final Map<ResourceLocation, Supplier<?>> f_122834_ = Maps.newLinkedHashMap();
   public static final ResourceLocation f_122895_ = new ResourceLocation("root");
   protected static final WritableRegistry<WritableRegistry<?>> f_122896_ = new MappedRegistry<>(m_122978_("root"), Lifecycle.experimental());
   public static final Registry<? extends Registry<?>> f_122897_ = f_122896_;
   public static final ResourceKey<Registry<SoundEvent>> f_122898_ = m_122978_("sound_event");
   public static final ResourceKey<Registry<Fluid>> f_122899_ = m_122978_("fluid");
   public static final ResourceKey<Registry<MobEffect>> f_122900_ = m_122978_("mob_effect");
   public static final ResourceKey<Registry<Block>> f_122901_ = m_122978_("block");
   public static final ResourceKey<Registry<Enchantment>> f_122902_ = m_122978_("enchantment");
   public static final ResourceKey<Registry<EntityType<?>>> f_122903_ = m_122978_("entity_type");
   public static final ResourceKey<Registry<Item>> f_122904_ = m_122978_("item");
   public static final ResourceKey<Registry<Potion>> f_122905_ = m_122978_("potion");
   public static final ResourceKey<Registry<ParticleType<?>>> f_122906_ = m_122978_("particle_type");
   public static final ResourceKey<Registry<BlockEntityType<?>>> f_122907_ = m_122978_("block_entity_type");
   public static final ResourceKey<Registry<Motive>> f_122908_ = m_122978_("motive");
   public static final ResourceKey<Registry<ResourceLocation>> f_122909_ = m_122978_("custom_stat");
   public static final ResourceKey<Registry<ChunkStatus>> f_122910_ = m_122978_("chunk_status");
   public static final ResourceKey<Registry<RuleTestType<?>>> f_122911_ = m_122978_("rule_test");
   public static final ResourceKey<Registry<PosRuleTestType<?>>> f_122912_ = m_122978_("pos_rule_test");
   public static final ResourceKey<Registry<MenuType<?>>> f_122913_ = m_122978_("menu");
   public static final ResourceKey<Registry<RecipeType<?>>> f_122914_ = m_122978_("recipe_type");
   public static final ResourceKey<Registry<RecipeSerializer<?>>> f_122915_ = m_122978_("recipe_serializer");
   public static final ResourceKey<Registry<Attribute>> f_122916_ = m_122978_("attribute");
   public static final ResourceKey<Registry<GameEvent>> f_175423_ = m_122978_("game_event");
   public static final ResourceKey<Registry<PositionSourceType<?>>> f_175408_ = m_122978_("position_source_type");
   public static final ResourceKey<Registry<StatType<?>>> f_122917_ = m_122978_("stat_type");
   public static final ResourceKey<Registry<VillagerType>> f_122808_ = m_122978_("villager_type");
   public static final ResourceKey<Registry<VillagerProfession>> f_122809_ = m_122978_("villager_profession");
   public static final ResourceKey<Registry<PoiType>> f_122810_ = m_122978_("point_of_interest_type");
   public static final ResourceKey<Registry<MemoryModuleType<?>>> f_122811_ = m_122978_("memory_module_type");
   public static final ResourceKey<Registry<SensorType<?>>> f_122812_ = m_122978_("sensor_type");
   public static final ResourceKey<Registry<Schedule>> f_122813_ = m_122978_("schedule");
   public static final ResourceKey<Registry<Activity>> f_122814_ = m_122978_("activity");
   public static final ResourceKey<Registry<LootPoolEntryType>> f_122815_ = m_122978_("loot_pool_entry_type");
   public static final ResourceKey<Registry<LootItemFunctionType>> f_122816_ = m_122978_("loot_function_type");
   public static final ResourceKey<Registry<LootItemConditionType>> f_122817_ = m_122978_("loot_condition_type");
   public static final ResourceKey<Registry<LootNumberProviderType>> f_175409_ = m_122978_("loot_number_provider_type");
   public static final ResourceKey<Registry<LootNbtProviderType>> f_175410_ = m_122978_("loot_nbt_provider_type");
   public static final ResourceKey<Registry<LootScoreProviderType>> f_175411_ = m_122978_("loot_score_provider_type");
   public static final ResourceKey<Registry<DimensionType>> f_122818_ = m_122978_("dimension_type");
   public static final ResourceKey<Registry<Level>> f_122819_ = m_122978_("dimension");
   public static final ResourceKey<Registry<LevelStem>> f_122820_ = m_122978_("dimension");
   public static final DefaultedRegistry<GameEvent> f_175412_ = m_122995_(f_175423_, "step", () -> {
      return GameEvent.f_157785_;
   });
   @Deprecated public static final Registry<SoundEvent> f_122821_ = forge(f_122898_, () -> {
      return SoundEvents.f_12019_;
   });
   @Deprecated public static final DefaultedRegistry<Fluid> f_122822_ = forge(f_122899_, "empty", () -> {
      return Fluids.f_76191_;
   });
   @Deprecated public static final Registry<MobEffect> f_122823_ = forge(f_122900_, () -> {
      return MobEffects.f_19621_;
   });
   @Deprecated public static final DefaultedRegistry<Block> f_122824_ = forge(f_122901_, "air", () -> {
      return Blocks.f_50016_;
   });
   @Deprecated public static final Registry<Enchantment> f_122825_ = forge(f_122902_, () -> {
      return Enchantments.f_44987_;
   });
   @Deprecated public static final DefaultedRegistry<EntityType<?>> f_122826_ = forge(f_122903_, "pig", () -> {
      return EntityType.f_20510_;
   });
   @Deprecated public static final DefaultedRegistry<Item> f_122827_ = forge(f_122904_, "air", () -> {
      return Items.f_41852_;
   });
   @Deprecated public static final DefaultedRegistry<Potion> f_122828_ = forge(f_122905_, "empty", () -> {
      return Potions.f_43598_;
   });
   @Deprecated public static final Registry<ParticleType<?>> f_122829_ = forge(f_122906_, () -> {
      return ParticleTypes.f_123794_;
   });
   @Deprecated public static final Registry<BlockEntityType<?>> f_122830_ = forge(f_122907_, () -> {
      return BlockEntityType.f_58917_;
   });
   @Deprecated public static final DefaultedRegistry<Motive> f_122831_ = forge(f_122908_, "kebab", () -> {
      return Motive.f_31866_;
   });
   public static final Registry<ResourceLocation> f_122832_ = m_122999_(f_122909_, () -> {
      return Stats.f_12926_;
   });
   @Deprecated public static final DefaultedRegistry<ChunkStatus> f_122833_ = forge(f_122910_, "empty", () -> {
      return ChunkStatus.f_62314_;
   });
   public static final Registry<RuleTestType<?>> f_122861_ = m_122999_(f_122911_, () -> {
      return RuleTestType.f_74312_;
   });
   public static final Registry<PosRuleTestType<?>> f_122862_ = m_122999_(f_122912_, () -> {
      return PosRuleTestType.f_74205_;
   });
   @Deprecated public static final Registry<MenuType<?>> f_122863_ = forge(f_122913_, () -> {
      return MenuType.f_39964_;
   });
   public static final Registry<RecipeType<?>> f_122864_ = m_122999_(f_122914_, () -> {
      return RecipeType.f_44107_;
   });
   @Deprecated public static final Registry<RecipeSerializer<?>> f_122865_ = forge(f_122915_, () -> {
      return RecipeSerializer.f_44077_;
   });
   @Deprecated public static final Registry<Attribute> f_122866_ = forge(f_122916_, () -> {
      return Attributes.f_22286_;
   });
   public static final Registry<PositionSourceType<?>> f_175420_ = m_122999_(f_175408_, () -> {
      return PositionSourceType.f_157871_;
   });
   @Deprecated public static final Registry<StatType<?>> f_122867_ = forge(f_122917_, () -> {
      return Stats.f_12982_;
   });
   public static final DefaultedRegistry<VillagerType> f_122868_ = m_122995_(f_122808_, "plains", () -> {
      return VillagerType.f_35821_;
   });
   @Deprecated public static final DefaultedRegistry<VillagerProfession> f_122869_ = forge(f_122809_, "none", () -> {
      return VillagerProfession.f_35585_;
   });
   @Deprecated public static final DefaultedRegistry<PoiType> f_122870_ = forge(f_122810_, "unemployed", () -> {
      return PoiType.f_27331_;
   });
   @Deprecated public static final DefaultedRegistry<MemoryModuleType<?>> f_122871_ = forge(f_122811_, "dummy", () -> {
      return MemoryModuleType.f_26349_;
   });
   @Deprecated public static final DefaultedRegistry<SensorType<?>> f_122872_ = forge(f_122812_, "dummy", () -> {
      return SensorType.f_26809_;
   });
   @Deprecated public static final Registry<Schedule> f_122873_ = forge(f_122813_, () -> {
      return Schedule.f_38012_;
   });
   @Deprecated public static final Registry<Activity> f_122874_ = forge(f_122814_, () -> {
      return Activity.f_37979_;
   });
   public static final Registry<LootPoolEntryType> f_122875_ = m_122999_(f_122815_, () -> {
      return LootPoolEntries.f_79619_;
   });
   public static final Registry<LootItemFunctionType> f_122876_ = m_122999_(f_122816_, () -> {
      return LootItemFunctions.f_80736_;
   });
   public static final Registry<LootItemConditionType> f_122877_ = m_122999_(f_122817_, () -> {
      return LootItemConditions.f_81811_;
   });
   public static final Registry<LootNumberProviderType> f_175421_ = m_122999_(f_175409_, () -> {
      return NumberProviders.f_165731_;
   });
   public static final Registry<LootNbtProviderType> f_175422_ = m_122999_(f_175410_, () -> {
      return NbtProviders.f_165624_;
   });
   public static final Registry<LootScoreProviderType> f_175413_ = m_122999_(f_175411_, () -> {
      return ScoreboardNameProviders.f_165869_;
   });
   public static final ResourceKey<Registry<FloatProviderType<?>>> f_175414_ = m_122978_("float_provider_type");
   public static final Registry<FloatProviderType<?>> f_175415_ = m_122999_(f_175414_, () -> {
      return FloatProviderType.f_146519_;
   });
   public static final ResourceKey<Registry<IntProviderType<?>>> f_175416_ = m_122978_("int_provider_type");
   public static final Registry<IntProviderType<?>> f_175417_ = m_122999_(f_175416_, () -> {
      return IntProviderType.f_146550_;
   });
   public static final ResourceKey<Registry<HeightProviderType<?>>> f_175418_ = m_122978_("height_provider_type");
   public static final Registry<HeightProviderType<?>> f_175419_ = m_122999_(f_175418_, () -> {
      return HeightProviderType.f_161981_;
   });
   public static final ResourceKey<Registry<NoiseGeneratorSettings>> f_122878_ = m_122978_("worldgen/noise_settings");
   public static final ResourceKey<Registry<ConfiguredSurfaceBuilder<?>>> f_122879_ = m_122978_("worldgen/configured_surface_builder");
   public static final ResourceKey<Registry<ConfiguredWorldCarver<?>>> f_122880_ = m_122978_("worldgen/configured_carver");
   public static final ResourceKey<Registry<ConfiguredFeature<?, ?>>> f_122881_ = m_122978_("worldgen/configured_feature");
   public static final ResourceKey<Registry<ConfiguredStructureFeature<?, ?>>> f_122882_ = m_122978_("worldgen/configured_structure_feature");
   public static final ResourceKey<Registry<StructureProcessorList>> f_122883_ = m_122978_("worldgen/processor_list");
   public static final ResourceKey<Registry<StructureTemplatePool>> f_122884_ = m_122978_("worldgen/template_pool");
   public static final ResourceKey<Registry<Biome>> f_122885_ = m_122978_("worldgen/biome");
   public static final ResourceKey<Registry<SurfaceBuilder<?>>> f_122886_ = m_122978_("worldgen/surface_builder");
   @Deprecated public static final Registry<SurfaceBuilder<?>> f_122835_ = forge(f_122886_, () -> {
      return SurfaceBuilder.f_75214_;
   });
   public static final ResourceKey<Registry<WorldCarver<?>>> f_122836_ = m_122978_("worldgen/carver");
   @Deprecated public static final Registry<WorldCarver<?>> f_122837_ = forge(f_122836_, () -> {
      return WorldCarver.f_64974_;
   });
   public static final ResourceKey<Registry<Feature<?>>> f_122838_ = m_122978_("worldgen/feature");
   @Deprecated public static final Registry<Feature<?>> f_122839_ = forge(f_122838_, () -> {
      return Feature.f_65731_;
   });
   public static final ResourceKey<Registry<StructureFeature<?>>> f_122840_ = m_122978_("worldgen/structure_feature");
   @Deprecated public static final Registry<StructureFeature<?>> f_122841_ = forge(f_122840_, () -> {
      return StructureFeature.f_67014_;
   });
   public static final ResourceKey<Registry<StructurePieceType>> f_122842_ = m_122978_("worldgen/structure_piece");
   public static final Registry<StructurePieceType> f_122843_ = m_122999_(f_122842_, () -> {
      return StructurePieceType.f_67138_;
   });
   public static final ResourceKey<Registry<FeatureDecorator<?>>> f_122844_ = m_122978_("worldgen/decorator");
   @Deprecated public static final Registry<FeatureDecorator<?>> f_122845_ = forge(f_122844_, () -> {
      return FeatureDecorator.f_70681_;
   });
   public static final ResourceKey<Registry<BlockStateProviderType<?>>> f_122846_ = m_122978_("worldgen/block_state_provider_type");
   public static final ResourceKey<Registry<BlockPlacerType<?>>> f_122847_ = m_122978_("worldgen/block_placer_type");
   public static final ResourceKey<Registry<FoliagePlacerType<?>>> f_122848_ = m_122978_("worldgen/foliage_placer_type");
   public static final ResourceKey<Registry<TrunkPlacerType<?>>> f_122849_ = m_122978_("worldgen/trunk_placer_type");
   public static final ResourceKey<Registry<TreeDecoratorType<?>>> f_122850_ = m_122978_("worldgen/tree_decorator_type");
   public static final ResourceKey<Registry<FeatureSizeType<?>>> f_122851_ = m_122978_("worldgen/feature_size_type");
   public static final ResourceKey<Registry<Codec<? extends BiomeSource>>> f_122852_ = m_122978_("worldgen/biome_source");
   public static final ResourceKey<Registry<Codec<? extends ChunkGenerator>>> f_122853_ = m_122978_("worldgen/chunk_generator");
   public static final ResourceKey<Registry<StructureProcessorType<?>>> f_122854_ = m_122978_("worldgen/structure_processor");
   public static final ResourceKey<Registry<StructurePoolElementType<?>>> f_122855_ = m_122978_("worldgen/structure_pool_element");
   @Deprecated public static final Registry<BlockStateProviderType<?>> f_122856_ = forge(f_122846_, () -> {
      return BlockStateProviderType.f_68752_;
   });
   @Deprecated public static final Registry<BlockPlacerType<?>> f_122857_ = forge(f_122847_, () -> {
      return BlockPlacerType.f_67487_;
   });
   @Deprecated public static final Registry<FoliagePlacerType<?>> f_122858_ = forge(f_122848_, () -> {
      return FoliagePlacerType.f_68591_;
   });
   public static final Registry<TrunkPlacerType<?>> f_122859_ = m_122999_(f_122849_, () -> {
      return TrunkPlacerType.f_70315_;
   });
   @Deprecated public static final Registry<TreeDecoratorType<?>> f_122860_ = forge(f_122850_, () -> {
      return TreeDecoratorType.f_70043_;
   });
   public static final Registry<FeatureSizeType<?>> f_122888_ = m_122999_(f_122851_, () -> {
      return FeatureSizeType.f_68296_;
   });
   public static final Registry<Codec<? extends BiomeSource>> f_122889_ = m_122981_(f_122852_, Lifecycle.stable(), () -> {
      return BiomeSource.f_47888_;
   });
   public static final Registry<Codec<? extends ChunkGenerator>> f_122890_ = m_122981_(f_122853_, Lifecycle.stable(), () -> {
      return ChunkGenerator.f_62136_;
   });
   public static final Registry<StructureProcessorType<?>> f_122891_ = m_122999_(f_122854_, () -> {
      return StructureProcessorType.f_74456_;
   });
   public static final Registry<StructurePoolElementType<?>> f_122892_ = m_122999_(f_122855_, () -> {
      return StructurePoolElementType.f_69236_;
   });
   private final ResourceKey<? extends Registry<T>> f_122887_;
   private final Lifecycle f_122893_;

   private static <T> ResourceKey<Registry<T>> m_122978_(String p_122979_) {
      return ResourceKey.m_135788_(new ResourceLocation(p_122979_));
   }

   public static <T extends WritableRegistry<?>> void m_122969_(WritableRegistry<T> p_122970_) {
      p_122970_.forEach((p_175461_) -> {
         if (p_175461_.m_6566_().isEmpty()) {
            Util.m_143785_("Registry '" + p_122970_.m_7981_(p_175461_) + "' was empty after loading");
         }

         if (p_175461_ instanceof DefaultedRegistry) {
            ResourceLocation resourcelocation = ((DefaultedRegistry)p_175461_).m_122315_();
            Validate.notNull(p_175461_.m_7745_(resourcelocation), "Missing default of DefaultedMappedRegistry: " + resourcelocation);
         }

      });
   }

   private static <T> Registry<T> m_122999_(ResourceKey<? extends Registry<T>> p_123000_, Supplier<T> p_123001_) {
      return m_122981_(p_123000_, Lifecycle.experimental(), p_123001_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> Registry<T> forge(ResourceKey<? extends Registry<T>> key, Supplier<T> def) {
      return forge(key, Lifecycle.experimental(), def);
   }

   private static <T> DefaultedRegistry<T> m_122995_(ResourceKey<? extends Registry<T>> p_122996_, String p_122997_, Supplier<T> p_122998_) {
      return m_122990_(p_122996_, p_122997_, Lifecycle.experimental(), p_122998_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> DefaultedRegistry<T> forge(ResourceKey<? extends Registry<T>> key, String defKey, Supplier<T> def) {
      return forge(key, defKey, Lifecycle.experimental(), def);
   }

   private static <T> Registry<T> m_122981_(ResourceKey<? extends Registry<T>> p_122982_, Lifecycle p_122983_, Supplier<T> p_122984_) {
      return m_122985_(p_122982_, new MappedRegistry<>(p_122982_, p_122983_), p_122984_, p_122983_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> Registry<T> forge(ResourceKey<? extends Registry<T>> key, Lifecycle cycle, Supplier<T> def) {
      return m_122985_(key, net.minecraftforge.registries.GameData.getWrapper(key, cycle), def, cycle);
   }

   private static <T> DefaultedRegistry<T> m_122990_(ResourceKey<? extends Registry<T>> p_122991_, String p_122992_, Lifecycle p_122993_, Supplier<T> p_122994_) {
      return m_122985_(p_122991_, new DefaultedRegistry<>(p_122992_, p_122991_, p_122993_), p_122994_, p_122993_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> DefaultedRegistry<T> forge(ResourceKey<? extends Registry<T>> key, String defKey, Lifecycle cycle, Supplier<T> def) {
      return m_122985_(key, net.minecraftforge.registries.GameData.getWrapper(key, cycle, defKey), def, cycle);
   }

   private static <T, R extends WritableRegistry<T>> R m_122985_(ResourceKey<? extends Registry<T>> p_122986_, R p_122987_, Supplier<T> p_122988_, Lifecycle p_122989_) {
      ResourceLocation resourcelocation = p_122986_.m_135782_();
      f_122834_.put(resourcelocation, p_122988_);
      WritableRegistry<R> writableregistry = (WritableRegistry<R>)f_122896_;
      return (R)writableregistry.m_7135_((ResourceKey)p_122986_, p_122987_, p_122989_);
   }

   protected Registry(ResourceKey<? extends Registry<T>> p_122920_, Lifecycle p_122921_) {
      Bootstrap.m_179912_(() -> {
         return "registry " + p_122920_;
      });
      this.f_122887_ = p_122920_;
      this.f_122893_ = p_122921_;
   }

   public ResourceKey<? extends Registry<T>> m_123023_() {
      return this.f_122887_;
   }

   public String toString() {
      return "Registry[" + this.f_122887_ + " (" + this.f_122893_ + ")]";
   }

   public <U> DataResult<Pair<T, U>> decode(DynamicOps<U> p_123016_, U p_123017_) {
      return p_123016_.compressMaps() ? p_123016_.getNumberValue(p_123017_).flatMap((p_175463_) -> {
         T t = this.m_7942_(p_175463_.intValue());
         return t == null ? DataResult.error("Unknown registry id: " + p_175463_) : DataResult.success(t, this.m_6228_(t));
      }).map((p_175455_) -> {
         return Pair.of((T)p_175455_, p_123016_.empty());
      }) : ResourceLocation.f_135803_.decode(p_123016_, p_123017_).flatMap((p_175452_) -> {
         T t = this.m_7745_(p_175452_.getFirst());
         return t == null ? DataResult.error("Unknown registry key: " + p_175452_.getFirst()) : DataResult.success(Pair.of(t, p_175452_.getSecond()), this.m_6228_(t));
      });
   }

   public <U> DataResult<U> encode(T p_123020_, DynamicOps<U> p_123021_, U p_123022_) {
      ResourceLocation resourcelocation = this.m_7981_(p_123020_);
      if (resourcelocation == null) {
         return DataResult.error("Unknown registry element " + p_123020_);
      } else {
         return p_123021_.compressMaps() ? p_123021_.mergeToPrimitive(p_123022_, p_123021_.createInt(this.m_7447_(p_123020_))).setLifecycle(this.f_122893_) : p_123021_.mergeToPrimitive(p_123022_, p_123021_.createString(resourcelocation.toString())).setLifecycle(this.f_122893_);
      }
   }

   public <U> Stream<U> keys(DynamicOps<U> p_123030_) {
      return this.m_6566_().stream().map((p_175458_) -> {
         return p_123030_.createString(p_175458_.toString());
      });
   }

   @Nullable
   public abstract ResourceLocation m_7981_(T p_123006_);

   public abstract Optional<ResourceKey<T>> m_7854_(T p_123008_);

   public abstract int m_7447_(@Nullable T p_122977_);

   @Nullable
   public abstract T m_6246_(@Nullable ResourceKey<T> p_122980_);

   @Nullable
   public abstract T m_7745_(@Nullable ResourceLocation p_123002_);

   protected abstract Lifecycle m_6228_(T p_123012_);

   public abstract Lifecycle m_7837_();

   public Optional<T> m_6612_(@Nullable ResourceLocation p_123007_) {
      return Optional.ofNullable(this.m_7745_(p_123007_));
   }

   public Optional<T> m_123009_(@Nullable ResourceKey<T> p_123010_) {
      return Optional.ofNullable(this.m_6246_(p_123010_));
   }

   public T m_123013_(ResourceKey<T> p_123014_) {
      T t = this.m_6246_(p_123014_);
      if (t == null) {
         throw new IllegalStateException("Missing: " + p_123014_);
      } else {
         return t;
      }
   }

   public abstract Set<ResourceLocation> m_6566_();

   public abstract Set<Entry<ResourceKey<T>, T>> m_6579_();

   @Nullable
   public abstract T m_142697_(Random p_175464_);

   public Stream<T> m_123024_() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   public abstract boolean m_7804_(ResourceLocation p_123011_);

   public abstract boolean m_142003_(ResourceKey<T> p_175475_);

   public static <T> T m_122961_(Registry<? super T> p_122962_, String p_122963_, T p_122964_) {
      return m_122965_(p_122962_, new ResourceLocation(p_122963_), p_122964_);
   }

   public static <V, T extends V> T m_122965_(Registry<V> p_122966_, ResourceLocation p_122967_, T p_122968_) {
      return (T)((WritableRegistry)p_122966_).m_7135_(ResourceKey.m_135785_(p_122966_.f_122887_, p_122967_), p_122968_, Lifecycle.stable());
   }

   public static <V, T extends V> T m_122956_(Registry<V> p_122957_, int p_122958_, String p_122959_, T p_122960_) {
      return (T)((WritableRegistry)p_122957_).m_5748_(p_122958_, ResourceKey.m_135785_(p_122957_.f_122887_, new ResourceLocation(p_122959_)), p_122960_, Lifecycle.stable());
   }

   static {
      BuiltinRegistries.m_123870_();
      f_122834_.forEach((p_175466_, p_175467_) -> {
         if (p_175467_.get() == null) {
            f_122894_.error("Unable to bootstrap registry '{}'", (Object)p_175466_);
         }

      });
      m_122969_(f_122896_);
   }
}
