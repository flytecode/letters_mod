package net.minecraft.world.entity.boss.enderdragon.phases;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

public class EnderDragonPhase<T extends DragonPhaseInstance> {
   private static EnderDragonPhase<?>[] f_31388_ = new EnderDragonPhase[0];
   public static final EnderDragonPhase<DragonHoldingPatternPhase> f_31377_ = m_31402_(DragonHoldingPatternPhase.class, "HoldingPattern");
   public static final EnderDragonPhase<DragonStrafePlayerPhase> f_31378_ = m_31402_(DragonStrafePlayerPhase.class, "StrafePlayer");
   public static final EnderDragonPhase<DragonLandingApproachPhase> f_31379_ = m_31402_(DragonLandingApproachPhase.class, "LandingApproach");
   public static final EnderDragonPhase<DragonLandingPhase> f_31380_ = m_31402_(DragonLandingPhase.class, "Landing");
   public static final EnderDragonPhase<DragonTakeoffPhase> f_31381_ = m_31402_(DragonTakeoffPhase.class, "Takeoff");
   public static final EnderDragonPhase<DragonSittingFlamingPhase> f_31382_ = m_31402_(DragonSittingFlamingPhase.class, "SittingFlaming");
   public static final EnderDragonPhase<DragonSittingScanningPhase> f_31383_ = m_31402_(DragonSittingScanningPhase.class, "SittingScanning");
   public static final EnderDragonPhase<DragonSittingAttackingPhase> f_31384_ = m_31402_(DragonSittingAttackingPhase.class, "SittingAttacking");
   public static final EnderDragonPhase<DragonChargePlayerPhase> f_31385_ = m_31402_(DragonChargePlayerPhase.class, "ChargingPlayer");
   public static final EnderDragonPhase<DragonDeathPhase> f_31386_ = m_31402_(DragonDeathPhase.class, "Dying");
   public static final EnderDragonPhase<DragonHoverPhase> f_31387_ = m_31402_(DragonHoverPhase.class, "Hover");
   private final Class<? extends DragonPhaseInstance> f_31389_;
   private final int f_31390_;
   private final String f_31391_;

   private EnderDragonPhase(int p_31394_, Class<? extends DragonPhaseInstance> p_31395_, String p_31396_) {
      this.f_31390_ = p_31394_;
      this.f_31389_ = p_31395_;
      this.f_31391_ = p_31396_;
   }

   public DragonPhaseInstance m_31400_(EnderDragon p_31401_) {
      try {
         Constructor<? extends DragonPhaseInstance> constructor = this.m_31397_();
         return constructor.newInstance(p_31401_);
      } catch (Exception exception) {
         throw new Error(exception);
      }
   }

   protected Constructor<? extends DragonPhaseInstance> m_31397_() throws NoSuchMethodException {
      return this.f_31389_.getConstructor(EnderDragon.class);
   }

   public int m_31405_() {
      return this.f_31390_;
   }

   public String toString() {
      return this.f_31391_ + " (#" + this.f_31390_ + ")";
   }

   public static EnderDragonPhase<?> m_31398_(int p_31399_) {
      return p_31399_ >= 0 && p_31399_ < f_31388_.length ? f_31388_[p_31399_] : f_31377_;
   }

   public static int m_31406_() {
      return f_31388_.length;
   }

   private static <T extends DragonPhaseInstance> EnderDragonPhase<T> m_31402_(Class<T> p_31403_, String p_31404_) {
      EnderDragonPhase<T> enderdragonphase = new EnderDragonPhase<>(f_31388_.length, p_31403_, p_31404_);
      f_31388_ = Arrays.copyOf(f_31388_, f_31388_.length + 1);
      f_31388_[enderdragonphase.m_31405_()] = enderdragonphase;
      return enderdragonphase;
   }
}