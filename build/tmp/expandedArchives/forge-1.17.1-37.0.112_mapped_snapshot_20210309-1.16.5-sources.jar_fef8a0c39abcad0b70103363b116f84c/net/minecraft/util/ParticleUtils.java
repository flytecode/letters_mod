package net.minecraft.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleUtils {
   public static void m_144962_(Level p_144963_, BlockPos p_144964_, ParticleOptions p_144965_, UniformInt p_144966_) {
      for(Direction direction : Direction.values()) {
         int i = p_144966_.m_142270_(p_144963_.f_46441_);

         for(int j = 0; j < i; ++j) {
            m_144957_(p_144963_, p_144964_, direction, p_144965_);
         }
      }

   }

   public static void m_144967_(Direction.Axis p_144968_, Level p_144969_, BlockPos p_144970_, double p_144971_, ParticleOptions p_144972_, UniformInt p_144973_) {
      Vec3 vec3 = Vec3.m_82512_(p_144970_);
      boolean flag = p_144968_ == Direction.Axis.X;
      boolean flag1 = p_144968_ == Direction.Axis.Y;
      boolean flag2 = p_144968_ == Direction.Axis.Z;
      int i = p_144973_.m_142270_(p_144969_.f_46441_);

      for(int j = 0; j < i; ++j) {
         double d0 = vec3.f_82479_ + Mth.m_14064_(p_144969_.f_46441_, -1.0D, 1.0D) * (flag ? 0.5D : p_144971_);
         double d1 = vec3.f_82480_ + Mth.m_14064_(p_144969_.f_46441_, -1.0D, 1.0D) * (flag1 ? 0.5D : p_144971_);
         double d2 = vec3.f_82481_ + Mth.m_14064_(p_144969_.f_46441_, -1.0D, 1.0D) * (flag2 ? 0.5D : p_144971_);
         double d3 = flag ? Mth.m_14064_(p_144969_.f_46441_, -1.0D, 1.0D) : 0.0D;
         double d4 = flag1 ? Mth.m_14064_(p_144969_.f_46441_, -1.0D, 1.0D) : 0.0D;
         double d5 = flag2 ? Mth.m_14064_(p_144969_.f_46441_, -1.0D, 1.0D) : 0.0D;
         p_144969_.m_7106_(p_144972_, d0, d1, d2, d3, d4, d5);
      }

   }

   public static void m_144957_(Level p_144958_, BlockPos p_144959_, Direction p_144960_, ParticleOptions p_144961_) {
      Vec3 vec3 = Vec3.m_82512_(p_144959_);
      int i = p_144960_.m_122429_();
      int j = p_144960_.m_122430_();
      int k = p_144960_.m_122431_();
      double d0 = vec3.f_82479_ + (i == 0 ? Mth.m_14064_(p_144958_.f_46441_, -0.5D, 0.5D) : (double)i * 0.55D);
      double d1 = vec3.f_82480_ + (j == 0 ? Mth.m_14064_(p_144958_.f_46441_, -0.5D, 0.5D) : (double)j * 0.55D);
      double d2 = vec3.f_82481_ + (k == 0 ? Mth.m_14064_(p_144958_.f_46441_, -0.5D, 0.5D) : (double)k * 0.55D);
      double d3 = i == 0 ? Mth.m_14064_(p_144958_.f_46441_, -1.0D, 1.0D) : 0.0D;
      double d4 = j == 0 ? Mth.m_14064_(p_144958_.f_46441_, -1.0D, 1.0D) : 0.0D;
      double d5 = k == 0 ? Mth.m_14064_(p_144958_.f_46441_, -1.0D, 1.0D) : 0.0D;
      p_144958_.m_7106_(p_144961_, d0, d1, d2, d3, d4, d5);
   }
}