package net.minecraft.world.level.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class StainedGlassPaneBlock extends IronBarsBlock implements BeaconBeamBlock {
   private final DyeColor f_56836_;

   public StainedGlassPaneBlock(DyeColor p_56838_, BlockBehaviour.Properties p_56839_) {
      super(p_56839_);
      this.f_56836_ = p_56838_;
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52309_, Boolean.valueOf(false)).m_61124_(f_52310_, Boolean.valueOf(false)).m_61124_(f_52311_, Boolean.valueOf(false)).m_61124_(f_52312_, Boolean.valueOf(false)).m_61124_(f_52313_, Boolean.valueOf(false)));
   }

   public DyeColor m_7988_() {
      return this.f_56836_;
   }
}