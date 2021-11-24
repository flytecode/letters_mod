package net.minecraft.world.level.material;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EmptyFluid extends Fluid {
   public Item m_6859_() {
      return Items.f_41852_;
   }

   public boolean m_5486_(FluidState p_75930_, BlockGetter p_75931_, BlockPos p_75932_, Fluid p_75933_, Direction p_75934_) {
      return true;
   }

   public Vec3 m_7000_(BlockGetter p_75918_, BlockPos p_75919_, FluidState p_75920_) {
      return Vec3.f_82478_;
   }

   public int m_6718_(LevelReader p_75922_) {
      return 0;
   }

   protected boolean m_6759_() {
      return true;
   }

   protected float m_6752_() {
      return 0.0F;
   }

   public float m_6098_(FluidState p_75926_, BlockGetter p_75927_, BlockPos p_75928_) {
      return 0.0F;
   }

   public float m_7427_(FluidState p_75924_) {
      return 0.0F;
   }

   protected BlockState m_5804_(FluidState p_75937_) {
      return Blocks.f_50016_.m_49966_();
   }

   public boolean m_7444_(FluidState p_75944_) {
      return false;
   }

   public int m_7430_(FluidState p_75946_) {
      return 0;
   }

   public VoxelShape m_7999_(FluidState p_75939_, BlockGetter p_75940_, BlockPos p_75941_) {
      return Shapes.m_83040_();
   }
}