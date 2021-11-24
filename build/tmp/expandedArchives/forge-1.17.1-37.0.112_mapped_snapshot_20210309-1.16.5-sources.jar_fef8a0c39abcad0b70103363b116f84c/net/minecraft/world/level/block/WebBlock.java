package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WebBlock extends Block implements net.minecraftforge.common.IForgeShearable {
   public WebBlock(BlockBehaviour.Properties p_58178_) {
      super(p_58178_);
   }

   public void m_7892_(BlockState p_58180_, Level p_58181_, BlockPos p_58182_, Entity p_58183_) {
      p_58183_.m_7601_(p_58180_, new Vec3(0.25D, (double)0.05F, 0.25D));
   }
}
