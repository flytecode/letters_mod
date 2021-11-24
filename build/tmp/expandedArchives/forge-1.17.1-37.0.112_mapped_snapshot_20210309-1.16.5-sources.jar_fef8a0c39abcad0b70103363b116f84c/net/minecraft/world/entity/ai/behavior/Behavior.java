package net.minecraft.world.entity.ai.behavior;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public abstract class Behavior<E extends LivingEntity> {
   private static final int f_147431_ = 60;
   protected final Map<MemoryModuleType<?>, MemoryStatus> f_22522_;
   private Behavior.Status f_22523_ = Behavior.Status.STOPPED;
   private long f_22524_;
   private final int f_22525_;
   private final int f_22526_;

   public Behavior(Map<MemoryModuleType<?>, MemoryStatus> p_22528_) {
      this(p_22528_, 60);
   }

   public Behavior(Map<MemoryModuleType<?>, MemoryStatus> p_22530_, int p_22531_) {
      this(p_22530_, p_22531_, p_22531_);
   }

   public Behavior(Map<MemoryModuleType<?>, MemoryStatus> p_22533_, int p_22534_, int p_22535_) {
      this.f_22525_ = p_22534_;
      this.f_22526_ = p_22535_;
      this.f_22522_ = p_22533_;
   }

   public Behavior.Status m_22536_() {
      return this.f_22523_;
   }

   public final boolean m_22554_(ServerLevel p_22555_, E p_22556_, long p_22557_) {
      if (this.m_22543_(p_22556_) && this.m_6114_(p_22555_, p_22556_)) {
         this.f_22523_ = Behavior.Status.RUNNING;
         int i = this.f_22525_ + p_22555_.m_5822_().nextInt(this.f_22526_ + 1 - this.f_22525_);
         this.f_22524_ = p_22557_ + (long)i;
         this.m_6735_(p_22555_, p_22556_, p_22557_);
         return true;
      } else {
         return false;
      }
   }

   protected void m_6735_(ServerLevel p_22540_, E p_22541_, long p_22542_) {
   }

   public final void m_22558_(ServerLevel p_22559_, E p_22560_, long p_22561_) {
      if (!this.m_7773_(p_22561_) && this.m_6737_(p_22559_, p_22560_, p_22561_)) {
         this.m_6725_(p_22559_, p_22560_, p_22561_);
      } else {
         this.m_22562_(p_22559_, p_22560_, p_22561_);
      }

   }

   protected void m_6725_(ServerLevel p_22551_, E p_22552_, long p_22553_) {
   }

   public final void m_22562_(ServerLevel p_22563_, E p_22564_, long p_22565_) {
      this.f_22523_ = Behavior.Status.STOPPED;
      this.m_6732_(p_22563_, p_22564_, p_22565_);
   }

   protected void m_6732_(ServerLevel p_22548_, E p_22549_, long p_22550_) {
   }

   protected boolean m_6737_(ServerLevel p_22545_, E p_22546_, long p_22547_) {
      return false;
   }

   protected boolean m_7773_(long p_22537_) {
      return p_22537_ > this.f_22524_;
   }

   protected boolean m_6114_(ServerLevel p_22538_, E p_22539_) {
      return true;
   }

   public String toString() {
      return this.getClass().getSimpleName();
   }

   private boolean m_22543_(E p_22544_) {
      for(Entry<MemoryModuleType<?>, MemoryStatus> entry : this.f_22522_.entrySet()) {
         MemoryModuleType<?> memorymoduletype = entry.getKey();
         MemoryStatus memorystatus = entry.getValue();
         if (!p_22544_.m_6274_().m_21876_(memorymoduletype, memorystatus)) {
            return false;
         }
      }

      return true;
   }

   public static enum Status {
      STOPPED,
      RUNNING;
   }
}