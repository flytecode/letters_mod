package net.minecraft.world.level.storage.loot.entries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.Serializer;

public class LootPoolEntries {
   public static final LootPoolEntryType f_79619_ = m_79629_("empty", new EmptyLootItem.Serializer());
   public static final LootPoolEntryType f_79620_ = m_79629_("item", new LootItem.Serializer());
   public static final LootPoolEntryType f_79621_ = m_79629_("loot_table", new LootTableReference.Serializer());
   public static final LootPoolEntryType f_79622_ = m_79629_("dynamic", new DynamicLoot.Serializer());
   public static final LootPoolEntryType f_79623_ = m_79629_("tag", new TagEntry.Serializer());
   public static final LootPoolEntryType f_79624_ = m_79629_("alternatives", CompositeEntryBase.m_79435_(AlternativesEntry::new));
   public static final LootPoolEntryType f_79625_ = m_79629_("sequence", CompositeEntryBase.m_79435_(SequentialEntry::new));
   public static final LootPoolEntryType f_79626_ = m_79629_("group", CompositeEntryBase.m_79435_(EntryGroup::new));

   private static LootPoolEntryType m_79629_(String p_79630_, Serializer<? extends LootPoolEntryContainer> p_79631_) {
      return Registry.m_122965_(Registry.f_122875_, new ResourceLocation(p_79630_), new LootPoolEntryType(p_79631_));
   }

   public static Object m_79628_() {
      return GsonAdapterFactory.m_78801_(Registry.f_122875_, "entry", "type", LootPoolEntryContainer::m_6751_).m_78822_();
   }
}