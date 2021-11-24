package net.minecraft.world.level.block;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class MelonBlock extends StemGrownBlock {
   public MelonBlock(BlockBehaviour.Properties p_54829_) {
      super(p_54829_);
   }

   public StemBlock m_7161_() {
      return (StemBlock)Blocks.f_50190_;
   }

   public AttachedStemBlock m_7810_() {
      return (AttachedStemBlock)Blocks.f_50188_;
   }
}