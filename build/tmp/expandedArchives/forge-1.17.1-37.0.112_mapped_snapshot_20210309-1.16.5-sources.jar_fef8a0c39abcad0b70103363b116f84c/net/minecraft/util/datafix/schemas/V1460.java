package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1460 extends NamespacedSchema {
   public V1460(int p_17553_, Schema p_17554_) {
      super(p_17553_, p_17554_);
   }

   protected static void m_17560_(Schema p_17561_, Map<String, Supplier<TypeTemplate>> p_17562_, String p_17563_) {
      p_17561_.register(p_17562_, p_17563_, () -> {
         return V100.m_17330_(p_17561_);
      });
   }

   protected static void m_17575_(Schema p_17576_, Map<String, Supplier<TypeTemplate>> p_17577_, String p_17578_) {
      p_17576_.register(p_17577_, p_17578_, () -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_17576_)));
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17658_) {
      Map<String, Supplier<TypeTemplate>> map = Maps.newHashMap();
      p_17658_.registerSimple(map, "minecraft:area_effect_cloud");
      m_17560_(p_17658_, map, "minecraft:armor_stand");
      p_17658_.register(map, "minecraft:arrow", (p_17683_) -> {
         return DSL.optionalFields("inBlockState", References.f_16783_.in(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:bat");
      m_17560_(p_17658_, map, "minecraft:blaze");
      p_17658_.registerSimple(map, "minecraft:boat");
      m_17560_(p_17658_, map, "minecraft:cave_spider");
      p_17658_.register(map, "minecraft:chest_minecart", (p_17680_) -> {
         return DSL.optionalFields("DisplayState", References.f_16783_.in(p_17658_), "Items", DSL.list(References.f_16782_.in(p_17658_)));
      });
      m_17560_(p_17658_, map, "minecraft:chicken");
      p_17658_.register(map, "minecraft:commandblock_minecart", (p_17677_) -> {
         return DSL.optionalFields("DisplayState", References.f_16783_.in(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:cow");
      m_17560_(p_17658_, map, "minecraft:creeper");
      p_17658_.register(map, "minecraft:donkey", (p_17674_) -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_17658_)), "SaddleItem", References.f_16782_.in(p_17658_), V100.m_17330_(p_17658_));
      });
      p_17658_.registerSimple(map, "minecraft:dragon_fireball");
      p_17658_.registerSimple(map, "minecraft:egg");
      m_17560_(p_17658_, map, "minecraft:elder_guardian");
      p_17658_.registerSimple(map, "minecraft:ender_crystal");
      m_17560_(p_17658_, map, "minecraft:ender_dragon");
      p_17658_.register(map, "minecraft:enderman", (p_17671_) -> {
         return DSL.optionalFields("carriedBlockState", References.f_16783_.in(p_17658_), V100.m_17330_(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:endermite");
      p_17658_.registerSimple(map, "minecraft:ender_pearl");
      p_17658_.registerSimple(map, "minecraft:evocation_fangs");
      m_17560_(p_17658_, map, "minecraft:evocation_illager");
      p_17658_.registerSimple(map, "minecraft:eye_of_ender_signal");
      p_17658_.register(map, "minecraft:falling_block", (p_17668_) -> {
         return DSL.optionalFields("BlockState", References.f_16783_.in(p_17658_), "TileEntityData", References.f_16781_.in(p_17658_));
      });
      p_17658_.registerSimple(map, "minecraft:fireball");
      p_17658_.register(map, "minecraft:fireworks_rocket", (p_17665_) -> {
         return DSL.optionalFields("FireworksItem", References.f_16782_.in(p_17658_));
      });
      p_17658_.register(map, "minecraft:furnace_minecart", (p_17654_) -> {
         return DSL.optionalFields("DisplayState", References.f_16783_.in(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:ghast");
      m_17560_(p_17658_, map, "minecraft:giant");
      m_17560_(p_17658_, map, "minecraft:guardian");
      p_17658_.register(map, "minecraft:hopper_minecart", (p_17651_) -> {
         return DSL.optionalFields("DisplayState", References.f_16783_.in(p_17658_), "Items", DSL.list(References.f_16782_.in(p_17658_)));
      });
      p_17658_.register(map, "minecraft:horse", (p_17648_) -> {
         return DSL.optionalFields("ArmorItem", References.f_16782_.in(p_17658_), "SaddleItem", References.f_16782_.in(p_17658_), V100.m_17330_(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:husk");
      p_17658_.registerSimple(map, "minecraft:illusion_illager");
      p_17658_.register(map, "minecraft:item", (p_17645_) -> {
         return DSL.optionalFields("Item", References.f_16782_.in(p_17658_));
      });
      p_17658_.register(map, "minecraft:item_frame", (p_17642_) -> {
         return DSL.optionalFields("Item", References.f_16782_.in(p_17658_));
      });
      p_17658_.registerSimple(map, "minecraft:leash_knot");
      p_17658_.register(map, "minecraft:llama", (p_17639_) -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_17658_)), "SaddleItem", References.f_16782_.in(p_17658_), "DecorItem", References.f_16782_.in(p_17658_), V100.m_17330_(p_17658_));
      });
      p_17658_.registerSimple(map, "minecraft:llama_spit");
      m_17560_(p_17658_, map, "minecraft:magma_cube");
      p_17658_.register(map, "minecraft:minecart", (p_17634_) -> {
         return DSL.optionalFields("DisplayState", References.f_16783_.in(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:mooshroom");
      p_17658_.register(map, "minecraft:mule", (p_17629_) -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_17658_)), "SaddleItem", References.f_16782_.in(p_17658_), V100.m_17330_(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:ocelot");
      p_17658_.registerSimple(map, "minecraft:painting");
      p_17658_.registerSimple(map, "minecraft:parrot");
      m_17560_(p_17658_, map, "minecraft:pig");
      m_17560_(p_17658_, map, "minecraft:polar_bear");
      p_17658_.register(map, "minecraft:potion", (p_17624_) -> {
         return DSL.optionalFields("Potion", References.f_16782_.in(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:rabbit");
      m_17560_(p_17658_, map, "minecraft:sheep");
      m_17560_(p_17658_, map, "minecraft:shulker");
      p_17658_.registerSimple(map, "minecraft:shulker_bullet");
      m_17560_(p_17658_, map, "minecraft:silverfish");
      m_17560_(p_17658_, map, "minecraft:skeleton");
      p_17658_.register(map, "minecraft:skeleton_horse", (p_17619_) -> {
         return DSL.optionalFields("SaddleItem", References.f_16782_.in(p_17658_), V100.m_17330_(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:slime");
      p_17658_.registerSimple(map, "minecraft:small_fireball");
      p_17658_.registerSimple(map, "minecraft:snowball");
      m_17560_(p_17658_, map, "minecraft:snowman");
      p_17658_.register(map, "minecraft:spawner_minecart", (p_17614_) -> {
         return DSL.optionalFields("DisplayState", References.f_16783_.in(p_17658_), References.f_16789_.in(p_17658_));
      });
      p_17658_.register(map, "minecraft:spectral_arrow", (p_17609_) -> {
         return DSL.optionalFields("inBlockState", References.f_16783_.in(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:spider");
      m_17560_(p_17658_, map, "minecraft:squid");
      m_17560_(p_17658_, map, "minecraft:stray");
      p_17658_.registerSimple(map, "minecraft:tnt");
      p_17658_.register(map, "minecraft:tnt_minecart", (p_17604_) -> {
         return DSL.optionalFields("DisplayState", References.f_16783_.in(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:vex");
      p_17658_.register(map, "minecraft:villager", (p_17598_) -> {
         return DSL.optionalFields("Inventory", DSL.list(References.f_16782_.in(p_17658_)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", References.f_16782_.in(p_17658_), "buyB", References.f_16782_.in(p_17658_), "sell", References.f_16782_.in(p_17658_)))), V100.m_17330_(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:villager_golem");
      m_17560_(p_17658_, map, "minecraft:vindication_illager");
      m_17560_(p_17658_, map, "minecraft:witch");
      m_17560_(p_17658_, map, "minecraft:wither");
      m_17560_(p_17658_, map, "minecraft:wither_skeleton");
      p_17658_.registerSimple(map, "minecraft:wither_skull");
      m_17560_(p_17658_, map, "minecraft:wolf");
      p_17658_.registerSimple(map, "minecraft:xp_bottle");
      p_17658_.registerSimple(map, "minecraft:xp_orb");
      m_17560_(p_17658_, map, "minecraft:zombie");
      p_17658_.register(map, "minecraft:zombie_horse", (p_17592_) -> {
         return DSL.optionalFields("SaddleItem", References.f_16782_.in(p_17658_), V100.m_17330_(p_17658_));
      });
      m_17560_(p_17658_, map, "minecraft:zombie_pigman");
      m_17560_(p_17658_, map, "minecraft:zombie_villager");
      return map;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17656_) {
      Map<String, Supplier<TypeTemplate>> map = Maps.newHashMap();
      m_17575_(p_17656_, map, "minecraft:furnace");
      m_17575_(p_17656_, map, "minecraft:chest");
      m_17575_(p_17656_, map, "minecraft:trapped_chest");
      p_17656_.registerSimple(map, "minecraft:ender_chest");
      p_17656_.register(map, "minecraft:jukebox", (p_17586_) -> {
         return DSL.optionalFields("RecordItem", References.f_16782_.in(p_17656_));
      });
      m_17575_(p_17656_, map, "minecraft:dispenser");
      m_17575_(p_17656_, map, "minecraft:dropper");
      p_17656_.registerSimple(map, "minecraft:sign");
      p_17656_.register(map, "minecraft:mob_spawner", (p_17574_) -> {
         return References.f_16789_.in(p_17656_);
      });
      p_17656_.register(map, "minecraft:piston", (p_17559_) -> {
         return DSL.optionalFields("blockState", References.f_16783_.in(p_17656_));
      });
      m_17575_(p_17656_, map, "minecraft:brewing_stand");
      p_17656_.registerSimple(map, "minecraft:enchanting_table");
      p_17656_.registerSimple(map, "minecraft:end_portal");
      p_17656_.registerSimple(map, "minecraft:beacon");
      p_17656_.registerSimple(map, "minecraft:skull");
      p_17656_.registerSimple(map, "minecraft:daylight_detector");
      m_17575_(p_17656_, map, "minecraft:hopper");
      p_17656_.registerSimple(map, "minecraft:comparator");
      p_17656_.registerSimple(map, "minecraft:banner");
      p_17656_.registerSimple(map, "minecraft:structure_block");
      p_17656_.registerSimple(map, "minecraft:end_gateway");
      p_17656_.registerSimple(map, "minecraft:command_block");
      m_17575_(p_17656_, map, "minecraft:shulker_box");
      p_17656_.registerSimple(map, "minecraft:bed");
      return map;
   }

   public void registerTypes(Schema p_17660_, Map<String, Supplier<TypeTemplate>> p_17661_, Map<String, Supplier<TypeTemplate>> p_17662_) {
      p_17660_.registerType(false, References.f_16771_, DSL::remainder);
      p_17660_.registerType(false, References.f_16793_, () -> {
         return DSL.constType(m_17310_());
      });
      p_17660_.registerType(false, References.f_16772_, () -> {
         return DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", References.f_16785_.in(p_17660_)), "Inventory", DSL.list(References.f_16782_.in(p_17660_)), "EnderItems", DSL.list(References.f_16782_.in(p_17660_)), DSL.optionalFields("ShoulderEntityLeft", References.f_16785_.in(p_17660_), "ShoulderEntityRight", References.f_16785_.in(p_17660_), "recipeBook", DSL.optionalFields("recipes", DSL.list(References.f_16793_.in(p_17660_)), "toBeDisplayed", DSL.list(References.f_16793_.in(p_17660_)))));
      });
      p_17660_.registerType(false, References.f_16773_, () -> {
         return DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(References.f_16785_.in(p_17660_)), "TileEntities", DSL.list(References.f_16781_.in(p_17660_)), "TileTicks", DSL.list(DSL.fields("i", References.f_16787_.in(p_17660_))), "Sections", DSL.list(DSL.optionalFields("Palette", DSL.list(References.f_16783_.in(p_17660_))))));
      });
      p_17660_.registerType(true, References.f_16781_, () -> {
         return DSL.taggedChoiceLazy("id", m_17310_(), p_17662_);
      });
      p_17660_.registerType(true, References.f_16785_, () -> {
         return DSL.optionalFields("Passengers", DSL.list(References.f_16785_.in(p_17660_)), References.f_16786_.in(p_17660_));
      });
      p_17660_.registerType(true, References.f_16786_, () -> {
         return DSL.taggedChoiceLazy("id", m_17310_(), p_17661_);
      });
      p_17660_.registerType(true, References.f_16782_, () -> {
         return DSL.hook(DSL.optionalFields("id", References.f_16788_.in(p_17660_), "tag", DSL.optionalFields("EntityTag", References.f_16785_.in(p_17660_), "BlockEntityTag", References.f_16781_.in(p_17660_), "CanDestroy", DSL.list(References.f_16787_.in(p_17660_)), "CanPlaceOn", DSL.list(References.f_16787_.in(p_17660_)), "Items", DSL.list(References.f_16782_.in(p_17660_)))), V705.f_18072_, HookFunction.IDENTITY);
      });
      p_17660_.registerType(false, References.f_16774_, () -> {
         return DSL.compoundList(DSL.list(References.f_16782_.in(p_17660_)));
      });
      p_17660_.registerType(false, References.f_16775_, DSL::remainder);
      p_17660_.registerType(false, References.f_16776_, () -> {
         return DSL.optionalFields("entities", DSL.list(DSL.optionalFields("nbt", References.f_16785_.in(p_17660_))), "blocks", DSL.list(DSL.optionalFields("nbt", References.f_16781_.in(p_17660_))), "palette", DSL.list(References.f_16783_.in(p_17660_)));
      });
      p_17660_.registerType(false, References.f_16787_, () -> {
         return DSL.constType(m_17310_());
      });
      p_17660_.registerType(false, References.f_16788_, () -> {
         return DSL.constType(m_17310_());
      });
      p_17660_.registerType(false, References.f_16783_, DSL::remainder);
      Supplier<TypeTemplate> supplier = () -> {
         return DSL.compoundList(References.f_16788_.in(p_17660_), DSL.constType(DSL.intType()));
      };
      p_17660_.registerType(false, References.f_16777_, () -> {
         return DSL.optionalFields("stats", DSL.optionalFields("minecraft:mined", DSL.compoundList(References.f_16787_.in(p_17660_), DSL.constType(DSL.intType())), "minecraft:crafted", supplier.get(), "minecraft:used", supplier.get(), "minecraft:broken", supplier.get(), "minecraft:picked_up", supplier.get(), DSL.optionalFields("minecraft:dropped", supplier.get(), "minecraft:killed", DSL.compoundList(References.f_16784_.in(p_17660_), DSL.constType(DSL.intType())), "minecraft:killed_by", DSL.compoundList(References.f_16784_.in(p_17660_), DSL.constType(DSL.intType())), "minecraft:custom", DSL.compoundList(DSL.constType(m_17310_()), DSL.constType(DSL.intType())))));
      });
      p_17660_.registerType(false, References.f_16778_, () -> {
         return DSL.optionalFields("data", DSL.optionalFields("Features", DSL.compoundList(References.f_16790_.in(p_17660_)), "Objectives", DSL.list(References.f_16791_.in(p_17660_)), "Teams", DSL.list(References.f_16792_.in(p_17660_))));
      });
      p_17660_.registerType(false, References.f_16790_, () -> {
         return DSL.optionalFields("Children", DSL.list(DSL.optionalFields("CA", References.f_16783_.in(p_17660_), "CB", References.f_16783_.in(p_17660_), "CC", References.f_16783_.in(p_17660_), "CD", References.f_16783_.in(p_17660_))));
      });
      Map<String, Supplier<TypeTemplate>> map = V1451_6.m_181077_(p_17660_);
      p_17660_.registerType(false, References.f_16791_, () -> {
         return DSL.hook(DSL.optionalFields("CriteriaType", DSL.taggedChoiceLazy("type", DSL.string(), map)), V1451_6.f_181074_, V1451_6.f_181075_);
      });
      p_17660_.registerType(false, References.f_16792_, DSL::remainder);
      p_17660_.registerType(true, References.f_16789_, () -> {
         return DSL.optionalFields("SpawnPotentials", DSL.list(DSL.fields("Entity", References.f_16785_.in(p_17660_))), "SpawnData", References.f_16785_.in(p_17660_));
      });
      p_17660_.registerType(false, References.f_16779_, () -> {
         return DSL.optionalFields("minecraft:adventure/adventuring_time", DSL.optionalFields("criteria", DSL.compoundList(References.f_16794_.in(p_17660_), DSL.constType(DSL.string()))), "minecraft:adventure/kill_a_mob", DSL.optionalFields("criteria", DSL.compoundList(References.f_16784_.in(p_17660_), DSL.constType(DSL.string()))), "minecraft:adventure/kill_all_mobs", DSL.optionalFields("criteria", DSL.compoundList(References.f_16784_.in(p_17660_), DSL.constType(DSL.string()))), "minecraft:husbandry/bred_all_animals", DSL.optionalFields("criteria", DSL.compoundList(References.f_16784_.in(p_17660_), DSL.constType(DSL.string()))));
      });
      p_17660_.registerType(false, References.f_16794_, () -> {
         return DSL.constType(m_17310_());
      });
      p_17660_.registerType(false, References.f_16784_, () -> {
         return DSL.constType(m_17310_());
      });
      p_17660_.registerType(false, References.f_16780_, DSL::remainder);
      p_17660_.registerType(true, References.f_16795_, DSL::remainder);
      p_17660_.registerType(false, References.f_145628_, () -> {
         return DSL.optionalFields("Entities", DSL.list(References.f_16785_.in(p_17660_)));
      });
   }
}