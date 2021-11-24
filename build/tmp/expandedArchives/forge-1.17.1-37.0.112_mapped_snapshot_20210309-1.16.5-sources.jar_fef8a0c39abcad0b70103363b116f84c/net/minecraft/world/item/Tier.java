package net.minecraft.world.item;

import net.minecraft.world.item.crafting.Ingredient;

public interface Tier {
   int m_6609_();

   float m_6624_();

   float m_6631_();

   @Deprecated // FORGE: Use TierSortingRegistry to define which tiers are better than others
   int m_6604_();

   int m_6601_();

   Ingredient m_6282_();

   @javax.annotation.Nullable default net.minecraft.tags.Tag<net.minecraft.world.level.block.Block> getTag() { return null; }
}
