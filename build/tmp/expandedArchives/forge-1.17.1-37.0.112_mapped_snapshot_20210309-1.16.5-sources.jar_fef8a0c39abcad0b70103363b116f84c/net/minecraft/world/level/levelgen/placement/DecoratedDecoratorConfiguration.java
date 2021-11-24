package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class DecoratedDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<DecoratedDecoratorConfiguration> f_70570_ = RecordCodecBuilder.create((p_70579_) -> {
      return p_70579_.group(ConfiguredDecorator.f_70471_.fieldOf("outer").forGetter(DecoratedDecoratorConfiguration::m_70577_), ConfiguredDecorator.f_70471_.fieldOf("inner").forGetter(DecoratedDecoratorConfiguration::m_70580_)).apply(p_70579_, DecoratedDecoratorConfiguration::new);
   });
   private final ConfiguredDecorator<?> f_70571_;
   private final ConfiguredDecorator<?> f_70572_;

   public DecoratedDecoratorConfiguration(ConfiguredDecorator<?> p_70575_, ConfiguredDecorator<?> p_70576_) {
      this.f_70571_ = p_70575_;
      this.f_70572_ = p_70576_;
   }

   public ConfiguredDecorator<?> m_70577_() {
      return this.f_70571_;
   }

   public ConfiguredDecorator<?> m_70580_() {
      return this.f_70572_;
   }
}