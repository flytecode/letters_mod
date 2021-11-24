package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V705 extends NamespacedSchema {
   protected static final HookFunction f_18072_ = new HookFunction() {
      public <T> T apply(DynamicOps<T> p_18167_, T p_18168_) {
         return V99.m_18205_(new Dynamic<>(p_18167_, p_18168_), V704.f_18032_, "minecraft:armor_stand");
      }
   };

   public V705(int p_18075_, Schema p_18076_) {
      super(p_18075_, p_18076_);
   }

   protected static void m_18082_(Schema p_18083_, Map<String, Supplier<TypeTemplate>> p_18084_, String p_18085_) {
      p_18083_.register(p_18084_, p_18085_, () -> {
         return V100.m_17330_(p_18083_);
      });
   }

   protected static void m_18093_(Schema p_18094_, Map<String, Supplier<TypeTemplate>> p_18095_, String p_18096_) {
      p_18094_.register(p_18095_, p_18096_, () -> {
         return DSL.optionalFields("inTile", References.f_16787_.in(p_18094_));
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_18148_) {
      Map<String, Supplier<TypeTemplate>> map = Maps.newHashMap();
      p_18148_.registerSimple(map, "minecraft:area_effect_cloud");
      m_18082_(p_18148_, map, "minecraft:armor_stand");
      p_18148_.register(map, "minecraft:arrow", (p_18164_) -> {
         return DSL.optionalFields("inTile", References.f_16787_.in(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:bat");
      m_18082_(p_18148_, map, "minecraft:blaze");
      p_18148_.registerSimple(map, "minecraft:boat");
      m_18082_(p_18148_, map, "minecraft:cave_spider");
      p_18148_.register(map, "minecraft:chest_minecart", (p_18161_) -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18148_), "Items", DSL.list(References.f_16782_.in(p_18148_)));
      });
      m_18082_(p_18148_, map, "minecraft:chicken");
      p_18148_.register(map, "minecraft:commandblock_minecart", (p_18158_) -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:cow");
      m_18082_(p_18148_, map, "minecraft:creeper");
      p_18148_.register(map, "minecraft:donkey", (p_18155_) -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_18148_)), "SaddleItem", References.f_16782_.in(p_18148_), V100.m_17330_(p_18148_));
      });
      p_18148_.registerSimple(map, "minecraft:dragon_fireball");
      m_18093_(p_18148_, map, "minecraft:egg");
      m_18082_(p_18148_, map, "minecraft:elder_guardian");
      p_18148_.registerSimple(map, "minecraft:ender_crystal");
      m_18082_(p_18148_, map, "minecraft:ender_dragon");
      p_18148_.register(map, "minecraft:enderman", (p_18146_) -> {
         return DSL.optionalFields("carried", References.f_16787_.in(p_18148_), V100.m_17330_(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:endermite");
      m_18093_(p_18148_, map, "minecraft:ender_pearl");
      p_18148_.registerSimple(map, "minecraft:eye_of_ender_signal");
      p_18148_.register(map, "minecraft:falling_block", (p_18143_) -> {
         return DSL.optionalFields("Block", References.f_16787_.in(p_18148_), "TileEntityData", References.f_16781_.in(p_18148_));
      });
      m_18093_(p_18148_, map, "minecraft:fireball");
      p_18148_.register(map, "minecraft:fireworks_rocket", (p_18140_) -> {
         return DSL.optionalFields("FireworksItem", References.f_16782_.in(p_18148_));
      });
      p_18148_.register(map, "minecraft:furnace_minecart", (p_18137_) -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:ghast");
      m_18082_(p_18148_, map, "minecraft:giant");
      m_18082_(p_18148_, map, "minecraft:guardian");
      p_18148_.register(map, "minecraft:hopper_minecart", (p_18134_) -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18148_), "Items", DSL.list(References.f_16782_.in(p_18148_)));
      });
      p_18148_.register(map, "minecraft:horse", (p_18131_) -> {
         return DSL.optionalFields("ArmorItem", References.f_16782_.in(p_18148_), "SaddleItem", References.f_16782_.in(p_18148_), V100.m_17330_(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:husk");
      p_18148_.register(map, "minecraft:item", (p_18128_) -> {
         return DSL.optionalFields("Item", References.f_16782_.in(p_18148_));
      });
      p_18148_.register(map, "minecraft:item_frame", (p_18125_) -> {
         return DSL.optionalFields("Item", References.f_16782_.in(p_18148_));
      });
      p_18148_.registerSimple(map, "minecraft:leash_knot");
      m_18082_(p_18148_, map, "minecraft:magma_cube");
      p_18148_.register(map, "minecraft:minecart", (p_18122_) -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:mooshroom");
      p_18148_.register(map, "minecraft:mule", (p_18119_) -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_18148_)), "SaddleItem", References.f_16782_.in(p_18148_), V100.m_17330_(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:ocelot");
      p_18148_.registerSimple(map, "minecraft:painting");
      p_18148_.registerSimple(map, "minecraft:parrot");
      m_18082_(p_18148_, map, "minecraft:pig");
      m_18082_(p_18148_, map, "minecraft:polar_bear");
      p_18148_.register(map, "minecraft:potion", (p_18116_) -> {
         return DSL.optionalFields("Potion", References.f_16782_.in(p_18148_), "inTile", References.f_16787_.in(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:rabbit");
      m_18082_(p_18148_, map, "minecraft:sheep");
      m_18082_(p_18148_, map, "minecraft:shulker");
      p_18148_.registerSimple(map, "minecraft:shulker_bullet");
      m_18082_(p_18148_, map, "minecraft:silverfish");
      m_18082_(p_18148_, map, "minecraft:skeleton");
      p_18148_.register(map, "minecraft:skeleton_horse", (p_18113_) -> {
         return DSL.optionalFields("SaddleItem", References.f_16782_.in(p_18148_), V100.m_17330_(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:slime");
      m_18093_(p_18148_, map, "minecraft:small_fireball");
      m_18093_(p_18148_, map, "minecraft:snowball");
      m_18082_(p_18148_, map, "minecraft:snowman");
      p_18148_.register(map, "minecraft:spawner_minecart", (p_18110_) -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18148_), References.f_16789_.in(p_18148_));
      });
      p_18148_.register(map, "minecraft:spectral_arrow", (p_18107_) -> {
         return DSL.optionalFields("inTile", References.f_16787_.in(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:spider");
      m_18082_(p_18148_, map, "minecraft:squid");
      m_18082_(p_18148_, map, "minecraft:stray");
      p_18148_.registerSimple(map, "minecraft:tnt");
      p_18148_.register(map, "minecraft:tnt_minecart", (p_18104_) -> {
         return DSL.optionalFields("DisplayTile", References.f_16787_.in(p_18148_));
      });
      p_18148_.register(map, "minecraft:villager", (p_18101_) -> {
         return DSL.optionalFields("Inventory", DSL.list(References.f_16782_.in(p_18148_)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", References.f_16782_.in(p_18148_), "buyB", References.f_16782_.in(p_18148_), "sell", References.f_16782_.in(p_18148_)))), V100.m_17330_(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:villager_golem");
      m_18082_(p_18148_, map, "minecraft:witch");
      m_18082_(p_18148_, map, "minecraft:wither");
      m_18082_(p_18148_, map, "minecraft:wither_skeleton");
      m_18093_(p_18148_, map, "minecraft:wither_skull");
      m_18082_(p_18148_, map, "minecraft:wolf");
      m_18093_(p_18148_, map, "minecraft:xp_bottle");
      p_18148_.registerSimple(map, "minecraft:xp_orb");
      m_18082_(p_18148_, map, "minecraft:zombie");
      p_18148_.register(map, "minecraft:zombie_horse", (p_18092_) -> {
         return DSL.optionalFields("SaddleItem", References.f_16782_.in(p_18148_), V100.m_17330_(p_18148_));
      });
      m_18082_(p_18148_, map, "minecraft:zombie_pigman");
      m_18082_(p_18148_, map, "minecraft:zombie_villager");
      p_18148_.registerSimple(map, "minecraft:evocation_fangs");
      m_18082_(p_18148_, map, "minecraft:evocation_illager");
      p_18148_.registerSimple(map, "minecraft:illusion_illager");
      p_18148_.register(map, "minecraft:llama", (p_18081_) -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_18148_)), "SaddleItem", References.f_16782_.in(p_18148_), "DecorItem", References.f_16782_.in(p_18148_), V100.m_17330_(p_18148_));
      });
      p_18148_.registerSimple(map, "minecraft:llama_spit");
      m_18082_(p_18148_, map, "minecraft:vex");
      m_18082_(p_18148_, map, "minecraft:vindication_illager");
      return map;
   }

   public void registerTypes(Schema p_18150_, Map<String, Supplier<TypeTemplate>> p_18151_, Map<String, Supplier<TypeTemplate>> p_18152_) {
      super.registerTypes(p_18150_, p_18151_, p_18152_);
      p_18150_.registerType(true, References.f_16786_, () -> {
         return DSL.taggedChoiceLazy("id", m_17310_(), p_18151_);
      });
      p_18150_.registerType(true, References.f_16782_, () -> {
         return DSL.hook(DSL.optionalFields("id", References.f_16788_.in(p_18150_), "tag", DSL.optionalFields("EntityTag", References.f_16785_.in(p_18150_), "BlockEntityTag", References.f_16781_.in(p_18150_), "CanDestroy", DSL.list(References.f_16787_.in(p_18150_)), "CanPlaceOn", DSL.list(References.f_16787_.in(p_18150_)), "Items", DSL.list(References.f_16782_.in(p_18150_)))), f_18072_, HookFunction.IDENTITY);
      });
   }
}