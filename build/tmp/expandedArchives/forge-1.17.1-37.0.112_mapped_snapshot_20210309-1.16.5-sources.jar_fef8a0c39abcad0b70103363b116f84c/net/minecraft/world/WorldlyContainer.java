package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public interface WorldlyContainer extends Container {
   int[] m_7071_(Direction p_19238_);

   boolean m_7155_(int p_19235_, ItemStack p_19236_, @Nullable Direction p_19237_);

   boolean m_7157_(int p_19239_, ItemStack p_19240_, Direction p_19241_);
}