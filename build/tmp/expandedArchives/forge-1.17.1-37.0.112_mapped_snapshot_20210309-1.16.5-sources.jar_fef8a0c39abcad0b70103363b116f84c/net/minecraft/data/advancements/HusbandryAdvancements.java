package net.minecraft.data.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.BeeNestDestroyedTrigger;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.FishingRodHookedTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnBlockTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.advancements.critereon.StartRidingTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;

public class HusbandryAdvancements implements Consumer<Consumer<Advancement>> {
   private static final EntityType<?>[] f_123991_ = new EntityType[]{EntityType.f_20457_, EntityType.f_20560_, EntityType.f_20503_, EntityType.f_20520_, EntityType.f_20557_, EntityType.f_20504_, EntityType.f_20510_, EntityType.f_20555_, EntityType.f_20499_, EntityType.f_20505_, EntityType.f_20517_, EntityType.f_20466_, EntityType.f_20553_, EntityType.f_20507_, EntityType.f_20452_, EntityType.f_20550_, EntityType.f_20456_, EntityType.f_20482_, EntityType.f_147035_, EntityType.f_147039_};
   private static final Item[] f_123992_ = new Item[]{Items.f_42526_, Items.f_42528_, Items.f_42529_, Items.f_42527_};
   private static final Item[] f_123993_ = new Item[]{Items.f_42458_, Items.f_42459_, Items.f_42456_, Items.f_42457_};
   private static final Item[] f_123994_ = new Item[]{Items.f_42410_, Items.f_42400_, Items.f_42406_, Items.f_42485_, Items.f_42486_, Items.f_42436_, Items.f_42437_, Items.f_42526_, Items.f_42527_, Items.f_42528_, Items.f_42529_, Items.f_42530_, Items.f_42531_, Items.f_42572_, Items.f_42575_, Items.f_42579_, Items.f_42580_, Items.f_42581_, Items.f_42582_, Items.f_42583_, Items.f_42591_, Items.f_42619_, Items.f_42620_, Items.f_42674_, Items.f_42675_, Items.f_42677_, Items.f_42687_, Items.f_42697_, Items.f_42698_, Items.f_42699_, Items.f_42658_, Items.f_42659_, Items.f_42730_, Items.f_42732_, Items.f_42734_, Items.f_42576_, Items.f_42718_, Items.f_42780_, Items.f_42787_, Items.f_151079_};
   private static final Item[] f_176032_ = new Item[]{Items.f_42423_, Items.f_42433_, Items.f_42428_, Items.f_42386_, Items.f_42391_, Items.f_42396_};

   public void accept(Consumer<Advancement> p_123998_) {
      Advancement advancement = Advancement.Builder.m_138353_().m_138371_(Blocks.f_50335_, new TranslatableComponent("advancements.husbandry.root.title"), new TranslatableComponent("advancements.husbandry.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/husbandry.png"), FrameType.TASK, false, false, false).m_138386_("consumed_item", ConsumeItemTrigger.TriggerInstance.m_23707_()).m_138389_(p_123998_, "husbandry/root");
      Advancement advancement1 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_42405_, new TranslatableComponent("advancements.husbandry.plant_seed.title"), new TranslatableComponent("advancements.husbandry.plant_seed.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("wheat", PlacedBlockTrigger.TriggerInstance.m_59505_(Blocks.f_50092_)).m_138386_("pumpkin_stem", PlacedBlockTrigger.TriggerInstance.m_59505_(Blocks.f_50189_)).m_138386_("melon_stem", PlacedBlockTrigger.TriggerInstance.m_59505_(Blocks.f_50190_)).m_138386_("beetroots", PlacedBlockTrigger.TriggerInstance.m_59505_(Blocks.f_50444_)).m_138386_("nether_wart", PlacedBlockTrigger.TriggerInstance.m_59505_(Blocks.f_50200_)).m_138389_(p_123998_, "husbandry/plant_seed");
      Advancement advancement2 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_42405_, new TranslatableComponent("advancements.husbandry.breed_an_animal.title"), new TranslatableComponent("advancements.husbandry.breed_an_animal.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("bred", BredAnimalsTrigger.TriggerInstance.m_18679_()).m_138389_(p_123998_, "husbandry/breed_an_animal");
      this.m_123999_(Advancement.Builder.m_138353_()).m_138398_(advancement1).m_138371_(Items.f_42410_, new TranslatableComponent("advancements.husbandry.balanced_diet.title"), new TranslatableComponent("advancements.husbandry.balanced_diet.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138389_(p_123998_, "husbandry/balanced_diet");
      Advancement.Builder.m_138353_().m_138398_(advancement1).m_138371_(Items.f_42397_, new TranslatableComponent("advancements.husbandry.netherite_hoe.title"), new TranslatableComponent("advancements.husbandry.netherite_hoe.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138386_("netherite_hoe", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42397_)).m_138389_(p_123998_, "husbandry/obtain_netherite_hoe");
      Advancement advancement3 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_42655_, new TranslatableComponent("advancements.husbandry.tame_an_animal.title"), new TranslatableComponent("advancements.husbandry.tame_an_animal.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("tamed_animal", TameAnimalTrigger.TriggerInstance.m_68854_()).m_138389_(p_123998_, "husbandry/tame_an_animal");
      this.m_124007_(Advancement.Builder.m_138353_()).m_138398_(advancement2).m_138371_(Items.f_42677_, new TranslatableComponent("advancements.husbandry.breed_all_animals.title"), new TranslatableComponent("advancements.husbandry.breed_all_animals.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138389_(p_123998_, "husbandry/bred_all_animals");
      Advancement advancement4 = this.m_124011_(Advancement.Builder.m_138353_()).m_138398_(advancement).m_138360_(RequirementsStrategy.f_15979_).m_138371_(Items.f_42523_, new TranslatableComponent("advancements.husbandry.fishy_business.title"), new TranslatableComponent("advancements.husbandry.fishy_business.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138389_(p_123998_, "husbandry/fishy_business");
      Advancement advancement5 = this.m_124009_(Advancement.Builder.m_138353_()).m_138398_(advancement4).m_138360_(RequirementsStrategy.f_15979_).m_138371_(Items.f_42456_, new TranslatableComponent("advancements.husbandry.tactical_fishing.title"), new TranslatableComponent("advancements.husbandry.tactical_fishing.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138389_(p_123998_, "husbandry/tactical_fishing");
      Advancement advancement6 = Advancement.Builder.m_138353_().m_138398_(advancement5).m_138360_(RequirementsStrategy.f_15979_).m_138386_(Registry.f_122827_.m_7981_(Items.f_151057_).m_135815_(), FilledBucketTrigger.TriggerInstance.m_38793_(ItemPredicate.Builder.m_45068_().m_151445_(Items.f_151057_).m_45077_())).m_138371_(Items.f_151057_, new TranslatableComponent("advancements.husbandry.axolotl_in_a_bucket.title"), new TranslatableComponent("advancements.husbandry.axolotl_in_a_bucket.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138389_(p_123998_, "husbandry/axolotl_in_a_bucket");
      Advancement.Builder.m_138353_().m_138398_(advancement6).m_138386_("kill_axolotl_target", EffectsChangedTrigger.TriggerInstance.m_149277_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_147039_).m_36662_())).m_138371_(Items.f_42459_, new TranslatableComponent("advancements.husbandry.kill_axolotl_target.title"), new TranslatableComponent("advancements.husbandry.kill_axolotl_target.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138389_(p_123998_, "husbandry/kill_axolotl_target");
      this.m_124013_(Advancement.Builder.m_138353_()).m_138398_(advancement3).m_138371_(Items.f_42526_, new TranslatableComponent("advancements.husbandry.complete_catalogue.title"), new TranslatableComponent("advancements.husbandry.complete_catalogue.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(50)).m_138389_(p_123998_, "husbandry/complete_catalogue");
      Advancement advancement7 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138386_("safely_harvest_honey", ItemUsedOnBlockTrigger.TriggerInstance.m_45507_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_17925_(BlockTags.f_13072_).m_17931_()).m_52654_(true), ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42590_))).m_138371_(Items.f_42787_, new TranslatableComponent("advancements.husbandry.safely_harvest_honey.title"), new TranslatableComponent("advancements.husbandry.safely_harvest_honey.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138389_(p_123998_, "husbandry/safely_harvest_honey");
      Advancement advancement8 = Advancement.Builder.m_138353_().m_138398_(advancement7).m_138371_(Items.f_42784_, new TranslatableComponent("advancements.husbandry.wax_on.title"), new TranslatableComponent("advancements.husbandry.wax_on.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("wax_on", ItemUsedOnBlockTrigger.TriggerInstance.m_45507_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_146722_(HoneycombItem.f_150863_.get().keySet()).m_17931_()), ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42784_))).m_138389_(p_123998_, "husbandry/wax_on");
      Advancement.Builder.m_138353_().m_138398_(advancement8).m_138371_(Items.f_42428_, new TranslatableComponent("advancements.husbandry.wax_off.title"), new TranslatableComponent("advancements.husbandry.wax_off.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("wax_off", ItemUsedOnBlockTrigger.TriggerInstance.m_45507_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_146722_(HoneycombItem.f_150864_.get().keySet()).m_17931_()), ItemPredicate.Builder.m_45068_().m_151445_(f_176032_))).m_138389_(p_123998_, "husbandry/wax_off");
      Advancement.Builder.m_138353_().m_138398_(advancement).m_138386_("silk_touch_nest", BeeNestDestroyedTrigger.TriggerInstance.m_17512_(Blocks.f_50717_, ItemPredicate.Builder.m_45068_().m_45071_(new EnchantmentPredicate(Enchantments.f_44985_, MinMaxBounds.Ints.m_55386_(1))), MinMaxBounds.Ints.m_55371_(3))).m_138371_(Blocks.f_50717_, new TranslatableComponent("advancements.husbandry.silk_touch_nest.title"), new TranslatableComponent("advancements.husbandry.silk_touch_nest.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138389_(p_123998_, "husbandry/silk_touch_nest");
      Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_42453_, new TranslatableComponent("advancements.husbandry.ride_a_boat_with_a_goat.title"), new TranslatableComponent("advancements.husbandry.ride_a_boat_with_a_goat.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("ride_a_boat_with_a_goat", StartRidingTrigger.TriggerInstance.m_160401_(EntityPredicate.Builder.m_36633_().m_36644_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20552_).m_150328_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_147035_).m_36662_()).m_36662_()))).m_138389_(p_123998_, "husbandry/ride_a_boat_with_a_goat");
      Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Items.f_151056_, new TranslatableComponent("advancements.husbandry.make_a_sign_glow.title"), new TranslatableComponent("advancements.husbandry.make_a_sign_glow.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("make_a_sign_glow", ItemUsedOnBlockTrigger.TriggerInstance.m_45507_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_17925_(BlockTags.f_13068_).m_17931_()), ItemPredicate.Builder.m_45068_().m_151445_(Items.f_151056_))).m_138389_(p_123998_, "husbandry/make_a_sign_glow");
   }

   private Advancement.Builder m_123999_(Advancement.Builder p_124000_) {
      for(Item item : f_123994_) {
         p_124000_.m_138386_(Registry.f_122827_.m_7981_(item).m_135815_(), ConsumeItemTrigger.TriggerInstance.m_23703_(item));
      }

      return p_124000_;
   }

   private Advancement.Builder m_124007_(Advancement.Builder p_124008_) {
      for(EntityType<?> entitytype : f_123991_) {
         p_124008_.m_138386_(EntityType.m_20613_(entitytype).toString(), BredAnimalsTrigger.TriggerInstance.m_18667_(EntityPredicate.Builder.m_36633_().m_36636_(entitytype)));
      }

      p_124008_.m_138386_(EntityType.m_20613_(EntityType.f_20490_).toString(), BredAnimalsTrigger.TriggerInstance.m_18669_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20490_).m_36662_(), EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20490_).m_36662_(), EntityPredicate.f_36550_));
      return p_124008_;
   }

   private Advancement.Builder m_124009_(Advancement.Builder p_124010_) {
      for(Item item : f_123993_) {
         p_124010_.m_138386_(Registry.f_122827_.m_7981_(item).m_135815_(), FilledBucketTrigger.TriggerInstance.m_38793_(ItemPredicate.Builder.m_45068_().m_151445_(item).m_45077_()));
      }

      return p_124010_;
   }

   private Advancement.Builder m_124011_(Advancement.Builder p_124012_) {
      for(Item item : f_123992_) {
         p_124012_.m_138386_(Registry.f_122827_.m_7981_(item).m_135815_(), FishingRodHookedTrigger.TriggerInstance.m_40447_(ItemPredicate.f_45028_, EntityPredicate.f_36550_, ItemPredicate.Builder.m_45068_().m_151445_(item).m_45077_()));
      }

      return p_124012_;
   }

   private Advancement.Builder m_124013_(Advancement.Builder p_124014_) {
      Cat.f_28102_.forEach((p_124003_, p_124004_) -> {
         p_124014_.m_138386_(p_124004_.m_135815_(), TameAnimalTrigger.TriggerInstance.m_68848_(EntityPredicate.Builder.m_36633_().m_36660_(p_124004_).m_36662_()));
      });
      return p_124014_;
   }
}