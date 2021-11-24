package net.minecraft.util.profiling.metrics;

public enum MetricCategory {
   PATH_FINDING("pathfinding"),
   EVENT_LOOPS("event-loops"),
   MAIL_BOXES("mailboxes"),
   TICK_LOOP("ticking"),
   JVM("jvm"),
   CHUNK_RENDERING("chunk rendering"),
   CHUNK_RENDERING_DISPATCHING("chunk rendering dispatching"),
   CPU("cpu");

   private final String f_145974_;

   private MetricCategory(String p_145980_) {
      this.f_145974_ = p_145980_;
   }

   public String m_145981_() {
      return this.f_145974_;
   }
}