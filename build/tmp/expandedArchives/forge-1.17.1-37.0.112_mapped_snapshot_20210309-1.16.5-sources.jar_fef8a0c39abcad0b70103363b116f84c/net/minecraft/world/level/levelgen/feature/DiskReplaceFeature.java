package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;

public class DiskReplaceFeature extends BaseDiskFeature {
   public DiskReplaceFeature(Codec<DiskConfiguration> p_65613_) {
      super(p_65613_);
   }

   public boolean m_142674_(FeaturePlaceContext<DiskConfiguration> p_159573_) {
      return !p_159573_.m_159774_().m_6425_(p_159573_.m_159777_()).m_76153_(FluidTags.f_13131_) ? false : super.m_142674_(p_159573_);
   }
}