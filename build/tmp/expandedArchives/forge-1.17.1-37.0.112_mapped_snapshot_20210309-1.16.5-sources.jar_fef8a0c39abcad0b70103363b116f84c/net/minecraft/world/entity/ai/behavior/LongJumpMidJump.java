package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;

public class LongJumpMidJump extends Behavior<Mob> {
   public static final int f_147592_ = 100;
   private final UniformInt f_147593_;
   private SoundEvent f_147594_;

   public LongJumpMidJump(UniformInt p_147596_, SoundEvent p_147597_) {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_148200_, MemoryStatus.VALUE_PRESENT), 100);
      this.f_147593_ = p_147596_;
      this.f_147594_ = p_147597_;
   }

   protected boolean m_6737_(ServerLevel p_147603_, Mob p_147604_, long p_147605_) {
      return !p_147604_.m_20096_();
   }

   protected void m_6735_(ServerLevel p_147611_, Mob p_147612_, long p_147613_) {
      p_147612_.m_147244_(true);
      p_147612_.m_20124_(Pose.LONG_JUMPING);
   }

   protected void m_6732_(ServerLevel p_147619_, Mob p_147620_, long p_147621_) {
      if (p_147620_.m_20096_()) {
         p_147620_.m_20256_(p_147620_.m_20184_().m_82490_((double)0.1F));
         p_147619_.m_6269_((Player)null, p_147620_, this.f_147594_, SoundSource.NEUTRAL, 2.0F, 1.0F);
      }

      p_147620_.m_147244_(false);
      p_147620_.m_20124_(Pose.STANDING);
      p_147620_.m_6274_().m_21936_(MemoryModuleType.f_148200_);
      p_147620_.m_6274_().m_21879_(MemoryModuleType.f_148199_, this.f_147593_.m_142270_(p_147619_.f_46441_));
   }
}