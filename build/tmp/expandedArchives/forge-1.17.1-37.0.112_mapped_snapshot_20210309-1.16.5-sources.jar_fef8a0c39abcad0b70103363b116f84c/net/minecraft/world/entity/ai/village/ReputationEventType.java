package net.minecraft.world.entity.ai.village;

public interface ReputationEventType {
   ReputationEventType f_26985_ = m_26991_("zombie_villager_cured");
   ReputationEventType f_26986_ = m_26991_("golem_killed");
   ReputationEventType f_26987_ = m_26991_("villager_hurt");
   ReputationEventType f_26988_ = m_26991_("villager_killed");
   ReputationEventType f_26989_ = m_26991_("trade");

   static ReputationEventType m_26991_(final String p_26992_) {
      return new ReputationEventType() {
         public String toString() {
            return p_26992_;
         }
      };
   }
}