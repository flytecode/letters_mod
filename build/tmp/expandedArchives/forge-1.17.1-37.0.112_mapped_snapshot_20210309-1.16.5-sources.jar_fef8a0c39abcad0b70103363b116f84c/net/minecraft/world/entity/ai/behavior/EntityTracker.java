package net.minecraft.world.entity.ai.behavior;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;

public class EntityTracker implements PositionTracker {
   private final Entity f_22846_;
   private final boolean f_22847_;

   public EntityTracker(Entity p_22849_, boolean p_22850_) {
      this.f_22846_ = p_22849_;
      this.f_22847_ = p_22850_;
   }

   public Vec3 m_7024_() {
      return this.f_22847_ ? this.f_22846_.m_20182_().m_82520_(0.0D, (double)this.f_22846_.m_20192_(), 0.0D) : this.f_22846_.m_20182_();
   }

   public BlockPos m_6675_() {
      return this.f_22846_.m_142538_();
   }

   public boolean m_6826_(LivingEntity p_22853_) {
      if (!(this.f_22846_ instanceof LivingEntity)) {
         return true;
      } else {
         Optional<List<LivingEntity>> optional = p_22853_.m_6274_().m_21952_(MemoryModuleType.f_148205_);
         return this.f_22846_.m_6084_() && optional.isPresent() && optional.get().contains(this.f_22846_);
      }
   }

   public Entity m_147481_() {
      return this.f_22846_;
   }

   public String toString() {
      return "EntityTracker for " + this.f_22846_;
   }
}