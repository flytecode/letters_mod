package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V100 extends Schema {
   public V100(int p_17328_, Schema p_17329_) {
      super(p_17328_, p_17329_);
   }

   protected static TypeTemplate m_17330_(Schema p_17331_) {
      return DSL.optionalFields("ArmorItems", DSL.list(References.f_16782_.in(p_17331_)), "HandItems", DSL.list(References.f_16782_.in(p_17331_)));
   }

   protected static void m_17335_(Schema p_17336_, Map<String, Supplier<TypeTemplate>> p_17337_, String p_17338_) {
      p_17336_.register(p_17337_, p_17338_, () -> {
         return m_17330_(p_17336_);
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17350_) {
      Map<String, Supplier<TypeTemplate>> map = super.registerEntities(p_17350_);
      m_17335_(p_17350_, map, "ArmorStand");
      m_17335_(p_17350_, map, "Creeper");
      m_17335_(p_17350_, map, "Skeleton");
      m_17335_(p_17350_, map, "Spider");
      m_17335_(p_17350_, map, "Giant");
      m_17335_(p_17350_, map, "Zombie");
      m_17335_(p_17350_, map, "Slime");
      m_17335_(p_17350_, map, "Ghast");
      m_17335_(p_17350_, map, "PigZombie");
      p_17350_.register(map, "Enderman", (p_17348_) -> {
         return DSL.optionalFields("carried", References.f_16787_.in(p_17350_), m_17330_(p_17350_));
      });
      m_17335_(p_17350_, map, "CaveSpider");
      m_17335_(p_17350_, map, "Silverfish");
      m_17335_(p_17350_, map, "Blaze");
      m_17335_(p_17350_, map, "LavaSlime");
      m_17335_(p_17350_, map, "EnderDragon");
      m_17335_(p_17350_, map, "WitherBoss");
      m_17335_(p_17350_, map, "Bat");
      m_17335_(p_17350_, map, "Witch");
      m_17335_(p_17350_, map, "Endermite");
      m_17335_(p_17350_, map, "Guardian");
      m_17335_(p_17350_, map, "Pig");
      m_17335_(p_17350_, map, "Sheep");
      m_17335_(p_17350_, map, "Cow");
      m_17335_(p_17350_, map, "Chicken");
      m_17335_(p_17350_, map, "Squid");
      m_17335_(p_17350_, map, "Wolf");
      m_17335_(p_17350_, map, "MushroomCow");
      m_17335_(p_17350_, map, "SnowMan");
      m_17335_(p_17350_, map, "Ozelot");
      m_17335_(p_17350_, map, "VillagerGolem");
      p_17350_.register(map, "EntityHorse", (p_17343_) -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_17350_)), "ArmorItem", References.f_16782_.in(p_17350_), "SaddleItem", References.f_16782_.in(p_17350_), m_17330_(p_17350_));
      });
      m_17335_(p_17350_, map, "Rabbit");
      p_17350_.register(map, "Villager", (p_17334_) -> {
         return DSL.optionalFields("Inventory", DSL.list(References.f_16782_.in(p_17350_)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", References.f_16782_.in(p_17350_), "buyB", References.f_16782_.in(p_17350_), "sell", References.f_16782_.in(p_17350_)))), m_17330_(p_17350_));
      });
      m_17335_(p_17350_, map, "Shulker");
      p_17350_.registerSimple(map, "AreaEffectCloud");
      p_17350_.registerSimple(map, "ShulkerBullet");
      return map;
   }

   public void registerTypes(Schema p_17352_, Map<String, Supplier<TypeTemplate>> p_17353_, Map<String, Supplier<TypeTemplate>> p_17354_) {
      super.registerTypes(p_17352_, p_17353_, p_17354_);
      p_17352_.registerType(false, References.f_16776_, () -> {
         return DSL.optionalFields("entities", DSL.list(DSL.optionalFields("nbt", References.f_16785_.in(p_17352_))), "blocks", DSL.list(DSL.optionalFields("nbt", References.f_16781_.in(p_17352_))), "palette", DSL.list(References.f_16783_.in(p_17352_)));
      });
      p_17352_.registerType(false, References.f_16783_, DSL::remainder);
   }
}