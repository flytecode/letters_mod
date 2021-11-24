package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;

public class SpringFeature extends Feature<SpringConfiguration> {
   public SpringFeature(Codec<SpringConfiguration> p_66914_) {
      super(p_66914_);
   }

   public boolean m_142674_(FeaturePlaceContext<SpringConfiguration> p_160404_) {
      SpringConfiguration springconfiguration = p_160404_.m_159778_();
      WorldGenLevel worldgenlevel = p_160404_.m_159774_();
      BlockPos blockpos = p_160404_.m_159777_();
      if (!springconfiguration.f_68128_.contains(worldgenlevel.m_8055_(blockpos.m_7494_()).m_60734_())) {
         return false;
      } else if (springconfiguration.f_68125_ && !springconfiguration.f_68128_.contains(worldgenlevel.m_8055_(blockpos.m_7495_()).m_60734_())) {
         return false;
      } else {
         BlockState blockstate = worldgenlevel.m_8055_(blockpos);
         if (!blockstate.m_60795_() && !springconfiguration.f_68128_.contains(blockstate.m_60734_())) {
            return false;
         } else {
            int i = 0;
            int j = 0;
            if (springconfiguration.f_68128_.contains(worldgenlevel.m_8055_(blockpos.m_142125_()).m_60734_())) {
               ++j;
            }

            if (springconfiguration.f_68128_.contains(worldgenlevel.m_8055_(blockpos.m_142126_()).m_60734_())) {
               ++j;
            }

            if (springconfiguration.f_68128_.contains(worldgenlevel.m_8055_(blockpos.m_142127_()).m_60734_())) {
               ++j;
            }

            if (springconfiguration.f_68128_.contains(worldgenlevel.m_8055_(blockpos.m_142128_()).m_60734_())) {
               ++j;
            }

            if (springconfiguration.f_68128_.contains(worldgenlevel.m_8055_(blockpos.m_7495_()).m_60734_())) {
               ++j;
            }

            int k = 0;
            if (worldgenlevel.m_46859_(blockpos.m_142125_())) {
               ++k;
            }

            if (worldgenlevel.m_46859_(blockpos.m_142126_())) {
               ++k;
            }

            if (worldgenlevel.m_46859_(blockpos.m_142127_())) {
               ++k;
            }

            if (worldgenlevel.m_46859_(blockpos.m_142128_())) {
               ++k;
            }

            if (worldgenlevel.m_46859_(blockpos.m_7495_())) {
               ++k;
            }

            if (j == springconfiguration.f_68126_ && k == springconfiguration.f_68127_) {
               worldgenlevel.m_7731_(blockpos, springconfiguration.f_68124_.m_76188_(), 2);
               worldgenlevel.m_6217_().m_5945_(blockpos, springconfiguration.f_68124_.m_76152_(), 0);
               ++i;
            }

            return i > 0;
         }
      }
   }
}