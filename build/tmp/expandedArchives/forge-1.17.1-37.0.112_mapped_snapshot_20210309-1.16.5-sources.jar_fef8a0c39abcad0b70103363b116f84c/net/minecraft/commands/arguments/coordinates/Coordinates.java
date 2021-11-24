package net.minecraft.commands.arguments.coordinates;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public interface Coordinates {
   Vec3 m_6955_(CommandSourceStack p_119566_);

   Vec2 m_6970_(CommandSourceStack p_119567_);

   default BlockPos m_119568_(CommandSourceStack p_119569_) {
      return new BlockPos(this.m_6955_(p_119569_));
   }

   boolean m_6888_();

   boolean m_6892_();

   boolean m_6900_();
}