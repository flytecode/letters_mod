package net.minecraft.world.inventory;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ContainerLevelAccess {
   ContainerLevelAccess f_39287_ = new ContainerLevelAccess() {
      public <T> Optional<T> m_6721_(BiFunction<Level, BlockPos, T> p_39304_) {
         return Optional.empty();
      }
   };

   static ContainerLevelAccess m_39289_(final Level p_39290_, final BlockPos p_39291_) {
      return new ContainerLevelAccess() {
         public <T> Optional<T> m_6721_(BiFunction<Level, BlockPos, T> p_39311_) {
            return Optional.of(p_39311_.apply(p_39290_, p_39291_));
         }
      };
   }

   <T> Optional<T> m_6721_(BiFunction<Level, BlockPos, T> p_39298_);

   default <T> T m_39299_(BiFunction<Level, BlockPos, T> p_39300_, T p_39301_) {
      return this.m_6721_(p_39300_).orElse(p_39301_);
   }

   default void m_39292_(BiConsumer<Level, BlockPos> p_39293_) {
      this.m_6721_((p_39296_, p_39297_) -> {
         p_39293_.accept(p_39296_, p_39297_);
         return Optional.empty();
      });
   }
}