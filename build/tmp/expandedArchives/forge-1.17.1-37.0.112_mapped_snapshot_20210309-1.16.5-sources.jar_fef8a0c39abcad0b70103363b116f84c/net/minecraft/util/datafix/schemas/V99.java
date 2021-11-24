package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class V99 extends Schema {
   private static final Logger f_18181_ = LogManager.getLogger();
   static final Map<String, String> f_18182_ = DataFixUtils.make(Maps.newHashMap(), (p_145919_) -> {
      p_145919_.put("minecraft:furnace", "Furnace");
      p_145919_.put("minecraft:lit_furnace", "Furnace");
      p_145919_.put("minecraft:chest", "Chest");
      p_145919_.put("minecraft:trapped_chest", "Chest");
      p_145919_.put("minecraft:ender_chest", "EnderChest");
      p_145919_.put("minecraft:jukebox", "RecordPlayer");
      p_145919_.put("minecraft:dispenser", "Trap");
      p_145919_.put("minecraft:dropper", "Dropper");
      p_145919_.put("minecraft:sign", "Sign");
      p_145919_.put("minecraft:mob_spawner", "MobSpawner");
      p_145919_.put("minecraft:noteblock", "Music");
      p_145919_.put("minecraft:brewing_stand", "Cauldron");
      p_145919_.put("minecraft:enhanting_table", "EnchantTable");
      p_145919_.put("minecraft:command_block", "CommandBlock");
      p_145919_.put("minecraft:beacon", "Beacon");
      p_145919_.put("minecraft:skull", "Skull");
      p_145919_.put("minecraft:daylight_detector", "DLDetector");
      p_145919_.put("minecraft:hopper", "Hopper");
      p_145919_.put("minecraft:banner", "Banner");
      p_145919_.put("minecraft:flower_pot", "FlowerPot");
      p_145919_.put("minecraft:repeating_command_block", "CommandBlock");
      p_145919_.put("minecraft:chain_command_block", "CommandBlock");
      p_145919_.put("minecraft:standing_sign", "Sign");
      p_145919_.put("minecraft:wall_sign", "Sign");
      p_145919_.put("minecraft:piston_head", "Piston");
      p_145919_.put("minecraft:daylight_detector_inverted", "DLDetector");
      p_145919_.put("minecraft:unpowered_comparator", "Comparator");
      p_145919_.put("minecraft:powered_comparator", "Comparator");
      p_145919_.put("minecraft:wall_banner", "Banner");
      p_145919_.put("minecraft:standing_banner", "Banner");
      p_145919_.put("minecraft:structure_block", "Structure");
      p_145919_.put("minecraft:end_portal", "Airportal");
      p_145919_.put("minecraft:end_gateway", "EndGateway");
      p_145919_.put("minecraft:shield", "Banner");
   });
   protected static final HookFunction f_18180_ = new HookFunction() {
      public <T> T apply(DynamicOps<T> p_18312_, T p_18313_) {
         return V99.m_18205_(new Dynamic<>(p_18312_, p_18313_), V99.f_18182_, "ArmorStand");
      }
   };

   public V99(int p_18185_, Schema p_18186_) {
      super(p_18185_, p_18186_);
   }

   protected static TypeTemplate m_18188_(Schema p_18189_) {
      return DSL.optionalFields("Equipment", DSL.list(References.f_16782_.in(p_18189_)));
   }

   protected static void m_18193_(Schema p_18194_, Map<String, Supplier<TypeTemplate>> p_18195_, String p_18196_) {
      p_18194_.register(p_18195_, p_18196_, () -> {
         return m_18188_(p_18194_);
      });
   }

   protected static void m_18224_(Schema p_18225_, Map<String, Supplier<TypeTemplate>> p_18226_, String p_18227_) {
      p_18225_.register(p_18226_, p_18227_, () -> {
         return DSL.optionalFields("inTile", References.f_16787_.in(p_18225_));
      });
   }

   protected static void m_18236_(Schema p_18237_, Map<String, Supplier<TypeTemplate>> p_18238_, String p_18239_) {
      p_18237_.register(p_18238_, p_18239_, () -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18237_));
      });
   }

   protected static void m_18246_(Schema p_18247_, Map<String, Supplier<TypeTemplate>> p_18248_, String p_18249_) {
      p_18247_.register(p_18248_, p_18249_, () -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_18247_)));
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_18305_) {
      Map<String, Supplier<TypeTemplate>> map = Maps.newHashMap();
      p_18305_.register(map, "Item", (p_18301_) -> {
         return DSL.optionalFields("Item", References.f_16782_.in(p_18305_));
      });
      p_18305_.registerSimple(map, "XPOrb");
      m_18224_(p_18305_, map, "ThrownEgg");
      p_18305_.registerSimple(map, "LeashKnot");
      p_18305_.registerSimple(map, "Painting");
      p_18305_.register(map, "Arrow", (p_18298_) -> {
         return DSL.optionalFields("inTile", References.f_16787_.in(p_18305_));
      });
      p_18305_.register(map, "TippedArrow", (p_18295_) -> {
         return DSL.optionalFields("inTile", References.f_16787_.in(p_18305_));
      });
      p_18305_.register(map, "SpectralArrow", (p_18292_) -> {
         return DSL.optionalFields("inTile", References.f_16787_.in(p_18305_));
      });
      m_18224_(p_18305_, map, "Snowball");
      m_18224_(p_18305_, map, "Fireball");
      m_18224_(p_18305_, map, "SmallFireball");
      m_18224_(p_18305_, map, "ThrownEnderpearl");
      p_18305_.registerSimple(map, "EyeOfEnderSignal");
      p_18305_.register(map, "ThrownPotion", (p_18289_) -> {
         return DSL.optionalFields("inTile", References.f_16787_.in(p_18305_), "Potion", References.f_16782_.in(p_18305_));
      });
      m_18224_(p_18305_, map, "ThrownExpBottle");
      p_18305_.register(map, "ItemFrame", (p_18284_) -> {
         return DSL.optionalFields("Item", References.f_16782_.in(p_18305_));
      });
      m_18224_(p_18305_, map, "WitherSkull");
      p_18305_.registerSimple(map, "PrimedTnt");
      p_18305_.register(map, "FallingSand", (p_18279_) -> {
         return DSL.optionalFields("Block", References.f_16787_.in(p_18305_), "TileEntityData", References.f_16781_.in(p_18305_));
      });
      p_18305_.register(map, "FireworksRocketEntity", (p_18274_) -> {
         return DSL.optionalFields("FireworksItem", References.f_16782_.in(p_18305_));
      });
      p_18305_.registerSimple(map, "Boat");
      p_18305_.register(map, "Minecart", () -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18305_), "Items", DSL.list(References.f_16782_.in(p_18305_)));
      });
      m_18236_(p_18305_, map, "MinecartRideable");
      p_18305_.register(map, "MinecartChest", (p_18269_) -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18305_), "Items", DSL.list(References.f_16782_.in(p_18305_)));
      });
      m_18236_(p_18305_, map, "MinecartFurnace");
      m_18236_(p_18305_, map, "MinecartTNT");
      p_18305_.register(map, "MinecartSpawner", () -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18305_), References.f_16789_.in(p_18305_));
      });
      p_18305_.register(map, "MinecartHopper", (p_18264_) -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18305_), "Items", DSL.list(References.f_16782_.in(p_18305_)));
      });
      m_18236_(p_18305_, map, "MinecartCommandBlock");
      m_18193_(p_18305_, map, "ArmorStand");
      m_18193_(p_18305_, map, "Creeper");
      m_18193_(p_18305_, map, "Skeleton");
      m_18193_(p_18305_, map, "Spider");
      m_18193_(p_18305_, map, "Giant");
      m_18193_(p_18305_, map, "Zombie");
      m_18193_(p_18305_, map, "Slime");
      m_18193_(p_18305_, map, "Ghast");
      m_18193_(p_18305_, map, "PigZombie");
      p_18305_.register(map, "Enderman", (p_18259_) -> {
         return DSL.optionalFields("carried", References.f_16787_.in(p_18305_), m_18188_(p_18305_));
      });
      m_18193_(p_18305_, map, "CaveSpider");
      m_18193_(p_18305_, map, "Silverfish");
      m_18193_(p_18305_, map, "Blaze");
      m_18193_(p_18305_, map, "LavaSlime");
      m_18193_(p_18305_, map, "EnderDragon");
      m_18193_(p_18305_, map, "WitherBoss");
      m_18193_(p_18305_, map, "Bat");
      m_18193_(p_18305_, map, "Witch");
      m_18193_(p_18305_, map, "Endermite");
      m_18193_(p_18305_, map, "Guardian");
      m_18193_(p_18305_, map, "Pig");
      m_18193_(p_18305_, map, "Sheep");
      m_18193_(p_18305_, map, "Cow");
      m_18193_(p_18305_, map, "Chicken");
      m_18193_(p_18305_, map, "Squid");
      m_18193_(p_18305_, map, "Wolf");
      m_18193_(p_18305_, map, "MushroomCow");
      m_18193_(p_18305_, map, "SnowMan");
      m_18193_(p_18305_, map, "Ozelot");
      m_18193_(p_18305_, map, "VillagerGolem");
      p_18305_.register(map, "EntityHorse", (p_18254_) -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_18305_)), "ArmorItem", References.f_16782_.in(p_18305_), "SaddleItem", References.f_16782_.in(p_18305_), m_18188_(p_18305_));
      });
      m_18193_(p_18305_, map, "Rabbit");
      p_18305_.register(map, "Villager", (p_18245_) -> {
         return DSL.optionalFields("Inventory", DSL.list(References.f_16782_.in(p_18305_)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", References.f_16782_.in(p_18305_), "buyB", References.f_16782_.in(p_18305_), "sell", References.f_16782_.in(p_18305_)))), m_18188_(p_18305_));
      });
      p_18305_.registerSimple(map, "EnderCrystal");
      p_18305_.registerSimple(map, "AreaEffectCloud");
      p_18305_.registerSimple(map, "ShulkerBullet");
      m_18193_(p_18305_, map, "Shulker");
      return map;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_18303_) {
      Map<String, Supplier<TypeTemplate>> map = Maps.newHashMap();
      m_18246_(p_18303_, map, "Furnace");
      m_18246_(p_18303_, map, "Chest");
      p_18303_.registerSimple(map, "EnderChest");
      p_18303_.register(map, "RecordPlayer", (p_18235_) -> {
         return DSL.optionalFields("RecordItem", References.f_16782_.in(p_18303_));
      });
      m_18246_(p_18303_, map, "Trap");
      m_18246_(p_18303_, map, "Dropper");
      p_18303_.registerSimple(map, "Sign");
      p_18303_.register(map, "MobSpawner", (p_18223_) -> {
         return References.f_16789_.in(p_18303_);
      });
      p_18303_.registerSimple(map, "Music");
      p_18303_.registerSimple(map, "Piston");
      m_18246_(p_18303_, map, "Cauldron");
      p_18303_.registerSimple(map, "EnchantTable");
      p_18303_.registerSimple(map, "Airportal");
      p_18303_.registerSimple(map, "Control");
      p_18303_.registerSimple(map, "Beacon");
      p_18303_.registerSimple(map, "Skull");
      p_18303_.registerSimple(map, "DLDetector");
      m_18246_(p_18303_, map, "Hopper");
      p_18303_.registerSimple(map, "Comparator");
      p_18303_.register(map, "FlowerPot", (p_18192_) -> {
         return DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), References.f_16788_.in(p_18303_)));
      });
      p_18303_.registerSimple(map, "Banner");
      p_18303_.registerSimple(map, "Structure");
      p_18303_.registerSimple(map, "EndGateway");
      return map;
   }

   public void registerTypes(Schema p_18307_, Map<String, Supplier<TypeTemplate>> p_18308_, Map<String, Supplier<TypeTemplate>> p_18309_) {
      p_18307_.registerType(false, References.f_16771_, DSL::remainder);
      p_18307_.registerType(false, References.f_16772_, () -> {
         return DSL.optionalFields("Inventory", DSL.list(References.f_16782_.in(p_18307_)), "EnderItems", DSL.list(References.f_16782_.in(p_18307_)));
      });
      p_18307_.registerType(false, References.f_16773_, () -> {
         return DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(References.f_16785_.in(p_18307_)), "TileEntities", DSL.list(References.f_16781_.in(p_18307_)), "TileTicks", DSL.list(DSL.fields("i", References.f_16787_.in(p_18307_)))));
      });
      p_18307_.registerType(true, References.f_16781_, () -> {
         return DSL.taggedChoiceLazy("id", DSL.string(), p_18309_);
      });
      p_18307_.registerType(true, References.f_16785_, () -> {
         return DSL.optionalFields("Riding", References.f_16785_.in(p_18307_), References.f_16786_.in(p_18307_));
      });
      p_18307_.registerType(false, References.f_16784_, () -> {
         return DSL.constType(NamespacedSchema.m_17310_());
      });
      p_18307_.registerType(true, References.f_16786_, () -> {
         return DSL.taggedChoiceLazy("id", DSL.string(), p_18308_);
      });
      p_18307_.registerType(true, References.f_16782_, () -> {
         return DSL.hook(DSL.optionalFields("id", DSL.or(DSL.constType(DSL.intType()), References.f_16788_.in(p_18307_)), "tag", DSL.optionalFields("EntityTag", References.f_16785_.in(p_18307_), "BlockEntityTag", References.f_16781_.in(p_18307_), "CanDestroy", DSL.list(References.f_16787_.in(p_18307_)), "CanPlaceOn", DSL.list(References.f_16787_.in(p_18307_)), "Items", DSL.list(References.f_16782_.in(p_18307_)))), f_18180_, HookFunction.IDENTITY);
      });
      p_18307_.registerType(false, References.f_16775_, DSL::remainder);
      p_18307_.registerType(false, References.f_16787_, () -> {
         return DSL.or(DSL.constType(DSL.intType()), DSL.constType(NamespacedSchema.m_17310_()));
      });
      p_18307_.registerType(false, References.f_16788_, () -> {
         return DSL.constType(NamespacedSchema.m_17310_());
      });
      p_18307_.registerType(false, References.f_16777_, DSL::remainder);
      p_18307_.registerType(false, References.f_16778_, () -> {
         return DSL.optionalFields("data", DSL.optionalFields("Features", DSL.compoundList(References.f_16790_.in(p_18307_)), "Objectives", DSL.list(References.f_16791_.in(p_18307_)), "Teams", DSL.list(References.f_16792_.in(p_18307_))));
      });
      p_18307_.registerType(false, References.f_16790_, DSL::remainder);
      p_18307_.registerType(false, References.f_16791_, DSL::remainder);
      p_18307_.registerType(false, References.f_16792_, DSL::remainder);
      p_18307_.registerType(true, References.f_16789_, DSL::remainder);
      p_18307_.registerType(false, References.f_16780_, DSL::remainder);
      p_18307_.registerType(true, References.f_16795_, DSL::remainder);
      p_18307_.registerType(false, References.f_145628_, () -> {
         return DSL.optionalFields("Entities", DSL.list(References.f_16785_.in(p_18307_)));
      });
   }

   protected static <T> T m_18205_(Dynamic<T> p_18206_, Map<String, String> p_18207_, String p_18208_) {
      return p_18206_.update("tag", (p_145917_) -> {
         return p_145917_.update("BlockEntityTag", (p_145912_) -> {
            String s = p_18206_.get("id").asString().result().map(NamespacedSchema::m_17311_).orElse("minecraft:air");
            if (!"minecraft:air".equals(s)) {
               String s1 = p_18207_.get(s);
               if (s1 != null) {
                  return p_145912_.set("id", p_18206_.createString(s1));
               }

               f_18181_.warn("Unable to resolve BlockEntity for ItemStack: {}", (Object)s);
            }

            return p_145912_;
         }).update("EntityTag", (p_145908_) -> {
            String s = p_18206_.get("id").asString("");
            return "minecraft:armor_stand".equals(NamespacedSchema.m_17311_(s)) ? p_145908_.set("id", p_18206_.createString(p_18208_)) : p_145908_;
         });
      }).getValue();
   }
}