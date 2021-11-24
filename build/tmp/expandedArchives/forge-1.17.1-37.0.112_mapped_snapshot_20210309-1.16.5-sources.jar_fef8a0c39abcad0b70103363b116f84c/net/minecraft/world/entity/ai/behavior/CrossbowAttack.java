package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CrossbowAttack<E extends Mob & CrossbowAttackMob, T extends LivingEntity> extends Behavior<E> {
   private static final int f_147479_ = 1200;
   private int f_22771_;
   private CrossbowAttack.CrossbowState f_22772_ = CrossbowAttack.CrossbowState.UNCHARGED;

   public CrossbowAttack() {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_26372_, MemoryStatus.VALUE_PRESENT), 1200);
   }

   protected boolean m_6114_(ServerLevel p_22778_, E p_22779_) {
      LivingEntity livingentity = m_22784_(p_22779_);
      return p_22779_.m_21093_(is -> is.m_41720_() instanceof CrossbowItem) && BehaviorUtils.m_22667_(p_22779_, livingentity) && BehaviorUtils.m_22632_(p_22779_, livingentity, 0);
   }

   protected boolean m_6737_(ServerLevel p_22781_, E p_22782_, long p_22783_) {
      return p_22782_.m_6274_().m_21874_(MemoryModuleType.f_26372_) && this.m_6114_(p_22781_, p_22782_);
   }

   protected void m_6725_(ServerLevel p_22794_, E p_22795_, long p_22796_) {
      LivingEntity livingentity = m_22784_(p_22795_);
      this.m_22797_(p_22795_, livingentity);
      this.m_22786_(p_22795_, livingentity);
   }

   protected void m_6732_(ServerLevel p_22805_, E p_22806_, long p_22807_) {
      if (p_22806_.m_6117_()) {
         p_22806_.m_5810_();
      }

      if (p_22806_.m_21093_(is -> is.m_41720_() instanceof CrossbowItem)) {
         p_22806_.m_6136_(false);
         CrossbowItem.m_40884_(p_22806_.m_21211_(), false);
      }

   }

   private void m_22786_(E p_22787_, LivingEntity p_22788_) {
      if (this.f_22772_ == CrossbowAttack.CrossbowState.UNCHARGED) {
         p_22787_.m_6672_(ProjectileUtil.getWeaponHoldingHand(p_22787_, item -> item instanceof CrossbowItem));
         this.f_22772_ = CrossbowAttack.CrossbowState.CHARGING;
         p_22787_.m_6136_(true);
      } else if (this.f_22772_ == CrossbowAttack.CrossbowState.CHARGING) {
         if (!p_22787_.m_6117_()) {
            this.f_22772_ = CrossbowAttack.CrossbowState.UNCHARGED;
         }

         int i = p_22787_.m_21252_();
         ItemStack itemstack = p_22787_.m_21211_();
         if (i >= CrossbowItem.m_40939_(itemstack)) {
            p_22787_.m_21253_();
            this.f_22772_ = CrossbowAttack.CrossbowState.CHARGED;
            this.f_22771_ = 20 + p_22787_.m_21187_().nextInt(20);
            p_22787_.m_6136_(false);
         }
      } else if (this.f_22772_ == CrossbowAttack.CrossbowState.CHARGED) {
         --this.f_22771_;
         if (this.f_22771_ == 0) {
            this.f_22772_ = CrossbowAttack.CrossbowState.READY_TO_ATTACK;
         }
      } else if (this.f_22772_ == CrossbowAttack.CrossbowState.READY_TO_ATTACK) {
         p_22787_.m_6504_(p_22788_, 1.0F);
         ItemStack itemstack1 = p_22787_.m_21120_(ProjectileUtil.getWeaponHoldingHand(p_22787_, item -> item instanceof CrossbowItem));
         CrossbowItem.m_40884_(itemstack1, false);
         this.f_22772_ = CrossbowAttack.CrossbowState.UNCHARGED;
      }

   }

   private void m_22797_(Mob p_22798_, LivingEntity p_22799_) {
      p_22798_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_22799_, true));
   }

   private static LivingEntity m_22784_(LivingEntity p_22785_) {
      return p_22785_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
   }

   static enum CrossbowState {
      UNCHARGED,
      CHARGING,
      CHARGED,
      READY_TO_ATTACK;
   }
}
