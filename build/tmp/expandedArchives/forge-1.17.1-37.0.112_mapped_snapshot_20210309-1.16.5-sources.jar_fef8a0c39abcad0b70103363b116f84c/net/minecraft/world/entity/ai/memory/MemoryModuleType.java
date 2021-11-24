package net.minecraft.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SerializableUUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class MemoryModuleType<U> extends net.minecraftforge.registries.ForgeRegistryEntry<MemoryModuleType<?>> {
   public static final MemoryModuleType<Void> f_26349_ = m_26388_("dummy");
   public static final MemoryModuleType<GlobalPos> f_26359_ = m_26390_("home", GlobalPos.f_122633_);
   public static final MemoryModuleType<GlobalPos> f_26360_ = m_26390_("job_site", GlobalPos.f_122633_);
   public static final MemoryModuleType<GlobalPos> f_26361_ = m_26390_("potential_job_site", GlobalPos.f_122633_);
   public static final MemoryModuleType<GlobalPos> f_26362_ = m_26390_("meeting_point", GlobalPos.f_122633_);
   public static final MemoryModuleType<List<GlobalPos>> f_26363_ = m_26388_("secondary_job_site");
   public static final MemoryModuleType<List<LivingEntity>> f_148204_ = m_26388_("mobs");
   public static final MemoryModuleType<List<LivingEntity>> f_148205_ = m_26388_("visible_mobs");
   public static final MemoryModuleType<List<LivingEntity>> f_26366_ = m_26388_("visible_villager_babies");
   public static final MemoryModuleType<List<Player>> f_26367_ = m_26388_("nearest_players");
   public static final MemoryModuleType<Player> f_26368_ = m_26388_("nearest_visible_player");
   public static final MemoryModuleType<Player> f_148206_ = m_26388_("nearest_visible_targetable_player");
   public static final MemoryModuleType<WalkTarget> f_26370_ = m_26388_("walk_target");
   public static final MemoryModuleType<PositionTracker> f_26371_ = m_26388_("look_target");
   public static final MemoryModuleType<LivingEntity> f_26372_ = m_26388_("attack_target");
   public static final MemoryModuleType<Boolean> f_26373_ = m_26388_("attack_cooling_down");
   public static final MemoryModuleType<LivingEntity> f_26374_ = m_26388_("interaction_target");
   public static final MemoryModuleType<AgeableMob> f_26375_ = m_26388_("breed_target");
   public static final MemoryModuleType<Entity> f_26376_ = m_26388_("ride_target");
   public static final MemoryModuleType<Path> f_26377_ = m_26388_("path");
   public static final MemoryModuleType<List<GlobalPos>> f_26378_ = m_26388_("interactable_doors");
   public static final MemoryModuleType<Set<GlobalPos>> f_26379_ = m_26388_("doors_to_close");
   public static final MemoryModuleType<BlockPos> f_26380_ = m_26388_("nearest_bed");
   public static final MemoryModuleType<DamageSource> f_26381_ = m_26388_("hurt_by");
   public static final MemoryModuleType<LivingEntity> f_26382_ = m_26388_("hurt_by_entity");
   public static final MemoryModuleType<LivingEntity> f_26383_ = m_26388_("avoid_target");
   public static final MemoryModuleType<LivingEntity> f_26323_ = m_26388_("nearest_hostile");
   public static final MemoryModuleType<LivingEntity> f_148194_ = m_26388_("nearest_attackable");
   public static final MemoryModuleType<GlobalPos> f_26324_ = m_26388_("hiding_place");
   public static final MemoryModuleType<Long> f_26325_ = m_26388_("heard_bell_time");
   public static final MemoryModuleType<Long> f_26326_ = m_26388_("cant_reach_walk_target_since");
   public static final MemoryModuleType<Boolean> f_26327_ = m_26390_("golem_detected_recently", Codec.BOOL);
   public static final MemoryModuleType<Long> f_26328_ = m_26390_("last_slept", Codec.LONG);
   public static final MemoryModuleType<Long> f_26329_ = m_26390_("last_woken", Codec.LONG);
   public static final MemoryModuleType<Long> f_26330_ = m_26390_("last_worked_at_poi", Codec.LONG);
   public static final MemoryModuleType<AgeableMob> f_26331_ = m_26388_("nearest_visible_adult");
   public static final MemoryModuleType<ItemEntity> f_26332_ = m_26388_("nearest_visible_wanted_item");
   public static final MemoryModuleType<Mob> f_26333_ = m_26388_("nearest_visible_nemesis");
   public static final MemoryModuleType<Integer> f_148195_ = m_26390_("play_dead_ticks", Codec.INT);
   public static final MemoryModuleType<Player> f_148196_ = m_26388_("tempting_player");
   public static final MemoryModuleType<Integer> f_148197_ = m_26390_("temptation_cooldown_ticks", Codec.INT);
   public static final MemoryModuleType<Boolean> f_148198_ = m_26390_("is_tempted", Codec.BOOL);
   public static final MemoryModuleType<Integer> f_148199_ = m_26390_("long_jump_cooling_down", Codec.INT);
   public static final MemoryModuleType<Boolean> f_148200_ = m_26388_("long_jump_mid_jump");
   public static final MemoryModuleType<Boolean> f_148201_ = m_26390_("has_hunting_cooldown", Codec.BOOL);
   public static final MemoryModuleType<Integer> f_148202_ = m_26390_("ram_cooldown_ticks", Codec.INT);
   public static final MemoryModuleType<Vec3> f_148203_ = m_26388_("ram_target");
   public static final MemoryModuleType<UUID> f_26334_ = m_26390_("angry_at", SerializableUUID.f_123272_);
   public static final MemoryModuleType<Boolean> f_26335_ = m_26390_("universal_anger", Codec.BOOL);
   public static final MemoryModuleType<Boolean> f_26336_ = m_26390_("admiring_item", Codec.BOOL);
   public static final MemoryModuleType<Integer> f_26337_ = m_26388_("time_trying_to_reach_admire_item");
   public static final MemoryModuleType<Boolean> f_26338_ = m_26388_("disable_walk_to_admire_item");
   public static final MemoryModuleType<Boolean> f_26339_ = m_26390_("admiring_disabled", Codec.BOOL);
   public static final MemoryModuleType<Boolean> f_26340_ = m_26390_("hunted_recently", Codec.BOOL);
   public static final MemoryModuleType<BlockPos> f_26341_ = m_26388_("celebrate_location");
   public static final MemoryModuleType<Boolean> f_26342_ = m_26388_("dancing");
   public static final MemoryModuleType<Hoglin> f_26343_ = m_26388_("nearest_visible_huntable_hoglin");
   public static final MemoryModuleType<Hoglin> f_26344_ = m_26388_("nearest_visible_baby_hoglin");
   public static final MemoryModuleType<Player> f_26345_ = m_26388_("nearest_targetable_player_not_wearing_gold");
   public static final MemoryModuleType<List<AbstractPiglin>> f_26346_ = m_26388_("nearby_adult_piglins");
   public static final MemoryModuleType<List<AbstractPiglin>> f_26347_ = m_26388_("nearest_visible_adult_piglins");
   public static final MemoryModuleType<List<Hoglin>> f_26348_ = m_26388_("nearest_visible_adult_hoglins");
   public static final MemoryModuleType<AbstractPiglin> f_26350_ = m_26388_("nearest_visible_adult_piglin");
   public static final MemoryModuleType<LivingEntity> f_26351_ = m_26388_("nearest_visible_zombified");
   public static final MemoryModuleType<Integer> f_26352_ = m_26388_("visible_adult_piglin_count");
   public static final MemoryModuleType<Integer> f_26353_ = m_26388_("visible_adult_hoglin_count");
   public static final MemoryModuleType<Player> f_26354_ = m_26388_("nearest_player_holding_wanted_item");
   public static final MemoryModuleType<Boolean> f_26355_ = m_26388_("ate_recently");
   public static final MemoryModuleType<BlockPos> f_26356_ = m_26388_("nearest_repellent");
   public static final MemoryModuleType<Boolean> f_26357_ = m_26388_("pacified");
   private final Optional<Codec<ExpirableValue<U>>> f_26358_;

   public MemoryModuleType(Optional<Codec<U>> p_26386_) {
      this.f_26358_ = p_26386_.map(ExpirableValue::m_26304_);
   }

   public String toString() {
      return Registry.f_122871_.m_7981_(this).toString();
   }

   public Optional<Codec<ExpirableValue<U>>> m_26387_() {
      return this.f_26358_;
   }

   private static <U> MemoryModuleType<U> m_26390_(String p_26391_, Codec<U> p_26392_) {
      return Registry.m_122965_(Registry.f_122871_, new ResourceLocation(p_26391_), new MemoryModuleType<>(Optional.of(p_26392_)));
   }

   private static <U> MemoryModuleType<U> m_26388_(String p_26389_) {
      return Registry.m_122965_(Registry.f_122871_, new ResourceLocation(p_26389_), new MemoryModuleType<>(Optional.empty()));
   }
}
