package net.minecraft.core;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Set;

public enum Direction8 {
   NORTH(Direction.NORTH),
   NORTH_EAST(Direction.NORTH, Direction.EAST),
   EAST(Direction.EAST),
   SOUTH_EAST(Direction.SOUTH, Direction.EAST),
   SOUTH(Direction.SOUTH),
   SOUTH_WEST(Direction.SOUTH, Direction.WEST),
   WEST(Direction.WEST),
   NORTH_WEST(Direction.NORTH, Direction.WEST);

   private final Set<Direction> f_122586_;

   private Direction8(Direction... p_122592_) {
      this.f_122586_ = Sets.immutableEnumSet(Arrays.asList(p_122592_));
   }

   public Set<Direction> m_122593_() {
      return this.f_122586_;
   }
}