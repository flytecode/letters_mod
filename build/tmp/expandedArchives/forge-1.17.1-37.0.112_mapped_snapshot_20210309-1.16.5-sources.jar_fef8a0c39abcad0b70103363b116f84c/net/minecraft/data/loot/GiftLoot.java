package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class GiftLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_124402_) {
      p_124402_.accept(BuiltInLootTables.f_78724_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42649_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42648_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42581_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42402_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42583_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42401_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42714_).m_79707_(2))));
      p_124402_.accept(BuiltInLootTables.f_78725_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42464_)).m_79076_(LootItem.m_79579_(Items.f_42465_)).m_79076_(LootItem.m_79579_(Items.f_42466_)).m_79076_(LootItem.m_79579_(Items.f_42467_))));
      p_124402_.accept(BuiltInLootTables.f_78726_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42698_)).m_79076_(LootItem.m_79579_(Items.f_42582_)).m_79076_(LootItem.m_79579_(Items.f_42486_)).m_79076_(LootItem.m_79579_(Items.f_42580_)).m_79076_(LootItem.m_79579_(Items.f_42659_))));
      p_124402_.accept(BuiltInLootTables.f_78727_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42676_)).m_79076_(LootItem.m_79579_(Items.f_42516_))));
      p_124402_.accept(BuiltInLootTables.f_78728_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42451_)).m_79076_(LootItem.m_79579_(Items.f_42534_))));
      p_124402_.accept(BuiltInLootTables.f_78729_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42406_)).m_79076_(LootItem.m_79579_(Items.f_42687_)).m_79076_(LootItem.m_79579_(Items.f_42572_))));
      p_124402_.accept(BuiltInLootTables.f_78730_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42526_)).m_79076_(LootItem.m_79579_(Items.f_42527_))));
      p_124402_.accept(BuiltInLootTables.f_78731_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42412_).m_79707_(26)).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124430_) -> {
         p_124430_.m_128359_("Potion", "minecraft:swiftness");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124428_) -> {
         p_124428_.m_128359_("Potion", "minecraft:slowness");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124426_) -> {
         p_124426_.m_128359_("Potion", "minecraft:strength");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124424_) -> {
         p_124424_.m_128359_("Potion", "minecraft:healing");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124422_) -> {
         p_124422_.m_128359_("Potion", "minecraft:harming");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124420_) -> {
         p_124420_.m_128359_("Potion", "minecraft:leaping");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124418_) -> {
         p_124418_.m_128359_("Potion", "minecraft:regeneration");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124416_) -> {
         p_124416_.m_128359_("Potion", "minecraft:fire_resistance");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124414_) -> {
         p_124414_.m_128359_("Potion", "minecraft:water_breathing");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124412_) -> {
         p_124412_.m_128359_("Potion", "minecraft:invisibility");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124410_) -> {
         p_124410_.m_128359_("Potion", "minecraft:night_vision");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124408_) -> {
         p_124408_.m_128359_("Potion", "minecraft:weakness");
      })))).m_79076_(LootItem.m_79579_(Items.f_42738_).m_5577_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124404_) -> {
         p_124404_.m_128359_("Potion", "minecraft:poison");
      }))))));
      p_124402_.accept(BuiltInLootTables.f_78732_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42454_))));
      p_124402_.accept(BuiltInLootTables.f_78733_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42517_))));
      p_124402_.accept(BuiltInLootTables.f_78734_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_41983_))));
      p_124402_.accept(BuiltInLootTables.f_78735_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_41870_)).m_79076_(LootItem.m_79579_(Items.f_41871_)).m_79076_(LootItem.m_79579_(Items.f_41872_)).m_79076_(LootItem.m_79579_(Items.f_41873_)).m_79076_(LootItem.m_79579_(Items.f_41874_)).m_79076_(LootItem.m_79579_(Items.f_41875_)).m_79076_(LootItem.m_79579_(Items.f_41876_)).m_79076_(LootItem.m_79579_(Items.f_41877_)).m_79076_(LootItem.m_79579_(Items.f_41878_)).m_79076_(LootItem.m_79579_(Items.f_41932_)).m_79076_(LootItem.m_79579_(Items.f_41933_)).m_79076_(LootItem.m_79579_(Items.f_41934_)).m_79076_(LootItem.m_79579_(Items.f_41935_)).m_79076_(LootItem.m_79579_(Items.f_41936_)).m_79076_(LootItem.m_79579_(Items.f_41937_)).m_79076_(LootItem.m_79579_(Items.f_41938_))));
      p_124402_.accept(BuiltInLootTables.f_78736_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42427_)).m_79076_(LootItem.m_79579_(Items.f_42428_)).m_79076_(LootItem.m_79579_(Items.f_42429_)).m_79076_(LootItem.m_79579_(Items.f_42426_))));
      p_124402_.accept(BuiltInLootTables.f_78737_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42428_)).m_79076_(LootItem.m_79579_(Items.f_42433_)).m_79076_(LootItem.m_79579_(Items.f_42386_))));
   }
}