package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.RuinedPortalFeature;

public class RuinedPortalConfiguration implements FeatureConfiguration {
   public static final Codec<RuinedPortalConfiguration> f_68054_ = RuinedPortalFeature.Type.f_66735_.fieldOf("portal_type").xmap(RuinedPortalConfiguration::new, (p_68060_) -> {
      return p_68060_.f_68055_;
   }).codec();
   public final RuinedPortalFeature.Type f_68055_;

   public RuinedPortalConfiguration(RuinedPortalFeature.Type p_68058_) {
      this.f_68055_ = p_68058_;
   }
}