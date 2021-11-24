package net.minecraft.world.level.block.entity;

import net.minecraft.world.Container;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface Hopper extends Container {
   VoxelShape f_59296_ = Block.m_49796_(2.0D, 11.0D, 2.0D, 14.0D, 16.0D, 14.0D);
   VoxelShape f_59297_ = Block.m_49796_(0.0D, 16.0D, 0.0D, 16.0D, 32.0D, 16.0D);
   VoxelShape f_59298_ = Shapes.m_83110_(f_59296_, f_59297_);

   default VoxelShape m_59300_() {
      return f_59298_;
   }

   double m_6343_();

   double m_6358_();

   double m_6446_();
}