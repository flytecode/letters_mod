package net.minecraft.world.level.levelgen;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

public interface Decoratable<R> {
   R m_7679_(ConfiguredDecorator<?> p_64157_);

   default R m_158241_(int p_158242_) {
      return this.m_7679_(FeatureDecorator.f_70682_.m_70720_(new ChanceDecoratorConfiguration(p_158242_)));
   }

   default R m_158243_(IntProvider p_158244_) {
      return this.m_7679_(FeatureDecorator.f_70683_.m_70720_(new CountConfiguration(p_158244_)));
   }

   default R m_64158_(int p_64159_) {
      return this.m_158243_(ConstantInt.m_146483_(p_64159_));
   }

   default R m_64160_(int p_64161_) {
      return this.m_158243_(UniformInt.m_146622_(0, p_64161_));
   }

   default R m_158245_(VerticalAnchor p_158246_, VerticalAnchor p_158247_) {
      return this.m_158248_(new RangeDecoratorConfiguration(UniformHeight.m_162034_(p_158246_, p_158247_)));
   }

   default R m_158250_(VerticalAnchor p_158251_, VerticalAnchor p_158252_) {
      return this.m_158248_(new RangeDecoratorConfiguration(TrapezoidHeight.m_162006_(p_158251_, p_158252_)));
   }

   default R m_158248_(RangeDecoratorConfiguration p_158249_) {
      return this.m_7679_(FeatureDecorator.f_70692_.m_70720_(p_158249_));
   }

   default R m_64152_() {
      return this.m_7679_(FeatureDecorator.f_70687_.m_70720_(NoneDecoratorConfiguration.f_67811_));
   }
}