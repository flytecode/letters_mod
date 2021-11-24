package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PlayDead extends Behavior<Axolotl> {
   public PlayDead() {
      super(ImmutableMap.of(MemoryModuleType.f_148195_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26382_, MemoryStatus.VALUE_PRESENT), 200);
   }

   protected boolean m_6114_(ServerLevel p_149319_, Axolotl p_149320_) {
      return p_149320_.m_20072_();
   }

   protected boolean m_6737_(ServerLevel p_149322_, Axolotl p_149323_, long p_149324_) {
      return p_149323_.m_20072_() && p_149323_.m_6274_().m_21874_(MemoryModuleType.f_148195_);
   }

   protected void m_6735_(ServerLevel p_149330_, Axolotl p_149331_, long p_149332_) {
      Brain<Axolotl> brain = p_149331_.m_6274_();
      brain.m_21936_(MemoryModuleType.f_26370_);
      brain.m_21936_(MemoryModuleType.f_26371_);
      p_149331_.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 200, 0));
   }
}