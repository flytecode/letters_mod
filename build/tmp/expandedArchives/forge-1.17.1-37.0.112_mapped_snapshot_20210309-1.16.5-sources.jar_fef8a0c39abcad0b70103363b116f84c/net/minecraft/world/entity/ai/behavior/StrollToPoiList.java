package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;

public class StrollToPoiList extends Behavior<Villager> {
   private final MemoryModuleType<List<GlobalPos>> f_24354_;
   private final MemoryModuleType<GlobalPos> f_24355_;
   private final float f_24356_;
   private final int f_24357_;
   private final int f_24358_;
   private long f_24359_;
   @Nullable
   private GlobalPos f_24360_;

   public StrollToPoiList(MemoryModuleType<List<GlobalPos>> p_24362_, float p_24363_, int p_24364_, int p_24365_, MemoryModuleType<GlobalPos> p_24366_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.REGISTERED, p_24362_, MemoryStatus.VALUE_PRESENT, p_24366_, MemoryStatus.VALUE_PRESENT));
      this.f_24354_ = p_24362_;
      this.f_24356_ = p_24363_;
      this.f_24357_ = p_24364_;
      this.f_24358_ = p_24365_;
      this.f_24355_ = p_24366_;
   }

   protected boolean m_6114_(ServerLevel p_24375_, Villager p_24376_) {
      Optional<List<GlobalPos>> optional = p_24376_.m_6274_().m_21952_(this.f_24354_);
      Optional<GlobalPos> optional1 = p_24376_.m_6274_().m_21952_(this.f_24355_);
      if (optional.isPresent() && optional1.isPresent()) {
         List<GlobalPos> list = optional.get();
         if (!list.isEmpty()) {
            this.f_24360_ = list.get(p_24375_.m_5822_().nextInt(list.size()));
            return this.f_24360_ != null && p_24375_.m_46472_() == this.f_24360_.m_122640_() && optional1.get().m_122646_().m_123306_(p_24376_.m_20182_(), (double)this.f_24358_);
         }
      }

      return false;
   }

   protected void m_6735_(ServerLevel p_24378_, Villager p_24379_, long p_24380_) {
      if (p_24380_ > this.f_24359_ && this.f_24360_ != null) {
         p_24379_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(this.f_24360_.m_122646_(), this.f_24356_, this.f_24357_));
         this.f_24359_ = p_24380_ + 100L;
      }

   }
}