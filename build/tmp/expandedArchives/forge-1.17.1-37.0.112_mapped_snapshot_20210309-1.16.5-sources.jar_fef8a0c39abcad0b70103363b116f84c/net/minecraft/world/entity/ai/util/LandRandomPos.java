package net.minecraft.world.entity.ai.util;

import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class LandRandomPos {
   @Nullable
   public static Vec3 m_148488_(PathfinderMob p_148489_, int p_148490_, int p_148491_) {
      return m_148503_(p_148489_, p_148490_, p_148491_, p_148489_::m_21692_);
   }

   @Nullable
   public static Vec3 m_148503_(PathfinderMob p_148504_, int p_148505_, int p_148506_, ToDoubleFunction<BlockPos> p_148507_) {
      boolean flag = GoalUtils.m_148442_(p_148504_, p_148505_);
      return RandomPos.m_148561_(() -> {
         BlockPos blockpos = RandomPos.m_148549_(p_148504_.m_21187_(), p_148505_, p_148506_);
         BlockPos blockpos1 = m_148513_(p_148504_, p_148505_, flag, blockpos);
         return blockpos1 == null ? null : m_148518_(p_148504_, blockpos1);
      }, p_148507_);
   }

   @Nullable
   public static Vec3 m_148492_(PathfinderMob p_148493_, int p_148494_, int p_148495_, Vec3 p_148496_) {
      Vec3 vec3 = p_148496_.m_82492_(p_148493_.m_20185_(), p_148493_.m_20186_(), p_148493_.m_20189_());
      boolean flag = GoalUtils.m_148442_(p_148493_, p_148494_);
      return m_148497_(p_148493_, p_148494_, p_148495_, vec3, flag);
   }

   @Nullable
   public static Vec3 m_148521_(PathfinderMob p_148522_, int p_148523_, int p_148524_, Vec3 p_148525_) {
      Vec3 vec3 = p_148522_.m_20182_().m_82546_(p_148525_);
      boolean flag = GoalUtils.m_148442_(p_148522_, p_148523_);
      return m_148497_(p_148522_, p_148523_, p_148524_, vec3, flag);
   }

   @Nullable
   private static Vec3 m_148497_(PathfinderMob p_148498_, int p_148499_, int p_148500_, Vec3 p_148501_, boolean p_148502_) {
      return RandomPos.m_148542_(p_148498_, () -> {
         BlockPos blockpos = RandomPos.m_148553_(p_148498_.m_21187_(), p_148499_, p_148500_, 0, p_148501_.f_82479_, p_148501_.f_82481_, (double)((float)Math.PI / 2F));
         if (blockpos == null) {
            return null;
         } else {
            BlockPos blockpos1 = m_148513_(p_148498_, p_148499_, p_148502_, blockpos);
            return blockpos1 == null ? null : m_148518_(p_148498_, blockpos1);
         }
      });
   }

   @Nullable
   public static BlockPos m_148518_(PathfinderMob p_148519_, BlockPos p_148520_) {
      p_148520_ = RandomPos.m_148545_(p_148520_, p_148519_.f_19853_.m_151558_(), (p_148534_) -> {
         return GoalUtils.m_148461_(p_148519_, p_148534_);
      });
      return !GoalUtils.m_148445_(p_148519_, p_148520_) && !GoalUtils.m_148458_(p_148519_, p_148520_) ? p_148520_ : null;
   }

   @Nullable
   public static BlockPos m_148513_(PathfinderMob p_148514_, int p_148515_, boolean p_148516_, BlockPos p_148517_) {
      BlockPos blockpos = RandomPos.m_148537_(p_148514_, p_148515_, p_148514_.m_21187_(), p_148517_);
      return !GoalUtils.m_148451_(blockpos, p_148514_) && !GoalUtils.m_148454_(p_148516_, p_148514_, blockpos) && !GoalUtils.m_148448_(p_148514_.m_21573_(), blockpos) ? blockpos : null;
   }
}