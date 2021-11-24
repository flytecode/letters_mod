package net.minecraft.data.advancements;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ChanneledLightningTrigger;
import net.minecraft.advancements.critereon.DamagePredicate;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledByCrossbowTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LighthingBoltPredicate;
import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.LocationTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.advancements.critereon.PlayerPredicate;
import net.minecraft.advancements.critereon.ShotCrossbowTrigger;
import net.minecraft.advancements.critereon.SlideDownBlockTrigger;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.advancements.critereon.TargetBlockTrigger;
import net.minecraft.advancements.critereon.TradeTrigger;
import net.minecraft.advancements.critereon.UsedTotemTrigger;
import net.minecraft.advancements.critereon.UsingItemTrigger;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;

public class AdventureAdvancements implements Consumer<Consumer<Advancement>> {
   private static final List<ResourceKey<Biome>> f_123978_ = ImmutableList.of(Biomes.f_48150_, Biomes.f_48208_, Biomes.f_48207_, Biomes.f_48203_, Biomes.f_48219_, Biomes.f_48155_, Biomes.f_48152_, Biomes.f_48159_, Biomes.f_48205_, Biomes.f_48226_, Biomes.f_48213_, Biomes.f_48220_, Biomes.f_48214_, Biomes.f_48160_, Biomes.f_48157_, Biomes.f_48202_, Biomes.f_48212_, Biomes.f_48154_, Biomes.f_48148_, Biomes.f_48223_, Biomes.f_48224_, Biomes.f_48216_, Biomes.f_48204_, Biomes.f_48218_, Biomes.f_48222_, Biomes.f_48217_, Biomes.f_48158_, Biomes.f_48153_, Biomes.f_48161_, Biomes.f_48151_, Biomes.f_48206_, Biomes.f_48149_, Biomes.f_48215_, Biomes.f_48156_, Biomes.f_48166_, Biomes.f_48167_, Biomes.f_48168_, Biomes.f_48170_, Biomes.f_48171_, Biomes.f_48172_, Biomes.f_48197_, Biomes.f_48198_);
   private static final EntityType<?>[] f_123979_ = new EntityType[]{EntityType.f_20551_, EntityType.f_20554_, EntityType.f_20558_, EntityType.f_20562_, EntityType.f_20563_, EntityType.f_20565_, EntityType.f_20566_, EntityType.f_20567_, EntityType.f_20568_, EntityType.f_20453_, EntityType.f_20455_, EntityType.f_20456_, EntityType.f_20458_, EntityType.f_20468_, EntityType.f_20509_, EntityType.f_20511_, EntityType.f_20512_, EntityType.f_20513_, EntityType.f_20518_, EntityType.f_20521_, EntityType.f_20523_, EntityType.f_20524_, EntityType.f_20526_, EntityType.f_20479_, EntityType.f_20481_, EntityType.f_20491_, EntityType.f_20493_, EntityType.f_20495_, EntityType.f_20497_, EntityType.f_20496_, EntityType.f_20500_, EntityType.f_20530_, EntityType.f_20501_, EntityType.f_20531_};

   private static LightningStrikeTrigger.TriggerInstance m_176029_(MinMaxBounds.Ints p_176030_, EntityPredicate p_176031_) {
      return LightningStrikeTrigger.TriggerInstance.m_153413_(EntityPredicate.Builder.m_36633_().m_36638_(DistancePredicate.m_148840_(MinMaxBounds.Doubles.m_154808_(30.0D))).m_150326_(LighthingBoltPredicate.m_153250_(p_176030_)).m_36662_(), p_176031_);
   }

   private static UsingItemTrigger.TriggerInstance m_176026_(EntityType<?> p_176027_, Item p_176028_) {
      return UsingItemTrigger.TriggerInstance.m_163883_(EntityPredicate.Builder.m_36633_().m_36656_(PlayerPredicate.Builder.m_156767_().m_156771_(EntityPredicate.Builder.m_36633_().m_36636_(p_176027_).m_36662_()).m_62313_()), ItemPredicate.Builder.m_45068_().m_151445_(p_176028_));
   }

   public void accept(Consumer<Advancement> p_123983_) {
      Advancement advancement = Advancement.Builder.m_138353_().m_138371_(Items.f_42676_, new TranslatableComponent("advancements.adventure.root.title"), new TranslatableComponent("advancements.adventure.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"), FrameType.TASK, false, false, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("killed_something", KilledTrigger.TriggerInstance.m_48141_()).m_138386_("killed_by_something", KilledTrigger.TriggerInstance.m_48142_()).m_138389_(p_123983_, "adventure/root");
      Advancement advancement1 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Blocks.f_50028_, new TranslatableComponent("advancements.adventure.sleep_in_bed.title"), new TranslatableComponent("advancements.adventure.sleep_in_bed.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("slept_in_bed", LocationTrigger.TriggerInstance.m_53674_()).m_138389_(p_123983_, "adventure/sleep_in_bed");
      m_123986_(Advancement.Builder.m_138353_(), f_123978_).m_138398_(advancement1).m_138371_(Items.f_42475_, new TranslatableComponent("advancements.adventure.adventuring_time.title"), new TranslatableComponent("advancements.adventure.adventuring_time.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(500)).m_138389_(p_123983_, "adventure/adventuring_time");
      Advancement advancement2 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_42616_, new TranslatableComponent("advancements.adventure.trade.title"), new TranslatableComponent("advancements.adventure.trade.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("traded", TradeTrigger.TriggerInstance.m_70987_()).m_138389_(p_123983_, "adventure/trade");
      Advancement advancement3 = this.m_123984_(Advancement.Builder.m_138353_()).m_138398_(advancement).m_138371_(Items.f_42383_, new TranslatableComponent("advancements.adventure.kill_a_mob.title"), new TranslatableComponent("advancements.adventure.kill_a_mob.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138360_(RequirementsStrategy.f_15979_).m_138389_(p_123983_, "adventure/kill_a_mob");
      this.m_123984_(Advancement.Builder.m_138353_()).m_138398_(advancement3).m_138371_(Items.f_42388_, new TranslatableComponent("advancements.adventure.kill_all_mobs.title"), new TranslatableComponent("advancements.adventure.kill_all_mobs.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138389_(p_123983_, "adventure/kill_all_mobs");
      Advancement advancement4 = Advancement.Builder.m_138353_().m_138398_(advancement3).m_138371_(Items.f_42411_, new TranslatableComponent("advancements.adventure.shoot_arrow.title"), new TranslatableComponent("advancements.adventure.shoot_arrow.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("shot_arrow", PlayerHurtEntityTrigger.TriggerInstance.m_60149_(DamagePredicate.Builder.m_24931_().m_24932_(DamageSourcePredicate.Builder.m_25471_().m_25474_(true).m_25472_(EntityPredicate.Builder.m_36633_().m_36634_(EntityTypeTags.f_13123_))))).m_138389_(p_123983_, "adventure/shoot_arrow");
      Advancement advancement5 = Advancement.Builder.m_138353_().m_138398_(advancement3).m_138371_(Items.f_42713_, new TranslatableComponent("advancements.adventure.throw_trident.title"), new TranslatableComponent("advancements.adventure.throw_trident.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("shot_trident", PlayerHurtEntityTrigger.TriggerInstance.m_60149_(DamagePredicate.Builder.m_24931_().m_24932_(DamageSourcePredicate.Builder.m_25471_().m_25474_(true).m_25472_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20487_))))).m_138389_(p_123983_, "adventure/throw_trident");
      Advancement.Builder.m_138353_().m_138398_(advancement5).m_138371_(Items.f_42713_, new TranslatableComponent("advancements.adventure.very_very_frightening.title"), new TranslatableComponent("advancements.adventure.very_very_frightening.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("struck_villager", ChanneledLightningTrigger.TriggerInstance.m_21746_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20492_).m_36662_())).m_138389_(p_123983_, "adventure/very_very_frightening");
      Advancement.Builder.m_138353_().m_138398_(advancement2).m_138371_(Blocks.f_50143_, new TranslatableComponent("advancements.adventure.summon_iron_golem.title"), new TranslatableComponent("advancements.adventure.summon_iron_golem.description"), (ResourceLocation)null, FrameType.GOAL, true, true, false).m_138386_("summoned_golem", SummonedEntityTrigger.TriggerInstance.m_68275_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20460_))).m_138389_(p_123983_, "adventure/summon_iron_golem");
      Advancement.Builder.m_138353_().m_138398_(advancement4).m_138371_(Items.f_42412_, new TranslatableComponent("advancements.adventure.sniper_duel.title"), new TranslatableComponent("advancements.adventure.sniper_duel.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(50)).m_138386_("killed_skeleton", KilledTrigger.TriggerInstance.m_48136_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20524_).m_36638_(DistancePredicate.m_148836_(MinMaxBounds.Doubles.m_154804_(50.0D))), DamageSourcePredicate.Builder.m_25471_().m_25474_(true))).m_138389_(p_123983_, "adventure/sniper_duel");
      Advancement.Builder.m_138353_().m_138398_(advancement3).m_138371_(Items.f_42747_, new TranslatableComponent("advancements.adventure.totem_of_undying.title"), new TranslatableComponent("advancements.adventure.totem_of_undying.description"), (ResourceLocation)null, FrameType.GOAL, true, true, false).m_138386_("used_totem", UsedTotemTrigger.TriggerInstance.m_74452_(Items.f_42747_)).m_138389_(p_123983_, "adventure/totem_of_undying");
      Advancement advancement6 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_42717_, new TranslatableComponent("advancements.adventure.ol_betsy.title"), new TranslatableComponent("advancements.adventure.ol_betsy.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("shot_crossbow", ShotCrossbowTrigger.TriggerInstance.m_65483_(Items.f_42717_)).m_138389_(p_123983_, "adventure/ol_betsy");
      Advancement.Builder.m_138353_().m_138398_(advancement6).m_138371_(Items.f_42717_, new TranslatableComponent("advancements.adventure.whos_the_pillager_now.title"), new TranslatableComponent("advancements.adventure.whos_the_pillager_now.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("kill_pillager", KilledByCrossbowTrigger.TriggerInstance.m_46900_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20513_))).m_138389_(p_123983_, "adventure/whos_the_pillager_now");
      Advancement.Builder.m_138353_().m_138398_(advancement6).m_138371_(Items.f_42717_, new TranslatableComponent("advancements.adventure.two_birds_one_arrow.title"), new TranslatableComponent("advancements.adventure.two_birds_one_arrow.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(65)).m_138386_("two_birds", KilledByCrossbowTrigger.TriggerInstance.m_46900_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20509_), EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20509_))).m_138389_(p_123983_, "adventure/two_birds_one_arrow");
      Advancement.Builder.m_138353_().m_138398_(advancement6).m_138371_(Items.f_42717_, new TranslatableComponent("advancements.adventure.arbalistic.title"), new TranslatableComponent("advancements.adventure.arbalistic.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).m_138354_(AdvancementRewards.Builder.m_10005_(85)).m_138386_("arbalistic", KilledByCrossbowTrigger.TriggerInstance.m_46893_(MinMaxBounds.Ints.m_55371_(5))).m_138389_(p_123983_, "adventure/arbalistic");
      Advancement advancement7 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138362_(Raid.m_37779_(), new TranslatableComponent("advancements.adventure.voluntary_exile.title"), new TranslatableComponent("advancements.adventure.voluntary_exile.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).m_138386_("voluntary_exile", KilledTrigger.TriggerInstance.m_48134_(EntityPredicate.Builder.m_36633_().m_36634_(EntityTypeTags.f_13121_).m_36640_(EntityEquipmentPredicate.f_32177_))).m_138389_(p_123983_, "adventure/voluntary_exile");
      Advancement.Builder.m_138353_().m_138398_(advancement7).m_138362_(Raid.m_37779_(), new TranslatableComponent("advancements.adventure.hero_of_the_village.title"), new TranslatableComponent("advancements.adventure.hero_of_the_village.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138386_("hero_of_the_village", LocationTrigger.TriggerInstance.m_53675_()).m_138389_(p_123983_, "adventure/hero_of_the_village");
      Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Blocks.f_50719_.m_5456_(), new TranslatableComponent("advancements.adventure.honey_block_slide.title"), new TranslatableComponent("advancements.adventure.honey_block_slide.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("honey_block_slide", SlideDownBlockTrigger.TriggerInstance.m_67006_(Blocks.f_50719_)).m_138389_(p_123983_, "adventure/honey_block_slide");
      Advancement.Builder.m_138353_().m_138398_(advancement4).m_138371_(Blocks.f_50716_.m_5456_(), new TranslatableComponent("advancements.adventure.bullseye.title"), new TranslatableComponent("advancements.adventure.bullseye.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(50)).m_138386_("bullseye", TargetBlockTrigger.TriggerInstance.m_70236_(MinMaxBounds.Ints.m_55371_(15), EntityPredicate.Composite.m_36673_(EntityPredicate.Builder.m_36633_().m_36638_(DistancePredicate.m_148836_(MinMaxBounds.Doubles.m_154804_(30.0D))).m_36662_()))).m_138389_(p_123983_, "adventure/bullseye");
      Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_42463_, new TranslatableComponent("advancements.adventure.walk_on_powder_snow_with_leather_boots.title"), new TranslatableComponent("advancements.adventure.walk_on_powder_snow_with_leather_boots.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("walk_on_powder_snow_with_leather_boots", LocationTrigger.TriggerInstance.m_154322_(Blocks.f_152499_, Items.f_42463_)).m_138389_(p_123983_, "adventure/walk_on_powder_snow_with_leather_boots");
      Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_151041_, new TranslatableComponent("advancements.adventure.lightning_rod_with_villager_no_fire.title"), new TranslatableComponent("advancements.adventure.lightning_rod_with_villager_no_fire.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("lightning_rod_with_villager_no_fire", m_176029_(MinMaxBounds.Ints.m_55371_(0), EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20492_).m_36662_())).m_138389_(p_123983_, "adventure/lightning_rod_with_villager_no_fire");
      Advancement advancement8 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_151059_, new TranslatableComponent("advancements.adventure.spyglass_at_parrot.title"), new TranslatableComponent("advancements.adventure.spyglass_at_parrot.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("spyglass_at_parrot", m_176026_(EntityType.f_20508_, Items.f_151059_)).m_138389_(p_123983_, "adventure/spyglass_at_parrot");
      Advancement advancement9 = Advancement.Builder.m_138353_().m_138398_(advancement8).m_138371_(Items.f_151059_, new TranslatableComponent("advancements.adventure.spyglass_at_ghast.title"), new TranslatableComponent("advancements.adventure.spyglass_at_ghast.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("spyglass_at_ghast", m_176026_(EntityType.f_20453_, Items.f_151059_)).m_138389_(p_123983_, "adventure/spyglass_at_ghast");
      Advancement.Builder.m_138353_().m_138398_(advancement9).m_138371_(Items.f_151059_, new TranslatableComponent("advancements.adventure.spyglass_at_dragon.title"), new TranslatableComponent("advancements.adventure.spyglass_at_dragon.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("spyglass_at_dragon", m_176026_(EntityType.f_20565_, Items.f_151059_)).m_138389_(p_123983_, "adventure/spyglass_at_dragon");
   }

   private Advancement.Builder m_123984_(Advancement.Builder p_123985_) {
      for(EntityType<?> entitytype : f_123979_) {
         p_123985_.m_138386_(Registry.f_122826_.m_7981_(entitytype).toString(), KilledTrigger.TriggerInstance.m_48134_(EntityPredicate.Builder.m_36633_().m_36636_(entitytype)));
      }

      return p_123985_;
   }

   protected static Advancement.Builder m_123986_(Advancement.Builder p_123987_, List<ResourceKey<Biome>> p_123988_) {
      for(ResourceKey<Biome> resourcekey : p_123988_) {
         p_123987_.m_138386_(resourcekey.m_135782_().toString(), LocationTrigger.TriggerInstance.m_53670_(LocationPredicate.m_52634_(resourcekey)));
      }

      return p_123987_;
   }
}