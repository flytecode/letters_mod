package net.minecraft.advancements;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.BeeNestDestroyedTrigger;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.advancements.critereon.BrewedPotionTrigger;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.ChanneledLightningTrigger;
import net.minecraft.advancements.critereon.ConstructBeaconTrigger;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.CuredZombieVillagerTrigger;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.FishingRodHookedTrigger;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
import net.minecraft.advancements.critereon.ItemPickedUpByEntityTrigger;
import net.minecraft.advancements.critereon.ItemUsedOnBlockTrigger;
import net.minecraft.advancements.critereon.KilledByCrossbowTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LevitationTrigger;
import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.advancements.critereon.LocationTrigger;
import net.minecraft.advancements.critereon.LootTableTrigger;
import net.minecraft.advancements.critereon.NetherTravelTrigger;
import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.advancements.critereon.ShotCrossbowTrigger;
import net.minecraft.advancements.critereon.SlideDownBlockTrigger;
import net.minecraft.advancements.critereon.StartRidingTrigger;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.advancements.critereon.TargetBlockTrigger;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.advancements.critereon.TradeTrigger;
import net.minecraft.advancements.critereon.UsedEnderEyeTrigger;
import net.minecraft.advancements.critereon.UsedTotemTrigger;
import net.minecraft.advancements.critereon.UsingItemTrigger;
import net.minecraft.resources.ResourceLocation;

public class CriteriaTriggers {
   private static final Map<ResourceLocation, CriterionTrigger<?>> f_10566_ = Maps.newHashMap();
   public static final ImpossibleTrigger f_10567_ = m_10595_(new ImpossibleTrigger());
   public static final KilledTrigger f_10568_ = m_10595_(new KilledTrigger(new ResourceLocation("player_killed_entity")));
   public static final KilledTrigger f_10569_ = m_10595_(new KilledTrigger(new ResourceLocation("entity_killed_player")));
   public static final EnterBlockTrigger f_10570_ = m_10595_(new EnterBlockTrigger());
   public static final InventoryChangeTrigger f_10571_ = m_10595_(new InventoryChangeTrigger());
   public static final RecipeUnlockedTrigger f_10572_ = m_10595_(new RecipeUnlockedTrigger());
   public static final PlayerHurtEntityTrigger f_10573_ = m_10595_(new PlayerHurtEntityTrigger());
   public static final EntityHurtPlayerTrigger f_10574_ = m_10595_(new EntityHurtPlayerTrigger());
   public static final EnchantedItemTrigger f_10575_ = m_10595_(new EnchantedItemTrigger());
   public static final FilledBucketTrigger f_10576_ = m_10595_(new FilledBucketTrigger());
   public static final BrewedPotionTrigger f_10577_ = m_10595_(new BrewedPotionTrigger());
   public static final ConstructBeaconTrigger f_10578_ = m_10595_(new ConstructBeaconTrigger());
   public static final UsedEnderEyeTrigger f_10579_ = m_10595_(new UsedEnderEyeTrigger());
   public static final SummonedEntityTrigger f_10580_ = m_10595_(new SummonedEntityTrigger());
   public static final BredAnimalsTrigger f_10581_ = m_10595_(new BredAnimalsTrigger());
   public static final LocationTrigger f_10582_ = m_10595_(new LocationTrigger(new ResourceLocation("location")));
   public static final LocationTrigger f_10583_ = m_10595_(new LocationTrigger(new ResourceLocation("slept_in_bed")));
   public static final CuredZombieVillagerTrigger f_10584_ = m_10595_(new CuredZombieVillagerTrigger());
   public static final TradeTrigger f_10585_ = m_10595_(new TradeTrigger());
   public static final ItemDurabilityTrigger f_10586_ = m_10595_(new ItemDurabilityTrigger());
   public static final LevitationTrigger f_10587_ = m_10595_(new LevitationTrigger());
   public static final ChangeDimensionTrigger f_10588_ = m_10595_(new ChangeDimensionTrigger());
   public static final TickTrigger f_10589_ = m_10595_(new TickTrigger());
   public static final TameAnimalTrigger f_10590_ = m_10595_(new TameAnimalTrigger());
   public static final PlacedBlockTrigger f_10591_ = m_10595_(new PlacedBlockTrigger());
   public static final ConsumeItemTrigger f_10592_ = m_10595_(new ConsumeItemTrigger());
   public static final EffectsChangedTrigger f_10550_ = m_10595_(new EffectsChangedTrigger());
   public static final UsedTotemTrigger f_10551_ = m_10595_(new UsedTotemTrigger());
   public static final NetherTravelTrigger f_10552_ = m_10595_(new NetherTravelTrigger());
   public static final FishingRodHookedTrigger f_10553_ = m_10595_(new FishingRodHookedTrigger());
   public static final ChanneledLightningTrigger f_10554_ = m_10595_(new ChanneledLightningTrigger());
   public static final ShotCrossbowTrigger f_10555_ = m_10595_(new ShotCrossbowTrigger());
   public static final KilledByCrossbowTrigger f_10556_ = m_10595_(new KilledByCrossbowTrigger());
   public static final LocationTrigger f_10557_ = m_10595_(new LocationTrigger(new ResourceLocation("hero_of_the_village")));
   public static final LocationTrigger f_10558_ = m_10595_(new LocationTrigger(new ResourceLocation("voluntary_exile")));
   public static final SlideDownBlockTrigger f_10559_ = m_10595_(new SlideDownBlockTrigger());
   public static final BeeNestDestroyedTrigger f_10560_ = m_10595_(new BeeNestDestroyedTrigger());
   public static final TargetBlockTrigger f_10561_ = m_10595_(new TargetBlockTrigger());
   public static final ItemUsedOnBlockTrigger f_10562_ = m_10595_(new ItemUsedOnBlockTrigger());
   public static final LootTableTrigger f_10563_ = m_10595_(new LootTableTrigger());
   public static final ItemPickedUpByEntityTrigger f_10564_ = m_10595_(new ItemPickedUpByEntityTrigger());
   public static final PlayerInteractTrigger f_10565_ = m_10595_(new PlayerInteractTrigger());
   public static final StartRidingTrigger f_145088_ = m_10595_(new StartRidingTrigger());
   public static final LightningStrikeTrigger f_145089_ = m_10595_(new LightningStrikeTrigger());
   public static final UsingItemTrigger f_145090_ = m_10595_(new UsingItemTrigger());

   public static <T extends CriterionTrigger<?>> T m_10595_(T p_10596_) {
      if (f_10566_.containsKey(p_10596_.m_7295_())) {
         throw new IllegalArgumentException("Duplicate criterion id " + p_10596_.m_7295_());
      } else {
         f_10566_.put(p_10596_.m_7295_(), p_10596_);
         return p_10596_;
      }
   }

   @Nullable
   public static <T extends CriterionTriggerInstance> CriterionTrigger<T> m_10597_(ResourceLocation p_10598_) {
      return (CriterionTrigger<T>)f_10566_.get(p_10598_);
   }

   public static Iterable<? extends CriterionTrigger<?>> m_10594_() {
      return f_10566_.values();
   }
}