package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class RamTarget<E extends PathfinderMob> extends Behavior<E> {
   public static final int f_147800_ = 200;
   public static final float f_147801_ = 1.65F;
   private final Function<E, UniformInt> f_147802_;
   private final TargetingConditions f_147803_;
   private final float f_147805_;
   private final ToDoubleFunction<E> f_147806_;
   private Vec3 f_147807_;
   private final Function<E, SoundEvent> f_147808_;

   public RamTarget(Function<E, UniformInt> p_182335_, TargetingConditions p_182336_, float p_182337_, ToDoubleFunction<E> p_182338_, Function<E, SoundEvent> p_182339_) {
      super(ImmutableMap.of(MemoryModuleType.f_148202_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_148203_, MemoryStatus.VALUE_PRESENT), 200);
      this.f_147802_ = p_182335_;
      this.f_147803_ = p_182336_;
      this.f_147805_ = p_182337_;
      this.f_147806_ = p_182338_;
      this.f_147808_ = p_182339_;
      this.f_147807_ = Vec3.f_82478_;
   }

   protected boolean m_6114_(ServerLevel p_147824_, PathfinderMob p_147825_) {
      return p_147825_.m_6274_().m_21874_(MemoryModuleType.f_148203_);
   }

   protected boolean m_6737_(ServerLevel p_147827_, PathfinderMob p_147828_, long p_147829_) {
      return p_147828_.m_6274_().m_21874_(MemoryModuleType.f_148203_);
   }

   protected void m_6735_(ServerLevel p_147838_, PathfinderMob p_147839_, long p_147840_) {
      BlockPos blockpos = p_147839_.m_142538_();
      Brain<?> brain = p_147839_.m_6274_();
      Vec3 vec3 = brain.m_21952_(MemoryModuleType.f_148203_).get();
      this.f_147807_ = (new Vec3((double)blockpos.m_123341_() - vec3.m_7096_(), 0.0D, (double)blockpos.m_123343_() - vec3.m_7094_())).m_82541_();
      brain.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(vec3, this.f_147805_, 0));
   }

   protected void m_6725_(ServerLevel p_147842_, E p_147843_, long p_147844_) {
      List<LivingEntity> list = p_147842_.m_45971_(LivingEntity.class, this.f_147803_, p_147843_, p_147843_.m_142469_());
      Brain<?> brain = p_147843_.m_6274_();
      if (!list.isEmpty()) {
         LivingEntity livingentity = list.get(0);
         livingentity.m_6469_(DamageSource.m_19370_(p_147843_).m_181120_(), (float)p_147843_.m_21133_(Attributes.f_22281_));
         int i = p_147843_.m_21023_(MobEffects.f_19596_) ? p_147843_.m_21124_(MobEffects.f_19596_).m_19564_() + 1 : 0;
         int j = p_147843_.m_21023_(MobEffects.f_19597_) ? p_147843_.m_21124_(MobEffects.f_19597_).m_19564_() + 1 : 0;
         float f = 0.25F * (float)(i - j);
         float f1 = Mth.m_14036_(p_147843_.m_6113_() * 1.65F, 0.2F, 3.0F) + f;
         float f2 = livingentity.m_21275_(DamageSource.m_19370_(p_147843_)) ? 0.5F : 1.0F;
         livingentity.m_147240_((double)(f2 * f1) * this.f_147806_.applyAsDouble(p_147843_), this.f_147807_.m_7096_(), this.f_147807_.m_7094_());
         this.m_147834_(p_147842_, p_147843_);
         p_147842_.m_6269_((Player)null, p_147843_, this.f_147808_.apply(p_147843_), SoundSource.HOSTILE, 1.0F, 1.0F);
      } else {
         Optional<WalkTarget> optional = brain.m_21952_(MemoryModuleType.f_26370_);
         Optional<Vec3> optional1 = brain.m_21952_(MemoryModuleType.f_148203_);
         boolean flag = !optional.isPresent() || !optional1.isPresent() || optional.get().m_26420_().m_7024_().m_82554_(optional1.get()) < 0.25D;
         if (flag) {
            this.m_147834_(p_147842_, p_147843_);
         }
      }

   }

   protected void m_147834_(ServerLevel p_147835_, E p_147836_) {
      p_147835_.m_7605_(p_147836_, (byte)59);
      p_147836_.m_6274_().m_21879_(MemoryModuleType.f_148202_, this.f_147802_.apply(p_147836_).m_142270_(p_147835_.f_46441_));
      p_147836_.m_6274_().m_21936_(MemoryModuleType.f_148203_);
   }
}