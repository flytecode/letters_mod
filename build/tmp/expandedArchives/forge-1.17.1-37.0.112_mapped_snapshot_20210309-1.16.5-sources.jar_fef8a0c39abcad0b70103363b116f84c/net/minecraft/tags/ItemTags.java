package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public final class ItemTags {
   protected static final StaticTagHelper<Item> f_13163_ = StaticTags.m_144351_(Registry.f_122904_, "tags/items");
   public static final Tag.Named<Item> f_13167_ = m_13194_("wool");
   public static final Tag.Named<Item> f_13168_ = m_13194_("planks");
   public static final Tag.Named<Item> f_13169_ = m_13194_("stone_bricks");
   public static final Tag.Named<Item> f_13170_ = m_13194_("wooden_buttons");
   public static final Tag.Named<Item> f_13171_ = m_13194_("buttons");
   public static final Tag.Named<Item> f_13172_ = m_13194_("carpets");
   public static final Tag.Named<Item> f_13173_ = m_13194_("wooden_doors");
   public static final Tag.Named<Item> f_13174_ = m_13194_("wooden_stairs");
   public static final Tag.Named<Item> f_13175_ = m_13194_("wooden_slabs");
   public static final Tag.Named<Item> f_13176_ = m_13194_("wooden_fences");
   public static final Tag.Named<Item> f_13177_ = m_13194_("wooden_pressure_plates");
   public static final Tag.Named<Item> f_13178_ = m_13194_("wooden_trapdoors");
   public static final Tag.Named<Item> f_13179_ = m_13194_("doors");
   public static final Tag.Named<Item> f_13180_ = m_13194_("saplings");
   public static final Tag.Named<Item> f_13181_ = m_13194_("logs_that_burn");
   public static final Tag.Named<Item> f_13182_ = m_13194_("logs");
   public static final Tag.Named<Item> f_13183_ = m_13194_("dark_oak_logs");
   public static final Tag.Named<Item> f_13184_ = m_13194_("oak_logs");
   public static final Tag.Named<Item> f_13185_ = m_13194_("birch_logs");
   public static final Tag.Named<Item> f_13186_ = m_13194_("acacia_logs");
   public static final Tag.Named<Item> f_13187_ = m_13194_("jungle_logs");
   public static final Tag.Named<Item> f_13188_ = m_13194_("spruce_logs");
   public static final Tag.Named<Item> f_13189_ = m_13194_("crimson_stems");
   public static final Tag.Named<Item> f_13190_ = m_13194_("warped_stems");
   public static final Tag.Named<Item> f_13191_ = m_13194_("banners");
   public static final Tag.Named<Item> f_13137_ = m_13194_("sand");
   public static final Tag.Named<Item> f_13138_ = m_13194_("stairs");
   public static final Tag.Named<Item> f_13139_ = m_13194_("slabs");
   public static final Tag.Named<Item> f_13140_ = m_13194_("walls");
   public static final Tag.Named<Item> f_13141_ = m_13194_("anvil");
   public static final Tag.Named<Item> f_13142_ = m_13194_("rails");
   public static final Tag.Named<Item> f_13143_ = m_13194_("leaves");
   public static final Tag.Named<Item> f_13144_ = m_13194_("trapdoors");
   public static final Tag.Named<Item> f_13145_ = m_13194_("small_flowers");
   public static final Tag.Named<Item> f_13146_ = m_13194_("beds");
   public static final Tag.Named<Item> f_13147_ = m_13194_("fences");
   public static final Tag.Named<Item> f_13148_ = m_13194_("tall_flowers");
   public static final Tag.Named<Item> f_13149_ = m_13194_("flowers");
   public static final Tag.Named<Item> f_13150_ = m_13194_("piglin_repellents");
   public static final Tag.Named<Item> f_13151_ = m_13194_("piglin_loved");
   public static final Tag.Named<Item> f_144309_ = m_13194_("ignored_by_piglin_babies");
   public static final Tag.Named<Item> f_144310_ = m_13194_("piglin_food");
   public static final Tag.Named<Item> f_144311_ = m_13194_("fox_food");
   public static final Tag.Named<Item> f_13152_ = m_13194_("gold_ores");
   public static final Tag.Named<Item> f_144312_ = m_13194_("iron_ores");
   public static final Tag.Named<Item> f_144313_ = m_13194_("diamond_ores");
   public static final Tag.Named<Item> f_144314_ = m_13194_("redstone_ores");
   public static final Tag.Named<Item> f_144315_ = m_13194_("lapis_ores");
   public static final Tag.Named<Item> f_144316_ = m_13194_("coal_ores");
   public static final Tag.Named<Item> f_144317_ = m_13194_("emerald_ores");
   public static final Tag.Named<Item> f_144318_ = m_13194_("copper_ores");
   public static final Tag.Named<Item> f_13153_ = m_13194_("non_flammable_wood");
   public static final Tag.Named<Item> f_13154_ = m_13194_("soul_fire_base_blocks");
   public static final Tag.Named<Item> f_144319_ = m_13194_("candles");
   public static final Tag.Named<Item> f_13155_ = m_13194_("boats");
   public static final Tag.Named<Item> f_13156_ = m_13194_("fishes");
   public static final Tag.Named<Item> f_13157_ = m_13194_("signs");
   public static final Tag.Named<Item> f_13158_ = m_13194_("music_discs");
   public static final Tag.Named<Item> f_13159_ = m_13194_("creeper_drop_music_discs");
   public static final Tag.Named<Item> f_13160_ = m_13194_("coals");
   public static final Tag.Named<Item> f_13161_ = m_13194_("arrows");
   public static final Tag.Named<Item> f_13162_ = m_13194_("lectern_books");
   public static final Tag.Named<Item> f_13164_ = m_13194_("beacon_payment_items");
   public static final Tag.Named<Item> f_13165_ = m_13194_("stone_tool_materials");
   public static final Tag.Named<Item> f_13166_ = m_13194_("stone_crafting_materials");
   public static final Tag.Named<Item> f_144320_ = m_13194_("freeze_immune_wearables");
   public static final Tag.Named<Item> f_144321_ = m_13194_("axolotl_tempt_items");
   public static final Tag.Named<Item> f_144322_ = m_13194_("occludes_vibration_signals");
   public static final Tag.Named<Item> f_144323_ = m_13194_("cluster_max_harvestables");

   private ItemTags() {
   }

   public static Tag.Named<Item> m_13194_(String p_13195_) {
      return f_13163_.m_13244_(p_13195_);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> createOptional(net.minecraft.resources.ResourceLocation name) {
       return createOptional(name, null);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> createOptional(net.minecraft.resources.ResourceLocation name, @javax.annotation.Nullable java.util.Set<java.util.function.Supplier<Item>> defaults) {
      return f_13163_.createOptional(name, defaults);
   }

   public static TagCollection<Item> m_13193_() {
      return f_13163_.m_13246_();
   }
}
