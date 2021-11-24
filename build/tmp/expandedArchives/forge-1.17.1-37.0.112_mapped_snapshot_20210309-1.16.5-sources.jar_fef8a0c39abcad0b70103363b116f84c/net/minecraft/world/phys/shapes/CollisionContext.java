package net.minecraft.world.phys.shapes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;

public interface CollisionContext {
   static CollisionContext m_82749_() {
      return EntityCollisionContext.f_82865_;
   }

   static CollisionContext m_82750_(Entity p_82751_) {
      return new EntityCollisionContext(p_82751_);
   }

   boolean m_6226_();

   boolean m_6513_(VoxelShape p_82755_, BlockPos p_82756_, boolean p_82757_);

   boolean m_142055_(Item p_165990_);

   boolean m_7142_(Item p_82752_);

   boolean m_7875_(FluidState p_82753_, FlowingFluid p_82754_);
}