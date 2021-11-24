package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class VillagerGoalPackages {
   private static final float f_148040_ = 0.4F;

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24585_(VillagerProfession p_24586_, float p_24587_) {
      return ImmutableList.of(Pair.of(0, new Swim(0.8F)), Pair.of(0, new InteractWithDoor()), Pair.of(0, new LookAtTargetSink(45, 90)), Pair.of(0, new VillagerPanicTrigger()), Pair.of(0, new WakeUp()), Pair.of(0, new ReactToBell()), Pair.of(0, new SetRaidStatus()), Pair.of(0, new ValidateNearbyPoi(p_24586_.m_35622_(), MemoryModuleType.f_26360_)), Pair.of(0, new ValidateNearbyPoi(p_24586_.m_35622_(), MemoryModuleType.f_26361_)), Pair.of(1, new MoveToTargetSink()), Pair.of(2, new PoiCompetitorScan(p_24586_)), Pair.of(3, new LookAndFollowTradingPlayerSink(p_24587_)), Pair.of(5, new GoToWantedItem(p_24587_, false, 4)), Pair.of(6, new AcquirePoi(p_24586_.m_35622_(), MemoryModuleType.f_26360_, MemoryModuleType.f_26361_, true, Optional.empty())), Pair.of(7, new GoToPotentialJobSite(p_24587_)), Pair.of(8, new YieldJobSite(p_24587_)), Pair.of(10, new AcquirePoi(PoiType.f_27346_, MemoryModuleType.f_26359_, false, Optional.of((byte)14))), Pair.of(10, new AcquirePoi(PoiType.f_27347_, MemoryModuleType.f_26362_, true, Optional.of((byte)14))), Pair.of(10, new AssignProfessionFromJobSite()), Pair.of(10, new ResetProfession()));
   }

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24589_(VillagerProfession p_24590_, float p_24591_) {
      WorkAtPoi workatpoi;
      if (p_24590_ == VillagerProfession.f_35590_) {
         workatpoi = new WorkAtComposter();
      } else {
         workatpoi = new WorkAtPoi();
      }

      return ImmutableList.of(m_24588_(), Pair.of(5, new RunOne<>(ImmutableList.of(Pair.of(workatpoi, 7), Pair.of(new StrollAroundPoi(MemoryModuleType.f_26360_, 0.4F, 4), 2), Pair.of(new StrollToPoi(MemoryModuleType.f_26360_, 0.4F, 1, 10), 5), Pair.of(new StrollToPoiList(MemoryModuleType.f_26363_, p_24591_, 1, 6, MemoryModuleType.f_26360_), 5), Pair.of(new HarvestFarmland(), p_24590_ == VillagerProfession.f_35590_ ? 2 : 5), Pair.of(new UseBonemeal(), p_24590_ == VillagerProfession.f_35590_ ? 4 : 7)))), Pair.of(10, new ShowTradesToPlayer(400, 1600)), Pair.of(10, new SetLookAndInteract(EntityType.f_20532_, 4)), Pair.of(2, new SetWalkTargetFromBlockMemory(MemoryModuleType.f_26360_, p_24591_, 9, 100, 1200)), Pair.of(3, new GiveGiftToHero(100)), Pair.of(99, new UpdateActivityFromSchedule()));
   }

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24583_(float p_24584_) {
      return ImmutableList.of(Pair.of(0, new MoveToTargetSink(80, 120)), m_24582_(), Pair.of(5, new PlayTagWithOtherKids()), Pair.of(5, new RunOne<>(ImmutableMap.of(MemoryModuleType.f_26366_, MemoryStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(InteractWith.m_23260_(EntityType.f_20492_, 8, MemoryModuleType.f_26374_, p_24584_, 2), 2), Pair.of(InteractWith.m_23260_(EntityType.f_20553_, 8, MemoryModuleType.f_26374_, p_24584_, 2), 1), Pair.of(new VillageBoundRandomStroll(p_24584_), 1), Pair.of(new SetWalkTargetFromLookTarget(p_24584_, 2), 1), Pair.of(new JumpOnBed(p_24584_), 2), Pair.of(new DoNothing(20, 40), 2)))), Pair.of(99, new UpdateActivityFromSchedule()));
   }

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24592_(VillagerProfession p_24593_, float p_24594_) {
      return ImmutableList.of(Pair.of(2, new SetWalkTargetFromBlockMemory(MemoryModuleType.f_26359_, p_24594_, 1, 150, 1200)), Pair.of(3, new ValidateNearbyPoi(PoiType.f_27346_, MemoryModuleType.f_26359_)), Pair.of(3, new SleepInBed()), Pair.of(5, new RunOne<>(ImmutableMap.of(MemoryModuleType.f_26359_, MemoryStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(new SetClosestHomeAsWalkTarget(p_24594_), 1), Pair.of(new InsideBrownianWalk(p_24594_), 4), Pair.of(new GoToClosestVillage(p_24594_, 4), 2), Pair.of(new DoNothing(20, 40), 2)))), m_24588_(), Pair.of(99, new UpdateActivityFromSchedule()));
   }

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24595_(VillagerProfession p_24596_, float p_24597_) {
      return ImmutableList.of(Pair.of(2, new RunOne<>(ImmutableList.of(Pair.of(new StrollAroundPoi(MemoryModuleType.f_26362_, 0.4F, 40), 2), Pair.of(new SocializeAtBell(), 2)))), Pair.of(10, new ShowTradesToPlayer(400, 1600)), Pair.of(10, new SetLookAndInteract(EntityType.f_20532_, 4)), Pair.of(2, new SetWalkTargetFromBlockMemory(MemoryModuleType.f_26362_, p_24597_, 6, 100, 200)), Pair.of(3, new GiveGiftToHero(100)), Pair.of(3, new ValidateNearbyPoi(PoiType.f_27347_, MemoryModuleType.f_26362_)), Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.f_26374_), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(Pair.of(new TradeWithVillager(), 1)))), m_24582_(), Pair.of(99, new UpdateActivityFromSchedule()));
   }

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24598_(VillagerProfession p_24599_, float p_24600_) {
      return ImmutableList.of(Pair.of(2, new RunOne<>(ImmutableList.of(Pair.of(InteractWith.m_23260_(EntityType.f_20492_, 8, MemoryModuleType.f_26374_, p_24600_, 2), 2), Pair.of(new InteractWith<>(EntityType.f_20492_, 8, AgeableMob::m_142668_, AgeableMob::m_142668_, MemoryModuleType.f_26375_, p_24600_, 2), 1), Pair.of(InteractWith.m_23260_(EntityType.f_20553_, 8, MemoryModuleType.f_26374_, p_24600_, 2), 1), Pair.of(new VillageBoundRandomStroll(p_24600_), 1), Pair.of(new SetWalkTargetFromLookTarget(p_24600_, 2), 1), Pair.of(new JumpOnBed(p_24600_), 1), Pair.of(new DoNothing(30, 60), 1)))), Pair.of(3, new GiveGiftToHero(100)), Pair.of(3, new SetLookAndInteract(EntityType.f_20532_, 4)), Pair.of(3, new ShowTradesToPlayer(400, 1600)), Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.f_26374_), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(Pair.of(new TradeWithVillager(), 1)))), Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.f_26375_), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(Pair.of(new VillagerMakeLove(), 1)))), m_24582_(), Pair.of(99, new UpdateActivityFromSchedule()));
   }

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24601_(VillagerProfession p_24602_, float p_24603_) {
      float f = p_24603_ * 1.5F;
      return ImmutableList.of(Pair.of(0, new VillagerCalmDown()), Pair.of(1, SetWalkTargetAwayFrom.m_24019_(MemoryModuleType.f_26323_, f, 6, false)), Pair.of(1, SetWalkTargetAwayFrom.m_24019_(MemoryModuleType.f_26382_, f, 6, false)), Pair.of(3, new VillageBoundRandomStroll(f, 2, 2)), m_24588_());
   }

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24604_(VillagerProfession p_24605_, float p_24606_) {
      return ImmutableList.of(Pair.of(0, new RingBell()), Pair.of(0, new RunOne<>(ImmutableList.of(Pair.of(new SetWalkTargetFromBlockMemory(MemoryModuleType.f_26362_, p_24606_ * 1.5F, 2, 150, 200), 6), Pair.of(new VillageBoundRandomStroll(p_24606_ * 1.5F), 2)))), m_24588_(), Pair.of(99, new ResetRaidStatus()));
   }

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24607_(VillagerProfession p_24608_, float p_24609_) {
      return ImmutableList.of(Pair.of(0, new RunOne<>(ImmutableList.of(Pair.of(new GoOutsideToCelebrate(p_24609_), 5), Pair.of(new VictoryStroll(p_24609_ * 1.1F), 2)))), Pair.of(0, new CelebrateVillagersSurvivedRaid(600, 600)), Pair.of(2, new LocateHidingPlaceDuringRaid(24, p_24609_ * 1.4F)), m_24588_(), Pair.of(99, new ResetRaidStatus()));
   }

   public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24610_(VillagerProfession p_24611_, float p_24612_) {
      int i = 2;
      return ImmutableList.of(Pair.of(0, new SetHiddenState(15, 3)), Pair.of(1, new LocateHidingPlace(32, p_24612_ * 1.25F, 2)), m_24588_());
   }

   private static Pair<Integer, Behavior<LivingEntity>> m_24582_() {
      return Pair.of(5, new RunOne<>(ImmutableList.of(Pair.of(new SetEntityLookTarget(EntityType.f_20553_, 8.0F), 8), Pair.of(new SetEntityLookTarget(EntityType.f_20492_, 8.0F), 2), Pair.of(new SetEntityLookTarget(EntityType.f_20532_, 8.0F), 2), Pair.of(new SetEntityLookTarget(MobCategory.CREATURE, 8.0F), 1), Pair.of(new SetEntityLookTarget(MobCategory.WATER_CREATURE, 8.0F), 1), Pair.of(new SetEntityLookTarget(MobCategory.UNDERGROUND_WATER_CREATURE, 8.0F), 1), Pair.of(new SetEntityLookTarget(MobCategory.WATER_AMBIENT, 8.0F), 1), Pair.of(new SetEntityLookTarget(MobCategory.MONSTER, 8.0F), 1), Pair.of(new DoNothing(30, 60), 2))));
   }

   private static Pair<Integer, Behavior<LivingEntity>> m_24588_() {
      return Pair.of(5, new RunOne<>(ImmutableList.of(Pair.of(new SetEntityLookTarget(EntityType.f_20492_, 8.0F), 2), Pair.of(new SetEntityLookTarget(EntityType.f_20532_, 8.0F), 2), Pair.of(new DoNothing(30, 60), 8))));
   }
}