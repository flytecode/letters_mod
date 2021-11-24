package net.minecraft.stats;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Stats {
   public static final StatType<Block> f_12949_ = m_13010_("mined", Registry.f_122824_);
   public static final StatType<Item> f_12981_ = m_13010_("crafted", Registry.f_122827_);
   public static final StatType<Item> f_12982_ = m_13010_("used", Registry.f_122827_);
   public static final StatType<Item> f_12983_ = m_13010_("broken", Registry.f_122827_);
   public static final StatType<Item> f_12984_ = m_13010_("picked_up", Registry.f_122827_);
   public static final StatType<Item> f_12985_ = m_13010_("dropped", Registry.f_122827_);
   public static final StatType<EntityType<?>> f_12986_ = m_13010_("killed", Registry.f_122826_);
   public static final StatType<EntityType<?>> f_12987_ = m_13010_("killed_by", Registry.f_122826_);
   public static final StatType<ResourceLocation> f_12988_ = m_13010_("custom", Registry.f_122832_);
   public static final ResourceLocation f_12989_ = m_13007_("leave_game", StatFormatter.f_12873_);
   public static final ResourceLocation f_144255_ = m_13007_("play_time", StatFormatter.f_12876_);
   public static final ResourceLocation f_144256_ = m_13007_("total_world_time", StatFormatter.f_12876_);
   public static final ResourceLocation f_12991_ = m_13007_("time_since_death", StatFormatter.f_12876_);
   public static final ResourceLocation f_12992_ = m_13007_("time_since_rest", StatFormatter.f_12876_);
   public static final ResourceLocation f_12993_ = m_13007_("sneak_time", StatFormatter.f_12876_);
   public static final ResourceLocation f_12994_ = m_13007_("walk_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_12995_ = m_13007_("crouch_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_12996_ = m_13007_("sprint_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_12997_ = m_13007_("walk_on_water_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_12998_ = m_13007_("fall_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_12999_ = m_13007_("climb_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_13000_ = m_13007_("fly_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_13001_ = m_13007_("walk_under_water_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_13002_ = m_13007_("minecart_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_13003_ = m_13007_("boat_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_13004_ = m_13007_("pig_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_13005_ = m_13007_("horse_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_12923_ = m_13007_("aviate_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_12924_ = m_13007_("swim_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_12925_ = m_13007_("strider_one_cm", StatFormatter.f_12875_);
   public static final ResourceLocation f_12926_ = m_13007_("jump", StatFormatter.f_12873_);
   public static final ResourceLocation f_12927_ = m_13007_("drop", StatFormatter.f_12873_);
   public static final ResourceLocation f_12928_ = m_13007_("damage_dealt", StatFormatter.f_12874_);
   public static final ResourceLocation f_12929_ = m_13007_("damage_dealt_absorbed", StatFormatter.f_12874_);
   public static final ResourceLocation f_12930_ = m_13007_("damage_dealt_resisted", StatFormatter.f_12874_);
   public static final ResourceLocation f_12931_ = m_13007_("damage_taken", StatFormatter.f_12874_);
   public static final ResourceLocation f_12932_ = m_13007_("damage_blocked_by_shield", StatFormatter.f_12874_);
   public static final ResourceLocation f_12933_ = m_13007_("damage_absorbed", StatFormatter.f_12874_);
   public static final ResourceLocation f_12934_ = m_13007_("damage_resisted", StatFormatter.f_12874_);
   public static final ResourceLocation f_12935_ = m_13007_("deaths", StatFormatter.f_12873_);
   public static final ResourceLocation f_12936_ = m_13007_("mob_kills", StatFormatter.f_12873_);
   public static final ResourceLocation f_12937_ = m_13007_("animals_bred", StatFormatter.f_12873_);
   public static final ResourceLocation f_12938_ = m_13007_("player_kills", StatFormatter.f_12873_);
   public static final ResourceLocation f_12939_ = m_13007_("fish_caught", StatFormatter.f_12873_);
   public static final ResourceLocation f_12940_ = m_13007_("talked_to_villager", StatFormatter.f_12873_);
   public static final ResourceLocation f_12941_ = m_13007_("traded_with_villager", StatFormatter.f_12873_);
   public static final ResourceLocation f_12942_ = m_13007_("eat_cake_slice", StatFormatter.f_12873_);
   public static final ResourceLocation f_12943_ = m_13007_("fill_cauldron", StatFormatter.f_12873_);
   public static final ResourceLocation f_12944_ = m_13007_("use_cauldron", StatFormatter.f_12873_);
   public static final ResourceLocation f_12945_ = m_13007_("clean_armor", StatFormatter.f_12873_);
   public static final ResourceLocation f_12946_ = m_13007_("clean_banner", StatFormatter.f_12873_);
   public static final ResourceLocation f_12947_ = m_13007_("clean_shulker_box", StatFormatter.f_12873_);
   public static final ResourceLocation f_12948_ = m_13007_("interact_with_brewingstand", StatFormatter.f_12873_);
   public static final ResourceLocation f_12955_ = m_13007_("interact_with_beacon", StatFormatter.f_12873_);
   public static final ResourceLocation f_12956_ = m_13007_("inspect_dropper", StatFormatter.f_12873_);
   public static final ResourceLocation f_12957_ = m_13007_("inspect_hopper", StatFormatter.f_12873_);
   public static final ResourceLocation f_12958_ = m_13007_("inspect_dispenser", StatFormatter.f_12873_);
   public static final ResourceLocation f_12959_ = m_13007_("play_noteblock", StatFormatter.f_12873_);
   public static final ResourceLocation f_12960_ = m_13007_("tune_noteblock", StatFormatter.f_12873_);
   public static final ResourceLocation f_12961_ = m_13007_("pot_flower", StatFormatter.f_12873_);
   public static final ResourceLocation f_12962_ = m_13007_("trigger_trapped_chest", StatFormatter.f_12873_);
   public static final ResourceLocation f_12963_ = m_13007_("open_enderchest", StatFormatter.f_12873_);
   public static final ResourceLocation f_12964_ = m_13007_("enchant_item", StatFormatter.f_12873_);
   public static final ResourceLocation f_12965_ = m_13007_("play_record", StatFormatter.f_12873_);
   public static final ResourceLocation f_12966_ = m_13007_("interact_with_furnace", StatFormatter.f_12873_);
   public static final ResourceLocation f_12967_ = m_13007_("interact_with_crafting_table", StatFormatter.f_12873_);
   public static final ResourceLocation f_12968_ = m_13007_("open_chest", StatFormatter.f_12873_);
   public static final ResourceLocation f_12969_ = m_13007_("sleep_in_bed", StatFormatter.f_12873_);
   public static final ResourceLocation f_12970_ = m_13007_("open_shulker_box", StatFormatter.f_12873_);
   public static final ResourceLocation f_12971_ = m_13007_("open_barrel", StatFormatter.f_12873_);
   public static final ResourceLocation f_12972_ = m_13007_("interact_with_blast_furnace", StatFormatter.f_12873_);
   public static final ResourceLocation f_12973_ = m_13007_("interact_with_smoker", StatFormatter.f_12873_);
   public static final ResourceLocation f_12974_ = m_13007_("interact_with_lectern", StatFormatter.f_12873_);
   public static final ResourceLocation f_12975_ = m_13007_("interact_with_campfire", StatFormatter.f_12873_);
   public static final ResourceLocation f_12976_ = m_13007_("interact_with_cartography_table", StatFormatter.f_12873_);
   public static final ResourceLocation f_12977_ = m_13007_("interact_with_loom", StatFormatter.f_12873_);
   public static final ResourceLocation f_12978_ = m_13007_("interact_with_stonecutter", StatFormatter.f_12873_);
   public static final ResourceLocation f_12979_ = m_13007_("bell_ring", StatFormatter.f_12873_);
   public static final ResourceLocation f_12980_ = m_13007_("raid_trigger", StatFormatter.f_12873_);
   public static final ResourceLocation f_12950_ = m_13007_("raid_win", StatFormatter.f_12873_);
   public static final ResourceLocation f_12951_ = m_13007_("interact_with_anvil", StatFormatter.f_12873_);
   public static final ResourceLocation f_12952_ = m_13007_("interact_with_grindstone", StatFormatter.f_12873_);
   public static final ResourceLocation f_12953_ = m_13007_("target_hit", StatFormatter.f_12873_);
   public static final ResourceLocation f_12954_ = m_13007_("interact_with_smithing_table", StatFormatter.f_12873_);

   private static ResourceLocation m_13007_(String p_13008_, StatFormatter p_13009_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_13008_);
      Registry.m_122961_(Registry.f_122832_, p_13008_, resourcelocation);
      f_12988_.m_12899_(resourcelocation, p_13009_);
      return resourcelocation;
   }

   private static <T> StatType<T> m_13010_(String p_13011_, Registry<T> p_13012_) {
      return Registry.m_122961_(Registry.f_122867_, p_13011_, new StatType<>(p_13012_));
   }
}