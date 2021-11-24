package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;

public class Swim extends Behavior<Mob> {
   private final float f_24381_;

   public Swim(float p_24383_) {
      super(ImmutableMap.of());
      this.f_24381_ = p_24383_;
   }

   protected boolean m_6114_(ServerLevel p_24388_, Mob p_24389_) {
      return p_24389_.m_20069_() && p_24389_.m_20120_(FluidTags.f_13131_) > p_24389_.m_20204_() || p_24389_.m_20077_();
   }

   protected boolean m_6737_(ServerLevel p_24391_, Mob p_24392_, long p_24393_) {
      return this.m_6114_(p_24391_, p_24392_);
   }

   protected void m_6725_(ServerLevel p_24399_, Mob p_24400_, long p_24401_) {
      if (p_24400_.m_21187_().nextFloat() < this.f_24381_) {
         p_24400_.m_21569_().m_24901_();
      }

   }
}