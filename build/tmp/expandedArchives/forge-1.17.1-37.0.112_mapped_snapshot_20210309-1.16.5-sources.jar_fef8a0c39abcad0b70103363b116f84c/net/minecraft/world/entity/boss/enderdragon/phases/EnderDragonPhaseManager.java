package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragonPhaseManager {
   private static final Logger f_31408_ = LogManager.getLogger();
   private final EnderDragon f_31409_;
   private final DragonPhaseInstance[] f_31410_ = new DragonPhaseInstance[EnderDragonPhase.m_31406_()];
   private DragonPhaseInstance f_31411_;

   public EnderDragonPhaseManager(EnderDragon p_31414_) {
      this.f_31409_ = p_31414_;
      this.m_31416_(EnderDragonPhase.f_31387_);
   }

   public void m_31416_(EnderDragonPhase<?> p_31417_) {
      if (this.f_31411_ == null || p_31417_ != this.f_31411_.m_7309_()) {
         if (this.f_31411_ != null) {
            this.f_31411_.m_7081_();
         }

         this.f_31411_ = this.m_31418_(p_31417_);
         if (!this.f_31409_.f_19853_.f_46443_) {
            this.f_31409_.m_20088_().m_135381_(EnderDragon.f_31067_, p_31417_.m_31405_());
         }

         f_31408_.debug("Dragon is now in phase {} on the {}", p_31417_, this.f_31409_.f_19853_.f_46443_ ? "client" : "server");
         this.f_31411_.m_7083_();
      }
   }

   public DragonPhaseInstance m_31415_() {
      return this.f_31411_;
   }

   public <T extends DragonPhaseInstance> T m_31418_(EnderDragonPhase<T> p_31419_) {
      int i = p_31419_.m_31405_();
      if (this.f_31410_[i] == null) {
         this.f_31410_[i] = p_31419_.m_31400_(this.f_31409_);
      }

      return (T)this.f_31410_[i];
   }
}