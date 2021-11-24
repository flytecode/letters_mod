package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.Decoratable;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class ConfiguredDecorator<DC extends DecoratorConfiguration> implements Decoratable<ConfiguredDecorator<?>> {
   public static final Codec<ConfiguredDecorator<?>> f_70471_ = Registry.f_122845_.dispatch("type", (p_70488_) -> {
      return p_70488_.f_70472_;
   }, FeatureDecorator::m_70710_);
   private final FeatureDecorator<DC> f_70472_;
   private final DC f_70473_;

   public ConfiguredDecorator(FeatureDecorator<DC> p_70476_, DC p_70477_) {
      this.f_70472_ = p_70476_;
      this.f_70473_ = p_70477_;
   }

   public Stream<BlockPos> m_70480_(DecorationContext p_70481_, Random p_70482_, BlockPos p_70483_) {
      return this.f_70472_.m_7887_(p_70481_, p_70482_, this.f_70473_, p_70483_);
   }

   public String toString() {
      return String.format("[%s %s]", Registry.f_122845_.m_7981_(this.f_70472_), this.f_70473_);
   }

   public ConfiguredDecorator<?> m_7679_(ConfiguredDecorator<?> p_70486_) {
      return new ConfiguredDecorator<>(FeatureDecorator.f_70678_, new DecoratedDecoratorConfiguration(p_70486_, this));
   }

   public DC m_70484_() {
      return this.f_70473_;
   }
}