package net.minecraft.world;

public enum InteractionResult {
   SUCCESS,
   CONSUME,
   CONSUME_PARTIAL,
   PASS,
   FAIL;

   public boolean m_19077_() {
      return this == SUCCESS || this == CONSUME || this == CONSUME_PARTIAL;
   }

   public boolean m_19080_() {
      return this == SUCCESS;
   }

   public boolean m_146666_() {
      return this == SUCCESS || this == CONSUME;
   }

   public static InteractionResult m_19078_(boolean p_19079_) {
      return p_19079_ ? SUCCESS : CONSUME;
   }
}