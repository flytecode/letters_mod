package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FishingLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   public static final LootItemCondition.Builder f_124385_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_48222_));
   public static final LootItemCondition.Builder f_124386_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_48223_));
   public static final LootItemCondition.Builder f_124387_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_48224_));
   public static final LootItemCondition.Builder f_124388_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_48197_));
   public static final LootItemCondition.Builder f_124389_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_48183_));
   public static final LootItemCondition.Builder f_124390_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_48184_));
   public static final LootItemCondition.Builder f_124391_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_48198_));

   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_124395_) {
      p_124395_.accept(BuiltInLootTables.f_78720_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootTableReference.m_79776_(BuiltInLootTables.f_78721_).m_79707_(10).m_79711_(-2)).m_79076_(LootTableReference.m_79776_(BuiltInLootTables.f_78722_).m_79707_(5).m_79711_(2).m_6509_(LootItemEntityPropertyCondition.m_81864_(LootContext.EntityTarget.THIS, EntityPredicate.Builder.m_36633_().m_36648_(FishingHookPredicate.m_39766_(true))))).m_79076_(LootTableReference.m_79776_(BuiltInLootTables.f_78723_).m_79707_(85).m_79711_(-1))));
      p_124395_.accept(BuiltInLootTables.f_78723_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_(LootItem.m_79579_(Items.f_42526_).m_79707_(60)).m_79076_(LootItem.m_79579_(Items.f_42527_).m_79707_(25)).m_79076_(LootItem.m_79579_(Items.f_42528_).m_79707_(2)).m_79076_(LootItem.m_79579_(Items.f_42529_).m_79707_(13))));
      p_124395_.accept(BuiltInLootTables.f_78721_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_(LootItem.m_79579_(Blocks.f_50196_).m_79707_(17)).m_79076_(LootItem.m_79579_(Items.f_42463_).m_79707_(10).m_5577_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(0.0F, 0.9F)))).m_79076_(LootItem.m_79579_(Items.f_42454_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42500_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42589_).m_79707_(10).m_5577_(SetNbtFunction.m_81187_(Util.m_137469_(new CompoundTag(), (p_124397_) -> {
         p_124397_.m_128359_("Potion", "minecraft:water");
      })))).m_79076_(LootItem.m_79579_(Items.f_42401_).m_79707_(5)).m_79076_(LootItem.m_79579_(Items.f_42523_).m_79707_(2).m_5577_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(0.0F, 0.9F)))).m_79076_(LootItem.m_79579_(Items.f_42399_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42398_).m_79707_(5)).m_79076_(LootItem.m_79579_(Items.f_42532_).m_79707_(1).m_5577_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(10.0F)))).m_79076_(LootItem.m_79579_(Blocks.f_50266_).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42583_).m_79707_(10)).m_79076_(LootItem.m_79579_(Blocks.f_50571_).m_6509_(f_124385_.m_7818_(f_124386_).m_7818_(f_124387_).m_7818_(f_124388_).m_7818_(f_124389_).m_7818_(f_124390_).m_7818_(f_124391_)).m_79707_(10))));
      p_124395_.accept(BuiltInLootTables.f_78722_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_(LootItem.m_79579_(Items.f_42656_)).m_79076_(LootItem.m_79579_(Items.f_42450_)).m_79076_(LootItem.m_79579_(Items.f_42411_).m_5577_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(0.0F, 0.25F))).m_5577_(EnchantWithLevelsFunction.m_165196_(ConstantValue.m_165692_(30.0F)).m_80499_())).m_79076_(LootItem.m_79579_(Items.f_42523_).m_5577_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(0.0F, 0.25F))).m_5577_(EnchantWithLevelsFunction.m_165196_(ConstantValue.m_165692_(30.0F)).m_80499_())).m_79076_(LootItem.m_79579_(Items.f_42517_).m_5577_(EnchantWithLevelsFunction.m_165196_(ConstantValue.m_165692_(30.0F)).m_80499_())).m_79076_(LootItem.m_79579_(Items.f_42715_))));
   }
}