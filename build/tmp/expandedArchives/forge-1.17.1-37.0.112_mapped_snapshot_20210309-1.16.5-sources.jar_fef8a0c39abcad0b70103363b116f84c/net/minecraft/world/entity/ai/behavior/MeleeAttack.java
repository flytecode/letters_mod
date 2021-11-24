package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;

public class MeleeAttack extends Behavior<Mob> {
   private final int f_23510_;

   public MeleeAttack(int p_23512_) {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_26372_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26373_, MemoryStatus.VALUE_ABSENT));
      this.f_23510_ = p_23512_;
   }

   protected boolean m_6114_(ServerLevel p_23521_, Mob p_23522_) {
      LivingEntity livingentity = this.m_23532_(p_23522_);
      return !this.m_23527_(p_23522_) && BehaviorUtils.m_22667_(p_23522_, livingentity) && BehaviorUtils.m_147441_(p_23522_, livingentity);
   }

   private boolean m_23527_(Mob p_23528_) {
      return p_23528_.m_21093_((p_147697_) -> {
         Item item = p_147697_.m_41720_();
         return item instanceof ProjectileWeaponItem && p_23528_.m_5886_((ProjectileWeaponItem)item);
      });
   }

   protected void m_6735_(ServerLevel p_23524_, Mob p_23525_, long p_23526_) {
      LivingEntity livingentity = this.m_23532_(p_23525_);
      BehaviorUtils.m_22595_(p_23525_, livingentity);
      p_23525_.m_6674_(InteractionHand.MAIN_HAND);
      p_23525_.m_7327_(livingentity);
      p_23525_.m_6274_().m_21882_(MemoryModuleType.f_26373_, true, (long)this.f_23510_);
   }

   private LivingEntity m_23532_(Mob p_23533_) {
      return p_23533_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
   }
}