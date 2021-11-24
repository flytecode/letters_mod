package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class PiglinBarterLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_124468_) {
      p_124468_.accept(BuiltInLootTables.f_78738_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42517_).m_79707_(5).m_5577_((new EnchantRandomlyFunction.Builder()).m_80444_(Enchantments.f_44976_))).m_79076_(LootItem.m_79579_(Items.f_42471_).m_79707_(8).m_5577_((new EnchantRandomlyFunction.Builder()).m_80444_(Enchantments.f_44976_))).m_79076_(LootItem.m_79579_(Items.f_42589_).m_79707_(8).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124476_) -> {
         p_124476_.m_128359_("Potion", "minecraft:fire_resistance");
      })))).m_79076_(LootItem.m_79579_(Items.f_42736_).m_79707_(8).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124474_) -> {
         p_124474_.m_128359_("Potion", "minecraft:fire_resistance");
      })))).m_79076_(LootItem.m_79579_(Items.f_42589_).m_79707_(10).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124470_) -> {
         p_124470_.m_128359_("Potion", "minecraft:water");
      })))).m_79076_(LootItem.m_79579_(Items.f_42749_).m_79707_(10).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(10.0F, 36.0F)))).m_79076_(LootItem.m_79579_(Items.f_42584_).m_79707_(10).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 4.0F)))).m_79076_(LootItem.m_79579_(Items.f_42401_).m_79707_(20).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 9.0F)))).m_79076_(LootItem.m_79579_(Items.f_42692_).m_79707_(20).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 12.0F)))).m_79076_(LootItem.m_79579_(Items.f_41999_).m_79707_(40)).m_79076_(LootItem.m_79579_(Items.f_42754_).m_79707_(40).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42613_).m_79707_(40)).m_79076_(LootItem.m_79579_(Items.f_42454_).m_79707_(40).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 4.0F)))).m_79076_(LootItem.m_79579_(Items.f_42049_).m_79707_(40).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 8.0F)))).m_79076_(LootItem.m_79579_(Items.f_42691_).m_79707_(40).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 8.0F)))).m_79076_(LootItem.m_79579_(Items.f_42737_).m_79707_(40).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(6.0F, 12.0F)))).m_79076_(LootItem.m_79579_(Items.f_41832_).m_79707_(40).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0F, 16.0F)))).m_79076_(LootItem.m_79579_(Items.f_42755_).m_79707_(40).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0F, 16.0F))))));
   }
}