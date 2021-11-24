package net.minecraft.world.level.storage.loot.entries;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.world.level.storage.loot.LootContext;

@FunctionalInterface
interface ComposableEntryContainer {
   ComposableEntryContainer f_79405_ = (p_79418_, p_79419_) -> {
      return false;
   };
   ComposableEntryContainer f_79406_ = (p_79409_, p_79410_) -> {
      return true;
   };

   boolean m_6562_(LootContext p_79426_, Consumer<LootPoolEntry> p_79427_);

   default ComposableEntryContainer m_79411_(ComposableEntryContainer p_79412_) {
      Objects.requireNonNull(p_79412_);
      return (p_79424_, p_79425_) -> {
         return this.m_6562_(p_79424_, p_79425_) && p_79412_.m_6562_(p_79424_, p_79425_);
      };
   }

   default ComposableEntryContainer m_79420_(ComposableEntryContainer p_79421_) {
      Objects.requireNonNull(p_79421_);
      return (p_79415_, p_79416_) -> {
         return this.m_6562_(p_79415_, p_79416_) || p_79421_.m_6562_(p_79415_, p_79416_);
      };
   }
}